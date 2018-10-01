package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class ListasTotalesDiaBean {
	private ArrayList<ListaTotalDiaBean> listaTotalDiaVenta;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaEncargo;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaEntrega;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaDevolucion;
	private ArrayList<ListaTotalDiaBean> listaTotalDiaAnticipo;
	private int numero_movimientos = 0;
	
	
	
	public int getNumero_movimientos() {
		return numero_movimientos;
	}
	public void setNumero_movimientos(int numero_movimientos) {
		this.numero_movimientos = numero_movimientos;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaVenta() {
		return listaTotalDiaVenta;
	}
	public void setListaTotalDiaVenta(
			ArrayList<ListaTotalDiaBean> listaTotalDiaVenta) {
		this.listaTotalDiaVenta = listaTotalDiaVenta;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaEncargo() {
		return listaTotalDiaEncargo;
	}
	public void setListaTotalDiaEncargo(
			ArrayList<ListaTotalDiaBean> listaTotalDiaEncargo) {
		this.listaTotalDiaEncargo = listaTotalDiaEncargo;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaEntrega() {
		return listaTotalDiaEntrega;
	}
	public void setListaTotalDiaEntrega(
			ArrayList<ListaTotalDiaBean> listaTotalDiaEntrega) {
		this.listaTotalDiaEntrega = listaTotalDiaEntrega;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaDevolucion() {
		return listaTotalDiaDevolucion;
	}
	public void setListaTotalDiaDevolucion(
			ArrayList<ListaTotalDiaBean> listaTotalDiaDevolucion) {
		this.listaTotalDiaDevolucion = listaTotalDiaDevolucion;
	}
	public ArrayList<ListaTotalDiaBean> getListaTotalDiaAnticipo() {
		return listaTotalDiaAnticipo;
	}
	public void setListaTotalDiaAnticipo(
			ArrayList<ListaTotalDiaBean> listaTotalDiaAnticipo) {
		this.listaTotalDiaAnticipo = listaTotalDiaAnticipo;
	}
	
	

}
