package cl.gmo.pos.venta.web.beans;

public class PrismaCantidadBean {
	
	private int codigo;
	private String descripcion;
	
	public PrismaCantidadBean() {
		codigo=0;
		descripcion="";
	}
	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
