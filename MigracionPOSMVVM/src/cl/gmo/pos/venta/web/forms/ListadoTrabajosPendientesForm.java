package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.SucursalesBean;
import cl.gmo.pos.venta.web.beans.TrabajosPendientesBean;

public class ListadoTrabajosPendientesForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5806735215551417023L;
	private String codigo;
	private String cliente;
	private String divisa;
	private String fechaPedidoIni;
	private String fechaPedidoTer;
	private String formaPago;
	private String local;
	private String cerrado;// (determina si del presupuesto se genero una venta pedido).
	private String listadoDetallado;
	private String anulado;
	private String agente;
	private String tipoPedido;
	private ArrayList<TrabajosPendientesBean> listaPendientes;
	private ArrayList<DivisaBean> listaDivisas;
	private ArrayList<SucursalesBean> colSucursales;
	private String respuesta;
	
	
	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public ArrayList<DivisaBean> getListaDivisas() {
		return listaDivisas;
	}
	public void setListaDivisas(ArrayList<DivisaBean> listaDivisas) {
		this.listaDivisas = listaDivisas;
	}
	public ArrayList<SucursalesBean> getColSucursales() {
		return colSucursales;
	}
	public void setColSucursales(ArrayList<SucursalesBean> colSucursales) {
		this.colSucursales = colSucursales;
	}
	public ArrayList<TrabajosPendientesBean> getListaPendientes() {
		return listaPendientes;
	}
	public void setListaPendientes(ArrayList<TrabajosPendientesBean> listaPendientes) {
		this.listaPendientes = listaPendientes;
	}
	public String getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public String getFechaPedidoIni() {
		return fechaPedidoIni;
	}
	public void setFechaPedidoIni(String fechaPedidoIni) {
		this.fechaPedidoIni = fechaPedidoIni;
	}
	public String getFechaPedidoTer() {
		return fechaPedidoTer;
	}
	public void setFechaPedidoTer(String fechaPedidoTer) {
		this.fechaPedidoTer = fechaPedidoTer;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getCerrado() {
		return cerrado;
	}
	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
	}
	public String getListadoDetallado() {
		return listadoDetallado;
	}
	public void setListadoDetallado(String listadoDetallado) {
		this.listadoDetallado = listadoDetallado;
	}
	public String getAnulado() {
		return anulado;
	}
	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}

	
}
