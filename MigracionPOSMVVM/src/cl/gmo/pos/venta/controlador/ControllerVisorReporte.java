package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.media.AMedia;


public class ControllerVisorReporte implements Serializable {

	
	private static final long serialVersionUID = 4396054285266878836L;
	private AMedia fileContent;
	private String title;
	
	
	@Init
	public void inicial(@ExecutionArgParam("reporte")AMedia reporte,
			@ExecutionArgParam("titulo")String titulo) {	
		
		fileContent = reporte;
		title = titulo;
		
	}


	public AMedia getFileContent() {
		return fileContent;
	}


	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	

}
