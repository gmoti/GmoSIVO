package cl.gmo.pos.venta.controlador;

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

public class ControllerAlbaran extends DevolucionForm{
	
	DevolucionForm dform ;
	DevolucionBean devbean ;
	DevolucionDispatchActions dev_dis = new DevolucionDispatchActions();
	Utils util = new Utils();
	Session sesion = Sessions.getCurrent();
	HashMap<String,Object> objetos;
	private SeleccionPagoForm 		seleccionPagoForm;
	private VentaPedidoForm 		ventaPedidoForm;
	
	private String codigo_completo;
	private Date fecha_alb;
	private Date fecha_gar;
	private boolean rboleta;
	private boolean rguia;
	private boolean ch_entrega;
	private boolean ch_facturado;
	
	@Init	
	public void inicial() {
		
		//String local = sesion.getAttribute("sucursal").toString();
		dform = dev_dis.cargaInicial(sesion);
		this.setListaFormasPago(dform.getListaFormasPago());
		this.setLista_mot_devo(dform.getLista_mot_devo());
		this.setLista_productos(dform.getLista_productos());
		this.setLista_albaranes(dform.getLista_albaranes());
		this.setListaAgentes(dform.getListaAgentes());
		this.setListaConvenios(dform.getListaConvenios());
		this.setListaIdiomas(dform.getListaIdiomas());
		this.setListaDivisas(dform.getListaDivisas());
		this.setListaProvincia(dform.getListaProvincia());
		this.setListaTipoAlbaranes(dform.getListaTipoAlbaranes());
		this.setRboleta(true);
		this.setUsuario((String)sesion.getAttribute(Constantes.STRING_USUARIO));
		this.setEstado_boleta("-1");
	}
	@Command
	@NotifyChange({"*"})
	public void cargaDatos(@BindingParam("dev") DevolucionForm form) {
		dform = new DevolucionForm();
		
		//DEFINO CONTSANTES POR DEFECTO
		form.setTipoAlbaran("D");
		form.setAccion("cargarDatos");
		
		if(this.isRboleta() == true) {
			form.setBoleta_guia("B");
		}else {
			form.setBoleta_guia("G");
		}
		
		System.out.println("BOLETA_GUIA ==>"+form.getBoleta_guia()+" "+form.getNumero_boleta_guia());
		dform =	dev_dis.cargaAlbaran(form,sesion);
		System.out.println("EXISTE BOLETA ==>"+dform.getExisteBoleta());
		if(dform.getExisteBoleta().trim().toUpperCase().equals("FALSE")) {
				this.setNif(dform.getNif());
				this.setDvnif(dform.getDvnif());
				this.setHora(util.traeHoraString());
				this.setFecha_alb(util.traeFecha());
				this.setFormaPago("CONTADO");
				this.setIdioma("CASTELLANO");
				this.setTipo_albaran("DEVOLUCION");
				this.setDireccion_cli(dform.getDireccion_cli());
				this.setNdireccion_cli(dform.getNdireccion_cli());
				System.out.println("ALBARAN CONTROLLER ==>"+dform.getProvincia()+"<==>"+dform.getComu_cli()+"<==>"+dform.getProvincia_cliente()+"<==>"+dform.getCiudad());
				this.setProvincia_cliente(dform.getCiudad());
				
				this.getListaDivisas().forEach(t->{
					if(t.getId().equals(dform.getDivisa())) {
						this.setDivisa(t.getDescripcion());
					}
				 }
				);
		}else{
			Messagebox.show("La boleta ya fue anulada \n,No es posible generar otra \n Nota de Crédito a la \n misma Boleta.");
		}
	   
		
	
	}
	@Command
	@NotifyChange({"*"})
	public void nuevoAlbaran(@BindingParam("arg1") DevolucionForm form){
		this.setFecha_gar(null);
		
	}
	@Command
	@NotifyChange({"*"})
	public void cobrar(@BindingParam("arg1") DevolucionForm form){
		dform = new DevolucionForm();
		seleccionPagoForm =  new SeleccionPagoForm();
	    dform = form;
	    ClienteBean cliente = util.traeCliente(dform.getNif(), "");
		//dform = dev_dis.cargaAlbaran(dform,sesion);		
		seleccionPagoForm.setOrigen(sesion.getAttribute(Constantes.STRING_ORIGEN).toString());
		seleccionPagoForm.setFecha(util.traeFechaHoyFormateadaString());

		objetos = new HashMap<String,Object>();
		objetos.put("cliente",cliente);
		objetos.put("pagoForm",seleccionPagoForm);
		objetos.put("ventaOrigenForm",dform);
		objetos.put("origen","ALBARAN_DEVOLUCION");
		
		Window windowPagoVentaDirecta = (Window)Executions.createComponents(
                "/zul/venta_directa/pagoVentaDirecta.zul", null, objetos);
		
		windowPagoVentaDirecta.doModal();			
		
	}
	
	@NotifyChange({"dform","controlBotones"})
	@GlobalCommand	
    public void creaPagoExitosoDevolucion(@BindingParam("seleccionPago")SeleccionPagoForm seleccionPago) {		
		dform = new DevolucionForm();
		dform.setAccion(Constantes.FORWARD_DEVOLUCION);			
		sesion.setAttribute(Constantes.STRING_TIPO_ALBARAN, "DEVOLUCION");	
		dform.setBoleta_guia(sesion.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString());
		dform.setNumero_boleta_guia(sesion.getAttribute("NUMERO_BOLETA_GUIA").toString());
		dform.setLista_productos(this.getLista_productos());
		for(ProductosBean b : dform.getLista_productos()) {
			System.out.println(b.getCod_articulo()+"<=======>"+b.getDescripcion());
		}
		dform.setAgente(this.getAgente());
		this.getListaAgentes().forEach(t->{
			if(t.getUsuario().equals(this.getAgente())){
				dform.setAgente(t.getUsuario());
				dform.setAgenteSeleccionado(t.getUsuario());
			}
		}
		);
		this.getLista_mot_devo().forEach(t->{
				if(t.getDescripcion().equals(this.getMotivo())){
					dform.setMotivo(t.getCodigo());
				}
			}
		);
		dform.setFecha(util.traeFechaHoyFormateadaString());
		dform.setCodigo_cliente(sesion.getAttribute(Constantes.STRING_CLIENTE).toString());
		dform.setAgente(sesion.getAttribute(Constantes.STRING_USUARIO).toString());
		dform.setTipoAlbaran("D");
		
		try {
			dform = dev_dis.cargaAlbaran(dform,sesion);			
			sesion.setAttribute(Constantes.STRING_TICKET, dform.getCodigo1()+ "/" + dform.getCodigo2());
			this.setCodigo_completo(dform.getCodigo1()+ "/" + dform.getCodigo2());
			postCobro();
			
            if (dform.getEstado_boleta().trim().toUpperCase().contains("TRUE") ) {
				
				Messagebox.show("Error: No se pudo generar la boleta, Intentelo nuevamente.");
			}else {
				
				//http://10.216.4.24/39%2066666666-6%201.pdf     61 66666666-6 79912
				String url ="http://10.216.4.24/NC/61 66666666-6 79912.pdf";
				//String url ="http://10.216.4.24/39%" + ventaDirectaForm.getNif() + "-" + ventaDirectaForm.getDv() + 
				
				objetos = new HashMap<String,Object>();
				objetos.put("documento",url);
				objetos.put("titulo","Venta Directa");
				
				Window window = (Window)Executions.createComponents(
		                "/zul/reportes/VisorDocumento.zul", null, objetos);
				
		        window.doModal();			
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}			
		
	}
	
	@NotifyChange({"DevolucionForm"})
	public void postCobro() {	
		Messagebox.show("Devolución realizada con exito");
	}
	
	
	
	@Command
	public void cerrar(@BindingParam("arg1")  Window x) {
	    x.detach();
	}
	
	public String getCodigo_completo() {
		return codigo_completo;
	}

	public void setCodigo_completo(String codigo_completo) {
		this.codigo_completo = codigo_completo;
	}

	public Date getFecha_alb() {
		return fecha_alb;
	}

	public void setFecha_alb(Date fecha_alb) {
		this.fecha_alb = fecha_alb;
	}

	public boolean isRboleta() {
		return rboleta;
	}

	public void setRboleta(boolean rboleta) {
		this.rboleta = rboleta;
	}

	public boolean isRguia() {
		return rguia;
	}

	public void setRguia(boolean rguia) {
		this.rguia = rguia;
	}

	public boolean isCh_entrega() {
		return ch_entrega;
	}

	public void setCh_entrega(boolean ch_entrega) {
		this.ch_entrega = ch_entrega;
	}

	public boolean isCh_facturado() {
		return ch_facturado;
	}

	public void setCh_facturado(boolean ch_facturado) {
		this.ch_facturado = ch_facturado;
	}

	public Date getFecha_gar() {
		return fecha_gar;
	}

	public void setFecha_gar(Date fecha_gar) {
		this.fecha_gar = fecha_gar;
	}

	
}
