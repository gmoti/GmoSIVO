package cl.gmo.pos.venta.web.helper;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ListasTotalesDiaBean;
import cl.gmo.pos.venta.web.facade.PosListadosFacade;
import cl.gmo.pos.venta.web.forms.ListadoTotalDiaForm;

public class ListadoTotalDiaHelper extends Utils{
	
	Logger log = Logger.getLogger( this.getClass() );
	 public void cargaListadosTotalesDia(ListadoTotalDiaForm form,String sucursal){
		 log.info("ListadoTotalDiaHelper:cargaListadosTotalesDia inicio");
		 ListasTotalesDiaBean listasTotalesDia = PosListadosFacade.traeListasTotales(form.getFecha_inicio(), sucursal);
		 form.setListaTotalDiaVenta(listasTotalesDia.getListaTotalDiaVenta());
		 form.setListaTotalDiaAnticipo(listasTotalesDia.getListaTotalDiaAnticipo());
		 form.setListaTotalDiaDevolucion(listasTotalesDia.getListaTotalDiaDevolucion());
		 form.setListaTotalDiaEncargo(listasTotalesDia.getListaTotalDiaEncargo());
		 form.setListaTotalDiaEntrega(listasTotalesDia.getListaTotalDiaEntrega());
		 form.setNumero_movimientos(listasTotalesDia.getNumero_movimientos());
		 
	 }
	public boolean valida_campos(ListadoTotalDiaForm formulario) {
		boolean estado = true;
		if (Constantes.STRING_BLANCO.equals(formulario.getFecha_inicio())) {
			formulario.setError(Constantes.STRING_ERROR_CLIENTE);
			estado = false;
		}
		return estado;
	} 
}
