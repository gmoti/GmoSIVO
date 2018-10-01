/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.facade.PosGraduacionesFacade;
import cl.gmo.pos.venta.web.facade.PosProductosFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

/**
 *
 * @author Advice70
 */
public class BusquedaProductosHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
    public ArrayList<FamiliaBean> traeFamilias(String form_origen)
    {
    	log.info("BusquedaProductosHelper:traeFamilias inicio");
        return PosUtilesFacade.traeFamilias(form_origen);
    }
    
    public ArrayList<TipoFamiliaBean> traeTipoFamilias(String form_origen, String codigoMultioferta)
    {
    	log.info("BusquedaProductosHelper:traeTipoFamilias inicio");
        return PosUtilesFacade.traeTipoFamilias(form_origen, codigoMultioferta);
    }
    
    public ArrayList<FamiliaBean> traeFamiliasMultiofertas(String form_origen, String codigoMultioferta, String tipoFamilia)
    {
    	log.info("BusquedaProductosHelper:traeFamiliasMultiofertas inicio");
        return PosUtilesFacade.traeFamiliasMultiofertas(form_origen, codigoMultioferta, tipoFamilia);
    }
    
    public ArrayList<SubFamiliaBean> traeSubFamilias(String familia)
    {
    	log.info("BusquedaProductosHelper:traeSubFamilias inicio");
        return PosUtilesFacade.traeSubFamilias(familia);
    }
    
    public ArrayList<SubFamiliaBean> traeSubFamiliasMultiofertas(String familia, String codigoMultioferta)
    {
    	log.info("BusquedaProductosHelper:traeSubFamiliasMultiofertas inicio");
        return PosUtilesFacade.traeSubFamiliasMultiofertas(familia, codigoMultioferta);
    }    
    
    public ArrayList<GrupoFamiliaBean> traeGrupoFamilias(String familia, String subfamilia)
    {
    	log.info("BusquedaProductosHelper:traeGrupoFamilias inicio");
        return PosUtilesFacade.traeGrupoFamilias(familia, subfamilia);
    }
    
    public ArrayList<GrupoFamiliaBean> traeGrupoFamiliasMultiofertas(String familia, String subfamilia, String codigoMultioferta)
    {
    	log.info("BusquedaProductosHelper:traeGrupoFamiliasMultiofertas inicio");
        return PosUtilesFacade.traeGrupoFamiliasMultiofertas(familia, subfamilia, codigoMultioferta);
    }
    
    public ArrayList<ProveedorBean> traeProveedores()
    {
    	log.info("BusquedaProductosHelper:traeProveedores inicio");
        return PosUtilesFacade.traeProveedores();
    }
    
    public ArrayList<ProductosBean> traeProductos(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda)
    {
    	log.info("BusquedaProductosHelper:traeProductos inicio");
    	String n=Constantes.STRING_MENOS_UNO;
    	String grpo = grupo;
    	if(n.equals(grpo)){
    		grpo = null;
    	}
        return PosProductosFacade.traeProductos(familia, subfamilia, grpo, proveedor, descripcion, fabricante,codigoBusqueda,codigoBarraBusqueda, local, tipo_busqueda);
    }

	public boolean visualizaBusquedaAvanzada(BusquedaProductosForm formulario, String form_origen, HttpSession session, String familia) {
		log.info("BusquedaProductosHelper:visualizaBusquedaAvanzada inicio");
		ArrayList<FamiliaBean> lista_familias = formulario.getListaFamilias();
		
		boolean estado = false;
		
		if (form_origen.equals(Constantes.STRING_PEDIDO) || form_origen.equals(Constantes.STRING_ACTION_PRESUPUESTO_MAY))
		{
			for (FamiliaBean familiaBean : lista_familias) 
			{
				if (familiaBean.getCodigo().equals(familia)) 
				{
					if (familiaBean.getTipo_fam().equals(Constantes.STRING_C))
					{
						estado = true;
					}
					break;
				}
			}
		}
		return estado;
	}

	public void traeGraduacionesClientes(BusquedaProductosForm formulario, HttpSession session) 
	{
		log.info("BusquedaProductosHelper:traeGraduacionesClientes inicio");
		String cliente = session.getAttribute(Constantes.STRING_CLIENTE).toString();
		try {
			formulario.setListaGraduaciones(PosGraduacionesFacade.traeGraduacionesClientes(cliente));
		} catch (Exception e) {
			log.error("BusquedaProductosHelper:traeGraduacionesClientes error catch",e);
		}
	}

	public void traeGraduacionSeleccionada(BusquedaProductosForm formulario) {
		log.info("BusquedaProductosHelper:traeGraduacionSeleccionada inicio");
		GraduacionesBean graduacion = new GraduacionesBean();
		graduacion =  formulario.getListaGraduaciones().get(formulario.getIndex_graduacion());
		formulario.setGraduacion(graduacion);
	}

	//Validación productos graduados 20180417
	public void traeProductosGraduados(BusquedaProductosForm formulario, HttpSession session) {
		log.info("BusquedaProductosHelper:traeProductosGraduados inicio");
		GraduacionesHelper helper_graduacion = new GraduacionesHelper();
		if (formulario.getGraduacion().getFecha().equals(Constantes.STRING_BLANCO))
		{
			session.setAttribute(Constantes.STRING_ESTADO, "error_graduacion");
			formulario.setListaProductos(new ArrayList<ProductosBean>());
			formulario.setFamilia(Constantes.STRING_CERO);
			formulario.setSubFamilia(Constantes.STRING_CERO);
			formulario.setGrupo(Constantes.STRING_CERO);
			formulario.setCodigoBusqueda(Constantes.STRING_BLANCO);
			formulario.setCodigoBarraBusqueda(Constantes.STRING_BLANCO);
			return;
		}
		if (formulario.isChk_cerca()) {
			helper_graduacion.realiza_Trasposicion_cerca(formulario.getGraduacion());
		}else{
			helper_graduacion.realiza_Trasposicion(formulario.getGraduacion());
		}
		
		session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
		
		ArrayList<FamiliaBean> lista_familias = formulario.getListaFamilias();
		String familia = formulario.getFamilia();
		String tipo_fam = Constantes.STRING_BLANCO;
			
			for (FamiliaBean familiaBean : lista_familias) 
			{
				if (familiaBean.getCodigo().equals(familia)) 
				{
						tipo_fam =  familiaBean.getTipo_fam();
						break;
				}
			}
		formulario.setListaProductos(PosProductosFacade.traeProductosGraduados(formulario.getOjo(), tipo_fam,
				familia, formulario.getSubFamilia(), formulario.getGrupo(), formulario.getDescripcion(),
				formulario.getCodigoBusqueda(), formulario.getCodigoBarraBusqueda(),
				session.getAttribute(Constantes.STRING_SUCURSAL).toString() , formulario.getGraduacion()));
		System.out.println("ESTA VACIO =>"+formulario.getListaProductos().isEmpty()+" OR SIZE =>"+formulario.getListaProductos().size());
		
	}
	public void traeProductosGraduadosMulti(BusquedaProductosForm formulario, HttpSession session) {
		log.info("BusquedaProductosHelper:traeProductosGraduados inicio");
		GraduacionesHelper helper_graduacion = new GraduacionesHelper();
		if (formulario.getGraduacion().getFecha().equals(Constantes.STRING_BLANCO))
		{
			session.setAttribute(Constantes.STRING_ESTADO, "error_graduacion");
			formulario.setListaProductos(new ArrayList<ProductosBean>());			
			formulario.setGrupo(Constantes.STRING_CERO);
			formulario.setCodigoBusqueda(Constantes.STRING_BLANCO);
			formulario.setCodigoBarraBusqueda(Constantes.STRING_BLANCO);
			return;
		}
		if (formulario.isChk_cerca()) {
			helper_graduacion.realiza_Trasposicion_cerca(formulario.getGraduacion());
		}else{
			helper_graduacion.realiza_Trasposicion(formulario.getGraduacion());
		}
		
		session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
					
		formulario.setListaProductos(PosProductosFacade.traeProductosGraduados(formulario.getOjo(), formulario.getTipofamilia(),
				formulario.getFamilia(), formulario.getSubFamilia(), formulario.getGrupo(), formulario.getDescripcion(),
				formulario.getCodigoBusqueda(), formulario.getCodigoBarraBusqueda(),
				session.getAttribute(Constantes.STRING_SUCURSAL).toString() , formulario.getGraduacion()));
		
	}
	public boolean validaRecetaCerca(BusquedaProductosForm formulario) 
	{
		log.info("BusquedaProductosHelper:validaRecetaCerca inicio");
		if (formulario.getOjo().equals(Constantes.STRING_DERECHO)) 
		{
			if (formulario.getGraduacion().getOD_adicion() == 0.0) 
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			if (formulario.getGraduacion().getOI_adicion() == 0.0) 
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}

	
	public boolean compruebaBusquedaPorCodigo(BusquedaProductosForm formulario, String local, String form_origen, boolean busqueda_avanzada_lentilla, HttpSession session) {
		log.info("BusquedaProductosHelper:compruebaBusquedaPorCodigo inicio");
		boolean estado = false;
		if (form_origen.equals(Constantes.STRING_PEDIDO) || form_origen.equals(Constantes.STRING_ACTION_PRESUPUESTO_MAY))
		{
			if (formulario.getFamilia().equals(Constantes.STRING_CERO)) {
				if (!formulario.getCodigoBusqueda().equals(Constantes.STRING_BLANCO) || !formulario.getCodigoBarraBusqueda().equals(Constantes.STRING_BLANCO)) {
					ProductosBean producto = new ProductosBean();
					producto = PosProductosFacade.traeProducto(formulario.getCodigoBusqueda(), 1, local, Constantes.STRING_PEDIDO, formulario.getCodigoBarraBusqueda());
					
					if(null != producto && null != producto.getCodigo())
					{
						if (producto.getTipoFamilia().equals(Constantes.STRING_C)) {
							formulario.setFamilia(producto.getFamilia());
							estado = true;
						}
						else if (this.esLenteContactoGraduable(producto)) {
							if (busqueda_avanzada_lentilla) {
								ArrayList<ProductosBean> lista = new ArrayList<ProductosBean>();
								lista.add(producto);
								formulario.setListaProductos(lista);
							}
							else
							{
								busqueda_avanzada_lentilla = true;
								session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA, true);
								estado = true;
							}
							
						}
						else
						{
							estado = false;
						}
						
					}
					
				}
				
			}
		}
		return estado;
	}

	
	public BusquedaProductosForm mostrarMultiOferta(BusquedaProductosForm formulario, String form_origen){
		String respuesta =null; 
		System.out.println("Paso por mostrar multioferta \n");
		ArrayList<ProductosBean> listaMostrarMulti = new ArrayList();
		try{
			
			if(form_origen.equals("DIRECTA")){            	
            	if(null != formulario.getListaProductos()){
            		int size = formulario.getListaProductos().size();
            		for(int i = 0; i < size; i++ ){
            			ProductosBean prod = formulario.getListaProductos().get(i);
            			if(null != prod){
            				if("MUL".equals(prod.getFamilia())){
            					String codigoMulti = prod.getCodigo();
                				respuesta  = PosProductosFacade.mostrarMultiOferta(codigoMulti);
                				if("TRUE".equals(respuesta)){
                					listaMostrarMulti.add(prod);      					
                				}               			
                			}else{
                				listaMostrarMulti.add(prod);  
                			}
            			}                			              			
            		}                		
            	}
            	formulario.setListaProductos(listaMostrarMulti);
            }
			
			
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return formulario;
	}
    
	public ArrayList<ProductosBean> traeProductosMulti(String familia, String subfamilia, String grupo,
            String proveedor, String descripcion, String fabricante, String codigoBusqueda, String codigoBarraBusqueda, String local, String tipo_busqueda, String codigo_barra_busqueda)
    {
		System.out.println("Paso por traeProductosMulti \n");

    	log.info("BusquedaProductosHelper:traeProductos inicio");
    	String n=Constantes.STRING_MENOS_UNO;
    	String grpo = grupo;
    	if(n.equals(grpo)){
    		grpo = null;
    	}
        return PosProductosFacade.traeProductosMulti(familia, subfamilia, grpo, proveedor, descripcion, fabricante,codigoBusqueda,codigoBarraBusqueda, local, tipo_busqueda, codigo_barra_busqueda);
    }
	
	/*
	 * Suplementos de la multioferta venta pedido
	 */
	public void verSuplementosProductoMultioferta(BusquedaProductosForm formulario,
			HttpSession session) {
		
		System.out.println("Paso por verSuplementosProductoMultioferta \n");

		
		log.info("VentaPedidoHelper:verSuplementosProducto inicio");		
		
		String indexproductomulti = formulario.getAddProducto();//indexproductomulti;
        int indexmulti = formulario.getIndex_multi();// indexmulti
        int indexproductoMultioferta =0;
        try{
        	indexproductoMultioferta = Integer.parseInt(indexproductomulti);
        }catch(Exception ex){
        	indexproductoMultioferta =0;
        }
        
        //extraigo todos los productos de la multioferta para despues diferenciar a cual corresponda de la linea de productos de la venta

        
		formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));//cargar los prodctos internos de la multioferta seleccionada.
		if(null != formulario.getListaProductosMultioferta()){
    		for (ProductosBean prodmulti : formulario.getListaProductosMultioferta()){
    			if(prodmulti.getIndexProductoMulti()== indexproductoMultioferta && prodmulti.getIndexRelMulti() == indexmulti){
    				formulario.setEstado(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO);
    				session.setAttribute(Constantes.STRING_PRODUCTO, prodmulti);
    			}
    		}
		}	
	}
	
	
	public void cargaListaProductosMultioferta(BusquedaProductosForm formulario, HttpSession session) {
		
		System.out.println("Paso por mostrar cargaListaProductosMultioferta \n");

		formulario.setListaMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS));
        ArrayList<ProductosBean> listaprodAux = new ArrayList<ProductosBean>();
        
        ProductosBean pro = new ProductosBean();
        pro.setCodigo(formulario.getCodigoMultioferta());
        pro.setIndexMulti(formulario.getIndex_multi());        
       
        if(null != formulario.getListaMultioferta()){//saber si la lista de multiofertas es nula
	        for (ProductosBean multi : formulario.getListaMultioferta()){//recorrer la lista de multiofertas
	        	if(pro.getCodigo().equals(multi.getCodigo())&& pro.getIndexMulti() == multi.getIndexMulti()){//preguntar si el producto multioferta es igual al producto que existe en la listaMultioferta	        		
	        		formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));//cargar los prodctos internos de la multioferta seleccionada.
	        		if(null != formulario.getListaProductosMultioferta()){
		        		for (ProductosBean prodmulti : formulario.getListaProductosMultioferta()){
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

		
	}
	
	public void agregaSuplementosProductoMultioferta(BusquedaProductosForm formulario,
			HttpSession session) {
		
			System.out.println("Paso por mostrar agregaSuplementosProductosMultioferta \n");

			log.info("VentaPedidoHelper:agregaSuplementosProducto inicio");
			ProductosBean prod = (ProductosBean)session.getAttribute(Constantes.STRING_PRODUCTO);
			session.setAttribute(Constantes.STRING_PRODUCTO, Constantes.STRING_BLANCO);
			ArrayList<SuplementopedidoBean> lista_suplementos = (ArrayList<SuplementopedidoBean>)session.getAttribute(Constantes.STRING_LISTA_SUPLEMENTOS);
			prod.setListaSuplementos(lista_suplementos);
			int i = 0;
			

			formulario.setListaProductosMultioferta((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX));//cargar los prodctos internos de la multioferta seleccionada.
			
			
			if(null != formulario.getListaProductosMultioferta()){
	    		for (ProductosBean prodmulti : formulario.getListaProductosMultioferta()){	    			
	    			if(prodmulti.getIndexProductoMulti()== prod.getIndexProductoMulti() && prodmulti.getIndexRelMulti() == prod.getIndexRelMulti()){
	    				
	    				formulario.getListaProductosMultioferta().set(i, prod);
	    				
	    			}
	    			i++;
	    		}
			}
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, formulario.getListaProductosMultioferta());
			i = 0;
			ArrayList<ProductosBean> listaproductosMultiAux = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX);
			if(null != listaproductosMultiAux){
	    		for (ProductosBean prodmulti : listaproductosMultiAux){	    			
	    			if(prodmulti.getIndexProductoMulti()== prod.getIndexProductoMulti() && prodmulti.getIndexRelMulti() == prod.getIndexRelMulti()){
	    				
	    				listaproductosMultiAux.set(i, prod);
	    				
	    			}
	    			i++;
	    		}
			}
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaproductosMultiAux);
	}
	
	public void modificaGrupoMultioferta(BusquedaProductosForm formulario, int index,
			String grupo, HttpSession session) {		
		
		ArrayList<ProductosBean> listaproductosMulti = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX);
		formulario.setListaProductosMultioferta(new ArrayList<ProductosBean>());//cargar los prodctos internos de la multioferta seleccionada.
		formulario.getListaProductosMultioferta().addAll(listaproductosMulti);
		if(null != formulario.getListaProductosMultioferta()){
	    	for (int i =0; i < formulario.getListaProductosMultioferta().size(); i++){
	    		ProductosBean prodmulti = formulario.getListaProductosMultioferta().get(i);
	    		int indexProductoMulti = Integer.parseInt(formulario.getAddProducto());
	    		if(prodmulti.getIndexProductoMulti()== indexProductoMulti  && prodmulti.getIndexRelMulti() == formulario.getIndex_multi()){
	    			prodmulti.setGrupo(grupo);
	    			formulario.getListaProductosMultioferta().set(i, prodmulti);
	    			
	    		}	    	
	    	}
		}	
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, formulario.getListaProductosMultioferta());
		
		ArrayList<ProductosBean> listaproductosMultiAux2 = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX);
		ArrayList<ProductosBean> listaproductosMultiAux = new ArrayList<ProductosBean>(); 
		listaproductosMultiAux.addAll(listaproductosMultiAux2);
		if(null != listaproductosMultiAux){
    		for (int i =0;i < listaproductosMultiAux.size();i++){
    			ProductosBean prodmulti = listaproductosMultiAux2.get(i);
    			int indexProductoMulti = Integer.parseInt(formulario.getAddProducto());
    			if(prodmulti.getIndexProductoMulti()== indexProductoMulti  && prodmulti.getIndexRelMulti() == formulario.getIndex_multi()){    				
    				listaproductosMultiAux.set(i, prodmulti);    				
    			}    		
    		}
		}
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaproductosMultiAux);
	}

	public boolean visualizaBusquedaAvanzadaLentilla(
			BusquedaProductosForm formulario, String form_origen,
			HttpSession session,  String familia) {
		log.info("BusquedaProductosHelper:visualizaBusquedaAvanzadaLentilla inicio");
		ArrayList<FamiliaBean> lista_familias = formulario.getListaFamilias();
		boolean estado = false;
		
		if (form_origen.equals(Constantes.STRING_PEDIDO) || form_origen.equals(Constantes.STRING_ACTION_PRESUPUESTO_MAY))
		{
			for (FamiliaBean familiaBean : lista_familias) 
			{
				if (familiaBean.getCodigo().equals(familia)) 
				{
					if (familiaBean.getTipo_fam().equals(Constantes.STRING_L))
					{
						estado = true;
					}
					break;
				}
			}
		}
		return estado;
	}

	public void verificaCantidadProductos(BusquedaProductosForm formulario) {
		int contador = Constantes.INT_CERO;
		for(TipoFamiliaBean tfam : formulario.getListaTipoFamilia()){    		
			contador = contador + tfam.getCantidad();
    	} 
		int contador_prod = Constantes.INT_CERO;
		if (null != formulario.getListaProductosMultioferta() && formulario.getListaProductosMultioferta().size() > 0) {
			for (ProductosBean prod : formulario.getListaProductosMultioferta()) {
				contador_prod = contador_prod + prod.getCantidad();
			}
		}		
		if (contador == contador_prod) {
			formulario.setCantidad_prod(Constantes.STRING_TRUE);
		}else{
			formulario.setCantidad_prod(Constantes.STRING_FALSE);
		}
	}

	public void validaBusquedasAvanzadas(BusquedaProductosForm formulario,
			HttpSession session, boolean busqueda_avanzada, boolean busqueda_avanzada_lentilla) {
		
		if (busqueda_avanzada) {
			if (null == formulario.getGraduacion() ||  null == formulario.getGraduacion().getFecha() ||
					formulario.getGraduacion().getFecha().equals(Constantes.STRING_BLANCO)) {
				
				formulario.setFamilia(Constantes.STRING_CERO);
				formulario.setCodigoBarraBusqueda(Constantes.STRING_BLANCO);
				formulario.setCodigoBusqueda(Constantes.STRING_BLANCO);
				session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_ERROR_GRADUACION);
				session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, false);
				
			}
			else
			{
				session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, true);
			}
		}
		else
		{
			session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA, busqueda_avanzada);
		}
		
		if (busqueda_avanzada_lentilla) {
			if (null == formulario.getGraduacion_lentilla() ||  null == formulario.getGraduacion_lentilla().getFecha() ||
					formulario.getGraduacion_lentilla().getFecha().equals(Constantes.STRING_BLANCO)) {
				
				formulario.setFamilia(Constantes.STRING_CERO);
				formulario.setCodigoBarraBusqueda(Constantes.STRING_BLANCO);
				formulario.setCodigoBusqueda(Constantes.STRING_BLANCO);
				session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA, false);
				session.setAttribute(Constantes.STRING_ESTADO, Constantes.STRING_ERROR_GRADUACION);
				
			}
			else
			{
				session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA, true);
			}
		}
		else
		{
			session.setAttribute(Constantes.STRING_BUSQUEDA_AVANZADA_LENTILLA, busqueda_avanzada_lentilla);
		}
		
		
	}

	public void actualizaListas(HttpSession session) {
		ArrayList<ProductosBean> listaProductosMultioferta = new ArrayList<ProductosBean>();
		listaProductosMultioferta =(ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);  
    	ArrayList<ProductosBean> listaProductosMultiofertaAux = new ArrayList<ProductosBean>();
    	listaProductosMultiofertaAux = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX);
        
    	if (null != listaProductosMultioferta) {
    		int x = 0;
    		int y = 0;
    		for (ProductosBean productos : listaProductosMultioferta)
    		{
    			for (ProductosBean prodmulti : listaProductosMultiofertaAux)
    			{
    				if(productos.getCodigoMultioferta().equals(prodmulti.getCodigoMultioferta()) && productos.getIndexProductoMulti() == prodmulti.getIndexProductoMulti() && productos.getIndexRelMulti() == prodmulti.getIndexRelMulti())
    				{
    					listaProductosMultioferta.set(x, prodmulti);
    				}
    				y++;
    			}
				x++;
			}
    	}
    	
    	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, listaProductosMultioferta);//actualiza la lista session de Multiofertas 
    	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, listaProductosMultiofertaAux);
		
		
	}

	public boolean validaEsLenteContactoGraduable(BusquedaProductosForm formulario, String form_origen, boolean busqueda_avanzada_lentilla) {
		
		ProductosBean prod = new ProductosBean();
		if (null != formulario.getFamilia()) {
			prod.setFamilia(formulario.getFamilia());
		}
		else
		{
			prod.setFamilia(Constantes.STRING_BLANCO);
		}
		if (null != formulario.getSubFamilia()) {
			prod.setSubFamilia(formulario.getSubFamilia());
		}
		else
		{
			prod.setSubFamilia(Constantes.STRING_BLANCO);
		}
		if (null != formulario.getGrupo()) {
			prod.setGrupoFamilia(formulario.getGrupo());
		}
		else
		{
			prod.setGrupoFamilia(Constantes.STRING_BLANCO);
		}
		
			if (!form_origen.equals(Constantes.STRING_DIRECTA)) {
				
				if (busqueda_avanzada_lentilla) {
					return false;
				}
				else
				{
					return esLenteContactoGraduable(prod);
				}
				
			}
			else
			{
				return false;
			}
	}
	/*
	 * LMARIN 20140508 
	 * Compruebo si  el producto tien suplementos obligatorios 
	 */
	
	public boolean tiene_suplementos_obligatorios(BusquedaProductosForm formulario) throws Exception {
		
		ArrayList<SuplementopedidoBean> prod = new ArrayList<SuplementopedidoBean>();
		
		prod = PosUtilesFacade.traeSuplementosObligatorios(formulario.getCodigo_barras().replace(" ","+"));
		
		boolean res = false;
		
		if(prod.isEmpty()){
			res = false;
		}else{
			res = true;
		}
		
		return res;
	}

	/* 
	 * LMARIN 20140828
	 * Método que devuelve las multiofertas asociadas a un trio
	 * @param String armazon
	 * @param String cristal
	 * @param String tienda
	 * @return ArrayList<ProductosBean> codigo multiofertas
	 */
	public ArrayList<ProductosBean>  traeMultiofertasAso(String armazon,String cristal,String tienda){
		
		ArrayList<ProductosBean> prod = new ArrayList<ProductosBean>(); 
		try {
			 prod = PosUtilesFacade.traeMultiofertasAso(armazon, cristal, tienda);
			 
			 System.out.println("Trae Multiofertas Facade ===>"+prod.size());

		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		return prod;
		
	} 
	
}
