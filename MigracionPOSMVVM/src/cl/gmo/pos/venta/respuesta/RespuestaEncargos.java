package cl.gmo.pos.venta.respuesta;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public final class RespuestaEncargos implements Serializable {

	
	private static final long serialVersionUID = -4092862627145248409L;
	
	
	private RespuestaEncargos() {
		
	}
	
	public static VentaPedidoForm evaluaEstado(VentaPedidoForm ventaPedido) {
		
		String estado;
		String error;
		String flujo;	
		
		estado = ventaPedido.getEstado();
		error = ventaPedido.getError();
		flujo = ventaPedido.getFlujo();
		
		if (flujo.equals("formulario")) {
			
			//bloquearCampos();
			if(estado.equals("guardado"))
			{
				Messagebox.show("Encargo Almacenado");
			}
			if(estado.equals("eliminado"))
			{
				Messagebox.show("Encargo Eliminado");
			}		
			
		}else {
			
			switch (estado) {
			case "guardado":	
				Messagebox.show("Encargo Almacenado");
				break;
				
			case "producto_precio_especial":				
				break;
				
			case "producto_con_suplemento_obligatorio":				
				break;
				
			case "sg_autorizacion":				
				break;
				
			case "producto_arcli":
				
				productoArcli(ventaPedido);
				break;
				
			case "AGREGA_DETALLE_PRODUCTO_ARCLI_PRESUPUESTO":				
				break;
				
			case "producto_confirmacion":				
				break;		
			
			case "venta_no_factible":	
				Messagebox.show("Esta venta no es técnicamente factible: " + error);
				break;
				
			case "genera_cobros":				
				break;
			
			case "carga_multioferta":				
				break;
				
			case "venta":				
				break;	
			
			}
		}	
		
		return ventaPedido;
	}
	
	
	private static void productoArcli(VentaPedidoForm ventaPedido) {	
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();
		objetos.put("ventaPedido", ventaPedido);
		
		Window window = (Window)Executions.createComponents(
                "/zul/encargos/AdicionalesArcli.zul", null, objetos);
		
        window.doModal();
		
		
	}
	
	
	

}
