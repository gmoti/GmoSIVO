package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;



public class ControllerMostrarPagosBoleta implements Serializable {
	
	private static final long serialVersionUID = 6125242362315412759L;
	Session sess = Sessions.getCurrent();
	
	private SeleccionPagoForm seleccionPagoForm;
	private SeleccionPagoDispatchActions seleccionPagoDispatchActions;
	
	
	@Init
	public void inicial(@ExecutionArgParam("seleccionPago")SeleccionPagoForm arg) {	
		
		seleccionPagoForm = new SeleccionPagoForm();
		seleccionPagoForm = arg;
		seleccionPagoDispatchActions = new SeleccionPagoDispatchActions();
		
		
		try {
			
			seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

	
	//============  metodos getter and setter ===============

	public SeleccionPagoForm getSeleccionPagoForm() {
		return seleccionPagoForm;
	}

	public void setSeleccionPagoForm(SeleccionPagoForm seleccionPagoForm) {
		this.seleccionPagoForm = seleccionPagoForm;
	}
	

}
