/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.actions;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.ClienteForm;
import cl.gmo.pos.venta.web.helper.ClienteHelper;

/**
 *
 * @author Advice70
 */
public class ClienteDispatchActions {
	Logger log = Logger.getLogger( this.getClass() );
    ClienteHelper helper = new ClienteHelper();
    
    public ClienteDispatchActions(){}
    
    public ClienteForm cargaInicial(String local)
    {    
    	ClienteForm formulario = new ClienteForm(); 
    	log.info("ClienteDispatchActions:cargaInicial inicio");
    	formulario.setListaAgentes(helper.traeAgentes(local));
    	formulario.setListaTipoVia(helper.traeTipoVias());
    	formulario.setListaProvincia(helper.traeProvincias());
    	log.info("ClienteDispatchActions:cargaInicial fin");
    	return formulario;
    	
    }
    
    public ClienteForm cargaFormulario(ClienteForm form,Session request)
    {
    	log.info("ClienteDispatchActions:cargaFormulario inicio");
    	Session session = request;
    	String local = String.valueOf(session.getAttribute("sucursal"));
    	
        ClienteForm formulario = (ClienteForm)form;
        formulario.setEstaGrabado(0);
        //formulario.setCodigo(codCliente);
        cargaInicial(local);
        String agente_sucursal = (String) session.getAttribute(Constantes.STRING_USUARIO);
    	formulario.setAgente(agente_sucursal);
    	formulario.setAgente_sucursal(agente_sucursal);
    	formulario.setPagina_status("inicioPag");
    	formulario.setMk_telefonia("-1");
    	formulario.setMk_correo_postal("-1");
    	formulario.setMk_nodata("-1");
    	formulario.setMk_sms("-1");
    	formulario.setMk_correo_electronico("-1");
    	log.info("ClienteDispatchActions:cargaFormulario fin");
        //return mapping.findForward(Constantes.FORWARD_CLIENTE);
    	return formulario;
    }
    
    public ClienteForm buscaCliente(ClienteForm form,Session request)
    {
    	log.info("ClienteDispatchActions:buscaCliente inicio");
    	Session session = request;
    	String local = String.valueOf(session.getAttribute("sucursal"));
    	ClienteForm formulario = (ClienteForm)form;     	
        cargaInicial(local); 
        formulario.setEstaGrabado(2);
        log.info("ClienteDispatchActions:buscaCliente fin");
        return formulario;
    }
    
    public ClienteForm ingresoCliente(ClienteForm form,Session request){
    	log.info("ClienteDispatchActions:ingresoCliente inicio");
    	try{
    		
    		Session session = request;
        	String local = String.valueOf(session.getAttribute("sucursal"));    		
    		ClienteForm formulario = (ClienteForm)form;
    		System.out.println("SESSION LOCAL ====> "+session.getAttribute("sucursal"));
    		formulario.setLocal(local);
    		formulario.setEstaGrabado(0);
    		Utils util = new Utils();
    		
    		if(Constantes.STRING_ACTION_INGRESO_CLIENTE.equals(formulario.getAccion())){
    			
    			//Ingreso de Clientes 20141007   			
    			helper.ingresoCliente(formulario);   
    			
	    		System.out.println("Cliente postal (1111 )=> "+formulario.getRut()+" "+formulario.getDv()+" "+formulario.getMk_correo_postal()+","+formulario.getMk_correo_electronico()+","+formulario.getMk_telefonia()+","+formulario.getMk_sms()+","+formulario.getMk_nodata());

    			formulario.setCodigo_cliente_agregado(String.valueOf(formulario.getCodigo()));
    			String provincia_cliente = formulario.getProvincia_cliente();
    			formulario.setProvincia(util.isEntero(provincia_cliente));
    			int codigo_provincia = formulario.getProvincia();
    			String codigo_tipo_via = formulario.getTipo_via();
    			//Informacion cliente factura
    			
    			
    			if(null != formulario.getRemitente() && !("".equals(formulario.getRemitente().trim()))){
    				
    				
    				ClienteBean clienteFacturable = helper.traeClienteSeleccionado(null,formulario.getCodigo_cliente_agregado_factura());
    				
    				formulario.setCodigo_cliente_agregado_factura(clienteFacturable.getCodigo());
    				formulario.setNombre_cliente_factura(clienteFacturable.getNombre()+" "+clienteFacturable.getApellido());
    				formulario.setTipo_via_factura(clienteFacturable.getTipo_via());
    				formulario.setVia_factura(clienteFacturable.getDireccion());
    				formulario.setNumero_factura(clienteFacturable.getNumero());
    				formulario.setLocalidad_factura(clienteFacturable.getPoblacion());
    				formulario.setProvincia_factura(clienteFacturable.getProvincia_cliente());
    				formulario.setRemitente(clienteFacturable.getNif());
    				formulario.setDvFactura(clienteFacturable.getDvnif());
    				
    				int giro = util.isEntero(formulario.getGiro());
    		    	GiroBean giroCliente = PosUtilesFacade.traeDescripGiroCliente(giro);
    		    	
    		    	if(null != giroCliente){
    		    		formulario.setDescripcionGiro(giroCliente.getDescripcion());
    		    	}
    				
    			}
    			formulario.setPagina_status("");
        		cargaInicial(local);
        		formulario.setProvincia(codigo_provincia);
        		formulario.setTipo_via(codigo_tipo_via);
        		
    		}else if(Constantes.STRING_ACTION_TRAE_CLIENTE_SELECCIONADO.equals(formulario.getAccion())){
    			
    			formulario.cleanForm();
    			formulario.setEstaGrabado(0);
    			ClienteBean cliente = helper.traeClienteSeleccionado(formulario.getNif_cliente_agregado(),formulario.getCodigo_cliente_agregado());
    			
    			formulario.setCodigo(Integer.parseInt(cliente.getCodigo()));
    			formulario.setRut(cliente.getNif());
    			formulario.setDv(cliente.getDvnif());
    			formulario.setFnacimiento(cliente.getFecha_nac());
    			    		
    			formulario.setApellidos(cliente.getApellido());
    			formulario.setEdad(Constantes.STRING_BLANCO);
    			formulario.setNombres(cliente.getNombre());
    		
    			formulario.setAgente(cliente.getAgente());
    			formulario.setTipo_via(cliente.getTipo_via());
    			formulario.setVia(cliente.getDireccion());
    			formulario.setNumero(cliente.getNumero());
    			formulario.setLocalidad(cliente.getPoblacion());
    			formulario.setProvincia_cliente(cliente.getProvincia_cliente());
    			formulario.setProvincia(cliente.getProvincia());
    			formulario.setBloque(cliente.getNumero());
    			formulario.setCod_postal(cliente.getCodigo_postal());
    			formulario.setContacto(cliente.getPersona_contacto());
    			formulario.setEmail(cliente.getEmail());
    		    
    			if(Constantes.STRING_BLANCO.equals(cliente.getFono_casa()) || null == cliente.getFono_casa() ){
    				formulario.setTelefono(Constantes.STRING_BLANCO);
    			}else{
    				
        			String fonocasa = cliente.getFono_casa().length() >= 8 &&  cliente.getFono_casa().substring(0,1).equals("0") ? cliente.getFono_casa().substring(2, cliente.getFono_casa().length()): cliente.getFono_casa();

    				formulario.setTelefono(fonocasa);
    			}
    			
    			if((Constantes.STRING_BLANCO.equals(cliente.getFono_movil())) || null == cliente.getFono_movil()){
    				formulario.setTelefono_movil(Constantes.STRING_BLANCO);
    			}else{
    				formulario.setTelefono_movil(cliente.getFono_movil());
    			}
    			
    			formulario.setSexo(cliente.getSexo());  
    			
    			if(cliente.getGiro() == 0){
    				formulario.setGiro("");
    			}else{
    				formulario.setGiro(String.valueOf(cliente.getGiro()));
    			}
    			
    			
    			formulario.setProfesion(cliente.getProfesion()); 
    			formulario.setOcio(cliente.getOcio());
    			formulario.setBloque(cliente.getBloque());
    			
    			//LMARIN - 20150318
    			
    			formulario.setMk_correo_postal(cliente.getMk_correo_postal());
    			formulario.setMk_correo_electronico(cliente.getMk_correo_electronico());
    			formulario.setMk_sms(cliente.getMk_sms());
    			formulario.setMk_telefonia(cliente.getMk_telefonia());
    			formulario.setMk_nodata(cliente.getMk_nodata());
    			    			
    			//Informacion cliente factura    			
    			
    			if("" != cliente.getCliente_cliente() && null != cliente.getCliente_cliente()){
    				ClienteBean clienteFacturable = helper.traeClienteSeleccionado(null,cliente.getCliente_cliente());
    				
    				formulario.setCodigo_cliente_agregado_factura(clienteFacturable.getCodigo());
    				formulario.setNombre_cliente_factura(clienteFacturable.getNombre()+" "+clienteFacturable.getApellido());
    				formulario.setTipo_via_factura(clienteFacturable.getTipo_via());
    				formulario.setVia_factura(clienteFacturable.getDireccion());
    				formulario.setNumero_factura(clienteFacturable.getNumero());
    				formulario.setLocalidad_factura(clienteFacturable.getPoblacion());
    				formulario.setProvincia_factura(clienteFacturable.getProvincia_cliente());
    				formulario.setRemitente(clienteFacturable.getNif());
    				formulario.setDvFactura(clienteFacturable.getDvnif());
    				    		    	
    		    	GiroBean giroCliente = PosUtilesFacade.traeDescripGiroCliente(cliente.getGiro());
    		    	
    		    	if(null != giroCliente){
    		    		formulario.setDescripcionGiro(giroCliente.getDescripcion());
    		    	}
    				
    			}
    			
    			
    			//fin informacion cliente factura
    			
    			formulario.setPagina_status("");
    			cargaInicial(local);
    			formulario.setEstado_pagina(Constantes.STRING_ACTION_TRAE_CLIENTE_SELECCIONADO);
    			
    		}else if(Constantes.STRING_ACTION_NUEVO_CLIENTE.equals(formulario.getAccion())){
    			
    			System.out.println("PASO POR NUEVO CLIENTE");
    			
    			int codCliente = Constantes.INT_CERO;    			
    			formulario.cleanForm();
    			formulario.setNif_cliente_agregado("");
    			formulario.setCodigo_cliente_agregado("");
    			
    	    	//if(null != local){    		
    	    		//codCliente = helper.traeCodigoLocalCliente(local);     		    		
    	    	//} 
    	    	formulario.setCodigo(codCliente);    	    	
    	    	cargaInicial(local);
    		}else if("traeClienteSeleccionadoFactura".equals(formulario.getAccion())){
    			
    			System.out.println("Nif CLIENTE FACTURA ==>"+ formulario.getNif_cliente_agregado_factura());
    			ClienteBean cliente = helper.traeClienteSeleccionado(formulario.getNif_cliente_agregado_factura(),formulario.getCodigo_cliente_agregado_factura());
    			
    			formulario.setExito("traeClienteSeleccionadoFactura");
    			
    			formulario.setRemitente(cliente.getNif());
    			formulario.setCodigo_cliente_agregado_factura(cliente.getCodigo());
    			formulario.setNombre_cliente_factura(cliente.getNombre() + " " + cliente.getApellido());
    						
				formulario.setTipo_via_factura(cliente.getTipo_via());
				formulario.setVia_factura(cliente.getDireccion());
				formulario.setNumero_factura(cliente.getNumero());
				formulario.setLocalidad_factura(cliente.getPoblacion());
				formulario.setProvincia_factura(cliente.getProvincia_cliente());
				formulario.setRemitente(cliente.getNif());
				formulario.setDvFactura(cliente.getDvnif());
    			System.out.println("NOMBRE CLIENTE FACTURA ==>"+formulario.getNombre_cliente_factura()+" EXITO ==> "+formulario.getExito());
    			cargaInicial(local);
    		}
    		form = formulario;
    		return form;
    		
    	}catch(Exception ex){
    		log.error("ClienteDispatchActions:ingresoCliente error catch",ex);
    	}
    	log.info("ClienteDispatchActions:ingresoCliente fin");
    	//return mapping.findForward(Constantes.STRING_SUCCESS);
		return form;
    	
    }
    
   
    
    
    public ClienteForm CargabusquedaGiro(ClienteForm form,Session request){    	
    	Session session = request;
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));    		
		ClienteForm formulario = (ClienteForm)form;
		formulario.setLocal(local);
		ClienteHelper helper = new ClienteHelper();
		formulario.setEstaGrabado(2);  	   	
		return formulario;
    }
    
    public ClienteForm busquedaGiro(ClienteForm form,Session request){
    	Session session = request;
    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));    		
		ClienteForm formulario = (ClienteForm)form;
		formulario.setLocal(local);
		ClienteHelper helper = new ClienteHelper();
		formulario.setEstaGrabado(2);
		formulario = helper.busquedaGiro(formulario);
		
		
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
		return formulario;
    }
    public ClienteForm buscarClienteAjax(String nif) {
		log.info("BusquedaClientesDispatchActions:buscar inicio");
		ClienteForm clif = new ClienteForm();
		ClienteBean cliente = helper.traeClienteSeleccionado(nif,null);
		int cod  = !cliente.getCodigo().equals("") && cliente.getCodigo() != null ? Integer.valueOf(cliente.getCodigo()):0;
		clif.setDv(cliente.getDvnif());
		clif.setApellidos(cliente.getApellido());
		clif.setCodigo(cod);
		clif.setNombres(cliente.getNombre());
		clif.setVia(cliente.getDireccion());
		clif.setTipo_via(cliente.getTipo_via());
		clif.setNumero(cliente.getNumero());
		clif.setEmail(cliente.getEmail());
		clif.setTelefono(cliente.getFono_casa());
		clif.setTelefono_movil(cliente.getFono_movil());
		clif.setProfesion(cliente.getProfesion());
		clif.setLocalidad(cliente.getPoblacion());
		clif.setSexo(cliente.getSexo());
		clif.setMk_correo_electronico(cliente.getMk_correo_electronico());
		clif.setMk_correo_postal(cliente.getMk_correo_postal());
		clif.setMk_nodata(cliente.getMk_nodata());
		clif.setMk_sms(cliente.getMk_sms());
		clif.setMk_telefonia(cliente.getMk_telefonia());
		clif.setAgente(cliente.getAgente());
		clif.setProvincia_cliente(cliente.getProvincia_cliente());
		log.info("BusquedaClientesDispatchActions:buscar fin");
		return clif;
	}

    
}
