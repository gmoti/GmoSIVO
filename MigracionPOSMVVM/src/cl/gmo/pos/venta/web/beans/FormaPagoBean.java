/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

/**
 *
 * @author Advice70
 */
public class FormaPagoBean {
    
    private String id;
    private String descripcion;
    private String texto;
    
    public FormaPagoBean() {
    	this.id="";
    	this.descripcion="";
    	this.texto="";
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
    
    
    
}
