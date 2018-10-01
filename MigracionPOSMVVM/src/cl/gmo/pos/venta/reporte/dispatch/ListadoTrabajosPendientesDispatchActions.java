package cl.gmo.pos.venta.reporte.dispatch;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.reporte.nuevo.ListadoTrabajosPendientesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.ListadoTrabajosPendientesForm;


public class ListadoTrabajosPendientesDispatchActions {
	
	ListadoTrabajosPendientesHelper helper = new ListadoTrabajosPendientesHelper();
	Logger log = Logger.getLogger( this.getClass() );
	public void cargaInicial(ListadoTrabajosPendientesForm formulario, Session session)
	{
		log.info("ListadoTrabajosPendientesDispatchActions:cargaInicial  inicio");

		log.info("ListadoTrabajosPendientesDispatchActions:cargaInicial  fin");
		helper.cargaInicial(formulario, session);
	}
	public void Limpia(ListadoTrabajosPendientesForm formulario)
	{
		formulario.setDivisa(Constantes.STRING_CERO);
		formulario.setLocal(Constantes.STRING_CERO);
	}
	public ListadoTrabajosPendientesForm cargaFormulario(ListadoTrabajosPendientesForm form, Session request)
	{
		log.info("ListadoTrabajosPendientesDispatchActions:Limpia  inicio");
		ListadoTrabajosPendientesForm formulario = (ListadoTrabajosPendientesForm)form;
		Session session = request;
		this.cargaInicial(formulario, session);
		this.Limpia(formulario);
		log.info("ListadoTrabajosPendientesDispatchActions:Limpia  fin");
		
		//return mapping.findForward(Constantes.FORWARD_LISTADO_TRABAJOS_PENDIENTES);
		return formulario;
	}
	
	public ListadoTrabajosPendientesForm buscar(ListadoTrabajosPendientesForm form, Session request)
	{
		log.info("ListadoTrabajosPendientesDispatchActions:buscar  inicio");
		Session session =request;
		String sucursal = (String)session.getAttribute(Constantes.STRING_SUCURSAL);
		ListadoTrabajosPendientesForm formulario = (ListadoTrabajosPendientesForm)form;
		helper.cargaListadosTrabajosPendientes(formulario, sucursal);
		this.cargaInicial(formulario, session);
		this.Limpia(formulario);
		session.setAttribute(Constantes.STRING_ACTION_LISTA_FECHA_BUSQUEDA, formulario.getFechaPedidoIni()+" - "+formulario.getFechaPedidoTer());
		session.setAttribute(Constantes.STRING_CERRADO, formulario.getCerrado());
		session.setAttribute(Constantes.STRING_ACTION_LISTA_PENDIENTES, formulario.getListaPendientes());
		log.info("ListadoTrabajosPendientesDispatchActions:buscar  fin");
		//return mapping.findForward(Constantes.FORWARD_LISTADO_TRABAJOS_PENDIENTES);
		return formulario;
	}
}
