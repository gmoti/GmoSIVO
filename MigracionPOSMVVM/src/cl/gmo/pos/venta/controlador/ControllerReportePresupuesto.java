package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;

public class ControllerReportePresupuesto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2716248868828378546L;

	Session sess = Sessions.getCurrent();
	
	private AMedia fileContent;	
	private byte[] bytes;
	private ReportesHelper reportesHelper; 
	
	@Wire("#reporte5")
	private Window win;
	
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)	             {
	     Selectors.wireComponents(view, this, false);
	     
	}
	
	
	@Init
	public void iniciar() {
		
		reportesHelper = new ReportesHelper();
		bytes= reportesHelper.creaPresupuesto(sess);
		
		final AMedia media = new AMedia("prueba.pdf", "pdf", "application/pdf", bytes);	
		fileContent = media;
	}
	
	
	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

}
