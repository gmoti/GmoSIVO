package cl.gmo.pos.venta.controlador.presupuesto;

import java.util.HashMap;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.controlador.BeanGlobal;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;
import cl.gmo.pos.venta.web.helper.BusquedaConveniosHelper;

public class BusquedaConveniosDispatchActions{
	Logger log = Logger.getLogger(this.getClass());
	BusquedaConveniosHelper helper = new BusquedaConveniosHelper();
	public BusquedaConveniosDispatchActions() {
	}

	public void cargaBusquedaConvenios (BusquedaConveniosForm form, Session request) {
		log.info("BusquedaConveniosDispatchActions:cargaBusquedaConvenios inicio");
		BusquedaConveniosForm formulario = (BusquedaConveniosForm)form;
		
		formulario.cleanForm();
		log.info("BusquedaConveniosDispatchActions:cargaBusquedaConvenios fin");
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
		return;
	}
	
	public void buscar(BusquedaConveniosForm form, Session request) {
		log.info("BusquedaConveniosDispatchActions:buscar inicio");
		BusquedaConveniosForm formulario = (BusquedaConveniosForm)form;
		
		if (Constantes.STRING_BUSCAR.equals(formulario.getAccion())) {
			formulario.setLista_convenios(helper.traeConvenios(formulario));
		}
			
		log.info("BusquedaConveniosDispatchActions:buscar fin");
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
		return;
	}
	
	public void selecciona_convenio(BusquedaConveniosForm form, Session request) {
		
		log.info("BusquedaConveniosDispatchActions:selecciona_convenio inicio");
		BusquedaConveniosForm formulario = (BusquedaConveniosForm)form;
		
		if (Constantes.STRING_DESPLIEGA_FAMILIAS.equals(formulario.getAccion())) {
			helper.traeConveniolnFamilias(formulario);
		}
		else
		{
			//String indice = request.getParameter("indice").toString();
			String indice = request.getAttribute("indice").toString();
			ConvenioBean convenio = formulario.getLista_convenios().get(Integer.parseInt(indice));
			formulario.setSel_convenio(convenio.getId());
			formulario.setSel_convenio_det(convenio.getDescripcion());
			helper.traeDescuentosConvenio(formulario, convenio.getId());
		}
		
		log.info("BusquedaConveniosDispatchActions:selecciona_convenio fin");
		//return mapping.findForward(Constantes.FORWARD_SELECCION);
		return;
	}
	
	public BeanGlobal buscarConvenioAjax(BusquedaConveniosForm form, Session request) {
		log.info("BusquedaConveniosDispatchActions:buscarConvenioAjax inicio");
		
		BeanGlobal global = new BeanGlobal();
		
		global.setObj_1(Constantes.STRING_BLANCO);
		global.setObj_2(Constantes.STRING_BLANCO);
		global.setObj_3(Constantes.STRING_BLANCO);
		
		BusquedaConveniosForm formulario = (BusquedaConveniosForm)form;
		//String cdg = request.getParameter("convenio");
		String cdg = request.getAttribute("convenio").toString();
		
		formulario.setCodigo(cdg);
		formulario.setLista_convenios(helper.traeConvenios(formulario));
		ConvenioBean convenio = null;
		
		if (null != formulario.getLista_convenios() && formulario.getLista_convenios().size()>0) {			
			convenio = formulario.getLista_convenios().get(Constantes.INT_CERO);
		}
		
		HashMap<String, String> hm = new HashMap<String, String>();
		if (null != convenio) {
			hm.put("descripcion", convenio.getDescripcion().replace("°", " "));
			hm.put("cdg", convenio.getId());
			hm.put("isapre", convenio.getIsapre());
			
			global.setObj_1(convenio.getDescripcion().replace("°", " "));
			global.setObj_2(convenio.getId());
			global.setObj_3(convenio.getIsapre());			
		}
		
		//JSONObject obj_convenio = JSONObject.fromObject(hm);
		//response.setHeader("X-JSON", obj_convenio.toString());
		
		log.info("BusquedaConveniosDispatchActions:buscarConvenioAjax fin");
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);	
		return global;
	}
	
	public BusquedaConveniosForm selecciona_convenio_cdg(BusquedaConveniosForm form, Session request) {
		log.info("BusquedaConveniosDispatchActions:selecciona_convenio_cdg inicio inicio");
		
		BusquedaConveniosForm formulario = (BusquedaConveniosForm)form;

		if (Constantes.STRING_DESPLIEGA_FAMILIAS.equals(formulario.getAccion())) {
			helper.traeConveniolnFamilias(formulario);
		}
		else
		{
			//String id = request.getParameter("convenio").toString();
			String id = request.getAttribute("convenio").toString();			
			formulario.setSel_convenio(id);
			helper.traeDescuentosConvenio(formulario, id);
		}

		log.info("BusquedaConveniosDispatchActions:selecciona_convenio_cdg fin");
		//return mapping.findForward(Constantes.FORWARD_SELECCION);
		return formulario;
	}
	
	
}
