package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.apache.commons.validator.UrlValidator;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;


public class ControllerVisorDocumento implements Serializable {

	
	private static final long serialVersionUID = 7151833775352526075L;
	
	private String fileContent;
	private String title;
	private String urlRecibida;
	private boolean exito=false;
	
	
	@Init
	public void inicial(@ExecutionArgParam("documento")String url,
			@ExecutionArgParam("titulo")String titulo) {	
		
		
		urlRecibida = url;
		
		verificaArchivo();
		
		fileContent = url;
		title = titulo;
		
	}
	
	
	public void verificaArchivo() {
		
		UrlValidator validar = new UrlValidator();
		
		while (1==1) {	
			
			try {
				Thread.sleep(8000);
				break;
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}			
		}
		
		
	}


	public String getFileContent() {
		return fileContent;
	}


	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	

}
