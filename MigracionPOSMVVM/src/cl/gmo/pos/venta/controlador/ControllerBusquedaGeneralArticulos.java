package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import cl.gmo.pos.venta.reporte.dispatch.InformeBusquedaProductoDispatchActions;
import cl.gmo.pos.venta.web.forms.InformeBusquedaProductoForm;


public class ControllerBusquedaGeneralArticulos implements Serializable {

	
	private static final long serialVersionUID = -7019940900649604869L;
	Session sess = Sessions.getCurrent();
	
	private InformeBusquedaProductoForm informeBusquedaProductoForm;
	private InformeBusquedaProductoDispatchActions informeBusquedaProducto;
	private String codigo;
	private String descripcion;
	
	
	@Init
	public void inicial() {
		
		informeBusquedaProductoForm = new InformeBusquedaProductoForm();
		informeBusquedaProducto = new  InformeBusquedaProductoDispatchActions();  
		
		
		informeBusquedaProducto.cargaFormulario(informeBusquedaProductoForm, sess);
	
	}
	
	
	@NotifyChange({"informeBusquedaProductoForm"})
	@Command
	public void reporte() {		
		
		informeBusquedaProductoForm.setCodigoArticulo(0);
		informeBusquedaProductoForm.setDescripcionArticulo(descripcion);
		
		informeBusquedaProductoForm = informeBusquedaProducto.buscarArticulo(informeBusquedaProductoForm, sess);
		
	}	
	
	
	//============== Getter and Setter ======================
	
	public InformeBusquedaProductoForm getInformeBusquedaProductoForm() {
		return informeBusquedaProductoForm;
	}

	public void setInformeBusquedaProductoForm(InformeBusquedaProductoForm informeBusquedaProductoForm) {
		this.informeBusquedaProductoForm = informeBusquedaProductoForm;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	

}
