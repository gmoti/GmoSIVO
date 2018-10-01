package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.BusquedaProductoBean;
import cl.gmo.pos.venta.web.beans.InformeBusquedaProductoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

public class InformeBusquedaProductoForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -481752786123041202L;
	private int codigoArticulo;
	private String descripcionArticulo;
	private String fechaDesde;
	private String fechaHasta;
	private String estadoPagina;

	private ArrayList <InformeBusquedaProductoBean> listaBusquedaProducto = new ArrayList <InformeBusquedaProductoBean>();
	
	
	

	
	public String getEstadoPagina() {
		return estadoPagina;
	}
	public void setEstadoPagina(String estadoPagina) {
		this.estadoPagina = estadoPagina;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public ArrayList<InformeBusquedaProductoBean> getListaBusquedaProducto() {
		return listaBusquedaProducto;
	}
	public void setListaBusquedaProducto(
			ArrayList<InformeBusquedaProductoBean> listaBusquedaProducto) {
		this.listaBusquedaProducto = listaBusquedaProducto;
	}
	public int getCodigoArticulo() {
		return codigoArticulo;
	}
	public void setCodigoArticulo(int codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}
	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}
	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}
	
}
