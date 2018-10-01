package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;

public class BusquedaMedicoForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -346249689943992429L;
	private String nif;
	private String apellido;
	private String nombre;
	private String accion;
	private ArrayList<OftalmologoBean> listaOftalmologos;
	private String oftalmologo;
	private String codigo;
	private String error = Constantes.STRING_ERROR;
	
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getOftalmologo() {
		return oftalmologo;
	}
	public void setOftalmologo(String oftalmologo) {
		this.oftalmologo = oftalmologo;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<OftalmologoBean> getListaOftalmologos() {
		return listaOftalmologos;
	}
	public void setListaOftalmologos(ArrayList<OftalmologoBean> listaOftalmologos) {
		this.listaOftalmologos = listaOftalmologos;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
