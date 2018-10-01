package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import oracle.jdbc.driver.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.ListadoPresupuestosDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoCabeceraBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoLineaBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoTotalBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestosBean;


public class ListaPresupuestosDAOImpl implements ListadoPresupuestosDAO{
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ListaPresupuestosBean traeListadoPresupuestos(
			String sucursal, String nifCliente, String cerrado,
			String codigoPresupuesto, String codigoProducto, String fInicio,
			String fTermino, String Agente, String divisa, String fpago)
			throws Exception {

		log.info("ListaPresupuestosDAOImpl:traeListadoPresupuestos inicio");
		Connection con = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		ResultSet rsCabecera = null;
		ResultSet rsDetalle = null;
		ResultSet rsTotal = null;
		
		ArrayList<ListaPresupuestoCabeceraBean> listaPresupuestoCabeceraBean = new ArrayList<ListaPresupuestoCabeceraBean>();
		ArrayList<ListaPresupuestoLineaBean> listaPresupuestoLineaBean = new ArrayList<ListaPresupuestoLineaBean>();
		ArrayList<ListaPresupuestoTotalBean> listaPresupuestoTotalBean = new ArrayList<ListaPresupuestoTotalBean>();
		ListaPresupuestosBean listaPresupuestosBean = new ListaPresupuestosBean();
		
		try{
		log.info("ListaPresupuestosDAOImpl:traeListadoPresupuestos conectando base datos");
		con = ConexionFactory.INSTANCE.getConexion();
		
		cs = con.prepareCall("{ call SP_INFORME_SEL_PRESUPUESTO(?,?,?,?,?,?,?,?,?,?,?,?)}");
		/*cs.setString(1, "T004");
		cs.setString(2, null);
		cs.setString(3, null);
		cs.setString(4, null);
		cs.setString(5, "02/01/11");
		cs.setString(6, "02/01/11");
		cs.setString(7, null);
		cs.setString(8, null);
		cs.setString(9, null);*/
		cs.setString(1, sucursal);
		if(Constantes.STRING_BLANCO.equals(nifCliente)){
			cs.setString(2, null);
		}else{
			cs.setString(2, nifCliente);
		}
		if(Constantes.STRING_CERO.equals(cerrado)){
			cs.setString(3, null);
		}else{
			cs.setString(3, cerrado);
		}
		if(Constantes.STRING_BLANCO.equals(codigoPresupuesto) || Constantes.STRING_CERO.equals(codigoPresupuesto)){
			cs.setString(4, null);
		}else{
			cs.setString(4, codigoPresupuesto);
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
		if(Constantes.STRING_BLANCO.equals(Agente)){
			cs.setString(7, null);
		}else{
			cs.setString(7, Agente);
		}
		if(Constantes.STRING_CERO.equals(divisa)){
			cs.setString(8, null);
		}else{
			cs.setString(8, divisa);
		}
		if(Constantes.STRING_CERO.equals(fpago)){
			cs.setString(9, null);
		}else{
			cs.setString(9, fpago);
		}
		cs.registerOutParameter(10, OracleTypes.CURSOR);
		cs.registerOutParameter(11, OracleTypes.CURSOR);
		cs.registerOutParameter(12, OracleTypes.CURSOR);

		cs.execute();
		
		rsCabecera = (ResultSet) cs.getObject(10);
		
		while (rsCabecera.next()){
			log.debug("ListaPresupuestosDAOImpl:traeListadoPresupuestos entrando ciclo while");
			ListaPresupuestoCabeceraBean listaPresupuestosCaberaBean = new ListaPresupuestoCabeceraBean();
			listaPresupuestosCaberaBean.setCodigoCabecera(rsCabecera.getString("CDG"));
			listaPresupuestosCaberaBean.setFecha(util.formatoFechaEspecial(rsCabecera.getString("FECHAPEDIDO")));
			listaPresupuestosCaberaBean.setAgente(rsCabecera.getString("AGENTE"));
			listaPresupuestosCaberaBean.setCliente(rsCabecera.getString("CLIENTE"));
			listaPresupuestosCaberaBean.setNombre(rsCabecera.getString("NOMBRE"));
			listaPresupuestosCaberaBean.setNif_cliente(rsCabecera.getString("NIF"));
			listaPresupuestosCaberaBean.setApellido(rsCabecera.getString("APELLI"));
			listaPresupuestosCaberaBean.setDescuento(rsCabecera.getString("DTO"));
			listaPresupuestosCaberaBean.setCambio(rsCabecera.getString("CAMBIO"));
			listaPresupuestoCabeceraBean.add(listaPresupuestosCaberaBean);
		}
		System.out.println("_________________________DETALLE________________________________");
		rsDetalle = (ResultSet) cs.getObject(11);
		while (rsDetalle.next()){	
			log.debug("ListaPresupuestosDAOImpl:traeListadoPresupuestos entrando ciclo while");
			ListaPresupuestoLineaBean listaPresupuestosLineaBean = new ListaPresupuestoLineaBean();
			listaPresupuestosLineaBean.setCodigo(rsDetalle.getString("PREVTCB"));
			listaPresupuestosLineaBean.setCodigoBarra(rsDetalle.getString("CODIGOBARRAS"));
			listaPresupuestosLineaBean.setDescripcion(rsDetalle.getString("DESCRIPCION"));
			listaPresupuestosLineaBean.setCantidad(rsDetalle.getString("CANTIDAD"));
			listaPresupuestosLineaBean.setPrecioIva(rsDetalle.getString("PRECIOIVA"));
			listaPresupuestosLineaBean.setDescuento(rsDetalle.getString("DTO"));
			listaPresupuestoLineaBean.add(listaPresupuestosLineaBean);
		}
		rsTotal  = (ResultSet) cs.getObject(12);
		System.out.println("________________________TOTAL_________________________________");
		while (rsTotal.next()){
			log.debug("ListaPresupuestosDAOImpl:traeListadoPresupuestos entrando ciclo while");
			ListaPresupuestoTotalBean listaPresupuestosTotalBean = new ListaPresupuestoTotalBean();
			listaPresupuestosTotalBean.setCodigoTotal(rsTotal.getString("PREVTCB"));
			listaPresupuestosTotalBean.setTotal(rsTotal.getString("TOTAL"));
			listaPresupuestoTotalBean.add(listaPresupuestosTotalBean);
		}
		listaPresupuestosBean.setListaPresupuestoCabeceras(listaPresupuestoCabeceraBean);
		listaPresupuestosBean.setListaPresupuestoLineas(listaPresupuestoLineaBean);
		listaPresupuestosBean.setListaPresupuestoTotales(listaPresupuestoTotalBean);
		
		}catch (Exception e) {
			 try{
				 if(null != rsCabecera){
	            	 log.warn("ListaPresupuestosDAOImpl:traeListadoPresupuestos cierre ResultSet");
	            	 rsCabecera.close();
	             }
	             if(null != rsTotal){
	            	 log.warn("ListaPresupuestosDAOImpl:traeListadoPresupuestos cierre ResultSet");
	            	 rsTotal.close();
	             }
	             if(null != rsDetalle){
	            	 log.warn("ListaPresupuestosDAOImpl:traeListadoPresupuestos cierre ResultSet");
	            	 rsDetalle.close();
	             }
	             if (null != cs){
	            	 log.warn("ListaPresupuestosDAOImpl:traeListadoPresupuestos cierre CallableStatement");
	                 cs.close();
	             }     
	             if (null != con){
	            	 log.warn("ListaPresupuestosDAOImpl:traeListadoPresupuestos cierre Connection");
			    	   con.close();
		           } 
	             
	         }catch(Exception ex){
	        	 log.error("ListaPresupuestosDAOImpl:traeListadoPresupuestos error", ex);
	         }
		}
		return listaPresupuestosBean;
	}

}
