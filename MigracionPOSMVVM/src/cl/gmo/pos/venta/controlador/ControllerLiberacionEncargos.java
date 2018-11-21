package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import cl.gmo.pos.venta.controlador.general.BusquedaLiberacionesDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.forms.BusquedaLiberacionesForm;

public class ControllerLiberacionEncargos implements Serializable {

	
	private static final long serialVersionUID = -648978358513467205L;
	Session sess = Sessions.getCurrent();
	
	private BusquedaLiberacionesForm busquedaLiberacionesForm;
	private BusquedaLiberacionesDispatchActions BusquedaLiberaciones;
	private Date fdesde;
	private Date fhasta;
	private String estadoEncargo;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");
	
	private String usuario;
	private String sucursal;
	private String sucursalDes;
	
	
	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);
		sucursal = (String)sess.getAttribute(Constantes.STRING_SUCURSAL);
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		busquedaLiberacionesForm = new BusquedaLiberacionesForm();
		BusquedaLiberaciones = new BusquedaLiberacionesDispatchActions();
		fdesde = new Date(System.currentTimeMillis());
		fhasta = new Date(System.currentTimeMillis());
		estadoEncargo = "1";
		
	}
	
	
	@NotifyChange({"busquedaLiberacionesForm"})
	@Command
	public void buscar_liberacion() {
		
		String encargo="";
		
		switch (estadoEncargo) {
		case "1":	
			encargo="-1";
			break;
			
		case "2":
			encargo="0";
			break;
			
		case "3":
			encargo="1";
			break;
			
		case "4":
			encargo="2";
			break;	
		
		}
		
		
		
		busquedaLiberacionesForm.setAccion("buscar");
		busquedaLiberacionesForm.setFecha(dt.format(fdesde));
		busquedaLiberacionesForm.setFechaHasta(dt.format(fhasta));
		busquedaLiberacionesForm.setEstado_encargo(encargo);
		BusquedaLiberaciones.buscarLiberacion(busquedaLiberacionesForm, sess);
		
	}
	
	@NotifyChange({"busquedaLiberacionesForm"})
	@Command
	public void seleccionaDetalle(@BindingParam("pedido")VentaPedidoBean pedido) {
		
		//document.getElementById("accion").value='detalles';
		//document.getElementById("codigoDetalle").value=codigo;
		//document.getElementById("grupoDetalle").value=grupo;
		//document.getElementById("poscroll").value=document.getElementById("scrolling_liberacion").scrollTop;			
		//document.getElementById('indexForm').value=index;
		//document.getElementById('lineaDetalle').value=linea;
		//document.busquedaLiberacionesForm.submit();	
		
		busquedaLiberacionesForm.setAccion(Constantes.STRING_ACTION_DETALLE);
		busquedaLiberacionesForm.setCodigoDetalle(pedido.getCod_lista_lib());
		busquedaLiberacionesForm.setGrupoDetalle(pedido.getGrupo());
	
		busquedaLiberacionesForm.setListaDetalle(new ArrayList<VentaPedidoBean>());
		busquedaLiberacionesForm.setListaSuplementos(new ArrayList<SuplementopedidoBean>());
		
		BusquedaLiberaciones.buscarLiberacion(busquedaLiberacionesForm, sess);
		
	}
	
	
	@NotifyChange({"busquedaLiberacionesForm"})
	@Command
	public void detalle_suplemento(@BindingParam("pedido")VentaPedidoBean pedido) {
		
		//document.getElementById("accion").value='suplemento';
		//document.getElementById("identPedtv").value=codigo;	
		//document.getElementById('index2').value=index2;
		//document.busquedaLiberacionesForm.submit();	
		
		busquedaLiberacionesForm.setAccion(Constantes.STRING_SUPLEMENTO);
		busquedaLiberacionesForm.setIdentPedtv(String.valueOf(pedido.getCodigo()));
		
		BusquedaLiberaciones.buscarLiberacion(busquedaLiberacionesForm, sess);		
	}	
	
	
	@NotifyChange({"busquedaLiberacionesForm"})
	@Command
	public void liberacion_pedido() {
		
		//document.getElementById("accion").value='liberacion';
		//document.busquedaLiberacionesForm.submit();
		
		busquedaLiberacionesForm.setAccion(Constantes.STRING_ACTION_LIBERACION);
		
		BusquedaLiberaciones.buscarLiberacion(busquedaLiberacionesForm, sess);	
		
	}
	
	
	//=============== getter and setter ==============

	public BusquedaLiberacionesForm getBusquedaLiberacionesForm() {
		return busquedaLiberacionesForm;
	}

	public void setBusquedaLiberacionesForm(BusquedaLiberacionesForm busquedaLiberacionesForm) {
		this.busquedaLiberacionesForm = busquedaLiberacionesForm;
	}

	public Date getFdesde() {
		return fdesde;
	}
	
	public void setFdesde(Date fdesde) {
		this.fdesde = fdesde;
	}

	public Date getFhasta() {
		return fhasta;
	}

	public void setFhasta(Date fhasta) {
		this.fhasta = fhasta;
	}

	public String getEstadoEncargo() {
		return estadoEncargo;
	}

	public void setEstadoEncargo(String estadoEncargo) {
		this.estadoEncargo = estadoEncargo;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getSucursalDes() {
		return sucursalDes;
	}

	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
