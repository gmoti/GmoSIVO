package cl.gmo.pos.venta.controlador;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.DevolucionDispatchActions;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;

import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.TipoMotivoDevolucionBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

public class ControllerAlbaran implements Serializable{
	
	
	private static final long serialVersionUID = 3102390269320411220L;
	Session sess = Sessions.getCurrent();
	HashMap<String,Object> objetos;
	
	private DevolucionForm devolucionForm;
	private DevolucionBean devolucionBean ;
	private SeleccionPagoForm seleccionPagoForm;
	private VentaPedidoForm ventaPedidoForm;
	private DevolucionDispatchActions devolucionDispatch;
	private SeleccionPagoDispatchActions seleccionPagoDispatch;
	private ClienteBean cliente;
	
	private String usuario;	
	private String sucursalDes;	
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");		
	
	private Date fechaActual;
	//private Time horaActual;
	private Date fechaGarantia;
	private boolean disabledCampo=false;
	
	private TipoAlbaranBean tipoAlbaranBean;
	private IdiomaBean idiomaBean;
	private AgenteBean agenteBean;
	private DivisaBean divisaBean;
	private TipoMotivoDevolucionBean tipoMotivoDevolucionBean;
	private FormaPagoBean formaPagoBean;
	private ConvenioBean convenioBean;
	private ProvinciaBean provinciaBean;
	
	
	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		sess.setAttribute("_Convenio","0");	
		
		devolucionForm = new DevolucionForm();
		devolucionBean = new DevolucionBean();
		seleccionPagoForm = new SeleccionPagoForm();
		ventaPedidoForm = new VentaPedidoForm();
		devolucionDispatch = new DevolucionDispatchActions();
		seleccionPagoDispatch = new SeleccionPagoDispatchActions();
		
		tipoAlbaranBean = new TipoAlbaranBean();
		idiomaBean = new IdiomaBean();
		agenteBean = new AgenteBean();
		divisaBean = new DivisaBean();
		tipoMotivoDevolucionBean = new TipoMotivoDevolucionBean();
		formaPagoBean = new FormaPagoBean();
		convenioBean = new ConvenioBean();
		provinciaBean = new ProvinciaBean();
		cliente = new ClienteBean();
		
		fechaActual = new Date(System.currentTimeMillis());
		//horaActual  = new Date(System.currentTimeMillis());
		fechaGarantia= new Date(System.currentTimeMillis());
		disabledCampo=true;
		
		devolucionDispatch.cargaFormulario(devolucionForm, sess);		
	}
	
	
	//==================================================================
	//=============Procesos principales de la toolbar ==================
	//==================================================================	
	
	@Command
	@NotifyChange({"*"})
	public void nuevoAlbaran(){
		
		devolucionForm.setAccion("nuevo");
		devolucionDispatch.cargaFormulario(devolucionForm, sess);
		
		fechaActual = new Date(System.currentTimeMillis());
		//horaActual  = new Date(System.currentTimeMillis());
		fechaGarantia= new Date(System.currentTimeMillis());
		
		devolucionForm.setFecha(dt.format(fechaActual));
		devolucionForm.setHora(tt.format(fechaActual));
		
		tipoAlbaranBean=null;
		idiomaBean=null;
		agenteBean=null;
		divisaBean=null;
		tipoMotivoDevolucionBean=null;
		formaPagoBean=null;
		convenioBean=null;
		provinciaBean=null;
		
		disabledCampo=false;
		cliente = new ClienteBean();
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void pagarAlbaran(){
		
		Optional<Date> fg = Optional.ofNullable(fechaGarantia);
		
		Optional<TipoAlbaranBean> tab = Optional.ofNullable(tipoAlbaranBean);
		Optional<IdiomaBean> ib = Optional.ofNullable(idiomaBean);
		Optional<AgenteBean> ab = Optional.ofNullable(agenteBean);
		Optional<DivisaBean> db = Optional.ofNullable(divisaBean);
		Optional<TipoMotivoDevolucionBean> tmd = Optional.ofNullable(tipoMotivoDevolucionBean);
		Optional<FormaPagoBean> fpb = Optional.ofNullable(formaPagoBean);
		Optional<ConvenioBean> cb = Optional.ofNullable(convenioBean);
		Optional<ProvinciaBean> pb = Optional.ofNullable(provinciaBean);
		
		if (tab.isPresent()) devolucionForm.setTipo_albaran(String.valueOf(tab.get().getDescripcion()));
		else devolucionForm.setTipo_albaran("");
		
		if(ib.isPresent()) devolucionForm.setIdioma(ib.get().getId());
		else devolucionForm.setIdioma("0");
		
		if(ab.isPresent()) devolucionForm.setAgente(ab.get().getUsuario());
		else devolucionForm.setAgente("0");
		
		if(db.isPresent()) devolucionForm.setDivisa(db.get().getId());
		else devolucionForm.setDivisa("0");
		
		if(tmd.isPresent()) devolucionForm.setMotivo(tmd.get().getCodigo());
		else devolucionForm.setMotivo("0");
		
		if(fpb.isPresent()) devolucionForm.setFormaPago(fpb.get().getId());
		else devolucionForm.setFormaPago("0");
		
		if(cb.isPresent()) devolucionForm.setConvenio(cb.get().getId());
		else devolucionForm.setConvenio("0");
		
		if(pb.isPresent()) devolucionForm.setProvincia(pb.get().getCodigo());
		else devolucionForm.setProvincia("0");
		
		
		
		if (fg.isPresent())		
			devolucionForm.setFecha_garantia(dt.format(fechaGarantia));
		else
			devolucionForm.setFecha_garantia("");
		
		if(devolucionForm.getTipo_albaran().equals("DIRECTA")) {			
			devolucionForm.setAccion("traeAlbaranBuscado2");
			devolucionDispatch.cargaFormulario(devolucionForm, sess);			
		}		
		
		String val_letras = "/^[A-Z a-zÑñ. ]{3,50}$/";	
		/*
		if(!Pattern.matches(val_letras, devolucionForm.getDireccion())) {
			Messagebox.show("Debe Ingresar una dirección de cliente válida.");
			return;
		}
		
		if(!Pattern.matches(val_letras, devolucionForm.getNdireccion_cli())) {
			Messagebox.show("Debes ingresar el N° de Dirección del cliente.");
			return;
		}
		
		if(!Pattern.matches(val_letras, devolucionForm.getProvincia())) {
			Messagebox.show("Debes ingresar la provincia del cliente.");
			return;
		}
		*/
		cobrar_albaran_validaCaja();
		
	}
	
	private void cobrar_albaran_validaCaja(){
		
		String fecha = devolucionForm.getFecha();
		String codigo_cliente = devolucionForm.getCodigo_cliente();
		boolean resp=false;
		
		if(codigo_cliente.equals("")) {
			Messagebox.show("Debe ingresar un cliente.");
			return;
		}
		
		resp = devolucionDispatch.validaCajaAjax(devolucionForm, sess);
			
		if (resp) {
			cobrar_albaran();
		}else {
			Messagebox.show("La caja esta cerrada para la fecha seleccionada.");
			return;
		}
	
	}
	
	private void cobrar_albaran(){
		
		String tipo_albaran = devolucionForm.getTipo_albaran();   
		String albaranDevolcionPago = devolucionForm.getAlbaranDevolcionPago();
		String fecha = devolucionForm.getFecha();
		
		if(tipo_albaran.equals("DIRECTA")){
			//document.getElementById("fecha").disabled=false;
			fecha = devolucionForm.getFecha();
			//document.getElementById("fecha").disabled=true;
			//showPopWin("<%=request.getContextPath()%>/SeleccionPago.do?method=cargaFormularioAlbaranDirecta&fecha="+fecha+"", 710, 285, vuelve_Pago_albaran, false);
			try {
				seleccionPagoForm = new SeleccionPagoForm();
				seleccionPagoForm.setEstado("");
				seleccionPagoDispatch.cargaFormularioAlbaranDirecta(seleccionPagoForm, sess);
				
				objetos = new HashMap<String,Object>();
				//objetos.put("cliente",cliente);
				objetos.put("pagoForm",seleccionPagoForm);
				objetos.put("ventaOrigenForm",devolucionForm);
				objetos.put("origen","PEDIDO");
				
				Window windowPagoVentaDirecta = (Window)Executions.createComponents(
		                "/zul/venta_directa/SeleccionPago.zul", null, objetos);
				
				windowPagoVentaDirecta.doModal();			
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
			
			
		}else if(tipo_albaran.equals("DEVOLUCION") && albaranDevolcionPago.equals("OK")){
			//showPopWin("<%=request.getContextPath()%>/SeleccionPago.do?method=cargaFormulario", 710, 285, vuelve_Pago_albaran_devolucion, false);
			
			//seleccionPagoDispatch.carga_formulario(seleccionPagoForm, sess, fecha_formulario);
			seleccionPagoForm = new SeleccionPagoForm();
			
			
		}else if(tipo_albaran.equals("DEVOLUCION")){		
			String agente = devolucionForm.getAgente();
			String motivo = devolucionForm.getMotivo();
			String tieneArmCrisContacto = devolucionForm.getTieneArmCrisContacto();
			String isController = devolucionForm.getIsController();
			
			if(agente.equals("0")) {
				Messagebox.show("Debe seleccionar agente");
				return;
			}
			
			if(motivo.equals("0")) {
				Messagebox.show("Debe seleccionar un motivo de devolución");
				return;
			}
			
			
			if(tieneArmCrisContacto.equals("true")){
				
				if(isController.equals("true") ){
					
					//showPopWin("<%=request.getContextPath()%>/SeleccionPago.do?method=cargaFormularioCobroAlbaran&fecha="+fecha+"", 710, 285, vuelve_Pago_albaran_devolucion, false);
					try {
						
						seleccionPagoForm = new SeleccionPagoForm();	
						seleccionPagoForm.setEstado("");
						sess.setAttribute(Constantes.STRING_ORIGEN, "ALBARAN_DEVOLUCION");
						
						seleccionPagoForm.setFech_pago(devolucionForm.getFecha());
						seleccionPagoForm.setFecha(devolucionForm.getFecha());
						//seleccionPagoForm.setTipo_doc('B');
						seleccionPagoForm.setOrigen("ALBARAN_DEVOLUCION");						
						
						seleccionPagoDispatch.cargaFormularioCobroAlbaran(seleccionPagoForm, sess);
						
						objetos = new HashMap<String,Object>();
						objetos.put("cliente",cliente);
						objetos.put("pagoForm",seleccionPagoForm);
						objetos.put("ventaOrigenForm",devolucionForm);
						objetos.put("origen","ALBARAN_DEVOLUCION");
						
						Window windowPagoVentaDirecta = (Window)Executions.createComponents(
				                "/zul/venta_directa/SeleccionPago.zul", null, objetos);
						
						windowPagoVentaDirecta.doModal();					
						
					} catch (Exception e) {						
						e.printStackTrace();
					}	
					
					
				}else{
					Messagebox.show("Los productos son ópticos, solo un controller puede hacer la devolución");
					return;
				}
			}else{
				//showPopWin("<%=request.getContextPath()%>/SeleccionPago.do?method=cargaFormularioCobroAlbaran&fecha="+fecha+"", 710, 285, vuelve_Pago_albaran_devolucion, false);
				
				try {
					
					seleccionPagoForm = new SeleccionPagoForm();
					seleccionPagoForm.setEstado("");
					sess.setAttribute(Constantes.STRING_ORIGEN, "ALBARAN_DEVOLUCION");
					
					seleccionPagoForm.setFech_pago(devolucionForm.getFecha());
					seleccionPagoForm.setFecha(devolucionForm.getFecha());
					//seleccionPagoForm.setTipo_doc('B');
					seleccionPagoForm.setOrigen("ALBARAN_DEVOLUCION");
					
					seleccionPagoDispatch.cargaFormularioCobroAlbaran(seleccionPagoForm, sess);
					
					objetos = new HashMap<String,Object>();
					objetos.put("cliente",cliente);
					objetos.put("pagoForm",seleccionPagoForm);
					objetos.put("ventaOrigenForm",devolucionForm);
					objetos.put("origen","ALBARAN_DEVOLUCION");
					
					Window windowPagoVentaDirecta = (Window)Executions.createComponents(
			                "/zul/venta_directa/SeleccionPago.zul", null, objetos);
					
					windowPagoVentaDirecta.doModal();	
					
				} catch (Exception e) {				
					e.printStackTrace();
				}
				
				
			}
					
		}						
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void eliminarAlbaran(){
		
		String tipo_albaran = devolucionForm.getTipo_albaran();
		String albaranDevolcionPago = devolucionForm.getAlbaranDevolcionPago();	
		       	
		 if(tipo_albaran.equals("DIRECTA")){
		 	//document.getElementById("fecha").disabled=false;			
			devolucionForm.setAccion("eliminaAlbaran");
			devolucionDispatch.cargaAlbaran(devolucionForm, sess);
			
		}else if(tipo_albaran.equals("ENTREGA")){
			//document.getElementById("fecha").disabled=false;
			devolucionForm.setAccion("eliminaAlbaran");
			devolucionDispatch.cargaAlbaran(devolucionForm, sess);
			
		}else if(tipo_albaran.equals("DEVOLUCION") && albaranDevolcionPago.equals("OK")){
			//document.getElementById("fecha").disabled=false;
			devolucionForm.setAccion("eliminaAlbaran");
			devolucionDispatch.cargaAlbaran(devolucionForm, sess);
		}
		 
		 
		if (devolucionForm.getRespuestaEliminaAlbaran().equals("true"))
			Messagebox.show("Albaran eliminado de forma satisfactoria");
		else
			Messagebox.show("Albaran no puede ser eliminado");
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void mostrarPagosBoletas(){
		
		seleccionPagoForm = new SeleccionPagoForm();		
		seleccionPagoForm.setSerie(devolucionForm.getCodigo1()+"/"+devolucionForm.getCodigo2());
		
		objetos = new HashMap<String,Object>();		
		objetos.put("seleccionPago",seleccionPagoForm);
		objetos.put("ventanaOrigen","albaran");
		
		Window windowMostrarPagosBoleta = (Window)Executions.createComponents(
                "/zul/encargos/MostrarPagosBoleta.zul", null, objetos);
		
		windowMostrarPagosBoleta.doModal();
		
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void mostrarListaAlbaranes(){	
		
		devolucionDispatch.mostrarListaAlbaranes(devolucionForm, sess);
		
		objetos = new HashMap<String,Object>();
		objetos.put("devolucion",devolucionForm);
		
		Window winListaAlbaran = (Window)Executions.createComponents(
                "/zul/mantenedores/ListaAlbaranes.zul", null, objetos);
		
		winListaAlbaran.doModal();
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void abrirBuscarAlbaranes(){
		
		Window winBuscarAlbaran = (Window)Executions.createComponents(
                "/zul/mantenedores/BusquedaAlbaran.zul", null, null);
		
		winBuscarAlbaran.doModal();	
	}
	
	@Command
	public void cerrarVentanas(@BindingParam("win")  Window win) {
	    win.detach();
	}	
	
	
	//===============================================================
	//========= Prcesos adicionales de la clase =====================
	//===============================================================	
		
	@NotifyChange({"devolucionForm"})
	@GlobalCommand	
    public void creaPagoExitosoDevolucion(@BindingParam("seleccionPago")SeleccionPagoForm seleccionPago) {	
		
		String[] tmp;
		String rut;
		String nota;
		String urlbol;
		
		devolucionForm.setAccion(Constantes.FORWARD_DEVOLUCION);
		devolucionDispatch.cargaAlbaran(devolucionForm, sess);		
		
		tmp = devolucionForm.getEstado_boleta().split("_");
		
		rut  = tmp[3];
		nota = tmp[1]+".pdf";
		urlbol = "http://10.216.4.16/NC/61 "+rut+" "+nota;
		
		if(tmp[0].equals("0") || tmp[2].equals("true")){
			Messagebox.show("Error: No se pudo generar la boleta");	
			return;
			
		}else if(tmp[0].equals("1") && tmp[2].equals("false")){			
			
			Messagebox.show("Generando Nota de Cr\u00e9dito, espere un momento por favor....");
			
			Messagebox.show("Albaran", "Generando Nota de Cr\\u00e9dito, espere un momento por favor...",
					Messagebox.OK,	Messagebox.INFORMATION, new EventListener<Event>() {			
				@Override
				public void onEvent(Event e) throws Exception {	
					
						if( ((Integer) e.getData()).intValue() == Messagebox.OK ) {
							
							objetos = new HashMap<String,Object>();
							objetos.put("documento",urlbol);
							objetos.put("titulo","Albaran");
							
							Window window = (Window)Executions.createComponents(
					                "/zul/reportes/VisorDocumento.zul", null, objetos);
							
					        window.doModal();
						}						
					}
			});	
			
			
		}else if(tmp[0].equals("2") && tmp[2].equals("false")){
			Messagebox.show("!ATENCI\u00d3N¡ AGREGAR MAS FOLIOS, SE ESTAN AGOTANDO");
			return;
		}
		
		
		//var tipoimpresion = $j("#tipoimp").val() == "1" ? "Carta": "Termica";
		/*
		var rut = tmp[3];
		
		var nota = tmp[1]+".pdf"; 
						
		var urlbol = "http://10.216.4.16/NC/61 "+rut+" "+nota;				
		
		if(tmp[0] == "0" || tmp[2] =="true"){
			alert("Error: No se pudo generar la boleta");
			
		}else if(tmp[0] == "1" && tmp[2] =="false"){
			
			$j(".pantalla2,#load_gif").css("display","block");
			alert("Generando Nota de Cr\u00e9dito, espere un momento por favor....");
			setTimeout(function(){  
				window.open(urlbol); $j(".pantalla2,#load_gif").css("display","NONE");
			}, 4000);						
			
		}else if(tmp[0] == "2" && tmp[2] =="false"){
			alert("!ATENCI\u00d3N¡ AGREGAR MAS FOLIOS, SE ESTAN AGOTANDO");
		}	
		*/
		
			
	}
	
	@NotifyChange({"devolucionForm"})
	public void postCobro() {	
		Messagebox.show("DevoluciÃ³n realizada con exito");
	}	
	
	@NotifyChange({"devolucionForm"})
	@Command
	public void buscarAlbaranesDevo(){
		
		boolean respuesta = false;
		String codigo1="";
		String codigo2="";
		String cdg="";
		
		codigo1 = devolucionForm.getCodigo1();
		codigo2 = devolucionForm.getCodigo2();		
		
		if(!codigo1.equals("") && !codigo2.equals("")){
			cdg = codigo1 +"/"+codigo2;
		}
		
		if(cdg.equals("")){
			Messagebox.show("Debe ingresar un código de albaran válido");
			respuesta = true;
		}else{
			devolucionForm.setCdg_venta(cdg);						
			respuesta = false;
		}
		
		if(!respuesta){			
			devolucionForm.setAccion("traeAlbaranBuscado");
			devolucionForm.setInicio_pagina("busqueda_rapida");
			
			devolucionDispatch.cargaAlbaran(devolucionForm, sess);			
		}
	}
	
	@NotifyChange({"*"})
	@GlobalCommand
	public void albaranSeleccionado(@BindingParam("albaran")AlbaranBean albaran) {
		
		devolucionForm.setAccion("traeAlbaranBuscado");
		devolucionForm.setCdg_venta(albaran.getCodigo_albaran());		
		devolucionDispatch.cargaAlbaran(devolucionForm, sess);
		
		cliente.setNif(devolucionForm.getNif());
		cliente.setDvnif(devolucionForm.getDvnif());
		
		try {
			fechaActual = dt.parse(devolucionForm.getFecha());
			
			Optional<String> d = Optional.ofNullable(devolucionForm.getFecha_garantia());
			if(d.isPresent() || d.get().equals(""))
				fechaGarantia = dt.parse(devolucionForm.getFecha_garantia());
			else
				fechaGarantia = null;
			
			//horaActual = tt.parse(devolucionForm.getFecha());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		
		Optional<String> c = Optional.ofNullable(devolucionForm.getConvenio());
		if (!c.isPresent()) devolucionForm.setConvenio("0");
		
		Optional<String> p = Optional.ofNullable(devolucionForm.getProvincia());
		if (!p.isPresent()) devolucionForm.setProvincia("0");
			
		
		posicionaCombos();
	}
	
	
	//======= Metodos secundarios ====================
	//================================================
	
	@NotifyChange({"*"})
	@Command
	public void cargarDatos(){
		
		boolean enviar=true;
		String tipoAlabaran = devolucionForm.getTipoAlbaran();
		String numero_boleta_guia = devolucionForm.getNumero_boleta_guia();
		String boleta_guia = devolucionForm.getBoleta_guia();
		//var resp = getRadioButtonSelected(boleta_guia);
		int data;	
		cliente = new ClienteBean();
		
		
		if(numero_boleta_guia.equals("")){
			Messagebox.show("Debe Ingresar un N\u00FAmero de Boleta");
			return;
		}	
		
		try {
			
			data = devolucionDispatch.validaFechaNC(devolucionForm, sess);
			
			switch(data){
			case 1:
				/*$j(".pantalla2,#load_gif").css("display","block");
				alert("Cargando datos ,favor  espere un momento por favor....");
				setTimeout(function(){  
				 $j(".pantalla2,#load_gif").css("display","NONE");
				}, 4000);		*/	
				
				//document.getElementById("tipoAlbaran").value="D";
				//document.getElementById('accion').value = 'cargarDatos';
				//document.getElementById("tipoAlbaran").disabled=false;
				//document.forms[0].submit();		
				devolucionForm.setTipoAlbaran("D");
				devolucionForm.setAccion("cargarDatos");
				devolucionDispatch.cargaAlbaran(devolucionForm, sess);
				
				cliente.setNif(devolucionForm.getNif());
				cliente.setDvnif(devolucionForm.getDvnif());
				
				Optional<String> d = Optional.ofNullable(devolucionForm.getFecha_garantia());
				
				try {
					fechaActual = dt.parse(devolucionForm.getFecha());
					//horaActual = dt.parse(devolucionForm.getFecha());
					
					if(!d.isPresent() || d.get().equals(""))
						fechaGarantia = null;
					else	
						fechaGarantia = dt.parse(devolucionForm.getFecha_garantia());
					
											
					
				} catch (ParseException e) {			
					e.printStackTrace();
				}
				
				
				Optional<String> c = Optional.ofNullable(devolucionForm.getConvenio());
				if (!c.isPresent()) devolucionForm.setConvenio("0");
				
				Optional<String> p = Optional.ofNullable(devolucionForm.getProvincia());
				if (!p.isPresent()) devolucionForm.setProvincia("0");				
				
				posicionaCombos();
				disabledCampo=false;				
				break;				  			
			case 2:
					
				Messagebox.show("No se pueden anular boletas con uno o m\u00E1s a\u00F1os de antiguedad.");
				break;
			default:
					
				Messagebox.show("No se puede realizar la devoluci\u00F3n, favor comunicarse con mesa de ayuda.");
			break;				  			
			}
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
		
		return;	
	}
	
	
	
	@NotifyChange({"*"})
	@Command
	public void posicionaCombos() {		
		
		Optional<DivisaBean> b = devolucionForm.getListaDivisas().stream().filter(s -> devolucionForm.getDivisa().equals(s.getId())).findFirst();
		if (b.isPresent())
			divisaBean = b.get();
		else
			divisaBean = null;
		
		Optional<IdiomaBean> d = devolucionForm.getListaIdiomas().stream().filter(s -> devolucionForm.getIdioma().equals(s.getId())).findFirst();
		if (d.isPresent())
			idiomaBean = d.get();
		else
			idiomaBean = null;
		
		Optional<FormaPagoBean> e = devolucionForm.getListaFormasPago().stream().filter(s -> devolucionForm.getFormaPago().equals(s.getId())).findFirst();
		if (e.isPresent())
			formaPagoBean = e.get();
		else
			formaPagoBean = null;
		
		Optional<AgenteBean> a = devolucionForm.getListaAgentes().stream().filter(s -> devolucionForm.getAgente().equals(s.getUsuario())).findFirst();		
		if (a.isPresent())
			agenteBean = a.get();
		else
			agenteBean = null;
		
		Optional<TipoAlbaranBean> f = devolucionForm.getListaTipoAlbaranes().stream().filter(s -> devolucionForm.getTipoAlbaran().equals(String.valueOf(s.getCodigo()))).findFirst();		
		if (f.isPresent())
			tipoAlbaranBean = f.get();
		else
			tipoAlbaranBean = null;
		
		Optional<TipoMotivoDevolucionBean> g = devolucionForm.getLista_mot_devo().stream().filter(s -> devolucionForm.getMotivo().equals(s.getCodigo())).findFirst();		
		if (g.isPresent())
			tipoMotivoDevolucionBean = g.get();
		else
			tipoMotivoDevolucionBean = null;
		
		Optional<ConvenioBean> h = devolucionForm.getListaConvenios().stream().filter(s -> devolucionForm.getConvenio().equals(s.getTipo())).findFirst();		
		if (h.isPresent())
			convenioBean = h.get();
		else
			convenioBean = null;
		
		Optional<ProvinciaBean> i = devolucionForm.getListaProvincia().stream().filter(s -> devolucionForm.getProvincia().equals(s.getCodigo())).findFirst();		
		if (i.isPresent())
			provinciaBean = i.get();
		else
			provinciaBean = null;
		
	}
	
	
	// Getter and Setter =============================
	//================================================

	public DevolucionForm getDevolucionForm() {
		return devolucionForm;
	}

	public void setDevolucionForm(DevolucionForm devolucionForm) {
		this.devolucionForm = devolucionForm;
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

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	/*public Date getHoraActual() {
		return horaActual;
	}

	public void setHoraActual(Date horaActual) {
		this.horaActual = horaActual;
	}*/

	public boolean isDisabledCampo() {
		return disabledCampo;
	}

	public void setDisabledCampo(boolean disabledCampo) {
		this.disabledCampo = disabledCampo;
	}

	public Date getFechaGarantia() {
		return fechaGarantia;
	}

	public void setFechaGarantia(Date fechaGarantia) {
		this.fechaGarantia = fechaGarantia;
	}


	public DevolucionBean getDevolucionBean() {
		return devolucionBean;
	}


	public void setDevolucionBean(DevolucionBean devolucionBean) {
		this.devolucionBean = devolucionBean;
	}


	public TipoAlbaranBean getTipoAlbaranBean() {
		return tipoAlbaranBean;
	}


	public void setTipoAlbaranBean(TipoAlbaranBean tipoAlbaranBean) {
		this.tipoAlbaranBean = tipoAlbaranBean;
	}


	public IdiomaBean getIdiomaBean() {
		return idiomaBean;
	}


	public void setIdiomaBean(IdiomaBean idiomaBean) {
		this.idiomaBean = idiomaBean;
	}


	public AgenteBean getAgenteBean() {
		return agenteBean;
	}


	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}


	public DivisaBean getDivisaBean() {
		return divisaBean;
	}


	public void setDivisaBean(DivisaBean divisaBean) {
		this.divisaBean = divisaBean;
	}


	public FormaPagoBean getFormaPagoBean() {
		return formaPagoBean;
	}


	public void setFormaPagoBean(FormaPagoBean formaPagoBean) {
		this.formaPagoBean = formaPagoBean;
	}


	public ConvenioBean getConvenioBean() {
		return convenioBean;
	}


	public void setConvenioBean(ConvenioBean convenioBean) {
		this.convenioBean = convenioBean;
	}


	public ProvinciaBean getProvinciaBean() {
		return provinciaBean;
	}


	public void setProvinciaBean(ProvinciaBean provinciaBean) {
		this.provinciaBean = provinciaBean;
	}


	public TipoMotivoDevolucionBean getTipoMotivoDevolucionBean() {
		return tipoMotivoDevolucionBean;
	}


	public void setTipoMotivoDevolucionBean(TipoMotivoDevolucionBean tipoMotivoDevolucionBean) {
		this.tipoMotivoDevolucionBean = tipoMotivoDevolucionBean;
	}
	
	
	
	
	
}
