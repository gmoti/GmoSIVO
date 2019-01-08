/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

import java.io.Serializable;

/**
 *
 * @author Advice70
 */
public class FormaPagoBean implements Serializable{
    
   
	private static final long serialVersionUID = 1552499422070973483L;
	private String id;
    private String descripcion;
    private String texto;
    private boolean activo;
    
    public FormaPagoBean() {
    	this.id="";
    	this.descripcion="";
    	this.texto="";
    	this.activo=false;
    }    

    public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
    
    
    
    
    
}
