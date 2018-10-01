package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class TrabajosPendientesBean {
	private String serie="";
	private String fecha="";
	private String numeroCaja="";
	private String cliente="";
	private String nombre="";
	private String apellidos="";
	private String descuento1="";
	private String fPago="";
	private String albaran="";
	private ArrayList<ListadosTrabajosPendientesLineaBean> lineas;
	private String articulo="";
	private String descripcion="";
	private String cantidad="";
	private String precio="";
	private String descuento2="";
	private String notas="";
	private String total="0";
	private String numeroBoleta="";
	private String txtTotal="";
	private String txtNumeroBoleta="";
	
	
	
	
	public String getTxtTotal() {
		return txtTotal;
	}
	public void setTxtTotal(String txtTotal) {
		this.txtTotal = txtTotal;
	}
	public String getTxtNumeroBoleta() {
		return txtNumeroBoleta;
	}
	public void setTxtNumeroBoleta(String txtNumeroBoleta) {
		this.txtNumeroBoleta = txtNumeroBoleta;
	}
	public String getNumeroBoleta() {
		return numeroBoleta;
	}
	public void setNumeroBoleta(String numeroBoleta) {
		this.numeroBoleta = numeroBoleta;
	}
	public String getAlbaran() {
		return albaran;
	}
	public void setAlbaran(String albaran) {
		this.albaran = albaran;
	}
	public ArrayList<ListadosTrabajosPendientesLineaBean> getLineas() {
		return lineas;
	}
	public void setLineas(ArrayList<ListadosTrabajosPendientesLineaBean> lineas) {
		this.lineas = lineas;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNumeroCaja() {
		return numeroCaja;
	}
	public void setNumeroCaja(String numeroCaja) {
		this.numeroCaja = numeroCaja;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDescuento1() {
		return descuento1;
	}
	public void setDescuento1(String descuento1) {
		this.descuento1 = descuento1;
	}
	public String getfPago() {
		return fPago;
	}
	public void setfPago(String fPago) {
		this.fPago = fPago;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
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
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getDescuento2() {
		return descuento2;
	}
	public void setDescuento2(String descuento2) {
		this.descuento2 = descuento2;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	
	
	
	
}
