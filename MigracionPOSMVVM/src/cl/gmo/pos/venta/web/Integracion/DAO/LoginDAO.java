package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.Integracion.DTO.UsuarioDTO;

/**
 *
 * @author Advise68
 */
public interface  LoginDAO {
    
    public UsuarioDTO encuentraUsuario(String Usuario, String password) throws Exception;
    
}
