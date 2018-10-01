/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ProductosDAOImpl;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

/**
 *
 * @author Advice70
 */
public class PosProductosFacade {
	
	
	public static ProductosBean traeInfoproducto(String producto)
    {
		ProductosBean prod = new ProductosBean();
        try {
            ProductosDAOImpl productosImpl = new ProductosDAOImpl();
            prod = productosImpl.traeInfoproducto(producto);
            
        } catch (Exception e) {
            
        }
        return prod;
    }
	
	
	public static ProductosBean traeProducto(String producto, int cantidad, String local, String tipo_busqueda, String cod_barra)
    {
		ProductosBean prod = new ProductosBean();
        try {
            ProductosDAOImpl productosImpl = new ProductosDAOImpl();
    		System.out.println("traeProducto singular "+producto +","+cantidad +","+local +","+tipo_busqueda +","+cod_barra);

            prod = productosImpl.traeProducto(producto, cantidad, local, tipo_busqueda, cod_barra);
            
        } catch (Exception e) {
            
        }
        return prod;
    }
	
	public static ProductosBean traeProductoEsp(String producto,String tienda,String encargo,String grupo,String cdg)
    {
		ProductosBean prod = new ProductosBean();
        try {
            ProductosDAOImpl productosImpl = new ProductosDAOImpl();
            prod = productosImpl.traeProductoEsp(producto,tienda,encargo,grupo,cdg);
            
        } catch (Exception e) {
            
        }
        return prod;
    }
    
    public static ArrayList<ProductosBean> traeProductos(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda)
    {
        ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
        try {
    		System.out.println("traeProductos "+familia+"||"+ subfamilia+"||"+grupo+"||"+proveedor+"||"+descripcion+"||"+fabricante+"||"+codigoBusqueda+"||"+codigoBarraBusqueda+"||"+local+"||"+tipo_busqueda+"||"+codigoBarraBusqueda);

            ProductosDAOImpl productosDao = new ProductosDAOImpl();
            listaProductos = productosDao.traeProductos(familia, subfamilia, grupo, proveedor, descripcion, fabricante,codigoBusqueda,codigoBarraBusqueda,local,tipo_busqueda);

        } catch (Exception e) {
            listaProductos = null;
        }
        return listaProductos;
    }


	public static ArrayList<ProductosBean> traeProductosGraduados(String ojo, String tipo_fam, String familia, String subfamilia, String grupo, 
					String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local, GraduacionesBean graduacion) {
		ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
        try {
            ProductosDAOImpl productosDao = new ProductosDAOImpl();
            listaProductos = productosDao.traeProductosGraduados(ojo, tipo_fam, familia, subfamilia, grupo, descripcion, codigoBusqueda, codigoBarraBusqueda, local, graduacion);
            
        } catch (Exception e) {
            
            listaProductos = null;
        }
        return listaProductos;
	}
	
	public static String  mostrarMultiOferta(String codigoMulti){
		String respuesta =null;
		ProductosDAOImpl productosDao = new ProductosDAOImpl();
		try{
			
			respuesta = productosDao.mostrarMultiOferta(codigoMulti);
			
		}catch(Exception ex){
			respuesta = "ERROR";
		}
		
		return respuesta;
		
	}
	
	public static ArrayList<ProductosBean> traeProductosMulti(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda, String codigo_barra_busqueda)
    {
        ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
        try {
            ProductosDAOImpl productosDao = new ProductosDAOImpl();
    		 //System.out.println("traeProductosMulti"+familia + subfamilia + grupo + proveedor + descripcion + fabricante + codigoBusqueda + codigoBarraBusqueda + local + tipo_busqueda + codigo_barra_busqueda);

            listaProductos = productosDao.traeProductosMulti(familia, subfamilia, grupo, proveedor, descripcion, fabricante,codigoBusqueda,codigoBarraBusqueda,local,tipo_busqueda, codigo_barra_busqueda);
        } catch (Exception e) {
            
            listaProductos = null;
        }
        return listaProductos;
    }
	
    
}
