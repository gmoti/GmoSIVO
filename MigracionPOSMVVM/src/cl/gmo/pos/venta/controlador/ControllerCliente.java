package cl.gmo.pos.venta.controlador;



import java.io.Serializable;
import java.sql.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.ClienteDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;

import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.forms.ClienteForm;

public class ControllerCliente extends ClienteForm  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6987458990189549075L;
	ClienteForm clif ;
	ClienteBean cliente;
	ClienteDispatchActions clid = new ClienteDispatchActions();
	Utils util = new Utils();
	Session sesion = Sessions.getCurrent();

	
	boolean cpostal = false;
	boolean cemail =false;
	boolean csms =false;
	boolean cnodata =false;
	boolean ctelefonia =false;
	boolean rhombre=false;
	boolean rmujer=false;
	boolean rempresa=false;
	private Date fechaNac =null;
	

	String  sprovincia="Selecciona Provinicia";
	String  sagente="Seleccione Agente";
	String  stipovia="Seleccione Tipo Via";
	

	
	@Init	
	public void inicial() {
		
		
		 clif = new ClienteForm();  
		
		 clif = clid.cargaInicial("T002");
		 cliente = new ClienteBean();
		 this.setListaAgentes(clif.getListaAgentes());
		 this.setListaProvincia(clif.getListaProvincia());
		 this.setListaTipoVia(clif.getListaTipoVia());

	}
	
	
	@Command
	@NotifyChange({"dv","apellidos","codigo","nombres","via","numero","localidad","email","telefono","telefono_movil","cpostal","ctelefonia","csms","cemail","cnodata","rhombre","rmujer","rempresa","tipo_via","provincia_cliente","agente","sagente","stipovia","sprovincia"})
	public void buscar(@BindingParam("arg1")String arg1)  {
		
		clif = clid.buscarClienteAjax(arg1);
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
			

	}
	
	@Command
	@NotifyChange({"*"})
	public void ingresarCliente(@BindingParam("arg")ClienteForm cliform)  {
		
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
		
		clid.ingresoCliente(cliform,sesion);
		
		if(cliform.getExito().equals(Constantes.STRING_ACTION_MODIFICADO)) {
			Messagebox.show("Se modifico exitosamente el Cliente.");
		}
		if(cliform.getExito().equals(Constantes.STRING_ACTION_EXISTE)) {
			Messagebox.show("Se ingreso exitosamente el Cliente.");
		}
		if(cliform.getExito().equals(Constantes.STRING_FALSE)) {
			Messagebox.show("No se pudo Ingresar el Cliente.");
		}
		
	}
	
	@Command
	public void cerrar(@BindingParam("arg1")  Window x) {
	    x.detach();
	}
	
	@Command
	@NotifyChange({"*"})
	public void nuevoUsuario(@BindingParam("arg1")String arg1){
		clif.cleanForm();
	}
	
	public boolean getCpostal() {
		return cpostal;
	}
	public void setCpostal(boolean cpostal) {
		this.cpostal = cpostal;
	}
	public boolean isCemail() {
		return cemail;
	}
	public void setCemail(boolean cemail) {
		this.cemail = cemail;
	}
	public boolean isCsms() {
		return csms;
	}
	public void setCsms(boolean csms) {
		this.csms = csms;
	}
	public boolean isCnodata() {
		return cnodata;
	}
	public void setCnodata(boolean cnodata) {
		this.cnodata = cnodata;
	}
	public boolean isCtelefonia() {
		return ctelefonia;
	}
	public void setCtelefonia(boolean ctelefonia) {
		this.ctelefonia = ctelefonia;
	}
	
	public boolean isRhombre() {
		return rhombre;
	}

	public void setRhombre(boolean rhombre) {
		this.rhombre = rhombre;
	}

	public boolean isRmujer() {
		return rmujer;
	}

	public void setRmujer(boolean rmujer) {
		this.rmujer = rmujer;
	}

	public boolean isRempresa() {
		return rempresa;
	}

	public void setRempresa(boolean rempresa) {
		this.rempresa = rempresa;
	}
	
	public String getSprovincia() {
		return sprovincia;
	}

	public void setSprovincia(String sprovincia) {
		this.sprovincia = sprovincia;
	}

	public String getSagente() {
		return sagente;
	}

	public void setSagente(String sagente) {
		this.sagente = sagente;
	}

	public String getStipovia() {
		return stipovia;
	}

	public void setStipovia(String stipovia) {
		this.stipovia = stipovia;
	}
	
	public ClienteBean getCliente() {
		return cliente;
	}
	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}


	public ClienteForm getClif() {
		return clif;
	}


	public void setClif(ClienteForm clif) {
		this.clif = clif;
	}
	
	
}
