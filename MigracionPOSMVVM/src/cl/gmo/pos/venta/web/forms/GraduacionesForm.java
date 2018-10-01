package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.HistorialGraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.beans.PrismaBaseBean;
import cl.gmo.pos.venta.web.beans.PrismaCantidadBean;

public class GraduacionesForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5553699540040095429L;
	private String nombre;
	private String apellido;
	private String cod_doctor;
	private String fecha_graduacion;
	
	private double numero_graduacion;
	private String nombre_cliente;
	private int cliente;
	private String tipo;
	private String doctor;
	private String OD_esfera;
	private String OD_cilindro;
	private String OD_eje;
	private String OD_cerca;
	private String OD_adicion;
	private String OD_dnpl;
	private String OD_dnpc;
	private String OD_avsc;
	private String OD_avcc;
	private String OD_observaciones;
	private String OI_esfera;
	private String OI_cilindro;
	private String OI_eje;
	private String OI_cerca;
	private String OI_adicion;
	private String OI_dnpl;
	private String OI_dnpc;
	private String OI_avsc;
	private String OI_avcc;
	private String OI_observaciones;
	private int Interna;
	private int Externa;
	private ArrayList<GraduacionesBean> listaGraduaciones;
	private ArrayList<OftalmologoBean> listaOftalmologos;
	private ArrayList<AgenteBean> listaAgentes;
	private ArrayList<PrismaBaseBean> listaPrismaBase;
	private String OD_base;
	private String OD_altura;
	private String OD_cantidad;
	
	private ArrayList<PrismaCantidadBean>listaCantidadOD;
	private ArrayList<PrismaBaseBean>listaBaseOD;
	private ArrayList<Double>listaAlturaOD;
	
	private String OI_base;
	private String OI_altura;
	private String OI_cantidad;
	
	private ArrayList<PrismaCantidadBean>listaCantidadOI;
	private ArrayList<PrismaBaseBean>listaBaseOI;
	private ArrayList<Double>listaAlturaOI;
	
	private String oftalmologo;
	private String agente;
	private double honorarios;
	private String fechaEmision;
	private String fechaProxRevision;
	private String plan;
	private String accion;
	private String pagina;
	private String cerrarPagina;
	
	private String exito = "inicio_pagina";
	
	private String codigo_cliente_agregado;
	private String nif_cliente_agregado;
	private String existe_graduacion;
	
	private String nifdoctor; 
	private String dvnifdoctor;
	private String nombre_doctor;
	private boolean diferenteAdd;
	private int estaGrabado;
	
	public void  cleanForm(){
		
		this.agente = Constantes.STRING_BLANCO;
		this.doctor = Constantes.STRING_BLANCO;
		this.cod_doctor = Constantes.STRING_BLANCO;
		this.OD_esfera = Constantes.STRING_BLANCO;
		this.OD_cilindro = Constantes.STRING_BLANCO;
		this.OD_eje = Constantes.STRING_BLANCO;
		this.OD_cerca = Constantes.STRING_BLANCO;
		this.OD_adicion = Constantes.STRING_BLANCO;
		this.OD_dnpl = Constantes.STRING_BLANCO;
		this.OD_dnpc = Constantes.STRING_BLANCO;
		this.OD_avsc = Constantes.STRING_BLANCO;
		this.OD_avcc = Constantes.STRING_BLANCO;
		this.OD_observaciones = Constantes.STRING_BLANCO;
		this.OI_esfera = Constantes.STRING_BLANCO;
		this.OI_cilindro = Constantes.STRING_BLANCO;
		this.OI_eje = Constantes.STRING_BLANCO;
		this.OI_cerca = Constantes.STRING_BLANCO;
		this.OI_adicion = Constantes.STRING_BLANCO;
		this.OI_dnpl = Constantes.STRING_BLANCO;
		this.OI_dnpc = Constantes.STRING_BLANCO;
		this.OI_avsc = Constantes.STRING_BLANCO;
		this.OI_avcc = Constantes.STRING_BLANCO;
		this.OI_observaciones = Constantes.STRING_BLANCO;
		this.Interna = Constantes.INT_CERO;
		this.Externa = Constantes.INT_CERO;
		this.listaGraduaciones = new ArrayList<GraduacionesBean> () ;
		this.listaOftalmologos = new ArrayList<OftalmologoBean>();
		this.listaAgentes = new ArrayList<AgenteBean>();
		this.listaPrismaBase = new ArrayList<PrismaBaseBean>();
		this.OD_base = Constantes.STRING_BLANCO;
		this.OD_altura = Constantes.STRING_BLANCO;
		this.OD_cantidad = Constantes.STRING_BLANCO;
		this.OI_base = Constantes.STRING_BLANCO;
		this.OI_altura = Constantes.STRING_BLANCO;
		this.OI_cantidad = Constantes.STRING_BLANCO;
		this.fechaEmision = Constantes.STRING_BLANCO;
		this.fechaProxRevision = Constantes.STRING_BLANCO;		
		this.exito = "inicio_pagina";
		this.tipo=Constantes.STRING_BLANCO;
		this.codigo_cliente_agregado=Constantes.STRING_BLANCO;
		this.nif_cliente_agregado=Constantes.STRING_BLANCO;
		this.existe_graduacion=Constantes.STRING_BLANCO;
		this.diferenteAdd = false;
		this.estaGrabado = 0;
		
	}
	
	public boolean isDiferenteAdd() {
		return diferenteAdd;
	}

	public void setDiferenteAdd(boolean diferenteAdd) {
		this.diferenteAdd = diferenteAdd;
	}

	public String getNombre_doctor() {
		return nombre_doctor;
	}

	public void setNombre_doctor(String nombre_doctor) {
		this.nombre_doctor = nombre_doctor;
	}

	public String getDvnifdoctor() {
		return dvnifdoctor;
	}

	public void setDvnifdoctor(String dvnifdoctor) {
		this.dvnifdoctor = dvnifdoctor;
	}

	public String getNifdoctor() {
		return nifdoctor;
	}

	public void setNifdoctor(String nifdoctor) {
		this.nifdoctor = nifdoctor;
	}

	public String getExiste_graduacion() {
		return existe_graduacion;
	}

	public void setExiste_graduacion(String existe_graduacion) {
		this.existe_graduacion = existe_graduacion;
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

	public String getCod_doctor() {
		return cod_doctor;
	}

	public void setCod_doctor(String cod_doctor) {
		this.cod_doctor = cod_doctor;
	}

	public String getFecha_graduacion() {
		return fecha_graduacion;
	}

	public void setFecha_graduacion(String fecha_graduacion) {
		this.fecha_graduacion = fecha_graduacion;
	}

	public double getNumero_graduacion() {
		return numero_graduacion;
	}

	public void setNumero_graduacion(double numero_graduacion) {
		this.numero_graduacion = numero_graduacion;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getOD_esfera() {
		return OD_esfera;
	}

	public void setOD_esfera(String oD_esfera) {
		OD_esfera = oD_esfera;
	}

	public String getOD_cilindro() {
		return OD_cilindro;
	}

	public void setOD_cilindro(String oD_cilindro) {
		OD_cilindro = oD_cilindro;
	}

	public String getOD_eje() {
		return OD_eje;
	}

	public void setOD_eje(String oD_eje) {
		OD_eje = oD_eje;
	}

	public String getOD_cerca() {
		return OD_cerca;
	}

	public void setOD_cerca(String oD_cerca) {
		OD_cerca = oD_cerca;
	}

	public String getOD_adicion() {
		return OD_adicion;
	}

	public void setOD_adicion(String oD_adicion) {
		OD_adicion = oD_adicion;
	}

	public String getOD_dnpl() {
		return OD_dnpl;
	}

	public void setOD_dnpl(String oD_dnpl) {
		OD_dnpl = oD_dnpl;
	}

	public String getOD_dnpc() {
		return OD_dnpc;
	}

	public void setOD_dnpc(String oD_dnpc) {
		OD_dnpc = oD_dnpc;
	}

	public String getOD_avsc() {
		return OD_avsc;
	}

	public void setOD_avsc(String oD_avsc) {
		OD_avsc = oD_avsc;
	}

	public String getOD_avcc() {
		return OD_avcc;
	}

	public void setOD_avcc(String oD_avcc) {
		OD_avcc = oD_avcc;
	}

	public String getOD_observaciones() {
		return OD_observaciones;
	}

	public void setOD_observaciones(String oD_observaciones) {
		OD_observaciones = oD_observaciones;
	}

	public String getOI_esfera() {
		return OI_esfera;
	}

	public void setOI_esfera(String oI_esfera) {
		OI_esfera = oI_esfera;
	}

	public String getOI_cilindro() {
		return OI_cilindro;
	}

	public void setOI_cilindro(String oI_cilindro) {
		OI_cilindro = oI_cilindro;
	}

	public String getOI_eje() {
		return OI_eje;
	}

	public void setOI_eje(String oI_eje) {
		OI_eje = oI_eje;
	}

	public String getOI_cerca() {
		return OI_cerca;
	}

	public void setOI_cerca(String oI_cerca) {
		OI_cerca = oI_cerca;
	}

	public String getOI_adicion() {
		return OI_adicion;
	}

	public void setOI_adicion(String oI_adicion) {
		OI_adicion = oI_adicion;
	}

	public String getOI_dnpl() {
		return OI_dnpl;
	}

	public void setOI_dnpl(String oI_dnpl) {
		OI_dnpl = oI_dnpl;
	}

	public String getOI_dnpc() {
		return OI_dnpc;
	}

	public void setOI_dnpc(String oI_dnpc) {
		OI_dnpc = oI_dnpc;
	}

	public String getOI_avsc() {
		return OI_avsc;
	}

	public void setOI_avsc(String oI_avsc) {
		OI_avsc = oI_avsc;
	}

	public String getOI_avcc() {
		return OI_avcc;
	}

	public void setOI_avcc(String oI_avcc) {
		OI_avcc = oI_avcc;
	}

	public String getOI_observaciones() {
		return OI_observaciones;
	}

	public void setOI_observaciones(String oI_observaciones) {
		OI_observaciones = oI_observaciones;
	}

	public int getInterna() {
		return Interna;
	}

	public void setInterna(int interna) {
		Interna = interna;
	}

	public int getExterna() {
		return Externa;
	}

	public void setExterna(int externa) {
		Externa = externa;
	}

	public ArrayList<GraduacionesBean> getListaGraduaciones() {
		return listaGraduaciones;
	}

	public void setListaGraduaciones(ArrayList<GraduacionesBean> listaGraduaciones) {
		this.listaGraduaciones = listaGraduaciones;
	}

	public ArrayList<OftalmologoBean> getListaOftalmologos() {
		return listaOftalmologos;
	}

	public void setListaOftalmologos(ArrayList<OftalmologoBean> listaOftalmologos) {
		this.listaOftalmologos = listaOftalmologos;
	}

	public ArrayList<AgenteBean> getListaAgentes() {
		return listaAgentes;
	}

	public void setListaAgentes(ArrayList<AgenteBean> listaAgentes) {
		this.listaAgentes = listaAgentes;
	}

	public ArrayList<PrismaBaseBean> getListaPrismaBase() {
		return listaPrismaBase;
	}

	public void setListaPrismaBase(ArrayList<PrismaBaseBean> listaPrismaBase) {
		this.listaPrismaBase = listaPrismaBase;
	}

	public String getOD_base() {
		return OD_base;
	}

	public void setOD_base(String oD_base) {
		OD_base = oD_base;
	}

	public String getOD_altura() {
		return OD_altura;
	}

	public void setOD_altura(String oD_altura) {
		OD_altura = oD_altura;
	}

	public String getOD_cantidad() {
		return OD_cantidad;
	}

	public void setOD_cantidad(String oD_cantidad) {
		OD_cantidad = oD_cantidad;
	}

	public ArrayList<PrismaCantidadBean> getListaCantidadOD() {
		return listaCantidadOD;
	}

	public void setListaCantidadOD(ArrayList<PrismaCantidadBean> listaCantidadOD) {
		this.listaCantidadOD = listaCantidadOD;
	}

	public ArrayList<PrismaBaseBean> getListaBaseOD() {
		return listaBaseOD;
	}

	public void setListaBaseOD(ArrayList<PrismaBaseBean> listaBaseOD) {
		this.listaBaseOD = listaBaseOD;
	}

	public ArrayList<Double> getListaAlturaOD() {
		return listaAlturaOD;
	}

	public void setListaAlturaOD(ArrayList<Double> listaAlturaOD) {
		this.listaAlturaOD = listaAlturaOD;
	}

	public String getOI_base() {
		return OI_base;
	}

	public void setOI_base(String oI_base) {
		OI_base = oI_base;
	}

	public String getOI_altura() {
		return OI_altura;
	}

	public void setOI_altura(String oI_altura) {
		OI_altura = oI_altura;
	}

	public String getOI_cantidad() {
		return OI_cantidad;
	}

	public void setOI_cantidad(String oI_cantidad) {
		OI_cantidad = oI_cantidad;
	}

	public ArrayList<PrismaCantidadBean> getListaCantidadOI() {
		return listaCantidadOI;
	}

	public void setListaCantidadOI(ArrayList<PrismaCantidadBean> listaCantidadOI) {
		this.listaCantidadOI = listaCantidadOI;
	}

	public ArrayList<PrismaBaseBean> getListaBaseOI() {
		return listaBaseOI;
	}

	public void setListaBaseOI(ArrayList<PrismaBaseBean> listaBaseOI) {
		this.listaBaseOI = listaBaseOI;
	}

	public ArrayList<Double> getListaAlturaOI() {
		return listaAlturaOI;
	}

	public void setListaAlturaOI(ArrayList<Double> listaAlturaOI) {
		this.listaAlturaOI = listaAlturaOI;
	}

	public String getOftalmologo() {
		return oftalmologo;
	}

	public void setOftalmologo(String oftalmologo) {
		this.oftalmologo = oftalmologo;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public double getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(double honorarios) {
		this.honorarios = honorarios;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getFechaProxRevision() {
		return fechaProxRevision;
	}

	public void setFechaProxRevision(String fechaProxRevision) {
		this.fechaProxRevision = fechaProxRevision;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getCerrarPagina() {
		return cerrarPagina;
	}

	public void setCerrarPagina(String cerrarPagina) {
		this.cerrarPagina = cerrarPagina;
	}

	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public String getCodigo_cliente_agregado() {
		return codigo_cliente_agregado;
	}

	public void setCodigo_cliente_agregado(String codigo_cliente_agregado) {
		this.codigo_cliente_agregado = codigo_cliente_agregado;
	}

	public String getNif_cliente_agregado() {
		return nif_cliente_agregado;
	}

	public void setNif_cliente_agregado(String nif_cliente_agregado) {
		this.nif_cliente_agregado = nif_cliente_agregado;
	}

	public int getEstaGrabado() {
		return estaGrabado;
	}

	public void setEstaGrabado(int estaGrabado) {
		this.estaGrabado = estaGrabado;
	}

	
}
