package cl.gmo.pos.venta.reporte.nuevo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ListadoBoletasBean;
import cl.gmo.pos.venta.web.facade.PosListadosFacade;
import cl.gmo.pos.venta.web.forms.ListadoBoletasForm;

public class ListadoBoletasHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	
	
	public ListadoBoletasForm cargaListadoBoletas(ListadoBoletasForm form,String sucursal){
	 	log.info("ListadoBoletasHelper:cargaListadoBoletas inicio");
	 	ArrayList<ListadoBoletasBean> listasBoletas = PosListadosFacade.traeListadoBoletas(form.getFecha_inicio(), sucursal, form.getNumero_boleta());
	 	form.setListaBoletas(listasBoletas);
	 	
	 	return form;
	 } 

}
