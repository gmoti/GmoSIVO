package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ClienteBean;

public class BusquedaClientesForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1964510672563440486L;
	private String accion;
	private String codigo;
	private String nif;
	private String dvnif;
	private String nombre;
	private String apellido;
	private ArrayList<ClienteBean> listaClientes;
	private String pagina = Constantes.STRING_BLANCO;
	private String error = Constantes.STRING_ERROR;
	
	public BusquedaClientesForm() {
		this.nif="";
		this.nombre="";
		this.apellido="";
	}
	
	
	
	public String getDvnif() {
		return dvnif;
	}
	public void setDvnif(String dvnif) {
		this.dvnif = dvnif;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public ArrayList<ClienteBean> getListaClientes() {
		return listaClientes;
	}
	public void setListaClientes(ArrayList<ClienteBean> listaClientes) {
		this.listaClientes = listaClientes;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	

}
