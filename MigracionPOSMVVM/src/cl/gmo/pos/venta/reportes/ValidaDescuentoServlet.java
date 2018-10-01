package cl.gmo.pos.venta.reportes;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.helper.VentaPedidoHelper;

public class ValidaDescuentoServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	/*
	 *
	 * 
	 */
	private static final long serialVersionUID = 4325449129820715984L;
	Logger log = Logger.getLogger(this.getClass());

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("ValidaDescuentoServlet:service  inicio");
		String usuario = request.getParameter(Constantes.STRING_USUARIO).toString();
		String pass = request.getParameter(Constantes.STRING_PASS).toString();
		String tipo = request.getParameter(Constantes.STRING_TIPO).toString();
		
		BigDecimal descuento;
		if (!tipo.equals(Constantes.STRING_CERO))
		{
			 descuento = new VentaPedidoHelper().traeDecuento(usuario, pass, tipo);
		}
		else
		{
			descuento = new VentaPedidoHelper().traeDecuento(usuario, pass, null);
		}
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		System.out.println("Descuento ==>"+descuento.toString());
		response.getWriter().write(descuento.toString());
		log.info("ValidaDescuentoServlet:service  fin");
	}
	
}
