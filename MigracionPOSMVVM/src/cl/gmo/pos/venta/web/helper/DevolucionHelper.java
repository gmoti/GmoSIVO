package cl.gmo.pos.venta.web.helper;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;



import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.DevolucionDAOImpl;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.CajaBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaDirectaBean;
import cl.gmo.pos.venta.web.facade.PosDevolucionFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaFacade;
import cl.gmo.pos.venta.web.forms.DevolucionForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;




public class DevolucionHelper extends Utils {
	Logger log = Logger.getLogger( this.getClass() );
	 public DevolucionForm traeDevolucion(String numero, String tipo,DevolucionForm formulario){
		try {
			 
			log.info("DevolucionHelper:traeDevolucion inicio");
			 	DevolucionBean dev = PosDevolucionFacade.traeDevoluciones(numero, tipo);		 	
				
			 	System.out.println("Existe boleta (dev) =====>"+dev.getExisteBoleta());
			 	
			 	dev.getExisteBoleta();
			 	if("false".equals(dev.getExisteBoleta())){
					formulario.setCodigo_cliente(dev.getCodigo_cliente());				
					ClienteBean cliente = traeCliente(null, formulario.getCodigo_cliente());				
					formulario.setNif(cliente.getNif());
					formulario.setDvnif(cliente.getDvnif());				
					formulario.setNombreCliente(dev.getNombreCliente() +" "+ dev.getApellido_cliente());
					formulario.setDireccion(cliente.getDireccion().concat(" - ").concat(cliente.getNumero()));
					formulario.setProvincia(cliente.getProvincia_desc());
					formulario.setCiudad(cliente.getPoblacion());
					formulario.setGiro(cliente.getGiro_desc());	
					formulario.setIdioma(dev.getIdioma());
					formulario.setAgente(dev.getAgente());
					formulario.setDivisa(dev.getDivisa());
					formulario.setDto(dev.getDto());
					formulario.setFormaPago(dev.getFormaPago());
					formulario.setCambio(dev.getCambio());
					formulario.setFacturado(dev.getFacturado());
					formulario.setConvenio(dev.getConvenio());
					formulario.setConfidencial(dev.getConfidencial());
					formulario.setModificado(dev.getModificado());
					formulario.setMontador(dev.getMontador());	
					formulario.setFecha_garantia(dev.getFecha_garantia());
					formulario.setImportePend(dev.getImportePend());
					formulario.setCdg_venta(dev.getCodigo_venta());
					formulario.setExisteBoleta(dev.getExisteBoleta());
					formulario.setFecha_albaran_devolucion(dev.getFecha());
					formulario.setLista_productos(dev.getLista_productos());
					
					
					formulario.setTieneArmCrisContacto(String.valueOf(this.tieneArmCrisContacto(formulario.getLista_productos())));
			 	}else{
			 		formulario.setExisteBoleta(dev.getExisteBoleta());
			 		formulario.setMensajeRetornoSp(dev.getMensajeRetornoSp());
			 	}
			 	
		} catch (Exception e) {
			log.error("DevolucionHelper:traeDevolucion error catch",e);
		}
		
		return formulario;
	 }
	 
	 public boolean tieneArmCrisContacto(ArrayList<ProductosBean> lista_productos){
		 boolean respuesta=false;
		 try{
			 
			 if(null != lista_productos){
				 
				 for(ProductosBean pro : lista_productos){
					 if("M".equals(pro.getFamilia()) || "C".equals(pro.getFamilia()) || ("L".equals(pro.getFamilia()) && !pro.getGrupo().equals("0"))){
						 respuesta = true;
						 break;
					 }
				 }
			 }
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return respuesta;
	 }
	 
	 public ArrayList<AgenteBean> traeAgentes(String local)
	 {
		 log.info("DevolucionHelper:traeDevolucion inicio");
	    return PosUtilesFacade.traeAgentes(local);
	 }
	 
	 public ArrayList<FormaPagoBean> traeFormasPago()
	 {
		 log.info("DevolucionHelper:traeFormasPago inicio");
		return PosUtilesFacade.traeFormasPago();
	 }
	 
	 
	 public ArrayList<ConvenioBean> traeConvenios()
	 {
		 log.info("DevolucionHelper:traeConvenios inicio");
		 return PosUtilesFacade.traeConvenios();
	 }
	 
	 public ArrayList<IdiomaBean> traeIdiomas()
	 {
		 log.info("DevolucionHelper:traeIdiomas inicio");
		 return PosUtilesFacade.traeIdiomas();
	 }
	 
	 public ArrayList<DivisaBean> traeDivisas()
	 {
		 log.info("DevolucionHelper:traeDivisas inicio");
		 return PosUtilesFacade.traeDivisas();
	 }
	 
	 public  ArrayList<TipoAlbaranBean> traeTipoAlbaranes() {
		 log.info("DevolucionHelper:traeTipoAlbaranes inicio");
		 return PosUtilesFacade.traeTipoAlbaranes();
	 }
	 
	 public DevolucionForm  traeCodigoDevolucion(String local, DevolucionForm formulario){
		 log.info("DevolucionHelper:traeCodigoDevolucion inicio");
		 String codigo=Constantes.STRING_BLANCO;
		 try {
			 codigo = PosDevolucionFacade.traeCodigoDevolucion(local);
			 
			 if(null != codigo){
				 String [] codstr = codigo.split(Constantes.STRING_SLASH);
				 formulario.setCodigo1(codstr[0]);				 
				 formulario.setCodigo2(formato_Numero_Ticket(Integer.parseInt(codstr[1])));
				 formulario.setNumero_cab(codstr[1]);			 
			 }
			 
			} catch (Exception e) {
				log.error("DevolucionHelper:traeCodigoDevolucion error catch",e);
			}
		 return formulario;
	 }	 

	 public DevolucionBean  realizaDevolucion(DevolucionForm formulario, String local){
		 log.info("DevolucionHelper:realizaDevolucion inicio");
		 boolean respuesta =false; 
		 DevolucionBean respDevo = new DevolucionBean();
		 try{
			 int numeroBoleta = Integer.parseInt(formulario.getNumero_boleta_guia());
			 int numero_cab = 0;
			 
			 
			 String codigo = PosDevolucionFacade.traeCodigoDevolucion(local);
			 
			 System.out.println("Codigo Devolucion =>"+codigo);
			 
			 if(null != codigo){
				 String [] codstr = codigo.split(Constantes.STRING_SLASH);
				 formulario.setCodigo1(codstr[0]);				 
				 formulario.setCodigo2(formato_Numero_Ticket(Integer.parseInt(codstr[1])));
				 formulario.setNumero_cab(codstr[1]);
				 
			 }
			 
			 if(null != formulario.getNumero_cab() && !("".equals(formulario.getNumero_cab()))){
				 System.out.println("Numero CAB ==>"+formulario.getNumero_cab());
				 numero_cab = Integer.parseInt(formulario.getNumero_cab());
			 }
			 
			 String codigo_cliente=formulario.getCodigo_cliente();
			 respDevo = PosDevolucionFacade.realizaDevolucion(numeroBoleta, formulario.getBoleta_guia(), formulario.getMotivo(), formulario.getFecha(), formulario.getTipoAlbaran(),null, local, formulario.getCodigo1(),numero_cab, codigo_cliente, formulario.getAgenteSeleccionado());
			 System.out.println("PosDevolucionFacade.realizaDevolucion ==> "+numeroBoleta+","+ formulario.getBoleta_guia()+","+ formulario.getMotivo()+","+ formulario.getFecha()+","+ formulario.getTipoAlbaran()+","+null+","+ local+","+ formulario.getCodigo1()+","+numero_cab+","+ codigo_cliente+","+ formulario.getAgenteSeleccionado());
			 formulario.setCdg_venta(formulario.getCodigo1()+"/"+formulario.getCodigo2());
			 
		 }catch(Exception ex){
			 log.error("DevolucionHelper:realizaDevolucion error catch",ex);
			 respuesta =false; 
		 }
		 return respDevo;
	 }

	 public ArrayList<AlbaranBean> buscarAlbaranes(DevolucionForm formulario, String local){
		 
		 ArrayList<AlbaranBean> lista_albaranes = new ArrayList<AlbaranBean>();
		 try{
			 
			 String codigo1 =(formulario.getCodigo1()!= null && !("".equals(formulario.getCodigo1())))?formulario.getCodigo1():null;
			 String codigo2 = (formulario.getCodigo2()!= null && !("".equals(formulario.getCodigo2())))?formulario.getCodigo2():null;
			 String nif = (formulario.getNif() != null && !("".equals(formulario.getNif())))?formulario.getNif():null;
			 String fecha = (formulario.getFecha() != null && !("".equals(formulario.getFecha())))?formulario.getFecha():null;
			 String agente = (formulario.getAgente() != null && !("0".equals(formulario.getAgente())))?formulario.getAgente():null;
			 String cdg=null;
			 if(null != codigo1 && null != codigo2){
				 cdg = codigo1 +"/"+ codigo2;
			 }	 
			 lista_albaranes = PosDevolucionFacade.buscarAlbaranes(cdg, nif, fecha, agente, local);
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return lista_albaranes;
	 }
	 
	 public DevolucionForm traeAlbaran(DevolucionForm formulario, String local){
		 
		 String cdg = formulario.getCdg_venta();
		 String fecha = formulario.getFecha();
		 String agente = formulario.getAgente();
		 AlbaranBean alb = null;
		 try{
			 
			 	alb = PosDevolucionFacade.traeAlbaran(cdg, fecha, agente, local);
			 	
			 	if(null != alb){
				 	formulario.setCodigo_cliente(alb.getCliente());
					formulario.setNombreCliente(alb.getNombrecli() + " " +alb.getApellidocli());
					
					formulario.setIdioma(alb.getIdioma());
					formulario.setAgente(alb.getAgente_albaran());
					formulario.setDivisa(alb.getDivisa());
					formulario.setDto(""+alb.getDto());
					formulario.setFormaPago(alb.getFpago());
					formulario.setCambio(""+alb.getCambio());
					formulario.setFacturado(alb.getFacturado());
					formulario.setConvenio(alb.getConvenio());
					formulario.setConfidencial(alb.getConfidencial());
					formulario.setModificado(alb.getModificado());
					formulario.setMontador(alb.getMontador());	
					formulario.setFecha_garantia(alb.getFecgarant());
					formulario.setImportePend(alb.getImportport());
					formulario.setCdg_venta(alb.getCodigo_albaran());
					if(null != alb.getTipomotdev()){
						formulario.setMotivo(alb.getTipomotdev());
					}else{
						formulario.setMotivo("0");
					}
					
					
					if(null != alb.getCodigo_albaran() && !"".equals(alb.getCodigo_albaran())){
						String [] vec = alb.getCodigo_albaran().split("/");
						if(vec.length==2){
							formulario.setCodigo1(vec[0]);
							formulario.setCodigo2(vec[1]);
						}
					}
					
					formulario.setFecha(alb.getFecha_albaran());
					formulario.setHora(alb.getHora_albaran());
					formulario.setNif(alb.getNif_cliente());
					formulario.setDvnif(alb.getDv_nif());
					formulario.setTipo_albaran(alb.getTipo_albaran());			
					
					formulario.setLista_productos(alb.getLista_productos_albaran());
					
			 	}else{
			 		formulario.setEstado_lista_albaran("0");
			 	}
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return formulario;
	 }	 
	 
	 public String ingresaPago(ArrayList<PagoBean> listaPago, HttpSession session, DevolucionForm formulario, String local)
	 {
			log.info("VentaDirectaHelper:ingresaPago inicio");
	    	boolean estado = false;
	    	String devolucion = formulario.getTipoAlbaran();
	    	String tipo_doc = session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString();
	    	String res ="";
	    	
	    	
	    	try {
	    		VentaDirectaBean vent = PosVentaFacade.traeNumerosCaja(local);
	    		CajaBean caja = null;
	    		if(null != vent){
	    			caja = vent.getListaCajas().get(0);
	    		}else{
	    			caja = new CajaBean();
	    		}
	    		
	    		Utils util= new Utils();
	    		util.isEntero(1.0);
	    		for (PagoBean pago : listaPago) {
	    			log.info("VentaDirectaHelper:ingresaPago entrando ciclo for");
	    			PosVentaFacade.insertaPagoAlbaran(formulario.getCdg_venta(),
														pago.getForma_pago(),
														pago.getCantidad(),
														pago.getFecha(),
														formulario.getDivisa(), 
														util.convertirEntero(formulario.getCambio()), 
														caja.getCodigo(), 
														pago.getCantidad(),
														devolucion,
														Constantes.STRING_N,
														session.getAttribute(Constantes.STRING_USUARIO).toString(),
														null,
														pago.getDescuento(),
														tipo_doc);
				}
	    		VentaDirectaHelper ventaHelper = new VentaDirectaHelper();
	    		
	    		//NOTA DE CREDITO LMARIN 20150602
	    		res = ventaHelper.ingresaDocumento(formulario.getCdg_venta(),
						Integer.parseInt(session.getAttribute(Constantes.STRING_DOCUMENTO).toString()),
						"B",
						formulario.getSumaTotalAlabaranes(),
						formulario.getFecha(),session.getAttribute(Constantes.STRING_SUCURSAL).toString());
	    		
				
			} catch (Exception e) {
				log.error("VentaDirectaHelper:ingresaPago error catch",e);
			}
	    	return res;
		}
	 
	 public boolean ingresaPagoAlbaran(ArrayList<PagoBean> listaPago, HttpSession session, DevolucionForm formulario, String local)
	 {
			log.info("VentaDirectaHelper:ingresaPago inicio");
	    	boolean estado = false;
	    	String devolucion = formulario.getTipoAlbaran();
	    	String tipo_doc = session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString();
	    	
	    	if("D".equalsIgnoreCase(devolucion)){
	    		devolucion = "S";
	    	}
	    	
	    	
	    	try {
	    		VentaDirectaBean vent = PosVentaFacade.traeNumerosCaja(local);
	    		CajaBean caja = null;
	    		if(null != vent){
	    			caja = vent.getListaCajas().get(0);
	    		}else{
	    			caja = new CajaBean();
	    		}
	    		
	    		Utils util= new Utils();
	    		util.isEntero(1.0);
	    		for (PagoBean pago : listaPago) {
	    			log.info("VentaDirectaHelper:ingresaPago entrando ciclo for");
	    			pago.setCod_venta(formulario.getCodigo1()+"/"+formulario.getCodigo2());
	    			PosVentaFacade.insertaPagoAlbaran(formulario.getCdg_venta(),
														pago.getForma_pago(),
														pago.getCantidad(),
														pago.getFecha(),//cambiar fecha pago
														formulario.getDivisa(), 
														util.convertirEntero(formulario.getCambio()), 
														caja.getCodigo(), 
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
	 public int sumaTotalValorProductosAlbaran(DevolucionForm ventaForm){
	      log.info("VentaDirectaHelper:sumaTotalValorProductos inicio");
		  int precioFinalProductos = Constantes.INT_CERO;
		  if (null != ventaForm.getLista_productos()) {
			  for(ProductosBean precioProducto:ventaForm.getLista_productos()){
				  log.info("VentaDirectaHelper:sumaTotalValorProductos entrando ciclo for");
				  precioFinalProductos = (precioProducto.getPrecio_costo())+ precioFinalProductos;
			  }
			  return precioFinalProductos;
		  }else
		  {
			  return 0;
		  }
		  
	    }	 
	 
	 public int sumaTotalDescuentos(DevolucionForm ventaForm){
	      log.info("VentaDirectaHelper:sumaTotalValorProductos inicio");
		  int precioFinalProductos = Constantes.INT_CERO;
		  if (null != ventaForm.getLista_productos()) {
			  for(ProductosBean precioProducto:ventaForm.getLista_productos()){
				  log.info("VentaDirectaHelper:sumaTotalValorProductos entrando ciclo for");
				  precioFinalProductos = precioProducto.getPrecio_costo()+ precioFinalProductos;
			  }
			  return precioFinalProductos;
		  }else
		  {
			  return 0;
		  }	
		  
	    }

	public void validaAutorizacionKodak(DevolucionForm formulario) {
		log.info("VentaDirectaHelper:validaAutorizacionKodak inicio");
			if (PosDevolucionFacade.ValidaAutorizacionKodak(formulario.getUsuario())) {
				formulario.setAutorizacionKodak(Constantes.STRING_TRUE);
			}
			else
			{
				formulario.setAutorizacionKodak(Constantes.STRING_FALSE);
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
			System.out.println("Boleta devo ====>===>==>=> "+res);
		} catch (IOException e) {			
			System.out.println("IOEXCEPCION  20141219 => "+ e.getMessage());
		} catch (Exception e) {
			System.out.println("EXCEPCION  20141219 => "+ e.getMessage());
		}
		return res ;
	} 
	
	
	//LMARIN NOTA DE CREDITO 20150604
	
	public String genera_nota_credito(String tipodoc,ArrayList<PagoBean> listaPagos,DevolucionForm devform,String foliocl,HttpSession session,DevolucionForm formulario2){
				
		Utils util = new Utils();
		String res = null;
		String folio = foliocl;
		System.out.println("FOLIO DEVOLUCION HELPER ===> "+folio);
		
		try {					
			res = util.genera_notacredito(tipodoc,folio,listaPagos,devform,session,formulario2);
			System.out.println("Boleta devo ====>===>==>=> "+res);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("EXCEPCION DEVOLUCION HELPER  20141219 => "+ e.getMessage());
		}
		return res ;
	} 
	
	
	public  String insertaDocumento(String ticket, int documento, String tipo_documento,
			int total, String fecha,String local) 
	{
		 String res ="";
		 System.out.println("DEVOLUCION HELPER insertaDocumento  insertaDocumento");
		 DevolucionDAOImpl dev = new DevolucionDAOImpl();
		 try{
			 res = dev.insertaDocumento(ticket, documento, tipo_documento, total,fecha, local);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return res;	
	}
	
	//LMARIN 20180209
	public  int validarFechaNC(int numdevo) 
	{
		 int res =0;
		 DevolucionDAOImpl dev = new DevolucionDAOImpl();
		 try{
			 res = dev.validarFechaNC(numdevo);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return res;	
	}
	
	 
}
