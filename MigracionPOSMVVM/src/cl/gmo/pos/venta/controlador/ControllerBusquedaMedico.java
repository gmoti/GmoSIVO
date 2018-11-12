package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaMedicosActions;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.forms.BusquedaMedicoForm;


public class ControllerBusquedaMedico implements Serializable {
	
	private static final long serialVersionUID = 2040298264375078846L;
	Session sess = Sessions.getCurrent();
	
	private BusquedaMedicoForm busquedaMedicoForm;
	private BusquedaMedicosActions busquedaMedicosActions; 
	private String retorno;
	
	
	@Init
	public void inicial(@ExecutionArgParam("retorno")String ret) {
		
		busquedaMedicoForm = new BusquedaMedicoForm();
		busquedaMedicosActions = new BusquedaMedicosActions();		
		
		busquedaMedicoForm.setNif("");
		busquedaMedicoForm.setNombre("");
		busquedaMedicoForm.setApellido("");
		
		retorno=ret;
	}
	
	
	@NotifyChange({"busquedaMedicoForm"})
	@Command
	public void buscar() {
		
		busquedaMedicoForm.setAccion("busquedaMedicos");
		busquedaMedicosActions.buscarMedico(busquedaMedicoForm, sess);
	}
	
	@Command
	public void medicoSeleccionado(@BindingParam("medico")OftalmologoBean medico, @BindingParam("win")Window win) {
		
		HashMap<String,Object> objetos;
		
		objetos = new HashMap<String,Object>();
		objetos.put("medico",medico);
		
		BindUtils.postGlobalCommand(null, null, retorno, objetos);		
		win.detach();
	}
	
	
	//========= Getter and Setter ================

	public BusquedaMedicoForm getBusquedaMedicoForm() {
		return busquedaMedicoForm;
	}


	public void setBusquedaMedicoForm(BusquedaMedicoForm busquedaMedicoForm) {
		this.busquedaMedicoForm = busquedaMedicoForm;
	}
	
	
	

}
