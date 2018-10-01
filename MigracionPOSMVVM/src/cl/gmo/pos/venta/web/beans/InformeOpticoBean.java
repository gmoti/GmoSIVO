package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class InformeOpticoBean {
	private String cdgCli="";
	private String graduacionCli="";
	private String nombreCli="";
	private String fechaNacCli="";
	private String domicilioCli="";
	private String telCli="";
	private ArrayList <GraduacionInformeOpticoBean> listaGraduaciones = new ArrayList <GraduacionInformeOpticoBean>();
	
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
