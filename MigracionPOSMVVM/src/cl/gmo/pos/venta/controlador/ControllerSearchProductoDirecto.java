package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.facade.PosProductosFacade;

public class ControllerSearchProductoDirecto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1661796987562967607L;
	Session sess = Sessions.getCurrent();
	
	private String cdg;
	
	
	@Init
	public void inicial() {
		
		cdg="";	
	}
	
	@NotifyChange({"cdg"})
	@Command
	public void buscarProducto(@BindingParam("win")Window win) {
		
		ProductosBean producto;		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		
		if(cdg.equals("")) {
			Messagebox.show("Debe indicar un código a buscar");
			return;
		}
		
		producto = PosProductosFacade.traeProducto(null, 1, sess.getAttribute(Constantes.STRING_SUCURSAL).toString(), "DIRECTA", getCdg());
		objetos.put("producto",producto);		
		
		BindUtils.postGlobalCommand(null, null, "actProdGrid", objetos);
		win.detach();
	}

	public String getCdg() {
		return cdg;
	}

	public void setCdg(String cdg) {
		this.cdg = cdg;
	}	

}
