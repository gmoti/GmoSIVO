package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;

public class MedicoForm extends GenericForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5334499406631047033L;
	private String accion;
	private String codigo;
	private String rut;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String titulo;
	private String dv;
	private String codpos;
	private String tfno;
	private String email;
	private String fax;
	private String provinci;
	private String externo;	
	private String locali;
	private ArrayList <ProvinciaBean> listaProvincia;
	private String exito = Constantes.STRING_ACTION_INICIO_PAGINA;
	private String codigo_medico_agregado;
	private String nif_medico_agregado;
	private String dvnif_medico_agregado;
	private String nombre_medico_agregado;
	private String apellido_medico_agregado;
	private String pagina;
	
	public void cleanForm(){
		
		this.codigo = Constantes.STRING_BLANCO;
		this.rut = Constantes.STRING_BLANCO;
		this.nombres = Constantes.STRING_BLANCO;
		this.apellidos = Constantes.STRING_BLANCO;
		this.direccion = Constantes.STRING_BLANCO;
		this.titulo = Constantes.STRING_BLANCO;
		this.dv = Constantes.STRING_BLANCO;
		this.codpos = Constantes.STRING_BLANCO;
		this.tfno = Constantes.STRING_BLANCO;
		this.email = Constantes.STRING_BLANCO;
		this.fax = Constantes.STRING_BLANCO;
		this.provinci = Constantes.STRING_BLANCO;
		this.externo = Constantes.STRING_BLANCO;	
		this.locali = Constantes.STRING_BLANCO;
		this.listaProvincia = new ArrayList<ProvinciaBean>();
		this.exito = Constantes.STRING_ACTION_INICIO_PAGINA;
		this.pagina = Constantes.STRING_ACTION_INICIO_PAGINA;
	}
	
	
	public String getDvnif_medico_agregado() {
		return dvnif_medico_agregado;
	}


	public void setDvnif_medico_agregado(String dvnif_medico_agregado) {
		this.dvnif_medico_agregado = dvnif_medico_agregado;
	}


	public String getNombre_medico_agregado() {
		return nombre_medico_agregado;
	}


	public void setNombre_medico_agregado(String nombre_medico_agregado) {
		this.nombre_medico_agregado = nombre_medico_agregado;
	}


	public String getApellido_medico_agregado() {
		return apellido_medico_agregado;
	}


	public void setApellido_medico_agregado(String apellido_medico_agregado) {
		this.apellido_medico_agregado = apellido_medico_agregado;
	}


	public String getPagina() {
		return pagina;
	}


	public void setPagina(String pagina) {
		this.pagina = pagina;
	}


	public String getCodigo_medico_agregado() {
		return codigo_medico_agregado;
	}


	public void setCodigo_medico_agregado(String codigo_medico_agregado) {
		this.codigo_medico_agregado = codigo_medico_agregado;
	}


	public String getNif_medico_agregado() {
		return nif_medico_agregado;
	}


	public void setNif_medico_agregado(String nif_medico_agregado) {
		this.nif_medico_agregado = nif_medico_agregado;
	}


	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public ArrayList<ProvinciaBean> getListaProvincia() {
		return listaProvincia;
	}
	public void setListaProvincia(ArrayList<ProvinciaBean> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}
	public String getCodpos() {
		return codpos;
	}
	public void setCodpos(String codpos) {
		this.codpos = codpos;
	}
	public String getTfno() {
		return tfno;
	}
	public void setTfno(String tfno) {
		this.tfno = tfno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getProvinci() {
		return provinci;
	}
	public void setProvinci(String provinci) {
		this.provinci = provinci;
	}
	public String getExterno() {
		return externo;
	}
	public void setExterno(String externo) {
		this.externo = externo;
	}
	public String getLocali() {
		return locali;
	}
	public void setLocali(String locali) {
		this.locali = locali;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	

}
