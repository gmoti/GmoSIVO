/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.Integracion.DTO;

import cl.gmo.pos.venta.utils.Constantes;

/**
 *
 * @author Advise64
 */
public class SucursalDTO {
    private String sucursal=Constantes.STRING_BLANCO;
    private String descripcion=Constantes.STRING_BLANCO;
    private String telefono= Constantes.STRING_BLANCO;
    private String tipo_boleta= Constantes.STRING_BLANCO;

    
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
