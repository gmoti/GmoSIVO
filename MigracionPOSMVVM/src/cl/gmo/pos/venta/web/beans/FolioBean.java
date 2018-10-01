package cl.gmo.pos.venta.web.beans;

public class FolioBean {
	
	private Integer desdeBoleta;
	private Integer hastaBoleta;
	private Integer desdeGuia;
	private Integer hastaGuia;
	private Integer desdeNota;
	private Integer hastaNota;
	private String respuesta;
	private String tipoFolio;
	private String localErrorFolio;
	
	
	public Integer getDesdeBoleta() {
		return desdeBoleta;
	}
	public void setDesdeBoleta(Integer desdeBoleta) {
		this.desdeBoleta = desdeBoleta;
	}
	public Integer getHastaBoleta() {
		return hastaBoleta;
	}
	public void setHastaBoleta(Integer hastaBoleta) {
		this.hastaBoleta = hastaBoleta;
	}
	public Integer getDesdeGuia() {
		return desdeGuia;
	}
	public void setDesdeGuia(Integer desdeGuia) {
		this.desdeGuia = desdeGuia;
	}
	public Integer getHastaGuia() {
		return hastaGuia;
	}
	public void setHastaGuia(Integer hastaGuia) {
		this.hastaGuia = hastaGuia;
	}
	public Integer getDesdeNota() {
		return desdeNota;
	}
	public void setDesdeNota(Integer desdeNota) {
		this.desdeNota = desdeNota;
	}
	public Integer getHastaNota() {
		return hastaNota;
	}
	public void setHastaNota(Integer hastaNota) {
		this.hastaNota = hastaNota;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getTipoFolio() {
		return tipoFolio;
	}
	public void setTipoFolio(String tipoFolio) {
		this.tipoFolio = tipoFolio;
	}
	public String getLocalErrorFolio() {
		return localErrorFolio;
	}
	public void setLocalErrorFolio(String localErrorFolio) {
		this.localErrorFolio = localErrorFolio;
	}
	
	
}
