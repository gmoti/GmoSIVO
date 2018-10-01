/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.forms;

import cl.gmo.pos.venta.utils.Constantes;


/**
 *
 * @author Advise64
 */
public class UsuarioForm{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5747972167069546142L;
	private String nombreUsuario=Constantes.STRING_BLANCO;
	private String descNombreUsuario=Constantes.STRING_BLANCO;
    private String claveUsuario=Constantes.STRING_BLANCO;
    
    
    

    
	public String getDescNombreUsuario() {
		return descNombreUsuario;
	}

	public void setDescNombreUsuario(String descNombreUsuario) {
		this.descNombreUsuario = descNombreUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }  
}
