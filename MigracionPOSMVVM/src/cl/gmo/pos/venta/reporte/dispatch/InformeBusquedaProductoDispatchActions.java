package cl.gmo.pos.venta.reporte.dispatch;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.forms.InformeBusquedaProductoForm;
import cl.gmo.pos.venta.web.helper.InformeBusquedaProductoHelper;

public class InformeBusquedaProductoDispatchActions{
	Logger log = Logger.getLogger( this.getClass() );
	
	public InformeBusquedaProductoDispatchActions() {
	}

	public InformeBusquedaProductoForm cargaFormulario(InformeBusquedaProductoForm form,
			Session request)
	{		
		log.info("InformeBusquedaProductoDispatchActions:cargaFormulario  inicio");
		InformeBusquedaProductoForm formulario = (InformeBusquedaProductoForm) form;
		Utils util = new Utils();
		formulario.setFechaDesde(util.restarDiasFecha(30));
		formulario.setFechaHasta(util.traeFechaHoyFormateadaString());
		formulario.setEstadoPagina("");
		log.info("InformeBusquedaProductoDispatchActions:cargaFormulario  fin");
		
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA_GENERAL_ARTICULOS);
		return formulario;
	}
	
	public InformeBusquedaProductoForm buscarArticulo(InformeBusquedaProductoForm form,	Session request)
	{		
		log.info("InformeBusquedaProductoDispatchActions:buscarArticulo  inicio");
		
		InformeBusquedaProductoForm formulario = (InformeBusquedaProductoForm)form;
		InformeBusquedaProductoHelper informeBusquedaProducto = new InformeBusquedaProductoHelper();
		
		String fechaDesde = formulario.getFechaDesde();
		String fechaHasta = formulario.getFechaHasta();
		
		/*Date fecha_desde = informeBusquedaProducto.formatoFechaCh(fechaDesde);
		Date fecha_hasta = informeBusquedaProducto.formatoFechaCh(fechaHasta);
		
		int diferenciaMeses = informeBusquedaProducto.fechasDiferenciaEnDias(fecha_desde, fecha_hasta);
		*/
		//if(diferenciaMeses >= 30){
		informeBusquedaProducto.traeInformeBusquedaProducto(Integer.toString(formulario.getCodigoArticulo()), formulario.getDescripcionArticulo(), formulario);
		/*}else{
			formulario.setEstadoPagina("ERROR_DIAS");
		}*/
		
		log.info("InformeBusquedaProductoDispatchActions:buscarArticulo  fin");
		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA_GENERAL_ARTICULOS);
		return formulario;
	}
	
}
