package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.presupuesto.PresupuestoDispatchActions;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;


public class ControllerBusquedaPresupuesto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9094708405939416646L;



	Session sess = Sessions.getCurrent();
		
	private PresupuestoForm presupuesto;
	private PresupuestoDispatchActions presupuestoDispatchActions;	
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view, 
						@ExecutionArgParam("presupuestoForm")PresupuestoForm arg) {
		
		    Selectors.wireComponents(view, this, false);
		   
		    presupuestoDispatchActions = new PresupuestoDispatchActions();
		    presupuesto = new PresupuestoForm();
		    presupuesto = (PresupuestoForm)arg; 			    
		    presupuesto = presupuestoDispatchActions.cargaPresupuestos(presupuesto, sess);	    
	}
	
	
	@Command
	public void seleccionaProducto(@BindingParam("win")Window win) {		
		win.detach();		
	}

	
	//getter and setter

	public PresupuestoForm getPresupuesto() {
		return presupuesto;
	}


	public void setPresupuesto(PresupuestoForm presupuesto) {
		this.presupuesto = presupuesto;
	}
	
}
