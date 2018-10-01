package cl.gmo.pos.venta.web.Integracion.Factory;

import java.sql.Connection;

/**
 *
 * @author Advise68
 */
public interface ConexionFactory {
    
    public static final ConexionFactory INSTANCE = new ConexionGMO();
	
	public Connection getConexion();
    
}
