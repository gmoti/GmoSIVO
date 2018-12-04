package cl.gmo.pos.venta.controlador;



import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.controlador.general.ClienteDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.TipoViaBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.forms.ClienteForm;

public class ControllerCliente implements Serializable{
	
	
	private static final long serialVersionUID = 6987458990189549075L;
	
	ClienteForm clienteForm;	
	BusquedaClientesForm busquedaClientesForm;
	ClienteBean cliente;
	ClienteDispatchActions clid = new ClienteDispatchActions();	
	BusquedaClientesDispatchActions busquedaClientes;
	
	Session sess = Sessions.getCurrent();	
	HashMap<String,Object> objetos;

	private String usuario;	
	private String sucursalDes;	
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	

	private boolean bfechaNac=false;
	private boolean bDireccion=false;
	private boolean bNumero=false;
	private boolean bProvincia=false;
	private boolean bEmail=false;
	private boolean bTelefono=false;
	private boolean bMovil=false;
	
	private Date fechaNac;
	private boolean bDisableinicial;
	private boolean bDisableinicialRut;
	
	private AgenteBean agenteBean;
	private TipoViaBean tipoViaBean;
	private ProvinciaBean provinciaBean;
	
	private static String[] codTelefono= {"02","32","33","34","35","41","42","43","45","51","52","53","55","57","58","61","63","64","65","67","71","72","73","75"};
 	private ListModelList modelo = new ListModelList(Arrays.asList(codTelefono));
	@Init	
	public void inicial() {		
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		busquedaClientes = new BusquedaClientesDispatchActions();
		clienteForm = new ClienteForm();
		
		//fechaNac = new Date(System.currentTimeMillis());
		bDisableinicial = false;
		bDisableinicialRut = false;		
		
		agenteBean = new AgenteBean();
		tipoViaBean = new TipoViaBean();
		provinciaBean = new ProvinciaBean();
		 
		clienteForm = clid.cargaFormulario(clienteForm, sess);	
	}
	
	
	//====================================================
	//========= Funciones principales tool bar============
	//====================================================		
	
	@Command
	@NotifyChange({"clienteForm","fechaNac"})
	public void ingresarCliente()  {
		
		
		 if (!validacionGeneral())
			 return;		
		
		clienteForm.setAccion("ingresoCliente");	
		clid.ingresoCliente(clienteForm, sess);	
		
		switch (clienteForm.getExito()) {
		case Constantes.STRING_ACTION_MODIFICADO:
			Messagebox.show("Se modifico exitosamente el Cliente.");
			break;
			
		case Constantes.STRING_ACTION_EXISTE:
			Messagebox.show("Se ingreso exitosamente el Cliente.");
			break;
			
		case Constantes.STRING_FALSE:
			Messagebox.show("No se pudo Ingresar el Cliente.");
			break;		

		default:
			Messagebox.show("Informacion " + clienteForm.getExito());
			break;
		}	
	}
	
	
	public boolean validacionGeneral() {
		
		boolean bRet=true;
		
		Optional<Date> fn = Optional.ofNullable(fechaNac);
		Optional<AgenteBean> ab = Optional.ofNullable(agenteBean);		
		Optional<String> tvb = Optional.ofNullable(tipoViaBean.getCodigo());
		Optional<String> pb = Optional.ofNullable(provinciaBean.getCodigo());		
		Optional<String> r = Optional.ofNullable(clienteForm.getRut());		
		Optional<String> nom = Optional.ofNullable(clienteForm.getNombres());
		Optional<String> ape = Optional.ofNullable(clienteForm.getApellidos());		
		
		Optional<String> loc = Optional.ofNullable(clienteForm.getLocalidad());
		Optional<String> con = Optional.ofNullable(clienteForm.getContacto());
		Optional<String> ema = Optional.ofNullable(clienteForm.getEmail());
		Optional<String> pro = Optional.ofNullable(clienteForm.getProfesion());
		
		
		if (!r.isPresent() || clienteForm.getRut().trim().equals("") || clienteForm.getRut().trim().equals("0")) {
			Messagebox.show("Debe indicar el rut");
		    return false;		
		}
		
		if(!nom.isPresent() || nom.get().trim().equals("")) {
			Messagebox.show("Debe indicar el Nombre del cliente");
			return false;
		}else {
			clienteForm.setNombres(nom.get());
		}
				
		if(!ape.isPresent() || ape.get().trim().equals("")) {
			Messagebox.show("Debe indicar el Apellido del cliente");
			return false;
		}else {
			clienteForm.setApellidos(ape.get());
		}	
		
		if(fn.isPresent()) 
			clienteForm.setFnacimiento(dt.format(fechaNac));
		
		if(!ab.isPresent() || ab.get().getUsuario().equals("Seleccione") || ab.get().getUsuario().equals("")) {
			Messagebox.show("Debe indicar un agente");
			return false;			
		}else	
			clienteForm.setAgente(agenteBean.getUsuario());
		
		
		if(!tvb.isPresent() || tvb.get().equals(""))	
			clienteForm.setTipo_via("0");
		else	
			clienteForm.setTipo_via(tipoViaBean.getCodigo());
		 
		
		if(!pb.isPresent() || pb.get().equals("")) {
			clienteForm.setProvincia_cliente("0");
			Messagebox.show("Debe indicar la provincia o comuna");
			return false;
		}
		else
			clienteForm.setProvincia_cliente(provinciaBean.getCodigo());
		
		
		if(!loc.isPresent() || loc.get().equals("")) {
			clienteForm.setLocalidad("");
			Messagebox.show("Debe indicar la localidad");
			return false;
		}
		else
			clienteForm.setLocalidad(loc.get());
		
		
		if(!con.isPresent() || con.get().equals("")) {
			clienteForm.setContacto("");
			Messagebox.show("Debe indicar un contacto");
			return false;
		}
		else
			clienteForm.setContacto(con.get());
		
		
		if(!ema.isPresent() || ema.get().equals("")) {
			clienteForm.setEmail("");
			Messagebox.show("Debe indicar un Email");
			return false;
		}
		else
			clienteForm.setEmail(ema.get());
		
		
		if(!pro.isPresent() || pro.get().equals("")) {
			clienteForm.setProfesion("");
			Messagebox.show("Debe indicar una Profesion");
			return false;
		}
		else
			clienteForm.setProfesion(pro.get());
		
		if(clienteForm.getMk_correo_postal().equals("false") &&
			clienteForm.getMk_correo_electronico().equals("false") &&
			clienteForm.getMk_sms().equals("false") &&
			clienteForm.getMk_telefonia().equals("false") &&
			clienteForm.getMk_nodata().equals("false")) {
			
			Messagebox.show("Debe indicar un tipo de marketing");
			return false;
		}
		
		
		
		return bRet;
	}
	
	
	@Command
	public void cerrar(@BindingParam("arg1")  Window x) {
	    x.detach();
	}
	
	@Command
	@NotifyChange({"clienteForm","fechaNac","bDisableinicialRut"})
	public void nuevoCliente(){
		
		clienteForm.setAccion("nuevo_cliente");
		clid.ingresoCliente(clienteForm, sess);
		//fechaNac = new Date(System.currentTimeMillis());
		fechaNac =null;
		bDisableinicialRut = false;
	}
	
	@Command
	@NotifyChange({"clienteForm","fechaNac","bDisableinicial","bDisableinicialRut"})
	public void modificarCliente() {
		
		bDisableinicial = false;
		bDisableinicialRut = false;		
	}
	
	@Command
	public void clienteGraduacion() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("origen","cliente");
		objetos.put("cliente",cliente);
		
		Window winGraduacion = (Window)Executions.createComponents(
                "/zul/mantenedores/GraduacionClientes.zul", null, objetos);
		
		winGraduacion.doModal();
		
	}
	
	
	//========================================
	//======== Funciones secundarias =========
	//========================================
	@Command
	@NotifyChange({"*"})
	public void buscar()  {
		
		busquedaClientesForm = new BusquedaClientesForm();
		
		sess.setAttribute("nif",clienteForm.getRut());
		sess.setAttribute("pagina","");		
		
		try {
			cliente = busquedaClientes.buscarClienteAjax(busquedaClientesForm, sess);
			
			clienteForm.setCodigo(Integer.parseInt(cliente.getCodigo()));
			clienteForm.setDv(cliente.getDvnif());			
			
			clienteForm.setAccion("traeClienteSeleccionado");
			clienteForm.setNif_cliente_agregado(cliente.getNif());
			clienteForm.setCodigo_cliente_agregado(cliente.getCodigo());
			
			clid.ingresoCliente(clienteForm, sess);
			
			clienteForm.setFnacimiento(cliente.getFecha_nac());
			clienteForm.setNombres(cliente.getNombre());
			clienteForm.setApellidos(cliente.getApellido());
			
			Optional<String> f = Optional.ofNullable(cliente.getFecha_nac());
			
			if(f.isPresent())			
				fechaNac =  dt.parse(cliente.getFecha_nac());
			else
				fechaNac = null;
			
			Optional<String> a = Optional.ofNullable(clienteForm.getAgente());				
			clienteForm.setAgente(a.orElse(""));		
			
			Optional<String> b = Optional.ofNullable(clienteForm.getTipo_via());		
			clienteForm.setTipo_via(b.orElse(""));		
			
			Optional<String> c = Optional.ofNullable(clienteForm.getProvincia_cliente());	
			clienteForm.setProvincia_cliente(c.orElse(""));					
			
			posicionaCombos();
			
			bDisableinicial = true;
			bDisableinicialRut = true;
			
		} catch (IOException | ParseException e) {			
			e.printStackTrace();
		}
		
		
	}	
	
	
	@Command
	@NotifyChange({"*"})
	public void buscarRemitente()  {
		
		sess.setAttribute("nif",clienteForm.getRemitente());
		sess.setAttribute("pagina","");	
		
		try {
			ClienteBean clien = busquedaClientes.buscarClienteAjax(busquedaClientesForm, sess);
			
			clienteForm.setRemitente(clien.getNif());
			clienteForm.setDvFactura(clien.getDvnif());
			clienteForm.setNombre_cliente_factura(clien.getNombre() + " " + clien.getApellido());
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void posicionaCombos() {			
		
		Optional<AgenteBean> a = clienteForm.getListaAgentes().stream().filter(s -> clienteForm.getAgente().equals(s.getUsuario())).findFirst();		
		if (a.isPresent()) 
			agenteBean = a.get();
		else
			agenteBean = null;
		
		Optional<TipoViaBean> b = clienteForm.getListaTipoVia().stream().filter(s -> clienteForm.getTipo_via().equals(s.getCodigo())).findFirst();		
		if (b.isPresent())
			tipoViaBean = b.get();
		else
			tipoViaBean = null;
		
		Optional<ProvinciaBean> c = clienteForm.getListaProvincia().stream().filter(s -> clienteForm.getProvincia_cliente().equals(s.getCodigo())).findFirst();		
		if (c.isPresent())
			provinciaBean = c.get();
		else
			provinciaBean = null;
		
	}
	
	@NotifyChange({"clienteForm"})
	@Command
	public boolean validarRut(@BindingParam("persona")String persona) {
		
		int rutAux,m,s = 0;
		char dv;
		String rut;
		
		if(persona.equals("cliente"))
			rut = clienteForm.getRut();
		else
			rut = clienteForm.getRemitente();		
		 
		boolean validacion = false;
		try {
			rut =  rut.toUpperCase();
			rut = rut.replace(".", "");
			rut = rut.replace("-", "");
			rutAux = Integer.parseInt(rut);
			 
			dv = rut.charAt(rut.length() - 1);
			 
			m = 0; s = 1;
			
			for (; rutAux != 0; rutAux /= 10) {
				s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}
			
			if(persona.equals("cliente"))
				clienteForm.setDv(String.valueOf((char) (s != 0 ? s + 47 : 75)));
			else
				clienteForm.setDvFactura(String.valueOf((char) (s != 0 ? s + 47 : 75)));
			
			/*if (dv == (char) (s != 0 ? s + 47 : 75)) {
				validacion = true;
				clienteForm.setDv(String.valueOf(dv));
			}*/
		 
		} catch (java.lang.NumberFormatException e) {
		} catch (Exception e) {
		}
		return validacion;
	}
	
	
	@Command
	public void busquedaCliente(@BindingParam("retorno")String retorno) {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();		
		objetos.put("retorno",retorno);		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	
	
	@NotifyChange({"clienteForm","bDisableinicial","bDisableinicialRut","agenteBean","tipoViaBean","provinciaBean"})
	@GlobalCommand
	public void buscarClienteCliente(@BindingParam("cliente")ClienteBean cliente) {
		
		clienteForm.setAccion("traeClienteSeleccionado");
		clienteForm.setNif_cliente_agregado(cliente.getNif());
		clienteForm.setCodigo_cliente_agregado(cliente.getCodigo());
		
		clid.ingresoCliente(clienteForm, sess);		
		
		Optional<String> a = Optional.ofNullable(clienteForm.getAgente());				
		clienteForm.setAgente(a.orElse(""));	
		
		Optional<String> b = Optional.ofNullable(clienteForm.getTipo_via());		
		clienteForm.setTipo_via(b.orElse(""));	
		
		Optional<String> c = Optional.ofNullable(clienteForm.getProvincia_cliente());	
		clienteForm.setProvincia_cliente(c.orElse(""));	
		
		posicionaCombos();
		
		bDisableinicial = true;
		bDisableinicialRut = true;
		
	}
	
	@NotifyChange({"clienteForm"})
	@GlobalCommand
	public void buscarClienteRemitente(@BindingParam("cliente")ClienteBean cliente) {
		
		ClienteForm remitente = new ClienteForm();
		
		remitente.setAccion("traeClienteSeleccionado");
		remitente.setNif_cliente_agregado(cliente.getNif());
		remitente.setCodigo_cliente_agregado(cliente.getCodigo());		
		
		clid.ingresoCliente(remitente, sess);
		
		//asigno variables
		clienteForm.setNombre_cliente_factura(cliente.getNombre() + " " + cliente.getApellido());
		clienteForm.setRemitente(cliente.getNif());
		clienteForm.setDvFactura(cliente.getDvnif());
		
		//bDisableinicial = true;
		//bDisableinicialRut = true;
		
	}
	
	@NotifyChange({"clienteForm"})
	@Command
	public void buscarGiro() {
		
		Optional<String> g = Optional.ofNullable(clienteForm.getGiro());
		Optional<String> dg= Optional.ofNullable(clienteForm.getDescripcionGiro());
		
		clienteForm.setGiro(g.orElse("").toUpperCase());
		clienteForm.setDescripcionGiro(dg.orElse("").toUpperCase());	
	
		clienteForm.setAccion("busqueda");
		clid.busquedaGiro(clienteForm, sess);
		
	}	
	
	@NotifyChange({"clienteForm"})
	@Command
	public void seleccionaGiro(@BindingParam("giro")GiroBean giro, @BindingParam("win")Popup win) {
		
		clienteForm.setGiro(giro.getCodigo());
		clienteForm.setDescripcionGiro(giro.getDescripcion());
		win.close();
	}
	
	@NotifyChange({"clienteForm"})
	@Command
	public void buscarGiroAjax()
	{
	   String  giroID = clienteForm.getGiro().trim();      
	   
		if(!giroID.equals("")){
			
			clienteForm.setAccion("traeGiroSeleccionadoFactura");	
			sess.setAttribute("giroID",clienteForm.getGiro().trim());
			
		   clid.traeGiroSeleccionadoFactura(clienteForm, sess);
		   
		   if(clienteForm.getGiro().equals("")) {			  
			   Messagebox.show("El giro no existe.");
			   clienteForm.setGiro("");
			   clienteForm.setDescripcionGiro("");
		   }	   	   
		   
	   }else{
	   	Messagebox.show("Debe ingresar código de giro");
	   }
	}
	
	
	//Getter and Setter ======================
	//========================================
	
	
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

	public ClienteForm getClienteForm() {
		return clienteForm;
	}

	public void setClienteForm(ClienteForm clienteForm) {
		this.clienteForm = clienteForm;
	}	

	public Date getFechaNac() {
		return fechaNac;
	}
	
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public boolean isBfechaNac() {
		return bfechaNac;
	}

	public void setBfechaNac(boolean bfechaNac) {
		this.bfechaNac = bfechaNac;
	}

	public boolean isbDireccion() {
		return bDireccion;
	}

	public void setbDireccion(boolean bDireccion) {
		this.bDireccion = bDireccion;
	}

	public boolean isbNumero() {
		return bNumero;
	}

	public void setbNumero(boolean bNumero) {
		this.bNumero = bNumero;
	}

	public boolean isbProvincia() {
		return bProvincia;
	}

	public void setbProvincia(boolean bProvincia) {
		this.bProvincia = bProvincia;
	}

	public boolean isbEmail() {
		return bEmail;
	}

	public void setbEmail(boolean bEmail) {
		this.bEmail = bEmail;
	}

	public boolean isbTelefono() {
		return bTelefono;
	}

	public void setbTelefono(boolean bTelefono) {
		this.bTelefono = bTelefono;
	}

	public boolean isbMovil() {
		return bMovil;
	}

	public void setbMovil(boolean bMovil) {
		this.bMovil = bMovil;
	}

	public boolean isbDisableinicial() {
		return bDisableinicial;
	}

	public void setbDisableinicial(boolean bDisableinicial) {
		this.bDisableinicial = bDisableinicial;
	}

	public boolean isbDisableinicialRut() {
		return bDisableinicialRut;
	}

	public void setbDisableinicialRut(boolean bDisableinicialRut) {
		this.bDisableinicialRut = bDisableinicialRut;
	}

	public ListModelList getModelo() {
		return modelo;
	}

	public void setModelo(ListModelList modelo) {
		this.modelo = modelo;
	}


	public AgenteBean getAgenteBean() {
		return agenteBean;
	}


	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}


	public TipoViaBean getTipoViaBean() {
		return tipoViaBean;
	}


	public void setTipoViaBean(TipoViaBean tipoViaBean) {
		this.tipoViaBean = tipoViaBean;
	}


	public ProvinciaBean getProvinciaBean() {
		return provinciaBean;
	}


	public void setProvinciaBean(ProvinciaBean provinciaBean) {
		this.provinciaBean = provinciaBean;
	}
	
	
	
	
}
