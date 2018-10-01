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

import cl.gmo.pos.venta.controlador.ventaDirecta.BusquedaProductosDispatchActions;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;

public class ControllerBusquedaProductosMultioferta implements Serializable {

	
	private static final long serialVersionUID = -17555639221404027L;
	Session sess = Sessions.getCurrent();
	
	private BusquedaProductosForm busquedaProductosForm;
	private BusquedaProductosDispatchActions busquedaProductosDispatchActions;
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view, 
						@ExecutionArgParam("busquedaProductos")BusquedaProductosForm BusquedaProductos) {	

		Selectors.wireComponents(view, this, false);
		
		busquedaProductosForm = BusquedaProductos;
		busquedaProductosDispatchActions = new BusquedaProductosDispatchActions();		
		
		try {			
			busquedaProductosForm.setAccion("ped");
			busquedaProductosDispatchActions.busquedaMultiofertaAsoc(busquedaProductosForm, sess);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	@Command
	public void cerrar(@BindingParam("win")Window win) {
		
		win.detach();
	}

	
	
	//========= metodos getter and setter ============
	//================================================
	
	public BusquedaProductosForm getBusquedaProductosForm() {
		return busquedaProductosForm;
	}

	public void setBusquedaProductosForm(BusquedaProductosForm busquedaProductosForm) {
		this.busquedaProductosForm = busquedaProductosForm;
	}	
	
	
}
