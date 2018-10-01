package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.InformeBusquedaProductoDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.BusquedaProductoBean;
import cl.gmo.pos.venta.web.beans.InformeBusquedaProductoBean;


public class InformeBusquedaProductoDAOImpl implements InformeBusquedaProductoDAO{
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ArrayList <InformeBusquedaProductoBean> traeProductos(String codigo,
			String descripcion) throws Exception {
		log.info("InformeBusquedaProductoDAOImpl:traeProductos inicio");
		Connection con = null;
		ResultSet articulos = null;
		ResultSet precio = null;
		CallableStatement cs = null;

		ArrayList <InformeBusquedaProductoBean> listaInforme = new ArrayList <InformeBusquedaProductoBean>();
		
		try{
			log.info("InformeBusquedaProductoDAOImpl:traeProductos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_ART_GENER(?,?,?,?)}";
			cs = con.prepareCall(sql);
			//cs.setString(1, "315500011");
			if(Constantes.STRING_CERO.equals(codigo)){
				cs.setString(1, null);
			}else{
				cs.setString(1, codigo);
			}
			
			cs.setString(2, descripcion);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			
			cs.execute();
			articulos = (ResultSet)cs.getObject(3);
			precio = (ResultSet)cs.getObject(4);
			
			ArrayList <BusquedaProductoBean> listaProducto = new ArrayList <BusquedaProductoBean>();
			while(precio.next()){
				log.debug("InformeBusquedaProductoDAOImpl:traeProductos entrando ciclo while");
					BusquedaProductoBean articulo = new BusquedaProductoBean();
					articulo.setPrecio(precio.getString("PRECIO"));
					articulo.setPrecioIva(precio.getString("PRECIOIVA"));
					articulo.setTarifa(precio.getString("TARIFA"));
					articulo.setArticulo(precio.getString("ARTICULO"));
					listaProducto.add(articulo);
					
			}
			while(articulos.next()){
				log.debug("InformeBusquedaProductoDAOImpl:traeProductos entrando ciclo while");
				InformeBusquedaProductoBean informe = new InformeBusquedaProductoBean();
				informe.setCdg(articulos.getString("CDG"));
				informe.setCodigoBarra(articulos.getString("CODIGOBARRAS"));
				informe.setDescripcion(articulos.getString("DESCRIPCION"));
				informe.setFamilia(articulos.getString("FAMILIA"));
				informe.setGrupoFamilia(articulos.getString("GRUPOFAM"));
				informe.setSubFamilia(articulos.getString("SUBFAM"));
				
				for(BusquedaProductoBean tmp:listaProducto){
					
					if(tmp.getArticulo().equals(informe.getCdg())){
						informe.addListaBusquedaProducto(tmp);
					}
				}
				
				listaInforme.add(informe);	
			}
			
			
			
			return listaInforme;
		}catch(Exception ex){
			log.error("InformeBusquedaProductoDAOImpl:traeProductos error controlado",ex);
			
		} finally {
            try{
                if (null != articulos){
                	log.warn("InformeBusquedaProductoDAOImpl:traeProductos cierre ResultSet");
                	articulos.close();
                }
                if (null != precio){
                	log.warn("InformeBusquedaProductoDAOImpl:traeProductos cierre ResultSet");
                	precio.close();
                }
                if (null != cs){
                	log.warn("InformeBusquedaProductoDAOImpl:traeProductos cierre CallableStatement");
             	   cs.close();
                }  
                if (null != con){
                	log.warn("InformeBusquedaProductoDAOImpl:traeProductos cierre Connection");
             	   con.close();
                }  
            }catch(SQLException e){
            	log.error("InformeBusquedaProductoDAOImpl:traeProductos error", e);
            }
        }
		return listaInforme;
	}

}
