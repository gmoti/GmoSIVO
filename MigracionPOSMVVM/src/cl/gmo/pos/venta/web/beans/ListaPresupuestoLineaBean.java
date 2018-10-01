package cl.gmo.pos.venta.web.beans;

public class ListaPresupuestoLineaBean {
	private String codigo;
	private String codigoBarra;
	private String descripcion;
	private String cantidad;
	private String precioIva;
	private String descuento;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
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
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	
	
}
