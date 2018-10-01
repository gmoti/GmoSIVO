package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.PresupuestoDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.PresupuestoBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

public class PresupuestoDAOImpl implements PresupuestoDAO {
	Logger log = Logger.getLogger( this.getClass() );
	public PresupuestosBean traeGenericosFormulario(String local) throws Exception {
		log.info("PresupuestoDAOImpl:traeGenericosFormulario inicio");
		PresupuestosBean bean = new PresupuestosBean();
        Connection con = null;
        CallableStatement st = null;
        
        try {
        	log.info("PresupuestoDAOImpl:traeGenericosFormulario conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_GENERICOS(?,?,?,?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, local);
            st.registerOutParameter(2, OracleTypes.VARCHAR);
            st.registerOutParameter(3, OracleTypes.VARCHAR);
            st.registerOutParameter(4, OracleTypes.VARCHAR);
            st.registerOutParameter(5, OracleTypes.INTEGER);
            
            st.execute();
            
            bean.setIdioma(st.getObject(2).toString());
            bean.setDivisa(st.getObject(3).toString());
            bean.setForma_pago(st.getObject(4).toString());
            bean.setPorcentaje_descuento_max(st.getInt(5));
            bean.setCambio(1);
            
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traeGenericosFormulario error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_GENERICOS"); 
        }finally {
            try{
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traeGenericosFormulario cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traeGenericosFormulario cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traeGenericosFormulario error", e);
            }
        }
        return bean;
	}

	public int traeCodigoVenta(String local) throws Exception {
		log.info("PresupuestoDAOImpl:traeCodigoVenta inicio");
		int codigo = Constantes.INT_CERO;
        Connection con = null;
        CallableStatement st = null;
        
        try {
        	log.info("PresupuestoDAOImpl:traeCodigoVenta conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_COD_PRESUP(?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, local);
            st.registerOutParameter(2, OracleTypes.NUMERIC);
            
            st.execute();
            codigo = Integer.parseInt(st.getObject(2).toString());
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traeCodigoVenta error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_COD_PRESUP"); 
        }finally {
            try{
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traeCodigoVenta cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traeCodigoVenta cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traeCodigoVenta error", e);
            }
        }
        return codigo;
	}

	public String traeCodigo_Suc(String local) throws Exception {
		log.info("PresupuestoDAOImpl:traeCodigo_Suc inicio");
		String codigo = Constantes.STRING_BLANCO;
        Connection con = null;
        CallableStatement st = null;
        
        try {
        	log.info("PresupuestoDAOImpl:traeCodigo_Suc conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_ENC_TICKET(?,?)}";
            st = con.prepareCall(sql);
            st.setString(1, local);
            st.registerOutParameter(2, OracleTypes.VARCHAR);
            
            st.execute();
            codigo = st.getObject(2).toString();
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traeCodigo_Suc error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_ENC_TICKET"); 
        }finally {
            try{
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traeCodigo_Suc cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traeCodigo_Suc cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traeCodigo_Suc error", e);
            }
        }
        return codigo;
	}

	public boolean insertaPresupuesto(PresupuestosBean presupuesto, String local) throws Exception {
		log.info("PresupuestoDAOImpl:insertaPresupuesto inicio");
		Connection con = null;
		CallableStatement cs = null;
		boolean estado = false;
		
		try {
			log.info("PresupuestoDAOImpl:insertaPresupuesto conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_PRESUP_INS_PRESUP_CB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, presupuesto.getCodigo());
			cs.setString(2, presupuesto.getSerie());
			cs.setInt(3, presupuesto.getNumero());
			cs.setString(4, presupuesto.getAgente());
			cs.setString(5, presupuesto.getDivisa());
			cs.setString(6, presupuesto.getIdioma());
			cs.setDouble(7, presupuesto.getDcto());
			cs.setString(8, presupuesto.getFecha());
			cs.setInt(9, presupuesto.getCambio());
			cs.setString(10, presupuesto.getNotas());
			cs.setString(11, presupuesto.getAgente());
			cs.setString(12, presupuesto.getForma_pago());
			cs.setInt(13, presupuesto.getCliente());
			cs.setString(14,local);
			cs.setString(15, presupuesto.getConvenio());
			cs.registerOutParameter(16, Types.NUMERIC);
			
			cs.execute();
			estado = true;
			presupuesto.setNumero(cs.getInt(16));
			
		}catch (Exception e) {
			estado =  false;
			log.error("PresupuestoDAOImpl:insertaPresupuesto error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_PRESUP_INS_PRESUP_CB"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("PresupuestoDAOImpl:insertaPresupuesto cierre CallableStatement");
                   cs.close();
               }   
               if (null != con){
            	   log.warn("PresupuestoDAOImpl:insertaPresupuesto cierre Connection");
		    	   con.close();
	           }                
           }catch(Exception e){
        	   log.error("PresupuestoDAOImpl:insertaPresupuesto error", e);
           }
       }
       
       return estado;
		
	}

	public void insertaPresupuestoDetalle(ProductosBean prod,
			PresupuestosBean presupuesto, int x, String local) throws Exception {
		log.info("PresupuestoDAOImpl:insertaPresupuestoDetalle inicio");
		Connection con = null;
		CallableStatement cs = null;
		
		try {
			log.info("PresupuestoDAOImpl:insertaPresupuestoDetalle conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_PRESUP_INS_PRESUP_LINEA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, presupuesto.getCodigo() + "/" + x );
			cs.setInt(2, x);
			cs.setString(3, presupuesto.getCodigo());
			cs.setString(4, prod.getCodigo());
			cs.setString(5, prod.getDescripcion());
			cs.setInt(6, prod.getCantidad());
			cs.setDouble(7, prod.getPrecio_sin_iva());
			cs.setDouble(8, prod.getDescuento());
			cs.setDouble(9, prod.getPrecio());
			cs.setString(10, prod.getCod_barra());
			cs.setString(11, local);
			if (null != prod.getOjo() && !prod.getOjo().equals(Constantes.STRING_BLANCO)) 
			{
				if (prod.getOjo().equals(Constantes.STRING_DERECHO)) {
					cs.setString(12, Constantes.STRING_D);
				}
				if (prod.getOjo().equals(Constantes.STRING_IZQUIERDO)) {
					cs.setString(12, Constantes.STRING_I);
				}
			}
			else
			{
				cs.setString(12, prod.getOjo());
			}
			cs.setDouble(13, prod.getEsfera());
			cs.setDouble(14, prod.getCilindro());
			cs.setDouble(15, prod.getDiametro());
			cs.setString(16, prod.getGrad_fecha());
			cs.setInt(17, prod.getGrad_numero());
			cs.setInt(18, prod.getEje());
			if (null != prod.getTipo() && !prod.getTipo().equals(Constantes.STRING_BLANCO)) 
			{
				if (prod.getTipo().equals(Constantes.STRING_LEJOS_OPT)) {
					cs.setString(19, Constantes.STRING_L);
				}
				if (prod.getTipo().equals(Constantes.STRING_CERCA_OPT)) {
					cs.setString(19, Constantes.STRING_C);
				}
			}
			else
			{
				cs.setString(19, prod.getTipo());
			}
			
			
			cs.execute();
			
		}  catch (Exception e) {
			log.error("PresupuestoDAOImpl:insertaPresupuestoDetalle error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_PRESUP_INS_PRESUP_LINEA"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("PresupuestoDAOImpl:insertaPresupuestoDetalle cierre CallableStatement");
                   cs.close();
               }   
               if (null != con){
            	   log.warn("PresupuestoDAOImpl:insertaPresupuestoDetalle cierre Connection");
		    	   con.close();
	           }                
           }catch(Exception e){
        	   log.error("PresupuestoDAOImpl:insertaPresupuestoDetalle error", e);
           }
       }
		
	}

	public ArrayList<PresupuestosBean> traeListaPresupuestos(String cliente, String local) throws Exception {
		log.info("PresupuestoDAOImpl:traeListaPresupuestos inicio");
        Connection con = null;
        CallableStatement st = null;
        ArrayList<PresupuestosBean> lista_presup = new ArrayList<PresupuestosBean>();
        ResultSet rs = null;
        Utils util = new Utils();
        try {
        	log.info("PresupuestoDAOImpl:traeListaPresupuestos conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_PRESUPUESTOS(?,?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, cliente);
            st.setString(2, local);
            st.registerOutParameter(3, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(3);
            
            while (rs.next()) {
            	log.debug("PresupuestoDAOImpl:traeListaPresupuestos entrando ciclo while");
            	PresupuestosBean bean = new PresupuestosBean();
            	
				bean.setCodigo(rs.getString("CDG"));
				bean.setSerie(rs.getString("SERIE"));
				bean.setNumero(rs.getInt("NUMERO"));
				bean.setFecha(util.formatoFecha(rs.getDate("FECHAPEDIDO")));
				bean.setForma_pago(rs.getString("FPAGO"));
				bean.setCambio(rs.getInt("CAMBIO"));
				bean.setDivisa(rs.getString("DIVISA"));
				bean.setDcto(rs.getInt("DTO"));
				bean.setNotas(rs.getString("NOTAS"));
				bean.setIdioma(rs.getString("IDIOMA"));
				bean.setId_agente(rs.getString("AGENTE"));
				bean.setFecha_entrega(rs.getString("FECHAENTREGA"));
				bean.setCerrado(rs.getString("CERRADO"));
				bean.setConvenio(rs.getString("CONVENIO"));
				bean.setConvenio_det(rs.getString("CONVENIODET"));
				
				lista_presup.add(bean);
			}
            
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traeListaPresupuestos error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_PRESUPUESTOS"); 
        }finally {
            try{
            	if(null != rs){
   	        	  log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre ResultSet");
   	        	   rs.close();
   	           }
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre Connection");
 		    	   con.close();
 	           } 
 	           
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traeListaPresupuestos error", e);
            }
        }
        return lista_presup;
		
	}

	public ArrayList<ProductosBean> traeListaProductos(String codigo) throws Exception {
		
		log.info("PresupuestoDAOImpl:traeListaProductos inicio");
        Connection con = null;
        CallableStatement st = null;
        ArrayList<ProductosBean> lista_prod = new ArrayList<ProductosBean>();
        ResultSet rs = null;
        Utils util = new Utils();
        try {
        	log.info("PresupuestoDAOImpl:traeListaProductos conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_PRESUPUESTO_DET(?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, codigo);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
            
            while (rs.next()) {
            	log.debug("PresupuestoDAOImpl:traeListaProductos entrando ciclo while");
            	ProductosBean bean = new ProductosBean();
            	bean.setCod_pedvtcb(codigo);
            	bean.setCodigo(rs.getString("ARTICULO"));
            	bean.setCod_articulo(rs.getString("ARTICULO"));
            	bean.setLinea(String.valueOf(rs.getInt("LINEA")));
            	bean.setDescripcion(rs.getString("DESCRIPCION"));
            	bean.setCantidad(rs.getInt("CANTIDAD"));
            	bean.setPrecio(rs.getInt("PRECIOIVA"));
            	bean.setImporte(rs.getInt("PRECIOIVA") * rs.getInt("CANTIDAD"));
            	bean.setPrecio_sin_iva(rs.getInt("PRECIO"));
            	bean.setDto(String.valueOf(rs.getInt("DTO")));
            	bean.setDescuento(rs.getDouble("DTO"));
            	bean.setCod_barra(rs.getString("CODIGOBARRAS"));
            	
            	if (null != rs.getString("OJO")) {
            		if (Constantes.STRING_D.equals(rs.getString("OJO"))) {
                		bean.setOjo(Constantes.STRING_DERECHO);
    				}
                	else if (Constantes.STRING_I.equals(rs.getString("OJO"))) {
                		bean.setOjo(Constantes.STRING_IZQUIERDO);
    				}
                	else
                	{
                		bean.setOjo(Constantes.STRING_BLANCO);
                	}
				}
            	else
            	{
            		bean.setOjo(Constantes.STRING_BLANCO);
            	}
            	
            	
            	bean.setEsfera(rs.getDouble("ESFERA"));
            	bean.setCilindro(rs.getDouble("CILINDRO"));
            	bean.setDiametro(rs.getDouble("DIAMETRO"));
            	bean.setFecha_graduacion(util.formatoFecha(rs.getDate("FECHAGRAD")));
            	bean.setNumero_graduacion(rs.getInt("NUMEROGRAD"));
            	bean.setEje(rs.getInt("EJE"));
            	if (null != rs.getString("TIPO")) {
            		if (Constantes.STRING_C.equals(rs.getString("TIPO"))) {
                		bean.setTipo(Constantes.STRING_CERCA_OPT);
    				}
                	else if (Constantes.STRING_L.equals(rs.getString("OJO"))) {
                		bean.setTipo(Constantes.STRING_LEJOS_OPT);
    				}
                	else
                	{
                		bean.setOjo(Constantes.STRING_BLANCO);
                	}
				}
            	else
            	{
            		bean.setTipo(rs.getString("TIPO"));
            	}
            	
            	bean.setFamilia(rs.getString("FAMILIA"));
            	bean.setSubFamilia(rs.getString("SUBFAM"));
				bean.setGrupoFamilia(rs.getString("GRUPOFAM"));
				bean.setTipoFamilia(rs.getString("TIPOFAM"));
				lista_prod.add(bean);
			}
            
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traeListaProductos error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_PRESUPUESTO_DET"); 
        }finally {
            try{
            	if(null != rs){
     	        	  log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre ResultSet");
     	        	   rs.close();
     	           }
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traeListaProductos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traeListaProductos cierre Connection");
 		    	   con.close();
 	           } 
               
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traeListaProductos error", e);
            }
        }
        return lista_prod;
	}

	public String[] traspasoPresupuesto(String codigo, String local) throws Exception {
		log.info("PresupuestoDAOImpl:traspasoPresupuesto inicio");
		Connection con = null;
        CallableStatement st = null;
        String mensaje[]= new String[2];
        Utils util = new Utils();
        try {
        	log.info("PresupuestoDAOImpl:traspasoPresupuesto conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_COPIAR_VTA_PEDI(?,?,?,?,?,?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, codigo);
            st.setString(2, local);
            st.setString(3, util.formatoFecha(util.traeFecha()));
            st.registerOutParameter(4, Types.NUMERIC);
            st.registerOutParameter(5, Types.VARCHAR);
            st.registerOutParameter(6, Types.VARCHAR);
            st.registerOutParameter(7, Types.VARCHAR);
            
            st.execute();
            mensaje[0] = st.getString(5);
            mensaje[1] = st.getString(7).trim() + "/" + st.getString(6).trim();

        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traspasoPresupuesto error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_COPIAR_VTA_PEDI"); 
        }finally {
            try{
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traspasoPresupuesto cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traspasoPresupuesto cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traspasoPresupuesto error", e);
            }
        }
        return mensaje;
	}

	public boolean eliminarPresupuesto(String codigo) throws Exception {
		log.info("PresupuestoDAOImpl:eliminarPresupuesto inicio");
		Connection con = null;
        CallableStatement st = null;
        int estado = Constantes.INT_CERO;
        boolean bol_estado = false;
        try {
        	log.info("PresupuestoDAOImpl:eliminarPresupuesto conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_DEL_PRESUPUESTO(?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, codigo);
            st.registerOutParameter(2, Types.NUMERIC);
            
            st.execute();
            estado = Integer.parseInt(st.getObject(2).toString());
            
            if(estado == Constantes.INT_UNO)
            {
            	bol_estado = true;
            }
            else
            {
            	bol_estado = false;
            }

        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:eliminarPresupuesto error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_DEL_PRESUPUESTO"); 
        }finally {
            try{
                if (null != st){
                	log.warn("PresupuestoDAOImpl:eliminarPresupuesto cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:eliminarPresupuesto cierre Connection");
 		    	   con.close();
 	           } 
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:eliminarPresupuesto error", e);
            }
        }
        return bol_estado;
	}

	public ArrayList<PresupuestosBean> traePresupuestos(String local,
			String presupuesto, String agente, String cliente, String fecha) throws Exception {
		log.info("PresupuestoDAOImpl:traeListaPresupuestos inicio");
        Connection con = null;
        CallableStatement st = null;
        ArrayList<PresupuestosBean> lista_presup = new ArrayList<PresupuestosBean>();
        ResultSet rs = null;
        Utils util = new Utils();
        try {
        	log.info("PresupuestoDAOImpl:traePresupuestos conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_LISTA_PRESUP(?,?,?,?,?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, local);
            st.setString(2, presupuesto);
            st.setString(3, agente);
            st.setString(4, cliente);
            st.setString(5, fecha);
            st.registerOutParameter(6, OracleTypes.CURSOR);
            st.execute();
			rs = (ResultSet)st.getObject(6);
            
            while (rs.next()) {
            	log.debug("PresupuestoDAOImpl:traePresupuestos entrando ciclo while");
            	PresupuestosBean bean = new PresupuestosBean();
            	
				bean.setCodigo(rs.getString("CDG"));
				bean.setFecha(util.formatoFecha(rs.getDate("FECHAPEDIDO")));
				bean.setAgente(rs.getString("AGENTE"));
				bean.setCerrado(rs.getString("CERRADO"));
				
				lista_presup.add(bean);
			}
            
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traePresupuestos error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_LISTA_PRESUP"); 
        }finally {
            try{
            	if(null != rs){
   	        	  log.warn("PresupuestoDAOImpl:traePresupuestos cierre ResultSet");
   	        	   rs.close();
   	           }
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traePresupuestos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traePresupuestos cierre Connection");
 		    	   con.close();
 	           } 
 	           
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traePresupuestos error", e);
            }
        }
        return lista_presup;
	}

	public PresupuestosBean traePresupuestoSeleccionado(String cdg) throws Exception {
		log.info("PresupuestoDAOImpl:traeListaPresupuestos inicio");
        Connection con = null;
        CallableStatement st = null;
        PresupuestosBean presupuesto = new PresupuestosBean();
        ResultSet rs = null;
        Utils util = new Utils();
        try {
        	log.info("PresupuestoDAOImpl:traeListaPresupuestos conectando base datos");
            con = ConexionFactory.INSTANCE.getConexion();
            String sql = "{call SP_PRESUP_SEL_PRESUP_SEL(?,?)}";
            st = con.prepareCall(sql);
            
            st.setString(1, cdg);
            st.registerOutParameter(2, OracleTypes.CURSOR);
            
            st.execute();
            rs = (ResultSet)st.getObject(2);
            
            while (rs.next()) {
            	log.debug("PresupuestoDAOImpl:traeListaPresupuestos entrando ciclo while");
            	
            	presupuesto.setCodigo(rs.getString("CDG"));
            	presupuesto.setSerie(rs.getString("SERIE"));
            	presupuesto.setNumero(rs.getInt("NUMERO"));
            	presupuesto.setFecha(util.formatoFecha(rs.getDate("FECHAPEDIDO")));
            	presupuesto.setForma_pago(rs.getString("FPAGO"));
            	presupuesto.setCambio(rs.getInt("CAMBIO"));
            	presupuesto.setDivisa(rs.getString("DIVISA"));
            	presupuesto.setCliente(rs.getInt("CLIENTE"));
            	presupuesto.setDcto(rs.getInt("DTO"));
            	presupuesto.setNotas(rs.getString("NOTAS"));
            	presupuesto.setIdioma(rs.getString("IDIOMA"));
            	presupuesto.setId_agente(rs.getString("AGENTE"));
            	presupuesto.setFecha_entrega(rs.getString("FECHAENTREGA"));
            	presupuesto.setCerrado(rs.getString("CERRADO"));
            	presupuesto.setConvenio(rs.getString("CONVENIO"));
            	presupuesto.setConvenio_det(rs.getString("CONVENIODET"));
			}
            
        } catch (Exception e) {
        	log.error("PresupuestoDAOImpl:traeListaPresupuestos error controlado",e);
            throw new Exception("Error en DAO, SP_PRESUP_SEL_PRESUPUESTOS"); 
        }finally {
            try{
            	if(null != rs){
   	        	  log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre ResultSet");
   	        	   rs.close();
   	           }
                if (null != st){
                	log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre CallableStatement");
                	st.close();
                }              
                if (null != con){
                	log.warn("PresupuestoDAOImpl:traeListaPresupuestos cierre Connection");
 		    	   con.close();
 	           } 
 	           
            }catch(Exception e){
            	log.error("PresupuestoDAOImpl:traeListaPresupuestos error", e);
            }
        }
        return presupuesto;
	}

}
