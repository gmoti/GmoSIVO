package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.MedicoDaoImpl;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;

public class PosBusquedaMedicosFacade {

	public static ArrayList<OftalmologoBean> traeMedicos(String codigo, String nif, String apellido, String nombre){
		ArrayList<OftalmologoBean> listaMedicos = new ArrayList<OftalmologoBean>();
		MedicoDaoImpl medicoDao = new MedicoDaoImpl();
		try{
			
			listaMedicos = medicoDao.traeDoctores(codigo, nif, apellido, nombre);
			
		}catch(Exception ex){
			ex.printStackTrace();			
		}		
		return listaMedicos;
	}
	
}
