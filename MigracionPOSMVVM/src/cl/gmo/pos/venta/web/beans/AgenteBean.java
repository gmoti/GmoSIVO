/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

/**
 *
 * @author Advice70
 */
public class AgenteBean {
    
    private String Nombre_completo;
    private String usuario;
    
    public AgenteBean() {
    	Nombre_completo="";
    	usuario="";
    }


    public void setNombre_completo(String Nombre_completo) {
        this.Nombre_completo = Nombre_completo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre_completo() {
        return Nombre_completo;
    }

    public String getUsuario() {
        return usuario;
    }
    
    
    
}
