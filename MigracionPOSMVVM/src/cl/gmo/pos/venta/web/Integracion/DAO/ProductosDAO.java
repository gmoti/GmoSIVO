/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

/**
 *
 * @author Advice70
 */
public interface ProductosDAO {
    public ArrayList<ProductosBean> traeProductos(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda) throws Exception;
    
    public ProductosBean traeProducto(String producto, int cantidad, String local, String tipo_busqueda, String cod_barra) throws Exception;
  
    public ArrayList<ProductosBean> traeProductosGraduados(String ojo, String tipo_fam, String familia, String subfamilia, String grupo,
            String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local, GraduacionesBean graduacion) throws Exception;
}
