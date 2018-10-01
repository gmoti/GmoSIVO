package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.InformeBusquedaProductoBean;
import cl.gmo.pos.venta.web.facade.PosVentaFacade;
import cl.gmo.pos.venta.web.forms.InformeBusquedaProductoForm;

public class InformeBusquedaProductoHelper extends Utils {
	Logger log = Logger.getLogger( this.getClass() );
	 public void traeInformeBusquedaProducto(String codigo, String descripcion, InformeBusquedaProductoForm form){
		 log.info("InformeBusquedaProductoHelper:traeInformeBusquedaProducto inicio");
		 try {		 
			 ArrayList <InformeBusquedaProductoBean> informeBusquedaProducto = PosVentaFacade.traeInformeBusquedaProducto(codigo, descripcion);
			 form.setListaBusquedaProducto(informeBusquedaProducto);
		} catch (Exception e) {
			log.error("InformeBusquedaProductoHelper:traeInformeBusquedaProducto error catch",e);
		}
	 }
	
	
}

