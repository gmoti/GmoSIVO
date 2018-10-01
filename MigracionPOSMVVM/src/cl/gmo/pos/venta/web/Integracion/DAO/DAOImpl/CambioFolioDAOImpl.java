package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.Integracion.DAO.CambioFolioDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.FolioBean;

public class CambioFolioDAOImpl implements CambioFolioDAO {
	
	Logger log = Logger.getLogger( this.getClass() );
	
	public ArrayList<FolioBean> traeFolios(String local){
		
		ArrayList<FolioBean> lista = new ArrayList<FolioBean>();
		Connection con = null;	  
		ResultSet rs = null;
		CallableStatement cs = null;
		FolioBean folio = null;
		try{
			
			log.info("CambioFolioDAOImpl:traeFolios conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_FOLIO_TIENDA(?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, local);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			while(rs.next()){	
				log.debug("CambioFolioDAOImpl:traeFolios entrando ciclo while");
				
				folio = new FolioBean();
				folio.setDesdeBoleta(rs.getInt("DESDEVTA"));
				folio.setHastaBoleta(rs.getInt("HASTAVTA"));
								
				folio.setDesdeGuia(rs.getInt("DESDETRASPASO"));
				folio.setHastaGuia(rs.getInt("HASTATRASPASO"));
				
				folio.setDesdeNota(rs.getInt("DESDEDEVOLUCION"));			
				folio.setHastaNota(rs.getInt("HASTADEVOLUCION"));
			}
			
			lista.add(folio);
			
		}catch(Exception ex){			
			ex.printStackTrace();
		}finally{

            try{
                if (null != rs){
                	log.warn("CambioFolioDAOImpl:traeFolios cierre ResultSet");
                    rs.close();
                }
                if ( null != cs ){
                	log.warn("CambioFolioDAOImpl:traeFolios cierre CallableStatement");
             	   	cs.close();
                }  
                if (null != con){
                	log.warn("CambioFolioDAOImpl:traeFolios cierre Connection");
             	   	con.close();
                }  
            }catch(SQLException e){
            	log.error("CambioFolioDAOImpl:traeFolios error", e);
            }        
		}
		
		return lista;
	}

	public FolioBean modificarFolio(FolioBean folio, String local){

		Connection con = null;	  
		CallableStatement cs = null;
		String retorno = null;
		FolioBean folioRespuesta = new FolioBean();
		try{
			log.info("CambioFolioDAOImpl:modificaFolio conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println(folio.getDesdeGuia()+"<=>"+folio.getHastaGuia()+"<=>"+folio.getDesdeBoleta()+"<=>"+folio.getHastaBoleta()+"<=>"+folio.getDesdeNota()+"<=>"+folio.getHastaNota()+"<=>"+local);
			String sql = "{call SP_FOLIO_INS_FOLIO_TIENDA(?,?,?,?,?,?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			cs.setInt(1, folio.getDesdeGuia());
			cs.setInt(2, folio.getHastaGuia());
			
			cs.setInt(3, folio.getDesdeBoleta());
			cs.setInt(4, folio.getHastaBoleta());
			
			cs.setInt(5, folio.getDesdeNota());
			cs.setInt(6, folio.getHastaNota());
			
			cs.registerOutParameter(7, OracleTypes.NUMERIC);
			cs.setString(8, local);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.VARCHAR);
			cs.execute();
			
			int respuesta = cs.getInt(7);
			String tipoFolio = cs.getString(9);
			String localErrorFolio = cs.getString(10);
			
			if(respuesta == 0){
				folioRespuesta.setRespuesta("exito");
			}else if(respuesta == -1){
				folioRespuesta.setRespuesta("error");
			}else if(respuesta == 1){
				folioRespuesta.setRespuesta(tipoFolio);
				folioRespuesta.setLocalErrorFolio(localErrorFolio);
			}else{
				folioRespuesta.setRespuesta("error");
			}
			
		}catch(Exception ex){
			retorno = "error";
			log.error("CambioFolioDAOImpl:modificaFolio error controlado", ex);
		}
		finally{
			 try{	                
	                if (null != cs){
	                	log.warn("CambioFolioDAOImpl:modificarFolio cierre CallableStatement");
	             	   	cs.close();
	                }  
	                if (null != con){
	                	log.warn("CambioFolioDAOImpl:modificarFolio cierre Connection");
	             	   	con.close();
	                }  
	            }catch(SQLException e){
	            	retorno = "error";
	            	log.error("CambioFolioDAOImpl:modificarFolio error", e);
	            }     
		}

		return folioRespuesta;
	}

	public String traeCampoLibre(String local) {
		Connection con = null;	  
		CallableStatement cs = null;
		String retorno = null;
		try{
			log.info("CambioFolioDAOImpl:traeCampoLibre conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_FOLIO_SEL_CAMPOLIBRE(?,?)}";
			cs = con.prepareCall(sql);
			
			cs.setString(1, local);
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.execute();
			
			retorno = cs.getString(2);
			
		}catch(Exception ex){
			retorno = "error";
			log.error("CambioFolioDAOImpl:traeCampoLibre error controlado", ex);
		}
		finally{
			 try{	                
	                if (null != cs){
	                	log.warn("CambioFolioDAOImpl:traeCampoLibre cierre CallableStatement");
	             	   	cs.close();
	                }  
	                if (null != con){
	                	log.warn("CambioFolioDAOImpl:traeCampoLibre cierre Connection");
	             	   	con.close();
	                }  
	            }catch(SQLException e){
	            	retorno = "";
	            	log.error("CambioFolioDAOImpl:traeCampoLibre error", e);
	            }     
		}

		return retorno;
	}
	
}
