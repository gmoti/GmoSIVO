package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DTO.UsuarioDTO;
import cl.gmo.pos.venta.web.beans.SucursalesBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.UsuarioForm;

public class LoginHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
    public int validaUsuario(UsuarioForm usuarioForm){        
    	log.info("LoginHelper:validaUsuario inicio");
        UsuarioDTO usuario = new UsuarioDTO();
        int paso = 1;
        
        try{
            usuario = PosUtilesFacade.validaUsuario(usuarioForm.getNombreUsuario(), usuarioForm.getClaveUsuario());
              if(usuario==null){
                 paso=1;
              }
              if(usuario.getNombreUsuario().equals(usuarioForm.getNombreUsuario())){
            	  if(usuario.getPassword().equals(usuarioForm.getClaveUsuario())){
            		  usuarioForm.setDescNombreUsuario(usuario.getDescNombreUsuario());
            		  if(usuario.getCantidadSucursales()==1)
            		  {
            			  paso=2;
            		  }
            		  if(usuario.getCantidadSucursales()>1)
            		  {
            			  paso=3;
            		  }
            	  }
              }

           return paso;
        }catch (Exception e){
        	log.error("LoginHelper:validaUsuario error catch",e);
        }
        
        return paso;
    }
    public ArrayList<SucursalesBean> traeNombreSucursal(String usuario){
    	log.info("LoginHelper:traeNombreSucursal inicio");
        ArrayList<SucursalesBean> listaSucursales =null;
       try {
           listaSucursales = PosUtilesFacade.traeNombreSucursal(usuario);   
       } catch (Exception ex) {
    	   log.error("LoginHelper:traeNombreSucursal error catch",ex);
       }
        return listaSucursales;
   }
}
