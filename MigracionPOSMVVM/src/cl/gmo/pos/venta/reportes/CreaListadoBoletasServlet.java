package cl.gmo.pos.venta.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.helper.ReportesHelper;



public class CreaListadoBoletasServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	/**
	 *
	 */
	private static final long serialVersionUID = -3178996433258841444L;
	Logger log = Logger.getLogger( this.getClass() );
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		log.info("CreaListadoBoletasServlet:service  inicio");
		new ReportesHelper().creaListadoBoletas(session, response);
		log.info("CreaListadoBoletasServlet:service  fin");
	}

}
