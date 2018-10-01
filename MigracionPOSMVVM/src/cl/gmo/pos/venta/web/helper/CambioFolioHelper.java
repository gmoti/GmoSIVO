package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.FolioBean;
import cl.gmo.pos.venta.web.facade.PosCambioFolioFacade;
import cl.gmo.pos.venta.web.forms.CambioFolioForm;

public class CambioFolioHelper  extends Utils {
	
	Logger log = Logger.getLogger( this.getClass() );
	
	public CambioFolioForm cargarPagina(CambioFolioForm formulario, String local){
		ArrayList<FolioBean> lista = new ArrayList<FolioBean>();
		try{
			
			lista = PosCambioFolioFacade.traeFolios(local);
			
			if(null != lista){				
				for(FolioBean folio: lista){
					
					if(null != folio.getDesdeBoleta()){
						formulario.setDesdeBoleta(folio.getDesdeBoleta());
					}else{
						formulario.setDesdeBoleta(0);
					}
					
					if(null != folio.getHastaBoleta()){
						formulario.setHastaBoleta(folio.getHastaBoleta());
					}else{
						formulario.setHastaBoleta(0);
					}
					
					if(null != folio.getDesdeGuia()){
						formulario.setDesdeGuia(folio.getDesdeGuia());
					}else{
						formulario.setDesdeGuia(0);
					}
					
					if(null != folio.getHastaGuia()){
						formulario.setHastaGuia(folio.getHastaGuia());
					}else{
						formulario.setHastaGuia(0);
					}
					
					if(null != folio.getDesdeNota()){
						formulario.setDesdeNota(folio.getDesdeNota());
					}else{
						formulario.setDesdeNota(0);
					}					
					
					if(null != folio.getHastaNota()){
						formulario.setHastaNota(folio.getHastaNota());	
					}else{
						formulario.setHastaNota(0);	
					}						
				}				
			}			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return formulario;
	}

	
	public FolioBean modificarFolio(CambioFolioForm formulario, String local){
		FolioBean folio = new FolioBean();
		String retorno= null;
		FolioBean folioRespuesta = null;
		try{
			
			folio.setDesdeGuia(formulario.getDesdeGuia());
			folio.setHastaGuia(formulario.getHastaGuia());
			
			folio.setDesdeBoleta(formulario.getDesdeBoleta());
			folio.setHastaBoleta(formulario.getHastaBoleta());
			
			folio.setDesdeNota(formulario.getDesdeNota());
			folio.setHastaNota(formulario.getHastaNota());
			
			folioRespuesta = PosCambioFolioFacade.modificarFolio(folio, local);
			
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
		return folioRespuesta;
	}

	public String  valida(int desde, int hasta, String documento, String local){
		
		String campolibre = PosCambioFolioFacade.traeCampoLibre(local);
		
		try{
			if(desde > hasta){
				return "El inicio de "+documento+" no puede ser mayor al t\u00E9rmino.";
			}else{
				int diferencia = hasta - desde;
				if (!"PROPIO".equals(campolibre.trim())) {
					if (!"OUTLET".equals(campolibre)) {
						if(diferencia > 500){
							return "La cantidad de Folios de "+documento+" no puede ser mayor a 500";
						}
					}
					
				}
				
			}
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return "exito";
	}
}
