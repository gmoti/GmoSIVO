package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.FolioBean;

public interface CambioFolioDAO {

	public ArrayList<FolioBean> traeFolios(String local);
	
}
