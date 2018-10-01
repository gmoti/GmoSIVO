package cl.gmo.pos.venta.web.beans;

import java.sql.Date;
import java.util.ArrayList;

public class PresupuestoBean {
	
	private String numero="";
	private String fecha=""; 
	private String agente="";
	private String nombres="";
	private String apellido="";
	private double importe;
	private String descuento="";
	private String forma_pago="";
	private String total="";
	private ArrayList<ListaPresupuestoLineaBean> lineas;
	private String codigo="";
	private String descripcion="";
	private String cantidad="";
	private String precioIva="";
	private String descuentoArt="";
	private String textoTotal="";
	private String linea="";
	private String nif_cliente="";
	
	
	
	public String getNif_cliente() {
		return nif_cliente;
	}
	public void setNif_cliente(String nif_cliente) {
		this.nif_cliente = nif_cliente;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
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
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecioIva() {
		return precioIva;
	}
	public void setPrecioIva(String precioIva) {
		this.precioIva = precioIva;
	}
	public String getDescuentoArt() {
		return descuentoArt;
	}
	public void setDescuentoArt(String descuentoArt) {
		this.descuentoArt = descuentoArt;
	}
	public String getTextoTotal() {
		return textoTotal;
	}
	public void setTextoTotal(String textoTotal) {
		this.textoTotal = textoTotal;
	}
	public ArrayList<ListaPresupuestoLineaBean> getLineas() {
		return lineas;
	}
	public void setLineas(ArrayList<ListaPresupuestoLineaBean> lineas) {
		this.lineas = lineas;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	
	

}
