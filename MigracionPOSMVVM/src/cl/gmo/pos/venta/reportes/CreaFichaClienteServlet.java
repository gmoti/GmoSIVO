package cl.gmo.pos.venta.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.helper.ReportesHelper;

public class CreaFichaClienteServlet  extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{
	
	
	private static final long serialVersionUID = 1978167766706639522L;
	Logger log = Logger.getLogger( this.getClass() );
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		log.info("CreaFichaClienteServlet:service  inicio");
		String cdg =(String) request.getParameter(Constantes.STRING_ACTION_CDG);
		String cliente  = (String)request.getParameter(Constantes.STRING_CLIENTE);
		new ReportesHelper().creaFichaCliente(session, response, cliente);
		log.info("CreaFichaClienteServlet:service  fin");
	}

}
