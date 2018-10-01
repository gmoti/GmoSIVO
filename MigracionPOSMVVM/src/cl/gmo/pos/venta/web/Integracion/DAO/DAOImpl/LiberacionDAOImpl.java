package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.Integracion.DAO.LibearcionDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;

public class LiberacionDAOImpl implements LibearcionDAO {
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public boolean cambioEstadoLiberacion(String codigo_venta, String estado, String grupo, int identPedvtln, String articulo) {
		log.info("LiberacionDAOImpl:cambioEstadoLiberacion inicio");
		Connection conexion = null;
		CallableStatement cs = null;
		String mensajeDb = "";
		boolean respueta = false;
		try{	
			log.info("LiberacionDAOImpl:cambioEstadoLiberacion conectando base datos");
			conexion = ConexionFactory.INSTANCE.getConexion();			   
			cs = conexion.prepareCall("{call SP_LIB_VTA_PEDIDO_UPD_ESTADO(?,?,?,?,?,?,?) }");
			cs.setString(1, codigo_venta);
			cs.setString(2, estado);
			cs.registerOutParameter(3, Types.NUMERIC);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.setInt(5, Integer.parseInt(grupo));
			cs.setInt(6, identPedvtln);
			cs.setString(7, articulo);
			cs.execute();
			
			int codigo_error = cs.getInt(3);
			if(0 == codigo_error){
				respueta = true;
			}
			mensajeDb = cs.getString(4);
			
		}catch(Exception ex){
			log.error("LiberacionDAOImpl:cambioEstadoLiberacion error controlado "+mensajeDb,ex);
			respueta = false;
		}finally{
			
			try{
	               if (null != cs){
	            	   log.warn("LiberacionDAOImpl:cambioEstadoLiberacion cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != conexion){
	            	   log.warn("LiberacionDAOImpl:cambioEstadoLiberacion cierre Connection");
	            	   conexion.close();
	               }
	           }catch(Exception e){
	        	   log.error("LiberacionDAOImpl:cambioEstadoLiberacion error", e);
	           }			
		}
		return respueta;
	}

}
