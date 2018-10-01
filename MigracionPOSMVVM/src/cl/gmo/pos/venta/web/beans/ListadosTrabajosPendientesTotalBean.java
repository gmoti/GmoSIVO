package cl.gmo.pos.venta.web.beans;

public class ListadosTrabajosPendientesTotalBean {
	private String total="";
	private String codigoTotal="";
	private String nota="";
	private String numeroBoleta="";
	
	
	public String getNumeroBoleta() {
		return numeroBoleta;
	}
	public void setNumeroBoleta(String numeroBoleta) {
		this.numeroBoleta = numeroBoleta;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCodigoTotal() {
		return codigoTotal;
	}
	public void setCodigoTotal(String codigoTotal) {
		this.codigoTotal = codigoTotal;
	}

}
