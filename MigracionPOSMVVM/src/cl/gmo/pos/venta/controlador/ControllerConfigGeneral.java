package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import cl.gmo.pos.venta.utils.Constantes;

public class ControllerConfigGeneral implements Serializable {

	
	private static final long serialVersionUID = -4389693669719749251L;
	Logger log = Logger.getLogger( this.getClass() );	
	
	Session sess = Sessions.getCurrent();	
	private String usuario;	
	private String sucursalDes;	
	
	private String ipBoleta;
	private String ipNota;
	
	
	
	@Init
	public void inicial(@ExecutionArgParam("origen")String arg) {	
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		ipBoleta="10.216.4.24";
		ipNota	="10.216.4.16";
		
	}
	
	
	@Command
	public void grabar() {
		
		sess.setAttribute("ipBoleta", ipBoleta);
		sess.setAttribute("ipNota", ipNota);
	}

	
	//Getter and Setter

	public String getIpBoleta() {
		return ipBoleta;
	}

	public void setIpBoleta(String ipBoleta) {
		this.ipBoleta = ipBoleta;
	}

	public String getIpNota() {
		return ipNota;
	}

	public void setIpNota(String ipNota) {
		this.ipNota = ipNota;
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
