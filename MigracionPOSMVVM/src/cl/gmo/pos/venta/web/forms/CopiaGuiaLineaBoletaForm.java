package cl.gmo.pos.venta.web.forms;



import cl.gmo.pos.venta.utils.Constantes;

public class CopiaGuiaLineaBoletaForm {
	
	private String codigo=Constantes.STRING_BLANCO;
	private String descripcion=Constantes.STRING_BLANCO;
	private String cantidad=Constantes.STRING_BLANCO;
	private String precio=Constantes.STRING_BLANCO;
	private String descuento=Constantes.STRING_BLANCO;
	private String total=Constantes.STRING_BLANCO;
	private String encargo=Constantes.STRING_BLANCO;
	private String multioferta=Constantes.STRING_BLANCO;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMultioferta() {
		return multioferta;
	}
	public void setMultioferta(String multioferta) {
		this.multioferta = multioferta;
	}
	public String getEncargo() {
		return encargo;
	}
	public void setEncargo(String encargo) {
		this.encargo = encargo;
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
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
