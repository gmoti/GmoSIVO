package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.controlador.presupuesto.BusquedaPresupuestosDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.forms.BusquedaPresupuestosForm;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;


public class ControllerBuscarPresupuesto implements Serializable{
	
	
	private static final long serialVersionUID = -9094708405939416646L;
	Session sess = Sessions.getCurrent();
		
	private BusquedaPresupuestosForm busquedaPresupuestos;
	private BusquedaPresupuestosDispatchActions busquedaPresupuestosDispatch;
	private Date fecha;
	private AgenteBean agenteBean;
	
	private String usuario;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	
	
	@Init
	public void inicial() {		    
		
		usuario = sess.getAttribute(Constantes.STRING_USUARIO).toString();
		   
		busquedaPresupuestosDispatch = new BusquedaPresupuestosDispatchActions();
		busquedaPresupuestos = new BusquedaPresupuestosForm();
		
		fecha=null;
		agenteBean = new AgenteBean();  
		
		sess.setAttribute("flujo","nuevo");
		busquedaPresupuestosDispatch.carga_formulario(busquedaPresupuestos, sess);
		
		busquedaPresupuestos.setCliente("");
		busquedaPresupuestos.setPresupuesto("");
		
		//Optional<AgenteBean> ab = busquedaPresupuestos.getLista_agentes().stream().filter(s -> "Seleccione".equals(s.getUsuario())).findFirst();		
		//if(ab.isPresent()) agenteBean = ab.get(); else agenteBean = null;
		
		Optional<AgenteBean> ab = busquedaPresupuestos.getLista_agentes().stream().filter(s -> usuario.equals(s.getUsuario())).findFirst();		
		if(ab.isPresent()) agenteBean = ab.get(); else agenteBean = null;
	}
	
	
	@Command
	public void seleccionaProducto(@BindingParam("win")Window win,
			@BindingParam("arg")BusquedaPresupuestosForm arg,
			@BindingParam("arg2")PresupuestosBean arg2) {	
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();
		objetos.put("arg", arg);
		objetos.put("arg2", arg2);
		
		BindUtils.postGlobalCommand(null, null, "seleccionaProductoBusqueda", objetos);
		
		win.detach();		
	}
	
	
	@NotifyChange({"busquedaPresupuestos"})
	@Command
	public void buscarPresupuesto() {
		
		Optional<Date> f = Optional.ofNullable(fecha);
		
		if (f.isPresent()) {			
			busquedaPresupuestos.setFecha(dt.format(f.get()));			
		}else
			busquedaPresupuestos.setFecha("");		
		
		if (!agenteBean.getUsuario().equals("Seleccione"))
			busquedaPresupuestos.setAgente(agenteBean.getUsuario());		
		
		busquedaPresupuestosDispatch.buscar(busquedaPresupuestos, sess);		
	}
	
	
	//getter and setter

	public BusquedaPresupuestosForm getBusquedaPresupuestos() {
		return busquedaPresupuestos;
	}

	public void setBusquedaPresupuestos(BusquedaPresupuestosForm busquedaPresupuestos) {
		this.busquedaPresupuestos = busquedaPresupuestos;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}

	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}
	
}
