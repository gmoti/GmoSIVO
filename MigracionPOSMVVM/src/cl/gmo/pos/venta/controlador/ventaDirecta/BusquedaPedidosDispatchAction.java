package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.util.ArrayList;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.forms.BusquedaPedidosForm;
import cl.gmo.pos.venta.web.helper.BusquedaPedidosHelper;

public class BusquedaPedidosDispatchAction {
	
	BusquedaPedidosHelper helper = new BusquedaPedidosHelper();
	Logger log = Logger.getLogger(this.getClass());

	public BusquedaPedidosDispatchAction() {
	}
	
	private void carga_inicial(BusquedaPedidosForm formulario, Session session)
	{
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.setLista_agentes(helper.traeAgentes_Local(local));
	}
	
	public BusquedaPedidosForm carga_formulario(BusquedaPedidosForm form, Session request) {
		
		log.info("BusquedaPedidosDispatchAction:carga_formulario inicio");
		BusquedaPedidosForm  formulario = (BusquedaPedidosForm)form;
		Session session = request;
		this.carga_inicial(formulario, session);
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
		log.info("BusquedaPedidosDispatchAction:carga_formulario fin");
		//return mapping.findForward(Constantes.STRING_ACTION_BUSQUEDA_PEDIDO); 
		return formulario;
	}
	
	public BusquedaPedidosForm buscar(BusquedaPedidosForm form, Session request) {
				
		log.info("BusquedaPedidosDispatchAction:buscar inicio");
		BusquedaPedidosForm  formulario = (BusquedaPedidosForm)form;
		Session session = request;
		
		this.carga_inicial(formulario, session);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		helper.traePedidos(formulario, local, session);
		log.info("BusquedaPedidosDispatchAction:buscar  fin");
		//return mapping.findForward(Constantes.STRING_ACTION_BUSQUEDA_PEDIDO); 
		return formulario;
	}

}
