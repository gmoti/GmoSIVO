/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.TipoViaBean;
import cl.gmo.pos.venta.web.facade.PosClientesFacade;
import cl.gmo.pos.venta.web.forms.ClienteForm;

/**
 *
 * @author Advice70
 */
public class ClienteHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
    public ArrayList<AgenteBean> traeAgentes(String local)
    {
    	log.info("ClienteHelper:traeAgentes inicio");
    	ArrayList<AgenteBean> listaAgentes = new ArrayList<AgenteBean>();
    	try {
    		listaAgentes = PosClientesFacade.traeAgentes(local);
			
		} catch (Exception e) {
			log.error("ClienteHelper:traeAgentes error catch",e);
		}
        return listaAgentes;
    	
    }

    public ArrayList<TipoViaBean> traeTipoVias()
    {
    	log.info("ClienteHelper:traeTipoVias inicio");
    	ArrayList<TipoViaBean> listaTipoVias = new ArrayList<TipoViaBean>();
    	try {
    		listaTipoVias = PosClientesFacade.traeTipoVias();
			
		} catch (Exception e) {
			log.error("ClienteHelper:traeTipoVias error catch",e);
		}
        return listaTipoVias;
    	
    }
    
    
    
    public boolean validaBusqueda(ClienteForm formulario) {
    	log.info("ClienteHelper:validaBusqueda inicio");
    	if (null == formulario.getRut() && null == formulario.getNombres()
                && null ==  formulario.getApellidos()&& formulario.getCodigo() == 0 ) {
            
            formulario.setError(Constantes.STRING_TEXTO_CLIENTE_INGRESA_PARAMETRO);
            return false;
        }
        else
        {
            return  true;
        }
        
    }
    
       
    public void ingresoCliente(ClienteForm formulario){
    	log.info("ClienteHelper:ingresoCliente inicio");
    	int respuesta = Constantes.INT_CERO;
    	Utils util = new Utils();
    	try{
    		
    		if(null != formulario.getRut() && !("".equals(formulario.getRut().trim()))) {
				
				if(null != formulario.getDv() && !("".equals(formulario.getDv().trim()))){
					
					ClienteBean cliente = new ClienteBean();
		    		
		    		cliente.setCodigo(String.valueOf(formulario.getCodigo()));
		    		
		    		if(null != formulario.getRut() && !(Constantes.STRING_BLANCO.equals(formulario.getRut().trim()))){
		    			cliente.setNif(formulario.getRut().toUpperCase());
		    		}else{
		    			cliente.setNif(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getVia() && !(Constantes.STRING_BLANCO.equals(formulario.getVia().trim()))){
		    			cliente.setDireccion(formulario.getVia().toUpperCase());
		    		}else{
		    			cliente.setDireccion(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getCod_postal() && !(Constantes.STRING_BLANCO.equals(formulario.getCod_postal().trim()))){
		    			cliente.setCodigo_postal(formulario.getCod_postal().toUpperCase());
		    		}else{
		    			cliente.setCodigo_postal(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getLocalidad() && !(Constantes.STRING_BLANCO.equals(formulario.getLocalidad().trim()))){
		    			cliente.setPoblacion(formulario.getLocalidad().toUpperCase());
		    		}else{
		    			cliente.setPoblacion(Constantes.STRING_BLANCO);
		    		}   		
		    		
		    		
		    		cliente.setProvincia_cliente(formulario.getProvincia_cliente());
		    		//cliente.setProvincia(formulario.getProvincia_cliente());
		    				
		    		if(null != formulario.getEmail() && !(Constantes.STRING_BLANCO.equals(formulario.getEmail().trim()))){
		    			cliente.setEmail(formulario.getEmail().toUpperCase());
		    		}else{
		    			cliente.setEmail(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getAgente() && !(Constantes.STRING_BLANCO.equals(formulario.getAgente().trim()))){
		    			cliente.setAgente(formulario.getAgente().toUpperCase());
		    		}else{
		    			cliente.setAgente(Constantes.STRING_BLANCO);
		    		}       		
		    		
		    		if(null != formulario.getApellidos() && !(Constantes.STRING_BLANCO.equals(formulario.getApellidos().trim()))){
		    			cliente.setApellido(formulario.getApellidos().toUpperCase());
		    		}else{
		    			cliente.setApellido(Constantes.STRING_BLANCO);
		    		}  		
		    		
		    		if(null != formulario.getNombres() && !(Constantes.STRING_BLANCO.equals(formulario.getNombres().trim()))){
		    			cliente.setNombre(formulario.getNombres().toUpperCase());
		    		}else{
		    			cliente.setNombre(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getTipo_via() && !(Constantes.STRING_BLANCO.equals(formulario.getTipo_via().trim()))){
		    			cliente.setTipo_via(formulario.getTipo_via().toUpperCase());
		    		}else{
		    			cliente.setTipo_via(Constantes.STRING_BLANCO);
		    		}
		    		 		
		    		if(null != formulario.getNumero() && !(Constantes.STRING_BLANCO.equals(formulario.getNumero().trim()))){
		    			cliente.setNumero(formulario.getNumero().toUpperCase());
		    		}else{
		    			cliente.setNumero(Constantes.STRING_BLANCO);
		    		}    		
		    		
		    		if(null != formulario.getBloque() && !(Constantes.STRING_BLANCO.equals(formulario.getBloque().trim()))){
		    			cliente.setBloque(formulario.getBloque().toUpperCase());
		    		}else{
		    			cliente.setBloque(Constantes.STRING_BLANCO);
		    		} 
		    		
		    		if(null != formulario.getSexo()){
		    			cliente.setSexo(formulario.getSexo());
		    		}else{
		    			cliente.setSexo(Constantes.STRING_BLANCO);
		    		}
		    		if(null == formulario.getTelefono() || formulario.getTelefono().equals("")){
		    			cliente.setFono_casa(Constantes.STRING_BLANCO);	    		
			    		cliente.setFono_trabajo(Constantes.STRING_BLANCO);
		    		}else{
		    			cliente.setFono_casa(formulario.getCod_telefono()+formulario.getTelefono());	    		
			    		cliente.setFono_trabajo(formulario.getCod_telefono()+formulario.getTelefono());
		    			
		    		}
		    		
		    		if(null == formulario.getTelefono_movil() || formulario.getTelefono_movil().equals("")){
		    			cliente.setFono_movil(Constantes.STRING_BLANCO);	    		
		    		}else{
		    			cliente.setFono_movil(formulario.getTelefono_movil());	    					    				    			
		    		}
		    		
		    		/*if(null == formulario.getSinfono()){
			    		cliente.setFono_casa(formulario.getCod_telefono()+formulario.getTelefono());	    		
			    		cliente.setFono_trabajo(formulario.getCod_telefono()+formulario.getTelefono());
		    		}else{
		    			cliente.setFono_casa(Constantes.STRING_BLANCO);	    		
			    		cliente.setFono_trabajo(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null == formulario.getSinmovil()){
		    			cliente.setFono_movil(formulario.getTelefono_movil());
		    		}else{
		    			cliente.setFono_movil(Constantes.STRING_BLANCO);
		    		}*/
		    		
		    		if(null != formulario.getContacto() && !(Constantes.STRING_BLANCO.equals(formulario.getContacto().trim()))){
		    			cliente.setPersona_contacto(formulario.getContacto().toUpperCase());
		    		}else{
		    			cliente.setPersona_contacto(Constantes.STRING_BLANCO);
		    		} 
		    		
		    		if(null != formulario.getContacto() && !(Constantes.STRING_BLANCO.equals(formulario.getContacto().trim()))){
		    			cliente.setPersona_contacto(formulario.getContacto().toUpperCase());
		    		}else{
		    			cliente.setPersona_contacto(Constantes.STRING_BLANCO);
		    		} 
		    		
		    		if(!formulario.getFnacimiento().equals("false") && null != formulario.getFnacimiento() && !(Constantes.STRING_BLANCO.equals(formulario.getFnacimiento().trim()))){
		    			System.out.println("Paso por aca getFecha verdadero ====>"+formulario.getFnacimiento());
		    			cliente.setFecha_nac(formulario.getFnacimiento().toUpperCase());
		    		}else{
		    			System.out.println("Paso por aca getFecha");
		    			cliente.setFecha_nac(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getDv() && !(Constantes.STRING_BLANCO.equals(formulario.getDv().trim()))){
		    			cliente.setDvnif(formulario.getDv().toUpperCase());
		    		}else{
		    			cliente.setDvnif(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getProfesion() && !(Constantes.STRING_BLANCO.equals(formulario.getProfesion().trim()))){
		    			cliente.setProfesion(formulario.getProfesion().toUpperCase());
		    		}else{
		    			cliente.setProfesion(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getLocal() && !(Constantes.STRING_BLANCO.equals(formulario.getLocal().trim()))){
		    			cliente.setLocal(formulario.getLocal().toUpperCase());
		    		}else{
		    			cliente.setLocal(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getOcio() && !(Constantes.STRING_BLANCO.equals(formulario.getOcio().trim()))){
		    			cliente.setOcio(formulario.getOcio().toUpperCase());
		    		}else{
		    			cliente.setOcio(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getGiro() && !(Constantes.STRING_BLANCO.equals(formulario.getGiro()))){
		    			int giro = util.isEntero(formulario.getGiro());
		    			cliente.setGiro(giro);
		    		}else{
		    			cliente.setGiro(-1);
		    		}
		    		
		    		if(null != formulario.getDescripcionGiro() && !(Constantes.STRING_BLANCO.equals(formulario.getDescripcionGiro()))){
		    			cliente.setGiro_desc(formulario.getDescripcionGiro());
		    		}else{
		    			cliente.setGiro_desc(Constantes.STRING_BLANCO);
		    		}
		    		
		    		if(null != formulario.getCodigo_cliente_agregado_factura() && !(Constantes.STRING_BLANCO.equals(formulario.getCodigo_cliente_agregado_factura()))){
		    			cliente.setCliente_cliente(formulario.getCodigo_cliente_agregado_factura());
		    		}else{
		    			cliente.setCliente_cliente(null);
		    		}
		    		System.out.println("Cliente postal => "+formulario.getMk_correo_postal()+","+formulario.getMk_correo_electronico()+","+formulario.getMk_telefonia()+","+formulario.getMk_sms()+","+formulario.getMk_nodata());
		    		if(null != formulario.getMk_correo_postal() && !(Constantes.STRING_BLANCO.equals(formulario.getMk_correo_postal() ))){
		    			
		    			cliente.setMk_correo_postal(formulario.getMk_correo_postal() );
		    		}else{
		    			cliente.setMk_correo_postal("-1");
		    		}
		    		
		    		if(null != formulario.getMk_correo_electronico() && !(Constantes.STRING_BLANCO.equals(formulario.getMk_correo_electronico() ))){
		    			cliente.setMk_correo_electronico(formulario.getMk_correo_electronico() );
		    		}else{
		    			cliente.setMk_correo_electronico("-1");
		    		}
		    		
		    		if(null != formulario.getMk_telefonia() && !(Constantes.STRING_BLANCO.equals(formulario.getMk_telefonia()))){
		    			cliente.setMk_telefonia(formulario.getMk_telefonia() );
		    		}else{
		    			cliente.setMk_telefonia("-1");
		    		}
		    		if(null != formulario.getMk_sms() && !(Constantes.STRING_BLANCO.equals(formulario.getMk_sms()))){
		    			cliente.setMk_sms(formulario.getMk_sms() );
		    		}else{
		    			cliente.setMk_sms("-1");
		    		}
		    		if(null != formulario.getMk_nodata() && !(Constantes.STRING_BLANCO.equals(formulario.getMk_nodata()))){
		    			cliente.setMk_nodata(formulario.getMk_nodata() );
		    		}else{
		    			cliente.setMk_nodata("-1");
		    		}
		    			
		    		respuesta  = PosClientesFacade.ingresoCliente(cliente);
		    		
		    		if(respuesta == 1){
		    			formulario.setExito(String.valueOf(Constantes.STRING_ACTION_EXISTE));
		    			formulario.setEstaGrabado(2);
		    		}else if(respuesta == -1){
		    			formulario.setExito(String.valueOf(Constantes.STRING_FALSE));
		    			formulario.setEstaGrabado(0);
		    		}else if(respuesta == 2){
		    			formulario.setExito(String.valueOf(Constantes.STRING_ACTION_MODIFICADO));
		    			formulario.setEstaGrabado(0);
		    		}else{
		    			//formulario.cleanForm();    
		    			formulario.setExito(String.valueOf(Constantes.STRING_TRUE));
		    			formulario.setEstaGrabado(0);
		    			cliente.setCodigo(String.valueOf(respuesta));
		    			formulario.setCodigo(respuesta);
		    		}					
				} else{
					formulario.setExito("ERROR_RUT");
				}
			}else{
				formulario.setExito("ERROR_RUT");
			}
    		
    	}catch(Exception ex){
    		log.error("ClienteHelper:ingresoCliente error catch",ex);
    	}	
    	
    }

    
       
    public int traeCodigoLocalCliente(String local){
    	log.info("ClienteHelper:traeCodigoLocalCliente inicio");
    	String valCodLocal="";
    	String valCodigo;
    	int codigoLocalConfig=0;
    	try{
    		
    		valCodLocal = PosClientesFacade.traeCodigoLocal(local);
    		
    		try{
    			if(null !=valCodLocal){
    				codigoLocalConfig = Integer.parseInt(valCodLocal);
    			}
    		}catch(Exception ex){
    			ex.printStackTrace();
    			codigoLocalConfig=0;
    		}
    		
    		if(214 > codigoLocalConfig){
    			valCodigo = Constantes.STRING_ACTION_UNO_SIETE_CEROS;
    		}else{
    			valCodigo = Constantes.STRING_ACTION_UNO_CUATRO_CEROS;
    		}
    		
    		int valRangoInicio = codigoLocalConfig * Integer.parseInt(valCodigo);
    		int valRangoFinal =  codigoLocalConfig * Integer.parseInt(valCodigo)+ Integer.parseInt(valCodigo);
    		
    		int codCliente = PosClientesFacade.traeCodigoCliente(valRangoInicio, valRangoFinal);
    		
    		codCliente = codCliente +1;
    		
    		return codCliente;    		
    		
    	}catch(Exception ex){
    		log.error("ClienteHelper:traeCodigoLocalCliente error catch",ex);
    		return  0;
    	}   	
    }
    
}
