package cl.gmo.pos.venta.controlador;

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
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
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
			contactologiaForm.setExisteContactologia("false");
			//System.out.println("prueba");
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	//==========Operaciones Principales de la clase ================
	//==============================================================
	
	@NotifyChange({"contactologiaForm"})
	@Command
	public void nuevoGraduacion() {
		//document.getElementById("estaGrabado").value=2;
		/*var inputs = document.getElementsByTagName("input");
		var cliente = document.getElementById('cliente');
		var exito = document.getElementById('exito');
		var nombre_cliente = document.getElementById('nombre_cliente');*/
		
		contactologiaForm.setEstaGrabado(2);
		
		contactologiaForm.setO_radio1("");
		contactologiaForm.setO_radio2("");
		contactologiaForm.setO_esfera("");
		contactologiaForm.setO_cilindro("");
		contactologiaForm.setO_eje("");
		contactologiaForm.setO_diamt("");
		contactologiaForm.setO_diaz("");
		contactologiaForm.setO_bandas("");
		contactologiaForm.setO_radio3("");
		contactologiaForm.setO_diamp("");
		contactologiaForm.setO_colo("");
		contactologiaForm.setO_adic("");
			
		contactologiaForm.setI_radio1("");
		contactologiaForm.setI_radio2("");
		contactologiaForm.setI_esfera("");
		contactologiaForm.setI_cilindro("");
		contactologiaForm.setI_eje("");
		contactologiaForm.setI_diamt("");
		contactologiaForm.setI_diaz("");
		contactologiaForm.setI_bandas("");
		contactologiaForm.setI_radio3("");
		contactologiaForm.setI_diamp("");
		contactologiaForm.setI_colo("");
		contactologiaForm.setI_adic("");  
		
		contactologiaForm.setNifdoctor("");
		contactologiaForm.setDvnifdoctor("");
		contactologiaForm.setRecomendaciones("");
		contactologiaForm.setCalculo_opt("");
		contactologiaForm.setLaboratorio("");
		contactologiaForm.setOtro("");
		contactologiaForm.setNombre_doctor("");
		
		contactologiaForm.setExisteContactologia("false");
		contactologiaForm.setGrabar("true");
		
	}
	
	
	@Command
	public void ingresoPresupuesto(@BindingParam("win")Window win ) {
		
		sess.setAttribute("cliente",contactologiaForm.getCliente());
		sess.setAttribute("nombre_cliente",contactologiaForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "contactologia");	
		
		win.detach();
		
		Window winPresup = (Window)Executions.createComponents(
                "/zul/presupuestos/PresupuestoBS.zul", null, objetos);			
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
                "/zul/encargos/encargosBS.zul", null, objetos);			
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
		
		Optional<Date> fcaducidad = Optional.ofNullable(fechaCaducidad);
		Optional<Date> fentrega   = Optional.ofNullable(fechaEntrega);
		Optional<Date> fencargo   = Optional.ofNullable(fechaEncargo);
		Optional<Date> frecepcion = Optional.ofNullable(fechaRecepcion);
		
		if (fcaducidad.isPresent())		
			contactologiaForm.setFecha_caducidad(dt.format(fechaCaducidad));
		else
			contactologiaForm.setFecha_caducidad("");
		
		if (fentrega.isPresent())		
			contactologiaForm.setFecha_entrega(dt.format(fechaEntrega));
		else
			contactologiaForm.setFecha_entrega("");
		
		if(fencargo.isPresent())
			contactologiaForm.setFecha_pedido(dt.format(fechaEncargo));
		else
			contactologiaForm.setFecha_pedido("");
		
		if(frecepcion.isPresent())
			contactologiaForm.setFecha_recepcion(dt.format(fechaRecepcion));
		else
			contactologiaForm.setFecha_recepcion("");
		
		doctor = contactologiaForm.getDoctor();
		pagina = contactologiaForm.getGrabar();
		existeContactologia = contactologiaForm.getExisteContactologia();		
		
		
		if(existeContactologia.equals("true")) {			
			Messagebox.show("La receta esta asociada a una venta, no puede ser modificada, debe generar una nueva receta");
			return;
		}else {
			
			respuesta= validaInfoContactologia();
			
			if((!pagina.equals("false")) || (!respuesta)) {
				
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
		if (!a.isPresent() || a.get().equals("")) {
			contactologiaForm.setFecha_pedido("");
			fechaEncargo=null;
		}else {
			try {
				fechaEncargo = dt.parse(contactologiaForm.getFecha_pedido());
			} catch (ParseException e) {				
				e.printStackTrace();
			}			
		}
		
		Optional<String> b =  Optional.ofNullable(contactologiaForm.getFecha_recepcion());
		if (!b.isPresent() || b.get().equals("")) {
			contactologiaForm.setFecha_recepcion("");
			fechaRecepcion=null;
		}else {
			try {
				fechaRecepcion = dt.parse(contactologiaForm.getFecha_recepcion());
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		Optional<String> c =  Optional.ofNullable(contactologiaForm.getFecha_entrega());
		if (!c.isPresent() || c.get().equals("")) {
			contactologiaForm.setFecha_entrega("");
			fechaEntrega=null;
		}else {
			try {
				fechaEntrega = dt.parse(contactologiaForm.getFecha_entrega());
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}
		
		Optional<String> d =  Optional.ofNullable(contactologiaForm.getFecha_caducidad());
		if (!d.isPresent() || d.get().equals("")) {
			contactologiaForm.setFecha_caducidad("");
			fechaCaducidad=null;
		}else {
			try {
				fechaCaducidad = dt.parse(contactologiaForm.getFecha_caducidad());
			} catch (ParseException e) {				
				e.printStackTrace();
			}
		}		
		
	}
	
	@NotifyChange({"contactologiaForm"})
	@Command
	public void buscarDoctorAjax()
    {        	
		Optional<String> nif = Optional.ofNullable(contactologiaForm.getNifdoctor());		
		
    	String nifdoctor = nif.orElse("");
    	
    	if(!nifdoctor.equals("")){    		
    		
    		sess.setAttribute("nifdoctor", nifdoctor);
    		contactologiaDispatch.buscarDoctorAjax(contactologiaForm, sess);
    		
    		if (contactologiaForm.getNifdoctor().equals("")) {    		
    			Messagebox.show("El doctor con rut "+nifdoctor+" no existe");
    		}   		
    		
    	}else{
    		Messagebox.show("Debe ingrese rut de doctor.");
    	}	
    }
	
	
	@NotifyChange({"contactologiaForm"})
	@Command
	public void buscarMedico() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();
		objetos.put("retorno", "seleccionaMedicoContactologia");
		
		Window winBuscarMedico = (Window)Executions.createComponents(
                "/zul/mantenedores/BusquedaMedico.zul", null, objetos);
		
		winBuscarMedico.doModal(); 		
	}
	
	@NotifyChange({"contactologiaForm"})
	@GlobalCommand
	public void seleccionaMedicoContactologia(@BindingParam("medico")OftalmologoBean medico) {
		
		contactologiaForm.setNifdoctor(medico.getNif());
		contactologiaForm.setDvnifdoctor(medico.getLnif());
		contactologiaForm.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
		//contactologiaForm.setCod_doctor(medico.getCodigo());
		contactologiaForm.setDoctor(medico.getCodigo());
			
	}
	
	
	//======validaciones  varias=================
	//=============================================
	
	public boolean validaInfoContactologia(){
		
		String radio_1D = contactologiaForm.getO_radio1().trim();
		String radio_1I = contactologiaForm.getI_radio1().trim();		
		String esfera_D= contactologiaForm.getO_esfera().trim();
		String esfera_I= contactologiaForm.getI_esfera().trim();
		String cilindro_D= contactologiaForm.getO_cilindro().trim();
		String cilindro_I= contactologiaForm.getI_cilindro().trim();
		String diamt_D= contactologiaForm.getO_diamt().trim();
		String diamt_I= contactologiaForm.getI_diamt().trim();
		String eje_D= contactologiaForm.getO_eje().trim();
		String eje_I= contactologiaForm.getI_eje().trim();		
		
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
		
		
		//Conversiones
		try {
			radio1D = Double.parseDouble(radio_1D);
		}catch (Exception e) {
			radio1D = 0.0;
		}
		
		try {
			radio1I = Double.parseDouble(radio_1I);
		}catch (Exception e) {
			radio1I = 0.0;
		}
		
		try {
			esferaD = Double.parseDouble(esfera_D);
		}catch (Exception e) {
			esferaD = 0.0;
		}
		
		try {
			esferaI = Double.parseDouble(esfera_I);
		}catch (Exception e) {
			esferaI = 0.0;
		}
		
		try {
			cilindroD = Double.parseDouble(cilindro_D);
		}catch (Exception e) {
			cilindroD = 0.0;
		}
		
		try {
			cilindroI = Double.parseDouble(cilindro_I);
		}catch (Exception e) {
			cilindroI = 0.0;
		}
		
		try {
			diamtD = Double.parseDouble(diamt_D);
		}catch (Exception e) {
			diamtD = 0.0;
		}
		
		try {
			diamtI = Double.parseDouble(diamt_I);
		}catch (Exception e) {
			diamtI = 0.0;
		}
		
		try {
			ejeD = Integer.parseInt(eje_D);
		}catch (Exception e) {
			ejeD = 0;
		}
		
		try {
			ejeI = Integer.parseInt(eje_I);
		}catch (Exception e) {
			ejeI = 0;
		}
		
		
		//Validaciones
		
		if(!radio_1D.equals("")){
			//radio1D = Double.valueOf(radio_1D.trim());
			
			if(radio1D <= 0.00 || radio1D > 99.99){
				Messagebox.show("El valor radio1 derecho est\u00E1 fuera del rango permitido mayor a 0 y menor a 99.99");
				return false;
			}					
		}else{				
			Messagebox.show("Debe ingresar valor en radio 1 derecho");
			return false;	
		}		
			
		
		if(!radio_1I.equals("")){
			//radio1I = Double.valueOf(radio_1I.trim());
			
			if(radio1I <= 0.00 || radio1I > 99.99){
				Messagebox.show("El valor radio1 izquierdo est\u00E1 fuera del rango permitido mayor a 0 y menor a 99.99");
				return false;
			}					
		}else{				
			Messagebox.show("Debe ingresar valor en radio 1 izquierdo");
			return false;	
		}
		
					
		if(!esfera_D.equals("")){
			//esferaD = Double.valueOf(esfera_D.trim());
			
			if(esferaD < -99.00 || esferaD > 99.00){					
				Messagebox.show("El valor esfera derecha est\u00E1 fuera del rango permitido entre -99 y 99");
				return false;					
			}			
		}else{
			Messagebox.show("Debe ingresar valor en esfera derecha");
			return 	false;
		}	
		
					
		if(!esfera_I.equals("")){
			//esferaI = Double.valueOf(esfera_I.trim());
			
			if(esferaI < -99.00 || esferaI > 99.00){					
				Messagebox.show("El valor esfera izquierda est\u00E1 fuera del rango permitido entre -99 y 99");
				return false;					
			}			
		}else{
			Messagebox.show("Debe ingresar valor en esfera izquierda");
			return 	false;
		}		
		
			
		if(!cilindro_D.equals("")){
			//cilindroD = Double.valueOf(cilindro_D.trim());
			
			if(cilindroD < -99.00 || cilindroD > 0){					
				Messagebox.show("El valor cilindro derecho est\u00E1 fuera del rango permitido entre -99 y 0");
				return false;				
			}
		}else{
			Messagebox.show("Debe ingresar valor en cilindro derecho");		
			return false;
		}		
		
		
		if(!cilindro_I.equals("")){
			//cilindroI = Double.valueOf(cilindro_I.trim());
			
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
				//ejeD = Integer.valueOf(eje_D.trim());
				
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
			if(!eje_I.equals("")){
				//ejeI = Integer.valueOf(eje_I.trim());
				
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
			//diamtD = Double.valueOf(diamt_D.trim());
			
			if(diamtD < 0.00  || diamtD > 30.00){
				Messagebox.show("El valor diametro total derecho est\u00E1 fuera del rango permitido entre 0 y 30");
				return false;
			}				
		}else{
			Messagebox.show("Debe ingresar valor en diametro total derecho");
			return false;
		}			
		
				
		if(!diamt_I.equals("")){	
			//diamtI = Double.valueOf(diamt_I.trim());
			
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
	
	@NotifyChange("*")
	@Command
	public boolean isNumeric(@BindingParam("valor")String valor) {  
		
		boolean bRet=false;
		
		if(valor != null && valor.matches("[-+]?\\d*\\.?\\d+")) {
			bRet=true;
		}else {
			Messagebox.show("No es un valor correcto");
		}
		
	    //return s != null && s.matches("[-+]?\\d*\\.?\\d+");
		return bRet;
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
