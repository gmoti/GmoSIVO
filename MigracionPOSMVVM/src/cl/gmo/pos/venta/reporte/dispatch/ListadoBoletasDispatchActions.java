package cl.gmo.pos.venta.reporte.dispatch;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.reporte.nuevo.ListadoBoletasHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.ListadoBoletasForm;


public class ListadoBoletasDispatchActions {
	Logger log = Logger.getLogger( this.getClass() );
	ListadoBoletasHelper helper = new ListadoBoletasHelper();
	
	public ListadoBoletasDispatchActions() {
	}
	
	public void cargaFormulario(ListadoBoletasForm form)
	{
		log.info("ListadoBoletasDispatchActions:cargaFormulario  inicio");
		log.info("ListadoBoletasDispatchActions:cargaFormulario Inicial fin");
		//return mapping.findForward(Constantes.FORWARD_LISTADO_BOLETAS);
		return;
	}
	
	public ListadoBoletasForm buscar(ListadoBoletasForm form, Session request)
	{
		log.info("ListadoBoletasDispatchActions:cargaFormulario  inicio");
		Session session =request;
		
		String sucursal = (String)session.getAttribute(Constantes.STRING_SUCURSAL);
		ListadoBoletasForm formulario = (ListadoBoletasForm)form;
		
		session.setAttribute(Constantes.STRING_ACTION_FECHA_BUSQUEDA_BOLETAS, formulario.getFecha_inicio());
		formulario = helper.cargaListadoBoletas(formulario,sucursal);
		
		session.setAttribute(Constantes.STRING_ACTION_LISTA_BOLETAS, formulario.getListaBoletas());
		
		log.info("ListadoBoletasDispatchActions:cargaFormulario  fin");
		//return mapping.findForward(Constantes.FORWARD_LISTADO_BOLETAS);
		return formulario;
	}

}
