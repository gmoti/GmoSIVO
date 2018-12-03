package cl.gmo.pos.venta.respuesta;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.VentaPedidoDispatchActions;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public final class RespuestaEncargos implements Serializable {
	
	private static final long serialVersionUID = -4092862627145248409L;	
	
	private RespuestaEncargos() {
		
	}
	
	public static VentaPedidoForm evaluaEstado(VentaPedidoForm ventaPedido, Session sess) {
		
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
				
				generaCobros(ventaPedido,sess);
				break;
			
			case "carga_multioferta":	
				
				CargaMultioferta(ventaPedido,sess);
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
	
	private static void CargaMultioferta(VentaPedidoForm ventaPedido, Session sess) {
		
		BusquedaProductosForm busquedaProductosForm;
		ProductosBean producto;
		HashMap<String,Object> objetos;
		int index=-1;	
		
		producto = (ProductosBean) sess.getAttribute("productosBean");
		
		for(int i=0; i < ventaPedido.getListaProductos().size(); i++) {
			index=i;
		}		
		
		busquedaProductosForm    = new BusquedaProductosForm();
		
		busquedaProductosForm.setCliente(ventaPedido.getCodigo());
		busquedaProductosForm.setCodigoBusqueda(producto.getCod_barra());
		busquedaProductosForm.setCodigoMultioferta(ventaPedido.getCodigo_mult());
		busquedaProductosForm.setIndex_multi(ventaPedido.getIndex_multi());			
		busquedaProductosForm.setFecha_graduacion(producto.getFecha_graduacion());			
		busquedaProductosForm.setCdg(ventaPedido.getCodigo_suc() +"/"+ ventaPedido.getCodigo());
		
		objetos = new HashMap<String,Object>();
		objetos.put("busquedaProductos",busquedaProductosForm);
		objetos.put("origen","consultaProducto");
		objetos.put("beanProducto",producto);
		objetos.put("index",index);		
		
		Window window = (Window)Executions.createComponents(
                "/zul/encargos/BusquedaMultiofertas.zul", null, objetos);
		
        window.doModal();
		
	}
	
	
	private static void generaCobros(VentaPedidoForm ventaPedido, Session sess) {		
		
		SeleccionPagoForm seleccionPagoForm;
		HashMap<String,Object> objetos;
		VentaPedidoDispatchActions VentaPedidoDispatch = new VentaPedidoDispatchActions();
		
		//aqui se cargan las variables de session
		VentaPedidoDispatch.generaVentaPedido(ventaPedido, sess);
		
		seleccionPagoForm = new SeleccionPagoForm();			
		
		objetos = new HashMap<String,Object>();
		objetos.put("cliente",(ClienteBean)sess.getAttribute("_Cliente"));
		objetos.put("pagoForm",seleccionPagoForm);
		objetos.put("ventaOrigenForm",ventaPedido);
		objetos.put("origen","PEDIDO");
		
		Window windowPagoVentaDirecta = (Window)Executions.createComponents(
                "/zul/venta_directa/SeleccionPago.zul", null, objetos);
		
		windowPagoVentaDirecta.doModal();	
		
	}
	
	
	

}
