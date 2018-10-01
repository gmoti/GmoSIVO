package cl.gmo.pos.venta.controlador;


import java.io.Serializable;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.reporte.nuevo.InformeOpticoHelper;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.web.forms.InformeOpticoForm;


public class ControllerInformeOptico implements Serializable{
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 3911700019027519574L;

Session sess = Sessions.getCurrent();
	
	@Wire("#reporte2")
	private Window win;
	
	private AMedia fileContent;	
	private String nif;
	private String local;
	private String nombreSucural;
	private byte[] bytes;
	
    private InformeOpticoHelper informeOpticoHelper;
    private InformeOpticoForm informeOpticoForm;
    private ReportesHelper reportesHelper;
    
   
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)	             {
	     Selectors.wireComponents(view, this, false);
	     
	}
	
	@Init
	public void inicial()  { 		
		
		informeOpticoHelper = new InformeOpticoHelper();
		informeOpticoForm = new InformeOpticoForm();
		reportesHelper = new ReportesHelper(); 
		
		local = (String) sess.getAttribute("sucursal");	
		nombreSucural = (String)sess.getAttribute("nombreSucural");	
		
		informeOpticoForm.setCdgCli("120000770");
		informeOpticoForm.setNombreCli("Francisco");
	}
	
	@Command
	public void buscar() {
		
	}
	
	
	@NotifyChange("fileContent")
	@Command
	public void reporte() {  		
		
		try {		
			
			informeOpticoForm = informeOpticoHelper.traeInformeOptico(informeOpticoForm.getCdgCli(), null, null, informeOpticoForm);
			sess.setAttribute("InformeOptico", informeOpticoForm);			
			bytes = reportesHelper.creaListadoOptico(sess);
					
			final AMedia media = new AMedia("prueba.pdf", "pdf", "application/pdf", bytes);			
			
			fileContent = media;			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public InformeOpticoForm getInformeOpticoForm() {
		return informeOpticoForm;
	}

	public void setInformeOpticoForm(InformeOpticoForm informeOpticoForm) {
		this.informeOpticoForm = informeOpticoForm;
	}
	


}
