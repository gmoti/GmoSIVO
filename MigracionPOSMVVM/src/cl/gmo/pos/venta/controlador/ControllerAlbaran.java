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
	
	DevolucionForm dform_in;
	DevolucionForm dform_out ;

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
		dform_in = dev_dis.cargaInicial(sesion);
		this.setListaFormasPago(dform_in.getListaFormasPago());
		this.setLista_mot_devo(dform_in.getLista_mot_devo());
		this.setLista_productos(dform_in.getLista_productos());
		this.setLista_albaranes(dform_in.getLista_albaranes());
		this.setListaAgentes(dform_in.getListaAgentes());
		this.setListaConvenios(dform_in.getListaConvenios());
		this.setListaIdiomas(dform_in.getListaIdiomas());
		this.setListaDivisas(dform_in.getListaDivisas());
		this.setListaProvincia(dform_in.getListaProvincia());
		this.setListaTipoAlbaranes(dform_in.getListaTipoAlbaranes());
		this.setRboleta(true);
		this.setUsuario((String)sesion.getAttribute(Constantes.STRING_USUARIO));
		this.setEstado_boleta("-1");
	}
	@Command
	@NotifyChange({"*"})
	public void cargaDatos(@BindingParam("dev") DevolucionForm form) {
		dform_in = new DevolucionForm();
		
		//DEFINO CONTSANTES POR DEFECTO
		form.setTipoAlbaran("D");
		form.setAccion("cargarDatos");
		
		if(this.isRboleta() == true) {
			form.setBoleta_guia("B");
		}else {
			form.setBoleta_guia("G");
		}
		
		System.out.println("BOLETA_GUIA ==>"+form.getBoleta_guia()+" "+form.getNumero_boleta_guia());
		dform_in =	dev_dis.cargaAlbaran(form,sesion);
		System.out.println("EXISTE BOLETA ==>"+dform_in.getExisteBoleta());
		if(dform_in.getExisteBoleta().trim().toUpperCase().equals("FALSE")) {
				this.setNif(dform_in.getNif());
				this.setDvnif(dform_in.getDvnif());
				this.setHora(util.traeHoraString());
				this.setFecha_alb(util.traeFecha());
				this.setFormaPago("CONTADO");
				this.setIdioma("CASTELLANO");
				this.setTipo_albaran("DEVOLUCION");
				this.setDireccion_cli(dform_in.getDireccion_cli());
				this.setNdireccion_cli(dform_in.getNdireccion_cli());
				System.out.println("ALBARAN CONTROLLER ==>"+dform_in.getProvincia()+"<==>"+dform_in.getComu_cli()+"<==>"+dform_in.getProvincia_cliente()+"<==>"+dform_in.getCiudad());
				this.setProvincia_cliente(dform_in.getCiudad());
				
				this.getListaDivisas().forEach(t->{
					if(t.getId().equals(dform_in.getDivisa())) {
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
		dform_in = new DevolucionForm();
		seleccionPagoForm =  new SeleccionPagoForm();
	    dform_in = form;
	    ClienteBean cliente = util.traeCliente(dform_in.getNif(), "");
		//dform_in = dev_dis.cargaAlbaran(dform_in,sesion);		
		seleccionPagoForm.setOrigen(sesion.getAttribute(Constantes.STRING_ORIGEN).toString());
		seleccionPagoForm.setFecha(util.traeFechaHoyFormateadaString());

		objetos = new HashMap<String,Object>();
		objetos.put("cliente",cliente);
		objetos.put("pagoForm",seleccionPagoForm);
		objetos.put("ventaOrigenForm",dform_in);
		objetos.put("origen","ALBARAN_DEVOLUCION");
		
		Window windowPagoVentaDirecta = (Window)Executions.createComponents(
                "/zul/venta_directa/pagoVentaDirecta.zul", null, objetos);
		
		windowPagoVentaDirecta.doModal();			
		
	}
	
	@NotifyChange({"*","controlBotones"})
	@GlobalCommand	
    public void creaPagoExitosoDevolucion(@BindingParam("seleccionPago")SeleccionPagoForm seleccionPago) {		
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
