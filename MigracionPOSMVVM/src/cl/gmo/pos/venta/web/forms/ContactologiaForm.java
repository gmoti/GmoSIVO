package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;

public class ContactologiaForm extends GenericForm{

	/**
	 * doct
	 */
	private static final long serialVersionUID = -3873309641321183168L;

	private String grabar;
	private String nombre_cliente;
	private String cliente;
	private String accion;
	private String exito;
	private String fecha_graduacion;
	private String numero_graduacion;
	private int numero;
	
	private String o_radio1;
	private String o_radio2;
	private String o_esfera;
	private String o_cilindro;
	private String o_eje;
	private String o_diamt;
	private String o_diaz;
	private String o_bandas;
	private String o_esp;
	private String o_radio3;
	private String o_diamp;
	private String o_colo;
	private String o_adic;
	
	private String i_radio1;
	private String i_radio2;
	private String i_esfera;
	private String i_cilindro;
	private String i_eje;
	private String i_diamt;
	private String i_diaz;
	private String i_bandas;
	private String i_esp;
	private String i_radio3;
	private String i_diamp;
	private String i_colo;
	private String i_adic;
	
	private ArrayList<GraduacionesBean> listaGraduacionOptometria;
	private ArrayList<GraduacionesBean> listaGraduaciones;
	private ArrayList<ContactologiaBean> listaContactologia;
	
	private String otros;	
	private String laboratorio;
	private String revision1;
	private String calculo_opt;
	private String fecha_pedido;
	private String fecha_recepcion;
	private String fecha_entrega;
	private String seguro;
	private String fecha_caducidad;
	
	private String od1;
	private String od2;
	private String od3;
	
	private String oi1;
	private String oi2;
	private String oi3;
	
	private String recomendaciones;

	private String limpiador;
	private String conservador;
	private String enzimatico;
	private String otro;	
	
	private String tipo;
	private String doctor;
	private String od_material;
	private String oi_material;
	private String odhidr;
	private String oihidr;
	private String oitipo;
	
	private String Odacumrep;
	private String Oiacumrep;
	
	private String marcadood;
	private String marcadooi;
	private ArrayList<OftalmologoBean>listaOftalmologos;
	
	private String existeContactologia;
	private String cerrarPagina;
	
	private String nifdoctor;
	private String dvnifdoctor;
	private String nombre_doctor;
	
	
	public void cleanForm(){		
		
		accion = Constantes.STRING_BLANCO;
		exito = Constantes.STRING_BLANCO;
		numero = Constantes.INT_CERO;
		
		o_radio1 = Constantes.STRING_BLANCO;
		o_radio2 = Constantes.STRING_BLANCO;
		o_esfera = Constantes.STRING_BLANCO;
		o_cilindro = Constantes.STRING_BLANCO;
		this.o_eje = Constantes.STRING_BLANCO;
		this.o_diamt = Constantes.STRING_BLANCO;
		this.o_diaz = Constantes.STRING_BLANCO;
		this.o_bandas = Constantes.STRING_BLANCO;
		this.o_esp = Constantes.STRING_BLANCO;
		this.o_radio3 = Constantes.STRING_BLANCO;
		this.o_diamp = Constantes.STRING_BLANCO;
		this.o_colo = Constantes.STRING_BLANCO;
		this.o_adic = Constantes.STRING_BLANCO;
		
		this.i_radio1 = Constantes.STRING_BLANCO;
		this.i_radio2 = Constantes.STRING_BLANCO;
		this.i_esfera = Constantes.STRING_BLANCO;
		this.i_cilindro = Constantes.STRING_BLANCO;
		this.i_eje = Constantes.STRING_BLANCO;
		this.i_diamt = Constantes.STRING_BLANCO;
		this.i_diaz = Constantes.STRING_BLANCO;
		this.i_bandas = Constantes.STRING_BLANCO;
		this.i_esp = Constantes.STRING_BLANCO;
		this.i_radio3 = Constantes.STRING_BLANCO;
		this.i_diamp = Constantes.STRING_BLANCO;
		this.i_colo = Constantes.STRING_BLANCO;
		this.i_adic = Constantes.STRING_BLANCO;
		
		this.listaGraduacionOptometria = new ArrayList<GraduacionesBean>();
		this.listaGraduaciones = new ArrayList<GraduacionesBean>();
		
		this.otros = Constantes.STRING_BLANCO;	
		this.laboratorio = Constantes.STRING_BLANCO;
		this.revision1 = Constantes.STRING_BLANCO;
		this.calculo_opt = Constantes.STRING_BLANCO;
		this.fecha_pedido = Constantes.STRING_BLANCO;
		this.fecha_recepcion = Constantes.STRING_BLANCO;
		this.fecha_entrega = Constantes.STRING_BLANCO;
		this.seguro = Constantes.STRING_BLANCO;
		this.fecha_caducidad = Constantes.STRING_BLANCO;
		
		this.od1 = Constantes.STRING_BLANCO;
		this.od2 = Constantes.STRING_BLANCO;
		this.od3 = Constantes.STRING_BLANCO;
		
		this.oi1 = Constantes.STRING_BLANCO;
		this.oi2 = Constantes.STRING_BLANCO;
		this.oi3 = Constantes.STRING_BLANCO;
		
		this.recomendaciones = Constantes.STRING_BLANCO;

		this.limpiador = Constantes.STRING_BLANCO;
		this.conservador = Constantes.STRING_BLANCO;
		this.enzimatico = Constantes.STRING_BLANCO;
		this.otro = Constantes.STRING_BLANCO;	
		
		this.tipo = Constantes.STRING_BLANCO;
		this.doctor = Constantes.STRING_BLANCO;
		this.od_material = Constantes.STRING_BLANCO;
		this.oi_material = Constantes.STRING_BLANCO;
		this.odhidr = Constantes.STRING_BLANCO;
		this.oihidr = Constantes.STRING_BLANCO;
		this.oitipo = Constantes.STRING_BLANCO;
		
		this.Odacumrep = Constantes.STRING_BLANCO;
		this.Oiacumrep = Constantes.STRING_BLANCO;
		
		this.marcadood = Constantes.STRING_BLANCO;
		this.marcadooi = Constantes.STRING_BLANCO;
		this.existeContactologia = Constantes.STRING_BLANCO;
		this.nifdoctor = Constantes.STRING_BLANCO;
		this.dvnifdoctor = Constantes.STRING_BLANCO;
	}
	
	
	
	public String getNombre_doctor() {
		return nombre_doctor;
	}



	public void setNombre_doctor(String nombre_doctor) {
		this.nombre_doctor = nombre_doctor;
	}



	public String getNifdoctor() {
		return nifdoctor;
	}


	public void setNifdoctor(String nifdoctor) {
		this.nifdoctor = nifdoctor;
	}


	public String getDvnifdoctor() {
		return dvnifdoctor;
	}


	public void setDvnifdoctor(String dvnifdoctor) {
		this.dvnifdoctor = dvnifdoctor;
	}


	public String getCerrarPagina() {
		return cerrarPagina;
	}


	public void setCerrarPagina(String cerrarPagina) {
		this.cerrarPagina = cerrarPagina;
	}


	public String getExisteContactologia() {
		return existeContactologia;
	}


	public void setExisteContactologia(String existeContactologia) {
		this.existeContactologia = existeContactologia;
	}


	public ArrayList<OftalmologoBean> getListaOftalmologos() {
		return listaOftalmologos;
	}


	public void setListaOftalmologos(ArrayList<OftalmologoBean> listaOftalmologos) {
		this.listaOftalmologos = listaOftalmologos;
	}


	public String getGrabar() {
		return grabar;
	}


	public void setGrabar(String grabar) {
		this.grabar = grabar;
	}


	public String getFecha_graduacion() {
		return fecha_graduacion;
	}


	public void setFecha_graduacion(String fecha_graduacion) {
		this.fecha_graduacion = fecha_graduacion;
	}


	public String getNumero_graduacion() {
		return numero_graduacion;
	}


	public void setNumero_graduacion(String numero_graduacion) {
		this.numero_graduacion = numero_graduacion;
	}


	public ArrayList<ContactologiaBean> getListaContactologia() {
		return listaContactologia;
	}


	public void setListaContactologia(
			ArrayList<ContactologiaBean> listaContactologia) {
		this.listaContactologia = listaContactologia;
	}


	public String getMarcadood() {
		return marcadood;
	}

	public void setMarcadood(String marcadood) {
		this.marcadood = marcadood;
	}

	public String getMarcadooi() {
		return marcadooi;
	}

	public void setMarcadooi(String marcadooi) {
		this.marcadooi = marcadooi;
	}



	public String getOdacumrep() {
		return Odacumrep;
	}


	public void setOdacumrep(String odacumrep) {
		Odacumrep = odacumrep;
	}


	public String getOiacumrep() {
		return Oiacumrep;
	}


	public void setOiacumrep(String oiacumrep) {
		Oiacumrep = oiacumrep;
	}


	public String getOi_material() {
		return oi_material;
	}

	public void setOi_material(String oi_material) {
		this.oi_material = oi_material;
	}

	public String getOihidr() {
		return oihidr;
	}

	public void setOihidr(String oihidr) {
		this.oihidr = oihidr;
	}

	public String getOitipo() {
		return oitipo;
	}

	public void setOitipo(String oitipo) {
		this.oitipo = oitipo;
	}

	public String getOdhidr() {
		return odhidr;
	}

	public void setOdhidr(String odhidr) {
		this.odhidr = odhidr;
	}

	public String getOd_material() {
		return od_material;
	}

	public void setOd_material(String od_material) {
		this.od_material = od_material;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public String getLimpiador() {
		return limpiador;
	}

	public void setLimpiador(String limpiador) {
		this.limpiador = limpiador;
	}

	public String getConservador() {
		return conservador;
	}

	public void setConservador(String conservador) {
		this.conservador = conservador;
	}

	public String getEnzimatico() {
		return enzimatico;
	}

	public void setEnzimatico(String enzimatico) {
		this.enzimatico = enzimatico;
	}

	public String getOtro() {
		return otro;
	}

	public void setOtro(String otro) {
		this.otro = otro;
	}

	public ArrayList<GraduacionesBean> getListaGraduaciones() {
		return listaGraduaciones;
	}

	public void setListaGraduaciones(ArrayList<GraduacionesBean> listaGraduaciones) {
		this.listaGraduaciones = listaGraduaciones;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getO_radio1() {
		return o_radio1;
	}

	public void setO_radio1(String o_radio1) {
		this.o_radio1 = o_radio1;
	}

	public String getO_radio2() {
		return o_radio2;
	}

	public void setO_radio2(String o_radio2) {
		this.o_radio2 = o_radio2;
	}

	public String getO_esfera() {
		return o_esfera;
	}

	public void setO_esfera(String o_esfera) {
		this.o_esfera = o_esfera;
	}

	public String getO_cilindro() {
		return o_cilindro;
	}

	public void setO_cilindro(String o_cilindro) {
		this.o_cilindro = o_cilindro;
	}

	public String getO_eje() {
		return o_eje;
	}

	public void setO_eje(String o_eje) {
		this.o_eje = o_eje;
	}

	public String getO_diamt() {
		return o_diamt;
	}

	public void setO_diamt(String o_diamt) {
		this.o_diamt = o_diamt;
	}

	public String getO_diaz() {
		return o_diaz;
	}

	public void setO_diaz(String o_diaz) {
		this.o_diaz = o_diaz;
	}

	public String getO_bandas() {
		return o_bandas;
	}

	public void setO_bandas(String o_bandas) {
		this.o_bandas = o_bandas;
	}

	public String getO_esp() {
		return o_esp;
	}

	public void setO_esp(String o_esp) {
		this.o_esp = o_esp;
	}

	public String getO_radio3() {
		return o_radio3;
	}

	public void setO_radio3(String o_radio3) {
		this.o_radio3 = o_radio3;
	}

	public String getO_diamp() {
		return o_diamp;
	}

	public void setO_diamp(String o_diamp) {
		this.o_diamp = o_diamp;
	}

	public String getO_colo() {
		return o_colo;
	}

	public void setO_colo(String o_colo) {
		this.o_colo = o_colo;
	}

	public String getO_adic() {
		return o_adic;
	}

	public void setO_adic(String o_adic) {
		this.o_adic = o_adic;
	}

	public String getI_radio1() {
		return i_radio1;
	}

	public void setI_radio1(String i_radio1) {
		this.i_radio1 = i_radio1;
	}

	public String getI_radio2() {
		return i_radio2;
	}

	public void setI_radio2(String i_radio2) {
		this.i_radio2 = i_radio2;
	}

	public String getI_esfera() {
		return i_esfera;
	}

	public void setI_esfera(String i_esfera) {
		this.i_esfera = i_esfera;
	}

	public String getI_cilindro() {
		return i_cilindro;
	}

	public void setI_cilindro(String i_cilindro) {
		this.i_cilindro = i_cilindro;
	}

	public String getI_eje() {
		return i_eje;
	}

	public void setI_eje(String i_eje) {
		this.i_eje = i_eje;
	}

	public String getI_diamt() {
		return i_diamt;
	}

	public void setI_diamt(String i_diamt) {
		this.i_diamt = i_diamt;
	}

	public String getI_diaz() {
		return i_diaz;
	}

	public void setI_diaz(String i_diaz) {
		this.i_diaz = i_diaz;
	}

	public String getI_bandas() {
		return i_bandas;
	}

	public void setI_bandas(String i_bandas) {
		this.i_bandas = i_bandas;
	}

	public String getI_esp() {
		return i_esp;
	}

	public void setI_esp(String i_esp) {
		this.i_esp = i_esp;
	}

	public String getI_radio3() {
		return i_radio3;
	}

	public void setI_radio3(String i_radio3) {
		this.i_radio3 = i_radio3;
	}

	public String getI_diamp() {
		return i_diamp;
	}

	public void setI_diamp(String i_diamp) {
		this.i_diamp = i_diamp;
	}	

	public String getI_colo() {
		return i_colo;
	}

	public void setI_colo(String i_colo) {
		this.i_colo = i_colo;
	}

	public String getI_adic() {
		return i_adic;
	}

	public void setI_adic(String i_adic) {
		this.i_adic = i_adic;
	}

	public ArrayList<GraduacionesBean> getListaGraduacionOptometria() {
		return listaGraduacionOptometria;
	}

	public void setListaGraduacionOptometria(
			ArrayList<GraduacionesBean> listaGraduacionOptometria) {
		this.listaGraduacionOptometria = listaGraduacionOptometria;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getRevision1() {
		return revision1;
	}

	public void setRevision1(String revision1) {
		this.revision1 = revision1;
	}

	public String getCalculo_opt() {
		return calculo_opt;
	}

	public void setCalculo_opt(String calculo_opt) {
		this.calculo_opt = calculo_opt;
	}

	public String getFecha_pedido() {
		return fecha_pedido;
	}

	public void setFecha_pedido(String fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}

	public String getFecha_recepcion() {
		return fecha_recepcion;
	}

	public void setFecha_recepcion(String fecha_recepcion) {
		this.fecha_recepcion = fecha_recepcion;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getSeguro() {
		return seguro;
	}

	public void setSeguro(String seguro) {
		this.seguro = seguro;
	}

	public String getFecha_caducidad() {
		return fecha_caducidad;
	}

	public void setFecha_caducidad(String fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
	}

	public String getOd1() {
		return od1;
	}

	public void setOd1(String od1) {
		this.od1 = od1;
	}

	public String getOd2() {
		return od2;
	}

	public void setOd2(String od2) {
		this.od2 = od2;
	}

	public String getOd3() {
		return od3;
	}

	public void setOd3(String od3) {
		this.od3 = od3;
	}

	public String getOi1() {
		return oi1;
	}

	public void setOi1(String oi1) {
		this.oi1 = oi1;
	}

	public String getOi2() {
		return oi2;
	}

	public void setOi2(String oi2) {
		this.oi2 = oi2;
	}

	public String getOi3() {
		return oi3;
	}

	public void setOi3(String oi3) {
		this.oi3 = oi3;
	}

	public String getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(String recomendaciones) {
		this.recomendaciones = recomendaciones;
	}
	
}

