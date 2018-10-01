package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;

public class PedidosPendientesBean {
	private String agente="";
	private String fechaPedido="";
	private String fechasEntragas="";
	private String anticipo="";
	private String cdg="";
	private String codigoP="";
	private String forma_pagoP="";
	private String tipo_pedidoP="";
	private String sobreP="";
	private String agenteP="";
	private String divisaP="";
	private String idiomaP="";
	private String convenioP="";
	private String promocionP="";
	private String fechaP="";
	private String horaP="";
	private String cambioP="";
	private ArrayList<ProductosBean> listaProduc = new ArrayList<ProductosBean>();
	private int descuento = Constantes.INT_CERO;
	private String cliente = null;
	private String convenio_det = Constantes.STRING_BLANCO;
	private String pedvtant = Constantes.STRING_BLANCO;
	private String cerrado;
	private String nota;
	private String local= Constantes.STRING_BLANCO;
	private String isapre="";
	
	
	public String getIsapre() {
		return isapre;
	}
	public void setIsapre(String isapre) {
		this.isapre = isapre;
	}
	public String getCerrado() {
		return cerrado;
	}
	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
	}
	public String getPedvtant() {
		return pedvtant;
	}
	public void setPedvtant(String pedvtant) {
		this.pedvtant = pedvtant;
	}
	public String getConvenio_det() {
		return convenio_det;
	}
	public void setConvenio_det(String convenio_det) {
		this.convenio_det = convenio_det;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	public void addListaProduc(ProductosBean producto) {
		this.listaProduc.add(producto);
	}
	public ArrayList<ProductosBean> getListaProduc() {
		return listaProduc;
	}
	public void setListaProduc(ArrayList<ProductosBean> listaProduc) {
		this.listaProduc = listaProduc;
	}
	public String getCodigoP() {
		return codigoP;
	}
	public void setCodigoP(String codigoP) {
		this.codigoP = codigoP;
	}
	public String getForma_pagoP() {
		return forma_pagoP;
	}
	public void setForma_pagoP(String forma_pagoP) {
		this.forma_pagoP = forma_pagoP;
	}
	public String getTipo_pedidoP() {
		return tipo_pedidoP;
	}
	public void setTipo_pedidoP(String tipo_pedidoP) {
		this.tipo_pedidoP = tipo_pedidoP;
	}
	public String getSobreP() {
		return sobreP;
	}
	public void setSobreP(String sobreP) {
		this.sobreP = sobreP;
	}
	public String getAgenteP() {
		return agenteP;
	}
	public void setAgenteP(String agenteP) {
		this.agenteP = agenteP;
	}
	public String getDivisaP() {
		return divisaP;
	}
	public void setDivisaP(String divisaP) {
		this.divisaP = divisaP;
	}
	public String getIdiomaP() {
		return idiomaP;
	}
	public void setIdiomaP(String idiomaP) {
		this.idiomaP = idiomaP;
	}
	public String getConvenioP() {
		return convenioP;
	}
	public void setConvenioP(String convenioP) {
		this.convenioP = convenioP;
	}
	public String getPromocionP() {
		return promocionP;
	}
	public void setPromocionP(String promocionP) {
		this.promocionP = promocionP;
	}
	public String getFechaP() {
		return fechaP;
	}
	public void setFechaP(String fechaP) {
		this.fechaP = fechaP;
	}
	public String getHoraP() {
		return horaP;
	}
	public void setHoraP(String horaP) {
		this.horaP = horaP;
	}
	public String getCambioP() {
		return cambioP;
	}
	public void setCambioP(String cambioP) {
		this.cambioP = cambioP;
	}
	public String getCdg() {
		return cdg;
	}
	public void setCdg(String cdg) {
		this.cdg = cdg;
	}
	public String getFechasEntragas() {
		return fechasEntragas;
	}
	public void setFechasEntragas(String fechasEntragas) {
		this.fechasEntragas = fechasEntragas;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(String anticipo) {
		this.anticipo = anticipo;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
	
}
