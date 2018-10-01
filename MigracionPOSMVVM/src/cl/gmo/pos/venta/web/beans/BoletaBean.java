package cl.gmo.pos.venta.web.beans;

public class BoletaBean {

	private String pedvtcb;
	private int numero;
	private int importe;
	private int importedef;
	private String tipo;
	private String fecha;
	private String local;
	private String nif;
	private String dv;
	
	public String getPedvtcb() {
		return pedvtcb;
	}
	public void setPedvtcb(String pedvtcb) {
		this.pedvtcb = pedvtcb;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getImporte() {
		return importe;
	}
	public void setImporte(int importe) {
		this.importe = importe;
	}
	public int getImportedef() {
		return importedef;
	}
	public void setImportedef(int importedef) {
		this.importedef = importedef;
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
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}

	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
}
