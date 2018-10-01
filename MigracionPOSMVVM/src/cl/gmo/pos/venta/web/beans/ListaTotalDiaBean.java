package cl.gmo.pos.venta.web.beans;

import cl.gmo.pos.venta.utils.Utils;

public class ListaTotalDiaBean {
	Utils util = new Utils();
	
	private String codigo="";
	private String tipoAgente="";
	private String total="";
	private String cobrado="";
	private String fPagado="";
	private String numeroDoc="";
	private String tipo="";
	private String montoDoc="";
	private String texto="";
	private String linea="";
	private int cobrado_num=0;
	private int total_num=0;
	private String fecha = "";
	
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getMontoDoc() {
		return montoDoc;
	}
	public void setMontoDoc(String montoDoc) {
		this.montoDoc = montoDoc;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getCobrado_num() {
		return cobrado_num;
	}
	public void setCobrado_num(int cobrado_num) {
		this.cobrado_num = cobrado_num;
	}
	public int getTotal_num() {
		return total_num;
	}
	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
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
	public String getTipoAgente() {
		return tipoAgente;
	}
	public void setTipoAgente(String tipoAgente) {
		this.tipoAgente = tipoAgente;
	}
	public String getCobrado() {
		return cobrado;
	}
	public void setCobrado (String cobrado) {
		this.cobrado = cobrado;
}
	public String getfPagado() {
		return fPagado;
	}
	public void setfPagado(String fPagado) {
		this.fPagado = fPagado;
	}
	public String getNumeroDoc() {
		return numeroDoc;
	}
	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setCobradoFormat(String cobrado) {
		this.cobrado = cobrado;
		
	}
	public void setTotalFormat(String total) {
		this.total = total;
		
	}
	
}
