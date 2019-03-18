package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

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
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.forms.MedicoForm;

public class ControllerMedico implements Serializable {
	
	private static final long serialVersionUID = 8901647515270644102L;
	Session sess = Sessions.getCurrent();
	
	private MedicoForm medicoForm;
	private MedicoDispatchActions medicoDispatchActions;
	
	private String usuario;	
	private String sucursalDes;
	
	private ProvinciaBean provinciaBean;
	
	HashMap<String,Object> objetos;
	
	
	@Init
	public void inicial() {
		
		medicoForm = new MedicoForm();
		medicoDispatchActions = new MedicoDispatchActions();
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		provinciaBean = new ProvinciaBean();
		
		medicoDispatchActions.cargaFormulario(medicoForm, sess);
		
	}

	
	@NotifyChange({"medicoForm","provinciaBean"})
	@Command
	public void ingresaMedico() {   
		
		Optional<String> rut = Optional.ofNullable(medicoForm.getRut());
		Optional<String> nom = Optional.ofNullable(medicoForm.getNombres());
		Optional<String> ape = Optional.ofNullable(medicoForm.getApellidos());
		Optional<String> pro = Optional.ofNullable(provinciaBean.getCodigo());
		
		if(!rut.isPresent() || rut.get().trim().equals("")) {		
			Messagebox.show("Debe ingresar el RUT");
			return;
		}else	
			medicoForm.setRut(rut.get());
		
		if(!nom.isPresent() || nom.get().trim().equals("")) {
			Messagebox.show("Debe ingresar el Nombre del medico");
			return;
		}else
			medicoForm.setNombres(nom.get());
		
		if(!ape.isPresent() || ape.get().trim().equals("")) {
			Messagebox.show("Debe ingresar el Apellido del medico");
			return;
		}else
			medicoForm.setApellidos(ape.get());
		
		if(!pro.isPresent() || pro.get().equals("")) {
			Messagebox.show("Debe ingresar la provincia");
			return;
		}else
			medicoForm.setProvinci(provinciaBean.getCodigo());
			
		
	   	
	   	medicoForm.setAccion(Constantes.STRING_ACTION_INGRESA_MEDICO);
	   	medicoDispatchActions.ingresaMedico(medicoForm, sess);
	   	
	   	if (medicoForm.getExito().equals("0")) {
	   		Messagebox.show("Ingreso de Medico exitoso");
	   	
	   	}else if (medicoForm.getExito().equals("2")){
	   		Messagebox.show("Medico modificado con exito");
	   	}
	   		
	   	else {
	   		Messagebox.show("Problemas al grabar el Medico");
	   	}
	   	
		
	}
	
	
	@NotifyChange({"medicoForm"})
	@Command
	public void buscarMedico() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("retorno", "seleccionaMedico");
		
		Window winBuscarMedico = (Window)Executions.createComponents(
                "/zul/mantenedores/BusquedaMedico.zul", null, objetos);
		
		winBuscarMedico.doModal(); 		
	}
	
	
	@NotifyChange({"medicoForm","provinciaBean"})
	@GlobalCommand
	public void seleccionaMedico(@BindingParam("medico")OftalmologoBean medico) {
		
		medicoForm.setCodigo(medico.getCodigo());
		medicoForm.setRut(medico.getNif());		
		medicoForm.setNombres(medico.getNombre());
		medicoForm.setApellidos(medico.getApelli());		
		medicoForm.setExterno(medico.getExterno());
		medicoForm.setDv(medico.getLnif());
		
		Optional<String> prov = Optional.ofNullable(medico.getProvinci());
		
		if (prov.isPresent()) {
			medicoForm.setProvinci(medico.getProvinci());		
			
			Optional<ProvinciaBean> pb = medicoForm.getListaProvincia().stream().filter(s -> medicoForm.getProvinci().equals(s.getCodigo())).findFirst();
			
			if (pb.isPresent())
			   provinciaBean = pb.get();			
		}
		
		
		//no se utilizan actualmente
		//medicoForm.setEmail(medico.getEmail());
		//medicoForm.setTfno(medico.getTfno());
		//medicoForm.setFax(medico.getFax());
		//medicoForm.setDireccion(medico.getDireccion());		
	}
	
	
	@NotifyChange({"medicoForm"})
	@Command
	public boolean retornaDv() {
		
		int rutAux,m,s = 0;
		char dv;
		String rut = medicoForm.getRut();
		 
		boolean validacion = false;
		try {
			rut =  rut.toUpperCase();
			rut = rut.replace(".", "");
			rut = rut.replace("-", "");
			rutAux = Integer.parseInt(rut);
			 
			dv = rut.charAt(rut.length() - 1);
			 
			m = 0; s = 1;
			
			for (; rutAux != 0; rutAux /= 10) {
				s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}
			
			medicoForm.setDv(String.valueOf((char) (s != 0 ? s + 47 : 75)));
			
			/*if (dv == (char) (s != 0 ? s + 47 : 75)) {
				validacion = true;
				clienteForm.setDv(String.valueOf(dv));
			}*/
		 
		} catch (java.lang.NumberFormatException e) {
		} catch (Exception e) {
		}
		return validacion;
		
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

	public ProvinciaBean getProvinciaBean() {
		return provinciaBean;
	}

	public void setProvinciaBean(ProvinciaBean provinciaBean) {
		this.provinciaBean = provinciaBean;
	}
	
}
