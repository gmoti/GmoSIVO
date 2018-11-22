package cl.gmo.pos.venta.web.forms;

import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

public class CambioFolioForm extends GenericForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2804220246371081543L;
	
	private Integer desdeBoleta;
	private Integer hastaBoleta;
	private Integer desdeGuia;
	private Integer hastaGuia;
	private Integer desdeNota;
	private Integer hastaNota;
	private String habilitaCampo;
	private String accion;
	private String retornoMOdificacion;
	private String localErrorFolio;
	private String mensaje;
	
	
	//solo para ejemplo ajax
	
	private String codpostal;
	private String poblacion;
	private String provincia;
	
	public String getCodpostal() {
		return codpostal;
	}
	public void setCodpostal(String codpostal) {
		this.codpostal = codpostal;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	
	
	public String getRetornoMOdificacion() {
		return retornoMOdificacion;
	}
	public void setRetornoMOdificacion(String retornoMOdificacion) {
		this.retornoMOdificacion = retornoMOdificacion;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getHabilitaCampo() {
		return habilitaCampo;
	}
	public void setHabilitaCampo(String habilitaCampo) {
		this.habilitaCampo = habilitaCampo;
	}
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
	public String getLocalErrorFolio() {
		return localErrorFolio;
	}
	public void setLocalErrorFolio(String localErrorFolio) {
		this.localErrorFolio = localErrorFolio;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
	
}
