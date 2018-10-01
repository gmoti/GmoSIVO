package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.web.Integracion.DAO.LoginDAO;
import cl.gmo.pos.venta.web.Integracion.DTO.SucursalDTO;
import cl.gmo.pos.venta.web.Integracion.DTO.UsuarioDTO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;

/**
 *
 * @author Advise68
 */
public class LoginDAOImpl implements LoginDAO {
	Logger log = Logger.getLogger( this.getClass() );
    public LoginDAOImpl() {
    }    

    public UsuarioDTO encuentraUsuario(String usuario, String clave) throws Exception {
    	log.info("LoginDAOImpl:encuentraUsuario inicio");
        Connection conexion = null;
        ResultSet rs = null;
        CallableStatement cstmt = null;
        System.out.println("Entro a LoginDAOImpl");
        
        try{
        	log.info("LoginDAOImpl:encuentraUsuario conectando base datos");
            conexion = ConexionFactory.INSTANCE.getConexion();
            //testing
            // cursor = 0;
            String descNombreUser;
            String user;
            String pass;
            int cant;
            UsuarioDTO usuarioEncontrado = new UsuarioDTO();
            cstmt = conexion.prepareCall("{call SP_LOGIN_SEL_VALIDAR_USUARIO2(?,?,?,?,?)}");
            cstmt.setString(1, usuario);    
            cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
            cstmt.registerOutParameter(3, OracleTypes.VARCHAR);
            cstmt.registerOutParameter(4, OracleTypes.VARCHAR);
            cstmt.registerOutParameter(5, OracleTypes.NUMBER);
            cstmt.execute();
            
            descNombreUser =cstmt.getString(2).toUpperCase();
            user = cstmt.getString(3).toUpperCase();
            pass =  cstmt.getString(4);
            cant = cstmt.getInt(5);
            usuarioEncontrado.setDescNombreUsuario(descNombreUser);
            usuarioEncontrado.setNombreUsuario(user);
            usuarioEncontrado.setPassword(pass);
            usuarioEncontrado.setCantidadSucursales(cant);
            return usuarioEncontrado;
            
        }catch(Exception e){         
        	log.error("LoginDAOImpl:encuentraUsuario error controlado",e);
            throw new Exception("Error en DAO al buscar Usuario");
        }finally{
            try{
                if (null != rs){
                	log.warn("LoginDAOImpl:encuentraUsuario cierre ResultSet");
                    rs.close();
                }
                if (null != cstmt){
                	log.warn("LoginDAOImpl:encuentraUsuario cierre CallableStatement");
                    cstmt.close();
                }
                if (null != conexion){
                	log.warn("LoginDAOImpl:encuentraUsuario cierre Connection");
                	conexion.close();
                } 
            }catch(SQLException e){
            	log.error("LoginDAOImpl:encuentraUsuario error", e);
            	throw new Exception("Error en DAO al Cerrar conexion en encuentraUsuario()");
            }            
        }
        
    }
    
    public UsuarioDTO traeSucursal(String usuario) throws Exception {
    	log.info("LoginDAOImpl:traeSucursal inicio");
        Connection conexion = null;
        ResultSet rs = null;
        CallableStatement cstmt = null;     
        try{
        	log.info("LoginDAOImpl:traeSucursal conectando base datos");
            conexion = ConexionFactory.INSTANCE.getConexion();
            cstmt = conexion.prepareCall("{ call SP_LOGIN_SEL_SUCURSALES(?,?)}");
            cstmt.setString(1, usuario);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(2);
            
            UsuarioDTO usuarioSucursal = new UsuarioDTO();
            ArrayList<SucursalDTO> sucursalesList = new ArrayList<SucursalDTO>();
            
           while (rs.next()){
        	   log.debug("LoginDAOImpl:traeSucursal entrando ciclo while");
                SucursalDTO sucursal = new SucursalDTO();
                String codSucursal = rs.getString("CDG");
                String descSucursal = rs.getString("CDG") + " - " + rs.getString("DESCRIPCION");
                String tipoBoleta = rs.getString("TIPO_BOLETA");
                sucursal.setSucursal(codSucursal);
                sucursal.setDescripcion(descSucursal);
                sucursal.setTelefono(rs.getString("TELEFONO1"));
                sucursal.setTipo_boleta(tipoBoleta);
                sucursalesList.add(sucursal);
            }  
           
           usuarioSucursal.setSucursales(sucursalesList);
           return usuarioSucursal;
        }catch(Exception e){
        	log.error("LoginDAOImpl:traeSucursal error controlado",e);
            throw new Exception("Error en DAO al buscar Usuario");
        }finally{
            try{
                if (null != rs){
                	log.warn("LoginDAOImpl:traeSucursal cierre ResultSet");
                    rs.close();
                }
                if (null != cstmt){
                	log.warn("LoginDAOImpl:traeSucursal cierre CallableStatement");
                    cstmt.close();
                }      
                if (null != conexion){
                	log.warn("LoginDAOImpl:traeSucursal cierre Connection");
                	conexion.close();
                } 
            }catch(Exception e){
            	log.error("LoginDAOImpl:traeSucursal error", e);
            }            
        }
    
    }
    
}
