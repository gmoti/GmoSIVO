package cl.gmo.pos.venta.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.helper.CreaCopiaGuiaBoletaHelper;


public class CreaCopiaGuiaBoletaServlet  extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{


	private static final long serialVersionUID = 6256452217167611347L;
	Logger log = Logger.getLogger( this.getClass() );
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("CreaCopiaGuiaBoletaServlet:service  inicio");
		String tipo =(String) request.getParameter(Constantes.STRING_TIPO);
		String numero =(String) request.getParameter(Constantes.STRING_NUMERO);

 		new CreaCopiaGuiaBoletaHelper().traeCopiaGuiaBoleta(numero,tipo,response);
 		log.info("CreaCopiaGuiaBoletaServlet:service  fin");
	}


	
}
