package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.math.BigDecimal;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.helper.VentaPedidoHelper;


public class ControllerAutorizadorDescuento implements Serializable {

	
	private static final long serialVersionUID = 977406260932782517L;
	Session sess = Sessions.getCurrent();
	
	private String user;
	private String pass;
	
	@Init
	public void inicial() {
		
		user="";
		pass="";
	}
	
	
	@Command
	public void autorizadesc() {
		
		BigDecimal descuento;
		String tipo=null;
		/*var user = document.getElementById('user').value;
		var pass = document.getElementById('pass').value;
		var tipo = document.getElementById('tipo_pedido').value;
		
		String usuario = request.getParameter(Constantes.STRING_USUARIO).toString();
		String pass = request.getParameter(Constantes.STRING_PASS).toString();
		String tipo = request.getParameter(Constantes.STRING_TIPO).toString();*/
		
		
		if (!tipo.equals(Constantes.STRING_CERO))
		{
			 descuento = new VentaPedidoHelper().traeDecuento(user, pass, tipo);
		}
		else
		{
			descuento = new VentaPedidoHelper().traeDecuento(user, pass, null);
		}		
			
		sess.setAttribute("Descuento", descuento.toString());
	}
	
	
	//==============Getter and Setter ==============

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

}
