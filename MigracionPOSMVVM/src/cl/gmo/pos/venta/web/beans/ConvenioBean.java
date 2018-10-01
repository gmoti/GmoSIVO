/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

/**
 *
 * @author Advice70
 */
public class ConvenioBean {
    
    private String id;
    private String descripcion;
    private String tipo;
    private String imprime_guia;
    private String rut;
    private String dv;
    private String giro;
    private String direccion;
    private String telefono;   
	private String isapre;
    

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getDv() {
		return dv;
	}

	public void setDv(String dv) {
		this.dv = dv;
	}

	public String getGiro() {
		return giro;
	}

	public void setGiro(String giro) {
		this.giro = giro;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getImprime_guia() {
		return imprime_guia;
	}

	public void setImprime_guia(String imprime_guia) {
		this.imprime_guia = imprime_guia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId() {
        return id;
    }
    
    public String getIsapre() {
		return isapre;
	}

	public void setIsapre(String isapre) {
		this.isapre = isapre;
	}    
    
}
