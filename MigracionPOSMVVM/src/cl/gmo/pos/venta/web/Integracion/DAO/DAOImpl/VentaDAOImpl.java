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
import cl.gmo.pos.venta.web.Integracion.DAO.VentaDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.CajaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaDirectaBean;

public class VentaDAOImpl implements VentaDAO{
	Logger log = Logger.getLogger( this.getClass() );
	
	public VentaDirectaBean traeDatosGenericosVentaDirecta(String local) throws Exception
	{
		log.info("VentaDAOImpl:traeDatosGenericosVentaDirecta inicio");
		Connection con = null;
		CallableStatement cs = null;
		VentaDirectaBean ventaBean = new VentaDirectaBean();
		
		try {
			log.info("VentaDAOImpl:traeDatosGenericosVentaDirecta conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_VTA_DIRE_SEL_GENERICOS(?,?,?,?,?)}");
			cs.setString(1, local);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.registerOutParameter(5, Types.NUMERIC);
			cs.execute();
			
			ventaBean.setCambio(1);
			ventaBean.setCliente(cs.getInt(2));
			ventaBean.setNombre_cliente(cs.getString(3));
			ventaBean.setDivisa(cs.getString(4));
			ventaBean.setPorcentaje_descuento_max(cs.getInt(5));
			
		} catch (Exception e) {
			log.error("VentaDAOImpl:traeDatosGenericosVentaDirecta error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_SEL_GENERICOS"); 
		} finally {
            try{
             if (null != cs){
            	 log.warn("VentaDAOImpl:traeDatosGenericosVentaDirecta cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaDAOImpl:traeDatosGenericosVentaDirecta cierre Connection");
		    	   con.close();
	           } 
             
         }catch(Exception e){
        	 log.error("VentaDAOImpl:traeDatosGenericosVentaDirecta error", e);
         }
		}
		
		return ventaBean;
	}
	
	public String traeEncabezado_Ticket(String local) throws Exception
	{
		log.info("VentaDAOImpl:traeEncabezado_Ticket inicio");
		Connection con = null;
		CallableStatement cs = null;
		String encabezado_ticket = "";
		
		try {
			log.info("VentaDAOImpl:traeEncabezado_Ticket conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_VTA_DIRE_SEL_ENC_TICKET(?,?)}");
			cs.setString(1, local);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.execute();
			encabezado_ticket = cs.getString(2);
		} catch (Exception e) {
			log.error("VentaDAOImpl:traeEncabezado_Ticket error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_SEL_ENC_TICKET"); 
		} finally {
            try{
             if (null != cs){
            	 log.warn("VentaDAOImpl:traeEncabezado_Ticket cierre CallableStatement");
                 cs.close();
             }     
             if (null != con){
            	 log.warn("VentaDAOImpl:traeEncabezado_Ticket cierre Connection");
		    	   con.close();
	           } 
         }catch(Exception e){
        	 log.error("VentaDAOImpl:traeEncabezado_Ticket error", e);
         }
		}
		
		return encabezado_ticket;
	}
	
	public int traeCodigoVenta(String codigo_sucursal) throws Exception
	{
		log.info("VentaDAOImpl:traeCodigoVenta inicio");
		Connection con = null;
		CallableStatement cs = null;
		int numero_ticket = Constantes.INT_CERO;
		System.out.println("Traigo numero de ticket() ==>"+codigo_sucursal);
		try {
			log.info("VentaDAOImpl:traeCodigoVenta conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call  SP_VTA_DIRE_SEL_NUMERO_TICKET(?,?)}");
			cs.setString(1, codigo_sucursal );
			cs.registerOutParameter(2, Types.NUMERIC);
			cs.execute();
			numero_ticket = cs.getInt(2);
		} catch (Exception e) {
			log.error("VentaDAOImpl:traeCodigoVenta error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_SEL_NUMERO_TICKET"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("VentaDAOImpl:traeCodigoVenta cierre CallableStatement");
                   cs.close();
               }       
               if (null != con){
            	   log.warn("VentaDAOImpl:traeCodigoVenta cierre Connection");
		    	   con.close();
	           } 
           }catch(Exception e){
        	   log.error("VentaDAOImpl:traeCodigoVenta error", e);
           }
       }
	   System.out.println("Numero de Ticket => "+numero_ticket);
	   
	   return numero_ticket;
	}

	@Override
	public VentaDirectaBean traeNumerosCaja(String local) throws Exception {
		log.info("VentaDAOImpl:traeNumerosCaja inicio");
		VentaDirectaBean ventaBean = new VentaDirectaBean();
		Connection con = null;
		ArrayList<CajaBean> listaCajas = new ArrayList<CajaBean>();
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			log.info("VentaDAOImpl:traeNumerosCaja conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_VTA_DIRE_SEL_CAJAS(?,?)}");
			cs.setString(1, local );
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);

			while (rs.next()) {
				log.debug("VentaDAOImpl:traeNumerosCaja entrando ciclo while");
				CajaBean caja = new CajaBean();
				caja.setCodigo(rs.getInt("CDG"));
				caja.setDescripcion(rs.getString("DESCRIPCION"));
				listaCajas.add(caja);
			}
			
			ventaBean.setListaCajas(listaCajas);
		} catch (Exception e) {
			log.error("VentaDAOImpl:traeNumerosCaja error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_SEL_CAJAS"); 
       } finally {
              try{
            	  if (null != rs){
            		  log.warn("VentaDAOImpl:traeNumerosCaja cierre ResultSet");
                      rs.close();
                  }
               if (null != cs){
            	   log.warn("VentaDAOImpl:traeNumerosCaja cierre CallableStatement");
                   cs.close();
               }              
               if (null != con){
            	   log.warn("VentaDAOImpl:traeNumerosCaja cierre Connection");
		    	   con.close();
	           } 
           }catch(Exception e){
        	   log.error("VentaDAOImpl:traeNumerosCaja error", e);
           }
       }
 
	
		return ventaBean;
	}

	
	public void insertaVenta(VentaDirectaBean ventaDirecta, String local, String tipo_documento)throws Exception
	{
		log.info("VentaDAOImpl:insertaVenta inicio");
		Utils util = new Utils();
		Connection con = null;
		CallableStatement cs = null;
		
		try {
			log.info("VentaDAOImpl:insertaVenta conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_DIRE_INS_VENTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if (ventaDirecta.getNumero_ticket() == 0) {
				cs.setString(1, ventaDirecta.getEncabezado_ticket() + "/");
			}
			else
			{
				cs.setString(1, ventaDirecta.getEncabezado_ticket() + "/" + util.formato_Numero_Ticket(ventaDirecta.getNumero_ticket()));
			}
			cs.setString(2, ventaDirecta.getEncabezado_ticket());
			cs.setInt(3, ventaDirecta.getNumero_ticket());
			cs.setInt(4, ventaDirecta.getCliente());
			cs.setString(5, ventaDirecta.getAgente());
			cs.setString(6, ventaDirecta.getDivisa());
			cs.setDouble(7, ventaDirecta.getDescuento());
			cs.setString(8, ventaDirecta.getFecha());
			cs.setString(9, ventaDirecta.getAgente());
			cs.setInt(10, ventaDirecta.getCambio());
			cs.setString(11, ventaDirecta.getForma_pago());
			cs.setString(12, ventaDirecta.getTipo_albaran());
			cs.setInt(13, ventaDirecta.getValor_total());
			cs.setString(14, local);
			cs.setInt(15, ventaDirecta.getNumero_caja());
			cs.setString(16, ventaDirecta.getHora());
			cs.setString(17, tipo_documento);
			cs.registerOutParameter(18, Types.NUMERIC);
			
			cs.execute();
			ventaDirecta.setNumero_ticket(cs.getInt(18));
			System.out.println("(1)"+ventaDirecta.getEncabezado_ticket() + "/" + util.formato_Numero_Ticket(ventaDirecta.getNumero_ticket()));
			System.out.println("(2)"+ventaDirecta.getEncabezado_ticket());
			System.out.println("(3)"+ventaDirecta.getNumero_ticket());
			System.out.println("(4)"+ventaDirecta.getCliente());
			System.out.println("(5)"+ventaDirecta.getAgente());
			System.out.println("(6)"+ventaDirecta.getDivisa());
			System.out.println("(7)"+ventaDirecta.getDescuento());
			System.out.println("(8)"+ventaDirecta.getFecha());
			System.out.println("(9)"+ventaDirecta.getAgente());
			System.out.println("(10)"+ventaDirecta.getCambio());
			System.out.println("(11)"+ventaDirecta.getForma_pago());
			System.out.println("(12)"+ventaDirecta.getTipo_albaran());
			System.out.println("(13)"+ventaDirecta.getValor_total());
			System.out.println("(14)"+local);
			System.out.println("(15)"+ventaDirecta.getNumero_caja());
			System.out.println("(16)"+ventaDirecta.getHora());
			System.out.println("(17)"+tipo_documento);
			
		}  catch (Exception e) {
			log.error("VentaDAOImpl:insertaVenta error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_INS_VENTA"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("VentaDAOImpl:insertaVenta cierre CallableStatement");
                   cs.close();
               }   
               if (null != con){
            	   log.warn("VentaDAOImpl:insertaVenta cierre Connection");
		    	   con.close();
	           }                
           }catch(Exception e){
        	   log.error("VentaDAOImpl:insertaVenta error", e);
           }
       }
	}
	
	public Boolean insertaPago(String codigo_venta, String forma_pago, int cantidad, String fecha,
			String divisa, int cambio, int caja, int cantidaddiv, String devolucion,
			String confidencial, String agente, String numero_bono, double descuento, String tipo_documento)throws Exception
	{
		log.info("VentaDAOImpl:insertaPago inicio");
		boolean estado = true;
		Connection conn = null;
		CallableStatement cs = null;
		
		try {
			log.info("VentaDAOImpl:insertaPago conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			cs = conn.prepareCall("{call SP_PAGO_INS_PAGO_VTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, codigo_venta);
			cs.setString(2, forma_pago);
			cs.setInt(3,  cantidad);
			cs.setString(4, fecha.toString());
			cs.setString(5, divisa);
			cs.setInt(6, caja);
			cs.setInt(7, cantidaddiv);
			cs.setString(8, devolucion);
			cs.setString(9, confidencial);
			cs.setString(10, agente);
			cs.setString(11, numero_bono);
			cs.setInt(12, cambio);
			cs.setDouble(13, descuento);
			cs.setString(14, tipo_documento);
			
			cs.execute();
			
		} catch (Exception e) {
			estado = false;
			log.error("VentaDAOImpl:insertaPago error controlado",e);
			throw new Exception("Error en DAO, SP_PAGO_INS_PAGO_VTA");
		} finally {
			try{
				if (null != cs){
					log.warn("VentaDAOImpl:insertaPago cierre CallableStatement");
					cs.close();
				}  
				if (null != conn ){
					log.warn("VentaDAOImpl:insertaPago cierre Connection");
					conn.close();
				} 
				}catch(Exception e){
					log.error("VentaDAOImpl:insertaPago error", e);
			}
			
		}
		return estado;
	}
		
	public void ingresaDetalle(ProductosBean producto, int indice, String codigo_albaran, String local) throws Exception
	{
		log.info("VentaDAOImpl:ingresaDetalle inicio");
		Connection con = null;
		CallableStatement cs = null;
		System.out.println("Inserto venta directa <===="+indice+"<==>"+codigo_albaran+"<==>"+local);
		try {
			log.info("VentaDAOImpl:ingresaDetalle conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_DIRE_INS_LINEA(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, codigo_albaran + Constantes.STRING_SLASH + String.valueOf(indice));
			cs.setInt(2, indice);
			cs.setString(3, codigo_albaran);
			cs.setString(4, producto.getCodigo());
			cs.setString(5, producto.getCod_barra());
			cs.setString(6, producto.getDescripcion());
			cs.setInt(7, producto.getCantidad());
			cs.setInt(8, producto.getPrecio_sin_iva());
			cs.setDouble(9, producto.getDescuento());
			cs.setInt(10, producto.getPrecio());
			cs.setInt(11, producto.getPrecio_costo());
			cs.setString(12, local);
			
			cs.execute();
			
		}  catch (Exception e) {
			log.error("VentaDAOImpl:ingresaDetalle error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_INS_LINEA"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("VentaDAOImpl:ingresaDetalle cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaDAOImpl:ingresaDetalle cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaDAOImpl:ingresaDetalle error", e);
           }
       }
	}

	public  String insertaDocumento(String ticket, int documento,
			String tipo_documento, String fecha, int total, String fecha2,String local) throws Exception
	{
		log.info("VentaDAOImpl:insertaDocumento inicio");
		Connection con = null;
		CallableStatement cs = null;
		String res = "";
		try {
			log.info("VentaDAOImpl:insertaDocumento conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_PAGO_INS_DOCUMENTO_BE(?,?,?,?,?,?,?)}");
			//cs = con.prepareCall("{call SP_PAGO_INS_DOCUMENTO_QA(?,?,?,?,?,?,?)}");
			cs.setString(1, ticket);
			cs.setInt(2, documento);
			cs.setInt(3, total);
			cs.setString(4, tipo_documento);
			cs.setString(5, local);
			cs.setString(6, fecha);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();
			res = cs.getString(7);
		}  catch (Exception e) {
			log.error("VentaDAOImpl:insertaDocumento error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_PAGO_INS_DOCUMENTO"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("VentaDAOImpl:insertaDocumento cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaDAOImpl:insertaDocumento cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaDAOImpl:insertaDocumento error", e);
           }
       }
	   System.out.println("insertaDocumento Directa => "+res);
	   return res;
	}
	
	public String insertaMultiofertaCB(ProductosBean producto, String codigo_venta, int linea, String fecha, int numero_venta, String local)throws Exception
	{
		log.info("VentaDAOImpl:insertaMultiofertaCB inicio");
		Connection con = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		String mensaje = "ERROR";
		
		try {
			log.info("VentaDAOImpl:insertaMultiofertaCB conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_MTO_CB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, Constantes.STRING_A);
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			log.error("VentaDAOImpl:insertaMultiofertaCB error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_INS_MTO_CB"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaDAOImpl:insertaMultiofertaCB cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaDAOImpl:insertaMultiofertaCB cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaDAOImpl:insertaMultiofertaCB error", e);
           }
		
		}

		return mensaje;
	}
	
	public String insertaMultiofertaDetalle(int numero_multioferta, ProductosBean producto, int linea, String fecha, String local, String vta_codigo)throws Exception
	{
		log.info("VentaDAOImpl:insertaMultiofertaDetalle inicio");
		Connection con = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		String mje = Constantes.STRING_BLANCO;
		
		try {
			log.info("VentaDAOImpl:insertaMultiofertaDetalle conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VTA_PEDI_INS_MTO_LINEA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, "MON/" +  util.formato_Numero_Ticket(numero_multioferta) + "/" + linea);
			cs.setInt(2, linea);
			cs.setString(3, "MON/" + util.formato_Numero_Ticket(numero_multioferta));
			cs.setString(4, producto.getCodigo());
			cs.setString(5, local);
			cs.setInt(6, producto.getCantidad());
			cs.setInt(7, producto.getCantidad());
			cs.setInt(8, producto.getImporte());
			cs.setString(9, null);
			cs.setString(10, null);
			cs.setDouble(11, 0.0);
			cs.setDouble(12, 0.0);
			cs.setDouble(13, 0.0);
			cs.setInt(14, 0);
			cs.setInt(15, 0);
			cs.setString(16, null);
			cs.setString(17,null);
			cs.setInt(18, 0);
			cs.registerOutParameter(19, Types.NUMERIC);
			cs.registerOutParameter(20, Types.VARCHAR);
			cs.setString(21, vta_codigo);
			
			cs.execute();
			
			mje = cs.getString(20);
			
		} catch (Exception e) {
			log.error("VentaDAOImpl:insertaMultiofertaDetalle error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_DIRE_INS_MTO_LINEA"); 
		} finally {
              try{
               if (null != cs){
            	   log.warn("VentaDAOImpl:insertaMultiofertaDetalle cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("VentaDAOImpl:insertaMultiofertaDetalle cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("VentaDAOImpl:insertaMultiofertaDetalle error", e);
           }
		
		}
		return mje;

	}
	
	public Boolean insertaPagoAlbaran(String codigo_venta, String forma_pago, int cantidad, String fecha,
			String divisa, int cambio, int caja, int cantidaddiv, String devolucion,
			String confidencial, String agente, String numero_bono, double descuento, String tipo_documento)throws Exception
	{
		log.info("VentaDAOImpl:insertaPago inicio");
		boolean estado = true;
		Connection conn = null;
		CallableStatement cs = null;
		
		try {
			log.info("VentaDAOImpl:insertaPago conectando base datos");
			conn = ConexionFactory.INSTANCE.getConexion();
			
			System.out.println(" exec SP_PAGO_INS_PAGO_VTA('"+codigo_venta+"','"+forma_pago+"',"+cantidad+"','"+fecha.toString()+"','"+divisa+"',"+caja+","+cantidaddiv+",'"+devolucion+"','"+confidencial+"','"+agente+"','"+numero_bono+"',"+cambio+","+descuento+",'"+tipo_documento+"');");
			
			cs = conn.prepareCall("{call SP_PAGO_INS_PAGO_VTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, codigo_venta);
			cs.setString(2, forma_pago);
			cs.setInt(3,  cantidad);
			cs.setString(4, fecha.toString());
			cs.setString(5, divisa);
			cs.setInt(6, caja);
			cs.setInt(7, cantidaddiv);
			cs.setString(8, devolucion);
			cs.setString(9, confidencial);
			cs.setString(10, agente);
			cs.setString(11, numero_bono);
			cs.setDouble(12, cambio);
			cs.setDouble(13, descuento);
			cs.setString(14, tipo_documento);
			
			cs.execute();			
			
		} catch (Exception e) {
			estado = false;
			log.error("VentaDAOImpl:insertaPago error controlado",e);
			throw new Exception("Error en DAO, SP_PAGO_INS_PAGO_VTA" + e.getMessage());
		} finally {
			try{
				if (null != cs){
					log.warn("VentaDAOImpl:insertaPago cierre CallableStatement");
					cs.close();
				}  
				if (null != conn ){
					log.warn("VentaDAOImpl:insertaPago cierre Connection");
					conn.close();
				} 
				}catch(Exception e){
					log.error("VentaDAOImpl:insertaPago error", e);
			}
			
		}
		return estado;
	}
}
