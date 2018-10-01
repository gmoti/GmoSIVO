package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.facade.PosBusquedaMedicosFacade;
import cl.gmo.pos.venta.web.forms.BusquedaMedicoForm;

public class BusquedaMedicosHelper extends Utils {
	Logger log = Logger.getLogger( this.getClass() );
	public ArrayList<OftalmologoBean> traeMedicos(BusquedaMedicoForm formulario){
		log.info("BusquedaMedicosHelper:traeMedicos inicio");
		ArrayList<OftalmologoBean> listaMedicos = new ArrayList<OftalmologoBean>();
		try{
			String nif = null;
			String apellido = null;
			String nombre = null;
			String codigo = null;
			
			if(null != formulario.getNif() && !(Constantes.STRING_BLANCO.equals(formulario.getNif()))){
				nif = formulario.getNif().toUpperCase();
			}
			if(null != formulario.getApellido()  && !(Constantes.STRING_BLANCO.equals(formulario.getApellido()))){
				apellido = formulario.getApellido().toUpperCase();
			}	
			if(null != formulario.getNombre()  && !(Constantes.STRING_BLANCO.equals(formulario.getNombre()))){
				nombre = formulario.getNombre().toUpperCase();
			}
			if(null != formulario.getCodigo()  && !(Constantes.STRING_BLANCO.equals(formulario.getCodigo()))){
				codigo = formulario.getCodigo().toUpperCase();
			}
			listaMedicos = PosBusquedaMedicosFacade.traeMedicos(codigo, nif, apellido, nombre);
			
		}catch(Exception ex){
			log.error("BusquedaMedicosHelper:traeMedicos error catch",ex);			
		}		
		return listaMedicos;
	}
	public boolean valida_campos(BusquedaMedicoForm formulario) {
		boolean estado = true;
		
		if (!Constantes.STRING_BLANCO.equals(formulario.getNif()) ||
				!Constantes.STRING_BLANCO.equals(formulario.getApellido()) || 
				!Constantes.STRING_BLANCO.equals(formulario.getNombre()) ||
				!Constantes.STRING_BLANCO.equals(formulario.getCodigo())) 
		{
			if (!Constantes.STRING_BLANCO.equals(formulario.getApellido())) {
				if (formulario.getApellido().length() < 4) {
					estado = false;
					formulario.setError(Constantes.STRING_ERROR_CLIENTE_MINIMO_CARACTERES);
				}
			}
			if (!Constantes.STRING_BLANCO.equals(formulario.getNombre())) {
				if (formulario.getNombre().length() < 4) {
					estado = false;
					formulario.setError(Constantes.STRING_ERROR_CLIENTE_MINIMO_CARACTERES);
				}
			}
			
		}
		else
		{
			estado = false;
			formulario.setError(Constantes.STRING_ERROR_CLIENTE);
		}


		return estado;
	}
	
	
}
