package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;

import cl.gmo.pos.venta.web.Integracion.DAO.MedicoDao;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;

public class MedicoDaoImpl implements MedicoDao {
	Logger log = Logger.getLogger( this.getClass() );
	public int ingresaMedico(OftalmologoBean medico){
		log.info("MedicoDaoImpl:ingresaMedico inicio");
		int respuesta = -1;
		Connection conn = null;
		CallableStatement cs = null;
		try{
			log.info("MedicoDaoImpl:ingresaMedico conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_MEDICO_INS_INGRESA_MEDIC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setString(1, medico.getCodigo());
			cs.setString(2, medico.getNombre());
			cs.setString(3, medico.getDireccion());
			cs.setString(4, medico.getLocali());
			cs.setString(5, medico.getCodpos());
			cs.setString(6, medico.getTfno());
			cs.setString(7, medico.getEmail());
			cs.setString(8, medico.getFax());
			cs.setString(9, medico.getApelli());
			cs.setString(10, medico.getProvinci());
			cs.setString(11, medico.getExterno());
			cs.setString(12, medico.getNif());
			cs.setString(13, medico.getLnif());
			cs.registerOutParameter(14, Types.NUMERIC);
			cs.registerOutParameter(15, Types.NUMERIC);
						
			cs.execute();
			
			respuesta = cs.getInt(14);
			int codigo = cs.getInt(15);
			if (respuesta==0 && codigo != 0) {
				medico.setCodigo(String.valueOf(codigo));
			}
			
			
			
		}catch(Exception ex){
			log.error("MedicoDaoImpl:ingresaMedico error controlado",ex);
			respuesta = -1;
		}finally{
            try{
                if (null != cs){
                	log.warn("MedicoDaoImpl:ingresaMedico cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("MedicoDaoImpl:ingresaMedico cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("MedicoDaoImpl:ingresaMedico error", e);
            }            
        }
		
		return respuesta;
	}

	public String traeCdgDoctor(){
		
		
		Connection conexion = null;
		ResultSet rs = null;
		CallableStatement st= null;
		
		OftalmologoBean doctor = null;
		try{			
			conexion = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_CDG_DOCTOR(?)}";
            st = conexion.prepareCall(sql);               
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(1);
            
            while(rs.next()){            	
            	doctor = new OftalmologoBean();           	
            	doctor.setCodigo(rs.getString("CODIGO"));            	
            }
            
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
            try{
            	if (null != rs){
                	rs.close();
                }
                if (null != st){
                	st.close();
                }
                if (null != conexion){
                	conexion.close();
                } 
                
            }catch(SQLException e){
            	e.printStackTrace();
            }            
        }
		return doctor.getCodigo();
	}

	public ArrayList<OftalmologoBean> traeDoctores(String codigo, String nif, String apellido, String nombre){
		
		ArrayList<OftalmologoBean> listaMedicos = new ArrayList<OftalmologoBean>();
		Connection conexion = null;
		ResultSet rs = null;
		CallableStatement st= null;
		OftalmologoBean  medico = null;
		
		try{
			
			conexion = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_LISTA_DOCTOR(?,?,?,?,?)}";
			st = conexion.prepareCall(sql);
			st.setString(1, codigo);
			st.setString(2, nif);
			st.setString(3, apellido);
			st.setString(4, nombre);
			st.registerOutParameter(5, OracleTypes.CURSOR);
			st.execute();
			rs = (ResultSet)st.getObject(5);
			
			while(rs.next()){
				medico = new OftalmologoBean();		
				
				if(null != rs.getString("CDG")){
					medico.setCodigo(rs.getString("CDG"));
				}
				
				
				if(null != rs.getString("NOMBRE") && !("".equals(rs.getString("NOMBRE").trim()))){
					medico.setNombre(rs.getString("NOMBRE"));
				}
				
				if(null != rs.getString("APELLI") && !("".equals(rs.getString("APELLI").trim()))){
					medico.setApelli(rs.getString("APELLI"));
				}
				
				if(null != rs.getString("CODPOS") && !("".equals(rs.getString("CODPOS").trim()))){
					medico.setCodpos(rs.getString("CODPOS"));
				}
				
				if(null != rs.getString("DIRECCI") && !("".equals(rs.getString("DIRECCI").trim()))){
					medico.setDireccion(rs.getString("DIRECCI"));
				}
				
				if(null != rs.getString("EMAIL") && !("".equals(rs.getString("EMAIL").trim()))){
					medico.setEmail(rs.getString("EMAIL"));
				}
				
				if(null != rs.getString("EXTERNO") && !("".equals(rs.getString("EXTERNO").trim()))){
					medico.setExterno(rs.getString("EXTERNO"));
				}
				
				if(null != rs.getString("FAX") && !("".equals(rs.getString("FAX").trim()))){
					medico.setFax(rs.getString("FAX"));
				}
				
				if(null != rs.getString("LETRANIF") && !("".equals(rs.getString("LETRANIF").trim()))){
					medico.setLnif(rs.getString("LETRANIF"));
				}
				
				if(null != rs.getString("LOCALI") && !("".equals(rs.getString("LOCALI").trim()))){
					medico.setLocali(rs.getString("LOCALI"));
				}
				
				if(null != rs.getString("NIF") && !("".equals(rs.getString("NIF").trim()))){
					medico.setNif(rs.getString("NIF"));
				}			
				
				if(null != rs.getString("PROVINCI") && !("".equals(rs.getString("PROVINCI").trim()))){
					medico.setProvinci(rs.getString("PROVINCI"));
				}
				
				if(null != rs.getString("TFNO") && !("".equals(rs.getString("TFNO").trim()))){
					medico.setTfno(rs.getString("TFNO"));
				}
				
				if(null != medico.getNombre() && null != medico.getCodigo() && null != medico.getApelli() && null != medico.getNif() && null != medico.getLnif()){
					listaMedicos.add(medico);
				}						
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();			
		}finally{
            try{
            	if (null != rs){
                	rs.close();
                }
                if (null != st){
                	st.close();
                }
                if (null != conexion){
                	conexion.close();
                } 
                
            }catch(SQLException e){
            	e.printStackTrace();
            }            
        }		
		return listaMedicos;
	}

	public static OftalmologoBean traeMedico(String codigo, String nif, String apellido, String nombre){
		
		
		Connection conexion = null;
		ResultSet rs = null;
		CallableStatement st= null;
		OftalmologoBean  medico = null;
		
		try{
			
			conexion = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_LISTA_DOCTOR(?,?,?,?,?)}";
			st = conexion.prepareCall(sql);
			st.setString(1, codigo);
			st.setString(2, nif);
			st.setString(3, apellido);
			st.setString(4, nombre);
			st.registerOutParameter(5, OracleTypes.CURSOR);
			st.execute();
			rs = (ResultSet)st.getObject(5);
			
			while(rs.next()){
				medico = new OftalmologoBean();
				
				if(null != rs.getString("CDG")){
					medico.setCodigo(rs.getString("CDG"));
				}else{
					medico.setCodigo("");
				}
				
				
				if(null != rs.getString("NOMBRE") && !("".equals(rs.getString("NOMBRE")))){
					medico.setNombre(rs.getString("NOMBRE"));
				}else{
					medico.setNombre("");
				}
				
				if(null != rs.getString("APELLI") && !("".equals(rs.getString("APELLI")))){
					medico.setApelli(rs.getString("APELLI"));
				}else{
					medico.setApelli("");
				}
				
				if(null != rs.getString("CODPOS") && !("".equals(rs.getString("CODPOS")))){
					medico.setCodpos(rs.getString("CODPOS"));
				}else{
					medico.setCodpos(rs.getString("CODPOS"));
				}
				
				if(null != rs.getString("DIRECCI") && !("".equals(rs.getString("DIRECCI")))){
					medico.setDireccion(rs.getString("DIRECCI"));
				}else{
					medico.setDireccion("");
				}
				
				if(null != rs.getString("EMAIL") && !("".equals(rs.getString("EMAIL")))){
					medico.setEmail(rs.getString("EMAIL"));
				}else{
					medico.setEmail("");
				}
				
				
				if(null != rs.getString("EXTERNO") && !("".equals(rs.getString("EXTERNO")))){
					medico.setExterno(rs.getString("EXTERNO"));
				}else{
					medico.setExterno("");
				}
				
				if(null != rs.getString("FAX") && !("".equals(rs.getString("FAX")))){
					medico.setFax(rs.getString("FAX"));
				}else{
					medico.setFax("");
				}
				
				if(null != rs.getString("LETRANIF") && !("".equals(rs.getString("LETRANIF")))){
					medico.setLnif(rs.getString("LETRANIF"));
				}else{
					medico.setLnif("");
				}
				
				if(null != rs.getString("LOCALI") && !("".equals(rs.getString("LOCALI")))){
					medico.setLocali(rs.getString("LOCALI"));
				}else{
					medico.setLocali("");
				}
				
				if(null != rs.getString("NIF") && !("".equals(rs.getString("NIF")))){
					medico.setNif(rs.getString("NIF"));
				}else{
					medico.setNif(rs.getString("NIF"));
				}			
				
				if(null != rs.getString("PROVINCI") && !("".equals(rs.getString("PROVINCI")))){
					medico.setProvinci(rs.getString("PROVINCI"));
				}else{
					medico.setProvinci("");
				}
				
				if(null != rs.getString("TFNO") && !("".equals(rs.getString("TFNO")))){
					medico.setTfno(rs.getString("TFNO"));
				}else{
					medico.setTfno("");
				}
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();			
		}finally{
            try{
            	if (null != rs){
                	rs.close();
                }
                if (null != st){
                	st.close();
                }
                if (null != conexion){
                	conexion.close();
                } 
            }catch(SQLException e){
            	e.printStackTrace();
            }            
        }		
		return medico;
		
	}
}
