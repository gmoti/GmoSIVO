package cl.gmo.pos.venta.web.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.beans.VentaDirectaBean;
import cl.gmo.pos.venta.web.facade.PosProductosFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaFacade;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public class VentaDirectaHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public boolean ingresaPago(ArrayList<PagoBean> listaPago, Session session, VentaDirectaForm formulario)
	{
		log.info("VentaDirectaHelper:ingresaPago inicio");
    	boolean estado = false;
    	String devolucion = Constantes.STRING_N;
    	String tipo_doc = session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString();
    	
    	if (Constantes.STRING_D	.equals(session.getAttribute(Constantes.STRING_TIPO_ALBARAN).toString())) {
			devolucion = Constantes.STRING_Y;
		}
    	
    	
    	try {
    		for (PagoBean pago : listaPago) {
    			log.info("VentaDirectaHelper:ingresaPago entrando ciclo for");
    			PosVentaFacade.insertaPago(formulario.getEncabezado_ticket() + "/" + formulario.getNumero_ticket(),
													pago.getForma_pago(),
													pago.getCantidad(),
													pago.getFecha(),
													formulario.getDivisa(), 
													formulario.getCambio(), 
													formulario.getNumero_caja(), 
													pago.getCantidad(),
													devolucion,
													Constantes.STRING_N,
													session.getAttribute(Constantes.STRING_USUARIO).toString(),
													null,
													pago.getDescuento(),
													tipo_doc);
			}
    		
			
		} catch (Exception e) {
			log.error("VentaDirectaHelper:ingresaPago error catch",e);
		}
    	return estado;
	}
	
    public VentaDirectaForm traeVenta(VentaDirectaForm ventaForm, Session session)
    {
    	log.info("VentaDirectaHelper:traeVenta inicio");

		try {
			

	    String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();	
	    String agente = session.getAttribute(Constantes.STRING_USUARIO).toString();
	    
		ArrayList<TiendaBean> dt = PosUtilesFacade.traeDatosTienda(local);

	    
    	ventaForm.setAgente(ventaForm.getNombreCajero());
    	ventaForm.setCajero(ventaForm.getNombreCajero());
    	ventaForm.setListaAgentes(PosUtilesFacade.traeAgentes(local));
    	ventaForm.setEncabezado_ticket(PosVentaFacade.traeEncabezadoTicket(local));
    	ventaForm.setFecha(this.formatoFecha(this.traeFecha()));
    	ventaForm.setHora(this.formatoHora(this.traeFecha()));
    	ventaForm.setNumero_caja(ventaForm.getNumero_caja());
    	ventaForm.setNumero_ticket(formato_Numero_Ticket(PosVentaFacade.traeNumeroTicket(local))); 
    	
    	ventaForm.setListaAlbaranes(PosUtilesFacade.traeTipoAlbaranes());
    	
    	VentaDirectaBean ventaBean = new VentaDirectaBean();
    	ventaBean = PosVentaFacade.traeDatosGenericosVentaDirecta(local);
    	
    	ventaForm.setCambio(ventaBean.getCambio());
    	ventaForm.setCliente(ventaBean.getCliente());
    	ventaForm.setNombreCliente(ventaBean.getNombre_cliente());
    	ventaForm.setDivisa(ventaBean.getDivisa());
    	ventaForm.setTipoAlbaran(Constantes.STRING_TIPO_ALB_NO);
    	ventaForm.setPorcentaje_descuento_max(ventaBean.getPorcentaje_descuento_max());
    	ventaForm.setTipoimp(dt.get(0).getTipo_impresion());
    	
		} catch (Exception e) {
			log.error("VentaDirectaHelper:traeVenta error catch",e);
		}
        return ventaForm;
    }
    
    public VentaDirectaForm traeNumerosCaja(VentaDirectaForm ventaForm, String local)
    {
    	log.info("VentaDirectaHelper:traeNumerosCaja inicio");
    	VentaDirectaBean ventaBean = new VentaDirectaBean();
    	try {
			ventaBean = PosVentaFacade.traeNumerosCaja(local);
			ventaForm.setListaCajas(ventaBean.getListaCajas());
		} catch (Exception e) {
			log.error("VentaDirectaHelper:traeNumerosCaja error catch",e);
		}
		return ventaForm;
    }
    
    public VentaDirectaForm traeListaAgentes(VentaDirectaForm ventaForm, String local)
    {
    	log.info("VentaDirectaHelper:traeListaAgentes inicio");

    	ArrayList<AgenteBean> listaAgentes = new ArrayList<AgenteBean>();
    	try {
    		listaAgentes = PosUtilesFacade.traeAgentes(local);
			ventaForm.setListaAgentes(listaAgentes);
		} catch (Exception e) {
			log.error("VentaDirectaHelper:traeListaAgentes error catch",e);
		}
		return ventaForm;
    }
    
    public int sumaTotalValorProductos(VentaDirectaForm ventaForm){
      log.info("VentaDirectaHelper:sumaTotalValorProductos inicio");
	  int precioFinalProductos = Constantes.INT_CERO;
	  if (null != ventaForm.getListaProductos()) {
		  for(ProductosBean precioProducto:ventaForm.getListaProductos()){
			  log.info("VentaDirectaHelper:sumaTotalValorProductos entrando ciclo for");
			  precioFinalProductos = (precioProducto.getPrecio()* precioProducto.getCantidad())+ precioFinalProductos;
		  }
		  return precioFinalProductos;
	  }else
	  {
		  return 0;
	  }
	  
    }
  
  	public int restaTotalValorProductos(VentaDirectaForm ventaForm){
  		log.info("VentaDirectaHelper:restaTotalValorProductos inicio");
	  Utils util = new Utils();
	  int precioFinalProductos = Constantes.INT_CERO;
	  ProductosBean productoSeleccionado = util.seleccionarProductoLista(ventaForm.getAddProducto(), ventaForm.getListaProductos());
	  precioFinalProductos = (ventaForm.getSumaTotal()-(productoSeleccionado.getPrecio()*productoSeleccionado.getCantidad()));
	  return precioFinalProductos;
    }

  	public ArrayList<ProductosBean> traeProductosGratuitos(ArrayList<ProductosBean> listaProductos, String nombre_local, String codigo_local) 
  	{
  		log.info("VentaDirectaHelper:traeProductosGratuitos inicio");
  		return this.traeLosProductosGratuitos(listaProductos, nombre_local, codigo_local, Constantes.STRING_DIRECTA);
  	}

	
  	public VentaDirectaForm actualizaProductos(VentaDirectaForm formulario,	String local, String tipo_busqueda, Session session) {
  		
  		    log.info("VentaDirectaHelper:actualizaProductos inicio");
  		
  			ProductosBean prod = new ProductosBean();
  			ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
  			ArrayList<ProductosBean> listaProductosMultiOfertas = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS);
  			
  			listaProductos = formulario.getListaProductos();
  			prod  = PosProductosFacade.traeProducto(null, formulario.getCantidad(), local, tipo_busqueda, formulario.getAddProducto());
  			
  			formulario.setCodigo_mult(Constantes.STRING_BLANCO);
	  		formulario.setIndex_multi(Constantes.INT_CERO);			
  			
  			if(null != prod.getCodigo()){
  				if (this.esLenteContactoGraduable(prod)) {
  					formulario.setEstado(Constantes.STRING_PRODUCTOS_GRADUABLE);
				}
  				else
  				{
  					if (null == listaProductos)
  		  			{
  		  				listaProductos = new ArrayList<ProductosBean>();
  		  				listaProductos.add(prod);	  				
  		  			}
  		  			else
  		  			{
  		  				if(listaProductos.size()>0){
	  		  				ProductosBean pr = listaProductos.get((listaProductos.size()-1));
	  		  				prod.setIndice((pr.getIndice()+1));	  				
	  		  				listaProductos.add(prod); 
  		  				}else{
  		  					listaProductos.add(prod);
  		  				}
  		  				 		  				
  		  			}
  				
  					System.out.println("LOCAL VD ====>"+local);
  					if(local.substring(0, 1).equals("T") || local.substring(0, 1).equals("V")) {			

  						//2017-11-23 LMARIN - PROMOPAR
  						this.aplicaDescuentoProductoEsp(prod);
  					}
  		  			formulario.setListaProductos(listaProductos);
  		  			
  		  			/**
  		  			 * Si el producto es multioferta se caraga en la listaProductosMultiOfertas para
  		  			 * luego actualizar la listaMultioferta de la session
  		  			 */
  		  			if("MUL".equals(prod.getFamilia())){
  		  			prod.setIndexMulti(this.obtenerIndexMultiOferta(listaProductosMultiOfertas));
  		  			
	  		  			if (null == listaProductosMultiOfertas){	
	  		  				listaProductosMultiOfertas = new ArrayList<ProductosBean>();
	  		  				listaProductosMultiOfertas.add(prod);
	  		  			}else{
	  		  				listaProductosMultiOfertas.add(prod);
	  		  			}
	  		  		session.setAttribute(Constantes.STRING_LISTA_MULTIOFERTAS, listaProductosMultiOfertas);
  		  			formulario.setEstado(Constantes.STRING_CARGA_MULTIOFERTAS);
  		  			formulario.setCodigo_mult(prod.getCodigo());
  		  			formulario.setIndex_multi(prod.getIndexMulti());
  		  			}
  		  			
  		  		//comprueba si tiene precio especial
  	  				Utils util = new Utils();
  	  				if (util.verificaPrecioEspecial(prod, formulario.getFecha())) {
  	  					session.setAttribute(Constantes.STRING_PRODUCTO, prod.getIndice());
  						formulario.setEstado(Constantes.STRING_PRODUCTO_PRECIO_ESPECIAL);
  					}
  		  			
  				}
  				
	  			
  			}
  			else
  			{
  				formulario.setEstado(Constantes.STRING_PRODUCTOS_NO_ENCONTRADO);
  			}
  			
  	return formulario;		
  			
	}
  	
  	public ArrayList<ProductosBean> actualizaProductosMultioferta(int indexRel_Multioferta, String addProducto,
  			int cantidad, ArrayList<ProductosBean> listaProductos,
			String local, String tipo_busqueda, String codigoMultioferta, ArrayList<TipoFamiliaBean> listaTipoFamilias, ArrayList<ProductosBean> listaProductosAux) {
  			
  			log.info("VentaDirectaHelper:actualizaProductosMultioferta inicio");
  			ProductosBean prod = new ProductosBean();
  			prod  = PosProductosFacade.traeProducto(addProducto, cantidad, local, tipo_busqueda, null); 			
  			  			
	  			if(null != prod){
	  				prod.setCodigoMultioferta(codigoMultioferta);
	  				prod.setIndexRelMulti(indexRel_Multioferta);
	  				prod.setIndexProductoMulti(this.obtenerIndexProductoMultiOferta(listaProductos, codigoMultioferta, indexRel_Multioferta));
		  			if (null == listaProductos)
		  			{
		  				listaProductos = new ArrayList<ProductosBean>();
		  				listaProductos.add(prod);
		  			}
		  			else
		  			{
		  				listaProductos.add(prod);
		  			}
	  			}  			
  			
		return listaProductos;
	}
  	
  	
  	public ProductosBean productoMultioferta(String addProducto,
  			int cantidad, ArrayList<ProductosBean> listaProductos,
			String local, String tipo_busqueda, String codigoMultioferta) {
  			log.info("VentaDirectaHelper:productoMultioferta inicio");
  			ProductosBean prod = new ProductosBean();
  			prod  = PosProductosFacade.traeProducto(addProducto, cantidad, local, tipo_busqueda, null);  			
  			
		return prod;
	}
  	
	public ArrayList<ProductosBean> agregaProductosGratuitos(
			ArrayList<ProductosBean> listaProductosAdicionales,
			ArrayList<ProductosBean> listaProductos) {
		log.info("VentaDirectaHelper:agregaProductosGratuitos inicio");
		for (Iterator<ProductosBean> iterator = listaProductosAdicionales.iterator(); iterator.hasNext();) {
			log.info("VentaDirectaHelper:agregaProductosGratuitos entrando ciclo for");
			ProductosBean productosBean = (ProductosBean) iterator.next();
			
			listaProductos.add(productosBean);
		}
		return listaProductos;
	}

	public void ingresaVenta(VentaDirectaForm formulario, String local, String tipo_documento) 
	{
		log.info("VentaDirectaHelper:ingresaVenta inicio");
		VentaDirectaBean ventaBean = new VentaDirectaBean();
		
		ventaBean.setEncabezado_ticket(formulario.getEncabezado_ticket());
		if(formulario.getNumero_ticket().equals(Constantes.STRING_BLANCO))
		{
			ventaBean.setNumero_ticket(0);
		}
		else
		{
			ventaBean.setNumero_ticket(Integer.parseInt(formulario.getNumero_ticket()));
		}
		
		System.out.println("Numero Cliente before ==>" +formulario.getCodigo_cliente());
		ventaBean.setCliente(Integer.parseInt(formulario.getCodigo_cliente()));
		System.out.println("Numero Cliente after  ==>" +ventaBean.getCliente());
		
		ventaBean.setHora(formulario.getHora());
		ventaBean.setAgente(formulario.getAgente());
		ventaBean.setDivisa(formulario.getDivisa());
		ventaBean.setDescuento(formulario.getDescuentoTotal());
		ventaBean.setFecha(formulario.getFecha());
		ventaBean.setCambio(formulario.getCambio());
		ventaBean.setForma_pago(null);
		ventaBean.setTipo_albaran(formulario.getTipoAlbaran());
		ventaBean.setValor_total(formulario.getSumaTotalFinal());
		ventaBean.setNumero_caja(formulario.getNumero_caja());
		
		try {
			PosVentaFacade.insertaVenta(ventaBean, local, tipo_documento);
			formulario.setNumero_ticket(formato_Numero_Ticket(ventaBean.getNumero_ticket()));
			formulario.setEstado(Constantes.STRING_GUARDADO);
		} catch (Exception e) {
			log.error("VentaDirectaHelper:ingresaVenta error catch",e);
		}
	}
	
	public boolean ingresaDetalle(ArrayList<ProductosBean> listaProductos, String codigo_albaran, String local, VentaDirectaForm formulario)
	{
		log.info("VentaDirectaHelper:ingresaDetalle inicio");
		boolean hay_multiofertas = false;
		String mensaje = Constantes.STRING_BLANCO;
		int x = 1;
		try {
			for (ProductosBean productosBean : listaProductos) {
				log.info("VentaDirectaHelper:ingresaDetalleMultiofertas entrando ciclo for");
				if (Constantes.STRING_MUL.equals(productosBean.getFamilia())) 
				{
					hay_multiofertas = true;
					//productosBean.setCdg_correlativo_multioferta(PosUtilesFacade.traeNumeroMultioferta() + 1);
					mensaje = PosVentaFacade.insertaMultiofertaCB(productosBean, formulario.getEncabezado_ticket() + "/"+ formulario.getNumero_ticket(), x, formulario.getFecha(), Integer.parseInt(formulario.getNumero_ticket()), local);
					log.warn("insertaMultiofertaCabecera MON/" + productosBean.getCdg_correlativo_multioferta() + " " + formulario.getEncabezado_ticket() + "/"+ formulario.getNumero_ticket());
					if (mensaje.equals("ERROR")) {
						formulario.setEstado("ERROR_GUARDADO");
					}
					PosVentaFacade.insertaDetalle(productosBean, x, codigo_albaran, local);
				}
				else
				{
					PosVentaFacade.insertaDetalle(productosBean, x, codigo_albaran, local);
				}
				x++;
			}
			
		} catch (Exception e) {
			log.error("VentaDirectaHelper:ingresaDetalle error catch",e);
		}
		return hay_multiofertas;
	}
	
	public void ingresaDetalleMultiofertas(ArrayList<ProductosBean> listaProductos, String local, VentaDirectaForm formulario, ArrayList<ProductosBean> listaProductosMultiofertas)
	{
		log.info("VentaDirectaHelper:ingresaDetalleMultiofertas inicio");
		int x = 1;
		String mensaje;
		try {
			for (ProductosBean productosBean : listaProductos) {
				log.info("VentaDirectaHelper:ingresaDetalleMultiofertas entrando ciclo for");
				if (productosBean.getCdg_correlativo_multioferta() != Constantes.INT_CERO)
				{
					x = 1;
					for (ProductosBean productosMulti : listaProductosMultiofertas) {
						log.info("VentaDirectaHelper:ingresaDetalleMultiofertas entrando ciclo for");
						if (productosMulti.getCodigoMultioferta().equals(productosBean.getCodigo())
								&& productosBean.getIndexMulti() == productosMulti.getIndexRelMulti())
						{
							mensaje = PosVentaFacade.insertaMultiofertaDetalle(productosBean.getCdg_correlativo_multioferta(), productosMulti, x, formulario.getFecha(), local, formulario.getEncabezado_ticket() + "/"+ formulario.getNumero_ticket() );
							if (mensaje.equals("ERROR")) {
								formulario.setEstado("ERROR_GUARDADO");
							}
							log.warn("insertaMultiofertaDetalle MON/" + productosBean.getCdg_correlativo_multioferta() + " " + formulario.getEncabezado_ticket() + "/"+ formulario.getNumero_ticket());
							x++;
						}
					}
				}
				
			}
		} catch (Exception e) {
			log.error("VentaDirectaHelper:ingresaDetalleMultiofertas error catch",e);
		}
	}

	public String ingresaDocumento(String ticket, int documento, String tipo_documento,
			int total, String fecha,String local) {
		log.info("VentaDirectaHelper:ingresaDocumento inicio");
		String res ="";
		try {
				res = PosVentaFacade.insertaDocumento(ticket, documento, tipo_documento, total, fecha,local);
			
		} catch (Exception e) {
			log.error("VentaDirectaHelper:ingresaDocumento error catch",e);
		}
		return res;
	}

	public void modificaCantidad(VentaDirectaForm formulario, int index,
			int cantidad) {
		log.info("VentaDirectaHelper:modificaCantidad inicio");
		ProductosBean producto = formulario.getListaProductos().get(index);
		producto.setCantidad(cantidad);
		producto.setImporte(producto.getPrecio() * cantidad);
		
		formulario.getListaProductos().set(index, producto);
		
		
		
	}

	public void cuentaProductos(VentaDirectaForm formulario) {
	
		if (null != formulario.getListaProductos()) {
			formulario.setCantidad_productos(formulario.getListaProductos().size());
		}
		else
		{
			formulario.setCantidad_productos(0);
		}
	}

	/*
	 * LMARIN 20141219
	 * Metodo que invoca la impresion de los datos que se utilizaran para generar la boleta  
	 */
	public String genera_datos_belec(String tipodoc,SeleccionPagoForm formulario,String foliocl,HttpSession session){
				
		Utils util = new Utils();
		String res = null;
		String folio = foliocl;
		
		try {		
			
			ArrayList<ProductosBean> listProductos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);				
			res = util.generaBoletaELEC(tipodoc,folio,formulario,listProductos);
			System.out.println("Boleta ====>===>==>=> "+res);
		} catch (IOException e) {			
			System.out.println("IOEXCEPCION  20141219 => "+ e.getMessage());
		} catch (Exception e) {
			System.out.println("EXCEPCION  20141219 => "+ e.getMessage());
		}
		return res ;
	} 
	//LMARIN  20150610 ticket de cambio
	public String ticket_cambio(SeleccionPagoForm sform){		
		String res = "";
		Utils util = new Utils();
		res = util.ticket_cambio(sform);
		return res;
	}
	
	
}
