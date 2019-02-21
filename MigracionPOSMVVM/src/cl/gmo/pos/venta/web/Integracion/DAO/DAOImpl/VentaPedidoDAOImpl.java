/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.VentaPedidoDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

/**
 *
 * @author Advice70
 */
public class VentaPedidoDAOImpl implements VentaPedidoDAO{
	Logger log = Logger.getLogger( this.getClass() );
    public VentaPedidoDAOImpl(){}
    
    
    public ArrayList<SuplementopedidoBean> traeSuplementosPedidos(String codigo) throws Exception 
	{
    	log.info("VentaPedidoDAOImpl:traeSuplementosPedidos inicio");
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		ArrayList<SuplementopedidoBean> listaSuplementoPedido = new ArrayList<SuplementopedidoBean>();
		
		
		try {
			log.info("VentaPedidoDAOImpl:traeSuplementosPedidos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_PEDIDO_TRATAMI(?,?)}";
			st = con.prepareCall(sql);
			if(null != codigo){
				st.setInt(1, Integer.parseInt(codigo));
			}else{
				st.setInt(1, 0);
			}
			
			st.registerOutParameter(2, OracleTypes.CURSOR);
			
			st.execute();
			
			rs = (ResultSet)st.getObject(2);
			
			while (rs.next()) {
				log.debug("VentaPedidoDAOImpl:traeSuplementosPedidos entrando ciclo while");
				SuplementopedidoBean bean = new SuplementopedidoBean();
				
				bean.setIdent(Integer.parseInt(codigo));				
				bean.setTratami(rs.getString("TRATAMI"));
				bean.setValor(rs.getString("VALOR"));
				bean.setDescripcion(rs.getString("DESCRIPCION"));
				
				
				listaSuplementoPedido.add(bean);
			}
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:traeSuplementosPedidos error controlado",e);
            throw new Exception("Error en DAO, SP_LISTA_DETALLE_SUPLE_LIBERA"); 
        }finally {
            try{
            	if (null != rs){
            		log.warn("VentaPedidoDAOImpl:traeSuplementosPedidos cierre ResultSet");
                	rs.close();
                } 
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeSuplementosPedidos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeSuplementosPedidos cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeSuplementosPedidos error", e);
            }
        }
        
        return listaSuplementoPedido;
	}
    
    public int traeCodigoVenta(String local) throws Exception
    {
    	log.info("VentaPedidoDAOImpl:traeCodigoVenta inicio");
        int codigo = Constantes.INT_CERO;
        Connection con = null;
        CallableStatement st = null;
        
        try {
        	log.info("VentaPedidoDAOImpl:traeCodigoVenta conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_VTA_PEDI_SEL_COD_PEDIDO(?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, local);
            st.registerOutParameter(2, OracleTypes.NUMERIC);
            
            st.execute();
            codigo = Integer.parseInt(st.getObject(2).toString());
        } catch (Exception e) {
        	log.error("VentaPedidoDAOImpl:traeCodigoVenta error controlado",e);
            throw new Exception("Error en DAO, SP_VTA_PEDI_SEL_COD_PEDIDO"); 
        }finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeCodigoVenta cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeCodigoVenta cierre Connection");
 		    	   con.close();
 	           	}
                
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeCodigoVenta error", e);
            }
        }
        return codigo;
        
    }

	public String traeCodigo_Suc(String local)  throws Exception{
		log.info("VentaPedidoDAOImpl:traeCodigo_Suc inicio");
		 String codigo = Constantes.STRING_BLANCO;
	        Connection con = null;
	        CallableStatement st = null;
	        
	        try {
	        	log.info("VentaPedidoDAOImpl:traeCodigo_Suc conectando base datos");
	            con = ConexionFactory.INSTANCE.getConexion();
	            String sql = "{call SP_VTA_PEDI_SEL_ENC_TICKET(?,?)}";
	            st = con.prepareCall(sql);
	            st.setString(1, local);
	            st.registerOutParameter(2, OracleTypes.VARCHAR);
	            
	            st.execute();
	            codigo = st.getObject(2).toString();
	        } catch (Exception e) {
	        	log.error("VentaPedidoDAOImpl:traeCodigo_Suc error controlado",e);
	            throw new Exception("Error en DAO, SP_VTA_PEDI_SEL_ENC_TICKET"); 
	        }finally {
	            try{
	                if (null != st){
	                	log.warn("VentaPedidoDAOImpl:traeCodigo_Suc cierre CallableStatement");
	                	st.close();
	                }              
	                if (null != con){
	                	log.warn("VentaPedidoDAOImpl:traeCodigo_Suc cierre Connection");
	 		    	   con.close();
	 	           } 
	            }catch(Exception e){
	            	log.error("VentaPedidoDAOImpl:traeCodigo_Suc error", e);
	            }
	        }
	        return codigo;
	}

	public VentaPedidoBean traeGenericosFormulario(String local) throws Exception {
		log.info("VentaPedidoDAOImpl:traeGenericosFormulario inicio");
		 	VentaPedidoBean bean = new VentaPedidoBean();
	        Connection con = null;
	        CallableStatement st = null;
	        
	        try {
	        	log.info("VentaPedidoDAOImpl:traeGenericosFormulario conectando base datos");
	            con = ConexionFactory.INSTANCE.getConexion();
	            String sql = "{call SP_VTA_PEDI_SEL_GENERICOS(?,?,?,?,?,?,?,?,?)}";
	            st = con.prepareCall(sql);
	            
	            st.setString(1, local);
	            st.registerOutParameter(2, OracleTypes.VARCHAR);
	            st.registerOutParameter(3, OracleTypes.VARCHAR);
	            st.registerOutParameter(4, OracleTypes.VARCHAR);
	            st.registerOutParameter(5, OracleTypes.NUMBER);
	            st.registerOutParameter(6, OracleTypes.NUMBER);
	            st.registerOutParameter(7, OracleTypes.NUMBER);
	            st.registerOutParameter(8, OracleTypes.VARCHAR);
	            st.registerOutParameter(9, OracleTypes.NUMBER);
	            st.execute();
	            
	            bean.setIdioma(st.getObject(2).toString());
	            bean.setDivisa(st.getObject(3).toString());
	            bean.setForma_pago(st.getObject(4).toString());
	            bean.setCambio(1);
	            bean.setPorcentaje_descuento_maximo(st.getInt(5));
	            bean.setPorcentaje_anticipo(st.getInt(6));
	            bean.setNumero_caja(st.getInt(7));
	            bean.setFenix(st.getString(8));
	            bean.setFtaller(st.getInt(9));
	            if (Constantes.STRING_S.equals(bean.getFenix())) {
					bean.setFenix(Constantes.STRING_TRUE);
				}
	            else
	            {
	            	bean.setFenix(Constantes.STRING_FALSE);
	            }

	        } catch (Exception e) {
	        	log.error("VentaPedidoDAOImpl:traeGenericosFormulario error controlado",e);
	            throw new Exception("Error en DAO, SP_VTA_PEDI_SEL_GENERICOS"); 
	        }finally {
	            try{
	                if (null != st){
	                	log.warn("VentaPedidoDAOImpl:traeGenericosFormulario cierre CallableStatement");
	                	st.close();
	                }              
	                if (null != con){
	                	log.warn("VentaPedidoDAOImpl:traeGenericosFormulario cierre Connection");
	 		    	   con.close();
	 	           } 
	            }catch(Exception e){
	            	log.error("VentaPedidoDAOImpl:traeGenericosFormulario error", e);
	            }
	        }
	        return bean;
	}

	public ArrayList<TipoPedidoBean> traeTiposPedidos() throws Exception 
	{
		log.info("VentaPedidoDAOImpl:traeTiposPedidos inicio");
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		ArrayList<TipoPedidoBean> listaTipoPedido = new ArrayList<TipoPedidoBean>();
		
		
		try {
			log.info("VentaPedidoDAOImpl:traeTiposPedidos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_VTA_PEDI_SEL_TIPOS_PEDIDOS(?)}";
			st = con.prepareCall(sql);
			st.registerOutParameter(1, OracleTypes.CURSOR);
			
			st.execute();
			
			rs = (ResultSet)st.getObject(1);
			
			while (rs.next()) {
				log.debug("VentaPedidoDAOImpl:traeTiposPedidos entrando ciclo while");
				TipoPedidoBean bean = new TipoPedidoBean();
				bean.setCodigo(rs.getString("CDG"));
				bean.setDescripcion(rs.getString("DESCRIPCION"));
				
				listaTipoPedido.add(bean);
			}
			
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:traeTiposPedidos error controlado",e);
            throw new Exception("Error en DAO, SP_VTA_PEDI_SEL_TIPOS_PEDIDOS"); 
        }finally {
            try{
            	if (null != rs){
            		log.warn("VentaPedidoDAOImpl:traeTiposPedidos cierre ResultSet");
                	rs.close();
                } 
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeTiposPedidos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeTiposPedidos cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeTiposPedidos error", e);
            }
        }
        
        return listaTipoPedido;
	}
	
	
	
	public ArrayList<VentaPedidoBean> traeDetalleVentasPedidos(String codigo, String grupo){
		
		log.info("VentaPedidoDAOImpl:traeDetalleVentasPedidos inicio");
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		Utils util = new Utils();
		ArrayList<VentaPedidoBean> lista_ventas = new ArrayList<VentaPedidoBean>();
		
		try{
			log.info("VentaPedidoDAOImpl:traeDetalleVentasPedidos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("SP_LISTA_DETALLE_VTA_PEDIDO => codigo==> "+codigo+" grupo ==>"+grupo);
			String sql = "{call SP_LISTA_DETALLE_VTA_PEDIDO(?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, codigo);
			st.setInt(2, Integer.parseInt(grupo));
			st.registerOutParameter(3, OracleTypes.CURSOR);
			
			st.execute();			
			rs = (ResultSet)st.getObject(3);
			
			VentaPedidoBean venta;
			
			while(rs.next()){
				log.debug("VentaPedidoDAOImpl:traeDetalleVentasPedidos entrando ciclo while");
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
				
				lista_ventas.add(venta);
			}			
			
			return lista_ventas;
			
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:traeDetalleVentasPedidos error controlado",ex);
			return lista_ventas;
			
		}finally {
            try{
            	if (null != rs){
            		log.warn("VentaPedidoDAOImpl:traeDetalleVentasPedidos cierre ResultSet");
                	rs.close();
                } 
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeDetalleVentasPedidos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeDetalleVentasPedidos cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeDetalleVentasPedidos error", e);
            }
        }
		

		
	}

	
	public ArrayList<VentaPedidoBean> traeVentasPedidos(String local, String fechaLib, String fechaHasta,String estado){
		log.info("VentaPedidoDAOImpl:traeVentasPedidos inicio");
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		ResultSet rsLentillas = null;
		ArrayList<VentaPedidoBean> lista_ventas = new ArrayList<VentaPedidoBean>();
		
		try{
			log.info("VentaPedidoDAOImpl:traeVentasPedidos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			//String sql = "{ call SP_LISTA_LIB_VTA_PEDIDO_OR(?,?,?,?,?,?,?)}";
			String sql = "{ call SP_LISTA_LIB_VTA_PEDIDO_OR(?,?,?,?,?,?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, local);
			st.registerOutParameter(2, OracleTypes.CURSOR);
			st.setString(3, fechaLib);
			st.setString(4, fechaHasta);
			st.setString(5, estado);
			st.registerOutParameter(6, OracleTypes.CURSOR);
			st.registerOutParameter(7, OracleTypes.INTEGER);
			st.registerOutParameter(8, OracleTypes.VARCHAR);
			st.execute();
			
			String mensaje = st.getString(8);
			int codigo = st.getInt(7);
			
			rs = (ResultSet)st.getObject(2);
			rsLentillas = (ResultSet)st.getObject(6);
			
			VentaPedidoBean vtaped;
			
			while(rs.next()){
				log.debug("VentaPedidoDAOImpl:traeVentasPedidos entrando ciclo while");
				vtaped = new VentaPedidoBean();
				
				vtaped.setCod_lista_lib(rs.getString("CODIGO"));
				
				
				if(!(Constantes.STRING_BLANCO.equals(rs.getString("FECHAPEDIDO"))) && null != rs.getString("FECHAPEDIDO")){
					
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_FECHA_ANO_MES_DIA);
					Date fecha = null;
					fecha = formatoDelTexto.parse(rs.getString("FECHAPEDIDO"));
					formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
					vtaped.setFecha(formatoDelTexto.format(fecha));				
				}
				
				
				if(!(Constantes.STRING_BLANCO.equals(rs.getString("FECHAENTREGA"))) && null != rs.getString("FECHAENTREGA")){
					
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_FECHA_ANO_MES_DIA);
					Date fecha = null;
					fecha = formatoDelTexto.parse(rs.getString("FECHAENTREGA"));
					formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
					vtaped.setFecha_entrega(formatoDelTexto.format(fecha));				
				}
				
				vtaped.setAgente(rs.getString("AGENTE"));
				vtaped.setCliente(rs.getString("CLIENTE") + " "+rs.getString("APELLIDO"));
				
				vtaped.setId_cliente(rs.getString("CODCLIENTE"));
				
				if(null != rs.getString("GRUPO")){
					vtaped.setGrupo(rs.getString("GRUPO"));
				}else{
					vtaped.setGrupo(Constantes.STRING_CERO);
				}
				
				
				vtaped.setHora(rs.getString("HORA"));
				if (rs.getInt("LENTILLA") == 0) {
					vtaped.setModifica_mostrar(Constantes.STRING_FALSE);
					vtaped.setSin_cdd(false);
					vtaped.setCantidad_cdd(0);
					vtaped.setCantidad_lentillas(0);
				}
				else
				{
					vtaped.setCantidad_lentillas(rs.getInt("LENTILLA"));
					vtaped.setModifica_mostrar(Constantes.STRING_TRUE);
				}
				
				vtaped.setLinea(-1);
				
				vtaped.setChecked(false);
				
				if(!(Constantes.STRING_CERO.equals(vtaped.getGrupo())) || (rs.getInt("LENTILLA") > 0)){
					lista_ventas.add(vtaped);
				}
			}	
			
			while(rsLentillas.next()){
				log.debug("VentaPedidoDAOImpl:traeVentasPedidos entrando ciclo while");
				vtaped = new VentaPedidoBean();
				
				vtaped.setCod_lista_lib(rsLentillas.getString("CODIGO"));
				
				
				if(!(Constantes.STRING_BLANCO.equals(rsLentillas.getString("FECHAPEDIDO"))) && null != rsLentillas.getString("FECHAPEDIDO")){
					
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_FECHA_ANO_MES_DIA);
					Date fecha = null;
					fecha = formatoDelTexto.parse(rsLentillas.getString("FECHAPEDIDO"));
					formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
					vtaped.setFecha(formatoDelTexto.format(fecha));				
				}
				
				
				if(!(Constantes.STRING_BLANCO.equals(rsLentillas.getString("FECHAENTREGA"))) && null != rsLentillas.getString("FECHAENTREGA")){
					
					SimpleDateFormat formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_FECHA_ANO_MES_DIA);
					Date fecha = null;
					fecha = formatoDelTexto.parse(rsLentillas.getString("FECHAENTREGA"));
					formatoDelTexto = new SimpleDateFormat(Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
					vtaped.setFecha_entrega(formatoDelTexto.format(fecha));				
				}
				
				vtaped.setAgente(rsLentillas.getString("AGENTE"));
				vtaped.setCliente(rsLentillas.getString("CLIENTE") + " "+rsLentillas.getString("APELLIDO"));
				
				vtaped.setId_cliente(rsLentillas.getString("CODCLIENTE"));
				
				if(null != rsLentillas.getString("GRUPO")){
					vtaped.setGrupo(rsLentillas.getString("GRUPO"));
				}else{
					vtaped.setGrupo(Constantes.STRING_CERO);
				}
				
				
				vtaped.setHora(rsLentillas.getString("HORA"));
				if (rsLentillas.getInt("LENTILLA") == 0) {
					vtaped.setModifica_mostrar(Constantes.STRING_FALSE);
					vtaped.setSin_cdd(false);
					vtaped.setCantidad_cdd(0);
					vtaped.setCantidad_lentillas(0);
				}
				else
				{
					vtaped.setCantidad_lentillas(rsLentillas.getInt("LENTILLA"));
					vtaped.setModifica_mostrar(Constantes.STRING_TRUE);
				}
				
				vtaped.setLinea(rsLentillas.getInt("LINEA"));
				vtaped.setTipo_familia(rsLentillas.getString("TIPOFAM"));
				vtaped.setChecked(false);
				
				//if(!(Constantes.STRING_CERO.equals(vtaped.getGrupo())) || (rsLentillas.getInt("LENTILLA") > 0)){
				lista_ventas.add(vtaped);
				//}
			}
			
			return lista_ventas;
			
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:traeVentasPedidos error controlado",ex);
			return lista_ventas;
		}catch(ParseException ex){
			log.error("VentaPedidoDAOImpl:traeVentasPedidos error controlado",ex);
			return lista_ventas;
		}finally {
            try{
            	if (null != rs){
            		log.warn("VentaPedidoDAOImpl:traeVentasPedidos cierre ResultSet");
                	rs.close();
                } 
            	if (null != rsLentillas){
            		log.warn("VentaPedidoDAOImpl:traeVentasPedidos cierre ResultSet");
            		rsLentillas.close();
                } 
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeVentasPedidos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeVentasPedidos cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeVentasPedidos error", e);
            }
        }
		
		
	}
	
	
	public ArrayList<VentaPedidoBean> traeDetalleLiberacionMulti(String codigo, String grupo){
		
		log.info("VentaPedidoDAOImpl:traeDetalleLiberacionMulti inicio");
		Connection con = null;
		CallableStatement st = null;
		ResultSet rs = null;
		ArrayList<VentaPedidoBean> lista_ventas = new ArrayList<VentaPedidoBean>();
		
		try{
			log.info("VentaPedidoDAOImpl:traeDetalleLiberacionMulti conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			
			System.out.println("SP_LISTA_DETALLE_VTA_PED_MULTI  codigon ==>"+codigo+"+ grupo ==>"+grupo);
			
			String sql = "{call SP_LISTA_DETALLE_VTA_PED_MULTI(?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, codigo);
			st.registerOutParameter(2, OracleTypes.CURSOR);
			
			st.execute();			
			rs = (ResultSet)st.getObject(2);
			
			VentaPedidoBean venta;
			
			while(rs.next()){
				log.debug("VentaPedidoDAOImpl:traeDetalleLiberacionMulti entrando ciclo while");
				venta = new VentaPedidoBean();
				
				venta.setCodigo(rs.getInt("IDENT"));
				venta.setCod_detalle_vta(rs.getString("ALBVTCB"));			
				venta.setCod_lista_lib(rs.getString("CDG"));				
				venta.setLinea(rs.getInt("LINEA"));
				venta.setCod_montacb(rs.getString("MONTACB"));
				venta.setArticulo(rs.getString("ARTISUBP"));
				venta.setCantidad(rs.getInt("CANTIDAD"));
				venta.setOjo(rs.getString("OJO"));
				venta.setEsfera(rs.getDouble("ESFERA"));
				venta.setCilindro(rs.getInt("CILINDRO"));
				venta.setDiametro(rs.getDouble("DIAMETRO"));
				venta.setEje(rs.getDouble("EJE"));
				venta.setGrupo(rs.getString("GRUPO"));
				venta.setUnahora(rs.getString("UNAHORA"));
				
				lista_ventas.add(venta);
			}			
			
			return lista_ventas;
			
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:traeDetalleLiberacionMulti error controlado",ex);
			return lista_ventas;
			
		}finally {
            try{
            	if (null != rs){
            		log.warn("VentaPedidoDAOImpl:traeDetalleLiberacionMulti cierre ResultSet");
                	rs.close();
                } 
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeDetalleLiberacionMulti cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeDetalleLiberacionMulti cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeDetalleLiberacionMulti error", e);
            }
        }
		

		
	}

	
	public GraduacionesBean traeGraduacionPedido(String cliente, String fecha, Double numero)throws Exception
	{
		
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		GraduacionesBean graduacion = new GraduacionesBean();
		
		try {
			log.info("VentaPedidoDAOImpl:traeGraduacionPedido conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_GRAD_SEL_GRADUACION_PED(?,?,?,?)}");
			cs.setString(1, cliente);
			cs.setString(2, fecha);
			cs.setDouble(3, numero);
			
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(4);
			while (rs.next()) {
				log.debug("VentaPedidoDAOImpl:traeGraduacionPedido entrando ciclo while");
				graduacion.setCodigo(0);
				graduacion.setAgente(rs.getString("AGENTE"));
				graduacion.setFecha(util.formatoFecha((rs.getDate("FECHA"))));
				graduacion.setDoctor(rs.getString("DOCTOR"));
				graduacion.setCod_doctor(rs.getString("CODDOCTOR"));				
				graduacion.setNumero(rs.getInt("NUMERO"));
				graduacion.setOrden(Constantes.STRING_BLANCO);
				
				if(null != rs.getString("ODADICION")){
					graduacion.setOD_adicion(rs.getDouble("ODADICION"));
				}else{
					graduacion.setOD_adicion(null);
				}
				
				if(null != rs.getString("ODCILINDRO")){
					graduacion.setOD_cilindro(rs.getDouble("ODCILINDRO"));
				}else{
					graduacion.setOD_cilindro(null);
				}
				
				if(null != rs.getString("ODEJE")){
					graduacion.setOD_eje(rs.getInt("ODEJE"));
				}else{
					graduacion.setOD_eje(null);
				}
				
				if(null !=rs.getString("ODESFERA")){
					graduacion.setOD_esfera(rs.getDouble("ODESFERA"));
				}else{
					graduacion.setOD_esfera(null);
				}
				
				graduacion.setOD_diametro(0.0);
				
				if(null != rs.getString("ODESFERAC")){
					graduacion.setOD_esfera_cerca(rs.getDouble("ODESFERAC"));
				}else{
					graduacion.setOD_esfera_cerca(null);
				}
				
				if(null != rs.getString("ODNP1")){
					graduacion.setOD_n(rs.getDouble("ODNP1"));
				}else{
					graduacion.setOD_n(null);
				}
				
				if(null != rs.getString("ODNP2")){
					graduacion.setOD_p(rs.getDouble("ODNP2"));
				}else{
					graduacion.setOD_p(null);
				}
				
				if(null != rs.getString("ODAVSC")){
					graduacion.setOD_avsc(rs.getDouble("ODAVSC"));
				}else{
					graduacion.setOD_avsc(null);
				}
				
				if(null != rs.getString("ODAVCC")){
					graduacion.setOD_avcc(rs.getDouble("ODAVCC"));
				}else{
					graduacion.setOD_avcc(null);
				}				
				graduacion.setOD_obser(rs.getString("ODOBSER"));
				graduacion.setOD_cantidad(String.valueOf(rs.getInt("ODCANTIDAD")));
				graduacion.setOD_base(rs.getString("ODBASE"));
				
				graduacion.setOD_altura(rs.getString("ODALTURA"));
				
				if(null !=rs.getString("OIADICION")){
					graduacion.setOI_adicion(rs.getDouble("OIADICION"));
				}else{
					graduacion.setOI_adicion(null);
				}
				
				if(null != rs.getString("OICILINDRO")){
					graduacion.setOI_cilindro(rs.getDouble("OICILINDRO"));
				}else{
					graduacion.setOI_cilindro(null);
				}
				
				if(null !=rs.getString("OIEJE")){
					graduacion.setOI_eje(rs.getInt("OIEJE"));
				}else{
					graduacion.setOI_eje(null);
				}
				
				
				if(null != rs.getString("OIESFERA")){
					graduacion.setOI_esfera(rs.getDouble("OIESFERA"));
				}else{
					graduacion.setOI_esfera(null);
				}
				
				
				graduacion.setOI_diametro(0.0);
				
				if(null != rs.getString("OIESFERAC")){
					graduacion.setOI_esfera_cerca(rs.getDouble("OIESFERAC"));
				}else{
					graduacion.setOI_esfera_cerca(null);
				}
				
				if(null != rs.getString("OINP1")){
					graduacion.setOI_n(rs.getDouble("OINP1"));
				}else{
					graduacion.setOI_n(null);
				}					
					
				if(null != rs.getString("OINP2")){
					graduacion.setOI_p(rs.getDouble("OINP2"));
				}else{
					graduacion.setOI_p(null);
				}
				
				if(null !=rs.getString("OIAVSC")){
					graduacion.setOI_avsc(rs.getDouble("OIAVSC"));
				}else{
					graduacion.setOI_avsc(null);
				}
				
				if(null !=rs.getString("OIAVCC")){
					graduacion.setOI_avcc(rs.getDouble("OIAVCC"));
				}else{
					graduacion.setOI_avcc(null);
				}
				
				
				graduacion.setOI_obser(rs.getString("OIOBSERVA"));
				graduacion.setOI_cantidad(String.valueOf(rs.getInt("OICANTIDAD")));
				graduacion.setOI_base(rs.getString("OIBASE"));
				graduacion.setOI_altura(rs.getString("OIALTURA"));
				graduacion.setTipo(rs.getString("TIPO"));
				graduacion.setDiferente_add(rs.getBoolean("DIFADICION"));
				graduacion.setFecha_emision(util.formatoFecha((rs.getDate("FECEMI"))));
				graduacion.setFecha_prox_revision(util.formatoFecha((rs.getDate("FECREV"))));
			}
			return graduacion;
			
		} catch(SQLException e){  
			log.error("VentaPedidoDAOImpl:traeGraduacionPedido error controlado",e);
	        throw new Exception("Error en DAO: SP_GRAD_SEL_GRADUACION_PED");
	    }finally{
	        try{
	            if (null != rs){
	            	log.warn("VentaPedidoDAOImpl:traeGraduacionPedido cierre ResultSet");
	                rs.close();
	            }
	            if (null != cs){
	            	log.warn("VentaPedidoDAOImpl:traeGraduacionPedido cierre CallableStatement");
	                cs.close();
	            }
	            if (null != conn ){
	            	log.warn("VentaPedidoDAOImpl:traeGraduacionPedido cierre Connection");
	            	conn.close();
	            } 
	        }catch(SQLException e){
	        	log.error("VentaPedidoDAOImpl:traeGraduacionPedido error", e);
	        	throw new Exception("Error en DAO al Cerrar conexion en traeUltimaGraduacionCliente()");
	        }            
	    }
	    
	}


	/*
	 * MODIFICACI�N LMARIN 20130913
	 * SOLO LE PASO CRISTALES AL PROCEDIMIENTO ALAMACENADO
	 */
	public Date traeFechaEntrega(String fecha, String local, String familia,
			String subFamilia, String grupoFamilia, String tipo,double esfera,double cilindro) throws Exception{
		log.info("VentaPedidoDAOImpl:traeFechaEntrega inicio");
		Connection con = null;
		CallableStatement st = null;
		Date fecha_entrega = new Date();
		try{
			log.info("VentaPedidoDAOImpl:traeFechaEntrega conectando base datos");
			
			con = ConexionFactory.INSTANCE.getConexion();
			
			/*
			 * LMARIN 20131014			
			 */
			
			if(tipo.equals("C")){
				String sql = "{call SP_VTA_PEDI_SEL_FECHA_PRI(?,?,?,?,?,?,?,?,?)}";
				st = con.prepareCall(sql);
				st.setString(1, local);
				st.setString(2, tipo);
				st.setString(3, familia);
				st.setString(4, subFamilia);
				st.setString(5, grupoFamilia);
				st.setString(6, fecha);
				st.setDouble(7,esfera);
				st.setDouble(8,cilindro);
				st.registerOutParameter(9, Types.DATE);			
				st.execute();
				fecha_entrega = st.getDate(9);
				//System.out.println(local+","+tipo+","+familia+","+subFamilia+","+grupoFamilia+","+fecha+","+esfera+","+cilindro+"==>"+fecha_entrega.toString());
			}
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:traeFechaEntrega error controlado 2013032013",ex);
			throw new Exception("Error en DAO: SP_VTA_PEDI_SEL_FECHA_PRI");
		}finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeFechaEntrega cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeFechaEntrega cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeFechaEntrega error", e);
            }
        }
		System.out.println("FECHA ENTREGA DAOIMPL==>"+fecha_entrega);
		return fecha_entrega;
	}

	
	/*public Date traeFechaEntrega(String fecha, String local, String familia,
			String subFamilia, String grupoFamilia, String tipo,double esfera,double cilindro) throws Exception{
		log.info("VentaPedidoDAOImpl:traeFechaEntrega inicio");
		Connection con = null;
		CallableStatement st = null;
		Date fecha_entrega = new Date();
		try{
			log.info("VentaPedidoDAOImpl:traeFechaEntrega conectando base datos");
			
			con = ConexionFactory.INSTANCE.getConexion();
			
			/*LMARIN 20130913*/
			/*String sql = "{call SP_VTA_PEDI_SEL_FECHA_PRI(?,?,?,?,?,?,?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, local);
			st.setString(2, tipo);
			st.setString(3, familia);
			st.setString(4, subFamilia);
			st.setString(5, grupoFamilia);
			st.setString(6, fecha);
			st.setDouble(7,esfera);
			st.setDouble(8,cilindro);
			st.registerOutParameter(9, Types.DATE);			
			st.execute();
			fecha_entrega = st.getDate(9);
			System.out.println(local+","+tipo+","+familia+","+subFamilia+","+grupoFamilia+","+fecha+","+esfera+","+cilindro+"==>"+fecha_entrega.toString());
		
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:traeFechaEntrega error controlado 2013032013",ex);
			throw new Exception("Error en DAO: SP_VTA_PEDI_SEL_FECHA_PRI");
		}finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeFechaEntrega cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeFechaEntrega cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeFechaEntrega error", e);
            }
        }
		
		return fecha_entrega;
	}*/


	public Date traeFechaEntregaExepcionFeriados(Date fecha_ini, String fecha_encargo) throws Exception {
		log.info("VentaPedidoDAOImpl:traeFechaEntregaExepcionFeriados inicio");
		Connection con = null;
		CallableStatement st = null;
		Date fecha_entrega = new Date();
		Utils util = new Utils();
		
		String fecha_f = util.formatoFecha(fecha_ini);
		String fecha_i = fecha_encargo;
		try{
			log.info("VentaPedidoDAOImpl:traeFechaEntregaExepcionFeriados conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_VTA_PEDI_SEL_FECHA_ENT(?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, fecha_i);
			st.setString(2, fecha_f);
			st.registerOutParameter(3, Types.DATE);
			
			st.execute();			
			fecha_entrega = st.getDate(3);
			
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new Exception("Error en DAO: SP_VTA_PEDI_SEL_FECHA_ENT");
		}finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:traeFechaEntregaExepcionFeriados cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:traeFechaEntregaExepcionFeriados cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:traeFechaEntregaExepcionFeriados error", e);
            }
        }
		
		return fecha_entrega;
	}

	public void insertaPedido(VentaPedidoBean ped, String local) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaPedido inicio");
		Connection con = null;
		CallableStatement st = null;
		
		try{
			log.info("VentaPedidoDAOImpl:insertaPedido conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_VTA_PEDI_INS_CB1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, ped.getCdg());
			st.setString(2, ped.getSerie());
			st.setInt(3, ped.getNumero());
			st.setInt(4, Integer.parseInt(ped.getId_cliente()));
			st.setString(5, ped.getId_agente());
			st.setString(6, ped.getDivisa());
			st.setString(7, ped.getIdioma());
			st.setInt(8, ped.getDto());
			st.setString(9, ped.getFecha());
			st.setString(10, ped.getFecha_entrega());
			st.setString(11, ped.getForma_pago());
			st.setInt(12, ped.getCambio());
			st.setString(13, ped.getNumero_sobre());
			st.setDouble(14, ped.getAnticipo());
			st.setString(15, ped.getNotas());
			st.setString(16, ped.getCerrado());
			st.setString(17, ped.getRetenido());
			st.setString(18, ped.getId_agente());
			st.setString(19, ped.getTipo_ped());
			st.setString(20, ped.getEnuso());
			if (null == ped.getTipo_ped_2() || "0".equals(ped.getTipo_ped_2())  || "".equals(ped.getTipo_ped_2())) {
				st.setString(21, null);
			}
			else
			{
				st.setString(21, ped.getTipo_ped_2());
			}
			
			st.setString(22, ped.getFinalizado());
			st.setString(23, ped.getHora());
			st.setDouble(24, ped.getAnticipo_total());
			st.setDouble(25, ped.getAnticipo_def());
			st.setDouble(26, ped.getAnticipo_total_def());
			if (Constantes.STRING_CERO.equals(ped.getConvenio()) || Constantes.STRING_BLANCO.equals(ped.getConvenio())) {
				st.setString(27, null);
			}
			else
			{
				st.setString(27, ped.getConvenio());
			}
			
			st.setString(28, ped.getAnulado());
			st.setString(29, ped.getDescargado());
			st.setString(30, ped.getId_promocion());
			st.setString(31, ped.getSupervisor());
			st.setString(32, local);
			st.setString(33, ped.getPedvtant());
			st.registerOutParameter(34, Types.NUMERIC);
			st.setString(35, ped.getCliente_dto());
			st.setString(36, ped.getVenta_seguro());
			
			st.execute();
			ped.setNumero(st.getInt(34));
			
			
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:insertaPedido error controlado",ex);
			throw new Exception("Error en DAO: SP_VTA_PEDI_INS_CB");
		}finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:insertaPedido cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:insertaPedido cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:insertaPedido error", e);

            }
        }
		
		
	}

	/*public void insertaPedido(VentaPedidoBean ped, String local) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaPedido inicio");
		Connection con = null;
		CallableStatement st = null;
		
		try{
			log.info("VentaPedidoDAOImpl:insertaPedido conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_VTA_PEDI_INS_CB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, ped.getCdg());
			st.setString(2, ped.getSerie());
			st.setInt(3, ped.getNumero());
			st.setInt(4, Integer.parseInt(ped.getId_cliente()));
			st.setString(5, ped.getId_agente());
			st.setString(6, ped.getDivisa());
			st.setString(7, ped.getIdioma());
			st.setInt(8, ped.getDto());
			st.setString(9, ped.getFecha());
			st.setString(10, ped.getFecha_entrega());
			st.setString(11, ped.getForma_pago());
			st.setInt(12, ped.getCambio());
			st.setString(13, ped.getNumero_sobre());
			st.setDouble(14, ped.getAnticipo());
			st.setString(15, ped.getNotas());
			st.setString(16, ped.getCerrado());
			st.setString(17, ped.getRetenido());
			st.setString(18, ped.getId_agente());
			st.setString(19, ped.getTipo_ped());
			st.setString(20, ped.getEnuso());
			if (null == ped.getTipo_ped_2() || "0".equals(ped.getTipo_ped_2())  || "".equals(ped.getTipo_ped_2())) {
				st.setString(21, null);
			}
			else
			{
				st.setString(21, ped.getTipo_ped_2());
			}
			
			st.setString(22, ped.getFinalizado());
			st.setString(23, ped.getHora());
			st.setDouble(24, ped.getAnticipo_total());
			st.setDouble(25, ped.getAnticipo_def());
			st.setDouble(26, ped.getAnticipo_total_def());
			if (Constantes.STRING_CERO.equals(ped.getConvenio()) || Constantes.STRING_BLANCO.equals(ped.getConvenio())) {
				st.setString(27, null);
			}
			else
			{
				st.setString(27, ped.getConvenio());
			}
			
			st.setString(28, ped.getAnulado());
			st.setString(29, ped.getDescargado());
			st.setString(30, ped.getId_promocion());
			st.setString(31, ped.getSupervisor());
			st.setString(32, local);
			st.setString(33, ped.getPedvtant());
			st.registerOutParameter(34, Types.NUMERIC);
			
			st.execute();
			ped.setNumero(st.getInt(34));
			
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:insertaPedido error controlado",ex);
			throw new Exception("Error en DAO: SP_VTA_PEDI_INS_CB");
		}finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:insertaPedido cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:insertaPedido cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:insertaPedido error", e);

            }
        }
		
		
	}*/


	public String insertaMultiofertaCB(ProductosBean producto,
			String codigo_venta, int linea, String fecha, int numero_venta,
			String local) throws Exception{
		log.info("VentaPedidoDAOImpl:insertaMultiofertaCB inicio");
		Connection con = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		String mensaje = "ERROR";
		
		try {
			log.info("VentaPedidoDAOImpl:insertaMultiofertaCB conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_MTO_CB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, Constantes.STRING_P);
			cs.setString(2, codigo_venta);
			cs.setInt(3, linea);
			cs.setString(4, producto.getCodigo());
			cs.setInt(5, producto.getCantidad());
			cs.setString(6, producto.getCod_barra());
			cs.setString(7, fecha);
			cs.setString(8, local);
			cs.setInt(9, producto.getCdg_correlativo_multioferta());
			cs.setString(10, Constantes.STRING_ACTION_MON);
			cs.setString(11, "MON/" + util.formato_Numero_Ticket(producto.getCdg_correlativo_multioferta()));
			cs.setString(12, "N");
			cs.setString(13, null);
			cs.setString(14, "N");
			cs.registerOutParameter(15, Types.NUMERIC);
			cs.registerOutParameter(16, Types.VARCHAR);
			
			cs.execute();
			producto.setCdg_correlativo_multioferta(cs.getInt(15));
			mensaje = cs.getString(16);
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:insertaMultiofertaCB error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_MTO_CB"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:insertaMultiofertaCB cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:insertaMultiofertaCB cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:insertaMultiofertaCB error", e);
           }
		}
		return mensaje;
	}


	public int insertaPedidoDetalle(ProductosBean productosBean, int x,
			String codigo, String local) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaPedidoDetalle inicio");
		Connection con = null;
		CallableStatement cs = null;
		int identidad = Constantes.INT_CERO;
		
		try {
			log.info("VentaPedidoDAOImpl:insertaPedidoDetalle conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_LINEA_PRUEBA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setString(1, codigo + Constantes.STRING_SLASH + x);
			cs.setInt(2, x);
			cs.setString(3, codigo);
			cs.setString(4, productosBean.getCodigo());
			cs.setString(5, productosBean.getCod_barra());
			cs.setString(6, productosBean.getDescripcion());
			cs.setString(7, local);
			cs.setInt(8, productosBean.getCantidad());
			cs.setDouble(9, productosBean.getPrecio());
			cs.setDouble(10, productosBean.getDescuento());
			cs.setString(11, null);
			cs.setInt(12, productosBean.getCantidad());  //valor de los articulos entregados
			cs.setDouble(13, productosBean.getPrecio_sin_iva());
			cs.setDouble(14, productosBean.getPrecio_costo());
			cs.setDouble(15, productosBean.getPrecio());
			if (null != productosBean.getTipo() && !productosBean.getTipo().equals(Constantes.STRING_BLANCO)) 
			{
				if (productosBean.getTipo().equals(Constantes.STRING_LEJOS_OPT)) {
					cs.setString(16, Constantes.STRING_L);
				}
				if (productosBean.getTipo().equals(Constantes.STRING_CERCA_OPT)) {
					cs.setString(16, Constantes.STRING_C);
				}
			}
			else
			{
				cs.setString(16, Constantes.STRING_BLANCO);
			}
			
			if (null != productosBean.getOjo() && !productosBean.getOjo().equals(Constantes.STRING_BLANCO)) 
			{
				if (productosBean.getOjo().equals(Constantes.STRING_DERECHO)) {
					cs.setString(17, Constantes.STRING_D);
				}
				if (productosBean.getOjo().equals(Constantes.STRING_IZQUIERDO)) {
					cs.setString(17, Constantes.STRING_I);
				}
			}
			else
			{

				cs.setString(17, null);
			}
			
			cs.setDouble(18, productosBean.getEsfera());
			cs.setDouble(19, productosBean.getCilindro());
			cs.setDouble(20, productosBean.getDiametro());
			cs.setInt(21, productosBean.getEje());
			cs.setInt(22, Integer.parseInt(productosBean.getGrupo()));
			cs.setString(23, productosBean.getFecha_graduacion());
			cs.setInt(24, productosBean.getNumero_graduacion());
			cs.setString(25, productosBean.getLiberado());
			cs.registerOutParameter(26, Types.NUMERIC);
			
			cs.execute();
			identidad = cs.getInt(26);
			
		}  catch (Exception e) {
			log.error("VentaPedidoDAOImpl:insertaPedidoDetalle error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_LINEA"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:insertaPedidoDetalle cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:insertaPedidoDetalle cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:insertaPedidoDetalle error", e);
           }
       }
       return identidad;
	}


	public String insertaMultiofertaDetalle(int cdg_correlativo_multioferta,
			ProductosBean producto, int linea, String fecha, String local, String codigo) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaMultiofertaDetalle inicio");
		Connection con = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		String mje = Constantes.STRING_BLANCO;
		
		try {
			log.info("VentaPedidoDAOImpl:insertaMultiofertaDetalle conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_MTO_LINEA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, "MON/" +  util.formato_Numero_Ticket(cdg_correlativo_multioferta) + "/" + linea);
			cs.setInt(2, linea);
			cs.setString(3, "MON/" + util.formato_Numero_Ticket(cdg_correlativo_multioferta));
			cs.setString(4, producto.getCodigo());
			cs.setString(5, local);
			cs.setInt(6, producto.getCantidad());
			cs.setInt(7, producto.getCantidad());
			cs.setInt(8, producto.getImporte());
			cs.setString(9, producto.getCod_barra());
			if(null != producto.getOjo() && (!producto.getOjo().equals(""))){
				if (producto.getOjo().equals(Constantes.STRING_DERECHO)) {
					cs.setString(10, Constantes.STRING_D);
				}
				if (producto.getOjo().equals(Constantes.STRING_IZQUIERDO)) {
					cs.setString(10, Constantes.STRING_I);
				}
			}else{
				cs.setString(10, Constantes.STRING_BLANCO);
			}
			
			cs.setDouble(11, producto.getEsfera());
			cs.setDouble(12, producto.getCilindro());
			cs.setDouble(13, producto.getDiametro());
			cs.setInt(14, producto.getEje());
			cs.setInt(15, Integer.parseInt(producto.getGrupo()));
			if (null != producto.getTipo() && !producto.getTipo().equals(Constantes.STRING_BLANCO)) 
			{
				if (producto.getTipo().equals(Constantes.STRING_LEJOS_OPT)) {
					cs.setString(16, Constantes.STRING_L);
				}
				if (producto.getTipo().equals(Constantes.STRING_CERCA_OPT)) {
					cs.setString(16, Constantes.STRING_C);
				}
			}
			else
			{
				cs.setString(16, Constantes.STRING_BLANCO);
			}
			cs.setString(17, producto.getFecha_graduacion());
			cs.setInt(18, producto.getNumero_graduacion());
			cs.registerOutParameter(19, Types.NUMERIC);
			cs.registerOutParameter(20, Types.VARCHAR);
			cs.setString(21, codigo);
			
			cs.execute();
			producto.setIdent(cs.getInt(19));
			mje = cs.getString(20);
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:insertaMultiofertaDetalle error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_INS_MTO_LINEA"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:insertaMultiofertaDetalle cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:insertaMultiofertaDetalle cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:insertaMultiofertaDetalle error", e);
           }
		
		}
		return mje;
		
	}


	public void insertaSuplementosLinea(SuplementopedidoBean suple,
			ProductosBean productosBean, int linea, int identidad) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaSuplementosLinea inicio");
		Connection con = null;
		CallableStatement cs = null;

		
		try {
			log.info("VentaPedidoDAOImpl:insertaSuplementosLinea conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_SUP_LINEA(?,?,?)}");
			cs.setInt(1, identidad);
			cs.setString(2, suple.getTratami());
			cs.setString(3, suple.getValor());
			cs.execute();
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:insertaSuplementosLinea error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_SUP_LINEA"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:insertaSuplementosLinea cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:insertaSuplementosLinea cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:insertaSuplementosLinea error", e);
           }
		
		}
	}
	public static void insertaPago(String codigo, String forma_pago,
			int cantidad, String fecha, String divisa, int cambio,
			String local, int cantidad2, String devolucion, String usuario,
			String numero_bono, Double dto,String rut_vs) throws Exception {
		 
		Connection con = null;
		CallableStatement cs = null;
		
		try {
			if(!forma_pago.equals("0")){
				con = ConexionFactory.INSTANCE.getConexion();
				System.out.println("call SP_VTA_PEDI_INS_PAGO_VS('"+codigo+"','"+forma_pago+"',"+cantidad+",'"+usuario+"',"+cambio+","+cantidad2+",'"+devolucion+"','"+divisa+"','"+fecha+"','"+numero_bono+"','"+local+"',"+dto+","+rut_vs+");");
				cs = con.prepareCall("{call SP_VTA_PEDI_INS_PAGO_VS(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setString(1, codigo);
				cs.setString(2, forma_pago);
				cs.setInt(3, cantidad);
				cs.setString(4, usuario);
				cs.setInt(5, cambio);
				cs.setInt(6, cantidad2);
				cs.setString(7, devolucion);
				cs.setString(8, divisa);
				cs.setString(9, fecha);
				cs.setString(10, numero_bono);
				cs.setString(11, local);
				cs.setDouble(12, dto);
				cs.setString(13,rut_vs);
				cs.execute();
			}
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_PAGO"); 
		} finally {
              try{
               if (null != cs){
                   cs.close();
               }  
               if (null != con){
            	   con.close();
               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
	}

	/*public static void insertaPago(String codigo, String forma_pago,
			int cantidad, String fecha, String divisa, int cambio,
			String local, int cantidad2, String devolucion, String usuario,
			String numero_bono, Double dto) throws Exception {
		
		Connection con = null;
		CallableStatement cs = null;
		
		try {
			if(!forma_pago.equals("0")){
				con = ConexionFactory.INSTANCE.getConexion();
				
				System.out.println("call SP_VTA_PEDI_INS_PAGO('"+codigo+"','"+forma_pago+"',"+cantidad+",'"+usuario+"',"+cambio+","+cantidad2+",'"+devolucion+"','"+divisa+"','"+fecha+"','"+numero_bono+"','"+local+"',"+dto+");");
				
				cs = con.prepareCall("{call SP_VTA_PEDI_INS_PAGO(?,?,?,?,?,?,?,?,?,?,?,?)}");
				cs.setString(1, codigo);
				cs.setString(2, forma_pago);
				cs.setInt(3, cantidad);
				cs.setString(4, usuario);
				cs.setInt(5, cambio);
				cs.setInt(6, cantidad2);
				cs.setString(7, devolucion);
				cs.setString(8, divisa);
				cs.setString(9, fecha);
				cs.setString(10, numero_bono);
				cs.setString(11, local);
				cs.setDouble(12, dto);
				
				cs.execute();
			}
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_PAGO"); 
		} finally {
              try{
               if (null != cs){
                   cs.close();
               }  
               if (null != con){
            	   con.close();
               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
	}*/


	public static String insertaDocumento(String ticket, int documento,
			String tipo_doc, long total, String fecha, String local,String nisapre,String dto) throws Exception {
		
		Connection con = null;
		CallableStatement cs = null;
		String res = "";
		try {
			if(tipo_doc != "0"){
				System.out.println("Numero de documento ===>"+documento);
				con = ConexionFactory.INSTANCE.getConexion();
				System.out.println("====> SP_VTA_PEDI_INS_DOCUMENTO_BE("+ticket+","+documento+","+tipo_doc+","+total+","+fecha+","+local+","+nisapre+","+dto+");");

				//cs = con.prepareCall("{call SP_VTA_PEDI_INS_DOCUMENTO_BE(?,?,?,?,?,?,?,?,?)}");
				cs = con.prepareCall("{call SP_VTA_PEDI_INS_DOCUMENTO_QA(?,?,?,?,?,?,?,?,?)}");
				cs.setString(1, fecha);
				cs.setLong(2, total);
				cs.setInt(3, documento);
				cs.setString(4, ticket);
				cs.setString(5, tipo_doc);
				cs.setString(6, local);
				cs.setString(7, nisapre);			
				cs.setString(8, dto);
				cs.registerOutParameter(9,OracleTypes.VARCHAR);
				
				cs.execute();
				
				res = cs.getString(9);
			}
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_DOCUMENTO"); 
		} finally {
              try{
               if (null != cs){
                   cs.close();
               }  
               if (null != con){
            	   con.close();
               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
		return res;
		
		
	}


	public static void insertaAdicionalesArcli(int identidad,
			ProductosBean productosBean) throws Exception 
	{
		
		Connection con = null;
		CallableStatement cs = null;
		
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_ADICION_ARCLI(?,?,?,?,?,?)}");
			
			cs.setString(1, String.valueOf(identidad));
			cs.setString(2, productosBean.getArcli_armazon());
			cs.setInt(3, productosBean.getArcli_puente());
			cs.setInt(4, productosBean.getArcli_diagonal());
			cs.setInt(5, productosBean.getArcli_horizontal());
			cs.setInt(6, productosBean.getArcli_vertical());
			
			cs.execute();
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_ADICION_ARCLI"); 
		} finally {
              try{
               if (null != cs){
                   cs.close();
               }  
               if (null != con){
            	   con.close();
               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
		
	}


	public static void traeAdicionalesArcli(ProductosBean prod) throws Exception 
	{
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_SEL_ADICION_ARCLI(?,?)}");
			
			cs.setString(1, String.valueOf(prod.getIdent()));
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			
			while (rs.next()) {
				prod.setArcli_armazon(rs.getString("TIPOARMAZON"));
				prod.setArcli_puente(rs.getInt("PUENTE"));
				prod.setArcli_diagonal(rs.getInt("DIAGONAL"));
				prod.setArcli_horizontal(rs.getInt("VALHORIZONTAL"));
				prod.setArcli_vertical(rs.getInt("VALVERTICAL"));
			}
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_SEL_ADICION_ARCLI"); 
		} finally {
              try{
            	  if (null != rs){
                      rs.close();
                  }
	               if (null != cs){
	                   cs.close();
	               }  
	               if (null != con){
	            	   con.close();
	               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
		
		
		
	}


	public static String[][] ValidaSeguroGarantia(String cdg,
			String encargo_garantia) throws Exception {
		
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		String estado[][] = null;
		Utils utiles = new Utils();
		
		
		try {
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_SEL_VALIDA_SG(?,?,?,?)}");
			
			cs.setString(1, cdg);
			cs.setString(2, encargo_garantia);
			cs.registerOutParameter(3, OracleTypes.NUMBER);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			
			cs.execute();
			int numero = cs.getInt(3);
			
			
			
			
			if (numero > 0) 
			{
				rs = (ResultSet)cs.getObject(4);
				
				
				estado= new String[4][3];
				
				int x = 0;
				while (rs.next()) {
					estado[x][0] = Constantes.STRING_TRUE;
					estado[x][1] =	utiles.formatoFechaString(rs.getString("FECHAGRAD"));
					estado[x][2] = rs.getString("NUMEROGRAD"); 
					if (x<10) {
						x++;
					}
				}
			}
			else
			{
				estado= new String[1][3];
				estado[0][0] = Constantes.STRING_FALSE;
				estado[0][1] = Constantes.STRING_BLANCO;
				estado[0][2] = Constantes.STRING_BLANCO;
			}
			
			return estado;
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_SEL_VALIDA_SG"); 
		} finally {
              try{
            	  if (null != rs){
                      rs.close();
                  }
	               if (null != cs){
	                   cs.close();
	               }  
	               if (null != con){
	            	   con.close();
	               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
		
	}


	public void insertaMultiofertaDetalleSuplemento(int indice,
			SuplementopedidoBean suple) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaMultiofertaDetalleSuplemento inicio");
		Connection con = null;
		CallableStatement cs = null;

		
		try {
			log.info("VentaPedidoDAOImpl:insertaMultiofertaDetalleSuplemento conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_MTO_LINEA_SUP(?,?,?)}");
			cs.setInt(1, indice);
			cs.setString(2, suple.getTratami());
			cs.setString(3, suple.getValor());
			cs.execute();
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:insertaMultiofertaDetalleSuplemento error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_INS_MTO_LINEA_SUP"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:insertaMultiofertaDetalleSuplemento cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:insertaMultiofertaDetalleSuplemento cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:insertaMultiofertaDetalleSuplemento error", e);
           }
		
		}
	}


	public boolean valida_promocion_edad_tu_descuento(ProductosBean prod, String codigo_pomo) throws Exception {
		
		log.info("VentaPedidoDAOImpl:valida_promocion_edad_tu_descuento inicio");
		Connection con = null;
		CallableStatement cs = null;
		boolean estado = false;
		
		try {
			log.info("VentaPedidoDAOImpl:valida_promocion_edad_tu_descuento conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_VALIDA_PROMO_EDAD(?,?,?,?,?)}");
			cs.setString(1, prod.getFamilia());
			cs.setString(2, prod.getSubFamilia());
			cs.setString(3, prod.getGrupoFamilia());
			cs.setString(4, codigo_pomo);
			cs.registerOutParameter(5, Types.NUMERIC);
			cs.execute();
			
			int num = cs.getInt(5);
			
			if (num != 0) {
				estado = true;
			}
			
			return estado;
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:valida_promocion_edad_tu_descuento error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_VALIDA_PROMO_EDAD"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:valida_promocion_edad_tu_descuento cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:valida_promocion_edad_tu_descuento cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:valida_promocion_edad_tu_descuento error", e);
           }
		
		}
		
	}


	public boolean validaCristalRecetaADDDesdeHasta(ProductosBean prod,
			GraduacionesBean graduacion) throws Exception {
		
		log.info("VentaPedidoDAOImpl:validaCristalRecetaADDDesdeHasta inicio");
		Connection con = null;
		CallableStatement cs = null;
		boolean estado = false;
		String familia = "";
		String subfam = "";
		String grupofam = "";
		double esfera = 0;
		double cilindro = 0;
		double adicion = 0;
		
		esfera = prod.getEsfera();
		cilindro = prod.getCilindro();
		familia = prod.getFamilia();
		subfam = prod.getSubFamilia();
		grupofam = prod.getGrupoFamilia();
		
		if (prod.getOjo().equals(Constantes.STRING_IZQUIERDO) || prod.getOjo().equals(Constantes.STRING_I)) {
			adicion = graduacion.getOI_adicion();
		}
		else
		{
			adicion = graduacion.getOD_adicion();
		}
		
		try {
			log.info("VentaPedidoDAOImpl:validaCristalRecetaADDDesdeHasta conectando base datos");
			
			System.out.println(" SP_UTIL_SEL_VALIDA_CRIS_ADD => "+familia+","+subfam+","+grupofam+","+esfera+","+cilindro+","+adicion);
			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_UTIL_SEL_VALIDA_CRIS_ADD(?,?,?,?,?,?,?)}");
			cs.setString(1, familia);
			cs.setString(2, subfam);
			cs.setString(3, grupofam);
			cs.setDouble(4, esfera);
			cs.setDouble(5, cilindro);
			cs.setDouble(6, adicion);
			cs.registerOutParameter(7, Types.NUMERIC);
			cs.execute();
			
			int num = cs.getInt(7);
			
			if (num != 0) {
				estado = true;
			}
			System.out.println("=>"+estado);
			return estado;
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:validaCristalRecetaADDDesdeHasta error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_VALIDA_PROMO_EDAD"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:validaCristalRecetaADDDesdeHasta cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:validaCristalRecetaADDDesdeHasta cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:validaCristalRecetaADDDesdeHasta error", e);
           }
		
		}
	}
	
	
	/*
	 * LMARIN 20131229
	 * CAMBIOS HISTORIAL ENCARGO
	 */
	public boolean validaTipoPedido(String encargo,String tienda) throws Exception {
		
		log.info("VentaPedidoDAOImpl:validaTipoPedido inicio");
		Connection con = null;
		CallableStatement cs = null;
		boolean estado = false;
		
		System.out.println("VentaPedidoDao =>"+encargo+"<=> tienda"+tienda);
		try {
			log.info("VentaPedidoDAOImpl:validaTipoPedido conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VAL_TIPOPED(?,?,?)}");
			cs.setString(1, encargo);
			cs.setString(2, tienda);
			cs.registerOutParameter(3, Types.NUMERIC);
			cs.execute();
			
			int num = cs.getInt(3);
			
			if (num == 1) {
				estado = true;
			}
			return estado;
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl:validaTipoPedido error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VAL_TIPOPED"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl:validaTipoPedido cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl:validaTipoPedido cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl:validaTipoPedido error", e);
           }
		
		}
	}
	
	/*
	 * LMARIN 20131229
	 * CAMBIOS HISTORIAL ENCARGO
	 */
	public int inserta_historial_encargo(String tipo_dev,String encargo_padre,String encargo,String fecha,String nif) throws Exception {
		
		log.info("VentaPedidoDAOImpl:SP_INSERTAR_HISTORIAL_ENCARGO inicio");
		Connection con = null;
		CallableStatement cs = null;
		boolean estado = false;
		String [] enc_pad_tmp = encargo_padre.split("/");
		String epadre = enc_pad_tmp[0]+"/"+enc_pad_tmp[1]; 
		System.out.println("VentaPedidoDao =>"+encargo);
		try {
			log.info("VentaPedidoDAOImpl:validaTipoPedido conectando base datos");			
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("tipo_dev =>"+tipo_dev+"<==> encargo_padre =>"+encargo_padre+"<==> encargo"+encargo+"fecha =>"+fecha+" nif ==>"+nif);
			cs = con.prepareCall("{call  SP_INSERTAR_HISTORIAL_ENCARGO(?,?,?,?,?,?)}");
			cs.setString(1, tipo_dev);
			cs.setString(2, epadre);
			cs.setString(3, encargo);
			cs.setString(4, fecha);
			cs.setString(5, nif);
			cs.registerOutParameter(6, Types.NUMERIC);
			cs.execute();
			
			int res = cs.getInt(6);
						
			return res;
			
		} catch (Exception e) {
			log.error("VentaPedidoDAOImpl: SP_INSERTAR_HISTORIAL_ENCARGO error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP:  SP_INSERTAR_HISTORIAL_ENCARGO"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaPedidoDAOImpl: SP_INSERTAR_HISTORIAL_ENCARGO cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaPedidoDAOImpl: SP_INSERTAR_HISTORIAL_ENCARGO cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaPedidoDAOImpl: SP_INSERTAR_HISTORIAL_ENCARGO error", e);
           }
		
		}
	}
	
	/*
	 * LMARIN 20140417
	 * CAMBIOS HISTORIAL ENCARGO
	 */
	public static int reimprimeboleta(String codigo, String encargo, String fecha) throws Exception {
		
		Connection con = null;
		CallableStatement cs = null;
		
		try {
			System.out.println("codigo ==> "+codigo+"<=> encargo ==>"+encargo+"<=>fecha ==>"+fecha);
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_REIMPRIME_BOLETA(?,?,?,?)}");
			cs.setString(1, codigo);
			cs.setString(2, encargo);
			cs.setString(3, fecha);
			cs.registerOutParameter(4, Types.NUMERIC);	
			
			cs.execute();
			
			int res = cs.getInt(4);
			
			return res;
		} catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_REIMPRIME_BOLETA"); 
		} finally {
              try{
               if (null != cs){
                   cs.close();
               }  
               if (null != con){
            	   con.close();
               }
           }catch(Exception e){
               e.printStackTrace();
           }
		
		}
	}
	
	/*
	 * AUTHOR : LMARIN
	 * FECHA: 20150421
	 * METODO QUE DEVUELVE LAS PROMOCIONES ACTIVAS
	 * @Return  ArrayList<PromocionBean>
	 */
	
	public ArrayList<PromocionBean> traePromociones() {
		log.info("VentaPedidoPendientesDAOImpl:traePromociones inicio");
		ArrayList<PromocionBean> listaPromo= new ArrayList<PromocionBean>();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			log.info("VentaPedidoPendientesDAOImpl:traePromociones conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_TRAE_PROMOCIONES(?)}");
			
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(1);
			
			while (rs.next()) {
				log.debug("VentaPedidoPendientesDAOImpl:traePromociones entrando ciclo while");
				PromocionBean promo = new PromocionBean();
				promo.setId(rs.getString("CDG"));
				promo.setDescripcion(rs.getString("DESCRIPCION"));				
				listaPromo.add(promo);
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:traePromociones error controlado",e);
		} finally {
            try{
            	if(null != rs){
               	 log.warn("VentaPedidoPendientesDAOImpl:traePromociones cierre ResultSet");
               	 rs.close();
                }
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:traePromociones cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:traePromociones cierre Connection");
		    	   con.close();
	           } 
             
	         }catch(Exception e){
	        	 log.error("VentaPedidoPendientesDAOImpl:traePromociones error", e);
	         }
		}
		return listaPromo;
	}
	
	
	public void insertaCliente_inter(VentaPedidoBean ped, String local) throws Exception {
		log.info("VentaPedidoDAOImpl:insertaPedido inicio");
		Connection con = null;
		CallableStatement st = null;
		
		try{
			log.info("VentaPedidoDAOImpl:insertaPedido conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			
			System.out.println("exec SP_VTA_INS_CLI_INTER("+ped.getCdg()+","+ped.getDni_pas()+","+ped.getNombre_inter()+","+ped.getNacionalidad()+","+ped.getEmail_inter()+");");
			
			String sql = "{call SP_VTA_INS_CLI_INTER(?,?,?,?,?)}";
			st = con.prepareCall(sql);
			st.setString(1, ped.getCdg());
			st.setString(2, ped.getDni_pas());
			st.setString(3, ped.getNombre_inter());
			st.setString(4, ped.getNacionalidad());
			st.setString(5, ped.getEmail_inter());
	
			st.execute();
			
			
			
		}catch(SQLException ex){
			log.error("VentaPedidoDAOImpl:insertaCliente_inter error controlado",ex);
			throw new Exception("Error en DAO: SP_VTA_INS_CLI_INTER");
		}finally {
            try{
                if (null != st){
                	log.warn("VentaPedidoDAOImpl:insertaCliente_inter cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("VentaPedidoDAOImpl:insertaCliente_inter cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("VentaPedidoDAOImpl:insertaCliente_inter error", e);

            }
        }
		
		
	}

	
	
	
}
