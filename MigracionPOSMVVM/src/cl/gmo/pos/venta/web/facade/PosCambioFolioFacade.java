package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.CambioFolioDAOImpl;
import cl.gmo.pos.venta.web.beans.FolioBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.forms.CambioFolioForm;

public class PosCambioFolioFacade {

	public static ArrayList<FolioBean> traeFolios(String local){
		ArrayList<FolioBean> lista = new ArrayList<FolioBean>();
		CambioFolioDAOImpl cambioDao = new CambioFolioDAOImpl();
		try{
			
			lista = cambioDao.traeFolios(local);
			
		}catch(Exception ex){			
			ex.printStackTrace();
		}		
		return lista;
	}
	
	public static FolioBean modificarFolio(FolioBean folio, String local){
		
		CambioFolioDAOImpl cambioDao = new CambioFolioDAOImpl();
		String retorno= null;
		FolioBean folioRespuesta= null;
		try{
			
			folioRespuesta  = cambioDao.modificarFolio(folio, local);
			
		}catch(Exception ex){			
			ex.printStackTrace();
		}		
		return folioRespuesta;
	}

	public static String traeCampoLibre(String local) {
		String retorno= null;
		CambioFolioDAOImpl cambioDao = new CambioFolioDAOImpl();
		try{
			
			retorno  = cambioDao.traeCampoLibre(local);
			
		}catch(Exception ex){			
			ex.printStackTrace();
		}		
		return retorno;
	}
	
}
