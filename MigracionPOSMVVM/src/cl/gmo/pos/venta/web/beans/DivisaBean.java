/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

/**
 *
 * @author Advice70
 */
public class DivisaBean {
    
    private String id;
    private String descripcion;
    private String redondear;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRedondear(String redondear) {
        this.redondear = redondear;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId() {
        return id;
    }

    public String getRedondear() {
        return redondear;
    }
    
}
