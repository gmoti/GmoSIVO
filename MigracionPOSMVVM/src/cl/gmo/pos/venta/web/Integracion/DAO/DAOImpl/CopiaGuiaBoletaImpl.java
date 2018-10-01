package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;



import oracle.jdbc.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.CopiaGuiaBoletaDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;
import cl.gmo.pos.venta.web.forms.CopiaGuiaLineaBoletaForm;

public class CopiaGuiaBoletaImpl implements CopiaGuiaBoletaDAO {
	
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public CopiaGuiaBoletaForm traeCopiaGuiaBoleta(String numeroDocumento,
			String tipoDocumento) throws Exception {
		log.info("CopiaGuiaBoletaImpl:traeCopiaGuiaBoleta inicio");
		CopiaGuiaBoletaForm informeDocu = new CopiaGuiaBoletaForm();
 		if(Constantes.STRING_B.equals(tipoDocumento)){
 			informeDocu = traeBoleta(numeroDocumento);
 		}else{
 			informeDocu =traeGuia(numeroDocumento);
 		}
		return informeDocu;
	}
	private CopiaGuiaBoletaForm traeBoleta(String numeroDocumento){
		log.info("CopiaGuiaBoletaImpl:traeBoleta inicio");
		Connection con = null;
		ResultSet direcCb = null;
		ResultSet direcLn = null;
		ResultSet pedidCb = null;
		ResultSet pedidLn = null;
		ResultSet direcLnTotal = null;
		ResultSet pedidLnTotal = null;
		ResultSet isAnticipo = null;
		CallableStatement cs = null;
		CopiaGuiaBoletaForm cabecera = new CopiaGuiaBoletaForm();
		String encargo = Constantes.STRING_BLANCO;
		try{
			log.info("CopiaGuiaBoletaImpl:traeBoleta conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_COPIA_BOLETA(?,?,?,?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			cs.setString(1, numeroDocumento);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			cs.registerOutParameter(7, OracleTypes.CURSOR);
			cs.registerOutParameter(8, OracleTypes.CURSOR);
			cs.execute();
			direcCb = (ResultSet)cs.getObject(2);
			direcLn = (ResultSet)cs.getObject(3);
			pedidCb = (ResultSet)cs.getObject(4);
			pedidLn = (ResultSet)cs.getObject(5);
			direcLnTotal = (ResultSet)cs.getObject(6);
			pedidLnTotal = (ResultSet)cs.getObject(7);
			isAnticipo = (ResultSet)cs.getObject(8);
			
			
			ArrayList<CopiaGuiaLineaBoletaForm> lineas = new ArrayList<CopiaGuiaLineaBoletaForm>();
			
			while(direcCb.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				cabecera.setDefine("direcCb");
				cabecera.setRut(direcCb.getString("NIF")+direcCb.getString("LETRANIF"));
				cabecera.setCliente(direcCb.getString("APELLI")+ Constantes.STRING_COMA + direcCb.getString("NOMBRE"));
				cabecera.setFecha(direcCb.getString("FECHA"));
				cabecera.setFechaEntrega(direcCb.getString("FECHA"));
				cabecera.setFechaPedido(direcCb.getString("FECHA"));
				cabecera.setNumeroAlbaran(direcCb.getString("CDG"));
				cabecera.setHora(direcCb.getString("HORA"));
				cabecera.setTienda(direcCb.getString("NOMBRE_T"));
				cabecera.setVendedor(direcCb.getString("DESCRIPCION"));
				cabecera.setCaja(direcCb.getString("NUMTPV"));
				cabecera.setTipo("BOLETA");
				encargo = direcCb.getString("CDG");
				cabecera.setTipoVenta("ALBARAN");
			
			}
			while(direcLn.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				CopiaGuiaLineaBoletaForm linea = new CopiaGuiaLineaBoletaForm();
				linea.setDescripcion(direcLn.getString("DESCRIPCION"));
				linea.setCantidad(direcLn.getString("CANTIDAD"));
				linea.setPrecio(direcLn.getString("PRECIOIVA"));
				linea.setDescuento(direcLn.getString("DTO"));
				linea.setTotal(direcLn.getString("PRECIOIVADEF"));
				linea.setEncargo(encargo);
				linea.setCodigo(direcLn.getString("CODIGOBARRAS"));
				linea.setMultioferta(direcLn.getString("MONTACB"));
				lineas.add(linea);
			}
			while(pedidCb.next()){
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				cabecera.setDefine("pedidCb");
				cabecera.setRut(pedidCb.getString("NIF")+pedidCb.getString("LETRANIF"));
				cabecera.setFecha(pedidCb.getString("FECHAPEDIDO"));
				cabecera.setFechaEntrega( pedidCb.getString("FECHAENTREGA"));
				cabecera.setCliente(pedidCb.getString("APELLI")+ Constantes.STRING_COMA + pedidCb.getString("NOMBRE"));
				cabecera.setFechaPedido(pedidCb.getString("FECHAPEDIDO"));
				cabecera.setNumeroAlbaran(pedidCb.getString("CDG"));
				cabecera.setHora(pedidCb.getString("HORA"));
				cabecera.setTienda(pedidCb.getString("NOMBRE_T"));
				cabecera.setVendedor(pedidCb.getString("DESCRIPCION"));
				cabecera.setTotalPagadoBoleta(pedidCb.getString("ANTICIPOTOT"));
				cabecera.setCaja(pedidCb.getString("NUMTPV"));
				cabecera.setTipo("BOLETA");
				encargo = pedidCb.getString("CDG");
				cabecera.setTipoVenta("ENCARGO");
			
			}
			while(pedidLn.next()){		
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				CopiaGuiaLineaBoletaForm linea = new CopiaGuiaLineaBoletaForm();
				linea.setDescripcion(pedidLn.getString("DESCRIPCION"));
				linea.setCantidad(pedidLn.getString("CANTIDAD"));
				linea.setPrecio(pedidLn.getString("PRECIOIVA"));
				linea.setDescuento(pedidLn.getString("DTO"));
				linea.setTotal(pedidLn.getString("PRECIOIVADEF"));
				linea.setEncargo(encargo);
				linea.setCodigo(pedidLn.getString("CODIGOBARRAS"));
				linea.setMultioferta(pedidLn.getString("MONTACB"));
				lineas.add(linea);
			}
			while(direcLnTotal.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				if(null!=direcLnTotal.getString("TOTAL_VENTA")){
					cabecera.setTotalVenta(direcLnTotal.getString("TOTAL_VENTA"));
				}
			}
			while(pedidLnTotal.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				if(null!=pedidLnTotal.getString("TOTAL_VENTA")){
					cabecera.setTotalVenta(pedidLnTotal.getString("TOTAL_VENTA"));
				}
			}while(isAnticipo.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeBoleta entrando ciclo while");
				if(null!=isAnticipo.getString("TOTAL_ANTICIPO")){
					cabecera.setTotalAnticipo(isAnticipo.getString("TOTAL_ANTICIPO"));
				}
			}
			cabecera.setLineas(lineas);			
			
			return cabecera;
		}catch(Exception ex){
			log.error("CopiaGuiaBoletaImpl:traeBoleta error controlado",ex);
			
		} finally {
            try{
                if (null != direcCb){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
                	direcCb.close();
                }
                if (null != direcLn){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
                	direcLn.close();
                }
                if (null != pedidCb){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
                	pedidCb.close();
                }
                if (null != pedidLn){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
                	pedidLn.close();
                }
                if(null != direcLnTotal){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
                	direcLnTotal.close();
                }
                if(null != pedidLnTotal){
    				log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
    				pedidLnTotal.close();
    			}
    			if(null != isAnticipo){
    				log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre ResultSet");
    				isAnticipo.close();
    			}
                if (null != cs){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre CallableStatement");
             	   cs.close();
                }  
                if (null != con){
                	log.warn("CopiaGuiaBoletaImpl:traeBoleta cierre Connection");
             	   con.close();
                }
    			
            }catch(SQLException e){
            	log.error("CopiaGuiaBoletaImpl:traeBoleta error", e);
            }
        }
		return cabecera;
	}
	
	private CopiaGuiaBoletaForm  traeGuia(String numeroDocumento){
		log.info("CopiaGuiaBoletaImpl:traeGuia inicio");
		Connection con = null;
		ResultSet guiaCab = null;
		ResultSet guiaLin = null;
		ResultSet guiaTotal = null;
		CallableStatement cs = null;
		String encargo = Constantes.STRING_BLANCO;
	
		CopiaGuiaBoletaForm cabecera = new CopiaGuiaBoletaForm();
		try{
			log.info("CopiaGuiaBoletaImpl:traeGuia conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_COPIA_GUIA(?,?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, numeroDocumento);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			
			cs.execute();
			guiaCab = (ResultSet)cs.getObject(2);
			guiaLin = (ResultSet)cs.getObject(3);
			guiaTotal = (ResultSet)cs.getObject(4);
			
			ArrayList<CopiaGuiaLineaBoletaForm> lineas = new ArrayList<CopiaGuiaLineaBoletaForm>();
			while(guiaCab.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeGuia entrando ciclo while");
				cabecera.setDefine("guiaCab");
				cabecera.setRut(guiaCab.getString("NIF")+guiaCab.getString("LETRANIF"));
				cabecera.setCliente(guiaCab.getString("APELLI")+ Constantes.STRING_COMA + guiaCab.getString("NOMBRE"));
				cabecera.setFecha(guiaCab.getString("FECHAPEDIDO"));
				cabecera.setFechaEntrega(guiaCab.getString("FECHAENTREGA"));
				cabecera.setFechaPedido(guiaCab.getString("FECHAPEDIDO"));
				cabecera.setNumeroAlbaran(guiaCab.getString("CDG"));
				cabecera.setHora(guiaCab.getString("HORA"));
				cabecera.setTienda(guiaCab.getString("NOMBRE_T"));
				cabecera.setVendedor(guiaCab.getString("DESCRIPCION"));
				cabecera.setCaja(guiaCab.getString("NUMTPV"));
				cabecera.setTipo("GUÍA");
				encargo = guiaCab.getString("CDG");
				cabecera.setTipoVenta("GUIA");
			}
			while(guiaLin.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeGuia entrando ciclo while");
				CopiaGuiaLineaBoletaForm linea = new CopiaGuiaLineaBoletaForm();
				linea.setEncargo(encargo);
				linea.setDescripcion(guiaLin.getString("DESCRIPCION"));
				linea.setCantidad(guiaLin.getString("CANTIDAD"));
				linea.setPrecio(guiaLin.getString("PRECIOIVA"));
				linea.setDescuento(guiaLin.getString("DTO"));
				linea.setTotal(guiaLin.getString("PRECIOIVADEF"));
				linea.setCodigo(guiaLin.getString("CODIGOBARRAS"));
				linea.setMultioferta(guiaLin.getString("MONTACB"));
				lineas.add(linea);
			}
			while(guiaTotal.next()){	
				log.debug("CopiaGuiaBoletaImpl:traeGuia entrando ciclo while");
				cabecera.setTotalVenta(guiaTotal.getString("TOTAL_VENTA"));
			}
		
			cabecera.setLineas(lineas);
			return cabecera;
		}catch(Exception ex){
			log.error("CopiaGuiaBoletaImpl:traeGuia error controlado",ex);
		} finally {
            try{
                if (null != guiaCab){
                	log.warn("CopiaGuiaBoletaImpl:traeGuia cierre ResultSet");
                	guiaCab.close();
                }
                if (null != guiaLin){
                	log.warn("CopiaGuiaBoletaImpl:traeGuia cierre ResultSet");
                	guiaLin.close();
                }
        		if(null != guiaTotal){
        			log.warn("CopiaGuiaBoletaImpl:traeGuia cierre ResultSet");
        			guiaTotal.close();
        		}
                if (null != cs){
                	log.warn("CopiaGuiaBoletaImpl:traeGuia cierre CallableStatement");
             	   cs.close();
                }  
                if (null != con){
                	log.warn("CopiaGuiaBoletaImpl:traeGuia cierre Connection");
             	   con.close();
                }  
            }catch(SQLException e){
            	log.error("CopiaGuiaBoletaImpl:traeGuia error", e);
            }
        }
		return cabecera;	
	}
}
