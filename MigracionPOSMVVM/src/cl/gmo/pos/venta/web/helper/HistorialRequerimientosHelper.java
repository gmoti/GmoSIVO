package cl.gmo.pos.venta.web.helper;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;

public class HistorialRequerimientosHelper{
	
	 Logger log = Logger.getLogger(this.getClass()); 
	 
	 public ArrayList<FamiliaBean> traeFamilias(String familia)
	 {
	    	log.info("HistorialRequerimientosHelper:traeFamilias inicio");
	        return PosUtilesFacade.traeFamilias(familia);
     }
	 
	 public ArrayList<SubFamiliaBean> traeSubFamilias(String subfamilia)
	 {
	    	log.info("HistorialRequerimientosHelper:traeSubFamilias inicio");
	        return PosUtilesFacade.traeSubFamilias(subfamilia);
	 }
	 
	 public ArrayList<GrupoFamiliaBean> traeGrupoFamilias(String familia, String subfamilia)
     {
	    	log.info("HistorialRequerimientosHelper:traeGrupoFamilias inicio");
	        return PosUtilesFacade.traeGrupoFamilias(familia,subfamilia);
     }

}
