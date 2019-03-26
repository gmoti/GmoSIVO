package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import cl.gmo.pos.venta.reporte.dispatch.ListadoBoletasDispatchActions;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.ListadoBoletasForm;


public class ControllerListadoBoleta implements Serializable{

	private static final long serialVersionUID = 7856414447863092650L;

	Session sess = Sessions.getCurrent();
	
	private AMedia fileContent;	
	private String nif;
	private String local;
	private String nombreSucural;
	private String usuario;	
	private String sucursalDes;	
	private byte[] bytes;
	
	private ReportesHelper reportesHelper; 
	private ListadoBoletasDispatchActions listadoBoletasDispatchActions;
	private ListadoBoletasForm listadoBoletasForm;
	private Date fecha;	
	
	
	@Init
	public void inicial()  { 		
		
		local = (String) sess.getAttribute("sucursal");	
		nombreSucural = (String)sess.getAttribute("nombreSucural");	
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		listadoBoletasForm = new ListadoBoletasForm();
		listadoBoletasDispatchActions = new ListadoBoletasDispatchActions();
		reportesHelper = new ReportesHelper();
	}
	
	@NotifyChange({"fileContent","fecha"})
	@Command
	public void reporte() {
		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaReporte = dt.format(fecha);
		
		listadoBoletasForm.setFecha_inicio(fechaReporte);		
		listadoBoletasForm = listadoBoletasDispatchActions.buscar(listadoBoletasForm, sess);
		
		bytes= reportesHelper.creaListadoBoletas(sess);
		
		final AMedia media = new AMedia("ListadoBoletas.pdf", "pdf", "application/pdf", bytes);			
		
		fileContent = media;
		
	}
	

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	public ListadoBoletasForm getListadoBoletasForm() {
		return listadoBoletasForm;
	}

	public void setListadoBoletasForm(ListadoBoletasForm listadoBoletasForm) {
		this.listadoBoletasForm = listadoBoletasForm;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

}
