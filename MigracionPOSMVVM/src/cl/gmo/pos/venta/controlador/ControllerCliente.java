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
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.controlador.general.ClienteDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
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
		
		fechaNac = new Date(System.currentTimeMillis());
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
		
		clienteForm.setAccion("ingresoCliente");
		clienteForm.setFnacimiento(dt.format(fechaNac));
		clienteForm.setAgente(agenteBean.getUsuario());
		clienteForm.setVia(tipoViaBean.getCodigo());
		clienteForm.setProvincia_cliente(provinciaBean.getCodigo());		
		
		clid.ingresoCliente(clienteForm, sess);		
		
		if(clienteForm.getExito().equals(Constantes.STRING_ACTION_MODIFICADO)) {
			Messagebox.show("Se modifico exitosamente el Cliente.");
		}
		if(clienteForm.getExito().equals(Constantes.STRING_ACTION_EXISTE)) {
			Messagebox.show("Se ingreso exitosamente el Cliente.");
		}
		if(clienteForm.getExito().equals(Constantes.STRING_FALSE)) {
			Messagebox.show("No se pudo Ingresar el Cliente.");
		}
		
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
	@NotifyChange({"clienteForm","fechaNac","bDisableinicial","bDisableinicialRut"})
	public void modificarCliente() {
		
		bDisableinicial = false;
		bDisableinicialRut = false;		
	}
	
	@Command
	public void clienteGraduacion() {
		
		Window winGraduacion = (Window)Executions.createComponents(
                "/zul/mantenedores/GraduacionClientes.zul", null, null);
		
		winGraduacion.doModal();
		
	}
	
	
	//========================================
	//======== Funciones secundarias =========
	//========================================
	@Command
	@NotifyChange({"clienteForm","fechaNac","bDisableinicial","bDisableinicialRut","agenteBean","tipoViaBean","provinciaBean"})
	public void buscar()  {
		
		busquedaClientesForm = new BusquedaClientesForm();
		
		sess.setAttribute("nif",clienteForm.getRut());
		sess.setAttribute("pagina","");		
		
		try {
			cliente = busquedaClientes.buscarClienteAjax(busquedaClientesForm, sess);
			
			clienteForm.setCodigo(Integer.parseInt(cliente.getCodigo()));
			clienteForm.setDv(cliente.getDvnif());
			clienteForm.setFnacimiento(cliente.getFecha_nac());
			clienteForm.setNombres(cliente.getNombre());
			clienteForm.setApellidos(cliente.getApellido());			
			fechaNac =  dt.parse(cliente.getFecha_nac());
			
			//validaciones pendientes
			
			clienteForm.setAccion("traeClienteSeleccionado");
			clienteForm.setNif_cliente_agregado(cliente.getNif());
			clienteForm.setCodigo_cliente_agregado(cliente.getCodigo());
			
			clid.ingresoCliente(clienteForm, sess);
			
			posicionaCombos();
			
			bDisableinicial = true;
			bDisableinicialRut = true;
			
		} catch (IOException | ParseException e) {			
			e.printStackTrace();
		}
		
		
	}	
	
	private void posicionaCombos() {
		
		Optional<AgenteBean> a = clienteForm.getListaAgentes().stream().filter(s -> clienteForm.getAgente().equals(s.getUsuario())).findFirst();		
		agenteBean = a.get();	
		
		Optional<TipoViaBean> b = clienteForm.getListaTipoVia().stream().filter(s -> clienteForm.getTipo_via().equals(s.getCodigo())).findFirst();		
		tipoViaBean = b.get();
		
		Optional<ProvinciaBean> c = clienteForm.getListaProvincia().stream().filter(s -> clienteForm.getProvincia_cliente().equals(s.getCodigo())).findFirst();		
		provinciaBean = c.get();
		
	}
	
	@NotifyChange({"clienteForm"})
	@Command
	public boolean validarRut() {
		
		int rutAux,m,s = 0;
		char dv;
		String rut = clienteForm.getRut();
		 
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
			
			clienteForm.setDv(String.valueOf((char) (s != 0 ? s + 47 : 75)));
			
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
	public void busquedaCliente() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteCliente");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	
	
	@NotifyChange({"clienteForm","bDisableinicial","bDisableinicialRut"})
	@GlobalCommand
	public void buscarClienteCliente(@BindingParam("cliente")ClienteBean cliente) {
		
		clienteForm.setAccion("traeClienteSeleccionado");
		clienteForm.setNif_cliente_agregado(cliente.getNif());
		clienteForm.setCodigo_cliente_agregado(cliente.getCodigo());
		
		clid.ingresoCliente(clienteForm, sess);
		
		posicionaCombos();
		
		bDisableinicial = true;
		bDisableinicialRut = true;
		
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
