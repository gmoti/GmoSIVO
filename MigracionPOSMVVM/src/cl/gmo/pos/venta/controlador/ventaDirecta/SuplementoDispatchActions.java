package cl.gmo.pos.venta.controlador.ventaDirecta;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.SuplementosForm;


public class SuplementoDispatchActions {
	SuplementosHelper helper = new SuplementosHelper();
	
	Logger log = Logger.getLogger( this.getClass() );
	public SuplementoDispatchActions() {
	}
	
	public void cargaformulario(SuplementosForm formulario, Session session)
	{
		log.info("SuplementoDispatchActions:cargaformulario  inicio");
		helper.cargaFormulario(formulario, session);
		log.info("SuplementoDispatchActions:cargaformulario  fin");
	}
	
	public SuplementosForm carga(SuplementosForm form,	Session request)
	{
		log.info("SuplementoDispatchActions:carga inicio");
		//String producto = request.getParameter(Constantes.STRING_PRODUCTO).toString();
		Session session = request;
		//session.setAttribute(Constantes.STRING_PRODUCTO, producto);
		//estrae producto proveniente de la venta 
		
		SuplementosForm formulario = (SuplementosForm)form;
		ProductosBean prod = (ProductosBean)session.getAttribute(Constantes.STRING_PRODUCTO);
		
		formulario.setProducto(prod.getCod_barra());
		
		String ojo = !prod.getOjo().equals("") ? prod.getOjo().toUpperCase() : "";
		formulario.setSeg_ojo(ojo);
		
		
		session.setAttribute(Constantes.STRING_LISTA_SUPLEMENTOS, prod.getListaSuplementos());
		this.cargaformulario(formulario, session);
		log.info("SuplementoDispatchActions:carga  fin");
		//return mapping.findForward(Constantes.FORWARD_SUPLEMENTOS);
		return formulario;
	}
	
	public SuplementosForm agregar(SuplementosForm form, Session request)
	{
		log.info("SuplementoDispatchActions:agregar  inicio");
		Session session = request;
		SuplementosForm formulario = (SuplementosForm)form;
		
		String accion = formulario.getAccion();
		String bloqueado = (String)session.getAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS);
		ProductosBean prod = (ProductosBean)session.getAttribute(Constantes.STRING_PRODUCTO);
		formulario.setProducto(prod.getCod_barra());	
		
		/*AGREGO DESCRIPCION OJO*/
		//formulario.setSeg_ojo(prod.getOjo());		
		
		this.cargaformulario(formulario, session);
		if (accion.equals(Constantes.STRING_CERRAR))
		{
			helper.validaCierre(formulario, session);
		}
		if (accion.equals(Constantes.STRING_ELIMINAR_PRODUCTO))
		{
			if (!Constantes.STRING_ACTION_BLOQUEA.equals(bloqueado)) {
				helper.eliminarSuplemento(formulario);
			}
			else
			{
				helper.cargaValores(formulario, session, prod);
				formulario.setError(Constantes.ERROR_ENCARGO_BLOQUEADO);
			}
		}
		if (accion.equals(Constantes.STRING_AGREGAR))
		{
			if (!Constantes.STRING_ACTION_BLOQUEA.equals(bloqueado)) {
				helper.agregaSuplemento(formulario, session);
			}
			else
			{
				helper.cargaValores(formulario, session, prod);
				formulario.setError(Constantes.ERROR_ENCARGO_BLOQUEADO);
			}
		}
		if (accion.equals(Constantes.STRING_CARGA_VALORES))
		{
			helper.cargaValores(formulario, session, prod);
		}
		log.info("SuplementoDispatchActions:agregar  fin");
		//return mapping.findForward(Constantes.FORWARD_SUPLEMENTOS);
		return formulario;
	}

}
