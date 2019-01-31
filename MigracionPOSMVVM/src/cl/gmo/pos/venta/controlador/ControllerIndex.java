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

import cl.gmo.pos.venta.controlador.general.LoginActions;
import cl.gmo.pos.venta.controlador.general.MenuDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.SucursalesBean;
import cl.gmo.pos.venta.web.forms.MenuForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.UsuarioForm;


public class ControllerIndex implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5375817743395813578L;	
	
	Session sess;	
	private UsuarioForm usuarioForm;
	private LoginActions loginActions;
	private MenuForm menuForm;
	private MenuDispatchActions menuDispatchActions;
	private String divVisibleLogin;
	private String divVisibleIndex;
	private SucursalesBean sucursalesBean;
	
	
	@Init
	public void inicio() {
		sess = Sessions.getCurrent();
		
		sucursalesBean = new SucursalesBean();
		usuarioForm = new UsuarioForm();
		loginActions= new LoginActions();
		menuForm    = new MenuForm();
		menuDispatchActions = new MenuDispatchActions();
		menuDispatchActions.cargaSucursal(menuForm, sess);
		divVisibleLogin="true";
		divVisibleIndex="false";
	}

	
	@NotifyChange({"usuarioForm","menuForm","divVisibleLogin","divVisibleIndex"})
	@Command
	public void validar() {
		
		String result;
		
		result = loginActions.validaLogin(usuarioForm, sess);
		
		if (!result.equals(Constantes.STRING_FALLA)) {	
						
			divVisibleLogin="false";
			divVisibleIndex="true";			
			
			sess.setAttribute(Constantes.STRING_USUARIO, usuarioForm.getNombreUsuario());
			sess.setAttribute(Constantes.STRING_DESC_USUARIO, usuarioForm.getDescNombreUsuario());
			
		}else {
			
			Messagebox.show("Invalido");
		}		
		
	}
	
	
	@NotifyChange({"sucursalesBean","menuForm","divVisibleLogin","divVisibleIndex"})
	@Command
	public void seleccionaSucursal() {
		
		String result;
		
		if(!sucursalesBean.equals(null)) {
			
			menuForm.setCodigoSucursal(sucursalesBean.getSucursal());
			result = menuDispatchActions.validaSucursal(menuForm, sess);
			
			if (result.equals(Constantes.FORWARD_SUCCESS)) {
				
				sess.setAttribute(Constantes.STRING_SUCURSAL, sucursalesBean.getSucursal());
				sess.setAttribute(Constantes.STRING_NOMBRE_SUCURSAL, sucursalesBean.getDescripcion());
				// 
				Window window = (Window)Executions.createComponents(
		                "/zul/MenuPrincipal.zul", null, null);
				
		        window.doModal();
		    }else {
		    	
		    	divVisibleLogin="true";
				divVisibleIndex="false";
		    }			
			
		}else {
			Messagebox.show("Sucursal es requerida");
		}
		
		
	}
	
	@NotifyChange({"usuarioForm"})
	@Command
	public void usuarioUpper(){		
		usuarioForm.setNombreUsuario(usuarioForm.getNombreUsuario().toUpperCase());	
	}
	
	
	@NotifyChange({"divVisibleLogin","divVisibleIndex"})
	@GlobalCommand
	public void cierraMenu(@BindingParam("indice")int indice) {
		
		if (indice==0) {
			divVisibleLogin="true";
			divVisibleIndex="false";
		}
		
		if (indice==1) {
			divVisibleLogin="false";
			divVisibleIndex="true";
		}	
	}	

	public UsuarioForm getUsuarioForm() {
		return usuarioForm;
	}

	public void setUsuarioForm(UsuarioForm usuarioForm) {
		this.usuarioForm = usuarioForm;
	}

	public MenuForm getMenuForm() {
		return menuForm;
	}

	public void setMenuForm(MenuForm menuForm) {
		this.menuForm = menuForm;
	}

	public String getDivVisibleLogin() {
		return divVisibleLogin;
	}

	public void setDivVisibleLogin(String divVisibleLogin) {
		this.divVisibleLogin = divVisibleLogin;
	}

	public String getDivVisibleIndex() {
		return divVisibleIndex;
	}

	public void setDivVisibleIndex(String divVisibleIndex) {
		this.divVisibleIndex = divVisibleIndex;
	}

	public SucursalesBean getSucursalesBean() {
		return sucursalesBean;
	}

	public void setSucursalesBean(SucursalesBean sucursalesBean) {
		this.sucursalesBean = sucursalesBean;
	}

}
