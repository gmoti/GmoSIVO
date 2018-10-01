package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class InformeBusquedaProductoBean {

	private String descripcion="";
	private String cdg="";
	private String codigoBarra="";
	private String familia="";
	private String subFamilia="";
	private String grupoFamilia="";
	private ArrayList <BusquedaProductoBean> listaBusquedaProducto = new ArrayList <BusquedaProductoBean>();
	
	public String getDescripcion() {
		return descripcion;
	}
	public void addListaBusquedaProducto(BusquedaProductoBean object) {
		listaBusquedaProducto.add(object);
	}
	public ArrayList<BusquedaProductoBean> getListaBusquedaProducto() {
		return listaBusquedaProducto;
	}
	public void setListaBusquedaProducto(
			ArrayList<BusquedaProductoBean> listaBusquedaProducto) {
		this.listaBusquedaProducto = listaBusquedaProducto;
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
	
	
	
}
