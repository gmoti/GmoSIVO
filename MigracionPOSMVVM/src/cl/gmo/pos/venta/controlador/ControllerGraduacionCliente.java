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
	BeanGraduaciones beanGraduaciones;
	
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
		beanGraduaciones = new BeanGraduaciones();
		
		graduacionesDispatch = new GraduacionesDispatchActions();
		busquedaClientesDispatch = new BusquedaClientesDispatchActions();	
		
		fechaEmision = new Date(System.currentTimeMillis());
		
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
			
			if (graduacionesForm.getPagina().equals("NOGRABAR")) {			
				
				graduacionesBean.setFecha(graduacionesForm.getFecha_graduacion());
				graduacionesBean.setNumero((int)graduacionesForm.getNumero_graduacion());
				
				verGraduacion(graduacionesBean);
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
		
		beanGraduaciones = new BeanGraduaciones();
		fechaEmision = new Date(System.currentTimeMillis());
		fechaProxRevision = null;
		graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
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
		//String mensaje5="";
		
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
		pasaValidacionADD = true;
		
		if (!check) {
			if (!graduacionesForm.getOI_adicion().equals("0.0") || !graduacionesForm.getOD_adicion().equals("0.0")) {
				if (graduacionesForm.getOI_adicion().equals(graduacionesForm.getOD_adicion()))
				{
					pasaValidacionADD = false;
				}
			}
			
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
	public void verGraduacion(@BindingParam("graduacion")GraduacionesBean graduacion)
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
		
		Optional<String> a ; 
		Optional<String> b ;
		Optional<String> c ;
		Optional<String> d ;
		Optional<String> e ;
		Optional<String> f ;
		Optional<String> g ;
		Optional<String> h ;
		Optional<String> i ;
		
		
		graduacionesForm.setAccion("verGraduacion");
		graduacionesForm.setFecha_graduacion(graduacion.getFecha());
		graduacionesForm.setNumero_graduacion(graduacion.getNumero());		
		graduacionesDispatch.IngresaGraduacion(graduacionesForm, sess);
		
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
		
		if (!a.isPresent() || a.get().equals("")) esfera="0"; else esfera = a.get();
		if (!b.isPresent() || b.get().equals("")) cilindro="0";	else cilindro = b.get();	
		if (!c.isPresent() || c.get().equals("")) eje="0"; else eje = c.get();
		if (!d.isPresent() || d.get().equals("")) cerca="0"; else cerca = d.get();
		if (!e.isPresent() || e.get().equals("")) adicion="0";else adicion  = e.get();
		if (!f.isPresent() || f.get().equals("")) dnpl="0";else dnpl = f.get();
		if (!g.isPresent() || g.get().equals("")) dnpc="0";else dnpc = g.get();
		if (!h.isPresent() || h.get().equals("")) avcc="0";else avcc = h.get(); 
		if (!i.isPresent() || i.get().equals("")) avsc="0";else avsc = i.get();		   
		
		beanGraduaciones.setOD_esfera(Double.parseDouble(esfera));
		beanGraduaciones.setOD_cilindro(Double.parseDouble(cilindro));
		beanGraduaciones.setOD_eje(Integer.parseInt(eje));
		beanGraduaciones.setOD_cerca(Double.parseDouble(cerca));
		beanGraduaciones.setOD_adicion(Double.parseDouble(adicion));
		beanGraduaciones.setOD_dnpl(Double.parseDouble(dnpl));
		beanGraduaciones.setOD_dnpc(Double.parseDouble(dnpc));
		beanGraduaciones.setOD_avcc(Double.parseDouble(avcc));
		beanGraduaciones.setOD_avsc(Double.parseDouble(avsc));
		
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
		
		beanGraduaciones.setOI_esfera(Double.parseDouble(esfera));
		beanGraduaciones.setOI_cilindro(Double.parseDouble(cilindro));
		beanGraduaciones.setOI_eje(Integer.parseInt(eje));
		beanGraduaciones.setOI_cerca(Double.parseDouble(cerca));
		beanGraduaciones.setOI_adicion(Double.parseDouble(adicion));
		beanGraduaciones.setOI_dnpl(Double.parseDouble(dnpl));
		beanGraduaciones.setOI_dnpc(Double.parseDouble(dnpc));
		beanGraduaciones.setOI_avcc(Double.parseDouble(avcc));
		beanGraduaciones.setOI_avsc(Double.parseDouble(avsc));	
		
		
		try {
			fechaEmision = dt.parse(graduacionesForm.getFechaEmision());
			fechaProxRevision = dt.parse(graduacionesForm.getFechaProxRevision());
		} catch (ParseException x) {			
			x.printStackTrace();
		}
		
		
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
				
		Optional<AgenteBean> e = graduacionesForm.getListaAgentes()				
			.stream().filter(s ->graduacionesForm.getAgente().equals(s.getUsuario())).findFirst();	
			
			if (e.isPresent()) agenteBean = e.get(); else agenteBean=null;
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
	
	@Command
	public void validaEsfera(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		
		Double mult = 0.25;
		Double cont = 0.00;		
		Double esfera= 0.00;	
		Double elementoadicion=0.00;		
			
		if(elemento != 0){
			
			esfera = elemento;
			
			if(esfera >= -30 && esfera <= 30){
				
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
					 
					 if(lado.equals("derecha")){
						 elementoadicion = beanGraduaciones.getOD_adicion();
						 validaAdicion(elementoadicion, lado);
						 
					 }else if(lado.equals("izquierda")){
						 elementoadicion = beanGraduaciones.getOI_adicion();
						 validaAdicion(elementoadicion, lado);
					 }
				}else{	
					
					 //elemento = esfera;	
					 
					 if(lado.equals("derecha")){
						 elementoadicion = beanGraduaciones.getOD_adicion();
						 validaAdicion(elementoadicion, lado);
						 
					 }else if(lado.equals("izquierda")){
						 elementoadicion = beanGraduaciones.getOI_adicion();
						 validaAdicion(elementoadicion, lado);
					 }
				}
			}else{
				Messagebox.show("El valor esfera "+lado+" esta fuera del rango permitido -30 y 30");
				elemento=0.00;
				
				if(lado.equals("derecha"))beanGraduaciones.setOD_esfera(0);
				else beanGraduaciones.setOI_esfera(0);
				return;
				//elemento.focus();
			}
		}else{
			esfera = 0.00;
			Messagebox.show("Debe ingresar valores entre -30 y 30");
			elemento=0.00;
			
			if(lado.equals("derecha"))beanGraduaciones.setOD_esfera(0);
			else beanGraduaciones.setOI_esfera(0);
			return;
			//elemento.value = parseFloat(esfera).toFixed(2);
			//elemento.focus();
		}		
	}
	
	
	@Command
	public void validaAdicion(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		Double esfera = 0.0;
		Double dnpl = 0.0;
		Double adicion =0.0;		
		
		adicion = elemento;		
		
		
		if(lado.equals("derecha")){	
			esfera = beanGraduaciones.getOD_esfera();			
			dnpl = beanGraduaciones.getOD_dnpl();
			
		}else if(lado.equals("izquierda")){
			esfera = beanGraduaciones.getOI_esfera();			
			dnpl = beanGraduaciones.getOI_dnpl();			
		}		
		
		if(esfera != 0){	
			
			if(lado.equals("derecha")){
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(adicion != 0){
					
					if(adicion >0 && adicion <=4){
						
						double cerca = adicion + esfera;
						
						//document.getElementById('OD_cerca').value = cerca.toFixed(2);						
						elemento = adicion;//.toFixed(2);
						dnpl = beanGraduaciones.getOD_dnpl();
						validaDNPL(dnpl, lado);
						
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");
							beanGraduaciones.setOD_adicion(0);
							return;
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
							beanGraduaciones.setOD_adicion(0);
							return;
						}
					}	
					
				}else{
					beanGraduaciones.setOD_cerca(0.00);				
					beanGraduaciones.setOD_dnpl(0.00);
					beanGraduaciones.setOD_dnpc(0.00);	
					return;
				}		
				
			}else if(lado.equals("izquierda")){
				
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(adicion != 0){
					
					if(adicion >0 && adicion <=4){
						
						double cerca = adicion + esfera;
						//document.getElementById('OI_cerca').value = cerca.toFixed(2);
						elemento = adicion; //.toFixed(2);
						dnpl = beanGraduaciones.getOI_dnpl();
						validaDNPL(dnpl, lado);
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");
							beanGraduaciones.setOI_adicion(0);
							return;
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
							beanGraduaciones.setOI_adicion(0);
							return;
						}
						
					}					
				}else{
					beanGraduaciones.setOI_cerca(0.00);
					beanGraduaciones.setOI_dnpl(0.00);
					beanGraduaciones.setOI_dnpc(0.00);
					return;
				}			
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");		
		}
	}
	
	
	@Command
	public boolean validaDNPL(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		Double dnpl = elemento;	
		Double adicion = 0.00;
		Double res=0.00;
		
		if(dnpl != 0){
			
			if(dnpl >= 20 && dnpl <= 40){
				
				//dnpl = parseFloat(elemento.value).toFixed(2);	
				
				if(lado.equals("derecha")){		
					
					res = dnpl - 1;
					adicion = beanGraduaciones.getOD_adicion();
			
					if(adicion != 0){
						beanGraduaciones.setOD_dnpc(res);
						//document.getElementById('OD_dnpc').value = parseFloat(res).toFixed(2);
					}else{
						beanGraduaciones.setOD_dnpc(0.00);
					}
					
					//elemento = parseFloat(dnpl).toFixed(2);
					elemento = dnpl;
					return true;
					
				}else if(lado.equals("izquierda")){
					
					res = dnpl - 1;				
					adicion = beanGraduaciones.getOI_adicion();
					
					if(adicion != 0){
						beanGraduaciones.setOI_dnpc(res);
						//document.getElementById('OI_dnpc').value = parseFloat(res).toFixed(2);
					}else{
						//document.getElementById('OI_dnpc').value="";
						beanGraduaciones.setOI_dnpc(0.00);
					}				
					
					//elemento.value = parseFloat(dnpl).toFixed(2);
					elemento = dnpl;
					return true;	
				}
			}else{			
				Messagebox.show("Distancia Naso pupilar, valor esta fuera de rango");
				
				if(lado.equals("derecha")){					
					beanGraduaciones.setOD_dnpc(0.00);
					
				}else if(lado.equals("izquierda")){
					beanGraduaciones.setOI_dnpc(0.00);
					
				}
				
				return false;	
			}
		}else{		
			if(lado.equals("derecha")){					
				beanGraduaciones.setOD_dnpc(0.00);
				
			}else if(lado.equals("izquierda")){
				beanGraduaciones.setOI_dnpc(0.00);
				
			}
			return false;	
		}	
		
		
		return false;	
	}


	@Command
	public void validaCilindro(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		Double cilindro=0.0; 	
		Double mult = 0.25;
		Double cont = 0.0;
		Double intCilindro=0.0;
			
		if(cilindro != 0){
				
				cilindro = elemento;
				
				if(cilindro >= -8 && cilindro <= 8){
					
					//cilindro = parseFloat(elemento.value).toFixed(2);
					
					if (cilindro%mult != 0){	
						
						 while((cilindro%mult != 0) && (cont < 55)){				 
							 
							 if(cilindro > 0){
								 cilindro = cilindro + 0.01F;
							 }else{
								 cilindro = cilindro + (-0.01F);
							 }
							 //cilindro = parseFloat(cilindro.toFixed(2));
							 cont++;
						 }				 
						 //elemento.value=cilindro.toFixed(2);
					}else{			
						 elemento = cilindro;			
					}
				}else{
					Messagebox.show("El valor cilindro "+lado+" esta fuera del rango permitido -8 y 8");
					elemento=0.00;
					
					if(lado.equals("derecho")){									
						beanGraduaciones.setOD_cilindro(0);
					}else {
						beanGraduaciones.setOI_cilindro(0);
					}	
					//elemento.focus();
				}
			}else{
				cilindro = 0.00;
				Messagebox.show("Debe ingresar valores entre -8 y 8");
				elemento = 0.00;
				//elemento.value = parseFloat(cilindro).toFixed(2);
				//elemento.focus();
			}
			
			intCilindro = cilindro;
			
			if(((intCilindro >= -8 && intCilindro < 0) || (intCilindro > 0 && intCilindro <= 8)) || (intCilindro >= -8.00 && intCilindro < 0.00) || (intCilindro > 0.00 && intCilindro <= 8.00) ){
				
				if(lado.equals("derecho")){				
					// document.getElementById('OD_eje').disabled =false;	
					
				}else if(lado.equals("izquierda")){
					//document.getElementById('OI_eje').disabled =false;
				}			
			}else{
				
				if(lado.equals("derecho")){	
					beanGraduaciones.setOD_eje(0);					
					//document.getElementById('OD_eje').disabled =true;		
					
				}else if(lado.equals("izquierda")){				
					beanGraduaciones.setOI_eje(0);
					//document.getElementById('OI_eje').disabled =true;
				}
			}			
	}
	
	
	@Command
	public void validaEje(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		Double eje = elemento;
		//boolean esnumero=false;
		
		if(eje != 0){		
			
			//esnumero = validarSiNumero(eje);
			
			//if(true == esnumero){
				if(eje < 0 || eje >180){
					Messagebox.show("El valor del eje "+lado+" esta fuera de rango [0..100]");
					eje = 0.00;
					elemento = 0.00;
					
				}
			/*}else{
				Messagebox.show("Debe ingresar solo numeros entre 0 y 180");
			}	*/	
		}	
	}
	
	
	@Command
	public void validacionCerca(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		Double esfera = 0.0;
		Double cerca = elemento;	
		Double adicion = 0.0;
		Double dnpl = 0.0;
		
		if(lado.equals("derecha")){	
			esfera = beanGraduaciones.getOD_esfera();			
			
		}else if(lado.equals("izquierda")){
			esfera = beanGraduaciones.getOI_esfera();
			
		}		
		
		if(esfera != 0){		
			if(lado.equals("derecha")){	
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(cerca !=0){
					
					if(cerca > esfera){
						
						adicion = cerca - esfera;
						beanGraduaciones.setOD_adicion(adicion);
						//document.getElementById('OD_adicion').value = adicion.toFixed(2);
						//elemento.value = parseFloat(cerca).toFixed(2);
						elemento=cerca;
						dnpl = beanGraduaciones.getOD_dnpl();
						validaDNPL(dnpl, lado);
						
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");
						beanGraduaciones.setOD_cerca(0);
						return;
					}	
					
				}		
				
			}else if(lado.equals("izquierda")){
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(cerca != 0){
					if(cerca > esfera){
						adicion = cerca - esfera;
						beanGraduaciones.setOI_adicion(adicion);
						//document.getElementById('OI_adicion').value = adicion.toFixed(2);
						//elemento.value = parseFloat(cerca).toFixed(2); 
						elemento=cerca;
						dnpl = beanGraduaciones.getOI_dnpl();
						validaDNPL(dnpl, lado);
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");
						beanGraduaciones.setOI_cerca(0);
						return;
					}				
				}	
				
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");
		}
		
	}

	
	@Command
	public boolean validaDNPC(@BindingParam("elemento")double elemento, @BindingParam("lado")String lado){
		
		Double dnpc = elemento;	
		Double adicion = 0.0;
		
		if(dnpc != 0){
			
			if(dnpc >= 20 && dnpc <= 40){
			
				if(lado.equals("derecha")){			
					adicion = beanGraduaciones.getOD_adicion();
					
					if(adicion == 0){
						Messagebox.show("Debe ingresar receta de cerca");
						beanGraduaciones.setOD_dnpc(0.0);						
						return false;
					}
					
				}else if("izquierda" == lado){
					
					adicion = beanGraduaciones.getOI_adicion();
					
					if(adicion == 0){
						Messagebox.show("Debe ingresar receta de cerca");
						beanGraduaciones.setOI_dnpc(0.0);						
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
		
		graduacionesForm.setOD_cantidad(String.valueOf(prismaCantidadOD.getCodigo()));
		graduacionesForm.setOI_cantidad(String.valueOf(prismaCantidadOI.getCodigo()));
		graduacionesForm.setOD_base(prismaBaseOD.getDescripcion());
		graduacionesForm.setOI_base(prismaBaseOI.getDescripcion());
		graduacionesForm.setAgente(agenteBean.getUsuario());
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
		
		agente = graduacionesForm.getAgente();
		
		if(agente.equals("Seleccione")){
			Messagebox.show("Debe Seleccionar agente");
			return false;		
		}
		
		
		
		//String fechaEmision = graduacionesForm.getFechaEmision().trim();	
		fecEmision = dt.format(fechaEmision);
		if(fecEmision.equals("")){
			Messagebox.show("Debe ingresar fecha emision");
			return false;
		}
		
		OD_cantidad = graduacionesForm.getOD_cantidad();
		OD_cantidad = OD_cantidad.trim();
		if(!OD_cantidad.equals("")  && !OD_cantidad.equals("-1") ){
			
			OD_base = graduacionesForm.getOD_base();
			
			if(OD_base.equals("")  || OD_base.equals("Seleccione") ){
				Messagebox.show("Debe seleccionar Base de Prisma Derecho");
				return false;
			}
			
		}
		
		OI_cantidad = graduacionesForm.getOI_cantidad();
		OI_cantidad = OI_cantidad.trim();
		if(!OI_cantidad.equals("")  && !OI_cantidad.equals("-1") ){
			
			OI_base = graduacionesForm.getOI_base();
			
			if(OI_base.equals("")  || OI_base.equals("Seleccione") ){
				Messagebox.show("Debe seleccionar Base de Prisma Izquierdo");
				return false;
			}
			
		}
		
		OD_base = graduacionesForm.getOD_base();
		if(!OD_base.equals("")  && !OD_base.equals("Seleccione") ){
			
			OD_cantidad = graduacionesForm.getOD_cantidad();		
			
			if(OD_cantidad.equals("")  || OD_cantidad.equals("-1") ){
				Messagebox.show("Debe seleccionar Cantidad de Prisma Derecho");
				return false;
			}
		}
		
		OI_base = graduacionesForm.getOI_base();
		if(!OI_base.equals("")  &&  OI_base.equals("Seleccione") ){
			
			OI_cantidad = graduacionesForm.getOI_cantidad();
			if(OI_cantidad.equals("")  || OI_cantidad.equals("-1") ){
				Messagebox.show("Debe seleccionar Cantidad de Prisma Izquierdo");
				return false;
			}
		}
		
		return true; //true si llega hasta aqui
	}


	public boolean validaGraduacion(){
		
		double esferaD = 0;
		double cilindroD = 0;
		double ejeD = 0;
		double adicionD=0;
		double OD_dnpl=0;
		
		double esferaI = 0;
		double cilindroI = 0;
		double ejeI = 0;
		double adicionI=0;
		double OI_dnpl=0;
		double intCilindro=0;
		
		/********* VALIDACIONES OJO DERECHO ********/
		esferaD = beanGraduaciones.getOD_esfera();		
		cilindroD = beanGraduaciones.getOD_cilindro();		
		ejeD = beanGraduaciones.getOD_eje();			
		adicionD = beanGraduaciones.getOD_adicion();	
		OD_dnpl = beanGraduaciones.getOD_dnpl();
				
		
		//if(esferaD != 0){
			if(esferaD < -30 || esferaD > 30){
				Messagebox.show("El valor esfera derecha esta fuera del rango permitido -30 y 30");
				return false;
			}		
		/*}else{
			Messagebox.show("Debe ingresar esfera derecha.");
			return false;
		}*/		
		
		//if(cilindroD != 0){		
			if(cilindroD < -8  || cilindroD > 8){
				Messagebox.show("El valor cilindro derecho esta fuera de rango");
				return false;
			}		
		/*}else{
			Messagebox.show("Debe ingresar valor del cilindro derecho");
			return false;
		}*/
		
		
		intCilindro = cilindroD;
		
		if(cilindroD != 0){
			if(ejeD == 0){
				Messagebox.show("Debe ingresar eje derecho");
				return false;
			}
		}
		
		
		if(ejeD != 0){
			if(cilindroD == 0){
				Messagebox.show("Debe ingresar cilindro derecho");
				return false;
			}
		}	
		
		if(ejeD != 0){
			if(ejeD < 0 || ejeD >180){
				Messagebox.show("El valor del eje derecho esta fuera de rango");
				return false;
			}
		}
		
		
		if(adicionD > 0){
			if(adicionD < 0.50 || adicionD > 4){
				Messagebox.show("El valor de la adicion derecha esta fuera de rango");
				return false;
			}
		}else {
			Messagebox.show("El Valor de la adicion no puede ser menor o igual a 0");
			return false;
		}
		
		if(OD_dnpl == 0){
			Messagebox.show("Debe ingresar distancia naso pupilar derecha.");
			return false;
		}else{
			boolean respuesta = validaDNPL(beanGraduaciones.getOD_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}
		
		
		
		/********* FIN VALIDACIONES OJO DERECHO ********/
		
		
		
		/********* VALIDACIONES OJO IZQUIERDO ********/
		
		esferaI = beanGraduaciones.getOI_esfera();		
		cilindroI = beanGraduaciones.getOI_cilindro();	
		ejeI = beanGraduaciones.getOI_eje();		
		adicionI = beanGraduaciones.getOI_adicion();		
		OI_dnpl = beanGraduaciones .getOI_dnpl();
		
		
		//if(esferaI != 0){
			if(esferaI < -30 || esferaI >30){
				Messagebox.show("El valor esfera izquierda esta fuera de rango");
				return false;
			}	
		/*}else{
			Messagebox.show("Debe ingresar esfera izquierda.");
			return false;
		}*/
		
		//if(cilindroI != 0){		
			if(cilindroI < -8  || cilindroI > 8){
				Messagebox.show("El valor cilindro izquierdo esta fuera de rango");
				return false;
			}		
		/*}else{
			Messagebox.show("Debe ingresar valor del cilindro izquierdo");
			return false;
		}*/
		
		if(cilindroI != 0){
			if(ejeI == 0){
				Messagebox.show("Debe ingresar eje izquierdo");
				return false;
			}
		}	
		
		if(ejeI != 0){
			if(cilindroI == 0){
				Messagebox.show("Debe ingresar cilindro izquierdo");
				return false;
			}
		}
		if(ejeI != 0){
			if(ejeI < 0 || ejeI >180){
				Messagebox.show("El valor del eje izquierdo esta fuera de rango");
				return false;
			}
		}
		
		
		if(adicionI != 0){
			if(adicionI <= 0 || adicionI > 4){
				Messagebox.show("El valor de la adicion izquierda esta fuera de rango");
				return false;
			}
		}
		
		if(OI_dnpl == 0){
			Messagebox.show("Debe ingresar distancia naso pupilar izquierda.");
			return false;
		}else{
			boolean respuesta = validaDNPL(beanGraduaciones.getOI_dnpl(), "izquierda");
			if(respuesta == false){
				return false;
			}
		}
		
		boolean respuestadnpc = validaDNPC(beanGraduaciones.getOD_dnpc(), "derecha");
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
		
		
		if(fProxRevision.equals("")){
			Messagebox.show("Debe ingresar fecha de revisi\u00F3n");
			return false;
		}
		
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

	public BeanGraduaciones getBeanGraduaciones() {
		return beanGraduaciones;
	}

	public void setBeanGraduaciones(BeanGraduaciones beanGraduaciones) {
		this.beanGraduaciones = beanGraduaciones;
	}

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}

	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}

	
	
	
	
}
