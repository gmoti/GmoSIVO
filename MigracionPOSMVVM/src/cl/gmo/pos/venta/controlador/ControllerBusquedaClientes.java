package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;

public class ControllerBusquedaClientes implements Serializable {
	
	Logger log = Logger.getLogger( this.getClass() );	
	private static final long serialVersionUID = 8803202876578131940L;
	Session sess = Sessions.getCurrent();
	
	private String usuario;	
	private String sucursalDes;
	HashMap<String,Object> objetos;
	private String retorno="";
	
	private BusquedaClientesForm busquedaClientesForm;
	private BusquedaClientesDispatchActions busquedaClientesDispatch;
	
	
	@Init
	public void inicial(@ExecutionArgParam("retorno")String arg) {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		retorno=arg;
		
		busquedaClientesForm = new BusquedaClientesForm();
		busquedaClientesDispatch = new BusquedaClientesDispatchActions();		
		
	}

	
	@NotifyChange({"busquedaClientesForm"})
	@Command
	public void buscarCliente() {
		
		busquedaClientesForm.setApellido(busquedaClientesForm.getApellido().toUpperCase());
		busquedaClientesForm.setNombre(busquedaClientesForm.getNombre().toUpperCase());
		
		busquedaClientesForm.setAccion("busqueda");
		busquedaClientesDispatch.buscar(busquedaClientesForm, sess);
		
	}
	
	
	@Command
	public void retornoSeleccion(@BindingParam("cliente")ClienteBean cliente, @BindingParam("win")Window win) {
		
		
		objetos = new HashMap<String,Object>();		
		objetos.put("cliente",cliente);
		
		BindUtils.postGlobalCommand(null, null, retorno, objetos);
		win.detach();
		
	}
	
	
	
	
	//Getter and Setter ==================
	//====================================

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

	public BusquedaClientesForm getBusquedaClientesForm() {
		return busquedaClientesForm;
	}

	public void setBusquedaClientesForm(BusquedaClientesForm busquedaClientesForm) {
		this.busquedaClientesForm = busquedaClientesForm;
	}	
	

}
