package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

import cl.gmo.pos.venta.controlador.ventaDirecta.VentaPedidoDispatchActions;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public class ControllerAdicionalesArcli implements Serializable {

	
	private static final long serialVersionUID = -5269222668890936554L;
	Logger log = Logger.getLogger( this.getClass() );	
	
	Session sess = Sessions.getCurrent();
	
	
	private VentaPedidoDispatchActions VentaPedidoDispatch;
	private VentaPedidoForm ventaPedidoForm;
	
	
	@Init
	public void inicial() {
		
		ventaPedidoForm = new VentaPedidoForm();
		VentaPedidoDispatch = new VentaPedidoDispatchActions();
		
		ventaPedidoForm.setPuente("0");
		ventaPedidoForm.setDiagonal("0");
		ventaPedidoForm.setHorizontal("0");
		ventaPedidoForm.setVertical("0");
	}
	
	
	@Command
	public void enviar() {
		
		int puente=0;
		int diagonal=0;
		int horizontal=0;
		int vertical=0;
		String armazon;
		
		puente = Integer.parseInt(ventaPedidoForm.getPuente());
		diagonal = Integer.parseInt(ventaPedidoForm.getDiagonal());
		horizontal = Integer.parseInt(ventaPedidoForm.getHorizontal());
		vertical = Integer.parseInt(ventaPedidoForm.getVertical());
		armazon = ventaPedidoForm.getTipo_armazon();
		
		
		if(armazon.equals("0")) {
			Messagebox.show("Debe ingresar un tipo de Armazón");
			return;
		}
			
		if(puente == 0) {
			Messagebox.show("Debe ingresar un puente");
			return;
		}
		
		if(diagonal == 0 ) {
			Messagebox.show("Debe ingresar uma diagonal");
			return;
		}
		
		if(horizontal == 0) {
			Messagebox.show("Debe ingresar uma horizontal");
			return;
		}
		
		if(vertical == 0) {
			Messagebox.show("Debe ingresar uma vertical");
			return;
		}
		
		
		
		if((puente > 9) && (puente <30)){
			
			if((diagonal > 30) && (diagonal < 100)) {
				
				if((horizontal > 25) && (horizontal < 100)) {
					
					if((vertical > 18) && (vertical < 70)) {
						
						/*returnVal = new Array(document.getElementById('armazon').value,
								document.getElementById('puente').value,
								document.getElementById('diagonal').value,
								document.getElementById('horizontal').value,
								document.getElementById('vertical').value,
								document.getElementById('productoSeleccionado').value);	*/	
						
					
					}else {					
						Messagebox.show("Vertical: esta fuera del rango permitido");
						ventaPedidoForm.setVertical("0");
					}					
					
				}else {					
					Messagebox.show("Horizontal: esta fuera del rango permitido");
					ventaPedidoForm.setHorizontal("0");
				}			
				
			}else {				
				Messagebox.show("Diagonal: esta fuera del rango permitido");
				ventaPedidoForm.setDiagonal("0");
			}		
			
		}else{
			Messagebox.show("Puente: esta fuera del rango permitido");
			ventaPedidoForm.setPuente("0");			
		}
		
       			
	}	
	
	//Getter and Setter
	
	public VentaPedidoForm getVentaPedidoForm() {
		return ventaPedidoForm;
	}


	public void setVentaPedidoForm(VentaPedidoForm ventaPedidoForm) {
		this.ventaPedidoForm = ventaPedidoForm;
	}
	
}
