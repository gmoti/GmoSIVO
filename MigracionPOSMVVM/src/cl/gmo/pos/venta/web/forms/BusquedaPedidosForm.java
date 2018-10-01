package cl.gmo.pos.venta.web.forms;

import java.io.Serializable;
import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;

public class BusquedaPedidosForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -455241037982553869L;
	
	private String cliente;
	private String encargo;
	private String fecha;
	private String agente;
	private ArrayList<AgenteBean> lista_agentes;
	private ArrayList<PedidosPendientesBean> lista_pedidos;
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getEncargo() {
		return encargo;
	}
	public void setEncargo(String encargo) {
		this.encargo = encargo;
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
	public ArrayList<PedidosPendientesBean> getLista_pedidos() {
		return lista_pedidos;
	}
	public void setLista_pedidos(ArrayList<PedidosPendientesBean> lista_pedidos) {
		this.lista_pedidos = lista_pedidos;
	}
	
	

}
