package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.LoginDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DTO.SucursalDTO;
import cl.gmo.pos.venta.web.Integracion.DTO.UsuarioDTO;
import cl.gmo.pos.venta.web.beans.SucursalesBean;

public class PosLoginFacade {
	 public static UsuarioDTO validaUsuario(String nombreUsuario, String clave) throws Exception{
	        
	        UsuarioDTO usuario = new UsuarioDTO();
	        
	        try{
	            LoginDAOImpl login = new LoginDAOImpl();
	            usuario = login.encuentraUsuario(nombreUsuario, clave);
	            
	        }catch (Exception e){
	            e.printStackTrace();
	            throw new Exception("posVentaFacade: validaUsuario");
	        }
	        return usuario;
	        //return LoginDAOImpl.encuentraUsuario(nombreUsuario, clave);        
	    }
	    public static ArrayList<SucursalesBean> traeNombreSucursal(String nombreUsuario) throws Exception{
	        try{
	        LoginDAOImpl login = new LoginDAOImpl();         
	        UsuarioDTO usuarioSucursalDTO = login.traeSucursal(nombreUsuario);
	        ArrayList<SucursalesBean> listaSucursales = new ArrayList<SucursalesBean>();
	        
	        for (SucursalDTO sucursalesDTO: usuarioSucursalDTO.getSucursales()){
	            SucursalesBean sucursales= new SucursalesBean();
	            sucursales.setDescripcion(sucursalesDTO.getDescripcion());
	            sucursales.setSucursal(sucursalesDTO.getSucursal());
	            sucursales.setTipo_boleta(sucursalesDTO.getTipo_boleta());
	            listaSucursales.add(sucursales);
	        }
	        
	        return listaSucursales;
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("posVentaFacade: validaUsuario");
	    }
	 
	 }
	    
	   
}
