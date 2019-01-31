package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;


public class ControllerVisorGuia implements Serializable {
	
	
	private static final long serialVersionUID = -5521605095936321170L;
	private SeleccionPagoForm seleccionPago;
	
	
	@Init
	public void inicial(@ExecutionArgParam("seleccionPago")SeleccionPagoForm pago) {	
		
		seleccionPago = pago;		
	}
	
	@Command
	public void cerrar(@BindingParam("win")Window win) {			
		
		BindUtils.postGlobalCommand(null, null, "numeroDocumento", null);
		
		win.detach();
		System.out.println("en cerrar");
	}
	
	@Command
	public void imprimir(@BindingParam("reporte")Div reporte) {
		
		
	}
	
	
	public SeleccionPagoForm getSeleccionPago() {
		return seleccionPago;
	}


	public void setSeleccionPago(SeleccionPagoForm seleccionPago) {
		this.seleccionPago = seleccionPago;
	}

}
