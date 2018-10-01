package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.driver.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.ListadoBoletasDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.ListadoBoletasBean;

public class ListadoBoletasDAOImpl implements ListadoBoletasDAO{
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ArrayList<ListadoBoletasBean> traeListasBoletas(String fecha, String local,
			String numeroBoleta) throws Exception {
		log.info("ListadoBoletasDAOImpl:traeListasBoletas inicio");
		ArrayList<ListadoBoletasBean> listasTotalesDias = new ArrayList<ListadoBoletasBean>();
		
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		Utils util = new Utils();
		log.info("ListadoBoletasDAOImpl:traeListasBoletas conectando base datos");
		
		try{
		con = ConexionFactory.INSTANCE.getConexion();
		cs = con.prepareCall("{ call SP_INFORME_SEL_BOLETAS(?,?,?,?)}");
		cs.setString(1, local);
		cs.setString(2, fecha);
		if(Constantes.STRING_BLANCO.equals(numeroBoleta)){
			cs.setInt(3,0);
		}else{
			cs.setInt(3,Integer.parseInt(numeroBoleta));
		}
		
		cs.registerOutParameter(4, OracleTypes.CURSOR);
		cs.execute();
		
		rs= (ResultSet) cs.getObject(4);
		
		
		while (rs.next()){
			log.info("ListadoBoletasDAOImpl:traeListasBoletas entrando ciclo while");
			ListadoBoletasBean listasTotalesDia = new ListadoBoletasBean();
			listasTotalesDia.setNumero(rs.getString("NUMERO"));
			listasTotalesDia.setCodigo(rs.getString("CODIGO"));
			listasTotalesDia.setTipo(rs.getString("TIPO"));
			listasTotalesDia.setImporte(rs.getInt("IMPORTE"));
			listasTotalesDia.setFecha(util.formatoFechaEspecial(rs.getString("FECHA")));
			listasTotalesDias.add(listasTotalesDia);
		}
		}catch (Exception e) {
			log.error("ListadoBoletasDAOImpl:traeListasBoletas error controlado",e);
		}finally{
			try{
			  if(null != rs){
				  log.warn("ListadoBoletasDAOImpl:traeListasBoletas cierre ResultSet");
                  rs.close();
			  }
			  if (null != cs){
           	   log.warn("ListadoBoletasDAOImpl:traeListasBoletas cierre CallableStatement");
                  cs.close();
              }  
              if (null != con){
           	   log.warn("ListadoBoletasDAOImpl:traeListasBoletas cierre Connection");
           	   con.close();
              }
              
          }catch(Exception e){
       	   log.error("LiberacionDAOImpl:cambioEstadoLiberacion error", e);
          }			
		}
		return listasTotalesDias;
	}

}
