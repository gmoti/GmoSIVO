package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.apache.commons.validator.UrlValidator;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;


public class ControllerVisorGuia implements Serializable {
	
	
	private static final long serialVersionUID = -5521605095936321170L;
	private SeleccionPagoForm seleccionPago;
	
	
	@Init
	public void inicial(@ExecutionArgParam("seleccionPago")SeleccionPagoForm pago) {	
		
		seleccionPago = pago;
		
	}


	public SeleccionPagoForm getSeleccionPago() {
		return seleccionPago;
	}


	public void setSeleccionPago(SeleccionPagoForm seleccionPago) {
		this.seleccionPago = seleccionPago;
	}

}
