package cl.gmo.pos.venta.web.helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;


import cl.gmo.pos.venta.reportes.CreaReportes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoLineaBean;
import cl.gmo.pos.venta.web.beans.ListaTotalDiaBean;
import cl.gmo.pos.venta.web.beans.ListadoBoletasBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.PresupuestoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;

import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.InformeOpticoForm;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public class ReportesHelper extends Utils{
	Utils util= new Utils();
	Logger log = Logger.getLogger( this.getClass() );
	public void creaBoleta(HttpSession session,HttpServletResponse response) throws Exception{
		log.info("ReportesHelper:creaBoleta inicio");

		ArrayList<ProductosBean> listProductos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_PRODUCTOS);
		ArrayList<ProductosBean> listProductosAdicionales = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_PRODUC_ADICIONALES);
				
		Integer total = (Integer)session.getAttribute(Constantes.STRING_REPORTER_TOTAL);
		String sucursal = (String)session.getAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL);
		String usuario = (String)session.getAttribute(Constantes.STRING_DESC_USUARIO);
		String fechaEntrega = Constantes.STRING_BLANCO;
		double descuento = (Double)session.getAttribute(Constantes.STRING_DESCUENTO);
		SeleccionPagoForm cabeceraBoleta = (SeleccionPagoForm)session.getAttribute(Constantes.STRING_REPORTER_CABECERA_BOLETA);
		ArrayList<PagoBean> pagos = (ArrayList<PagoBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_PAGOS);
		ArrayList<FormaPagoBean> formaPago = (ArrayList<FormaPagoBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_FORMA_PAGO);
		
		/*Traigo los Datos de la Tienda   20140807*/
		
		ArrayList<TiendaBean> arrTienda = PosUtilesFacade.traeDatosTienda(sucursal);
		
		int totalBoleta=0;
		int pagos_anteriores=0;
		int pagos_actuales=0;
		

		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put(Constantes.STRING_REPORTER_CLIENTE, cabeceraBoleta.getNombre_cliente());
		if (cabeceraBoleta.getOrigen().equals(Constantes.STRING_DIRECTA)) {
			parametros.put(Constantes.STRING_REPORTER_RUT, Constantes.STRING_BLANCO);
			parametros.put(Constantes.STRING_REPORTER_FECHA_PEDIDO, Constantes.STRING_BLANCO);
			parametros.put(Constantes.STRING_REPORTER_FECHA_PEDIDO_TITULO, Constantes.STRING_BLANCO);
			fechaEntrega = null;
		}
		else
		{
			ClienteBean cliente = new ClienteBean();
			cliente = this.traeCliente(null, cabeceraBoleta.getCodigo());
			parametros.put(Constantes.STRING_REPORTER_RUT, cabeceraBoleta.getNif());
			parametros.put(Constantes.STRING_REPORTER_FECHA_PEDIDO, (String)session.getAttribute(Constantes.STRING_FECHA));
			parametros.put(Constantes.STRING_REPORTER_FECHA_PEDIDO_TITULO, Constantes.STRING_FECHA_TITULO);
			fechaEntrega = (String)session.getAttribute(Constantes.STRING_FECHA_ENTREGA);
		}
		
		//parametros.put(Constantes.STRING_REPORTER_FECHA_PEDIDO, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_HORA,util.traeHoraString());
		parametros.put(Constantes.STRING_REPORTER_TIENDA, sucursal);
		//SE REEMPLAZA A PETICION DE C. HAUMANI POR LA FECHA DE ENTREGA 
		//parametros.put(Constantes.STRING_REPORTER_FECHA, util.traeFechaHoyFormateadaString());
		parametros.put(Constantes.STRING_REPORTER_FECHA,fechaEntrega);
		parametros.put(Constantes.STRING_REPORTER_NALBARAN, cabeceraBoleta.getSerie());
		//parametros.put(Constantes.STRING_REPORTER_FEHCA_ENTRADA, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_VENDEDOR, usuario);
		
		//parametros.put(Constantes.STRING_REPORTER_ANTICIPO_PAGADO, Integer.toString(Constantes.INT_CERO));
		
		parametros.put(Constantes.STRING_REPORTER_FORMA_PAGO_1, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_FORMA_PAGO_2, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_FORMA_PAGO_3, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_FORMA_PAGO_4, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGO_1, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGO_2, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGO_3, Constantes.STRING_BLANCO);
		parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGO_4, Constantes.STRING_BLANCO);
		if(null== fechaEntrega){
			parametros.put(Constantes.STRING_FECHA_ENTREGA, Constantes.STRING_BLANCO);
		}else{
			parametros.put(Constantes.STRING_FECHA_ENTREGA, "Fecha Entrega  "+fechaEntrega+" desde las 18:30 hrs.");
		}
		
		
		

		for(int i=1,x=0;x<pagos.size();i++,x++){
			log.info("ReportesHelper:creaBoleta entrando ciclo for");
			PagoBean pago = pagos.get(x);
			parametros.put(Constantes.STRING_REPORTER_FORMA_PAGO+i, util.buscaFormaPago(formaPago,pago));
			parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGO+i, "$ "+util.getNumber(Integer.toString(pago.getCantidad())));
			
			totalBoleta=totalBoleta+pago.getCantidad();
			if (pago.isTiene_doc()) {
				pagos_anteriores = pagos_anteriores+pago.getCantidad();
			}
			else
			{
				pagos_actuales = pagos_actuales+pago.getCantidad();
			}
		}

		if (Constantes.STRING_TRUE.equals(cabeceraBoleta.getTiene_documentos())) 
		{
//			if (totalBoleta == cabeceraBoleta.getV_total())  {
				// total con anticipo previo
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTALPAGAR, Constantes.STRING_BOLETA_TOTAL_PAGAR_POR_ANTICIPO);
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTAL, "TOTAL				" + "$ "+util.getNumber(Integer.toString(cabeceraBoleta.getV_total_parcial())));
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_ANTICIPO, "ANTICIPO			" + "$ "+util.getNumber(Integer.toString(totalBoleta)));
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_PENDIENTE,"PENDIENTE			" + "$ "+util.getNumber(Integer.toString(cabeceraBoleta.getDiferencia())));
//			}
//			else
//			{
//				//otro anticipo
//				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTALPAGAR, Constantes.STRING_BOLETA_TOTAL_PAGAR_POR_ANTICIPO);
//			}
			
		}
		else
		{
			if (totalBoleta == cabeceraBoleta.getV_total()) 
			{
				//total sin anticipo
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTALPAGAR, Constantes.STRING_BOLETA_TOTAL_PAGAR);
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTAL, "TOTAL			" + "$ "+util.getNumber(Integer.toString(totalBoleta)));
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_ANTICIPO, Constantes.STRING_BLANCO);
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_PENDIENTE,Constantes.STRING_BLANCO);
				
			}
			else
			{
				//solo anticipo
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTALPAGAR, Constantes.STRING_BOLETA_TOTAL_PAGAR_POR_ANTICIPO);
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_TOTAL, "TOTAL				" + "$ "+util.getNumber(Integer.toString(cabeceraBoleta.getV_total_parcial())));
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_ANTICIPO, "ANTICIPO			" + "$ "+util.getNumber(Integer.toString(totalBoleta)));
				parametros.put(Constantes.STRING_REPORTER_RESUMEN_PENDIENTE,"PENDIENTE			" + "$ "+util.getNumber(Integer.toString(cabeceraBoleta.getDiferencia())));
			}
			
		}
		
		parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGAR, util.getNumber(Integer.toString(pagos_actuales)));
		parametros.put(Constantes.STRING_REPORTER_TOTAL, util.getNumber(Integer.toString(totalBoleta)));
		
		/*
		 * LMARIN 20140807
		 * Agrego Fono
		 */
		if(arrTienda.get(0).getTelefono1() != null){
			parametros.put("telefono",arrTienda.get(0).getTelefono1());
		}else{
			parametros.put("telefono","6008220200");
		}
		
		
		
		//InputStream io = ReportesHelper.class.getResourceAsStream(Constantes.STRING_REPORTER_BOLETA_JASPER);
		System.out.println("Paso por generar boleta"); 
		InputStream io = ReportesHelper.class.getResourceAsStream(""); 
		
		Collection<ProductosBean> data = new ArrayList<ProductosBean>();
		
		for(ProductosBean articulo:listProductos){
			log.info("ReportesHelper:creaBoleta entrando ciclo for");
			ProductosBean articuloPrint = new ProductosBean();
			articuloPrint.setCantidad(articulo.getCantidad());
			articuloPrint.setDescripcion(articulo.getDescripcion());
			articuloPrint.setCod_barra(articulo.getCod_barra());
			articuloPrint.setImporte(articulo.getImporte());
			articuloPrint.setPrecio(articulo.getPrecio());
			articuloPrint.setDescuento(articulo.getDescuento());
			
			String descripcion =articuloPrint.getCod_barra()+"  "+articuloPrint.getDescripcion();
			articuloPrint.setDescripcion(descripcion);
			

			data.add(articuloPrint);
		}
		if(null != listProductosAdicionales){
			
			for(ProductosBean articuloAd:listProductosAdicionales){
				log.info("ReportesHelper:buscaFormaPago entrando ciclo for");
				String descripcion =articuloAd.getCod_barra()+"  "+articuloAd.getDescripcion();
				articuloAd.setDescripcion(descripcion);
				data.add(articuloAd);
			}

		}
		byte[] bytes = null;
		
		if (cabeceraBoleta.getDiferencia()==0) 
		{
			bytes = new CreaReportes().obtenerJasper(parametros, io,data);
		}
		else
		{
			ProductosBean articuloPrint = new ProductosBean();
			articuloPrint.setDescripcion(Constantes.STRING_BLANCO);
			articuloPrint.setCod_barra(Constantes.STRING_BLANCO);
			Collection<ProductosBean> data2 = new ArrayList<ProductosBean>();
			data2.add(articuloPrint);
			bytes = new CreaReportes().obtenerJasper(parametros, io,data2);
		}
		
		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaBoleta error catch",e);
		}
	}

	public void creaListadoBoletas(HttpSession session,HttpServletResponse response){
		log.info("ReportesHelper:creaListadoBoletas inicio");
			InputStream io = ReportesHelper.class.getResourceAsStream("listaBoletas.jasper");
		
			
				String sucursal = (String)session.getAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL);
				String fechaBusqueda = (String)session.getAttribute("fechaBusquedaBoletas");
				ArrayList<ListadoBoletasBean> listadoBoletasBean = (ArrayList<ListadoBoletasBean>)session.getAttribute("listasBoletas");
	
				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA, fechaBusqueda);
				parametros.put(Constantes.STRING_ACTION_FECHA_ACTUAL, util.traeFechaHoyFormateadaString());
				parametros.put(Constantes.STRING_SUCURSAL, sucursal);

				byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,listadoBoletasBean);
				
				response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
				response.setContentLength(bytes.length);
				response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
				ServletOutputStream servletOutputStream;
				try {
					servletOutputStream = response.getOutputStream();
					servletOutputStream.write(bytes, 0, bytes.length);
					servletOutputStream.flush();
					servletOutputStream.close();
				} catch (IOException e) {
					log.error("ReportesHelper:creaListadoBoletas error catch",e);
				}
		}
	
	public void creaListadoPresupuestos(HttpSession session,HttpServletResponse response){
		log.info("ReportesHelper:creaListadoPresupuestos inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("listadoPresupuestos.jasper");
	
		
			String sucursal = (String)session.getAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL);
			String fechaBusqueda = (String)session.getAttribute(Constantes.STRING_ACTION_FECHA_BUSQUEDA_PRESUPUESTO);
			String cerrado = (String)session.getAttribute(Constantes.STRING_CERRADO);
			ArrayList<PresupuestoBean> listadoPresupuestoBean = (ArrayList<PresupuestoBean>)session.getAttribute(Constantes.STRING_ACTION_LISTA_PRESUPUESTO);
			ArrayList<PresupuestoBean> reporteListaPresupuestosBean = new ArrayList<PresupuestoBean>();
			
			for(PresupuestoBean tmpPresupuesto:listadoPresupuestoBean){
				log.info("ReportesHelper:creaListadoPresupuestos entrando ciclo for");
				PresupuestoBean cabeceraPresup = new PresupuestoBean();
				cabeceraPresup.setNumero(tmpPresupuesto.getNumero());
				cabeceraPresup.setFecha(tmpPresupuesto.getFecha());
				cabeceraPresup.setAgente(tmpPresupuesto.getAgente());
				cabeceraPresup.setNombres(tmpPresupuesto.getNombres());
				cabeceraPresup.setApellido(tmpPresupuesto.getApellido());
				cabeceraPresup.setDescuento(tmpPresupuesto.getDescuento());
				cabeceraPresup.setForma_pago(tmpPresupuesto.getForma_pago());
				cabeceraPresup.setNif_cliente(tmpPresupuesto.getNif_cliente());
				if(Constantes.STRING_BLANCO.equals(tmpPresupuesto.getNumero())&& Constantes.STRING_BLANCO.equals(tmpPresupuesto.getAgente())){
					cabeceraPresup.setLinea(Constantes.STRING_BLANCO);
				}else{
					cabeceraPresup.setLinea("_______________________________________________________________________________________________________________________________________________________________________");
				}
				reporteListaPresupuestosBean.add(cabeceraPresup);
				for(ListaPresupuestoLineaBean tmp:tmpPresupuesto.getLineas()){
					log.info("ReportesHelper:creaListadoPresupuestos entrando ciclo for");
					PresupuestoBean lineaPresup = new PresupuestoBean();
					lineaPresup.setCodigo(tmp.getCodigo());
					if(null == tmp.getDescripcion()){
						lineaPresup.setDescripcion(Constantes.STRING_BLANCO);
					}else{
						lineaPresup.setDescripcion(tmp.getDescripcion());
					}
						
					lineaPresup.setCantidad(tmp.getCantidad());
					lineaPresup.setPrecioIva(util.getNumber(tmp.getPrecioIva()));
					lineaPresup.setDescuentoArt(tmp.getDescuento());
					reporteListaPresupuestosBean.add(lineaPresup);
				}
				PresupuestoBean totalPresup = new PresupuestoBean();
				totalPresup.setTotal(util.getNumber(tmpPresupuesto.getTotal()));
				totalPresup.setTextoTotal("B. Imponible Presupuesto");
				reporteListaPresupuestosBean.add(totalPresup);
			}
	
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA, fechaBusqueda);
			parametros.put(Constantes.STRING_ACTION_FECHA_ACTUAL, util.traeFechaHoyFormateadaString());
			parametros.put(Constantes.STRING_LOCAL, sucursal);
			parametros.put(Constantes.STRING_CERRADO, cerrado);

			byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,reporteListaPresupuestosBean);
			
			response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
			response.setContentLength(bytes.length);
			response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
			ServletOutputStream servletOutputStream;
			try {
				servletOutputStream = response.getOutputStream();
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (IOException e) {
				log.error("ReportesHelper:creaListadoPresupuestos error catch",e);
			}
	}
	
	public void creaListadoTotalDia(HttpSession session,HttpServletResponse response){
		log.info("ReportesHelper:creaListadoTotalDia inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("listadoTotalDia.jasper");
		

		 ArrayList<ListaTotalDiaBean> listaVenta =(ArrayList<ListaTotalDiaBean>)session.getAttribute("listaVenta");
		 ArrayList<ListaTotalDiaBean> listaAnticipo =(ArrayList<ListaTotalDiaBean>)session.getAttribute("listaAnticipo");
		 ArrayList<ListaTotalDiaBean> listaVentaDevolucion =(ArrayList<ListaTotalDiaBean>)session.getAttribute("listaVentaDevolucion");
		 ArrayList<ListaTotalDiaBean> listaVentaEncargo =(ArrayList<ListaTotalDiaBean>)session.getAttribute("listaVentaEncargo");
		 ArrayList<ListaTotalDiaBean> listaVentaEntrega =(ArrayList<ListaTotalDiaBean>)session.getAttribute("listaVentaEntrega");
		 int numero_movimientos = Integer.parseInt(session.getAttribute(Constantes.STRING_ACTION_LISTA_VENTA_MOVIMIENTOS).toString());
		 String fecha = session.getAttribute(Constantes.STRING_ACTION_LISTA_VENTA_FECHA).toString();
		 int total_cobrado = 0;
		 int total_movimientos = 0;

		 ArrayList<ListaTotalDiaBean> listaTotalDia = new ArrayList<ListaTotalDiaBean>();
		 ListaTotalDiaBean textoEntrega= new ListaTotalDiaBean();
		 textoEntrega.setTexto(Constantes.STRING_ACTION_ENTREGAS);
		 textoEntrega.setLinea("______________________________________________________________________________________________________________________________");
		 textoEntrega.setFecha(fecha);
		 listaTotalDia.add(textoEntrega);
		 log.info("ReportesHelper:creaListadoTotalDia entrando ciclo for: listaVentaEntrega");
		 for(ListaTotalDiaBean tmp:listaVentaEntrega){
			 log.info("ReportesHelper:creaListadoTotalDia recorriendo ciclo for: listaVentaEntrega");
			ListaTotalDiaBean listaTotalDiaBean= new ListaTotalDiaBean();
			listaTotalDiaBean.setTexto(Constantes.STRING_ACTION_ENTREGAS);
			listaTotalDiaBean.setCodigo(tmp.getCodigo());
			listaTotalDiaBean.setTipoAgente(tmp.getTipoAgente());
			listaTotalDiaBean.setTotal(tmp.getTotal());
			listaTotalDiaBean.setCobrado(tmp.getCobrado());
			if(null != tmp.getfPagado()){
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
			}else{
				listaTotalDiaBean.setfPagado("");
			}
			
			if(null !=tmp.getNumeroDoc()){
				listaTotalDiaBean.setNumeroDoc(tmp.getNumeroDoc());
			}else{
				listaTotalDiaBean.setNumeroDoc("");
			}
			
			if(null != tmp.getTipo()){
				listaTotalDiaBean.setTipo(tmp.getTipo());	
			}else{
				listaTotalDiaBean.setTipo("");
			}
			
			if(null != tmp.getTipo()){
				listaTotalDiaBean.setTipo(tmp.getTipo());
			}else{
				listaTotalDiaBean.setTipo("");
			}
				
			if(null != tmp.getMontoDoc()){
				listaTotalDiaBean.setMontoDoc(tmp.getMontoDoc());
			}else{
				listaTotalDiaBean.setMontoDoc("");
			}
			
			listaTotalDiaBean.setFecha(fecha);
			listaTotalDia.add(listaTotalDiaBean);
			
		}
		 ListaTotalDiaBean textoVentaDirec= new ListaTotalDiaBean();
		 textoVentaDirec.setTexto(Constantes.STRING_TEXTO_VENTAS_DIRECTAS);
		 textoVentaDirec.setLinea("______________________________________________________________________________________________________________________________");
		 textoVentaDirec.setFecha(fecha);
		 listaTotalDia.add(textoVentaDirec);
		 log.info("ReportesHelper:creaListadoTotalDia entrando ciclo for: listaVenta");
		 for(ListaTotalDiaBean tmp:listaVenta){
			 log.info("ReportesHelper:creaListadoTotalDia recorriendo ciclo for: listaVenta");
			 
				 	ListaTotalDiaBean listaTotalDiaBean= new ListaTotalDiaBean();
				 	listaTotalDiaBean.setTexto(Constantes.STRING_TEXTO_VENTAS_DIRECTAS);
					listaTotalDiaBean.setCodigo(tmp.getCodigo());
					listaTotalDiaBean.setTipoAgente(tmp.getTipoAgente());
					listaTotalDiaBean.setTotal(tmp.getTotal());
					listaTotalDiaBean.setCobrado(tmp.getCobrado());
					
					if(null != tmp.getfPagado()){
						listaTotalDiaBean.setfPagado(tmp.getfPagado());
					}else{
						listaTotalDiaBean.setfPagado("");
					}
					
					if(null != tmp.getfPagado()){
						listaTotalDiaBean.setfPagado(tmp.getfPagado());
					}else{
						listaTotalDiaBean.setfPagado("");
					}
					
					if(null != tmp.getNumeroDoc()){
						listaTotalDiaBean.setNumeroDoc(tmp.getNumeroDoc());
					}else{
						listaTotalDiaBean.setNumeroDoc("");
					}
					
					if(null != tmp.getTipo()){
						listaTotalDiaBean.setTipo(tmp.getTipo());
					}else{
						listaTotalDiaBean.setTipo("");
					}
					
					if(null != tmp.getMontoDoc()){
						listaTotalDiaBean.setMontoDoc(tmp.getMontoDoc());
					}else{
						listaTotalDiaBean.setMontoDoc("");
					}
					
					
					listaTotalDiaBean.setFecha(fecha);
					listaTotalDia.add(listaTotalDiaBean);
					
					total_cobrado += tmp.getCobrado_num();
					total_movimientos  += tmp.getTotal_num();

			
			}
		 ListaTotalDiaBean textoEncargo= new ListaTotalDiaBean();
		 textoEncargo.setTexto(Constantes.STRING_TEXTO_VENTAS_ENCARGOS);
		 textoEncargo.setLinea("______________________________________________________________________________________________________________________________");
		 textoEncargo.setFecha(fecha);
		 listaTotalDia.add(textoEncargo);
		 log.info("ReportesHelper:creaListadoTotalDia entrando ciclo for: listaVentaEncargo");
		 for(ListaTotalDiaBean tmp:listaVentaEncargo){
			 log.info("ReportesHelper:creaListadoTotalDia recorriendo ciclo for: listaVentaEncargo");
			ListaTotalDiaBean listaTotalDiaBean= new ListaTotalDiaBean();
			listaTotalDiaBean.setTexto(Constantes.STRING_TEXTO_VENTAS_ENCARGOS);
			listaTotalDiaBean.setCodigo(tmp.getCodigo());
			listaTotalDiaBean.setTipoAgente(tmp.getTipoAgente());
			listaTotalDiaBean.setTotal(tmp.getTotal());
			listaTotalDiaBean.setCobrado(tmp.getCobrado());
						
			if(null != tmp.getfPagado()){
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
			}else{
				listaTotalDiaBean.setfPagado("");
			}
			
			if(null != tmp.getfPagado()){
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
			}else{
				listaTotalDiaBean.setfPagado("");
			}
			
			if(null != tmp.getNumeroDoc()){
				listaTotalDiaBean.setNumeroDoc(tmp.getNumeroDoc());
			}else{
				listaTotalDiaBean.setNumeroDoc("");
			}
			
			if(null != tmp.getTipo()){
				listaTotalDiaBean.setTipo(tmp.getTipo());
			}else{
				listaTotalDiaBean.setTipo("");
			}
			
			if(null != tmp.getMontoDoc()){
				listaTotalDiaBean.setMontoDoc(tmp.getMontoDoc());
			}else{
				listaTotalDiaBean.setMontoDoc("");
			}
			
			
			
			listaTotalDiaBean.setFecha(fecha);
			listaTotalDia.add(listaTotalDiaBean);
			
			total_cobrado += tmp.getCobrado_num();
			total_movimientos  += tmp.getTotal_num();
		}
		 ListaTotalDiaBean textoAnticipo= new ListaTotalDiaBean();
		 textoAnticipo.setTexto(Constantes.STRING_TEXTO_ANTICIPOS_ENCARGOS_ANTERIORES);
		 textoAnticipo.setLinea("______________________________________________________________________________________________________________________________");
		 textoAnticipo.setFecha(fecha);
		 listaTotalDia.add(textoAnticipo);
		log.info("ReportesHelper:creaListadoTotalDia entrando ciclo for: listaAnticipo");
		for(ListaTotalDiaBean tmp:listaAnticipo){
			log.info("ReportesHelper:creaListadoTotalDia recorriendo ciclo for: listaAnticipo");
			ListaTotalDiaBean listaTotalDiaBean= new ListaTotalDiaBean();
			listaTotalDiaBean.setTexto(Constantes.STRING_TEXTO_ANTICIPOS_ENCARGOS_ANTERIORES);
			listaTotalDiaBean.setCodigo(tmp.getCodigo());
			listaTotalDiaBean.setTipoAgente(tmp.getTipoAgente());
			listaTotalDiaBean.setTotal(tmp.getTotal());
			listaTotalDiaBean.setCobrado(tmp.getCobrado());
			if(null != tmp.getfPagado()){
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
			}else{
				listaTotalDiaBean.setfPagado("");
			}
			
			if(null != tmp.getfPagado()){
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
			}else{
				listaTotalDiaBean.setfPagado("");
			}
			
			if(null != tmp.getNumeroDoc()){
				listaTotalDiaBean.setNumeroDoc(tmp.getNumeroDoc());
			}else{
				listaTotalDiaBean.setNumeroDoc("");
			}
			
			if(null != tmp.getTipo()){
				listaTotalDiaBean.setTipo(tmp.getTipo());
			}else{
				listaTotalDiaBean.setTipo("");
			}
			
			if(null != tmp.getMontoDoc()){
				listaTotalDiaBean.setMontoDoc(tmp.getMontoDoc());
			}else{
				listaTotalDiaBean.setMontoDoc("");
			}
			
			listaTotalDiaBean.setFecha(fecha);
			listaTotalDia.add(listaTotalDiaBean);
			
			total_cobrado += tmp.getCobrado_num();
		}
		 ListaTotalDiaBean textoDevolucion= new ListaTotalDiaBean();
		 textoDevolucion.setTexto(Constantes.STRING_TEXTO_DEVOLUCIONES);
		 textoDevolucion.setLinea("______________________________________________________________________________________________________________________________");
		 textoDevolucion.setFecha(fecha);
		 listaTotalDia.add(textoDevolucion);
		 log.info("ReportesHelper:creaListadoTotalDia entrando ciclo for: listaVentaDevolucion");
		 	String codigo_0=Constantes.STRING_BLANCO;
			String codigo_1=Constantes.STRING_BLANCO;
		 for(ListaTotalDiaBean tmp:listaVentaDevolucion)
		 {
			 log.info("ReportesHelper:creaListadoTotalDia recorriendo ciclo for: listaVentaDevolucion");
			ListaTotalDiaBean listaTotalDiaBean= new ListaTotalDiaBean();
			codigo_0=tmp.getCodigo().trim();
			if (codigo_0.equals(codigo_1)) {
				
				listaTotalDiaBean.setTexto(Constantes.STRING_TEXTO_DEVOLUCIONES);
				listaTotalDiaBean.setCodigo(Constantes.STRING_BLANCO);
				listaTotalDiaBean.setTipoAgente(Constantes.STRING_BLANCO);
				listaTotalDiaBean.setTotal(Constantes.STRING_BLANCO);
				listaTotalDiaBean.setCobrado(tmp.getCobrado());
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
				listaTotalDiaBean.setFecha(fecha);
				listaTotalDia.add(listaTotalDiaBean);
				
				if(null != tmp.getNumeroDoc()){
					listaTotalDiaBean.setNumeroDoc(tmp.getNumeroDoc());
				}else{
					listaTotalDiaBean.setNumeroDoc("");
				}
				
				if(null != tmp.getTipo()){
					listaTotalDiaBean.setTipo(tmp.getTipo());
				}else{
					listaTotalDiaBean.setTipo("");
				}
				
				if(null != tmp.getMontoDoc()){
					listaTotalDiaBean.setMontoDoc(tmp.getMontoDoc());
				}else{
					listaTotalDiaBean.setMontoDoc("");
				}
				
				codigo_1=tmp.getCodigo().trim();
				
			}
			else
			{
				listaTotalDiaBean.setTexto(Constantes.STRING_TEXTO_DEVOLUCIONES);
				listaTotalDiaBean.setCodigo(tmp.getCodigo());
				listaTotalDiaBean.setTipoAgente(tmp.getTipoAgente());
				listaTotalDiaBean.setTotal(tmp.getTotal());
				listaTotalDiaBean.setCobrado(tmp.getCobrado());
				listaTotalDiaBean.setfPagado(tmp.getfPagado());
				listaTotalDiaBean.setFecha(fecha);
				listaTotalDia.add(listaTotalDiaBean);
				
				if(null != tmp.getNumeroDoc()){
					listaTotalDiaBean.setNumeroDoc(tmp.getNumeroDoc());
				}else{
					listaTotalDiaBean.setNumeroDoc("");
				}
				
				if(null != tmp.getTipo()){
					listaTotalDiaBean.setTipo(tmp.getTipo());
				}else{
					listaTotalDiaBean.setTipo("");
				}
				
				if(null != tmp.getMontoDoc()){
					listaTotalDiaBean.setMontoDoc(tmp.getMontoDoc());
				}else{
					listaTotalDiaBean.setMontoDoc("");
				}
				
				codigo_1=tmp.getCodigo().trim();
				
				total_cobrado += tmp.getCobrado_num();
				total_movimientos += tmp.getTotal_num();
			}
			
		}
		String sucursal = (String)session.getAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL);
		String fechaBusqueda = (String)session.getAttribute(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA_TOTAL);
		
		int movimiento_promedio = 0;
		if (numero_movimientos != 0) {
			movimiento_promedio = (int) (total_movimientos / numero_movimientos);
		}
		else
		{
			movimiento_promedio = total_movimientos;
		}
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put(Constantes.STRING_ACTION_LISTA_TOTAL_COBRADO, this.formatoValoresReportes(total_cobrado));
		parametros.put(Constantes.STRING_ACTION_LISTA_TOTAL_MOVIMIENTOS, this.formatoValoresReportes(total_movimientos) );
		parametros.put(Constantes.STRING_ACTION_LISTA_MOVIMIENTO_PROMEDIO, this.formatoValoresReportes(movimiento_promedio));
		parametros.put(Constantes.STRING_ACTION_LISTA_NUMERO_MOVIMIENTOS, String.valueOf(numero_movimientos));
		parametros.put(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA, fechaBusqueda);
		parametros.put(Constantes.STRING_ACTION_FECHA_ACTUAL, util.traeFechaHoyFormateadaString());
		parametros.put(Constantes.STRING_LOCAL, sucursal);
		byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,listaTotalDia);
		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
			} catch (IOException e) {
				log.error("ReportesHelper:creaListadoTotalDia error catch",e);
		}
	}
	
	public void creaListadoTranajosPendientes(HttpSession session,HttpServletResponse response, HttpServletRequest request){
		log.info("ReportesHelper:creaListadoTranajosPendientes inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("listaPedido.jasper");
		
		String cdg = request.getParameter("cdg").toString();
		String cliente = request.getParameter("cliente").toString();
		String divisa = request.getParameter("divisa").toString();
		String fecha_ini = request.getParameter("fecha_ini").toString();
		String fecha_fin = request.getParameter("fecha_fin").toString();
		String cerrado = request.getParameter("cerrado").toString();
		String anulado = request.getParameter("anulado").toString();
		String local = request.getParameter("local").toString();
		
		if (cdg.equals(Constantes.STRING_BLANCO)) {
			cdg = null;
		}
		if (divisa.equals(Constantes.STRING_CERO)) {
			divisa = null;
		}
		if (cliente.equals(Constantes.STRING_BLANCO)) {
			cliente = null;
		}
		if (fecha_ini.equals(Constantes.STRING_BLANCO)) {
			fecha_ini = null;
		}
		if (fecha_fin.equals(Constantes.STRING_BLANCO)) {
			fecha_fin = null;
		}
		if (anulado.equals(Constantes.STRING_CERO)) {
			anulado = null;
		}
		if (local.equals(Constantes.STRING_CERO)) {
			local = null;
		}
		
		String sucursal = (String)session.getAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL);
		String fechaBusqueda = (String)session.getAttribute(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA);
/*		ArrayList<TrabajosPendientesBean> listaPendientes =(ArrayList<TrabajosPendientesBean>)session.getAttribute(Constantes.STRING_ACTION_LISTA_PENDIENTES);
		ArrayList<TrabajosPendientesBean> reportePendientes = new ArrayList<TrabajosPendientesBean>();
		
		for(TrabajosPendientesBean pendientesTmp: listaPendientes){
			log.info("ReportesHelper:creaListadoTranajosPendientes entrando ciclo for");
			TrabajosPendientesBean tmpCab= new TrabajosPendientesBean();
			tmpCab.setSerie(pendientesTmp.getSerie());
			tmpCab.setFecha(pendientesTmp.getFecha());
			tmpCab.setNumeroCaja(pendientesTmp.getNumeroCaja());
			tmpCab.setCliente(pendientesTmp.getCliente());
			tmpCab.setNombre(pendientesTmp.getNombre());
			tmpCab.setApellidos(pendientesTmp.getApellidos());
			tmpCab.setDescuento1(pendientesTmp.getDescuento1());
			tmpCab.setfPago(pendientesTmp.getfPago());
			tmpCab.setAlbaran(pendientesTmp.getAlbaran());
			tmpCab.setTotal(Constantes.STRING_BLANCO);
			reportePendientes.add(tmpCab);
			for(ListadosTrabajosPendientesLineaBean linea : pendientesTmp.getLineas()){
				log.info("ReportesHelper:creaListadoTranajosPendientes entrando ciclo for");
					if(tmpCab.getSerie().equals(linea.getCodigo())){
						TrabajosPendientesBean tmpLin= new TrabajosPendientesBean();
						tmpLin.setArticulo(linea.getArticulo());
						tmpLin.setDescripcion(linea.getDescripcion());
						tmpLin.setCantidad(linea.getCantidad());
						tmpLin.setPrecio("$"+linea.getPrecio());
						tmpLin.setDescuento2(linea.getDescuento());
						tmpLin.setTotal(Constantes.STRING_BLANCO);
						reportePendientes.add(tmpLin);
					}
				}
			TrabajosPendientesBean tmpTotal= new TrabajosPendientesBean();
			if(Constantes.STRING_CERO.equals(pendientesTmp.getTotal())){
				tmpTotal.setTotal(Constantes.STRING_BLANCO);
			}else{
				tmpTotal.setTotal("$"+pendientesTmp.getTotal());
			}
			
			tmpTotal.setNumeroBoleta(pendientesTmp.getNumeroBoleta());
				tmpTotal.setTxtNumeroBoleta(Constantes.STRING_BLANCO);
				tmpTotal.setTxtTotal(Constantes.STRING_BLANCO);
				tmpTotal.setTxtNumeroBoleta(Constantes.STRING_TEXTO_BOLETA);
				tmpTotal.setTxtTotal(Constantes.STRING_TEXTO_TOTAL_PEDIDO);
				reportePendientes.add(tmpTotal);	
		}
		*/
		
		Map parametros = new HashMap();  
		parametros.put(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA, fechaBusqueda);
		parametros.put(Constantes.STRING_ACTION_FECHA_ACTUAL, util.traeFechaHoyFormateadaString());
		//parametros.put(Constantes.STRING_LOCAL, sucursal);
		//parametros.put(Constantes.STRING_CERRADO, cerrado);
		
		
		parametros.put("cdg", cdg);
		parametros.put("cliente", cliente);
		parametros.put("divisa", divisa);
		parametros.put("fecha_ini", fecha_ini);
		parametros.put("fecha_fin", fecha_fin);
		parametros.put("cerrado", cerrado);
		parametros.put("anulado", anulado);
		parametros.put("local", local);
		parametros.put("fpago", null);
		parametros.put("tipoped", null);
		parametros.put("agente", null);
		
		byte[] bytes = new CreaReportes().obtenerJasperNuevo(parametros, io);
		
		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaListadoTranajosPendientes error catch",e);
		}
		
	}
	public void creaListadoOptico(HttpSession session,HttpServletResponse response){
		log.info("ReportesHelper:creaListadoOptico inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("reporteOptico.jasper");
		InformeOpticoForm informeOptico = (InformeOpticoForm)session.getAttribute("InformeOptico");

		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put(Constantes.STRING_ACTION_FECHA_ACTUAL, util.traeFechaHoyFormateadaString());
		parametros.put(Constantes.STRING_CLIENTE, informeOptico.getCliente());
		parametros.put(Constantes.FORWARD_GRADUACION, informeOptico.getGraduacionCli());
		parametros.put(Constantes.STRING_ACTION_NOMBRE_CLIENTE, informeOptico.getNombreCli());
		parametros.put(Constantes.STRING_ACTION_FECHA_NAC_CLIENTE, informeOptico.getFechaNacCli());
		parametros.put(Constantes.STRING_ACTION_DOMICILIO_CLIENTE, informeOptico.getDomicilioCli());
		parametros.put(Constantes.STRING_ACTION_FONO_CLIENTE, informeOptico.getTelCli());

		byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,informeOptico.getListaGraduaciones());

		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaListadoOptico error catch",e);
		}
	}
	public String traeImagenCodBarra(String codigo, String rutaTTF) throws FontFormatException, IOException
	{
		Properties prop = util.leeConfiguracion();
		
		String ruta = prop.getProperty("codigo.barra.ruta");
		String archivo=codigo.replace("/", "-");
		String format=new String("jpg");
		
		int width, height;
		File saveFile=new File(ruta+archivo);
		
		if(!(saveFile.exists())){
			saveFile = new File(ruta+archivo);
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				
			}
		}
		
		
		BufferedImage bi,biFiltered;;
		width=400;
		height=90;

		String str=new String(codigo);
		BufferedImage bufimg =new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphicsobj = bufimg.createGraphics();

		FileInputStream fin;
		
		try {
			fin = new FileInputStream(rutaTTF+"images/fre3of9x.TTF");
			Font font = Font.createFont(Font.TRUETYPE_FONT,fin);
			Font font1 = font.deriveFont(60f);
			
			graphicsobj.setFont(font1);
			graphicsobj.setFont(font.getFont("3 of 9 Barcode"));
			graphicsobj.setColor(Color.WHITE);

			//Generate barcode image for the code *11111*
			graphicsobj.fillRect(0,0,400,250);
			graphicsobj.setColor(Color.BLACK);
			codigo = "*"+codigo+"*";
			((Graphics2D)graphicsobj).drawString(codigo,10,80);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ImageIO.write(bufimg,format,saveFile);
		
		return ruta+archivo;
	}
	
	public void creaFichaTaller(HttpSession session,HttpServletResponse response, ArrayList<FichaTecnicaBean> lista, String ruta) throws Exception{
		log.info("ReportesHelper:creaFichaTaller inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("fichaTaller.jasper");
		

		Map<String, String> parametros = new HashMap<String, String>();
	    //parametros.put("fechaActual", util.traeFechaHoyFormateadaString());
		ArrayList<FichaTecnicaBean> listaFicha = lista;
		ArrayList<TiendaBean> tiendas = new ArrayList<TiendaBean>();
		String letra = "";
		tiendas = PosUtilesFacade.traeDatosTienda(session.getAttribute(Constantes.STRING_SUCURSAL).toString());
	
		if(tiendas.size() > 0 || tiendas != null){
			if(tiendas.get(0).getRegion().equals("13")){
				letra = "blank.jpg";
			}else{
				letra = "R.png";
			}
		}
		URI uri = new URI(this.getClass().getResource(letra).getPath());
		
		try {
			for (FichaTecnicaBean fichaTecnicaBean : listaFicha) {
				fichaTecnicaBean.setImagen(this.traeImagenCodBarra(fichaTecnicaBean.getNumero_encargo(), ruta));	
				fichaTecnicaBean.setImagen_barra(this.traeImagenCodBarra(fichaTecnicaBean.getCod_armazon(), ruta));	
				fichaTecnicaBean.setRegion(uri.getPath());
			}
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,listaFicha);
		//byte[] bytes = new CreaReportes().obtenerJasperSinPar(parametros, io,new JREmptyDataSource());

		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);   
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaFichaTaller error catch",e);
		}
		
		for (FichaTecnicaBean fichaTecnicaBean : listaFicha) {
			File saveFile=new File(fichaTecnicaBean.getImagen(),fichaTecnicaBean.getImagen_barra());
			
			if((saveFile.exists())){
				saveFile.delete();
			} 
			
		}
		
		
		
		
	}
	public void creaFichaCliente(HttpSession session,HttpServletResponse response, String strcliente){
		log.info("ReportesHelper:creaFichaCliente inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("fichaCliente.jasper");
		
		ArrayList<ProductosBean> listaProduc = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
		ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
		if(null != listaProduc){			
			for(ProductosBean pro : listaProduc){
				if(null == pro.getTipo()){
					pro.setTipo("");
				}
				listaProductos.add(pro);
			}			
		}
		
		
		VentaPedidoForm formulario = (VentaPedidoForm) session.getAttribute(Constantes.STRING_SESSION_FORMULARIO_VTA_PEDIDO);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		Utils util = new Utils();
		
		//ClienteBean cliente = util.traeCliente(null, formulario.getCliente());
		ClienteBean cliente = util.traeClienteSeleccionado(null, strcliente);
		
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put(Constantes.STRING_ACTION_FECHA_ACTUAL, util.traeFechaHoyFormateadaString());
		parametros.put(Constantes.STRING_ACTION_FECHA_INGRESO, formulario.getFecha());
		parametros.put(Constantes.STRING_FECHA_ENTREGA, formulario.getFecha_entrega());
		parametros.put(Constantes.STRING_CAJA, String.valueOf(formulario.getCaja()));		
		parametros.put(Constantes.STRING_CLIENTE, cliente.getNombre()+"          "+cliente.getApellido());
		
		parametros.put(Constantes.FORWARD_MEDICO, formulario.getGraduacion().getDoctor());
		parametros.put(Constantes.STRING_REPORTER_VENDEDOR, formulario.getAgente());
		
		if(null != cliente.getFono_casa()){
			parametros.put(Constantes.STRING_ACTION_TELLEFONO_CLI, cliente.getFono_casa());
		}else{
			parametros.put(Constantes.STRING_ACTION_TELLEFONO_CLI, "");
		}
		
		
		parametros.put(Constantes.STRING_ACTION_TELLEFONO_MEDICO, "");
		
		if(null != cliente.getFono_movil()){
			parametros.put(Constantes.STRING_ACTION_TELLEFONO_MOVIL, cliente.getFono_movil());
		}else{
			parametros.put(Constantes.STRING_ACTION_TELLEFONO_MOVIL, "");
		}
		
		
		AlbaranBean alb = util.traeCodigoAlbaran(formulario.getCodigo_suc() + "/" + formulario.getCodigo());
		String codigo_albaran="";
		if(null != alb){
			if(null != alb.getCodigo_albaran() && !("".equals(alb.getCodigo_albaran()))){
				codigo_albaran = alb.getCodigo_albaran();
			}
		}
		
		parametros.put(Constantes.STRING_REPORTER_RUT, cliente.getNif()+"-"+cliente.getDvnif());
		parametros.put(Constantes.STRING_ACTION_SERIE, formulario.getCodigo_suc() + "/" + formulario.getCodigo());
		parametros.put(Constantes.STRING_REPORTER_FECHA, formulario.getFecha());
		parametros.put(Constantes.STRING_ACTION_N_CAJA, String.valueOf(formulario.getCaja()));				
		parametros.put(Constantes.STRING_ACTION_N_CLI, cliente.getCodigo());
		parametros.put(Constantes.STRING_ACTION_NOMBRE_CLI, cliente.getNombre());
		parametros.put(Constantes.STRING_ACTION_APELLI_CLI, cliente.getApellido());
		parametros.put(Constantes.STRING_ACTION_DTO, formulario.getDtcoPorcentaje()+"");
		parametros.put(Constantes.STRING_ACTION_FPAGO, formulario.getForma_pago());		
		parametros.put(Constantes.STRING_ACTION_ALBARAN, codigo_albaran);		
		parametros.put(Constantes.STRING_ACTION_TOTAL_PEDIDOS, "1");
		parametros.put(Constantes.STRING_ACTION_T_TOTAL, "");
		parametros.put(Constantes.STRING_ACTION_SALDO, "");
		parametros.put(Constantes.STRING_NOTA, formulario.getNota());
		
		byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,listaProductos);
		//byte[] bytes = new CreaReportes().obtenerJasperSinPar(parametros, io,new JREmptyDataSource());

		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaFichaCliente error catch",e);
		}
	}
	
	public void creaGuia(HttpSession session,HttpServletResponse response){
		log.info("ReportesHelper:creaGuia inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("guia.jasper");
		
		ArrayList<ProductosBean> listaProduc = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
		SeleccionPagoForm formulario = (SeleccionPagoForm) session.getAttribute(Constantes.STRING_CABECERA_GUIA);
		Utils util = new Utils();
		ClienteBean cliente = new ClienteBean();
		cliente = util.traeCliente(formulario.getNif(), null);
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put(Constantes.STRING_ACTION_DIA_FECHA, util.traeFechaHoyFormateadaString().substring(0, 2));
		parametros.put(Constantes.STRING_ACTION_MES_ANO, util.traeFechaHoyFormateadaString());
		
		parametros.put(Constantes.STRING_ACTION_SENORES, formulario.getNombre_cliente());
		parametros.put(Constantes.STRING_ACTION_DIRECCION, formulario.getDireccion());
		parametros.put(Constantes.STRING_ACTION_GIRO, formulario.getGiro_descripcion());
		parametros.put(Constantes.STRING_REPORTER_RUT, formulario.getNif());
		parametros.put(Constantes.STRING_ACTION_COMUNA, formulario.getProvincia_descripcion());
		
		parametros.put(Constantes.STRING_ACTION_SUBTOTAL,String.valueOf(formulario.getV_total_parcial()));
		parametros.put(Constantes.STRING_DESCUENTO,String.valueOf(formulario.getDescuento()));
		parametros.put(Constantes.STRING_TOTAL, String.valueOf(formulario.getV_total()));
		
	

		byte[] bytes = new CreaReportes().obtenerJasper(parametros, io,listaProduc);
		//byte[] bytes = new CreaReportes().obtenerJasperSinPar(parametros, io,new JREmptyDataSource());

		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaGuia error catch",e);
		}
	}

	public void creaPresupuesto(HttpSession session,
			HttpServletResponse response) {
		
		log.info("ReportesHelper:creaPresupuesto inicio");
		InputStream io = ReportesHelper.class.getResourceAsStream("presupuesto.jasper");
		
		PresupuestoForm formulario = (PresupuestoForm) session.getAttribute(Constantes.STRING_PRESUPUESTO_FORM);
		ArrayList<ProductosBean> listaProduc = formulario.getListaProductos();
		Utils util = new Utils();
		ClienteBean cliente = new ClienteBean();
		cliente = util.traeCliente(null, formulario.getCliente());
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put(Constantes.STRING_PRESUPUESTO_REPORTE_FECHA, formulario.getFecha());
		parametros.put(Constantes.STRING_PRESUPUESTO_CLIENTE_NOMBRE, cliente.getApellido() + " " + cliente.getNombre());
		parametros.put(Constantes.STRING_PRESUPUESTO_CLIENTE, cliente.getCodigo());
		parametros.put(Constantes.STRING_PRESUPUESTO_NIF, cliente.getNif());
		parametros.put(Constantes.STRING_PRESUPUESTO_DIVISA, formulario.getDivisa());
		parametros.put(Constantes.STRING_PRESUPUESTO_LOCAL, (String)session.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL));
		parametros.put(Constantes.STRING_PRESUPUESTO_TELEFONO, (String)session.getAttribute(Constantes.STRING_SUCURSAL_TELEFONO));
		parametros.put(Constantes.STRING_PRESUPUESTO_PRESUPUESTO, formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo()	);
		parametros.put(Constantes.STRING_PRESUPUESTO_AGENTE, (String)session.getAttribute(Constantes.STRING_DESC_USUARIO));
		parametros.put(Constantes.STRING_PRESUPUESTO_TOTAL, this.formatoValoresReportes((int)Math.floor(formulario.getTotal())));
		parametros.put(Constantes.STRING_PRESUPUESTO_NOTA, formulario.getNota());
		
	
		
		byte[] bytes = new CreaReportes().obtenerJasper(parametros, io, listaProduc);
		
		response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
		response.setContentLength(bytes.length);
		response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_PRESUPUESTO_PDF);
		ServletOutputStream servletOutputStream;
		try {
			servletOutputStream = response.getOutputStream();
			servletOutputStream.write(bytes, 0, bytes.length);
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (IOException e) {
			log.error("ReportesHelper:creaPresupuesto error catch",e);
		}
	}
}

