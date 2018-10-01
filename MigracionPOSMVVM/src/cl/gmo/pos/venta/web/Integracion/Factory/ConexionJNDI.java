package cl.gmo.pos.venta.web.Integracion.Factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Advise68
 */
public class ConexionJNDI implements ConexionFactory{
    
    private DataSource datasource;

    public Connection getConexion() {
        Connection connection = null;
        try{
            connection = datasource.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }        
        return connection;
    }
    
    
    
}
