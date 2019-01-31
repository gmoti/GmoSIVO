package cl.gmo.pos.venta.controlador;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.VentaDirectaDispatchActions;
import cl.gmo.pos.venta.respuesta.RespuestaVentaDirecta;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ClienteDAOImpl;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.CajaBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;

public class ControllerVentaDirecta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1872185090221869401L;

	Session sess = Sessions.getCurrent();
	
	@Wire
	Window winVentaDirecta;	
	private Window wBusqueda;
	private Window wBusquedaDirecta;
	private boolean bWin=true;
	
	//Definiciones 
	private ClienteDAOImpl clienteImp;		
	private ProductosBean productoBean;
	private ClienteBean cliente;		
	private VentaDirectaForm ventaDirectaForm;
	private SeleccionPagoForm seleccionPagoForm;
	private VentaDirectaDispatchActions ventaDirectaAccion;
	
	//arreglos
	private List<FamiliaBean> familiaBeans;		
	
	//Generales
	HashMap<String,Object> objetos;		
	private BeanControlBotones controlBotones;		
	private AgenteBean agenteBean;
	private CajaBean   cajaBean;
	
	private String usuario;
	private String sucursal;
	private String sucursalDes;
	
	//Evalua Respuesta
	RespuestaVentaDirecta respuestaVentaDirecta;
		
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view) {
		
        Selectors.wireComponents(view, this, false);	
        
        usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);
		sucursal = (String)sess.getAttribute(Constantes.STRING_SUCURSAL);
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		sess.setAttribute("_Convenio","0");	
				
		clienteImp = new ClienteDAOImpl();					
		productoBean = new ProductosBean();		
		familiaBeans = new ArrayList<>();		
		ventaDirectaAccion = new VentaDirectaDispatchActions();	
		respuestaVentaDirecta = new RespuestaVentaDirecta();
		
		agenteBean = new AgenteBean();
		cajaBean   = new CajaBean();
		
		controlBotones = new BeanControlBotones();
		controlBotones.setEnableGrid("true");
		controlBotones.setEnableGrabar("true");
		controlBotones.setEnableNew("false");
		controlBotones.setEnablePagar("true");	
		
		controlBotones.setEnableGenerico1("false");
		controlBotones.setEnableGenerico2("true");		
		
		//Encabezado venta directa		
		ventaDirectaForm = new VentaDirectaForm();
		
		ventaDirectaAccion.carga(ventaDirectaForm, sess);
		ventaDirectaAccion.cargaCaja(ventaDirectaForm, sess);	
		
		Optional<CajaBean> cb = ventaDirectaForm.getListaCajas().stream().findFirst();		
		cajaBean = cb.get();
		
		creaItemSelecciona();
		Optional<AgenteBean> ab = ventaDirectaForm.getListaAgentes().stream().findFirst();
		agenteBean=ab.get();
		
	}
	
	
	//===================== Acciones de la ToolBar ======================
	//===================================================================
	
	//============ Nuevo Venta directa  ============
	//==============================================
		
	
	@NotifyChange({"ventaDirectaForm","controlBotones"})
	@Command	
	public void nuevaVenta() {		
		
		ventaDirectaAccion.carga(ventaDirectaForm, sess);
		
		controlBotones.setEnableGrid("true");
		controlBotones.setEnableGrabar("true");
		controlBotones.setEnablePagar("true");
		controlBotones.setEnableGenerico3("false");
		ventaDirectaForm.setAgente(agenteBean.getUsuario());
		
		ventaDirectaForm = ventaDirectaAccion.carga(ventaDirectaForm, sess);
		ventaDirectaForm.setCajero(agenteBean.getUsuario());
		ventaDirectaForm.setAgente(agenteBean.getUsuario());
		ventaDirectaForm.setNumero_caja(cajaBean.getCodigo());
		ventaDirectaForm.setSumaTotal(0);
		ventaDirectaForm.setSumaTotalFinal(0);
		ventaDirectaForm.setNombreCliente("");
		
		ventaDirectaForm.setListaProductos(new ArrayList<ProductosBean>());
		
		creaItemSelecciona();
		posicionaCombos();
		/*creaItemSelecciona();
		Optional<AgenteBean> ab = ventaDirectaForm.getListaAgentes().stream().findFirst();
		agenteBean=ab.get();		*/
		
		if (!bWin) {
			wBusqueda.detach();
			bWin=true;
		}	
		
		return;
	}
	
	
	//=========== Graba Venta directa =============
	//=============================================
	
	@NotifyChange({"ventaDirectaForm","controlBotones"})
	@Command
	public void grabaVenta() {
		
		//valida grabar venta
		if (ventaDirectaForm.getSumaTotal() < 1) {
			Messagebox.show("La venta esta sin lineas", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);			
			return;
		}		
					
		
		try {			
			ventaDirectaForm.setAccion(Constantes.STRING_AGREGAR_VENTA_DIRECTA);
			//ventaDirectaForm = ventaDirectaAccion.generaVentaDirecta(ventaDirectaForm, sess);			
			ventaDirectaAccion.IngresaVentaDirecta(ventaDirectaForm, sess);	
			respuestaVentaDirecta.evaluaEstado(ventaDirectaForm);
			//Messagebox.show("Venta almacenada");
			
			if (controlBotones.getEnableGenerico3().equals("true"))
				controlBotones.setEnablePagar("true");
			else
				controlBotones.setEnablePagar("false");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	//=========== Graba Pago Venta ================
	//=============================================
	
	@Command
	public void pagoVenta() {	
		
		String respuesta;
		String codigo;
		
		if (ventaDirectaForm.getSumaTotal() > 0) {				
			try {
				
				ventaDirectaForm.setAccion("valida_venta_directa");	
				ventaDirectaForm = ventaDirectaAccion.IngresaVentaDirecta(ventaDirectaForm, sess);
				
				if(ventaDirectaForm.getEstado().equals("VALIDACION_MULTIOFERTA_OK")) {					
					if(ventaDirectaForm.getNumero_ticket() !="") {
						if(ventaDirectaForm.getSumaTotal() > 0) {
							
							//param 1:cantidad, param 2:codigoMulti
							BeanGlobal global = ventaDirectaAccion.validaCantidadProductosMultiofertas(sess);
							
							respuesta = (String)global.getObj_1();
							codigo = (String)global.getObj_2();
							
							if(!respuesta.equals("menor")) {
								
								ventaDirectaForm = ventaDirectaAccion.generaVentaDirecta(ventaDirectaForm, sess);
								
								//llamo a la ventana de pago
								
								seleccionPagoForm = new SeleccionPagoForm();
								
								/*seleccionPagoForm.setFech_pago(ventaDirectaForm.getFecha());
								seleccionPagoForm.setFecha(ventaDirectaForm.getFecha());
								seleccionPagoForm.setTipo_doc('B');
								seleccionPagoForm.setOrigen("DIRECTA");
								
								sess.setAttribute(Constantes.STRING_TOTAL, ventaDirectaForm.getSumaTotal());
								sess.setAttribute(Constantes.STRING_CLIENTE, cliente.getCodigo()  );
								sess.setAttribute(Constantes.STRING_TICKET,  ventaDirectaForm.getEncabezado_ticket() + "/" + ventaDirectaForm.getNumero_ticket() );
								sess.setAttribute(Constantes.STRING_FECHA,   ventaDirectaForm.getFecha());								
								
								sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, ventaDirectaForm.getListaProductos());	*/			
								
								objetos = new HashMap<String,Object>();
								objetos.put("cliente",cliente);
								objetos.put("pagoForm",seleccionPagoForm);
								objetos.put("ventaOrigenForm",ventaDirectaForm);		
								objetos.put("origen","DIRECTA");
								
								Window window = (Window)Executions.createComponents(
						                "/zul/venta_directa/SeleccionPago.zul", null, objetos);
								
						        window.doModal();							
								
								//FIN
							}else {
								Messagebox.show("La cantidad de productos en la multioferta "+codigo+" no esta completa", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}else {
							Messagebox.show("Debe ingresar articulos para generar la venta", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);							
						}
					}else {						
						Messagebox.show("Debe guardar la venta, antes de cobrar", "Advertencia", Messagebox.OK, Messagebox.EXCLAMATION);
					}
				}				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}			
		}		
	}
	
	
	//======= pago exitoso en venta directa =======
	
	@NotifyChange({"ventaDirectaForm","controlBotones"})
	@GlobalCommand	
    public void creaPagoExitoso(@BindingParam("seleccionPago")SeleccionPagoForm seleccionPago) {	
		
		String boleta="";
		
		
		ventaDirectaForm.setAccion(Constantes.STRING_PAGO_EXITOSO);			
		sess.setAttribute(Constantes.STRING_TICKET, ventaDirectaForm.getEncabezado_ticket() + "/" + ventaDirectaForm.getNumero_ticket());
		sess.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, seleccionPago.getTipo_doc());
		sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, new ArrayList<ProductosBean>());
		sess.setAttribute(Constantes.STRING_DOCUMENTO, 0);
		sess.setAttribute("SeleccionPagoForm", seleccionPago);
		sess.setAttribute(Constantes.STRING_TIPO_ALBARAN, ventaDirectaForm.getTipoAlbaran());				
		
		try {
			ventaDirectaAccion.IngresaVentaDirecta(ventaDirectaForm, sess);		
			postCobro();
			
            if (ventaDirectaForm.getEstado_boleta().contains("TRUE") || ventaDirectaForm.getEstado_boleta().contains("true")) {
				
				Messagebox.show("Error: No se pudo generar la boleta, Intentelo nuevamente.");
			}else {
				
				
				String valor[] =  ventaDirectaForm.getEstado_boleta().split("_");
				
				//http://10.216.4.24/39 66666666-6 1.pdf
				//http://10.216.4.24/39%2066666666-6%203.pdf
				
				String url ="http://10.216.4.24/39 " + 
							ventaDirectaForm.getNif().trim() + "-" + ventaDirectaForm.getDv().trim() + " " + valor[1].trim()+".pdf";
							
				
				
				objetos = new HashMap<String,Object>();
				objetos.put("documento",url);
				objetos.put("titulo","Venta Directa");
				
				Window window = (Window)Executions.createComponents(
		                "/zul/reportes/VisorDocumento.zul", null, objetos);
				
		        window.doModal();	
		        
		        this.nuevaVenta();
		        controlBotones.setEnableGenerico1("false");
		        controlBotones.setEnableGenerico2("true");
		        
		        //BindUtils.postGlobalCommand(null, null, "nuevaVenta", null);
		        
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}			
		
	}
	
	
	
	//=========== Post Grabacion pago =============
	//=============================================		
	@NotifyChange({"ventaDirectaForm","controlBotones"})
	public void postCobro() {
		
		controlBotones.setEnablePagar("true");
		controlBotones.setEnableGenerico3("true");		
		Messagebox.show("Venta almacenada con exito");
	}
	
	
	
	//===================== Acciones comunes de la ventana ======================
	//===========================================================================
	
	@NotifyChange({"ventaDirectaForm","controlBotones"})
	@Command
	public void buscarCliente() {
		
		if (ventaDirectaForm.getNif().equals("")) {
			Messagebox.show("Debe ingresar un Nif");
			return;
		}
		
		try {
			
			cliente = clienteImp.traeCliente(ventaDirectaForm.getNif(), "");
			
			if (!cliente.getNif().equals("")) {			
				ventaDirectaForm.setCodigo_cliente(cliente.getCodigo());
				ventaDirectaForm.setNombreCliente(cliente.getNombre() +" " + cliente.getApellido());
				ventaDirectaForm.setNombre(cliente.getNombre());				
				ventaDirectaForm.setDv(cliente.getDvnif());
				
				sess.setAttribute("nombre_cliente",cliente.getNombre() + " " + cliente.getApellido());			
				sess.setAttribute(Constantes.STRING_CLIENTE, cliente.getCodigo());
	        	sess.setAttribute(Constantes.STRING_CLIENTE_VENTA, cliente.getCodigo());	        	
	        	sess.setAttribute("NOMBRE_CLIENTE",cliente.getNombre() + " " + cliente.getApellido());       	
	    	    sess.setAttribute(Constantes.STRING_TIPO_ALBARAN, ventaDirectaForm.getTipoAlbaran());				
				
				controlBotones.setEnableGrid("false");
				controlBotones.setEnableGrabar("false");
			}else {				
				
				Messagebox.show("El cliente no existe, desea Ingresarlo","Crear cliente", 
						Messagebox.YES | 
						Messagebox.NO, 
						Messagebox.QUESTION, new EventListener<Event>() {			
					@Override
					public void onEvent(Event e) throws Exception {				
							if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {								
											
									if (!bWin) wBusqueda.detach();									
									
									winVentaDirecta.detach();									
															
									Window window = (Window)Executions.createComponents(
							                "/zul/mantenedores/Clientes.zul", null, null);			
							        window.doModal();								
							}						
						}
				});					
			}
				
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
	
	@NotifyChange({"ventaDirectaForm","controlBotones"})
	@Command
	public void buscarClienteGenerico() {		
		ventaDirectaForm.setNif("66666666");		
		controlBotones.setEnableGrid("false");
		controlBotones.setEnableGrabar("false");
		
		buscarCliente();
	}	
	
	
	@Command
	public void buscaProducto() {		
		
		if (bWin) {
			objetos = new HashMap<String,Object>();
			objetos.put("familiaBeans",familiaBeans);			
			wBusqueda = (Window)Executions.createComponents(
	                "/zul/venta_directa/SearchProducto.zul", null, objetos);
			
			wBusqueda.doModal(); 
	        bWin=false;
		}else {
			wBusqueda.setVisible(true);
		}		
	}
	
	@Command
	public void buscaProductoDirecto() {
		
		wBusquedaDirecta = (Window)Executions.createComponents(
                "/zul/venta_directa/SearchProductoDirecto.zul", null, null);
		
		wBusquedaDirecta.doModal();		
	}	
	
	
	@NotifyChange({"ventaDirectaForm"})
    @GlobalCommand
	public void actProdGrid(@BindingParam("producto")ProductosBean arg) {		
		
		arg.setImporte(arg.getPrecio());
		arg.setCantidad(1);
		
		/*ArrayList<ProductosBean> productos = new ArrayList<ProductosBean>();
		
		if (ventaDirectaForm.getListaProductos() == null) {
			productos.add(arg);
			ventaDirectaForm.setListaProductos(productos);
		}else {
			productos = ventaDirectaForm.getListaProductos();
			productos.add(arg);
			ventaDirectaForm.setListaProductos(productos);
		}		*/
		
		
		ventaDirectaForm.setAccion(Constantes.STRING_AGREGAR_PRODUCTOS);
		ventaDirectaForm.setAddProducto(arg.getCod_barra());
		ventaDirectaForm.setCantidad(arg.getCantidad());
		
		
		try {
			ventaDirectaAccion.IngresaVentaDirecta(ventaDirectaForm, sess);
			actTotal(ventaDirectaForm.getListaProductos());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//System.out.println("estoy en otro controlador "+arg.getDescripcion());
				
	}
	
	 
	@NotifyChange({"ventaDirectaForm"})
	@Command
	public void actImporteGrid(@BindingParam("arg")ProductosBean arg){
		Integer newImport=0;		
		
		newImport = arg.getPrecio() * arg.getCantidad();
		
		for(ProductosBean b : ventaDirectaForm.getListaProductos()) {
			if(b.getCod_barra().equals(arg.getCod_barra())) {
				b.setImporte(newImport);
				break;
			}
		}	
		
		/*Optional<ProductosBean> p = ventaDirectaForm.getListaProductos()
				.stream()
				.filter(s -> s.getCod_barra().equals(arg.getCod_barra()))
				.findFirst()	;*/
		
		actTotal(ventaDirectaForm.getListaProductos());
		System.out.println("nuevo importe " + newImport);
	}
	
	
	@NotifyChange("ventaDirectaForm")	
	public void actTotal(List<ProductosBean> arg){
		int sumar=0;
		
		sumar = arg.stream().mapToInt(s -> s.getImporte()).sum();	
		
		ventaDirectaForm.setSumaTotal(sumar);
		System.out.println("nuevo total:" + sumar);
	}	
	
	@NotifyChange({"ventaDirectaForm"})	
	@Command
	public void deleteItem(@BindingParam("arg")ProductosBean b){
		
		
		if (ventaDirectaForm.getEstado().equals("fin")){			
			Messagebox.show("Venta finalizada, no es posible eliminar productos");
			return;
		}
 		
 		ventaDirectaForm.setAccion(Constantes.STRING_ELIMINAR_PRODUCTO);
		ventaDirectaForm.setAddProducto(b.getCodigo());	
		
		try {
			ventaDirectaAccion.IngresaVentaDirecta(ventaDirectaForm, sess);
			actTotal(ventaDirectaForm.getListaProductos());
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		//ventaDirectaForm.getListaProductos().remove(b);		
		//actTotal(ventaDirectaForm.getListaProductos());
	}
	
	
	@NotifyChange({"controlBotones","ventaDirectaForm"})	
	@Command
	public void seleccionaCaja() {
		
		Optional<String> ab = Optional.ofNullable(agenteBean.getUsuario());		
		
		if (!ab.isPresent() || ab.get().equals("") || ab.get().equals("Seleccione cajero")) {
			Messagebox.show("Debe seleccionar un cajero");
			return;
		}		
		
		controlBotones.setEnableGenerico1("true");
		controlBotones.setEnableGenerico2("false");
		
		ventaDirectaForm.setCajero(cajaBean.getDescripcion());
		ventaDirectaForm.setAgente(agenteBean.getUsuario());
		ventaDirectaForm.setNumero_caja(cajaBean.getCodigo());
		ventaDirectaForm.setNombreCliente("");		
		
		posicionaCombos();	
	}
	
	
	@Command
	public void salir() {
		
		Messagebox.show("Salir de Ventas Directas","Notificacion",
				Messagebox.YES|
				Messagebox.NO,
				Messagebox.QUESTION ,new EventListener<Event>() {

			@Override
			public void onEvent(Event e) throws Exception {				
				if(  ((Integer) e.getData()).intValue() == Messagebox.YES) {
					
					if (!bWin) wBusqueda.detach();
					
					winVentaDirecta.detach();
				}					
			}			
		});		
	}
	
	public void creaItemSelecciona() {
		
		String descripcion = "Seleccione cajero";		
		
		//Agente
		ArrayList<AgenteBean> aAgenteBean = new ArrayList<AgenteBean>();
		ArrayList<AgenteBean> newList1;
		
		agenteBean = new AgenteBean(); 
		agenteBean.setUsuario("Seleccione cajero");
		agenteBean.setNombre_completo(descripcion);
		aAgenteBean.add(agenteBean);
		newList1 = (ArrayList<AgenteBean>) Stream.concat(aAgenteBean.stream(), ventaDirectaForm.getListaAgentes().stream()).collect(Collectors.toList());
		ventaDirectaForm.setListaAgentes(newList1);
		
	}
	
	public void posicionaCombos() {		
		Optional<AgenteBean> a = ventaDirectaForm.getListaAgentes().stream().filter(s -> ventaDirectaForm.getAgente().equals(s.getUsuario())).findFirst();		
		agenteBean = a.get();		
	}
	
	
	
	//********* getter an setter
	//==================================
	public ProductosBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductosBean productoBean) {
		this.productoBean = productoBean;
	}	

	public VentaDirectaForm getVentaDirectaForm() {
		return ventaDirectaForm;
	}

	public void setVentaDirectaForm(VentaDirectaForm ventaDirectaForm) {
		this.ventaDirectaForm = ventaDirectaForm;
	}	

	public BeanControlBotones getControlBotones() {
		return controlBotones;
	}

	public void setControlBotones(BeanControlBotones controlBotones) {
		this.controlBotones = controlBotones;
	}

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}

	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}

	public CajaBean getCajaBean() {
		return cajaBean;
	}

	public void setCajaBean(CajaBean cajaBean) {
		this.cajaBean = cajaBean;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getSucursalDes() {
		return sucursalDes;
	}

	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}
	
	
}
