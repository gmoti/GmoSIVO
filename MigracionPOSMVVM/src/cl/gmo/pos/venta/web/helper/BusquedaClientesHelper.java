package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.facade.PosClientesFacade;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;

public class BusquedaClientesHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public ArrayList<ClienteBean> traeClientes(String nif, String nombre, String apellido, String codigo)
	{
		log.info("BusquedaClientesHelper:traeClientes inicio");
		ArrayList<ClienteBean> lista_clientes = new ArrayList<ClienteBean>();
		try{
			if(null != nif){
				nif = nif.trim();
				if(Constantes.STRING_BLANCO.equals(nif)){
					nif = null;
				}
			}
			if(null != nombre){
				nombre = nombre.trim();
				if(Constantes.STRING_BLANCO.equals(nombre)){
					nombre = null;
				}
			}
			if(null != apellido){
				apellido = apellido.trim();
				if(Constantes.STRING_BLANCO.equals(apellido)){
					apellido = null;
				}
			}
			lista_clientes = PosClientesFacade.traeClientes(nif, nombre, apellido, codigo);
			
			return lista_clientes;
		}catch(Exception ex){
			log.error("BusquedaClientesHelper:traeClientes error catch",ex);
			return lista_clientes;
		}
		
	}
	public boolean valida_campos(BusquedaClientesForm formulario) {
		
		boolean estado = true;
		if (!Constantes.STRING_BLANCO.equals(formulario.getNif()) ||
				!Constantes.STRING_BLANCO.equals(formulario.getNombre()) || 
				!Constantes.STRING_BLANCO.equals(formulario.getApellido())) 
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
