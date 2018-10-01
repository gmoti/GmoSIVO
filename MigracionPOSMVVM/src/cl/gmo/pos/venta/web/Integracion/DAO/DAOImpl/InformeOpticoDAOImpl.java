package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.InformeOpticoDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.GraduacionInformeOpticoBean;
import cl.gmo.pos.venta.web.beans.InformeOpticoBean;

public class InformeOpticoDAOImpl implements InformeOpticoDAO{
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ArrayList <InformeOpticoBean> traeGraduaciones(String codigo, Date fecha, String numero)
			throws Exception {
		log.info("InformeOpticoDAOImpl:traeGraduaciones inicio");
		Connection conexion = null;
		ResultSet graduaciones = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		
		try{
			log.info("InformeOpticoDAOImpl:traeGraduaciones conectando base datos");
			conexion = ConexionFactory.INSTANCE.getConexion();

				
			System.out.println("codigo =>"+codigo+"<=>fecha =>"+fecha+"<=> numero"+numero);
			cs = conexion.prepareCall("{ call SP_INFORME_SEL_OPTICO(?,?,?,?)}");
			
			cs.setInt(1, Integer.parseInt(codigo));
			cs.setString(2, null);
			cs.setString(3, null);
			cs.registerOutParameter(4, OracleTypes.CURSOR);

			cs.execute();

			graduaciones = (ResultSet)cs.getObject(4);
			
			ArrayList <InformeOpticoBean> listaGraduaciones = new ArrayList <InformeOpticoBean>();
			ArrayList <GraduacionInformeOpticoBean> informes = new ArrayList <GraduacionInformeOpticoBean>();
			InformeOpticoBean informe = new InformeOpticoBean();
			
			while (graduaciones.next()){
				
				log.debug("InformeOpticoDAOImpl:traeGraduaciones entrando ciclo while");
				informe.setCdgCli(graduaciones.getString("CDG"));
				if (null != graduaciones.getString("DIRECCION")) {
					informe.setDomicilioCli(graduaciones.getString("DIRECCION"));
					if (null != graduaciones.getString("POBLACION")) {
						informe.setDomicilioCli(informe.getDomicilioCli() + " " + graduaciones.getString("POBLACION"));
					}
				}
				else
				{
					informe.setDomicilioCli(Constantes.STRING_BLANCO);
				}
				informe.setNombreCli(graduaciones.getString("NOMRECLI")+" "+graduaciones.getString("APELLI"));
				informe.setTelCli(graduaciones.getString("TFNCASA"));
				informe.setFechaNacCli(graduaciones.getString("FECNAC"));

				GraduacionInformeOpticoBean graduacion = new GraduacionInformeOpticoBean();
				graduacion.setNombreDoc(graduaciones.getString("NOMBRE") + " " + graduaciones.getString("APELLI_DOC"));
				graduacion.setApellidoDoc(graduaciones.getString("APELLI"));
				graduacion.setFechaGrad(util.formatoFechaEspecial(graduaciones.getString("FECHA")));

				graduacion.setEsferaD(Double.toString(graduaciones.getDouble("ODESFERA")));
				graduacion.setCilindroD(Double.toString(graduaciones.getDouble("ODCILINDRO")));
				if(null==graduaciones.getString("ODEJE")){
					graduacion.setEjeD(Constantes.STRING_BLANCO);
				}else{
					graduacion.setEjeD(graduaciones.getString("ODEJE"));
				}
				
				graduacion.setAdicionD(Double.toString(graduaciones.getDouble("ODADICION")));
				graduacion.setEsferaCercaD(Double.toString(graduaciones.getDouble("ODESFERAC")));
				graduacion.setDistNPLejosD(Double.toString(graduaciones.getDouble("ODNP1")));
				graduacion.setDistNPCercaD(Double.toString(graduaciones.getDouble("ODNP2")));
				graduacion.setAvSinD(Double.toString(graduaciones.getDouble("ODAVSC")));
				graduacion.setAvConD(Double.toString(graduaciones.getDouble("ODAVCC")));
				
				if(null==graduaciones.getString("OIOBSERVA")){
					graduacion.setObserv(Constantes.STRING_BLANCO);	
				}
				graduacion.setEsferaI(Double.toString(graduaciones.getDouble("OIESFERA")));
				graduacion.setCilindroI(Double.toString(graduaciones.getDouble("OICILINDRO")));
				
				if(null==graduaciones.getString("OIEJE")){
					graduacion.setEjeI(Constantes.STRING_BLANCO);
				}else{
					graduacion.setEjeI(graduaciones.getString("OIEJE"));
				}
				
				graduacion.setAdicionI(graduaciones.getString("OIADICION"));
				graduacion.setEsferaCercaI(Double.toString(graduaciones.getDouble("OIESFERAC")));
				graduacion.setAdicionI(Double.toString(graduaciones.getDouble("OIADICION")));
				graduacion.setDistNPLejosI(Double.toString(graduaciones.getDouble("OINP1")));
				graduacion.setDistNPCercaI(Double.toString(graduaciones.getDouble("OINP2")));
				graduacion.setAvSinI(Double.toString(graduaciones.getDouble("OIAVSC")));
				graduacion.setAvConI(Double.toString(graduaciones.getDouble("OIAVCC")));
				
				informes.add(graduacion);
				informe.setListaGraduaciones(informes);
				listaGraduaciones.add(informe);
			}
			return listaGraduaciones;
		}catch(Exception e){
			log.error("InformeOpticoDAOImpl:traeGraduaciones error controlado",e);
			throw new Exception("Error en DAO al buscar Usuario");
		}finally{
			try{
				if (null != graduaciones){
					log.warn("InformeOpticoDAOImpl:traeGraduaciones cierre ResultSet");
					graduaciones.close();
				}
				if (null != cs){
					log.warn("InformeOpticoDAOImpl:traeGraduaciones cierre CallableStatement");
					cs.close();
				}      
				if (null != conexion){
					log.warn("InformeOpticoDAOImpl:traeGraduaciones cierre Connection");
					conexion.close();
				} 
			}catch(Exception e){
				log.error("InformeOpticoDAOImpl:traeGraduaciones error", e);
			}            
		}

	}

}
