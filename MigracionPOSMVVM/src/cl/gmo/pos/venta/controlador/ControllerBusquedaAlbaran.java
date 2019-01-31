package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.DevolucionDispatchActions;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;

public class ControllerBusquedaAlbaran implements Serializable {
	
	private static final long serialVersionUID = -41251263156926000L;
	Session sess = Sessions.getCurrent();
	
	private String usuario;	
	private String sucursalDes;
	HashMap<String,Object> objetos;
	
	private Date fechaAlbaran;
	private AgenteBean agenteBean;
	
	private DevolucionForm devolucionForm;
	private DevolucionDispatchActions devolucionDispatch;
	
	SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat tt = new SimpleDateFormat("hh:mm:ss");
	
	
	@Init
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);	
		
		//fechaAlbaran = new Date(System.currentTimeMillis());
		agenteBean = new AgenteBean();
		
		devolucionForm = new DevolucionForm();
		devolucionDispatch = new DevolucionDispatchActions();	
		
		
		devolucionDispatch.cargaBuscarAlbaran(devolucionForm, sess);
	}
	
	
	@NotifyChange({"devolucionForm"})
	@Command
	public void buscarAlbaranes(){
		
		boolean respuesta = false;
		String codigo1="";
		String codigo2="";
		String nif="";
		String fecha="";
		String agente="";
		String cdg="";
		
		devolucionForm.setAgente(agenteBean.getUsuario());		
		
		codigo1 = devolucionForm.getCodigo1();
		codigo2 = devolucionForm.getCodigo2();
		nif = devolucionForm.getNif();		
		agente = devolucionForm.getAgente();
		
		Optional<Date> f = Optional.ofNullable(fechaAlbaran);
		if(f.isPresent()) {
			fecha = dt.format(fechaAlbaran);
			devolucionForm.setFecha(dt.format(fechaAlbaran));			
		}else {
			fecha="";
			devolucionForm.setFecha(fecha);
		}				
		
		if(!codigo1.equals("") && !codigo2.equals("")){
			cdg = codigo1 +"/"+codigo2;
		}
		
		if(cdg.equals("") && nif.equals("") && fecha.equals("") && agente.equals("")){
			Messagebox.show("Debe ingresar al menos un campo para realizar la busqueda");
			respuesta = true;
		}else{
			respuesta = false;
		}
		
		if(respuesta==false){		
			devolucionDispatch.buscarAlbaran(devolucionForm, sess);			
		}
	}
	
	@Command
	public void selecAlbaran(@BindingParam("albaran")AlbaranBean albaran,
							 @BindingParam("win")Window win) {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("albaran",albaran);
		
		BindUtils.postGlobalCommand(null, null,"albaranSeleccionado" ,objetos);
		
		win.detach();
	}

	
	
	//=== Getter and Setter =======================
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

	public DevolucionForm getDevolucionForm() {
		return devolucionForm;
	}

	public void setDevolucionForm(DevolucionForm devolucionForm) {
		this.devolucionForm = devolucionForm;
	}

	public Date getFechaAlbaran() {
		return fechaAlbaran;
	}

	public void setFechaAlbaran(Date fechaAlbaran) {
		this.fechaAlbaran = fechaAlbaran;
	}

	public AgenteBean getAgenteBean() {
		return agenteBean;
	}

	public void setAgenteBean(AgenteBean agenteBean) {
		this.agenteBean = agenteBean;
	}
	

}
