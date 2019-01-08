/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

import java.io.Serializable;

/**
 *
 * @author Advise64
 */
public class SucursalesBean implements Serializable{
   
	private static final long serialVersionUID = -5115220267640134195L;
	private String sucursal="";
    private String descripcion="";
    private String telefono="";
    private String tipo_boleta="";

    
    public String getTipo_boleta() {
		return tipo_boleta;
	}

	public void setTipo_boleta(String tipo_boleta) {
		this.tipo_boleta = tipo_boleta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    
}
