package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.reporte.dispatch.ListadoTrabajosPendientesDispatchActions;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.forms.ListadoTrabajosPendientesForm;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.utils.Constantes;



public class ControllerListadoTrabajosPendientes implements Serializable {
	
	private static final long serialVersionUID = -6904312442529738680L;
	Session sess = Sessions.getCurrent();
	
	private ListadoTrabajosPendientesForm listadoTrabajosPendientesForm;
	private ListadoTrabajosPendientesDispatchActions listadoTrabajosPendientesDispatchActions;
		
	private AMedia fileContent;	
	private Date fechaInicio;
	private Date fechaFin;
	private byte[] bytes;
	private ReportesHelper reportes;
	private ClienteBean clienteBean;
	
	private DivisaBean divisaBean;
	private String anulado;	
	private String nombre;
	
	private String usuario;	
	private String sucursalDes;	
	
	
	@Init
	public void inicial()	{	
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		listadoTrabajosPendientesForm = new ListadoTrabajosPendientesForm();
		listadoTrabajosPendientesDispatchActions = new ListadoTrabajosPendientesDispatchActions();
		reportes = new ReportesHelper();	
		divisaBean = new DivisaBean();
		clienteBean = new ClienteBean();
		anulado = "";
		
		listadoTrabajosPendientesDispatchActions.cargaFormulario(listadoTrabajosPendientesForm, sess);		
		
		listadoTrabajosPendientesForm.setLocal(sess.getAttribute(Constantes.STRING_SUCURSAL).toString());
		listadoTrabajosPendientesForm.setCerrado("N");
		fechaInicio = new Date(System.currentTimeMillis());
		fechaFin    = new Date(System.currentTimeMillis());
	}	
	
	
	@Command
	public void buscarCliente() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteInfEncargo");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}
	
	@NotifyChange({"listadoTrabajosPendientesForm","nombre"})
	@GlobalCommand
	public void buscarClienteInfEncargo(@BindingParam("cliente")ClienteBean cliente) {
		
		nombre = cliente.getNombre() + " " + cliente.getApellido();		
		clienteBean = cliente;
		
		listadoTrabajosPendientesForm.setCliente(clienteBean.getCodigo());
	}
	
	@NotifyChange({"fileContent","listadoTrabajosPendientesForm"})
	@Command
	public void reporte() {	
		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaI = dt.format(fechaInicio);
		String fechaF = dt.format(fechaFin);
		
		listadoTrabajosPendientesForm.setFechaPedidoIni(fechaI);
		listadoTrabajosPendientesForm.setFechaPedidoTer(fechaF);
		listadoTrabajosPendientesForm.setDivisa(divisaBean.getId());
		listadoTrabajosPendientesForm.setAnulado(anulado);
		
		bytes = reportes.creaListadoTranajosPendientes(listadoTrabajosPendientesForm,sess);
		
		final AMedia media = new AMedia("ListadoTrabajoPendiente.pdf", "pdf", "application/pdf", bytes);		
		fileContent = media;		
		
	}
	
	
	
	//=============== getter and setter ================

	public ListadoTrabajosPendientesForm getListadoTrabajosPendientesForm() {
		return listadoTrabajosPendientesForm;
	}

	public void setListadoTrabajosPendientesForm(ListadoTrabajosPendientesForm listadoTrabajosPendientesForm) {
		this.listadoTrabajosPendientesForm = listadoTrabajosPendientesForm;
	}

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}	
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public DivisaBean getDivisaBean() {
		return divisaBean;
	}

	public void setDivisaBean(DivisaBean divisaBean) {
		this.divisaBean = divisaBean;
	}

	public String getAnulado() {
		return anulado;
	}

	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSucursalDes() {
		return sucursalDes;
	}

	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}		

}
