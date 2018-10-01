package cl.gmo.pos.venta.controlador.general;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.BusquedaMedicoForm;
import cl.gmo.pos.venta.web.helper.BusquedaMedicosHelper;

public class BusquedaMedicosActions {

	Logger log = Logger.getLogger( this.getClass() );
	public BusquedaMedicoForm buscar(BusquedaMedicoForm form,
			Session request) 
	{
		log.info("BusquedaMedicosActions:buscar inicio");
		BusquedaMedicoForm formulario = (BusquedaMedicoForm)form;
		formulario.setNif(formulario.getNif());
		log.info("BusquedaMedicosActions:buscar fin");
		//return mapping.findForward(Constantes.STRING_ACTION_BUSCAR_MEDICO);
		return formulario;
	}
	
	public BusquedaMedicoForm buscarMedico(BusquedaMedicoForm form,
			Session request) 
	{
		log.info("BusquedaMedicosActions:buscarMedico inicio");
		BusquedaMedicoForm formulario = (BusquedaMedicoForm)form;
		BusquedaMedicosHelper helper = new BusquedaMedicosHelper();
		formulario.setError(Constantes.STRING_ERROR);
		try{
			
			if(Constantes.STRING_ACTION_BUSQUEDA_MEDICO.equals(formulario.getAccion())){
				if (helper.valida_campos(formulario)) {
					formulario.setListaOftalmologos(helper.traeMedicos(formulario));
				}
				
				
			}			
			
		}catch(Exception ex){
			log.error("BusquedaMedicosActions:buscarMedico error catch",ex);
		}
		
		log.info("BusquedaMedicosActions:buscarMedico fin");
		//return mapping.findForward(Constantes.STRING_ACTION_BUSCAR_MEDICO);
		return formulario;
	} 
	
}
