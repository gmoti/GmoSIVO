package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.reportes.CreaReportes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.facade.PosClientesFacade;
import cl.gmo.pos.venta.web.facade.PosSeleccionPagoFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaPedidoFacade;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public class SeleccionPagoHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public SeleccionPagoForm cargaInicial(SeleccionPagoForm formulario, Session session, String fecha_formulario)
	{
		log.info("SeleccionPagoHelper:cargaInicial inicio");
		try {
			session.setAttribute(Constantes.STRING_MSJE,Constantes.STRING_VENTA);
			int total = NumberFormat.getNumberInstance().parse( session.getAttribute(Constantes.STRING_TOTAL).toString() ).intValue();
			formulario.setSuma_total_albaranes(total);
			String codigo_cliente = session.getAttribute(Constantes.STRING_CLIENTE).toString();
			String ticket = session.getAttribute(Constantes.STRING_TICKET).toString();
			String fecha_negocio = session.getAttribute(Constantes.STRING_FECHA).toString();
			String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();

			
			/*if (null != session.getAttribute(Constantes.STRING_ESTADO_FORM)) {
				formulario.setEstado_formulario_origen(session.getAttribute(Constantes.STRING_ESTADO_FORM).toString());
			}
			else
			{*/
				formulario.setEstado_formulario_origen(Constantes.STRING_BLANCO);
			//}
			ClienteBean cliente = new ClienteBean();
			formulario.setOrigen(session.getAttribute(Constantes.STRING_ORIGEN).toString());
			formulario.setListaFormasPago(PosUtilesFacade.traeFormasPagoTienda(local));
			cliente = PosClientesFacade.traeClienteFact(null, codigo_cliente);
			formulario.setV_total(total);
			formulario.setFecha(fecha_formulario);
			//formulario.setListaGiros(PosUtilesFacade.traeGiros());
			formulario.setListaGiros(new ArrayList<GiroBean>());
			GiroBean giroCliente = PosUtilesFacade.traeDescripGiroCliente(cliente.getGiro());
			if(null != giroCliente){
				formulario.setGiro_descripcion(giroCliente.getDescripcion());
			}else{
				formulario.setGiro_descripcion(Constantes.STRING_BLANCO);
			}
			
			formulario.setListaProvincias(new ArrayList<ProvinciaBean>());
			ProvinciaBean prov = PosUtilesFacade.traeDescripProvinciasCliente(String.valueOf(cliente.getProvincia()));
			
			if(null != prov){
				formulario.setProvincia_descripcion(prov.getDescripcion());
			}else{
				formulario.setProvincia_descripcion(Constantes.STRING_BLANCO);
			}			
			
			formulario.setSerie(ticket);
			
			formulario.setCodigo(cliente.getCodigo());
			formulario.setDireccion(cliente.getDireccion());
			formulario.setGiro(cliente.getGiro());
			formulario.setNif(cliente.getNif() + "-" + cliente.getDvnif());
			formulario.setPoblacion(cliente.getPoblacion());
			formulario.setProvincia(cliente.getProvincia());
			formulario.setNombre_cliente(cliente.getNombre() + " " + cliente.getApellido());
			formulario.setRazon(formulario.getNombre_cliente());
			formulario.setGiro_descripcion(formulario.getGiro_descripcion());
			formulario.setProvincia_descripcion(cliente.getProvincia_desc());
			
			ArrayList<PagoBean> listaPagos = new ArrayList<PagoBean>();
			listaPagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
			
			//calculando descuentos y cobros finales
			int total_pagado = 0;
			if (null == listaPagos || listaPagos.size() == 0) {
				
				formulario.setTiene_pagos(0);
				formulario.setV_total_parcial(total);
				formulario.setDiferencia(total);
				formulario.setV_a_pagar(total);
				formulario.setListaPagos(listaPagos);
				
			}else{
				
				
				for(PagoBean pagoBean : listaPagos) {
					formulario.setDescuento(pagoBean.getDescuento());
					total_pagado += pagoBean.getCantidad();
				}
				formulario.setTiene_pagos(1);
				//calculando descuentos y cobros finales
				
				int total_parcial = 0;
				if (formulario.getOrigen().equals(Constantes.STRING_DIRECTA)) {
					
					ArrayList<ProductosBean> lista_productos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
					
					for (ProductosBean producto : lista_productos) {
						total_parcial = total_parcial + producto.getImporte();
					}
				}
				else
				{
					total_parcial = (int) (total - (total * formulario.getDescuento() / 100));
				}
				
				formulario.setV_total_parcial(total_parcial);
				
				
				formulario.setDiferencia(total_parcial - total_pagado);
				formulario.setV_a_pagar(total_parcial - total_pagado);
				formulario.setListaPagos(listaPagos);
			}
			
			if (formulario.getDiferencia() == 0) {
				session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_PAGADO_TOTAL);
			}else{
				//se elimina ya que choca con la validacion de la forma de pago.
				//session.setAttribute(Constantes.STRING_ERROR, "");
			}
			
			//calcula el valor inicial del anticipo minimo
			if(formulario.getOrigen().equals(Constantes.STRING_PEDIDO))
			{
				int total_ventacr= 0,por_ventacr = 0,totaln = 0;

				int total_prod = 0, cont_elib=0, importevg=0;
				
				formulario.setFpago_origen(session.getAttribute(Constantes.STRING_FORMA_PAGO_ORIGEN).toString());
				
				int porcentaje = NumberFormat.getNumberInstance().parse( session.getAttribute(Constantes.STRING_PORCENTAJE_ANTICIPO).toString()).intValue();
				
				ArrayList<ProductosBean> lista_productos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);

				//CALCULO % DINAMICAMENTE EN FUNCION DE LOS ARTICULOS NO LIBERABLES VENTA CRUZADA 20171011
				
				/*for(ProductosBean p : lista_productos){
					System.out.println("p.getTipoFamilia() ==> "+p.getTipoFamilia());
					if(p.getTipoFamilia().equals("G") || p.getTipoFamilia().equals("O") || p.getTipoFamilia().equals("Q")) {
						total_ventacr += (p.getImporte() * (1 - Integer.parseInt((p.getDto()))/100);
					}
					if(p.getTipoFamilia().equals("M") || p.getTipoFamilia().equals("L")) {
						cont_elib++;
					}
					totaln += (p.getImporte() * (1 - Double.parseDouble(p.getDto()))/100);
				}
				
				if((int)total_ventacr > 0 && cont_elib > 0) {
					total_prod = (int)((total_ventacr * 100) / totaln) + porcentaje;
				}else{
					total_prod = porcentaje;
				}*/
				
				//System.out.println("total_ventacr =>"+total_ventacr+"<==> totaln =>"+totaln+" <==> cont_elib ==>"+cont_elib+" importevg =>"+importevg);
			
				
				//System.out.println("formulario.getTipo_pedido() =>"+session.getAttribute("TIPO_PEDIDO").toString()+"<==> TOTAL PROD =>"+total_prod);
				if(session.getAttribute("TIPO_PEDIDO") != null){
					if(session.getAttribute("TIPO_PEDIDO").toString().equals("SEG") && null == formulario.getTiene_documentos() ){
						System.out.println("Paso por SEGGGGGGGG ==>"+formulario.getSerie());
						importevg = Integer.parseInt(PosUtilesFacade.traeImporteVG(formulario.getSerie()));						 
						formulario.setAnticipo_pedido(importevg);
					}else{
						formulario.setPorcentaje_anticipo_pedido(porcentaje);
						System.out.println("formulario.getV_total_parcial() => "+formulario.getV_total_parcial()+"<==> formulario.getPorcentaje_anticipo_pedido() <==>"+formulario.getPorcentaje_anticipo_pedido()+"<==> porcentaje ==>"+porcentaje);
						formulario.setAnticipo_pedido((formulario.getV_total_parcial() * formulario.getPorcentaje_anticipo_pedido()) / 100);
					}
				} else {
					formulario.setPorcentaje_anticipo_pedido(porcentaje);
					System.out.println("formulario.getV_total_parcial() => "+formulario.getV_total_parcial()+"<==> formulario.getPorcentaje_anticipo_pedido() <==>"+formulario.getPorcentaje_anticipo_pedido()+"<==> porcentaje ==>"+porcentaje);
					formulario.setAnticipo_pedido((formulario.getV_total_parcial() * formulario.getPorcentaje_anticipo_pedido()) / 100);
				}
				
				//System.out.println("total_prod ==> "+total_prod);
				//formulario.setAnticipo_pedido(total_prod);
				//formulario.setPorcentaje_anticipo_pedido(importevg); 
				
				//formulario.setAnticipo_pedido((formulario.getV_total_parcial() * formulario.getPorcentaje_anticipo_pedido()) / 100);
				if ((formulario.getAnticipo_pedido() - total_pagado) >= 0)
				{
					formulario.setAnticipo_pedido(formulario.getAnticipo_pedido() -  total_pagado);
				}
				else
				{
					formulario.setAnticipo_pedido(0);
				}
				formulario.setTiene_documentos(this.TieneDocumentos(formulario.getSerie()));
				
				
				if (Constantes.STRING_PEDIDO.equals(formulario.getOrigen())) {
					formulario.setConvenio(this.traeConvenio(session));
					if (null != formulario.getConvenio() && null != formulario.getConvenio().getDescripcion()) {
						if (formulario.getConvenio().getImprime_guia().equals(Constantes.STRING_S) && formulario.getConvenio().getTipo().equals("O"))
						{
							if (null == formulario.getTiene_documentos() || formulario.getTiene_documentos().equals(Constantes.STRING_FALSE)) {
								formulario.setNif(formulario.getConvenio().getRut() + "-" + formulario.getConvenio().getDv());
								formulario.setDireccion(formulario.getConvenio().getDireccion());
								formulario.setRazon(formulario.getConvenio().getDescripcion());
								formulario.setGiro_descripcion(formulario.getConvenio().getGiro());
								formulario.setNombre_cliente(formulario.getConvenio().getDescripcion());
								formulario.setProvincia_descripcion(Constantes.STRING_BLANCO);
								formulario.setPoblacion(Constantes.STRING_BLANCO);
								formulario.setPorcentaje_anticipo_pedido(Constantes.INT_CERO);
								formulario.setAnticipo_pedido(Constantes.INT_CERO);
							}
						}
						
					}
				}
			}
			
			//valida si hay pagos para imprimir
			formulario.setTiene_pagos_actuales(Constantes.STRING_FALSE);
			
			for (PagoBean pago : listaPagos) {
				if (!pago.isTiene_doc())
				{
					formulario.setTiene_pagos_actuales(Constantes.STRING_TRUE);
				}
			}
			
			
			
		} catch (Exception e) {
			log.error("SeleccionPagoHelper:cargaInicial error catch",e);
		}
		
		
		ArrayList<PagoBean> lista_pagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
		
		
		if(null != lista_pagos && (lista_pagos.size() > 0)){
			formulario.setListaPagos(lista_pagos);
		}else{
			String ticket = (String) session.getAttribute(Constantes.STRING_TICKET);
			if(null != ticket){
				if(!("ALBARAN_DEVOLUCION".equals(formulario.getOrigen()))){
				try {					
					lista_pagos = this.traePagos(ticket, Constantes.STRING_PEDIDO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				}else{
					formulario.setListaPagos(new ArrayList<PagoBean>());
				}
				if(null != lista_pagos){
					formulario.setListaPagos(lista_pagos);
				}else{
					formulario.setListaPagos(new ArrayList<PagoBean>());
				}				
			}
		}
		
		
		return formulario;
	}
	private String TieneDocumentos(String codigo) {
		
		log.info("SeleccionPagoHelper:TieneDocumentos inicio");
		String tiene_documentos = Constantes.STRING_BLANCO;
    	try {
    		tiene_documentos = PosSeleccionPagoFacade.TieneDocumentos(codigo);
		} catch (Exception e) {
			log.error("SeleccionPagoHelper:TieneDocumentos error catch",e);
		}
    	return tiene_documentos;
	}
	public ConvenioBean traeConvenio(Session session)
	{
		try {
			String convenio = session.getAttribute(Constantes.STRING_CONVENIO).toString();
			
			if (null != convenio && !Constantes.STRING_BLANCO.equals(convenio)) {
				return this.traeConvenio(convenio);
			}
			else
			{
				return new ConvenioBean();
			}
		} catch (Exception e) {
			return new ConvenioBean();
		}
				
		}
    public String[] validaDocumento(SeleccionPagoForm formulario, int numero_documento, String local) 
    {
    	log.info("SeleccionPagoHelper:validaDocumento inicio");
    	String tipo_documento;
    	if (formulario.getTipo_doc() == Constantes.CHAR_G) {
			tipo_documento = Constantes.STRING_GUIA;
		}
    	else
    	{
    		tipo_documento = Constantes.STRING_BOLETA;
    	}
    	String resultado[] = new String[6];
    	try {
			resultado = PosSeleccionPagoFacade.validaDocumento(tipo_documento, numero_documento, local);
		} catch (Exception e) {
			log.error("SeleccionPagoHelper:validaDocumento error catch",e);
		}
    	return resultado;
    	
    }

	public ArrayList<PagoBean> agregaPago(SeleccionPagoForm formulario, Session session) {
		log.info("SeleccionPagoHelper:agregaPago inicio");
		ArrayList<PagoBean> listaPagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
		int total = Integer.parseInt(session.getAttribute(Constantes.STRING_TOTAL).toString());
		ArrayList<FormaPagoBean> listaFormasPago = new ArrayList<FormaPagoBean>();
		listaFormasPago = (ArrayList<FormaPagoBean>)session.getAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS);
		String fecha_negocio = session.getAttribute(Constantes.STRING_FECHA).toString();
			
		PagoBean pago = new PagoBean();
		pago.setCod_venta(formulario.getSerie());
		pago.setForma_pago(formulario.getForma_pago());
		
		pago.setCantidad(formulario.getV_a_pagar());
		pago.setFecha(formulario.getFecha());
		pago.setDescuento(formulario.getDescuento());
		pago.setRut_vs(formulario.getRut_vs());
		pago.setMonto_vs(formulario.getMonto_des_vs());

		
		Date fecha_1 = this.formatoFechaCh(fecha_negocio);
		Date fecha_2 = this.formatoFechaCh(pago.getFecha());
		
		boolean estado = true;
		
		if (fecha_2.before(fecha_1)) 
		{
			formulario.setFecha(fecha_negocio);
			estado = false;
			session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_FECHA_ANTERIOR);
		}
		else
		{
			formulario.setFecha(pago.getFecha());
		}
		
		for (FormaPagoBean formaPagoBean : listaFormasPago) {
			log.info("SeleccionPagoHelper:agregaPago entrando ciclo for");
			if (formaPagoBean.getId().equals(formulario.getForma_pago())) {
				pago.setDetalle_forma_pago(formaPagoBean.getDescripcion());
			}
		}
		
		
		if (null == listaPagos)
		{
			listaPagos = new ArrayList<PagoBean>();
			if (estado) {
				listaPagos.add(pago);
			}
		}
		else
		{
			//COMPRUEBA SI YA EXISTE LA MISMA FORMA DE PAGO EN LA MISMA FECHA
			for (PagoBean PagoBean : listaPagos) {
				log.info("SeleccionPagoHelper:agregaPago entrando ciclo for");
				if (PagoBean.getFecha().equals(pago.getFecha()) && PagoBean.getForma_pago().equals(pago.getForma_pago())) {
					estado = false;
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_REPITE_PAGO);
				} 
			}
			if (estado) {
				listaPagos.add(pago);
			}
		}
		
	
	return listaPagos;
	}

	public ArrayList<PagoBean> traePagos(String codigo_venta, String tipo) throws Exception{
		ArrayList<PagoBean> lista_pagos = new ArrayList<PagoBean>();
		log.info("SeleccionPagoHelper:traePagos inicio");

		try{
			
			lista_pagos = PosSeleccionPagoFacade.traePagos(codigo_venta, tipo);
			
		}catch(Exception ex){
			log.error("SeleccionPagoHelper:traePagos error catch",ex);
		}
		
		return lista_pagos;
		
	}
    /*
	 * LMARIN 20140708.
	 * Se Modifica el metodo para devolver diferentes casos al intentar modificar o anular la forma de pago 20140708.
	 */
	public String eliminaFormaPago(SeleccionPagoForm formulario, String local){
		log.info("SeleccionPagoHelper:eliminaFormaPago inicio");
		String respuesta = "";
		boolean respuesta2 = false;
		try{
			String cdg_vta = formulario.getSerie();
			String fecha_pedido = formulario.getFecha();
			String f_pago = formulario.getF_pago();
			String fecha_forma_pago = formulario.getFech_pago();
			String tipo_accion = formulario.getTipoaccion();
			String autorizador = formulario.getAutorizador();
			System.out.println("eliminaFormaPago ==>"+cdg_vta+","+fecha_pedido+","+f_pago+","+local+","+fecha_forma_pago+","+tipo_accion+","+autorizador);
			respuesta  = PosSeleccionPagoFacade.eliminaFormaPago(cdg_vta, fecha_pedido, f_pago, local, fecha_forma_pago,tipo_accion,autorizador);			
			respuesta2 = this.eliminarFormaPagoSession(formulario, local);
			
			if(respuesta.equals("TRUE") && respuesta2){
				respuesta  = "TRUE";
			}
			
			
		}catch(Exception ex){
			log.error("SeleccionPagoHelper:eliminaFormaPago error catch",ex);
		}
		System.out.println("Eliminar Forma de Pago =====>"+respuesta);
		return respuesta;
		
	}
	
	public boolean  eliminarFormaPagoSession(SeleccionPagoForm formulario, String local) throws Exception{
		log.info("SeleccionPagoHelper:eliminarFormaPagoSession inicio");
		boolean respuesta  = PosUtilesFacade.validaAperturaCaja(local, formulario.getFech_pago());
		boolean respuestaEliminar = false;
		ArrayList<PagoBean> listaAux = new ArrayList<PagoBean>();
		if(respuesta){
			if(null != formulario.getListaPagos()){				
				for(int i = 0 ; i < formulario.getListaPagos().size(); i ++){
					log.info("SeleccionPagoHelper:eliminarFormaPagoSession entrando ciclo for");
					PagoBean pago  =  formulario.getListaPagos().get(i);
					if(pago.getFecha().trim().equals(formulario.getFech_pago().trim()) &&  pago.getCod_venta().trim().equals(formulario.getSerie())){
						listaAux.add(pago);	
						
					}
				}
				
			}	
			
			if(null != listaAux){
				formulario.getListaPagos().removeAll(listaAux);
				respuestaEliminar= true;
			}
		}
		
		return respuestaEliminar;
	}
	public boolean eliminaFormaPagoAlbaranes(SeleccionPagoForm formulario, String local, String tipo){
		log.info("SeleccionPagoHelper:eliminaFormaPago inicio");
		boolean respuesta = false;
		boolean respuesta2 = false;
		try{
			String cdg_vta = formulario.getSerie();
			String fecha_pedido = formulario.getFecha();
			String f_pago = formulario.getF_pago();
			String fecha_forma_pago = formulario.getFech_pago();
			
			respuesta  = PosSeleccionPagoFacade.eliminaFormaPagoAlbaranes(cdg_vta, fecha_pedido, f_pago, local, fecha_forma_pago, tipo);			
			respuesta2 = this.eliminarFormaPagoSessionAlbaran(formulario, local);
			
			if(respuesta || respuesta2){
				respuesta  = true;
			}else{
				respuesta = false;
			}
			
			
		}catch(Exception ex){
			log.error("SeleccionPagoHelper:eliminaFormaPago error catch",ex);
		}
		return respuesta;
	}
	public boolean validaFormaPagoOA(String forma_pago, ConvenioBean convenioBean) {
		log.info("SeleccionPagoHelper:validaFormaPagoOA inicio");

			
			if ("OA".equals(forma_pago) || "OASD".equals(forma_pago)) {
				if (null == convenioBean.getTipo() && "".equals(convenioBean.getTipo())) {
					return false;
				}
				else
				{
					if ("O".equals(convenioBean.getTipo())) {
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			else
			{
				return true;
			}
			
		
		
	}
	public boolean verifica_OA_pagos(SeleccionPagoForm formulario) {

		ArrayList<PagoBean> lista = formulario.getListaPagos();
		
		for (PagoBean pagoBean : lista) {
			if (pagoBean.getForma_pago().equals("OA") || pagoBean.getForma_pago().equals("OASD")) {
				return true;
			}
		}
		
		return false;
	}
	public boolean validaPrimerPagoConvenio(SeleccionPagoForm formulario, Session session) {
		try{
				if ( null != formulario.getConvenio() && null != formulario.getConvenio().getId() && !(Constantes.STRING_BLANCO.equals(formulario.getConvenio().getId()))) {
					if(!formulario.getConvenio().getIsapre().equals("S")){
								if (formulario.getTiene_pagos() == 0) {
									//CUANTO NO TIENE PAGOS Y VA A PAGAR CON ORDEN DE ATENCION
									if ("S".equals(formulario.getConvenio().getImprime_guia()))
									{
										if( "OA".equals(formulario.getForma_pago()) || "OASD".equals(formulario.getForma_pago())) 
										{
											return true;
										}
										else
										{
											session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA_CONVENIO);
											formulario.setForma_pago("OA");
											return false;
										}
									}
									else
									{
											if("OA".equals(formulario.getForma_pago()) || "OASD".equals(formulario.getForma_pago())) 
											{
												//session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA_CONVENIO);
												//formulario.setForma_pago("OA");
												//return false;
												return true;
											}
											else
											{
												if ("C".equals(formulario.getConvenio().getTipo())) {
													return true;
												}
												else
												{
													session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA_CONVENIO);
													formulario.setForma_pago("OA");
													return false;
												}
												
												//return true;
											}
									}
								}
								else
								{
									//CUANTO TIENE PAGOS Y VA A PAGAR CON ORDEN DE ATENCION
									if ("OA".equals(formulario.getForma_pago()) || "OASD".equals(formulario.getForma_pago())) {
										session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA_CONVENIO_2);
										formulario.setForma_pago("1");
										return false;
									}
									else
									{
										if (formulario.getTiene_documentos().equals(Constantes.STRING_FALSE) && "S".equals(formulario.getConvenio().getImprime_guia())) {
											
											session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_ERROR_FPAGO_OA_CONVENIO_2_GUIA);
											formulario.setForma_pago("0");
											return false;
										}
										else
										{
											return true;
										}
									}
								}
					}else{
						return true;
					}
				}
				else
				{
					return true;
				}
		}catch(Exception e){
			System.out.println("Paso excepecion controlada =>"+e.getMessage());
			return true;
			
		}
	}
	public void valida_Documento_General(SeleccionPagoForm formulario,
			HttpSession session, String[] resultado, String local) {
		
		
			if (Constantes.CHAR_B == formulario.getTipo_doc() && Constantes.STRING_PEDIDO.equals(formulario.getOrigen()) || Constantes.STRING_ALBARAN_VENTA_DIRECTA.equals(formulario.getOrigen())) {
				if (Constantes.STRING_CERO.equals(resultado[0]))
				{
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_FALLA);
					session.setAttribute(Constantes.STRING_MSJE, resultado[1]);
				}
				else if (Constantes.STRING_UNO.equals(resultado[0]))
				{
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_EXITO);
					session.setAttribute(Constantes.STRING_DOCUMENTO, formulario.getNumero_boleta());
					session.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, formulario.getTipo_doc());
				}
				else
				{
					//en el caso de que la boleta este ocupada, y que vendga de pedido o albaran de venta directa
					if (this.validaCaja(local, resultado[3])) 
					{
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_CAMBIO_FOLIO);
						session.setAttribute(Constantes.STRING_MSJE, resultado[2]);
						session.setAttribute(Constantes.STRING_RESULTADO_CAMBIO_FOLIO, resultado);
					}
					else
					{
						session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_FALLA);
						session.setAttribute(Constantes.STRING_MSJE, resultado[1]);
					}
				}
			}
			else
			{
				if (!Constantes.STRING_UNO.equals(resultado[0]))
				{
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_FALLA);
					session.setAttribute(Constantes.STRING_MSJE, resultado[1]);
				}
				else
				{
					session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_EXITO);
					session.setAttribute(Constantes.STRING_DOCUMENTO, formulario.getNumero_boleta());
					session.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, formulario.getTipo_doc());
				}
			}
			
		
	}
	public void realiza_cambio_boleta(String[] resultado, int numero_boleta, Session session, String[] resultado_temp) {
		log.info("SeleccionPagoHelper:realiza_cambio_boleta inicio");
		boolean estado = false;
		
		if (Constantes.STRING_UNO.equals(resultado_temp[0])) {
			try {
				estado = PosSeleccionPagoFacade.cambiaFolioDocumento(resultado[2], resultado[3], resultado[4], resultado[5], numero_boleta);
				session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_FALLA);
				session.setAttribute(Constantes.STRING_MSJE, Constantes.STRING_CAMBIO_FOLIO_OK);
			} catch (Exception e) {
				log.error("SeleccionPagoHelper:realiza_cambio_boleta error catch",e);
			}
			
			if (!estado) {
				session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_FALLA);
				session.setAttribute(Constantes.STRING_MSJE, Constantes.STRING_ERROR_CAMBIO_DOCUMENTO);
			}
		}
		else
		{
				session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_BOLETA_FALLA);
				session.setAttribute(Constantes.STRING_MSJE, resultado_temp[1]);
		}
		
		
	}
	
	public boolean  eliminarFormaPagoSessionAlbaran(SeleccionPagoForm formulario, String local) throws Exception{
		log.info("SeleccionPagoHelper:eliminarFormaPagoSession inicio");
		boolean respuesta  = PosUtilesFacade.validaAperturaCaja(local, formulario.getFech_pago());
		
		if(respuesta){
			if(null != formulario.getListaPagos()){
				
				for(int i = 0 ; i < formulario.getListaPagos().size(); i ++){
					log.info("SeleccionPagoHelper:eliminarFormaPagoSession entrando ciclo for");
					PagoBean pago  =  formulario.getListaPagos().get(i);
					if(pago.getFecha().trim().equals(formulario.getFech_pago().trim()) &&  pago.getCod_venta().trim().equals(formulario.getSerie())){
						formulario.getListaPagos().remove(i);						
					}
				}
				return true;
			}	
		}
		
		return false;
	}
	public void aplicaDescuentoVentaDirecta(Session session,
			SeleccionPagoForm formulario, double cant) {
		ArrayList<ProductosBean> lista_productos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
		
		for (ProductosBean producto : lista_productos) {
			if (!producto.getFamilia().equals(Constantes.STRING_MUL)) {
				int precio = producto.getPrecio() * producto.getCantidad();
				double diferencia = cant / 100;
				double valor = Math.round(precio * diferencia);
				double saldo = precio - valor;
				int total_linea =  (int) Math.floor(saldo);
				producto.setDescuento(cant);
				producto.setImporte(total_linea);
			}
		}
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, lista_productos);
		
		int total = Constantes.INT_CERO;
		for (ProductosBean producto : lista_productos) {
			total = total + producto.getImporte();
			
		}
		formulario.setV_total(total);
		formulario.setV_total_parcial(total);
		formulario.setDescuento(cant);
		formulario.setDiferencia(total);
		
	}
	public void TraeBoleta(SeleccionPagoForm formulario, Session session) throws Exception {
		
		ArrayList<ProductosBean> listProductos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_PRODUCTOS);
		ArrayList<ProductosBean> listProductosAdicionales = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_PRODUC_ADICIONALES);
		ArrayList<ProductosBean> listCabeceraMulrioferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS);
		ArrayList<ProductosBean> listDetalleMulrioferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
		
		Optional<ArrayList<ProductosBean>> lp  =  Optional.ofNullable(listProductos);
		Optional<ArrayList<ProductosBean>> lpa =  Optional.ofNullable(listProductosAdicionales);
		Optional<ArrayList<ProductosBean>> lcm =  Optional.ofNullable(listCabeceraMulrioferta);
		Optional<ArrayList<ProductosBean>> ldm =  Optional.ofNullable(listDetalleMulrioferta);
		
		if (!lp.isPresent()) listProductos = new ArrayList<ProductosBean>();
		if (!lpa.isPresent()) listProductosAdicionales = new ArrayList<ProductosBean>();
		if (!lcm.isPresent()) listCabeceraMulrioferta = new ArrayList<ProductosBean>();
		if (!ldm.isPresent()) listDetalleMulrioferta = new ArrayList<ProductosBean>();		
		
		try {
			formulario.setCliente_venta(session.getAttribute(Constantes.STRING_CLIENTE_VENTA).toString());
		} catch (Exception e) {
			formulario.setCliente_venta(formulario.getCodigo());
		}
		
		
		ArrayList<ProductosBean> listProductosAdicionalesTemp = new ArrayList<ProductosBean>();
		listProductosAdicionalesTemp.addAll(listProductosAdicionales);
		
		String sucursal = (String)session.getAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL);
		String usuario = (String)session.getAttribute(Constantes.STRING_AGENTE);
		
		/*Rescato el telefono de la tienda*/
		String [] tienda = sucursal.split("-");
		ArrayList<TiendaBean> arrTienda = PosUtilesFacade.traeDatosTienda(tienda[0]);
		
		for(TiendaBean t : arrTienda){
			if( t.getTelefono1() != null ){
				formulario.setTelefono_tienda(t.getTelefono1());
			}else{
				formulario.setTelefono_tienda("600 822 02 00");
			}
		 
	    }
						
		String fechaEntrega = Constantes.STRING_BLANCO;
		ArrayList<PagoBean> pagos = (ArrayList<PagoBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_PAGOS);
		ArrayList<FormaPagoBean> formaPago = (ArrayList<FormaPagoBean>)session.getAttribute(Constantes.STRING_REPORTER_LISTA_FORMA_PAGO);
		int totalBoleta=0;
		int pagos_anteriores=0;
		int pagos_actuales=0;
		formulario.setBoleta_cliente(formulario.getNombre_cliente());
		if (!formulario.getOrigen().equals(Constantes.STRING_DIRECTA)) 
		{
			ClienteBean cliente = new ClienteBean();
			cliente = this.traeCliente(null, formulario.getCliente_venta());
			formulario.setBoleta_cliente(cliente.getNombre() + " " + cliente.getApellido());
			formulario.setBoleta_rut(cliente.getNif() + "-" + cliente.getDvnif());
			
			formulario.setBoleta_fecha_ped((String)session.getAttribute(Constantes.STRING_FECHA));
			formulario.setBoleta_titulo_fecha_ped(Constantes.STRING_FECHA_TITULO);
			fechaEntrega = (String)session.getAttribute(Constantes.STRING_FECHA_ENTREGA);
			formulario.setBoleta_titulo_albaran(Constantes.STRING_TITULO_NUMERO_PEDIDO);
		}
		else
		{
			formulario.setBoleta_titulo_albaran(Constantes.STRING_TITULO_NUMERO_ALBARAN);
		}
		
		formulario.setBoleta_hora(this.traeHoraString());
		formulario.setBoleta_tienda(sucursal);
		formulario.setBoleta_albaran(formulario.getSerie());
		formulario.setBoleta_vendedor(usuario);
		String fecha = this.traeFechaHoyFormateadaString();
		System.out.println("FECHA ENTREGA HELPER TRAE BOLETA => "+fechaEntrega);
		if(null != fechaEntrega && Constantes.STRING_BLANCO != fechaEntrega)
		{
			formulario.setBoleta_titulo_fecha_ent("Fecha Entrega");
			//Le sumo uno a la Fecha Entrega
			formulario.setBoleta_fecha_ent(this.fechaExcepMasUno(fechaEntrega));
			//formulario.setBoleta_fecha_ent(fechaEntrega);
		}
		else
		{
			formulario.setBoleta_titulo_fecha_ent("Fecha Entrega");
			formulario.setBoleta_fecha_ent(this.traeFechaHoyFormateadaString());
		}
		
		for(int i=1,x=0;x<pagos.size();i++,x++){
			log.info("ReportesHelper:creaBoleta entrando ciclo for");
			PagoBean pago = pagos.get(x);
			if (i == 1) {
				formulario.setBoleta_titulo_fpago_1(this.buscaFormaPago(formaPago,pago));
				formulario.setBoleta_fpago_1("$ "+this.getNumber(Integer.toString(pago.getCantidad())));
			}
			if (i == 2) {
				formulario.setBoleta_titulo_fpago_2(this.buscaFormaPago(formaPago,pago));
				formulario.setBoleta_fpago_2("$ "+this.getNumber(Integer.toString(pago.getCantidad())));
			}
			if (i == 3) {
				formulario.setBoleta_titulo_fpago_3(this.buscaFormaPago(formaPago,pago));
				formulario.setBoleta_fpago_3("$ "+this.getNumber(Integer.toString(pago.getCantidad())));
			}
			if (i == 4) {
				formulario.setBoleta_titulo_fpago_4(this.buscaFormaPago(formaPago,pago));
				formulario.setBoleta_fpago_4("$ "+this.getNumber(Integer.toString(pago.getCantidad())));
			}
			
			totalBoleta=totalBoleta+pago.getCantidad();
			if (pago.isTiene_doc()) {
				pagos_anteriores = pagos_anteriores+pago.getCantidad();
			}
			else
			{
				pagos_actuales = pagos_actuales+pago.getCantidad();
				fecha = pago.getFecha();
			}
			
		}
		
		formulario.setBoleta_fecha(fecha);

		if (Constantes.STRING_TRUE.equals(formulario.getTiene_documentos())) 
		{
			formulario.setBoleta_titulo_resumen_total_pagar(Constantes.STRING_BOLETA_TOTAL_PAGAR_POR_ANTICIPO);
			formulario.setBoleta_resumen_total("TOTAL				" + "$ "+this.getNumber(Integer.toString(formulario.getV_total_parcial())));
			formulario.setBoleta_resumen_anticipo("ANTICIPO			" + "$ "+this.getNumber(Integer.toString(totalBoleta)));
			formulario.setBoleta_resumen_pendiente("PENDIENTE			" + "$ "+this.getNumber(Integer.toString(formulario.getDiferencia())));

		}
		else
		{
			if (totalBoleta == formulario.getV_total_parcial()) 
			{
				//total sin anticipo
				formulario.setBoleta_titulo_resumen_total_pagar(Constantes.STRING_BOLETA_TOTAL_PAGAR);
				formulario.setBoleta_resumen_total("TOTAL			" + "$ "+this.getNumber(Integer.toString(totalBoleta)));
				formulario.setBoleta_resumen_anticipo(Constantes.STRING_BLANCO);
				formulario.setBoleta_resumen_pendiente(Constantes.STRING_BLANCO);
				
			}
			else
			{
				//solo anticipo
				formulario.setBoleta_titulo_resumen_total_pagar(Constantes.STRING_BOLETA_TOTAL_PAGAR_POR_ANTICIPO);
				formulario.setBoleta_resumen_total("TOTAL				" + "$ "+this.getNumber(Integer.toString(formulario.getV_total_parcial())));
				formulario.setBoleta_resumen_anticipo("ANTICIPO			" + "$ "+this.getNumber(Integer.toString(totalBoleta)));
				formulario.setBoleta_resumen_pendiente("PENDIENTE			" + "$ "+this.getNumber(Integer.toString(formulario.getDiferencia())));
			}
			
		}
		
		formulario.setBoleta_total_pagar(this.getNumber(Integer.toString(pagos_actuales)));
		//parametros.put(Constantes.STRING_REPORTER_TOTAL, this.getNumber(Integer.toString(totalBoleta)));


		ArrayList<ProductosBean> data = new ArrayList<ProductosBean>();
		ArrayList<ProductosBean> listProductos_total = new ArrayList<ProductosBean>();
		
		listProductos_total.addAll(this.ListaProductosBoleta(listProductos, listCabeceraMulrioferta, listDetalleMulrioferta));
		
		for(ProductosBean articulo:listProductos_total){
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
		
		if(null != listProductosAdicionalesTemp && listProductosAdicionalesTemp.size() > 0){
			
			for(ProductosBean articuloAd:listProductosAdicionalesTemp){
				log.info("ReportesHelper:buscaFormaPago entrando ciclo for");
				ProductosBean articuloPrint = new ProductosBean();
				articuloPrint.setCantidad(articuloAd.getCantidad());
				articuloPrint.setDescripcion(articuloAd.getDescripcion());
				articuloPrint.setCod_barra(articuloAd.getCod_barra());
				articuloPrint.setImporte(articuloAd.getImporte());
				articuloPrint.setPrecio(articuloAd.getPrecio());
				articuloPrint.setDescuento(articuloAd.getDescuento());
				String descripcion =articuloAd.getCod_barra()+"  "+articuloAd.getDescripcion();
				articuloPrint.setDescripcion(descripcion);
				data.add(articuloPrint);
			}

		}
		
		formulario.setBoletaListaProductos(new ArrayList<ProductosBean>());
		if (formulario.getDiferencia()==0) 
		{
			formulario.getBoletaListaProductos().addAll(data);
		}
		
	}

	public void TraeGuia(SeleccionPagoForm formulario, Session session) throws NumberFormatException, Exception {
		
		ArrayList<ProductosBean> listaProduc = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
		ArrayList<ProductosBean> listCabeceraMulrioferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS);
		ArrayList<ProductosBean> listDetalleMulrioferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
		
		Utils util = new Utils();
		formulario.setTitulo("");
		formulario.setGuia_fecha(util.traeFechaHoyFormateadaString().substring(0, 2));
		formulario.setGuia_fecha(util.traeFechaHoyFormateadaString());
		
		formulario.setGuia_cliente(formulario.getNombre_cliente());
		formulario.setGuia_direccion(formulario.getDireccion());
		formulario.setGuia_giro(formulario.getGiro_descripcion());
		formulario.setGuia_rut(formulario.getNif());
		formulario.setGuia_comuna(formulario.getProvincia_descripcion());
		
		
		formulario.setGuiaListaProductos(new ArrayList<ProductosBean>());
		formulario.getGuiaListaProductos().addAll(this.ListaProductosBoleta(listaProduc, listCabeceraMulrioferta, listDetalleMulrioferta));
		
		
		String codigo_cliente = session.getAttribute(Constantes.STRING_CLIENTE).toString();
		ClienteBean cliente = new ClienteBean();
		cliente = PosClientesFacade.traeCliente(null, codigo_cliente);
		
		formulario.setGuia_pie_nombre(cliente.getNombre() + " " + cliente.getApellido());
		formulario.setGuia_pie_rut(cliente.getNif() + "-" + cliente.getDvnif());
		
		int subtotal = Constantes.INT_CERO;
		int total = Constantes.INT_CERO;
		int art_con = Constantes.INT_CERO;
		int dto =  Constantes.INT_CERO;
		int total_facturar = Constantes.INT_CERO;
		int diferencia = Constantes.INT_CERO;
		//LMARIN 20170810
		
			for (ProductosBean productosBean : formulario.getGuiaListaProductos()) {
				if(productosBean.getFamilia() != null){
					if(productosBean.getFamilia().equals("DES")){
						art_con = productosBean.getImporte();
					}
				}
				total = total + productosBean.getImporte();
				subtotal += productosBean.getCantidad() * productosBean.getPrecio();
				
			}
	

		if (art_con != 0) {
			System.out.println("Paso DTO SEG ==>"+art_con);
			formulario.setTitulo("RECIBO POR RECAUDACION");
			formulario.setGuia_convenio_titulo_diferencia("SUMA ASEGURADA POR BCI");
			formulario.setGuia_convenio_titulo_total_facturar("TOTAL RECAUDACION PARA BCI");
			
		    total_facturar = Constantes.INT_CERO;
			diferencia = Constantes.INT_CERO;
			
			total_facturar = art_con;
			diferencia = total - art_con;
			
			formulario.setGuia_convenio_total_facturar(String.valueOf(total_facturar));
			formulario.setGuia_convenio_diferencia(String.valueOf(diferencia));
		
			dto = 0;
		   
		}else if(null != session.getAttribute("TIPO_PEDIDO") ){
			if(session.getAttribute("TIPO_PEDIDO").toString().equals("SEG")) {
				formulario.setTitulo("RECIBO POR RECAUDACION");
				formulario.setGuia_convenio_titulo_diferencia("SUMA ASEGURADA POR BCI");
				formulario.setGuia_convenio_titulo_total_facturar("TOTAL RECAUDACION PARA BCI");
				
				total_facturar = Integer.parseInt(PosUtilesFacade.traeImporteVG(formulario.getSerie()));				
				diferencia = total_facturar;
				total = total - diferencia; 
				dto = 	diferencia;
				formulario.setGuia_convenio_total_facturar(String.valueOf(total_facturar));
				formulario.setGuia_convenio_diferencia(String.valueOf(diferencia));
			}
			
		}else {
			dto = subtotal - total ;
		}
		
		System.out.println("Paso DTO SEG / total ==>"+total+"<==> sub ==>"+subtotal);
		formulario.setGuia_descuento(String.valueOf((dto)));
		formulario.setGuia_subtotal(String.valueOf(subtotal));
		formulario.setGuia_total(String.valueOf(total));
		
		if (Constantes.STRING_PEDIDO.equals(formulario.getOrigen())) 
		{
			formulario.setConvenio(this.traeConvenio(session));
			formulario.setGuia_ticket("Encargo: " + formulario.getSerie());
			
			if (null != formulario.getConvenio() && null != formulario.getConvenio().getDescripcion()) 
			{
				if (formulario.getConvenio().getImprime_guia().equals(Constantes.STRING_S) && formulario.getConvenio().getTipo().equals("O")) 
				{
					formulario.setGuia_convenio_titulo_diferencia(Constantes.STRING_TITULO_CONVENIO_DIFERENCIA);
					formulario.setGuia_convenio_titulo_total_facturar(Constantes.STRING_TITULO_CONVENIO_FACTURAR);
					
					total_facturar = Constantes.INT_CERO;
					diferencia = Constantes.INT_CERO;
					
					total_facturar = total - formulario.getDiferencia();
					diferencia = formulario.getDiferencia();
					
					formulario.setGuia_convenio_total_facturar(String.valueOf(total_facturar));
					formulario.setGuia_convenio_diferencia(String.valueOf(diferencia));
				}
				
			}
		}
		else
		{
			formulario.setGuia_convenio_titulo_diferencia(Constantes.STRING_BLANCO);
			formulario.setGuia_convenio_titulo_total_facturar(Constantes.STRING_BLANCO);
			formulario.setGuia_convenio_total_facturar(Constantes.STRING_BLANCO);
			formulario.setGuia_convenio_diferencia(Constantes.STRING_BLANCO);
		}
		
	}
	
	public int  respaldo_boleta(SeleccionPagoForm formulario){
		
		int estado = 0;
		try {
			estado = PosUtilesFacade.respaldo_boleta(formulario.getSerie(),formulario.getAutorizador(),formulario.getFech_pago(),formulario.getTipoaccion());

		} catch (Exception e) {
			log.error("VentaPedidoHelper:respaldo_boleta error catch",e);
		}
		return estado;
		
	} 
	
	/*
	 * LMARIN 20140417 
	 */
	public int reimprimeboleta(SeleccionPagoForm formulario){
		
		int estado = 0;
		try {
			
			System.out.println("numero de actualizacion de boleta ==>"+formulario.getNumero_boleta_up());
			estado = PosVentaPedidoFacade.reimprimeboleta(formulario.getNumero_boleta_up(),formulario.getSerie(),formulario.getFech_pago());

		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		return estado;
	}
	
	
	/*
	 * LMARIN 20150525
	 * Se retorna datos boleta electronica reimpresion
	 */
	public ArrayList<BoletaBean> reimpresionBoletaelec(SeleccionPagoForm formulario){
		
		ArrayList<BoletaBean> boleta = new ArrayList<BoletaBean>();
		
		try {
						
			boleta = PosSeleccionPagoFacade.reimpresionBoletaelec(formulario.getBoleta_fecha(),formulario.getSerie(),formulario.getBoleta_tienda());

		} catch (Exception e) {
			log.error("SeleccionPagoHelper:reimpresionBoletaelec error catch",e);
		}
		
		return  boleta;
	}
	/*
	 * LMARIN 20141028
	 * Se solicita el ingreso de un rut
	 */
	public String validaDtoCliente(SeleccionPagoForm formulario){
		String estado = "";
		try {
			estado = PosSeleccionPagoFacade.validaDtoCliente(formulario.getCliente_dto(),formulario.getCodigo_convenio());

		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		
		return  estado;
	}
	
	public String valida_usuario_vp(SeleccionPagoForm formulario)  throws Exception{
		
		String valor = "";
		try {
	        valor = PosUtilesFacade.valida_usuario_vp(formulario.getSerie(),formulario.getNif(),formulario.getV_a_pagar());
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("VebntaPedidoHelper: valida_usuario_vp");
	    }
	}
	
	public String valida_ip(SeleccionPagoForm formulario)  throws Exception{
		
		String valor = "";
		try {
	        valor = PosUtilesFacade.valida_ip(formulario.getIp(),formulario.getLocal());
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("VentaPedidoHelper: valida_ip");
	    }
	}
	
	
	
}
