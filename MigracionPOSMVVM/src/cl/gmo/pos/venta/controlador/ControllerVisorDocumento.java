package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;


public class ControllerVisorDocumento implements Serializable {

	
	private static final long serialVersionUID = 7151833775352526075L;
	
	private String fileContent;
	private String title;
	
	
	@Init
	public void inicial(@ExecutionArgParam("documento")String url,
			@ExecutionArgParam("titulo")String titulo) {	
		
		fileContent = url;
		title = titulo;
		
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
