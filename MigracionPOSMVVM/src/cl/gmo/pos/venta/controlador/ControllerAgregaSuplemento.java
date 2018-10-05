package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import cl.gmo.pos.venta.controlador.ventaDirecta.SuplementoDispatchActions;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.SuplementosForm;



public class ControllerAgregaSuplemento implements Serializable {

	private static final long serialVersionUID = 9155273456336143454L;
	
	Session sess = Sessions.getCurrent();
	private SuplementosForm suplementosForm;
	private SuplementoDispatchActions suplementoDispatchActions;
	
	
	@Init
	public void inicial(@ExecutionArgParam("producto")ProductosBean producto,
						@ExecutionArgParam("busquedaProductos")BusquedaProductosForm busquedaProductos) {
		
		suplementoDispatchActions = new SuplementoDispatchActions();
		
		suplementosForm = new SuplementosForm();
		suplementoDispatchActions.carga(suplementosForm, sess);	
	
	}	
	


	//============ Setter and Getter ===============
	
	public SuplementosForm getSuplementosForm() {
		return suplementosForm;
	}

	public void setSuplementosForm(SuplementosForm suplementosForm) {
		this.suplementosForm = suplementosForm;
	}	
	
}
