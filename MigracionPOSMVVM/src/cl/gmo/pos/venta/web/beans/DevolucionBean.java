package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class DevolucionBean {

	private String codigo_venta;
	private String serie_vta;
	private String accion="";
	private String codigo1="";
	private String codigo2="";
	private String fecha="";
	private String hora="";
	private String tipoAlbaran="";	
	private String boleta_guia;
	private String numero_boleta_guia;
	private String codigo_cliente;
	private String nombreCliente="";
	private String apellido_cliente;
	private String kodak;
	private String idioma="";	
	private String cambio="";	
	private String agente="";
	private String modificado;
	private String modificado_text;
	private String divisa="";
	private String montador="";
	private String facturado="";
	private String facturado_text ="";
	private String motivo="";
	private String formaPago="";
	private String confidencial="";
	private String fecha_garantia;
	private String convenio = "";
	private String importePend ="";
	private String dto;
	private ArrayList<ProductosBean> lista_productos;
	private String existeBoleta="";
	private boolean respuestaDevolucion;
	private String mensajeRetornoSp;
	
	
	
	public String getMensajeRetornoSp() {
		return mensajeRetornoSp;
	}
	public void setMensajeRetornoSp(String mensajeRetornoSp) {
		this.mensajeRetornoSp = mensajeRetornoSp;
	}
	public boolean isRespuestaDevolucion() {
		return respuestaDevolucion;
	}
	public void setRespuestaDevolucion(boolean respuestaDevolucion) {
		this.respuestaDevolucion = respuestaDevolucion;
	}
	public String getExisteBoleta() {
		return existeBoleta;
	}
	public void setExisteBoleta(String existeBoleta) {
		this.existeBoleta = existeBoleta;
	}
	public ArrayList<ProductosBean> getLista_productos() {
		return lista_productos;
	}
	public void setLista_productos(ArrayList<ProductosBean> lista_productos) {
		this.lista_productos = lista_productos;
	}
	public String getDto() {
		return dto;
	}
	public void setDto(String dto) {
		this.dto = dto;
	}
	public String getApellido_cliente() {
		return apellido_cliente;
	}
	public void setApellido_cliente(String apellido_cliente) {
		this.apellido_cliente = apellido_cliente;
	}
	public String getSerie_vta() {
		return serie_vta;
	}
	public void setSerie_vta(String serie_vta) {
		this.serie_vta = serie_vta;
	}
	public String getCodigo_venta() {
		return codigo_venta;
	}
	public void setCodigo_venta(String codigo_venta) {
		this.codigo_venta = codigo_venta;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getCodigo1() {
		return codigo1;
	}
	public void setCodigo1(String codigo1) {
		this.codigo1 = codigo1;
	}
	public String getCodigo2() {
		return codigo2;
	}
	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getTipoAlbaran() {
		return tipoAlbaran;
	}
	public void setTipoAlbaran(String tipoAlbaran) {
		this.tipoAlbaran = tipoAlbaran;
	}
	public String getBoleta_guia() {
		return boleta_guia;
	}
	public void setBoleta_guia(String boleta_guia) {
		this.boleta_guia = boleta_guia;
	}
	public String getNumero_boleta_guia() {
		return numero_boleta_guia;
	}
	public void setNumero_boleta_guia(String numero_boleta_guia) {
		this.numero_boleta_guia = numero_boleta_guia;
	}
	public String getCodigo_cliente() {
		return codigo_cliente;
	}
	public void setCodigo_cliente(String codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getKodak() {
		return kodak;
	}
	public void setKodak(String kodak) {
		this.kodak = kodak;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getCambio() {
		return cambio;
	}
	public void setCambio(String cambio) {
		this.cambio = cambio;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getModificado() {
		return modificado;
	}
	public void setModificado(String modificado) {
		this.modificado = modificado;
	}
	public String getModificado_text() {
		return modificado_text;
	}
	public void setModificado_text(String modificado_text) {
		this.modificado_text = modificado_text;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getMontador() {
		return montador;
	}
	public void setMontador(String montador) {
		this.montador = montador;
	}
	public String getFacturado() {
		return facturado;
	}
	public void setFacturado(String facturado) {
		this.facturado = facturado;
	}
	public String getFacturado_text() {
		return facturado_text;
	}
	public void setFacturado_text(String facturado_text) {
		this.facturado_text = facturado_text;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(String confidencial) {
		this.confidencial = confidencial;
	}
	
	public String getFecha_garantia() {
		return fecha_garantia;
	}
	public void setFecha_garantia(String fecha_garantia) {
		this.fecha_garantia = fecha_garantia;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public String getImportePend() {
		return importePend;
	}
	public void setImportePend(String importePend) {
		this.importePend = importePend;
	}
	
	
	
	
}
