package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.presupuesto.BusquedaConveniosDispatchActions;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;


public class ControllerBusquedaConvenio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8233031087916039002L;
	Session sess = Sessions.getCurrent();
	
	private BusquedaConveniosForm busquedaConveniosForm;
	private BusquedaConveniosDispatchActions busquedaConveniosDispatchActions;
	
	private String pVentana;
	private String pOrigen;
	
	HashMap<String,Object> objetos;
	
	@Wire
	private Window winBusquedaConvenio;
	
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view,
						@ExecutionArgParam("busquedaConvenios")BusquedaConveniosForm arg,
						@ExecutionArgParam("ventana")String arg2,
						@ExecutionArgParam("origen")String arg3	) {
		
		Selectors.wireComponents(view, this, false);
            
		busquedaConveniosForm = new BusquedaConveniosForm();
		busquedaConveniosForm = arg;
		pVentana=arg2;
		pOrigen=arg3;
		busquedaConveniosDispatchActions = new BusquedaConveniosDispatchActions();		
		busquedaConveniosDispatchActions.cargaBusquedaConvenios(busquedaConveniosForm, sess);           
	}	
	
	
	@NotifyChange({"busquedaConveniosForm"})
	@Command
	public void buscarConvenios() {
		
		busquedaConveniosForm.setAccion("buscar");
		busquedaConveniosDispatchActions.buscar(busquedaConveniosForm, sess);		
	}
	
	
	@NotifyChange({"busquedaConveniosForm"})
	@Command
	public void seleccionaConvenio(@BindingParam("arg")ConvenioBean convenio) {
		
		int index=0;
		objetos = new HashMap<String,Object>();		
		
		
		for(ConvenioBean cb : busquedaConveniosForm.getLista_convenios()) {			
			if(cb.getId().equals(convenio.getId())) {
				break;
			}
			index++;
		}
		
		sess.setAttribute("indice", index);	
		//busquedaConveniosDispatchActions.selecciona_convenio(busquedaConveniosForm, sess);
		objetos.put("busquedaConvenios",busquedaConveniosForm);
		objetos.put("ventana",pVentana);
		objetos.put("origen",pOrigen);
		objetos.put("win",winBusquedaConvenio);
		
		//se llama ventana convenio
		Window window = (Window)Executions.createComponents(
                "/zul/presupuestos/SeleccionaConvenio.zul", null, objetos);		
        window.doModal();	
		
	}
	
	
	
	
	//=============== getter and setter =================
	
	public BusquedaConveniosForm getBusquedaConveniosForm() {
		return busquedaConveniosForm;
	}

	public void setBusquedaConveniosForm(BusquedaConveniosForm busquedaConveniosForm) {
		this.busquedaConveniosForm = busquedaConveniosForm;
	}        
	
	

}
