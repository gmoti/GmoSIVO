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
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.GraduacionesDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;

public class GraduacionesDAOImpl implements GraduacionesDAO{
	Logger log = Logger.getLogger( this.getClass() );
	public GraduacionesBean traeUltimaGraduacionCliente(String cliente) throws Exception
	{
		log.info("GraduacionesDAOImpl:traeUltimaGraduacionCliente inicio");
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		GraduacionesBean graduacion = new GraduacionesBean();
		
		try {
			log.info("GraduacionesDAOImpl:traeUltimaGraduacionCliente conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_SEL_ULTIMA_GRAD(?,?)}");
			cs.setString(1, cliente);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			while (rs.next()) {
				log.debug("GraduacionesDAOImpl:traeUltimaGraduacionCliente entrando ciclo while");
				graduacion.setCodigo(0);
				graduacion.setFecha(util.formatoFecha((rs.getDate("FECHA"))));
				
				if(null != rs.getString("DOCTOR")){
					graduacion.setDoctor( rs.getString("DOCTOR_NOM") + " " + rs.getString("DOCTOR"));
				}else{
					graduacion.setDoctor( rs.getString("DOCTOR_NOM") + " " + "");
				}
				
				
				
				graduacion.setNumero(rs.getInt("NUMERO"));
				graduacion.setOrden(rs.getInt("NUMERO")+"");
				//graduacion.setOrden("");
				graduacion.setOD_adicion(rs.getDouble("ODADICION"));
				graduacion.setOD_cilindro(rs.getDouble("ODCILINDRO"));
				graduacion.setOD_eje(rs.getInt("ODEJE"));
				graduacion.setOD_esfera(rs.getDouble("ODESFERA"));
				graduacion.setOD_diametro(0.0);
				graduacion.setOD_esfera_cerca(rs.getDouble("ODESFERAC"));
				graduacion.setOD_n(rs.getDouble("ODNP1"));
				graduacion.setOD_p(rs.getDouble("ODNP2"));
				graduacion.setOI_adicion(rs.getDouble("OIADICION"));
				graduacion.setOI_cilindro(rs.getDouble("OICILINDRO"));
				graduacion.setOI_eje(rs.getInt("OIEJE"));
				graduacion.setOI_esfera(rs.getDouble("OIESFERA"));
				graduacion.setOI_diametro(0.0);
				graduacion.setOI_esfera_cerca(rs.getDouble("OIESFERAC"));
				graduacion.setOI_n(rs.getDouble("OINP1"));
				graduacion.setOI_p(rs.getDouble("OINP2"));
			}
			return graduacion;
			
		} catch(SQLException e){       
			log.error("GraduacionesDAOImpl:traeUltimaGraduacionCliente error controlado",e);
	        throw new Exception("Error en DAO: SP_GRAD_SEL_ULTIMA_GRAD");
	    }finally{
	        try{
	            if (null !=  rs){
	            	log.warn("GraduacionesDAOImpl:traeUltimaGraduacionCliente cierre ResultSet");
	                rs.close();
	            }
	            if (null !=  cs){
	            	log.warn("GraduacionesDAOImpl:traeUltimaGraduacionCliente cierre CallableStatement");
	                cs.close();
	            }
	            if (null !=  conn){
	            	log.warn("GraduacionesDAOImpl:traeUltimaGraduacionCliente cierre Connection");
	            	conn.close();
	            } 
	        }catch(SQLException e){
	        	log.error("GraduacionesDAOImpl:traeUltimaGraduacionCliente error", e);
	        	throw new Exception("Error en DAO al Cerrar conexion en traeUltimaGraduacionCliente()");
	        }            
	    }
	}
	
	public ContactologiaBean traeContactologiaClienteUltima(String cliente) throws Exception{
		log.info("GraduacionesDAOImpl:traeContactologiaClienteUltima inicio");
		ContactologiaBean bean = new ContactologiaBean();
		
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		
		try {
			log.info("GraduacionesDAOImpl:traeContactologiaClienteUltima conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_SEL_CONTAC_ULT_CLI(?,?)}");
			cs.setString(1, cliente);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			int x = Constantes.INT_CERO;
			
			while (rs.next()) {
				log.debug("GraduacionesDAOImpl:traeContactologiaCliente entrando ciclo while");
						
				bean.setCliente(rs.getInt("CLIENTE"));
				if(null != rs.getDate("FECHA")){
					String fecha = util.formatoFecha(rs.getDate("FECHA"));
					bean.setFecha(fecha);
				}else{
					bean.setTipo(rs.getString("FECHA"));
				}
								
				bean.setNumero(rs.getInt("NUMERO"));				
				bean.setTipo(rs.getString("TIPO"));
				bean.setClidefini_doctor(rs.getString("CLIDEFINI_DOCTOR"));	

				
				if(null != rs.getString("ODRADIO1")){
					bean.setOdradio1(rs.getDouble("ODRADIO1"));
				}else{
					bean.setOdradio1(null);
				}
				
				
				if(null != rs.getString("ODRADIO2")){
					bean.setOdradio2(rs.getDouble("ODRADIO2"));	
				}else{
					bean.setOdradio2(null);	
				}
				
				if(null != rs.getString("ODESFERA")){
					bean.setOdesfera(rs.getDouble("ODESFERA"));
				}else{
					bean.setOdesfera(null);	
				}
				
				if(null != rs.getString("ODCILINDRO")){
					bean.setOdcilindro(rs.getDouble("ODCILINDRO"));
				}else{
					bean.setOdcilindro(null);
				}
				
				if(null != rs.getString("ODDIAMT")){
					bean.setOddiamt(rs.getDouble("ODDIAMT"));	
				}else{
					bean.setOddiamt(null);	
				}
				
				if(null != rs.getString("ODDIAMZO")){
					bean.setOddiamz0(rs.getDouble("ODDIAMZO"));
				}else{
					bean.setOddiamz0(null);
				}
							
								
				bean.setOdbandas(rs.getString("ODBANDAS"));
				bean.setOdesp(rs.getString("ODESP"));
				bean.setOdtipo(rs.getString("ODTIPO"));
				bean.setOdmaterial(rs.getString("ODMATERIAL"));
				bean.setOdhidr(rs.getString("ODHIDR"));
				bean.setOdadic(rs.getString("ODADIC"));
				
				
				if(null != rs.getString("OIRADIO1")){
					bean.setOiradio1(rs.getDouble("OIRADIO1"));			
				}else{
					bean.setOiradio1(null);			
				}
				
				if(null != rs.getString("OIRADIO2")){
					bean.setOiradio2(rs.getDouble("OIRADIO2"));
				}else{
					bean.setOiradio2(null);
				}
				
				if(null != rs.getString("OIESFERA")){
					bean.setOiesfera(rs.getDouble("OIESFERA"));
				}else{
					bean.setOiesfera(null);
				}
				
				if(null != rs.getString("OICILINDRO")){
					bean.setOicilindro(rs.getDouble("OICILINDRO"));
				}else{
					bean.setOicilindro(null);
				}
				
				if(null != rs.getString("OIEJE")){
					bean.setOieje(rs.getInt("OIEJE"));
				}else{
					bean.setOieje(null);
				}
							
				if(null != rs.getString("OIDIAMT")){
					bean.setOidiamt(rs.getDouble("OIDIAMT"));
				}else{
					bean.setOidiamt(null);
				}
				
				if(null != rs.getString("OIDIAMZO")){
					bean.setOidiamz0(rs.getDouble("OIDIAMZO"));
				}else{
					bean.setOidiamz0(null);
				}
								
				bean.setOibandas(rs.getString("OIBANDAS"));
				bean.setOiesp(rs.getString("OIESP"));
				bean.setOitipo(rs.getString("OITIPO"));
				bean.setOimaterial(rs.getString("OIMATERIAL"));
				bean.setOihidr(rs.getString("OIHIDR"));
				bean.setOiadic(rs.getString("OIADIC"));				
				bean.setOtros(rs.getString("OTROS"));
				bean.setCalculos(rs.getString("CALCULO"));
				bean.setLaboratorio(rs.getString("LABORATORIO"));				
				
				
				if(null != rs.getString("ODPRECIO")){
					bean.setOdprecio(rs.getDouble("ODPRECIO"));
				}else{
					bean.setOdprecio(null);
				}
								
				if(null != rs.getString("OIPRECIO")){
					bean.setOiprecio(rs.getDouble("OIPRECIO"));
				}else{
					bean.setOiprecio(null);
				}
				
				if(null != rs.getString("ODPRECREP")){
					bean.setOdprecrep(rs.getDouble("ODPRECREP"));
				}else{
					bean.setOdprecrep(null);
				}
								
				if(null != rs.getString("OIPRECREP")){
					bean.setOiprecrep(rs.getDouble("OIPRECREP"));
				}else{
					bean.setOiprecrep(null);
				}
					
				
				bean.setFecped(util.formatoFecha(rs.getDate("FECPED")));				
				bean.setFecrec(util.formatoFecha(rs.getDate("FECREC")));				
				bean.setFecent(util.formatoFecha(rs.getDate("FECENT")));				
				bean.setFeccad(util.formatoFecha(rs.getDate("FECCAD")));
				bean.setRevision(util.formatoFecha(rs.getDate("REVISION")));
				
				
				bean.setLimpiador(rs.getString("LIMPIADOR"));
				bean.setConserva(rs.getString("CONSERVA"));
				bean.setEnzimatico(rs.getString("ENZIMATICO"));
				bean.setOtrosprod(rs.getString("OTROSPROD"));
				
				if(null != rs.getString("ODEJE")){
					bean.setOdeje(rs.getInt("ODEJE"));
				}else{
					bean.setOdeje(null);
				}
				
				if(null != rs.getString("ODACUMREP")){
					bean.setOdacumrep(rs.getDouble("ODACUMREP"));
				}else{
					bean.setOdacumrep(null);
				}
				
				if(null != rs.getString("OIACUMREP")){
					bean.setOiacumrep(rs.getDouble("OIACUMREP"));
				}else{
					bean.setOiacumrep(null);
				}				
				
				bean.setMarcaod(rs.getString("MARCAOD"));
				bean.setMarcaoi(rs.getString("MARCAOI"));
				bean.setSeguro(rs.getString("SEGURO"));
				bean.setRecomendacion(rs.getString("RECOMENDACION"));
	
			}
			
			
			
		} catch(SQLException e){ 
			log.error("GraduacionesDAOImpl:traeContactologiaClienteUltima error controlado",e);
            throw new Exception("Error en DAO: SP_GRAD_SEL_CONTAC_ULT_CLI");
        }finally{
            try{
                if (null !=  rs){
                	log.warn("GraduacionesDAOImpl:traeContactologiaClienteUltima cierre ResultSet");
                    rs.close();
                }
                if (null !=  cs){
                	log.warn("GraduacionesDAOImpl:traeContactologiaClienteUltima cierre CallableStatement");
                    cs.close();
                }
                if (null !=  conn){
                	log.warn("GraduacionesDAOImpl:traeContactologiaClienteUltima cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("GraduacionesDAOImpl:traeContactologiaCliente error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
            }            
        }
		
		
		return bean;
	}
	
	public ArrayList<GraduacionesBean> traeGraduacionesCliente(String cliente) throws Exception
	{
		log.info("GraduacionesDAOImpl:traeGraduacionesCliente inicio");
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		ArrayList<GraduacionesBean> lista_graduaciones = new ArrayList<GraduacionesBean>();
		
		try {
			log.info("GraduacionesDAOImpl:traeGraduacionesCliente conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_SEL_GRAD_CLIENTES(?,?)}");
			cs.setString(1, cliente);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			int x = 0;
			while (rs.next()) {
				log.debug("GraduacionesDAOImpl:traeGraduacionesCliente entrando ciclo while");
				GraduacionesBean graduacion = new GraduacionesBean();
				
				graduacion.setCodigo(x);
				graduacion.setFecha(util.formatoFecha((rs.getDate("FECHA"))));
				graduacion.setDoctor(rs.getString("DOCTOR"));
				graduacion.setNumero(rs.getInt("NUMERO"));
				graduacion.setOrden(Constantes.STRING_BLANCO);
				graduacion.setOD_adicion(rs.getDouble("ODADICION"));
				graduacion.setOD_cilindro(rs.getDouble("ODCILINDRO"));
				graduacion.setOD_eje(rs.getInt("ODEJE"));
				graduacion.setOD_esfera(rs.getDouble("ODESFERA"));
				graduacion.setOD_diametro(0.0);
				graduacion.setOD_esfera_cerca(rs.getDouble("ODESFERAC"));
				graduacion.setOD_n(rs.getDouble("ODNP1"));
				graduacion.setOD_p(rs.getDouble("ODNP2"));
				graduacion.setOI_adicion(rs.getDouble("OIADICION"));
				graduacion.setOI_cilindro(rs.getDouble("OICILINDRO"));
				graduacion.setOI_eje(rs.getInt("OIEJE"));
				graduacion.setOI_esfera(rs.getDouble("OIESFERA"));
				graduacion.setOI_diametro(0.0);
				graduacion.setOI_esfera_cerca(rs.getDouble("OIESFERAC"));
				graduacion.setOI_n(rs.getDouble("OINP1"));
				graduacion.setOI_p(rs.getDouble("OINP2"));
				graduacion.setOD_obser(rs.getString("ODOBSER"));
				graduacion.setOI_obser(rs.getString("OIOBSERVA"));
				graduacion.setDiferente_add(rs.getBoolean("DIFADICION"));

				lista_graduaciones.add(x,graduacion);
				
				x += 1;
			}
			
			return lista_graduaciones;
			
		} catch(SQLException e){        
			log.error("GraduacionesDAOImpl:traeGraduacionesCliente error controlado",e);
            throw new Exception("Error en DAO: SP_GRAD_SEL_GRAD_CLIENTES");
        }finally{
            try{
                if (null != rs){
                	log.warn("GraduacionesDAOImpl:traeGraduacionesCliente cierre ResultSet");
                    rs.close();
                }
                if (null != cs){
                	log.warn("GraduacionesDAOImpl:traeGraduacionesCliente cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("GraduacionesDAOImpl:traeGraduacionesCliente cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("GraduacionesDAOImpl:traeGraduacionesCliente error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
            }            
        }
	}

	public boolean ingresaGraduacion(GraduacionesBean graduacion) throws Exception{
		log.info("GraduacionesDAOImpl:ingresaGraduacion inicio");
		Connection conn = null;
		CallableStatement cs = null;
		int retorno = Constantes.INT_CERO;
		boolean respuesta = false;
		
		try {
			log.info("GraduacionesDAOImpl:ingresaGraduacion conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_INS_GRAD_CLIENTES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			
			cs.setInt(1,graduacion.getCliente());
			cs.setString(2, graduacion.getFecha());
			cs.setInt(3, graduacion.getNumero());
			cs.setString(4, graduacion.getDoctor());
			cs.setString(5, graduacion.getFecha_prox_revision());
			cs.setString(6, graduacion.getTipo());
			
			if(null != graduacion.getOD_esfera()){
				cs.setDouble(7, graduacion.getOD_esfera());
			}else{
				cs.setString(7, null);
			}			
			
			if(null != graduacion.getOD_cilindro()){
				cs.setDouble(8, graduacion.getOD_cilindro());
			}else{
				cs.setString(8, null);
			}			
			
			cs.setString(9, graduacion.getAgente());
			
			if(null != graduacion.getOD_eje()){
				cs.setInt(10, graduacion.getOD_eje());
			}else{
				cs.setString(10, null);
			}
			
			
			if(null != graduacion.getOD_adicion()){
				cs.setDouble(11, graduacion.getOD_adicion());
			}else{
				cs.setString(11, null);
			}
			
			if(null !=graduacion.getOD_esfera_cerca() ){
				cs.setDouble(12, graduacion.getOD_esfera_cerca());
			}else{
				cs.setString(12, null);
			}
			
			if(null != graduacion.getOD_n()){
				cs.setDouble(13,graduacion.getOD_n());
			}else{
				cs.setString(13, null);
			}		
			
			cs.setString(14, graduacion.getOD_obser());
			
			if(null != graduacion.getOI_esfera()){
				cs.setDouble(15, graduacion.getOI_esfera());
			}else{
				cs.setString(15, null);
			}
			
			if(null != graduacion.getOI_cilindro()){
				cs.setDouble(16, graduacion.getOI_cilindro());
			}else{
				cs.setString(16, null);
			}
			
			if(null !=graduacion.getOI_eje()){
				cs.setInt(17, graduacion.getOI_eje());
			}else{
				cs.setString(17, null);
			}
			
			if(null != graduacion.getOI_adicion()){
				cs.setDouble(18, graduacion.getOI_adicion());
			}else{
				cs.setString(18, null);
			}
			
			if(null != graduacion.getOI_esfera_cerca()){
				cs.setDouble(19, graduacion.getOI_esfera_cerca());
			}else{
				cs.setString(19, null);
			}
			
			if(null != graduacion.getOI_n()){
				cs.setDouble(20,graduacion.getOI_n());
			}else{
				cs.setString(20,null);
			}
			
			cs.setString(21, graduacion.getOI_obser());
			
			if(null != graduacion.getOD_p()){
				cs.setDouble(22, graduacion.getOD_p());
			}else{
				cs.setString(22, null);
			}
			
			if(null != graduacion.getOI_p()){
				cs.setDouble(23, graduacion.getOI_p());
			}else{
				cs.setString(23, null);
			}
			
			if(null != graduacion.getOD_avsc()){
				cs.setDouble(24,graduacion.getOD_avsc());
			}else{
				cs.setString(24,null);
			}
			
			
			if(null != graduacion.getOI_avsc()){
				cs.setDouble(25,graduacion.getOI_avsc());
			}else{
				cs.setString(25,null);
			}
			
			if(null !=graduacion.getOD_avcc()){
				cs.setDouble(26,graduacion.getOD_avcc());
			}else{
				cs.setString(26,null);
			}
			
			if(null != graduacion.getOI_avcc()){
				cs.setDouble(27,graduacion.getOI_avcc());
			}else{
				cs.setString(27, null);
			}			
			
			if(null == graduacion.getOD_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOD_cantidad())){
				cs.setString(28,null);
			}else{
				cs.setInt(28, Integer.parseInt(graduacion.getOD_cantidad()));
			}
			
			if(null == graduacion.getOD_base() || (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOD_base()))){
				cs.setString(29, null);
			}else{
				cs.setString(29, graduacion.getOD_base());
			}			
			
			if(null == graduacion.getOD_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOD_altura())){
				cs.setString(30, null);
			}else{
				cs.setDouble(30, Double.parseDouble(graduacion.getOD_altura()));
			}			
			
			if(null == graduacion.getOI_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOI_cantidad())){
				cs.setString(31,null);
			}else{
				cs.setInt(31, Integer.parseInt(graduacion.getOI_cantidad()));
			}	
			
			if(null == graduacion.getOI_base()|| (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOI_base()))){
				cs.setString(32, null);
			}else{
				cs.setString(32, graduacion.getOI_base());
			}
			
			
			if(null == graduacion.getOI_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOI_altura())){
				cs.setString(33, null);
			}else{
				cs.setDouble(33, Double.parseDouble(graduacion.getOI_altura()));
			}
			
			
			cs.setString(34, graduacion.getFecha_emision());
			
			cs.registerOutParameter(35, Types.NUMERIC);
			cs.setString(36, graduacion.getFecha_ant());
			if (graduacion.isDiferente_add()) {
				cs.setInt(37, 1);
			}
			else
			{
				cs.setInt(37, 0);
			}
			
			cs.execute();
			
			retorno = cs.getInt(35);
			
			
			if(retorno == Constantes.INT_CERO){				
				respuesta = true;
			}else if(Constantes.INT_MENOS_UNO == retorno){
				respuesta = false;
			}
		
			
			
			
		} catch(SQLException e){     
			log.error("GraduacionesDAOImpl:ingresaGraduacion error controlado",e);
			respuesta = false;
            throw new Exception("Error en DAO: SP_GRAD_INS_GRAD_CLIENTES");
        }finally{
            try{
                if (null != cs){
                	log.warn("GraduacionesDAOImpl:ingresaGraduacion cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("GraduacionesDAOImpl:ingresaGraduacion cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("GraduacionesDAOImpl:ingresaGraduacion error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
            }            
        }
        
        return respuesta;
	}

	public int traeNumeroGraduacion(String cliente, String fecha) throws Exception{
		log.info("GraduacionesDAOImpl:traeNumeroGraduacion inicio");
		Connection conn = null;
		CallableStatement cs = null;
		
		int numero=0;
		try{
			log.info("GraduacionesDAOImpl:traeNumeroGraduacion conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_BUSCAR_SEL_NUMERO_GRAD(?,?,?)}");
			cs.setString(1, cliente);
			cs.setString(2, fecha);
			cs.registerOutParameter(3, Types.NUMERIC);
			cs.execute();
			numero = cs.getInt(3);
			
		}catch(Exception ex){
			log.error("GraduacionesDAOImpl:traeNumeroGraduacion error controlado",ex);
		}finally{
			try{
                if (null != cs){
                	log.warn("GraduacionesDAOImpl:traeNumeroGraduacion cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("GraduacionesDAOImpl:traeNumeroGraduacion cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	throw new Exception("Error en DAO al Cerrar conexion en traeNumeroGraduacion");
            }     
		}
		return numero;
	}

	public boolean ingresaContactologia(ContactologiaBean graduacion) throws Exception{
		log.info("GraduacionesDAOImpl:ingresaContactologia inicio");
		Connection conn = null;
		CallableStatement cs = null;
		
		boolean respuesta = false;
		
		try {
			log.info("GraduacionesDAOImpl:ingresaContactologia conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_INS_INGRESA_CONTACTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
						
			cs.setInt(1,graduacion.getCliente());			
			cs.setString(2, graduacion.getFecha());
			cs.setInt(3, graduacion.getNumero());
			cs.setString(4, graduacion.getTipo());
			cs.setString(5, graduacion.getClidefini_doctor());
			
			if(null != graduacion.getOdradio1()){
				cs.setDouble(6, graduacion.getOdradio1());
			}else{
				cs.setString(6, null);
			}
			
			if(null !=graduacion.getOdradio2()){
				cs.setDouble(7, graduacion.getOdradio2());
			}else{
				cs.setString(7, null);
			}
			
			if(null != graduacion.getOdesfera()){
				cs.setDouble(8, graduacion.getOdesfera());
			}else{
				cs.setString(8, null);
			}
			
			if(null != graduacion.getOdcilindro()){
				cs.setDouble(9, graduacion.getOdcilindro());
			}else{
				cs.setString(9, null);
			}
			
			/**********************
			 * 
			 * agregar OD eje
			 * 
			 */
			
			if(null != graduacion.getOddiamt()){
				cs.setDouble(10, graduacion.getOddiamt());
			}else{
				cs.setString(10, null);
			}
			
			if(null !=graduacion.getOddiamz0()){
				cs.setDouble(11, graduacion.getOddiamz0());
			}else{
				cs.setString(11, null);
			}			
			
			cs.setString(12, graduacion.getOdbandas());
			cs.setString(13, graduacion.getOdesp());
			cs.setString(14, graduacion.getOdtipo());
			cs.setString(15, graduacion.getOdmaterial());
			cs.setString(16, graduacion.getOdhidr());
			cs.setString(17, graduacion.getOdadic());
			
			
			if(null != graduacion.getOiradio1()){
				cs.setDouble(18, graduacion.getOiradio1());
			}else{
				cs.setString(18, null);
			}
			
			if(null != graduacion.getOiradio2()){
				cs.setDouble(19, graduacion.getOiradio2());
			}else{
				cs.setString(19, null);
			}
			
			if(null != graduacion.getOiesfera()){
				cs.setDouble(20, graduacion.getOiesfera());
			}else{
				cs.setString(20, null);
			}
			
			if(null != graduacion.getOicilindro()){
				cs.setDouble(21, graduacion.getOicilindro());
			}else{
				cs.setString(21, null);
			}
			
			if(null != graduacion.getOieje()){
				cs.setDouble(22, graduacion.getOieje());
			}else{
				cs.setString(22, null);
			}
			
			if(null != graduacion.getOidiamt()){
				cs.setDouble(23, graduacion.getOidiamt());
			}else{
				cs.setString(23, null);
			}
			
			if(null != graduacion.getOidiamz0()){
				cs.setDouble(24, graduacion.getOidiamz0());
			}else{
				cs.setString(24, null);
			}
			
			cs.setString(25, graduacion.getOibandas());
			cs.setString(26, graduacion.getOiesp());
			cs.setString(27, graduacion.getOitipo());
			cs.setString(28, graduacion.getOimaterial());
			cs.setString(29, graduacion.getOihidr());
			cs.setString(30, graduacion.getOiadic());
			
			cs.setString(31, graduacion.getOtros());
			cs.setString(32, graduacion.getCalculos());
			cs.setString(33, graduacion.getLaboratorio());
			cs.setString(34, graduacion.getFecped());
			cs.setString(35, graduacion.getFecrec());
			cs.setString(36, graduacion.getFecent());
			cs.setString(37, graduacion.getFeccad());
			
			if(null != graduacion.getOdprecio()){
				cs.setDouble(38, graduacion.getOdprecio());
			}else{
				cs.setString(38, null);
			}
			
			if(null != graduacion.getOiprecio()){
				cs.setDouble(39, graduacion.getOiprecio());
			}else{
				cs.setString(39, null);			
			}
			
			if(null != graduacion.getOdprecrep()){
				cs.setDouble(40, graduacion.getOdprecrep());
			}else{
				cs.setString(40, null);			
			}
			
			if(null != graduacion.getOiprecrep()){
				cs.setDouble(41, graduacion.getOiprecrep());
			}else{
				cs.setString(41, null);		
			}
				
			
			cs.setString(42, graduacion.getRevision());
			
			cs.setString(43, graduacion.getLimpiador());
			cs.setString(44, graduacion.getConserva());
			cs.setString(45, graduacion.getEnzimatico());
			cs.setString(46, graduacion.getOtrosprod());
			
			if(null != graduacion.getOdeje()){
				cs.setDouble(47, graduacion.getOdeje());
			}else{
				cs.setString(47, null);			
			}
			
			if(null != graduacion.getOdacumrep()){
				cs.setDouble(48, graduacion.getOdacumrep());
			}else{
				cs.setString(48, null);			
			}
			
			if(null != graduacion.getOiacumrep()){
				cs.setDouble(49, graduacion.getOiacumrep());
			}else{
				cs.setString(49, null);
			}		
			
			cs.setString(50, graduacion.getMarcaod());
			cs.setString(51, graduacion.getMarcaoi());
			cs.setString(52, graduacion.getSeguro());
			cs.setString(53, graduacion.getRecomendacion());
			cs.setString(54, graduacion.getFecha_ant());
			System.out.println("Entro al ingreso de contactologia DAO impl");
			cs.execute();
		
			respuesta = true;
			
			
		} catch(SQLException e){     
			log.error("GraduacionesDAOImpl:ingresaContactologia error controlado",e);
			respuesta = false;
            throw new Exception("Error en DAO: SP_GRAD_INS_INGRESA_CONTACTO SQLException");
        }catch(Exception ex){
        	ex.printStackTrace();        	
        	respuesta =  false;
        	throw new Exception("Error en DAO: SP_GRAD_INS_INGRESA_CONTACTO EXCEPTION");
        }finally{
            try{
                if (null != cs){
                	log.warn("GraduacionesDAOImpl:ingresaContactologia cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("GraduacionesDAOImpl:ingresaContactologia cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
            }            
        }
        
        return respuesta;
	}
	
	public ArrayList<ContactologiaBean> traeContactologiaCliente(String cliente) throws Exception{
		log.info("GraduacionesDAOImpl:traeContactologiaCliente inicio");
		ArrayList<ContactologiaBean> lista_contacto = new ArrayList<ContactologiaBean>();
		
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		
		try {
			log.info("GraduacionesDAOImpl:traeContactologiaCliente conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_SEL_CONTACTO_CLIENTES(?,?)}");
			cs.setString(1, cliente);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			int x = Constantes.INT_CERO;
			
			while (rs.next()) {
				log.debug("GraduacionesDAOImpl:traeContactologiaCliente entrando ciclo while");
				ContactologiaBean bean = new ContactologiaBean();				
				bean.setNumero(x);
								
				bean.setCliente(rs.getInt("CLIENTE"));
				if(null != rs.getDate("FECHA")){
					String fecha = util.formatoFecha(rs.getDate("FECHA"));
					bean.setFecha(fecha);
				}else{
					bean.setTipo(rs.getString("FECHA"));
				}
								
				bean.setNumero(rs.getInt("NUMERO"));				
				bean.setTipo(rs.getString("TIPO"));
				bean.setClidefini_doctor(rs.getString("CLIDEFINI_DOCTOR"));	
				bean.setNifdoctor(rs.getString("NIFDOCTOR"));
				bean.setDvnifdoctor(rs.getString("DVNIFDOCTOR"));
				
				if(null != rs.getString("APELLIDODOCTOR")){
					bean.setNombre_doctor(rs.getString("NOMBREDOCTOR") +" "+ rs.getString("APELLIDODOCTOR"));
				}else{
					bean.setNombre_doctor(rs.getString("NOMBREDOCTOR") +" ");
				}
				
				
				if(null != rs.getString("ODRADIO1")){
					bean.setOdradio1(rs.getDouble("ODRADIO1"));
				}else{
					bean.setOdradio1(null);
				}
				
				
				if(null != rs.getString("ODRADIO2")){
					bean.setOdradio2(rs.getDouble("ODRADIO2"));	
				}else{
					bean.setOdradio2(null);	
				}
				
				if(null != rs.getString("ODESFERA")){
					bean.setOdesfera(rs.getDouble("ODESFERA"));
				}else{
					bean.setOdesfera(null);	
				}
				
				if(null != rs.getString("ODCILINDRO")){
					bean.setOdcilindro(rs.getDouble("ODCILINDRO"));
				}else{
					bean.setOdcilindro(null);
				}
				
				if(null != rs.getString("ODDIAMT")){
					bean.setOddiamt(rs.getDouble("ODDIAMT"));	
				}else{
					bean.setOddiamt(null);	
				}
				
				if(null != rs.getString("ODDIAMZO")){
					bean.setOddiamz0(rs.getDouble("ODDIAMZO"));
				}else{
					bean.setOddiamz0(null);
				}
							
								
				bean.setOdbandas(rs.getString("ODBANDAS"));
				bean.setOdesp(rs.getString("ODESP"));
				bean.setOdtipo(rs.getString("ODTIPO"));
				bean.setOdmaterial(rs.getString("ODMATERIAL"));
				bean.setOdhidr(rs.getString("ODHIDR"));
				bean.setOdadic(rs.getString("ODADIC"));
				
				
				if(null != rs.getString("OIRADIO1")){
					bean.setOiradio1(rs.getDouble("OIRADIO1"));			
				}else{
					bean.setOiradio1(null);			
				}
				
				if(null != rs.getString("OIRADIO2")){
					bean.setOiradio2(rs.getDouble("OIRADIO2"));
				}else{
					bean.setOiradio2(null);
				}
				
				if(null != rs.getString("OIESFERA")){
					bean.setOiesfera(rs.getDouble("OIESFERA"));
				}else{
					bean.setOiesfera(null);
				}
				
				if(null != rs.getString("OICILINDRO")){
					bean.setOicilindro(rs.getDouble("OICILINDRO"));
				}else{
					bean.setOicilindro(null);
				}
				
				if(null != rs.getString("OIEJE")){
					bean.setOieje(rs.getInt("OIEJE"));
				}else{
					bean.setOieje(null);
				}
							
				if(null != rs.getString("OIDIAMT")){
					bean.setOidiamt(rs.getDouble("OIDIAMT"));
				}else{
					bean.setOidiamt(null);
				}
				
				if(null != rs.getString("OIDIAMZO")){
					bean.setOidiamz0(rs.getDouble("OIDIAMZO"));
				}else{
					bean.setOidiamz0(null);
				}
								
				bean.setOibandas(rs.getString("OIBANDAS"));
				bean.setOiesp(rs.getString("OIESP"));
				bean.setOitipo(rs.getString("OITIPO"));
				bean.setOimaterial(rs.getString("OIMATERIAL"));
				bean.setOihidr(rs.getString("OIHIDR"));
				bean.setOiadic(rs.getString("OIADIC"));				
				bean.setOtros(rs.getString("OTROS"));
				bean.setCalculos(rs.getString("CALCULO"));
				bean.setLaboratorio(rs.getString("LABORATORIO"));				
				
				
				if(null != rs.getString("ODPRECIO")){
					bean.setOdprecio(rs.getDouble("ODPRECIO"));
				}else{
					bean.setOdprecio(null);
				}
								
				if(null != rs.getString("OIPRECIO")){
					bean.setOiprecio(rs.getDouble("OIPRECIO"));
				}else{
					bean.setOiprecio(null);
				}
				
				if(null != rs.getString("ODPRECREP")){
					bean.setOdprecrep(rs.getDouble("ODPRECREP"));
				}else{
					bean.setOdprecrep(null);
				}
								
				if(null != rs.getString("OIPRECREP")){
					bean.setOiprecrep(rs.getDouble("OIPRECREP"));
				}else{
					bean.setOiprecrep(null);
				}
					
				
				bean.setFecped(util.formatoFecha(rs.getDate("FECPED")));				
				bean.setFecrec(util.formatoFecha(rs.getDate("FECREC")));				
				bean.setFecent(util.formatoFecha(rs.getDate("FECENT")));				
				bean.setFeccad(util.formatoFecha(rs.getDate("FECCAD")));
				bean.setRevision(util.formatoFecha(rs.getDate("REVISION")));
				
				
				bean.setLimpiador(rs.getString("LIMPIADOR"));
				bean.setConserva(rs.getString("CONSERVA"));
				bean.setEnzimatico(rs.getString("ENZIMATICO"));
				bean.setOtrosprod(rs.getString("OTROSPROD"));
				
				if(null != rs.getString("ODEJE")){
					bean.setOdeje(rs.getInt("ODEJE"));
				}else{
					bean.setOdeje(null);
				}
				
				if(null != rs.getString("ODACUMREP")){
					bean.setOdacumrep(rs.getDouble("ODACUMREP"));
				}else{
					bean.setOdacumrep(null);
				}
				
				if(null != rs.getString("OIACUMREP")){
					bean.setOiacumrep(rs.getDouble("OIACUMREP"));
				}else{
					bean.setOiacumrep(null);
				}				
				
				bean.setMarcaod(rs.getString("MARCAOD"));
				bean.setMarcaoi(rs.getString("MARCAOI"));
				bean.setSeguro(rs.getString("SEGURO"));
				bean.setRecomendacion(rs.getString("RECOMENDACION"));
	

				lista_contacto.add(x,bean);
				
				x += 1;
			}
			
			
			
		} catch(SQLException e){ 
			log.error("GraduacionesDAOImpl:traeContactologiaCliente error controlado",e);
            throw new Exception("Error en DAO: SP_GRAD_SEL_GRAD_CLIENTES");
        }finally{
            try{
                if (null != rs){
                	log.warn("GraduacionesDAOImpl:traeContactologiaCliente cierre ResultSet");
                    rs.close();
                }
                if (null != cs){
                	log.warn("GraduacionesDAOImpl:traeContactologiaCliente cierre CallableStatement");
                    cs.close();
                }
                if (null != conn){
                	log.warn("GraduacionesDAOImpl:traeContactologiaCliente cierre Connection");
                	conn.close();
                } 
            }catch(SQLException e){
            	log.error("GraduacionesDAOImpl:traeContactologiaCliente error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
            }            
        }
		
		
		return lista_contacto;
	}
	
	 public boolean existeContactologiaPresEncargo(int numero, String fecha, String cliente, String pagina) throws Exception{
		 boolean respuesta=false;
		 
		 Connection conn = null;
		 CallableStatement cs = null;
		 ResultSet rs = null;
		 
		 try{
			 
			 conn = ConexionFactory.INSTANCE.getConexion();
			 cs = conn.prepareCall("{call SP_BUSCAR_SEL_EXISTE_CONTACTO(?,?,?,?,?)}");
			 cs.setInt(1, numero);
			 cs.setString(2, fecha);
			 cs.registerOutParameter(3, OracleTypes.VARCHAR);
			 cs.setString(4, cliente);
			 cs.setString(5, pagina);
			 cs.execute();
			 
			 String respuestasp = (String)cs.getObject(3);
			 
			 if("TRUE".equals(respuestasp)){
				 respuesta=true;
			 }else{
				 respuesta=false;
			 }
						
		 }catch(Exception ex){
			ex.printStackTrace(); 
		 }finally{
	            try{
	                if (null != rs){
	                	log.warn("existeContactologiaPresEncargo cierre ResultSet");
	                    rs.close();
	                }
	                if (null != cs){
	                	log.warn("existeContactologiaPresEncargo cierre CallableStatement");
	                    cs.close();
	                }
	                if (null != conn){
	                	log.warn("existeContactologiaPresEncargo cierre Connection");
	                	conn.close();
	                } 
	            }catch(SQLException e){
	            	log.error("existeContactologiaPresEncargo error", e);
	            	throw new Exception("Error en DAO al Cerrar conexion en existeContactologiaPresEncargo");
	            }            
	        }
		 
		 return respuesta;
	 }

	 
	 public boolean modificaContactologia(ContactologiaBean graduacion) throws Exception{
			log.info("GraduacionesDAOImpl:ingresaContactologia inicio");
			Connection conn = null;
			CallableStatement cs = null;
			
			boolean respuesta = false;
			
			try {
				log.info("GraduacionesDAOImpl:ingresaContactologia conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_GRAD_INS_INGRESA_CONTACTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
							
				cs.setInt(1,graduacion.getCliente());			
				cs.setString(2, graduacion.getFecha());
				cs.setInt(3, graduacion.getNumero());
				cs.setString(4, graduacion.getTipo());
				cs.setString(5, graduacion.getClidefini_doctor());
				
				if(null != graduacion.getOdradio1()){
					cs.setDouble(6, graduacion.getOdradio1());
				}else{
					cs.setString(6, null);
				}
				
				if(null !=graduacion.getOdradio2()){
					cs.setDouble(7, graduacion.getOdradio2());
				}else{
					cs.setString(7, null);
				}
				
				if(null != graduacion.getOdesfera()){
					cs.setDouble(8, graduacion.getOdesfera());
				}else{
					cs.setString(8, null);
				}
				
				if(null != graduacion.getOdcilindro()){
					cs.setDouble(9, graduacion.getOdcilindro());
				}else{
					cs.setString(9, null);
				}
				
				/**********************
				 * 
				 * agregar OD eje
				 * 
				 */
				
				if(null != graduacion.getOddiamt()){
					cs.setDouble(10, graduacion.getOddiamt());
				}else{
					cs.setString(10, null);
				}
				
				if(null !=graduacion.getOddiamz0()){
					cs.setDouble(11, graduacion.getOddiamz0());
				}else{
					cs.setString(11, null);
				}			
				
				cs.setString(12, graduacion.getOdbandas());
				cs.setString(13, graduacion.getOdesp());
				cs.setString(14, graduacion.getOdtipo());
				cs.setString(15, graduacion.getOdmaterial());
				cs.setString(16, graduacion.getOdhidr());
				cs.setString(17, graduacion.getOdadic());
				
				
				if(null != graduacion.getOiradio1()){
					cs.setDouble(18, graduacion.getOiradio1());
				}else{
					cs.setString(18, null);
				}
				
				if(null != graduacion.getOiradio2()){
					cs.setDouble(19, graduacion.getOiradio2());
				}else{
					cs.setString(19, null);
				}
				
				if(null != graduacion.getOiesfera()){
					cs.setDouble(20, graduacion.getOiesfera());
				}else{
					cs.setString(20, null);
				}
				
				if(null != graduacion.getOicilindro()){
					cs.setDouble(21, graduacion.getOicilindro());
				}else{
					cs.setString(21, null);
				}
				
				if(null != graduacion.getOieje()){
					cs.setDouble(22, graduacion.getOieje());
				}else{
					cs.setString(22, null);
				}
				
				if(null != graduacion.getOidiamt()){
					cs.setDouble(23, graduacion.getOidiamt());
				}else{
					cs.setString(23, null);
				}
				
				if(null != graduacion.getOidiamz0()){
					cs.setDouble(24, graduacion.getOidiamz0());
				}else{
					cs.setString(24, null);
				}
				
				cs.setString(25, graduacion.getOibandas());
				cs.setString(26, graduacion.getOiesp());
				cs.setString(27, graduacion.getOitipo());
				cs.setString(28, graduacion.getOimaterial());
				cs.setString(29, graduacion.getOihidr());
				cs.setString(30, graduacion.getOiadic());
				
				cs.setString(31, graduacion.getOtros());
				cs.setString(32, graduacion.getCalculos());
				cs.setString(33, graduacion.getLaboratorio());
				cs.setString(34, graduacion.getFecped());
				cs.setString(35, graduacion.getFecrec());
				cs.setString(36, graduacion.getFecent());
				cs.setString(37, graduacion.getFeccad());
				
				if(null != graduacion.getOdprecio()){
					cs.setDouble(38, graduacion.getOdprecio());
				}else{
					cs.setString(38, null);
				}
				
				if(null != graduacion.getOiprecio()){
					cs.setDouble(39, graduacion.getOiprecio());
				}else{
					cs.setString(39, null);			
				}
				
				if(null != graduacion.getOdprecrep()){
					cs.setDouble(40, graduacion.getOdprecrep());
				}else{
					cs.setString(40, null);			
				}
				
				if(null != graduacion.getOiprecrep()){
					cs.setDouble(41, graduacion.getOiprecrep());
				}else{
					cs.setString(41, null);		
				}
					
				
				cs.setString(42, graduacion.getRevision());
				
				cs.setString(43, graduacion.getLimpiador());
				cs.setString(44, graduacion.getConserva());
				cs.setString(45, graduacion.getEnzimatico());
				cs.setString(46, graduacion.getOtrosprod());
				
				if(null != graduacion.getOdeje()){
					cs.setDouble(47, graduacion.getOdeje());
				}else{
					cs.setString(47, null);			
				}
				
				if(null != graduacion.getOdacumrep()){
					cs.setDouble(48, graduacion.getOdacumrep());
				}else{
					cs.setString(48, null);			
				}
				
				if(null != graduacion.getOiacumrep()){
					cs.setDouble(49, graduacion.getOiacumrep());
				}else{
					cs.setString(49, null);
				}		
				
				cs.setString(50, graduacion.getMarcaod());
				cs.setString(51, graduacion.getMarcaoi());
				cs.setString(52, graduacion.getSeguro());
				cs.setString(53, graduacion.getRecomendacion());
				cs.setString(54, graduacion.getFecha_ant());
				System.out.println("Entro al ingreso de contactologia DAO impl");
				cs.execute();
			
				respuesta = true;
				
				
			} catch(SQLException e){     
				log.error("GraduacionesDAOImpl:ingresaContactologia error controlado",e);
				respuesta = false;
	            throw new Exception("Error en DAO: SP_GRAD_INS_INGRESA_CONTACTO SQLException");
	        }catch(Exception ex){
	        	ex.printStackTrace();        	
	        	respuesta =  false;
	        	throw new Exception("Error en DAO: SP_GRAD_INS_INGRESA_CONTACTO EXCEPTION");
	        }finally{
	            try{
	                if (null != cs){
	                	log.warn("GraduacionesDAOImpl:ingresaContactologia cierre CallableStatement");
	                    cs.close();
	                }
	                if (null != conn){
	                	log.warn("GraduacionesDAOImpl:ingresaContactologia cierre Connection");
	                	conn.close();
	                } 
	            }catch(SQLException e){
	            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
	            }            
	        }
	        
	        return respuesta;
		}

	 public boolean modificaGraduacion(GraduacionesBean graduacion) throws Exception{
			log.info("GraduacionesDAOImpl:ingresaGraduacion inicio");
			Connection conn = null;
			CallableStatement cs = null;
			int retorno = Constantes.INT_CERO;
			boolean respuesta = false;
			
			try {
				log.info("GraduacionesDAOImpl:ingresaGraduacion conectando base datos");
				conn = ConexionFactory.INSTANCE.getConexion();
				cs = conn.prepareCall("{call SP_GRAD_INS_GRAD_CLIENTES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
				
				
				cs.setInt(1,graduacion.getCliente());
				cs.setString(2, graduacion.getFecha());
				cs.setInt(3, graduacion.getNumero());
				cs.setString(4, graduacion.getDoctor());
				cs.setString(5, graduacion.getFecha_prox_revision());
				cs.setString(6, graduacion.getTipo());
				
				if(null != graduacion.getOD_esfera()){
					cs.setDouble(7, graduacion.getOD_esfera());
				}else{
					cs.setString(7, null);
				}			
				
				if(null != graduacion.getOD_cilindro()){
					cs.setDouble(8, graduacion.getOD_cilindro());
				}else{
					cs.setString(8, null);
				}			
				
				cs.setString(9, graduacion.getAgente());
				
				if(null != graduacion.getOD_eje()){
					cs.setInt(10, graduacion.getOD_eje());
				}else{
					cs.setString(10, null);
				}
				
				
				if(null != graduacion.getOD_adicion()){
					cs.setDouble(11, graduacion.getOD_adicion());
				}else{
					cs.setString(11, null);
				}
				
				if(null !=graduacion.getOD_esfera_cerca() ){
					cs.setDouble(12, graduacion.getOD_esfera_cerca());
				}else{
					cs.setString(12, null);
				}
				
				if(null != graduacion.getOD_n()){
					cs.setDouble(13,graduacion.getOD_n());
				}else{
					cs.setString(13, null);
				}		
				
				cs.setString(14, graduacion.getOD_obser());
				
				if(null != graduacion.getOI_esfera()){
					cs.setDouble(15, graduacion.getOI_esfera());
				}else{
					cs.setString(15, null);
				}
				
				if(null != graduacion.getOI_cilindro()){
					cs.setDouble(16, graduacion.getOI_cilindro());
				}else{
					cs.setString(16, null);
				}
				
				if(null !=graduacion.getOI_eje()){
					cs.setInt(17, graduacion.getOI_eje());
				}else{
					cs.setString(17, null);
				}
				
				if(null != graduacion.getOI_adicion()){
					cs.setDouble(18, graduacion.getOI_adicion());
				}else{
					cs.setString(18, null);
				}
				
				if(null != graduacion.getOI_esfera_cerca()){
					cs.setDouble(19, graduacion.getOI_esfera_cerca());
				}else{
					cs.setString(19, null);
				}
				
				if(null != graduacion.getOI_n()){
					cs.setDouble(20,graduacion.getOI_n());
				}else{
					cs.setString(20,null);
				}
				
				cs.setString(21, graduacion.getOI_obser());
				
				if(null != graduacion.getOD_p()){
					cs.setDouble(22, graduacion.getOD_p());
				}else{
					cs.setString(22, null);
				}
				
				if(null != graduacion.getOI_p()){
					cs.setDouble(23, graduacion.getOI_p());
				}else{
					cs.setString(23, null);
				}
				
				if(null != graduacion.getOD_avsc()){
					cs.setDouble(24,graduacion.getOD_avsc());
				}else{
					cs.setString(24,null);
				}
				
				
				if(null != graduacion.getOI_avsc()){
					cs.setDouble(25,graduacion.getOI_avsc());
				}else{
					cs.setString(25,null);
				}
				
				if(null !=graduacion.getOD_avcc()){
					cs.setDouble(26,graduacion.getOD_avcc());
				}else{
					cs.setString(26,null);
				}
				
				if(null != graduacion.getOI_avcc()){
					cs.setDouble(27,graduacion.getOI_avcc());
				}else{
					cs.setString(27, null);
				}			
				
				if(null == graduacion.getOD_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOD_cantidad())){
					cs.setString(28,null);
				}else{
					cs.setInt(28, Integer.parseInt(graduacion.getOD_cantidad()));
				}
				
				if(null == graduacion.getOD_base() || (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOD_base()))){
					cs.setString(29, null);
				}else{
					cs.setString(29, graduacion.getOD_base());
				}			
				
				if(null == graduacion.getOD_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOD_altura())){
					cs.setString(30, null);
				}else{
					cs.setDouble(30, Double.parseDouble(graduacion.getOD_altura()));
				}			
				
				if(null == graduacion.getOI_cantidad() || Constantes.STRING_BLANCO.equals(graduacion.getOI_cantidad())){
					cs.setString(31,null);
				}else{
					cs.setInt(31, Integer.parseInt(graduacion.getOI_cantidad()));
				}	
				
				if(null == graduacion.getOI_base()|| (Constantes.STRING_ACTION_SELECCIONE.equals(graduacion.getOI_base()))){
					cs.setString(32, null);
				}else{
					cs.setString(32, graduacion.getOI_base());
				}
				
				
				if(null == graduacion.getOI_altura() || Constantes.STRING_BLANCO.equals(graduacion.getOI_altura())){
					cs.setString(33, null);
				}else{
					cs.setDouble(33, Double.parseDouble(graduacion.getOI_altura()));
				}
				
				
				cs.setString(34, graduacion.getFecha_emision());
				
				cs.registerOutParameter(35, Types.NUMERIC);
				
				cs.setString(36, graduacion.getFecha_ant());
				
				if (graduacion.isDiferente_add()) {
					cs.setInt(37, 1);
				}
				else
				{
					cs.setInt(37, 0);
				}
				
				cs.execute();
				
				retorno = cs.getInt(35);
				
				
				if(retorno == Constantes.INT_CERO){				
					respuesta = true;
				}else if(Constantes.INT_MENOS_UNO == retorno){
					respuesta = false;
				}
			
				
				
				
			} catch(SQLException e){     
				log.error("GraduacionesDAOImpl:ingresaGraduacion error controlado",e);
				respuesta = false;
	            throw new Exception("Error en DAO: SP_GRAD_INS_GRAD_CLIENTES");
	        }finally{
	            try{
	                if (null != cs){
	                	log.warn("GraduacionesDAOImpl:ingresaGraduacion cierre CallableStatement");
	                    cs.close();
	                }
	                if (null != conn){
	                	log.warn("GraduacionesDAOImpl:ingresaGraduacion cierre Connection");
	                	conn.close();
	                } 
	            }catch(SQLException e){
	            	log.error("GraduacionesDAOImpl:ingresaGraduacion error", e);
	            	throw new Exception("Error en DAO al Cerrar conexion en traeGraduacionesCliente()");
	            }            
	        }
	        
	        return respuesta;
		}

	 public ContactologiaBean traeContactologia(int numero, String fecha, String cliente) throws Exception{
		 ContactologiaBean bean = null;
			
		 Connection conn = null;
		 CallableStatement cs = null;
		 ResultSet rs = null;
		 Utils util = new Utils();
		 
		 try{
			 
			 conn = ConexionFactory.INSTANCE.getConexion();
			 
			 System.out.println("SP_GRAD_SEL_CONTACTOLOGIA ("+cliente+","+numero+","+fecha+")");
			 
			 cs = conn.prepareCall("{call SP_GRAD_SEL_CONTACTOLOGIA(?,?,?,?)}");
			 cs.setString(1, cliente);
			 cs.setInt(2, numero);
			 cs.setString(3, fecha);
			 cs.registerOutParameter(4, OracleTypes.CURSOR);	 
			
			 cs.execute();
			 rs = (ResultSet)cs.getObject(4);
			 
			 while (rs.next()) {
					log.debug("GraduacionesDAOImpl:traeContactologia entrando ciclo while");
					bean = new ContactologiaBean();				
														
					bean.setCliente(rs.getInt("CLIENTE"));
					if(null != rs.getDate("FECHA")){
						String fechaStr = util.formatoFecha(rs.getDate("FECHA"));
						bean.setFecha(fechaStr);
					}else{
						bean.setTipo(rs.getString("FECHA"));
					}
									
					bean.setNumero(rs.getInt("NUMERO"));				
					bean.setTipo(rs.getString("TIPO"));
					bean.setClidefini_doctor(rs.getString("CLIDEFINI_DOCTOR"));	
					bean.setNifdoctor(rs.getString("NIFDOCTOR"));
					bean.setDvnifdoctor(rs.getString("DVNIFDOCTOR"));
					
					if(null != rs.getString("APELLIDODOCTOR")){
						bean.setNombre_doctor(rs.getString("NOMBREDOCTOR") +" "+ rs.getString("APELLIDODOCTOR"));
					}else{
						bean.setNombre_doctor(rs.getString("NOMBREDOCTOR") +" ");
					}
					
					
					if(null != rs.getString("ODRADIO1")){
						bean.setOdradio1(rs.getDouble("ODRADIO1"));
					}else{
						bean.setOdradio1(null);
					}
					
					
					if(null != rs.getString("ODRADIO2")){
						bean.setOdradio2(rs.getDouble("ODRADIO2"));	
					}else{
						bean.setOdradio2(null);	
					}
					
					if(null != rs.getString("ODESFERA")){
						bean.setOdesfera(rs.getDouble("ODESFERA"));
					}else{
						bean.setOdesfera(null);	
					}
					
					if(null != rs.getString("ODCILINDRO")){
						bean.setOdcilindro(rs.getDouble("ODCILINDRO"));
					}else{
						bean.setOdcilindro(null);
					}
					
					if(null != rs.getString("ODDIAMT")){
						bean.setOddiamt(rs.getDouble("ODDIAMT"));	
					}else{
						bean.setOddiamt(null);	
					}
					
					if(null != rs.getString("ODDIAMZO")){
						bean.setOddiamz0(rs.getDouble("ODDIAMZO"));
					}else{
						bean.setOddiamz0(null);
					}
								
									
					bean.setOdbandas(rs.getString("ODBANDAS"));
					bean.setOdesp(rs.getString("ODESP"));
					bean.setOdtipo(rs.getString("ODTIPO"));
					bean.setOdmaterial(rs.getString("ODMATERIAL"));
					bean.setOdhidr(rs.getString("ODHIDR"));
					bean.setOdadic(rs.getString("ODADIC"));
					
					
					if(null != rs.getString("OIRADIO1")){
						bean.setOiradio1(rs.getDouble("OIRADIO1"));			
					}else{
						bean.setOiradio1(null);			
					}
					
					if(null != rs.getString("OIRADIO2")){
						bean.setOiradio2(rs.getDouble("OIRADIO2"));
					}else{
						bean.setOiradio2(null);
					}
					
					if(null != rs.getString("OIESFERA")){
						bean.setOiesfera(rs.getDouble("OIESFERA"));
					}else{
						bean.setOiesfera(null);
					}
					
					if(null != rs.getString("OICILINDRO")){
						bean.setOicilindro(rs.getDouble("OICILINDRO"));
					}else{
						bean.setOicilindro(null);
					}
					
					if(null != rs.getString("OIEJE")){
						bean.setOieje(rs.getInt("OIEJE"));
					}else{
						bean.setOieje(null);
					}
								
					if(null != rs.getString("OIDIAMT")){
						bean.setOidiamt(rs.getDouble("OIDIAMT"));
					}else{
						bean.setOidiamt(null);
					}
					
					if(null != rs.getString("OIDIAMZO")){
						bean.setOidiamz0(rs.getDouble("OIDIAMZO"));
					}else{
						bean.setOidiamz0(null);
					}
									
					bean.setOibandas(rs.getString("OIBANDAS"));
					bean.setOiesp(rs.getString("OIESP"));
					bean.setOitipo(rs.getString("OITIPO"));
					bean.setOimaterial(rs.getString("OIMATERIAL"));
					bean.setOihidr(rs.getString("OIHIDR"));
					bean.setOiadic(rs.getString("OIADIC"));				
					bean.setOtros(rs.getString("OTROS"));
					bean.setCalculos(rs.getString("CALCULO"));
					bean.setLaboratorio(rs.getString("LABORATORIO"));				
					
					
					if(null != rs.getString("ODPRECIO")){
						bean.setOdprecio(rs.getDouble("ODPRECIO"));
					}else{
						bean.setOdprecio(null);
					}
									
					if(null != rs.getString("OIPRECIO")){
						bean.setOiprecio(rs.getDouble("OIPRECIO"));
					}else{
						bean.setOiprecio(null);
					}
					
					if(null != rs.getString("ODPRECREP")){
						bean.setOdprecrep(rs.getDouble("ODPRECREP"));
					}else{
						bean.setOdprecrep(null);
					}
									
					if(null != rs.getString("OIPRECREP")){
						bean.setOiprecrep(rs.getDouble("OIPRECREP"));
					}else{
						bean.setOiprecrep(null);
					}
						
					
					bean.setFecped(util.formatoFecha(rs.getDate("FECPED")));				
					bean.setFecrec(util.formatoFecha(rs.getDate("FECREC")));				
					bean.setFecent(util.formatoFecha(rs.getDate("FECENT")));				
					bean.setFeccad(util.formatoFecha(rs.getDate("FECCAD")));
					bean.setRevision(util.formatoFecha(rs.getDate("REVISION")));
					
					
					bean.setLimpiador(rs.getString("LIMPIADOR"));
					bean.setConserva(rs.getString("CONSERVA"));
					bean.setEnzimatico(rs.getString("ENZIMATICO"));
					bean.setOtrosprod(rs.getString("OTROSPROD"));
					
					if(null != rs.getString("ODEJE")){
						bean.setOdeje(rs.getInt("ODEJE"));
					}else{
						bean.setOdeje(null);
					}
					
					if(null != rs.getString("ODACUMREP")){
						bean.setOdacumrep(rs.getDouble("ODACUMREP"));
					}else{
						bean.setOdacumrep(null);
					}
					
					if(null != rs.getString("OIACUMREP")){
						bean.setOiacumrep(rs.getDouble("OIACUMREP"));
					}else{
						bean.setOiacumrep(null);
					}				
					
					bean.setMarcaod(rs.getString("MARCAOD"));
					bean.setMarcaoi(rs.getString("MARCAOI"));
					bean.setSeguro(rs.getString("SEGURO"));
					bean.setRecomendacion(rs.getString("RECOMENDACION"));
					
				}
			 
			
			 
			 
						
		 }catch(Exception ex){
			ex.printStackTrace(); 
		 }finally{
	            try{
	                if (null != rs){
	                	log.warn("existeContactologiaPresEncargo cierre ResultSet");
	                    rs.close();
	                }
	                if (null != cs){
	                	log.warn("existeContactologiaPresEncargo cierre CallableStatement");
	                    cs.close();
	                }
	                if (null != conn){
	                	log.warn("existeContactologiaPresEncargo cierre Connection");
	                	conn.close();
	                } 
	            }catch(SQLException e){
	            	log.error("existeContactologiaPresEncargo error", e);
	            	throw new Exception("Error en DAO al Cerrar conexion en existeContactologiaPresEncargo");
	            }            
	        }
		 
			return bean;
	 }
}
