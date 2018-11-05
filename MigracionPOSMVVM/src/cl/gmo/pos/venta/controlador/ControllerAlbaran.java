package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import com.ibm.icu.util.Calendar;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.actions.DevolucionDispatchActions;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
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
	
	private String usuario;	
	private String sucursalDes;	
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	
	
	private Date fechaActual;
	private Date horaActual;
	private Date fechaGarantia;
	private boolean disabledCampo=false;
	
	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		devolucionForm = new DevolucionForm();
		devolucionBean = new DevolucionBean();
		seleccionPagoForm = new SeleccionPagoForm();
		ventaPedidoForm = new VentaPedidoForm();
		devolucionDispatch = new DevolucionDispatchActions();
		
		fechaActual = new Date(System.currentTimeMillis());
		horaActual  = new Date(System.currentTimeMillis());
		fechaGarantia= new Date(System.currentTimeMillis());
		disabledCampo=true;
		
		devolucionDispatch.cargaFormulario(devolucionForm, sess);		
	}
	
	
	//==================================================================
	//=============Procesos principales de la toolbar ==================
	//==================================================================
	
	
	@Command
	@NotifyChange({"devolucionForm","disabledCampo"})
	public void nuevoAlbaran(){
		
		devolucionForm.setAccion("nuevo");
		devolucionDispatch.cargaFormulario(devolucionForm, sess);
		
		disabledCampo=false;
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void pagarAlbaran(){
		
		/*objetos = new HashMap<String,Object>();
		objetos.put("cliente",cliente);
		objetos.put("pagoForm",seleccionPagoForm);
		objetos.put("ventaOrigenForm",devolucionForm);
		objetos.put("origen","PEDIDO");*/
		
		Window windowPagoVentaDirecta = (Window)Executions.createComponents(
                "/zul/venta_directa/pagoVentaDirecta.zul", null, objetos);
		
		windowPagoVentaDirecta.doModal();
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void eliminarAlbaran(){
		
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void mostrarPagosBoletas(){
		
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void mostrarListaAlbaranes(){
		
		/*function mostrarListaAlbaranes(){
			showPopWin("<%=request.getContextPath()%>/Devolucion.do?method=mostrarListaAlbaranes", 714, 425,cargaAlbaran, false);
		}*/
		
				
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
	
		
	@NotifyChange({"*","controlBotones"})
	@GlobalCommand	
    public void creaPagoExitosoDevolucion(@BindingParam("seleccionPago")SeleccionPagoForm seleccionPago) {		
		
		/*
		dform_in = new DevolucionForm();
		dform_out = new DevolucionForm();
		dform_in.setAccion(Constantes.FORWARD_DEVOLUCION);			
		sesion.setAttribute(Constantes.STRING_TIPO_ALBARAN, "DEVOLUCION");	
		dform_in.setBoleta_guia(sesion.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString());
		dform_in.setNumero_boleta_guia(sesion.getAttribute("NUMERO_BOLETA_GUIA").toString());
		dform_in.setLista_productos(this.getLista_productos());
		for(ProductosBean b : dform_in.getLista_productos()) {
			System.out.println(b.getCod_articulo()+"<=======>"+b.getDescripcion());
		}
		dform_in.setAgente(this.getAgente());
		this.getListaAgentes().forEach(t->{
			if(t.getUsuario().equals(this.getAgente())){
				dform_in.setAgente(t.getUsuario());
				dform_in.setAgenteSeleccionado(t.getUsuario());
			}
		}
		);
		this.getLista_mot_devo().forEach(t->{
				if(t.getDescripcion().equals(this.getMotivo())){
					dform_in.setMotivo(t.getCodigo());
				}
			}
		);
		dform_in.setFecha(util.traeFechaHoyFormateadaString());
		dform_in.setCodigo_cliente(sesion.getAttribute(Constantes.STRING_CLIENTE).toString());
		dform_in.setAgente(sesion.getAttribute(Constantes.STRING_USUARIO).toString());
		dform_in.setTipoAlbaran("D");
		dform_in.setCambio("1");
		dform_in.setDivisa("PESO");
		try {
			dform_out = dev_dis.cargaAlbaran(dform_in,sesion);			
			sesion.setAttribute(Constantes.STRING_TICKET, dform_in.getCodigo1()+ "/" + dform_in.getCodigo2());
			
			postCobro();
			
            if (dform_out.getEstado_boleta().trim().toUpperCase().contains("TRUE") ) {
				
				Messagebox.show("Error: No se pudo generar la boleta, Intentelo nuevamente (1).");
			}else {
				if(!dform_out.getEstado_boleta().equals("") && dform_out.getEstado_boleta()!= null) {
					
					String[] boleta =  dform_out.getEstado_boleta().split("_");
					//String url ="http://10.216.4.24/NC/61 "+dform_in.getNif()+"-"+dform_in.getDvnif()+" "+boleta[1]+".pdf";
					String url ="http://10.216.4.24/NC/61 66666666-6 79912.pdf";
					
					objetos = new HashMap<String,Object>();
					objetos.put("documento",url);
					objetos.put("titulo","Venta Directa");
					
					Window window = (Window)Executions.createComponents(
			                "/zul/reportes/VisorDocumento.zul", null, objetos);
					
			        window.doModal();	
			        
				 }else {
					 Messagebox.show("Error: No se pudo generar la boleta, Intentelo nuevamente (2).");
				 }
						
			}
            System.out.println("DFORM CODIGO ============>"+dform_out.getCodigo1()+ "/" + dform_out.getCodigo2());
            this.setCodigo_completo(dform_out.getCodigo1()+ "/" + dform_out.getCodigo2());
			
		} catch (Exception e) {
			
			e.printStackTrace();
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
	
	@NotifyChange({"devolucionForm"})
	@GlobalCommand
	public void albaranSeleccionado(@BindingParam("albaran")AlbaranBean albaran) {
		
		devolucionForm.setAccion("traeAlbaranBuscado");
		devolucionForm.setCdg_venta(albaran.getCodigo_albaran());
		
		devolucionDispatch.cargaAlbaran(devolucionForm, sess);
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

	public Date getHoraActual() {
		return horaActual;
	}

	public void setHoraActual(Date horaActual) {
		this.horaActual = horaActual;
	}

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
	
}
