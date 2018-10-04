package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Window;



public class ControllerSeleccionaImpresion implements Serializable {

	
	private static final long serialVersionUID = 4965738955197248243L;
	
	private String seleccion;
	HashMap<String,Object> objetos;
	
	private String visibleGuia="false";
	
	
	@Init
	public void inicial(@ExecutionArgParam("origen")String origen) {		
		seleccion="X";			
		
		if (origen.equals("PEDIDO")) visibleGuia="true";
		
		if (origen.equals("DIRECTA")) visibleGuia="false";
		
	}
	
	@Command
	public void selecciona(@BindingParam("win")Window win) {
		
		objetos = new HashMap<String,Object>();
		objetos.put("seleccion",seleccion);
		BindUtils.postGlobalCommand(null, null, "seleccionImpresion", objetos);
		
		win.detach();
	}

	public String getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(String seleccion) {
		this.seleccion = seleccion;
	}

	public String getVisibleGuia() {
		return visibleGuia;
	}

	public void setVisibleGuia(String visibleGuia) {
		this.visibleGuia = visibleGuia;
	}	
	
	
	
	

}
