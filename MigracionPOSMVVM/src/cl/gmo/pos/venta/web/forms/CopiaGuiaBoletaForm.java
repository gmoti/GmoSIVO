package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;



import cl.gmo.pos.venta.utils.Constantes;

public class CopiaGuiaBoletaForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6632568641085706601L;
	private String define=Constantes.STRING_BLANCO;
	private String fecha=Constantes.STRING_BLANCO;
	private String cliente=Constantes.STRING_BLANCO;
	private String rut=Constantes.STRING_BLANCO;
	private String numeroAlbaran=Constantes.STRING_BLANCO;
	private String fechaPedido=Constantes.STRING_BLANCO;
	private String fechaEntrega=Constantes.STRING_BLANCO;
	private String hora=Constantes.STRING_BLANCO;
	private String tienda=Constantes.STRING_BLANCO;
	private String Vendedor=Constantes.STRING_BLANCO;
	private String numeroBoleta=Constantes.STRING_BLANCO;
	private String radio=Constantes.STRING_BLANCO;
	private String totalVenta=Constantes.STRING_BLANCO;
	private String totalAnticipo=Constantes.STRING_BLANCO;
	private String totalPagadoBoleta=Constantes.STRING_BLANCO;	
	private String caja = Constantes.STRING_BLANCO;
	private String tipo = Constantes.STRING_BLANCO;
	private String documento = Constantes.STRING_BLANCO;
	private ArrayList <CopiaGuiaLineaBoletaForm> lineas= new ArrayList <CopiaGuiaLineaBoletaForm> ();
	private String tipoVenta;
	
	
	public String getTipoVenta() {
		return tipoVenta;
	}
	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCaja() {
		return caja;
	}
	public void setCaja(String caja) {
		this.caja = caja;
	}
	public String getTotalPagadoBoleta() {
		return totalPagadoBoleta;
	}
	public void setTotalPagadoBoleta(String totalPagadoBoleta) {
		this.totalPagadoBoleta = totalPagadoBoleta;
	}
	public String getTotalAnticipo() {
		return totalAnticipo;
	}
	public void setTotalAnticipo(String totalAnticipo) {
		this.totalAnticipo = totalAnticipo;
	}
	public String getTotalVenta() {
		return totalVenta;
	}
	public void setTotalVenta(String totalVenta) {
		this.totalVenta = totalVenta;
	}
	public ArrayList<CopiaGuiaLineaBoletaForm> getLineas() {
		return lineas;
	}
	public void setLineas(ArrayList<CopiaGuiaLineaBoletaForm> lineas) {
		this.lineas = lineas;
	}
	public void deleteLineas() {
		lineas.clear();
		CopiaGuiaLineaBoletaForm vacio = new CopiaGuiaLineaBoletaForm();
		lineas.add(vacio);
	}
	public String getDefine() {
		return define;
	}
	public void setDefine(String define) {
		this.define = define;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNumeroAlbaran() {
		return numeroAlbaran;
	}
	public void setNumeroAlbaran(String numeroAlbaran) {
		this.numeroAlbaran = numeroAlbaran;
	}
	public String getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public String getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getTienda() {
		return tienda;
	}
	public void setTienda(String tienda) {
		this.tienda = tienda;
	}
	public String getVendedor() {
		return Vendedor;
	}
	public void setVendedor(String vendedor) {
		Vendedor = vendedor;
	}
	
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	public String getNumeroBoleta() {
		return numeroBoleta;
	}
	public void setNumeroBoleta(String numeroBoleta) {
		this.numeroBoleta = numeroBoleta;
	}

}
