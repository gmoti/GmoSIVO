package cl.gmo.pos.venta.web.beans;

public class BusquedaArticuloGralBean {
	private String precio="";
	private String precioIva="";
	private String tarifa="";
	private String articulo="";
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getPrecioIva() {
		return precioIva;
	}
	public void setPrecioIva(String precioIva) {
		this.precioIva = precioIva;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}
	
	
}
