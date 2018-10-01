package cl.gmo.pos.venta.controlador;



import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.reporte.nuevo.ListadoTotalDiaDAOImpl;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ListasTotalesDiaBean;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;

public class ControllerListadoTotalDia implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3623703810372235414L;

	Session sess = Sessions.getCurrent();
	
	@Wire("#test")
	private Window win;
	
	private AMedia fileContent;
	private Date fecha=null;
	private String local;
	private String nombreSucural;
	private byte[] bytes;
	
	private ListadoTotalDiaDAOImpl listadoTotalDiasImpl;
	private ListasTotalesDiaBean listasTotalesDiaBean;
	private ReportesHelper reportesHelper;
	
	
   @AfterCompose
   public void initSetup(@ContextParam(ContextType.VIEW) Component view)	             {
     Selectors.wireComponents(view, this, false);
     
   }
	
	
	@Init
	public void inicial()  { 		
		fecha = new Date(System.currentTimeMillis());
		listadoTotalDiasImpl = new ListadoTotalDiaDAOImpl();
		reportesHelper = new ReportesHelper();		
		local = (String) sess.getAttribute("sucursal");	
		nombreSucural = (String)sess.getAttribute("nombreSucural");		
	}
	
	

	@NotifyChange("fileContent")
	@Command
	public void reporte() {  	
		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaReporte = dt.format(fecha);
		sess.setAttribute(Constantes.STRING_ACTION_LISTA_VENTA_FECHA, fechaReporte);
		sess.setAttribute(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA_TOTAL, fechaReporte);
		sess.setAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL, nombreSucural);
		//System.out.println("fecha "  + fechaReporte);
		
		try {
			listasTotalesDiaBean = listadoTotalDiasImpl.traeListasTotales(fechaReporte, local);
			bytes = reportesHelper.creaListadoTotalDia(listasTotalesDiaBean);		
					
			final AMedia media = new AMedia("prueba.pdf", "pdf", "application/pdf", bytes);			
			
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
	

}
