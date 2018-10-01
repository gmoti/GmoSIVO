package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;



import cl.gmo.pos.venta.web.beans.ListaEstadosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.helper.BusquedaLiberacionesHelper;

public class BusquedaLiberacionesForm {
	
	/*
	* 
	*/
	private static final long serialVersionUID = 1287778398512998450L;
	private String codigo;
	private String accion;
	private String identPedtv;
	private ArrayList<VentaPedidoBean> listaPedidos;
	private ArrayList<VentaPedidoBean> listaDetalle;
	private ArrayList<SuplementopedidoBean> listaSuplementos;
	private String pedidos;
	private String detalle;
	private boolean checked;
	private String codigoLocal;
	private String codigoDetalle;
	private String grupoDetalle;
	BusquedaLiberacionesHelper helper = new BusquedaLiberacionesHelper();
	private String poscroll;
	private String index;
	private String index2;
	private String mensaje;
	private String fecha;
	private String fechaHasta;
	private String pagina_status;
	private String respuestaLiberacion;
	private String lineaDetalle;
	private String estado_encargo;
	private ArrayList<ListaEstadosBean> listaEstados;

	
	public String getLineaDetalle() {
		return lineaDetalle;
	}
	public void setLineaDetalle(String lineaDetalle) {
		this.lineaDetalle = lineaDetalle;
	}
	public String getRespuestaLiberacion() {
		return respuestaLiberacion;
	}
	public void setRespuestaLiberacion(String respuestaLiberacion) {
		this.respuestaLiberacion = respuestaLiberacion;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getPagina_status() {
		return pagina_status;
	}
	public void setPagina_status(String pagina_status) {
		this.pagina_status = pagina_status;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getIndex2() {
		return index2;
	}
	public void setIndex2(String index2) {
		this.index2 = index2;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getPoscroll() {
		return poscroll;
	}
	public void setPoscroll(String poscroll) {
		this.poscroll = poscroll;
	}
	public String getGrupoDetalle() {
		return grupoDetalle;
	}
	public void setGrupoDetalle(String grupoDetalle) {
		this.grupoDetalle = grupoDetalle;
	}
	public String getCodigoDetalle() {
		return codigoDetalle;
	}
	public void setCodigoDetalle(String codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
	}
	public ArrayList<VentaPedidoBean> getListaDetalle() {
		return listaDetalle;
	}
	public void setListaDetalle(ArrayList<VentaPedidoBean> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public ArrayList<VentaPedidoBean> getListaPedidos() {
		return listaPedidos;
	}
	public void setListaPedidos(ArrayList<VentaPedidoBean> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	public String getPedidos() {
		return pedidos;
	}
	public void setPedidos(String pedidos) {
		this.pedidos = pedidos;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}	
	public String getEstado_encargo() {
		return estado_encargo;
	}
	public void setEstado_encargo(String estado_encargo) {
		this.estado_encargo = estado_encargo;
	}
	
	//fquiroz error
	/*public void reset(ActionMapping mapping, 
			  HttpServletRequest request) 
	{
			//reset properties
			this.checked = false;
			//initial the customers collection
			this.listaDetalle = new ArrayList<VentaPedidoBean>();
			this.listaSuplementos =new ArrayList<SuplementopedidoBean>();
			this.listaPedidos = helper.traeVentasPedidos(this.codigoLocal, this.fecha, this.fechaHasta,this.estado_encargo);
	}
	
	public void resetLiberacion(ActionMapping mapping, 
			  HttpServletRequest request) 
	{
			//reset properties
			this.checked = false;
			//initial the customers collection
			this.listaDetalle = new ArrayList<VentaPedidoBean>();
			this.listaSuplementos =new ArrayList<SuplementopedidoBean>();
			this.listaPedidos = new ArrayList<VentaPedidoBean>();
			this.listaPedidos = helper.traeVentasPedidos(this.codigoLocal, this.fecha, this.fechaHasta,this.estado_encargo);
	}*/
	
	public ArrayList<SuplementopedidoBean> getListaSuplementos() {
		return listaSuplementos;
	}
	public void setListaSuplementos(ArrayList<SuplementopedidoBean> listaSuplementos) {
		this.listaSuplementos = listaSuplementos;
	}
	public String getCodigoLocal() {
		return codigoLocal;
	}
	public void setCodigoLocal(String codigoLocal) {
		this.codigoLocal = codigoLocal;
	}
	public String getIdentPedtv() {
		return identPedtv;
	}
	public void setIdentPedtv(String identPedtv) {
		this.identPedtv = identPedtv;
	}
	
	public ArrayList<ListaEstadosBean> getListaEstados() {
		return listaEstados;
	}
	public void setListaEstados(ArrayList<ListaEstadosBean> listaEstados) {
		this.listaEstados = listaEstados;
	}

	
	public void clean(){
		 String codigo="";
		 String accion="";
		 String identPedtv="";
		 ArrayList<VentaPedidoBean> listaPedidos = new ArrayList<VentaPedidoBean>();
		 ArrayList<VentaPedidoBean> listaDetalle = new ArrayList<VentaPedidoBean>();
		 ArrayList<SuplementopedidoBean> listaSuplementos = new ArrayList<SuplementopedidoBean>();
		 String pedidos="";
		 String detalle="";
		 boolean checked=false;
		 String codigoLocal="";
		 String codigoDetalle="";
		 String grupoDetalle="";	 
		 String mensaje="";
		 String fecha="";
		 String fechaHasta="";
		 String pagina_status="";
		 String respuestaLiberacion="";
		 String lineaDetalle="";		 
	}
	
}
