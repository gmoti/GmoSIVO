package cl.gmo.pos.venta.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.helper.ReportesHelper;

public class CreaGuiaServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6289097683962292477L;
	Logger log = Logger.getLogger( this.getClass() );
	

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			
			log.info("CreaGuiaServlet:service  inicio");
			new ReportesHelper().creaGuia(session, response);
			log.info("CreaGuiaServlet:service  fin");
	 }
}
