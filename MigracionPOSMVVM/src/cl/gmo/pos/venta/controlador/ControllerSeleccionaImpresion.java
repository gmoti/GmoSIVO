package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;



public class ControllerSeleccionaImpresion implements Serializable {

	
	private static final long serialVersionUID = 4965738955197248243L;
	
	private String seleccion;
	HashMap<String,Object> objetos;
	
	private boolean visibleGuia=false;
	private boolean visibleBoleta=false;
	private SeleccionPagoForm seleccionPago;
	
	
	@Init
	public void inicial(@ExecutionArgParam("origen")String origen, @ExecutionArgParam("seleccionPagoForm")SeleccionPagoForm seleccionPagoForm) {		
		seleccion="X";			
		
		seleccionPago = seleccionPagoForm;
		
		
		if (seleccionPago.getOrigen().equals("PEDIDO") && !seleccionPago.getSolo_boleta().equals("true")) 
			visibleGuia=true;
		
		if (!seleccionPago.getSolo_guia().equals("true")) 
			visibleBoleta=true;
		
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

	public SeleccionPagoForm getSeleccionPago() {
		return seleccionPago;
	}

	public void setSeleccionPago(SeleccionPagoForm seleccionPago) {
		this.seleccionPago = seleccionPago;
	}

	public boolean isVisibleGuia() {
		return visibleGuia;
	}

	public void setVisibleGuia(boolean visibleGuia) {
		this.visibleGuia = visibleGuia;
	}

	public boolean isVisibleBoleta() {
		return visibleBoleta;
	}

	public void setVisibleBoleta(boolean visibleBoleta) {
		this.visibleBoleta = visibleBoleta;
	}	
	
	
	
	

}
