package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class ListaPresupuestosBean {
	
	private ArrayList<ListaPresupuestoLineaBean> listaPresupuestoLineas;
	private ArrayList<ListaPresupuestoCabeceraBean> listaPresupuestoCabeceras;
	private ArrayList<ListaPresupuestoTotalBean> listaPresupuestoTotales;
	
	public ArrayList<ListaPresupuestoLineaBean> getListaPresupuestoLineas() {
		return listaPresupuestoLineas;
	}
	public void setListaPresupuestoLineas(
			ArrayList<ListaPresupuestoLineaBean> listaPresupuestoLineas) {
		this.listaPresupuestoLineas = listaPresupuestoLineas;
	}
	public ArrayList<ListaPresupuestoCabeceraBean> getListaPresupuestoCabeceras() {
		return listaPresupuestoCabeceras;
	}
	public void setListaPresupuestoCabeceras(
			ArrayList<ListaPresupuestoCabeceraBean> listaPresupuestoCabeceras) {
		this.listaPresupuestoCabeceras = listaPresupuestoCabeceras;
	}
	public ArrayList<ListaPresupuestoTotalBean> getListaPresupuestoTotales() {
		return listaPresupuestoTotales;
	}
	public void setListaPresupuestoTotales(
			ArrayList<ListaPresupuestoTotalBean> listaPresupuestoTotales) {
		this.listaPresupuestoTotales = listaPresupuestoTotales;
	}

	
	
}
