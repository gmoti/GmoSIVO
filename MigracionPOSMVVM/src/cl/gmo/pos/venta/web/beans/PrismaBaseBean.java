package cl.gmo.pos.venta.web.beans;

public class PrismaBaseBean {
	private String codigo;
	private String descripcion;
	
	public PrismaBaseBean() {
		codigo="";
		descripcion="";
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
	
	
}
