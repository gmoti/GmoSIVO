package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;

public class BusquedaPresupuestosForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1777766218730643436L;

	private String cliente;
	private String presupuesto;
	private String fecha;
	private String agente;
	private ArrayList<AgenteBean> lista_agentes;
	private ArrayList<PresupuestosBean> lista_presupuestos;
	
	
	public ArrayList<PresupuestosBean> getLista_presupuestos() {
		return lista_presupuestos;
	}
	public void setLista_presupuestos(ArrayList<PresupuestosBean> lista_presupuestos) {
		this.lista_presupuestos = lista_presupuestos;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public ArrayList<AgenteBean> getLista_agentes() {
		return lista_agentes;
	}
	public void setLista_agentes(ArrayList<AgenteBean> lista_agentes) {
		this.lista_agentes = lista_agentes;
	}
	
	
}
