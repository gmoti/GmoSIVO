package cl.gmo.pos.venta.web.beans;

import cl.gmo.pos.venta.utils.Constantes;

public class PagoBean{
	
	private String cod_venta = "";
    private String forma_pago = ""; 
    private String detalle_forma_pago="";
    private int cantidad = 0;
    private String fecha = ""; 
    private String divisa = ""; 
    private int cambio = 0;
    private int cod_caja = 0; 
    private int cantidad_divisa = 0; 
    private String devolucion = "";
    private String confidencial = ""; 
    private String agente = ""; 
    private String numero_bono = "";
    private double descuento = 0.0;
    private int v_a_pagar = 0;
    private boolean tiene_doc = false;
    private String  rut_vs ="";
    
    public String getRut_vs() {
		return rut_vs;
	}
	public void setRut_vs(String rut_vs) {
		this.rut_vs = rut_vs;
	}
	public String getMonto_vs() {
		return monto_vs;
	}
	public void setMonto_vs(String monto_vs) {
		this.monto_vs = monto_vs;
	}
	private String monto_vs ="";		
    
	public boolean isTiene_doc() {
		return tiene_doc;
	}
	public void setTiene_doc(boolean tiene_doc) {
		this.tiene_doc = tiene_doc;
	}
	public String getDetalle_forma_pago() {
		return detalle_forma_pago;
	}
	public void setDetalle_forma_pago(String detalle_forma_pago) {
		this.detalle_forma_pago = detalle_forma_pago;
	}
	public int getV_a_pagar() {
		return v_a_pagar;
	}
	public void setV_a_pagar(int v_a_pagar) {
		this.v_a_pagar = v_a_pagar;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public String getCod_venta() {
		return cod_venta;
	}
	public void setCod_venta(String cod_venta) {
		this.cod_venta = cod_venta;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public int getCambio() {
		return cambio;
	}
	public void setCambio(int cambio) {
		this.cambio = cambio;
	}
	public int getCod_caja() {
		return cod_caja;
	}
	public void setCod_caja(int cod_caja) {
		this.cod_caja = cod_caja;
	}
	public int getCantidad_divisa() {
		return cantidad_divisa;
	}
	public void setCantidad_divisa(int cantidad_divisa) {
		this.cantidad_divisa = cantidad_divisa;
	}
	public String getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(String devolucion) {
		this.devolucion = devolucion;
	}
	public String getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(String confidencial) {
		this.confidencial = confidencial;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getNumero_bono() {
		return numero_bono;
	}
	public void setNumero_bono(String numero_bono) {
		this.numero_bono = numero_bono;
	}

}
