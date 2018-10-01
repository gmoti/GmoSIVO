package cl.gmo.pos.venta.web.facade;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.MedicoDaoImpl;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;


public class PosMedicoFacade {

	public static int ingresaMedico(OftalmologoBean medico){
		
		int respuesta = Constantes.INT_MENOS_UNO;	
		MedicoDaoImpl medicoDao = new MedicoDaoImpl();		
		
		try{
			
			respuesta = medicoDao.ingresaMedico(medico);			
			
		}catch(Exception ex){
			ex.printStackTrace();
			return respuesta;
		}
		
		return respuesta;
	}
	
	
	public static String traeCodigoDoctor(){
		String codigo = null;
		MedicoDaoImpl medicoDao = new MedicoDaoImpl();	
		try{
			
			codigo = medicoDao.traeCdgDoctor();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return codigo;
	}
	
	
	public static OftalmologoBean traeMedico(String codigo){
		
		OftalmologoBean medico = new OftalmologoBean();
		MedicoDaoImpl medicoDao = new MedicoDaoImpl();	
		try{
			medico = medicoDao.traeMedico(codigo, null, null, null);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return medico;
	}
}
