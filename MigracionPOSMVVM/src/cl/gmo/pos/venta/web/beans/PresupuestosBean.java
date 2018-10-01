package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class PresupuestosBean {
	
    private String articulo;
    private String descripcion;
	private String codigo;
    private String fecha;
    private String hora;
    private String id_cliente;
    private String forma_pago;
    private int cambio;
    private String divisa;
    private String serie;
    private int numero;
    private double dcto;
    private String notas;
    private String idioma;
    private String id_agente;
    private String nombre_agente;
    private ArrayList<ProductosBean> lista_productos;
    private String fecha_entrega;
    private String agente;
    private int cliente;
    private String cliente_completo;
    private String cerrado;
    private int porcentaje_descuento_max;
    private String convenio;
    private String convenio_det;
    
	
    
	
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public String getConvenio_det() {
		return convenio_det;
	}
	public void setConvenio_det(String convenio_det) {
		this.convenio_det = convenio_det;
	}
	public int getPorcentaje_descuento_max() {
		return porcentaje_descuento_max;
	}
	public void setPorcentaje_descuento_max(int porcentaje_descuento_max) {
		this.porcentaje_descuento_max = porcentaje_descuento_max;
	}
	public String getCerrado() {
		return cerrado;
	}
	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
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
	public double getDcto() {
		return dcto;
	}
	public void setDcto(double dcto) {
		this.dcto = dcto;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(String id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	public int getCambio() {
		return cambio;
	}
	public void setCambio(int cambio) {
		this.cambio = cambio;
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
	public String getId_agente() {
		return id_agente;
	}
	public void setId_agente(String id_agente) {
		this.id_agente = id_agente;
	}
	public String getNombre_agente() {
		return nombre_agente;
	}
	public void setNombre_agente(String nombre_agente) {
		this.nombre_agente = nombre_agente;
	}
	public ArrayList<ProductosBean> getLista_productos() {
		return lista_productos;
	}
	public void setLista_productos(ArrayList<ProductosBean> lista_productos) {
		this.lista_productos = lista_productos;
	}
	public String getFecha_entrega() {
		return fecha_entrega;
	}
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public int getCliente() {
		return cliente;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public String getCliente_completo() {
		return cliente_completo;
	}
	public void setCliente_completo(String cliente_completo) {
		this.cliente_completo = cliente_completo;
	}
	
}
