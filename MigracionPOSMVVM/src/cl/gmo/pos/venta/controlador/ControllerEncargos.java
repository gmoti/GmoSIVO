package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.controlador.presupuesto.BusquedaConveniosDispatchActions;
import cl.gmo.pos.venta.controlador.presupuesto.PresupuestoHelper;
import cl.gmo.pos.venta.controlador.ventaDirecta.BusquedaProductosDispatchActions;

import cl.gmo.pos.venta.controlador.ventaDirecta.VentaPedidoDispatchActions;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.respuesta.RespuestaEncargos;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;
import cl.gmo.pos.venta.web.forms.BusquedaPedidosForm;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;
import cl.gmo.pos.venta.web.forms.DevolucionForm;

import cl.gmo.pos.venta.web.helper.FichaTecnicaHelper;


public class ControllerEncargos implements Serializable {

	
	private static final long serialVersionUID = -3904397835765271540L;
	Logger log = Logger.getLogger( this.getClass() );
	
	
	Session sess = Sessions.getCurrent();
	PresupuestoHelper helper = new PresupuestoHelper();
	HashMap<String,Object> objetos;
	private Window wBusqueda;
	private boolean bWin=true;
	private boolean wArcliVisible=false;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	
	
	private SeleccionPagoForm 		seleccionPagoForm;
	private VentaPedidoForm 		ventaPedidoForm;
	private BusquedaProductosForm 	busquedaProductosForm;	
	private BusquedaConveniosForm 	busquedaConveniosForm;
	private BusquedaClientesForm    busquedaClientesForm;
	
	private VentaPedidoDispatchActions 		 ventaPedidoDispatchActions;
	//private DevolucionDispatchActions 		 devolucionDispatchActions;
	private BusquedaProductosDispatchActions busquedaProductosDispatchActions;	
	private BusquedaConveniosDispatchActions busquedaConveniosDispatchActions;
	private BusquedaClientesDispatchActions  busquedaClientesDispatchActions;
	
	private AgenteBean 		agenteBean;
	private FormaPagoBean 	formaPagoBean;
	private DivisaBean 		divisaBean;
	private IdiomaBean 		idiomaBean;
	private TipoPedidoBean 	tipoPedidoBean;
	private PromocionBean   promocionBean;
	
	private ClienteBean 	cliente;
	private ProductosBean 	productoBean;
	
	private String fpagoDisable;
	private String agenteDisable;	
	
	private Date 	fecha;
	private Date 	fechaEntrega;
	private String 	sucursal;
	
	private BeanControlBotones 	beanControlBotones;
	private BeanControlCombos 	beanControlCombos;
	
	private byte[] bytes;
	private ReportesHelper reportes;
	private AMedia fileContent;
	
	private String selConvenio;
	
	private String usuario;	
	private String sucursalDes;
	
	//variables validaciones
	
	private long dto_total_monto = 0;
	private long dto_total = 0;
	
	//variables particulares de validaciones
	private boolean descripcionFocus=false;
	private boolean encargoEntregado=false;
	
	@Init
	public void inicial(@ExecutionArgParam("origen")String arg) {	
		
		sess.setAttribute(Constantes.STRING_FORMULARIO, "PEDIDO");

		usuario 	= (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		sucursal 	= sess.getAttribute(Constantes.STRING_SUCURSAL).toString();			
			
		beanControlBotones = new BeanControlBotones();	
		beanControlCombos  = new BeanControlCombos();
		
		ventaPedidoForm          		= new VentaPedidoForm();
		busquedaConveniosForm    		= new BusquedaConveniosForm();
		busquedaClientesForm			= new BusquedaClientesForm();
		
		ventaPedidoDispatchActions 		= new VentaPedidoDispatchActions();
		//devolucionDispatchActions  		= new DevolucionDispatchActions();
		busquedaClientesDispatchActions = new BusquedaClientesDispatchActions();
		
		cliente      = new ClienteBean();
		productoBean = new ProductosBean();	
		
		agenteBean 		= new AgenteBean(); 
		formaPagoBean 	= new FormaPagoBean();
		divisaBean 		= new DivisaBean();
		idiomaBean 		= new IdiomaBean();
		tipoPedidoBean 	= new TipoPedidoBean();
		promocionBean   = new PromocionBean();
		
		fpagoDisable ="True";
		agenteDisable="True";	
		selConvenio = "true";
		ventaPedidoForm.setPromocion("0");
		ventaPedidoForm.setConvenio("");		
		
		fecha= new Date(System.currentTimeMillis());
		fechaEntrega= new Date(System.currentTimeMillis());		
		ventaPedidoForm.setFecha(dt.format(new Date(System.currentTimeMillis())));
		ventaPedidoForm.setHora(tt.format(new Date(System.currentTimeMillis())));		
		
		sess.setAttribute("_Convenio","0");	
		
		//Si el encargo es invocado desde presupuesto, debe pasar por aqui
		if(arg.equals("presupuesto")) {			
			ventaPedidoForm.setDesde_presupuesto(Constantes.STRING_TRUE);
			ventaPedidoDispatchActions.IngresaVentaPedidoDesdePresupuesto(ventaPedidoForm, sess);	
			
			beanControlBotones.setEnableNew("false");
			beanControlBotones.setEnableListar("false");
			
			beanControlBotones.setEnableGenerico1("true");
			beanControlBotones.setEnableGenerico2("true");	
			
			beanControlBotones.setEnableGrid("false");
			beanControlBotones.setEnableMulti("false");
			
			beanControlCombos.setComboAgenteEnable("false");
			beanControlCombos.setComboDivisaEnable("true");
			beanControlCombos.setComboFpagoEnable("false");
			beanControlCombos.setComboIdiomaEnable("true");
			beanControlCombos.setComboPromoEnable("false");
			beanControlCombos.setComboTiposEnable("false");				
			
		}else if( arg.equals("menu")){	
			
			ventaPedidoDispatchActions.CargaFormulario(ventaPedidoForm, sess);	
		
			beanControlBotones.setEnableNew("false");
			beanControlBotones.setEnableListar("true");	
			beanControlBotones.setEnableGrid("true");
			beanControlBotones.setEnableMulti("true");
			
			beanControlCombos.setComboAgenteEnable("true");
			beanControlCombos.setComboDivisaEnable("true");
			beanControlCombos.setComboFpagoEnable("true");
			beanControlCombos.setComboIdiomaEnable("true");
			beanControlCombos.setComboPromoEnable("true");
			beanControlCombos.setComboTiposEnable("true");	
			
		}else if( arg.equals("contactologia") || arg.equals("graduacion")){
			
			ventaPedidoForm.setCliente(sess.getAttribute("cliente").toString());
			ventaPedidoForm.setNombre_cliente(sess.getAttribute("nombre_cliente").toString());
			
			ventaPedidoDispatchActions.IngresaVentaPedidoGraduacion(ventaPedidoForm, sess);
			
		}
		
		creaItemSelecciona();		
		posicionCombo();		
		
		//inicializo descuento
		dto_total_monto = ventaPedidoForm.getDescuento();
		dto_total = ventaPedidoForm.getDtcoPorcentaje();
		encargoEntregado=false;
		
	}
	
	
	//===================== Acciones de la ToolBar ======================
	//===================================================================
	
	//============ Cliente Internacional ===========
	//==============================================
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void cliente_Internacional(@BindingParam("pop")Popup pop) {
		
		if(!ventaPedidoForm.getNif().equals("")){
			if(!ventaPedidoForm.getCodigo_suc().equals("")){
  				//showPopWin('<%=request.getContextPath()%>/VentaPedido.do?method=cliente_inter', 440, 180,null,false);	
				pop.open(pop);
			}else{
				  Messagebox.show("Debes agregar un nuevo encargo.");
			}		  
		  }else{
			  Messagebox.show("Debes asociar al cliente generico primero");			  
		  }	
	}	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actualiza_Cliente_Internacional() {
		System.out.println("pais:" + ventaPedidoForm.getNacionalidad());
	}
	
	//============ Nuevo Pedido ====================
	//==============================================
	@NotifyChange({"ventaPedidoForm","encargoEntregado","beanControlBotones","beanControlCombos","agenteBean","divisaBean","formaPagoBean","idiomaBean","tipoPedidoBean","productoBean","fecha","fechaEntrega"})
	@Command
	public void nuevo_Pedido() {		
		
		ventaPedidoDispatchActions.nuevoFormulario(ventaPedidoForm, sess);
		ventaPedidoForm.setListaProductos(new ArrayList<ProductosBean>());
		
		fecha= new Date(System.currentTimeMillis());
		fechaEntrega= new Date(System.currentTimeMillis());	
		
		ventaPedidoForm.setFecha(dt.format(new Date(System.currentTimeMillis())));
		ventaPedidoForm.setHora(tt.format(new Date(System.currentTimeMillis())));
		ventaPedidoForm.setPromocion("0");			
		ventaPedidoForm.setConvenio("");
		
		beanControlCombos.setComboAgenteEnable("false");
		beanControlCombos.setComboDivisaEnable("false");
		beanControlCombos.setComboFpagoEnable("false");
		beanControlCombos.setComboIdiomaEnable("false");
		beanControlCombos.setComboPromoEnable("false");
		beanControlCombos.setComboTiposEnable("false");		
		
		beanControlBotones.setEnableListar("true");	
		beanControlBotones.setEnableGrid("false");
		beanControlBotones.setEnableMulti("true");
		encargoEntregado=false;
		
		creaItemSelecciona();
		posicionCombo();
		
		if (!bWin) {
			wBusqueda.detach();
			bWin=true;
		}		
	}
	
	
	//=========== Lista encargos del cliente  =======
	//===============================================	
	@NotifyChange("ventaPedidoForm")
	@Command
	public void cargaPedidoCliente() {
		
		BusquedaPedidosForm busquedaPedidosForm = new BusquedaPedidosForm();
		
		if (ventaPedidoForm.getNif().trim().equals("") || ventaPedidoForm.getNif().trim().equals("0") || ventaPedidoForm.getNombre_cliente().trim().equals("")) {
			
			Messagebox.show("Debe indicar el Nif");
			return;
		}		
		
		ventaPedidoDispatchActions.cargaPedidoAnterior(ventaPedidoForm, sess);
		//Constantes.STRING_ACTION_LISTA_PEDIDOS
		
		objetos = new HashMap<String,Object>();		
		objetos.put("listaPedidos",sess.getAttribute(Constantes.STRING_ACTION_LISTA_PEDIDOS));
		
		Window windowBusquedaEncargo = (Window)Executions.createComponents(
                "/zul/encargos/BusquedaEncargo.zul", null, objetos);
		
		windowBusquedaEncargo.doModal(); 		
		
	}
	
	//=========Busqueda avanzada de pedidos =========
	//===============================================
	@Command
	public void busquedaAvanzada() {		
		
		sess.setAttribute("flujo", ventaPedidoForm.getFlujo());
		
		Window windowBusquedaAvanzadaEncargo = (Window)Executions.createComponents(
                "/zul/encargos/BusquedaAvanzadaEncargo.zul", null, null);
		
		windowBusquedaAvanzadaEncargo.doModal(); 
	}
	
	
	//=========Busqueda avanzada de pedidos =========
	//===============================================
	@NotifyChange("ventaPedidoForm")	
	@Command
	public void eliminarPedido() {
		
		float sumdes = 0;
		int sumimpor = 0;
		int liberado= 0;
		int cprod  = 0;
		int totalencargo = 0; 	
				
		totalencargo = Integer.parseInt(String.valueOf(ventaPedidoForm.getTotal()));
		
		for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {			
			sumdes   += pb.getDescuento();	
			sumimpor +=	pb.getImporte();
			liberado += Integer.parseInt(pb.getLiberado());	
			cprod++;			
		}		
		
		
		//no modificar si tiene pagos efectuados
		if(ventaPedidoForm.getTiene_pagos().equals("true")) {
			Messagebox.show("El encargo tiene pagos realizados, no es posible modificarlo");
			return;
		}
				
		if (totalencargo==0 && (sumdes == (100 * cprod)) && (sumimpor == 0) && (cprod >= 3) ) {
			
			Messagebox.show("No es posible eliminar el encargo, ya se encuentra Liberado.");
			
		}else {
			
			Messagebox.show("ALERTA!! va a proceder a eliminar este registro, si desea eliminarlo de click en ACEPTAR de lo contrario de click en CANCELAR.","Eliminar Encargo", 
					Messagebox.OK | 
					Messagebox.CANCEL, 
					Messagebox.QUESTION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event e) throws Exception {				
						if( ((Integer) e.getData()).intValue() == Messagebox.OK ) {								
							
							ventaPedidoForm.setAccion("eliminarPedidoSeleccion");
							ventaPedidoForm = ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);	
							//RespuestaEncargos.evaluaEstado(ventaPedidoForm, sess);
							
							BindUtils.postGlobalCommand(null, null, "accionNuevoPedido", null);
							
							Messagebox.show("Presupuesto eliminado");
						}						
					}
			});	
			
			
			
		}		
	}
	
	
	@NotifyChange({"ventaPedidoForm","beanControlBotones","beanControlCombos","agenteBean","divisaBean","formaPagoBean","idiomaBean","tipoPedidoBean","productoBean","fecha","fechaEntrega"})
	@GlobalCommand
	public void accionNuevoPedido() {
		
		this.nuevo_Pedido();
	}
	
	
	
	//=========Busqueda avanzada de pedidos =========
	//===============================================	
	@Command
	public void mostrar_pagos_boletas() {
		
		seleccionPagoForm = new SeleccionPagoForm();
		
		seleccionPagoForm.setFech_pago(ventaPedidoForm.getFecha());
		seleccionPagoForm.setFecha(ventaPedidoForm.getFecha());
		seleccionPagoForm.setTipo_doc('G');
		seleccionPagoForm.setOrigen("PEDIDO");
		seleccionPagoForm.setSerie(ventaPedidoForm.getCodigo_suc()+"/"+ventaPedidoForm.getCodigo());
		
		objetos = new HashMap<String,Object>();		
		objetos.put("seleccionPago",seleccionPagoForm);
		objetos.put("ventanaOrigen","encargo");
		
		Window windowMostrarPagosBoleta = (Window)Executions.createComponents(
                "/zul/encargos/MostrarPagosBoleta.zul", null, objetos);
		
		windowMostrarPagosBoleta.doModal();	
		
	}
	
	
	//==============Genera Ficha Tecnica ============
	//===============================================	
	@NotifyChange("ventaPedidoForm")
	@Command
	public void generaFichaTecnica() {
		
		String codigo_pedido ="";
		String codigo_suc = "";
		String cliente = "";
		
		String result="";
		boolean haymulti=false;		
		
		codigo_pedido = ventaPedidoForm.getCodigo();
		codigo_suc = ventaPedidoForm.getCodigo_suc();
		cliente = ventaPedidoForm.getCliente();
			
		
		
		if(ventaPedidoForm.getCodigo().equals("") || ventaPedidoForm.getCliente().equals("")) {			
			return;
		}			
		
		result = ventaPedidoDispatchActions.validaTrioMultioferta(sess);	
		
		if(result.equals("")) 
			return;
		
		
		if(result.equals("ok")) {
			haymulti=true;				
			//cdg	= ventaPedidoForm.getCodigo_suc() + "/" + ventaPedidoForm.getCodigo();
			
			if(existeTrio()) {
				 
				CreaFichaTallerServlet(ventaPedidoForm);
			}else {
				if(haymulti){
   			 		
					CreaFichaTallerServlet(ventaPedidoForm);
   			 	}else{       			 		
   			 		Messagebox.show("Debe existir al menos un trio guardado o lente contacto graduable");
   			 		return;
   			 	}					
			}
			
		}else { //mo ok
			
			haymulti=false;				
			//cdg	= ventaPedidoForm.getCodigo_suc() + "/" + ventaPedidoForm.getCodigo();
			
			if(existeTrio()){				 
				 
				CreaFichaTallerServlet(ventaPedidoForm);
  			 }else{
  			 	if(haymulti){
  			 		
  			 		CreaFichaTallerServlet(ventaPedidoForm);
  			 	}else{      			 		
  			 		Messagebox.show("Debe existir al menos un trio guardado o lente contacto graduable");
  			 		return;
  			 	}
  			 }
			
		}			
					
	}	
	
	
	private boolean existeTrio() {
		boolean bRet=false;
		int cnt=0;
		int group=0;
		
		for (ProductosBean pb :  ventaPedidoForm.getListaProductos()) {
			
			group = Integer.parseInt(pb.getGrupo());
			
			if (group > 0) {
				cnt++;

				if (cnt >= 3) {
					bRet=true;
					break;
				}	
			}				
		}
		
		
		return bRet;
	}
	
	
	private void CreaFichaTallerServlet(VentaPedidoForm ventaPedidoForm) {		
		
		String cdg 		= ventaPedidoForm.getCodigo_suc() + "/" + ventaPedidoForm.getCodigo();
		String cliente  = ventaPedidoForm.getCliente().toString();
		String saldo 	= String.valueOf(ventaPedidoForm.getTotal());		
		int clienteint=0;
		int saldoint= 0;
		
		
		reportes = new ReportesHelper();		
		
		
		if(null != cliente){
			try{
				clienteint = Integer.parseInt(cliente);
			}catch(Exception ex){
				clienteint = 0;
			}			
		}
		
		if(null != saldo){
			try{
				saldoint = Integer.parseInt(saldo);
			}catch(Exception ex){
				saldoint = 0;
			}			
		}
		
		
		ArrayList<FichaTecnicaBean> lista = new FichaTecnicaHelper().traeFichaTaller(cdg, clienteint, saldoint);		
		log.info("CreaFichaTallerServlet:service  inicio");
		
		//String file = request.getSession().getServletContext().getRealPath("");
		//String file = Sessions.getCurrent().get
		String file="";
		
		try {			
			bytes = reportes.creaFichaTaller(sess, lista, file);
			final AMedia media = new AMedia("FichaTaller.pdf", "pdf", "application/pdf", bytes);			
			
			
			objetos = new HashMap<String,Object>();
			objetos.put("reporte",media);
			objetos.put("titulo","Ficha Tecnica");			
			
			Window windowVisorReporte = (Window)Executions.createComponents(
	                "/zul/reportes/VisorReporte.zul", null, objetos);
			
			windowVisorReporte.doModal();	
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		log.info("CreaFichaTallerServlet:service  fin");	
		
	}
	
	
	//============= Genera Ficha Cliente ============
	//===============================================	
	@NotifyChange("ventaPedidoForm")
	@Command
	public void generaFichaCliente() {
		
		String tieneTrioMulti="";
		boolean haymulti=false;
		boolean respuesta=false;
		
		String codigo_pedido	=ventaPedidoForm.getCodigo();
		String codigo_suc		=ventaPedidoForm.getCodigo_suc();
		String cliente			=ventaPedidoForm.getCliente();
		String cdg="";
		
		
		if(ventaPedidoForm.getCodigo().equals("") || ventaPedidoForm.getCliente().equals("")) {			
			return;
		}			
		
		tieneTrioMulti = ventaPedidoDispatchActions.validaTrioMultioferta(sess);	
		
		if (tieneTrioMulti.equals(""))
			return;
		
		if (tieneTrioMulti.equals("ok")) {
			
			haymulti=true;			
				
			cdg = codigo_suc +"/"+codigo_pedido;
			respuesta = existeTrio();
			
			if (respuesta) {
				//llama servlet
				CreaFichaClienteServlet(ventaPedidoForm);
			}else {
				if(haymulti) {
					//llama servlet
					CreaFichaClienteServlet(ventaPedidoForm);
				}else {
					Messagebox.show("Debe existir al menos un trio guardado o lente contacto graduable");
				}					
			}				
				
		}else {
			haymulti = false;
			
			cdg = codigo_suc +"/"+codigo_pedido;
			respuesta = existeTrio();
			
			if(respuesta){				 
				//llama servlet 
      			CreaFichaClienteServlet(ventaPedidoForm);
  			 }else{
  			 	if(haymulti){
  			 	    //llama servlet
      			 	CreaFichaClienteServlet(ventaPedidoForm);
  			 	}else{  			 		
  			 		Messagebox.show("Debe existir al menos un trio guardado o lente contacto graduable");
  			 	}
  			 }			
		}	
		
	}
	
	
	private void CreaFichaClienteServlet(VentaPedidoForm ventaPedidoForm) {		
		
		reportes = new ReportesHelper();
		String cdg 		= "";
		String cliente  = "";
		
		cdg      = ventaPedidoForm.getCodigo_suc() + "/" + ventaPedidoForm.getCodigo();
		cliente  = ventaPedidoForm.getCliente();		
		
		//new ReportesHelper().creaFichaCliente(sess, cliente);	
		
		sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, ventaPedidoForm.getListaProductos());
		sess.setAttribute(Constantes.STRING_SESSION_FORMULARIO_VTA_PEDIDO, ventaPedidoForm);
		
		bytes = reportes.creaFichaCliente(sess, cliente);
		final AMedia media = new AMedia("FichaCliente.pdf", "pdf", "application/pdf", bytes);
		
		objetos = new HashMap<String,Object>();
		objetos.put("reporte",media);
		objetos.put("titulo","Ficha Cliente");			
		
		Window windowVisorReporte = (Window)Executions.createComponents(
                "/zul/reportes/VisorReporte.zul", null, objetos);
		
		windowVisorReporte.doModal();
		
	}
	
	
	//============== Graba Pedido =================
	//=============================================
	//@NotifyChange({"ventaPedidoForm"})
	@NotifyChange({"ventaPedidoForm","beanControlBotones","beanControlCombos","agenteBean","divisaBean","formaPagoBean","idiomaBean","tipoPedidoBean","productoBean","fecha","fechaEntrega"})
	@Command
	public void ingresa_pedido() {		
		
		boolean valtienda=false;
		boolean valGrabar=false;
		
		Optional<TipoPedidoBean> tp  = Optional.ofNullable(tipoPedidoBean);	
		if (!tp.isPresent())
			tipoPedidoBean = new TipoPedidoBean();
		
		Optional<String> cvn = Optional.ofNullable(ventaPedidoForm.getConvenio());
		if(!cvn.isPresent())
			ventaPedidoForm.setConvenio("");
		
		//no modificar si tiene pagos efectuados
		if(ventaPedidoForm.getTiene_pagos().equals("true")) {
			Messagebox.show("El encargo tiene pagos realizados, no es posible modificarlo");
			return;
		}
		
		
		if (ventaPedidoForm.getNombre_cliente().equals("")) {
			Messagebox.show("Debe seleccionar un Cliente");
			return;
		}
		
		if (agenteBean == null) {
			Messagebox.show("Debe seleccionar un agente");
			return;
		}else {
			if(agenteBean.getUsuario().equals("")) {
				Messagebox.show("Debe seleccionar un agente");
				return;
			}			
		}
		
		if (ventaPedidoForm.getFecha().equals("")) {
			Messagebox.show("Debe ingresar una fecha");
			return;
		}
		
		if (ventaPedidoForm.getListaProductos().size() < 1) {
			Messagebox.show("Debe ingresar articulos para generar cobros");
			return;
		}
		
		
		//actualiza el grupo del la forma
		int longitud = ventaPedidoForm.getListaProductos().size();
		int i = 0;
		String[] grupoAux = new String[longitud];
		
		for (ProductosBean pb : ventaPedidoForm.getListaProductos()) {			
			grupoAux[i] = pb.getGrupo(); 
			i++;
		}
		
		ventaPedidoForm.setGrupo(grupoAux);	
		
		ventaPedidoForm.setAgente(agenteBean.getUsuario());			
		ventaPedidoForm.setForma_pago(formaPagoBean.getId());
		ventaPedidoForm.setIdioma(idiomaBean.getId());
		ventaPedidoForm.setDivisa(divisaBean.getId());
		ventaPedidoForm.setTipo_pedido(tipoPedidoBean.getCodigo());	
		ventaPedidoForm.setFecha_entrega(dt.format(fechaEntrega));	
		
		
		if (!ventaPedidoForm.getFlujo().equals("formulario")) {	
			
			if (ventaPedidoForm.getFlujo().equals("modificar")) {
			
				try {
					valtienda = ventaPedidoDispatchActions.validaTipoPedido(ventaPedidoForm, sess);
					
					if (valtienda) {					
						
		 				ventaPedidoForm.setAccion(Constantes.STRING_INGRESA_PEDIDO);
		 				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
		 				RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
						
					}else {
						
						ventaPedidoForm.setAccion(Constantes.STRING_INGRESA_PEDIDO);
		 				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
		 				RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
					}		
					
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}			
			
			
			}else {
			
				if(tipoPedidoBean.getCodigo().equals("SEG")) {				
					
					try {
						int val = ventaPedidoDispatchActions.validaVentaSeguro(ventaPedidoForm, sess);
						
						switch (val) {
						case 1:		
							
							Messagebox.show("El encargo a utilizar no esta asociado a garantia.");
							break;
						case 2:
							
							Messagebox.show("El encargo garantia ya fue utilizado, no es posible volver a ocuparlo.");
							break;
						case 3:						
			 				
			 				ventaPedidoForm.setAccion(Constantes.STRING_INGRESA_PEDIDO);
			 				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			 				RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
							break;
						default:
							
							Messagebox.show("Problema al conectarse a la Base de Datos.");
							break;
						} 					
						
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}		
					
					
				}else {
					
					
						try {
							valtienda = ventaPedidoDispatchActions.validaTipoPedido(ventaPedidoForm, sess);
							
						if (valtienda) {
						
							
						}else {
							
			 				ventaPedidoForm.setAccion(Constantes.STRING_INGRESA_PEDIDO);
			 				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);			 				
			 				RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
			 				}
						
						} catch (Exception e) {					
							e.printStackTrace();
						}
					
				}		
			}			
		}		
	}
	
	
	@NotifyChange("ventaPedidoForm")
	@Command
	public void validaCantidadProductosMulit() {		
		
		String cantidad="";
		String codigoMulti="";
		
		BeanGlobal bg = ventaPedidoDispatchActions.validaCantidadProductosMultiofertas(ventaPedidoForm, sess);
		
		cantidad = (String)bg.getObj_1();
		codigoMulti = (String)bg.getObj_2();
		
		Optional<String> cant   = Optional.ofNullable(cantidad);
		Optional<String> codMul = Optional.ofNullable(codigoMulti);
		
		if (!cant.isPresent()) cantidad=""; 
		if (!codMul.isPresent()) codigoMulti="";		
		
		if (!cantidad.equals("menor")) {			
			valida_venta();
		}else {			
			Messagebox.show("La cantidad de productos en la multioferta "+codigoMulti+" no esta completa");			
		}
		
	}
	
	@NotifyChange("ventaPedidoForm")
	private void valida_venta() {
		
		SeleccionPagoForm seleccionPagoForm = new SeleccionPagoForm();
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) {
			Messagebox.show("Venta finalizada, no es posible generar cobros");
			return;
		}
		
		if (ventaPedidoForm.getNombre_cliente().equals("")) {
			Messagebox.show("Debe seleccionar un Cliente");
			return;
		}	
		
		if (ventaPedidoForm.getListaProductos().size() < 1) {
			Messagebox.show("Debe ingresar articulos para generar cobros");
			return;
		}
		
		if (ventaPedidoForm.getCodigo().equals("")) {
			Messagebox.show("Debe guardar la venta, antes de cobrar");
			return;
		}		
		
		
		try {
			ventaPedidoForm.setAccion("valida_pedido");
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			
			sess.setAttribute("_Cliente", cliente);
			RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
			
			/*
			if (ventaPedidoForm.getEstado().equals(Constantes.STRING_GENERA_COBRO)) {
				
				//aqui se cargan las variables de session
				ventaPedidoDispatchActions.generaVentaPedido(ventaPedidoForm, sess);				
				seleccionPagoForm = new SeleccionPagoForm();		
				
				objetos = new HashMap<String,Object>();
				objetos.put("cliente",cliente);
				objetos.put("pagoForm",seleccionPagoForm);
				objetos.put("ventaOrigenForm",ventaPedidoForm);
				objetos.put("origen","PEDIDO");
				
				Window windowPagoVentaDirecta = (Window)Executions.createComponents(
		                "/zul/venta_directa/SeleccionPago.zul", null, objetos);
				
				windowPagoVentaDirecta.doModal();				
				
			}else {
				
				Messagebox.show(ventaPedidoForm.getError());
			}	*/		
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		
	} 
	
	
	//=========== Recupera Encargo seleccionado======
	//===============================================	
		
	@NotifyChange({"ventaPedidoForm","beanControlCombos","beanControlBotones","encargoEntregado","agenteBean","divisaBean","formaPagoBean","idiomaBean","fecha","fechaEntrega","tipoPedidoBean"})
	@GlobalCommand
	public void encargoSeleccionado(@BindingParam("arg")ArrayList<PedidosPendientesBean> arg,
									@BindingParam("arg2")PedidosPendientesBean arg2) {				
		
		try {
			sess.setAttribute(Constantes.STRING_ACTION_CDG, arg2.getCdg());
			ventaPedidoForm.setAccion(Constantes.STRING_ACTION_CARGA_PEDIDO_SELECCION);
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);			
			//encargoEntregado=evaluaEntrega();
			java.util.Date lFecha =  dt.parse(ventaPedidoForm.getFecha()); 
			java.util.Date lFechaEntrega=  dt.parse(ventaPedidoForm.getFecha_entrega());
			//ventaPedidoForm.getHora()
			
			fecha = new Date(lFecha.getTime());
			fechaEntrega = new Date(lFechaEntrega.getTime());			
			
			beanControlCombos.setComboAgenteEnable("false");
			beanControlCombos.setComboDivisaEnable("false");
			beanControlCombos.setComboFpagoEnable("false");
			beanControlCombos.setComboIdiomaEnable("false");
			beanControlCombos.setComboPromoEnable("false");
			beanControlCombos.setComboTiposEnable("false");		
			
			beanControlBotones.setEnableListar("true");	
			beanControlBotones.setEnableGrid("false");
			beanControlBotones.setEnableMulti("true");			
			
			posicionComboNuevo();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}	
		
	}
	
	//======= pago exitoso en venta pedido =======
	
	@NotifyChange({"ventaPedidoForm","encargoEntregado"})
	@GlobalCommand	
    public void creaPagoExitosoEncargo(@BindingParam("seleccionPago")SeleccionPagoForm seleccionPago) {
		
		String boleta="";
		
		ventaPedidoForm.setAccion(Constantes.STRING_PAGO_EXITOSO);			
		//sess.setAttribute(Constantes.STRING_TICKET, ventaPedidoForm.getCodigo_suc() + "/" + ventaPedidoForm.);
		sess.setAttribute(Constantes.STRING_TIPO_DOCUMENTO, seleccionPago.getTipo_doc());
		sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, new ArrayList<ProductosBean>());
		sess.setAttribute(Constantes.STRING_DOCUMENTO, 0);
		sess.setAttribute("SeleccionPagoForm", seleccionPago);
		//sess.setAttribute(Constantes.STRING_TIPO_ALBARAN, ventaPedidoForm.getTipoAlbaran());				
		
		try {
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);	
			postCobro();
			
			if (ventaPedidoForm.getEstado_boleta().contains("TRUE") || ventaPedidoForm.getEstado_boleta().contains("true")) {
				
				Messagebox.show("Error: No se pudo generar la boleta, Intentelo nuevamente.");
			}else {
				
				String valor[] =  ventaPedidoForm.getEstado_boleta().split("_");
				
				//Produccion	
				String url ="http://10.216.4.16/39 " + 
						ventaPedidoForm.getNif().trim() + 
						"-" + ventaPedidoForm.getDvnif().trim() + 
						" " + valor[1].trim()+".pdf";
				
				//Desarrollo
				/*String url ="http://10.216.4.24/39 " + 
						ventaPedidoForm.getNif().trim() + 
						"-" + ventaPedidoForm.getDvnif().trim() + 
						" " + valor[1].trim()+".pdf";*/	
				
				
				if(valor[0].equals("0") || valor[2].equals("true")){
					
					Messagebox.show("Error: No se pudo generar la boleta, Inténtelo nuevamente.");
					return;
					
				}else if(valor[0].equals("1") && valor[2].equals("false")){
					
					objetos = new HashMap<String,Object>();
					objetos.put("documento",url);
					objetos.put("titulo","Encargo");
					
					Window window = (Window)Executions.createComponents("/zul/reportes/VisorDocumento.zul", null, objetos);				
			        window.doModal();					
					
				}else if(valor[0].equals("2") && valor[2].equals("false")){
				
					Messagebox.show("!ATENCIÓN! AGREGAR MAS FOLIOS, SE ESTAN AGOTANDO");
					
					objetos = new HashMap<String,Object>();
					objetos.put("documento",url);
					objetos.put("titulo","Encargo");
				
					Window window = (Window)Executions.createComponents("/zul/reportes/VisorDocumento.zul", null, objetos);				
		        	window.doModal();
				}			
				
				
				//original
				/*objetos = new HashMap<String,Object>();
				objetos.put("documento",url);
				objetos.put("titulo","Encargo");
				
				Window window = (Window)Executions.createComponents("/zul/reportes/VisorDocumento.zul", null, objetos);				
		        window.doModal();*/
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}			
		
	}	
	
	
	//=========== Post Grabacion pago =============
	//=============================================		
	@NotifyChange({"ventaPedidoForm"})
	public void postCobro() {		
				
		Messagebox.show("Venta almacenada con exito");
	}
	
	//=========== Consulta productos multi ==========
	//===============================================				

	@Command
	public void busca_multi() {
		
		busquedaProductosForm 		= new BusquedaProductosForm();
		busquedaProductosDispatchActions = new BusquedaProductosDispatchActions();
		
		try {
			busquedaProductosForm.setAccion("carga");
			busquedaProductosForm = busquedaProductosDispatchActions.busquedaMultiofertaAsoc(busquedaProductosForm, sess);			
			
			objetos = new HashMap<String,Object>();
			objetos.put("busquedaProductos",busquedaProductosForm);			
			
			Window window = (Window)Executions.createComponents(
	                "/zul/encargos/BusquedaProductosMultioferta.zul", null, objetos);
			
	        window.doModal();
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

	
	
	
	//===================== Acciones comunes de la ventana ======================
	//===========================================================================
	
	//===================== BUsqueda de convenios ===============================
	
		@NotifyChange({"ventaPedidoForm","busquedaConveniosForm"})
		@Command
		public void busquedaRapidaConvenio() {
			
			if(!ventaPedidoForm.getEstado().equals("cerrado")) {
			
				if (!ventaPedidoForm.getConvenio().equals("")) {				
					
					sess.setAttribute("convenio", ventaPedidoForm.getConvenio());
					
					BeanGlobal bg = busquedaConveniosDispatchActions.buscarConvenioAjax(busquedaConveniosForm, sess);
					//param1 : descripcion
					//param2 : cdg
					//param3 : isapre				
					ventaPedidoForm.setConvenio_det((String)bg.getObj_1());					
					busquedaConveniosDispatchActions.selecciona_convenio_cdg(busquedaConveniosForm, sess);	
					
					busquedaConveniosForm.setSel_convenio((String)bg.getObj_2());
					busquedaConveniosForm.setSel_convenio_det((String)bg.getObj_1());
					ventaPedidoForm.setIsapre((String)bg.getObj_3());
					
					objetos = new HashMap<String,Object>();		
					objetos.put("busquedaConvenios",busquedaConveniosForm);
					objetos.put("ventana","encargo");
					objetos.put("origen","convenio");
					
					//se llama ventana convenio
					Window window = (Window)Executions.createComponents(
			                "/zul/presupuestos/SeleccionaConvenio.zul", null, objetos);		
			        window.doModal();		
					
					
				}else {				
					Messagebox.show("Debe ingresar un código de convenio");			
				}		
			}else {
				Messagebox.show("No se pueden modificar convenio, presupuesto esta cerrado");	
			}
		}
		
		
		@NotifyChange({"ventaPedidoForm","busquedaConveniosForm"})
		@Command
		public void busquedaConvenio() {
			
			if(!ventaPedidoForm.getEstado().equals("cerrado")) {
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","pedido");
				objetos.put("origen","pedido");
				
				Window window = (Window)Executions.createComponents(
		                "/zul/presupuestos/BusquedaConvenio.zul", null, objetos);		
		        window.doModal();			
				
			}else {
				Messagebox.show("No se pueden modificar convenio, presupuesto esta cerrado");	
			}		
		}	
		
		
		@NotifyChange({"ventaPedidoForm","selConvenio"})
		@GlobalCommand
		public void respVentanaConvenioPedido(@BindingParam("busquedaConvenios")BusquedaConveniosForm convenio) {
			
			/*document.ventaPedidoForm.forma_pago.value = valores[0];
	 		document.ventaPedidoForm.convenio.value = valores[2];
	 		if("" != valores[3])
	 		{
	 			document.ventaPedidoForm.convenio_det.value = valores[3];
	 		}
	 		document.ventaPedidoForm.convenio_ln.value = valores[4];
	 		document.getElementById('accion').value = "cambio_convenio";
        	document.ventaPedidoForm.submit();*/			
			
			try {
				
				selConvenio="false";		
				ventaPedidoForm.setConvenio(convenio.getSel_convenio());
				ventaPedidoForm.setConvenio_det(convenio.getSel_convenio_det());
				
				ventaPedidoForm.setAccion("cambio_convenio");
				
				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		@NotifyChange({"ventaPedidoForm","selConvenio"})
		@Command
		public void eliminaConvenioSeleccionado() {
			
							
			try {
				ventaPedidoForm.setAccion("elimina_convenio");
				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
				selConvenio="true";	
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
	
	
	
	
	@NotifyChange({"ventaPedidoForm","beanControlBotones"})
	@Command
	public void buscarClienteGenerico() {		
		ventaPedidoForm.setNif("66666666");		
		beanControlBotones.setEnableGrid("false");
		beanControlBotones.setEnableGrabar("false");	
		
		buscarCliente();
	}	
	
	
	
	@NotifyChange({"ventaPedidoForm","beanControlBotones"})
	@Command
	public void buscarCliente() {
		
		
		if(ventaPedidoForm.getNif().equals("")) {
			Messagebox.show("Debe ingresar un nif");
			return;
		}		
		
		try {			
			
			sess.setAttribute("nif", ventaPedidoForm.getNif());
			sess.setAttribute("pagina", "encargo");
			
			cliente = busquedaClientesDispatchActions.buscarClienteAjax(busquedaClientesForm, sess);			
			
			if (!cliente.getNif().equals("")) {			
				
				ventaPedidoForm.setNif(cliente.getNif());
				ventaPedidoForm.setDvnif(cliente.getDvnif());
				ventaPedidoForm.setNombre_cliente(cliente.getNombre() + " " + cliente.getApellido());
				ventaPedidoForm.setCliente(cliente.getCodigo());				
				
				sess.setAttribute("nombre_cliente",cliente.getNombre() + " " + cliente.getApellido());			
				sess.setAttribute(Constantes.STRING_CLIENTE, cliente.getCodigo());
	        	sess.setAttribute(Constantes.STRING_CLIENTE_VENTA, cliente.getCodigo());	        	
	        	sess.setAttribute("NOMBRE_CLIENTE",cliente.getNombre() + " " + cliente.getApellido());	
	        	
	        	ventaPedidoForm.setAccion(Constantes.STRING_AGREGAR_CLIENTES);	        	
	        	ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
	        	
	        	beanControlBotones.setEnableListar("false");
	        	beanControlBotones.setEnableBuscar("false");
	        	
	        	beanControlBotones.setEnableGrid("false");
	    		beanControlBotones.setEnableMulti("false");
	        	
					
			}else {
				Messagebox.show("El cliente no existe");
				ventaPedidoForm.setNif("");
			}
				
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	
	@Command
	public void buscaProducto() {
		
		if (ventaPedidoForm.getNif().equals("") || ventaPedidoForm.getNif().equals("0") || ventaPedidoForm.getCliente().equals("")) {
			Messagebox.show("El encargo no tiene un cliente asociado aun");
			return;
		} 
		
		
		
		if (bWin) {
			objetos = new HashMap<String,Object>();
			objetos.put("objetoForm",ventaPedidoForm);		
			wBusqueda = (Window)Executions.createComponents(
	                "/zul/presupuestos/SearchProducto.zul", null, objetos);
			
			wBusqueda.doModal();
			bWin=false;
		}else {
			wBusqueda.setVisible(true);
		}       
	}
	
	
	@NotifyChange({"ventaPedidoForm"})
    @GlobalCommand
	public void actProdGridVentaPedido(@BindingParam("producto")ProductosBean arg,
										@BindingParam("tipo")String tipo,										
										@BindingParam("seg_arm")String seg_arm,
										@BindingParam("cris_esp")String cris_esp,
										@BindingParam("cris_esp_seg")String cris_esp_seg,
										@BindingParam("seg_cristal")boolean seg_cristal) {	
		
		String segundoOjo="";
		double eje = 0;
		double esfera = 0;
		double cilindro= 0;
		int indice=0;
		//cris_esp_seg="1";
		
		if(ventaPedidoForm.getTiene_pagos().equals("true")) {
			Messagebox.show("El encargo tiene pagos realizados, no es posible modificarlo");
			return;
		}		
		
		//no viene la graduaciion
		arg.setImporte(arg.getPrecio());
		arg.setCantidad(1);		//arg cantidad seleccionada
		
		Optional<String> cvn = Optional.ofNullable(ventaPedidoForm.getConvenio());
		if(!cvn.isPresent())
			ventaPedidoForm.setConvenio("");
		
		sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, ventaPedidoForm.getListaProductos());		
		ventaPedidoForm.setAccion(Constantes.STRING_AGREGAR_PRODUCTOS);
		
		try {
			
			ventaPedidoForm.setCantidad(arg.getCantidad());
			ventaPedidoForm.setAddProducto(arg.getCod_barra());
			//ventaPedidoForm.setGraduacion(arg.getg);
			ventaPedidoForm.setOjo(arg.getOjo());
			ventaPedidoForm.setDescripcion(tipo);			
			
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);		
			
			//===============================================
			//Clase manejadora de la respuesta de estados
			//Simula las recarga de jsp
			indice = ventaPedidoForm.getListaProductos().size();			
			sess.setAttribute("productosBean", arg);			
			ventaPedidoForm.setAddProducto(String.valueOf(indice -1));
			RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
			
			
			//valida segundo cristal
			 if(seg_cristal && seg_arm.equals("2")){
				 
				 if (arg.getOjo().equals("derecho")) {
					segundoOjo="izquierdo";			 
					
		 			cilindro  = ventaPedidoForm.getGraduacion().getOI_cilindro();
		 			esfera = ventaPedidoForm.getGraduacion().getOI_esfera();
		 			eje =  ventaPedidoForm.getGraduacion().getOI_eje();					 
				 }else { 
					segundoOjo="derecho";
					
					cilindro  = ventaPedidoForm.getGraduacion().getOD_cilindro();
		 			esfera = ventaPedidoForm.getGraduacion().getOD_esfera();
		 			eje =  ventaPedidoForm.getGraduacion().getOD_eje();						 
				 }
				 
				 if(cilindro < 0){
					 
			 			esfera = esfera + cilindro;
			 			cilindro = Math.abs(cilindro);
			 			
			 			if (eje >= 0 && eje <=90) {
			 				eje = eje + 90;
			 			}
			 			else if(eje >= 91 && eje <=180)
			 			{
			 				eje = eje - 90;
			 			}
			 	 }				 
				 
				 String cilidroParse="";
				 String esferaParse="";
				 String ejeParse="";
				 
				 if (cilindro==0) cilidroParse = "0"; else cilidroParse=String.valueOf(cilindro);
				 if (esfera==0) esferaParse = "0"; else esferaParse=String.valueOf(esfera);
				 if (eje==0) ejeParse = "0"; else ejeParse=String.valueOf((int)eje);
				 
				 String res= arg.getCod_barra() + "," + 
						 segundoOjo + "," + tipo + "," + 
						 cilidroParse + "," + esferaParse + "," + ejeParse;
				 
				 ventaPedidoForm.setSegCris(res);		 
				 String codigo = ventaPedidoDispatchActions.valida_seg_cris(ventaPedidoForm, sess);	
				 
				 if (!codigo.equals("0")) {
					 
					ventaPedidoForm.setAccion("agregarProducto");
	                ventaPedidoForm.setAddProducto(codigo);
	                ventaPedidoForm.setCantidad(1);
	                ventaPedidoForm.setOjo(segundoOjo);
	                ventaPedidoForm.setDescripcion(tipo); 					 

					 if(cris_esp.equals("1")) {						 
						 if(cris_esp_seg.equals("1")){
							ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
							 
							indice = ventaPedidoForm.getListaProductos().size();			
							sess.setAttribute("productosBean", (ProductosBean)ventaPedidoForm.getListaProductos().get(indice -1));			
							ventaPedidoForm.setAddProducto(String.valueOf(indice -1));
							RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
							 
						 }
					 }else {						 
						ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess); 
						 
						indice = ventaPedidoForm.getListaProductos().size();			
						sess.setAttribute("productosBean", (ProductosBean)ventaPedidoForm.getListaProductos().get(indice -1));			
						ventaPedidoForm.setAddProducto(String.valueOf(indice -1));
						RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);
					 }				 
					 
				 }else {
					 Messagebox.show("No hay cristales de la misma familia para la dioptria del ojo "+segundoOjo+".");
					 return;
				 }
			
			 } 		
			 
			 
			 
			 
			actTotal(ventaPedidoForm.getListaProductos());		
				
			//===============================================
			//Clase manejadora de la respuesta de estados
			//Simula las recarga de struts
			/*int indice = ventaPedidoForm.getListaProductos().size();			
			
			sess.setAttribute("productosBean", arg);
			
			ventaPedidoForm.setAddProducto(String.valueOf(indice -1));
			RespuestaEncargos.evaluaEstado(ventaPedidoForm,sess);*/
			
			
		} catch (Exception e) {	
			
			Messagebox.show("Error no identificado:" + e.getMessage());
			
			e.printStackTrace();			
		}	
			
		
		
	}
	
	@NotifyChange({"ventaPedidoForm"})
	@GlobalCommand
	public void cargaAdicionalesArcli(@BindingParam("valores")String[] valores) {	
		
		try {
			ventaPedidoForm.setTipo_armazon(valores[0]);
			ventaPedidoForm.setPuente(valores[1]);
			ventaPedidoForm.setDiagonal(valores[2]);
			ventaPedidoForm.setHorizontal(valores[3]);
			ventaPedidoForm.setVertical(valores[4]);
			ventaPedidoForm.setAddProducto(valores[5]);
			ventaPedidoForm.setAccion("agrega_adicionales_arcli");
			
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		
	}
	
	@Command
	public void seleccionarAdicionalesARCLI(@BindingParam("indice")int indice) {
		
		ventaPedidoForm.setAddProducto(String.valueOf(indice));		
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();
		objetos.put("ventaPedido", ventaPedidoForm);
		
		Window window = (Window)Executions.createComponents(
                "/zul/encargos/AdicionalesArcli.zul", null, objetos);
		
        window.doModal();
		
	}
	
	
	@NotifyChange({"ventaPedidoForm"})
    @Command
	public void multiofertaProducto(@BindingParam("producto")ProductosBean arg, @BindingParam("index")int index) {	
		
		
		if (arg.getFamilia().equals("MUL")) {		
			
			//inicializo los productos asociado a la multioferta
			//sess.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, arg.getListaProductosMultiofertas());
			
			busquedaProductosForm    = new BusquedaProductosForm();
			busquedaProductosForm.setCliente(cliente.getCodigo());
			busquedaProductosForm.setCodigoBusqueda(arg.getCod_barra());
			busquedaProductosForm.setCodigoMultioferta(arg.getCodigo());
			busquedaProductosForm.setIndex_multi(arg.getIndexMulti());			
			busquedaProductosForm.setFecha_graduacion(arg.getFecha_graduacion());	
			busquedaProductosForm.setCdg(ventaPedidoForm.getCodigo_suc() +"/"+ ventaPedidoForm.getCodigo());		
			
			objetos = new HashMap<String,Object>();
			objetos.put("busquedaProductos",busquedaProductosForm);
			objetos.put("origen","encargo");
			objetos.put("beanProducto",arg);
			objetos.put("index",index);
			/*objetos.put("ventaPedido",ventaPedidoForm);*/
			
			Window window = (Window)Executions.createComponents(
	                "/zul/encargos/BusquedaMultiofertas.zul", null, objetos);
			
	        window.doModal();		
		}
	}
	
	
	@NotifyChange({"ventaPedidoForm"})	
	@Command
	public void deleteItem(@BindingParam("arg")ProductosBean b){
		
		Integer index=0;
		
		if(ventaPedidoForm.getEstado().equals("fin")) {			
			Messagebox.show("Venta finalizada, no es posible eliminar productos");
			return;
		}	
		
		for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {			
			
			if (pb.getCod_barra().equals(b.getCod_barra())){
				break;
			}
			index++;
			
		}
		
		
		if (b.getFamilia().equals("MUL")) {
			
			//document.getElementById("index_multi_eliminar").value = indexMulti;
     		//document.getElementById('productoSeleccionado').value = codigo;
     		//document.getElementById('accion').value = "eliminarProductoMultiOferta";
     		//document.ventaDirectaForm.submit();    		
     		ventaPedidoForm.setAccion("eliminarProductoMulti");
     		ventaPedidoForm.setError("");
     		ventaPedidoForm.setAddProducto(index.toString());			
		}else {
			//document.getElementById('productoSeleccionado').value = codigo;
     		//document.getElementById('accion').value = "eliminarProducto";
     		//document.ventaDirectaForm.submit();         		
     		ventaPedidoForm.setAccion("eliminarProducto");
     		ventaPedidoForm.setError("");
     		ventaPedidoForm.setAddProducto(index.toString());      		
		}	
		
 		try {
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			
			/*if (!ventaPedidoForm.getError().equals("")) {
				Messagebox.show(ventaPedidoForm.getError());
			    return;
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();					
		} 		
		
		//ventaPedidoForm.getListaProductos().remove(b);		
		actTotal(ventaPedidoForm.getListaProductos());
	}
	
	@NotifyChange("ventaPedidoForm")	
	public void actTotal(List<ProductosBean> arg){
		int sumar=0;
		
		sumar = arg.stream().mapToInt(ProductosBean::getImporte).sum();
		ventaPedidoForm.setSubTotal(sumar);
		ventaPedidoForm.setTotal(sumar);
		ventaPedidoForm.setTotalPendiante(sumar - ventaPedidoForm.getDescuento());
	}
	
	@NotifyChange({"productoBean"})
	@Command
	public void actualizaDetalles(@BindingParam("arg")ProductosBean arg ) {
		
		productoBean = arg;			
	}
	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actImporteGrid(@BindingParam("arg")ProductosBean arg){
		Integer newImport=0;		
		
		newImport = arg.getPrecio() * arg.getCantidad();
		
		for(ProductosBean b : ventaPedidoForm.getListaProductos()) {
			if(b.getCod_barra().equals(arg.getCod_barra())) {
				b.setImporte(newImport);
				break;
			}
		}	
		
		/*Optional<ProductosBean> p = ventaDirectaForm.getListaProductos()
				.stream()
				.filter(s -> s.getCod_barra().equals(arg.getCod_barra()))
				.findFirst()	;*/
		
		actTotal(ventaPedidoForm.getListaProductos());
		System.out.println("nuevo importe " + newImport);
	}
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actualiza_cantidad(@BindingParam("index")int index, @BindingParam("producto")ProductosBean producto)
	{
		
		int campo=0;
		
		campo = producto.getCantidad();
		
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) 
		{
			Messagebox.show("La venta esta cerrada, no es posible modificar productos");
			return;
		}	
		else
		{
			if (ventaPedidoForm.getFlujo().equals("modificar")) 
			{
				if(!ventaPedidoForm.getBloquea().equals("bloquea"))
				{					
					
					try {
						ventaPedidoForm.setAccion("cantidad");
						ventaPedidoForm.setAddProducto(String.valueOf(index));
						ventaPedidoForm.setCantidad_linea(campo);
						
						ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
						RespuestaEncargos.evaluaEstado(ventaPedidoForm, sess);
						
					} catch (Exception e) {						
						e.printStackTrace();
					}					
	         	}
	         	else
	         	{
	         		Messagebox.show("Encargo bloqueado, no es posible modificar productos");
	         		return;
	         	}
	         
	         }
	         else
	         {
	        	
				try {
					ventaPedidoForm.setAccion("cantidad");
					ventaPedidoForm.setAddProducto(String.valueOf(index));
					ventaPedidoForm.setCantidad_linea(campo);
					
					ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
					RespuestaEncargos.evaluaEstado(ventaPedidoForm, sess);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
	         }
 		}
        	
	}
	
	
	public void posicionComboNuevo() {		
		
		try {
			Optional<AgenteBean> a = ventaPedidoForm.getListaAgentes().stream().filter(s -> ventaPedidoForm.getAgente().equals(s.getUsuario())).findFirst();		
			agenteBean = a.get();	
			
			Optional<DivisaBean> b = ventaPedidoForm.getListaDivisas().stream().filter(s -> ventaPedidoForm.getDivisa().equals(s.getId())).findFirst();
			divisaBean = b.get();		
			
			Optional<IdiomaBean> d = ventaPedidoForm.getListaIdiomas().stream().filter(s -> ventaPedidoForm.getIdioma().equals(s.getId())).findFirst();
			idiomaBean = d.get();	
			
			Optional<FormaPagoBean> e = ventaPedidoForm.getListaFormasPago().stream().filter(s -> ventaPedidoForm.getForma_pago().equals(s.getId())).findFirst();
			formaPagoBean = e.get();	
			
			Optional<TipoPedidoBean> f = ventaPedidoForm.getListaTiposPedidos().stream().filter(s -> ventaPedidoForm.getTipo_pedido().equals(s.getCodigo())).findFirst();
			tipoPedidoBean = f.get();
		}catch(Exception e ) {
			System.out.println("error posicionando combos:"+e.getMessage());
		}
		
	}
	
	
   	
   public void posicionCombo() {
		
		String divisa="PESO";
		String idioma="CAST";
		String formaPago="1";	
		String cero="0";
		
		Optional<DivisaBean> b = ventaPedidoForm.getListaDivisas().stream().filter(s -> divisa.equals(s.getId())).findFirst();
		divisaBean = b.get();		
		
		Optional<IdiomaBean> d = ventaPedidoForm.getListaIdiomas().stream().filter(s -> idioma.equals(s.getId())).findFirst();
		idiomaBean = d.get();
		
		Optional<FormaPagoBean> e = ventaPedidoForm.getListaFormasPago().stream().filter(s -> formaPago.equals(s.getId())).findFirst();
		formaPagoBean = e.get();	
		
		
		
		Optional<AgenteBean> a = ventaPedidoForm.getListaAgentes().stream().filter(s -> "Seleccione".equals(s.getUsuario())).findFirst();
		agenteBean = a.get();
		
		Optional<TipoPedidoBean> c = ventaPedidoForm.getListaTiposPedidos().stream().filter(s -> cero.equals(s.getCodigo())).findFirst();
		tipoPedidoBean = c.get();
		
		Optional<PromocionBean> f = ventaPedidoForm.getListaPromociones().stream().filter(s -> cero.equals(s.getId())).findFirst();
		promocionBean = f.get();		
		
	}
	
	
	@NotifyChange({"formaPagoBean","agenteBean","tipoPedidoBean","divisaBean","idiomaBean","promocionBean"})
	@Command
	public void comboSetNull(@BindingParam("objetoBean")Object arg) {		
		
		if (arg instanceof FormaPagoBean)		
		    formaPagoBean=null;
		if (arg instanceof AgenteBean)		
		    agenteBean=null;		
		if (arg instanceof TipoPedidoBean)		
			tipoPedidoBean=null;
		
		if (arg instanceof DivisaBean)		
			divisaBean=null;
		if (arg instanceof AgenteBean)		
			agenteBean=null;
		if (arg instanceof PromocionBean)		
			promocionBean=null;
		
	}
	
	
	@Command
	public void salir(@BindingParam("win")Window win) {
		
		Messagebox.show("Salir de Venta Pedido","Notificacion",
				Messagebox.YES|
				Messagebox.NO,
				Messagebox.QUESTION ,new EventListener<Event>() {

			@Override
			public void onEvent(Event e) throws Exception {				
				if(  ((Integer) e.getData()).intValue() == Messagebox.YES) {
					
					if (!bWin) wBusqueda.detach();
					
					win.detach();
				}					
			}			
		});		
	}
	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void pedidoEntrega() {	
		
		if(ventaPedidoForm.getCerrado().equals("S")) {
			Messagebox.show("El encargo esta cerrado");
			return;
		}
		
		if(ventaPedidoForm.getEntregado().equals("true")) {
			Messagebox.show("El encargo fue entregado");
			return;
		}	
		
		if (evaluaEntrega()) {			
			return;
		}
		
		
		try {		
			
			ventaPedidoForm.setAccion("pedidoEntrega");
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			
			if (!ventaPedidoForm.isPedidosDelete()) {
				
				if (!ventaPedidoForm.getCodigo_confirmacion().equals("")) {					
					Messagebox.show(ventaPedidoForm.getMsnPedidoEntrega());
				}
				
				return;
			}
			
			
				
			objetos = new HashMap<String,Object>();			
			objetos.put("origen", "encargo");
			
			/*Window windowBusquedaEncargo = (Window)Executions.createComponents(
	                "/zul/encargos/MuestraAlbaran.zul", null, objetos);
			
			windowBusquedaEncargo.doModal(); 	*/	
			
			Window windowBusquedaEncargo = (Window)Executions.createComponents(
	                "/zul/mantenedores/AlbaranBS.zul", null, objetos);
			
			windowBusquedaEncargo.doModal();
			
			
		} catch (Exception e) {
			Messagebox.show("Error en pedido entrega","Error",Messagebox.OK,Messagebox.ERROR);
			e.printStackTrace();
		}	
		
	}
	
	
	@Command
	public void ingresaCupon(@BindingParam("pop")Popup pop) {
		
		try {		
			if (ventaPedidoForm.getListaProductos().size() < 1) {
				Messagebox.show("Debes agregar articulos para usar cupones de DTO.");
				return;
			}	
		}catch (Exception e) {
			Messagebox.show("Debes agregar articulos para usar cupones de DTO.");
			return;
		}
		
		pop.open(400,400);
		
		/*try {
			ventaPedidoDispatchActions.abre_valida_cupon();		
			
		} catch (Exception e) {
			Messagebox.show("Error en validacion de cupon","Error",Messagebox.OK,Messagebox.ERROR);
			e.printStackTrace();
		}				
		*/
	}	
	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void validaCupon(@BindingParam("pop")Popup pop) {
		
		String[] trio = new String[100];
		String[] sor_trio;
		String[] datos;
		
		String estado="";
		String trio_temp="";
		int a=0;
		
		if (ventaPedidoForm.getNumero_cupon().equals("")) {
			Messagebox.show("Debes Ingresar un cupon.");
			return;	
		}
				
		
		for(ProductosBean pb: ventaPedidoForm.getListaProductos()) {			
			if((pb.getFamilia().equals("M") ||  pb.getFamilia().equals("C") || pb.getFamilia().equals("G"))){				
				trio[a] = pb.getFamilia();
				a++;
		    } 
		}
		
		sor_trio = new String[a];
		
		for (int i=0 ; i<a ; i++)			
			sor_trio[i] = trio[i];		
		
		Arrays.sort(sor_trio);
		
		
		try {
			estado = ventaPedidoDispatchActions.valida_cupon(ventaPedidoForm, sess);
			
			datos = estado.split("_");
			
			if(datos[1] != "0"){
				  
				if(sor_trio.length == 3 || sor_trio.length == 1 ){
					
			  		trio_temp = sor_trio[0]+sor_trio[1]+sor_trio[2];

			  		if(sor_trio.length == 3){

			  			if(trio_temp.equals("CCM")){
			  				//bloque 1 switch
			  				switch(datos[0]){	
							  
					  	 	 case "1":	
					  	 		 
					  	 		Messagebox.show("Deseas confirmar el uso del cupon ?","Recuerda que al confirmar el cupon ya no podra ser utilizado", 
										Messagebox.YES | 
										Messagebox.NO, 
										Messagebox.QUESTION, new EventListener<Event>() {			
									@Override
									public void onEvent(Event e) throws Exception {				
											if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {
												
												//$('#numero_cupon',window.parent.document).val(cupon);
												//$.cookie("cupon",cupon);
												sess.setAttribute("cupon",ventaPedidoForm.getNumero_cupon());
												//window.parent.postValida_cupon();
												ventaPedidoForm.setAccion("aplica_cupon");
												ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);										    	
											}
										}
								});
						  	 	
						  	 break;	
						  	 
					  	 	 case "2":	
					  	 		   Messagebox.show("El cupon ya no se encuentra vigente.");
					  	 		   sess.setAttribute("cupon","");							  	 	   						 					  	 	 												
						  	 break;	
						  	 
						  	 case "3":	
						  		  Messagebox.show("El cupon ya fue utilizado, no es posible volver a utilizarlo.");
						  		  sess.setAttribute("cupon","");			 					  	 	 												
						  	 break;	
						  	
						  	 case "4":	
						  		   Messagebox.show("El cupon no es valido.");
						  		   sess.setAttribute("cupon","");						 					  	 	 												
						  	 break;	
						  	 
						  	 case "5":	
						  		 
						  		Messagebox.show("Deseas confirmar el uso del cupon ? . El monto disponible para descuento es de :" + datos[1],
						  				"Recuerda que al confirmar el cupon ya no podra ser utilizado", 
										Messagebox.YES | 
										Messagebox.NO, 
										Messagebox.QUESTION, new EventListener<Event>() {			
									@Override
									public void onEvent(Event e) throws Exception {				
											if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {												
												//$('#numero_cupon',window.parent.document).val(cupon);
												//$.cookie("cupon",cupon);
												sess.setAttribute("cupon",ventaPedidoForm.getNumero_cupon());
												//window.parent.postValida_cupon();
												ventaPedidoForm.setAccion("aplica_cupon");
												ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
											}
										}
								});							  		 
						  							 					  	 	 												
						  	 break;	
						  	 
						  	 case "6":	
						  		   Messagebox.show("El beneficio optico ya fue utilizado, no es posible realizar el descuento.");
						  		   sess.setAttribute("cupon","");	
						  	 break;
						  	 
						  	 case "7":	
						  		   Messagebox.show("El beneficio optico ya fue utilizado, no es posible realizar el descuento.");
						  		   sess.setAttribute("cupon","");	
						  	 break;
						  	 
						  	 case "8":	
						  		   Messagebox.show("El beneficio optico ya fue utilizado, no es posible realizar el descuento.");
						  		   sess.setAttribute("cupon","");	
						  	 break;
						  	 
						  	 case "100":	
						  		   Messagebox.show("No es posible aplicar el cupon, el encargo ya posee un cupon asociado.");
						  		   sess.setAttribute("cupon","");	
						  	 break;
						  	 
						  	 default:
							  	   Messagebox.show("Error , favor contactarse con MDA");
							  	   sess.setAttribute("cupon","");
						  	 break;
			  				}
			  			}else{
			  				Messagebox.show("El encargo no corresponde a un trio optico valido, no se puede aplicar el cupon de descuento.");
			  			}//trio_temp.equals("CCM")

			  		}else{

			  			trio_temp = sor_trio[0];
			  			
			  			if(sor_trio.length==1 && trio_temp.equals("G")){
			  				//bloque 2 switch	
			  				
			  				 switch(datos[0]){					  	 
					  	 	 case "1":	
					  	 		 
					  	 		Messagebox.show("Deseas confirmar el uso del cupon ?","Recuerda que al confirmar el cupon ya no podra ser utilizado", 
										Messagebox.YES | 
										Messagebox.NO, 
										Messagebox.QUESTION, new EventListener<Event>() {			
									@Override
									public void onEvent(Event e) throws Exception {				
											if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {												
												//$('#numero_cupon',window.parent.document).val(cupon);
												//$.cookie("cupon",cupon);
												sess.setAttribute("cupon",ventaPedidoForm.getNumero_cupon());
												//window.parent.postValida_cupon();
												ventaPedidoForm.setAccion("aplica_cupon");
												ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
											}
										}
								}); 
					  	 		
						  	 break;		
						  	 
					  	 	 case "2":	
					  	 		   Messagebox.show("El cupon ya no se encuentra vigente.");
					  	 		   sess.setAttribute("cupon","");						 					  	 	 												
						  	 break;	
						  	 
						  	 case "3":	
						  		  Messagebox.show("El cupon ya fue utilizado, no es posible volver a utilizarlo.");
						  		  sess.setAttribute("cupon","");					 					  	 	 												
						  	 break;	
						  	
						  	 case "4":	
						  		   Messagebox.show("El cupon no es valido.");
						  		   sess.setAttribute("cupon","");						 					  	 	 												
						  	 break;	
						  	 
						  	 case "5":	
						  		 
						  		Messagebox.show("Deseas confirmar el uso del cupon ? . El monto disponible para descuento es de :" + datos[1],
						  				"Recuerda que al confirmar el cupon ya no podra ser utilizado", 
										Messagebox.YES | 
										Messagebox.NO, 
										Messagebox.QUESTION, new EventListener<Event>() {			
									@Override
									public void onEvent(Event e) throws Exception {				
											if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {
												
												//$('#numero_cupon',window.parent.document).val(cupon);
												//$.cookie("cupon",cupon);
												sess.setAttribute("cupon",ventaPedidoForm.getNumero_cupon());
												//window.parent.postValida_cupon();
												ventaPedidoForm.setAccion("aplica_cupon");
												ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
											}
										}
								});						 					  	 	 												
						  	 break;	
						  	 
						  	 case "6":	
						  		   Messagebox.show("El beneficio optico ya fue utilizado, no es posible realizar el descuento.");
						  		   sess.setAttribute("cupon","");
						  	 break;
						  	 
						  	 case "7":	
						  		   Messagebox.show("No es posible usar el cupon de descuento \n,el usuario asociado al encargo no califica para Beneficio Optico GMO.");
						  		   sess.setAttribute("cupon","");	
						  	 break;
						  	 
						  	 case "8":	
						  		   Messagebox.show("No es posible usar el cupon de descuento \n,el usuario asociado al encargo no se encuentra vigente \n , No aplica para Beneficio Optico GMO.");
						  		   sess.setAttribute("cupon","");		
						  	 break;
						  	 
						  	 case "100":	
						  		   Messagebox.show("No es posible aplicar el cupon, el encargo ya posee un cupon asociado.");
						  		   sess.setAttribute("cupon","");		
						  	 break;
						  	 
						  	 default:
						  		   Messagebox.show("Error , favor contactarse con MDA");
						  		   sess.setAttribute("cupon","");
						  	 break;
			  				}
			  				
						}else{
							Messagebox.show("El cupon a utilizar esta asociado al Beneficio Optico GMO \n ,solo es aplicable a un trio optico o Gafa solar.(2)");
						}//sor_trio.length==1 && trio_temp.equals("G")
			  		}//sor_trio.length == 3
							
				}else{
					Messagebox.show("El cupon a utilizar esta asociado al Beneficio Optico GMO \n ,solo es aplicable a un trio optico o Gafa solar.(1)");
			  	}//sor_trio.length == 3 || sor_trio.length == 1 

			}else{
					
				//bloque 3 switch	
				switch(datos[0]){					  	 
		  	 	 case "1":	
		  	 		 
		  	 		Messagebox.show("Deseas confirmar el uso del cupon ?","Recuerda que al confirmar el cupon ya no podra ser utilizado", 
							Messagebox.YES | 
							Messagebox.NO, 
							Messagebox.QUESTION, new EventListener<Event>() {			
						@Override
						public void onEvent(Event e) throws Exception {				
								if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {									
									//$('#numero_cupon',window.parent.document).val(cupon);
									//$.cookie("cupon",cupon);
									sess.setAttribute("cupon",ventaPedidoForm.getNumero_cupon());
									//window.parent.postValida_cupon();
									ventaPedidoForm.setAccion("aplica_cupon");
									ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
								}
							}
					});
			  	 	
			  	 break;
			  	 
		  	 	 case "2":	
		  	 		   Messagebox.show("El cupon ya no se encuentra vigente.");
		  	 		   sess.setAttribute("cupon","");						 					  	 	 												
			  	 break;
			  	 
			  	 case "3":	
			  		  Messagebox.show("El cupon ya fue utilizado, no es posible volver a utilizarlo.");
			  		  sess.setAttribute("cupon","");					 					  	 	 												
			  	 break;	
			  	
			  	 case "4":	
			  		   Messagebox.show("El cupon no es valido.");
			  		   sess.setAttribute("cupon","");						 					  	 	 												
			  	 break;
			  	 
			     case "5":	
			    	 
			    	 Messagebox.show("Deseas confirmar el uso del cupon ? . El monto disponible para descuento es de :" + datos[1],
				  				"Recuerda que al confirmar el cupon ya no podra ser utilizado", 
								Messagebox.YES | 
								Messagebox.NO, 
								Messagebox.QUESTION, new EventListener<Event>() {			
							@Override
							public void onEvent(Event e) throws Exception {				
									if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {
										
										//$('#numero_cupon',window.parent.document).val(cupon);
										//$.cookie("cupon",cupon);
										sess.setAttribute("cupon",ventaPedidoForm.getNumero_cupon());
										//window.parent.postValida_cupon();
										ventaPedidoForm.setAccion("aplica_cupon");
										ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
									}
								}
						});				
		  	 	    
			  	 break;	
			  	 
			  	 case "6":	
			  		   Messagebox.show("El beneficio optico ya fue utilizado, no es posible realizar el descuento.");
			  		   sess.setAttribute("cupon","");	
			  	 break;
			  	 
			  	 case "7":	
			  		   Messagebox.show("No es posible usar el cupon de descuento \n,el usuario asociado al encargo no califica para Beneficio Optico GMO.");
			  		   sess.setAttribute("cupon","");
			  	 break;
			  	 
			  	 case "8":	
			  		   Messagebox.show("No es posible usar el cupon de descuento \n,el usuario asociado al encargo no se encuentra vigente \n , No aplica para Beneficio Optico GMO.");
			  		   sess.setAttribute("cupon","");		
			  	 break;
			  	 
			  	 case "100":	
			  		   Messagebox.show("No es posible aplicar el cupon de descuento, el encargo ya posee un cupon asociado.");
			  		   sess.setAttribute("cupon","");		
			  	 break;
			  	 
			  	 default:
			  		   Messagebox.show("Error , favor contactarse con MDA");
			  		   sess.setAttribute("cupon","");
			  	 break;
				}			
			}//datos[1] != "0")		
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		pop.close();
	}	
	
	
	
	
	//============== Convenios ================
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void buscarConvenioAjax() {		
		
		Window winSeleccionaConvenio=new Window();
		busquedaConveniosForm = new BusquedaConveniosForm();		
		busquedaConveniosDispatchActions = new BusquedaConveniosDispatchActions();	
		
		sess.setAttribute("convenio", ventaPedidoForm.getConvenio());
		
		if(ventaPedidoForm.getConvenio().equals("50464") && ventaPedidoForm.getCliente_dto().equals("")) {
			
			if(ventaPedidoForm.getCliente_dto().equals("")) {
				
				//showPopWin('<%=request.getContextPath()%>/SeleccionPago.do?method=exige_valida_dto&accion=valida', 230, 110,null,false);	
			}			
			
		}else {
			
			if (ventaPedidoForm.getEstado().equals("cerrado")) {				
				Messagebox.show("La venta esta cerrada, no es posible modificar convenios");
				return;
			}
			
			if(ventaPedidoForm.getFlujo().equals("formulario")) {				
				Messagebox.show("No hay ventas en curso, no es posible ingresar convenios");
				return;
			}
			
			if (ventaPedidoForm.getConvenio().equals("")) {
				Messagebox.show("debe ingresar un código de convenio");
				return;
			}			
			
			if(ventaPedidoForm.getFlujo().equals("modificar")) {
				
				if (ventaPedidoForm.getBloquea().equals("bloquea")) {					
					Messagebox.show("Encargo bloqueado, no es posible modificar convenios");
					return;
				}								
				
				BeanGlobal bg = busquedaConveniosDispatchActions.buscarConvenioAjax(busquedaConveniosForm, sess);
				//param1 : descripcion
				//param2 : cdg
				//param3 : isapre				
				ventaPedidoForm.setConvenio_det((String)bg.getObj_1());	
				ventaPedidoForm.setIsapre((String)bg.getObj_3());
				
				if(ventaPedidoForm.getConvenio_det().equals(""))
				{
					ventaPedidoForm.setConvenio_det("");
					ventaPedidoForm.setConvenio("");
					ventaPedidoForm.setIsapre("N");
				}	
				
				busquedaConveniosDispatchActions.selecciona_convenio_cdg(busquedaConveniosForm, sess);	
				
				busquedaConveniosForm.setSel_convenio((String)bg.getObj_2());
				busquedaConveniosForm.setSel_convenio_det((String)bg.getObj_1());
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","encargo");
				objetos.put("origen","encargo");
				objetos.put("win",winSeleccionaConvenio);		
				
				//se llama ventana convenio
				winSeleccionaConvenio = (Window)Executions.createComponents(
		                "/zul/presupuestos/SeleccionaConvenio.zul", null, objetos);	
					
				winSeleccionaConvenio.doModal();			
				
				
				
			}else {		
				
				if(ventaPedidoForm.getCliente().equals("") || ventaPedidoForm.getCliente().equals("0")) {					
					Messagebox.show("Debe seleccionar un cliente, para agregar convenios");
					return;
				}
				
				
				BeanGlobal bg = busquedaConveniosDispatchActions.buscarConvenioAjax(busquedaConveniosForm, sess);
				//param1 : descripcion
				//param2 : cdg
				//param3 : isapre				
				ventaPedidoForm.setConvenio_det((String)bg.getObj_1());
				ventaPedidoForm.setIsapre((String)bg.getObj_3());
				
				if(ventaPedidoForm.getConvenio_det().equals(""))
				{
					ventaPedidoForm.setConvenio_det("");
					ventaPedidoForm.setConvenio("");
					ventaPedidoForm.setIsapre("N");
				}				
				
				busquedaConveniosDispatchActions.selecciona_convenio_cdg(busquedaConveniosForm, sess);	
				
				busquedaConveniosForm.setSel_convenio((String)bg.getObj_2());
				busquedaConveniosForm.setSel_convenio_det((String)bg.getObj_1());
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","encargo");
				objetos.put("origen","encargo");
				objetos.put("win",winSeleccionaConvenio);
				
				//se llama ventana convenio
				winSeleccionaConvenio = (Window)Executions.createComponents(
		                "/zul/presupuestos/SeleccionaConvenio.zul", null, objetos);
				
				winSeleccionaConvenio.doModal();				
				
			} //flujo modificar
			
		}		
	}	
	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void busqueda_convenio_avanzada() {
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("La venta esta cerrada, no es posible modificar convenios");
			return;
		}
		
		if (ventaPedidoForm.getFecha().equals("formulario")) {
			Messagebox.show("No hay ventas en curso, no es posible ingresar convenios");
			return;			
		}
		
		if (ventaPedidoForm.getFlujo().equals("modificar")) {
			
			if (!ventaPedidoForm.getBloquea().equals("bloquea")) {
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","encargo");
				objetos.put("origen","busqueda");				
				
				//ventana convenio
				Window winBusquedaConvenio = (Window)Executions.createComponents(
		                "/zul/presupuestos/BusquedaConvenio.zul", null, objetos);		
				winBusquedaConvenio.doModal();
				
			}else {
				
				Messagebox.show("Encargo bloqueado, no es posible modificar convenios");
				return;
			}
			
			
		}else {
			
			if (!ventaPedidoForm.getCliente().equals("") && !ventaPedidoForm.getCliente().equals("0")) {
				
				objetos = new HashMap<String,Object>();		
				objetos.put("busquedaConvenios",busquedaConveniosForm);
				objetos.put("ventana","encargo");
				objetos.put("origen","busqueda");
				
				//ventana convenio
				Window winBusquedaConvenio = (Window)Executions.createComponents(
		                "/zul/presupuestos/BusquedaConvenio.zul", null, objetos);		
				winBusquedaConvenio.doModal();
				
			}else {				
				Messagebox.show("Debe seleccionar un cliente, para agregar convenios");
				return;
			}			
			
		}		
		
	}	
	
	//==============Busqueda Cliente desde Venta =====================
	//================================================================
	@Command
	public void busquedaCliente() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteEncargo");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	
	
	@NotifyChange({"ventaPedidoForm","beanControlBotones"})
	@GlobalCommand
	public void buscarClienteEncargo(@BindingParam("cliente")ClienteBean cliente) {
		
		ventaPedidoForm.setNif(cliente.getNif());
		this.buscarCliente();
	}
	
	
	
	
	//=========== Mantengo la persistencia de lista de splementos=======
	//==================================================================
	@NotifyChange({"ventaPedidoForm"})
	@GlobalCommand
	public void actulizaListaSuplementos(@BindingParam("suplementos")ArrayList<SuplementopedidoBean> suplementos,
										 @BindingParam("producto")ProductosBean producto,
										 @BindingParam("index")int index) {	
		
		/*
		try {
			ventaPedidoForm.setAccion(Constantes.STRING_AGREGAR_SUPLEMENTOS);
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
		
		ventaPedidoForm.getListaProductos().get(index).setListaSuplementos(suplementos);		
	} 	
	
	
	//=========Mantengo la persistencia de lista de Multiofertas =======
	//==================================================================
	@NotifyChange({"ventaPedidoForm"})
	@GlobalCommand
	public void actualizaListaProductosMulti(@BindingParam("productosMulti")ArrayList<ProductosBean> productosMulti,
			 								 @BindingParam("producto")ProductosBean producto,
			 								 @BindingParam("index")int index) {
		
		ventaPedidoForm.getListaProductos().get(index).setListaProductosMultiofertas(productosMulti);
		
		System.out.println("stop");
	}	
	
	//====================  Validaciones varias ========================
	//==================================================================
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actualiza_descuento_total_monto() {
		
		// descuento original
		// dto_total_monto 
		long campo = 0;
		long descuento_max = 0;
		long total = 0;
		long dto = 0;
		String tipo="";		
		objetos = new HashMap<String,Object>();
		objetos.put("retorno","devuelveDescuento_totalMonto_Encargo");
		
		campo = ventaPedidoForm.getDescuento();
		total = ventaPedidoForm.getSubTotal();
		dto   = (campo * 100) / total;	
		
		Optional<String> tp = Optional.ofNullable(tipoPedidoBean.getCodigo());
		
		if (!tp.isPresent() || tp.get().equals(""))		
			tipo = "0";
		else
			tipo = tp.get();
		
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("La venta esta cerrada, no es posible modificar");
			return;
		}	
		
		if (ventaPedidoForm.getBloquea().equals("bloquea")) {
			//Messagebox.show("Valor no puede ser mayor al monto total");			
			//ventaPedidoForm.setDescuento(dto_total_monto);
			
			Messagebox.show("La venta esta bloqueda, no es posible modificar");
			ventaPedidoForm.setDescuento(0);
			return;
		}		
		
		
		if (dto_total_monto > 0) {
			
			if(campo <= ventaPedidoForm.getTotal()) {
				descuento_max = ventaPedidoForm.getPorcentaje_descuento_max();
				
				if (dto <= descuento_max) {
		        	try {
		        		ventaPedidoForm.setAccion("descuento_total_monto");
			        	ventaPedidoForm.setCantidad_linea(Integer.valueOf(String.valueOf(campo)));		        		
						ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
					} catch (Exception e) {						
						e.printStackTrace();
					}
					
				}else {					
					
					sess.setAttribute("tipo", tipo);
					
					Window winAutoriza = (Window)Executions.createComponents(
			                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
					winAutoriza.doModal();					
				}				
				
			}else {
				Messagebox.show("Valor no puede ser mayor al monto total");
				//ventaPedidoForm.setDescuento(dto_total_monto);
				ventaPedidoForm.setDescuento(0);
				return;				
			}	
			
		}else {
			
			if (campo > 0) {

				if (campo <= ventaPedidoForm.getTotal()) {
					
					descuento_max = ventaPedidoForm.getPorcentaje_descuento_max();
					
					if(dto <= descuento_max) {								        		
						try {
							ventaPedidoForm.setAccion("descuento_total_monto");
				        	ventaPedidoForm.setCantidad_linea(Integer.valueOf(String.valueOf(campo)));
							ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);							
						} catch (Exception e) {							
							e.printStackTrace();
						}						
					}else {
						
						sess.setAttribute("tipo", tipo);
						
						Window winAutoriza = (Window)Executions.createComponents(
				                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
						winAutoriza.doModal();
					}
					
					
				}else {					
					Messagebox.show("Valor no puede ser mayor al monto total");
					//ventaPedidoForm.setDescuento(dto_total_monto);
					ventaPedidoForm.setDescuento(0);
					return;
				}			
				
			}
		}		
	}	
	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actualiza_descuento_total() {
		
		//variable original
		//dto_total
		long campo = 0;
		long descuento_max = 0;
		String tipo="";
		
		objetos = new HashMap<String,Object>();
		objetos.put("retorno","devuelveDescuento_totalDescuento_Encargo");
		
		campo = ventaPedidoForm.getDtcoPorcentaje();
		
		Optional<String> tp = Optional.ofNullable(tipoPedidoBean.getCodigo());
		
		if (!tp.isPresent() || tp.get().equals(""))		
			tipo = "0";
		else
			tipo = tp.get();
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("La venta esta cerrada, no es posible modificar");
			return;
		}	
		
		if (ventaPedidoForm.getBloquea().equals("bloquea")) {
			Messagebox.show("Valor no puede ser mayor al monto total");			
			ventaPedidoForm.setDescuento(dto_total_monto);
			return;
		}
		
		if ((campo < 0) || (campo > 100)) {
			Messagebox.show("Valor debe estar entre 0 y 100");
			ventaPedidoForm.setDtcoPorcentaje(Integer.valueOf(String.valueOf(dto_total)));
			return;		
		}
		
		
		if (dto_total > 0) {		
				
			descuento_max = ventaPedidoForm.getPorcentaje_descuento_max();
			
			if(campo <= descuento_max) {					
	        	//document.getElementById('cantidad_descuento').value = campo.replace(',','.'); 	        	
	        	try {
	        		ventaPedidoForm.setCantidad_descuento(campo);
	        		ventaPedidoForm.setAccion("descuento_total");
					ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
					
					ventaPedidoForm.setDtcoPorcentaje(0);
				} catch (Exception e) {						
					e.printStackTrace();
				}		        	
				
			}else {
				//autorizador				
				
				if (!tipo.equals("0")) {
					
					sess.setAttribute("tipo", tipo);			
											
					Window winAutoriza = (Window)Executions.createComponents(
			                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
					winAutoriza.doModal();								
					
				}else {
					Messagebox.show("Debes seleccionar un tipo de Encargo");
					return;
				}								
			}			
			
			
		}else {		
			
			if(campo > 0) {
				
				descuento_max = ventaPedidoForm.getPorcentaje_descuento_max();
				
				if(campo <= descuento_max) {					
		        	//document.getElementById('cantidad_descuento').value = campo.replace(',','.'); 	        	
		        	try {
		        		ventaPedidoForm.setCantidad_descuento(campo);
		        		ventaPedidoForm.setAccion("descuento_total");
						ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
						
						ventaPedidoForm.setDtcoPorcentaje(0);
					} catch (Exception e) {						
						e.printStackTrace();
					}		        	
					
				}else {
					//autorizador
					
					if (tipoPedidoBean != null) {
						
						sess.setAttribute("tipo", tipo);
						
						Window winAutoriza = (Window)Executions.createComponents(
				                "/zul/presupuestos/AutorizadorDescuento.zul", null, objetos);		
						winAutoriza.doModal();	
						
					}else {
						Messagebox.show("Debes seleccionar un tipo de Encargo");
						return;
					}						
				}				
				
			}			
		}		
	}	
	
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actualiza_descuento(@BindingParam("index")int index, @BindingParam("dcto")int dcto) {
		
		long campo = 0;
		long descuento_max = 0;
		String tipo="";
		objetos = new HashMap<String,Object>();
		
		campo = dcto;		
		objetos.put("retorno","devuelveDescuento_lineaMonto_Encargo");
		
		Optional<String> tp = Optional.ofNullable(tipoPedidoBean.getCodigo());
		
		if (!tp.isPresent() || tp.get().equals(""))		
			tipo = "0";	
		else
			tipo = tp.get();
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("La venta esta cerrada, no es posible modificar");
			return;
		}	
		
		if (ventaPedidoForm.getBloquea().equals("bloquea")) {
			Messagebox.show("Valor no puede ser mayor al monto total");			
			//ventaPedidoForm.setDescuento(dto_total_monto);
			ventaPedidoForm.getListaProductos().get(index).setDescuento(0);
			return;
		}
		
		if ((campo < 0) || (campo > 100)) {
			Messagebox.show("Valor debe estar entre 0 y 100");
			//ventaPedidoForm.setDtcoPorcentaje(Integer.valueOf(String.valueOf(dto_total)));
			ventaPedidoForm.getListaProductos().get(index).setDescuento(0);
			return;		
		}
		
		
		descuento_max = ventaPedidoForm.getPorcentaje_descuento_max();
		
		if (campo <= descuento_max) {       	
        	try {
        		//document.getElementById('cantidad_descuento').value = campo.replace(',','.');	
        		ventaPedidoForm.setCantidad_descuento(campo);
            	ventaPedidoForm.setAccion("descuento_linea");
            	ventaPedidoForm.setAddProducto(String.valueOf(index));
				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
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
	
	//===================== Retorno del autorizador =====================
	//===================================================================	
	@NotifyChange({"ventaPedidoForm"})
	@GlobalCommand
	public void devuelveDescuento_totalMonto_Encargo(@BindingParam("valores")BeanGlobal valores) {
		
		String acceso="";
		BigDecimal descuento_autorizado= BigDecimal.ZERO;
		String usuario="";
		long dto = 0;
		BigDecimal bgdto = BigDecimal.ZERO;		
		
		dto = (ventaPedidoForm.getDescuento() * 100) / ventaPedidoForm.getSubTotal();		
		bgdto = BigDecimal.valueOf(dto);	
		
		acceso = (String)valores.getObj_1();
		descuento_autorizado = (BigDecimal)valores.getObj_2();
		usuario   = (String)valores.getObj_3();		
		
		if(acceso.equals("true")) {
			
			if(bgdto.compareTo(descuento_autorizado)==1) {				
				
				Messagebox.show("El descuento mximo autorizado es de " + descuento_autorizado);
				ventaPedidoForm.setDescuento(0);				
				return;
			}else {						
				
				try {					
					ventaPedidoForm.setCantidad_linea(Integer.valueOf(String.valueOf(ventaPedidoForm.getDescuento())));
					ventaPedidoForm.setAccion("descuento_total_monto");
					ventaPedidoForm.setDescuento_autoriza(usuario);				
					ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
				} catch (Exception e) {					
					e.printStackTrace();
				}				
			}
			
			
		}else {			
			Messagebox.show("Usted no esta autorizado, para realizar este tipo de descuento");
			ventaPedidoForm.setDescuento(0);			
			return;
		}	
		
	}
	
	@NotifyChange({"ventaPedidoForm"})
	@GlobalCommand
	public void devuelveDescuento_totalDescuento_Encargo(@BindingParam("valores")BeanGlobal valores) {
		
		String acceso="";
		BigDecimal descuento_autorizado= BigDecimal.ZERO;
		String usuario="";
		BigDecimal descuento= BigDecimal.ZERO;
		
		acceso = (String)valores.getObj_1();
		descuento_autorizado = (BigDecimal)valores.getObj_2();
		usuario   = (String)valores.getObj_3();			
		
    	if (acceso.equals("true")) {	
    		
    		descuento = BigDecimal.valueOf(ventaPedidoForm.getDtcoPorcentaje());		
			
			if (descuento.compareTo(descuento_autorizado)==1) {
				Messagebox.show("El descuento máximo autorizado es de " + descuento_autorizado);
				ventaPedidoForm.setDtcoPorcentaje(0);
				return;
			}
			else
			{							
				try {
					ventaPedidoForm.setCantidad_descuento(Double.parseDouble(String.valueOf(descuento)));
					ventaPedidoForm.setAccion("descuento_total");
					ventaPedidoForm.setDescuento_autoriza(usuario);			
					
					ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
				} catch (Exception e) {					
					e.printStackTrace();
				}			
			}
		}
		else
		{
			Messagebox.show("Usted no esta autorizado, para realizar este tipo de descuento");
			ventaPedidoForm.setDtcoPorcentaje(0);
		}
		
		
	}
	
	
	@NotifyChange({"ventaPedidoForm"})
	@GlobalCommand
	public void devuelveDescuento_lineaMonto_Encargo(@BindingParam("valores")BeanGlobal valores) {
				
		String acceso="";
		BigDecimal descuento_autorizado= BigDecimal.ZERO;
		BigDecimal descuento_ingresado= BigDecimal.ZERO;
		String usuario="";
		int index = 0;
		int comparacion=0;
		
		acceso 					= (String)valores.getObj_1();
		descuento_autorizado 	= (BigDecimal)valores.getObj_2();
		usuario   				= (String)valores.getObj_3();
		index 					= (int)sess.getAttribute("_IndexDescuento");
 		
    	if (acceso.equals("true")) {
    		
			//var descuento_ingresado = document.getElementById("cantidad").value;
			descuento_ingresado = BigDecimal.valueOf(Double.parseDouble(String.valueOf(ventaPedidoForm.getListaProductos().get(index).getDescuento())));
			
			comparacion = descuento_ingresado.compareTo(descuento_autorizado);
			
			if (comparacion==1) {
				Messagebox.show("El descuento máximo autorizado es de " + descuento_autorizado);				
				ventaPedidoForm.getListaProductos().get(index).setDescuento(0);
				return;
			}
			else
			{				
				try {
					ventaPedidoForm.setAddProducto(String.valueOf(index));
					ventaPedidoForm.setCantidad_descuento(ventaPedidoForm.getListaProductos().get(index).getDescuento());
					ventaPedidoForm.setAccion("descuento_linea");
					ventaPedidoForm.setDescuento_autoriza(usuario);
					
					ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
				} catch (Exception e) {
					
					e.printStackTrace();
				}				
			}
		}
		else
		{
			Messagebox.show("Usted no esta autorizado, para realizar este tipo de descuento");
			ventaPedidoForm.getListaProductos().get(index).setDescuento(0);
			return;
		}
		
	}
	//===================== Fin Retorno Autorizador =====================
	
	
	
	//===================== Valida grupo ================================
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void actualiza_grupo(@BindingParam("index")int index) {
		
		if (ventaPedidoForm.getEstado().equals("cerrado")) {			
			Messagebox.show("La venta esta cerrada, no es posible modificar productos");
			return;
		}
		
		
		if (ventaPedidoForm.getFlujo().equals("modificar")) {			
			if(!ventaPedidoForm.getBloquea().equals("bloquea")) {	        	
	        	
	        	try {
	        		ventaPedidoForm.setAccion("grupo");
		        	ventaPedidoForm.setAddProducto(String.valueOf(index));
					ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
				} catch (Exception e) {					
					e.printStackTrace();
				}			
				
			}else {				
				Messagebox.show("Encargo bloqueado, no es posible modificar productos");
				return;
			}			
			
		}else {			
        	
			try {
				ventaPedidoForm.setAccion("grupo");
	        	ventaPedidoForm.setAddProducto(String.valueOf(index));
				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			} catch (Exception e) {				
				e.printStackTrace();
			}			
		}		
	}	
	
	
	//=================== Seleccion Tratamientos ============================
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void seleccionTratamientos(@BindingParam("index")int index,
									  @BindingParam("producto")ProductosBean producto) {
		
		Random rand = new Random();
		
    	try {
    		ventaPedidoForm.setAccion(Constantes.STRING_VER_SUPLEMENTOS);
        	ventaPedidoForm.setAddProducto(String.valueOf(index));
			ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			
			objetos = new HashMap<String,Object>();		
			objetos.put("producto",producto);
			objetos.put("index",index);
			objetos.put("origen","PEDIDO");
			objetos.put("name","win"+String.valueOf(rand.nextInt(1000)));
			//objetos.put("busquedaProductos",busquedaProductosForm);
			
			Window windowAgregaSuplementoEnc = (Window)Executions.createComponents(
	                "/zul/encargos/AgregaSuplemento.zul", null, objetos);
			
			windowAgregaSuplementoEnc.doModal();			
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	//=====================Valida Descripcion ===========================
	//===================================================================
	@NotifyChange({"ventaPedidoForm","descripcionFocus"})
	@Command
	public void actualiza_descripcion(@BindingParam("index")int index, @BindingParam("producto")ProductosBean producto ) {
		
		if(producto.getDescripcion().equals("")) {			
			Messagebox.show("Debe ingresar una descripcion del producto para continuar");
			descripcionFocus=true;
			ventaPedidoForm.getListaProductos().get(index).setDescripcion("Agregar Descripcion");
			return;
		}else {			
			
			try {
				ventaPedidoForm.setAccion("agrega_descripcion");
				ventaPedidoForm.setAddProducto(String.valueOf(index));
				ventaPedidoForm.setDescripcion(producto.getDescripcion());
				
				ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
			} catch (Exception e) {				
				e.printStackTrace();
			}
			
		}	
		
	}	
	
	
	//=====================Manejo de ComboBox ===========================
	//===================================================================
	public void creaItemSelecciona() {
		
		String descripcion = "Seleccione";		
		
		//Agente
		ArrayList<AgenteBean> aAgenteBean = new ArrayList<AgenteBean>();
		ArrayList<AgenteBean> newList1;
		
		agenteBean = new AgenteBean(); 
		agenteBean.setUsuario(descripcion);
		agenteBean.setNombre_completo(descripcion);
		aAgenteBean.add(agenteBean);
		newList1 = (ArrayList<AgenteBean>) Stream.concat(aAgenteBean.stream(), ventaPedidoForm.getListaAgentes().stream()).collect(Collectors.toList());
		ventaPedidoForm.setListaAgentes(newList1);
		
		//Forma de pago
		ArrayList<FormaPagoBean> aFormaPagoBean = new ArrayList<FormaPagoBean>();
		ArrayList<FormaPagoBean> newList2;
		
		formaPagoBean = new FormaPagoBean();
		formaPagoBean.setId("0");
		formaPagoBean.setDescripcion(descripcion);
		aFormaPagoBean.add(formaPagoBean);
		newList2 = (ArrayList<FormaPagoBean>) Stream.concat(aFormaPagoBean.stream(), ventaPedidoForm.getListaFormaPago().stream()).collect(Collectors.toList());
		ventaPedidoForm.setListaFormaPago(newList2);
		
		//Tipo Encargo
		ArrayList<TipoPedidoBean> aTipoPedidoBean = new ArrayList<TipoPedidoBean>();
		ArrayList<TipoPedidoBean> newList3;
		
		tipoPedidoBean = new TipoPedidoBean();
		tipoPedidoBean.setCodigo("0");
		tipoPedidoBean.setDescripcion(descripcion);
		aTipoPedidoBean.add(tipoPedidoBean);
		newList3 = (ArrayList<TipoPedidoBean>) Stream.concat(aTipoPedidoBean.stream(), ventaPedidoForm.getListaTiposPedidos().stream()).collect(Collectors.toList());
		ventaPedidoForm.setListaTiposPedidos(newList3);
		
		
		//Promocion
		ArrayList<PromocionBean> aPromocionBean = new ArrayList<PromocionBean>();
		ArrayList<PromocionBean> newList4;
		
		promocionBean = new PromocionBean();		
		promocionBean.setId("0");
		promocionBean.setDescripcion(descripcion);		
		aPromocionBean.add(promocionBean);
		newList4 = (ArrayList<PromocionBean>) Stream.concat(aPromocionBean.stream(), ventaPedidoForm.getListaPromociones().stream()).collect(Collectors.toList());
		ventaPedidoForm.setListaPromociones(newList4);	
	}	
	
	
	public boolean evaluaEntrega() {
		
		boolean entregado=false;		
		
		if (ventaPedidoForm.getFlujo().equals("modificar")) {
			if (ventaPedidoForm.getPagadoTotal().equals("true")) {
				if (ventaPedidoForm.getCerrado().equals("S"))
						entregado=true;				
				
				if (!ventaPedidoForm.getCerrado().equals('S')) {
					 if (ventaPedidoForm.getEntregado().equals("false"))
						 entregado=false;
					
					 if (!ventaPedidoForm.getEntregado().equals("false"))
						 entregado=true;					
				}
			}
			
			if (!ventaPedidoForm.getPagadoTotal().equals("true")) {
				if (ventaPedidoForm.getPedido_costo_cero().equals("true")) {
					if (ventaPedidoForm.getCerrado().equals("S"))
						entregado=true;
					
					if (!ventaPedidoForm.getCerrado().equals("S")) {
						if (ventaPedidoForm.getEntregado().equals("false"))
							entregado=false;
						
						if (!ventaPedidoForm.getEntregado().equals("false"))
							entregado=true;
																	
					}												
				}
				
				if (!ventaPedidoForm.getPedido_costo_cero().equals("true"))
					entregado=true;												
			}
		}								
	
		if (!ventaPedidoForm.getFlujo().equals("modificar")) {
			if (ventaPedidoForm.getPagadoTotal().equals("true")) {
				if (ventaPedidoForm.getCerrado().equals("S"))
						entregado=true;				
				
				if (!ventaPedidoForm.getCerrado().equals('S')) {
					 if (ventaPedidoForm.getEntregado().equals("false"))
						 entregado=false;
					
					 if (!ventaPedidoForm.getEntregado().equals("false"))
						 entregado=true;					
				}
			}
			
			if (!ventaPedidoForm.getPagadoTotal().equals("true")) {
				if (ventaPedidoForm.getPedido_costo_cero().equals("true")) {
					if (ventaPedidoForm.getCerrado().equals("S"))
						entregado=true;
					
					if (!ventaPedidoForm.getCerrado().equals("S")) {
						if (ventaPedidoForm.getEntregado().equals("false"))
							entregado=false;
						
						if (!ventaPedidoForm.getEntregado().equals("false"))
							entregado=true;
																	
					}												
				}
				
				if (!ventaPedidoForm.getPedido_costo_cero().equals("true"))
					entregado=true;												
			}
		}	
		
		return entregado;
	}
	
	
	
	@GlobalCommand
	public void notificacionAlbaram() {		
		BindUtils.postNotifyChange(null, null, ControllerEncargos.this, "ventaPedidoForm");
	}
	
	
	@GlobalCommand
	public void notificacionRespuestaEncargo() {		
		BindUtils.postNotifyChange(null, null, ControllerEncargos.this, "ventaPedidoForm");
	}
	
	
	//-----Evalua Promociones ------
	
	@NotifyChange({"ventaPedidoForm"})
	@Command
	public void seleccionaPromocion() throws Exception {
		
		String promocion=null;	
		
		String familia = "";
		String grupo = "";
		String grupfam = "";
		String cod = "";
		int indice = 0;
		int pos=0;		
		List<String> trio= new ArrayList<String>();		
		int dto = 0;
		int data= 0;
		
		promocion = promocionBean.getId();		
		ventaPedidoForm.setPromocion(promocion);
		
		switch (promocion) {
			case "PPTG":
				
				for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {
					
					familia = pb.getTipoFamilia();
					grupo	= pb.getGrupo();
					grupfam	= pb.getGrupoFamilia();
					
					if((familia.equals("M") ||  familia.equals("C") || familia.equals("G") || grupfam.equals("MUL"))){						
						trio.add(familia);	 			    	
	 			    }    

	 			    if(familia.equals("G")){
	 			    	pos = indice;
	 			    }					
					
					indice++;					
				}		
				
				//var sor_trio = trio.sort();
		 		//var dto = $j("#promocion").html().replace(/[^0-9]/g, '');
				String dtoAux = ventaPedidoForm.getPromocion().replace("/[^0-9]/g", "");
				
				try {
					dto = Integer.parseInt(dtoAux);
				}catch(Exception e) {
					dto=0;
				}
				
		 		
		 		   
	 		   if(trio.size() == 4 || trio.size() == 2){
	 			   
	 			  ventaPedidoForm.setAccion("descuento_linea");
	 			  ventaPedidoForm.setAddProducto(String.valueOf(pos));
	 			  ventaPedidoForm.setCantidad_descuento(dto);	
	 			  
	 			 ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);		 		   		
	 			   
	 		   }else{
	 			   Messagebox.show("La promoción sólo es aplicable al encargo  compuesto por un tíro y una gafa.");
	 		   }				
				
				break;
			//plan vuelta a clases	
			case "PCOM":
				
				for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {
				
					familia = pb.getTipoFamilia();
					cod = pb.getCod_barra();
					
					if(familia.equals("M")) {
						
						ventaPedidoForm.setNumero_cupon(cod);
						data = ventaPedidoDispatchActions.valida_promocombo(ventaPedidoForm, sess);
						
						if(data==1) {
							
							ventaPedidoForm.setAccion("aplica_dtocombo");
							ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
							
						}else if (data==2) {
							Messagebox.show("El armazon no esta en la promocion, no se puede aplicar el descuento.");
						}else {
							Messagebox.show("No se puede aplicar la promocion.(G)");
						}					
						
					}
					
				}
				
				break;	
				
			case "PPAR":
				
				int sumgrupo1=0;
				int sumgrupo2=0;
				int sumgrupo3=0;
				int sumgrupo4=0;
				int sumgrupo5=0;
				int[] sumarr = new int[5];
				String[] arm_arr = null;
				String[] posicion = null;
				String[] arm_arrAux = new String[100];
				String[] posicionAux = new String[100];
				int c=0,a=0;
				int precio=0;
				String result="";
				
				for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {					
					familia = pb.getTipoFamilia();
					grupo = pb.getGrupo();
					//grupfam = pb.getGrupoFamilia();
					grupfam = pb.getFamilia();
					
					if(familia.equals("G") || familia.equals("M") || grupfam.equals("MUL")) {
						//arm_arr.add(pb.getCod_barra()+","+grupo);
						arm_arrAux[a] = pb.getCod_barra()+","+grupo;
						a++;
					}
				}
				
				arm_arr = new String[a];
				String cadena="";
				
				for (int i=0 ; i < a; i++ ) {
					arm_arr[i] = arm_arrAux[i];					
				}		
				
				cadena = Arrays.deepToString(arm_arr).replaceAll("[\\[\\]]", "");				
				cadena = cadena.replace(" ", "");
				
				ventaPedidoForm.setValor_comodin(cadena);
				
				try {
					result = ventaPedidoDispatchActions.valida_promo_pares(ventaPedidoForm, sess);
				}catch(Exception e) {
					System.out.println(e.getMessage());
					return;
				}
				
				String[] temp = result.split("_");
				
				if(Integer.parseInt(temp[1]) >= 2 ){
					for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {
						
						grupo = pb.getGrupo();
						
		 	    	    if(grupo.equals("1")){
		 	    			sumgrupo1 +=  pb.getPrecio();
		 	    			sumarr[1] = sumgrupo1;
		 	    			
		 	    		}else if(grupo.equals("2")){
		 	    			sumgrupo2 +=  pb.getPrecio();
		 	    			sumarr[2] = sumgrupo2;
		 	    
		 	    		}else if(grupo.equals("3")){
		 	    			sumgrupo3 +=  pb.getPrecio();
		 	    			sumarr[3] = sumgrupo3;
		 	    		
		 	    		}else if(grupo.equals("4")){
		 	    			sumgrupo4 +=  pb.getPrecio();
		 	    			sumarr[4] = sumgrupo4;
		 	    			
		 	    		}else if(grupo.equals("5")){
		 	    			sumgrupo5 +=  pb.getPrecio();
		 	    			sumarr[5] = sumgrupo5;
		 	    	
		 	    		}
				
					}
					
					for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {
						
						//familia = pb.getFamilia();
						familia = pb.getTipoFamilia();
						grupo = pb.getGrupo();
						//grupfam = pb.getGrupoFamilia();
						grupfam = pb.getFamilia();
						precio = pb.getPrecio();
						
						if((familia.equals("G") && grupo.equals("0")) || grupfam.equals("MUL")){
							
							//posicion.add(precio+"."+grupo+indice);
							posicionAux[c]=precio+"."+grupo+indice;
	 		    			c++;
	 		         	}
	 		         	
	 		         	if(familia.equals("G") && !grupo.equals("0")){
	 		         		
	 		         		posicionAux[c]=sumarr[Integer.parseInt(grupo)]+"."+grupo+indice;
	 		         		//posicion.add(sumarr[Integer.parseInt(grupo)]+"."+grupo+indice);	 		         		   		    			
	 		    			c++;
	 		         	}						 		         							 		         							 		         							 		    
	 		       		
	 		         	if(familia.equals("M")){
	 		         		
	 		         		posicionAux[c]=sumarr[Integer.parseInt(grupo)]+"."+grupo+indice;
	 		         		//posicion.add(sumarr[Integer.parseInt(grupo)]+"."+grupo+indice);	 		         		    		    			
	 		    			c++;
	 		         	}
					}
					
					
					posicion = new String[c];
					
					
					for(int i=0;i<c;i++) {
						posicion[i] = posicionAux[i];
					}
					
					Arrays.sort(posicion);
					
					/*pos.sort(function(a,b){return b-a});*/
					String p = posicion[posicion.length -1];
					String[] posdesc = p.split("\\.");
					
	 			 	//String[] posdesc = posicion[posicion.length -1].split(".");
	 			 	
	 			 	ventaPedidoForm.setNumero_cupon(temp[0]+"_"+posdesc[1]);
	 			 	ventaPedidoForm.setAccion("aplica_dtopromopar");
	 			 	ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);   	
		            
		            
		            //$j.cookie("des_seg_armazon","777");
				}else {
					
					Messagebox.show("Promoción no aplicable a los productos seleccionados.");
					
				}				
				
				break;	
				
			case "BFDY":	
				
				String combo="";
				indice = 0;
				
				for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {				
					familia = pb.getTipoFamilia();
					combo += familia.trim();
				}
				
				for(ProductosBean pb : ventaPedidoForm.getListaProductos()) {	
					
					familia = pb.getTipoFamilia();
					grupfam = pb.getGrupoFamilia();
					
					if (sucursal.substring(0,1).equals("R")) {
						
						if(combo.equals("MCC") || combo.equals("CCM") || combo.equals("GCC") || combo.equals("CCG")){
							
							ventaPedidoForm.setAccion("descuento_total");
							ventaPedidoForm.setCantidad_descuento(20);
							ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
				        	
		      		   }else{
			      		 	if(!pb.getSubFamilia().equals("TEC")){
					      	   if(!pb.getPromopar().equals("0")){
						           if(familia.equals("M") ||  familia.equals("G")  || grupfam.equals("MUL")){
						        	   
						        	   ventaPedidoForm.setAccion("descuento_linea");
						        	   ventaPedidoForm.setAddProducto(String.valueOf(indice));
						        	   ventaPedidoForm.setCantidad_descuento(Double.parseDouble(pb.getPromopar()));	
						        	   ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
						        			
						       	   }
					      	   	}
						  	 }
		      		   }
					}
					
					if( sucursal.substring(0,1).equals("T") || sucursal.substring(0,1).equals("V") ){
				      	 if(!pb.getSubFamilia().equals("TEC")){
					      	   if(!pb.getPromopar().equals("0")){
					      		 if(familia.equals("M") ||  familia.equals("G")  || grupfam.equals("MUL")){
		
					      			ventaPedidoForm.setAccion("descuento_linea");
					      			ventaPedidoForm.setAddProducto(String.valueOf(indice));
					      			ventaPedidoForm.setCantidad_descuento(Double.parseDouble(pb.getPromopar()));	
						        	ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
						        			
						       	   }
					      	   }
						 }
		      	    }		
					
					
					indice++;					
				}
				
				
				break;	
			
			case "SVAN":				
				
	            ventaPedidoForm.setAccion("san_valentin");	
	        	ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
	        	
	        	break;
				
			default:
				
				ventaPedidoForm.setAccion("aplica_descuento_promocion");	
	        	ventaPedidoDispatchActions.IngresaVentaPedido(ventaPedidoForm, sess);
	        	
		}
		
		
		
		
		
	}	
	
	
	//======================Getter and Setter============================
	//===================================================================
	public VentaPedidoForm getVentaPedidoForm() {
		return ventaPedidoForm;
	}

	public void setVentaPedidoForm(VentaPedidoForm ventaPedidoForm) {
		this.ventaPedidoForm = ventaPedidoForm;
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

	public ProductosBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductosBean productoBean) {
		this.productoBean = productoBean;
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

	public BeanControlBotones getBeanControlBotones() {
		return beanControlBotones;
	}

	public void setBeanControlBotones(BeanControlBotones beanControlBotones) {
		this.beanControlBotones = beanControlBotones;
	}

	public TipoPedidoBean getTipoPedidoBean() {
		return tipoPedidoBean;
	}

	public void setTipoPedidoBean(TipoPedidoBean tipoPedidoBean) {
		this.tipoPedidoBean = tipoPedidoBean;
	}

	public BeanControlCombos getBeanControlCombos() {
		return beanControlCombos;
	}


	public void setBeanControlCombos(BeanControlCombos beanControlCombos) {
		this.beanControlCombos = beanControlCombos;
	}

	public PromocionBean getPromocionBean() {
		return promocionBean;
	}

	public void setPromocionBean(PromocionBean promocionBean) {
		this.promocionBean = promocionBean;
	}

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

	public String getSucursalDes() {
		return sucursalDes;
	}

	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}

	public boolean iswArcliVisible() {
		return wArcliVisible;
	}
	
	public void setwArcliVisible(boolean wArcliVisible) {
		this.wArcliVisible = wArcliVisible;
	}

	public boolean isDescripcionFocus() {
		return descripcionFocus;
	}

	public void setDescripcionFocus(boolean descripcionFocus) {
		this.descripcionFocus = descripcionFocus;
	}

	public boolean isEncargoEntregado() {
		return encargoEntregado;
	}

	public void setEncargoEntregado(boolean encargoEntregado) {
		this.encargoEntregado = encargoEntregado;
	}

	
}
