/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.forms;

import java.io.Serializable;
import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.TipoViaBean;

/**
 *
 * @author Advice70
 */
public class ClienteForm extends GenericForm implements Serializable {
    
   
	private static final long serialVersionUID = -7817605485399246594L;

	private String error;
    
    private String accion;
    private int codigo;
    private String rut;
    private String dv;
    private String nombres;
    private String apellidos;
    private String agente;
    private String fnacimiento;
    private String edad;
    private ArrayList<AgenteBean> listaAgentes;
    private ArrayList <TipoViaBean> listaTipoVia;
    private ArrayList <ProvinciaBean> listaProvincia;
    private String telefono;
    private String telefono_movil;
    private String sinfono;
    private String sinmovil;
    private String email;
    private String local;
    private String sexo;
    
    //direccion
    private String tipo_via;
    private String via;
    private String numero;
    private String bloque;
    private String localidad;
    private int provincia;
    private String provincia_cliente;
    private String contacto;
    private String cod_postal;
    
    //facturacion
    private String remitente;
    private String giro;
    private String descripcionGiro;
    private String profesion;
    private String ocio;

    private String exito = "inicio_pagina";
    private String codigo_cliente_agregado;
    private String nif_cliente_agregado;
    private String agente_sucursal;
    private String estado_pagina;
    private String modifica_cliente;
    private String codigo_cliente_agregado_factura;
    private String nif_cliente_agregado_factura;
    private ArrayList<GiroBean> listaGiros;
    
    private String nombre_cliente_factura=Constantes.STRING_BLANCO;
    private String tipo_via_factura=Constantes.STRING_BLANCO;
    private String via_factura=Constantes.STRING_BLANCO;
    private String numero_factura=Constantes.STRING_BLANCO;
    private String localidad_factura=Constantes.STRING_BLANCO;
    private String provincia_factura=Constantes.STRING_BLANCO;
    private String dvFactura = Constantes.STRING_BLANCO;
    
    private String pagina_status = Constantes.STRING_BLANCO;
    
    /*LMARIN 20140812*/
    private String cod_telefono = Constantes.STRING_BLANCO;   
	private String mk_correo_postal = Constantes.STRING_BLANCO;
	private String mk_correo_electronico = Constantes.STRING_BLANCO;
	private String mk_telefonia = Constantes.STRING_BLANCO;
	private String mk_sms = Constantes.STRING_BLANCO;	
	private String mk_nodata = Constantes.STRING_BLANCO;
	
	private boolean mkCorreoPostal = false;
	private boolean mkCorreoElectronico = false;
	private boolean mkTelefonia = false;
	private boolean mkSms = false;	
	private boolean mkNodata = false;
	
	
	
	
	
	public void cleanForm() {
    	
	    	 this.accion = Constantes.STRING_BLANCO;
	         this.codigo = 0;	         
	         this.rut = Constantes.STRING_BLANCO;
	         this.dv = Constantes.STRING_BLANCO;
	         this.nombres = Constantes.STRING_BLANCO;
	         this.apellidos = Constantes.STRING_BLANCO;
	         this.agente = Constantes.STRING_BLANCO;
	         this.fnacimiento = Constantes.STRING_BLANCO;
	         this.edad = Constantes.STRING_BLANCO;
	         this.sexo = Constantes.STRING_BLANCO;
	        
	         this.telefono = Constantes.STRING_BLANCO;
		     this.sinfono = Constantes.STRING_BLANCO;
	         this.email = Constantes.STRING_BLANCO;
	         this.local = Constantes.STRING_BLANCO;
	         this.telefono_movil = Constantes.STRING_BLANCO;
	         this.sinmovil = Constantes.STRING_BLANCO;
	        
	        //direccion
	         this.tipo_via = Constantes.STRING_BLANCO;
	         this.via = Constantes.STRING_BLANCO;
	         this.numero = Constantes.STRING_BLANCO;
	         this.bloque = Constantes.STRING_BLANCO;
	         this.localidad = Constantes.STRING_BLANCO;
	         this.provincia = 0;
	         this.contacto = Constantes.STRING_BLANCO;
	         this.cod_postal = Constantes.STRING_BLANCO;
	        
	        //facturacion
	         this.remitente = Constantes.STRING_BLANCO;
	         this.giro = Constantes.STRING_BLANCO;
	         this.profesion = Constantes.STRING_BLANCO;
	         this.ocio = Constantes.STRING_BLANCO;
	         this.exito = "inicio_pagina";
	         this.estado_pagina = Constantes.STRING_BLANCO;
	         this.modifica_cliente = Constantes.STRING_BLANCO;
	         
	         this.nombre_cliente_factura=Constantes.STRING_BLANCO;
	         this.tipo_via_factura=Constantes.STRING_BLANCO;
	         this.via_factura=Constantes.STRING_BLANCO;
	         this.numero_factura=Constantes.STRING_BLANCO;
	         this.localidad_factura=Constantes.STRING_BLANCO;
	         this.provincia_factura=Constantes.STRING_BLANCO;
	         this.dvFactura = Constantes.STRING_BLANCO; 
	         this.codigo_cliente_agregado_factura	 = Constantes.STRING_BLANCO;;
	         this.nif_cliente_agregado_factura = Constantes.STRING_BLANCO;
	         this.nif_cliente_agregado_factura  = Constantes.STRING_BLANCO;
	         this.descripcionGiro = Constantes.STRING_BLANCO;
	         this.pagina_status = "nuevo";
	         this.provincia_cliente="";
	         this.mk_correo_postal ="";
	         this.mk_correo_electronico ="";
	         this.mk_telefonia ="";
	         
    }
    
    
    public String getProvincia_cliente() {
		return provincia_cliente;
	}


	public void setProvincia_cliente(String provincia_cliente) {
		this.provincia_cliente = provincia_cliente;
	}


	public String getSinmovil() {
		return sinmovil;
	}


	public void setSinmovil(String sinmovil) {
		this.sinmovil = sinmovil;
	}


	public String getTelefono_movil() {
		return telefono_movil;
	}


	public void setTelefono_movil(String telefono_movil) {
		this.telefono_movil = telefono_movil;
	}


	public String getPagina_status() {
		return pagina_status;
	}


	public void setPagina_status(String pagina_status) {
		this.pagina_status = pagina_status;
	}


	public String getDvFactura() {
		return dvFactura;
	}


	public void setDvFactura(String dvFactura) {
		this.dvFactura = dvFactura;
	}


	public String getTipo_via_factura() {
		return tipo_via_factura;
	}


	public void setTipo_via_factura(String tipo_via_factura) {
		this.tipo_via_factura = tipo_via_factura;
	}


	public String getVia_factura() {
		return via_factura;
	}


	public void setVia_factura(String via_factura) {
		this.via_factura = via_factura;
	}


	public String getNumero_factura() {
		return numero_factura;
	}


	public void setNumero_factura(String numero_factura) {
		this.numero_factura = numero_factura;
	}


	public String getLocalidad_factura() {
		return localidad_factura;
	}


	public void setLocalidad_factura(String localidad_factura) {
		this.localidad_factura = localidad_factura;
	}


	public String getProvincia_factura() {
		return provincia_factura;
	}


	public void setProvincia_factura(String provincia_factura) {
		this.provincia_factura = provincia_factura;
	}


	public String getDescripcionGiro() {
		return descripcionGiro;
	}


	public void setDescripcionGiro(String descripcionGiro) {
		this.descripcionGiro = descripcionGiro;
	}


	public ArrayList<GiroBean> getListaGiros() {
		return listaGiros;
	}


	public void setListaGiros(ArrayList<GiroBean> listaGiros) {
		this.listaGiros = listaGiros;
	}


	public String getNombre_cliente_factura() {
		return nombre_cliente_factura;
	}

	public void setNombre_cliente_factura(String nombre_cliente_factura) {
		this.nombre_cliente_factura = nombre_cliente_factura;
	}

	public String getCodigo_cliente_agregado_factura() {
		return codigo_cliente_agregado_factura;
	}

	public void setCodigo_cliente_agregado_factura(
			String codigo_cliente_agregado_factura) {
		this.codigo_cliente_agregado_factura = codigo_cliente_agregado_factura;
	}

	public String getNif_cliente_agregado_factura() {
		return nif_cliente_agregado_factura;
	}

	public void setNif_cliente_agregado_factura(String nif_cliente_agregado_factura) {
		this.nif_cliente_agregado_factura = nif_cliente_agregado_factura;
	}

	public String getModifica_cliente() {
		return modifica_cliente;
	}

	public void setModifica_cliente(String modifica_cliente) {
		this.modifica_cliente = modifica_cliente;
	}

	public String getEstado_pagina() {
		return estado_pagina;
	}

	public void setEstado_pagina(String estado_pagina) {
		this.estado_pagina = estado_pagina;
	}

	public String getAgente_sucursal() {
		return agente_sucursal;
	}

	public void setAgente_sucursal(String agente_sucursal) {
		this.agente_sucursal = agente_sucursal;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setOcio(String ocio) {
        this.ocio = ocio;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public void setTipo_via(String tipo_via) {
        this.tipo_via = tipo_via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getBloque() {
        return bloque;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public String getContacto() {
        return contacto;
    }

    public String getGiro() {
        return giro;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getOcio() {
        return ocio;
    }

    public String getProfesion() {
        return profesion;
    }

    public int getProvincia() {
        return provincia;
    }

    public String getRemitente() {
        return remitente;
    }

    public String getTipo_via() {
        return tipo_via;
    }

    public String getVia() {
        return via;
    }
    
    

    public String getNumero() {
        return numero;
    }
    

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEdad() {
        return edad;
    }

    public void setFnacimiento(String fnacimiento) {
        this.fnacimiento = fnacimiento;
    }

    public String getFnacimiento() {
        return fnacimiento;
    }
    
    public void setDv(String dv) {
        this.dv = dv;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDv() {
        return dv;
    }

    public String getRut() {
        return rut;
    }
    
    public void setListaAgentes(ArrayList<AgenteBean> listaAgentes) {
        this.listaAgentes = listaAgentes;
    }

    public ArrayList<AgenteBean> getListaAgentes() {
        return listaAgentes;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAgente() {
        return agente;
    }

    public String getApellidos() {
        return apellidos;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombres() {
        return nombres;
    }

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getSinfono() {
		return sinfono;
	}

	public void setSinfono(String sinfono) {
		this.sinfono = sinfono;
	}

	public ArrayList<TipoViaBean> getListaTipoVia() {
		return listaTipoVia;
	}

	public void setListaTipoVia(ArrayList<TipoViaBean> listaTipoVia) {
		this.listaTipoVia = listaTipoVia;
	}

	public ArrayList<ProvinciaBean> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(ArrayList<ProvinciaBean> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
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


	//==========================
	
	
	public boolean isMkCorreoPostal() {
		return mkCorreoPostal;
	}


	public void setMkCorreoPostal(boolean mkCorreoPostal) {
		this.mkCorreoPostal = mkCorreoPostal;
	}


	public boolean isMkCorreoElectronico() {
		return mkCorreoElectronico;
	}


	public void setMkCorreoElectronico(boolean mkCorreoElectronico) {
		this.mkCorreoElectronico = mkCorreoElectronico;
	}


	public boolean isMkTelefonia() {
		return mkTelefonia;
	}


	public void setMkTelefonia(boolean mkTelefonia) {
		this.mkTelefonia = mkTelefonia;
	}


	public boolean isMkSms() {
		return mkSms;
	}


	public void setMkSms(boolean mkSms) {
		this.mkSms = mkSms;
	}


	public boolean isMkNodata() {
		return mkNodata;
	}


	public void setMkNodata(boolean mkNodata) {
		this.mkNodata = mkNodata;
	}
	
	
	
	
	
	

}
