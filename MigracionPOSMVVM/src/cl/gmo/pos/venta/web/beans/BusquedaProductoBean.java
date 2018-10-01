package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class BusquedaProductoBean {

	private String descripcion="";
	private String cdg="";
	private String codigoBarra="";
	private String familia="";
	private String subFamilia="";
	private String grupoFamilia="";
	private String precio="";
	private String precioIva="";
	private String tarifa="";
	private String articulo="";
	private ArrayList <BusquedaArticuloGralBean> listaBusquedaProducto = new ArrayList <BusquedaArticuloGralBean>();
	
	
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getPrecioIva() {
		return precioIva;
	}
	public void setPrecioIva(String precioIva) {
		this.precioIva = precioIva;
	}
	public String getTarifa() {
		return tarifa;
	}
	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCdg() {
		return cdg;
	}
	public void setCdg(String cdg) {
		this.cdg = cdg;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public String getSubFamilia() {
		return subFamilia;
	}
	public void setSubFamilia(String subFamilia) {
		this.subFamilia = subFamilia;
	}
	public String getGrupoFamilia() {
		return grupoFamilia;
	}
	public void setGrupoFamilia(String grupoFamilia) {
		this.grupoFamilia = grupoFamilia;
	}
	public ArrayList<BusquedaArticuloGralBean> getListaBusquedaProducto() {
		return listaBusquedaProducto;
	}
	public void setListaBusquedaProducto(
			ArrayList<BusquedaArticuloGralBean> listaBusquedaProducto) {
		this.listaBusquedaProducto = listaBusquedaProducto;
	}
	

	
}
