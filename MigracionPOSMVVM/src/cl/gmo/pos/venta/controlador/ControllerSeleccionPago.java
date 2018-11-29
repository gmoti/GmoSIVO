package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;



public class ControllerSeleccionPago implements Serializable{
	
	
	private static final long serialVersionUID = -3283893161345500968L;

	Session sess = Sessions.getCurrent();	
	HashMap<String,Object> objetos;
	
	private SeleccionPagoForm seleccionPagoForm;
	private VentaPedidoForm  ventaPedidoForm;
	private VentaDirectaForm ventaDirectaForm;
	private DevolucionForm devolucionForm;
	
	private ClienteBean cliente;	
	private SeleccionPagoDispatchActions seleccionPagoDispatchActions;
	private Integer diferencia_total=0;
	private Double dto = 0.0;
	private Integer sumaTotal;
	private Integer descuentoMaximo=0;
	
	private FormaPagoBean formaPagoBean;
	private String disableDescuento;
	private PagoBean pagoBeanAux;
	private String origen;
	private String fecha=null;
	private BeanControlBotones controlBotones;
	
	private Window ventanaActual= new Window();
	private boolean isapreVisible=false;
	private boolean pagarReadOnly=false;
	
	
	@Init
	public void inicio(@ExecutionArgParam("cliente")ClienteBean arg,
					   @ExecutionArgParam("pagoForm")SeleccionPagoForm arg2,
					   @ExecutionArgParam("ventaOrigenForm")Object arg3,
					   @ExecutionArgParam("origen")String arg4) {			
		
		String convenio="";
		String isapre="";		
		ArrayList<ProductosBean> listaProductos= new ArrayList<ProductosBean>();
		
		cliente             = null;		
		seleccionPagoForm 	= null;
		ventaPedidoForm		= null;
		ventaDirectaForm	= null;
		devolucionForm      = null;			
		
		controlBotones 	= new BeanControlBotones();
		formaPagoBean   = new FormaPagoBean();
		pagoBeanAux     = new PagoBean();	
		seleccionPagoDispatchActions = new SeleccionPagoDispatchActions();		
		
		cliente           = (ClienteBean)arg;		
		seleccionPagoForm = (SeleccionPagoForm)arg2;
		origen            = (String)arg4;
		
		controlBotones.setEnableGenerico1("false");		
		
		
		if (arg3 instanceof VentaPedidoForm) { 
			ventaPedidoForm = (VentaPedidoForm)arg3;
			fecha = ventaPedidoForm.getFecha();
			controlBotones.setEnableGenerico1("true");
			controlBotones.setEnableGenerico2("false");
			disableDescuento="false";
			
			this.dto = 0.0;
			sumaTotal = seleccionPagoForm.getSuma_total_albaranes();
			descuentoMaximo = ventaPedidoForm.getPorcentaje_descuento_max();
			sess.setAttribute(Constantes.STRING_AGENTE, ventaPedidoForm.getAgente());
			
			/* Acciones posteriores a la carga */
			/* =============================== */
			convenio = ventaPedidoForm.getConvenio();
			isapre   = ventaPedidoForm.getIsapre();
			listaProductos = ventaPedidoForm.getListaProductos();
			
			//postCarga(convenio, isapre, seleccionPagoForm.getDiferencia(), listaProductos);
		}
		
		if (arg3 instanceof VentaDirectaForm) { 
			ventaDirectaForm= (VentaDirectaForm)arg3;
			fecha = ventaDirectaForm.getFecha();
			controlBotones.setEnableGenerico1("false");
			controlBotones.setEnableGenerico2("true");
			disableDescuento="true";
			
			this.dto = ventaDirectaForm.getDescuentoTotal();
			sumaTotal = ventaDirectaForm.getSumaTotal();
			descuentoMaximo = ventaDirectaForm.getPorcentaje_descuento_max();
			sess.setAttribute(Constantes.STRING_AGENTE, ventaDirectaForm.getAgente());
		}
		
		if (arg3 instanceof DevolucionForm) { 
			devolucionForm= (DevolucionForm)arg3; 
			fecha = devolucionForm.getFecha();
			controlBotones.setEnableGenerico1("true");
			controlBotones.setEnableGenerico2("true");
			disableDescuento="false";
			
			this.dto = 0.0;
			sumaTotal = seleccionPagoForm.getSuma_total_albaranes();
			descuentoMaximo = 0;
			sess.setAttribute(Constantes.STRING_AGENTE, devolucionForm.getAgente());
		}		
		
		seleccionPagoDispatchActions.carga_formulario(seleccionPagoForm, sess, fecha);
		
		this.setDiferencia_total(seleccionPagoForm.getDiferencia());		
		seleccionPagoForm.setV_a_pagar(0);	
		
		if (arg3 instanceof VentaPedidoForm) {		
			
			postCarga(convenio, isapre, seleccionPagoForm.getDiferencia(), listaProductos);
	
		}
	}
	
	@NotifyChange({"seleccionPagoForm"})
	public void postCarga(String convenio, String isapre, Integer diferencia, ArrayList<ProductosBean> listaProductos) {		
		
		int paso_grp = 0;
	 	int cont = 0;
	 	int dent = 0;
	 	String tmp;
	 	int i=0;
	 	
	 	Optional<String> con = Optional.ofNullable(convenio);
	 	Optional<String> isa = Optional.ofNullable(isapre);
	 	
	 	convenio = con.orElse("");
	 	isapre = isa.orElse("");
	 	
	 	if (seleccionPagoForm.getListaPagos() == null)
	 		seleccionPagoForm.setListaPagos(new ArrayList<PagoBean>());
	 	
	 	for(PagoBean pb : seleccionPagoForm.getListaPagos()) {	 		
	 		if (pb.getForma_pago().equals("GRPON") || pb.getForma_pago().equals("ISAPR")) 
	 			paso_grp = 1; 		
	 	}
	 	
	 	for(ProductosBean pb : listaProductos) {	 		
	 		if (pb.getFamilia().equals("DES")){	 			
	 			
	 			i=0;
	 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {	 			
	 				if(fpb.getId().equals("GAR") || fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED") || fpb.getId().equals("CRB")) {
	 					fpb.setActivo(true);
	 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
	 				}
	 				i++;
	 			} 			
	 		}	 		
	 	}
	 	
	 	
	 	if (paso_grp != 1) {
	 		
	 	    if(!sess.getAttribute("convenio").equals("sdg")){
	 		
		 		if(convenio.equals("50368") || 
		 				convenio.equals("50369") || 
		 				convenio.equals("50472") || 
		 				convenio.equals("50473") || 
		 				convenio.equals("50474") ){
		 			i=0;
		 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
		 				if(!fpb.getId().equals("GRPON") && !fpb.getId().equals("0")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 				}	
		 				i++;
		 			} 		 			
		 			
		 			sess.setAttribute("convenio", "sdg");			  
					  
				 }else{
				 	borra_grpn();
				 }
	 		
		 		if(convenio.equals("51001") || convenio.equals("51002")){
		 			i=0;
		 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
		 				if(!fpb.getId().equals("OA") && !fpb.getId().equals("0")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 				}	
		 				i++;
		 			} 	 			
		 			
					sess.setAttribute("convenio", "sdg");	
				 }	
	 		
		 		if(convenio.equals("50472")){
					//$j("#sumaPagar").val("80000").attr("readonly",true);
					seleccionPagoForm.setV_a_pagar(80000);
					pagarReadOnly=true;
				 }
		 		
				 if(convenio.equals("50473")){
					//$j("#sumaPagar").val("120000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(120000);
					 pagarReadOnly=true;
				 }
				 if(convenio.equals("50474")){
					//$j("#sumaPagar").val("160000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(160000);
					 pagarReadOnly=true;
				 }
				 if(convenio.equals("50368")){
					//$j("#sumaPagar").val("30000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(30000);
					 pagarReadOnly=true;
				 }
				 if(convenio.equals("50369")){
					//$j("#sumaPagar").val("15000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(15000);
					 pagarReadOnly=true;
				 }	
	 			
				 if(convenio.equals("51001")){
					 
					 seleccionPagoForm.setV_total_parcial(41650); 
					 seleccionPagoForm.setV_a_pagar(41650);
					 seleccionPagoForm.setDiferencia(41650);
					 seleccionPagoForm.setV_total(41650);
					 seleccionPagoForm.setDiferencia(41650);
					 pagarReadOnly=true;
					 
					/*$j("#sumaParcial").val("41650").attr("readonly",true);
					$j("#sumaPagar").val("41650").attr("readonly",true);
					$j("#diferencia").val("41650").attr("readonly",true);
					$j("#v_total").val("41650").attr("readonly",true);
					$j("#diferencia_total").val("41650").attr("readonly",true);*/
					}
				 
				 if(convenio.equals("51002")){	
					 
					 seleccionPagoForm.setV_total_parcial(83300); 
					 seleccionPagoForm.setV_a_pagar(83300);
					 seleccionPagoForm.setDiferencia(83300);
					 seleccionPagoForm.setV_total(83300);
					 seleccionPagoForm.setDiferencia(83300);	
					 pagarReadOnly=true;
					 
					/*$j("#sumaParcial").val("83300").attr("readonly",true);
					$j("#sumaPagar").val("83300").attr("readonly",true);
					$j("#diferencia").val("83300").attr("readonly",true);
					$j("#v_total").val("83300").attr("readonly",true);
					$j("#diferencia_total").val("83300").attr("readonly",true);*/
				 	}
	 			
				 // CRUZ BLANCA
				 // 20141203 - SE MODIFICA A PETICION DE PAULO BARRERA.
				 if (seleccionPagoForm.getTiene_documentos().equals("false")){
					 if(isapre.equals("S")){
						 isapreVisible=true;
						 i=0;
						 for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
							 if(!fpb.getId().equals("ISAPR") && !fpb.getId().equals("EXCED") && !fpb.getId().equals("0")) {
				 					fpb.setActivo(true);
				 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
				 			}
							i++; 
				 		 } 
						 
					 }else{
						 i=0;
						 for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
							 if(fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED")) {
				 					fpb.setActivo(true);
				 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
				 			 }	
							 i++;
				 		 } 			 
						
					 }					 
					 
				 }else{
				 		borra_crb();		 		
				 		
				 }
	 			
	 	      }	 //cookie("convenio") != "sdg"				 
				 
	 		}else{//paso_grp != 1
				borra_grpn();
			}
	 	
	 	
	 	//valida doc convenio cruz blanca
	 	if (seleccionPagoForm.getTiene_documentos().equals("false")) {
	 		
	 		if(isapre.equals("S")) {
	 			isapreVisible=true;
	 			i=0;
	 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
	 				if(!fpb.getId().equals("ISAPR") && !fpb.getId().equals("EXCED") && !fpb.getId().equals("0")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 			}
	 				i++;
		 		 } 		
	 			
	 		}else {
	 			i=0;
	 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
	 				if(fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 			}
	 				i++;
		 		 }  			
	 						
	 		}
	 	}else{
	 		borra_crb();		 		
	 		
	 	}
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	 	
	} //fin metodo principal
	
	
	public void borra_grpn(){
		int i=0;
		for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
			if(fpb.getId().equals("GRPON")) {
 					fpb.setActivo(true);
 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
 			}
			i++;
 		 } 		
	}
	
	//GROUPON 
	public void borra_crb(){
		//$j.cookie("crb","crb_2");
		String alb = seleccionPagoForm.getOrigen();
		
		if(alb.toLowerCase().indexOf("albaran") < 0){
			int i=0;
			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
				if(fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED")) {
	 					fpb.setActivo(true);
	 			}
				i++;
	 		 } 			
		}	
	}
	
	

	
	@NotifyChange({"seleccionPagoForm","formaPagoBean"})
	@Command
	public void pagarVenta(@BindingParam("ventana")Window ventana) {
		
		
		ventanaActual = ventana;
		
		if(!seleccionPagoForm.getOrigen().equals("ALBARAN_DEVOLUCION")) {
			
			guardarPago();
		}
		
        if(seleccionPagoForm.getOrigen().equals("ALBARAN_DEVOLUCION")) {
			
        	guarda_PagoAlbaran();
        	
        	//si es impresion 
        	//generaBoleta()
		}
		
	}	
	
	
	public void guardarPago() {
		
		int correcto = 1;	
		
		//grabar variables de sesion para el pago
		sess.setAttribute(Constantes.STRING_LISTA_PAGOS, seleccionPagoForm.getListaPagos());
		//sess.setAttribute(Constantes.STRING_TOTAL, seleccionPagoForm.getV_a_pagar());
		sess.setAttribute(Constantes.STRING_LISTA_FORMAS_PAGOS, seleccionPagoForm.getListaFormasPago());
		sess.setAttribute(Constantes.STRING_FECHA, seleccionPagoForm.getFecha());			
		
		if(seleccionPagoForm.getEstado().equals("PAGADO_TOTAL")) {			
			Messagebox.show("No hay saldos pendientes por pagar");
			correcto = 0;			
		}else {
			
			if(seleccionPagoForm.getV_a_pagar()==0) {				
				if (seleccionPagoForm.getDescuento() != 100) {
					correcto = 0;					
					Messagebox.show("El monto a pagar debe ser mayor a cero");
				}
			}else {
				if (formaPagoBean.getId().equals("")) {					
					Messagebox.show("Debe ingresar una forma de pago");
					correcto = 0;
				}else {
					//no duplicar forma de pago
					Optional<PagoBean> a = seleccionPagoForm.getListaPagos()
												.stream().filter(s -> formaPagoBean.getId()
												.equals(s.getForma_pago()))
												.findFirst();
					if(a.isPresent()) {
						Messagebox.show("Ya se utilizo la forma de pago");
						correcto = 0;
					}
						
					
				}				
				
				/*else
				{
					if(seleccionPagoForm.getDiferencia() == 0)
					{						
						Messagebox.show("No hay saldos pendientes por pagar");
						correcto = 0;
					}
				}	*/		
			}		
		}
		
		
		if (correcto == 1) {			
			try {
				
				//asigno el tipo de pago seleccionado
				seleccionPagoForm.setForma_pago(formaPagoBean.getId());
				//seleccionPagoForm.setTipo_doc('B');				
				//seleccionPagoForm.setOrigen(origen);
				
				seleccionPagoForm.setAccion("pagar");
				seleccionPagoForm= seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);	
				
				//actualizo la diferencia total
				this.setDiferencia_total(seleccionPagoForm.getDiferencia());
				
				//formaPagoBean = null;		
				//en caso de ser completamente pagado				    
				    
			    if (seleccionPagoForm.getDiferencia() == 0) {	   
			    	
			    	objetos = new HashMap<String,Object>();		
					objetos.put("origen",origen);
			    	
			    	Window winSeleccionaPago = (Window)Executions.createComponents(
			                "/zul/venta_directa/SeleccionImpresion.zul", null, objetos);
					
			    	winSeleccionaPago.doModal(); 			    	
			    }
			    
			    //color en cero el monto ingresado
			    seleccionPagoForm.setV_a_pagar(0);
			    
				
			} catch (Exception e) {				
				e.printStackTrace();
			}				
		}			
	}
	
	
	public void guarda_PagoAlbaran()
	{
		int correcto = 1;
		
		//Se muven los bean de los combos a el Form
		
		Optional<FormaPagoBean> fpb = Optional.ofNullable(formaPagoBean);
		
		if(fpb.isPresent())		
			seleccionPagoForm.setForma_pago(formaPagoBean.getId());
		else
			seleccionPagoForm.setForma_pago("0");
		
		if (seleccionPagoForm.getEstado().equals("PAGADO_TOTAL")) {
			Messagebox.show("No hay saldos pendientes por pagar");
			correcto = 0;
		}
		else
		{
			
			if (seleccionPagoForm.getV_a_pagar() == 0) {
				if(seleccionPagoForm.getDescuento() != 100)
				{
					correcto = 0;
					Messagebox.show("No hay saldos pendientes por pagar");
				}
			
			}
			else
			{
				if (seleccionPagoForm.getForma_pago().equals("0")) {
					Messagebox.show("Debe ingresar una forma de pago");
					correcto = 0;
				}
				/*else
				{
					if(seleccionPagoForm.getDiferencia() == 0)
					{
						Messagebox.show("No hay saldos pendientes por pagar");
						correcto = 0;
					}
				} */
			}
		}
		
		
		if(seleccionPagoForm.getDiferencia() < 0){
			Messagebox.show("El pago de la devolucion no puede ser mayor al valor total");
			correcto = 0;
		}
		
		if (correcto == 1) {
			
			try {
				seleccionPagoForm.setAccion("pagar");
				seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);
				
				//impresion de nota de credito
				
				objetos = new HashMap<String,Object>();		
				objetos.put("seleccionPago",seleccionPagoForm);	
				
				BindUtils.postGlobalCommand(null, null, "creaPagoExitosoDevolucion", objetos);
				
				ventanaActual.detach();	
				
				
			} catch (Exception e) {				
				e.printStackTrace();
			}
			
			//load();
		}
		
	}
	
	
	@GlobalCommand
	public void seleccionImpresion(@BindingParam("seleccion")String arg) {
		
		//arg =1 boleta
		//arg =2 guia despacho
		//ARG =3 albaran devolucion 
		
		if(arg.equals("1")) {		
			
			try {
				seleccionPagoForm.setAccion("valida_boleta");
				seleccionPagoForm.setTipo_doc('B');
				
				seleccionPagoForm = seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);				
				
				objetos = new HashMap<String,Object>();		
				objetos.put("seleccionPago",seleccionPagoForm);
				
				if(origen.equals("DIRECTA"))
					BindUtils.postGlobalCommand(null, null, "creaPagoExitoso", objetos);
				
				if(origen.equals("PEDIDO"))
					BindUtils.postGlobalCommand(null, null, "creaPagoExitosoEncargo", objetos);				
				
				if(origen.equals("ALBARAN_DEVOLUCION"))
					BindUtils.postGlobalCommand(null, null, "creaPagoExitosoDevolucion", objetos);	
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}									
			
			ventanaActual.detach();		
			
		}	
		
	}		
	
	
	//validaciones sobre el pago
	@NotifyChange({"seleccionPagoForm"})
	@Command
	public void validaPago() {			
		
		int diferencia;		
		
		diferencia= diferencia_total - seleccionPagoForm.getV_a_pagar();				
		
		if (diferencia < 0) {			
			seleccionPagoForm.setV_a_pagar(0);
			seleccionPagoForm.setDiferencia(this.diferencia_total);
			Messagebox.show("La diferencia no puede ser menor a 0");
			return;
		}
		seleccionPagoForm.setDiferencia(diferencia);
		//this.setDiferencia_total(seleccionPagoForm.getDiferencia());
	}
	
	
	@NotifyChange({"seleccionPagoForm","disableDescuento"})
	@Command
	public void calculaTotalvtaDirecta() {	
		
		if (seleccionPagoForm.getDescuento() < 0 || seleccionPagoForm.getDescuento() > 100) {
			Messagebox.show("Porcentaje del descuento esta fuera de rango"); 
			seleccionPagoForm.setDescuento(dto);			
			return;
		}
		
		
		if (seleccionPagoForm.getDescuento() != dto) {
			
			Double descuento_max = Double.parseDouble(String.valueOf(descuentoMaximo));			
			
			if (dto <= descuento_max) {				
				try {
					
					//seleccionPagoForm.setDescuento(dto);
					seleccionPagoForm.setAccion("descuento_directa");					
					seleccionPagoForm =seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);
					
					//se reajusta el monto a pagar
					diferencia_total = seleccionPagoForm.getV_total();
					seleccionPagoForm.setV_a_pagar(0);
					
				} catch (Exception e) {					
					e.printStackTrace();
				}
				
			}else {
				
				Messagebox.show("El descuento debe ser menor o igual al " + descuento_max + "%"); 
				seleccionPagoForm.setDescuento(dto);
			}			
		}	
		
	}
	
	
	/*function calculaTotalvtaDirecta()
	{
		if (document.getElementById('dto').value != document.getElementById('descuentoTotal').value) {
			
			var descuento_max = parseFloat(document.getElementById('descuento_max').value);
			var dto = parseFloat(document.getElementById('descuentoTotal').value);
			
			if (parseInt(dto) <= parseInt(descuento_max)) {
				document.getElementById('accion').value="descuento_directa";
				document.getElementById('dto').value = document.getElementById('descuentoTotal').value;
				document.seleccionPagoForm.submit();
			} 
			else 
			{
				
					alert("El descuento debe ser menor o igual al " + descuento_max + "%");
					document.getElementById('descuentoTotal').value = document.getElementById('dto').value;
			}
		}
		
		
	}*/
	
	
	
	
	@NotifyChange({"seleccionPagoForm"})	
	@Command
	public void deleteItemAutoriza(@BindingParam("arg")PagoBean b){		
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","pagoVentaDirectaRespuesta");
		objetos.put("seleccionPago",seleccionPagoForm);	
		
		pagoBeanAux = new PagoBean();
		pagoBeanAux = b;
		
		Window winAutoriza = (Window)Executions.createComponents(
                "/zul/venta_directa/AutorizaBorrarPago.zul", null, objetos);
		
		winAutoriza.doModal();			
	}
	
	
	@NotifyChange({"seleccionPagoForm"})	
	@GlobalCommand
	public void pagoVentaDirectaRespuesta(@BindingParam("tipoAccion")String tipo, @BindingParam("autorizador")String autorizador) {
		
		//seleccionPagoForm.getListaPagos().remove(pagoBeanAux);
		//pagoBeanAux=null;
		
		if (seleccionPagoForm.getOrigen().equals("ALBARAN_DIRECTA")) {
			
			//eliminar_pago_albaran('${forma_pago}','${fech_pago}');
			/*document.getElementById('accion').value="eliminarFormaPagoSeleccionPago";
			document.getElementById('f_pago').value=forma_pago;	
			document.getElementById('fech_pago').value=fecha_pago;
			var x = document.forms[0];		
			x.action = "<%=request.getContextPath()%>/SeleccionPago.do?method=eliminaFPagoBoleta";
			x.submit();	*/			
			
			try {
				seleccionPagoForm.setF_pago(pagoBeanAux.getForma_pago());
				seleccionPagoForm.setFech_pago(pagoBeanAux.getFecha());			
				seleccionPagoForm.setAccion("eliminarFormaPagoSeleccionPago");
				seleccionPagoForm.setTipoaccion(tipo);
				seleccionPagoForm.setAutorizador(autorizador);				
				seleccionPagoDispatchActions.eliminaFPagoBoleta(seleccionPagoForm, sess);			
				
			} catch (Exception e) {				
				e.printStackTrace();
			}
			
		}else {
			
			if(seleccionPagoForm.getOrigen().equals("ALBARAN_DEVOLUCION")) {
				
				//eliminar_pago_albaran('${forma_pago}','${fech_pago}');
				try {
					seleccionPagoForm.setF_pago(pagoBeanAux.getForma_pago());
					seleccionPagoForm.setFech_pago(pagoBeanAux.getFecha());			
					seleccionPagoForm.setAccion("eliminarFormaPagoSeleccionPago");
					seleccionPagoForm.setTipoaccion(tipo);
					seleccionPagoForm.setAutorizador(autorizador);				
					seleccionPagoDispatchActions.eliminaFPagoBoleta(seleccionPagoForm, sess);			
					
				} catch (Exception e) {				
					e.printStackTrace();			}
				
				
			}else {
				
				//eliminar_pago('${forma_pago}','${fech_pago}','<%=request.getContextPath()%>');
				
				/*$j('#accion').val("eliminarFormaPago");
				$j('#f_pago').val(forma_pago);	
				$j('#fech_pago').val(fecha_pago);
				
				$j.cookie('convenio','0');
				$j("#sumaPagar").val("").attr("readonly",false);*/
				try {
					seleccionPagoForm.setF_pago(pagoBeanAux.getForma_pago());
					seleccionPagoForm.setFech_pago(pagoBeanAux.getFecha());			
					seleccionPagoForm.setAccion("eliminarFormaPago");
					seleccionPagoForm.setTipoaccion(tipo);
					seleccionPagoForm.setAutorizador(autorizador);					
					
					seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);				
					
				} catch (Exception e) {					
					e.printStackTrace();
				}
				
			}		
			
		}
		
		
		
		
		
	}
	
	
	@NotifyChange({"seleccionPagoForm"})	
	@Command
	public void generaBoleta() {
		
		if (seleccionPagoForm.getTiene_pagos()==0) {			
			Messagebox.show("No hay pagos, no es posible imprimir");
			return;			
		}
		
		if (seleccionPagoForm.getEstado().equals("PAGADO_TOTAL")) {			
			Messagebox.show("No es posible imprimir documentos, la venta ya se encuentra pagada en su totalidad");
			return;
		}
		
		if(seleccionPagoForm.getOrigen().equals("PEDIDO")) {
			
			if(seleccionPagoForm.getAnticipo_pedido()==0) {
				
				if (seleccionPagoForm.getTiene_pagos_actuales().equals("true")) {
					
					objetos = new HashMap<String,Object>();		
					objetos.put("origen","PEDIDO");
					
					//ventana de seleccion de impresion
					Window winSeleccionaGenera = (Window)Executions.createComponents(
			                "/zul/venta_directa/SeleccionImpresion.zul", null, objetos);
					winSeleccionaGenera.doModal();
					
				}else {
					
					Messagebox.show("No hay pagos nuevos para imprimir");
					return;
				} 			
				
			}else {		
				
				Messagebox.show("El monto pagado no puede ser menor al Anticipo total");
				return;
			}		
			
			
		}else {// distinto a pedido
			
			if(seleccionPagoForm.getDiferencia()==0) {
				
				if(seleccionPagoForm.getTiene_pagos_actuales().equals("true")) {
					
					objetos = new HashMap<String,Object>();		
					objetos.put("origen","DIRECTA");
					
					//ventana de seleccion de impresion
					Window winSelecciona = (Window)Executions.createComponents(
			                "/zul/venta_directa/SeleccionImpresion.zul", null, objetos);					
				}else {
					Messagebox.show("No hay pagos nuevos para imprimir");
					return;
				}				
				
			}else {		
				
				Messagebox.show("Existen pagos pendientes, no se puede imprimir");
				return;				
			}			
		}		
		
	}
	
    
	
	//getter and setter
	//==============================
	
	public SeleccionPagoForm getSeleccionPagoForm() {
		return seleccionPagoForm;
	}

	public void setSeleccionPagoForm(SeleccionPagoForm seleccionPagoForm) {
		this.seleccionPagoForm = seleccionPagoForm;
	}

	public ClienteBean getCliente() {
		return cliente;
	}

	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}

	public FormaPagoBean getFormaPagoBean() {
		return formaPagoBean;
	}

	public void setFormaPagoBean(FormaPagoBean formaPagoBean) {
		this.formaPagoBean = formaPagoBean;
	}	
	
	public Integer getDiferencia_total() {
		return diferencia_total;
	}

	public void setDiferencia_total(Integer diferencia_total) {
		this.diferencia_total = diferencia_total;
	}

	public String getDisableDescuento() {
		return disableDescuento;
	}

	public void setDisableDescuento(String disableDescuento) {
		this.disableDescuento = disableDescuento;
	}

	public BeanControlBotones getControlBotones() {
		return controlBotones;
	}

	public void setControlBotones(BeanControlBotones controlBotones) {
		this.controlBotones = controlBotones;
	}

	public Integer getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(Integer sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public boolean isIsapreVisible() {
		return isapreVisible;
	}

	public void setIsapreVisible(boolean isapreVisible) {
		this.isapreVisible = isapreVisible;
	}

	public boolean isPagarReadOnly() {
		return pagarReadOnly;
	}

	public void setPagarReadOnly(boolean pagarReadOnly) {
		this.pagarReadOnly = pagarReadOnly;
	}
	
	

	
}
