package cl.gmo.pos.venta.controlador;



import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.controlador.general.ClienteDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;


import cl.gmo.pos.venta.web.beans.ClienteBean;
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

	private String usuario;	
	private String sucursalDes;	
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");	
	
	private boolean cpostal = false;
	private boolean cemail =false;
	private boolean csms =false;
	private boolean cnodata =false;
	private boolean ctelefonia =false;
	
	private boolean bfechaNac=false;
	private boolean bDireccion=false;
	private boolean bNumero=false;
	private boolean bProvincia=false;
	private boolean bEmail=false;
	private boolean bTelefono=false;
	private boolean bMovil=false;
	
	private Date fechaNac;
	
	

	String  sprovincia="Selecciona Provinicia";
	String  sagente="Seleccione Agente";
	String  stipovia="Seleccione Tipo Via";	

	
	@Init	
	public void inicial() {		
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		busquedaClientes = new BusquedaClientesDispatchActions();
		
		fechaNac = new Date(System.currentTimeMillis());
		
		//clif = new ClienteForm();  
		clienteForm = new ClienteForm();	 
		//cliente = new ClienteBean();	 
		 
		clienteForm = clid.cargaFormulario(clienteForm, sess);	
	}
	
	
	@Command
	@NotifyChange({"clienteForm","fechaNac"})
	public void buscar()  {
		
		busquedaClientesForm = new BusquedaClientesForm();
		
		sess.setAttribute("nif",clienteForm.getRut());
		sess.setAttribute("pagina","");		
		
		try {
			cliente = busquedaClientes.buscarClienteAjax(busquedaClientesForm, sess);
			
			clienteForm.setDv(cliente.getDvnif());
			clienteForm.setFnacimiento(cliente.getFecha_nac());
			clienteForm.setNombres(cliente.getNombre());
			clienteForm.setApellidos(cliente.getApellido());			
			fechaNac =  dt.parse(cliente.getFecha_nac());
			
		} catch (IOException | ParseException e) {			
			e.printStackTrace();
		}
		
		
		/*
		if(clif.getCodigo() != 0) {
			this.setDv(clif.getDv());
			this.setApellidos(clif.getApellidos());
			this.setCodigo(clif.getCodigo());
			this.setNombres(clif.getNombres());
			this.setVia(clif.getVia());
			this.setNumero(clif.getNumero());
			this.setLocalidad(clif.getLocalidad());
			this.setEmail(clif.getEmail());
			this.setTelefono_movil(clif.getTelefono_movil());
			this.setProfesion(clif.getProfesion());
			this.setSexo(clif.getSexo());
			if(clif.getMk_correo_postal().equals("1")) {this.setCpostal(true);}
			if(clif.getMk_correo_electronico().equals("1")) {this.setCemail(true);}
			if(clif.getMk_sms().equals("1")) {this.setCsms(true);}
			if(clif.getMk_telefonia().equals("1")) {this.setCtelefonia(true);}
			if(clif.getMk_nodata().equals("1")) {this.setCnodata(true);}
			
			if(clif.getSexo().equals("H")) {this.setRhombre(true);}
			if(clif.getSexo().equals("M")) {this.setRmujer(true);}
			if(clif.getSexo().equals("I")) {this.setRempresa(true);}
			
			this.getListaTipoVia().forEach(t->{
				if(t.getCodigo().equals(clif.getTipo_via())){
					this.setTipo_via(t.getCodigo());
					this.setStipovia(t.getDescripcion());
					}
			});
			this.getListaAgentes().forEach(a->{
				if(a.getUsuario().equals(clif.getAgente())) {
					this.setAgente(a.getUsuario());
					this.setSagente(a.getUsuario());
				}
			});
			
			this.getListaProvincia().forEach(p->{
					if(p.getCodigo().equals(clif.getProvincia_cliente())) {
						this.setProvincia_cliente(p.getCodigo());
						this.setSprovincia(p.getDescripcion());
					}
				}
			);
			
			this.setTelefono(clif.getTelefono());
		}
			*/

	}
	
	@Command
	@NotifyChange({"clienteForm","fechaNac"})
	public void ingresarCliente()  {
		
		clienteForm.setAccion("ingresoCliente");
		clienteForm.setFnacimiento(dt.format(fechaNac));
		
		clid.ingresoCliente(clienteForm, sess);
		
		
		
		/*
		String fechaNac = (this.getFechaNac() != null) ? util.formatoFecha(this.getFechaNac()): "";
		
		if(this.getCpostal()) { cliform.setMk_correo_postal("1");}else{cliform.setMk_correo_postal("-1");}
		if(this.isCemail()) {cliform.setMk_correo_electronico("1");}else{cliform.setMk_correo_electronico("-1");}
		if(this.isCsms()) {cliform.setMk_sms("1");}else{cliform.setMk_sms("-1");}
		if(this.isCtelefonia()) {cliform.setMk_telefonia("1");}else{cliform.setMk_telefonia("-1");}
		if(this.isCnodata()) {cliform.setMk_nodata("1");}else{cliform.setMk_nodata("-1");}
		
		if(this.isRhombre()) {cliform.setSexo("H");}
		if(this.isRmujer()) {cliform.setSexo("M");}
		if(this.isRempresa()) {cliform.setSexo("I");}
		cliform.setProvincia(Integer.valueOf(this.getSprovincia()));
		cliform.setFnacimiento(fechaNac);
		cliform.setAccion("ingresoCliente");
		
		clid.ingresoCliente(cliform,sess);
		
		if(cliform.getExito().equals(Constantes.STRING_ACTION_MODIFICADO)) {
			Messagebox.show("Se modifico exitosamente el Cliente.");
		}
		if(cliform.getExito().equals(Constantes.STRING_ACTION_EXISTE)) {
			Messagebox.show("Se ingreso exitosamente el Cliente.");
		}
		if(cliform.getExito().equals(Constantes.STRING_FALSE)) {
			Messagebox.show("No se pudo Ingresar el Cliente.");
		}
		*/
	}
	
	@Command
	public void cerrar(@BindingParam("arg1")  Window x) {
	    x.detach();
	}
	
	@Command
	@NotifyChange({"clienteForm","fechaNac"})
	public void nuevoCliente(){
		
		clienteForm.setAccion("nuevo_cliente");
		clid.ingresoCliente(clienteForm, sess);
		fechaNac = new Date(System.currentTimeMillis());		
	}
	
	@Command
	@NotifyChange({"clienteForm","fechaNac"})
	public void modificarCliente() {
		
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
	
	
	
}
