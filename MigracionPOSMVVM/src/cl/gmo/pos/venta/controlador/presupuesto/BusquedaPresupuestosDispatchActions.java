package cl.gmo.pos.venta.controlador.presupuesto;


import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.forms.BusquedaPresupuestosForm;



public class BusquedaPresupuestosDispatchActions{

	BusquedaPresupuestosHelper helper = new BusquedaPresupuestosHelper();
	Logger log = Logger.getLogger(this.getClass());

	public BusquedaPresupuestosDispatchActions() {
	}
	
	private void carga_inicial(BusquedaPresupuestosForm formulario, Session session)
	{
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.setLista_agentes(helper.traeAgentes_Local(local));
	}
	
	public BusquedaPresupuestosForm carga_formulario(BusquedaPresupuestosForm form, Session request) {
		
		log.info("BusquedaPresupuestosDispatchActions:carga_formulario inicio");
		BusquedaPresupuestosForm  formulario = (BusquedaPresupuestosForm)form;
		//HttpSession session = request.getSession(true);
		Session session = request;
		this.carga_inicial(formulario, session);
		//String flujo = request.getParameter("flujo");
		String flujo = request.getAttribute("flujo").toString();
		
		if (!flujo.equals("nuevo")) {
			ArrayList<AgenteBean> agentes = new ArrayList<AgenteBean>();
			AgenteBean agent = new AgenteBean();
			agent.setUsuario(session.getAttribute(Constantes.STRING_USUARIO).toString());
			agent.setNombre_completo(session.getAttribute(Constantes.STRING_USUARIO).toString());
			agentes.add(agent);
			formulario.setLista_agentes(agentes);
		}
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_BLANCO);
		log.info("BusquedaPresupuestosDispatchActions:carga_formulario fin");
		//return mapping.findForward(Constantes.STRING_ACTION_BUSQUEDA_PRESUPUESTO);
		return formulario;
	}
	
	public BusquedaPresupuestosForm buscar(BusquedaPresupuestosForm form, Session request) {
				
		log.info("BusquedaPresupuestosDispatchActions:buscar inicio");
		BusquedaPresupuestosForm  formulario = (BusquedaPresupuestosForm)form;
		//HttpSession session = request.getSession(true);
		Session session = request;
		
		this.carga_inicial(formulario, session);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		helper.traePedidos(formulario, local, session);
		log.info("BusquedaPresupuestosDispatchActions:buscar  fin");
		//return mapping.findForward(Constantes.STRING_ACTION_BUSQUEDA_PRESUPUESTO); 
		return formulario;
	}

}
