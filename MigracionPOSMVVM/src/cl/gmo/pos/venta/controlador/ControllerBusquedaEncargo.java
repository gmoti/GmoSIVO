package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;



public class ControllerBusquedaEncargo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429518999220263049L;
	
	Session sess = Sessions.getCurrent();
	private ArrayList<PedidosPendientesBean> lista_pedidos;
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view, 
						@ExecutionArgParam("listaPedidos")ArrayList<PedidosPendientesBean> arg) {
		
		    Selectors.wireComponents(view, this, false);
		    
		    lista_pedidos = new ArrayList<PedidosPendientesBean>();
		    lista_pedidos = arg;	
	}
	
	@Command
	public void seleccionaProducto(@BindingParam("win")Window win) {		
		win.detach();		
	}

	
	
	//================ Getter and Setter ==================
	
	
	public ArrayList<PedidosPendientesBean> getLista_pedidos() {
		return lista_pedidos;
	}

	public void setLista_pedidos(ArrayList<PedidosPendientesBean> lista_pedidos) {
		this.lista_pedidos = lista_pedidos;
	}

	
}
