package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class AlbaranBean {

	//DATOS UTILIZADOS EN LA BUSQUEDA
	private String codigo_albaran;
	private String fecha_albaran;
	private String hora_albaran;
	private String agente_albaran;
	private String fecha_pedido;
	private String fecha_entrega;
	private String nif_cliente;	
	private String dv_nif;
	
	//DATOS DE LA BASE DE DATOS.
	private String serie;
	private int numero;
	private String cliente;
	private String divisa;
	private String idioma;
	private double dto;
	private String fpago;
	private double cambio;
	private int numvtos;
	private int diaspri;
	private int plazo;
	private double anticipo;
	private String fechaant;
	private String tipoantic;
	private String notas;
	private String agcttes;
	private String facturado;
	private String facvtcb;
	private String asignado;
	private String enviado;
	private String envvtcb;
	private String tipoalb;
	private String retenido;
	private String fecgarant;
	private String atendido;
	private String enuso;
	private String tipoport;
	private String importport;
	private String agente2;
	private double dto2;
	private int prevision;
	private String suref;
	private String nombrecli;
	private String apellidocli;
	private String nifcli;
	private String confidencial;
	private String cuotafija;
	private String numtpv;
	private String montador;
	private String descargado;
	private double anticipodef;
	private double baseimp;
	private String tipodevo;
	private int numdevo;
	private String convenio;
	private String modificado;
	private String albmod;
	private String tipomotdev;
	private String devteso;
	private ArrayList<ProductosBean> lista_productos_albaran;
	
	private String identped;
	
	private String tipo_albaran;
		
	
	
	public String getIdentped() {
		return identped;
	}
	public void setIdentped(String identped) {
		this.identped = identped;
	}
	public String getTipo_albaran() {
		return tipo_albaran;
	}
	public void setTipo_albaran(String tipo_albaran) {
		this.tipo_albaran = tipo_albaran;
	}
	public String getApellidocli() {
		return apellidocli;
	}
	public void setApellidocli(String apellidocli) {
		this.apellidocli = apellidocli;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public double getDto() {
		return dto;
	}
	public void setDto(double dto) {
		this.dto = dto;
	}
	public String getFpago() {
		return fpago;
	}
	public void setFpago(String fpago) {
		this.fpago = fpago;
	}
	public double getCambio() {
		return cambio;
	}
	public void setCambio(double cambio) {
		this.cambio = cambio;
	}
	public int getNumvtos() {
		return numvtos;
	}
	public void setNumvtos(int numvtos) {
		this.numvtos = numvtos;
	}
	public int getDiaspri() {
		return diaspri;
	}
	public void setDiaspri(int diaspri) {
		this.diaspri = diaspri;
	}
	public int getPlazo() {
		return plazo;
	}
	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}
	public double getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(double anticipo) {
		this.anticipo = anticipo;
	}
	public String getFechaant() {
		return fechaant;
	}
	public void setFechaant(String fechaant) {
		this.fechaant = fechaant;
	}
	public String getTipoantic() {
		return tipoantic;
	}
	public void setTipoantic(String tipoantic) {
		this.tipoantic = tipoantic;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public String getAgcttes() {
		return agcttes;
	}
	public void setAgcttes(String agcttes) {
		this.agcttes = agcttes;
	}
	public String getFacturado() {
		return facturado;
	}
	public void setFacturado(String facturado) {
		this.facturado = facturado;
	}
	public String getFacvtcb() {
		return facvtcb;
	}
	public void setFacvtcb(String facvtcb) {
		this.facvtcb = facvtcb;
	}
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
	public String getEnviado() {
		return enviado;
	}
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	public String getEnvvtcb() {
		return envvtcb;
	}
	public void setEnvvtcb(String envvtcb) {
		this.envvtcb = envvtcb;
	}
	public String getTipoalb() {
		return tipoalb;
	}
	public void setTipoalb(String tipoalb) {
		this.tipoalb = tipoalb;
	}
	public String getRetenido() {
		return retenido;
	}
	public void setRetenido(String retenido) {
		this.retenido = retenido;
	}
	public String getFecgarant() {
		return fecgarant;
	}
	public void setFecgarant(String fecgarant) {
		this.fecgarant = fecgarant;
	}
	public String getAtendido() {
		return atendido;
	}
	public void setAtendido(String atendido) {
		this.atendido = atendido;
	}
	public String getEnuso() {
		return enuso;
	}
	public void setEnuso(String enuso) {
		this.enuso = enuso;
	}
	public String getTipoport() {
		return tipoport;
	}
	public void setTipoport(String tipoport) {
		this.tipoport = tipoport;
	}
	public String getImportport() {
		return importport;
	}
	public void setImportport(String importport) {
		this.importport = importport;
	}
	public String getAgente2() {
		return agente2;
	}
	public void setAgente2(String agente2) {
		this.agente2 = agente2;
	}
	public double getDto2() {
		return dto2;
	}
	public void setDto2(double dto2) {
		this.dto2 = dto2;
	}
	public int getPrevision() {
		return prevision;
	}
	public void setPrevision(int prevision) {
		this.prevision = prevision;
	}
	public String getSuref() {
		return suref;
	}
	public void setSuref(String suref) {
		this.suref = suref;
	}
	public String getNombrecli() {
		return nombrecli;
	}
	public void setNombrecli(String nombrecli) {
		this.nombrecli = nombrecli;
	}
	public String getNifcli() {
		return nifcli;
	}
	public void setNifcli(String nifcli) {
		this.nifcli = nifcli;
	}
	public String getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(String confidencial) {
		this.confidencial = confidencial;
	}
	public String getCuotafija() {
		return cuotafija;
	}
	public void setCuotafija(String cuotafija) {
		this.cuotafija = cuotafija;
	}
	public String getNumtpv() {
		return numtpv;
	}
	public void setNumtpv(String numtpv) {
		this.numtpv = numtpv;
	}
	public String getMontador() {
		return montador;
	}
	public void setMontador(String montador) {
		this.montador = montador;
	}
	public String getDescargado() {
		return descargado;
	}
	public void setDescargado(String descargado) {
		this.descargado = descargado;
	}
	public double getAnticipodef() {
		return anticipodef;
	}
	public void setAnticipodef(double anticipodef) {
		this.anticipodef = anticipodef;
	}
	public double getBaseimp() {
		return baseimp;
	}
	public void setBaseimp(double baseimp) {
		this.baseimp = baseimp;
	}
	public String getTipodevo() {
		return tipodevo;
	}
	public void setTipodevo(String tipodevo) {
		this.tipodevo = tipodevo;
	}
	public int getNumdevo() {
		return numdevo;
	}
	public void setNumdevo(int numdevo) {
		this.numdevo = numdevo;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public String getModificado() {
		return modificado;
	}
	public void setModificado(String modificado) {
		this.modificado = modificado;
	}
	public String getAlbmod() {
		return albmod;
	}
	public void setAlbmod(String albmod) {
		this.albmod = albmod;
	}
	public String getTipomotdev() {
		return tipomotdev;
	}
	public void setTipomotdev(String tipomotdev) {
		this.tipomotdev = tipomotdev;
	}
	public String getDevteso() {
		return devteso;
	}
	public void setDevteso(String devteso) {
		this.devteso = devteso;
	}
	public String getDv_nif() {
		return dv_nif;
	}
	public void setDv_nif(String dv_nif) {
		this.dv_nif = dv_nif;
	}
	public String getNif_cliente() {
		return nif_cliente;
	}
	public void setNif_cliente(String nif_cliente) {
		this.nif_cliente = nif_cliente;
	}
	public String getFecha_pedido() {
		return fecha_pedido;
	}
	public void setFecha_pedido(String fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}
	public String getFecha_entrega() {
		return fecha_entrega;
	}
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	public String getCodigo_albaran() {
		return codigo_albaran;
	}
	public void setCodigo_albaran(String codigo_albaran) {
		this.codigo_albaran = codigo_albaran;
	}
	public String getFecha_albaran() {
		return fecha_albaran;
	}
	public void setFecha_albaran(String fecha_albaran) {
		this.fecha_albaran = fecha_albaran;
	}
	public String getHora_albaran() {
		return hora_albaran;
	}
	public void setHora_albaran(String hora_albaran) {
		this.hora_albaran = hora_albaran;
	}
	public String getAgente_albaran() {
		return agente_albaran;
	}
	public void setAgente_albaran(String agente_albaran) {
		this.agente_albaran = agente_albaran;
	}
	public ArrayList<ProductosBean> getLista_productos_albaran() {
		return lista_productos_albaran;
	}
	public void setLista_productos_albaran(
			ArrayList<ProductosBean> lista_productos_albaran) {
		this.lista_productos_albaran = lista_productos_albaran;
	}	
}
