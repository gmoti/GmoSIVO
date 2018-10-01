package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.ConvenioFamBean;
import cl.gmo.pos.venta.web.beans.ConvenioLnBean;

public class BusquedaConveniosForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 329080725569143982L;
	private String accion;
	private String codigo;
	private String nombre;
	private String empresa;
	private String indice;
	private ArrayList<ConvenioBean> lista_convenios;
	private ArrayList<ConvenioLnBean> lista_formas_pago;
	private ArrayList<ConvenioFamBean> lista_formas_pago_familias;
	private String sel_fpago;
	private String sel_dto;
	private String sel_convenio;
	private String sel_convenio_det;
	private int sel_convenioln_ident;
	
	
	
	public int getSel_convenioln_ident() {
		return sel_convenioln_ident;
	}
	public void setSel_convenioln_ident(int sel_convenioln_ident) {
		this.sel_convenioln_ident = sel_convenioln_ident;
	}
	public String getSel_convenio() {
		return sel_convenio;
	}
	public void setSel_convenio(String sel_convenio) {
		this.sel_convenio = sel_convenio;
	}
	public String getSel_convenio_det() {
		return sel_convenio_det;
	}
	public void setSel_convenio_det(String sel_convenio_det) {
		this.sel_convenio_det = sel_convenio_det;
	}
	public String getSel_fpago() {
		return sel_fpago;
	}
	public void setSel_fpago(String sel_fpago) {
		this.sel_fpago = sel_fpago;
	}
	public String getSel_dto() {
		return sel_dto;
	}
	public void setSel_dto(String sel_dto) {
		this.sel_dto = sel_dto;
	}
	public String getIndice() {
		return indice;
	}
	public void setIndice(String indice) {
		this.indice = indice;
	}
	public ArrayList<ConvenioLnBean> getLista_formas_pago() {
		return lista_formas_pago;
	}
	public void setLista_formas_pago(ArrayList<ConvenioLnBean> lista_formas_pago) {
		this.lista_formas_pago = lista_formas_pago;
	}
	public ArrayList<ConvenioFamBean> getLista_formas_pago_familias() {
		return lista_formas_pago_familias;
	}
	public void setLista_formas_pago_familias(
			ArrayList<ConvenioFamBean> lista_formas_pago_familias) {
		this.lista_formas_pago_familias = lista_formas_pago_familias;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public void setLista_convenios(ArrayList<ConvenioBean> lista_convenios) {
		this.lista_convenios = lista_convenios;
	}
	public ArrayList<ConvenioBean> getLista_convenios() {
		return lista_convenios;
	}
	
	public void cleanForm() {
		
		this.accion = Constantes.STRING_BLANCO;
		this.codigo = Constantes.STRING_BLANCO;
		this.empresa = Constantes.STRING_BLANCO;
		this.nombre = Constantes.STRING_BLANCO;
		this.setLista_convenios(null);
		this.setLista_formas_pago(null);
		this.setLista_formas_pago_familias(null);
		this.indice = Constantes.STRING_BLANCO;
		this.sel_dto = Constantes.STRING_BLANCO;
		this.sel_fpago = Constantes.STRING_BLANCO;
		this.sel_convenio = Constantes.STRING_BLANCO;
		this.sel_convenio_det = Constantes.STRING_BLANCO;
		this.sel_convenioln_ident = 0;
		
	}
	
	
	

}
