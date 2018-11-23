/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;



import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.UtilesDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.PrecioEspecialBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.ConvenioFamBean;
import cl.gmo.pos.venta.web.beans.ConvenioLnBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.ListaEstadosBean;
import cl.gmo.pos.venta.web.beans.MenuBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.SuplementosValores;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.beans.TipoViaBean;
import cl.gmo.pos.venta.web.beans.TipoMotivoDevolucionBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
/**
*
* @author Advice70
*/
public class UtilesDAOImpl implements UtilesDAO{
	Logger log = Logger.getLogger( this.getClass() );
public  ArrayList<TipoMotivoDevolucionBean> traeMotivoDevolucion() throws Exception{
		ArrayList<TipoMotivoDevolucionBean> lista_mot = new ArrayList<TipoMotivoDevolucionBean>();
		log.info("UtilesDAOImpl:traeMotivoDevolucion inicio");
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
               
        try {
        	log.info("UtilesDAOImpl:traeMotivoDevolucion conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_TIPODEVO(?)}";
            st = conn.prepareCall(sql);           
            st.registerOutParameter(1, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(1);
            while (rs.next())
            {      
            	log.debug("UtilesDAOImpl:traeMotivoDevolucion entrando ciclo while");
            	TipoMotivoDevolucionBean t = new TipoMotivoDevolucionBean();
            	
            	t.setCodigo(rs.getString("CODIGO"));
            	t.setDescripcion(rs.getString("DESCRIPCION"));
            	t.setMostrar(rs.getString("MOSTRAR"));
            	
            	lista_mot.add(t);
            }
           
            return lista_mot;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeMotivoDevolucion error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_TRATAMI_OPC");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeMotivoDevolucion cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeMotivoDevolucion cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeMotivoDevolucion cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeMotivoDevolucion error", e);
            }
        }
	}
	public BigDecimal traeDecuento(String usuario, String pass, String tipo) throws Exception
    {
		log.info("UtilesDAOImpl:traeDecuento inicio");
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        BigDecimal valor= new BigDecimal(-1);
        try {
        	log.info("UtilesDAOImpl:traeDecuento conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_VTA_SEL_VAL_DTO(?,?,?,?)}";
            // String sql = "{call SP_VTA_SEL_VAL_DTO_NEW(?,?,?,?)}";
            System.out.println("us => "+usuario+"pass => "+pass+" tipo =>"+tipo+"\n");
            st = conn.prepareCall(sql);
            st.setString(1, usuario);
            st.setString(2, pass);
            st.setString(3, tipo);
            st.registerOutParameter(4, OracleTypes.NUMBER);
            
            st.execute();
            
            valor = st.getBigDecimal(4);
           
            if (null == valor) {
				valor = new BigDecimal(-1);
			}
            return valor;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeDecuento error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_GET_TIENDASAP");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeDecuento cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeDecuento cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeDecuento cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeDecuento error", e);
            }
        }
       
    }
	public int traeCodigoMultioferta() throws Exception
	{
		log.info("UtilesDAOImpl:traeCodigoMultioferta inicio");
		Connection con = null;
		CallableStatement cs = null;
		int numero = 0;
		
		try {
			log.info("UtilesDAOImpl:traeCodigoMultioferta conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call  SP_VTA_DIRE_SEL_COD_MTO(?)}");
			cs.registerOutParameter(1, Types.NUMERIC);
			cs.execute();
			numero = cs.getInt(1);
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeCodigoMultioferta error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_SEL_COD_MTO"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("UtilesDAOImpl:traeCodigoMultioferta cierre CallableStatement");
                   cs.close();
               }       
               if (null != con){
            	   log.warn("UtilesDAOImpl:traeCodigoMultioferta cierre Connection");
		    	   con.close();
	           } 
           }catch(Exception e){
        	   log.error("UtilesDAOImpl:traeCodigoMultioferta error", e);
           }
       }
		return numero;
	}
	
	public ArrayList<SuplementopedidoBean> traeSuplementosObligatorios(String producto) throws Exception
	{
		log.info("UtilesDAOImpl:traeSuplementosObligatorios inicio");
		Connection conn = null;
        CallableStatement st= null;
        ResultSet rs = null;
        ArrayList<SuplementopedidoBean> suplementosObligatorios = new ArrayList<SuplementopedidoBean>();
        
        try {
        	log.info("UtilesDAOImpl:traeSuplementosObligatorios conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_TRATAMI_OBLI(?,?)}";
            st = conn.prepareCall(sql);
            
            System.out.println("Trae suplementos Obligatorios ==> "+ producto);
            st.setString(1, producto);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
            
            while (rs.next())
            {      
            	log.debug("UtilesDAOImpl:traeSuplementosObligatorios entrando ciclo while");
            	SuplementopedidoBean sup = new SuplementopedidoBean();
            	sup.setTratami(rs.getString("CDG"));
            	sup.setDescripcion(rs.getString("DESCRIPCION"));
            	
            	suplementosObligatorios.add(sup);
            }
           
            return suplementosObligatorios;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeSuplementosObligatorios error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_TRATAMI_OBLI");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeSuplementosObligatorios cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeSuplementosObligatorios cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeSuplementosObligatorios cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeSuplementosObligatorios error", e);
            }
        }
	}
	
	public ArrayList<SuplementopedidoBean> traeSuplementosOpcionales (String producto) throws Exception
	{
		log.info("UtilesDAOImpl:traeSuplementosOpcionales inicio");
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        ArrayList<SuplementopedidoBean> suplementosOpcionales = new ArrayList<SuplementopedidoBean>();
        
        try {
        	log.info("UtilesDAOImpl:traeSuplementosOpcionales conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_TRATAMI_OPC(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, producto);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
            while (rs.next())
            {      
            	log.debug("UtilesDAOImpl:traeSuplementosOpcionales entrando ciclo while");
            	SuplementopedidoBean sup = new SuplementopedidoBean();
            	sup.setTratami(rs.getString("CDG"));
            	sup.setDescripcion(rs.getString("DESCRIPCION"));
            	
            	suplementosOpcionales.add(sup);
            }
           
            return suplementosOpcionales;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeSuplementosOpcionales error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_TRATAMI_OPC");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeSuplementosOpcionales cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeSuplementosOpcionales cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeSuplementosOpcionales cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeSuplementosOpcionales error", e);
            }
        }
	}
	
	
	public String traeCodigoAgente(String agente) throws Exception
    {
		log.info("UtilesDAOImpl:traeCodigoAgente inicio");
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        String codigoAgente="";
        try {
        	log.info("UtilesDAOImpl:traeCodigoAgente conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_GET_CODIGOAGENTE(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, agente);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(2);
            
           
            while (rs.next())
            {             
            	log.debug("UtilesDAOImpl:traeCodigoAgente entrando ciclo while");
            	codigoAgente = rs.getString("CODIGOAGENTE");               
            }
           
            return codigoAgente;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeCodigoAgente error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_GET_TIENDASAP");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeCodigoAgente cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeCodigoAgente cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeCodigoAgente cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeCodigoAgente error", e);
            }
        }
       
    }
	
	public String traeCodigoLocalSap(String local) throws Exception
    {
		log.info("UtilesDAOImpl:traeCodigoLocalSap inicio");
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        String codigoSap="";
        try {
        	log.info("UtilesDAOImpl:traeCodigoLocalSap conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_GET_TIENDASAP_T(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, local);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            
            rs = (ResultSet)st.getObject(2);
            
           
            while (rs.next())
            {              
            	log.debug("UtilesDAOImpl:traeCodigoLocalSap entrando ciclo while");
            	codigoSap = rs.getString("CODIGOSAP");               
            }
           
            return codigoSap;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeCodigoLocalSap error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_GET_TIENDASAP");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeCodigoLocalSap cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeCodigoLocalSap cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeCodigoLocalSap cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeCodigoLocalSap error", e);
            }
        }
       
    }
	
	public ArrayList<TipoAlbaranBean> traeTipoAlbaranes() throws Exception
	{
		ArrayList<TipoAlbaranBean> listaTipoalbaranes = new ArrayList<TipoAlbaranBean>();				
		TipoAlbaranBean albaran = new TipoAlbaranBean();
		albaran.setCodigo('N');
		albaran.setDescripcion("NORMAL");
		listaTipoalbaranes.add(albaran);	
		albaran = new TipoAlbaranBean();
		albaran.setCodigo('D');
		albaran.setDescripcion("DEVOLUCION");
		listaTipoalbaranes.add(albaran);
		
		return listaTipoalbaranes;
		
	}
    
	public ArrayList<DivisaBean> traeDivisas() throws Exception
    {
		log.info("UtilesDAOImpl:traeDivisas inicio");
        ArrayList<DivisaBean> lista_divisas = new ArrayList<DivisaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeDivisas conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_DIVISAS(?)}";
            st = conn.prepareCall(sql);
            st.registerOutParameter(1, OracleTypes.CURSOR);
            
            st.execute();
            
           rs = (ResultSet)st.getObject(1);
            DivisaBean divisa;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeDivisas entrando ciclo while");
                divisa = new DivisaBean();
                divisa.setId(rs.getString("CDG"));
                divisa.setDescripcion(rs.getString("DESCRIPCION"));
                //divisa.setRedondear(rs.getString("REDONDEAR"));
               
                lista_divisas.add(divisa);
            }
           
            return lista_divisas;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeDivisas error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_DIVISAS");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeDivisas cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeDivisas cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeDivisas cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeDivisas error", e);
                e.printStackTrace();
            }
        }
       
    }
   
    public ArrayList<FormaPagoBean> traeFormasPago() throws Exception
    {
    	log.info("UtilesDAOImpl:traeFormasPago inicio");
        ArrayList<FormaPagoBean> lista_formasPago = new ArrayList<FormaPagoBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeFormasPago conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            st = conn.prepareCall("{call SP_UTIL_SEL_FORMAS_PAGO(?)}");
            st.registerOutParameter(1, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(1);
           
            FormaPagoBean fpago;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeFormasPago entrando ciclo while");
                fpago = new FormaPagoBean();
                fpago.setId(rs.getString("CDG"));
                fpago.setDescripcion(rs.getString("DESCRIPCION"));
                fpago.setTexto(rs.getString("TEXTO"));
               
                lista_formasPago.add(fpago);
            }
           
            return lista_formasPago;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeFormasPago error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_FORMAS_PAGO");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeFormasPago cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeFormasPago cierre CallableStatement");
                    st.close();
                }    
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeFormasPago cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeFormasPago error", e);
            }
        }
       
    }
   
    public ArrayList<IdiomaBean> traeIdiomas() throws Exception
    {
    	log.info("UtilesDAOImpl:traeIdiomas inicio");
        ArrayList<IdiomaBean> lista_Idiomas = new ArrayList<IdiomaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeIdiomas conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_IDIOMAS(?)}";
            st = conn.prepareCall(sql);
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.execute();
           
            rs = (ResultSet)st.getObject(1);
           
            IdiomaBean idioma;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeIdiomas entrando ciclo while");
                idioma = new IdiomaBean();
                idioma.setId(rs.getString("CDG"));
                idioma.setDescripcion(rs.getString("DESCRIPCION"));
               
                lista_Idiomas.add(idioma);
            }
           
            return lista_Idiomas;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeIdiomas error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_IDIOMAS");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeIdiomas cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeIdiomas cierre CallableStatement");
                    st.close();
                }        
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeIdiomas cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeIdiomas error", e);
            }
        }
       
    }
   
    public ArrayList<TipoViaBean> traeTipoVias() throws Exception
    {
    	log.info("UtilesDAOImpl:traeTipoVias inicio");
        ArrayList<TipoViaBean> lista_tipo_vias = new ArrayList<TipoViaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeTipoVias conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            st = conn.prepareCall("{call SP_UTIL_SEL_TIPOVIA(?)}");
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(1);
           
            TipoViaBean tipo_via;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeTipoVias entrando ciclo while");
            	tipo_via = new TipoViaBean();
            	tipo_via.setCodigo(rs.getString("CDG"));
            	tipo_via.setDescripcion(rs.getString("DESCRIPCION"));
                lista_tipo_vias.add(tipo_via);
            }
           
            return lista_tipo_vias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeTipoVias error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_TIPOVIA");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeTipoVias cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeTipoVias cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeTipoVias cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeTipoVias error", e);
            }
        }
       
    }
    
    public ArrayList<AgenteBean> traeAgentes(String local) throws Exception
    {
    	log.info("UtilesDAOImpl:traeAgentes inicio");
        ArrayList<AgenteBean> lista_Agentes = new ArrayList<AgenteBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeAgentes conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            st = conn.prepareCall("{call SP_UTIL_SEL_AGENTES(?,?)}");
            st.setString(1, local);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(2);
           
            AgenteBean agente;
            
            //Francisco Quiroz
            agente = new AgenteBean();
            agente.setUsuario("Seleccione");
            agente.setNombre_completo("Seleccione");
            lista_Agentes.add(agente);
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeAgentes entrando ciclo while");
                agente = new AgenteBean();
                agente.setUsuario(rs.getString("IDENT"));
                agente.setNombre_completo(rs.getString("DESCRIPTION"));
                lista_Agentes.add(agente);
            }
           
            return lista_Agentes;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeAgentes error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_AGENTES");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeAgentes cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeAgentes cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeAgentes cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeAgentes error", e);
            }
        }
       
    }
   
   public ArrayList<ConvenioBean> traeConvenios() throws Exception
    {
	   log.info("UtilesDAOImpl:traeConvenios inicio");
        ArrayList<ConvenioBean> lista_Convenios = new ArrayList<ConvenioBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        String descripcion = null;
       
        try {
        	log.info("UtilesDAOImpl:traeConvenios conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_CONVENIOS(?)}";
            st = conn.prepareCall(sql);
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(1);
           
            ConvenioBean convenio;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeConvenios entrando ciclo while");
                convenio = new ConvenioBean();
                convenio.setId(rs.getString("CDG"));
                convenio.setDescripcion(rs.getString("CDG") + "-" +rs.getString("DESCRIPCION"));
                lista_Convenios.add(convenio);
            }
           
            return lista_Convenios;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeConvenios error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_AGENTES");
        } finally {
               try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeConvenios cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeConvenios cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeConvenios cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeConvenios error", e);
            }
        }
       
    }
  
   public ArrayList<PromocionBean> traePromociones() throws Exception
    {
	   log.info("UtilesDAOImpl:traePromociones inicio");
        ArrayList<PromocionBean> lista_Promociones = new ArrayList<PromocionBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traePromociones conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call pro_trae_promociones()}";
            st = conn.prepareCall(sql);
            st.executeQuery();
            rs = st.getResultSet();
           
            PromocionBean promocion;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traePromociones entrando ciclo while");
                promocion = new PromocionBean();
                promocion.setId(rs.getString("ID"));
                promocion.setDescripcion(rs.getString("DESCRIPCION"));
               
                lista_Promociones.add(promocion);
            }
           
            return lista_Promociones;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traePromociones error controlado",e);
             throw new Exception("Error en DAO, al traer las promociones disponibles");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traePromociones cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traePromociones cierre CallableStatement");
                    st.close();
                }          
                if (null != conn){
                	log.warn("UtilesDAOImpl:traePromociones cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traePromociones error", e);
            }
        }
       
    }
  
   public ArrayList<FamiliaBean> traeFamilias(String form_origen) throws Exception
   {
	   log.info("UtilesDAOImpl:traeFamilias inicio");
        ArrayList<FamiliaBean> lista_Familias = new ArrayList<FamiliaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeFamilias conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_FAMILIA(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, form_origen);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(2);
           
            FamiliaBean familia;
            
            //fquiroz 
            //Incorporo nodo valor 0
            familia = new FamiliaBean();
            familia.setCodigo("0");
            familia.setDescripcion("SELECCIONAR");
            familia.setTipo_fam("");
            lista_Familias.add(familia);
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeFamilias entrando ciclo while");
                familia = new FamiliaBean();
                familia.setCodigo(rs.getString("CDG"));
                familia.setDescripcion(rs.getString("CDG") + " - " + rs.getString("DESCRIPCION"));
                familia.setTipo_fam(rs.getString("TIPOFAM"));
                
                lista_Familias.add(familia);
            }
           
            return lista_Familias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeFamilias error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_FAMILIA");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeFamilias cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeFamilias cierre CallableStatement");
                    st.close();
                }               
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeFamilias cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeFamilias error", e);
            }
        }
      
   }
   
   public ArrayList<TipoFamiliaBean> traeTipoFamilia(String form_origen, String codigoMultioferta) throws Exception{
	   log.info("UtilesDAOImpl:traeTipoFamilia inicio");
	   ArrayList<TipoFamiliaBean> lista_tipo_Familias = new ArrayList<TipoFamiliaBean>();
       Connection conn = null;
       ResultSet rs = null;
       CallableStatement st= null;
       
       try{
    	   log.info("UtilesDAOImpl:traeTipoFamilia conectando base datos");
    	   conn = ConexionFactory.INSTANCE.getConexion();
           String sql = "{call SP_BUSCAR_SEL_TIPOFAMILIA(?,?,?)}";
           st = conn.prepareCall(sql);
           System.out.println("Familias ==> "+form_origen+" <==> Codigo Multiofertas "+codigoMultioferta);
           st.setString(1, form_origen);
           st.setString(2, codigoMultioferta);
           st.registerOutParameter(3, OracleTypes.CURSOR);
           st.execute();
           rs = (ResultSet)st.getObject(3);
           
           TipoFamiliaBean tipoFamilia;       
           
           //FQuiroz Agrego un registro incial en 0
           /*tipoFamilia = new TipoFamiliaBean();
           tipoFamilia.setCodigo("0");
    	   tipoFamilia.setDescripcion("SELECCIONE");        	   
    	   tipoFamilia.setCantidad(0);      
    	   lista_tipo_Familias.add(tipoFamilia);*/
           
           while (rs.next())
           {
        	   log.debug("UtilesDAOImpl:traeTipoFamilia entrando ciclo while");
        	   tipoFamilia = new TipoFamiliaBean();
        	   tipoFamilia.setCodigo(rs.getString("CDG"));
        	   tipoFamilia.setDescripcion(rs.getString("CDG") + " - " + rs.getString("DESCRIPCION"));        	   
        	   tipoFamilia.setCantidad(rs.getInt("CANTIDAD"));
        	   
        	   lista_tipo_Familias.add(tipoFamilia);
           }
    	   
           return lista_tipo_Familias;
           
       }catch(Exception e){
    	   log.error("UtilesDAOImpl:traeTipoFamilia error controlado",e);
           throw new Exception("Error en DAO, SP_BUSCAR_SEL_TIPOFAMILIA");
       }finally{
    	   try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeTipoFamilia cierre ResultSet");
                   rs.close();
               }
               if (null != st){
            	   log.warn("UtilesDAOImpl:traeTipoFamilia cierre CallableStatement");
                   st.close();
               } 
               if(null != conn){
            	   log.warn("UtilesDAOImpl:traeTipoFamilia cierre Connection");
            	   conn.close();
               }
           }catch(Exception e){
        	   log.error("UtilesDAOImpl:traeTipoFamilia error", e);
           }
       }
   }
   
   public ArrayList<FamiliaBean> traeFamiliasMultiofertas(String form_origen, String codigoMultioferta, String tipoFamilia) throws Exception
   {
	   log.info("UtilesDAOImpl:traeFamiliasMultiofertas inicio");
        ArrayList<FamiliaBean> lista_Familias = new ArrayList<FamiliaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeFamiliasMultiofertas conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_FAMILIA_MULTI(?,?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, form_origen);
            st.setString(2, codigoMultioferta);
            st.setString(3, tipoFamilia);
            st.registerOutParameter(4, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(4);
           
            FamiliaBean familia;
            
            //FQuiroz Agrego un registro incial en 0
            /*familia = new FamiliaBean();
            familia.setCodigo("0");
            familia.setDescripcion("SELECCIONE");
            lista_Familias.add(familia);*/
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeFamiliasMultiofertas entrando ciclo while");
                familia = new FamiliaBean();
                familia.setCodigo(rs.getString("CDG"));
                familia.setDescripcion(rs.getString("DESCRIPCION"));
                
                lista_Familias.add(familia);
            }
           
            return lista_Familias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeFamiliasMultiofertas error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_FAMILIA_MULTI");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeFamiliasMultiofertas cierre CallableStatement");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeFamiliasMultiofertas cierre Connection");
                    st.close();
                } 
                if(null != conn){
             	   log.warn("UtilesDAOImpl:traeTipoFamilia cierre Connection");
             	   conn.close();
                }
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeFamiliasMultiofertas error", e);
            }
        }
      
   }
  
   public ArrayList<SubFamiliaBean> traeSubfamilias(String familia) throws Exception
   {
	   log.info("UtilesDAOImpl:traeSubfamilias inicio");
        ArrayList<SubFamiliaBean> lista_SubFamilias = new ArrayList<SubFamiliaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        String sql;
       
        try {
        	log.info("UtilesDAOImpl:traeSubfamilias conectando base datos");
        	conn = ConexionFactory.INSTANCE.getConexion();
            sql = "{call SP_BUSCAR_SEL_SUBFAMILIA(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, familia);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(2);
           
            SubFamiliaBean subfamilia;
            
            //FQUIROZ
            //Agrego nodo igual 0
            subfamilia = new SubFamiliaBean();
            subfamilia.setCodigo("0");
            subfamilia.setDescripcion("SELECCIONAR");
            lista_SubFamilias.add(subfamilia);
            
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeSubfamilias entrando ciclo while");
                subfamilia = new SubFamiliaBean();
                subfamilia.setCodigo(rs.getString("CDG"));
                subfamilia.setDescripcion(rs.getString("CDG") + " - " + rs.getString("DESCRIPCION"));
               
                lista_SubFamilias.add(subfamilia);
            }
           
            return lista_SubFamilias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeSubfamilias error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_SUBFAMILIA");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeSubfamilias cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeSubfamilias cierre CallableStatement");
                    st.close();
                }              
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeSubfamilias cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeSubfamilias error", e);
            }
        }
      
   }
   
   public ArrayList<SubFamiliaBean> traeSubfamiliasMultiofertas(String familia, String codigoMultioferta) throws Exception
   {
	   log.info("UtilesDAOImpl:traeSubfamiliasMultiofertas inicio");
        ArrayList<SubFamiliaBean> lista_SubFamilias = new ArrayList<SubFamiliaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        String sql;
       
        try {
        	log.info("UtilesDAOImpl:traeSubfamiliasMultiofertas conectando base datos");
        	conn = ConexionFactory.INSTANCE.getConexion();
            sql = "{call SP_BUSCAR_SEL_SUBFAMILIA_MULTI(?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, familia);
            st.setString(2, codigoMultioferta);
            st.registerOutParameter(3, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(3);
           
            SubFamiliaBean subfamilia;   
            
            //FQUIROZ
            //Agrego nodo igual 0
            /*subfamilia = new SubFamiliaBean();
            subfamilia.setCodigo("0");
            subfamilia.setDescripcion("SELECCIONE");
            lista_SubFamilias.add(subfamilia);*/
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeSubfamiliasMultiofertas entrando ciclo while");
                subfamilia = new SubFamiliaBean();
                subfamilia.setCodigo(rs.getString("CDG"));
                subfamilia.setDescripcion(rs.getString("DESCRIPCION"));
               
                lista_SubFamilias.add(subfamilia);
            }
           
            return lista_SubFamilias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeSubfamiliasMultiofertas error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_SUBFAMILIA");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeSubfamiliasMultiofertas cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeSubfamiliasMultiofertas cierre CallableStatement");
                    st.close();
                }
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeSubfamiliasMultiofertas cierre Connection");
                	conn.close();
                }      
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeSubfamiliasMultiofertas error", e);
            }
        }
      
   }
  
   public ArrayList<GrupoFamiliaBean> traeGruposFamilias(String familia, String subfamilia) throws Exception
   {
	   log.info("UtilesDAOImpl:traeGruposFamilias inicio");
        ArrayList<GrupoFamiliaBean> lista_GrupoFamilias = new ArrayList<GrupoFamiliaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeGruposFamilias conectando base datos");
        	conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_GRUPO(?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, familia);
            st.setString(2, subfamilia);
            st.registerOutParameter(3, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(3);
           
            GrupoFamiliaBean subgrupofamilia;
            
            subgrupofamilia = new GrupoFamiliaBean();
            subgrupofamilia.setCodigo("0");
            subgrupofamilia.setDescripcion("SELECCIONAR");           
            lista_GrupoFamilias.add(subgrupofamilia);     
            
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeGruposFamilias entrando ciclo while");
                subgrupofamilia = new GrupoFamiliaBean();
                subgrupofamilia.setCodigo(rs.getString("CDG"));
                subgrupofamilia.setDescripcion(rs.getString("CDG") + " - " + rs.getString("DESCRIPCION"));
               
                lista_GrupoFamilias.add(subgrupofamilia);
            }
           
            return lista_GrupoFamilias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeGruposFamilias error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_GRUPO");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeGruposFamilias cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeGruposFamilias cierre CallableStatement");
                    st.close();
                }               
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeGruposFamilias cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeGruposFamilias error", e);
            }
        }
      
   }
   
   public ArrayList<GrupoFamiliaBean> traeGruposFamiliasMultioferta(String familia, String subfamilia, String codigoMultioferta) throws Exception
   {
	   log.info("UtilesDAOImpl:traeGruposFamiliasMultioferta inicio");
        ArrayList<GrupoFamiliaBean> lista_GrupoFamilias = new ArrayList<GrupoFamiliaBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeGruposFamiliasMultioferta conectando base datos");
        	conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_BUSCAR_SEL_GRUPO_MULTI(?,?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, familia);
            st.setString(2, subfamilia);
            st.setString(3, codigoMultioferta);
            st.registerOutParameter(4, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(4);
           
            GrupoFamiliaBean subgrupofamilia;
            boolean traegrupo=false;        
            
            //FQuiroz agrega un registro en 0
            /*subgrupofamilia = new GrupoFamiliaBean();
            subgrupofamilia.setCodigo("0");
            subgrupofamilia.setDescripcion("SELECCIONE");               
            lista_GrupoFamilias.add(subgrupofamilia);*/
            
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeGruposFamiliasMultioferta entrando ciclo while");
                subgrupofamilia = new GrupoFamiliaBean();
                subgrupofamilia.setCodigo(rs.getString("CDG"));
                subgrupofamilia.setDescripcion(rs.getString("DESCRIPCION"));               
                lista_GrupoFamilias.add(subgrupofamilia);
                traegrupo = true;
            }         
            
            if(traegrupo==false){            	
            	subgrupofamilia = new GrupoFamiliaBean();
                subgrupofamilia.setCodigo("-1");
                subgrupofamilia.setDescripcion("Todos");               
                lista_GrupoFamilias.add(subgrupofamilia);
                traegrupo = true;
            }
           
            return lista_GrupoFamilias;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeGruposFamiliasMultioferta error controlado",e);
             throw new Exception("Error en DAO, SP_BUSCAR_SEL_GRUPO");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeGruposFamiliasMultioferta cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeGruposFamiliasMultioferta cierre CallableStatement");
                    st.close();
                } 
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeGruposFamiliasMultioferta cierre Connection");
                	conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeGruposFamiliasMultioferta error", e);
            }
        }
      
   }
  
   public ArrayList<GiroBean> traeGiros() throws Exception
   {
	   log.info("UtilesDAOImpl:traeGiros inicio");
	   ArrayList<GiroBean> lista_Giros = new ArrayList<GiroBean>();
       Connection conn = null;
       ResultSet rs = null;
       CallableStatement st= null;
      
       try {
    	   log.info("UtilesDAOImpl:traeGiros conectando base datos");
           conn = ConexionFactory.INSTANCE.getConexion();
           String sql = "{call SP_UTIL_SEL_GIROS(?)}";
           st = conn.prepareCall(sql);
           st.registerOutParameter(1, OracleTypes.CURSOR);
           
           st.execute();
           rs = (ResultSet)st.getObject(1);
          
           GiroBean giro;
          
           while (rs.next())
           {
        	   log.debug("UtilesDAOImpl:traeGiros entrando ciclo while");
               giro = new GiroBean();
               giro.setCodigo(rs.getString("CDG"));
               giro.setDescripcion(rs.getString("DESCRIPCION"));
              
               lista_Giros.add(giro);
           }
          
           return lista_Giros;
          
       } catch (Exception e) {
    	   log.error("UtilesDAOImpl:traeGiros error controlado",e);
            throw new Exception("Error en DAO, SP_UTIL_SEL_GIROS");
       } finally {
              try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeGiros cierre ResultSet");
                   rs.close();
               }
               if (null != st){
            	   log.warn("UtilesDAOImpl:traeGiros cierre CallableStatement");
                   st.close();
               }         
               if (null != conn){
            	   log.warn("UtilesDAOImpl:traeGiros cierre Connection");
    	    	   conn.close();
               } 
           }catch(Exception e){
        	   log.error("UtilesDAOImpl:traeGiros error", e);
           }
       }
	   
   }
   
   public ArrayList<ProvinciaBean> traeProvincias() throws Exception
   {
	   log.info("UtilesDAOImpl:traeProvincias inicio");
	   ArrayList<ProvinciaBean> lista_Provincias = new ArrayList<ProvinciaBean>();
       Connection conn = null;
       ResultSet rs = null;
       CallableStatement st= null;
      
       try {
    	   log.info("UtilesDAOImpl:traeProvincias conectando base datos");
           conn = ConexionFactory.INSTANCE.getConexion();
           String sql = "{call SP_UTIL_SEL_PROVINCIAS(?)}";
           st = conn.prepareCall(sql);
           st.registerOutParameter(1, OracleTypes.CURSOR);
           
           st.execute();
           rs = (ResultSet)st.getObject(1);
          
           ProvinciaBean prov;
          
           while (rs.next())
           {
        	   log.debug("UtilesDAOImpl:traeProvincias entrando ciclo while");
        	   prov = new ProvinciaBean();
        	   prov.setCodigo(rs.getString("CDG"));
        	   prov.setDescripcion(rs.getString("DESCRIPCION"));
              
        	   lista_Provincias.add(prov);
           }
          
           return lista_Provincias;
          
       } catch (Exception e) {
    	   log.error("UtilesDAOImpl:traeProvincias error controlado",e);
            throw new Exception("Error en DAO, SP_UTIL_SEL_PROVINCIAS");
       } finally {
              try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeProvincias cierre ResultSet");
                   rs.close();
               }
               if (null != st){
            	   log.warn("UtilesDAOImpl:traeProvincias cierre CallableStatement");
                   st.close();
               }  
               if (null != conn){
            	   log.warn("UtilesDAOImpl:traeProvincias cierre Connection");
    	    	   conn.close();
               } 
           }catch(Exception e){
        	   log.error("UtilesDAOImpl:traeProvincias error", e);
           }
       }
	   
   }
  
   public ArrayList<ProvinciaBean> traeProvinciasdev() throws Exception
   {
	   log.info("UtilesDAOImpl:traeProvincias inicio");
	   ArrayList<ProvinciaBean> lista_Provincias = new ArrayList<ProvinciaBean>();
       Connection conn = null;
       ResultSet rs = null;
       CallableStatement st= null;
      
       try {
    	   log.info("UtilesDAOImpl:traeProvincias conectando base datos");
           conn = ConexionFactory.INSTANCE.getConexion();
           String sql = "{call SP_UTIL_SEL_PROVINCIAS(?)}";
           st = conn.prepareCall(sql);
           st.registerOutParameter(1, OracleTypes.CURSOR);
           
           st.execute();
           rs = (ResultSet)st.getObject(1);
          
           ProvinciaBean prov;
          
           while (rs.next())
           {
        	   log.debug("UtilesDAOImpl:traeProvincias entrando ciclo while");
        	   prov = new ProvinciaBean();
        	   prov.setCodigo(rs.getString("CDG").concat("_").concat(rs.getString("DESCRIPCION")));
        	   prov.setDescripcion(rs.getString("DESCRIPCION"));
              
        	   lista_Provincias.add(prov);
           }
          
           return lista_Provincias;
          
       } catch (Exception e) {
    	   log.error("UtilesDAOImpl:traeProvincias error controlado",e);
            throw new Exception("Error en DAO, SP_UTIL_SEL_PROVINCIAS");
       } finally {
              try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeProvincias cierre ResultSet");
                   rs.close();
               }
               if (null != st){
            	   log.warn("UtilesDAOImpl:traeProvincias cierre CallableStatement");
                   st.close();
               }  
               if (null != conn){
            	   log.warn("UtilesDAOImpl:traeProvincias cierre Connection");
    	    	   conn.close();
               } 
           }catch(Exception e){
        	   log.error("UtilesDAOImpl:traeProvincias error", e);
           }
       }
	   
   }
   
   public ArrayList<ProveedorBean> traeProveedores() throws Exception
   {
	   log.info("UtilesDAOImpl:traeProveedores inicio");
        ArrayList<ProveedorBean> lista_proveedores = new ArrayList<ProveedorBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeProveedores conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "select * from proveedo";
            st = conn.prepareCall(sql);
            st.executeQuery();
            rs = st.getResultSet();
           
            ProveedorBean proveedor;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeProveedores entrando ciclo while");
                proveedor = new ProveedorBean();
                proveedor.setCodigo(rs.getInt("CDG"));
                proveedor.setDescripcion(rs.getString("DESCRIPCION"));
               
                lista_proveedores.add(proveedor);
            }
           
            return lista_proveedores;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeProveedores error controlado",e);
             throw new Exception("Error en DAO, al traer los proveedores disponibles");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeProveedores cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeProveedores cierre CallableStatement");
                    st.close();
                }               
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeProveedores cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeProveedores error", e);
            }
        }
      
   }
   

 
@Override
public boolean validaCaja(String sucursal, String fecha) throws Exception 
{
	log.info("UtilesDAOImpl:validaCaja inicio");
	boolean estado2 = false;
    Connection con = null;
    CallableStatement cs = null;
     try {
    	 log.info("UtilesDAOImpl:validaCaja conectando base datos");
    	con = ConexionFactory.INSTANCE.getConexion();
		cs = con.prepareCall("{call SP_UTIL_SEL_VALIDAR_CAJA(?,?,?)}");
		cs.setString(1, sucursal);
		cs.setString(2, fecha);
		cs.registerOutParameter(3, OracleTypes.VARCHAR);
		
		Utils utiles = new Utils();
		cs.execute();
		
		String estado = Constantes.STRING_BLANCO;
		estado = (String)cs.getObject(3);
		try {
			if (utiles.formatoFechaCh(fecha).after(utiles.traeFecha())) {
				estado2 = false;
			}
			else
			{
				if (null == estado) {
					estado2 = false;
				}
				if (estado.equals(Constantes.STRING_A)) {
					estado2 = true;
				}
				else
				{
					estado2 = false;
				}
			}
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaCaja error controlado",e);
			estado2 = false;
		}
		
	} catch (Exception e) {
		log.error("UtilesDAOImpl:validaCaja error controlado",e);
        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDAR_CAJA"); 
	} finally {
        try{
         if (null != cs){
        	 log.warn("UtilesDAOImpl:validaCaja cierre CallableStatement");
             cs.close();
         }           
         if (null != con){
        	 log.warn("UtilesDAOImpl:validaCaja cierre Connection");
	    	   con.close();
           } 
         
     }catch(Exception e){
    	 log.error("UtilesDAOImpl:validaCaja error", e);
     }
	}
	
	return estado2;
}
@Override
public boolean validaEstadoPed(String codigo) throws Exception 
{
	log.info("UtilesDAOImpl:validaEstadoPed inicio");
	boolean estado2 = false;
    Connection con = null;
    CallableStatement cs = null;
     try {
    	 log.info("UtilesDAOImpl:validaEstadoPed conectando base datos");
    	con = ConexionFactory.INSTANCE.getConexion();
		cs = con.prepareCall("{call SP_UTIL_SEL_VALIDAR_PED(?,?,?)}");
		cs.setString(1, codigo);
		cs.registerOutParameter(2, OracleTypes.VARCHAR);
		cs.registerOutParameter(3, OracleTypes.VARCHAR);
		cs.execute();
		
		String cerrado = Constantes.STRING_BLANCO;
		cerrado = (String)cs.getObject(2);
		String liberado = Constantes.STRING_BLANCO;
		liberado = (String)cs.getObject(3);
		try {
			if (Constantes.STRING_TIPO_ALB_NO.equals(cerrado)&& Constantes.STRING_CERO.equals(liberado)) {
				estado2 = true;
			}
			else
			{
					estado2 = false;
			}
		} catch (Exception e) {
			estado2 = false;
			log.error("UtilesDAOImpl:validaEstadoPed error controlado",e);
		}
		
	} catch (Exception e) {
		log.warn("UtilesDAOImpl:validaEstadoPed cierre ResultSet");
        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDAR_CAJA"); 
	} finally {
        try{
         if (null != cs){
             cs.close();
             log.warn("UtilesDAOImpl:validaEstadoPed cierre CallableStatement");
         }           
         if (null != con){
	    	   con.close();
	    	   log.warn("UtilesDAOImpl:validaEstadoPed cierre Connection");
           } 
         
     }catch(Exception e){
    	 log.error("UtilesDAOImpl:validaEstadoPed error", e);
     }
	}
	
	return estado2;
}
@Override
public MenuBean llenaMenu(String usuario) throws Exception {
	log.info("UtilesDAOImpl:llenaMenu inicio");
	Connection conexion = null;
	ResultSet rs = null;
	CallableStatement cstmt= null;
    MenuBean menu = new MenuBean();
    
	 try{
		 log.info("UtilesDAOImpl:llenaMenu conectando base datos");
		 conexion = ConexionFactory.INSTANCE.getConexion();
	     String patchMenu = null; 
	     cstmt = conexion.prepareCall("{call SP_LOGIN_SEL_MENU_USUARIO(?,?)}");
	     cstmt.setString(1, usuario);    
	     cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
	     cstmt.execute();
	     
	     patchMenu = cstmt.getString(2);
	     System.out.println(patchMenu);	   
	 
		 //File inFile = new File("C:\\Gestiopticas\\etc\\menu.mnu"); 
	     File inFile = new File("/POS/Gestiopticas/etc/menu.mnu");
	     BufferedReader reader = new BufferedReader(new FileReader(inFile));
	
	     boolean mantenedores = false;
	     boolean ventas = false;
	     boolean informes = false;
	     
		   String linea = null;
		   ArrayList <String> listarMenu = new ArrayList <String>();
		   String [] str = null;
		   while ( null != (linea=reader.readLine())) {
			   log.debug("UtilesDAOImpl:llenaMenu entrando ciclo while");
			   linea = linea.trim();
			   str = linea.split("\\|");
			   
			   /*PREGUNTA POR LOS MENUS*/
			   
			   if(str[0].equals("01.02")){ 			//MANTENEDORES
				   menu.setMantenedores(Constantes.STRING_MOSTRAR_MENU);
				   mantenedores = true;
			   }
			   if(str[0].equals("02")){    			//VENTAS
				   menu.setVenta(Constantes.STRING_MOSTRAR_MENU);
				   ventas = true;
			   }
			   
			   /*PREGUNTA POR LOS SUB_MENUS*/
			   if (ventas) {
				   
				   if(str[0].equals("02.01")){
					   menu.setVentaPresupuesto(Constantes.STRING_MOSTRAR_MENU);
				   }
				   if(str[0].equals("02.02")){
					   menu.setVentaPedido(Constantes.STRING_MOSTRAR_MENU);
				   }
				   if(str[0].equals("02.03")){
					   menu.setVentaAlbaranes(Constantes.STRING_MOSTRAR_MENU);
				   }
				   
				   //MODIFICADO POR LMARIN 20130827
				   //SOLO EN AMBIENTE DE DESARROLLO PARA MOSTRAR 
				   //LAS LIBERACIONES DE ENCARGO
				   
				   menu.setVentaLiberacionEncargo(Constantes.STRING_MOSTRAR_MENU);
				   /*if(str[0].equals("02.02.03")){
					   menu.setVentaLiberacionEncargo(Constantes.STRING_MOSTRAR_MENU);
				   }*/
				   if(str[0].equals("02.05")){
					   menu.setVentaVentaDirecta(Constantes.STRING_MOSTRAR_MENU);
					   ventas = false;
				   }
				   
				   
			   }
			   if (mantenedores){
				   
				   if(str[0].equals("01.02.02")){
					   menu.setMantenedoresClientes(Constantes.STRING_MOSTRAR_MENU);
				   }
				   if(str[0].equals("01.02.03")){
					   menu.setMantenedoresGraduacionClientes(Constantes.STRING_MOSTRAR_MENU);
				   }
				   if(str[0].equals("01.02.06")){
					   menu.setMantenedoresMedico(Constantes.STRING_MOSTRAR_MENU);
					}
				   if(str[0].equals("04.06")){
					   menu.setDevolucion(Constantes.STRING_MOSTRAR_MENU);
					   
				   }
				   if(str[0].equals("09.17")){
					   menu.setMantenedoresCambioFolio(Constantes.STRING_MOSTRAR_MENU);
					   
				   }
				   if(str[0].equals("01.02.07")){
					   menu.setEntregaPedido(Constantes.STRING_MOSTRAR_MENU);
					   mantenedores = false;
				   }
				   
				   
			   }
		   	}
		 }
		catch (Exception e) {
			log.error("UtilesDAOImpl:llenaMenu error controlado",e);
		     throw new Exception("Error en DAO, al leer el patch o archivo de menu"); 
		} finally {
		       try{
		        if (null != rs){
		        	log.warn("UtilesDAOImpl:llenaMenu cierre ResultSet");
		            rs.close();
		        }
		        if (null != cstmt){
		        	log.warn("UtilesDAOImpl:llenaMenu cierre CallableStatement");
		            cstmt.close();
		        }                
		        if (null != conexion){
		        	log.warn("UtilesDAOImpl:llenaMenu cierre Connection");
		        	conexion.close();
		           } 
		    }catch(Exception e){
		    	log.error("UtilesDAOImpl:llenaMenu error", e);
		    }
		}
    return menu;
}


	public ArrayList<OftalmologoBean> traeDoctor(){
		log.info("UtilesDAOImpl:traeDoctor inicio");
		ArrayList<OftalmologoBean> lista_doctores = new ArrayList<OftalmologoBean>();
		Connection conexion = null;
		ResultSet rs = null;
		CallableStatement st= null;
		
		
		try{
			log.info("UtilesDAOImpl:traeDoctor conectando base datos");
			conexion = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_DOCTOR(?)}";
            st = conexion.prepareCall(sql);               
            st.registerOutParameter(1, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(1);
            OftalmologoBean doctor;
            while(rs.next()){
            	log.debug("UtilesDAOImpl:traeDoctor entrando ciclo while");
            	doctor = new OftalmologoBean();
            	
            	
            	doctor.setCodigo(rs.getString("CDG"));
            	doctor.setNombre(rs.getString("NOMBRE") + " " + rs.getString("APELLI"));
            	doctor.setApelli(rs.getString("APELLI"));
            	doctor.setNif(rs.getString("NIF"));
            	doctor.setLnif(rs.getString("LETRANIF"));
            	
            	lista_doctores.add(doctor);
            }
            
			
		}catch(Exception ex){
			log.error("UtilesDAOImpl:traeDoctor error controlado",ex);
		}finally {
		       try{
			        if (null != rs){
			        	log.warn("UtilesDAOImpl:traeDoctor cierre ResultSet");			        	
			            rs.close();
			        }
			        if (null != st){
			        	log.warn("UtilesDAOImpl:traeDoctor cierre CallableStatement");
			            st.close();
			        }                
			        if (null != conexion){
			        	log.warn("UtilesDAOImpl:traeDoctor cierre Connection");
			        	conexion.close();
			           } 
			    }catch(Exception e){
			    	log.error("UtilesDAOImpl:traeDoctor error", e);
			    }
			}
		return lista_doctores;
	}

	public int traeDescuentoConvenio(ProductosBean productosBean,String convenio, String fpago,String local)  throws Exception {
		log.info("UtilesDAOImpl:traeDescuentoConvenio inicio");
		int valor = Constantes.INT_CERO;
	    Connection con = null;
	    
	    String promopar = (productosBean.getPromopar() != null) && (!productosBean.getPromopar().equals(""))? productosBean.getPromopar().trim():"";
	    		
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:traeDescuentoConvenio conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	//System.out.println("{call SP_UTIL_SEL_CONVENIO_DESCP_P("+convenio+","+fpago+","+productosBean.getFamilia()+","+productosBean.getSubFamilia()+","+productosBean.getGrupoFamilia()+","+productosBean.getPromopar().trim()+","+local+",:z)}");
			cs = con.prepareCall("{call SP_UTIL_SEL_CONVENIO_DESCP_P(?,?,?,?,?,?,?,?)}");
			cs.setString(1, convenio);
			cs.setString(2, fpago);
			cs.setString(3, productosBean.getFamilia());
			cs.setString(4, productosBean.getSubFamilia());
			cs.setString(5, productosBean.getGrupoFamilia());
			cs.setString(6, promopar);
			cs.setString(7, local);
			cs.registerOutParameter(8, Types.NUMERIC);
			cs.execute(); 
			
			valor = (cs.getObject(8).toString() != null) && (cs.getObject(8).toString() != "")  ?  Integer.parseInt(cs.getObject(8).toString()): 0;
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeDescuentoConvenio error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_CONVENIO_DESCUENTO"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:traeDescuentoConvenio cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:traeDescuentoConvenio cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:traeDescuentoConvenio error", e);
	     }
		}
		System.out.println("valor descuento convenio ===> "+productosBean.getPromopar()+"<==>"+valor);
		return valor;
	}

	public Boolean validaEstadoConvenioCliente(String convenio, String cliente)  throws Exception {
		log.info("UtilesDAOImpl:validaEstadoConvenioCliente inicio");
		boolean estado = false;
		int valor = Constantes.INT_CERO;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:validaEstadoConvenioCliente conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_UTIL_SEL_CONVENIO_VAL_EST(?,?,?)}");
			cs.setString(1, convenio);
			cs.setString(2, cliente);
			cs.registerOutParameter(3, Types.NUMERIC);
			
			cs.execute();
			
			valor = Integer.parseInt(cs.getObject(3).toString());
			
			if (valor != Constantes.INT_CERO) {
				estado = true;
			}
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaEstadoConvenioCliente error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_CONVENIO_VAL_EST"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:validaEstadoConvenioCliente cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:validaEstadoConvenioCliente cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:validaEstadoConvenioCliente error", e);
	     }
		}
		
		return estado;
		
	}

	public ArrayList<SuplementosValores> traeValoresSuplementos(
			String suplemento,String codigo) throws Exception {
		log.info("UtilesDAOImpl:traeValoresSuplementos inicio");
		ArrayList<SuplementosValores> lista = new ArrayList<SuplementosValores>();
		ResultSet rs = null;
		Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	log.info("UtilesDAOImpl:traeValoresSuplementos conectando base datos");
	    	 
	    	
	    	con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_UTIL_SEL_TRATAMI_VALOR_PB(?,?,?)}");
			cs.setString(1, suplemento);
			cs.setString(2, codigo);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			
			cs.execute();
			
			rs = (ResultSet)cs.getObject(3);
			
			while (rs.next()) {
				log.debug("UtilesDAOImpl:traeValoresSuplementos entrando ciclo while");
				SuplementosValores valores = new SuplementosValores();
				valores.setCodigo(rs.getString("VALORES"));
				valores.setDescripcion(rs.getString("DESCRIPCION"));
				
				lista.add(valores);
			}
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeValoresSuplementos error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_TRATAMI_VALOR"); 
		} finally {
	        try{
	        	if(null != rs){
		        	 log.warn("UtilesDAOImpl:traeValoresSuplementos cierre ResultSet");
	          	   rs.close();
	             }
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:traeValoresSuplementos cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:traeValoresSuplementos cierre Connection");
		    	   con.close();
	           } 
	         
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:traeValoresSuplementos error", e);
		     }
		}
		
	return lista;
	}

	public boolean validaCrisMon(String familia, String subFamilia,
			String grupoFamilia, String codigo_montura, double esfera,
			double cilindro) throws Exception {
		log.info("UtilesDAOImpl:validaCrisMon inicio");
		Connection con = null;
		CallableStatement cs = null;
		int numero = Constantes.INT_CERO;
		boolean estado = false;
		
		try {
			log.info("UtilesDAOImpl:validaCrisMon conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_CRIS_MON(?,?,?,?,?,?,?)}");
			cs.setString(1, familia);
			cs.setString(2, subFamilia);
			cs.setString(3, grupoFamilia);
			cs.setString(4, codigo_montura);
			cs.setDouble(5, esfera);
			cs.setDouble(6, cilindro);
			cs.registerOutParameter(7, Types.NUMERIC);
			
			cs.execute();
			
			numero = cs.getInt(7);
			
			if (numero != Constantes.INT_CERO) {
				estado = true;
			}
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaCrisMon error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDA_CRIS_MON"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:validaCrisMon cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:validaCrisMon cierre Connection");
		    	   con.close();
	           } 
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:validaCrisMon error", e);
		     }
		}
		
		return estado;
	}

	public boolean validaGafaGraduable(String codigo) throws Exception {
		log.info("UtilesDAOImpl:validaGafaGraduable inicio");
		Connection con = null;
		CallableStatement cs = null;
		int numero = Constantes.INT_CERO;
		boolean estado = false;
		
		try {
			log.info("UtilesDAOImpl:validaGafaGraduable conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_GAFA_GRAD(?,?)}");
			cs.setString(1, codigo);
			cs.registerOutParameter(2, Types.NUMERIC);
			
			cs.execute();
			
			numero = cs.getInt(2);
			
			if (numero != Constantes.INT_CERO) {
				estado = true;
			}
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaGafaGraduable error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDA_GAFA_GRAD"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:validaGafaGraduable cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:validaGafaGraduable cierre Connection");
		    	   con.close();
	           } 
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:validaGafaGraduable error", e);
		     }
		}
		
		return estado;
	}
	
	
	public GiroBean  traeDescripGiroCliente(int cod_giro) throws Exception
	   {
		log.info("UtilesDAOImpl:traeDescripGiroCliente inicio");
		   ArrayList<GiroBean> lista_Giros = new ArrayList<GiroBean>();
	       Connection conn = null;
	       ResultSet rs = null;
	       CallableStatement st= null;
	      
	       try {
	    	   log.info("UtilesDAOImpl:traeDescripGiroCliente conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           String sql = "{call SP_UTIL_SEL_GIRO_CLIENTE(?,?)}";
	           st = conn.prepareCall(sql);	           
	           st.setString(1, String.valueOf(cod_giro));
	           st.registerOutParameter(2, OracleTypes.CURSOR);
	           st.execute();
	           rs = (ResultSet)st.getObject(2);
	          
	           GiroBean giro = null;
	          
	           while (rs.next())
	           {
	        	   log.debug("UtilesDAOImpl:traeDescripGiroCliente entrando ciclo while");
	               giro = new GiroBean();
	               giro.setCodigo(rs.getString("CDG"));
	               giro.setDescripcion(rs.getString("DESCRIPCION"));              
	          
	           }
	          
	           return giro;
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:traeDescripGiroCliente error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_GIROS");
	       } finally {
	              try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeDescripGiroCliente cierre ResultSet");
	                   rs.close();
	               }
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:traeDescripGiroCliente cierre CallableStatement");
	                   st.close();
	               }         
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:traeDescripGiroCliente cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:traeDescripGiroCliente error", e);
	           }
	       }
		   
	   }
	
	
	public ProvinciaBean traeDescripProvinciasCliente(String codigo_provincia) throws Exception
	   {
		log.info("UtilesDAOImpl:traeDescripProvinciasCliente inicio");
	       Connection conn = null;
	       ResultSet rs = null;
	       CallableStatement st= null;
	      
	       try {
	    	   log.info("UtilesDAOImpl:traeDescripProvinciasCliente conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           String sql = "{call SP_UTIL_SEL_PROVINCIAS_CLIENTE(?,?)}";
	           st = conn.prepareCall(sql);
	           st.setString(1, codigo_provincia);
	           st.registerOutParameter(2, OracleTypes.CURSOR);
	           
	           st.execute();
	           rs = (ResultSet)st.getObject(2);
	          
	           ProvinciaBean prov = null;
	          
	           while (rs.next())
	           {
	        	   log.debug("UtilesDAOImpl:traeDescripProvinciasCliente entrando ciclo while");
	        	   prov = new ProvinciaBean();
	        	   prov.setCodigo(rs.getString("CDG"));
	        	   prov.setDescripcion(rs.getString("DESCRIPCION"));             
	        	   
	           }
	          
	           return prov;
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:traeDescripProvinciasCliente error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_PROVINCIAS");
	       } finally {
	              try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeDescripProvinciasCliente cierre ResultSet");
	                   rs.close();
	               }
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:traeDescripProvinciasCliente cierre CallableStatement");
	                   st.close();
	               }  
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:traeDescripProvinciasCliente cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:traeDescripProvinciasCliente error", e);
	           }
	       }
		   
	   }
	public boolean validaCopiaGuiaBoleta(String numero, String tipo) throws Exception {
		log.info("UtilesDAOImpl:validaCopiaGuiaBoleta inicio");
	       Connection conn = null;
	       CallableStatement st= null;
	       boolean estado = false;
	       int num=0;
	       try {
	    	   log.info("UtilesDAOImpl:validaCopiaGuiaBoleta conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           String sql = "{call SP_UTIL_SEL_VALIDA_COPIA_BG(?,?,?)}";
	           st = conn.prepareCall(sql);
	           st.setString(1, numero);
	           st.setString(2, tipo);
	           st.registerOutParameter(3, Types.NUMERIC);
	           
	           st.execute();
	           num = st.getInt(3);
				
				if (num != Constantes.INT_CERO) {
					estado = true;
				}
	          
	           return estado;
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:validaCopiaGuiaBoleta error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_VALIDA_COPIA_BG");
	       } finally {
	              try{
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:validaCopiaGuiaBoleta cierre CallableStatement");
	                   st.close();
	               }  
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:validaCopiaGuiaBoleta cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:validaCopiaGuiaBoleta error", e);
	           }
	       }
	}
	public boolean validaEsferaMasCilindro(String familia, String subFamilia,
			String grupoFamilia, double esfera, double cilindro) throws Exception {
		
		log.info("UtilesDAOImpl:validaEsferaMasCilindro inicio");
	     
		   Connection conn = null;
	       CallableStatement st= null;
	       boolean estado = false;
	       int num=0;
	       try {
	    	   log.info("UtilesDAOImpl:validaEsferaMasCilindro conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           System.out.println("familia =>"+familia+"<==> subfamilia =>"+subFamilia+"<=>grupofamilia =>"+grupoFamilia+"=>Esfera =>"+esfera+"=> Cilindro =>"+cilindro);
	           String sql = "{call SP_UTIL_SEL_VALIDA_ESF_MAS_CIL(?,?,?,?,?,?)}";
	           st = conn.prepareCall(sql);
	           st.setString(1, familia);
	           st.setString(2, subFamilia);
	           st.setString(3, grupoFamilia);
	           st.setDouble(4, esfera);
	           st.setDouble(5, cilindro);
	           st.registerOutParameter(6, Types.NUMERIC);
	           
	           st.execute();
	           num = st.getInt(6);
				
				if (num != Constantes.INT_CERO) {
					estado = true;
				}
	          
	           return estado;	
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:validaEsferaMasCilindro error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_VALIDA_ESF_MAS_CILS");
	       } finally {
	              try{
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:validaEsferaMasCilindro cierre CallableStatement");
	                   st.close();
	               }  
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:validaEsferaMasCilindro cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:validaEsferaMasCilindro error", e);
	           }
	       }
	}
	public String validaCrisMonSup(ProductosBean cristal, ProductosBean montura) throws Exception {
		log.info("UtilesDAOImpl:validaCrisMonSup inicio");
	     
		   Connection conn = null;
	       CallableStatement st= null;
	       ResultSet rs = null;
	       String msje = Constantes.STRING_BLANCO;
	       try {
	    	   log.info("UtilesDAOImpl:validaCrisMonSup conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           String sql = "{call SP_UTIL_SEL_VALIDA_CRISMONSUP(?,?,?,?,?,?,?,?)}";
	           st = conn.prepareCall(sql);
	           st.setString(1, cristal.getFamilia());
	           st.setString(2, cristal.getSubFamilia());
	           st.setString(3, cristal.getGrupoFamilia());
	           st.setString(4, montura.getCodigo());
	           st.setDouble(5, cristal.getEsfera());
	           st.setDouble(6, cristal.getCilindro());
	           st.registerOutParameter(7, OracleTypes.CURSOR);
	           st.registerOutParameter(8, Types.NUMERIC);
	           
	           st.execute();
	           
	           int estado = st.getInt(8);
	          
	           boolean encontrado = false;
	           boolean tiene = false;
	           
	           //cuando estado encontro validaciones
	           if (estado == 1) {
	        	   rs =(ResultSet)st.getObject(7);
	        	   while (rs.next()) {
		        	   tiene = true;
		        	   String suplemento_exigido = rs.getString("TRATAMI");
		        	   encontrado = false;
		        	   if (null != cristal.getListaSuplementos()) {
		        		   for (SuplementopedidoBean suplem : cristal.getListaSuplementos()) {
			        		   if (suplemento_exigido.equals(suplem.getTratami())) {
			        			   encontrado = true;
			        		   }
			        	   }
		        	   }
		        	   
		        	   if (!encontrado) {
		        		   msje = "Ojo: " + cristal.getOjo() +
		        		   			", Familia: " + cristal.getFamilia() + "-" + 
		        		   			cristal.getSubFamilia() + "-" + cristal.getGrupoFamilia() + 
		        		   			", Material: " + rs.getString("MATERIAL") + 
		        		   			", Estilo: " + rs.getString("ESTILO") + 
		        		   			", Dioptr�a: " + cristal.getEsfera() + " , " + cristal.getCilindro() +
		        		   			"; Debe venderse con suplemento " + suplemento_exigido;
		        		   return msje;
		        	   }
		        	   else
		        	   {
		        		   msje = Constantes.STRING_TRUE;
		        	   }
		           }
	        	   
	        	   if (!tiene) 
		           {
		        	   msje = Constantes.STRING_TRUE;
		           }
				
	           }
	           else //cuando estado no tiene validaciones, devuelve un true
	           {
	        	   msje = Constantes.STRING_TRUE;
	           }
	           
	           return msje;
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:validaCrisMonSup error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_VALIDA_CRISMONSUP");
	       } finally {
	              try{
	            	  if (null != rs){
		            	   log.warn("UtilesDAOImpl:validaCrisMonSup cierre Resultset");
		            	   rs.close();
		               }
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:validaCrisMonSup cierre CallableStatement");
	                   st.close();
	               }  
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:validaCrisMonSup cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:validaCrisMonSup error", e);
	           }
	       }
	}
	
	
	public ArrayList<GiroBean> busquedaGiro(String codigogiro, String descripcionGiro) throws Exception{
		ArrayList<GiroBean> lista = new ArrayList<GiroBean>();
		

		   log.info("UtilesDAOImpl:traeGiros inicio");
		   ArrayList<GiroBean> lista_Giros = new ArrayList<GiroBean>();
	       Connection conn = null;
	       ResultSet rs = null;
	       CallableStatement st= null;
	      
	       try {
	    	   log.info("UtilesDAOImpl:traeGiros conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           String sql = "{call SP_UTIL_SEL_GIRO_FACTURA(?,?,?)}";
	           st = conn.prepareCall(sql);
	           st.setString(1, codigogiro);
	           st.setString(2, descripcionGiro);
	           st.registerOutParameter(3, OracleTypes.CURSOR);
	           
	           st.execute();
	           rs = (ResultSet)st.getObject(3);
	          
	           GiroBean giro;
	          
	           while (rs.next())
	           {
	        	   log.debug("UtilesDAOImpl:traeGiros entrando ciclo while");
	               giro = new GiroBean();
	               giro.setCodigo(rs.getString("CDG"));
	               giro.setDescripcion(rs.getString("DESCRIPCION"));
	              
	               lista_Giros.add(giro);
	           }
	          
	           return lista_Giros;
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:traeGiros error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_GIROS");
	       } finally {
	              try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeGiros cierre ResultSet");
	                   rs.close();
	               }
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:traeGiros cierre CallableStatement");
	                   st.close();
	               }         
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:traeGiros cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:traeGiros error", e);
	           }
	       }
		   
	   
	}
	public ArrayList<ConvenioBean> traeConveniosParametros(String codigo,
			String nombre, String empresa) throws Exception 
	{
		 log.info("UtilesDAOImpl:traeConveniosParametros inicio");
	        ArrayList<ConvenioBean> lista_Convenios = new ArrayList<ConvenioBean>();
	        Connection conn = null;
	        ResultSet rs = null;
	        CallableStatement st= null;
	       
	        try {
	        	log.info("UtilesDAOImpl:traeConveniosParametros conectando base datos");
	            conn = ConexionFactory.INSTANCE.getConexion();
	            System.out.println("codigo ==> "+codigo +"<==> nombre"+nombre+"<==> empresa ==>"+empresa);
	            String sql = "{call SP_UTIL_SEL_CONVENIOS_PARAM(?,?,?,?)}";
	            st = conn.prepareCall(sql);
	            st.setString(1, codigo);
	            st.setString(2, nombre);
	            st.setString(3, empresa);
	            st.registerOutParameter(4, OracleTypes.CURSOR);
	            
	            st.execute();
	            rs = (ResultSet)st.getObject(4);
	           
	            ConvenioBean convenio;
	           
	            while (rs.next())
	            {
	            	log.debug("UtilesDAOImpl:traeConveniosParametros entrando ciclo while");
	                convenio = new ConvenioBean();
	                convenio.setId(rs.getString("CDG"));
	                convenio.setDescripcion(rs.getString("DESCRIPCION"));
	                convenio.setTipo(rs.getString("TIPO"));
	                convenio.setImprime_guia(rs.getString("GUIA"));
	                convenio.setRut(rs.getString("RUT"));
	                convenio.setDv(rs.getString("LETRARUT"));
	                convenio.setDireccion(rs.getString("DIRECCION"));
	                convenio.setGiro(rs.getString("GIRO"));
	                convenio.setTelefono(rs.getString("TELEFONO"));
	                convenio.setIsapre(rs.getString("ISAPRE"));

	                lista_Convenios.add(convenio);
	            }
	           
	            return lista_Convenios;
	           
	        } catch (Exception e) {
	        	log.error("UtilesDAOImpl:traeConveniosParametros error controlado",e);
	             throw new Exception("Error en DAO, SP_UTIL_SEL_CONVENIOS_PARAM");
	        } finally {
	               try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeConveniosParametros cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeConveniosParametros cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeConveniosParametros cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeConveniosParametros error", e);
	            }
	        }
	       
	    }
	public ArrayList<ConvenioLnBean> traeConvenioLineas(String id) throws Exception {
		log.info("UtilesDAOImpl:traeConveniosLineas inicio");
        ArrayList<ConvenioLnBean> lista = new ArrayList<ConvenioLnBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeConveniosLineas conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_CONVENIOS_LINEAS(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, id);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
           
            ConvenioLnBean linea;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeConveniosLineas entrando ciclo while");
            	linea = new ConvenioLnBean();
            	linea.setConveniocb(rs.getString("CONVENIOCB"));
            	linea.setDto(rs.getInt("DTO"));
            	linea.setForma_pago(rs.getString("FPAGO"));
            	linea.setForma_pago_desc(rs.getString("FPAGO_DESC"));
            	linea.setIdent(rs.getInt("IDENT"));
                lista.add(linea);
            }
           
            return lista;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeConveniosLineas error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_CONVENIOS_LINEAS");
        } finally {
               try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeConveniosLineas cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeConveniosLineas cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeConveniosLineas cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeConveniosLineas error", e);
            }
        }
	}
	public ArrayList<ConvenioFamBean> traeConvenioLineasFamilias(int ident) throws Exception {
		log.info("UtilesDAOImpl:traeConveniosLineas inicio");
        ArrayList<ConvenioFamBean> lista = new ArrayList<ConvenioFamBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeConvenioLineasFamilias conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_CONVENIOS_LN_FAM(?,?)}";
            st = conn.prepareCall(sql);
            st.setInt(1, ident);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
           
            ConvenioFamBean linea;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeConvenioLineasFamilias entrando ciclo while");
            	linea = new ConvenioFamBean();
            	linea.setConvenioln(rs.getInt("CONVENIOLN"));
            	linea.setFamilia(rs.getString("FAMILIA"));
            	linea.setSubfamilia(rs.getString("SUBFAM"));
            	linea.setGrupofamilia(rs.getString("GRUPOFAM"));
                lista.add(linea);
            }
           
            return lista;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_CONVENIOS_LN_FAM");
        } finally {
               try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
            }
        }
	}
	
	public ArrayList<BoletaBean> traeListaBoletasFechas(String cdg_vta, String fecha) throws Exception{
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_BOLETAS_FECHAS(?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, cdg_vta);
            st.setString(2, fecha);
            st.registerOutParameter(3, OracleTypes.CURSOR);
            Utils util = new Utils();
            st.execute();
            rs = (ResultSet)st.getObject(3);
            BoletaBean boleta;
            
            while(rs.next()){
            	boleta = new BoletaBean();
            	boleta.setPedvtcb(rs.getString("PEDVTCB"));
            	boleta.setNumero(rs.getInt("NUMERO"));
            	boleta.setImporte(rs.getInt("IMPORTEDEF"));
            	boleta.setTipo(rs.getString("TIPO"));
            	boleta.setFecha(util.formatoFecha(rs.getDate("FECHA")));
            	boleta.setLocal(rs.getString("LOCAL"));
            	lista_boletas.add(boleta);            	
            }
			
			
		 } catch (Exception e) {
	        	log.error("UtilesDAOImpl:traeListaBoletas error controlado",e);
	             throw new Exception("Error en DAO, SP_UTIL_SEL_BOLETAS_FECHAS");
	        } finally {
	               try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeListaBoletasFechas cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeListaBoletasFechas cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeListaBoletasFechas cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeListaBoletasFechas error", e);
	            }
	        }
		return lista_boletas;
	}
	
	
	public ArrayList<BoletaBean> traeListaBoletas(String cdg_vta) throws Exception{
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_BOLETAS(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, cdg_vta);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            Utils util = new Utils();
            st.execute();
            rs = (ResultSet)st.getObject(2);
            BoletaBean boleta;
            
            while(rs.next()){
            	boleta = new BoletaBean();
            	boleta.setPedvtcb(rs.getString("PEDVTCB"));
            	boleta.setNumero(rs.getInt("NUMERO"));
            	boleta.setImporte(rs.getInt("IMPORTEDEF"));
            	boleta.setTipo(rs.getString("TIPO"));
            	boleta.setFecha(util.formatoFecha(rs.getDate("FECHA")));
            	boleta.setLocal(rs.getString("LOCAL"));
            	lista_boletas.add(boleta);            	
            }
			
			
		 } catch (Exception e) {
	        	log.error("UtilesDAOImpl:traeListaBoletas error controlado",e);
	             throw new Exception("Error en DAO, SP_UTIL_SEL_CONVENIOS_LN_FAM");
	        } finally {
	               try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
	            }
	        }
		return lista_boletas;
	}
	
	
	public Integer[] traeValoresFenix(ProductosBean[] productos)throws Exception {
		log.info("UtilesDAOImpl:traeValoresFenix inicio");
		
		Integer valores[] = new Integer[2];
		
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        
        String fam[] = new String[3];
        String subfam[] = new String[3];
        String grupofam[] = new String[3];
        
        int x = 0;
        for (ProductosBean prod : productos) {
			fam[x] = prod.getFamilia();
			subfam[x] = prod.getSubFamilia();
			grupofam[x] = prod.getGrupoFamilia();
			x++;
		}
       
        try {
        	log.info("UtilesDAOImpl:traeValoresFenix conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_DTO_FENIX(?,?,?,?,?,?,?,?,?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, fam[0]);
            st.setString(2, fam[1]);
            st.setString(3, fam[2]);
            st.setString(4, subfam[0]);
            st.setString(5, subfam[1]);
            st.setString(6, subfam[2]);
            st.setString(7, grupofam[0]);
            st.setString(8, grupofam[1]);
            st.setString(9, grupofam[2]);
            st.registerOutParameter(10, OracleTypes.CURSOR);
            st.registerOutParameter(11, Types.VARCHAR);
            
            st.execute();
            String valor = st.getString(11);
            if (null != valor) {
            	rs = (ResultSet)st.getObject(10);
            	
            	while (rs.next())
                {
                	log.debug("UtilesDAOImpl:traeValoresFenix entrando ciclo while");
                	valores[0] = rs.getInt("PRECIO1");
                	valores[1] = rs.getInt("PRECIO2");
                }
               
			}
            else
            {
            	valores[0] = Constantes.INT_CERO;
            	valores[1] = Constantes.INT_CERO;
            }
            
           
            
            return valores;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeValoresFenix error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_DTO_FENIX");
        } finally {
               try{
               if (null != rs){
            	   log.warn("UtilesDAOImpl:traeValoresFenix cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeValoresFenix cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeValoresFenix cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeValoresFenix error", e);
            }
        }
	}
	
	public ArrayList<BoletaBean> traeListaBoletasAlbaranes(String cdg_vta, String tipo_albaran) throws Exception{
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_BOLETAS_ALBARANES(?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, cdg_vta);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            st.setString(3, tipo_albaran);
            Utils util = new Utils();
            st.execute();
            rs = (ResultSet)st.getObject(2);
            BoletaBean boleta;
            
            while(rs.next()){
            	boleta = new BoletaBean();
            	boleta.setPedvtcb(rs.getString("ALBVTCB"));
            	boleta.setNumero(rs.getInt("NUMERO"));
            	boleta.setImporte(rs.getInt("IMPORTE"));
            	boleta.setImportedef(rs.getInt("IMPORTEDEF"));
            	boleta.setTipo(rs.getString("TIPO"));
            	boleta.setFecha(util.formatoFecha(rs.getDate("FECHA")));
            	//boleta.setLocal(rs.getString("LOCAL"));
            	
            	lista_boletas.add(boleta);            	
            }
			
			
		 } catch (Exception e) {
	        	log.error("UtilesDAOImpl:traeListaBoletas error controlado",e);
	             throw new Exception("Error en DAO, SP_UTIL_SEL_CONVENIOS_LN_FAM");
	        } finally {
	               try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
	            }
	        }
		return lista_boletas;
	}
	
	public boolean eliminaAlbaran(String cdg, String fecha, String local, String tipo_albaran){
		boolean respuesta=false;
		Connection conn = null;        
        CallableStatement st= null;
        String respuestaSP = "";
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_DEL_ALBARANES(?,?,?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, cdg);
            st.registerOutParameter(2, OracleTypes.VARCHAR);
            st.setString(3, local);
            st.setString(4, fecha);
            st.setString(5, tipo_albaran);
            st.execute();
            respuestaSP = (String)st.getObject(2);
			
            if("TRUE".equals(respuestaSP)){
            	respuesta=true;
            }else{
            	respuesta=false;
            }
            
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try{	              
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
	            }
	        }
		return respuesta;
	}
	
	public boolean isController(String agente){
		boolean respuesta = false;
		Connection conn = null;        
        CallableStatement st= null;
        String respuestaSP = "";
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_ISCONTROLLER(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, agente);
            st.registerOutParameter(2, OracleTypes.VARCHAR);
            
            st.execute();
            respuestaSP = (String)st.getObject(2);
			
            if("S".equals(respuestaSP)){
            	respuesta=true;
            }else{
            	respuesta=false;
            }
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
            try{	              
                if (null != st){
                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
                    st.close();
                }  
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
            }
        }
		return false;
	}
	
	public AlbaranBean traeCodigoAlbaran(String cdg_pedvtcb){
		AlbaranBean alb = null;
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_CODIGO_ALBARANES(?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, cdg_pedvtcb);
            st.registerOutParameter(2, OracleTypes.VARCHAR);
            st.registerOutParameter(3, OracleTypes.CURSOR);
			
            st.execute();
            
            String respuesta = (String)st.getObject(2);
            
            if(Constantes.STRING_TRUE_MAY.equals(respuesta)){
            	rs = (ResultSet)st.getObject(3);
			}
            
            if(null != rs){      
            	
	            while(rs.next()){
	            	alb = new AlbaranBean();
	            	alb.setCodigo_albaran(rs.getString("ALBVTCB"));	            	
	            }
	            
            }
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
            try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
	            }
	        }
		
		return alb;
	}
	public String validaProductosMultiofertaBD(int cdg_correlativo_multioferta,
			String vta, String tipo) {
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        String mensaje = Constantes.STRING_BLANCO;
		try{
			
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_VALIDA_MULTIOFERTA(?,?,?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, "MON/" + cdg_correlativo_multioferta);
            st.setString(2, vta);
            st.setString(3, tipo);
            st.registerOutParameter(4, OracleTypes.VARCHAR);
			
            st.execute();
            
            mensaje = st.getString(4);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
            try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:traeConvenioLineasFamilias cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:traeConvenioLineasFamilias error", e);
	            }
	        }
		
		return mensaje;
	}
	
	public  ArrayList<PrecioEspecialBean> traePrecioEspecial(String codigoBarra, int cantidad, String fecha) throws Exception{
		log.info("UtilesDAOImpl:traePrecioEspecial inicio");
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        ArrayList<PrecioEspecialBean> listaPrecios = new ArrayList<PrecioEspecialBean>();
               
        try {
        	System.out.println("aplicaprecioespecial ==>"+codigoBarra+"<==>"+cantidad+"<=>"+fecha);
        	log.info("UtilesDAOImpl:traePrecioEspecial conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_PRECIO_ESPECIAL(?,?,?,?)}";
            st = conn.prepareCall(sql);           
            st.setString(1, codigoBarra);
            st.setInt(2, cantidad);
            st.setString(3, fecha);
            st.registerOutParameter(4, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(4);
            while (rs.next())
            {      
            	log.debug("UtilesDAOImpl:traePrecioEspecial entrando ciclo while");
            	PrecioEspecialBean precios = new PrecioEspecialBean();
            	
            	precios.setPrecio(rs.getInt("PRECIO"));
            	precios.setDto(rs.getDouble("DTO"));
            	precios.setCantidad(rs.getInt("CANTIDAD"));
            	
            	listaPrecios.add(precios);
            }
           
            return listaPrecios;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traePrecioEspecial error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_PRECIO_ESPECIAL");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traePrecioEspecial cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traePrecioEspecial cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:traePrecioEspecial cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traePrecioEspecial error", e);
            }
        }
	}
	public int validaDiametroESFCL(ProductosBean productosBean) throws Exception {
		log.info("UtilesDAOImpl:validaDiametroESFCL inicio");
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
        int valor = 0;
               
        try {
        	log.info("UtilesDAOImpl:validaDiametroESFCL conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_UTIL_SEL_DIAMETRO_ESF(?,?,?,?,?,?)}";
            st = conn.prepareCall(sql);           
            st.setString(1, productosBean.getFamilia());
            st.setString(2, productosBean.getSubFamilia());
            st.setString(3, productosBean.getGrupoFamilia());
            st.setDouble(4, productosBean.getEsfera());
            st.setDouble(5, productosBean.getCilindro());
            st.registerOutParameter(6, OracleTypes.NUMERIC);
            
            st.execute();
            valor = st.getInt(6);
           
            return valor;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:validaDiametroESFCL error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_DIAMETRO_ESF");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:validaDiametroESFCL cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:validaDiametroESFCL cierre CallableStatement");
                    st.close();
                }     
                if (null != conn){
                	log.warn("UtilesDAOImpl:validaDiametroESFCL cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:validaDiametroESFCL error", e);
            }
        }
	}
	public boolean validaEstadoPedidoEntregado(String codigoPendiente) throws Exception {
		log.info("UtilesDAOImpl:validaEstadoPed inicio");
		boolean estado2 = false;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:validaEstadoPedidoEntregado conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDAR_PED(?,?,?)}");
			cs.setString(1, codigoPendiente);
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.execute();
			
			String cerrado = Constantes.STRING_BLANCO;
			cerrado = (String)cs.getObject(2);
			try {
				if (Constantes.STRING_TIPO_ALB_NO.equals(cerrado)) {
					estado2 = false;
				}
				else
				{
					estado2 = true;
				}
			} catch (Exception e) {
				estado2 = false;
				log.error("UtilesDAOImpl:validaEstadoPedidoEntregado error controlado",e);
			}
			
		} catch (Exception e) {
			log.warn("UtilesDAOImpl:validaEstadoPedidoEntregado cierre ResultSet");
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDAR_CAJA"); 
		} finally {
	        try{
	         if (null != cs){
	             cs.close();
	             log.warn("UtilesDAOImpl:validaEstadoPedidoEntregado cierre CallableStatement");
	         }           
	         if (null != con){
		    	   con.close();
		    	   log.warn("UtilesDAOImpl:validaEstadoPedidoEntregado cierre Connection");
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:validaEstadoPedidoEntregado error", e);
	     }
		}
		
		return estado2;
	}
	
	public boolean validaEstadoLiberado(String encargo) throws Exception {
		log.info("UtilesDAOImpl:validaEstadoLiberado inicio");
		boolean estado2 = false;
	    Connection con = null;
	    CallableStatement cs = null;
        try {
		    	log.info("UtilesDAOImpl:validaEstadoLiberado conectando base datos");
		    	con = ConexionFactory.INSTANCE.getConexion();
				cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_ESTADO(?,?)}");
				cs.setString(1, encargo);
				cs.registerOutParameter(2, OracleTypes.NUMERIC);
				cs.execute();
				
				int estado = 0;
				estado = (Integer) cs.getObject(2);
				try {
					if (estado == 0) {
						estado2 = false;
					}
					else
					{
						estado2 = true;
					}
				} catch (Exception e) {
					estado2 = false;
					log.error("UtilesDAOImpl:validaEstadoLiberado error controlado",e);
				}
			} catch (Exception e) {
				log.warn("UtilesDAOImpl:validaEstadoLiberado cierre ResultSet");
		        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDA_ESTADO"); 
			} finally {
		        try{
		         if (null != cs){
		             cs.close();
		             log.warn("UtilesDAOImpl:validaEstadoLiberado cierre CallableStatement");
		         }           
		         if (null != con){
			    	   con.close();
			    	   log.warn("UtilesDAOImpl:validaEstadoLiberado cierre Connection");
		           } 
		         
	      }catch(Exception e){
		    	 log.error("UtilesDAOImpl:validaEstadoLiberado error", e);
	      }
		}
		
		return estado2;
	}
	
	public double validaEsferaMasCilindroMasAdd(String familia,
			String subFamilia, String grupoFamilia, double esfera,
			double cilindro, double adicion) throws Exception{
		log.info("UtilesDAOImpl:validaEsferaMasCilindro inicio");
	     
		   Connection conn = null;
	       CallableStatement st= null;
	       double num=0;
	       try {
	    	   log.info("UtilesDAOImpl:validaEsferaMasCilindro conectando base datos");
	           conn = ConexionFactory.INSTANCE.getConexion();
	           String sql = "{call SP_UTIL_SEL_VALIDA_ESFMASCILAD(?,?,?,?,?,?,?)}";
	           st = conn.prepareCall(sql);
	           st.setString(1, familia);
	           st.setString(2, subFamilia);
	           st.setString(3, grupoFamilia);
	           st.setDouble(4, esfera);
	           st.setDouble(5, cilindro);
	           st.setDouble(6, adicion);
	           st.registerOutParameter(7, Types.NUMERIC);
	           
	           st.execute();
	           num = st.getDouble(7);
				
	          
	           return num;	
	          
	       } catch (Exception e) {
	    	   log.error("UtilesDAOImpl:validaEsferaMasCilindro error controlado",e);
	            throw new Exception("Error en DAO, SP_UTIL_SEL_VALIDA_ESF_MAS_CILS");
	       } finally {
	              try{
	               if (null != st){
	            	   log.warn("UtilesDAOImpl:validaEsferaMasCilindro cierre CallableStatement");
	                   st.close();
	               }  
	               if (null != conn){
	            	   log.warn("UtilesDAOImpl:validaEsferaMasCilindro cierre Connection");
	    	    	   conn.close();
	               } 
	           }catch(Exception e){
	        	   log.error("UtilesDAOImpl:validaEsferaMasCilindro error", e);
	           }
	       }
	}
	public boolean validaProhibCrisArm(String familia, String subFamilia,
			String grupoFamilia, String codigo) throws Exception {
		log.info("UtilesDAOImpl:validaProhibCrisArm inicio");
		boolean estado2 = false;
		int estado = 0;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:validaProhibCrisArm conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("SP_UTIL_SEL_PROH_CRIS_ARM ===> "+familia+" - "+subFamilia+" - "+grupoFamilia+" - "+codigo);
			cs = con.prepareCall("{call SP_UTIL_SEL_PROH_CRIS_ARM(?,?,?,?,?)}");
			cs.setString(1, familia);
			cs.setString(2, subFamilia);
			cs.setString(3, grupoFamilia);
			cs.setString(4, codigo);
			cs.registerOutParameter(5, OracleTypes.NUMERIC);
			cs.execute();
			
			estado = cs.getInt(5);
			if (estado > 0) {
				estado2 = true;
			}
			
		} catch (Exception e) {
			log.warn("UtilesDAOImpl:validaProhibCrisArm cierre ResultSet");
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_PROH_CRIS_ARM"); 
		} finally {
	        try{
	         if (null != cs){
	             cs.close();
	             log.warn("UtilesDAOImpl:validaProhibCrisArm cierre CallableStatement");
	         }           
	         if (null != con){
		    	   con.close();
		    	   log.warn("UtilesDAOImpl:validaProhibCrisArm cierre Connection");
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:validaProhibCrisArm error", e);
	     }
		}
		
		return estado2;
	}
	
	public int validExPed(String encargo,String nif) throws Exception {
		
		log.info("VentaPedidoDAOImpl:validExPed inicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		System.out.println("SP validExPed =>"+encargo+"<=> NIF =>"+nif);
		try {
			log.info("VentaPedidoDAOImpl:validExPed conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_VAL_EX_PED(?,?,?)}");
			cs.setString(1, encargo);
			cs.setString(2,nif);
			cs.registerOutParameter(3, Types.NUMERIC);
			cs.execute();
			
			estado= cs.getInt(3);
			
			System.out.println("Estado ===>"+estado);
			
			/*if (num == 1) {
				estado = true;
			}*/
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:validExPed error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:validExPed cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:validExPed cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:validExPed error", e);
           }
		
		}
		return estado;
	}
	public ArrayList<VentaPedidoBean> trae_historial_encargo(String encargo) throws Exception {
		
		
		
		ArrayList<VentaPedidoBean> lista_historial = new ArrayList<VentaPedidoBean>();
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
		try{
			System.out.println("encargo ==>"+encargo);
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_TRAE_HISTORIAL_ENCARGO(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, encargo);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(2);
            VentaPedidoBean vpbean ;
            
            while(rs.next()){
            	vpbean = new VentaPedidoBean();
            	vpbean.setNumdev(rs.getString("LINEA"));
            	vpbean.setEncargo_padre(rs.getString("ENCARGO_PADRE"));
            	vpbean.setLineadev(rs.getString("CODSAP"));
            	lista_historial.add(vpbean);            	
            }
           
		 } catch (Exception e) {
	        	log.error("UtilesDAOImpl:traeListaBoletas error controlado",e);
	        	System.out.println("error=>"+e.getMessage());
	             //&throw new Exception("Error en DAO, SP_TRAE_HISTORIAL_ENCARGO");
	     } finally {
	               try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:trae_historial_encargo cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:trae_historial_encargo cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:trae_historial_encargo cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:trae_historial_encargo error", e);
	            }
	    }
		return lista_historial;
	}

	public ArrayList<VentaPedidoBean> trae_grupos_encargo(String encargo) throws Exception {
						
		ArrayList<VentaPedidoBean> lista_grupos_encargos = new ArrayList<VentaPedidoBean>();
		Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
		try{
			conn = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_LISTA_GRUPOS_ENCARGOS(?,?)}";
            st = conn.prepareCall(sql);
            st.setString(1, encargo);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            st.execute();
            rs = (ResultSet)st.getObject(2);
            VentaPedidoBean vpbean ;
            
            while(rs.next()){
            	vpbean = new VentaPedidoBean();
            	vpbean.setGrupo(rs.getString("GRUPO"));
            	lista_grupos_encargos.add(vpbean);            	
            }
           
		 } catch (Exception e) {
	        	log.error("UtilesDAOImpl:trae_grupos_encargo error controlado",e);
	        	System.out.println("SP_LISTA_GRUPOS_ENCARGOS error=>"+e.getMessage());
	             //&throw new Exception("Error en DAO, SP_LISTA_GRUPOS_ENCARGOS");
	     } finally {
	               try{
	               if (null != rs){
	            	   log.warn("UtilesDAOImpl:trae_grupos_encargo cierre ResultSet");
	                    rs.close();
	                }
	                if (null != st){
	                	log.warn("UtilesDAOImpl:trae_grupos_encargo cierre CallableStatement");
	                    st.close();
	                }  
	                if (null != conn){
	                	log.warn("UtilesDAOImpl:trae_grupos_encargo cierre Connection");
	     	    	   conn.close();
	                } 
	            }catch(Exception e){
	            	log.error("UtilesDAOImpl:trae_grupos_encargo error", e);
	            }
	    }
		return lista_grupos_encargos;
	}
	public int validExTienda(String numero) throws Exception {
		
		log.info("VentaPedidoDAOImpl:validExTienda inicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		System.out.println("SP validExTienda =>"+numero);
		try {
			log.info("VentaPedidoDAOImpl:validExTienda conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_VAL_EX_TIENDA(?,?)}");
			cs.setString(1, numero);
			cs.registerOutParameter(2, Types.NUMERIC);
			cs.execute();
			
			estado= cs.getInt(2);
			
			System.out.println("Estado ===>"+estado);
			
			/*if (num == 1) {
				estado = true;
			}*/
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:validExPed error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:validExPed cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:validExPed cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:validExPed error", e);
           }
		
		}
		return estado;
	}
	public int valida_dto_seg_armazon(String cdg,String local,String enc) throws Exception {
	
		log.info("VentaPedidoDAOImpl:valida_dto_seg_armazon inicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		try {
			log.info("VentaPedidoDAOImpl:valida_dto_seg_armazon conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_APLICA_PROMOCION(?,?,?,?)}");
			System.out.println(" cdg ==> "+cdg+"<==> local =>"+local+"<==> enc =>"+enc);
			cs.setString(1, cdg);
			cs.setString(2, local);
			cs.setString(3, enc);
			cs.registerOutParameter(4, Types.NUMERIC);
			
			cs.execute();
			
			estado= cs.getInt(4);
		
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:valida_dto_seg_armazon error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:valida_dto_seg_armazon cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:valida_dto_seg_armazon cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:valida_dto_seg_armazon error", e);
              }
		
		}
		return estado;
	}
	
	public int valida_tipo_convenio(String tipoconvenio) throws Exception {
		
		log.info("VentaPedidoDAOImpl:valida_tipo_convenio inicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		try {
			log.info("VentaPedidoDAOImpl:valida_tipo_convenio conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_TIPO_PROMOCION(?,?)}");
			cs.setString(1, tipoconvenio);
			cs.registerOutParameter(2, Types.NUMERIC);
			
			cs.execute();
			
			estado= cs.getInt(2);
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:valida_tipo_convenio error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:valida_tipo_convenio cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:valida_tipo_convenio cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:valida_tipo_convenio error", e);
              }
		
		}
		return estado;
	}
	
	public int valida_permiso_mod_fpago(String usuario,String pass , String local) throws Exception {
		
		log.info("VentaPedidoDAOImpl:valida_permiso_mod_fpago inicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		try {
			log.info("VentaPedidoDAOImpl:valida_permiso_mod_fpago conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println(usuario+","+pass+","+local);
			cs = con.prepareCall("{call  SP_VALIDA_MOD_FPAGO(?,?,?,?)}");
			cs.setString(1, usuario);
			cs.setString(2, pass);
			cs.setString(3, local);
			cs.registerOutParameter(4, Types.NUMERIC);
			
			cs.execute();
			
			estado= cs.getInt(4);
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:valida_permiso_mod_fpago error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:valida_permiso_mod_fpago cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:valida_permiso_mod_fpago cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:valida_permiso_mod_fpago error", e);
              }
		
		}
		return estado;
	}
	
	public int respaldo_boleta(String encargo,String responsable,String fecha,String tipo) throws Exception {
		
		log.info("VentaPedidoDAOImpl:respaldo_boletainicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		String res = responsable.equals("") ? " ": responsable;  
		try {
			log.info("VentaPedidoDAOImpl:respaldo_boleta conectando base datos");
			
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println(encargo+"<=>"+res+"<=>"+fecha+"<=>"+tipo);
			cs = con.prepareCall("{call  SP_COPIA_ENCARGO(?,?,?,?,?)}");
			//cs.setString(1, tipofpago);
			cs.setString(1, encargo);
			cs.setString(2, res);
			cs.setString(3, fecha);
			cs.setString(4, tipo);
			cs.registerOutParameter(5, Types.NUMERIC);			
			cs.execute();
			
			estado= cs.getInt(5);
			
			System.out.println("devuelvo===>"+estado);
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:respaldo_boleta error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:respaldo_boleta cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:respaldo_boleta cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:respaldo_boleta error", e);
              }
		
		}
		return estado;
	}
	/* Metodo que comprueba si ya agregue los productos gratuitos*/
	public int valida_productos_gratuitos(String encargo) throws Exception {
		
		log.info("VentaPedidoDAOImpl:valida_productos_gratuitos inicio");
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		try {
			log.info("VentaPedidoDAOImpl:valida_productos_gratuitos conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_VALIDA_PRODUCTOS_GRATUITOS(?,?)}");
			cs.setString(1, encargo);	
			cs.registerOutParameter(2, Types.NUMERIC);
			
			cs.execute();
			
			estado= cs.getInt(2);
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:valida_productos_gratuitos error controlado",e);
            //throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:valida_productos_gratuitos cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:valida_productos_gratuitos cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:valida_productos_gratuitos error", e);
              }
		
		}
		return estado;
	}
	
	
	/* 
	 * LMARIN 20140613
	 * Metodo que me devuelve el monto minimo total 
	 * que debe tener un encargo para que se pueda aplicar un convenio determinado
	 * @param String convenio
	 * @return int monto 
	 */
	
	public int traeMontoMinDescuento(ProductosBean prod,String convenio,String fpago,int dto) throws Exception {
		
		log.info("VentaPedidoDAOImpl:traeMontoMinDescuento inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		int estado = 0;
		
		
		try {
			log.info("VentaPedidoDAOImpl:traeMontoMinDescuento conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_TRAE_DESCUENTO_EXCEP(?,?,?,?,?,?,?)}");
			cs.setString(1,convenio);	
			cs.setString(2,fpago);	
			cs.setInt(3,dto);	
			cs.setString(4,prod.getFamilia());	
			cs.setString(5,prod.getSubFamilia());	
			cs.setString(6,prod.getGrupoFamilia());	
			cs.registerOutParameter(7, Types.NUMERIC);
			
			System.out.println("convenio =>"+convenio+" =>fpago =>"+fpago+" dto=>"+dto+" Familia=>"+prod.getFamilia()+" TipoFamilia=>"+prod.getSubFamilia()+" GrupoFamilia=>"+prod.getGrupoFamilia());
			
			cs.execute();
			
			estado= cs.getInt(7);
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:traeMontoMinDescuento error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:traeMontoMinDescuento cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:traeMontoMinDescuento cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:traeMontoMinDescuento error", e);
              }
		
		}
		return estado;
	}
	
	
	/** 
	 * LMARIN 20140807
	 * Metodo que me devuelve un Objeto con los datos de la tienda
	 * @param String local
	 * @return ArrayList<TiendaBean> 
	 */
	
	public ArrayList<TiendaBean> traeDatosTienda(String local) throws Exception {
		
		log.info("VentaPedidoDAOImpl:traeDatosTienda inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		ArrayList<TiendaBean> tienda = new ArrayList<TiendaBean>();
		ResultSet dtienda  =null;
		System.out.println("Tienda  utilesdao ==>"+ local);
		try {
			log.info("VentaPedidoDAOImpl:traeDatosTienda conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_TRAE_DATOS_TIENDA(?,?)}");
			cs.setString(1,local);		
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
		    dtienda = (ResultSet) cs.getObject(2); 
		     
			TiendaBean tb;
			
			while(dtienda.next()){				
				tb = new TiendaBean();
				tb.setLocal(dtienda.getString("LOCAL"));
				tb.setSpedvt(dtienda.getString("SPEDVT"));
				tb.setTelefono1(dtienda.getString("TELEFONO1"));
				tb.setTelefono2(dtienda.getString("TELEFONO2"));
				tb.setEmail(dtienda.getString("EMAIL"));
				tb.setTitular(dtienda.getString("TITULAR"));
				tb.setJefeMall(dtienda.getString("JEFEMALL"));
				tb.setCampolibre6(dtienda.getString("CAMPOLIBRE6"));
				tb.setCampolibre7(dtienda.getString("CAMPOLIBRE7"));
				tb.setClasificacion(dtienda.getString("CLASIFICACION"));
				tb.setRegion(dtienda.getString("REGION"));
				tb.setTipo_impresion(dtienda.getString("TIPO_IMPRESION"));
				tb.setDireccion(dtienda.getString("DIRECCION"));
				tb.setLocalidad(dtienda.getString("COMUNA"));
				tienda.add(tb);
			}
		
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:traeDatosTienda error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:traeDatosTienda cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:traeDatosTienda cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:traeDatosTienda error", e);
              }
		
		}
		
		
		return tienda;
	}
	
	
	
	
	
	/* 
	 * LMARIN 20140821
	 * Metodo que devuelce siempre un dia m�s
	 * @param String fecha
	 * @return int  dias
	 */
	
	public Date agregaDias(String fecha) throws Exception {
		
		log.info("VentaPedidoDAOImpl:traeDatosTienda inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		Date fechao = new Date();
		try {
			log.info("VentaPedidoDAOImpl:agregaDias conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_AGREGAR_DIAS(?,?)}");
			cs.setString(1,fecha);
			cs.registerOutParameter(2, Types.DATE);
			cs.execute();
		    fechao =  cs.getDate(2);		   
		   
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:agregaDias error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:agregaDias cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:agregaDias cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:agregaDias error", e);
              }
		
		}
		
		return fechao;
	}
	
	/* 
	 * LMARIN 20140828
	 * M�todo que devuelve las multiofertas asociadas a un trio
	 * @ param String armazon
	 * @ param String cristal
	 * @ param String tienda
	 * @ return ArrayList<ProductosBean> codigo multiofertas
	 */
	
	public ArrayList<ProductosBean> traeMultiofertasAso (String armazon,String cristal,String tienda) throws Exception {
		
		log.info("UtilesDAOImpl:traeMultiofertasAso inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		ArrayList<ProductosBean> mul = new ArrayList<ProductosBean>();
		ResultSet moferta  =null;
		try {
			
			log.info("UtilesDAOImpl:traeMultiofertasAso conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_TRAE_MULTIOFERTAS_ASOCIADAS(?,?,?,?)}");
			cs.setString(1,armazon);	
			cs.setString(2,cristal);
			cs.setString(3,tienda);	
			
			System.out.println("a =>"+armazon+" b=>"+cristal+" c=>"+tienda);

			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.execute();
		    moferta = (ResultSet) cs.getObject(4); 
		     	
		    ProductosBean prod;
		    while(moferta.next()){	
				prod = new ProductosBean();
				prod.setCodigo(moferta.getString("CDG"));
				prod.setCod_barra(moferta.getString("CODIGOBARRAS"));
				prod.setDescripcion(moferta.getString("DESCRIPCION"));
				mul.add(prod);								
			}
				
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeMultiofertasAso error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("UtilesDAOImpl:traeMultiofertasAso cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("UtilesDAOImpl:traeMultiofertasAso cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("UtilesDAOImpl:traeMultiofertasAso error", e);
              }
		
		}
		
		
		return mul;
	}
	
	/* 
	* LMARIN 20150327
	* M�todo que devuelve DTO asociado a los combos de multiofertas seleccionadas
	* @ param String mofer1,mofer2
	* @ return int DTO Combos Multiofertas
	*/
	
	public int traeDTOMofercombos (String mofer1,String mofer2) throws Exception {
		
		log.info("UtilesDAOImpl:traeDTOMofercombos inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		int res  = 0;
		try {
			
			log.info("UtilesDAOImpl:traeDTOMofercombos conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			
			System.out.println("SP_MOFERCOMBOS ("+mofer1+","+mofer2+")");
			
			cs = con.prepareCall("{call  SP_MOFERCOMBOS(?,?,?)}");
			
			System.out.println("traeDTOMofercombos (UtilesDAOImpl) ==>" + mofer1 +"<=>"+ mofer2);
			
			cs.setString(1,mofer1);	
			cs.setString(2,mofer2);
		
			cs.registerOutParameter(3, OracleTypes.NUMERIC);
			cs.execute();
		    res = cs.getInt(3); 
		    
			System.out.println("traeDTOMofercombos res (1) ======> "+res);
 			  				
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeDTOMofercombos error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("UtilesDAOImpl:traeDTOMofercombos cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("UtilesDAOImpl:traeDTOMofercombos cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("UtilesDAOImpl:traeDTOMofercombos error", e);
              }		
		}	
		System.out.println("traeDTOMofercombos (2) res ======> "+res);
		return res;
	}
	/** 
	 * @Author: LMARIN 20151110
	 * Metodo que me devuelve el monto minimo total 
	 * que debe tener un encargo para que se pueda aplicar un convenio determinado
	 * @param String encargo
	 * @return int monto de dto 
	 */
	
	public int traeMontoDTOPromoLec(String encargo,String convenio) throws Exception {
		
		log.info("VentaPedidoDAOImpl:traeMontoDTOPromoLec inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		int dto = 0;
				
		try {
			
			log.info("VentaPedidoDAOImpl:traeMontoDTOPromoLec conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("exec SP_VALIDA_PROMOLEC('"+encargo+"','"+convenio+"');");
			cs = con.prepareCall("{call  SP_VALIDA_PROMOLEC(?,?,?)}");
			cs.setString(1,encargo);
			cs.setString(2,convenio);
			cs.registerOutParameter(3, Types.NUMERIC);						
			cs.execute();			
			dto = cs.getInt(3);	
			System.out.println("RES SP_VALIDA_PROMOLEC ==>"+dto);
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:traeMontoDTOPromoLec error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("VentaPedidoDAOImpl:traeMontoDTOPromoLec cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("VentaPedidoDAOImpl:traeMontoDTOPromoLec cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("VentaPedidoDAOImpl:traeMontoDTOPromoLec error", e);
              }
		
		}
		return dto;
	}
	
	/** 
	 * @Author: LMARIN 
	 * @date: 20160803
	 * @param: M�todo que asigna grupo a las lentillas 
	 */
	
	public int gruposLentillas(String encargo) throws Exception {
		
		log.info("VentaPedidoDAOImpl:traeMontoDTOPromoLec inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		int dto = 0;
				
		try {
			System.out.println("Paso por grupo lentillas ===> "+encargo);
			log.info("UtilesDAOImpl:gruposLentillas conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_GRUPO_LENTILLAS(?)}");
			cs.setString(1,encargo);				
			cs.execute();			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:gruposLentillas error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("UtilesDAOImpl:gruposLentillas cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("UtilesDAOImpl:gruposLentillas cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("UtilesDAOImpl:gruposLentillas error", e);
              }
		
		}
		return dto;
	}
	/** 
	 * @Author: LMARIN 
	 * @date: 20160830
	 * @description :M�todo que trae articulo relaciona en los articulos clientes desde encargo padre
	 * @param String encargo,String tipofam,String grupo
	 * @return String res
	 */
	
	public String traeArticuloRel(String encargo,String tipofam,String grupo) throws Exception {
		
		log.info("VentaPedidoDAOImpl:traeMontoDTOPromoLec inicio"); 
		Connection con = null;
		CallableStatement cs = null;
		String res = "";
				
		try {
			System.out.println("Paso por grupo lentillas ===> "+encargo);
			log.info("UtilesDAOImpl:gruposLentillas conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call  SP_TRAE_ARTICULOS_REL(?,?,?,?)}");
			cs.setString(1,encargo);	
			cs.setString(2,tipofam);
			cs.setString(3,grupo);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();			
			res = cs.getString(4);
			System.out.println("ENCARGO ==> " +encargo+"  TIPOFAM ==>"+tipofam+" GRUPO ==>"+grupo);
			System.out.println("CODSAP2 ==> " +res);
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeArticuloRel error controlado",e);
		} finally {
              try{
	               if (null != cs){
	            	   log.warn("UtilesDAOImpl:traeArticuloRel cierre CallableStatement");
	                   cs.close();
	               }  
	               if (null != con){
	            	   log.warn("UtilesDAOImpl:traeArticuloRel cierre Connection");
	            	   con.close();
	               }
              }catch(Exception e){
            	  log.error("UtilesDAOImpl:traeArticuloRel error", e);
              }
		
		}
		return res;
	}
	
	/** 
	 * @Author: LMARIN 
	 * @date: 20170515
	 * @description :M�todo que trae los datos pra la liberacion automatica
	 * @param String encargo,String tipofam,String grupo
	 * @return String res
	 */
	public ArrayList<VentaPedidoBean> traeDetalleVentasPedidosAM(String codigo, String grupo){
		
		log.info("UtilesDAOImpl:traeDetalleVentasPedidos inicio");
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		Utils util = new Utils();
		ArrayList<VentaPedidoBean> lista_ventas = new ArrayList<VentaPedidoBean>();
		
		try{
			log.info("UtilesDAOImpl:traeDetalleVentasPedidos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("SP_LISTA_DETALLE_VTA_PEDIDO_AM => codigo==> "+codigo+" grupo ==>"+grupo);
			String sql = "{call SP_LISTA_DETALLE_VTA_PEDIDO_AM(?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, codigo);
			st.setInt(2, Integer.parseInt(grupo));
			st.registerOutParameter(3, OracleTypes.CURSOR);
			
			st.execute();			
			rs = (ResultSet)st.getObject(3);
			
			VentaPedidoBean venta;
			
			while(rs.next()){
				log.debug("UtilesDAOImpl:traeDetalleVentasPedidosAM entrando ciclo while");
				venta = new VentaPedidoBean();
				
				venta.setCodigo(rs.getInt("IDENT"));
				venta.setCod_detalle_vta(rs.getString("CODIGO"));
				venta.setCod_lista_lib(rs.getString("PEDVTCB"));
				venta.setArticulo(rs.getString("ARTICULO"));
				if(null != rs.getString("DESCRIPCION")){
					venta.setDescripcion(rs.getString("DESCRIPCION").trim());
				}else{
					venta.setDescripcion(Constantes.STRING_BLANCO);
				}				
				venta.setUnahora(rs.getString("UNAHORA"));
				venta.setOjo(rs.getString("OJO"));
				venta.setEsfera(rs.getDouble("ESFERA"));
				venta.setCilindro(rs.getDouble("CILINDRO"));
				venta.setCantidad(rs.getInt("CANTIDAD"));
				venta.setLinea(rs.getInt("LINEA"));
				venta.setNumerograd(rs.getDouble("NUMEROGRAD"));
				venta.setFechagrad(util.formatoFecha(rs.getDate("FECHAGRAD")));
				venta.setEje(rs.getDouble("EJE"));
				venta.setTipo_familia(rs.getString("TIPO_FAMILIA"));
				venta.setId_cliente(rs.getString("CLIENTE"));
				venta.setFecha(util.formatoFecha(rs.getDate("FECHAPEDIDO")));
				venta.setFecha_entrega(util.formatoFecha(rs.getDate("FECHAENTREGA")));
				venta.setHora(rs.getString("HORA"));
				venta.setAgente(rs.getString("AGENTE"));
				venta.setLocal(rs.getString("LOCAL"));
				venta.setLocalsap(rs.getString("LOCALSAP"));
				venta.setGrupo(rs.getString("GRUPO"));
				System.out.println("LOCAL =>"+rs.getString("LOCAL")+" <===> LOCALSAP"+rs.getString("LOCALSAP")+" FECHAPEDIDO ==>"+rs.getString("FECHAPEDIDO")+" <==> FECHAENTREGA =>"+rs.getString("FECHAENTREGA"));
				
				lista_ventas.add(venta);
			}			
			
			return lista_ventas;
			
		}catch(SQLException ex){
			log.error("UtilesDAOImpl:traeDetalleVentasPedidosAM error controlado",ex);
			return lista_ventas;
			
		}finally {
            try{
            	if (null != rs){
            		log.warn("UtilesDAOImpl:traeDetalleVentasPedidosAM cierre ResultSet");
                	rs.close();
                } 
                if (null != st){
                	log.warn("UtilesDAOImpl:traeDetalleVentasPedidosAM cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("UtilesDAOImpl:traeDetalleVentasPedidosAM cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeDetalleVentasPedidosAM error", e);
            }
        }
	}
	
	
	/** 
	 * @Author: LMARIN 
	 * @date: 20170526
	 * @description :M�todo que valida si una lentilla es graduable
	 * @param String codigo
	 * @return boolean 
	 */
	public boolean validaLentillaGraduable(String codigo) throws Exception {
	
		log.info("UtilesDAOImpl:validaLentillaGraduable inicio");
		Connection con = null;
		CallableStatement cs = null;
		int numero = Constantes.INT_CERO;
		boolean estado = false;
		
		try {
			log.info("UtilesDAOImpl:validaLentillaGraduable conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	System.out.println("Valido Lentillas ==>"+ codigo);
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_LEN_GRAD(?,?)}");
			cs.setString(1, codigo);
			cs.registerOutParameter(2, Types.NUMERIC);
			
			cs.execute();
			
			numero = cs.getInt(2);
			
			if (numero != Constantes.INT_CERO) {
				estado = true;
			}
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaLentillaGraduable error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDA_LEN_GRAD"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:validaLentillaGraduable cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:validaLentillaGraduable cierre Connection");
		    	   con.close();
	           } 
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:validaLentillaGraduable error", e);
		     }
		}
		
		return estado;
	}
	
	/** 
	 * @Author: LMARIN 
	 * @date: 20170821
	 * @description :M�todo que valida venta seguro
	 * @param String codigo
	 * @return int 
	 */
	public int validaVentaSeguro(String codigo) throws Exception {
	
		log.info("UtilesDAOImpl:validaVentaSeguro inicio");
		Connection con = null;
		CallableStatement cs = null;
		int numero = Constantes.INT_CERO;
		int estado = 0;
		
		try {
			log.info("UtilesDAOImpl:validaVentaSeguro conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	System.out.println("validaVentaSeguro ==>"+ codigo);
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_VTA_SEG(?,?)}");
			System.out.println("SP_UTIL_SEL_VALIDA_VTA_SEG ==> "+codigo);
			cs.setString(1, codigo);
			cs.registerOutParameter(2, Types.NUMERIC);
			cs.execute();
			estado = cs.getInt(2);
			System.out.println(estado);	
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaVentaSeguro error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDA_VTA_SEG"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:validaVentaSeguro cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:validaVentaSeguro cierre Connection");
		    	   con.close();
	           } 
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:validaVentaSeguro error", e);
		     }
		}
		
		return estado;
	}
	
	/** 
	 * @Author: LMARIN 
	 * @date: 20171010
	 * @description :M�todo que valida si aplica liberaci�n automatica
	 * @param String local
	 * @return String
	 */
	public String validaLibau(String local) throws Exception {
	
		log.info("UtilesDAOImpl:validaLibau inicio");
		Connection con = null;
		CallableStatement cs = null;
		String estado = "";
		
		try {
			log.info("UtilesDAOImpl:validaLibau conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	System.out.println("validaVentaSeguro ==>"+ local);
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_LIBAU(?,?)}");
			System.out.println("SP_UTIL_SEL_VALIDA_LIBAU ==> "+local);
			cs.setString(1,local);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			estado = cs.getString(2).trim();
			System.out.println(estado);	
		} catch (Exception e) {
			log.error("UtilesDAOImpl:validaLibau error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_VALIDA_LIBAU"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:validaLibau cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:validaLibau cierre Connection");
		    	   con.close();
	           } 
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:validaLibau error", e);
		     }
		}
		
		return estado;
	}
	
	/** 
	 * @Author: LMARIN 
	 * @date: 20171020
	 * @description :M�todo que TRAE EL VALOR DEL ENCARGO ANTERIOR CORRESPONDIENTE A LA VENTA SEGURO
	 * @param String encargo
	 * @return String
	 */
	public String traeImporteVG(String encargo) throws Exception {
	
		log.info("UtilesDAOImpl:retotalVS inicio");
		Connection con = null;
		CallableStatement cs = null;
		String importe = "";
		
		try {
			log.info("UtilesDAOImpl:retotalVS conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	System.out.println("validaVentaSeguro ==>"+ encargo);
			cs = con.prepareCall("{call SP_UTIL_RET_IMPVENTASEG(?,?)}");
			System.out.println("SP_UTIL_RET_IMPVENTASEG ==> "+encargo);
			cs.setString(1,encargo);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			importe = cs.getString(2).trim();
			System.out.println(importe);	
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeImporteVG error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_RET_IMPVENTASEG"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:traeImporteVG cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:traeImporteVG cierre Connection");
		    	   con.close();
	           } 
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:traeImporteVG error", e);
		     }
		}
		
		return importe;
	}
	
	public ArrayList<FormaPagoBean> traeFormasPagoTienda(String local) throws Exception
    {
    	log.info("UtilesDAOImpl:traeFormasPago inicio");
        ArrayList<FormaPagoBean> lista_formasPago = new ArrayList<FormaPagoBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeFormasPago conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            st = conn.prepareCall("{call SP_UTIL_SEL_FORMAS_PAGO_T(?,?)}");
            st.setString(1, local);
            System.out.println("local =======>"+local);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
           
            FormaPagoBean fpago;
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeFormasPago entrando ciclo while");
                fpago = new FormaPagoBean();
                fpago.setId(rs.getString("CDG"));
                fpago.setDescripcion(rs.getString("DESCRIPCION"));
                fpago.setTexto(rs.getString("TEXTO"));
               
                lista_formasPago.add(fpago);
            }
           
            return lista_formasPago;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeFormasPago error controlado",e);
             throw new Exception("Error en DAO, SP_UTIL_SEL_FORMAS_PAGO_T");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeFormasPago cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeFormasPago cierre CallableStatement");
                    st.close();
                }    
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeFormasPago cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeFormasPago error", e);
            }
        }
       
    }
   
	
	public ArrayList<ListaEstadosBean> traeEstadosEncargo() throws Exception
    {
    	log.info("UtilesDAOImpl:traeEstadosEncargo inicio");
        ArrayList<ListaEstadosBean> lista_estados = new ArrayList<ListaEstadosBean>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement st= null;
       
        try {
        	log.info("UtilesDAOImpl:traeEstadosEncargo conectando base datos");
            conn = ConexionFactory.INSTANCE.getConexion();
            st = conn.prepareCall("{call SP_LISTAESTADOS(?)}");
            st.registerOutParameter(1, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(1);
           
            while (rs.next())
            {
            	log.debug("UtilesDAOImpl:traeEstadosEncargo entrando ciclo while");
            	ListaEstadosBean lista = new ListaEstadosBean();
                lista.setId(rs.getString("ID"));
                lista.setDescripcion(rs.getString("DESCRIPCION"));
               
                lista_estados.add(lista);
            }
           
            return lista_estados;
           
        } catch (Exception e) {
        	log.error("UtilesDAOImpl:traeEstadosEncargo error controlado",e);
             throw new Exception("Error en DAO, SP_LISTAESTADOS");
        } finally {
               try{
                if (null != rs){
                	log.warn("UtilesDAOImpl:traeEstadosEncargo cierre ResultSet");
                    rs.close();
                }
                if (null != st){
                	log.warn("UtilesDAOImpl:traeEstadosEncargo cierre CallableStatement");
                    st.close();
                }    
                if (null != conn){
                	log.warn("UtilesDAOImpl:traeEstadosEncargo cierre Connection");
     	    	   conn.close();
                } 
            }catch(Exception e){
            	log.error("UtilesDAOImpl:traeEstadosEncargo error", e);
            }
        }
       
    }
	
	public int traeDescuentoCupon(ProductosBean productosBean,String cupon, String fpago,String local,String agente, String cliente,String encargo)  throws Exception {
		log.info("UtilesDAOImpl:traeDescuentoCupon inicio");
		int valor = Constantes.INT_CERO;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:traeDescuentoCupon conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("{call EXEC SP_UTIL_SEL_CUPON_DESC('"+cupon+"','"+fpago+"','"+productosBean.getFamilia()+"','"+productosBean.getSubFamilia()+"','"+productosBean.getGrupoFamilia()+"','"+productosBean.getPromopar().trim()+"','"+local+"','"+productosBean.getCodigo()+"','"+productosBean.getCod_barra()+"','"+productosBean.getCod_pedvtcb()+"','"+cliente+"','"+agente+"','"+encargo+"',:z)}");
			cs = con.prepareCall("{call SP_UTIL_SEL_CUPON_DESC(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, cupon);
			cs.setString(2, fpago);
			cs.setString(3, productosBean.getFamilia());
			cs.setString(4, productosBean.getSubFamilia());
			cs.setString(5, productosBean.getGrupoFamilia());
			cs.setString(6, productosBean.getPromopar().trim());
			cs.setString(7, local);
			cs.setString(8, productosBean.getCodigo());
			cs.setString(9, cliente);
			cs.setString(10, agente);
			cs.setString(11, encargo);
			cs.registerOutParameter(12, Types.NUMERIC);
			cs.execute(); 
			
			valor = (cs.getObject(12).toString() != null) && (cs.getObject(12).toString() != "")  ?  Integer.parseInt(cs.getObject(12).toString()): 0;
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeDescuentoCupon error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_SEL_CUPON_DESC"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:traeDescuentoCupon cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:traeDescuentoCupon cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:traeDescuentoCupon error", e);
	     }
		}
	    System.out.println("UtilesDAOImpl:traeDescuentoCupon ==>"+valor);
		return valor;
	}
	
	
	public int valida_encargo(String encargo,String cliente)  throws Exception {
		log.info("UtilesDAOImpl:valida_encargo inicio");
		int valor = Constantes.INT_CERO;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:valida_encargo conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("{call SP_VALIDA_ENCARGO("+encargo+","+cliente+",:z)}");
			cs = con.prepareCall("{call SP_VALIDA_ENCARGO(?,?,?)}");
			cs.setString(1, encargo);
			cs.setString(2, cliente);
			cs.registerOutParameter(3, Types.NUMERIC);
			cs.execute(); 
			
			valor = (cs.getObject(3).toString() != null) && (cs.getObject(3).toString() != "")  ?  Integer.parseInt(cs.getObject(3).toString()): 0;
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:valida_encargo error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_VALIDA_ENCARGO"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:valida_encargo cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:valida_encargo cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:valida_encargo error", e);
	     }
		}
		return valor;
	}
    
	public int IngresaCupon(String cupon,String local,String agente, String cliente,String encargo)  throws Exception {
		log.info("UtilesDAOImpl:IngresaCupon inicio");
		int valor = Constantes.INT_CERO;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:IngresaCupon conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("{call SP_INGRESA_CUPON("+cupon+","+local+","+cliente+","+agente+","+encargo+",:z)}");
			cs = con.prepareCall("{call SP_INGRESA_CUPON(?,?,?,?,?,?)}");
			
			cs.setString(1, cupon);
			cs.setString(2, local);
			cs.setString(3, cliente);
			cs.setString(4, agente);
			cs.setString(5, encargo);
			cs.registerOutParameter(6, Types.NUMERIC);
			cs.execute(); 
			
			valor = (cs.getObject(6).toString() != null) && (cs.getObject(6).toString() != "")  ?  Integer.parseInt(cs.getObject(6).toString()): 0;
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:IngresaCupon error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_INGRESA_CUPON"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:IngresaCupon cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:IngresaCupon cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:IngresaCupon error", e);
	     }
		}
	    System.out.println("UtilesDAOImpl:IngresaCupon ==>"+valor);
		return valor;
	}
	
	public String valida_cupon(String cupon,String nif,String cdg)  throws Exception {
		log.info("UtilesDAOImpl:valida_cupon inicio");
		String valor = "";
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	log.info("UtilesDAOImpl:valida_cupon conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("{call SP_VALIDA_CUPON("+cupon+","+nif+","+cdg+",:z)}");
			cs = con.prepareCall("{call SP_VALIDA_CUPON(?,?,?,?)}");
			cs.setString(1, cupon);
			cs.setString(2,nif);
			cs.setString(3,cdg);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute(); 
			
			valor = (cs.getObject(4).toString() != null) && (cs.getObject(4).toString() != "")  ? cs.getObject(4).toString(): "0_0";
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeDescuentoCupon error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_VALIDA_CUPON"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:valida_cupon cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:valida_cupon cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:valida_cupon error", e);
	     }
		}
		return valor;
	}
	
	public double traeDescuentoCombo(ProductosBean productosBean,String local,String agente, String cliente)  throws Exception {
		log.info("UtilesDAOImpl:traeDescuentoCombo inicio");
		double valor = Constantes.INT_CERO;
	    Connection con = null;	
	    CallableStatement cs = null;
	     try {
	    	log.info("UtilesDAOImpl:traeDescuentoComboconectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("{call SP_TRAE_DTO_PROMOCOMBO("+productosBean.getFamilia()+","+productosBean.getSubFamilia()+","+productosBean.getGrupoFamilia()+","+productosBean.getPromopar().trim()+","+local+","+productosBean.getCodigo()+" "+productosBean.getImporte()+" "+","+cliente+","+agente+":z)}");
			cs = con.prepareCall("{call SP_TRAE_DTO_PROMOCOMBO(?,?,?,?,?,?,?,?,?,?)}");

			cs.setString(1, productosBean.getFamilia());
			cs.setString(2, productosBean.getSubFamilia());
			cs.setString(3, productosBean.getGrupoFamilia());
			cs.setString(4, productosBean.getPromopar().trim());
			cs.setString(5, local);
			cs.setString(6, productosBean.getCodigo());
			cs.setInt(7, productosBean.getPrecio());
			cs.setString(8, cliente);
			cs.setString(9, agente);
			cs.registerOutParameter(10, Types.NUMERIC);
			cs.execute(); 

			valor = (cs.getObject(10).toString() != null) && (cs.getObject(10).toString() != "")  ?  Double.parseDouble(cs.getObject(10).toString()): 0;
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:traeDescuentoCombo error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_TRAE_DTO_PROMOCOMBO"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:traeDescuentoCombo cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:traeDescuentoCombo cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:traeDescuentoCombo error", e);
	     }
		}
	    System.out.println("UtilesDAOImpl:traeDescuentoCombo ==>"+valor);
		return valor;
	}
	public int valida_armazon_pcombo(String codigo)  throws Exception {
		log.info("UtilesDAOImpl:valida_armazon_pcombo inicio");
		int valor = Constantes.INT_CERO;
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	 log.info("UtilesDAOImpl:valida_armazon_pcombo conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	System.out.println("{call SP_VALIDA_ARM_COMBO("+codigo+",:z)}");
			cs = con.prepareCall("{call SP_VALIDA_ARM_COMBO(?,?)}");
			cs.setString(1, codigo);
			cs.registerOutParameter(2, Types.NUMERIC);
			cs.execute(); 
			
			valor = (cs.getObject(2).toString() != null) && (cs.getObject(2).toString() != "")  ?  Integer.parseInt(cs.getObject(2).toString()): 0;
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:valida_armazon_pcombo error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP: SP_VALIDA_ARM_COMBO"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:valida_armazon_pcombo cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:valida_armazon_pcombo cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:valida_armazon_pcombo error", e);
	     }
		}
		return valor;
	}
	
	
	public String valida_promo_pares(String codigo)  throws Exception {
		log.info("UtilesDAOImpl:valida_promo_pares inicio");
		String valor = "";
	    Connection con = null;
	    CallableStatement cs = null;
	     try {
	    	log.info("UtilesDAOImpl:valida_promo_pares conectando base datos");
	    	con = ConexionFactory.INSTANCE.getConexion();
	    	
	    	String cdg[] = codigo.split(",");  
	    	System.out.println("{call SP_VALIDA_PROMO_PARES("+cdg[0]+","+cdg[1]+",:z)}");
			cs = con.prepareCall("{call SP_VALIDA_PROMO_PARES(?,?,?)}");
			cs.setString(1, cdg[0]);
			cs.setString(2, cdg[1]);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute(); 
			
			valor = (String) ((cs.getObject(3).toString() != null) && (cs.getObject(3).toString() != "")  ?  cs.getObject(3).toString(): 0);
			
		} catch (Exception e) {
			log.error("UtilesDAOImpl:valida_armazon_pcombo error controlado",e);
	        throw new Exception("Error en DAO, al ejecutar SP:SP_VALIDA_PROMO_PARES"); 
		} finally {
	        try{
	         if (null != cs){
	        	 log.warn("UtilesDAOImpl:valida_promo_pares cierre CallableStatement");
	             cs.close();
	         }           
	         if (null != con){
	        	 log.warn("UtilesDAOImpl:valida_promo_pares cierre Connection");
		    	   con.close();
	           } 
	         
	     }catch(Exception e){
	    	 log.error("UtilesDAOImpl:valida_promo_pares error", e);
	     }
		}
		return valor;
	}
	
	
	 public ArrayList<ProductosBean> traeProductosGraduados(String ojo, String tipo_fam, String familia, String subfamilia, String grupo,
	           String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local,double cilindro,double esfera,int eje) throws Exception{
     {
	    	log.info("ProductosDAOImpl:traeProductosGraduados inicio");
	    	try {
	    		if (familia.equals(Constantes.STRING_CERO)){familia = null;}
	            if (subfamilia.equals(Constantes.STRING_CERO)){subfamilia = null;}
	            if (grupo.equals(Constantes.STRING_CERO)){grupo = null;}
			} catch (Exception e) {
				log.error("ProductosDAOImpl:traeProductosGraduados error controlado",e);
			}
			
			/*if (ojo.equals(Constantes.STRING_DERECHO)) {
				cilindro = graduacion.getOD_cilindro_traspuesto();
				esfera = graduacion.getOD_esfera_traspuesto();
				eje = graduacion.getOD_eje_traspuesto();
			}
			if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
				cilindro = graduacion.getOI_cilindro_traspuesto();
				esfera = graduacion.getOI_esfera_traspuesto();
				eje = graduacion.getOI_eje_traspuesto();
			}*/
			
	    	
	    	ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
	        Connection con = null;
	        ResultSet rs = null;
	        CallableStatement st = null;
	        
	        try {
	        	log.info("ProductosDAOImpl:traeProductosGraduados conectando base datos");
	            con = ConexionFactory.INSTANCE.getConexion();
	            //System.out.println("BUSQUEDA CRISTALES => SP_BUSCAR_SEL_ART_CRISTALES("+familia+"|"+subfamilia+"|"+grupo+"|"+local+"|"+codigoBusqueda+"|"+codigoBarraBusqueda+"|"+tipo_fam+"|"+cilindro+"|"+esfera+"|"+eje+",:z);");
	            System.out.println("BUSQUEDA CRISTALES => SP_BUSCAR_SEL_ART_CRISTALES_AU('"+familia+"','"+subfamilia+"','"+grupo+"','"+local+"','"+codigoBusqueda+"','"+codigoBarraBusqueda+"','"+tipo_fam+"',"+cilindro+","+esfera+","+eje+",:z);");
	            String sql = "{call SP_BUSCAR_SEL_ART_CRISTALES_AU(?,?,?,?,?,?,?,?,?,?,?)}";
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
	            	System.out.println("CODIGOBARRAS ==>"+rs.getString("CODIGOBARRAS"));
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
	 
	 
	public String valida_usuario_vp(String encargo,String nif,int monto)  throws Exception {
			log.info("UtilesDAOImpl:valida_usuario_vp inicio");
			String valor = "";
		    Connection con = null;
		    CallableStatement cs = null;
		     try {
		    	log.info("UtilesDAOImpl:valida_usuario_vpconectando base datos");
		    	con = ConexionFactory.INSTANCE.getConexion();
		    	
		    	System.out.println("{call SP_VALIDA_USER_VP("+encargo+","+nif+","+monto+",:z)}");
				cs = con.prepareCall("{call SP_VALIDA_USER_VP(?,?,?,?)}");
				cs.setString(1, encargo);
				cs.setString(2,nif);
				cs.setInt(3,monto);
				cs.registerOutParameter(4, Types.VARCHAR);
				cs.execute(); 
				
				valor = (cs.getObject(4).toString() != null) && (cs.getObject(4).toString() != "")  ? cs.getObject(4).toString(): "0_0";
				
			} catch (Exception e) {
				log.error("UtilesDAOImpl:traeDescuentoCupon error controlado",e);
		        throw new Exception("Error en DAO, alSP_VALIDA_CUPON ejecutar SP: SP_VALIDA_USUARIO_VP"); 
			} finally {
		        try{
		         if (null != cs){
		        	 log.warn("UtilesDAOImpl:valida_usuario_vp cierre CallableStatement");
		             cs.close();
		         }           
		         if (null != con){
		        	 log.warn("UtilesDAOImpl:valida_usuario_vp cierre Connection");
			    	   con.close();
		           } 
		         
		     }catch(Exception e){
		    	 log.error("UtilesDAOImpl:valida_cupon error", e);
		     }
			}
			return valor;
	 }
	
}
