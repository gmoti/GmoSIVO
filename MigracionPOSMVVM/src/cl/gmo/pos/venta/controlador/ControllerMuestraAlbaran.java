package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import cl.gmo.pos.venta.web.forms.DevolucionForm;


public class ControllerMuestraAlbaran implements Serializable {
	
	private static final long serialVersionUID = -5044145029742215965L;
	
	private DevolucionForm 	devolucionForm;
	
	
	@Init
	public void inicial(@ExecutionArgParam("devolucionForm")DevolucionForm devolucion) {	

		devolucionForm 	= devolucion;		
	
	}

	
	//=========== Getter and Setter ===========

	public DevolucionForm getDevolucionForm() {
		return devolucionForm;
	}


	public void setDevolucionForm(DevolucionForm devolucionForm) {
		this.devolucionForm = devolucionForm;
	}
	
	
}
