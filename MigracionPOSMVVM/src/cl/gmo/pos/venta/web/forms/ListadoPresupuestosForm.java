package cl.gmo.pos.venta.web.forms;

import java.sql.Date;
import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.PresupuestoBean;

public class ListadoPresupuestosForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6404694682434278282L;
	private int codigo;
	private String agente;
	private Date fecha;
	private String fechaInicio;
	private String fechaTermino;
	private String divisa;
	private String cerrado;
	private String forma_pago;
	private String cliente;
	private String anulado;
	private ArrayList<PresupuestoBean> listaPresupuestos;
	private  ArrayList<DivisaBean> listaDivisas;
	private ArrayList<FormaPagoBean>  listaFormasPago;
	
	
	public void ListadoPresupuestosForm() {
		
		this.codigo=0;
		this.agente="";
		this.fecha=new Date(System.currentTimeMillis());
		this.fechaInicio="";
		this.fechaTermino="";
		this.divisa="";
		this.cerrado="";
		this.forma_pago="";
		this.cliente="";
		this.anulado="";
		
		listaPresupuestos=null;
		listaDivisas=null;
		listaFormasPago=null;
		
	}
	
	
	
	public ArrayList<DivisaBean> getListaDivisas() {
		return listaDivisas;
	}
	public void setListaDivisas(ArrayList<DivisaBean> listaDivisas) {
		this.listaDivisas = listaDivisas;
	}
	public ArrayList<FormaPagoBean> getListaFormasPago() {
		return listaFormasPago;
	}
	public void setListaFormasPago(ArrayList<FormaPagoBean> listaFormasPago) {
		this.listaFormasPago = listaFormasPago;
	}	
	
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaTermino() {
		return fechaTermino;
	}
	public void setFechaTermino(String fechaTermino) {
		this.fechaTermino = fechaTermino;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getCerrado() {
		return cerrado;
	}
	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getAnulado() {
		return anulado;
	}
	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}
	public ArrayList<PresupuestoBean> getListaPresupuestos() {
		return listaPresupuestos;
	}
	public void setListaPresupuestos(ArrayList<PresupuestoBean> listaPresupuestos) {
		this.listaPresupuestos = listaPresupuestos;
	}	
	
}
