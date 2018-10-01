package cl.gmo.pos.venta.web.beans;

public class ListadoBoletasBean {
	
	private String numero="";
	private String codigo="";
	private String tipo="";
	private String fecha="";
	private int importe;
	
	
	public int getImporte() {
		return importe;
	}
	public void setImporte(int importe) {
		this.importe = importe;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	

}
