package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ListaTotalDiaBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;


public class ListadoTotalDiaForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4611890091870243728L;
	private String fecha_inicio;
	private String fecha_fin;
	private String numero_tienda;
	private int numero_caja;
	private String divisa;
	private String forma_pago;
	private String confidencial;
	private int cantidad;
	private int numero_movimientos = 0;
	private double total_final;
	private double total_descuentos;
	private double total_pendiente;
	private double total_recibido;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaVenta;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaEncargo;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaEntrega;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaDevolucion;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaAnticipo;
	private String error = Constantes.STRING_ERROR;
	
	
	
	public int getNumero_movimientos() {
		return numero_movimientos;
	}
	public void setNumero_movimientos(int numero_movimientos) {
		this.numero_movimientos = numero_movimientos;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaVenta() {
		return listaTotalDiaVenta;
	}
	public void setListaTotalDiaVenta(
			ArrayList<ListaTotalDiaBean> listaTotalDiaVenta) {
		this.listaTotalDiaVenta = listaTotalDiaVenta;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaEncargo() {
		return listaTotalDiaEncargo;
	}
	public void setListaTotalDiaEncargo(
			ArrayList<ListaTotalDiaBean> listaTotalDiaEncargo) {
		this.listaTotalDiaEncargo = listaTotalDiaEncargo;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaEntrega() {
		return listaTotalDiaEntrega;
	}
	public void setListaTotalDiaEntrega(
			ArrayList<ListaTotalDiaBean> listaTotalDiaEntrega) {
		this.listaTotalDiaEntrega = listaTotalDiaEntrega;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaDevolucion() {
		return listaTotalDiaDevolucion;
	}
	public void setListaTotalDiaDevolucion(
			ArrayList<ListaTotalDiaBean> listaTotalDiaDevolucion) {
		this.listaTotalDiaDevolucion = listaTotalDiaDevolucion;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaAnticipo() {
		return listaTotalDiaAnticipo;
	}
	public void setListaTotalDiaAnticipo(
			ArrayList<ListaTotalDiaBean> listaTotalDiaAnticipo) {
		this.listaTotalDiaAnticipo = listaTotalDiaAnticipo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	public String getNumero_tienda() {
		return numero_tienda;
	}
	public void setNumero_tienda(String numero_tienda) {
		this.numero_tienda = numero_tienda;
	}
	public int getNumero_caja() {
		return numero_caja;
	}
	public void setNumero_caja(int numero_caja) {
		this.numero_caja = numero_caja;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	public String getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(String confidencial) {
		this.confidencial = confidencial;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getTotal_final() {
		return total_final;
	}
	public void setTotal_final(double total_final) {
		this.total_final = total_final;
	}
	public double getTotal_descuentos() {
		return total_descuentos;
	}
	public void setTotal_descuentos(double total_descuentos) {
		this.total_descuentos = total_descuentos;
	}
	public double getTotal_pendiente() {
		return total_pendiente;
	}
	public void setTotal_pendiente(double total_pendiente) {
		this.total_pendiente = total_pendiente;
	}
	public double getTotal_recibido() {
		return total_recibido;
	}
	public void setTotal_recibido(double total_recibido) {
		this.total_recibido = total_recibido;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
}
