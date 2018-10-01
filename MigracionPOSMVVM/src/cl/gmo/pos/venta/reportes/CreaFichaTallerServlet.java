package cl.gmo.pos.venta.reportes;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;
import cl.gmo.pos.venta.web.helper.FichaTecnicaHelper;
import cl.gmo.pos.venta.web.helper.ReportesHelper;



public class CreaFichaTallerServlet  extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	private static final long serialVersionUID = -1145964391330295954L;
	Logger log = Logger.getLogger( this.getClass() );
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String cdg =(String) request.getParameter(Constantes.STRING_ACTION_CDG);
		String cliente  = (String)request.getParameter(Constantes.STRING_CLIENTE);
		String saldo = (String)request.getParameter("saldo");
		int clienteint=0;
		int saldoint= 0;
		if(null != cliente){
			try{
				clienteint = Integer.parseInt(cliente);
			}catch(Exception ex){
				clienteint = 0;
			}			
		}
		if(null != saldo){
			try{
				saldoint = Integer.parseInt(saldo);
			}catch(Exception ex){
				saldoint = 0;
			}			
		}
		
		
		ArrayList<FichaTecnicaBean> lista = new FichaTecnicaHelper().traeFichaTaller(cdg, clienteint, saldoint);
		//ArrayList<FichaTecnicaBean> lista = new FichaTecnicaHelper().traeFichaTaller("19/0082635",410021795);
		log.info("CreaFichaTallerServlet:service  inicio");
		
		String file = request.getSession().getServletContext().getRealPath("");
		try {
			new ReportesHelper().creaFichaTaller(session, response, lista, file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("CreaFichaTallerServlet:service  fin");
	}

}
