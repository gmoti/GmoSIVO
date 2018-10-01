package cl.gmo.pos.venta.controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.reporte.dispatch.CopiaGuiaBoletaDispatchActions;
import cl.gmo.pos.venta.reporte.nuevo.CreaCopiaGuiaBoletaHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;




public class ControllerCopiaGuiaBoleta implements Serializable {

	
	private static final long serialVersionUID = 4489999255615790601L;
	Session sess = Sessions.getCurrent();
	
	private String documento;
	private String numeroBoleta;
	private CopiaGuiaBoletaForm copiaGuiaBoletaForm;
	private CopiaGuiaBoletaDispatchActions copiaGuiaBoletaDispatchActions;
	
	HashMap<String,Object> objetos;
	
	
	@Init
	public void inicial() {
		
		documento = ""; 
		numeroBoleta = "";   
		copiaGuiaBoletaForm = new CopiaGuiaBoletaForm();
		copiaGuiaBoletaDispatchActions = new CopiaGuiaBoletaDispatchActions();
	
	}
	
	
	@NotifyChange({"documento","numeroBoleta"})
	@Command
	public void reporte() {
		
		CreaCopiaGuiaBoletaHelper reporte = new CreaCopiaGuiaBoletaHelper();
		
		if (documento.equals("")) {			
			Messagebox.show("Debe seleccionar un tipo de documento");
			return;
		}
		
		if (numeroBoleta.equals("")) {
			Messagebox.show("Debe seleccionar un tipo de documento");
			return;
		}
		
		
		try {
			
			sess.setAttribute("numeroBoleta",numeroBoleta);
			sess.setAttribute("tipo",documento);
			
			copiaGuiaBoletaDispatchActions.validaDocuento(copiaGuiaBoletaForm, sess);			
			
			//String tipo =(String) request.getParameter(Constantes.STRING_TIPO);
			//String numero =(String) request.getParameter(Constantes.STRING_NUMERO);

			byte[] bytes = reporte.traeCopiaGuiaBoleta(numeroBoleta, documento);
			
			final AMedia media = new AMedia("CopiaBoleta.pdf", "pdf", "application/pdf", bytes);			
			
			objetos = new HashMap<String,Object>();
			objetos.put("reporte",media);
			objetos.put("titulo","Copia Boleta - Guia");			
			
			Window windowVisorReporte = (Window)Executions.createComponents(
	                "/zul/reportes/VisorReporte.zul", null, objetos);
			
			windowVisorReporte.doModal();
			
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
	}


	//=============== getter and setter ===================
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNumeroBoleta() {
		return numeroBoleta;
	}

	public void setNumeroBoleta(String numeroBoleta) {
		this.numeroBoleta = numeroBoleta;
	}


	public CopiaGuiaBoletaForm getCopiaGuiaBoletaForm() {
		return copiaGuiaBoletaForm;
	}

	public void setCopiaGuiaBoletaForm(CopiaGuiaBoletaForm copiaGuiaBoletaForm) {
		this.copiaGuiaBoletaForm = copiaGuiaBoletaForm;
	}	

}
