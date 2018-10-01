package cl.gmo.pos.venta.web.helper;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.ConvenioLnBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;

public class BusquedaConveniosHelper extends Utils {

	Logger log = Logger.getLogger( this.getClass() );
	public ArrayList<ConvenioBean> traeConvenios(BusquedaConveniosForm formulario) {
		ArrayList<ConvenioBean> lista = new ArrayList<ConvenioBean>();
		
		if (null == formulario.getCodigo() || Constantes.STRING_BLANCO.equals(formulario.getCodigo())) {
			formulario.setCodigo(null);
		}
		if (null == formulario.getNombre() || Constantes.STRING_BLANCO.equals(formulario.getNombre())) {
			formulario.setNombre(null);
		}
		if (null == formulario.getEmpresa() || Constantes.STRING_BLANCO.equals(formulario.getEmpresa())) {
			formulario.setEmpresa(null);
		}
		try {
			lista = PosUtilesFacade.traeConvenios(formulario.getCodigo(), 
														formulario.getNombre(), formulario.getEmpresa());
		} catch (Exception e) {
			log.error("BusquedaConveniosHelper:traeConvenios error catch",e);
		}
		return lista;
	}
	public void traeDescuentosConvenio(BusquedaConveniosForm formulario, String id) {
		
		formulario.setSel_dto(Constantes.STRING_BLANCO);
		formulario.setSel_fpago(Constantes.STRING_BLANCO);
		formulario.setLista_formas_pago_familias(null);
		try {
			formulario.setLista_formas_pago(PosUtilesFacade.traeDescuentosConvenios(id));
		} catch (Exception e) {
			log.error("BusquedaConveniosHelper:traeConvenios error catch",e);
		}
		
	}
	public void traeConveniolnFamilias(BusquedaConveniosForm formulario) {
		String indice = formulario.getIndice();
		formulario.setAccion(Constantes.STRING_BLANCO);
		
		ConvenioLnBean convenioln = formulario.getLista_formas_pago().get(Integer.parseInt(indice));
		formulario.setSel_fpago(convenioln.getForma_pago());
		formulario.setSel_dto(String.valueOf(convenioln.getDto()));
		formulario.setSel_convenioln_ident(convenioln.getIdent());
		try {
			formulario.setLista_formas_pago_familias(PosUtilesFacade.traeDescuentosConveniosFamilias(convenioln.getIdent()));
		} catch (Exception e) {
			log.error("BusquedaConveniosHelper:traeConveniolnFamilias error catch",e);
		}
		
	}
	
}
