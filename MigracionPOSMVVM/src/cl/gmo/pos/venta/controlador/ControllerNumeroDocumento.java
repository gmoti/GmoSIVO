package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;

public class ControllerNumeroDocumento implements Serializable {

	
	private static final long serialVersionUID = 980505385138847455L;
	
	Session sess = Sessions.getCurrent();	
	HashMap<String,Object> objetos;	
	
	private SeleccionPagoForm seleccionPago; 
	private SeleccionPagoDispatchActions seleccionPagoDispatch;
	
	@Init
	public void inicio(@ExecutionArgParam("seleccionPagoForm")SeleccionPagoForm arg) {
		
		seleccionPago = arg;
		seleccionPagoDispatch = new SeleccionPagoDispatchActions();		
	}
	
	@NotifyChange("seleccionPago")
	@Command
	public void confirmaNumeroDocumento(@BindingParam("win")Window win)
	{
		int num_documento;
		int num_documento2;
		String error;
		
		num_documento = seleccionPago.getNumero_boleta();
		num_documento2= seleccionPago.getNumero_boleta_conf();
		
		if (num_documento == num_documento2) {
			
			seleccionPago.setAccion(Constantes.STRING_VALIDA_BOLETA);
			
			try {
				seleccionPagoDispatch.IngresaPago(seleccionPago, sess);
				
				error = sess.getAttribute(Constantes.STRING_ERROR).toString();
				
				if (error.equals(Constantes.STRING_BOLETA_EXITO)) {
					
					objetos = new HashMap<String,Object>();		
					objetos.put("num_documento",num_documento);
					objetos.put("num_documento2",num_documento2);
					
					BindUtils.postGlobalCommand(null, null, "retornoNumeroDocumento", objetos);
					
					win.detach();
				}					
				
			} catch (Exception e) {				
				e.printStackTrace();
			}
			
		}
		else
		{
			Messagebox.show("Los numeros de los documento no coninciden");
			seleccionPago.setNumero_boleta(0);
			seleccionPago.setNumero_boleta_conf(0);			
		}
		
	}


	//Getter and Setter
	
	public SeleccionPagoForm getSeleccionPago() {
		return seleccionPago;
	}

	public void setSeleccionPago(SeleccionPagoForm seleccionPago) {
		this.seleccionPago = seleccionPago;
	}
}
