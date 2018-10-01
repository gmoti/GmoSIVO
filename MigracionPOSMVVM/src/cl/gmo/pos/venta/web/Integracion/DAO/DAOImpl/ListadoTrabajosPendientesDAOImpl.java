/**
 * 
 */
package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.ListadoTrabajosPendientesDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesCabeceraBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesLineaBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesTotalBean;

/**
 * @author Advise64
 *
 */
public class ListadoTrabajosPendientesDAOImpl implements
		ListadoTrabajosPendientesDAO {
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ListadosTrabajosPendientesBean traeListadosTrabajosPendientes(
			String codigo, String nifCliente, String agente, String divisa,
			String fInicio, String fTermino, String fpago, String sucursal,
			String cerrado, String listadoDetallado, String tipoPedido,
			String anulado) throws Exception {
		log.info("ListadoTrabajosPendientesDAOImpl:traeListadosTrabajosPendientes inicio");
		ListadosTrabajosPendientesBean listadosTrabajosPendientesBean = new ListadosTrabajosPendientesBean();
		ArrayList<ListadosTrabajosPendientesCabeceraBean> cabeceraLista = new ArrayList<ListadosTrabajosPendientesCabeceraBean>();
		ArrayList<ListadosTrabajosPendientesLineaBean> lineaLista = new ArrayList<ListadosTrabajosPendientesLineaBean>();
		ArrayList<ListadosTrabajosPendientesTotalBean> totalLista = new ArrayList<ListadosTrabajosPendientesTotalBean>();
		
		Connection con = null;
		CallableStatement cs = null;
		ResultSet cabeceraRs = null;
		ResultSet detalleRs = null;
		ResultSet totalRs = null;
		try{
			log.info("ListadoTrabajosPendientesDAOImpl:traeListadosTrabajosPendientes conectando base datos");
		con = ConexionFactory.INSTANCE.getConexion();
		cs = con.prepareCall("{ call SP_INFORME_SEL_PEDIDOS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		if(Constantes.STRING_BLANCO.equals(codigo)){
			cs.setString(1, null);
		}else{
			cs.setString(1, codigo);
		}
		if(Constantes.STRING_BLANCO.equals(nifCliente)){
			cs.setString(2, null);
		}else{
			cs.setString(2, nifCliente);
		}
		if(Constantes.STRING_BLANCO.equals(agente)){
			cs.setString(3, null);
		}else{
			cs.setString(3, agente);
		}
		if(Constantes.STRING_BLANCO.equals(divisa) || Constantes.STRING_CERO.equals(divisa)){
			cs.setString(4, null);
		}else{
			cs.setString(4, divisa);
		}
		if(Constantes.STRING_BLANCO.equals(fInicio)){
			cs.setString(5, null);
		}else{
			cs.setString(5, fInicio);
		}
		if(Constantes.STRING_BLANCO.equals(fTermino)){
			cs.setString(6, null);
		}else{
			cs.setString(6, fTermino);
		}
		if(Constantes.STRING_BLANCO.equals(fpago)){
			cs.setString(7, null);
		}else{
			cs.setString(7, fpago);
		}
		if(Constantes.STRING_BLANCO.equals(sucursal)){
			cs.setString(8, null);
		}else{
			cs.setString(8, sucursal);
		}
		if(Constantes.STRING_BLANCO.equals(cerrado) || Constantes.STRING_CERO.equals(cerrado)){
			cs.setString(9, null);
		}else{
			cs.setString(9, cerrado);
		}
		if(Constantes.STRING_BLANCO.equals(listadoDetallado)){
			cs.setString(10, null);
		}else{
			cs.setString(10, listadoDetallado);
		}
		if(Constantes.STRING_BLANCO.equals(tipoPedido)){
			cs.setString(11, null);
		}else{
			cs.setString(11, tipoPedido);
		}
		if(Constantes.STRING_BLANCO.equals(anulado) || Constantes.STRING_CERO.equals(anulado)){
			cs.setString(12, null);
		}else{
			cs.setString(12, anulado);
		}
		
		cs.registerOutParameter(13, OracleTypes.CURSOR);
		cs.registerOutParameter(14, OracleTypes.CURSOR);
		cs.registerOutParameter(15, OracleTypes.CURSOR);
		
		cs.execute();
		
		cabeceraRs = (ResultSet) cs.getObject(13);
		detalleRs = (ResultSet) cs.getObject(14);
		totalRs = (ResultSet) cs.getObject(15);
		
		while (cabeceraRs.next())
        {
			log.debug("ListadoTrabajosPendientesDAOImpl:traeListadosTrabajosPendientes entrando ciclo while");
			ListadosTrabajosPendientesCabeceraBean cabecera = new ListadosTrabajosPendientesCabeceraBean();
			cabecera.setSerieNumero(cabeceraRs.getString("CDG"));
			cabecera.setFecha(cabeceraRs.getString("FECHAPEDIDO"));
			cabecera.setNumeroCaja(cabeceraRs.getString("NUMERO"));
			cabecera.setCliente(cabeceraRs.getString("CLIENTE"));
			cabecera.setNombre(cabeceraRs.getString("NOMBRE"));
			cabecera.setApellido(cabeceraRs.getString("APELLI"));
			cabecera.setDescuento(cabeceraRs.getString("DTO"));
			cabecera.setFormaPago(cabeceraRs.getString("DESCRIPCION"));
			cabecera.setNota(cabeceraRs.getString("NOTAS"));
			//cabecera.setAlbaran(cabeceraRs.getString("ALBVTCB"));
			cabeceraLista.add(cabecera);
        }
		while (detalleRs.next())
        {
			log.debug("ListadoTrabajosPendientesDAOImpl:traeListadosTrabajosPendientes entrando ciclo while");
			ListadosTrabajosPendientesLineaBean linea = new ListadosTrabajosPendientesLineaBean();
			linea.setCodigo(detalleRs.getString("PEDVTCB"));
			linea.setArticulo(detalleRs.getString("CODIGOBARRAS"));
			linea.setDescripcion(detalleRs.getString("DESCRIPCION"));
			linea.setCantidad(detalleRs.getString("CANTIDAD"));
			linea.setPrecio(detalleRs.getString("PRECIOIVA"));
			linea.setDescuento(detalleRs.getString("DTO"));
			linea.setTipo(detalleRs.getString("TIPO"));
			lineaLista.add(linea);
		}
		while (totalRs.next())
        {
			log.debug("ListadoTrabajosPendientesDAOImpl:traeListadosTrabajosPendientes entrando ciclo while");
			ListadosTrabajosPendientesTotalBean total = new ListadosTrabajosPendientesTotalBean();
			total.setCodigoTotal(totalRs.getString("PEDVTCB"));
			total.setTotal(totalRs.getString("TOTAL"));
			total.setNumeroBoleta(totalRs.getString("NUMERO"));
			totalLista.add(total);
        }
		listadosTrabajosPendientesBean.setCabecera(cabeceraLista);
		listadosTrabajosPendientesBean.setLinea(lineaLista);
		listadosTrabajosPendientesBean.setTotal(totalLista);
		}catch (Exception e) {
			 try{
				 if(null != cabeceraRs){
		     			log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
		     			cabeceraRs.close();
		            }
		     		if(null != detalleRs){
	     				log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
	     				detalleRs.close();
	     			}
		     		if(null != totalRs){
	     				log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
	     				totalRs.close();
	     			}
	             if (null != cs){
	            	 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre CallableStatement");
	                 cs.close();
	             }     
	             if (null != con){
	            	 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre Connection");
			    	  con.close();
		           } 
	              
	            
	             
	         }catch(Exception ex){
	        	 log.error("ListadoTotalDiaDAOImpl:traeListasTotales error", ex);
	         }
		}
		return listadosTrabajosPendientesBean;
	}

}
