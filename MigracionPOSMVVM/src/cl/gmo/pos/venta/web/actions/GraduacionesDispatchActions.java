package cl.gmo.pos.venta.web.actions;

import java.util.ArrayList;
import java.util.HashMap;
//import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;
import cl.gmo.pos.venta.web.helper.BusquedaMedicosHelper;
import cl.gmo.pos.venta.web.helper.GraduacionesHelper;

public class GraduacionesDispatchActions{
	Logger log = Logger.getLogger( this.getClass() );
	public GraduacionesDispatchActions(){}
	
	public GraduacionesForm cargaFormulario(GraduacionesForm form,	Session request)
	{
		log.info("GraduacionesDispatchActions:cargaFormulario  inicio");	
		GraduacionesHelper helper = new GraduacionesHelper();
		GraduacionesForm formulario = (GraduacionesForm)form;
		//HttpSession session = request.getSession();
		Session session = request;//.getSession();
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		formulario.setEstaGrabado(0);		
		try
		{
			if(null != formulario.getNombre() &&  null != formulario.getApellido()){
				formulario.setNombre_cliente(formulario.getNombre()+" "+ formulario.getApellido());
			}
			
			if(0 == formulario.getCliente()){
				ArrayList<GraduacionesBean> listaGraduaciones = new ArrayList<GraduacionesBean>();				
				formulario.setListaGraduaciones(listaGraduaciones);	
				formulario.setOD_cantidad(Constantes.STRING_BLANCO);
				formulario.setOD_base(Constantes.STRING_MENOS_UNO);
				formulario.setOI_cantidad(Constantes.STRING_BLANCO);
				formulario.setOI_base(Constantes.STRING_MENOS_UNO);
				formulario.setTipo(Constantes.STRING_I);
				formulario.setPagina("");
				formulario.setNombre_cliente("");
				formulario.setApellido("");
				formulario.setNombre("");
				
			}else{
				ArrayList<GraduacionesBean> listaGraduaciones = new ArrayList<GraduacionesBean>();				
				formulario.setListaGraduaciones(listaGraduaciones);	
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				helper.traeUltimaGraduacionListaGraduacion(formulario);
				formulario.setExito(Constantes.STRING_ACTION_VER_GRADUACION);
				if(null != formulario.getListaGraduaciones() && formulario.getListaGraduaciones().size()>0){
					formulario.setPagina(Constantes.STRING_NO_GRABAR);
				}else{
					formulario.setPagina("");
				}
				
				if(Constantes.STRING_CERO.equals(formulario.getCerrarPagina())){
					formulario.setCerrarPagina(Constantes.STRING_CERO);
				}else{
					formulario.setCerrarPagina(Constantes.STRING_UNO);
				}
			}
			
			formulario.setListaAgentes(helper.traeAgentes(local));
			//formulario.setListaOftalmologos(helper.traeDoctor());
			formulario.setFecha_graduacion(helper.traeFechaHoyFormateadaString());
			formulario.setListaCantidadOD(helper.traeListaCantidad());
			formulario.setListaBaseOD(helper.traeListaBase());
			formulario.setListaCantidadOI(helper.traeListaCantidad());
			formulario.setListaBaseOI(helper.traeListaBase());
			
		}catch(Exception ex){
			log.error("GraduacionesDispatchActions:cargaFormulario  error catch",ex);
		}
		log.info("GraduacionesDispatchActions:cargaFormulario  fin");
		//return mapping.findForward(Constantes.FORWARD_GRADUACION);
		return formulario;
	}

	public GraduacionesForm IngresaGraduacion(GraduacionesForm form, Session request)
	{
		log.info("GraduacionesDispatchActions:IngresaGraduacion  inicio");
		GraduacionesHelper helper = new GraduacionesHelper();
		GraduacionesForm formulario = (GraduacionesForm)form;
		//HttpSession session = request.getSession();
		Session session = request;//.getSession();
		String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));
		formulario.setEstaGrabado(0);
		try
		{
			
			if(Constantes.STRING_ACTION_VER_GRADUACION.equals(formulario.getAccion())){
				
				formulario = helper.traeGraduacionPedido(formulario);
				formulario.setListaAgentes(helper.traeAgentes(local));
				//formulario.setListaOftalmologos(helper.traeDoctor());
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				formulario.setExito(Constantes.STRING_ACTION_VER_GRADUACION);
				formulario.setPagina(Constantes.STRING_NO_GRABAR);
				formulario.setListaCantidadOD(helper.traeListaCantidad());
				formulario.setListaBaseOD(helper.traeListaBase());
				formulario.setListaCantidadOI(helper.traeListaCantidad());
				formulario.setListaBaseOI(helper.traeListaBase());			
				
				
			}else if(Constantes.STRING_INSERTAR_GRADUACION.equals(formulario.getAccion())){
				boolean respuesta = false;
				respuesta  = helper.ingresaGraduacion(formulario);	
				GraduacionesBean graduacion = helper.traeUltimaGraduacionCliente(String.valueOf(formulario.getCliente()));
				//formulario.cleanForm();
				
				if(respuesta){
					formulario.setExito(Constantes.STRING_TRUE);
				}else{
					formulario.setExito(Constantes.STRING_FALSE);
				}
				
				formulario.setListaAgentes(helper.traeAgentes(local));
				//formulario.setListaOftalmologos(helper.traeDoctor());
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				formulario.setListaCantidadOD(helper.traeListaCantidad());
				formulario.setListaBaseOD(helper.traeListaBase());
				formulario.setListaCantidadOI(helper.traeListaCantidad());
				formulario.setListaBaseOI(helper.traeListaBase());
				formulario.setOD_cantidad(Constantes.STRING_BLANCO);
				formulario.setOD_base(Constantes.STRING_MENOS_UNO);
				formulario.setOI_cantidad(Constantes.STRING_BLANCO);
				formulario.setOI_base(Constantes.STRING_MENOS_UNO);
				formulario.setTipo(Constantes.STRING_I);
				formulario.setEstaGrabado(0);
				
				formulario.setExito(Constantes.STRING_ACTION_VER_GRADUACION);
				formulario.setPagina(Constantes.STRING_NO_GRABAR);
				formulario.setNumero_graduacion(graduacion.getNumero());
				formulario.setFecha_graduacion(graduacion.getFecha());
				
			}else if(Constantes.FORWARD_CONTACTOLOGIA.equals(formulario.getAccion())){
				
				//return mapping.findForward(Constantes.FORWARD_CONTACTOLOGIA);
				return formulario;
				
			}else if("modificarGraduacion".equals(formulario.getAccion())){
				
				boolean respuesta = false;
				respuesta  = helper.modificaGraduacion(formulario);	
				formulario.cleanForm();
				
				if(respuesta){
					formulario.setExito(Constantes.STRING_TRUE);					
				}else{
					formulario.setExito(Constantes.STRING_FALSE);
				}
				
				formulario.setListaAgentes(helper.traeAgentes(local));
				//formulario.setListaOftalmologos(helper.traeDoctor());
				formulario.setListaGraduaciones(helper.traeGraduacionesCliente(String.valueOf(formulario.getCliente())));
				formulario.setListaCantidadOD(helper.traeListaCantidad());
				formulario.setListaBaseOD(helper.traeListaBase());
				formulario.setListaCantidadOI(helper.traeListaCantidad());
				formulario.setListaBaseOI(helper.traeListaBase());
				formulario.setOD_cantidad(Constantes.STRING_BLANCO);
				formulario.setOD_base(Constantes.STRING_MENOS_UNO);
				formulario.setOI_cantidad(Constantes.STRING_BLANCO);
				formulario.setOI_base(Constantes.STRING_MENOS_UNO);
				formulario.setTipo(Constantes.STRING_I);
				formulario.setEstaGrabado(0);
			}
			
		}catch(Exception ex){
			log.error("GraduacionesDispatchActions:IngresaGraduacion  error catch",ex);
		}
		
		//return mapping.findForward(Constantes.FORWARD_GRADUACION);
		return formulario;
	}

	
	public GraduacionesForm buscarDoctorAjax(GraduacionesForm form,	Session request)
	{
		log.info("GraduacionesDispatchActions:cargaFormulario  inicio");	
		GraduacionesForm formulario = (GraduacionesForm)form;
		formulario.setEstaGrabado(2);
		//HttpSession session = request.getSession();
		Session session = request;//.getSession();
		
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));   
    	//String nifdoctor=request.getParameter("nifdoctor");
    	String nifdoctor=request.getAttribute("nifdoctor").toString();
    	Utils helper = new Utils();
    	ArrayList<OftalmologoBean> listaMedicos = null;
    	
    	listaMedicos = helper.traeMedicos(nifdoctor);
    	HashMap hm = new HashMap();
    	
    	if(null != listaMedicos && listaMedicos.size()>0 ){
    		
    		for(OftalmologoBean bean: listaMedicos){
    			hm.put("nifdoctor", bean.getNif());
    			formulario.setNifdoctor(bean.getNif());
    			hm.put("codigodoctor", bean.getCodigo());
    			formulario.setDoctor(bean.getCodigo());
    			hm.put("dvnifdoctor", bean.getLnif());
    			formulario.setDvnifdoctor(bean.getLnif());
    			hm.put("nombredoctor", bean.getNombre() +" "+bean.getApelli());
    			formulario.setNombre_doctor(bean.getNombre() +" "+bean.getApelli());
    			
    		}
    	}else{
    		hm.put("nifdoctor", "");
			hm.put("codigodoctor", "");
			hm.put("dvnifdoctor", "");
			hm.put("nombredoctor", "");
			formulario.setNifdoctor("");
			formulario.setDoctor("");
			formulario.setDvnifdoctor("");
			formulario.setNombre_doctor("");
    	}
    	try{
	    	formulario.setListaAgentes(helper.traeAgentes(local));		
			formulario.setFecha_graduacion(helper.traeFechaHoyFormateadaString());
			formulario.setListaCantidadOD(helper.traeListaCantidad());
			formulario.setListaBaseOD(helper.traeListaBase());
			formulario.setListaCantidadOI(helper.traeListaCantidad());
			formulario.setListaBaseOI(helper.traeListaBase());
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
    	//JSONObject json = JSONObject.fromObject(hm);
		//response.setHeader("X-JSON", json.toString());
		
		log.info("GraduacionesDispatchActions:cargaFormulario  fin");
		//return mapping.findForward(Constantes.FORWARD_GRADUACION);
		return formulario;
	}
}
