package cl.gmo.pos.venta.reporte.dispatch;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.reporte.nuevo.ListadoPresupuestosHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.ListadoPresupuestosForm;


public class ListadoPresupuestosDispatchActions {
	Logger log = Logger.getLogger( this.getClass() );
	ListadoPresupuestosHelper helper = new ListadoPresupuestosHelper();

	public ListadoPresupuestosDispatchActions() {
	}
	
	public void cargaInicial(ListadoPresupuestosForm formulario, String local, Session session)
	{
		log.info("ListadoPresupuestosDispatchActions:cargaInicial Inicial inicio");
		helper.traeDatosFormulario(formulario, session);
		log.info("ListadoPresupuestosDispatchActions:cargaInicial Inicial fin");
	}
	
	public void Limpia(ListadoPresupuestosForm formulario)
	{
		log.info("ListadoPresupuestosDispatchActions:Limpia Inicial inicio");
		formulario.setDivisa(Constantes.STRING_CERO);
		formulario.setForma_pago(Constantes.STRING_CERO);
		formulario.setCerrado(Constantes.STRING_CERO);
		log.info("ListadoPresupuestosDispatchActions:Limpia Inicial fin");
	}
	
	public void cargaFormulario(ListadoPresupuestosForm form, Session request)
	{
		log.info("ListadoPresupuestosDispatchActions:cargaFormulario Inicial inicio");
		ListadoPresupuestosForm formulario = (ListadoPresupuestosForm)form;
		Session session =request;
		String sucursal = (String)session.getAttribute(Constantes.STRING_SUCURSAL);
		this.cargaInicial(formulario, sucursal, session);
		log.info("ListadoPresupuestosDispatchActions:cargaFormulario Inicial fin");
		
		//return mapping.findForward(Constantes.FORWARD_LISTADO_PRESUPUESTOS);
		return;
	}
	
	public void buscar(ListadoPresupuestosForm form, Session response)
	{
		log.info("ListadoPresupuestosDispatchActions:buscar Inicial inicio");
		Session session = response;
		
		String sucursal = (String)session.getAttribute(Constantes.STRING_SUCURSAL);
		ListadoPresupuestosForm formulario = (ListadoPresupuestosForm)form;
		this.cargaInicial(formulario, sucursal, session);
		formulario = helper.cargaListadoPresupuestos(formulario,sucursal);
		this.Limpia(formulario);
		session.setAttribute(Constantes.STRING_ACTION_LISTA_PRESUPUESTO, formulario.getListaPresupuestos());
		session.setAttribute(Constantes.STRING_ACTION_FECHA_BUSQUEDA_PRESUPUESTO, formulario.getFechaInicio()+" - "+formulario.getFechaTermino());
		session.setAttribute(Constantes.STRING_CERRADO, formulario.getCerrado());
		log.info("ListadoPresupuestosDispatchActions:buscar Inicial fin");
		//return mapping.findForward(Constantes.FORWARD_LISTADO_PRESUPUESTOS);
	return;
	}
}
