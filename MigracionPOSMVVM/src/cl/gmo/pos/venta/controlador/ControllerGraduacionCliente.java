package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.validador.BeanGraduaciones;
import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.GraduacionesDispatchActions;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
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
	//BeanGraduaciones beanGraduaciones;
	
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
	private AgenteBean agenteBean;
	
	String regexp1 = "^-?(([0-9]{1,2})+(?:[.][0-9]{0,2})+)?$";
	String regexp2 = "^([0-9]{1,3})?$";
	
	NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
	DecimalFormat df = new DecimalFormat("0.00"); 
	//DecimalFormat df = (DecimalFormat)nf;
	//nf.setRoundingMode(roundingMode);
	
	
	
	@Init	
	public void inicial(@ExecutionArgParam("origen")String origen,
						@ExecutionArgParam("cliente")ClienteBean cliente) {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		prismaCantidadOD = new PrismaCantidadBean();
		prismaBaseOD = new PrismaBaseBean();
		prismaCantidadOI = new PrismaCantidadBean();
		prismaBaseOI = new PrismaBaseBean();
		agenteBean = new AgenteBean();
		
		graduacionesForm = new GraduacionesForm();
		graduacionesBean = new GraduacionesBean();
		busquedaClientesForm = new BusquedaClientesForm();	
		//beanGraduaciones = new BeanGraduaciones();
		
		graduacionesDispatch = new GraduacionesDispatchActions();
		busquedaClientesDispatch = new BusquedaClientesDispatchActions();	
		
		fechaEmision = new Date(System.currentTimeMillis());
		fechaProxRevision = new Date(System.currentTimeMillis());
		
		Optional<String> o = Optional.ofNullable(origen);
		if(!o.isPresent()) {
			origen="menu";
			cliente = new ClienteBean();
		} 
		
		
		if (origen.equals("cliente") && !cliente.getCodigo().equals("0")) {			
			graduacionesForm.setCliente(Integer.parseInt(cliente.getCodigo()));
			graduacionesForm.setNombre(cliente.getNombre());
			graduacionesForm.setApellido(cliente.getApellido());			
			graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
			
			if (graduacionesForm.getPagina().equals("NOGRABAR")) {			
				
				//graduacionesBean.setFecha(graduacionesForm.getFecha_graduacion());
				//graduacionesBean.setNumero((int)graduacionesForm.getNumero_graduacion());
				
				verGraduacion(graduacionesBean,0);
				//verGraduacion(0);
			}else {
				
				graduacionesForm.setNifdoctor("");
				graduacionesForm.setDvnifdoctor("");
				graduacionesForm.setNombre_doctor("");
			
				graduacionesForm.setOD_cantidad("-1");
				graduacionesForm.setOI_cantidad("-1");
				
				graduacionesForm.setOD_base("Seleccione");
				graduacionesForm.setOI_base("Seleccione");		
				graduacionesForm.setAgente("Seleccione");
				posicionaCombo();
			}
			
			
		}else {
			graduacionesForm.setCliente(0);
			graduacionesDispatch.cargaFormulario(graduacionesForm, sess);	
			
			graduacionesForm.setNifdoctor("");
			graduacionesForm.setDvnifdoctor("");
			graduacionesForm.setNombre_doctor("");
		
			graduacionesForm.setOD_cantidad("-1");
			graduacionesForm.setOI_cantidad("-1");
			
			graduacionesForm.setOD_base("Seleccione");
			graduacionesForm.setOI_base("Seleccione");		
			graduacionesForm.setAgente("Seleccione");
			
			posicionaCombo();
		}			
		
		
				
	}
	
	//==========Operaciones Principales de la clase ================
	//==============================================================
	//@NotifyChange({"graduacionesForm","beanGraduaciones","fechaEmision","fechaProxRevision","agenteBean"})
	@NotifyChange("*")
	@Command
	public void nuevaGraduacion() {
		
		//beanGraduaciones = new BeanGraduaciones();
		fechaEmision = new Date(System.currentTimeMillis());
		fechaProxRevision = new Date(System.currentTimeMillis());
		//graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
		graduacionesForm.setOD_cantidad("-1");
		graduacionesForm.setOI_cantidad("-1");
		
		graduacionesForm.setOD_base("Seleccione");
		graduacionesForm.setOI_base("Seleccione");		
		
		graduacionesForm.setAgente("Seleccione");
		graduacionesForm.setExiste_graduacion("false");
		
		graduacionesForm.setNifdoctor("");
		graduacionesForm.setDvnifdoctor("");
		graduacionesForm.setNombre_doctor("");
		//solo limpia los campos visualmente
		
		graduacionesForm.setOD_esfera("");
		graduacionesForm.setOD_cilindro("");
		graduacionesForm.setOD_eje("");
		graduacionesForm.setOD_cerca("");
		graduacionesForm.setOD_adicion("");
		graduacionesForm.setOD_dnpl("");
		graduacionesForm.setOD_dnpc("");
		graduacionesForm.setOD_avsc("");
		graduacionesForm.setOD_avcc("");
		
		graduacionesForm.setOI_esfera("");
		graduacionesForm.setOI_cilindro("");
		graduacionesForm.setOI_eje("");
		graduacionesForm.setOI_cerca("");
		graduacionesForm.setOI_adicion("");
		graduacionesForm.setOI_dnpl("");
		graduacionesForm.setOI_dnpc("");
		graduacionesForm.setOI_avsc("");
		graduacionesForm.setOI_avcc("");	
		
		graduacionesForm.setOD_observaciones("");
		graduacionesForm.setOI_observaciones("");
		
		graduacionesForm.setPagina("");		
		graduacionesForm.setExiste_graduacion("");
		
		
		posicionaCombo();
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
                "/zul/presupuestos/PresupuestoBS.zul", null, objetos);			
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
                "/zul/encargos/encargosBS.zul", null, objetos);			
		winEncargo.doModal();
		
	}
	
	@Command
	public void ingresoContactologia(){
		
		sess.setAttribute(Constantes.STRING_CLIENTE, graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente", graduacionesForm.getNombre_cliente());
		
		Window winContactologia = (Window)Executions.createComponents(
                "/zul/mantenedores/ContactologiaBS.zul", null, null);
		
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
		//String mensaje5="";
		
		//FQuiroz elimino las variable intermedias para el manejo de las formulas opticas
		/*
		graduacionesForm.setOD_esfera(String.valueOf(beanGraduaciones.getOD_esfera()));
		graduacionesForm.setOD_cilindro(String.valueOf(beanGraduaciones.getOD_cilindro()));
		graduacionesForm.setOD_eje(String.valueOf(Integer.parseInt(String.valueOf(beanGraduaciones.getOD_eje()))));
		graduacionesForm.setOD_cerca(String.valueOf(beanGraduaciones.getOD_cerca()));
		graduacionesForm.setOD_adicion(String.valueOf(beanGraduaciones.getOD_adicion()));
		graduacionesForm.setOD_dnpc(String.valueOf(beanGraduaciones.getOD_dnpl()));
		graduacionesForm.setOD_dnpl(String.valueOf(beanGraduaciones.getOD_dnpl()));
		graduacionesForm.setOD_avcc(String.valueOf(beanGraduaciones.getOD_avcc()));
		graduacionesForm.setOD_avsc(String.valueOf(beanGraduaciones.getOD_avsc()));
		
		graduacionesForm.setOI_esfera(String.valueOf(beanGraduaciones.getOI_esfera()));
		graduacionesForm.setOI_cilindro(String.valueOf(beanGraduaciones.getOI_cilindro()));
		graduacionesForm.setOI_eje(String.valueOf(Integer.parseInt(String.valueOf(beanGraduaciones.getOI_eje()))));
		graduacionesForm.setOI_cerca(String.valueOf(beanGraduaciones.getOI_cerca()));
		graduacionesForm.setOI_adicion(String.valueOf(beanGraduaciones.getOI_adicion()));
		graduacionesForm.setOI_dnpc(String.valueOf(beanGraduaciones.getOI_dnpl()));
		graduacionesForm.setOI_dnpl(String.valueOf(beanGraduaciones.getOI_dnpl()));
		graduacionesForm.setOI_avcc(String.valueOf(beanGraduaciones.getOI_avcc()));
		graduacionesForm.setOI_avsc(String.valueOf(beanGraduaciones.getOI_avsc()));
		*/
		
		graduacionesForm.setAccion("insertarGraduacion");
		pagina = graduacionesForm.getPagina();
		existe_graduacion = graduacionesForm.getExiste_graduacion();		
		
		respuesta1 = validaInformacion();	
		
		if(!respuesta1){	
			return;
		}
		
		respuesta  = validaGraduacion();
		
		if(!respuesta){	
			return;
		}
		
		
		//valida diferente adicion
		check = graduacionesForm.isDiferenteAdd();		
		pasaValidacionADD = false;
		
		if (!check) {
			if (!graduacionesForm.getOI_adicion().equals("0.0") || !graduacionesForm.getOD_adicion().equals("0.0")) {
				if (graduacionesForm.getOI_adicion().equals(graduacionesForm.getOD_adicion()))
				{
					pasaValidacionADD = true;
				}
			}			
		}else {
			pasaValidacionADD = true;
		}
		
		
		if (pasaValidacionADD) {
			
			if(!existe_graduacion.equals("true")){	
				
				if(respuesta && respuesta1){
					
					if(!pagina.equals("NOGRABAR")){
						
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
										
										//if (graduacionesForm.getExito().equals("true")) {
											Messagebox.show("Los datos fueron grabados correctamente");											
											BindUtils.postGlobalCommand(null, null, "notificacionCambios", null);
											return;
										/*}else {
											Messagebox.show("Error al intentar grabar los datos");
											return;
										}*/
										
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
														
														if (graduacionesForm.getExito().equals("true")) {
															Messagebox.show("Los datos fueron grabados correctamente");
															return;
														}else {
															Messagebox.show("Error al intentar grabar los datos");
															return;
														}
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
	
	@GlobalCommand
	public void notificacionCambios() {		
		BindUtils.postNotifyChange(null, null, ControllerGraduacionCliente.this, "graduacionesForm");
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
	
	//@NotifyChange({"graduacionesForm","beanGraduaciones","prismaCantidadOD","prismaBaseOD","prismaCantidadOI","prismaBaseOI","fechaEmision","fechaProxRevision"})
	@NotifyChange("*")
	@Command
	public void verGraduacion(@BindingParam("graduacion")GraduacionesBean graduacion,
							@BindingParam("origen")int origen)
	/*public void verGraduacion(@BindingParam("origen")int origen)	*/
	{	
		String esfera="0";
		String cilindro="0";		
		String eje="0";
		String cerca="0";
		String adicion="0";
		String dnpl="0";
		String dnpc="0";
		String avcc="0";
		String avsc="0";
		Optional<String> fechaEmi;
		Optional<String> fechaRev;
		
		Optional<String> a ; 
		Optional<String> b ;
		Optional<String> c ;
		Optional<String> d ;
		Optional<String> e ;
		Optional<String> f ;
		Optional<String> g ;
		Optional<String> h ;
		Optional<String> i ;			
		
		
		if (origen==1) {
			graduacionesForm.setFecha_graduacion(graduacion.getFecha());
			graduacionesForm.setNumero_graduacion(graduacion.getNumero());
			
			graduacionesForm.setAccion(Constantes.STRING_ACTION_VER_GRADUACION);
			graduacionesDispatch.IngresaGraduacion(graduacionesForm, sess);
		}
		//ojo derecho
		
		a = Optional.ofNullable(graduacionesForm.getOD_esfera()); 
		b = Optional.ofNullable(graduacionesForm.getOD_cilindro());		
		c = Optional.ofNullable(graduacionesForm.getOD_eje());
		d = Optional.ofNullable(graduacionesForm.getOD_cerca());
		e = Optional.ofNullable(graduacionesForm.getOD_adicion());
		f = Optional.ofNullable(graduacionesForm.getOD_dnpl());
		g = Optional.ofNullable(graduacionesForm.getOD_dnpc());
		h = Optional.ofNullable(graduacionesForm.getOD_avcc());
		i = Optional.ofNullable(graduacionesForm.getOD_avsc());
		
		fechaEmi = Optional.ofNullable(graduacionesForm.getFechaEmision());
		fechaRev = Optional.ofNullable(graduacionesForm.getFechaProxRevision());
		
		if (!a.isPresent() || a.get().equals("")) esfera="0"; else esfera = a.get();
		if (!b.isPresent() || b.get().equals("")) cilindro="0";	else cilindro = b.get();	
		if (!c.isPresent() || c.get().equals("")) eje="0"; else eje = c.get();
		if (!d.isPresent() || d.get().equals("")) cerca="0"; else cerca = d.get();
		if (!e.isPresent() || e.get().equals("")) adicion="0";else adicion  = e.get();
		if (!f.isPresent() || f.get().equals("")) dnpl="0";else dnpl = f.get();
		if (!g.isPresent() || g.get().equals("")) dnpc="0";else dnpc = g.get();
		if (!h.isPresent() || h.get().equals("")) avcc="0";else avcc = h.get(); 
		if (!i.isPresent() || i.get().equals("")) avsc="0";else avsc = i.get();		   
		
		/*beanGraduaciones.setOD_esfera(Double.parseDouble(esfera));
		beanGraduaciones.setOD_cilindro(Double.parseDouble(cilindro));
		beanGraduaciones.setOD_eje(Integer.parseInt(eje));
		beanGraduaciones.setOD_cerca(Double.parseDouble(cerca));
		beanGraduaciones.setOD_adicion(Double.parseDouble(adicion));
		beanGraduaciones.setOD_dnpl(Double.parseDouble(dnpl));
		beanGraduaciones.setOD_dnpc(Double.parseDouble(dnpc));
		beanGraduaciones.setOD_avcc(Double.parseDouble(avcc));
		beanGraduaciones.setOD_avsc(Double.parseDouble(avsc));*/
		
		//ojo izquierdo
		
		a = Optional.ofNullable(graduacionesForm.getOI_esfera()); 
		b = Optional.ofNullable(graduacionesForm.getOI_cilindro());		
		c = Optional.ofNullable(graduacionesForm.getOI_eje());
		d = Optional.ofNullable(graduacionesForm.getOI_cerca());
		e = Optional.ofNullable(graduacionesForm.getOI_adicion());
		f = Optional.ofNullable(graduacionesForm.getOI_dnpl());
		g = Optional.ofNullable(graduacionesForm.getOI_dnpc());
		h = Optional.ofNullable(graduacionesForm.getOI_avcc());
		i = Optional.ofNullable(graduacionesForm.getOI_avsc());
		
		if (!a.isPresent() || a.get().equals("")) esfera="0"; else esfera = a.get();
		if (!b.isPresent() || b.get().equals("")) cilindro="0";	else cilindro = b.get();	
		if (!c.isPresent() || c.get().equals("")) eje="0"; else eje = c.get();
		if (!d.isPresent() || d.get().equals("")) cerca="0"; else cerca = d.get();
		if (!e.isPresent() || e.get().equals("")) adicion="0";else adicion  = e.get();
		if (!f.isPresent() || f.get().equals("")) dnpl="0";else dnpl = f.get();
		if (!g.isPresent() || g.get().equals("")) dnpc="0";else dnpc = g.get();
		if (!h.isPresent() || h.get().equals("")) avcc="0";else avcc = h.get(); 
		if (!i.isPresent() || i.get().equals("")) avsc="0";else avsc = i.get();			
		
		/*beanGraduaciones.setOI_esfera(Double.parseDouble(esfera));
		beanGraduaciones.setOI_cilindro(Double.parseDouble(cilindro));
		beanGraduaciones.setOI_eje(Integer.parseInt(eje));
		beanGraduaciones.setOI_cerca(Double.parseDouble(cerca));
		beanGraduaciones.setOI_adicion(Double.parseDouble(adicion));
		beanGraduaciones.setOI_dnpl(Double.parseDouble(dnpl));
		beanGraduaciones.setOI_dnpc(Double.parseDouble(dnpc));
		beanGraduaciones.setOI_avcc(Double.parseDouble(avcc));
		beanGraduaciones.setOI_avsc(Double.parseDouble(avsc));	*/
		
		try {
			fechaEmision  =  dt.parse(fechaEmi.orElse("31/12/2999"));
			fechaProxRevision = dt.parse(fechaRev.orElse("31/12/2999"));			
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		/*
		try {
			fechaEmision = dt.parse(graduacionesForm.getFechaEmision());
			fechaProxRevision = dt.parse(graduacionesForm.getFechaProxRevision());
		} catch (ParseException x) {			
			x.printStackTrace();
		}*/
		
		
		posicionaCombo();
	}
	
	
		
	//@NotifyChange({"graduacionesForm","prismaCantidadOD","prismaBaseOD","prismaCantidadOI","prismaBaseOI","agenteBean"})
	@NotifyChange({"*"})
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
		
		Optional<String> Agente = Optional.ofNullable(graduacionesForm.getAgente());		
		graduacionesForm.setAgente(Agente.orElse(""));
		
		Optional<PrismaCantidadBean> a = graduacionesForm.getListaCantidadOD()
				.stream().filter(s ->graduacionesForm.getOD_cantidad().equals(String.valueOf(s.getCodigo()))).findFirst();		
						
			if (a.isPresent()) prismaCantidadOD = a.get(); else prismaCantidadOD=new PrismaCantidadBean();
		
		Optional<PrismaCantidadBean> b = graduacionesForm.getListaCantidadOI()
				.stream().filter(s ->graduacionesForm.getOI_cantidad().equals(String.valueOf(s.getCodigo()))).findFirst();		
						
			if (b.isPresent()) prismaCantidadOI = b.get(); else prismaCantidadOI=new PrismaCantidadBean();
		
			
		Optional<PrismaBaseBean> c = graduacionesForm.getListaBaseOD()				
				.stream().filter(s ->graduacionesForm.getOD_base().equals(s.getDescripcion())).findFirst();		
						
			if (c.isPresent()) prismaBaseOD = c.get(); else prismaBaseOD=new PrismaBaseBean();
		
		Optional<PrismaBaseBean> d = graduacionesForm.getListaBaseOI()				
				.stream().filter(s ->graduacionesForm.getOI_base().equals(s.getDescripcion())).findFirst();		
						
			if (d.isPresent()) prismaBaseOI = d.get(); else prismaBaseOI=new PrismaBaseBean();			
				
		Optional<AgenteBean> e = graduacionesForm.getListaAgentes()				
			.stream().filter(s ->graduacionesForm.getAgente().equals(s.getUsuario())).findFirst();	
			
			if (e.isPresent()) agenteBean = e.get(); else agenteBean=new AgenteBean();
	}
	
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void buscarDoctorAjax()
    {        	
    	String nifdoctor = graduacionesForm.getNifdoctor();	
    	
    	if(!nifdoctor.equals("")){    		
    		
    		sess.setAttribute("nifdoctor", nifdoctor);
    		graduacionesDispatch.buscarDoctorAjax(graduacionesForm, sess);
    		
    		if (graduacionesForm.getNifdoctor().equals("")) {    		
    			Messagebox.show("El doctor con rut "+nifdoctor+" no existe");
    		}   		
    		
    	}else{
    		Messagebox.show("Debe ingrese rut de doctor.");
    	}	
    }
	
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void buscarMedico() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();
		objetos.put("retorno", "seleccionaMedicoGraduacion");
		
		Window winBuscarMedico = (Window)Executions.createComponents(
                "/zul/mantenedores/BusquedaMedico.zul", null, objetos);
		
		winBuscarMedico.doModal(); 		
	}
	
	@NotifyChange({"graduacionesForm"})
	@GlobalCommand
	public void seleccionaMedicoGraduacion(@BindingParam("medico")OftalmologoBean medico) {
		
		graduacionesForm.setNifdoctor(medico.getNif());
		graduacionesForm.setDvnifdoctor(medico.getLnif());
		graduacionesForm.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
		graduacionesForm.setCod_doctor(medico.getCodigo());
		graduacionesForm.setDoctor(medico.getCodigo());
			
	}
	
	//===================================================
	//=======Validaciones varias ========================
	//===================================================	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void validaEsfera(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		
		Double mult = 0.25;
		Double cont = 0.00;		
		Double esfera= 0.00;	
		Double elementoadicion=0.00;		
		
		//String regexp = "^-?(([0-9]{1,2})+(?:[.][0-9]{0,2})+)?$";
		/*
		if (!Pattern.matches(regexp1, elemento)) {
			Messagebox.show("Formato incorrecto");
			return;
		}*/	
		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);	
		
		if(!elemento.equals("")){
			try {
				esfera = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_esfera(nf.format(esfera));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_esfera(nf.format(esfera));
				}
				
				elemento = nf.format(esfera);
			}catch(Exception e) {
				esfera = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_esfera("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_esfera("");
				}
				
				Messagebox.show("Formato de la esfera es incorrecto");
				return;
			}
		}
		
			
		if(!elemento.equals("")){
			
			try {
				esfera = Double.valueOf(elemento);
			}catch(Exception e) {
				esfera = 0.00;
			}
			
			if(esfera >= -30.00 && esfera <= 30.00){
				
				//esfera = parseFloat(elemento.value).toFixed(2);
				
				if (esfera%mult != 0){
					
					 while((esfera%mult !=0 ) && (cont < 55)){
						 
						 if(esfera > 0){				 
							 esfera =  (esfera + 0.01);
						 }else{
							 esfera =  (esfera + -0.01);
						 }
						 //esfera = parseFloat(esfera.toFixed(2));
						 cont++;
					 }				
					 
					 //elemento.value=esfera.toFixed(2);
					 
					 try {
						 if(lado.equals("derecha")){
							 elementoadicion = Double.parseDouble(graduacionesForm.getOD_adicion());
							 validaAdicion(graduacionesForm.getOD_adicion(), lado);
							 
						 }else if(lado.equals("izquierda")){
							 elementoadicion = Double.parseDouble(graduacionesForm.getOI_adicion());
							 validaAdicion(graduacionesForm.getOI_adicion(), lado);
						 }
					 }catch (Exception e) { 
						 elementoadicion=0.0;				
					 }
					 
					 //validaAdicion(elementoadicion, lado);
					 
				}else{	
					
					 //elemento = esfera;	
					 try {
						 if(lado.equals("derecha")){
							 elementoadicion = Double.parseDouble(graduacionesForm.getOD_adicion());
							 validaAdicion(graduacionesForm.getOD_adicion(), lado);
							 
						 }else if(lado.equals("izquierda")){
							 elementoadicion = Double.parseDouble(graduacionesForm.getOI_adicion());
							 validaAdicion(graduacionesForm.getOI_adicion(), lado);
						 }
					 }catch (Exception e) {
						 elementoadicion=0.0;
					 }
					 
					 //validaAdicion(elementoadicion, lado);
				}
			}else{
				Messagebox.show("El valor esfera "+lado+" esta fuera del rango permitido -30 y 30");
				elemento="";
				
				if(lado.equals("derecha"))					
					graduacionesForm.setOD_esfera("");				
				else					
					graduacionesForm.setOI_esfera("");
				
				return;
				//elemento.focus();
			}
		}else{
			esfera = 0.00;
			Messagebox.show("Debe ingresar valores entre -30 y 30");
			elemento="";
			
			if(lado.equals("derecha"))				
				graduacionesForm.setOD_esfera("");
			else				
				graduacionesForm.setOI_esfera("");
			
			return;
			
			//elemento.value = parseFloat(esfera).toFixed(2);
			//elemento.focus();
		}		
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void validaAdicion(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		Double esfera = 0.0;
		Double dnpl = 0.0;
		Double adicion =0.0;	
		
		
		/*if (!Pattern.matches(regexp1, elemento)) {
			Messagebox.show("Formato incorrecto");
			return;
		}*/			
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);	
		
		if(!elemento.equals("")){
			try {
				adicion = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_adicion(nf.format(adicion));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_adicion(nf.format(adicion));
				}
				
				elemento = nf.format(adicion);
			}catch(Exception e) {
				adicion = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_adicion("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_adicion("");
				}
				
				Messagebox.show("Formato de campo adici�n es incorrecto");
				return;
			}
		}
			
		
		try {
			if(lado.equals("derecha")){
				esfera=Double.parseDouble(graduacionesForm.getOD_esfera());
			}else if(lado.equals("izquierda")){
				esfera=Double.parseDouble(graduacionesForm.getOI_esfera());
			}		
		}catch (Exception e) {
			esfera=0.0;
		}
		
		
		try {
			if(lado.equals("derecha")){
				dnpl=Double.parseDouble(graduacionesForm.getOD_dnpl());
			}else if(lado.equals("izquierda")){				
				dnpl=Double.parseDouble(graduacionesForm.getOI_dnpl());
			}		
		}catch (Exception e) {
			dnpl=0.0;
		}
		
		if(esfera != 0){	
			
			if(lado.equals("derecha")){
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(adicion != 0){
					
					if(adicion > 0 && adicion <= 4){
						
						double cerca = adicion + esfera;
						graduacionesForm.setOD_cerca(String.valueOf(cerca));						
						//document.getElementById('OD_cerca').value = cerca.toFixed(2);						
						//elemento = adicion;//.toFixed(2);			
						
						validaDNPL(graduacionesForm.getOD_dnpl(), lado);
						
					}else{
						
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");							
							graduacionesForm.setOD_adicion("");
							return;
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");							
							graduacionesForm.setOD_adicion("");
							return;
						}
					}	
					
				}else{						
					
					graduacionesForm.setOD_cerca("");
					graduacionesForm.setOD_dnpl("");
					graduacionesForm.setOD_dnpc("");
					
					return;
				}		
				
			}else if(lado.equals("izquierda")){
				
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(adicion != 0){
					
					if(adicion >0 && adicion <=4){
						
						double cerca = adicion + esfera;
						graduacionesForm.setOI_cerca(String.valueOf(cerca));	
						//document.getElementById('OI_cerca').value = cerca.toFixed(2);
						//elemento = adicion; //.toFixed(2);						
						
						validaDNPL(graduacionesForm.getOI_dnpl(), lado);
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");							
							graduacionesForm.setOI_adicion("");
							return;
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
							return;
						}
						
					}					
				}else{			
					
					graduacionesForm.setOI_cerca("");
					graduacionesForm.setOI_dnpl("");
					graduacionesForm.setOI_dnpc("");
					return;
				}			
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");		
		}
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public boolean validaDNPL(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		Double dnpl = 0.00;	
		Double adicion = 0.00;
		Double res=0.00;
		
		/*if (!Pattern.matches(regexp1, elemento)) {
			Messagebox.show("Formato incorrecto");
			return false;
		}*/		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);	
		
		if(!elemento.equals("")){
			try {
				dnpl = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_dnpl(nf.format(dnpl));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_dnpl(nf.format(dnpl));
				}
				
				elemento = nf.format(dnpl);
			}catch(Exception e) {
				dnpl = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_dnpl("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_dnpl("");
				}
				
				Messagebox.show("Formato de campo DNPL es incorrecto");
				return false;
			}
		}
		
		
		if(dnpl != 0){
			
			if(dnpl >= 20 && dnpl <= 40){
				
				//dnpl = parseFloat(elemento.value).toFixed(2);	
				
				if(lado.equals("derecha")){		
					
					res = dnpl - 1;					
					
					try {
						adicion = Double.parseDouble(graduacionesForm.getOD_adicion());
					}catch (Exception e) {
						adicion=0.0;
					}					
			
					if(adicion != 0){						
						graduacionesForm.setOD_dnpc(nf.format(res));
						//document.getElementById('OD_dnpc').value = parseFloat(res).toFixed(2);
					}else{						
						graduacionesForm.setOD_dnpc("");
					}
					
					//elemento = parseFloat(dnpl).toFixed(2);
					elemento = nf.format(dnpl);
					return true;
					
				}else if(lado.equals("izquierda")){
					
					res = dnpl - 1;				
					
					try {
						adicion =  Double.parseDouble(graduacionesForm.getOI_adicion());
					}catch (Exception e) {
						adicion=0.0;
					}					
					
					if(adicion != 0){						
						graduacionesForm.setOI_dnpc(nf.format(res));
						//document.getElementById('OI_dnpc').value = parseFloat(res).toFixed(2);
					}else{						
						graduacionesForm.setOI_dnpc("");
					}				
					
					//elemento.value = parseFloat(dnpl).toFixed(2);
					elemento = nf.format(dnpl);
					return true;	
				}
			}else{			
				Messagebox.show("Distancia Naso pupilar, valor esta fuera de rango");
				/*
				if(lado.equals("derecha")){					
					graduacionesForm.setOD_dnpc("");
					
				}else if(lado.equals("izquierda")){					
					graduacionesForm.setOI_dnpc("");
				}*/
				
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


	@NotifyChange({"graduacionesForm"})
	@Command
	public void validaCilindro(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		Double cilindro=0.0; 	
		Double mult = 0.25;
		Double cont = 0.0;
		Double intCilindro=0.0;		
		
		/*if (!Pattern.matches(regexp1, elemento)) {
			Messagebox.show("Formato incorrecto");
			return;
		}*/
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);		
		
		if(!elemento.equals("")){
			try {
				cilindro = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_cilindro(nf.format(cilindro));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_cilindro(nf.format(cilindro));
				}
				
				elemento = nf.format(cilindro);
			}catch(Exception e) {
				cilindro = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_cilindro("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_cilindro("");
				}
				
				Messagebox.show("Formato del cilindro es incorrecto");
				return;
			}
		}	
		
		
			
		if(!elemento.equals("")){
				
				/*try {
					cilindro = Double.valueOf(elemento);
				}catch(Exception e) {
					cilindro=0.00;
				}	*/			
				
				if(cilindro >= -8 && cilindro <= 8){				
					
					//cilindro = parseFloat(elemento.value).toFixed(2);
					cilindro = Double.valueOf(nf.format(cilindro));
					
					if (cilindro%mult != 0){	
						
						 while((cilindro%mult != 0) && (cont < 55)){				 
							 
							 if(cilindro > 0){
								 cilindro = cilindro + 0.01F;
							 }else{
								 cilindro = cilindro + (-0.01F);
							 }
							 
							 //cilindro = parseFloat(cilindro.toFixed(2));
							 cilindro = Double.valueOf(nf.format(cilindro));
							 cont++;
						 }				 
						 //elemento.value=cilindro.toFixed(2);
						 
						 if(lado.equals("derecha")){
							graduacionesForm.setOD_cilindro(nf.format(cilindro));
						 }else {						
							graduacionesForm.setOI_cilindro(nf.format(cilindro));
						 }
					}else{			
						//elemento.value = cilindro;
						
						if(lado.equals("derecha")){
							graduacionesForm.setOD_cilindro(String.valueOf(cilindro));
						}else {						
							graduacionesForm.setOI_cilindro(String.valueOf(cilindro));
						}						
					}	
							
					
				}else{
					Messagebox.show("El valor cilindro "+lado+" esta fuera del rango permitido -8 y 8");
					elemento="";
					
					if(lado.equals("derecha")){
						graduacionesForm.setOD_cilindro("");
					}else {						
						graduacionesForm.setOI_cilindro("");
					}	
					//elemento.focus();
				}
			}else{
				cilindro = 0.00;
				Messagebox.show("El valor cilindro debe entre los valores entre -8 y 8");
				elemento = "";
				//elemento.value = parseFloat(cilindro).toFixed(2);
				//elemento.focus();
				if(lado.equals("derecha")){
					graduacionesForm.setOD_cilindro("");
				}else {						
					graduacionesForm.setOI_cilindro("");
				}
			}
			
			intCilindro = cilindro;
			
			if(((intCilindro >= -8 && intCilindro < 0) || (intCilindro > 0 && intCilindro <= 8)) || (intCilindro >= -8.00 && intCilindro < 0.00) || (intCilindro > 0.00 && intCilindro <= 8.00) ){
				
				if(lado.equals("derecha")){				
					// document.getElementById('OD_eje').disabled =false;	
					
				}else if(lado.equals("izquierda")){
					//document.getElementById('OI_eje').disabled =false;
				}			
			}else{
				
				if(lado.equals("derecha")){					
					graduacionesForm.setOD_eje("");
					//document.getElementById('OD_eje').disabled =true;					
				}else if(lado.equals("izquierda")){					
					graduacionesForm.setOI_eje("");
					//document.getElementById('OI_eje').disabled =true;
				}
			}			
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void validaEje(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		Double eje = 0.00;
		//boolean esnumero=false;
		
		/*if (!Pattern.matches(regexp2, elemento)) {
			Messagebox.show("Formato incorrecto");
			return;
		}*/
		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);	
		
		if(!elemento.equals("")){
			try {
				eje = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_eje(nf.format(eje));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_eje(nf.format(eje));
				}
				
				elemento = nf.format(eje);
			}catch(Exception e) {
				eje = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_eje("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_eje("");
				}
				
				Messagebox.show("Formato del eje es incorrecto");
				return;
			}
		}		
			
		
		if(eje != 0){		
			
			//esnumero = validarSiNumero(eje);			
			//if(true == esnumero){
				if(eje < 0 || eje >180){
					Messagebox.show("El valor del eje "+lado+" esta fuera de rango [0..100]");
					eje = 0.00;
					elemento="";
					//elemento.value = "";
					
					if(lado.equals("derecha")){					
						graduacionesForm.setOD_eje("");
										
					}else if(lado.equals("izquierda")){					
						graduacionesForm.setOI_eje("");
						
					}
					
					
				}
			/*}else{
				Messagebox.show("Debe ingresar solo numeros entre 0 y 180");
			}	*/	
		}	
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void validacionCerca(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		Double esfera = 0.0;
		Double cerca = 0.0;	
		Double adicion = 0.0;
		Double dnpl = 0.0;
		
		/*if (!Pattern.matches(regexp1, elemento)) {
			Messagebox.show("Formato incorrecto");
			return;
		}*/
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);			
		
		if(!elemento.equals("")){
			try {
				cerca = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_cerca(nf.format(cerca));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_cerca(nf.format(cerca));
				}
				
				elemento = nf.format(cerca);
			}catch(Exception e) {
				cerca = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_cerca("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_cerca("");
				}
				
				Messagebox.show("Formato de campo cerca es incorrecto");
				return;
			}
		}			
		
		
		
		try {
			if(lado.equals("derecha")){						
				esfera = Double.parseDouble(graduacionesForm.getOD_esfera());
			}else if(lado.equals("izquierda")){				
				esfera = Double.parseDouble(graduacionesForm.getOI_esfera());
			}
		}catch (Exception e) {
			esfera=0.00;
		}
		
		if(esfera != 0){		
			if(lado.equals("derecha")){	
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(cerca !=0){
					
					if(cerca > esfera){
						
						adicion = cerca - esfera;						
						graduacionesForm.setOD_adicion(nf.format(adicion));
						graduacionesForm.setOD_cerca(nf.format(cerca));
						//document.getElementById('OD_adicion').value = adicion.toFixed(2);
						//elemento.value = parseFloat(cerca).toFixed(2);
						//elemento=cerca;				
						
						validaDNPL(graduacionesForm.getOD_dnpl(), lado);
						
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");
						
						graduacionesForm.setOD_cerca("");
						return;
					}	
					
				}		
				
			}else if(lado.equals("izquierda")){
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(cerca != 0){
					if(cerca > esfera){
						
						adicion = cerca - esfera;						
						graduacionesForm.setOI_adicion(nf.format(adicion));
						graduacionesForm.setOI_cerca(nf.format(cerca));
						//document.getElementById('OI_adicion').value = adicion.toFixed(2);
						//elemento.value = parseFloat(cerca).toFixed(2); 
						//elemento=cerca;						
						
						validaDNPL(graduacionesForm.getOI_dnpl(), lado);
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");						
						graduacionesForm.setOI_cerca("");
						return;
					}				
				}	
				
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");
		}
		
	}

	@NotifyChange({"graduacionesForm"})
	@Command
	public boolean validaDNPC(@BindingParam("elemento")String elemento, @BindingParam("lado")String lado){
		
		Double dnpc 	= 0.0;	
		Double adicion 	= 0.0;
		
		/*if (!Pattern.matches(regexp1, elemento)) {
			Messagebox.show("Formato incorrecto");
			return false;
		}*/		
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);	
		
		if(!elemento.equals("")){
			try {
				dnpc = Double.valueOf(elemento);
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_dnpc(nf.format(dnpc));				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_dnpc(nf.format(dnpc));
				}
				
				elemento = nf.format(dnpc);
			}catch(Exception e) {
				dnpc = 0.00;
				
				if(lado.equals("derecha")){
				   graduacionesForm.setOD_dnpc("");				 
				}else if(lado.equals("izquierda")){				 
				   graduacionesForm.setOI_dnpc("");
				}
				
				Messagebox.show("Formato de campo DNPC es incorrecto");
				return false;
			}
		}
		
		
		
		if(dnpc != 0){
			
			if(dnpc >= 20 && dnpc <= 40){
			
				if(lado.equals("derecha")){				
					
					try {
						adicion = Double.parseDouble(graduacionesForm.getOD_adicion());
					}catch(Exception e) {
						adicion=0.0;
					}
					
					
					if(adicion == 0){
						Messagebox.show("Debe ingresar receta de cerca");						
						graduacionesForm.setOD_dnpc("");
						return false;
					}
					
				}else if("izquierda" == lado){					
					
					try {
						adicion = Double.parseDouble(graduacionesForm.getOI_adicion());
					}catch(Exception e) {
						adicion=0.0;
					}					
					
					if(adicion == 0){
						Messagebox.show("Debe ingresar receta de cerca");						
						graduacionesForm.setOI_dnpc("");
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

	
	private boolean validaInformacion(){
		
		String nombre_cliente; 
		String codigo_cliente;
		String doctor;
		String agente;
		String fecEmision;
		String OD_cantidad;
		String OD_base;
		String OI_cantidad;
		String OI_base;
		
		Optional<AgenteBean> ab;
		Optional<Integer> pcb;
		Optional<String> pbb;
		
		pcb = Optional.ofNullable(prismaCantidadOD.getCodigo());
		if (pcb.isPresent())
			graduacionesForm.setOD_cantidad(String.valueOf(prismaCantidadOD.getCodigo()));
		else
			graduacionesForm.setOD_cantidad("");
		
		pcb = Optional.ofNullable(prismaCantidadOI.getCodigo());
		if (pcb.isPresent())
			graduacionesForm.setOI_cantidad(String.valueOf(prismaCantidadOI.getCodigo()));
		else
			graduacionesForm.setOI_cantidad("");
		
		pbb = Optional.ofNullable(prismaBaseOD.getDescripcion());
		if(pbb.isPresent())
			graduacionesForm.setOD_base(prismaBaseOD.getDescripcion());
		else
			graduacionesForm.setOD_base("");
		
		pbb = Optional.ofNullable(prismaBaseOI.getDescripcion());
		if(pbb.isPresent())
			graduacionesForm.setOI_base(prismaBaseOI.getDescripcion());
		else
			graduacionesForm.setOI_base("");
		
		
		ab =  Optional.ofNullable(agenteBean);
		if (ab.isPresent())
			graduacionesForm.setAgente(agenteBean.getUsuario());
		else {
			Messagebox.show("Debe Seleccionar agente");
			graduacionesForm.setAgente("");
			agenteBean=null;
			return false;
		}
			
		
		nombre_cliente = graduacionesForm.getNombre_cliente().trim();
		
		if(nombre_cliente.equals("")){
			Messagebox.show("Debe ingresar cliente");
			return false;
		}
		
		codigo_cliente = String.valueOf(graduacionesForm.getCliente()).trim();
		
		if(codigo_cliente.equals("") || codigo_cliente.equals("0")){		
			Messagebox.show("Debe ingresar un cliente");
			return false;
		}		
		
		doctor = graduacionesForm.getDoctor();
		
		if(doctor.equals("")){
			Messagebox.show("Debe ingresar un doctor");
			return false;		
		}
		
		/*agente = graduacionesForm.getAgente();
		
		if(agente.equals("Seleccione")){
			Messagebox.show("Debe Seleccionar agente");
			return false;		
		}*/
		
		
		
		//String fechaEmision = graduacionesForm.getFechaEmision().trim();	
		fecEmision = dt.format(fechaEmision);
		if(fecEmision.equals("")){
			Messagebox.show("Debe ingresar fecha emision");
			return false;
		}
		
		OD_cantidad = graduacionesForm.getOD_cantidad();
		OD_cantidad = OD_cantidad.trim();
		/*if(!OD_cantidad.equals("")  && !OD_cantidad.equals("-1") ){
			
			OD_base = graduacionesForm.getOD_base();
			
			if(OD_base.equals("")  || OD_base.equals("Seleccione") ){
				Messagebox.show("Debe seleccionar Base de Prisma Derecho");
				return false;
			}			
		}*/
		
		OI_cantidad = graduacionesForm.getOI_cantidad();
		OI_cantidad = OI_cantidad.trim();
		/*if(!OI_cantidad.equals("")  && !OI_cantidad.equals("-1") ){
			
			OI_base = graduacionesForm.getOI_base();
			
			if(OI_base.equals("")  || OI_base.equals("Seleccione") ){
				Messagebox.show("Debe seleccionar Base de Prisma Izquierdo");
				return false;
			}			
		}*/
		
		OD_base = graduacionesForm.getOD_base();
		/*if(!OD_base.equals("")  && !OD_base.equals("Seleccione") ){
			
			OD_cantidad = graduacionesForm.getOD_cantidad();		
			
			if(OD_cantidad.equals("")  || OD_cantidad.equals("-1") ){
				Messagebox.show("Debe seleccionar Cantidad de Prisma Derecho");
				return false;
			}
		}*/
		
		OI_base = graduacionesForm.getOI_base();
		/*if(!OI_base.equals("")  &&  OI_base.equals("Seleccione") ){
			
			OI_cantidad = graduacionesForm.getOI_cantidad();
			if(OI_cantidad.equals("")  || OI_cantidad.equals("-1") ){
				Messagebox.show("Debe seleccionar Cantidad de Prisma Izquierdo");
				return false;
			}
		}*/
		
		return true; //true si llega hasta aqui
	}


	public boolean validaGraduacion(){
		
		double esferaD 		= 0.0;
		double cilindroD 	= 0.0;
		double ejeD 		= 0.0;
		double adicionD		= 0.0;
		double OD_dnpl		= 0.0;
		
		double esferaI 		= 0.0;
		double cilindroI 	= 0.0;
		double ejeI 		= 0.0;
		double adicionI		= 0.0;
		double OI_dnpl		= 0.0;
		double intCilindro	= 0.0;
		
		double valorDoble   = 0.0;
		
		/********* VALIDACIONES OJO DERECHO ********/
		//esferaD = beanGraduaciones.getOD_esfera();		
		//cilindroD = beanGraduaciones.getOD_cilindro();		
		//ejeD = beanGraduaciones.getOD_eje();			
		//adicionD = beanGraduaciones.getOD_adicion();	
		//OD_dnpl = beanGraduaciones.getOD_dnpl();	
		
		try {			
			esferaD = Double.parseDouble(graduacionesForm.getOD_esfera());
		}catch(Exception e) {
			esferaD=0;
		}
		
		try {			
			cilindroD = Double.parseDouble(graduacionesForm.getOD_cilindro());
		}catch(Exception e) {
			cilindroD=0;
		}
		
		try {			
			ejeD = Double.parseDouble(graduacionesForm.getOD_eje());
		}catch(Exception e) {
			ejeD=0;
		}
		
		try {			
			adicionD = Double.parseDouble(graduacionesForm.getOD_adicion());
		}catch(Exception e) {
			adicionD=0;
		}
		
		try {			
			OD_dnpl = Double.parseDouble(graduacionesForm.getOD_dnpl());
		}catch(Exception e) {
			OD_dnpl=0;
		}	
		
		//=========================================================================
				
		
		if(!graduacionesForm.getOD_esfera().equals("") &&  !graduacionesForm.getOD_esfera().equals(null)){
			if(esferaD < -30 || esferaD > 30){
				Messagebox.show("El valor esfera derecha esta fuera del rango permitido -30 y 30");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar esfera derecha.");
			return false;
		}		
		
		if(!graduacionesForm.getOD_cilindro().equals("") &&  !graduacionesForm.getOD_cilindro().equals(null)){		
			if(cilindroD < -8  || cilindroD > 8){
				Messagebox.show("El valor cilindro derecho esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro derecho");
			return false;
		}
		
		
		intCilindro = cilindroD;
		
		if(cilindroD != 0){
			if(ejeD == 0){
				Messagebox.show("Debe ingresar eje derecho");
				return false;
			}
		}
		
		
		if(!graduacionesForm.getOD_eje().equals("")  && !graduacionesForm.getOD_eje().equals(null)){
			if(graduacionesForm.getOD_cilindro().equals("") || graduacionesForm.getOD_cilindro().equals(null)){
				Messagebox.show("Debe ingresar cilindro derecho");
				return false;
			}
		}	
		
		if(!graduacionesForm.getOD_eje().equals("")  && !graduacionesForm.getOD_eje().equals(null)){
			if(ejeD < 0 || ejeD >180){
				Messagebox.show("El valor del eje derecho esta fuera de rango");
				return false;
			}
		}	
		
		
		if(!graduacionesForm.getOD_adicion().equals("")  && !graduacionesForm.getOD_adicion().equals(null)){
			if(adicionD < 0.50 || adicionD > 4){
				Messagebox.show("El valor de la adicion derecha esta fuera de rango");
				return false;
			}
		/*}else {
			Messagebox.show("El Valor de la adicion no puede ser menor o igual a 0");
			return false;*/
		}
		
		if(graduacionesForm.getOD_dnpl().equals("") || graduacionesForm.getOD_dnpl().equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar derecha.");
			return false;
		}else{
			
			boolean respuesta = validaDNPL(graduacionesForm.getOD_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}		
		
		/********* FIN VALIDACIONES OJO DERECHO ********/
		
		
		
		/********* VALIDACIONES OJO IZQUIERDO ********/
		
		//esferaI = beanGraduaciones.getOI_esfera();		
		//cilindroI = beanGraduaciones.getOI_cilindro();	
		//ejeI = beanGraduaciones.getOI_eje();		
		//adicionI = beanGraduaciones.getOI_adicion();		
		//OI_dnpl = beanGraduaciones .getOI_dnpl();
		
		try {			
			esferaI = Double.parseDouble(graduacionesForm.getOI_esfera());
		}catch(Exception e) {
			esferaI=0;
		}
		
		try {			
			cilindroI = Double.parseDouble(graduacionesForm.getOI_cilindro());
		}catch(Exception e) {
			cilindroI=0;
		}
		
		try {			
			ejeI = Double.parseDouble(graduacionesForm.getOI_eje());
		}catch(Exception e) {
			ejeI=0;
		}
		
		try {			
			adicionI = Double.parseDouble(graduacionesForm.getOI_adicion());
		}catch(Exception e) {
			adicionI=0;
		}
		
		try {			
			OI_dnpl = Double.parseDouble(graduacionesForm.getOI_dnpl());
		}catch(Exception e) {
			OI_dnpl=0;
		}
		
		//=============================
		
		if(!graduacionesForm.getOI_esfera().equals("") &&  !graduacionesForm.getOI_esfera().equals(null)){
			if(esferaI < -30 || esferaI > 30){
				Messagebox.show("El valor esfera izquierda esta fuera del rango permitido -30 y 30");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar esfera izquierda.");
			return false;
		}		
		
		if(!graduacionesForm.getOI_cilindro().equals("") &&  !graduacionesForm.getOI_cilindro().equals(null)){		
			if(cilindroI < -8  || cilindroI > 8){
				Messagebox.show("El valor cilindro izquierdo esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro izquierdo");
			return false;
		}
		
		if(cilindroI != 0){
			if(ejeI == 0){
				Messagebox.show("Debe ingresar eje izquierdo");
				return false;
			}
		}	
		
		if(!graduacionesForm.getOI_eje().equals("")  && !graduacionesForm.getOI_eje().equals(null)){
			if(graduacionesForm.getOI_cilindro().equals("") || graduacionesForm.getOI_cilindro().equals(null)){
				Messagebox.show("Debe ingresar cilindro izquierdo");
				return false;
			}
		}	
		
		if(!graduacionesForm.getOI_eje().equals("")  && !graduacionesForm.getOI_eje().equals(null)){
			if(ejeI < 0 || ejeI >180){
				Messagebox.show("El valor del eje izquierdo esta fuera de rango");
				return false;
			}
		}	
		
		
		if(!graduacionesForm.getOI_adicion().equals("")  && !graduacionesForm.getOI_adicion().equals(null)){
			if(adicionI < 0.50 || adicionI > 4){
				Messagebox.show("El valor de la adicion izquierda esta fuera de rango");
				return false;
			}
		/*}else {
			Messagebox.show("El Valor de la adicion no puede ser menor o igual a 0");
			return false;*/
		}
		
		if(graduacionesForm.getOI_dnpl().equals("") || graduacionesForm.getOI_dnpl().equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar izquierda.");
			return false;
		}else{
			
			boolean respuesta = validaDNPL(graduacionesForm.getOI_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}
		
		/*try {
			valorDoble = Double.parseDouble(graduacionesForm.getOD_dnpc());
		}catch (Exception e) {
			valorDoble = 0.0;
		}*/
		
		boolean respuestadnpc = validaDNPC(graduacionesForm.getOD_dnpc(), "derecha");
		if(respuestadnpc == false){
			return false;
		}
		
		/********* FIN VALIDACIONES OJO IZQUIERDO ********/	
		
		String fProxRevision;
		String fEmision;
		
		graduacionesForm.setFechaEmision(dt.format(fechaEmision));
		graduacionesForm.setFechaProxRevision(dt.format(fechaProxRevision));
		
		
		fProxRevision = graduacionesForm.getFechaProxRevision().trim();
		fEmision = graduacionesForm.getFechaEmision().trim();		
			
		
		if(fEmision.equals("")){
			Messagebox.show("Debe ingresar fecha de emision");
			return false;
		}
		
		
		/*if(fProxRevision.equals("")){
			Messagebox.show("Debe ingresar fecha de revisi\u00F3n");
			return false;
		}*/
		
		return true;//CAMBIAR  a true
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

	/*public BeanGraduaciones getBeanGraduaciones() {
		return beanGraduaciones;
	}

	public void setBeanGraduaciones(BeanGraduaciones beanGraduaciones) {
		this.beanGraduaciones = beanGraduaciones;
	}*/

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}

	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}

	
	
	
	
}
