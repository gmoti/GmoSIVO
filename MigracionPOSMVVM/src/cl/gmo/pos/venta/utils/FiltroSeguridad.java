package cl.gmo.pos.venta.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroSeguridad implements Filter {
	 
    private FilterConfig config = null;
    private HashMap map         = null;   
    String paginaError          = null;
    String paginaFinSesion      = null;
    String paginaError2         = null;
 
     public String getExtension(String fichero) {
        String extension = null;
        String s = fichero;
        int i = s.lastIndexOf('.');
 
        if (i > 0 &&  i < s.length() - 1) {
            extension = s.substring(i+1).toLowerCase();
        }
        return extension;
    }
 
    public void destroy( ) {
        config = null;
    }
 
    public void init( FilterConfig config ) throws ServletException {
        this.config = config;
        String delimitador  = ",";
        paginaError         = config.getInitParameter("paginaError");
        paginaFinSesion     = config.getInitParameter("paginaFinSesion");
        Enumeration e = config.getInitParameterNames( );
        map = new HashMap( );
        if(null == paginaError)
            throw new ServletException("Por favor, proporcione "
                                    + " el parametro 'paginaError'.");
        while( e.hasMoreElements() ) {
            String element      = (String)e.nextElement( );
            String linea_props  = (String)config.getInitParameter( element );
            String[] props      = linea_props.split(delimitador);
            map.put(element,props);
        }
      }
 
 
/** Proceso Principal del filtro. */
public void doFilter(ServletRequest  request, 
                     ServletResponse response,
                     FilterChain     chain ) throws IOException, ServletException {
 
    String uri  =((HttpServletRequest)request).getRequestURI();
    System.out.println("==>"+uri);
    HttpSession sesion         = null;
    boolean     todoBien       = false;
    boolean     redireccionado = false;
 
    try{
    	System.out.println("Paso filtro");
         if (request instanceof HttpServletRequest) {
             sesion = ((HttpServletRequest)request).getSession();             
         }
         if (null != sesion.getAttribute("usuario")){
              chain.doFilter( request, response );
              todoBien = true;
         }else{
            /*Se ha perdido de la sesion el Usuario */
             ((HttpServletResponse)response).sendRedirect( paginaFinSesion );
             redireccionado = true;
         }
      
    }catch( javax.servlet.ServletException je ) {
             Throwable t = je.getRootCause( );
             System.out.println( "Filtro: Excepcion: "+t.getMessage( ) );
             t.printStackTrace( );
    }
    catch(Exception ex){
              System.out.println( "Error al acceder a: "+uri );
              System.out.println( "Filtro: "+ex.getMessage( ) );
              ex.printStackTrace();
              throw new ServletException( "Estoy en el Filtro pidiendo: "
                                 + ""+ uri+"<br/>"+ex.getMessage());
    }
 
    }//fin metodo
}//fin clase