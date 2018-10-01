package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.SuplementosValores;

public class SuplementosForm{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4437787658885464608L;
	private ArrayList<SuplementopedidoBean> listaSuplementos;
	private ArrayList<SuplementopedidoBean> suplementos;
	private String valor;
	private String suplemento;
	private String suplemento_desc;
	private String producto;
	private String error;
	private String accion;
	private String seg_ojo;
	
	ArrayList<SuplementosValores> lista_valores_suplementos;
	
	
	
	
	
	
	public ArrayList<SuplementosValores> getLista_valores_suplementos() {
		return lista_valores_suplementos;
	}
	public void setLista_valores_suplementos(
			ArrayList<SuplementosValores> lista_valores_suplementos) {
		this.lista_valores_suplementos = lista_valores_suplementos;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public ArrayList<SuplementopedidoBean> getSuplementos() {
		return suplementos;
	}
	public void setSuplementos(ArrayList<SuplementopedidoBean> suplementos) {
		this.suplementos = suplementos;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getSuplemento_desc() {
		return suplemento_desc;
	}
	public void setSuplemento_desc(String suplemento_desc) {
		this.suplemento_desc = suplemento_desc;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getSuplemento() {
		return suplemento;
	}
	public void setSuplemento(String suplemento) {
		this.suplemento = suplemento;
	}
	public ArrayList<SuplementopedidoBean> getListaSuplementos() {
		return listaSuplementos;
	}
	public void setListaSuplementos(ArrayList<SuplementopedidoBean> listaSuplementos) {
		this.listaSuplementos = listaSuplementos;
	}

	public String getSeg_ojo() {
		return seg_ojo;
	}
	public void setSeg_ojo(String seg_ojo) {
		this.seg_ojo = seg_ojo;
	}
	
	

}
