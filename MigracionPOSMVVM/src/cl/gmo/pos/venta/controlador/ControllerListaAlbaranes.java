package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;

public class ControllerListaAlbaranes implements Serializable {

	
	private static final long serialVersionUID = 5279530803603818947L;
	Session sess = Sessions.getCurrent();	
	
	private DevolucionForm devolucionForm;
	HashMap<String,Object> objetos;
	
	@Init
	public void inicial(@ExecutionArgParam("devolucion")DevolucionForm devolucion) {
		
		devolucionForm = devolucion;		
		
	}
	
	@NotifyChange({"devolucionForm"})
	@Command
	public void seleccionaAlbaran(@BindingParam("albaran")AlbaranBean albaran, @BindingParam("win")Window win) {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("albaran",albaran);
		
		BindUtils.postGlobalCommand(null, null,"albaranSeleccionado" ,objetos);
		
		win.detach();
		
		
	}	

	public DevolucionForm getDevolucionForm() {
		return devolucionForm;
	}

	public void setDevolucionForm(DevolucionForm devolucionForm) {
		this.devolucionForm = devolucionForm;
	}

}
