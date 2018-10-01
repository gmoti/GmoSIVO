package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.ProductosBean;

public class FichaClienteForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8392150965728412289L;
	private String cliente;
	private ArrayList<ProductosBean> listaProductos;
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public ArrayList<ProductosBean> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(ArrayList<ProductosBean> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	

}
