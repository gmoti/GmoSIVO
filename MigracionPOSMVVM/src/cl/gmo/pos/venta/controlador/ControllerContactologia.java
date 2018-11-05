package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.ContactologiaDispatchActions;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.forms.ContactologiaForm;

public class ControllerContactologia implements Serializable {

	
	private static final long serialVersionUID = 4072602925383715793L;
	Session sess = Sessions.getCurrent();	
	
	private String usuario;	
	private String sucursalDes;
	private ContactologiaForm contactologiaForm;
	private ContactologiaDispatchActions contactologiaDispatch;
	
	HashMap<String,Object> objetos;
	
	private Date fechaEncargo;
	private Date fechaRecepcion;
	private Date fechaEntrega;
	private Date fechaCaducidad;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");
	
	
	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		/*fechaEncargo = new Date(System.currentTimeMillis());
		fechaRecepcion = new Date(System.currentTimeMillis());
		fechaEntrega = new Date(System.currentTimeMillis());
		fechaCaducidad = new Date(System.currentTimeMillis());*/
		
		contactologiaForm = new ContactologiaForm();
		contactologiaDispatch = new ContactologiaDispatchActions();			
		
		contactologiaForm.setNombre_cliente(sess.getAttribute("nombre_cliente").toString());
		
		try {			
			contactologiaDispatch.cargaFormulario(contactologiaForm, sess);
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	//==========Operaciones Principales de la clase ================
	//==============================================================
	
	@NotifyChange({"contactologiaForm"})
	@Command
	public void nuevoGraduacion() {
		//ventana inalcanzable
	}
	
	
	@Command
	public void ingresoPresupuesto(@BindingParam("win")Window win ) {
		
		sess.setAttribute("cliente",contactologiaForm.getCliente());
		sess.setAttribute("nombre_cliente",contactologiaForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "contactologia");	
		
		win.detach();
		
		Window winPresup = (Window)Executions.createComponents(
                "/zul/presupuestos/presupuesto.zul", null, objetos);			
		winPresup.doModal();		
	}
	
	
	@Command
	public void ingresoEncargo(@BindingParam("win")Window win ) {
		
		sess.setAttribute("cliente",contactologiaForm.getCliente());
		sess.setAttribute("nombre_cliente",contactologiaForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "contactologia");	
		
		win.detach();
		
		Window winPresup = (Window)Executions.createComponents(
                "/zul/encargos/encargos.zul", null, objetos);			
		winPresup.doModal();		
	}
	
	@NotifyChange({"contactologiaForm"})
	@Command
	public void insertarContactologia() {
		
		String doctor="";
		String pagina="";
		String existeContactologia="";
		boolean respuesta=false;
		String respuesta3="";
		
		contactologiaForm.setFecha_caducidad(dt.format(fechaCaducidad));
		contactologiaForm.setFecha_entrega(dt.format(fechaEntrega));
		contactologiaForm.setFecha_pedido(dt.format(fechaEncargo));
		contactologiaForm.setFecha_recepcion(dt.format(fechaRecepcion));	
		
		doctor = contactologiaForm.getDoctor();
		pagina = contactologiaForm.getGrabar();
		existeContactologia = contactologiaForm.getExisteContactologia();		
		
		
		if(existeContactologia.equals("true")) {			
			Messagebox.show("La receta esta asociada a una venta, no puede ser modificada, debe generar una nueva receta");
			return;
		}else {
			
			respuesta= validaInfoContactologia();
			
			if(!(pagina.equals("false")) || (!respuesta)) {
				
				if (respuesta) {
					
					if(!doctor.equals("-1")) {
						
						contactologiaForm.setAccion("ingresoContactologia");
						contactologiaDispatch.ingresaContactologia(contactologiaForm, sess);
					}else {				
						Messagebox.show("Debe ingresar doctor");
						return;
					}
				}
				
			}else {
				
				Messagebox.show("Desea modificar la receta ?", "Modificar",
						Messagebox.YES | 
						Messagebox.CANCEL, 
						Messagebox.QUESTION, new EventListener<Event>() {			
					@Override
					public void onEvent(Event e) throws Exception {	
						
							if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {								
								
								contactologiaForm.setAccion("modificarContactologia");
								contactologiaDispatch.ingresaContactologia(contactologiaForm, sess);
							}						
						}
				});					
			}			
		}
	}	
		
	
	@Command
	public void cerrarContactologia(@BindingParam("win")Window win) {
		win.detach();
	}

	
	//======Funcionalidades varias=================
	//=============================================
	@NotifyChange({"contactologiaForm","fechaEncargo","fechaRecepcion","fechaEntrega","fechaCaducidad"})
	@Command
	public void verGraduacion(@BindingParam("contactologia")ContactologiaBean contactologia) {
		
		contactologiaForm.setAccion("verGraduacion");
		contactologiaForm.setFecha_graduacion(contactologia.getFecha());
		contactologiaForm.setNumero_graduacion(String.valueOf(contactologia.getNumero()));
		contactologiaDispatch.ingresaContactologia(contactologiaForm, sess);	
		
		Optional<String> a =  Optional.ofNullable(contactologiaForm.getFecha_pedido());
		if (!a.isPresent()) contactologiaForm.setFecha_pedido("");
		
		Optional<String> b =  Optional.ofNullable(contactologiaForm.getFecha_recepcion());
		if (!b.isPresent()) contactologiaForm.setFecha_recepcion("");
		
		Optional<String> c =  Optional.ofNullable(contactologiaForm.getFecha_entrega());
		if (!c.isPresent()) contactologiaForm.setFecha_entrega("");
		
		Optional<String> d =  Optional.ofNullable(contactologiaForm.getFecha_caducidad());
		if (!d.isPresent()) contactologiaForm.setFecha_caducidad("");
		
		
		try {
			fechaEncargo = dt.parse(contactologiaForm.getFecha_pedido());						
		} catch (ParseException e) {			
			e.printStackTrace();
		}		
		try {
			fechaRecepcion = dt.parse(contactologiaForm.getFecha_recepcion());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		try {
			fechaEntrega = dt.parse(contactologiaForm.getFecha_entrega());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		try {
			fechaCaducidad = dt.parse(contactologiaForm.getFecha_caducidad());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		
	}
	
	public boolean validaInfoContactologia(){
		
		String radio_1D = contactologiaForm.getO_radio1();
		String radio_1I = contactologiaForm.getI_radio1();		
		String esfera_D= contactologiaForm.getO_esfera();
		String esfera_I= contactologiaForm.getI_esfera();
		String cilindro_D= contactologiaForm.getO_cilindro();
		String cilindro_I= contactologiaForm.getI_cilindro();
		String diamt_D= contactologiaForm.getO_diamt();
		String diamt_I= contactologiaForm.getI_diamt();
		String eje_D= contactologiaForm.getO_eje();
		String eje_I= contactologiaForm.getI_eje();		
		
		Double radio1D = 0.00;
		Double radio1I = 0.00;
		Double esferaD = 0.00;
		Double esferaI = 0.00;	
		Double cilindroD = 0.00;
		Double cilindroI = 0.00;
		Double diamtD = 0.00;
		Double diamtI = 0.00;
		int ejeD = 0;
		int ejeI = 0;
		
		if(!radio_1D.equals("")){
			radio1D = Double.valueOf(radio_1D.trim());
			
			if(radio1D <= 0.00 || radio1D > 99.99){
				Messagebox.show("El valor radio1 derecho est\u00E1 fuera del rango permitido mayor a 0 y menor a 99.99");
				return false;
			}					
		}else{				
			Messagebox.show("Debe ingresar valor en radio 1 derecho");
			return false;	
		}		
			
		
		if(!radio_1I.equals("")){
			radio1I = Double.valueOf(radio_1I.trim());
			
			if(radio1I <= 0.00 || radio1I > 99.99){
				Messagebox.show("El valor radio1 izquierdo est\u00E1 fuera del rango permitido mayor a 0 y menor a 99.99");
				return false;
			}					
		}else{				
			Messagebox.show("Debe ingresar valor en radio 1 izquierdo");
			return false;	
		}
		
					
		if(!esfera_D.equals("")){
			esferaD = Double.valueOf(esfera_D.trim());
			
			if(esferaD < -99.00 || esferaD > 99.00){					
				Messagebox.show("El valor esfera derecha est\u00E1 fuera del rango permitido entre -99 y 99");
				return false;					
			}			
		}else{
			Messagebox.show("Debe ingresar valor en esfera derecha");
			return 	false;
		}	
		
					
		if(!esfera_I.equals("")){
			esferaI = Double.valueOf(esfera_I.trim());
			
			if(esferaI < -99.00 || esferaI > 99.00){					
				Messagebox.show("El valor esfera izquierda est\u00E1 fuera del rango permitido entre -99 y 99");
				return false;					
			}			
		}else{
			Messagebox.show("Debe ingresar valor en esfera izquierda");
			return 	false;
		}		
		
			
		if(!cilindro_D.equals("")){
			cilindroD = Double.valueOf(cilindro_D.trim());
			
			if(cilindroD < -99.00 || cilindroD > 0){					
				Messagebox.show("El valor cilindro derecho est\u00E1 fuera del rango permitido entre -99 y 0");
				return false;				
			}
		}else{
			Messagebox.show("Debe ingresar valor en cilindro derecho");		
			return false;
		}		
		
		
		if(!cilindro_I.equals("")){
			cilindroI = Double.valueOf(cilindro_I.trim());
			
			if(cilindroI < -99.00 || cilindroI > 0){					
				Messagebox.show("El valor cilindro izquierdo est\u00E1 fuera del rango permitido entre -99 y 0");
				return false;				
			}
		}else{
			Messagebox.show("Debe ingresar valor en cilindro izquierdo");		
			return false;
		}		
		
				
		if(cilindroD < 0){				
			if(!eje_D.equals("")){	
				ejeD = Integer.valueOf(eje_D.trim());
				
				if(ejeD < 0 || ejeD > 180){
					Messagebox.show("El valor eje derecho est\u00E1 fuera del rango permitido entre 0 y 180");
					return false;
				}						
			}else{
				Messagebox.show("Debe ingresar eje derecho dentro de los rangos  0 y 180");
				return false;
			}			
		}		
		
					
		if(cilindroI < 0){				
			if(eje_I.equals("")){
				ejeI = Integer.valueOf(eje_I.trim());
				
				if(ejeI < 0 || ejeI > 180){
					Messagebox.show("El valor eje izquierdo est\u00E1 fuera del rango permitido entre 0 y 180");
					return false;
				}						
			}else{
				Messagebox.show("Debe ingresar eje izquierdo dentro de los rangos  0 y 180");
				return false;
			}			
		}		
		
			
		if(!diamt_D.equals("")){	
			diamtD = Double.valueOf(diamt_D.trim());
			
			if(diamtD < 0.00  || diamtD > 30.00){
				Messagebox.show("El valor diametro total derecho est\u00E1 fuera del rango permitido entre 0 y 30");
				return false;
			}				
		}else{
			Messagebox.show("Debe ingresar valor en diametro total derecho");
			return false;
		}			
		
				
		if(!diamt_I.equals("")){	
			diamtI = Double.valueOf(diamt_I.trim());
			
			if(diamtI < 0.00  || diamtI > 30.00){
				Messagebox.show("El valor diametro total izquierdo est\u00E1 fuera del rango permitido entre 0 y 30");
				return false;
			}				
		}else{
			Messagebox.show("Debe ingresar valor en diametro total izquierdo");
			return false;
		}
		
		return true;
	}
	
	
	
	//======Getter and Setter =====================
	//=============================================

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
	
	public ContactologiaForm getContactologiaForm() {
		return contactologiaForm;
	}
	
	public void setContactologiaForm(ContactologiaForm contactologiaForm) {
		this.contactologiaForm = contactologiaForm;
	}

	public Date getFechaEncargo() {
		return fechaEncargo;
	}

	public void setFechaEncargo(Date fechaEncargo) {
		this.fechaEncargo = fechaEncargo;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	
}
