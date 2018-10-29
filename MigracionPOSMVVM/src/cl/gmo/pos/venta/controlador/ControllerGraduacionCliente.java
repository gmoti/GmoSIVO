package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.GraduacionesDispatchActions;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;

public class ControllerGraduacionCliente implements Serializable{	
	
	private static final long serialVersionUID = 4892447350424455478L;
	Session sess = Sessions.getCurrent();	
	
	private String usuario;	
	private String sucursalDes;
	
	GraduacionesForm graduacionesForm ;
	GraduacionesBean graduacionesBean ;
	BusquedaClientesForm busquedaClientesForm;
	
	GraduacionesDispatchActions graduacionesDispatch; 	
	BusquedaClientesDispatchActions busquedaClientesDispatch;
	
	private Date fechaEmision;
	private Date fechaProxRevision;
	
	HashMap<String,Object> objetos;
	

	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		graduacionesForm = new GraduacionesForm();
		graduacionesBean = new GraduacionesBean();
		busquedaClientesForm = new BusquedaClientesForm();
		
		graduacionesDispatch = new GraduacionesDispatchActions();
		busquedaClientesDispatch = new BusquedaClientesDispatchActions();
		
		graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
		
	}
	
	
	
	@NotifyChange({"graduacionesForm"})
	@GlobalCommand
	public void buscarClienteGraduacion(@BindingParam("cliente")ClienteBean cliente) {
		
		graduacionesForm.setCliente(Integer.parseInt(cliente.getCodigo()));
		graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
		
		graduacionesForm.setNombre_cliente(cliente.getNombre()+ " " + cliente.getApellido());
	}	
	
	
	@Command
	public void ingresoContactologia(){
		
		Window winContactologia = (Window)Executions.createComponents(
                "/zul/mantenedores/Contactologia.zul", null, null);
		
		winContactologia.doModal();	
		
	}
	
		
	@Command
	public void cerrar(@BindingParam("arg1")  Window x) {
	    x.detach();
	}
	
	
	
	//===================================================
	//=======Metodos secundarios ========================
	//===================================================
	@Command
	public void busquedaCliente() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteGraduacion");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	
	
	
	
	
	
	// Getter and Setter ================================
	// ==================================================

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
	
	public GraduacionesForm getGraduacionesForm() {
		return graduacionesForm;
	}
	
	public void setGraduacionesForm(GraduacionesForm graduacionesForm) {
		this.graduacionesForm = graduacionesForm;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaProxRevision() {
		return fechaProxRevision;
	}

	public void setFechaProxRevision(Date fechaProxRevision) {
		this.fechaProxRevision = fechaProxRevision;
	}
	
}
