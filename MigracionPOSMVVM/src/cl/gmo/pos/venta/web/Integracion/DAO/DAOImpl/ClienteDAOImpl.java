package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.ClienteDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.ClienteBean;

public class ClienteDAOImpl implements ClienteDAO {

	Logger log = Logger.getLogger( this.getClass() );
	
public int  traeCodigoCliente(int valRangoInicio, int valRangoFinal){
		log.info("ClienteDAOImpl:traeCodigoCliente inicio");
		Connection con = null;	  
		ResultSet rs = null;
		CallableStatement cs = null;
		int codCliente = 0;
		
		try{
			log.info("ClienteDAOImpl:traeCodigoCliente conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_CODIGO_CLIENTE(?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setInt(1, valRangoInicio);
			cs.setInt(2, valRangoFinal);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(3);
			
			while(rs.next()){	
				log.debug("ClienteDAOImpl:traeCodigoCliente entrando ciclo while");
				codCliente = rs.getInt("CODCLIENTE");
			}
			
			return codCliente;
			
		}catch(Exception ex){
			log.error("ClienteDAOImpl:traeCodigoCliente error controlado", ex);
			return codCliente;
		} finally {
            try{
                if (null != rs){
                	log.warn("ClienteDAOImpl:traeCodigoCliente cierre ResultSet");
                    rs.close();
                }
                if (null != cs){
                	log.warn("ClienteDAOImpl:traeCodigoCliente cierre CallableStatement");
             	   	cs.close();
                }  
                if (null != con){
                	log.warn("ClienteDAOImpl:traeCodigoCliente cierre Connection");
             	   	con.close();
                }  
            }catch(SQLException e){
            	log.error("ClienteDAOImpl:traeCodigoCliente error", e);
            }
        }		
	}
	
	public String  traeCodigoLocal(String local){
		log.info("ClienteDAOImpl:traeCodigoLocal inicio");
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String codLocal = "";
		
		try{
			log.info("ClienteDAOImpl:traeCodigoLocal conectando base dato");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_COD_LOCAL(?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, local);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			
			while(rs.next()){
				log.debug("ClienteDAOImpl:traeCodigoLocal entrando ciclo while");
				codLocal = rs.getString("CODIGLOCAL");
			}
			
			
			
		}catch(Exception ex){
			log.error("ClienteDAOImpl:traeCodigoLocal error controlado", ex);
			codLocal=null;
		} finally {
            try{
                if (null != rs){
                	log.warn("ClienteDAOImpl:traeCodigoLocal cierre ResultSet");
                    rs.close();
                }
                if (null != cs){
                	log.warn("ClienteDAOImpl:traeCodigoLocal cierre CallableStatement");
             	   	cs.close();
                }  
                if (null != con){
                	log.warn("ClienteDAOImpl:traeCodigoLocal cierre Connection");
             	   	con.close();
                }  
            }catch(SQLException e){
            	log.error("ClienteDAOImpl:traeCodigoLocal error", e);
            }
        }	
		return codLocal;
	}
	
	public ArrayList<ClienteBean> traeClientes(String nif, String nombre,
			String apellido, String codigo) throws Exception{
		log.info("ClienteDAOImpl:traeClientes inicio");
		ArrayList<ClienteBean> listaClientes = new ArrayList<ClienteBean>();
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		try {
			log.info("ClienteDAOImpl:traeClientes conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_CLIENTE(?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, nif);
			cs.setString(2, nombre);
			cs.setString(3, apellido);
			cs.setString(4, null);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(5);
			
			ClienteBean cliente;
			
			while (rs.next()) {
				log.debug("ClienteDAOImpl:traeClientes entrando ciclo while");
				cliente = new ClienteBean();
				cliente.setCodigo(rs.getString("CODIGO"));
				cliente.setNif(rs.getString("RUT"));
				if (null == rs.getString("DVNIF")) {
					cliente.setDvnif("sin/dv");
				}else{
					cliente.setDvnif(rs.getString("DVNIF"));
				}
				cliente.setFecha_nac(rs.getString("FECHANAC"));
				cliente.setApellido(rs.getString("APELLIDO"));
				cliente.setNombre(rs.getString("NOMBRE"));		
				cliente.setAgente(rs.getString("AGENTE"));
				cliente.setTipo_via(rs.getString("TIPOVIA"));
				cliente.setDireccion(rs.getString("VIA"));
				cliente.setNumero(rs.getString("NUMERO"));
				cliente.setPoblacion(rs.getString("LOCALIDAD"));
				cliente.setProvincia(rs.getInt("COD_PROVINCIA"));
				cliente.setCodigo_postal(rs.getString("CODIGOPOSTAL"));
				cliente.setPersona_contacto(rs.getString("PERSONACONTACTO"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setFono_casa(rs.getString("TELEFONO"));				
				cliente.setGiro(rs.getInt("COD_GIRO"));
				cliente.setProvincia_desc(rs.getString("PROVINCIA"));
				cliente.setRazon_social(rs.getString("RAZON_APELLIDO"));
				
				listaClientes.add(cliente);
			}
            return listaClientes;
		} catch (SQLException e) {   
			log.error("ClienteDAOImpl:traeClientes error controlado", e);
            throw new Exception("Error en DAO, al traer los clientes disponibles"); 
       } finally {
              try{
               if (null != rs){
            	   log.warn("ClienteDAOImpl:traeClientes cierre ResultSet");
                   rs.close();
               }
               if (null != cs){
            	   log.warn("ClienteDAOImpl:traeClientes cierre CallableStatement");
            	   cs.close();
               }  
               if (null != con){
            	   log.warn("ClienteDAOImpl:traeClientes cierre Connection");
            	   con.close();
               }  
           }catch(SQLException e){
        	   log.error("ClienteDAOImpl:traeClientes error", e);
        	   throw new Exception("Error en DAO, Al cerrar las conexiones"); 
           }
       }
		
		
	}

	/* (sin Javadoc)
	 * @see cl.gmo.pos.venta.web.Integracion.DAO.ClienteDAO#traeCliente(java.lang.String, java.lang.String)
	 */
	public ClienteBean traeCliente(String nif, String codigo) throws Exception
	{
		log.info("ClienteDAOImpl:traeCliente inicio");
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		String telc ="", telm ="",poblacion="";
		try {
			log.info("ClienteDAOImpl:traeCliente conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_CLIENTE(?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, nif);
			cs.setString(2, null);
			cs.setString(3, null);
			cs.setString(4, codigo);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(5);
			
			ClienteBean cliente = new ClienteBean();
			
			while (rs.next()) {
				
				if(null == rs.getString("TELEFONO")  || rs.getString("TELEFONO").equals("") || rs.getString("TELEFONO").length() < 4){
					telc = "";
					System.out.println("PASO POR ACA TELEFONO 0");
				}else{
					telc = rs.getString("TELEFONO");
				}
				
				if(null == rs.getString("MOVIL")  || rs.getString("MOVIL").equals("") || rs.getString("MOVIL").length() < 4){
					telm = "";
					System.out.println("PASO POR ACA MOVIL 0");
				}else{
					telm = rs.getString("MOVIL");
									
				}
				if(null == rs.getString("LOCALIDAD") || rs.getString("LOCALIDAD").equals("") ){
					poblacion = "";
				}else{
					poblacion = rs.getString("LOCALIDAD");
									
				}
			
				log.debug("ClienteDAOImpl:traeCliente entrando ciclo while");
				cliente.setCodigo(rs.getString("CODIGO"));
				cliente.setNif(rs.getString("RUT"));
				cliente.setDvnif(rs.getString("DVNIF"));
				cliente.setFecha_nac(rs.getString("FECHANAC"));
				cliente.setApellido(rs.getString("APELLIDO"));
				cliente.setNombre(rs.getString("NOMBRE"));		
				cliente.setAgente(rs.getString("AGENTE"));
				cliente.setTipo_via(rs.getString("TIPOVIA"));
				cliente.setDireccion(rs.getString("VIA"));
				cliente.setNumero(rs.getString("NUMERO"));
				cliente.setPoblacion(poblacion);
				//cliente.setProvincia(rs.getInt("COD_PROVINCIA"));
				cliente.setProvincia_cliente(rs.getString("COD_PROVINCIA"));
				cliente.setCodigo_postal(rs.getString("CODIGOPOSTAL"));
				cliente.setPersona_contacto(rs.getString("PERSONACONTACTO"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setFono_casa(telc);		
				cliente.setFono_movil(telm);
				cliente.setGiro(rs.getInt("COD_GIRO"));
				//cliente.setProvincia(rs.getInt("COD_PROVINCIA"));
				cliente.setRazon_social(rs.getString("RAZON_APELLIDO"));
				cliente.setProfesion(rs.getString("PROFESION"));
				cliente.setSexo(rs.getString("SEXO"));
				cliente.setOcio(rs.getString("OCIO"));
				cliente.setBloque(rs.getString("BLOQUE"));
				cliente.setCliente_cliente(rs.getString("CLIENTE_CLIENTE"));
				
				//COMENTO CAMBIO REALIZADO EN LA BASE DE DATOS 20150323
				
				cliente.setMk_correo_postal(rs.getString("MK_POSTAL"));
			    cliente.setMk_correo_electronico(rs.getString("MK_EMAIL"));
			    cliente.setMk_telefonia(rs.getString("MK_TELEFONIA"));
			    cliente.setMk_sms(rs.getString("MK_SMS"));
			    cliente.setMk_nodata(rs.getString("MK_NODATA"));
			
				// se comenta este campo para cuando se agregue el campo nuevo en la tabla cliente
				// cliente.setDirecciondesp(rs.getString("DIRECCIONDESP"));
			}
		 return cliente;
	} catch (SQLException e) { 
		
		log.error("ClienteDAOImpl:traeCodigoCliente error controlado", e);
        throw new Exception("Error en DAO, SP_BUSCAR_SEL_CLIENTE"); 
   } finally {
          try{
           if (null != rs){
        	   log.warn("ClienteDAOImpl:traeCliente cierre ResultSet");
               rs.close();
           }
           if (null != cs){
        	   log.warn("ClienteDAOImpl:traeCliente cierre CallableStatement");
        	   cs.close();
           }
           if (null != con){
        	   log.warn("ClienteDAOImpl:traeCliente cierre Connection");
        	   con.close();
           } 
       }catch(SQLException e){
    	   log.error("ClienteDAOImpl:traeCliente error", e);
       }
   	  }	
	}

	
	public ClienteBean traeClienteFacturable(String nif, String codigo) throws Exception
	{
		log.info("ClienteDAOImpl:traeClienteFacturable inicio");
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		
		try {
			log.info("ClienteDAOImpl:traeClienteFacturable conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_CLIENTE_FACT(?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, nif);
			cs.setString(2, codigo);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(3);
			
			ClienteBean cliente = new ClienteBean();
			
			while (rs.next()) {
				log.debug("ClienteDAOImpl:traeClienteFacturable entrando ciclo while");
				cliente.setCodigo(rs.getString("CODIGO"));
				cliente.setNif(rs.getString("RUT"));
				cliente.setDvnif(rs.getString("DVNIF"));
				cliente.setApellido(rs.getString("APELLIDO"));
				cliente.setNombre(rs.getString("NOMBRE"));	
				cliente.setDireccion(rs.getString("VIA"));
				cliente.setPoblacion(rs.getString("LOCALIDAD"));
				cliente.setProvincia_desc(rs.getString("PROVINCIA"));
				cliente.setGiro_desc(rs.getString("GIRO"));
				cliente.setProvincia_cliente(rs.getString("COD_PROVINCIA"));			
				cliente.setGiro(rs.getInt("COD_GIRO"));
			}
		 return cliente;
	} catch (SQLException e) {  
		log.error("ClienteDAOImpl:traeClienteFacturable error controlado", e);
        throw new Exception("Error en DAO, SP_BUSCAR_SEL_CLIENTE_FACT"); 
   } finally {
          try{
           if (null != rs){
        	   log.warn("ClienteDAOImpl:traeClienteFacturable cierre ResultSet");
               rs.close();
           }
           if (null != cs){
        	   log.warn("ClienteDAOImpl:traeClienteFacturable cierre CallableStatement");
        	   cs.close();
           }
           if (null != con){
        	   log.warn("ClienteDAOImpl:traeClienteFacturable cierre Connection");
        	   con.close();
           } 
       }catch(SQLException e){
    	   log.error("ClienteDAOImpl:traeClienteFacturable error", e);
       }
   }	}
	
	public int ingresoCliente(ClienteBean cliente){
		log.info("ClienteDAOImpl:ingresoCliente inicio");
		Connection conexion = null;
		CallableStatement cs = null;
		int retorno = Constantes.INT_CERO;
		String fono_casa= "",fono_movil ="",fono_trabajo="";
		try{
			log.info("ClienteDAOImpl:ingresoCliente conectando base datos");
			conexion = ConexionFactory.INSTANCE.getConexion();
					   
			cs = conexion.prepareCall("{call SP_VTA_PEDI_INS_CLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			System.out.println(cliente.getCodigo()+','+cliente.getNif()+','+cliente.getDireccion()+','+cliente.getCodigo_postal()+','+cliente.getPoblacion());
			System.out.println(cliente.getProvincia_cliente()+','+cliente.getEmail()+','+cliente.getAgente()+','+cliente.getApellido()+','+cliente.getNombre());
			System.out.println(cliente.getTipo_via()+','+cliente.getNumero()+','+cliente.getFono_casa()+','+cliente.getFono_movil()+','+cliente.getFono_trabajo());
			System.out.println(cliente.getPersona_contacto()+','+cliente.getFecha_nac()+','+cliente.getDvnif()+','+cliente.getProfesion()+','+cliente.getLocal()+','+cliente.getSexo());
			System.out.println(cliente.getMk_correo_postal()+','+cliente.getMk_correo_electronico()+','+cliente.getMk_telefonia());
			//System.out.println("Fono casa  ==> "+cliente.getFono_casa());
			if(cliente.getFono_casa() == null || cliente.getFono_casa().equals("")){
				fono_casa ="";
			}else{
				fono_casa = cliente.getFono_casa();
			}
			
			if(cliente.getFono_movil() == null || cliente.getFono_movil().equals("")){
				fono_movil = "";
			}else{
				fono_movil = cliente.getFono_movil();
			}
			if(cliente.getFono_trabajo() == null || cliente.getFono_trabajo().equals("")){				
				fono_trabajo ="";
			}else{
				fono_trabajo = cliente.getFono_trabajo();
			}
			
			
			cs.setInt(1, Integer.valueOf(cliente.getCodigo()).intValue());
			cs.setString(2, cliente.getNif());
			cs.setString(3, cliente.getDireccion());
			cs.setString(4, cliente.getCodigo_postal());
			cs.setString(5, cliente.getPoblacion());
			cs.setString(6, cliente.getProvincia_cliente());
			cs.setString(7, cliente.getEmail());
			cs.setString(8, cliente.getAgente());
			cs.setString(9, cliente.getApellido());
			cs.setString(10, cliente.getNombre());
			cs.setString(11, cliente.getTipo_via());
			cs.setString(12, cliente.getNumero());
			cs.setString(13, fono_casa);
			cs.setString(14, fono_movil);
			cs.setString(15, fono_trabajo);
			cs.setString(16, cliente.getPersona_contacto());
			cs.setString(17, cliente.getFecha_nac());
			cs.setString(18, cliente.getDvnif());
			cs.setString(19, cliente.getProfesion());
			cs.setString(20, cliente.getLocal());
			cs.setString(21, cliente.getSexo());
			
			
			if(null != cliente.getCliente_cliente()){
				cs.setInt(22, Integer.valueOf(cliente.getCliente_cliente()).intValue());
			}else{
				cs.setString(22, null);
			}
			
			cs.setString(23, cliente.getOcio());
			
			cs.setString(25, cliente.getBloque());
			
			cs.registerOutParameter(24, Types.NUMERIC);
			
			if(cliente.getGiro() == -1){
				cs.setString(26, null);
			}else{
				cs.setString(26, String.valueOf(cliente.getGiro()));
			}
			System.out.println(",cliente =>"+cliente.getCliente_cliente()+','+cliente.getOcio()+"salida"+','+cliente.getBloque()+','+cliente.getGiro());
			cs.setString(27, cliente.getMk_correo_postal());
			cs.setString(28, cliente.getMk_correo_electronico());
			cs.setString(29, cliente.getMk_telefonia());
			cs.setString(30, cliente.getMk_sms());
			cs.setString(31, cliente.getMk_nodata());
			System.out.println(",marketing =>"+cliente.getMk_correo_postal()+','+cliente.getMk_correo_electronico()+','+cliente.getMk_telefonia()+','+cliente.getMk_sms()+','+cliente.getMk_nodata());
			cs.execute();
			
			retorno = cs.getInt(24);
			System.out.println("RETORNO =>"+retorno);	
			
		}catch(SQLException ex){
			System.out.println("horror(1)===>"+ex.getMessage());
			log.error("ClienteDAOImpl:ingresoCliente error controlado", ex);
			retorno = Constantes.INT_MENOS_UNO;	
		}catch(Exception ex){
			System.out.println("horror(2)===>"+ex.getMessage());
			log.error("ClienteDAOImpl:ingresoCliente error controlado", ex);
			retorno = -1;
		}finally{
			try{
	               if (null != cs){
	            	   log.warn("ClienteDAOImpl:ingresoCliente cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != conexion){
	            	   log.warn("ClienteDAOImpl:ingresoCliente cierre Connection");
	            	   conexion.close();
	               }
	         }catch(Exception e){
	        	 log.error("ClienteDAOImpl:ingresoCliente error", e);
	         }
		}
		
		return retorno;
	}
}
