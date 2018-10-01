package cl.gmo.pos.venta.controlador.general;



import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.UsuarioForm;
import cl.gmo.pos.venta.web.helper.LoginHelper;


public class LoginActions{

	LoginHelper loginHelper = new LoginHelper();
	Session session;
	Logger log = Logger.getLogger( this.getClass() );
	
	
	public LoginActions() {
	}
	public void cargaLogin(UsuarioForm form, Session request) {
		log.info("LoginActions:cargaLogin  inicio");

		log.info("LoginActions:cargaLogin  fin");
		//return mapping.findForward(Constantes.FORWARD_CARGA);
		return;
	}
	public String validaLogin(UsuarioForm form, Session request) {
		
		log.info("LoginActions:validaLogin  inicio");
		UsuarioForm usuario = (UsuarioForm)form;
		String caso=Constantes.STRING_FALLA;
		Session session = request;
		
		/*
		if(request.getSession(false)==null || !request.getSession(false).isNew()){
			this.session = request.getSession(false);
		}else{
			System.out.println("Session valida");
			this.session = request.getSession(true);
		} */
		
		
		int validaEstadoUsuario = loginHelper.validaUsuario(usuario);

		if((validaEstadoUsuario)==1){
			session.setAttribute(Constantes.STRING_ERROR, Constantes.STRING_FALLA);
			caso=Constantes.STRING_FALLA;
		}
		if((validaEstadoUsuario)==2){
			session.setAttribute(Constantes.STRING_DESC_USUARIO, usuario.getDescNombreUsuario().toUpperCase());
			session.setAttribute(Constantes.STRING_USUARIO, usuario.getNombreUsuario().toUpperCase());
			session.removeAttribute(Constantes.STRING_ERROR);
			session.setMaxInactiveInterval(3600);
			caso=Constantes.STRING_SELECT_SUCURSAL;
		}
		if((validaEstadoUsuario)==3){
			session.setAttribute(Constantes.STRING_DESC_USUARIO, usuario.getDescNombreUsuario().toUpperCase());
			session.setAttribute(Constantes.STRING_USUARIO, usuario.getNombreUsuario().toUpperCase());
			session.removeAttribute(Constantes.STRING_ERROR);
			session.setMaxInactiveInterval(3600);
			caso=Constantes.STRING_SELECT_SUCURSAL;
		}
		log.info("LoginActions:validaLogin  fin");
		//return mapping.findForward(caso);
		return caso;
	}
}
