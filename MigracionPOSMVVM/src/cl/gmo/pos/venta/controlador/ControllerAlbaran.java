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
		
		devolucionDispatch.cargaFormulario(devolucionForm, sess);		
	}
	
	
	//==================================================================
	//=============Procesos principales de la toolbar ==================
	//==================================================================
	
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void nuevoAlbaran(){
		
		devolucionForm.setAccion("nuevo");
		devolucionDispatch.cargaFormulario(devolucionForm, sess);
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void pagarAlbaran(){
		
		
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
		
		
	}
	
	@Command
	@NotifyChange({"devolucionForm"})
	public void abrirBuscarAlbaranes(){
		
		
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
	
	@NotifyChange({"DevolucionForm"})
	public void postCobro() {	
		Messagebox.show("Devolución realizada con exito");
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
	
}
