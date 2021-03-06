package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.protobuf.Message;

import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;



public class ControllerPagoVentaDirecta implements Serializable{
	
	
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
	
	private FormaPagoBean formaPagoBean;
	private String disableDescuento;
	private PagoBean pagoBeanAux;
	private String origen;
	private String fecha=null;
	private BeanControlBotones controlBotones;
	
	private Window ventanaActual= new Window();
	
	@Init
	public void inicio(@ContextParam(ContextType.VIEW) Component view, 
					   @ExecutionArgParam("cliente")ClienteBean arg,
					   @ExecutionArgParam("pagoForm")SeleccionPagoForm arg2,
					   @ExecutionArgParam("ventaOrigenForm")Object arg3,
					   @ExecutionArgParam("origen")String arg4) {
		
		
		Selectors.wireComponents(view, this, false);
		
		cliente             = null;		
		seleccionPagoForm 	= null;
		ventaPedidoForm		= null;
		ventaDirectaForm	= null;
		
		controlBotones 		= new BeanControlBotones();
		controlBotones.setEnableGenerico1("false");
		
		formaPagoBean = new FormaPagoBean();
		pagoBeanAux   = new PagoBean();	
		seleccionPagoDispatchActions = new SeleccionPagoDispatchActions();		
		
		cliente           = (ClienteBean)arg;		
		seleccionPagoForm = (SeleccionPagoForm)arg2;
		origen            = (String)arg4;
		
		if (arg3 instanceof VentaPedidoForm) { 
			ventaPedidoForm = (VentaPedidoForm)arg3;
			fecha = ventaPedidoForm.getFecha();
			controlBotones.setEnableGenerico1("true");
			controlBotones.setEnableGenerico2("false");
			this.dto = 0.0;
			
		}
		
		if (arg3 instanceof VentaDirectaForm) { 
			ventaDirectaForm= (VentaDirectaForm)arg3;
			fecha = ventaDirectaForm.getFecha();
			controlBotones.setEnableGenerico1("false");
			controlBotones.setEnableGenerico2("true");
			this.dto = ventaDirectaForm.getDescuentoTotal();
		}
		if (arg3 instanceof DevolucionForm) { 
			devolucionForm = (DevolucionForm)arg3;
			fecha = seleccionPagoForm.getFecha();
			controlBotones.setEnableGenerico1("false");
			controlBotones.setEnableGenerico2("true");
			this.dto = 0.0;
		}
		
		seleccionPagoDispatchActions.carga_formulario(seleccionPagoForm, sess, fecha);
		
		this.setDiferencia_total(seleccionPagoForm.getV_total());		
		seleccionPagoForm.setV_a_pagar(0);		
		
	}

	
	@NotifyChange({"seleccionPagoForm","formaPagoBean"})
	@Command
	public void pagarVenta(@BindingParam("ventana")Window ventana) {
		
		
		ventanaActual = ventana;
		
		if(!seleccionPagoForm.getOrigen().equals("ALBARAN_DEVOLUCION")) {
			
			guardarPago();
		}
		
        if(seleccionPagoForm.getOrigen().equals("ALBARAN_DEVOLUCION")) {
        	guardarPago();
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
	
	
	@GlobalCommand
	public void seleccionImpresion(@BindingParam("seleccion")String arg) {
		
		//arg =1 boleta
		//arg =2 guia despacho
		
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
		
		if (seleccionPagoForm.getDescuento() != dto) {
			
			Double descuento_max = Double.parseDouble(String.valueOf(ventaDirectaForm.getPorcentaje_descuento_max()));			
			
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
		objetos.put("seleccionPagoForm",seleccionPagoForm);
		pagoBeanAux = new PagoBean();
		pagoBeanAux = b;
		
		Window winAutoriza = (Window)Executions.createComponents(
                "/zul/venta_directa/AutorizaBorrarPago.zul", null, objetos);
		
		winAutoriza.doModal();			
	}
	
	
	@NotifyChange({"seleccionPagoForm"})	
	@GlobalCommand
	public void deleteItem() {
		
		seleccionPagoForm.getListaPagos().remove(pagoBeanAux);
		pagoBeanAux=null;
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
	
}
