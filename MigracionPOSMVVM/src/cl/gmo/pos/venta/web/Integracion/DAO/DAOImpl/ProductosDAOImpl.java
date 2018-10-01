/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.ProductosDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

/**
 *
 * @author Advice70
 */
public class ProductosDAOImpl implements ProductosDAO {
	
	Logger log = Logger.getLogger( this.getClass() );
	
    public ArrayList<ProductosBean> traeProductos(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda) throws Exception{
    	log.info("ProductosDAOImpl:traeProductos inicio");
    	
    	try {
    		if (familia.equals(Constantes.STRING_CERO)){familia = null;}
            if (subfamilia.equals(Constantes.STRING_CERO)){subfamilia = null;}
            if (grupo.equals(Constantes.STRING_CERO)){grupo = null;}
		} catch (Exception e) {
			log.error("ProductosDAOImpl:traeProductos error controlado",e);
		}
    	
    	ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
        Connection con = null;
        ResultSet rs = null;
        CallableStatement st = null;
        Utils util = new Utils();
        String fecha = util.formatoFecha(util.traeFecha());
        try {
        	log.info("ProductosDAOImpl:traeProductos conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_ARTICULO(?,?,?,?,?,?,?,?,?)}";
            System.out.println("SP_BUSCAR_SEL_ARTICULO => "+familia+"|"+subfamilia+"|"+grupo+"|"+local+"|"+codigoBusqueda+"|"+codigoBarraBusqueda+"|"+tipo_busqueda+"|"+fecha);
            st = con.prepareCall(sql);
            st.setString(1, familia);
            st.setString(2, subfamilia);
            st.setString(3, grupo);
            st.setString(4, local);
            st.setString(5, codigoBusqueda);
            st.setString(6, codigoBarraBusqueda);
            st.setString(7, tipo_busqueda);
            st.setString(8, fecha);
            st.registerOutParameter(9, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(9);
            
            ProductosBean producto;
            
            while (rs.next()) {
            	log.debug("ProductosDAOImpl:traeProductos entrando ciclo while");
                producto = new ProductosBean();
                producto.setCod_barra(rs.getString("CODIGOBARRAS"));
                producto.setCodigo(rs.getString("CDG"));
                producto.setDescripcion(rs.getString("DESCRIPCION"));
                producto.setEstado(Constantes.INT_UNO);
                producto.setGrupo(rs.getString("GRUPOFAM"));
                producto.setPrecio(rs.getInt("PRECIOIVA"));
                producto.setPrecio_costo(rs.getInt("PRECIOCOSTO"));
                producto.setPrecio_sin_iva(rs.getInt("PRECIO"));
                producto.setFamilia(rs.getString("FAMILIA"));
                producto.setPromopar(rs.getString("PROMOPAR"));  
                //SE COMENTA DESARROLLO DE INDICAR ARTICULOS ARMAZONES PROPIOS
                //producto.setTipotarifa(rs.getString("TARIFA"));
                //20150324 SE SETEA MOMENTANEAMENTE LA TARIFA PARA NO PRODUCIR ERRORES 
                producto.setTipotarifa("P.V.E");
                listaProductos.add(producto); 
            }
            
            System.out.println("Descripcion de productos");
            int a = 0; 
            for(ProductosBean b : listaProductos){
            	System.out.println("P"+a+"==> "+b.getCod_barra()+"<==>"+b.getTipotarifa());
            	a++;
            }
            
            
            return listaProductos;
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:traeProductos error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ARTICULO"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:traeProductos cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:traeProductos cierre CallableStatement");
                    st.close();
                }  
                if (null != con){
                	log.warn("ProductosDAOImpl:traeProductos cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:traeProductos error", e);
            }
        }
    }
    
    public ProductosBean traeProducto(String producto, int cantidad, String local, String tipo_busqueda, String cod_barra) throws Exception
    {
    	log.info("ProductosDAOImpl:traeProducto inicio");
        ProductosBean prod = new ProductosBean();
        Connection con = null;
        CallableStatement st = null;
        ResultSet rs = null;
        Utils util = new Utils();
        String fech = util.formatoFecha(util.traeFecha());
        try {
        	log.info("ProductosDAOImpl:traeProducto conectando base datos");
        	con = ConexionFactory.INSTANCE.getConexion();
        	System.out.println("EXEC SP_BUSCAR_SEL_ARTICULO('','','','"+local+"','"+producto+"','"+cod_barra+"','"+tipo_busqueda+"','"+fech+"');");
            String sql = "{call SP_BUSCAR_SEL_ARTICULO(?,?,?,?,?,?,?,?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, "");
            st.setString(2, "");
            st.setString(3, "");
            st.setString(4, local);
            st.setString(5, producto);
            st.setString(6, cod_barra);
            st.setString(7, tipo_busqueda);
            st.setString(8, util.formatoFecha(util.traeFecha()));
            st.registerOutParameter(9, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(9);
            prod.setDescatalogado("S");  
            while (rs.next())
              {
            	log.debug("ProductosDAOImpl:traeProducto entrando ciclo while");            	
            	prod.setCod_barra(rs.getString("CODIGOBARRAS"));
                prod.setCodigo(rs.getString("CDG"));
                prod.setDescripcion(rs.getString("DESCRIPCION"));
                prod.setLiberado(Constantes.STRING_CERO);
                prod.setEstado(Constantes.INT_CERO);
                prod.setGrupoFamilia(rs.getString("GRUPOFAM"));
                prod.setSubFamilia(rs.getString("SUBFAM"));
                prod.setCantidad(cantidad);
                prod.setPrecio(rs.getInt("PRECIOIVA"));
                prod.setFamilia(rs.getString("FAMILIA"));
                prod.setPrecio_costo(rs.getInt("PRECIOCOSTO"));
                prod.setPrecio_sin_iva(rs.getInt("PRECIO"));
                prod.setImporte(prod.getCantidad()* prod.getPrecio());
                prod.setTotal(prod.getCantidad()* prod.getPrecio());
                prod.setTipoFamilia(rs.getString("TIPOFAM"));
                prod.setCodigoSap(rs.getString("CODIGOSAP"));
                prod.setDescatalogado(rs.getString("DESCATALOGADO"));  
                prod.setPromopar(rs.getString("PROMOPAR"));  
                //SE COMENTA LA TARIFA 20150324
                //prod.setTipotarifa(rs.getString("TARIFA"));
            }
            
            
            return prod;
                
            
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:traeProducto error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ARTICULO"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:traeProducto cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:traeProducto cierre CallableStatement");
                    st.close();
                }      
                if (null != con){
                	log.warn("ProductosDAOImpl:traeProducto cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:traeProducto error", e);
            }
        }
        
    }
    public ProductosBean traeProductoEsp(String producto,String tienda,String encargo,String grupo,String cdg) throws Exception
    {
    	log.info("ProductosDAOImpl:traeProductoEsp inicio");
        ProductosBean prod = new ProductosBean();
        Connection con = null;
        CallableStatement st = null;
        ResultSet rs = null;
        Utils util = new Utils();
                
        try {
        	log.info("ProductosDAOImpl:traeProductoEsp conectando base datos");
        	con = ConexionFactory.INSTANCE.getConexion();
        	
        	
            String sql = "{call SP_BUSCAR_SEL_ARTICULO_ESP_R2(?,?,?,?,?,?)}";
            System.out.println("SP_BUSCAR_SEL_ARTICULO_ESP_R2('"+producto+"','"+tienda+"','"+encargo+"','"+grupo+"','"+cdg+"');");
            st = con.prepareCall(sql);
            st.setString(1, producto);
            st.setString(2, tienda);
            st.setString(3, encargo);
            st.setString(4, grupo);
            st.setString(5, cdg);
            st.registerOutParameter(6, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(6);
            prod.setDescatalogado("S");  
            while (rs.next())
            {
            	log.debug("ProductosDAOImpl:traeProductoEsp entrando ciclo while"); 
            	System.out.println("dto =====> "+rs.getString("DTO"));
            	prod.setDto(rs.getString("DTO"));
            	prod.setCod_barra(rs.getString("CODIGOBARRAS"));
                prod.setCodigo(rs.getString("CDG"));
                prod.setDescripcion(rs.getString("DESCRIPCION"));
                prod.setLiberado(Constantes.STRING_CERO);
                prod.setEstado(Constantes.INT_CERO);
                prod.setGrupoFamilia(rs.getString("GRUPOFAM"));
                prod.setSubFamilia(rs.getString("SUBFAM"));
                prod.setCantidad(1);
                //prod.setDtototal(rs.getString("DTOTOT"));
                
                System.out.println("PRECIOIVA ==> "+rs.getInt("PRECIOIVA"));
                
                prod.setPrecio(rs.getInt("PRECIOIVA"));
                
                prod.setFamilia(rs.getString("FAMILIA"));
                
                System.out.println("PRECIOCOSTO ==> "+rs.getInt("PRECIOCOSTO"));
                
                prod.setPrecio_costo(rs.getInt("PRECIOCOSTO"));
                
                System.out.println("PRECIO !!!!!!! ==> "+rs.getInt("PRECIO"));
                
                System.out.println("DTOTOTAL ==> "+rs.getString("DTOTOTAL"));
                
                prod.setPrecio_sin_iva(rs.getInt("PRECIO"));
                prod.setImporte(prod.getCantidad()* prod.getPrecio_sin_iva());
                prod.setTotal(prod.getCantidad()* prod.getPrecio_sin_iva());
                prod.setTipoFamilia(rs.getString("TIPOFAM"));
                prod.setCodigoSap(rs.getString("CODIGOSAP"));
                prod.setDescatalogado(rs.getString("DESCATALOGADO"));  
                prod.setPreciomul(rs.getInt("PRECIOMUL")); 
                prod.setTarifalocal(rs.getString("TIPOTIENDA").trim());
                prod.setDtototal(rs.getString("DTOTOTAL"));
                prod.setTipoe(rs.getString("TIPOE"));
                System.out.println("tipotarifa  ==> "+rs.getString("TIPOTIENDA"));
            }
            
            return prod;
            
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:traeProductoEsp error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ARTICULO_ESP"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:traeProductoEsp cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:traeProductoEsp cierre CallableStatement");
                    st.close();
                }      
                if (null != con){
                	log.warn("ProductosDAOImpl:traeProductoEsp cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:traeProductoEsp error", e);
            }
        }
        
    }
    /*public ProductosBean traeProductoEsp(String producto,String tienda,String encargo) throws Exception
    {
    	log.info("ProductosDAOImpl:traeProductoEsp inicio");
        ProductosBean prod = new ProductosBean();
        Connection con = null;
        CallableStatement st = null;
        ResultSet rs = null;
        Utils util = new Utils();
                
        try {
        	log.info("ProductosDAOImpl:traeProductoEsp conectando base datos");
        	con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_ARTICULO_ESP(?,?,?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, producto);
            st.setString(2, tienda);
            st.setString(3, encargo);
            st.registerOutParameter(4, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(4);
            prod.setDescatalogado("S");  
            while (rs.next())
              {
            	log.debug("ProductosDAOImpl:traeProductoEsp entrando ciclo while");            	
            	prod.setCod_barra(rs.getString("CODIGOBARRAS"));
                prod.setCodigo(rs.getString("CDG"));
                prod.setDescripcion(rs.getString("DESCRIPCION"));
                prod.setLiberado(Constantes.STRING_CERO);
                prod.setEstado(Constantes.INT_CERO);
                prod.setGrupoFamilia(rs.getString("GRUPOFAM"));
                prod.setSubFamilia(rs.getString("SUBFAM"));
                prod.setCantidad(1);
                prod.setPrecio(rs.getInt("PRECIOIVA"));
                prod.setFamilia(rs.getString("FAMILIA"));
                prod.setPrecio_costo(rs.getInt("PRECIOCOSTO"));
                prod.setPrecio_sin_iva(rs.getInt("PRECIO"));
                prod.setImporte(prod.getCantidad()* prod.getPrecio());
                prod.setTotal(prod.getCantidad()* prod.getPrecio());
                prod.setTipoFamilia(rs.getString("TIPOFAM"));
                prod.setCodigoSap(rs.getString("CODIGOSAP"));
                prod.setDescatalogado(rs.getString("DESCATALOGADO"));  
            }
            
            
            return prod;
                
            
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:traeProductoEsp error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ARTICULO_ESP"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:traeProductoEsp cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:traeProductoEsp cierre CallableStatement");
                    st.close();
                }      
                if (null != con){
                	log.warn("ProductosDAOImpl:traeProductoEsp cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:traeProductoEsp error", e);
            }
        }
        
    }*/

    public ProductosBean traeInfoproducto(String producto) throws Exception
    {
    	log.info("ProductosDAOImpl:traeInfoproducto inicio");
        ProductosBean prod = new ProductosBean();
        Connection con = null;
        CallableStatement st = null;
        ResultSet rs = null;
                
        try {
        	log.info("ProductosDAOImpl:traeInfoproducto conectando base datos");
        	con = ConexionFactory.INSTANCE.getConexion();
        	System.out.println("traeInfoproducto ======> "+producto);
            String sql = "{call SP_UTIL_GET_INFO_ARTIC(?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, producto);            
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(2);
                    
            while (rs.next())
              {
            	log.debug("ProductosDAOImpl:traeInfoproducto entrando ciclo while");
            	prod = new ProductosBean();
            	prod.setCodigo(rs.getString("CODIGO"));
            	prod.setFamilia(rs.getString("FAMILIA"));
            	prod.setSubFamilia(rs.getString("SUBFAM"));
            	prod.setGrupoFamilia(rs.getString("GRUPOFAM"));
            	prod.setColor(rs.getString("COLOR"));
            	prod.setIndiceArticuloLib(rs.getDouble("INDICE"));
            	prod.setDesdediametro(rs.getFloat("DIAMETRO"));
            	
            }
                return prod;
                
            
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:traeInfoproducto error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_GET_INFO_ARTIC"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:traeInfoproducto cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:traeInfoproducto cierre CallableStatement");
                    st.close();
                }      
                if (null != con){
                	log.warn("ProductosDAOImpl:traeInfoproducto cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:traeInfoproducto error", e);
            }
        }
        
    }
    
	public ArrayList<ProductosBean> traeProductosGraduados(String ojo, String tipo_fam, String familia, String subfamilia, String grupo,
	           String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local, GraduacionesBean graduacion) throws Exception{
	    {
	    	log.info("ProductosDAOImpl:traeProductosGraduados inicio");
	    	try {
	    		if (familia.equals(Constantes.STRING_CERO)){familia = null;}
	            if (subfamilia.equals(Constantes.STRING_CERO)){subfamilia = null;}
	            if (grupo.equals(Constantes.STRING_CERO)){grupo = null;}
			} catch (Exception e) {
				log.error("ProductosDAOImpl:traeProductosGraduados error controlado",e);
			}
			
			double cilindro = Constantes.INT_CERO;
			double esfera = Constantes.INT_CERO;
			int eje = Constantes.INT_CERO;
			
			if (ojo.equals(Constantes.STRING_DERECHO)) {
				cilindro = graduacion.getOD_cilindro_traspuesto();
				esfera = graduacion.getOD_esfera_traspuesto();
				eje = graduacion.getOD_eje_traspuesto();
			}
			if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
				cilindro = graduacion.getOI_cilindro_traspuesto();
				esfera = graduacion.getOI_esfera_traspuesto();
				eje = graduacion.getOI_eje_traspuesto();
			}
			
	    	
	    	ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
	        Connection con = null;
	        ResultSet rs = null;
	        CallableStatement st = null;
	        
	        try {
	        	log.info("ProductosDAOImpl:traeProductosGraduados conectando base datos");
	            con = ConexionFactory.INSTANCE.getConexion();
	            //System.out.println("BUSQUEDA CRISTALES => SP_BUSCAR_SEL_ART_CRISTALES("+familia+"|"+subfamilia+"|"+grupo+"|"+local+"|"+codigoBusqueda+"|"+codigoBarraBusqueda+"|"+tipo_fam+"|"+cilindro+"|"+esfera+"|"+eje+",:z);");
	            System.out.println("BUSQUEDA CRISTALES => SP_BUSCAR_SEL_ART_CRISTALES('"+familia+"','"+subfamilia+"','"+grupo+"','"+local+"','"+codigoBusqueda+"','"+codigoBarraBusqueda+"','"+tipo_fam+"',"+cilindro+","+esfera+","+eje+",:z);");
	            String sql = "{call SP_BUSCAR_SEL_ART_CRISTALES(?,?,?,?,?,?,?,?,?,?,?)}";
	            st = con.prepareCall(sql);
	            st.setString(1, familia);
	            st.setString(2, subfamilia);
	            st.setString(3, grupo);
	            st.setString(4, local);
	            st.setString(5, codigoBusqueda);
	            st.setString(6, codigoBarraBusqueda);
	            st.setString(7, tipo_fam);
	            st.setDouble(8, cilindro);
	            st.setDouble(9, esfera);
	            st.setInt(10, eje);
	            st.registerOutParameter(11, OracleTypes.CURSOR);
	            
	            st.execute();
	            
	            rs = (ResultSet)st.getObject(11);
	            
	            ProductosBean producto;
	            while (rs.next()) {
	            	log.debug("ProductosDAOImpl:traeProductosGraduados entrando ciclo while");
	                producto = new ProductosBean();
	                producto.setCod_barra(rs.getString("CODIGOBARRAS"));
	                producto.setCodigo(rs.getString("CDG"));
	                producto.setDescripcion(rs.getString("DESCRIPCION"));
	                producto.setEstado(Constantes.INT_UNO);
	                producto.setGrupo(rs.getString("GRUPOFAM"));
	                producto.setPrecio(rs.getInt("PRECIOIVA"));
	                producto.setPrecio_costo(rs.getInt("PRECIOCOSTO"));
	                producto.setPrecio_sin_iva(rs.getInt("PRECIO"));
	                producto.setFamilia(rs.getString("FAMILIA"));
	                producto.setDiametro(rs.getInt("DIAMETRO"));
	                listaProductos.add(producto); 
	            }
	            return listaProductos;
	        } catch (Exception e) {
	        	log.error("ProductosDAOImpl:traeProductosGraduados error controlado",e);
	             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ART_CRISTALES"); 
	        } finally {
	               try{
	                if (null != rs){
	                	log.warn("ProductosDAOImpl:traeProductosGraduados cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("ProductosDAOImpl:traeProductosGraduados cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != con){
	                	log.warn("ProductosDAOImpl:traeProductosGraduados cierre Connection");
	             	   con.close();
	                } 
	            }catch(Exception e){
	            	log.error("ProductosDAOImpl:traeProductosGraduados error", e);
	            }
	        }
	    }
	}
    
    public String  mostrarMultiOferta(String codigoMulti) throws Exception{
		String respuesta =null;
		
		log.info("ProductosDAOImpl:mostrarMultiOferta inicio");
        ProductosBean prod = new ProductosBean();
        Connection con = null;
        CallableStatement st = null;
        ResultSet rs = null;
                
        try {
        	log.info("ProductosDAOImpl:traeInfoproducto conectando base datos");
        	con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_MOSTRAR_MULTI(?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, codigoMulti);            
            st.registerOutParameter(2, OracleTypes.VARCHAR);
            
            st.execute();
            
            respuesta = st.getString(2);       
                
            
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:mostrarMultiOferta error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_MOSTRAR_MULTI"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:mostrarMultiOferta cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:mostrarMultiOferta cierre CallableStatement");
                    st.close();
                }      
                if (null != con){
                	log.warn("ProductosDAOImpl:mostrarMultiOferta cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:mostrarMultiOferta error", e);
            }
        }
		return respuesta;
		
	} 
    
    public ArrayList<ProductosBean> traeProductosMulti(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda, String codigo_barra_busqueda) throws Exception{
    	log.info("ProductosDAOImpl:traeProductos inicio");
    	try {
    		if (familia.equals(Constantes.STRING_CERO)){familia = null;}
            if (subfamilia.equals(Constantes.STRING_CERO)){subfamilia = null;}
            if (grupo.equals(Constantes.STRING_CERO)){grupo = null;}
		} catch (Exception e) {
			log.error("ProductosDAOImpl:traeProductos error controlado",e);
		}

    	ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
        Connection con = null;
        ResultSet rs = null;
        CallableStatement st = null;
        Utils util = new Utils();
        
        try {
        	log.info("ProductosDAOImpl:traeProductos conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_ARTICULO_MULTI(?,?,?,?,?,?,?,?,?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, familia);
            st.setString(2, subfamilia);
            st.setString(3, grupo);
            st.setString(4, local);
            st.setString(5, codigoBusqueda);
            st.setString(6, codigoBarraBusqueda);
            st.setString(7, tipo_busqueda);
            st.setString(8, util.formatoFecha(util.traeFecha()));
            st.registerOutParameter(9, OracleTypes.CURSOR);
            st.setString(10, codigo_barra_busqueda);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(9);
            
            ProductosBean producto;
            
            while (rs.next()) {
            	log.debug("ProductosDAOImpl:traeProductos entrando ciclo while");
                producto = new ProductosBean();
                producto.setCod_barra(rs.getString("CODIGOBARRAS"));
                producto.setCodigo(rs.getString("CDG"));
                producto.setDescripcion(rs.getString("DESCRIPCION"));
                producto.setEstado(Constantes.INT_UNO);
                producto.setGrupo(rs.getString("GRUPOFAM"));
                producto.setPrecio(rs.getInt("PRECIOIVA"));
                producto.setPrecio_costo(rs.getInt("PRECIOCOSTO"));
                producto.setPrecio_sin_iva(rs.getInt("PRECIO"));
                producto.setFamilia(rs.getString("FAMILIA"));
                
                listaProductos.add(producto); 
            }
            return listaProductos;
        } catch (Exception e) {
        	log.error("ProductosDAOImpl:traeProductos error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_ARTICULO"); 
        } finally {
               try{
                if (null != rs){
                	log.warn("ProductosDAOImpl:traeProductos cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("ProductosDAOImpl:traeProductos cierre CallableStatement");
                    st.close();
                }  
                if (null != con){
                	log.warn("ProductosDAOImpl:traeProductos cierre Connection");
             	   con.close();
                } 
            }catch(Exception e){
            	log.error("ProductosDAOImpl:traeProductos error", e);
            }
        }
    }
    

}
