package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.presupuesto.BusquedaConveniosDispatchActions;
import cl.gmo.pos.venta.controlador.presupuesto.PresupuestoDispatchActions;
import cl.gmo.pos.venta.controlador.presupuesto.PresupuestoHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ClienteDAOImpl;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;
import cl.gmo.pos.venta.web.forms.BusquedaPresupuestosForm;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;

public class ControllerPresupuesto implements Serializable{
	
	
	private static final long serialVersionUID = 834007943950085993L;
	Session sess = Sessions.getCurrent();
	PresupuestoHelper helper = new PresupuestoHelper();
	
	private PresupuestoForm presupuestoForm;
	private BusquedaConveniosForm busquedaConveniosForm;
	private PresupuestoDispatchActions presupuestoDispatchActions;
	private BusquedaConveniosDispatchActions busquedaConveniosDispatchActions;
	private ClienteDAOImpl clienteImp;
	private ClienteBean cliente;
	
	private Date fecha;
	private Date fechaEntrega;	
	
	private AgenteBean agenteBean;
	private FormaPagoBean formaPagoBean;
	private DivisaBean divisaBean;
	private IdiomaBean idiomaBean;
	private ProductosBean productoBean;
	
	private String fpagoDisable;
	private String agenteDisable;
	
	HashMap<String,Object> objetos;
	private Window wBusqueda;
	private boolean bWin=true;
	private Window winPresupuesto;
	
	private BeanControlBotones 	beanControlBotones;
	private BeanControlCombos 	beanControlCombos;
	
	//Variables auxiliares para validaciones
	//Situacion Pre y Post cambio de valor
	
	private double dto_total_monto =0;
    private double dto_total=0;	
    
    private String selConvenio;
    
    private String usuario;
	private String sucursal;
	private String sucursalDes;
	
	@Init
	public void inicial(@ExecutionArgParam("origen")String arg) {   
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);
		sucursal = (String)sess.getAttribute(Constantes.STRING_SUCURSAL);
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		sess.setAttribute(Constantes.STRING_PRESUPUESTO, 0);
        
        beanControlBotones = new BeanControlBotones();	
		beanControlCombos  = new BeanControlCombos();
		
		agenteBean = new AgenteBean(); 
		formaPagoBean = new FormaPagoBean();
		divisaBean = new DivisaBean();
		idiomaBean = new IdiomaBean();		
		productoBean = new ProductosBean();	
		
		clienteImp = new ClienteDAOImpl();
		cliente = new ClienteBean();
		
		presupuestoDispatchActions = new PresupuestoDispatchActions();
		busquedaConveniosDispatchActions = new BusquedaConveniosDispatchActions();
		
		presupuestoForm = new PresupuestoForm();
		busquedaConveniosForm = new BusquedaConveniosForm();		
		
		fecha 		 = new Date(System.currentTimeMillis());
		fechaEntrega = new Date(System.currentTimeMillis());
		
		fpagoDisable="True";
		agenteDisable="True";
		selConvenio = "true";	
		
		if (arg.equals("menu"))
			presupuestoDispatchActions.cargaFormulario(presupuestoForm, sess);		
		
		//Posicion incial
		
		beanControlBotones.setEnableNew("false");
		beanControlBotones.setEnablePrint("true");
		beanControlBotones.setEnableEliminar("true");
		beanControlBotones.setEnableBuscar("false");
		beanControlBotones.setEnableListar("true");
		beanControlBotones.setEnableGenerico1("true");
		beanControlBotones.setEnableGrabar("true");		
		
		//el presupuesto proviene de contactologia
		
		if(arg.equals("contactologia") || arg.equals("graduacion")) {
			
			presupuestoForm.setCliente(sess.getAttribute("cliente").toString());
			presupuestoForm.setNombre_cliente(sess.getAttribute("nombre_cliente").toString());
			
			presupuestoDispatchActions.IngresaPresupuestoGraduacion(presupuestoForm, sess);
		} 		
				
		
	}


	
	
	//===================== Acciones de la ToolBar ======================
	//===================================================================
	
	//============ Nuevo Presupuesto ===============
	//==============================================
	
	@NotifyChange({"presupuestoForm","beanControlBotones","fpagoDisable","agenteDisable","divisaBean","idiomaBean","formaPagoBean","agenteBean"})
	@Command
	public void nuevoPresupuesto() {		
		
		fpagoDisable="False";
		agenteDisable="False";		
		
		presupuestoDispatchActions.nuevoFormulario(presupuestoForm, sess);
		
		presupuestoForm.setDivisa("PESO");
		presupuestoForm.setIdioma("CAST");		
		presupuestoForm.setAgente(sess.getAttribute(Constantes.STRING_USUARIO).toString());
		presupuestoForm.setForma_pago("1");
		//presupuestoForm.setFlujo(Constantes.STRING_NUEVO);
		
		posicionaCombos();
		
		if (!bWin) {
			wBusqueda.detach();
			bWin=true;
		}		
		
		//Posicion incial
		
		beanControlBotones.setEnableNew("true");
		beanControlBotones.setEnablePrint("true");
		beanControlBotones.setEnableEliminar("true");
		beanControlBotones.setEnableBuscar("false");
		beanControlBotones.setEnableListar("true");
		beanControlBotones.setEnableGenerico1("true");
		beanControlBotones.setEnableGrabar("true");	
		
		presupuestoForm.setListaProductos(new ArrayList<ProductosBean>());
	}
	
	//=========== Graba Presupuesto ===============
	//=============================================
	
	@NotifyChange({"presupuestoForm","beanControlBotones"})
	@Command
	public void grabarPresupuesto() {		
		
		//validaciones varias
		
		if (presupuestoForm.getCodigo_suc().equals("")){
			Messagebox.show("No se ha iniciado un nuevo presupuesto");
			return;
		}
		
		//Optional<FormaPagoBean> a = Optional.ofNullable(formaPagoBean);
		Optional<AgenteBean> b = Optional.ofNullable(agenteBean);
		
		if(!b.isPresent()){
			Messagebox.show("No ha seleccionado el agente");
			return;			
		}
		
		if(presupuestoForm.getListaProductos().size() == 0){
			Messagebox.show("El presupuesto no tiene lineas");
			return;				
		}
		
		//sess.setAttribute(Constantes.STRING_FORMULARIO, "PRESUPUESTO");
		presupuestoForm.setEstado(Constantes.STRING_FORMULARIO);
		presupuestoForm.setAccion("ingresa_presupuesto");		
		presupuestoForm.setForma_pago(formaPagoBean.getId());
		presupuestoForm.setAgente(agenteBean.getUsuario());	
		
		
		presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);		
		Messagebox.show("Grabacion exitosa");	
		
		//Activar botones
		
		beanControlBotones.setEnablePrint("false");
		beanControlBotones.setEnableEliminar("false");
		beanControlBotones.setEnableBuscar("false");
		beanControlBotones.setEnableListar("false");
		beanControlBotones.setEnableGenerico1("false");
		beanControlBotones.setEnableGrabar("false");		
		
	}
	
	//=========== Selecciona Presupuesto ============
	//===============================================	
	
	@NotifyChange({"presupuestoForm","agenteBean","divisaBean","formaPagoBean","idiomaBean","beanControlBotones"})
	@GlobalCommand
	public void presupuestoSeleccionado(@BindingParam("arg")PresupuestoForm arg,
										@BindingParam("arg2")PresupuestosBean arg2) {		
		
		int index=0;		
		
		for (PresupuestosBean p : arg.getListaPresupuestos()) {			
			if (p.getCodigo().equals(arg2.getCodigo())) break;
			index++;			
		}		
		
		presupuestoForm = arg;		
		
		sess.setAttribute(Constantes.STRING_PRESUPUESTO, index);
		presupuestoForm.setAccion(Constantes.STRING_SELECCIONA_PRESUPUESTO);
		presupuestoForm = presupuestoDispatchActions.cargaPresupuestos(presupuestoForm, sess);	
		
		beanControlBotones.setEnableNew("true");
		beanControlBotones.setEnablePrint("false");
		beanControlBotones.setEnableEliminar("false");		
		beanControlBotones.setEnableGenerico1("false");		
		
		//salvo el descuento original
		asignaDescAux();
		posicionaCombos();		
	}
	
	@NotifyChange({"presupuestoForm","agenteBean","divisaBean","formaPagoBean","idiomaBean","beanControlBotones"})
	@GlobalCommand
	public void seleccionaProductoBusqueda(@BindingParam("arg")BusquedaPresupuestosForm arg,
										   @BindingParam("arg2")PresupuestosBean arg2) {		
		
				
		presupuestoForm.setAccion(Constantes.STRING_CARGA_PRESUPUESTO_SELECCION);
		presupuestoForm.setAddProducto(arg2.getCodigo());
		
		presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);		
		
		beanControlBotones.setEnableNew("true");
		beanControlBotones.setEnablePrint("false");
		beanControlBotones.setEnableEliminar("false");		
		beanControlBotones.setEnableGenerico1("false");		
		
		//salvo el descuento original
		asignaDescAux();
		posicionaCombos();		
	}
	
	
	
	//============ Imprimir Presupuesto ==============
	//================================================	
	
	@Command
	public void imprimirPresupuesto() {
		
		Window window = (Window)Executions.createComponents(
                "/zul/reportes/ReportePresupuesto.zul", null, null);		
        window.doModal();
	}	
	
	//================= Crear presupuesto =================
	//=================================================
	
	@NotifyChange({"presupuestoForm"})
	@Command
	public void crearEncargo(@BindingParam("win")Window win) {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		winPresupuesto = win;
		
		if (!presupuestoForm.getEstado().equals("cerrado")){		
		
			Messagebox.show("Esta seguro que desea traspasar el Presupuesto a un Encargo","Traspaso de Presupuesto", 
					Messagebox.YES | 
					Messagebox.NO, 
					Messagebox.QUESTION, new EventListener<Event>() {			
			@Override
			public void onEvent(Event e) throws Exception {				
					if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {
						
						BeanGlobal bg = presupuestoDispatchActions.traspasoPedido(presupuestoForm, sess);						
						
						presupuestoForm = (PresupuestoForm)bg.getObj_1();
						String accion = (String)bg.getObj_2();
						
						if (accion.equals(Constantes.FORWARD_ENCARGO)) {			
							if (!bWin) {
								wBusqueda.detach();
							}
							
							winPresupuesto.detach();
							
							objetos.put("origen", "presupuesto");							
							Window window = (Window)Executions.createComponents(
					                "/zul/encargos/encargos.zul", null, objetos);			
					        window.doModal();	
					        
					        
						}
					}						
				}
			});	
			
		} else {
			
			Messagebox.show("Este presupuesto, ya ha sido transferido a Encargo");
		}	
	}	
	
	
	//==================== Eliminar presupuesto =================================
	//===========================================================================
	@NotifyChange({"presupuestoForm"})
	@Command
	public void eliminar_Presupuesto(){
		
		if (!presupuestoForm.getEstado().equals("cerrado")){
			
			Messagebox.show("ALERTA!! va a proceder a eliminar este registro, si desea eliminarlo de click en ACEPTAR de lo contrario de click en CANCELAR.","Eliminar Encargo", 
					Messagebox.OK | 
					Messagebox.CANCEL, 
					Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event e) throws Exception {				
						if( ((Integer) e.getData()).intValue() == Messagebox.OK ) {								
							
							//presupuestoForm.setAccion(accion);
							presupuestoForm = presupuestoDispatchActions.eliminarPresupuesto(presupuestoForm, sess);
							BindUtils.postGlobalCommand(null, null, "accionNuevoPedido", null);
							
							Messagebox.show("Presupuesto eliminado");							
						}						
					}
			});				
			
		}else {
			Messagebox.show("No se puede eliminar, el presupuesto esta cerrado");
			
		}		
		
	}
	
	
	//==================== Buscar Presupuestos ========================
	//=================================================================
	@NotifyChange({"presupuestoForm"})
	@Command
	public void busquedaPresupuesto(){
		
		Window winBuscaPresupuesto = (Window)Executions.createComponents(
                "/zul/presupuestos/BuscarPresupuesto.zul", null, null);
		
		winBuscaPresupuesto.doModal();
		
	}
	
	
	//==================== Listar Presupuestos ========================
	//=================================================================
	@Command
	public void listar_detalles() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("presupuestoForm",presupuestoForm);
		
		Window winListaPresupuesto = (Window)Executions.createComponents(
                "/zul/presupuestos/ListarPresupuesto.zul", null, objetos);
		
		winListaPresupuesto.doModal();		
	}	
	
	
	//===================== Acciones comunes de la ventana ======================
	//===========================================================================
	
	//===================== BUsqueda de convenios ===============================
	//===========================================================================
	
	@NotifyChange("*")
	@GlobalCommand
	public void accionNuevoPedido() {	
		beanControlBotones.setEnableNew("false");
		this.nuevoPresupuesto();
	}
	
	@NotifyChange({"presupuestoForm","busquedaConveniosForm"})
	@Command
	public void busquedaRapidaConvenio() {
		
		Window winSeleccionaConvenio=new Window();
		
		if(!presupuestoForm.getEstado().equals("cerrado")) {
		
			if (!presupuestoForm.getConvenio().equals("")) {				
				
				sess.setAttribute("convenio", presupuestoForm.getConvenio());
				
				BeanGlobal bg = busquedaConveniosDispatchActions.buscarConvenioAjax(busquedaConveniosForm, sess);
				//param1 : descripcion
				//param2 : cdg
				//param3 : isapre				
				presupuestoForm.setConvenio_det((String)bg.getObj_1());	
				//presupuestoForm.setIsapre((String)bg.getObj_3());
				
				if(presupuestoForm.getConvenio_det().equals(""))
				{
					presupuestoForm.setConvenio_det("");
					presupuestoForm.setConvenio("");
					//presupuestoForm.setIsapre("N");
				}
				
				busquedaConveniosDispatchActions.selecciona_convenio_cdg(busquedaConveniosForm, sess);
				
				busquedaConveniosForm.setSel_convenio((String)bg.getObj_2());
				busquedaConveniosForm.setSel_convenio_det((String)bg.getObj_1());
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","presupuesto");
				objetos.put("origen","presupuesto");
				objetos.put("win",winSeleccionaConvenio);
				
				//se llama ventana convenio
				 winSeleccionaConvenio = (Window)Executions.createComponents(
		                "/zul/presupuestos/SeleccionaConvenio.zul", null, objetos);		
				winSeleccionaConvenio.doModal();		
				
				
			}else {				
				Messagebox.show("Debe ingresar un c�digo de convenio");			
			}		
		}else {
			Messagebox.show("No se pueden modificar convenio, presupuesto esta cerrado");	
		}
	}
	
	
	@NotifyChange({"presupuestoForm","busquedaConveniosForm"})
	@Command
	public void busquedaConvenio() {
		
		if(!presupuestoForm.getEstado().equals("cerrado")) {
			
			objetos = new HashMap<String,Object>();		
			objetos.put("busquedaConvenios",busquedaConveniosForm);
			objetos.put("ventana","presupuesto");
			objetos.put("origen","presupuesto");
			
			Window window = (Window)Executions.createComponents(
	                "/zul/presupuestos/BusquedaConvenio.zul", null, objetos);		
	        window.doModal();			
			
		}else {
			Messagebox.show("No se pueden modificar convenio, presupuesto esta cerrado");	
		}		
	}	
	
	
	@NotifyChange({"presupuestoForm","selConvenio"})
	@GlobalCommand
	public void respVentanaConvenioPres(@BindingParam("busquedaConvenios")BusquedaConveniosForm convenio) {
		
		selConvenio="false";		
		presupuestoForm.setConvenio(convenio.getSel_convenio());
		presupuestoForm.setConvenio_det(convenio.getSel_convenio_det());
		
	}
	
	@NotifyChange({"presupuestoForm","selConvenio"})
	@Command
	public void eliminaConvenioSeleccionado() {
		
		presupuestoForm.setAccion("elimina_convenio");		
		presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
		selConvenio="true";	
	}
	
	
	
	@NotifyChange({"presupuestoForm","beanControlBotones"})
	@Command
	public void buscarCliente() {
		
		try {
			
			
			presupuestoForm.setEstaGrabado(2);
			cliente = helper.traeClienteSeleccionado(presupuestoForm.getNif(),null);
			
			if (!cliente.getNif().equals("")) {
			
				
				presupuestoForm.setNif(cliente.getNif());
				presupuestoForm.setDvnif(cliente.getDvnif());
				presupuestoForm.setNombre_cliente(cliente.getNombre() + " " + cliente.getApellido());
				presupuestoForm.setCliente(cliente.getCodigo());
				
				GraduacionesBean graduacion = helper.traeUltimaGraduacionCliente(cliente.getCodigo());	
				presupuestoForm.setGraduacion(graduacion);
				
				sess.setAttribute("nombre_cliente",cliente.getNombre() + " " + cliente.getApellido());			
				sess.setAttribute(Constantes.STRING_CLIENTE, cliente.getCodigo());
	        	sess.setAttribute(Constantes.STRING_CLIENTE_VENTA, cliente.getCodigo());	        	
	        	sess.setAttribute("NOMBRE_CLIENTE",cliente.getNombre() + " " + cliente.getApellido());	
	        	
	        	presupuestoForm.setAccion("agregarCliente");
	        	//presupuestoForm.setFlujo(Constantes.STRING_FORMULARIO);                 
	    		presupuestoForm = presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
	    		
	    		
	    		//activo botones
	    		beanControlBotones.setEnableListar("false");	    		
	    		beanControlBotones.setEnableGrabar("false");	    		
	    		
					
			}else {
				Messagebox.show("El cliente no existe");
			}
				
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	
	@Command
	public void buscaProducto() {		
		
		if (bWin) {
			objetos = new HashMap<String,Object>();
			objetos.put("objetoForm",presupuestoForm);		
			wBusqueda = (Window)Executions.createComponents(
	                "/zul/presupuestos/SearchProducto.zul", null, objetos);
			
			wBusqueda.doModal();
			bWin=false;
		}else {
			wBusqueda.setVisible(true);
		}
       
	}
	
	
	@NotifyChange({"productoBean"})
	@Command
	public void actualizaDetalles(@BindingParam("arg")ProductosBean arg ) {
		
		productoBean = arg;			
	}	
	
	
	@Command
	public void salir(@BindingParam("arg")Window arg) {
		
		Messagebox.show("Salir de Presupuesto","Notificacion",
				Messagebox.YES|
				Messagebox.NO,
				Messagebox.QUESTION ,new EventListener<Event>() {

			@Override
			public void onEvent(Event e) throws Exception {				
				if(  ((Integer) e.getData()).intValue() == Messagebox.YES) {
					
					if (!bWin) wBusqueda.detach();
					
					arg.detach();
				}					
			}			
		});		
	}
		
	
	
	@NotifyChange({"presupuestoForm"})
    @GlobalCommand
	public void actProdGridPresupuesto(@BindingParam("producto")ProductosBean arg, @BindingParam("tipo")String tipo) {
		
		//no viene la graduaciion
		arg.setImporte(arg.getPrecio());
		arg.setCantidad(1);
		
		sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, presupuestoForm.getListaProductos());
		presupuestoForm.setAccion(Constantes.STRING_AGREGAR_PRODUCTOS);
		
		try {
			
			presupuestoForm.setCantidad(arg.getCantidad());
			presupuestoForm.setAddProducto(arg.getCod_barra());
			//ventaPedidoForm.setGraduacion(arg.getg);
			presupuestoForm.setOjo(arg.getOjo());
			presupuestoForm.setDescripcion(tipo);		
			
			presupuestoForm = presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}	
		
		
		
		/*arg.setImporte(arg.getPrecio());
		arg.setCantidad(1);
		
		ArrayList<ProductosBean> productos = new ArrayList<ProductosBean>();
		
		if (presupuestoForm.getListaProductos() == null) {
			productos.add(arg);
			presupuestoForm.setListaProductos(productos);
		}else {
			productos = presupuestoForm.getListaProductos();
			productos.add(arg);
			presupuestoForm.setListaProductos(productos);
		}	*/
			
		actTotal(presupuestoForm.getListaProductos());
		System.out.println("estoy en otro controlador "+arg.getDescripcion());				
	}
	
	@NotifyChange({"presupuestoForm"})	
	@Command
	public void deleteItem(@BindingParam("arg")ProductosBean b){
		
		presupuestoForm.getListaProductos().remove(b);		
		actTotal(presupuestoForm.getListaProductos());
	}
	
	@NotifyChange("presupuestoForm")	
	public void actTotal(List<ProductosBean> arg){
		int sumar=0;
		
		sumar = arg.stream().mapToInt(ProductosBean::getImporte).sum();
		presupuestoForm.setSubTotal(sumar);
		presupuestoForm.setTotal(sumar);
		presupuestoForm.setTotalPendiante(sumar - presupuestoForm.getDescuento());
		
		//System.out.println("nuevo total:" + total);
	}		
	
	@NotifyChange({"presupuestoForm"})
	@Command
	public void actImporteGrid(@BindingParam("arg")ProductosBean arg){
		Integer newImport=0;		
		
		newImport = arg.getPrecio() * arg.getCantidad();
		
		for(ProductosBean b : presupuestoForm.getListaProductos()) {
			if(b.getCod_barra().equals(arg.getCod_barra())) {
				b.setImporte(newImport);
				break;
			}
		}	
		
		/*Optional<ProductosBean> p = ventaDirectaForm.getListaProductos()
				.stream()
				.filter(s -> s.getCod_barra().equals(arg.getCod_barra()))
				.findFirst()	;*/
		
		actTotal(presupuestoForm.getListaProductos());
		System.out.println("nuevo importe " + newImport);
	}	
	
	
	public void posicionaCombos() {
			
		Optional<AgenteBean> a = presupuestoForm.getListaAgentes().stream().filter(s -> presupuestoForm.getAgente().equals(s.getUsuario())).findFirst();		
		if (a.isPresent()) agenteBean = a.get(); else agenteBean=null;		
		
		Optional<DivisaBean> b = presupuestoForm.getListaDivisas().stream().filter(s -> presupuestoForm.getDivisa().equals(s.getId())).findFirst();
		if (b.isPresent()) divisaBean = b.get(); else divisaBean=null;	
		
		Optional<FormaPagoBean> c = presupuestoForm.getListaFormasPago().stream().filter(s -> presupuestoForm.getForma_pago().equals(s.getId())).findFirst();
		if (c.isPresent()) formaPagoBean = c.get(); else formaPagoBean=null;	
		
		Optional<IdiomaBean> d = presupuestoForm.getListaIdiomas().stream().filter(s -> presupuestoForm.getIdioma().equals(s.getId())).findFirst();
		if (d.isPresent()) idiomaBean = d.get(); else idiomaBean=null;
		
	}
	
	@Command
	public void busquedaCliente() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClientePresupuesto");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	
	
	@NotifyChange({"presupuestoForm"})
	@GlobalCommand
	public void buscarClientePresupuesto(@BindingParam("cliente")ClienteBean cliente) {
		
		presupuestoForm.setNif(cliente.getNif());
		this.buscarCliente();
		
	}
	
	
	
	//Combos Precargados para evitar recargas
	//========================================
	/*	
		public void cargaFamilias() {		
			try {			
				familiaBeans = utilesDaoImpl.traeFamilias("DIRECTA");
				//cargaSubFamilias("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	*/
	
	//================= Validaciones Varias =====================
	//===========================================================
	
	@Command
	public void asignaDescAux() {
		
		dto_total_monto = presupuestoForm.getDescuento();
		System.out.println("valor original descuento" + dto_total_monto);
	}	
	
	@NotifyChange({"presupuestoForm"})
	@Command
	public void actualiza_descuento(@BindingParam("index")int index, @BindingParam("dcto")int dcto) {
		
		long campo = 0;
		long descuento_max = 0;
		String tipo="";
		objetos = new HashMap<String,Object>();
		
		campo = dcto;		
		objetos.put("retorno","devuelveDescuento_lineaMonto_Encargo");
		
		/*Optional<String> tp = Optional.ofNullable(tipoPedidoBean.getCodigo());
		
		if (!tp.isPresent() || tp.get().equals(""))		
			tipo = "0";	
		else
			tipo = tp.get();*/
		
		if (presupuestoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("El Presupuesto esta cerrada, no es posible modificar");
			return;
		}	
		
		
		
		if ((campo < 0) || (campo > 100)) {
			Messagebox.show("Valor debe estar entre 0 y 100");
			//ventaPedidoForm.setDtcoPorcentaje(Integer.valueOf(String.valueOf(dto_total)));
			presupuestoForm.getListaProductos().get(index).setDescuento(0);
			return;		
		}
		
		
		descuento_max = presupuestoForm.getPorcentaje_descuento_max();
		
		if (campo <= descuento_max) {       	
        	try {
        		//document.getElementById('cantidad_descuento').value = campo.replace(',','.');	
        		presupuestoForm.setCantidad_descuento(campo);
        		presupuestoForm.setAccion("descuento_linea");
        		presupuestoForm.setAddProducto(String.valueOf(index));
        		presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
				
			} catch (Exception e) {				
				e.printStackTrace();
			}			
		}else {	
			
			sess.setAttribute("tipo", tipo);
			sess.setAttribute("_IndexDescuento", index);
			
			Window winAutoriza = (Window)Executions.createComponents(
	                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
			winAutoriza.doModal();		
			
		}	
		
	}
	
	
	
	@NotifyChange({"presupuestoForm"})
	@Command
	public void actualiza_descuento_total_monto() {
		
		boolean compara=true;
		//dto_total_monto es la variable de respaldo del descuento
		
		Double dto;
		Double total;
		long campo = 0;
		
		objetos = new HashMap<String,Object>();
		objetos.put("retorno","devuelveDescuento_totalMonto_Presupuesto");
		
		total = presupuestoForm.getSubTotal();
		dto   = (presupuestoForm.getDescuento() * 100) / total;	
		campo = (long)presupuestoForm.getDescuento();
		
		if (presupuestoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("El presupuesto esta cerrado, no es posible modificar productos");
			return;
		}
		
		//si el porcentaje de descuento es mayor que el maximo dispuesto
		//entonces se carga el autorizador
		
		if(dto_total_monto > 0) {
			
			
			if (presupuestoForm.getDescuento() > presupuestoForm.getTotal()) {
				Messagebox.show("Valor no puede ser mayor al monto total");
				presupuestoForm.setDescuento(dto_total_monto);
				return;
			}
			
			if (dto < presupuestoForm.getPorcentaje_descuento_max()) {
				
				presupuestoForm.setAccion("descuento_total_monto");
			    presupuestoForm.setCantidad_linea(Integer.parseInt(String.valueOf(campo)));			
			    presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
			    
			    presupuestoForm.setDtcoPorcentaje(0);	
			    presupuestoForm.setDescuento(0);
			}else {
				
				//solicito autorizacion				
				Window wAutoriza = (Window)Executions.createComponents(
		                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);
				
				wAutoriza.doModal();				
			}   
		    
			
		}else {
			
			if(presupuestoForm.getDescuento() > 0) {				
				
				if (presupuestoForm.getDescuento() > presupuestoForm.getTotal()) {
					Messagebox.show("Valor no puede ser mayor al monto total");
					presupuestoForm.setDescuento(dto_total_monto);
					return;
				}				
				
				
				if(dto < presupuestoForm.getPorcentaje_descuento_max()) {
					
					presupuestoForm.setAccion("descuento_total_monto");
				    presupuestoForm.setCantidad_linea(Integer.parseInt(String.valueOf(campo)));			
				    presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
				    
				    presupuestoForm.setDtcoPorcentaje(0);
				    presupuestoForm.setDescuento(0);
					
				}else {
					
					Window wAutoriza = (Window)Executions.createComponents(
			                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);
					
					wAutoriza.doModal();					
				}						
			}		
			
		}	
		
	}
	
	@NotifyChange({"presupuestoForm"})
	@Command
	public void actualiza_descuento_total() {
		
		//variable original
		//dto_total
		long campo = 0;
		long descuento_max = 0;
		String tipo="";
		
		objetos = new HashMap<String,Object>();
		objetos.put("retorno","devuelveDescuento_totalMonto_Presupuesto");
		
		campo = (long)presupuestoForm.getDtcoPorcentaje();
		
		if (presupuestoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("La venta esta cerrada, no es posible modificar");
			return;
		}			
		
		
		if ((campo < 0) || (campo > 100)) {
			Messagebox.show("Valor debe estar entre 0 y 100");
			presupuestoForm.setDtcoPorcentaje(Integer.valueOf(String.valueOf(dto_total)));
			return;		
		}
		
		
		if (dto_total > 0) {		
				
			descuento_max = presupuestoForm.getPorcentaje_descuento_max();
			
			if(campo <= descuento_max) {					
	        	//document.getElementById('cantidad_descuento').value = campo.replace(',','.'); 	        	
	        	try {
	        		presupuestoForm.setCantidad_descuento(campo);
	        		presupuestoForm.setAccion("descuento_total");					
					presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
					
					presupuestoForm.setDtcoPorcentaje(0);
					presupuestoForm.setDescuento(0);
				} catch (Exception e) {						
					e.printStackTrace();
				}		        	
				
			}else {
				//autorizador				
				
				Window winAutoriza = (Window)Executions.createComponents(
		                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
				winAutoriza.doModal();				
											
			}			
			
			
		}else {		
			
			if(campo > 0) {
				
				descuento_max = presupuestoForm.getPorcentaje_descuento_max();
				
				if(campo <= descuento_max) {					
		        	//document.getElementById('cantidad_descuento').value = campo.replace(',','.'); 	        	
		        	try {
		        		presupuestoForm.setCantidad_descuento(campo);
		        		presupuestoForm.setAccion("descuento_total");
		        		presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
		        		
		        		presupuestoForm.setDtcoPorcentaje(0);
		        		presupuestoForm.setDescuento(0);
					} catch (Exception e) {						
						e.printStackTrace();
					}		        	
					
				}else {
					//autorizador					
						
					Window winAutoriza = (Window)Executions.createComponents(
			                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
					winAutoriza.doModal();							
										
				}				
				
			}			
		}		
	}
	
	//=====================Valida Descripcion ===========================
		//===================================================================
		@NotifyChange({"presupuestoForm"})
		@Command
		public void actualiza_descripcion(@BindingParam("index")int index, @BindingParam("producto")ProductosBean producto ) {
			
			if(producto.getDescripcion().equals("")) {			
				Messagebox.show("Debe ingresar una descripcion del producto para continuar");
				//descripcionFocus=true;
				presupuestoForm.getListaProductos().get(index).setDescripcion("Agregar Descripcion");
				return;
			}else {			
				
				try {
					presupuestoForm.setAccion("agrega_descripcion");
					presupuestoForm.setAddProducto(String.valueOf(index));
					presupuestoForm.setDescripcion(producto.getDescripcion());
					
					presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
				} catch (Exception e) {				
					e.printStackTrace();
				}
				
			}	
			
		}	
	
	
	//===================== Retorno del autorizador =====================
	//===================================================================
	@NotifyChange({"presupuestoForm"})
	@GlobalCommand
	public void devuelveDescuento_totalMonto_Presupuesto(@BindingParam("valores")BeanGlobal valores) {
		
		
		presupuestoForm.setDescuento(0);
	}
	
		
	
	//=================getter and setter=========================
	//============================================================
	public PresupuestoForm getPresupuestoForm() {
		return presupuestoForm;
	}

	public void setPresupuestoForm(PresupuestoForm presupuestoForm) {
		this.presupuestoForm = presupuestoForm;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}


	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}

	public FormaPagoBean getFormaPagoBean() {
		return formaPagoBean;
	}

	public void setFormaPagoBean(FormaPagoBean formaPagoBean) {
		this.formaPagoBean = formaPagoBean;
	}

	public DivisaBean getDivisaBean() {
		return divisaBean;
	}

	public void setDivisaBean(DivisaBean divisaBean) {
		this.divisaBean = divisaBean;
	}

	public IdiomaBean getIdiomaBean() {
		return idiomaBean;
	}

	public void setIdiomaBean(IdiomaBean idiomaBean) {
		this.idiomaBean = idiomaBean;
	}

	public String getFpagoDisable() {
		return fpagoDisable;
	}

	public void setFpagoDisable(String fpagoDisable) {
		this.fpagoDisable = fpagoDisable;
	}

	public String getAgenteDisable() {
		return agenteDisable;
	}

	public void setAgenteDisable(String agenteDisable) {
		this.agenteDisable = agenteDisable;
	}

	public ProductosBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductosBean productoBean) {
		this.productoBean = productoBean;
	}

	public BusquedaConveniosForm getBusquedaConveniosForm() {
		return busquedaConveniosForm;
	}

	public void setBusquedaConveniosForm(BusquedaConveniosForm busquedaConveniosForm) {
		this.busquedaConveniosForm = busquedaConveniosForm;
	}
	
	//========== Generales control de botones y acciones ===============

	public String getSelConvenio() {
		return selConvenio;
	}

	public void setSelConvenio(String selConvenio) {
		this.selConvenio = selConvenio;
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

	public BeanControlBotones getBeanControlBotones() {
		return beanControlBotones;
	}

	public void setBeanControlBotones(BeanControlBotones beanControlBotones) {
		this.beanControlBotones = beanControlBotones;
	}

	public BeanControlCombos getBeanControlCombos() {
		return beanControlCombos;
	}

	public void setBeanControlCombos(BeanControlCombos beanControlCombos) {
		this.beanControlCombos = beanControlCombos;
	}	
	
}
