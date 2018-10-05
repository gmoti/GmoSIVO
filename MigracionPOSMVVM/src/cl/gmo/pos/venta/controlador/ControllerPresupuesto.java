package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
import cl.gmo.pos.venta.web.forms.PresupuestoForm;

public class ControllerPresupuesto implements Serializable{
	
	/**
	 * 
	 */
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
	
	@Wire
	private Window winPresupuesto;
	
	//Variables auxiliares para validaciones
	//Situacion Pre y Post cambio de valor
	
	private double dto_total_monto =0;
    private double dto_total=0;	
    
    private String selConvenio;
    
    private String usuario;
	private String sucursal;
	private String sucursalDes;
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view) {
		
        Selectors.wireComponents(view, this, false);
		
		fecha 		 = new Date(System.currentTimeMillis());
		fechaEntrega = new Date(System.currentTimeMillis());
		
		fpagoDisable="True";
		agenteDisable="True";
		selConvenio = "true";
		
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
		
		sess.setAttribute(Constantes.STRING_PRESUPUESTO, 0);
		presupuestoDispatchActions.cargaFormulario(presupuestoForm, sess);
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);
		sucursal = (String)sess.getAttribute(Constantes.STRING_SUCURSAL);
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
	}


	@Command
	public void listar_detalles() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("presupuestoForm",presupuestoForm);
		
		Window window = (Window)Executions.createComponents(
                "/zul/presupuestos/BusquedaPresupuesto.zul", null, objetos);
		
        window.doModal(); 		
		
	}
	
	
	//===================== Acciones de la ToolBar ======================
	//===================================================================
	
	//============ Nuevo Presupuesto ===============
	//==============================================
	
	@NotifyChange({"presupuestoForm","fpagoDisable","agenteDisable","divisaBean","idiomaBean","formaPagoBean","agenteBean"})
	@Command
	public void nuevoPresupuesto() {		
		
		fpagoDisable="False";
		agenteDisable="False";
		
		//presupuestoForm = new PresupuestoForm();
		//sess.setAttribute(Constantes.STRING_PRESUPUESTO, 0);
		presupuestoForm = presupuestoDispatchActions.nuevoFormulario(presupuestoForm, sess);
		
		presupuestoForm.setDivisa("PESO");
		presupuestoForm.setIdioma("CAST");		
		presupuestoForm.setAgente(sess.getAttribute(Constantes.STRING_USUARIO).toString());
		presupuestoForm.setForma_pago("1");
		
		posicionaCombos();
		
		if (!bWin) {
			wBusqueda.detach();
			bWin=true;
		}		
	}
	
	//=========== Graba Presupuesto ===============
	//=============================================
	
	@NotifyChange("presupuestoForm")
	@Command
	public void grabarPresupuesto() {		
		
		//sess.setAttribute(Constantes.STRING_FORMULARIO, "PRESUPUESTO");
		presupuestoForm.setEstado(Constantes.STRING_FORMULARIO);
		presupuestoForm.setAccion("ingresa_presupuesto");		
		
		//presupuestoForm.setCodigo(Constantes.STRING_BLANCO);		
		presupuestoForm.setForma_pago(formaPagoBean.getId());
		presupuestoForm.setAgente(agenteBean.getUsuario());		
		
		presupuestoForm = presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);		
		Messagebox.show("Grabacion exitosa");		
	}
	
	//=========== Selecciona Presupuesto ============
	//===============================================	
	
	@NotifyChange({"presupuestoForm","agenteBean","divisaBean","formaPagoBean","idiomaBean"})
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
	public void crearEncargo() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();			
		
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
														
						}						
					}
			});				
			
		}		
		
	}
	
	
	//==================== Listar Presupuestos ========================
	//=================================================================
	@NotifyChange({"presupuestoForm"})
	@Command
	public void busquedaPresupuesto(){}
	
	
	
	//===================== Acciones comunes de la ventana ======================
	//===========================================================================
	
	//===================== BUsqueda de convenios ===============================
	//===========================================================================
	
	@NotifyChange({"presupuestoForm","busquedaConveniosForm"})
	@Command
	public void busquedaRapidaConvenio() {
		
		if(!presupuestoForm.getEstado().equals("cerrado")) {
		
			if (!presupuestoForm.getConvenio().equals("")) {				
				
				sess.setAttribute("convenio", presupuestoForm.getConvenio());
				
				BeanGlobal bg = busquedaConveniosDispatchActions.buscarConvenioAjax(busquedaConveniosForm, sess);
				//param1 : descripcion
				//param2 : cdg
				//param3 : isapre				
				presupuestoForm.setConvenio_det((String)bg.getObj_1());				
				busquedaConveniosDispatchActions.selecciona_convenio_cdg(busquedaConveniosForm, sess);
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","presupuesto");
				objetos.put("origen","convenio");
				
				//se llama ventana convenio
				Window window = (Window)Executions.createComponents(
		                "/zul/presupuestos/SeleccionaConvenio.zul", null, objetos);		
		        window.doModal();		
				
				
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
	
	
	
	@NotifyChange({"presupuestoForm"})
	@Command
	public void buscarCliente(@BindingParam("arg")String arg) {
		
		try {
			
			
			presupuestoForm.setEstaGrabado(2);
			cliente = helper.traeClienteSeleccionado(arg,null);
			
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
	        	presupuestoForm.setFlujo(Constantes.STRING_FORMULARIO);                 
	    		presupuestoForm = presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
					
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
	public void actProdGridPresupuesto(@BindingParam("producto")ProductosBean arg) {
		
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
			presupuestoForm.setDescripcion(arg.getDescripcion());		
			
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
			
		Optional<AgenteBean> a = presupuestoForm.getListaAgentes().stream().filter(s -> presupuestoForm.getAgente().equals(s.getClass())).findFirst();		
		if (a.isPresent()) agenteBean = a.get(); else agenteBean=null;		
		
		Optional<DivisaBean> b = presupuestoForm.getListaDivisas().stream().filter(s -> presupuestoForm.getDivisa().equals(s.getId())).findFirst();
		if (b.isPresent()) divisaBean = b.get(); else divisaBean=null;	
		
		Optional<FormaPagoBean> c = presupuestoForm.getListaFormasPago().stream().filter(s -> presupuestoForm.getForma_pago().equals(s.getId())).findFirst();
		if (c.isPresent()) formaPagoBean = c.get(); else formaPagoBean=null;	
		
		Optional<IdiomaBean> d = presupuestoForm.getListaIdiomas().stream().filter(s -> presupuestoForm.getIdioma().equals(s.getId())).findFirst();
		if (d.isPresent()) idiomaBean = d.get(); else idiomaBean=null;
		
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
	public void actualiza_descuento_total_monto() {
		
		boolean compara=true;
		//dto_total_monto es la variable de respaldo del descuento
		
		Double dto;
		Double total;
		
		total = presupuestoForm.getSubTotal();
		dto   = (presupuestoForm.getDescuento() * 100) / total;		
		
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
			    presupuestoForm.setCantidad_linea(Integer.parseInt(String.valueOf(presupuestoForm.getDescuento())));			
			    presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);				
				
			}else {
				
				//solicito autorizacion				
				Window wAutoriza = (Window)Executions.createComponents(
		                "/zul/presupuestos/AutorizadorDescuento.zul", null, null);
				
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
				    presupuestoForm.setCantidad_linea(Integer.parseInt(String.valueOf(presupuestoForm.getDescuento())));			
				    presupuestoDispatchActions.IngresaPresupuesto(presupuestoForm, sess);
					
				}else {
					
					Window wAutoriza = (Window)Executions.createComponents(
			                "/zul/presupuestos/AutorizadorDescuento.zul", null, null);
					
					wAutoriza.doModal();					
				}						
			}		
			
		}	
		
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
	
}
