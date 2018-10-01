package cl.gmo.pos.venta.reporte.dispatch;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.facade.PosSeleccionPagoFacade;
import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;
import cl.gmo.pos.venta.web.helper.CreaCopiaGuiaBoletaHelper;


public class CopiaGuiaBoletaDispatchActions {

	Logger log = Logger.getLogger( this.getClass() );
	CreaCopiaGuiaBoletaHelper helper = new CreaCopiaGuiaBoletaHelper();
	
	public CopiaGuiaBoletaForm cargaFormulario(CopiaGuiaBoletaForm form, Session request)
	{
		
		log.info("CopiaGuiaBoletaDispatchActions:cargaFormulario inicio");
		CopiaGuiaBoletaForm formulario = (CopiaGuiaBoletaForm)form;
		formulario.setNumeroBoleta(Constantes.STRING_CERO);
		log.info("CopiaGuiaBoletaDispatchActions:cargaFormulario fin");
		//return mapping.findForward(Constantes.STRING_ACTION_COPIA_GUIA_BOLETA);
		return formulario;
	}
	
	public CopiaGuiaBoletaForm validaDocuento(CopiaGuiaBoletaForm form,
			Session request) throws IOException
	{
		log.info("CopiaGuiaBoletaDispatchActions:validaDocuento inicio");
		CopiaGuiaBoletaForm formulario = (CopiaGuiaBoletaForm)form;
		//String numero = request.getParameter("numeroBoleta").toString();
		//String tipo = request.getParameter("tipo").toString();
		
		String numero = request.getAttribute("numeroBoleta").toString();
		String tipo = request.getAttribute("tipo").toString();
		
		Session session = request;
		ArrayList<BoletaBean> boleta= new ArrayList<BoletaBean>();
		String numerotipodoc="";
		
	
		formulario.setNumeroBoleta(numero);
		formulario.setDocumento(tipo);
		System.out.println("tipo doc  ==> "+tipo);
		boleta = PosSeleccionPagoFacade.reimpresionBoletaelec(tipo, numero,session.getAttribute(Constantes.STRING_SUCURSAL).toString());
		if(tipo.equals("B")){
			numerotipodoc ="39";
		}else if(tipo.equals("N")){
			numerotipodoc ="NC/61";
		}		
		
		System.out.println("numerotipodoc ==> "+numerotipodoc);
		/*for(BoletaBean b : boleta){			
			  System.out.println("Copia de guias y boletas ===>"+numerotipodoc+" "+b.getNif()+"-"+b.getDv()+" "+b.getNumero());
			  response.getWriter().print(numerotipodoc+" "+b.getNif()+"-"+b.getDv()+" "+b.getNumero());		
	    }*/
		
		log.info("CopiaGuiaBoletaDispatchActions:validaDocuento fin");
		//return null;
		return formulario;
		
				
	}
}
