package cl.gmo.pos.venta.reportes;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.helper.ReportesHelper;

public class CreaBoletaServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	Logger log = Logger.getLogger( this.getClass() );
	private static final long serialVersionUID = -6028635143174424321L;
	 protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			log.info("CreaBoletaServlet:service  inicio");
			try {
				new ReportesHelper().creaBoleta(session, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("CreaBoletaServlet:service  fin");
	 }
	}
