package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.MedicoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.forms.MedicoForm;

public class ControllerMedico implements Serializable {
	
	private static final long serialVersionUID = 8901647515270644102L;
	Session sess = Sessions.getCurrent();
	
	private MedicoForm medicoForm;
	private MedicoDispatchActions medicoDispatchActions;
	
	private String usuario;	
	private String sucursalDes;
	
	
	@Init
	public void inicial() {
		
		medicoForm = new MedicoForm();
		medicoDispatchActions = new MedicoDispatchActions();
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		medicoDispatchActions.cargaFormulario(medicoForm, sess);
		
	}

	
	@NotifyChange({"medicoForm"})
	@Command
	public void ingresaMedico() {   	
	   	
	   	medicoForm.setAccion("ingresaMedico");
	   	medicoDispatchActions.ingresaMedico(medicoForm, sess);
		
	}
	
	
	@NotifyChange({"medicoForm"})
	@Command
	public void buscarMedico() {
		
		Window winBuscarMedico = (Window)Executions.createComponents(
                "/zul/mantenedores/BusquedaMedico.zul", null, null);
		
		winBuscarMedico.doModal(); 		
	}
	
	
	@NotifyChange({"medicoForm"})
	@GlobalCommand
	public void seleccionaMedico(@BindingParam("medico")OftalmologoBean medico) {
		
		medicoForm.setCodigo(medico.getCodigo());
		medicoForm.setRut(medico.getNif());
		
		medicoForm.setNombres(medico.getNombre());
		medicoForm.setApellidos(medico.getApelli());
		
		medicoForm.setExterno(medico.getExterno());
		
		//no se utilizan actualmente
		//medicoForm.setEmail(medico.getEmail());
		//medicoForm.setTfno(medico.getTfno());
		//medicoForm.setFax(medico.getFax());
		//medicoForm.setDireccion(medico.getDireccion());		
	}
	
	
	@NotifyChange({"medicoForm"})
	@Command
	public void retornaDv() {
		
		int dv = Digito_verificador(medicoForm.getRut());
		
		if(dv == -1){			
			Messagebox.show("Debe ingresar RUT valido");
			return;
		}else{
			medicoForm.setDv(String.valueOf(dv));			
		}
		
	}
	
	private int Digito_verificador(String rut){
		
		
		return 1;
	}

	   


	
	//================ getter and setter ===================
	
	public MedicoForm getMedicoForm() {
		return medicoForm;
	}

	public void setMedicoForm(MedicoForm medicoForm) {
		this.medicoForm = medicoForm;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSucursalDes() {
		return sucursalDes;
	}

	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}
	
}
