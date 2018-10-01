package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.InformeOpticoBean;
import cl.gmo.pos.venta.web.facade.PosVentaFacade;
import cl.gmo.pos.venta.web.forms.InformeOpticoForm;

public class InformeOpticoHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public void traeInformeOptico(String codigo, Date fecha, String numero,InformeOpticoForm form)
	 {
		log.info("InformeOpticoHelper:traeInformeOptico inicio");
		try {
			ArrayList <InformeOpticoBean>  listaGraduaciones =PosVentaFacade.traeInformeOptico(codigo, fecha, numero);
			for(InformeOpticoBean tmp:listaGraduaciones){
				form.setCdgCli(tmp.getCdgCli());
				form.setGraduacionCli(tmp.getGraduacionCli());
				form.setNombreCli(tmp.getNombreCli());
				form.setTelCli(tmp.getTelCli());
				form.setDomicilioCli(tmp.getDomicilioCli());
				form.setFechaNacCli(formatoFechaEspecial(tmp.getFechaNacCli()));
				form.setListaGraduaciones(tmp.getListaGraduaciones());
	
				
			}
		} catch (Exception e) {
			log.error("InformeOpticoHelper:traeInformeOptico error catch",e);
		}
		

	 }

}
