package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.facade.BusquedaLiberacionesFacade;
import cl.gmo.pos.venta.web.facade.PosSeleccionPagoFacade;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;


public class BusquedaProductosMultiOfertasDispatchActions{

	BusquedaProductosHelper helper = new BusquedaProductosHelper();
    String form_origen = Constantes.STRING_BLANCO;
    Logger log = Logger.getLogger( this.getClass() );
	public BusquedaProductosMultiOfertasDispatchActions(){
		//constructor
	}
	
	public BusquedaProductosForm cargaBusquedaProductosMultiOfertas(BusquedaProductosForm form, Session request)
    {
		System.out.println("Paso 1 BML");
		log.info("BusquedaProductosMultiOfertasDispatchActions:cargaBusquedaProductosMultiOfertas inicio");
		
		Session session =request;
        form_origen = Constantes.STRING_BLANCO;
        BusquedaProductosForm formulario = (BusquedaProductosForm)form;
        //form_origen = request.getParameter(Constantes.STRING_FORMULARIO).toString();
        form_origen = request.getAttribute(Constantes.STRING_FORMULARIO).toString();
        
        VentaDirectaHelper ventaHelper = new VentaDirectaHelper();
        ArrayList<PagoBean> lista_Pagos = new ArrayList<PagoBean>();
        
        try {
			//ORIGINAL SE DEBE DESCOMENTAR 08/04/12
			lista_Pagos =  PosSeleccionPagoFacade.traePagos(formulario.getCdg(), Constantes.STRING_PEDIDO);
			if(null == lista_Pagos){
				formulario.setTienePagos("false");
			}else{
				
				if(lista_Pagos.size()==0){
					formulario.setTienePagos("false");
				}else{
					formulario.setTienePagos("true");
				}
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoHelper:traePagos error catch",e);
		}
		
        formulario.setForm_origen(form_origen);
        
        
        formulario.setListaMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS));
        ArrayList<ProductosBean> listaprodAux = new ArrayList<ProductosBean>();
        
        /**
         * ProductosBean pro = new ProductosBean();
         * producto multioferta seleccionada para cargar los productos internos de la 
         * multioferta
         */
        
        ProductosBean pro = new ProductosBean();
        
        System.out.println("cod => "+ formulario.getCodigoMultioferta()+"<==> index_multi =>"+formulario.getIndex_multi());
        pro.setCodigo(formulario.getCodigoMultioferta());
        pro.setIndexMulti(formulario.getIndex_multi());        
       
        
        System.out.println("Imprimo la session Busqueda Multioferta ==> "+session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
        
        if(null != formulario.getListaMultioferta()){//saber si la lista de multiofertas es nula
	        for (ProductosBean multi : formulario.getListaMultioferta()){//recorrer la lista de multiofertas
	        	if(pro.getCodigo().equals(multi.getCodigo())&& pro.getIndexMulti() == multi.getIndexMulti()){//preguntar si el producto multioferta es igual al producto que existe en la listaMultioferta	        		
	        		formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));//cargar los prodctos internos de la multioferta seleccionada.
	        		
	        		if(null != formulario.getListaProductosMultioferta()){
		        		for (ProductosBean prodmulti : formulario.getListaProductosMultioferta()){
		        			
		        			System.out.println("cod multi =>"+prodmulti.getCodigoMultioferta()+"\n");
		        			
		        			if(prodmulti.getCodigoMultioferta().equals(pro.getCodigo()) && prodmulti.getIndexRelMulti() == pro.getIndexMulti()){
		        				listaprodAux.add(prodmulti);
		        			}
		        		}
	        		}
	        		formulario.setListaProductosMultioferta(listaprodAux);
	        		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaprodAux);	        		
	        	}
	        }    
        }else{
        	formulario.setListaMultioferta(new ArrayList<ProductosBean>());
        }
         
        
        session.setAttribute(Constantes.STRING_LISTA_MULTIOFERTAS, formulario.getListaMultioferta());               
        formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
        
        
        /**
         * Procesamiento si la multioferta esta siendo cargada
         * desde la pagina de venta pedido (encargo)
         */
        
        if("PEDIDO".equals(form_origen)){
        	
        	GraduacionesBean graduacion = new GraduacionesBean();
        	System.out.println("Graduaciones ==>"+String.valueOf(formulario.getCliente())+"<=>"+formulario.getFecha_graduacion()+"<=>"+formulario.getNumero_graduacion());
        	graduacion = BusquedaLiberacionesFacade.traeGraduacionPedido(String.valueOf(formulario.getCliente()), formulario.getFecha_graduacion(), formulario.getNumero_graduacion());
        	formulario.setGraduacion(graduacion);
        }
        
        helper.verificaCantidadProductos(formulario);
		
        log.info("BusquedaProductosMultiOfertasDispatchActions:cargaBusquedaProductosMultiOfertas fin");
        //return mapping.findForward(Constantes.FORWARD_BUSQUEDA);
        return formulario;
    }
	
	public BusquedaProductosForm buscarMultioferta(BusquedaProductosForm form, Session request)
    {
		System.out.println("Paso 2 BML");

		log.info("BusquedaProductosMultiOfertasDispatchActions:buscarMultioferta inicio");		
		
        BusquedaProductosForm formulario = (BusquedaProductosForm)form;
        formulario.setErroMultioferta(Constantes.STRING_BLANCO);
        String accion = Constantes.STRING_BLANCO;
        accion = formulario.getAccion();
        VentaDirectaHelper ventaHelper;
        Session session =request;
        String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();        
        formulario.setForm_origen(formulario.getForm_origen());
        formulario.setEstadoCercaMulti("");   
        
        if("PEDIDO".equals(form_origen)){
    		System.out.println("Paso 2 1 BML");

        	GraduacionesBean graduacion = new GraduacionesBean();
        	//graduacion = BusquedaLiberacionesFacade.traeGraduacionPedido(String.valueOf(formulario.getCliente()), formulario.getFecha_graduacion(), formulario.getNumero_graduacion());
        	formulario.setGraduacion((GraduacionesBean)session.getAttribute(Constantes.STRING_GRADUACION));
        	formulario.setGraduacion_lentilla((ContactologiaBean)session.getAttribute(Constantes.STRING_GRADUACION_LENTILLA));
        	
        }
        
        if (Constantes.STRING_ELIMINAR_PRODUCTO.equals(accion))
		{
    		System.out.println("Paso 2 2 BML");

        	ventaHelper = new VentaDirectaHelper();
        	formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
        	ArrayList<ProductosBean> listaProductosMultiofertasAux = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX);
        	
        	listaProductosMultiofertasAux = ventaHelper.eliminarProductoMultioferta(formulario.getIndex_producto_multi(),formulario.getIndex_multi(), formulario.getProductoEliminar(), listaProductosMultiofertasAux);
			formulario.setListaProductosMultioferta(ventaHelper.eliminarProductoMultioferta(formulario.getIndex_producto_multi(),formulario.getIndex_multi(), formulario.getProductoEliminar(), formulario.getListaProductosMultioferta()));
			
			
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, formulario.getListaProductosMultioferta());			
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaProductosMultiofertasAux);
			formulario.setListaProductosMultioferta(listaProductosMultiofertasAux);
			formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
		}
        if (Constantes.STRING_TIPO_FAMILIA.equals(accion)) {
        	
    		System.out.println("Paso 2 3 BML");

        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));        	
        	formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        	formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
        }
        if (Constantes.STRING_FAMILIA.equals(accion)) {
        	
    		System.out.println("Paso 2 4 BML");

        	
        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
        	formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        	formulario.setListaSubFamilias(helper.traeSubFamiliasMultiofertas(formulario.getFamilia(), formulario.getCodigoMultioferta()));
        	formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
        }
        if (Constantes.STRING_SUBFAMILIA.equals(accion)) {
        	
    		System.out.println("Paso 2 5 BML");

        	
        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
        	formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        	formulario.setListaSubFamilias(helper.traeSubFamiliasMultiofertas(formulario.getFamilia(), formulario.getCodigoMultioferta()));
        	formulario.setListaGruposFamilias(helper.traeGrupoFamiliasMultiofertas(formulario.getFamilia(), formulario.getSubFamilia(), formulario.getCodigoMultioferta()));
        	formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
        } 
        if (Constantes.STRING_BUSCAR.equals(accion)) {
        	
    		System.out.println("Paso 2 6 BML");

        	
        	String tipoFamilia = formulario.getTipofamilia();
        	formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),tipoFamilia));
        	
        	if("C".equals(tipoFamilia)){
        		helper.traeProductosGraduadosMulti(formulario, session);
        	}else{
	            formulario.setListaProductos(helper.traeProductosMulti(formulario.getFamilia(),
	                    formulario.getSubFamilia(), formulario.getGrupo(), formulario.getProveedor(),
	                    formulario.getDescripcion(), formulario.getFabricante(),formulario.getCodigoMultioferta(),
	                    formulario.getCodigoBusqueda(), local, form_origen,formulario.getCodigoBarraBusqueda()));  
        	}
            
            formulario.setTipofamilia(tipoFamilia);
            formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));            
            formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
                       
        }
        if (Constantes.STRING_AGREGAR.equals(accion)) 
        {
    		System.out.println("Paso 2 7 BML");

            formulario.getProducto();
            formulario.getCantidad();
           
        }
        if(Constantes.STRING_PASAR_MULTIOFERTA.equals(accion)){
        	
    		System.out.println("Paso 2 8 BML");    		
        	
        	if("PEDIDO".equals(form_origen)){
        		
        		System.out.println("Paso 2 8 1 BML");
        		        		
        		VentaPedidoHelper pedidoHelper = new VentaPedidoHelper();
	        	//formulario.setListaProductosMultioferta(ventaHelper.actualizaProductos(formulario.getProducto(),formulario.getCantidad(), formulario.getListaProductosMultioferta(), local, Constantes.STRING_DIRECTA));
	        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
	        	
	        	formulario.setListaMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS));
	        	formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
	        	
	        	ArrayList<ProductosBean> listaProductosMultiofertaAux = new ArrayList<ProductosBean>();
	        	listaProductosMultiofertaAux.addAll((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
	        		
	        	int i=0;
	        	for (ProductosBean multi : formulario.getListaMultioferta()){        		
	            	if(formulario.getCodigoMultioferta().equals(multi.getCodigo()) && multi.getIndexMulti() == formulario.getIndex_multi()){            		
	            		formulario.setListaProductosMultioferta(pedidoHelper.actualizaProductosMultioferta(session, formulario, multi.getIndexMulti(), formulario.getProducto(),formulario.getCantidad(), formulario.getListaProductosMultioferta(), local, form_origen, formulario.getCodigoMultioferta(), formulario.getListaTipoFamilia(), null));
	            		listaProductosMultiofertaAux = pedidoHelper.actualizaProductosMultioferta(session, formulario, multi.getIndexMulti(), formulario.getProducto(),formulario.getCantidad(),listaProductosMultiofertaAux, local, form_origen, formulario.getCodigoMultioferta(), formulario.getListaTipoFamilia(), null);
	            	}        	
	            	i++;
	            }         	     	
	        	
	        	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, formulario.getListaProductosMultioferta());//actualiza la lista session de Multiofertas 
	        	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaProductosMultiofertaAux);
	        	
	        	/**
	        	 * Se setea nuevamente la lista del formulario para que sean vistas solo los productos
	        	 * de la multioferta que seleccionada.
	        	 */
	        	formulario.setListaProductosMultioferta(listaProductosMultiofertaAux);
	        	
	        
	        	int contador=0;
	        	for(TipoFamiliaBean tfam : formulario.getListaTipoFamilia()){
	        		
	        		for(ProductosBean  prod : formulario.getListaProductosMultioferta()){
	        			
	        			if(tfam.getCodigo().equals(prod.getTipoFamilia())&& prod.getIndexRelMulti()==formulario.getIndex_multi()){
	        				contador++;
	        			}        			
	        		} 
	        		/**
	        		 * Valida si la cantidad de articulos ingresados es mayor 
	        		 * a la cantidad permitida por la multioferta
	        		 */
	        		if(contador > tfam.getCantidad() || formulario.getCantidad() > tfam.getCantidad()){
	        			listaProductosMultiofertaAux = new ArrayList<ProductosBean>();
	        			listaProductosMultiofertaAux.addAll((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
	        			listaProductosMultiofertaAux.remove(listaProductosMultiofertaAux.size()-1);
	        			
	        			formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
	        			formulario.getListaProductosMultioferta().remove((formulario.getListaProductosMultioferta().size()-1));
	        			
	        			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, formulario.getListaProductosMultioferta());
	        			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaProductosMultiofertaAux); 
	        			
	        			formulario.setListaProductosMultioferta(listaProductosMultiofertaAux);
	        			
	        			formulario.setErroMultioferta(Constantes.STRING_ERROR);
	        		}
	        		contador=0;
	        	}         	
	        	
	        	
	        	//formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
	        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
	        	formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        		
	        	
	        	//precarga de los productos que sean opticos
	            ProductosBean prod_prec = (ProductosBean)session.getAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO);
	    		if (null != prod_prec && null != prod_prec.getFamilia()) 
	    		{
	    			
	    			formulario.setTipofamilia(prod_prec.getTipoFamilia());
	    			formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),prod_prec.getTipoFamilia()));
	    			formulario.setFamilia(prod_prec.getFamilia());
	            	formulario.setListaSubFamilias(helper.traeSubFamiliasMultiofertas(formulario.getFamilia(), formulario.getCodigoMultioferta()));
	            	formulario.setSubFamilia(prod_prec.getSubFamilia());
	            	formulario.setListaGruposFamilias(helper.traeGrupoFamiliasMultiofertas(formulario.getFamilia(), formulario.getSubFamilia(), formulario.getCodigoMultioferta()));
	            	formulario.setGrupo(prod_prec.getGrupoFamilia());
	    			session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, true);
	    			formulario.setOjo(Constantes.STRING_BLANCO);
	    		}
	    		formulario.setListaProductos(new ArrayList<ProductosBean>());
	    		//FIN PRECARGA
        	}else{       		
        	
        		System.out.println("Paso 2 8 2 BML");
 
	        	ventaHelper = new VentaDirectaHelper();
	        	//formulario.setListaProductosMultioferta(ventaHelper.actualizaProductos(formulario.getProducto(),formulario.getCantidad(), formulario.getListaProductosMultioferta(), local, Constantes.STRING_DIRECTA));
	        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
	        	
	        	formulario.setListaMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS));
	        	formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));  
	        	ArrayList<ProductosBean> listaProductosMultiofertaAux = new ArrayList<ProductosBean>();
	        	listaProductosMultiofertaAux.addAll((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
		        	
	        	int i=0;
	        	for (ProductosBean multi : formulario.getListaMultioferta()){        		
	            	if(formulario.getCodigoMultioferta().equals(multi.getCodigo()) && multi.getIndexMulti() == formulario.getIndex_multi()){            		
	            		formulario.setListaProductosMultioferta(ventaHelper.actualizaProductosMultioferta(multi.getIndexMulti(), formulario.getProducto(),formulario.getCantidad(), formulario.getListaProductosMultioferta(), local, form_origen, formulario.getCodigoMultioferta(), formulario.getListaTipoFamilia(), null));
	            		listaProductosMultiofertaAux = ventaHelper.actualizaProductosMultioferta(multi.getIndexMulti(), formulario.getProducto(),formulario.getCantidad(),listaProductosMultiofertaAux, local, form_origen, formulario.getCodigoMultioferta(), formulario.getListaTipoFamilia(), null);
	            	}        	
	            	i++;
	            }         	     	
	        	
	        	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, formulario.getListaProductosMultioferta());//actualiza la lista session de Multiofertas 
	        	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaProductosMultiofertaAux);
	        	
	        	/**
	        	 * Se setea nuevamente la lista del formulario para que sean vistas solo los productos
	        	 * de la multioferta que seleccionada.
	        	 */
	        	formulario.setListaProductosMultioferta(listaProductosMultiofertaAux);
	        	
	        	
	        	/**
	        	 * Se deben modificar para la venta pedido
	        	 */
	        	//formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
	        	//formulario.setListaProductosMultioferta(ventaHelper.actualizaProductosMultioferta(-1,formulario.getProducto(),formulario.getCantidad(), formulario.getListaProductosMultioferta(), local, "PEDIDO", formulario.getCodigoMultioferta(), formulario.getListaTipoFamilia(), (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX)));
	        	
	        	int contador=0;
	        	for(TipoFamiliaBean tfam : formulario.getListaTipoFamilia()){
	        		
	        		for(ProductosBean  prod : formulario.getListaProductosMultioferta()){
	        			
	        			if(tfam.getCodigo().equals(prod.getTipoFamilia())&& prod.getIndexRelMulti()==formulario.getIndex_multi()){
	        				contador++;
	        			}        			
	        		} 
	        		/**
	        		 * Valida si la cantidad de articulos ingresados es mayor 
	        		 * a la cantidad permitida por la multioferta
	        		 */
	        		if(contador > tfam.getCantidad() || formulario.getCantidad() > tfam.getCantidad()){
	        			
	        			listaProductosMultiofertaAux = new ArrayList<ProductosBean>();
	        			listaProductosMultiofertaAux.addAll((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
	        			listaProductosMultiofertaAux.remove(listaProductosMultiofertaAux.size()-1);
	        			
	        			formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
	        			formulario.getListaProductosMultioferta().remove((formulario.getListaProductosMultioferta().size()-1));
	        			
	        			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, formulario.getListaProductosMultioferta());
	        			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaProductosMultiofertaAux); 
	        			
	        			formulario.setListaProductosMultioferta(listaProductosMultiofertaAux);
	        			
	        			formulario.setErroMultioferta(Constantes.STRING_ERROR);
	        			/*
	        			formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
	        			formulario.getListaProductosMultioferta().remove((formulario.getListaProductosMultioferta().size()-1));
	        			
	        			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, formulario.getListaProductosMultioferta());
	        			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, formulario.getListaProductosMultioferta());        			
	        			formulario.setErroMultioferta(Constantes.STRING_ERROR);*/
	        		}
	        		contador=0;
	        	}         	
	        	
	        	
	        	//formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));
	        	formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
	        	formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        	}
        	
        }
        if (Constantes.STRING_CERCA.equals(accion)) {
    		System.out.println("Paso 2 9 BML");

    		if (!helper.validaRecetaCerca(formulario)) {
				//session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_ERROR_CERCA);
				//session.setAttribute(Constantes.STRING_ESTADO_MULTI, Constantes.STRING_ERROR_CERCA);
    			formulario.setEstadoCercaMulti(Constantes.STRING_ERROR_CERCA);
				formulario.setChk_cerca(false);
			}
    		formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
			formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        }
        if (Constantes.STRING_CERCA_NO.equals(accion)) {
        	
    		System.out.println("Paso 2 10 BML");

        	
        	formulario.setEstadoCercaMulti("");
			formulario.setChk_cerca(false);
			formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
			formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
        }
        
        if (Constantes.STRING_AGREGAR_SUPLEMENTOS.equals(formulario.getAccion())) {
        	
    		System.out.println("Paso 2 11 BML");

        	
			helper.agregaSuplementosProductoMultioferta(formulario, session);
			formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
			formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
			
			helper.actualizaListas(session);
		}
		if (Constantes.STRING_VER_SUPLEMENTOS.equals(formulario.getAccion())) {
			
    		System.out.println("Paso 2 12 BML");

			
			helper.verSuplementosProductoMultioferta(formulario, session);
			formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
			formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
		}
		if (Constantes.STRING_GRUPO.equals(formulario.getAccion())) {

    		System.out.println("Paso 2 13 BML");

			
			String tipoFamilia = formulario.getTipofamilia();			
			int index = formulario.getIndexProductos();
			String [] indice = formulario.getGrupos();		
			
			helper.modificaGrupoMultioferta(formulario, index, indice[index], session);
			
			formulario.setTipofamilia(tipoFamilia);
			formulario.setListaTipoFamilia(helper.traeTipoFamilias(form_origen, formulario.getCodigoMultioferta()));
			formulario.setListaFamilias(helper.traeFamiliasMultiofertas(form_origen, formulario.getCodigoMultioferta(),formulario.getTipofamilia()));
		
			helper.actualizaListas(session);
		}
		
    	         	     	
    	if (null == formulario.getListaFamilias() || formulario.getListaFamilias().size()==0) {
    		System.out.println("Paso 2 14 BML");

			formulario.setListaFamilias(new ArrayList<FamiliaBean>());
		}
    	if (null == formulario.getListaSubFamilias() || formulario.getListaSubFamilias().size()==0) {
    		System.out.println("Paso 2 15 BML");

			formulario.setListaSubFamilias(new ArrayList<SubFamiliaBean>());
		}
    	if (null == formulario.getListaGruposFamilias() || formulario.getListaGruposFamilias().size()==0) {
    		System.out.println("Paso 2 16 BML");

			formulario.setListaGruposFamilias(new ArrayList<GrupoFamiliaBean>());
		}
		System.out.println("Paso 2 17 PASO HELPER BML");

		helper.verificaCantidadProductos(formulario);
		
        log.info("BusquedaProductosMultiOfertasDispatchActions:buscarMultioferta fin");
        //return mapping.findForward(Constantes.FORWARD_BUSQUEDA);  
        return formulario;
    
    }
}
