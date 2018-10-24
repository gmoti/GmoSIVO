package cl.gmo.pos.venta.controlador.general;

import java.io.IOException;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.helper.BusquedaClientesHelper;

public class BusquedaClientesDispatchActions {

	BusquedaClientesHelper helper = new BusquedaClientesHelper();
	Logger log = Logger.getLogger(this.getClass());

	public BusquedaClientesDispatchActions() {
	}

	public BusquedaClientesForm cargaBusquedaClientes(BusquedaClientesForm form, Session request) {
		
		log.info("BusquedaClientesDispatchActions:cargaBusquedaClientes inicio");
		log.info("BusquedaClientesDispatchActions:cargaBusquedaClientes fin");
		
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
		return form;
	}

	public BusquedaClientesForm buscar(BusquedaClientesForm form, Session request) {
		
		log.info("BusquedaClientesDispatchActions:buscar inicio");
		BusquedaClientesForm formulario = (BusquedaClientesForm) form;
		String accion = Constantes.STRING_BLANCO;
		accion = formulario.getAccion();
		formulario.setError(Constantes.STRING_ERROR);
		if (Constantes.STRING_BUSQUEDA.equals(accion)) {
			if (helper.valida_campos(formulario)) {
				formulario.setListaClientes(helper.traeClientes(
						formulario.getNif(), formulario.getNombre(),
						formulario.getApellido(), formulario.getCodigo()));
			}
			
		}
		log.info("BusquedaClientesDispatchActions:buscar fin");
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
		return formulario;
	}

	
	public ClienteBean buscarClienteAjax(BusquedaClientesForm form, Session request) throws IOException {
		
		
		log.info("BusquedaClientesDispatchActions:buscar inicio");
		BusquedaClientesForm formulario = (BusquedaClientesForm) form;

		String nif=request.getAttribute("nif").toString();
		String pagina = request.getAttribute("pagina").toString();
		
		ClienteBean cliente = helper.traeClienteSeleccionado(nif,null);	
		
		String nombre = cliente.getNombre();
		String apellido = cliente.getApellido();
		
    	HashMap hm = new HashMap();
    	
    	System.out.println("buscarClienteAjax() ==>" + cliente.getNif() + "<==>"+cliente.getCodigo()+" <==> "+apellido);
    	
    	if(null != cliente){
    		
    		hm.put("nif", cliente.getNif());    		
    		hm.put("nombre_cliente", nombre+ " " + apellido);
    		hm.put("dvnif", cliente.getDvnif());
    		hm.put("codigo_cliente", cliente.getCodigo());
    		hm.put("fecha_nac", cliente.getFecha_nac());
    		hm.put("nombre", nombre);
    		hm.put("apellido", apellido);
    		
    	}else{
    		hm.put("nif", "");    		
    		hm.put("nombre_cliente", "");
    		hm.put("dvFactura", "");
    		hm.put("codigo_cliente", "");
    		hm.put("fecha_nac", "");
    	}
    	
    	//JSONObject json = JSONObject.fromObject(hm);    	
    	//response.getWriter().print(json);
    	
		
		log.info("BusquedaClientesDispatchActions:buscar fin");
		//return null;
		return cliente;
	}

}
