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
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.SuplementoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.SuplementosValores;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.SuplementosForm;



public class ControllerAgregaSuplemento implements Serializable {

	private static final long serialVersionUID = 9155273456336143454L;
	
	Session sess = Sessions.getCurrent();
	private SuplementosForm suplementosForm;
	private SuplementoDispatchActions suplementoDispatchActions;
	private SuplementopedidoBean suplementopedidoBean;
	
	private ProductosBean productosBean;
	private BusquedaProductosForm busquedaProductosForm;
	private SuplementosValores suplementosValores;
	HashMap<String,Object> objetos;
	
	private int ind;
	private String origen;
	
	@Init
	public void inicial(@ExecutionArgParam("producto")ProductosBean producto,
						@ExecutionArgParam("index")int index,
						@ExecutionArgParam("busquedaProductos")BusquedaProductosForm busquedaProductos,
						@ExecutionArgParam("origen")String org) {
		
		productosBean = producto;
		ind = index;
		
		//PEDIDO: SI ES DE ENCARGO
		//MULTIOFERTA: SI DE LA BUSQUEDA DE MULTIOFERTA
		origen = org;
		
		busquedaProductosForm = busquedaProductos;
		suplementosValores = new SuplementosValores();
		
		suplementoDispatchActions = new SuplementoDispatchActions();		
		suplementosForm = new SuplementosForm();
		suplementopedidoBean = new SuplementopedidoBean();
		
		sess.setAttribute(Constantes.STRING_PRODUCTO,producto);
		
		suplementoDispatchActions.carga(suplementosForm, sess);	
	
	}	
	
	
	@NotifyChange({"suplementosForm","suplementosValores","suplementopedidoBean"})
	@Command
	public void enviar() {	
		
		sess.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, "formulario");
		sess.setAttribute(Constantes.STRING_PRODUCTO, productosBean);
		
		suplementosForm.setSuplemento(suplementopedidoBean.getTratami());
		suplementosForm.setValor(suplementosValores.getDescripcion());
		
		suplementosForm.setAccion("agregar");
		suplementoDispatchActions.agregar(suplementosForm, sess);	
	}
	
	
	@Command
	public void cerrar(@BindingParam("win")Window win) {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("suplementos",suplementosForm.getListaSuplementos());
		objetos.put("producto",productosBean);
		objetos.put("index",ind);
		
		if (origen.equals("PEDIDO"))
			BindUtils.postGlobalCommand(null, null, "actulizaListaSuplementos", objetos);
		
		if(origen.equals("MULTIOFERTA"))
			BindUtils.postGlobalCommand(null, null, "actulizaListaSuplementosMulti", objetos);
		
		win.detach();
	}
	
	
	
	
	
	/*
	function enviar()
    {
    	try{ 
    	if (document.suplementosForm.suplemento.value != 0) {
    		if(document.suplementosForm.valor.value != "")
        	{
        		document.suplementosForm.accion.value = "agregar";
        		document.suplementosForm.submit();
        	}
        	else
        	{
        		alert("Debe ingresar un valor");	
        	}
		}
		else
		{
			alert("Debe seleccionar un suplemento");
		}
    	}catch(err){
    		alert("Artculo no permite suplementos");
    	}
			
    }*/
	
	@NotifyChange({"suplementosForm","suplementopedidoBean"})
	@Command
	public void recupera_suplemento() {
		
		suplementosForm.setSuplemento(suplementopedidoBean.getTratami());
		suplementosForm.setSuplemento_desc(suplementopedidoBean.getDescripcion());
		suplementosForm.setAccion("carga_valores");
		suplementoDispatchActions.agregar(suplementosForm, sess);
	}
	
	@NotifyChange({"suplementosForm"})
	@Command
	public void eliminarSuplemento(@BindingParam("index")int index) {		
		suplementosForm.setSuplemento_desc(String.valueOf(index));
		suplementosForm.setAccion("eliminarProducto");
		suplementoDispatchActions.agregar(suplementosForm, sess);		
	}
	
	

	//============ Setter and Getter ===============
	
	public SuplementosForm getSuplementosForm() {
		return suplementosForm;
	}

	public void setSuplementosForm(SuplementosForm suplementosForm) {
		this.suplementosForm = suplementosForm;
	}

	public SuplementopedidoBean getSuplementopedidoBean() {
		return suplementopedidoBean;
	}

	public void setSuplementopedidoBean(SuplementopedidoBean suplementopedidoBean) {
		this.suplementopedidoBean = suplementopedidoBean;
	}

	public SuplementosValores getSuplementosValores() {
		return suplementosValores;
	}

	public void setSuplementosValores(SuplementosValores suplementosValores) {
		this.suplementosValores = suplementosValores;
	}	
	
}
