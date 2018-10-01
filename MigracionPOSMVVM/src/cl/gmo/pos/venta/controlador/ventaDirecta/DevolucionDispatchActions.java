package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;


public class DevolucionDispatchActions {
	Logger log = Logger.getLogger( this.getClass() );
	
	public DevolucionForm cargaInicial(DevolucionForm formulario, DevolucionHelper helper, String local, Session session){		
		log.info("DevolucionDispatchActions:cargaInicial inicio");
    	formulario.setFecha(helper.traeFechaHoyFormateadaString());
    	formulario.setHora(helper.traeHoraString());
    	formulario.setListaAgentes(helper.traeAgentes(local));
    	formulario.setListaFormasPago(helper.traeFormasPago());
    	formulario.setListaConvenios(helper.traeConvenios());
    	formulario.setListaIdiomas(helper.traeIdiomas());
    	formulario.setListaDivisas(helper.traeDivisas());
		formulario.setListaTipoAlbaranes(helper.traeTipoAlbaranes());
		formulario.setLista_mot_devo(helper.traeMotivoDevolucion());
		formulario.setUsuario((String)session.getAttribute(Constantes.STRING_USUARIO));
		formulario.setListaProvincia(helper.traeProvinciasdev());
		formulario.setEstado_boleta("-1");
		helper.validaAutorizacionKodak(formulario);
		
		
		log.info("DevolucionDispatchActions:cargaInicial fin");
		return formulario;
		
	}
	
	public void resetSession(DevolucionForm formulario, Session session){
		
		session.setAttribute(Constantes.STRING_TOTAL, "");
		session.setAttribute("suma_total_albaranes", "");
    	session.setAttribute(Constantes.STRING_CLIENTE, "");
    	session.setAttribute(Constantes.STRING_TICKET, "");
    	session.setAttribute(Constantes.STRING_DIVISA, ""); 
		session.setAttribute(Constantes.STRING_CAMBIO, "");
		
		session.setAttribute(Constantes.STRING_TIPO_ALBARAN, "");
		session.setAttribute(Constantes.STRING_ORIGEN, "");
		session.setAttribute(Constantes.STRING_ESTADO_FORM, "");
		session.setAttribute(Constantes.STRING_LISTA_PAGOS, null);
		session.setAttribute(Constantes.STRING_FECHA, "");	
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, null);
		session.setAttribute("lista_albaranes_buscados", null);
		session.setAttribute(Constantes.STRING_LISTA_PAGOS, null);
		session.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, null);
		
	}
	
	public DevolucionForm cargaFormulario(DevolucionForm form,
			Session request)
	{
		log.info("DevolucionDispatchActions:cargaFormulario inicio");
		DevolucionForm formulario = (DevolucionForm)form;		
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		
		this.resetSession(formulario, session);
		
		
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
    	String accion = formulario.getAccion();
    	formulario.cleanForm();
    	formulario.setEstaGrabado(0);
    	this.cargaInicial(formulario, helper, local, session);
    	
    	
		formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
		formulario.setIdioma(Constantes.STRING_CAST);
		formulario.setBoleta_guia(Constantes.STRING_B);		
		session.setAttribute("lista_albaranes_buscados", null);
		formulario.setInicio_pagina("inicio");
		formulario.setTipo_albaran("");
		if("nuevo".equals(accion)){
			//formulario = helper.traeCodigoDevolucion(local, formulario);
			formulario.setMostrarIconos("buscar");
			formulario.setInicio_pagina("");
			formulario.setEstaGrabado(2);
		}
		
		log.info("DevolucionDispatchActions:cargaFormulario fin");
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}
	
	public DevolucionForm IngresaEntregaDesdePedido(DevolucionForm form,
			Session request) {
		
		Session session = request;
		DevolucionForm formulario = (DevolucionForm)form;
		formulario.setAccion("traeAlbaranBuscado");
		formulario.setCdg_venta((String)session.getAttribute(Constantes.STRING_CDG));
		
		this.cargaAlbaran(formulario, request);
		formulario.setEstaGrabado(2);
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}
	
	public DevolucionForm cargaAlbaran(DevolucionForm form,
			Session request)
	{
		log.info("DevolucionDispatchActions:cargaAlbaran inicio");
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
    	formulario.setEstaGrabado(2);
    	formulario.setExisteBoleta("");
		try{
			//trae la venta segun el numero de boleta para realizar la devolucion
			if(Constantes.STRING_ACTION_CARGA_DATOS.equals(formulario.getAccion())){
				formulario = helper.traeDevolucion(formulario.getNumero_boleta_guia(), formulario.getBoleta_guia(), formulario);
				formulario = helper.traeCodigoDevolucion(local, formulario);
				formulario.setAlbaranDevolcionPago("");
				formulario.setCodigo1("");
				formulario.setCodigo2("");
				formulario = cargaInicial(formulario, helper, local, session);
				formulario.setInicio_pagina("");
				String cdg_vta = formulario.getCodigo1()+"/"+formulario.getCodigo2();				
				session.setAttribute(Constantes.STRING_TIPO_DOCUMENTO,formulario.getBoleta_guia());
				formulario.setCdg_venta(cdg_vta);
				session.setAttribute(Constantes.STRING_TICKET, formulario.getCdg_venta());
				formulario.setCargado(Constantes.STRING_TRUE);
				formulario.setRespuestaDevolucion("");
				formulario.setAgente((String) session.getAttribute(Constantes.STRING_USUARIO));
				formulario.setEstado_boleta("false");
				session.setAttribute("DEVFORM", formulario);
				
				
				if("true".equalsIgnoreCase(formulario.getExisteBoleta())){
					
				}else{
					if("NO".equalsIgnoreCase(formulario.getExisteBoleta())){
						formulario.setExisteBoleta("NO");
					}
				}
				
				SeleccionPagoHelper helperPagos = new SeleccionPagoHelper();
				//Suma del total de los producotos del alabran
				//se reutiliza el metodo sumaTotalValorProductos de venta directa
				VentaDirectaHelper helperVtaDire = new VentaDirectaHelper();
				VentaDirectaForm formVtaDirecta = new VentaDirectaForm();
				ArrayList<ProductosBean> lista_Productos = new ArrayList<ProductosBean>();
				ArrayList<ProductosBean> lista_ProductosAux = new ArrayList<ProductosBean>(formulario.getLista_productos().size()+1);
				lista_ProductosAux.addAll(formulario.getLista_productos());
				
				if(null != lista_ProductosAux){
					for(ProductosBean pro: lista_ProductosAux){
						pro.setCantidad(pro.getCantidad()*-1);
						lista_Productos.add(pro);
					}
				}
				
				if("false".equals(formulario.getExisteBoleta())){
					formulario.setTipo_albaran("DEVOLUCION");
					formVtaDirecta.setListaProductos(lista_Productos);
					formulario.setSumaTotalAlabaranes(helper.sumaTotalDescuentos(formulario));//para llenar el campo de  total el cual puede ser modifcado en caso de descuento
					formulario.setSumaTotal(helper.sumaTotalValorProductosAlbaran(formulario)*-1);//suma total real
					//helper.sumaTotalDescuentos(formulario);
					String origen = "ALBARAN_"+formulario.getTipo_albaran();
					session.setAttribute(Constantes.STRING_TOTAL, formulario.getSumaTotalAlabaranes());
					session.setAttribute("suma_total_albaranes", formulario.getSumaTotal());
			    	session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCodigo_cliente());
			    	session.setAttribute(Constantes.STRING_TICKET, formulario.getCdg_venta());
			    	session.setAttribute(Constantes.STRING_DIVISA, formulario.getDivisa()); 
					session.setAttribute(Constantes.STRING_CAMBIO, formulario.getCambio());
					//session.setAttribute(Constantes.STRING_CAJA, formulario.getNumero_caja()); 
					session.setAttribute(Constantes.STRING_TIPO_ALBARAN, formulario.getTipoAlbaran());
					session.setAttribute(Constantes.STRING_ORIGEN, origen);
					session.setAttribute(Constantes.STRING_ESTADO_FORM, Constantes.STRING_BLANCO);
					session.setAttribute(Constantes.STRING_LISTA_PAGOS, null);
					session.setAttribute(Constantes.STRING_FECHA, formulario.getFecha());	
					session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getLista_productos());
				
					String sucursal = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
					String nombre_sucursal = session.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL).toString();				
					session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, helperVtaDire.traeProductosGratuitos(formulario.getLista_productos() ,nombre_sucursal ,sucursal ));
					
				
								
				
					//boolean validaCaja = helper.validaCaja(local, formulario.getFecha());
					boolean validaCaja = true;
					formulario.setEstadoCaja(String.valueOf(validaCaja));
			
					//condicion para determinar si se puede realizar la devolucion
					if(validaCaja){	
						//SE PUEDE DEVOLVER
						formulario.setDevolver_vta("true");
					}else{
						//NO SE PUEDE DEVOLVER
						formulario.setDevolver_vta("false");
					}					
				}
			
				//setear la cantidad a -1
				if(null != lista_ProductosAux){
					for(ProductosBean pro: lista_ProductosAux){
						pro.setCantidad(pro.getCantidad()*-1);
						lista_Productos.add(pro);
					}
				}				
				
				formulario.setAgente("");
				//determinar si es controler
				String agente = String.valueOf(session.getAttribute(Constantes.STRING_USUARIO));
				session.setAttribute("isComntroller", helper.isController(agente));
				
			}else if(Constantes.FORWARD_DEVOLUCION.equals(formulario.getAccion())){
				
				//LMARIN 201560603 NOTA DE CREDITO
				
				formulario.setEstaGrabado(2);
				String ncreddev ="",out2="",resb="";
				String agente = formulario.getAgenteSeleccionado();
				DevolucionForm formularioAux = new DevolucionForm();
				String albaranDevolcionPago = formulario.getAlbaranDevolcionPago();
				DevolucionBean respDevo = new DevolucionBean();
				
				//System.out.println("N° BOLETA GUIA =>"+formulario.getNumero_boleta_guia()+"<==> BOLETA GUIA =>"+formulario.getBoleta_guia());
				formularioAux = helper.traeDevolucion(formulario.getNumero_boleta_guia(), formulario.getBoleta_guia(), formularioAux);
				formularioAux.setTipoAlbaran(formulario.getTipoAlbaran());
				formularioAux.setAgente(formulario.getAgente());
				formularioAux.setMotivo(formulario.getMotivo());
				formularioAux.setHora(formulario.getHora());
				formularioAux.setFecha(formulario.getFecha());
				formularioAux.setNombreCliente(formulario.getNombreCliente());
				formularioAux.setKodak(formulario.getKodak());
				formularioAux.setEntrega(formulario.getEntrega());
				
				//System.out.println("albaranDevolcionPago ===> "+albaranDevolcionPago);
				if(!("OK".equals(albaranDevolcionPago))){		
														
					//GENERO DEVOLUCION
					System.out.println("Realiza Devolucion antes");
					
					respDevo = helper.realizaDevolucion(formulario, local);

					System.out.println("Realiza Devolucion despues + respuesta =>"+respDevo.isRespuestaDevolucion());
					
					if(respDevo.isRespuestaDevolucion()){
						
						ArrayList<PagoBean> listaPagos =(ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
						session.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, formulario.getTipoAlbaran()	);
						formulario.setTipoAlbaran("S");
						
						String agenteTemporal = session.getAttribute(Constantes.STRING_USUARIO).toString();
						session.setAttribute(Constantes.STRING_USUARIO, formulario.getAgenteSeleccionado());
						
						
						
						session.setAttribute(Constantes.STRING_USUARIO, agenteTemporal);
						
						formulario = cargaInicial(formulario, helper, local, session);
						if(null != respDevo.getCodigo1()&& !("".equals(respDevo.getCodigo1()))){
							String [] codigo = respDevo.getCodigo1().split("/");
							formulario.setCodigo1(respDevo.getSerie_vta());
							System.out.println("CODIGO DEVOLUCION ===>"+codigo[1]);
							if(codigo.length>0){
								formulario.setCodigo2(codigo[1]);
							}
							
						}
						
						System.out.println("PASO ANTES DE GUARDAR DOCUMENTOS");
			
						//GENERO NOTA DE CREDITO 20150604
						DevolucionForm devform = null;
						SeleccionPagoForm spagoform = null;
						DevolucionForm devformtmp1 = (DevolucionForm) session.getAttribute("DEVFORM");

						if(devformtmp1 == null){
							System.out.println("=>NUEVO DEVFORM NULO <=");
							session.setAttribute("DEVFORM1", formulario);
							 devform = (DevolucionForm) session.getAttribute("DEVFORM1");
						}else{
							System.out.println("=> NORMAL DEVFORM <=");
							 devform = (DevolucionForm) session.getAttribute("DEVFORM");
						}
				
						String idoc = helper.insertaDocumento(formulario.getCodigo1()+"/"+formulario.getCodigo2(),
								0,
								"N",
								devform.getSumaTotalAlabaranes(), helper.formatoFecha(helper.traeFecha()),local);
						
						
						System.out.println("NEW IDOC  ==>"+idoc+" dir cli =>"+devform.getDireccion_cli()+"<==> N DIR =>"+devform.getNdireccion_cli()+" c cli =>"+devform.getComu_cli());

						
						String [] folio = idoc.split("_");
						System.out.println("idoc ==> "+idoc);
						//GENERA BOLETA

						//System.out.println("GENERA BOLETA =>"+spagoform.getSerie()+"<=>"+spagoform.getBoleta_cliente()+"<=>Nif() =>"+spagoform.getNif()+"<=> Nombre_cliente() =>"+spagoform.getNombre_cliente()+"<=> Boleta_fecha_ent()=>"+spagoform.getBoleta_fecha_ent()+"<=>FECHA =>"+spagoform.getFecha()+"<=> FPAGO =>"+spagoform.getFech_pago());						
						
						DevolucionForm formulario2 = (DevolucionForm)form;
						
						System.out.println("GENERA BOLETA NC FOLIO==>"+folio[0]);
						
					    if(!folio[0].equals("0")){	
					    	out2 = helper.genera_nota_credito("NOTAC-1", listaPagos,devform,folio[1], session,formulario2);
							if(out2!= null){
								session.setAttribute("DEVFORM1",null);
							}
					    	System.out.println("VentaPedidoDispatchActions ==>"+out2);
						}
					    resb = idoc +"_"+out2;
						//formulario.setEstado_boleta("-1");
						formulario.setEstado_boleta(resb);
																							
						
						helper.ingresaPagoAlbaran(listaPagos, session, formulario, local);
						
						
						formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
						formulario.setIdioma(Constantes.STRING_CAST);
						formulario.setBoleta_guia(formularioAux.getBoleta_guia());
						formulario.setCargado(Constantes.STRING_FALSE);
						formulario.setTipoAlbaran(formularioAux.getTipoAlbaran());				
						formulario.setAgente(agente);
						formulario.setMotivo(formularioAux.getMotivo());
						formulario.setHora(formularioAux.getHora());
						formulario.setFecha(formularioAux.getFecha());
						formulario.setNombreCliente(formularioAux.getNombreCliente());
						formulario.setLista_productos(formularioAux.getLista_productos());
						formulario.setKodak(formularioAux.getKodak());
						formulario.setEntrega(formularioAux.getEntrega());
						
						
					}else{
						System.out.println("No realizo devolucion <========");
						formulario = cargaInicial(formulario, helper, local, session);
						
						formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
						formulario.setIdioma(Constantes.STRING_CAST);
						formulario.setBoleta_guia(formularioAux.getBoleta_guia());
						formulario.setCargado(Constantes.STRING_FALSE);
						formulario.setTipoAlbaran(formularioAux.getTipoAlbaran());				
						formulario.setAgente(agente);
						formulario.setMotivo(formularioAux.getMotivo());
						formulario.setHora(formularioAux.getHora());
						formulario.setFecha(formularioAux.getFecha());
						formulario.setNombreCliente(formularioAux.getNombreCliente());
						formulario.setLista_productos(formularioAux.getLista_productos());
						formulario.setKodak(formularioAux.getKodak());
						formulario.setEntrega(formularioAux.getEntrega());
					}
					
					if(respDevo.isRespuestaDevolucion()){
						formulario.setRespuestaDevolucion("TRUE");
					}else{
						formulario.setRespuestaDevolucion("FALSE");
					}
					
				}else{					
					
					ArrayList<PagoBean> listaPagos =(ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
					session.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, formulario.getTipoAlbaran()	);
					String agentetemporal = session.getAttribute(Constantes.STRING_USUARIO).toString();
					session.setAttribute(Constantes.STRING_USUARIO, formulario.getAgenteSeleccionado());
					boolean respuesta = helper.ingresaPagoAlbaran(listaPagos, session, formulario, local);
					session.setAttribute(Constantes.STRING_USUARIO, agentetemporal);
					formulario.setAccion("traeAlbaranBuscado");
				}
				
				
				
				
				
			}
			if (Constantes.STRING_AGREGAR_CLIENTES.equals(formulario.getAccion())) {
				formulario.setEstaGrabado(2);
				String fecha = formulario.getFecha();
				String agente_selec = formulario.getAgente();
				String motivo_select = formulario.getMotivo();
				String codigo_cliente = formulario.getCodigo_cliente();
				String nombre_cliente = formulario.getNombreCliente();
				String nif_cliente = formulario.getNif();
				String dvnif_cliente = formulario.getDvnif();
				
				
				String nif = formulario.getNif();	
				String clienteagregadoId = formulario.getClienteagregadoId();
				ClienteBean cliente = null;
				boolean respuestaValidaCliente=false;
				
				if(null != clienteagregadoId && !("".equals(clienteagregadoId))){
					cliente = helper.traeClienteSeleccionado(null,clienteagregadoId);
					respuestaValidaCliente=this.validaClienteDevolucion(cliente);
					
				}else{
					cliente = helper.traeClienteSeleccionado(nif,null);
					respuestaValidaCliente=this.validaClienteDevolucion(cliente);
				}
				
				formulario.setValidaCliente(String.valueOf(respuestaValidaCliente));
				
				DevolucionForm formularioAuxiliar = new DevolucionForm();				
				formularioAuxiliar = helper.traeDevolucion(formulario.getNumero_boleta_guia(), formulario.getBoleta_guia(), formularioAuxiliar);				
				cargaInicial(formulario, helper, local, session);
				
				formulario.setFecha(fecha);
				formulario.setAgente(agente_selec);
				formulario.setMotivo(motivo_select);
				
				formulario.setLista_productos(formularioAuxiliar.getLista_productos());
				
				if(respuestaValidaCliente){
					formulario.setCodigo_cliente(cliente.getCodigo());
					formulario.setNif(cliente.getNif());
					formulario.setDvnif(cliente.getDvnif());
					formulario.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
				}else{
					formulario.setCodigo_cliente(codigo_cliente);
					formulario.setNif(nif_cliente);
					formulario.setDvnif(dvnif_cliente);
					formulario.setNombreCliente(nombre_cliente);
				}		
				
				
				//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
				return formulario;
			}
			
			/*
			 * Se encarga de traer el albaran encontrado en la busqueda avanzada.
			 */
			if("traeAlbaranBuscado".equals(formulario.getAccion()) || "traeAlbaranBuscado2".equals(formulario.getAccion())){
				formulario.setEstaGrabado(2);
				formulario = cargaInicial(formulario, helper, local, session);			
				helper.traeAlbaran(formulario, local);			
				SeleccionPagoHelper helperPagos = new SeleccionPagoHelper();
				//Suma del total de los producotos del alabran
				//se reutiliza el metodo sumaTotalValorProductos de venta directa
				VentaDirectaHelper helperVtaDire = new VentaDirectaHelper();
				VentaDirectaForm formVtaDirecta = new VentaDirectaForm();
				
				formVtaDirecta.setListaProductos(formulario.getLista_productos());
				formulario.setSumaTotalAlabaranes(helper.sumaTotalDescuentos(formulario));//para llenar el campo de  total el cual puede ser modifcado en caso de descuento
				formulario.setSumaTotal(helper.sumaTotalValorProductosAlbaran(formulario));//suma total real
				//seteo de los campos para realizar el cobro del albran;
				if("DIRECTA".equals(formulario.getTipo_albaran()) || "DEVOLUCION".equals(formulario.getTipo_albaran())){
					String origen = "ALBARAN_"+formulario.getTipo_albaran();
					formulario.setAlbaranDevolcionPago("");
					if("DEVOLUCION".equals(formulario.getTipo_albaran())){
						formulario.setSumaTotal(formulario.getSumaTotal());
						formulario.setSumaTotalAlabaranes(formulario.getSumaTotalAlabaranes());
						formulario.setAlbaranDevolcionPago("OK");
					}
					session.setAttribute(Constantes.STRING_TOTAL, formulario.getSumaTotalAlabaranes());
					session.setAttribute("suma_total_albaranes", formulario.getSumaTotal());
			    	session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCodigo_cliente());
			    	session.setAttribute(Constantes.STRING_TICKET, formulario.getCdg_venta());
			    	session.setAttribute(Constantes.STRING_DIVISA, formulario.getDivisa()); 
					session.setAttribute(Constantes.STRING_CAMBIO, formulario.getCambio());
					//session.setAttribute(Constantes.STRING_CAJA, formulario.getNumero_caja()); 
					session.setAttribute(Constantes.STRING_TIPO_ALBARAN, formulario.getTipoAlbaran());
					session.setAttribute(Constantes.STRING_ORIGEN, origen);
					session.setAttribute(Constantes.STRING_ESTADO_FORM, Constantes.STRING_BLANCO);
					session.setAttribute(Constantes.STRING_LISTA_PAGOS, null);
					session.setAttribute(Constantes.STRING_FECHA, formulario.getFecha());	
					session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getLista_productos());
					
					String sucursal = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
					String nombre_sucursal = session.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL).toString();
					
					session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, helperVtaDire.traeProductosGratuitos(formulario.getLista_productos() ,nombre_sucursal ,sucursal ));
				}				
				boolean validaCaja = false;
				
				if(!("0".equals(formulario.getEstado_lista_albaran()))){
				
				ArrayList<PagoBean> lista_pagos = helperPagos.traePagos(formulario.getCdg_venta(), "DIRECTA");
				session.setAttribute(Constantes.STRING_LISTA_PAGOS, lista_pagos);
				validaCaja = helper.validaCaja(local, formulario.getFecha());
				
				formulario.setEstadoCaja(String.valueOf(validaCaja));
				
				
				//condicion para determinar si el albaran puede ser eliminado
				if(validaCaja && (lista_pagos==null || lista_pagos.size()==0)){	
					//SE PUEDE ELIMINAR
					formulario.setElimina_albaran("true");
				}else{
					//NO SE PUEDE ELIMINAR
					formulario.setElimina_albaran("false");
				}			
				formulario.setInicio_pagina("");	
				}else{
					formulario.setInicio_pagina("inicio");	
				}
				
				
				//determinar si se deben mostrar los iconos de listar busqueda y lista de pagos y boletas.
				ArrayList<AlbaranBean> listaAlbaranes = (ArrayList<AlbaranBean>)session.getAttribute("lista_albaranes_buscados");				
				if(null != listaAlbaranes && listaAlbaranes.size() > 0  ){
					formulario.setMostrarIconos("true");
				}else{
					formulario.setMostrarIconos("false");
				}	
				
				
				
				if("busqueda_rapida".equals(formulario.getInicio_pagina()) && ("DIRECTA".equals(formulario.getTipo_albaran()) || "DEVOLUCION".equals(formulario.getTipo_albaran()))){
					formulario.setLista_albaranes(helper.buscarAlbaranes(formulario, local));
					if(null != formulario.getLista_albaranes() && formulario.getLista_albaranes().size() == 0){
						formulario.setEstado_lista_albaran("0");					
						}	
					
					session.setAttribute("lista_albaranes_buscados", formulario.getLista_albaranes());
				}
				
				
				formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
				formulario.setIdioma(Constantes.STRING_CAST);
				formulario.setBoleta_guia(Constantes.STRING_B);
				
				if("DIRECTA".equals(formulario.getTipo_albaran()) || "ENTREGA".equals(formulario.getTipo_albaran())){
					formulario.setTipoAlbaran("N");
				}else if("DEVOLUCION".equals(formulario.getTipo_albaran())){
					formulario.setTipoAlbaran("D");
					if(validaCaja){	
						//SE PUEDE DEVOLVER
						formulario.setDevolver_vta("true");
					}else{
						//NO SE PUEDE DEVOLVER
						formulario.setDevolver_vta("false");
					}	
				}
				
				if("traeAlbaranBuscado2".equals(formulario.getAccion())){
					formulario.setAccion("validaArticulosVentaDirecta");
				}
			}
			
			if("eliminaAlbaran".equals(formulario.getAccion())){
				
				formulario.setEstaGrabado(0);
				
				formulario.setEstado_boleta("false");
				String cdg = formulario.getCdg_venta();
				
				SeleccionPagoHelper helperPagos = new SeleccionPagoHelper();
				ArrayList<PagoBean> lista_pagos = helperPagos.traePagos(formulario.getCdg_venta(), "DIRECTA");
				ArrayList<BoletaBean> listaBoletas=helper.traeListaBoletasAlbaranes(formulario.getCdg_venta(), "DIRECTA");				
				if( (lista_pagos != null &&  lista_pagos.size()>0) && (listaBoletas != null &&  listaBoletas.size()>0) ){
					
					formulario.setInicio_pagina("inicio");					
					formulario.setRespuestaEliminaAlbaran("error_pago");
					
				}else{
					boolean respuesta = helper.eliminaAlbaran(cdg, formulario.getFecha(), local, formulario.getTipo_albaran());
					if(respuesta){
						
						this.resetSession(formulario, session);			    	
				    	formulario.cleanForm();
				    	formulario.setEstaGrabado(0);
				    	this.cargaInicial(formulario, helper, local, session);			    	
				    	
						formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
						formulario.setIdioma(Constantes.STRING_CAST);
						formulario.setBoleta_guia(Constantes.STRING_B);		
						session.setAttribute("lista_albaranes_buscados", null);
						formulario.setInicio_pagina("inicio");
						
						formulario.setRespuestaEliminaAlbaran(String.valueOf(respuesta));
						
					}else{
						this.resetSession(formulario, session);			    	
				    	formulario.cleanForm();
				    	formulario.setEstaGrabado(0);
				    	this.cargaInicial(formulario, helper, local, session);			    	
				    	
						formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
						formulario.setIdioma(Constantes.STRING_CAST);
						formulario.setBoleta_guia(Constantes.STRING_B);		
						session.setAttribute("lista_albaranes_buscados", null);
						formulario.setInicio_pagina("inicio");
						formulario.setRespuestaEliminaAlbaran(String.valueOf(respuesta));
					}
					
				}
				
			}
			
			if("retornoPagoAlbaran".equals(formulario.getAccion())){
				
				int total = 0;
				if(null != session.getAttribute(Constantes.STRING_TOTAL)){
					total = Integer.parseInt(session.getAttribute(Constantes.STRING_TOTAL).toString());
				}
				formulario.setSumaTotalAlabaranes(total);
				ArrayList<PagoBean> listaPagos =(ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);
				String agenteTemporal = session.getAttribute(Constantes.STRING_USUARIO).toString();
				session.setAttribute(Constantes.STRING_USUARIO,formulario.getAgenteSeleccionado());
				
				
				//LMARIN NOTA DE CREDITO 20150602
				
				String resnc ="",out1="";				  
				/*resnc = helper.ingresaPago(listaPagos, session, formulario, local);
				String [] folio = resnc.split("_");
				//GENERA BOLETA
				SeleccionPagoForm spagoform = (SeleccionPagoForm)session.getAttribute("SeleccionPagoForm");
				System.out.println("GENERA BOLETA =>"+spagoform.getSerie()+"<=>"+spagoform.getBoleta_cliente()+"<=>Nif() =>"+spagoform.getNif()+"<=> Nombre_cliente() =>"+spagoform.getNombre_cliente()+"<=> Boleta_fecha_ent()=>"+spagoform.getBoleta_fecha_ent()+"<=>FECHA =>"+spagoform.getFecha()+"<=> FPAGO =>"+spagoform.getFech_pago());
				
			    if(folio[0].equals("1")){	
			    	out1 = helper.genera_datos_belec("NC-1", spagoform, folio[1], session);					
			    	System.out.println("VentaPedidoDispatchActions ==>"+out1);
				}
			    resnc = resnc +"_"+out1;*/
			    
			    System.out.println("Devo dispatch acticon ==> "+resnc);
				formulario.setEstado_boleta(resnc);
															
				session.setAttribute(Constantes.STRING_USUARIO,agenteTemporal);
				formulario = cargaInicial(formulario, helper, local, session);
				formulario.setEstaGrabado(0);
				//return mapping.findForward("cargaAlbaranBuscado");	
				return formulario;
			}
			
			if("validaArticulosVentaDirecta".equals(formulario.getAccion())){
				
				BusquedaProductosHelper helperAux = new BusquedaProductosHelper();	
				
				
				if(null != formulario.getLista_productos()){
					
					for(ProductosBean productos:formulario.getLista_productos()){						
						if(null != productos.getProductoMultioferta()){							
													
							//VALIDA QUE LAS CABECERAS DE MULTIOFERTAS TENGAN LINEAS	
							if (helper.validaProductosMultiofertaBD(productos.getProductoMultioferta(), formulario.getCdg_venta(), "A"))
							{																					
								//respuesa satisfactoriade validacion
								formulario.setRespuestaValidaMultiofertas(Constantes.STRING_GENERA_COBRO);
							}
							else
							{
								//formulario.setEstado(Constantes.STRING_VENTA);
								formulario.setRespuestaValidaMultiofertas("La Multioferta "+ productos.getProductoMultioferta().getCod_barra() +" no contiene todos sus articulos, debe generar una nueva venta y cargar correctamente la multioferta");
								//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
								return formulario;
							}
							
							
						}						
					}
					formulario.setRespuestaValidaMultiofertas(Constantes.STRING_GENERA_COBRO);
				}
				
				
			}
			
			
			
		}catch(Exception ex){
			log.error("DevolucionDispatchActions:cargaAlbaran error catch",ex);
		}	
		log.info("DevolucionDispatchActions:cargaAlbaran fin");
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}

	public DevolucionForm cargaBuscarAlbaran(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		formulario.setEstaGrabado(0);
		try{		
			
			formulario.setListaAgentes(helper.traeAgentes(local));						
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		//return mapping.findForward("buscar_devolucion");
		return formulario;
	}
	
	public boolean validaClienteDevolucion(ClienteBean cliente){
		boolean respuesta = true;;
		try{
			
			if (!(null != cliente.getTipo_via() && !("".equals(cliente.getTipo_via())))){
				return false;
			}
			if(!(null != cliente.getNumero() && !("".equals(cliente.getNumero())))){
				return false;
			}
			if(!(null != cliente.getDireccion() && !("".equals(cliente.getDireccion())))){
				return false;
			}
			if(cliente.getProvincia_cliente() == "0"){
				return false;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respuesta;
	}
	
	public DevolucionForm buscarAlbaran(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		
		try{			
			
			formulario.setLista_albaranes(helper.buscarAlbaranes(formulario, local));			
			formulario.setListaAgentes(helper.traeAgentes(local));
			if(null != formulario.getLista_albaranes() && formulario.getLista_albaranes().size() == 0){
				formulario.setEstado_lista_albaran("0");
			}	
			
			session.setAttribute("lista_albaranes_buscados", formulario.getLista_albaranes());
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		
		//return mapping.findForward("buscar_devolucion");
		return formulario;
	}
	
	public DevolucionForm mostrarListaAlbaranes(
			DevolucionForm form, Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		try{
			
			formulario.setLista_albaranes((ArrayList<AlbaranBean>)session.getAttribute("lista_albaranes_buscados"));
			
			if(null == formulario.getLista_albaranes()){
				formulario.setLista_albaranes(new ArrayList<AlbaranBean>());
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//return mapping.findForward("mostrar_lista_albaranes");
		return formulario;
	}
	
	public DevolucionForm generaCobroAlbaran(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		formulario.setEstaGrabado(2);
		try{
			
			session.setAttribute(Constantes.STRING_TOTAL, formulario.getSumaTotalAlabaranes());
	    	session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCodigo_cliente());
	    	session.setAttribute(Constantes.STRING_CLIENTE_VENTA, formulario.getCodigo_cliente());
	    	session.setAttribute(Constantes.STRING_TICKET, formulario.getCdg_venta());
	    	session.setAttribute(Constantes.STRING_DIVISA, formulario.getDivisa()); 
			session.setAttribute(Constantes.STRING_CAMBIO, formulario.getCambio());
			//session.setAttribute(Constantes.STRING_CAJA, formulario.getNumero_caja()); 
			session.setAttribute(Constantes.STRING_TIPO_ALBARAN, formulario.getTipoAlbaran());
			session.setAttribute(Constantes.STRING_ORIGEN, "ALBARAN_DIRECTA");
			session.setAttribute(Constantes.STRING_ESTADO_FORM, Constantes.STRING_BLANCO);
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, null);
			session.setAttribute(Constantes.STRING_FECHA, formulario.getFecha());
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}
	
	
	public DevolucionForm cargaAlbaranBuscado(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		try{
			
			
			formulario = cargaInicial(formulario, helper, local, session);			
			helper.traeAlbaran(formulario, local);			
			SeleccionPagoHelper helperPagos = new SeleccionPagoHelper();
			//Suma del total de los producotos del alabran
			//se reutiliza el metodo sumaTotalValorProductos de venta directa
			VentaDirectaHelper helperVtaDire = new VentaDirectaHelper();
			VentaDirectaForm formVtaDirecta = new VentaDirectaForm();
			formVtaDirecta.setListaProductos(formulario.getLista_productos());
			formulario.setSumaTotalAlabaranes(helperVtaDire.sumaTotalValorProductos(formVtaDirecta));//para llenar el campo de  total el cual puede ser modifcado en caso de descuento
			formulario.setSumaTotal(helper.sumaTotalValorProductosAlbaran(formulario));//suma total real
			//seteo de los campos para realizar el cobro del albran;
			if("DIRECTA".equals(formulario.getTipo_albaran())){
				String origen = "ALBARAN_"+formulario.getTipo_albaran();
				session.setAttribute(Constantes.STRING_TOTAL, formulario.getSumaTotalAlabaranes());
				session.setAttribute("suma_total_albaranes", formulario.getSumaTotal());
		    	session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCodigo_cliente());
		    	session.setAttribute(Constantes.STRING_TICKET, formulario.getCdg_venta());
		    	session.setAttribute(Constantes.STRING_DIVISA, formulario.getDivisa()); 
				session.setAttribute(Constantes.STRING_CAMBIO, formulario.getCambio());
				//session.setAttribute(Constantes.STRING_CAJA, formulario.getNumero_caja()); 
				session.setAttribute(Constantes.STRING_TIPO_ALBARAN, formulario.getTipoAlbaran());
				session.setAttribute(Constantes.STRING_ORIGEN, origen);
				session.setAttribute(Constantes.STRING_ESTADO_FORM, Constantes.STRING_BLANCO);
				session.setAttribute(Constantes.STRING_LISTA_PAGOS, null);
				session.setAttribute(Constantes.STRING_FECHA, formulario.getFecha());	
				session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getLista_productos());
				session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, helperVtaDire.traeProductosGratuitos(formulario.getLista_productos() , session.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL).toString(), session.getAttribute(Constantes.STRING_SUCURSAL).toString()));
			}				
			
			ArrayList<PagoBean> lista_pagos = helperPagos.traePagos(formulario.getCdg_venta(), "DIRECTA");
			session.setAttribute(Constantes.STRING_LISTA_PAGOS, lista_pagos);
			boolean validaCaja = helper.validaCaja(local, formulario.getFecha());
			
			formulario.setEstadoCaja(String.valueOf(validaCaja));
	
			//condicion para determinar si el albaran puede ser eliminado
			if(validaCaja && (lista_pagos==null || lista_pagos.size()==0)){	
				//SE PUEDE ELIMINAR
				formulario.setElimina_albaran("true");
			}else{
				//NO SE PUEDE ELIMINAR
				formulario.setElimina_albaran("false");
			}			
			
			//determinar si se deben mostrar los iconos de listar busqueda y lista de pagos y boletas.
			ArrayList<AlbaranBean> listaAlbaranes = (ArrayList<AlbaranBean>)session.getAttribute("lista_albaranes_buscados");				
			if(null != listaAlbaranes && listaAlbaranes.size() > 0){
				formulario.setMostrarIconos("true");
			}else{
				formulario.setMostrarIconos("false");
			}
			
			
			formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
			formulario.setIdioma(Constantes.STRING_CAST);
			formulario.setBoleta_guia(Constantes.STRING_B);
			
			if("DIRECTA".equals(formulario.getTipo_albaran()) || "ENTREGA".equals(formulario.getTipo_albaran())){
				formulario.setTipoAlbaran("N");
			}else{
				formulario.setTipoAlbaran("D");
			}	
			
			formulario.setRespuestaPagoAlbaran("OKPago");
			formulario.setRespuestaValidaMultiofertas("");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}
	
	
	public DevolucionForm cambioAlbaran(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		String agente = String.valueOf(session.getAttribute(Constantes.STRING_USUARIO));
		try{
			
			String tipoAlbaran = formulario.getTipoAlbaran();
			
			cargaInicial(formulario, helper, local, session);
	    	
			if("D".equals(tipoAlbaran)){
				formulario = helper.traeCodigoDevolucion(local, formulario);
				session.setAttribute("isComntroller", helper.isController(agente));
			}else{
				session.setAttribute("isComntroller", "");
			}
			
			formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
			formulario.setIdioma(Constantes.STRING_CAST);
			formulario.setBoleta_guia(Constantes.STRING_B);
			formulario.setAgente((String) session.getAttribute(Constantes.STRING_USUARIO));
			session.setAttribute("lista_albaranes_buscados", null);
			formulario.setTipoAlbaran(tipoAlbaran);
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}
	
	public DevolucionForm realizaDevolucion(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		String agente = String.valueOf(session.getAttribute(Constantes.STRING_USUARIO));
		try{
			
			DevolucionBean respDevo = helper.realizaDevolucion(formulario, local);
			formulario.cleanForm();
			formulario = cargaInicial(formulario, helper, local, session);
			
			formulario.setCambio(Constantes.STRING_UNO_CERO_CERO);
			formulario.setIdioma(Constantes.STRING_CAST);
			formulario.setBoleta_guia(Constantes.STRING_B);
			formulario.setCargado(Constantes.STRING_FALSE);
				
			if(respDevo.isRespuestaDevolucion()){
				formulario.setRespuestaDevolucion("TRUE");
				formulario.setEstaGrabado(0);
			}else{
				formulario.setRespuestaDevolucion("FALSE");
				formulario.setEstaGrabado(2);
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//return mapping.findForward(Constantes.FORWARD_DEVOLUCION);
		return formulario;
	}


	public boolean validaCajaAjax(DevolucionForm form,
			Session request)
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		Session session = request;
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		String agente = String.valueOf(session.getAttribute(Constantes.STRING_USUARIO));
		boolean validaCaja=false;
		
		try{
			
			//String fecha=request.getParameter("fecha");	
			String fecha= (String) request.getAttribute("fecha");
			validaCaja = helper.validaCaja(local, formulario.getFecha());
			
			HashMap hm = new HashMap();			
			hm.put("respuesta", validaCaja);
			
			//JSONObject json = JSONObject.fromObject(hm);
			//response.setHeader("X-JSON", json.toString());
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//return mapping.findForward(null);
		return validaCaja;
	}
	
	public int validaFechaNC(DevolucionForm form,
			Session request) throws IOException
	{
		DevolucionForm formulario = (DevolucionForm)form;
		DevolucionHelper helper = new DevolucionHelper();
		
		int res = 0;
		res = helper.validarFechaNC(Integer.parseInt(formulario.getNumero_boleta_guia().trim()));
		System.out.println("res ===> "+res);
		//response.getWriter().print(res);
		
		//return mapping.findForward(null);
		return res;
	}
	

}
