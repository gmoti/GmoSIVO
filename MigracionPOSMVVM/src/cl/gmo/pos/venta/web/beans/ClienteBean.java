/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

import cl.gmo.pos.venta.utils.Constantes;

/**
 *
 * @author Advice70
 */
public class ClienteBean {
    
	private String codigo  = Constantes.STRING_BLANCO;
	private String nif  = Constantes.STRING_BLANCO;
	private String nombre = Constantes.STRING_BLANCO;
	private String apellido  = Constantes.STRING_BLANCO;
	private String direccion  = Constantes.STRING_BLANCO;
	private int  provincia;
	private String provincia_cliente = Constantes.STRING_BLANCO;
	private String poblacion  = Constantes.STRING_BLANCO;
	private int giro;
	private String razon_social  = Constantes.STRING_BLANCO;
	private String codigo_postal = Constantes.STRING_BLANCO;
	private String email = Constantes.STRING_BLANCO;
	private String agente = Constantes.STRING_BLANCO;
	private String tipo_via = Constantes.STRING_BLANCO;
	private String numero = Constantes.STRING_BLANCO;
	private String fono_casa = Constantes.STRING_BLANCO;
	private String fono_movil = Constantes.STRING_BLANCO;
	private String fono_trabajo  = Constantes.STRING_BLANCO;
	private String persona_contacto = Constantes.STRING_BLANCO;
	private String profesion = Constantes.STRING_BLANCO;
	private String dvnif = Constantes.STRING_BLANCO;
	private String local = Constantes.STRING_BLANCO;
	private String fecha_nac = Constantes.STRING_BLANCO;
	private String sexo = Constantes.STRING_BLANCO;
	private String ocio = Constantes.STRING_BLANCO;
	private String bloque = "";
	private String provincia_desc =Constantes.STRING_BLANCO;
	private String giro_desc = Constantes.STRING_BLANCO;
	private String cliente_cliente = Constantes.STRING_BLANCO;
	private String direcciondesp = Constantes.STRING_BLANCO;
	
	/*LMARIN 20140812*/
	private String cod_telefono = Constantes.STRING_BLANCO;  	
	private String mk_correo_postal = Constantes.STRING_BLANCO;
	private String mk_correo_electronico = Constantes.STRING_BLANCO;
	private String mk_telefonia = Constantes.STRING_BLANCO;
	private String mk_sms = Constantes.STRING_BLANCO;	
	private String mk_nodata = Constantes.STRING_BLANCO;
	
	
	public String getProvincia_cliente() {
		return provincia_cliente;
	}
	public void setProvincia_cliente(String provincia_cliente) {
		this.provincia_cliente = provincia_cliente;
	}
	public String getCliente_cliente() {
		return cliente_cliente;
	}
	public void setCliente_cliente(String cliente_cliente) {
		this.cliente_cliente = cliente_cliente;
	}
	public String getProvincia_desc() {
		return provincia_desc;
	}
	public void setProvincia_desc(String provincia_desc) {
		this.provincia_desc = provincia_desc;
	}
	public String getGiro_desc() {
		return giro_desc;
	}
	public void setGiro_desc(String giro_desc) {
		this.giro_desc = giro_desc;
	}
	public String getBloque() {
		return bloque;
	}
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}
	public int getProvincia() {
		return provincia;
	}
	public void setProvincia(int provincia) {
		this.provincia = provincia;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public int getGiro() {
		return giro;
	}
	public void setGiro(int giro) {
		this.giro = giro;
	}
	public String getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigo_postal() {
		return codigo_postal;
	}
	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getTipo_via() {
		return tipo_via;
	}
	public void setTipo_via(String tipo_via) {
		this.tipo_via = tipo_via;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getFono_casa() {
		return fono_casa;
	}
	public void setFono_casa(String fono_casa) {
		this.fono_casa = fono_casa;
	}
	public String getFono_movil() {
		return fono_movil;
	}
	public void setFono_movil(String fono_movil) {
		this.fono_movil = fono_movil;
	}
	public String getFono_trabajo() {
		return fono_trabajo;
	}
	public void setFono_trabajo(String fono_trabajo) {
		this.fono_trabajo = fono_trabajo;
	}
	public String getPersona_contacto() {
		return persona_contacto;
	}
	public void setPersona_contacto(String persona_contacto) {
		this.persona_contacto = persona_contacto;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	public String getDvnif() {
		return dvnif;
	}
	public void setDvnif(String dvnif) {
		this.dvnif = dvnif;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getFecha_nac() {
		return fecha_nac;
	}
	public void setFecha_nac(String fecha_nac) {
		this.fecha_nac = fecha_nac;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getOcio() {
		return ocio;
	}
	public void setOcio(String ocio) {
		this.ocio = ocio;
	}
	public String getDirecciondesp() {
		return direcciondesp;
	}
	public void setDirecciondesp(String direcciondesp) {
		this.direcciondesp = direcciondesp;
	}	
	public String getMk_correo_postal() {
		return mk_correo_postal;
	}
	public void setMk_correo_postal(String mk_correo_postal) {
		this.mk_correo_postal = mk_correo_postal;
	}
	public String getMk_correo_electronico() {
		return mk_correo_electronico;
	}
	public void setMk_correo_electronico(String mk_correo_electronico) {
		this.mk_correo_electronico = mk_correo_electronico;
	}
	public String getMk_telefonia() {
		return mk_telefonia;
	}
	public void setMk_telefonia(String mk_telefonia) {
		this.mk_telefonia = mk_telefonia;
	}
	public String getMk_sms() {
		return mk_sms;
	}
	public void setMk_sms(String mk_sms) {
		this.mk_sms = mk_sms;
	}
	public String getMk_nodata() {
		return mk_nodata;
	}
	public void setMk_nodata(String mk_nodata) {
		this.mk_nodata = mk_nodata;
	}
	public String getCod_telefono() {
		return cod_telefono;
	}
	public void setCod_telefono(String cod_telefono) {
		this.cod_telefono = cod_telefono;
	}
}
