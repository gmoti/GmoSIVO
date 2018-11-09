package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.ExecutionParam;
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

import com.google.javascript.jscomp.Var;
import com.google.protobuf.Message;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.GraduacionesDispatchActions;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.PrismaBaseBean;
import cl.gmo.pos.venta.web.beans.PrismaCantidadBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;

public class ControllerGraduacionCliente implements Serializable{	
	
	private static final long serialVersionUID = 4892447350424455478L;
	Session sess = Sessions.getCurrent();	
	
	private String usuario;	
	private String sucursalDes;
	
	GraduacionesForm graduacionesForm ;
	GraduacionesBean graduacionesBean ;
	BusquedaClientesForm busquedaClientesForm;
	
	GraduacionesDispatchActions graduacionesDispatch; 	
	BusquedaClientesDispatchActions busquedaClientesDispatch;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	
	
	private Date fechaEmision;
	private Date fechaProxRevision;
	
	HashMap<String,Object> objetos;
	
	private PrismaCantidadBean prismaCantidadOD;
	private PrismaBaseBean prismaBaseOD;
	private PrismaCantidadBean prismaCantidadOI;
	private PrismaBaseBean prismaBaseOI;
	

	@Init	
	public void inicial(@ExecutionArgParam("origen")String origen,
						@ExecutionArgParam("cliente")ClienteBean cliente) {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		prismaCantidadOD = new PrismaCantidadBean();
		prismaBaseOD = new PrismaBaseBean();
		prismaCantidadOI = new PrismaCantidadBean();
		prismaBaseOI = new PrismaBaseBean();
		
		graduacionesForm = new GraduacionesForm();
		graduacionesBean = new GraduacionesBean();
		busquedaClientesForm = new BusquedaClientesForm();	
		
		graduacionesDispatch = new GraduacionesDispatchActions();
		busquedaClientesDispatch = new BusquedaClientesDispatchActions();	
		
		Optional<String> o = Optional.ofNullable(origen);
		if(!o.isPresent()) {
			origen="menu";
			cliente = new ClienteBean();
		} 
		
		
		if (origen.equals("cliente")) {			
			graduacionesForm.setCliente(Integer.parseInt(cliente.getCodigo()));
			graduacionesForm.setNombre(cliente.getNombre());
			graduacionesForm.setApellido(cliente.getApellido());
			
			graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
		}else {
			graduacionesForm.setCliente(0);
			graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
			
			graduacionesForm.setOD_cantidad("-1");
			graduacionesForm.setOI_cantidad("-1");
			
			graduacionesForm.setOD_base("Seleccione");
			graduacionesForm.setOI_base("Seleccione");		
			
		}	
		
		posicionaCombo();
		
	}
	
	//==========Operaciones Principales de la clase ================
	//==============================================================
	@NotifyChange({"graduacionesForm"})
	@Command
	public void nuevaGraduacion() {
		
		//solo limpia los campos visualmente
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void ingresoPresupuesto(@BindingParam("win")Window win) {
		
		sess.setAttribute("cliente",graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente",graduacionesForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "graduacion");	
		
		win.detach();
		
		Window winPresup = (Window)Executions.createComponents(
                "/zul/presupuestos/presupuesto.zul", null, objetos);			
		winPresup.doModal();
		
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void ingresoEncargo(@BindingParam("win")Window win) {
		
		sess.setAttribute("cliente",graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente",graduacionesForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "graduacion");	
		
		win.detach();
		
		Window winEncargo = (Window)Executions.createComponents(
                "/zul/encargos/encargos.zul", null, objetos);			
		winEncargo.doModal();
		
	}
	
	@Command
	public void ingresoContactologia(){
		
		sess.setAttribute(Constantes.STRING_CLIENTE, graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente", graduacionesForm.getNombre_cliente());
		
		Window winContactologia = (Window)Executions.createComponents(
                "/zul/mantenedores/Contactologia.zul", null, null);
		
		winContactologia.doModal();			
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void insertarGraduacion(){
		
		String pagina="";
		String existe_graduacion="";
		boolean respuesta1=false;
		boolean respuesta=true;
		boolean check=false;
		boolean pasaValidacionADD = true;
		
		String OD_esfera = "";
		String OD_cilindro = "";
		String OD_eje = "";
		String OD_cerca = ""; 
		String OD_adicion = "";
		String OD_dnpl = "";
		String OD_dnpc = "";
		
		String OI_esfera = "";
		String OI_cilindro = "";
		String OI_eje = "";
		String OI_cerca = ""; 
		String OI_adicion = "";
		String OI_dnpl = "";
		String OI_dnpc = "";
		
		String mensaje ="";
		String mensaje1="";
		String mensaje2="";
		String mensaje3="";
		String mensaje4="";
		String mensaje5="";
		
		graduacionesForm.setAccion("insertarGraduacion");
		pagina = graduacionesForm.getPagina();
		existe_graduacion = graduacionesForm.getExiste_graduacion();		
		
		respuesta1 = validaInformacion();
		respuesta  = true;
		
		if(respuesta1){
			respuesta  = validaGraduacion();
		}
		
		//valida diferente adicion
		check = graduacionesForm.isDiferenteAdd();		
		pasaValidacionADD = true;
		
		if (!check) {
			if (!graduacionesForm.getOI_adicion().equals("") || !graduacionesForm.getOD_adicion().equals("")) {
				if (graduacionesForm.getOI_adicion().equals(graduacionesForm.getOD_adicion()))
				{
					pasaValidacionADD = false;
				}
			}
			
		}
		
		
		if (pasaValidacionADD) {
			if(existe_graduacion.equals("true")){	
				
				if(respuesta && respuesta1){
					if("NOGRABAR" != pagina){
						
						OD_esfera = graduacionesForm.getOD_esfera();
						OD_cilindro = graduacionesForm.getOD_cilindro();
						OD_eje = graduacionesForm.getOD_eje();
						OD_cerca = graduacionesForm.getOD_cerca(); 
						OD_adicion = graduacionesForm.getOD_adicion();
						OD_dnpl = graduacionesForm.getOD_dnpl();
						OD_dnpc = graduacionesForm.getOD_dnpc();
						
						if(OD_eje.equals("")){
							OD_eje="      ";
						}
						if(OD_cerca.equals("")){
							OD_cerca="       ";
						}
						if(OD_adicion.equals("")){
							OD_adicion="       ";
						}
						if(OD_dnpl.equals("")){
							OD_dnpl="       ";
						}
						if(OD_dnpc.equals("")){
							OD_dnpc="       ";
						}
						
						OI_esfera = graduacionesForm.getOI_esfera();
						OI_cilindro = graduacionesForm.getOI_cilindro();
						OI_eje = graduacionesForm.getOI_eje();
						OI_cerca = graduacionesForm.getOI_cerca(); 
						OI_adicion = graduacionesForm.getOI_adicion();
						OI_dnpl = graduacionesForm.getOI_dnpl();
						OI_dnpc = graduacionesForm.getOI_dnpc();
						
						if(OI_eje.equals("")){
							OI_eje="      ";
						}
						if(OI_cerca.equals("")){
							OI_cerca="       ";
						}
						if(OI_adicion.equals("")){
							OI_adicion="       ";
						}
						if(OI_dnpl.equals("")){
							OI_dnpl="       ";
						}
						if(OI_dnpc.equals("")){
							OI_dnpc="       ";
						}						
						
						mensaje1 = "Estos son los datos de la receta registrados:    \n ";
						mensaje2 = "  Ojo   Esf         Cil       Eje       Cerca         Add         DNPL         DNPC  \n ";
						mensaje3 = "   D     "+OD_esfera+"          "+OD_cilindro+"        "+OD_eje+"          "+OD_cerca+"             "+OD_adicion+"          "+OD_dnpl+"           "+OD_dnpc+"\n ";
						mensaje4 = "    I      "+OI_esfera+"          "+OI_cilindro+"        "+OI_eje+"          "+OI_cerca+"             "+OI_adicion+"          "+OI_dnpl+"           "+OI_dnpc+"\n ";
						
						mensaje = mensaje1 + mensaje2 + mensaje3 + mensaje4;						
						
						Messagebox.show(mensaje, "seguro(a) que esta correctos?",
								Messagebox.YES | 
								Messagebox.CANCEL, 
								Messagebox.QUESTION, new EventListener<Event>() {			
							@Override
							public void onEvent(Event e) throws Exception {	
								
									if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {								
										
										graduacionesDispatch.IngresaGraduacion(graduacionesForm, sess);
									}						
								}
						});	
						
					
					}else if(graduacionesForm.getPagina().equals("NOGRABAR")){
						
						Messagebox.show("Desea modificar la receta ?", "Modificar",
								Messagebox.YES | 
								Messagebox.CANCEL, 
								Messagebox.QUESTION, new EventListener<Event>() {			
							@Override
							public void onEvent(Event e) throws Exception {	
								
									if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {										
										
										String OD_esfera = graduacionesForm.getOD_esfera();
										String OD_cilindro = graduacionesForm.getOD_cilindro();
										String OD_eje = graduacionesForm.getOD_eje();
										String OD_cerca = graduacionesForm.getOD_cerca(); 
										String OD_adicion = graduacionesForm.getOD_adicion();
										String OD_dnpl = graduacionesForm.getOD_dnpl();
										String OD_dnpc = graduacionesForm.getOD_dnpc();
										
										if(OD_eje.equals("")){
											OD_eje="      ";
										}
										if(OD_cerca.equals("")){
											OD_cerca="       ";
										}
										if(OD_adicion.equals("")){
											OD_adicion="       ";
										}
										if(OD_dnpl.equals("")){
											OD_dnpl="       ";
										}
										if(OD_dnpc.equals("")){
											OD_dnpc="       ";
										}
										
										String OI_esfera = graduacionesForm.getOI_esfera();
										String OI_cilindro = graduacionesForm.getOI_cilindro();
										String OI_eje = graduacionesForm.getOI_eje();
										String OI_cerca = graduacionesForm.getOI_cerca(); 
										String OI_adicion = graduacionesForm.getOI_adicion();
										String OI_dnpl = graduacionesForm.getOI_dnpl();
										String OI_dnpc = graduacionesForm.getOI_dnpc();
										
										if(OI_eje.equals("")){
											OI_eje="      ";
										}
										if(OI_cerca.equals("")){
											OI_cerca="       ";
										}
										if(OI_adicion.equals("")){
											OI_adicion="       ";
										}
										if(OI_dnpl.equals("")){
											OI_dnpl="       ";
										}
										if(OI_dnpc.equals("")){
											OI_dnpc="       ";
										}
										
										//segunda pregunta
										
										String mensaje1 = "Estos son los datos de la receta registrados:    \n ";
										String mensaje2 = "  Ojo   Esf         Cil       Eje       Cerca         Add         DNPL         DNPC  \n ";
										String mensaje3 = "   D     "+OD_esfera+"          "+OD_cilindro+"        "+OD_eje+"          "+OD_cerca+"             "+OD_adicion+"          "+OD_dnpl+"           "+OD_dnpc+"\n ";
										String mensaje4 = "    I      "+OI_esfera+"          "+OI_cilindro+"        "+OI_eje+"          "+OI_cerca+"             "+OI_adicion+"          "+OI_dnpl+"           "+OI_dnpc+"\n ";
										
										String mensaje = mensaje1 + mensaje2 + mensaje3 + mensaje4;
										
										Messagebox.show(mensaje, "seguro(a) que esta correctos?",
												Messagebox.YES | 
												Messagebox.CANCEL, 
												Messagebox.QUESTION, new EventListener<Event>() {			
											@Override
											public void onEvent(Event e) throws Exception {	
												
													if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {		
														graduacionesForm.setAccion("modificarGraduacion");
														graduacionesDispatch.IngresaGraduacion(graduacionesForm, sess);
													}						
												}
										});	
										
										
										
									}						
								}
						}); //primera pregunta
						
					}
					
				}			
			}else{
				//if("NOGRABAR" == pagina)
				Messagebox.show("La receta esta asociada a una venta, no puede ser modificada, debe generar una nueva receta");
			}
		}
		else{
			Messagebox.show("La ADD para cada Ojo es distinta");
			}		
	}
	
		
	
	@Command
	public void cerrar(@BindingParam("win")  Window win) {
	    win.detach();
	}
	
	
	
	//===================================================
	//=======Metodos secundarios ========================
	//===================================================
	@Command
	public void busquedaCliente() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteGraduacion");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	
	
	@NotifyChange({"graduacionesForm","prismaCantidadOD","prismaBaseOD","prismaCantidadOI","prismaBaseOI","fechaEmision","fechaProxRevision"})
	@GlobalCommand
	public void buscarClienteGraduacion(@BindingParam("cliente")ClienteBean cliente) {
		
		graduacionesForm.setCliente(Integer.parseInt(cliente.getCodigo()));
		graduacionesDispatch.cargaFormulario(graduacionesForm, sess);		
		graduacionesForm.setNombre_cliente(cliente.getNombre()+ " " + cliente.getApellido());
		
		try {
			fechaEmision = dt.parse(graduacionesForm.getFechaEmision());
			fechaProxRevision = dt.parse(graduacionesForm.getFechaProxRevision());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		posicionaCombo();
	}
	
	@NotifyChange({"graduacionesForm","prismaCantidadOD","prismaBaseOD","prismaCantidadOI","prismaBaseOI","fechaEmision","fechaProxRevision"})
	@Command
	public void verGraduacion(@BindingParam("graduacion")GraduacionesBean graduacion)
	{
		graduacionesForm.setAccion("verGraduacion");
		graduacionesForm.setFecha_graduacion(graduacion.getFecha());
		graduacionesForm.setNumero_graduacion(graduacion.getNumero());		
		graduacionesDispatch.IngresaGraduacion(graduacionesForm, sess);		
		
		try {
			fechaEmision = dt.parse(graduacionesForm.getFechaEmision());
			fechaProxRevision = dt.parse(graduacionesForm.getFechaProxRevision());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		
		posicionaCombo();
	}
	
	
	private boolean validaInformacion() {
		
		String nombre_cliente="";	
		String codigo_cliente="";
		String doctor="";
		String agente="";
		
		String OD_cantidad="";
		String OI_cantidad="";
		String OD_base="";
		String OI_base="";
		String fechaEmision="";
	
		nombre_cliente = graduacionesForm.getNombre_cliente().trim();	
		
		if(nombre_cliente.equals("")){
			Messagebox.show("Debe ingresar cliente");
			return false;
		}
		
		codigo_cliente = graduacionesForm.getNombre_cliente().trim();	
		
		if(codigo_cliente.equals("") || codigo_cliente.equals("0")){
			
			if(codigo_cliente.equals("0")){
				Messagebox.show("Debe buscar un cliente");
			}else{
				Messagebox.show("Debe ingresar un cliente");
			}
			
			return false;
		}
		
		doctor = graduacionesForm.getDoctor();
		if(doctor.equals("")){
			Messagebox.show("Debe ingresar un doctor");
			return false;		
		}
		
		agente = graduacionesForm.getAgente();
		if(graduacionesForm.getAgente().equals("-1")){
			Messagebox.show("Debe Seleccionar agente");
			return false;		
		}
		
		fechaEmision = graduacionesForm.getFechaEmision().trim();		
		
		if(fechaEmision.equals("")){
			Messagebox.show("Debe ingresar fecha emision");
			return false;
		}
		
		OD_cantidad = graduacionesForm.getOD_cantidad().trim();		
		
		if(!OD_cantidad.equals("")  && !OD_cantidad.equals("-1") ){
			
			OD_base = graduacionesForm.getOD_base().trim();
			
			if(OD_base.equals("")  || OD_base.equals("Seleccione")){				
				Messagebox.show("Debe seleccionar Base de Prisma Derecho");
				return false;
			}
			
		}
		
		OI_cantidad = graduacionesForm.getOI_cantidad().trim();
		
		if(!OI_cantidad.equals("")  && !OI_cantidad.equals("-1") ){
			
			OI_base = graduacionesForm.getOI_base().trim();
			
			if(OI_base.equals("")  || OI_base.equals("Seleccione")){
				Messagebox.show("Debe seleccionar Base de Prisma Izquierdo");
				return false;
			}
			
		}
		
		OD_base = graduacionesForm.getOD_base().trim();
		
		if(!OD_base.equals("")  &&  !OD_base.equals("Seleccione")){
			
			OD_cantidad = graduacionesForm.getOD_cantidad().trim();	
			
			if(OD_cantidad.equals("")  || OD_cantidad.equals("-1") ){
				
				Messagebox.show("Debe seleccionar Cantidad de Prisma Derecho");
				return false;
			}
		}
		
		OI_base = graduacionesForm.getOI_base().trim();
		
		if(!OI_base.equals("") && !OI_base.equals("Seleccione")){
			
			OI_cantidad = graduacionesForm.getOI_cantidad().trim();
			
			if(OI_cantidad.equals("") || OI_cantidad.equals("-1") ){
				
				Messagebox.show("Debe seleccionar Cantidad de Prisma Izquierdo");
				return false;
			}
		}	
		
		return true;
	}
	
	
	private boolean validaGraduacion(){
		
		String esferaD="";
		String cilindroD="";
		String ejeD="";
		String adicionD="";
		String OD_dnpl="";
		
		String esferaI = "";				
		String cilindroI = "";			
		String ejeI = "";		
		String adicionI = "";		
		String OI_dnpl = "";	
		
		boolean respuesta=false;
		boolean respuestadnpc=false;		
		
		/********* VALIDACIONES OJO DERECHO ********/
		esferaD = graduacionesForm.getOD_esfera().trim();		
		cilindroD = graduacionesForm.getOD_cilindro().trim();		
		ejeD = graduacionesForm.getOD_eje().trim();			
		adicionD = graduacionesForm.getOD_adicion().trim();		
		OD_dnpl = graduacionesForm.getOD_dnpl().trim();		
		
		if(!esferaD.equals("") && !esferaD.equals(null)){
			
			int esfera_D = Integer.parseInt(esferaD);
			
			if(Integer.parseInt(esferaD) < -30 || Integer.parseInt(esferaD) >30){
				Messagebox.show("El valor esfera derecha esta fuera del rango permitido -30 y 30");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar esfera derecha.");
			return false;
		}	
		
		
		if(!cilindroD.equals("") && !cilindroD.equals(null)){		
			
			if(Integer.parseInt(cilindroD) < -8  || Integer.parseInt(cilindroD) > 8){
				Messagebox.show("El valor cilindro derecho esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro derecho");
			return false;
		}
		
		
		int intCilindro = Integer.parseInt(cilindroD);
		
		if((!cilindroD.equals("") && !cilindroD.equals(null)) && (intCilindro != 0)){
			if(ejeD.equals("") || ejeD.equals(null)){
				Messagebox.show("Debe ingresar eje derecho");
				return false;
			}
		}		
		
		if(!ejeD.equals("") && !ejeD.equals(null)){
			if(cilindroD.equals("") || cilindroD.equals(null)){
				Messagebox.show("Debe ingresar cilindro derecho");
				return false;
			}
		}	
		
		if(!ejeD.equals("") && !ejeD.equals(null)){
			if(Integer.parseInt(ejeD) < 0 || Integer.parseInt(ejeD) >180){
				Messagebox.show("El valor del eje derecho esta fuera de rango");
				return false;
			}
		}
		
		
		if(!adicionD.equals("") && !adicionD.equals(null)){
			if(Float.valueOf(adicionD) < 0.50 || Float.valueOf(adicionD) > 4){
				Messagebox.show("El valor de la adicion derecha esta fuera de rango");
				return false;
			}
		}
		
		if(OD_dnpl.equals("") || OD_dnpl.equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar derecha.");
			return false;
		}else{
			
			respuesta = validaDNPL(graduacionesForm.getOD_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}
		
		/********* FIN VALIDACIONES OJO DERECHO ********/
		
		
		
		/********* VALIDACIONES OJO IZQUIERDO ********/
		
		esferaI = graduacionesForm.getOI_esfera().trim();		
		cilindroI = graduacionesForm.getOI_cilindro().trim();		
		ejeI = graduacionesForm.getOI_eje().trim();			
		adicionI = graduacionesForm.getOI_adicion().trim();		
		OI_dnpl = graduacionesForm.getOI_dnpl().trim();		
		
		if(!esferaI.equals("") && !esferaI.equals(null)){
			if(Integer.parseInt(esferaI) < -30 || Integer.parseInt(esferaI) >30){
				Messagebox.show("El valor esfera izquierda esta fuera de rango");
				return false;
			}	
		}else{
			Messagebox.show("Debe ingresar esfera izquierda.");
			return false;
		}
		
		if(!cilindroI.equals("") && !cilindroI.equals(null)){		
			if(Integer.parseInt(cilindroI) < -8  || Integer.parseInt(cilindroI) > 8){
				Messagebox.show("El valor cilindro izquierdo esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro izquierdo");
			return false;
		}
		
		if(!cilindroI.equals("") && !cilindroI.equals(null) && (Integer.parseInt(cilindroI)!=0)){
			if(ejeI.equals("") || ejeI.equals(null)){
				Messagebox.show("Debe ingresar eje izquierdo");
				return false;
			}
		}	
		
		if(!ejeI.equals("") && !ejeI.equals(null)){
			if(cilindroI.equals("") || cilindroI.equals(null)){
				Messagebox.show("Debe ingresar cilindro izquierdo");
				return false;
			}
		}
		
		if(!ejeI.equals("") && !ejeI.equals(null)){
			if(Integer.parseInt(ejeI) < 0 || Integer.parseInt(ejeI) >180){
				Messagebox.show("El valor del eje izquierdo esta fuera de rango");
				return false;
			}
		}
		
		
		if(!adicionI.equals("") && !adicionI.equals(null)){
			if(Integer.parseInt(adicionI) <= 0 || Integer.parseInt(adicionI) > 4){
				Messagebox.show("El valor de la adicion izquierda esta fuera de rango");
				return false;
			}
		}
		
		if(OI_dnpl.equals("") || OI_dnpl.equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar izquierda.");
			return false;
		}else{
			 respuesta = validaDNPL(graduacionesForm.getOI_dnpl(), "izquierda");
			if(respuesta == false){
				return false;
			}
		}
		
		respuestadnpc = validaDNPC(graduacionesForm.getOD_dnpl(), "derecha");
		
		if(false){
			return false;
		}
		
		/********* FIN VALIDACIONES OJO IZQUIERDO ********/		
		
		String fechaProxRevision = graduacionesForm.getFechaProxRevision().trim();
		String fechaEmision = graduacionesForm.getFechaEmision().trim();			
		
		if(fechaEmision.equals("")){
			Messagebox.show("Debe ingresar fecha de emision");
			return false;
		}		
		
		if(fechaProxRevision.equals("")){
			Messagebox.show("Debe ingresar fecha de revision");
			return false;
		}
		
		return true;
}
	
	private boolean validaDNPL(String elemento, String lado){
		
		String dnpl = elemento.trim();
		String adicion="";
		Float res = Float.valueOf("0.00");
		
		if(!dnpl.equals("")){
			
			if(Integer.parseInt(dnpl) >= 20 && Integer.parseInt(dnpl) <= 40){
				
				dnpl = elemento;
				
				if(lado.equals("derecha")){	
					
					res = Float.valueOf(dnpl) - 1;					
					adicion = graduacionesForm.getOD_adicion();
					
					if(!adicion.equals("")){						
						graduacionesForm.setOD_dnpc(res.toString());
					}else{
						graduacionesForm.setOD_dnpc("");
					}
					
					elemento = dnpl;
					return true;
					
				}else if(lado.equals("izquierda")){
					
					res = Float.valueOf(dnpl) - 1;					
					adicion = graduacionesForm.getOI_adicion();
					
					if(!adicion.equals("")){						
						graduacionesForm.setOI_dnpc(res.toString());
					}else{
						graduacionesForm.setOI_dnpc("");
					}			
					
					elemento = dnpl;
					return true;	
				}
			}else{			
				Messagebox.show("Distancia Naso pupilar, valor esta fuera de rango");
				return false;	
			}
		}else{		
			if(lado.equals("derecha")){				
				graduacionesForm.setOD_dnpc("");
				
			}else if(lado.equals("izquierda")){
				graduacionesForm.setOI_dnpc("");
			}
			
			return false;
		}	
		
		return false;
			
	}

	private boolean validaDNPC(String elemento, String lado){
		
		String dnpc = elemento.trim();	
		String adicion="";
		
		if(!dnpc.equals("")){
			if(Integer.parseInt(dnpc) >= 20 && Integer.parseInt(dnpc) <= 40){
			
				if(lado.equals("derecha")){			
					adicion = graduacionesForm.getOD_adicion().trim();					
					
					if(adicion.equals("")){
						Messagebox.show("Debe ingresar receta de cerca");						
						graduacionesForm.setOD_dnpc("");
						return false;
					}
					
				}else if(lado.equals("izquierda")){
					adicion = graduacionesForm.getOI_adicion().trim();					
					
					if(adicion.equals("")){
						Messagebox.show("Debe ingresar receta de cerca");
						graduacionesForm.setOD_dnpc("");
						return false;
					}
				}		
			
			}else{
				Messagebox.show("Distancia Naso pupilar cerca, valor esta fuera de rango");
				return false;	
			}
		}	
		return true;
	}
	
	@NotifyChange({"graduacionesForm","prismaCantidadOD","prismaBaseOD","prismaCantidadOI","prismaBaseOI"})
	public void posicionaCombo() {	
		
		Optional<String> CantidadOD = Optional.ofNullable(graduacionesForm.getOD_cantidad());
		if(!CantidadOD.isPresent()) 
			graduacionesForm.setOD_cantidad("");
		
		Optional<String> CantidadOI = Optional.ofNullable(graduacionesForm.getOI_cantidad());
		if(!CantidadOI.isPresent()) 
			graduacionesForm.setOI_cantidad("");
		
		Optional<String> BaseOD = Optional.ofNullable(graduacionesForm.getOD_base());
		if(!BaseOD.isPresent()) 
			graduacionesForm.setOD_base("");
		
		Optional<String> BaseOI = Optional.ofNullable(graduacionesForm.getOI_base());
		if(!BaseOI.isPresent()) 
			graduacionesForm.setOI_base("");
		
		
		Optional<PrismaCantidadBean> a = graduacionesForm.getListaCantidadOD()
				.stream().filter(s ->graduacionesForm.getOD_cantidad().equals(String.valueOf(s.getCodigo()))).findFirst();		
						
			if (a.isPresent()) prismaCantidadOD = a.get(); else prismaCantidadOD=null;
		
		Optional<PrismaCantidadBean> b = graduacionesForm.getListaCantidadOI()
				.stream().filter(s ->graduacionesForm.getOI_cantidad().equals(String.valueOf(s.getCodigo()))).findFirst();		
						
			if (b.isPresent()) prismaCantidadOI = b.get(); else prismaCantidadOI=null;
		
			
		Optional<PrismaBaseBean> c = graduacionesForm.getListaBaseOD()				
				.stream().filter(s ->graduacionesForm.getOD_base().equals(s.getDescripcion())).findFirst();		
						
			if (c.isPresent()) prismaBaseOD = c.get(); else prismaBaseOD=null;
		
		Optional<PrismaBaseBean> d = graduacionesForm.getListaBaseOI()				
				.stream().filter(s ->graduacionesForm.getOI_base().equals(s.getDescripcion())).findFirst();		
						
			if (d.isPresent()) prismaBaseOI = d.get(); else prismaBaseOI=null;		
	}
	
	
	
	
	
	
	
	
	// Getter and Setter ================================
	// ==================================================

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
	
	public GraduacionesForm getGraduacionesForm() {
		return graduacionesForm;
	}
	
	public void setGraduacionesForm(GraduacionesForm graduacionesForm) {
		this.graduacionesForm = graduacionesForm;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaProxRevision() {
		return fechaProxRevision;
	}

	public void setFechaProxRevision(Date fechaProxRevision) {
		this.fechaProxRevision = fechaProxRevision;
	}

	public PrismaCantidadBean getPrismaCantidadOD() {
		return prismaCantidadOD;
	}

	public void setPrismaCantidadOD(PrismaCantidadBean prismaCantidadOD) {
		this.prismaCantidadOD = prismaCantidadOD;
	}

	public PrismaBaseBean getPrismaBaseOD() {
		return prismaBaseOD;
	}

	public void setPrismaBaseOD(PrismaBaseBean prismaBaseOD) {
		this.prismaBaseOD = prismaBaseOD;
	}

	public PrismaCantidadBean getPrismaCantidadOI() {
		return prismaCantidadOI;
	}

	public void setPrismaCantidadOI(PrismaCantidadBean prismaCantidadOI) {
		this.prismaCantidadOI = prismaCantidadOI;
	}

	public PrismaBaseBean getPrismaBaseOI() {
		return prismaBaseOI;
	}

	public void setPrismaBaseOI(PrismaBaseBean prismaBaseOI) {
		this.prismaBaseOI = prismaBaseOI;
	}
	
	
	
}
