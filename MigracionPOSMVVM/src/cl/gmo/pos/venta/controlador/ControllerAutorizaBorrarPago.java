package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.VentaPedidoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;

public class ControllerAutorizaBorrarPago implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3664998797199826235L;
	Session sess = Sessions.getCurrent();
	
	private SeleccionPagoForm seleccionPagoForm;	
	private VentaPedidoDispatchActions ventaPedidoDispatchActions;

	HashMap<String,Object> objetos;
	private String user;
	private String pass;
	private String procedimiento;
	private String respuesta="";
	
	@Init
	public void inicio(@ExecutionArgParam("retorno")String retorno, @ExecutionArgParam("seleccionPago")SeleccionPagoForm seleccionPago) {		
		user="";
		pass="";
		procedimiento="";	
		respuesta = retorno;
		
		ventaPedidoDispatchActions = new VentaPedidoDispatchActions();	
		seleccionPagoForm = seleccionPago;
	}

	
	
	@Command
	public void autoriza(@BindingParam("win")Window win) {	
		
	  int resp=0; 	
		  
	  sess.setAttribute(Constantes.STRING_USUARIO, this.getUser()); 
	  sess.setAttribute(Constantes.STRING_PASS, this.getPass());
	  sess.setAttribute(Constantes.STRING_LISTA_PAGOS, seleccionPagoForm.getListaPagos());	
	  
	  objetos = new HashMap<String,Object>();		
	  objetos.put("retorno","autorizaBorrarPagoRespuesta");
		
		try {
			resp = ventaPedidoDispatchActions.valida_permiso_mod_fpago(sess);
			//resp=1;
			
			switch(resp) {
			case 0:
				Messagebox.show("Debes estar autorizado para borrar formas de Pago");
				break;
			case 1:			
				objetos = new HashMap<String,Object>();		
				objetos.put("tipoAccion",procedimiento);
				objetos.put("autorizador",this.getUser());
				
				BindUtils.postGlobalCommand(null, null, respuesta, objetos);
				win.detach();
				break;
			case 2:
				Messagebox.show("No tienes asignado este local para realizar cambios");
				break;
			case 3:
				Messagebox.show("Usuario o contraseña incorrecta");
				break;			
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
		
	
	//Getter and Setter

	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

}
