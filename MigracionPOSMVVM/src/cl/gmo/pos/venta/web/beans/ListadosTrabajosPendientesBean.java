package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class ListadosTrabajosPendientesBean {
	private ArrayList<ListadosTrabajosPendientesCabeceraBean> cabecera = new ArrayList<ListadosTrabajosPendientesCabeceraBean>();
	private ArrayList<ListadosTrabajosPendientesLineaBean> linea = new ArrayList<ListadosTrabajosPendientesLineaBean>();
	private ArrayList<ListadosTrabajosPendientesTotalBean> total = new ArrayList<ListadosTrabajosPendientesTotalBean>();
	public ArrayList<ListadosTrabajosPendientesCabeceraBean> getCabecera() {
		return cabecera;
	}
	public void setCabecera(
			ArrayList<ListadosTrabajosPendientesCabeceraBean> cabecera) {
		this.cabecera = cabecera;
	}
	public ArrayList<ListadosTrabajosPendientesLineaBean> getLinea() {
		return linea;
	}
	public void setLinea(ArrayList<ListadosTrabajosPendientesLineaBean> linea) {
		this.linea = linea;
	}
	public ArrayList<ListadosTrabajosPendientesTotalBean> getTotal() {
		return total;
	}
	public void setTotal(ArrayList<ListadosTrabajosPendientesTotalBean> total) {
		this.total = total;
	}
	
	
}
