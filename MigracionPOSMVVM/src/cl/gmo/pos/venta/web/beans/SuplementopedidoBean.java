package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class SuplementopedidoBean {
	
	private int ident;
	private int pedtln;
	private String tratami;
	private int preciodef;
	private int precio;
	private String ivacb;
	private String valor;
	private String descripcion;
	private int index;
	private String obligatorio;
	private String ojo;
	private String tratamiento;
	private int indiceref;
	private double diametro;
	private String color;
	ArrayList<SuplementopedidoBean> suplementos_Obligatorios = new ArrayList<SuplementopedidoBean>();
	ArrayList<SuplementopedidoBean> suplementos_Opcionales = new ArrayList<SuplementopedidoBean>();
	
	
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getIndiceref() {
		return indiceref;
	}
	public void setIndiceref(int indiceref) {
		this.indiceref = indiceref;
	}
	public double getDiametro() {
		return diametro;
	}
	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}
	public String getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	public String getOjo() {
		return ojo;
	}
	public void setOjo(String ojo) {
		this.ojo = ojo;
	}
	public String getObligatorio() {
		return obligatorio;
	}
	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public ArrayList<SuplementopedidoBean> getSuplementos_Obligatorios() {
		return suplementos_Obligatorios;
	}
	public void setSuplementos_Obligatorios(
			ArrayList<SuplementopedidoBean> suplementos_Obligatorios) {
		this.suplementos_Obligatorios = suplementos_Obligatorios;
	}
	public ArrayList<SuplementopedidoBean> getSuplementos_Opcionales() {
		return suplementos_Opcionales;
	}
	public void setSuplementos_Opcionales(
			ArrayList<SuplementopedidoBean> suplementos_Opcionales) {
		this.suplementos_Opcionales = suplementos_Opcionales;
	}
	public int getIdent() {
		return ident;
	}
	public void setIdent(int ident) {
		this.ident = ident;
	}
	public int getPedtln() {
		return pedtln;
	}
	public void setPedtln(int pedtln) {
		this.pedtln = pedtln;
	}
	public String getTratami() {
		return tratami;
	}
	public void setTratami(String tratami) {
		this.tratami = tratami;
	}
	public int getPreciodef() {
		return preciodef;
	}
	public void setPreciodef(int preciodef) {
		this.preciodef = preciodef;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public String getIvacb() {
		return ivacb;
	}
	public void setIvacb(String ivacb) {
		this.ivacb = ivacb;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
