package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Big;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.helper.VentaPedidoHelper;


public class ControllerAutorizadorDescuento implements Serializable {

	
	private static final long serialVersionUID = 977406260932782517L;
	Session sess = Sessions.getCurrent();
	
	private String user;
	private String pass;
	HashMap<String,Object> objetos;
	
	@Init
	public void inicial() {
		
		user="";
		pass="";
	}
	
	
	@Command
	public void autorizadesc(@BindingParam("win")Window win) {
		
		BigDecimal descuento;
		String tipo="";
		BeanGlobal bg = new BeanGlobal();
		
		tipo = sess.getAttribute(Constantes.STRING_TIPO).toString();		
		
		if (!tipo.equals(Constantes.STRING_CERO))
		{
			 descuento = new VentaPedidoHelper().traeDecuento(user, pass, tipo);			 
			 if(descuento.equals(new BigDecimal(-1))) {
				 bg.setObj_1("falae");
				 bg.setObj_2(BigDecimal.ZERO);
				 bg.setObj_3("");				 
			 }else {
				 bg.setObj_1("false");
				 bg.setObj_2(descuento);
				 bg.setObj_3(user);
			 }			 
						 
		}
		else
		{
			descuento = new VentaPedidoHelper().traeDecuento(user, pass, null);
			if(descuento.equals(new BigDecimal(-1))) {
				 bg.setObj_1("falae");
				 bg.setObj_2(BigDecimal.ZERO);
				 bg.setObj_3("");				 
			 }else {
				 bg.setObj_1("false");
				 bg.setObj_2(descuento);
				 bg.setObj_3(user);
			 }	
		}	
		
		
		objetos = new HashMap<String,Object>();		
		objetos.put("valores", bg);		
			
		sess.setAttribute("Descuento", descuento.toString());
		
		BindUtils.postGlobalCommand(null, null, "devuelve_descuento_total_monto", objetos);
		win.detach();
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
