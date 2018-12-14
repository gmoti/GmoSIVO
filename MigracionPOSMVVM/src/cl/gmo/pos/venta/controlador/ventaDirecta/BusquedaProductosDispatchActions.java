/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;



public class BusquedaProductosDispatchActions {
	
	Logger log = Logger.getLogger( this.getClass() );
    BusquedaProductosHelper helper = new BusquedaProductosHelper();
    String form_origen = Constantes.STRING_BLANCO;
    boolean busqueda_avanzada = false;
    boolean busqueda_avanzada_lentilla = false;
    
    public BusquedaProductosDispatchActions() {
    }
    
    public BusquedaProductosForm cargaBusquedaProductos(BusquedaProductosForm form, Session request)
    {
    	System.out.println("Paso por aca al cerrar ventana");
    	log.info("BusquedaProductosDispatchActions:cargaBusquedaProductos inicio");
    	Session session = request;
    	
        form_origen = Constantes.STRING_BLANCO;
        BusquedaProductosForm formulario = (BusquedaProductosForm)form;
        formulario.cleanForm();
        
        //form_origen = request.getParameter(Constantes.STRING_FORMULARIO).toString();
        form_origen = request.getAttribute(Constantes.STRING_FORMULARIO).toString();
        
        formulario.setListaFamilias(helper.traeFamilias(form_origen));
        
    	
    	session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_BLANCO);
    	formulario.setGraduacion((GraduacionesBean)session.getAttribute(Constantes.STRING_GRADUACION));
    	formulario.setGraduacion_lentilla((ContactologiaBean)session.getAttribute(Constantes.STRING_GRADUACION_LENTILLA));
    	session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, false);
    	session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA, false);
    	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_SIN_PRODUCTOS);
    	
    	if (!form_origen.equals(Constantes.STRING_DIRECTA)) 
    	{
    		
    		ProductosBean prod_prec = (ProductosBean)session.getAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO);
    		if (null != prod_prec && null != prod_prec.getFamilia()) 
    		{

    			formulario.setListaFamilias(helper.traeFamilias(form_origen));
    			formulario.setListaSubFamilias(helper.traeSubFamilias(prod_prec.getFamilia()));
    			formulario.setListaGruposFamilias(helper.traeGrupoFamilias(prod_prec.getFamilia(), prod_prec.getSubFamilia()));
    			formulario.setFamilia(prod_prec.getFamilia());
    			formulario.setSubFamilia(prod_prec.getSubFamilia());
    			formulario.setGrupo(prod_prec.getGrupoFamilia());
    			
    			busqueda_avanzada = helper.visualizaBusquedaAvanzada(formulario, form_origen, session, prod_prec.getFamilia());			
    			busqueda_avanzada_lentilla = helper.visualizaBusquedaAvanzadaLentilla(formulario, form_origen, session, prod_prec.getFamilia());
    			
    			helper.validaBusquedasAvanzadas(formulario, session, busqueda_avanzada, busqueda_avanzada_lentilla);

    			busqueda_avanzada = Boolean.parseBoolean(session.getAttribute(Constantes.STRING_BUSQUEDA_AVANZADA).toString());
    			busqueda_avanzada_lentilla = Boolean.parseBoolean(session.getAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA).toString());
    			
    			if (!busqueda_avanzada && !busqueda_avanzada_lentilla) {
    				formulario.setFamilia(Constantes.STRING_CERO);
    				formulario.setSubFamilia(Constantes.STRING_CERO);
    				formulario.setGrupo(Constantes.STRING_CERO);
    			}
    		}
    		
    	}
    	
    	log.info("BusquedaProductosDispatchActions:cargaBusquedaProductos fin");
        //return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
        return formulario;
    }
    
    public BusquedaProductosForm cargaBusquedaProductosDirecto(BusquedaProductosForm form, Session request)
    {
    	log.info("BusquedaProductosDispatchActions:cargaBusquedaProductosDirecto inicio");
    	log.info("BusquedaProductosDispatchActions:cargaBusquedaProductosDirecto fin");
    	//return mapping.findForward(Constantes.FORWARD_BUSQUEDA_DIRECTA);
    	
    	return form;
    }
    
    
    public BusquedaProductosForm buscar(BusquedaProductosForm form, Session request)
    {
    	log.info("BusquedaProductosDispatchActions:buscar inicio");
        BusquedaProductosForm formulario = (BusquedaProductosForm)form;
        String accion = Constantes.STRING_BLANCO;
        accion = formulario.getAccion();
        
 
        System.out.println("accion ==>"+ accion);
        Session session =request;
        session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_BLANCO);
        
        if (Constantes.STRING_ERROR.equals(accion)) {
        	//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
        	return formulario;
        }
        
        
        String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
        session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, false);
        session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA, false);
        
        formulario.setListaProductos(null);
        
        if (Constantes.STRING_FAMILIA.equals(accion)) {
        	formulario.setListaFamilias(helper.traeFamilias(form_origen));
        	formulario.setListaSubFamilias(helper.traeSubFamilias(formulario.getFamilia()));
        	formulario.setListaGruposFamilias(null);
        	busqueda_avanzada_lentilla=false;
        }
        if (Constantes.STRING_SUBFAMILIA.equals(accion)) {
        	formulario.setListaFamilias(helper.traeFamilias(form_origen));
        	formulario.setListaSubFamilias(helper.traeSubFamilias(formulario.getFamilia()));
        	formulario.setListaGruposFamilias(helper.traeGrupoFamilias(formulario.getFamilia(), formulario.getSubFamilia()));
        	busqueda_avanzada_lentilla=false;
        } 
        if (Constantes.STRING_GRUPOFAMILIA.equals(accion)) {
        	busqueda_avanzada_lentilla=false;
        }
        if (Constantes.STRING_BUSCAR.equals(accion)) {
        	if (helper.compruebaBusquedaPorCodigo(formulario, local, form_origen, busqueda_avanzada_lentilla, session)) {
        		
        		busqueda_avanzada_lentilla = Boolean.parseBoolean(session.getAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA).toString());
        		//busqueda_avanzada = true;
        		//session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, busqueda_avanzada);
        		//return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
			}
        	else
        	{
        		//valida si es lente de Contacto graduable
        	if (helper.validaEsLenteContactoGraduable(formulario, form_origen, busqueda_avanzada_lentilla)) {
					busqueda_avanzada_lentilla=true;
				}
        		else
        		{
        			formulario.setListaProductos(helper.traeProductos(formulario.getFamilia(),
                    formulario.getSubFamilia(), formulario.getGrupo(), formulario.getProveedor(),
                    formulario.getDescripcion(), formulario.getFabricante(),formulario.getCodigoBusqueda(),
                    formulario.getCodigoBarraBusqueda(), local, form_origen));        		
                    formulario.setListaFamilias(helper.traeFamilias(form_origen));
                                                                               
                    //SABER SI LA MULTIOFERTA SE PUEDE MOSTRAR EN VENTA DIRECTA
                    formulario = helper.mostrarMultiOferta(formulario, form_origen);
                    
                    
                    if (null == formulario.getListaProductos() || formulario.getListaProductos().isEmpty())
                    {
                    	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_SIN_PRODUCTOS);
                    }
                    else
                    {
                    	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_LISTA_PRODUCTOS_ESTADO);
                    }
        		}
        		
        	}
        }
        if (Constantes.STRING_BUSCAR_GRADUACION.equals(accion))
        {
        	helper.traeGraduacionesClientes(formulario, session);
        	session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_BUSCAR_GRADUACION);
        }
        if (Constantes.STRING_SELECCIONA_GRADUACION.equals(accion))
        {
        	helper.traeGraduacionSeleccionada(formulario);
        }
        if (Constantes.STRING_BUSQUEDA_GRADUADA.equals(accion))
        {
        	if (formulario.getFamilia().equals(Constantes.STRING_ACTION_OCHO_NUEVE_NUEVE)) {
        		System.out.println("Paso uno busqueda graduada");
        		formulario.setListaProductos(helper.traeProductos(formulario.getFamilia(),
                        formulario.getSubFamilia(), formulario.getGrupo(), formulario.getProveedor(),
                        formulario.getDescripcion(), formulario.getFabricante(),formulario.getCodigoBusqueda(),
                        formulario.getCodigoBarraBusqueda(), local, form_origen));
                formulario.setListaFamilias(helper.traeFamilias(form_origen));
			}
        	else
        	{
        		System.out.println("Paso dos busqueda graduada");

        		helper.traeProductosGraduados(formulario, session);
        	}
        	if (formulario.getListaProductos().isEmpty())
            {
            	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_BLANCO);
            }
            else
            {
            	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_LISTA_PRODUCTOS_ESTADO);
            }
        }
        if (Constantes.STRING_CERCA.equals(accion)) {
        		if (!helper.validaRecetaCerca(formulario)) {
    				session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_ERROR_CERCA);
    				formulario.setChk_cerca(false);
    			}
			
		}
        if (Constantes.STRING_CERCA_NO.equals(accion)) {
				formulario.setChk_cerca(false);
        }
        
        if (!form_origen.equals(Constantes.STRING_DIRECTA)) {
        	busqueda_avanzada = helper.visualizaBusquedaAvanzada(formulario, form_origen, session, formulario.getFamilia());
        	//busqueda_avanzada_lentilla = helper.validaEsLenteContactoGraduable(formulario, form_origen, busqueda_avanzada_lentilla);
        	//busqueda_avanzada_lentilla = helper.visualizaBusquedaAvanzadaLentilla(formulario, form_origen, session, formulario.getFamilia());
        }

		if (!form_origen.equals(Constantes.STRING_DIRECTA)) {
			helper.validaBusquedasAvanzadas(formulario, session, busqueda_avanzada, busqueda_avanzada_lentilla);
		}
        
        if (Constantes.STRING_AGREGAR.equals(accion)) 
        {
            formulario.getProducto();
            formulario.getCantidad();
        }
        
    	
        log.info("BusquedaProductosDispatchActions:buscar fin");
        //return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
        return formulario;

    }
    /*
     * LMARIN 20140506
     */
    public boolean  tiene_suple(BusquedaProductosForm form, Session request
            ) throws Exception
    {
		 boolean valor = false;

		 BusquedaProductosForm formulario = (BusquedaProductosForm)form;
		 
		 //System.out.println("cODIGO bARRAS ===>"+formulario.getCodigo_barras().replace(" ","+"));
		 valor = helper.tiene_suplementos_obligatorios(formulario);
		  
		 //FQuiroz comenta valor devuelto  
		 //response.getWriter().print(valor);		
		 //return null;
		 return valor;
    }
    
    /*
     * LMARIN 20140828
     */
    public BusquedaProductosForm  busquedaMultiofertaAsoc(BusquedaProductosForm form, Session request) throws Exception
    {
		
		 BusquedaProductosForm formulario = (BusquedaProductosForm)form;	
		 
		 String armazon ="",cristal ="" ,ruta="";
		 
		 Session session = request;
		 
		 formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
		 ArrayList<ProductosBean> prod = formulario.getListaProductos();
		 ArrayList<ProductosBean>listaMultioferta = new ArrayList<ProductosBean>();
		 
		 try{
			
			 for(ProductosBean b : prod){
				 if(b.getFamilia().substring(0,1).equals("A")){
					 armazon = b.getCod_barra();
				 }
				 if(b.getFamilia().substring(0,1).equals("8")){
					 cristal = b.getCod_barra();
				 }
			 }
			 System.out.println("Filtro Local =>"+session.getAttribute(Constantes.STRING_SUCURSAL).toString());
			 listaMultioferta = helper.traeMultiofertasAso(armazon, cristal,session.getAttribute(Constantes.STRING_SUCURSAL).toString());

			 
			 if(formulario.getAccion().equals("carga")){
				 formulario.setListaMultioferta(listaMultioferta);
				 ruta = "busqueda_multioferta";
			 }
			 if(formulario.getAccion().equals("ped")){
				 session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA,false);
				 session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA,false);
	         	 session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_LISTA_PRODUCTOS_ESTADO);

				 formulario.setAccion(Constantes.STRING_AGREGAR_PRODUCTOS);
				 formulario.setAddProducto(listaMultioferta.get(0).getCodigo());
				 ruta = "pedido";
			 }						 
		 }catch(Exception e){
			 System.out.println("Mensaje de error ==> busquedaMultiofertaAsoc  => "+e.getMessage());
		 }
		 //return mapping.findForward(ruta);
		 return formulario;
    }

	public boolean isBusqueda_avanzada() {
		return busqueda_avanzada;
	}

	public void setBusqueda_avanzada(boolean busqueda_avanzada) {
		this.busqueda_avanzada = busqueda_avanzada;
	}

	public boolean isBusqueda_avanzada_lentilla() {
		return busqueda_avanzada_lentilla;
	}

	public void setBusqueda_avanzada_lentilla(boolean busqueda_avanzada_lentilla) {
		this.busqueda_avanzada_lentilla = busqueda_avanzada_lentilla;
	}
    
    
    
    
    
}
