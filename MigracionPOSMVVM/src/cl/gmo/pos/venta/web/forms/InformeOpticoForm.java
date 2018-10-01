package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.GraduacionInformeOpticoBean;

public class InformeOpticoForm{
	

	private static final long serialVersionUID = -1538285922792384865L;
	
	private String cliente=Constantes.STRING_BLANCO;
	private String graduacion=Constantes.STRING_BLANCO;
	private String cdgCli=Constantes.STRING_BLANCO;
	private String graduacionCli=Constantes.STRING_BLANCO;
	private String nombreCli=Constantes.STRING_BLANCO;
	private String fechaNacCli=Constantes.STRING_BLANCO;
	private String domicilioCli=Constantes.STRING_BLANCO;
	private String telCli=Constantes.STRING_BLANCO;
	private ArrayList <GraduacionInformeOpticoBean> listaGraduaciones = new ArrayList <GraduacionInformeOpticoBean>();
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getGraduacion() {
		return graduacion;
	}
	public void setGraduacion(String graduacion) {
		this.graduacion = graduacion;
	}
	public String getCdgCli() {
		return cdgCli;
	}
	public void setCdgCli(String cdgCli) {
		this.cdgCli = cdgCli;
	}
	public String getGraduacionCli() {
		return graduacionCli;
	}
	public void setGraduacionCli(String graduacionCli) {
		this.graduacionCli = graduacionCli;
	}
	public String getNombreCli() {
		return nombreCli;
	}
	public void setNombreCli(String nombreCli) {
		this.nombreCli = nombreCli;
	}
	public String getFechaNacCli() {
		return fechaNacCli;
	}
	public void setFechaNacCli(String fechaNacCli) {
		this.fechaNacCli = fechaNacCli;
	}
	public String getDomicilioCli() {
		return domicilioCli;
	}
	public void setDomicilioCli(String domicilioCli) {
		this.domicilioCli = domicilioCli;
	}
	
	public String getTelCli() {
		return telCli;
	}
	public void setTelCli(String telCli) {
		this.telCli = telCli;
	}
	public ArrayList<GraduacionInformeOpticoBean> getListaGraduaciones() {
		return listaGraduaciones;
	}
	public void setListaGraduaciones(
			ArrayList<GraduacionInformeOpticoBean> listaGraduaciones) {
		this.listaGraduaciones = listaGraduaciones;
	}
	public void addListaGraduaciones(GraduacionInformeOpticoBean graduacion) {
		this.listaGraduaciones.add(graduacion);
	}
	

}
