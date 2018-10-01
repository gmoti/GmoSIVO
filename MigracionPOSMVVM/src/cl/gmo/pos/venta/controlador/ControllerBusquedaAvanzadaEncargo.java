package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.BusquedaPedidosDispatchAction;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.forms.BusquedaPedidosForm;


public class ControllerBusquedaAvanzadaEncargo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1470100323610237867L;
	Session sess = Sessions.getCurrent();
	
	private BusquedaPedidosDispatchAction busquedaPedidosDispatchAction;
	private BusquedaPedidosForm busquedaPedidosForm;
	private Date fecha_encargo;
	private AgenteBean agenteBean;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	
	
	
	
	@Init
	//public void inicial(@ExecutionArgParam("fluje")String flujo) {
	public void inicial() {	
		
		//Selectors.wireComponents(view, this, false);
		
		busquedaPedidosDispatchAction = new BusquedaPedidosDispatchAction();
		busquedaPedidosForm = new BusquedaPedidosForm();
		agenteBean = new AgenteBean();
		//fecha_encargo = new Date(System.currentTimeMillis());		
		
		busquedaPedidosDispatchAction.carga_formulario(busquedaPedidosForm, sess);
	}
	
	@Command
	public void cierraVentana(@BindingParam("win")Window win) {
		
		win.detach();
	}
	
	@NotifyChange({"busquedaPedidosForm","agenteBean","fecha_encargo"})
	@Command
	public void buscar() {	
		
		Optional<Date> fe = Optional.ofNullable(fecha_encargo);
		
		if (fe.isPresent())
			busquedaPedidosForm.setFecha(dt.format(fecha_encargo));
		else	
			busquedaPedidosForm.setFecha("");
		
		busquedaPedidosForm.setAgente(agenteBean.getUsuario());
		//busquedaPedidosForm.setFecha(dt.format(fecha_encargo));
		busquedaPedidosDispatchAction.buscar(busquedaPedidosForm, sess);
	}

	
	//=========Metodos Getter and Setter===========

	public BusquedaPedidosForm getBusquedaPedidosForm() {
		return busquedaPedidosForm;
	}
	
	public void setBusquedaPedidosForm(BusquedaPedidosForm busquedaPedidosForm) {
		this.busquedaPedidosForm = busquedaPedidosForm;
	}


	public Date getFecha_encargo() {
		return fecha_encargo;
	}


	public void setFecha_encargo(Date fecha_encargo) {
		this.fecha_encargo = fecha_encargo;
	}

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}

	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}
	
	

}
