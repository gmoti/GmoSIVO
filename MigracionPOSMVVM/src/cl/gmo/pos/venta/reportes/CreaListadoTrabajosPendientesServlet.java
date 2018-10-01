package cl.gmo.pos.venta.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.helper.ReportesHelper;

public class CreaListadoTrabajosPendientesServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8277089872667025973L;
	Logger log = Logger.getLogger( this.getClass() );
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		log.info("CreaListadoTrabajosPendientesServlet:service  inicio");		
		new ReportesHelper().creaListadoTranajosPendientes(session, response, request);
		log.info("CreaListadoTrabajosPendientesServlet:service  fin");
	}
}
