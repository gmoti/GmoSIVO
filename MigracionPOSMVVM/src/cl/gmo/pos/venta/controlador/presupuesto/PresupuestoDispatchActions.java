package cl.gmo.pos.venta.controlador.presupuesto;

import java.util.ArrayList;
import java.util.HashMap;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.controlador.BeanGlobal;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;


public class PresupuestoDispatchActions {
	
	PresupuestoHelper helper = new PresupuestoHelper();
	Logger log = Logger.getLogger( this.getClass() );
	
	public void cargaInicial(PresupuestoForm formulario, String local, Session session)
	{
		log.info("PresupuestoDispatchActions:cargaInicial  inicio");
		formulario.setListaFormasPago(helper.traeFormasPago());
		formulario.setListaAgentes(helper.traeAgentes(local));
		//formulario.setListaConvenios(helper.traeConvenios());
		formulario.setListaDivisas(helper.traeDivisas());
		formulario.setListaIdiomas(helper.traeIdiomas());
		//formulario.setListaPromociones(helper.traePromociones());
		formulario.setListaTiposPedidos(helper.traeListaTiposPedidos());		
		
		helper.traeDatosFormulario(formulario, session);
		log.info("PresupuestoDispatchActions:cargaInicial  fin");
	}
	
	public PresupuestoForm cargaFormulario(PresupuestoForm form,
			Session request)
	{
		log.info("PresupuestoDispatchActions:cargaFormulario  inicio");		
		PresupuestoForm formulario = (PresupuestoForm)form;
		formulario.setEstaGrabado(0);
		//Session session = request.getSession(true);
		Session session = request;
		
		session.removeAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.cleanForm();
		
		this.cargaInicial(formulario, local, session);
		
		helper.limpiaPreliminar(formulario, session);
		formulario.setFlujo(Constantes.STRING_FORMULARIO);
		formulario.setEstado(Constantes.STRING_FORMULARIO);
		
		log.info("PresupuestoDispatchActions:cargaFormulario  fin");
		helper.limpiaCliente(formulario);
		
		//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
		return formulario;
	}
	
	public BeanGlobal traspasoPedido(PresupuestoForm form,Session request)
	{
		log.info("PresupuestoDispatchActions:traspasoPedido  inicio");
		
		BeanGlobal bg = new BeanGlobal();
		
		PresupuestoForm formulario = (PresupuestoForm)form;
		//HttpSession session = request.getSession(true);
		Session session = request;
		
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		String action = Constantes.STRING_BLANCO;
		
		//comprueba que la caja esta abierta para hacer el traspaso
		if (helper.validaCaja(local, helper.formatoFecha(helper.traeFecha()))) {
			if (helper.comprueba_presupuesto(formulario)) {
				action = helper.traspasoPedido(formulario, local, session);
			}
			else
			{
				formulario.setCerrado(false);
				formulario.setEstado(Constantes.STRING_ERROR);
				formulario.setError(Constantes.STRING_ERROR_GUARDAR);
				action =  Constantes.FORWARD_PRESUPUESTO;
			}
		}
		else
		{
			formulario.setEstado(Constantes.STRING_ERROR);
			formulario.setError(Constantes.STRING_ERROR_VALIDA_CAJA_PRESUPUESTO);
			action =  Constantes.FORWARD_PRESUPUESTO;
		}
			
		log.info("PresupuestoDispatchActions:traspasoPedido  fin");
		//return mapping.findForward(action);	
		
		bg.setObj_1(formulario);
		bg.setObj_2(action);
		
		return bg;
	}
	
	public PresupuestoForm eliminarPresupuesto(PresupuestoForm form,
			Session request)
	{
		log.info("PresupuestoDispatchActions:eliminarPresupuesto  inicio");
		PresupuestoForm formulario = (PresupuestoForm)form;
		//HttpSession session = request.getSession(true);
		Session session = request;
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.setEstaGrabado(2);
		
		if (helper.eliminaPresupuesto(formulario)) {
			formulario.cleanForm();
			this.cargaInicial(formulario, local, session);
			helper.limpiaPreliminar(formulario, session);
			formulario.setEstado(Constantes.STRING_ELIMINADO);
			formulario.setError(Constantes.STRING_ERROR);
			formulario.setFlujo(Constantes.STRING_FORMULARIO);
			helper.limpiaCliente(formulario);
		}
		else
		{
			formulario.setEstado(Constantes.STRING_ELIMINADO);
			formulario.setError(Constantes.STRING_ERROR_ELIMINAR);
		}
		log.info("PresupuestoDispatchActions:eliminarPresupuesto  fin");
		
		//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
		return formulario;
	}
	
	
	public PresupuestoForm nuevoFormulario(PresupuestoForm form, Session request)
	{
		log.info("PresupuestoDispatchActions:nuevoFormulario  inicio");
		PresupuestoForm formulario = (PresupuestoForm)form;
		//HttpSession session = request.getSession(true);
		Session session = request;
		
		formulario.setEstaGrabado(2);
		formulario.cleanForm();
		formulario.setFlujo(Constantes.STRING_NUEVO);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, null);
    	helper.preCarga(formulario, local);
		this.cargaInicial(formulario, local, session);
		
		//se elimina la opcion de que grabe al inicio el presupuesto, ya que se almacena posteriormente
		//helper.ingresaPresupuesto(formulario);
		formulario.setEstado(Constantes.STRING_NUEVO);
		
		log.info("PresupuestoDispatchActions:nuevoFormulario  fin");
		//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
		return formulario;
	}
	
	public PresupuestoForm cargaPresupuestos(PresupuestoForm form,
			Session request)
	{
		log.info("PresupuestoDispatchActions:cargaPresupuestos  inicio");
		PresupuestoForm formulario = (PresupuestoForm)form;
		//HttpSession session = request.getSession(true);
		Session session = request;
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.setFlujo(Constantes.STRING_MODIFICAR);
		formulario.setEstaGrabado(2);
		System.out.println("Paso presupuesto");
		
		if (Constantes.STRING_SELECCIONA_PRESUPUESTO.equals(formulario.getAccion())) {
			//fquiroz 2/8/2017
			//sustituyo por un atributo
			//int indice = Integer.parseInt(request.getParameter(Constantes.STRING_PRESUPUESTO).toString());
			int indice = Integer.parseInt(request.getAttribute(Constantes.STRING_PRESUPUESTO).toString());
			
			helper.traePresupuesto(formulario, indice);
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
			helper.tarifica_Presupuesto(formulario);
			session.setAttribute(Constantes.STRING_PRESUPUESTO_FORM, formulario);
			formulario.setAccion(Constantes.STRING_BLANCO);
			log.info("PresupuestoDispatchActions:cargaPresupuestos  fin");
			//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
			return formulario;
		}
		else
		{
			helper.traeListaPresupuestos(formulario, local);
			log.info("PresupuestoDispatchActions:cargaPresupuestos  fin");
			//return mapping.findForward(Constantes.FORWARD_LISTA_PRESUPUESTOS);
			return formulario;
		}
	}
	
	public PresupuestoForm IngresaPresupuesto(PresupuestoForm form,	Session request)
	{
		
		log.info("PresupuestoDispatchActions:IngresaPresupuesto  inicio");		
		
		PresupuestoForm formulario = (PresupuestoForm)form;
	
		//HttpSession session = request.getSession(true);
		Session session = request;
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.setEstaGrabado(2);
		
		if (formulario.getEstado().equals(Constantes.STRING_FORMULARIO)) {
			formulario.setEstado(Constantes.STRING_FORMULARIO);
		}
		else
		{
			formulario.setEstado(Constantes.STRING_VENTA);
		}
		
		formulario.setError(Constantes.STRING_ERROR);
		
		if (Constantes.STRING_APLICA_DESCUENTO_LINEA.equals(formulario.getAccion())) 
		{
			//if ("".equals(formulario.getConvenio()) && !(formulario.getDtcoPorcentaje()>Constantes.INT_CERO)) {
				helper.aplica_descuento_por_linea(formulario);
				helper.tarifica_Presupuesto(formulario);
			/*}
			else
			{
				formulario.setEstado(Constantes.STRING_ERROR);
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
			}*/
			
			//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO); 
			return formulario;
			
		}
		if (Constantes.STRING_ELIMINA_CONVENIO.equals(formulario.getAccion()))
		{
			if (!Constantes.STRING_BLANCO.equals(formulario.getConvenio())) {
				helper.eliminaDescuentos(formulario);
				formulario.setConvenio(Constantes.STRING_BLANCO);
				formulario.setConvenio_det(Constantes.STRING_BLANCO);
				formulario.setConvenio_ln(Constantes.INT_CERO);
			}
		}
		if (Constantes.STRING_CAMBIO_CONVENIO.equals(formulario.getAccion())) 
		{
			
				if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
					if (helper.verificaConvenioCliente(formulario)) {
						helper.actualizaProductosPorConvenio(formulario,local);	
						helper.tarifica_Presupuesto(formulario);
					}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_CONVENIO_CLIENTE);
						formulario.setEstado(Constantes.STRING_ERROR);
						formulario.setConvenio(Constantes.STRING_BLANCO);
						formulario.setConvenio_det(Constantes.STRING_BLANCO);
					}
				}
				else
				{
					formulario.setError(Constantes.STRING_ERROR_SIN_PROD_CONVE);
					formulario.setEstado(Constantes.STRING_ERROR);
					formulario.setConvenio(Constantes.STRING_BLANCO);
					formulario.setConvenio_det(Constantes.STRING_BLANCO);
				}
			
		}
		if (Constantes.STRING_APLICA_DESCUENTO_TOTAL.equals(formulario.getAccion())) 
		{
			if ("".equals(formulario.getConvenio())) {
					//helper.aplica_descuento_total(formulario);
					helper.aplica_descuento_total_lineal(formulario);
					helper.tarifica_Presupuesto(formulario);
			}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
				formulario.setEstado(Constantes.STRING_ERROR);
			}
			
			//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO); 
			return formulario;
			
		}
		if (Constantes.STRING_APLICA_DESCUENTO_TOTAL_MONTO.equals(formulario.getAccion())) 
		{
			if (Constantes.STRING_BLANCO.equals(formulario.getConvenio())) {
					//helper.aplica_descuento_total(formulario);
					helper.aplica_descuento_total_monto(formulario);
					helper.tarifica_Presupuesto(formulario);
			}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
				formulario.setEstado(Constantes.STRING_ERROR);
			}
			
			//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO); 
			return formulario;
			
		}
		if (Constantes.STRING_CANTIDAD.equals(formulario.getAccion())) {
			
			int index = Integer.parseInt(formulario.getAddProducto());
			int cantidad = formulario.getCantidad();
			
			helper.modificaCantidad(formulario, index, cantidad);
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
			formulario.setEstado(Constantes.STRING_VENTA);
			helper.tarifica_Presupuesto(formulario);
		}
		if (Constantes.STRING_AGREGAR_PRODUCTOS.equals(formulario.getAccion())) {
			System.out.println("Paso al agregar productos");
			formulario.setGraduacion((GraduacionesBean)session.getAttribute(Constantes.STRING_GRADUACION));
			
			formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
			
			
			/* LMARIN 20130821 Agrego produtos */
			
			formulario.setListaProductos(helper.actualizaProductos(formulario, null, formulario.getCantidad() ,formulario.getListaProductos(), 
						local, Constantes.STRING_PEDIDO, formulario.getAddProducto(), formulario.getGraduacion(), formulario.getOjo(),formulario.getTipo(), session));
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());

			helper.tarifica_Presupuesto(formulario);
		}
		if (Constantes.STRING_AGREGAR_CLIENTES.equals(formulario.getAccion())) {
			session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());
			helper.traeUltimaGraduacionCliente(formulario);
			helper.traeUltimaGraduacionContactologiaCliente(formulario);
			session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
			session.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, formulario.getGraduacion_lentilla());
			if (formulario.getEstado().equals(Constantes.STRING_FORMULARIO)) {
				//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
				return formulario;
			}
		}
		if (Constantes.STRING_ELIMINAR_PRODUCTO.equals(formulario.getAccion()))
		{
			formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
			formulario.setListaProductos(helper.eliminarProductos(formulario.getAddProducto(), formulario.getListaProductos()));
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, formulario.getListaProductos());
			
			helper.tarifica_Presupuesto(formulario);
		}
		if (Constantes.STRING_AGREGAR_SUPLEMENTOS.equals(formulario.getAccion())) {
			helper.agregaSuplementosProducto(formulario, session);
		}
		if (Constantes.STRING_VER_SUPLEMENTOS.equals(formulario.getAccion())) {
			helper.verSuplementosProducto(formulario, session);
		}
		if (Constantes.STRING_AGREGA_DESCRIPCION.equals(formulario.getAccion())) {
			
			int index = Integer.parseInt(formulario.getAddProducto());
			helper.agregaDescripcion(formulario, index);
		}
		if (Constantes.STRING_INGRESA_PRESUPUESTO.equals(formulario.getAccion())) 
		{
				if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) 
				{
					if(helper.ingresaPresupuesto(formulario, local))
					{
						helper.ingresaPresupuestoDetalle(formulario, local);
						session.setAttribute(Constantes.STRING_PRESUPUESTO_FORM, formulario);
						formulario.setEstaGrabado(1);
					}
					formulario.setEstado(Constantes.STRING_GUARDADO);
					formulario.setFlujo(Constantes.STRING_MODIFICAR);
					formulario.setEstaGrabado(1);
					
				}
				else
				{
					formulario.setEstado(Constantes.STRING_ERROR);
					formulario.setError(Constantes.STRING_ERROR_SIN_PROD);
				}
		}
		if (Constantes.STRING_CARGA_PRESUPUESTO_SELECCION.equals(formulario.getAccion())) {
			
			helper.traePresupuestoSeleccionado(formulario);
			
			session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());
			ClienteBean cliente = helper.traeClienteSeleccionado(null,formulario.getCliente());
			formulario.setNif(cliente.getNif());
    		formulario.setNombre_cliente( cliente.getNombre() + " " + cliente.getApellido());
    		formulario.setDvnif(cliente.getDvnif());
    		formulario.setCliente(cliente.getCodigo());
    		formulario.setFlujo(Constantes.STRING_MODIFICAR);
			helper.traeUltimaGraduacionCliente(formulario);
			helper.traeUltimaGraduacionContactologiaCliente(formulario);
			session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
			session.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, formulario.getGraduacion_lentilla());
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
			helper.tarifica_Presupuesto(formulario);
			session.setAttribute(Constantes.STRING_PRESUPUESTO_FORM, formulario);
			formulario.setAccion(Constantes.STRING_BLANCO);
			formulario.setEstaGrabado(2);
		}
		
		log.info("PresupuestoDispatchActions:IngresaPresupuesto  fin");
		//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
		return formulario;
		
	}

	public PresupuestoForm IngresaPresupuestoGraduacion(PresupuestoForm form, Session request)
	{
		//******SETEO NUEVO FORMULARIO
		log.info("PresupuestoDispatchActions:IngresaPresupuestoGraduacion  inicio");
		PresupuestoForm formulario = (PresupuestoForm)form;
		
		formulario.cleanForm();
		
		//HttpSession session = request.getSession(true);
		Session session = request;
		String cliente  = formulario.getCliente();
		String nombre_cliente = formulario.getNombre_cliente();
		formulario.setEstaGrabado(2);
		ClienteBean clienteObj = helper.traeClienteSeleccionado(null,cliente);
		
		if(null != clienteObj){
			formulario.setNif(clienteObj.getNif());
			formulario.setDvnif(clienteObj.getDvnif());
			formulario.cleanForm();
			formulario.setCliente(cliente);
			formulario.setNombre_cliente(nombre_cliente);
		}else{
			
			formulario.setNif("");
			formulario.setDvnif("");
			formulario.cleanForm();
			formulario.setCliente("");
			formulario.setNombre_cliente("");
		}
		
		
		formulario.setFlujo(Constantes.STRING_NUEVO);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, null);
		
		
    	helper.preCarga(formulario, local);
		this.cargaInicial(formulario, local, session);
		formulario.setGraduacion(new GraduacionesBean());
		
		//se elimina la opcion de que grabe al inicio el presupuesto, ya que se almacena posteriormente
		//helper.ingresaPresupuesto(formulario);
		formulario.setEstado(Constantes.STRING_NUEVO);		
		//*************FIN SETEO NUEVO FORMULARIO		
		//SETEO DE INGRESO DE CLIENTE 
		
		session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());
		helper.traeUltimaGraduacionCliente(formulario);
		helper.traeUltimaGraduacionContactologiaCliente(formulario);
		session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
		session.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, formulario.getGraduacion_lentilla());
		
		//****************
		log.info("PresupuestoDispatchActions:IngresaPresupuestoGraduacion  fin");
		//return mapping.findForward(Constantes.FORWARD_PRESUPUESTO);
		return formulario;
	}
	
	/*
	public ActionForward buscarClienteAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		log.info("BusquedaClientesDispatchActions:buscar inicio");
		PresupuestoForm formulario = (PresupuestoForm)form;
		
		String nif=request.getParameter("nif");
		String pagina = request.getParameter("pagina");
		formulario.setEstaGrabado(2);
		ClienteBean cliente = helper.traeClienteSeleccionado(nif,null);
		
		
		
    	HashMap hm = new HashMap();
    	
    	if(null != cliente){
    		formulario.setNif(cliente.getNif());
    		formulario.setNombre_cliente( cliente.getNombre() + " " + cliente.getApellido());
    		formulario.setDvnif(cliente.getDvnif());
    		formulario.setCodigo(cliente.getCodigo());
    		hm.put("nif", cliente.getNif());    		
    		hm.put("nombre_cliente", cliente.getNombre() + " " + cliente.getApellido());
    		hm.put("dvnif", cliente.getDvnif());
    		hm.put("codigo_cliente", cliente.getCodigo());
    		hm.put("fecha_nac", cliente.getFecha_nac());
    		hm.put("nombre", cliente.getNombre());
    		hm.put("apellido", cliente.getApellido());
    		
    		GraduacionesBean graduacion = helper.traeUltimaGraduacionCliente(cliente.getCodigo());
    		formulario.setGraduacion(graduacion);
    		if(null != graduacion){
    			
    			hm.put("fecha_graduacion", graduacion.getFecha());
    			hm.put("OD_esfera", graduacion.getOD_esfera());
    			hm.put("OD_cilindro", graduacion.getOD_cilindro());    			
    			hm.put("OD_eje", graduacion.getOD_eje());
    			hm.put("OD_adicion", graduacion.getOD_adicion());
    			hm.put("OD_esfera_cerca", graduacion.getOD_esfera_cerca());
    			hm.put("OD_n", graduacion.getOD_n());
    			hm.put("OD_p", graduacion.getOD_p());
    			
    			hm.put("doctor", graduacion.getDoctor());
    			
    			hm.put("OI_esfera", graduacion.getOI_esfera());
    			hm.put("OI_cilindro", graduacion.getOI_cilindro());
    			hm.put("OI_eje", graduacion.getOI_eje());
    			hm.put("OI_adicion", graduacion.getOI_adicion());
    			hm.put("OI_esfera_cerca", graduacion.getOI_esfera_cerca());
    			hm.put("OI_n", graduacion.getOI_n());
    			hm.put("OI_p", graduacion.getOI_p());   		
    			
    		}else{
    			hm.put("fecha_graduacion", Constantes.STRING_BLANCO);
    			hm.put("OD_esfera", Constantes.STRING_BLANCO);
    			hm.put("OD_eje", Constantes.STRING_BLANCO);
    			hm.put("OD_adicion", Constantes.STRING_BLANCO);
    			hm.put("OD_esfera_cerca", Constantes.STRING_BLANCO);
    			hm.put("OD_n", Constantes.STRING_BLANCO);
    			hm.put("OD_p", Constantes.STRING_BLANCO);
    			
    			hm.put("doctor", Constantes.STRING_BLANCO);
    			
    			hm.put("OI_esfera", Constantes.STRING_BLANCO);
    			hm.put("OI_cilindro", Constantes.STRING_BLANCO);
    			hm.put("OI_eje", Constantes.STRING_BLANCO);
    			hm.put("OI_adicion", Constantes.STRING_BLANCO);
    			hm.put("OI_esfera_cerca", Constantes.STRING_BLANCO);
    			hm.put("OI_n", Constantes.STRING_BLANCO);
    			hm.put("OI_p", Constantes.STRING_BLANCO);
    		}
    		
    	}else{
    		hm.put("nif", "");    		
    		hm.put("nombre_cliente", "");
    		hm.put("dvFactura", "");
    		hm.put("codigo_cliente", "");
    		hm.put("fecha_nac", "");
    	}
		
    	JSONObject json = JSONObject.fromObject(hm);
		response.setHeader("X-JSON", json.toString());
		
		log.info("BusquedaClientesDispatchActions:buscar fin");
		return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
	}
	*/
	
}
