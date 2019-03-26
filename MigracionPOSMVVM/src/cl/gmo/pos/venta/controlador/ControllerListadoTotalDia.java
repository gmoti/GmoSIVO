package cl.gmo.pos.venta.controlador;



import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import cl.gmo.pos.venta.reporte.nuevo.ListadoTotalDiaDAOImpl;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ListasTotalesDiaBean;
import org.zkoss.bind.annotation.Init;

public class ControllerListadoTotalDia implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3623703810372235414L;

	Session sess = Sessions.getCurrent();
	
	private String usuario;	
	private String sucursalDes;	
	
	private AMedia fileContent;
	private Date fecha=null;
	private String local;
	private String nombreSucural;
	private byte[] bytes;
	
	private ListadoTotalDiaDAOImpl listadoTotalDiasImpl;
	private ListasTotalesDiaBean listasTotalesDiaBean;
	private ReportesHelper reportesHelper;	
	
	
	@Init
	public void inicial()  { 		
		//fecha = new Date(System.currentTimeMillis());
		listadoTotalDiasImpl = new ListadoTotalDiaDAOImpl();
		reportesHelper = new ReportesHelper();	
		
		local = sess.getAttribute(Constantes.STRING_SUCURSAL).toString();	
		//nombreSucural = sess.getAttribute("nombreSucural").toString();	
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
	}
	
	

	@NotifyChange("fileContent")
	@Command
	public void reporte() {  	
		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaReporte = dt.format(fecha);
		sess.setAttribute(Constantes.STRING_ACTION_LISTA_VENTA_FECHA, fechaReporte);
		sess.setAttribute(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA_TOTAL, fechaReporte);
		sess.setAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL, sucursalDes);
		//System.out.println("fecha "  + fechaReporte);
		
		try {
			listasTotalesDiaBean = listadoTotalDiasImpl.traeListasTotales(fechaReporte, local);
			bytes = reportesHelper.creaListadoTotalDia(listasTotalesDiaBean);		
					
			final AMedia media = new AMedia("ListadoTotalDia.pdf", "pdf", "application/pdf", bytes);			
			
			fileContent = media;			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
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
