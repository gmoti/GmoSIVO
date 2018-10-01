/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

/**
 *
 * @author Advice70
 */
public class GrupoFamiliaBean {
    private String codigo;
    private String descripcion;
    private String familia;
    private String subfamilia;

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getSubfamilia() {
        return subfamilia;
    }

    public void setSubfamilia(String subfamilia) {
        this.subfamilia = subfamilia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
