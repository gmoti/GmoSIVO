package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Window;

public class ControllerInformacion implements Serializable {
	
	private static final long serialVersionUID = -4020346183413069780L;
	
	String sRetSI="";
	String sRetNO="";
	HashMap<String,Object> objetos;
	
	String mensaje1="";
	String mensaje2="";
	String mensaje3="";
	String mensaje4="";
	String pregunta="";
	
	@Init	
	public void inicial(@ExecutionArgParam("retornoSI")String retornoSI,
						@ExecutionArgParam("retornoNO")String retornoNO,
						@ExecutionArgParam("mens1")String mens1,
						@ExecutionArgParam("mens2")String mens2,
						@ExecutionArgParam("mens3")String mens3,
						@ExecutionArgParam("mens4")String mens4,
						@ExecutionArgParam("pregunta")String preg) {
		
		sRetSI = retornoSI;
		sRetNO = retornoNO;
		mensaje1 = mens1;
		mensaje2 = mens2;
		mensaje3 = mens3;
		mensaje4 = mens4;
		pregunta = preg;
	}
	
	@Command
	public void respSi(@BindingParam("win")Window win) {
		
		//objetos = new HashMap<String,Object>();
		//objetos.put("respuesta","SI");
		
		BindUtils.postGlobalCommand(null, null, sRetSI, null);
		win.detach();
	}
	
	@Command
	public void respNo(@BindingParam("win")Window win) {
		
		//objetos = new HashMap<String,Object>();
		//objetos.put("respuesta","NO");
		
		BindUtils.postGlobalCommand(null, null, sRetNO, null);
		win.detach();
	}

	public String getMensaje1() {
		return mensaje1;
	}

	public void setMensaje1(String mensaje1) {
		this.mensaje1 = mensaje1;
	}

	public String getMensaje2() {
		return mensaje2;
	}

	public void setMensaje2(String mensaje2) {
		this.mensaje2 = mensaje2;
	}

	public String getMensaje3() {
		return mensaje3;
	}

	public void setMensaje3(String mensaje3) {
		this.mensaje3 = mensaje3;
	}

	public String getMensaje4() {
		return mensaje4;
	}

	public void setMensaje4(String mensaje4) {
		this.mensaje4 = mensaje4;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	
	
	

}
