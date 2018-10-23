package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;



public class ControllerMostrarPagosBoleta implements Serializable {
	
	private static final long serialVersionUID = 6125242362315412759L;
	Session sess = Sessions.getCurrent();
	
	HashMap<String,Object> objetos;
	private SeleccionPagoForm seleccionPagoForm;
	private SeleccionPagoDispatchActions seleccionPagoDispatchActions;
	private PagoBean pagoBean;
	
	
	@Init
	public void inicial(@ExecutionArgParam("seleccionPago")SeleccionPagoForm arg) {	
		
		seleccionPagoForm = new SeleccionPagoForm();
		seleccionPagoForm = arg;
		seleccionPagoDispatchActions = new SeleccionPagoDispatchActions();	
		pagoBean = new PagoBean();
		
		
		
		sess.setAttribute("tipo", "PEDIDO");		
			
		try {
			seleccionPagoDispatchActions.cargaMostrarPagosBoletas(seleccionPagoForm, sess);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
	
	
	@Command
	public void solicitaAutorizacionBorrarPago(@BindingParam("pago")PagoBean pago) {	
		
		pagoBean=pago;
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","autorizaBorrarPagoRespuesta");
		objetos.put("seleccionPago",seleccionPagoForm);
		
		Window windowMostrarPagosBoleta = (Window)Executions.createComponents(
                "/zul/venta_directa/AutorizaBorrarPago.zul", null, objetos);
		
		windowMostrarPagosBoleta.doModal();			
	}
	
	
	@NotifyChange("seleccionPagoForm")
	@GlobalCommand
	public void autorizaBorrarPagoRespuesta(@BindingParam("tipoAccion")String tipo, @BindingParam("autorizador")String autorizador) {
		
		
		//pagoBean : contiene el pago seleccionado  
		
		try {
			
			//String cdg_vta = formulario.getSerie();
			//String fecha_pedido = formulario.getFecha();
			//String f_pago = formulario.getF_pago();
			//String fecha_forma_pago = formulario.getFech_pago();
			//String tipo_accion = formulario.getTipoaccion();
			//String autorizador = formulario.getAutorizador();
			
			
			seleccionPagoForm.setF_pago(pagoBean.getForma_pago());
			seleccionPagoForm.setFech_pago(pagoBean.getFecha());			
			seleccionPagoForm.setAccion("eliminarFormaPagoBoleta");
			seleccionPagoForm.setTipoaccion(tipo);
			seleccionPagoForm.setAutorizador(autorizador);
			
			seleccionPagoDispatchActions.IngresaPago(seleccionPagoForm, sess);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	

	
	//============  metodos getter and setter ===============

	public SeleccionPagoForm getSeleccionPagoForm() {
		return seleccionPagoForm;
	}

	public void setSeleccionPagoForm(SeleccionPagoForm seleccionPagoForm) {
		this.seleccionPagoForm = seleccionPagoForm;
	}
	

}
