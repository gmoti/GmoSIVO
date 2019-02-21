/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;


import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.controlador.presupuesto.GraduacionesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoDAOImpl;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.facade.PosGraduacionesFacade;
import cl.gmo.pos.venta.web.facade.PosProductosFacade;
import cl.gmo.pos.venta.web.facade.PosSeleccionPagoFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaPedidoFacade;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;
import cl.gmo.pos.venta.web.utils.tu_edad_tu_descuento;

/**
 *
 * @author Advice70
 */
public class VentaPedidoHelper extends Utils{
    
	Logger log = Logger.getLogger( this.getClass() );

	public BigDecimal traeDecuento(String usuario, String pass, String tipo)
	{
		System.out.println("PASO 1 VPH");
		log.info("VentaPedidoHelper:traeDecuento inicio");
        return PosUtilesFacade.traeDescuento(usuario, pass, tipo);
    }
    public ArrayList<DivisaBean> traeDivisas()
    {
    	System.out.println("PASO 2 VPH");
    	log.info("VentaPedidoHelper:traeDivisas inicio");
        return PosUtilesFacade.traeDivisas();
    }
    

    public ArrayList<FormaPagoBean> traeFormasPago()
    {
    	System.out.println("PASO 3 VPH");
    	log.info("VentaPedidoHelper:traeFormasPago inicio");
        return PosUtilesFacade.traeFormasPago();
    }
    
    public ArrayList<IdiomaBean> traeIdiomas()
    {
    	System.out.println("PASO 4 VPH");
    	log.info("VentaPedidoHelper:traeIdiomas inicio");
        return PosUtilesFacade.traeIdiomas();
    }
    
    public ArrayList<ConvenioBean> traeConvenios()
    {
    	System.out.println("PASO 5 VPH");
    	log.info("VentaPedidoHelper:traeConvenios inicio");
        return PosUtilesFacade.traeConvenios();
    }
    
    public ArrayList<AgenteBean> traeAgentes(String local)
    {
    	System.out.println("PASO 6 VPH");
    	log.info("VentaPedidoHelper:traeAgentesinicio inicio");
        return PosUtilesFacade.traeAgentes(local);
    }
    
    /*
     * Modificado LMARIN 20150422
     *
     */
    public ArrayList<PromocionBean> traePromociones() 
    {
    	System.out.println("PASO 7 VPH");
    	log.info("VentaPedidoHelper:traePromociones inicio");
    	return PosVentaPedidoFacade.traePromociones();    	
    }
    
    
    /*LMARIN 20140122*/
    public ArrayList<VentaPedidoBean> traeGruposEncargo(String encargo)
    {
    	log.info("VentaPedidoHelper:traeAgentesinicio inicio");
        return PosUtilesFacade.trae_grupos_encargo(encargo);
    }
    
    public void preCarga(VentaPedidoForm formulario, String local) {
    	System.out.println("PASO 8 VPH");
    	log.info("VentaPedidoHelper:traePromociones inicio");
    	//se elimina la validacion de caja al cargar el formulario
    	//if (this.validaCaja(local, this.formatoFecha(this.traeFecha()))) {
    		formulario.setFecha(this.formatoFecha(this.traeFecha()));
    
		//}
		formulario.setHora(this.formatoHora(this.traeFecha()));
		//formulario.setCodigo(this.formato_Numero_Ticket(PosVentaPedidoFacade.traeCodigoVenta(local)));
		formulario.setCodigo(Constantes.STRING_BLANCO);
				
	}
    public void limpiaPreliminar(VentaPedidoForm formulario, Session session) 
    {
    	System.out.println("PASO 9 VPH");
    	log.info("VentaPedidoHelper:limpiaPreliminar inicio");
		formulario.setCodigo(Constantes.STRING_BLANCO);
		formulario.setCambio(Constantes.INT_CERO);
		formulario.setCodigo_suc(Constantes.STRING_BLANCO);
		formulario.setForma_pago(Constantes.STRING_CERO);
		formulario.setAgente(Constantes.STRING_CERO);
		formulario.setDivisa(Constantes.STRING_CERO);
		formulario.setIdioma(Constantes.STRING_CERO);
		formulario.setCantidadProductos(Constantes.INT_CERO);
		formulario.setListaGrupos(null);
		formulario.setMostrarDev(false);
		formulario.setEncargo_seguro(Constantes.STRING_BLANCO);
		//formulario.setGraduacion(new GraduacionesBean());
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, null);
		session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, null);
		session.setAttribute(Constantes.STRING_LISTA_MULTIOFERTAS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX, null);
    	session.setAttribute(Constantes.STRING_CONVENIO, null);
    	session.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, Constantes.STRING_BLANCO);
    	
	}

    public void traeDatosFormulario(VentaPedidoForm formulario, Session session)
    {
    	System.out.println("PASO 10 VPH");
    	log.info("VentaPedidoHelper:traeDatosFormulario inicio");

	
    	try {
	    	String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
			formulario.setCodigo_suc(PosVentaPedidoFacade.traeCodigoSuc(local));
			ArrayList<TiendaBean> dt = PosUtilesFacade.traeDatosTienda(local);
			
			//DATOS GENERICOS DESDE CONFIGURACION Y SESSION
			String agente = session.getAttribute(Constantes.STRING_USUARIO).toString();
			VentaPedidoBean  ventaPedido = new VentaPedidoBean();
			ventaPedido = PosVentaPedidoFacade.traeGenericos(local);
			formulario.setIdioma(ventaPedido.getIdioma());
			formulario.setDivisa(ventaPedido.getDivisa());
			formulario.setForma_pago(ventaPedido.getForma_pago());
			formulario.setCambio(ventaPedido.getCambio());
			formulario.setAgente(agente);
			formulario.setPorcentaje_anticipo(ventaPedido.getPorcentaje_anticipo());
			formulario.setPorcentaje_descuento_max(ventaPedido.getPorcentaje_descuento_maximo());
			formulario.setCaja(ventaPedido.getNumero_caja());
			formulario.setFenix(ventaPedido.getFenix());
			formulario.setTipoimp(dt.get(0).getTipo_impresion()); 
			if(ventaPedido.getFtaller() == 1) {
				formulario.setMuestra_ftaller("S");
			}else {
				formulario.setMuestra_ftaller("N");
			}
    	} catch (Exception e) {
			log.warn("Helper traeDatosFormulario => ",e);
		}
		
    }
    
    public ArrayList<ProductosBean> actualizaProductosMultioferta(Session session, BusquedaProductosForm formulario, int indexRel_Multioferta, String addProducto,
  			int cantidad, ArrayList<ProductosBean> listaProductos,
			String local, String tipo_busqueda, String codigoMultioferta, ArrayList<TipoFamiliaBean> listaTipoFamilias, ArrayList<ProductosBean> listaProductosAux) {
    	
    		System.out.println("PASO 11 VPH");
    	
  			log.info("VentaDirectaHelper:actualizaProductosMultioferta inicio");
  			ProductosBean prod = new ProductosBean();
  			System.out.println("ADD PRODUCTO ==> "+addProducto+" <==> CANTIDAD <=> "+cantidad+"<==> LOCAL => "+local+" <=> TIPO BUSQUEDA =>"+tipo_busqueda);

  			prod  = PosProductosFacade.traeProducto(addProducto, cantidad, local, tipo_busqueda, null); 			
  			  			
	  			if(null != prod){
	  				System.out.println("codigoMultioferta =>"+codigoMultioferta);
	  				System.out.println("indexRel_Multioferta =>"+indexRel_Multioferta);
	  				prod.setCodigoMultioferta(codigoMultioferta);
	  				prod.setIndexRelMulti(indexRel_Multioferta);
	  				System.out.println("MUL ===> "+this.obtenerIndexProductoMultiOferta(listaProductos, codigoMultioferta, indexRel_Multioferta));
	  				prod.setIndexProductoMulti(this.obtenerIndexProductoMultiOferta(listaProductos, codigoMultioferta, indexRel_Multioferta));
		  			if (null == listaProductos)
		  			{
		  				listaProductos = new ArrayList<ProductosBean>();	
		  				listaProductos =  actualizaProductosMulti(prod, formulario, cantidad, listaProductos, local, tipo_busqueda, prod.getCod_barra(), formulario.getGraduacion(), formulario.getOjo(), formulario.getDescripcion(), session);
		  				
		  			}
		  			else
		  			{	
		  				listaProductos = actualizaProductosMulti(prod, formulario, cantidad, listaProductos, local, tipo_busqueda, prod.getCod_barra(), formulario.getGraduacion(), formulario.getOjo(), formulario.getDescripcion(), session);
		  			}	
		  			
		  			
	  			}  			
  			
		return listaProductos;
	}
    								
    public ArrayList<ProductosBean> actualizaProductosMulti(ProductosBean prod, BusquedaProductosForm formulario,int cantidad, ArrayList<ProductosBean> listaProductos,
			String local, String tipo_busqueda, String cod_barra, GraduacionesBean graduacion, String ojo, 
			String tipo, Session session) {
    	
    	System.out.println("PASO 12 VPH");
    	boolean cristal_validado = true;
		log.info("VentaPedidoHelper:actualizaProductos inicio");
		//REALIZA TRASPOSICION 
			GraduacionesHelper helper_graduacion = new GraduacionesHelper();			
			
			if (Constantes.STRING_LEJOS_OPT.equals(tipo) && tipo != null) {
				helper_graduacion.realiza_Trasposicion(graduacion);
			}
			else if (Constantes.STRING_CERCA_OPT.equals(tipo) && tipo != null) {
				helper_graduacion.realiza_Trasposicion_cerca(graduacion);
			}
			else
			{
				tipo = Constantes.STRING_BLANCO;
			}
			
						
			if (Constantes.STRING_C.equals(prod.getTipoFamilia()) || Constantes.STRING_M.equals(prod.getTipoFamilia())) {
				prod.setTiene_grupo(Constantes.STRING_TRUE);
				if (prod.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI) || prod.getCod_barra().equals(Constantes.STRING_ACTION_CCLI)) {
					
					prod.setDescripcion_manual(Constantes.STRING_FALSE);
					
					 //HABILITA DESCRIPCION MANUAL
					
					prod.setDescripcion_manual(Constantes.STRING_TRUE);    
					//formulario.setEstado(Constantes.STRING_PRODUCTO_CLIENTE);
				}
				else
				{
					prod.setDescripcion_manual(Constantes.STRING_FALSE);
				}
			}
			
			if (Constantes.STRING_C.equals(prod.getTipoFamilia()) || Constantes.STRING_L.equals(prod.getTipoFamilia())) 
			{
				
				session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, prod);
				if (Constantes.STRING_ACTION_OCHO_NUEVE_NUEVE.equals(prod.getFamilia()))
				{
					if (ojo.equals(Constantes.STRING_DERECHO)) {
						prod.setOjo(ojo);
						prod.setEsfera(graduacion.getOD_esfera_traspuesto());
						prod.setEje(graduacion.getOD_eje_traspuesto());
						prod.setCilindro(graduacion.getOD_cilindro_traspuesto());
					}
					else if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
						prod.setOjo(ojo);
						prod.setEsfera(graduacion.getOI_esfera_traspuesto());
						prod.setEje(graduacion.getOI_eje_traspuesto());
						prod.setCilindro(graduacion.getOI_cilindro_traspuesto());
					}
					else
					{
						prod.setOjo(Constantes.STRING_BLANCO);
					}
					prod.setGrad_fecha(graduacion.getFecha());
					prod.setGrad_numero(graduacion.getNumero());
					prod.setFecha_graduacion(graduacion.getFecha());
					prod.setNumero_graduacion(graduacion.getNumero());
				}
				else
				{
					if (ojo.equals(Constantes.STRING_DERECHO)) {
						prod.setEsfera(graduacion.getOD_esfera_traspuesto());
						prod.setEje(graduacion.getOD_eje_traspuesto());
						prod.setCilindro(graduacion.getOD_cilindro_traspuesto());
						prod.setOjo(ojo);
					}
					else if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
						prod.setEsfera(graduacion.getOI_esfera_traspuesto());
						prod.setEje(graduacion.getOI_eje_traspuesto());
						prod.setCilindro(graduacion.getOI_cilindro_traspuesto());
						prod.setOjo(ojo);
					}
					else
					{
						prod.setOjo(Constantes.STRING_BLANCO);
					}
					prod.setTipo(tipo);
					prod.setGrad_fecha(graduacion.getFecha());
					prod.setGrad_numero(graduacion.getNumero());
					prod.setFecha_graduacion(graduacion.getFecha());
					prod.setNumero_graduacion(graduacion.getNumero());
				}
				
				
				if (this.valida_existe_error_cristal(prod, tipo, ojo)) {
					cristal_validado = false;
				}
				else
				{
					//VERIFICA SI DEBE TENER SUPLEMENTOS OBLIGATORIOS
					ArrayList<SuplementopedidoBean> suplementos = new ArrayList<SuplementopedidoBean>();
					try {
						suplementos = PosUtilesFacade.traeSuplementosObligatorios(cod_barra);
					} catch (Exception e) {
						log.error("VentaPedidoHelper:actualizaProductos error catch",e);
					}
					if (null != suplementos && suplementos.size() != Constantes.INT_CERO)
					{
						 formulario.setEstado(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO);
						 session.setAttribute(Constantes.STRING_PRODUCTO, prod);
						 prod.setTiene_suple(Constantes.STRING_TRUE);
					}
					
					
					
					//VERIFICA SI DEBE TENER SUPLEMENTOS OPCIONALES
					ArrayList<SuplementopedidoBean> suplementos_OPC = new ArrayList<SuplementopedidoBean>();
					try {
						suplementos_OPC = PosUtilesFacade.traeSuplementosOpcionales(cod_barra);
					} catch (Exception e) {
						log.error("VentaPedidoHelper:actualizaProductos error catch",e);
					}
					if (null != suplementos_OPC && suplementos_OPC.size() != Constantes.INT_CERO)
					{
						 prod.setTiene_suple(Constantes.STRING_TRUE);
					}
					
					 //prod.setTiene_suple(Constantes.STRING_TRUE);//solo para prueba se debe eliminar esta linea.
				}
			
			}
			
			//VERIFICA SI NECESITA CONFIRMACION DE CODIGO GAFA SOL O MONTURA
			if (Constantes.STRING_M.equals(prod.getTipoFamilia())) {
				formulario.setEstado(Constantes.STRING_PRODUCTO_CON_CONFIRMACION);
				session.setAttribute(Constantes.STRING_PRODUCTO, prod);
				
				
				
			}
			else
			{
				if (Constantes.STRING_G.equals(prod.getTipoFamilia())) {
					try {
						if (PosUtilesFacade.validaGafaGraduable(prod.getCodigo())) {
							formulario.setEstado(Constantes.STRING_PRODUCTO_CON_CONFIRMACION);
							session.setAttribute(Constantes.STRING_PRODUCTO, prod);
						}
					} catch (Exception e) {
						log.error("VentaPedidoHelper:actualizaProductos error catch",e);
					}
				}
			}
			
			//extrae los productos de la multioferta
			ArrayList<ProductosBean> lista_productos_enc = new ArrayList<ProductosBean>();
			ArrayList<ProductosBean> lista_productos_total = new ArrayList<ProductosBean>();
			lista_productos_enc = (ArrayList<ProductosBean>) session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
			if (null != listaProductos) {
				lista_productos_total.addAll(listaProductos);
			}
			
			
			if (null != lista_productos_enc && lista_productos_enc.size()>0) {
				for (ProductosBean productosBean : lista_productos_enc) {
					lista_productos_total.add(productosBean);
				}
			}
			
				if(null != prod){
					if (cristal_validado) 
					{
						//validacion cristal add receta /300092 /10-05-2013
						//devuele true, si el cristal necesita adicion
						boolean cristal_valida_add = true;
						if(this.validaCristalADD(prod, graduacion))
						{
							//devuelve true si esta correcto
							if (this.validaCristalRecetaADD(prod, graduacion)) {
								if (!this.validaCristalRecetaADDDesdeHasta(prod, graduacion)) {
									formulario.setEstado(Constantes.STRING_ERROR_CRISTAL_ADD_NOPERMITIDA);
									cristal_valida_add = false;
								}
							}
							else
							{
								formulario.setEstado(Constantes.STRING_ERROR_CRISTAL_SIN_ADICION);
								cristal_valida_add = false;
							}
						}
						
						//agregar validacion aqui
						if (cristal_valida_add) {
						
				  			if (null == lista_productos_total || lista_productos_total.size() == Constantes.INT_CERO)
				  			{
				  				listaProductos = new ArrayList<ProductosBean>();
				  				prod.setIndice(Constantes.INT_CERO);
				  				
				  				if (prod.getTiene_grupo().equals(Constantes.STRING_TRUE)) {
									prod.setGrupo(Constantes.STRING_UNO);
								}
				  				listaProductos.add(0,prod);
				  			}
				  			else
				  			{
				  				if (prod.getTiene_grupo().equals(Constantes.STRING_TRUE)) 
				  				{
					  				int maxgrupo = Constantes.INT_UNO;
									int cantidad_gr = Constantes.INT_CERO;
									String maxLista="";
									/*
									 * Determina el MAX de grupo que existe en la lista de productos de la 
									 * multioferta
									 */
					  				for (ProductosBean productos : lista_productos_total) {
					  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
										if (maxgrupo < Integer.parseInt(productos.getGrupo())) {
											maxgrupo = Integer.parseInt(productos.getGrupo());
										}
									}
					  				
					  				/*
									 * Determina el MAX de grupo que existe en la lista de productos de la 
									 * venta pedido
									 */
					  				/*int maxgrupoPedido = Constantes.INT_UNO;
					  				ArrayList<ProductosBean> listaProductosPedido = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
					  				if(null != listaProductosPedido){
						  				for (ProductosBean productos : listaProductosPedido) {
						  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
											if (maxgrupoPedido < Integer.parseInt(productos.getGrupo())) {
												maxgrupoPedido = Integer.parseInt(productos.getGrupo());
											}
										}
					  				}*/		  				
					  				
					  				
					  				/*if(maxgrupo >= maxgrupoPedido){
					  					maxLista="MULTI";
					  				}else{
					  					maxLista="PEDIDO";
					  				}*/
					  				
					  				
					  				//if("MULTI".equals(maxLista)){
					  					/*
					  					 * Contar la cantidad de veces que se encuentra el grupo dentro de la lista
					  					 * de productos multioferta y lista de productos.
					  					 */
						  				for (ProductosBean productos : lista_productos_total) {
						  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
											if (maxgrupo == Integer.parseInt(productos.getGrupo())) {
												cantidad_gr += 1;
											}
										}
						  				/*if(null != listaProductosPedido){
							  				for (ProductosBean productos : listaProductosPedido) {
							  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
												if (maxgrupoPedido == Integer.parseInt(productos.getGrupo())) {
													cantidad_gr += 1;
												}
											}
						  				}*/	
						  				
						  				if (cantidad_gr < 3) {
											prod.setGrupo(String.valueOf(maxgrupo));
										}
						  				else
						  				{
						  					prod.setGrupo(String.valueOf(maxgrupo + 1));
						  				}
					  				/*}else{
					  					
					  					 * Contar la cantidad de veces que se encuentra el grupo dentro de la lista
					  					 * de productos multioferta y lista de productos.
					  					 
					  					cantidad_gr = Constantes.INT_CERO;
					  					for (ProductosBean productos : listaProductosPedido) {
						  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
											if (maxgrupoPedido == Integer.parseInt(productos.getGrupo())) {
												cantidad_gr += 1;
											}
										}
					  					for (ProductosBean productos : listaProductos) {
						  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
											if (maxgrupo == Integer.parseInt(productos.getGrupo())) {
												cantidad_gr += 1;
											}
										}
						  				if (cantidad_gr < 3) {
											prod.setGrupo(String.valueOf(maxgrupoPedido));
										}
						  				else
						  				{
						  					prod.setGrupo(String.valueOf(maxgrupoPedido + 1));
						  				}
					  				}*/
				  				}
				  				System.out.println("SIZE =====<<<<<<<<<" +listaProductos.size());
				  				prod.setIndice(listaProductos.size());
				  				listaProductos.add(listaProductos.size(),prod);
				  				formulario.setListaProductos(listaProductos);
				  				
				  				//verifica ti tiene promocion
				  				/*if (!Constantes.STRING_CERO.equals(formulario.getPromocion())) 
				  				{
				  					this.aplica_descuento(formulario);
				  				}*/
				  			}	
						}
					}
					else
					{
						formulario.setEstado(Constantes.STRING_ERROR_CRISTAL_INCOM);
					}
				}			
				
		return listaProductos;
	}
    
	public ArrayList<ProductosBean> actualizaProductos(VentaPedidoForm formulario, String addProducto,
			int cantidad, ArrayList<ProductosBean> listaProductos,
			String local, String tipo_busqueda, String cod_barra, GraduacionesBean graduacion, String ojo, 
			String tipo, Session session) throws Exception {
		
		System.out.println("PASO 13 VPH");
		
		log.info("VentaPedidoHelper:actualizaProductos inicio");
		//REALIZA TRASPOSICION
			
		boolean cristal_validado = true;
		boolean cristal_valida_add = false;
		
		GraduacionesHelper helper_graduacion = new GraduacionesHelper();
			
			formulario.setCodigo_mult(Constantes.STRING_BLANCO);
	  		formulario.setIndex_multi(Constantes.INT_CERO);			

			ProductosBean prod = new ProductosBean();
			prod  = PosProductosFacade.traeProducto(addProducto, cantidad, local, tipo_busqueda, cod_barra);
			
				
			if (Constantes.STRING_LEJOS_OPT.equals(tipo)) {
				helper_graduacion.realiza_Trasposicion(graduacion);
			}
			else if (Constantes.STRING_CERCA_OPT.equals(tipo)) {
				helper_graduacion.realiza_Trasposicion_cerca(graduacion);
			}
			else
			{
				tipo = Constantes.STRING_BLANCO;
			}
			
			//verifica descuento por convenio
			//System.out.println("Descuento por convenio ==> "+formulario.getConvenio());
			if (!Constantes.STRING_BLANCO.equals(formulario.getConvenio()))
			{
					System.out.println("Paso Descuento por convenio ==> "+formulario.getConvenio());									
					this.aplicaDescuentoConvenio(prod, formulario.getConvenio(), formulario.getForma_pago(),listaProductos,local);
			}
			
			//20180220  Aplico DTO POR CUPON
			
			
						
			if (Constantes.STRING_C.equals(prod.getTipoFamilia()) || Constantes.STRING_M.equals(prod.getTipoFamilia()) || "G".equals(prod.getTipoFamilia())) {
				prod.setTiene_grupo(Constantes.STRING_TRUE);
				if (prod.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI) || prod.getCod_barra().equals(Constantes.STRING_ACTION_CCLI)) {
					
					prod.setDescripcion_manual(Constantes.STRING_FALSE);
					
					 //HABILITA DESCRIPCION MANUAL
					
					prod.setDescripcion_manual(Constantes.STRING_TRUE);
					if (prod.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI))
					{
						formulario.setEstado(Constantes.STRING_PRODUCTO_ARCLI);
					}
					
				}
				else
				{
					prod.setDescripcion_manual(Constantes.STRING_FALSE);
				}
				
				
			}
			//para lentillas
			if (Constantes.STRING_L.equals(prod.getTipoFamilia())) {
				
				if (this.esLenteContactoGraduable(prod)) {
					session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, prod);
					ContactologiaBean contactologia = new ContactologiaBean();
					contactologia = formulario.getGraduacion_lentilla();
					
						if (ojo.equals(Constantes.STRING_DERECHO)) {
							prod.setOjo(ojo);
							if (null == contactologia.getOdesfera()) 
							{ 
								prod.setEsfera(Constantes.INT_CERO);
							}
							else
							{
								prod.setEsfera(contactologia.getOdesfera());
							}
							if (null == contactologia.getOdeje()) 
							{
								prod.setEje(Constantes.INT_CERO);
							}
							else
							{
								prod.setEje(contactologia.getOdeje());
							}
							if (null == contactologia.getOdcilindro()) 
							{
								prod.setCilindro(Constantes.INT_CERO);
							}
							else
							{
								prod.setCilindro(contactologia.getOdcilindro());
							}
							
						}
						else if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
							prod.setOjo(ojo);
							if (null == contactologia.getOiesfera()) 
							{ 
								prod.setEsfera(Constantes.INT_CERO);
							}
							else
							{
								prod.setEsfera(contactologia.getOiesfera());
							}
							if (null == contactologia.getOieje()) 
							{
								prod.setEje(Constantes.INT_CERO);
							}
							else
							{
								prod.setEje(contactologia.getOieje());
							}
							if (null == contactologia.getOicilindro()) 
							{
								prod.setCilindro(Constantes.INT_CERO);
							}
							else
							{
								prod.setCilindro(contactologia.getOicilindro());
							}
						}
						else
						{
							prod.setOjo(Constantes.STRING_BLANCO);
						}
						prod.setTipo(tipo);
						prod.setGrad_fecha(contactologia.getFecha());
						prod.setGrad_numero(contactologia.getNumero());
						prod.setFecha_graduacion(contactologia.getFecha());
						prod.setNumero_graduacion(contactologia.getNumero());
						
						if (this.valida_existe_error_cristal(prod, tipo, ojo)) {
							cristal_validado = false;
						}
				}
			}
			//para cristales
			if (Constantes.STRING_C.equals(prod.getTipoFamilia())) 
			{
				session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, prod);
				if (Constantes.STRING_ACTION_OCHO_NUEVE_NUEVE.equals(prod.getFamilia()))
				{
					if (ojo.equals(Constantes.STRING_DERECHO)) {
						prod.setOjo(ojo);
						prod.setEsfera(graduacion.getOD_esfera_traspuesto());
						prod.setEje(graduacion.getOD_eje_traspuesto());
						prod.setCilindro(graduacion.getOD_cilindro_traspuesto());
					}
					else if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
						prod.setOjo(ojo);
						prod.setEsfera(graduacion.getOI_esfera_traspuesto());
						prod.setEje(graduacion.getOI_eje_traspuesto());
						prod.setCilindro(graduacion.getOI_cilindro_traspuesto());
					}
					else
					{
						prod.setOjo(Constantes.STRING_BLANCO);
					}
					prod.setTipo(tipo);
					prod.setGrad_fecha(graduacion.getFecha());
					prod.setGrad_numero(graduacion.getNumero());
					prod.setFecha_graduacion(graduacion.getFecha());
					prod.setNumero_graduacion(graduacion.getNumero());
				}
				else
				{
					if (ojo.equals(Constantes.STRING_DERECHO)) {
						prod.setEsfera(graduacion.getOD_esfera_traspuesto());
						prod.setEje(graduacion.getOD_eje_traspuesto());
						prod.setCilindro(graduacion.getOD_cilindro_traspuesto());
						prod.setOjo(ojo);
					}
					else if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
						prod.setEsfera(graduacion.getOI_esfera_traspuesto());
						prod.setEje(graduacion.getOI_eje_traspuesto());
						prod.setCilindro(graduacion.getOI_cilindro_traspuesto());
						prod.setOjo(ojo);
					}
					else
					{
						prod.setOjo(Constantes.STRING_BLANCO);
					}
					prod.setTipo(tipo);
					prod.setGrad_fecha(graduacion.getFecha());
					prod.setGrad_numero(graduacion.getNumero());
					prod.setFecha_graduacion(graduacion.getFecha());
					prod.setNumero_graduacion(graduacion.getNumero());
				}
				
				if (this.valida_existe_error_cristal(prod, tipo, ojo)) {
					cristal_validado = false;
				}
				else
				{
					//VERIFICA SI DEBE TENER SUPLEMENTOS OBLIGATORIOS
					ArrayList<SuplementopedidoBean> suplementos = new ArrayList<SuplementopedidoBean>();
					try {
						suplementos = PosUtilesFacade.traeSuplementosObligatorios(cod_barra);
					} catch (Exception e) {
						log.error("VentaPedidoHelper:actualizaProductos error catch",e);
					} 
					if (null != suplementos && suplementos.size() != Constantes.INT_CERO)
					{
						 formulario.setEstado(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO);
						 session.setAttribute(Constantes.STRING_PRODUCTO, prod);
						 prod.setTiene_suple(Constantes.STRING_TRUE);
					}
					
					
					
					//VERIFICA SI DEBE TENER SUPLEMENTOS OPCIONALES
					ArrayList<SuplementopedidoBean> suplementos_OPC = new ArrayList<SuplementopedidoBean>();
					try {
						suplementos_OPC = PosUtilesFacade.traeSuplementosOpcionales(cod_barra);
					} catch (Exception e) {
						log.error("VentaPedidoHelper:actualizaProductos error catch",e);
					}
					
					if (null != suplementos_OPC && suplementos_OPC.size() != Constantes.INT_CERO)
					{
						 prod.setTiene_suple(Constantes.STRING_TRUE);
					}
					
				}
				
				
			}
			
			//VERIFICA SI NECESITA CONFIRMACION DE CODIGO GAFA SOL O MONTURA
			/*if (Constantes.STRING_M.equals(prod.getTipoFamilia()) &&(!Constantes.STRING_ACTION_ARCLI.equals(prod.getCod_barra()))) {
				formulario.setEstado(Constantes.STRING_PRODUCTO_CON_CONFIRMACION);
				session.setAttribute(Constantes.STRING_PRODUCTO, prod);
				
			}
			else
			{
				if (Constantes.STRING_G.equals(prod.getTipoFamilia())) {
					try {
						if (PosUtilesFacade.validaGafaGraduable(prod.getCodigo())) {
							formulario.setEstado(Constantes.STRING_PRODUCTO_CON_CONFIRMACION);
							session.setAttribute(Constantes.STRING_PRODUCTO, prod);
						}
					} catch (Exception e) {
						log.error("VentaPedidoHelper:actualizaProductos error catch",e);
					}
				}
			}*/
			
			//extrae los productos de la multioferta
			ArrayList<ProductosBean> lista_productos_multi = new ArrayList<ProductosBean>();
			ArrayList<ProductosBean> lista_productos_total = new ArrayList<ProductosBean>();
			lista_productos_multi = (ArrayList<ProductosBean>) session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
			if (null != listaProductos) {
				lista_productos_total.addAll(listaProductos);
			}
			
			if (null != lista_productos_multi && lista_productos_multi.size()>0) {
				for (ProductosBean productosBean : lista_productos_multi) {
					lista_productos_total.add(productosBean);
				}
			}
				
				
			if(null != prod){
					if (cristal_validado) 
					{		
						//validacion cristal add receta /300092 /10-05-2013
						//devuele true, si el cristal necesita adicion
						cristal_valida_add = true;
						if(this.validaCristalADD(prod, graduacion))
						{
							//devuelve true si esta correcto
							if (this.validaCristalRecetaADD(prod, graduacion)) {
								if (!this.validaCristalRecetaADDDesdeHasta(prod, graduacion)) {
									formulario.setError(Constantes.STRING_ERROR_CRISTAL_ADICION);
									cristal_valida_add = false;
								}
							}
							else
							{
								formulario.setError(Constantes.STRING_ERROR_CRISTAL_ADD);
								cristal_valida_add = false;
							}
						}
						
						//agregar validacion aqui
						if (cristal_valida_add) {
							if (null == lista_productos_total || lista_productos_total.size() == Constantes.INT_CERO)
				  			{
				  				
				  				listaProductos = new ArrayList<ProductosBean>();
				  				prod.setIndice(Constantes.INT_CERO);
				  				
				  				if (prod.getTiene_grupo().equals(Constantes.STRING_TRUE)) {	
				  					if("G".equals(prod.getTipoFamilia())){
				  						prod.setGrupo(Constantes.STRING_CERO);
				  					}else{
				  						prod.setGrupo(Constantes.STRING_UNO);
				  					}
									
								}
				  				listaProductos.add(0,prod);
				  			}
				  			else
				  			{
				  				if (prod.getTiene_grupo().equals(Constantes.STRING_TRUE)) 
				  				{
				  					int cont=0,maxgrupo= 0;
				  					
				  					for (ProductosBean p : lista_productos_total) {
				  						if(p.getTipoFamilia().equals("L") && !p.getGrupo().equals("0")) {
				  							cont++;
				  						}
				  						//System.out.println(p.getGrupoFamilia()+" "+p.getFamilia()+" "+p.getTipoFamilia());
				  					}
				  					if(cont <= 0) {
				  						maxgrupo = Constantes.INT_UNO;
				  					}else {
				  						maxgrupo = cont+1;
				  					}
				  					System.out.println("maxgrupo ===============>   "+maxgrupo);
									int cantidad_gr = Constantes.INT_CERO;
					  				for (ProductosBean productos : lista_productos_total) {
					  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
										if (maxgrupo < Integer.parseInt(productos.getGrupo())) {
											maxgrupo = Integer.parseInt(productos.getGrupo());
										}
									}
					  				for (ProductosBean productos : lista_productos_total) {
					  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
										if (maxgrupo == Integer.parseInt(productos.getGrupo())) {
											cantidad_gr += 1;
										}
									}
					  				
					  				if (cantidad_gr < 3) {
					  					if("G".equals(prod.getTipoFamilia())){
					  						prod.setGrupo(Constantes.STRING_CERO);
					  					}else{
					  						System.out.println("paso grupo lec + trio ===>"+ maxgrupo);
					  						prod.setGrupo(String.valueOf(maxgrupo));
					  					}
										
									}
					  				else
					  				{
					  					if("G".equals(prod.getTipoFamilia())){
					  						prod.setGrupo(Constantes.STRING_CERO);
					  					}else{
					  						prod.setGrupo(String.valueOf(maxgrupo + 1));
					  					}			  					
					  				}
					  				System.out.println();
				  				}
				  				
				  				prod.setIndice(listaProductos.size());
				  				listaProductos.add(listaProductos.size(),prod);
				  				
				  				
				  				
				  				//verifica ti tiene promocion
				  				if (null != formulario.getPromocion() && !Constantes.STRING_CERO.equals(formulario.getPromocion())) 
				  				{
				  					this.aplica_descuento(formulario);
				  				}
				  			}
				  			
							
							
							
							
							
				  			if (formulario.getEstado().equals(Constantes.STRING_PRODUCTO_ARCLI)) {
			  					formulario.setAddProducto(String.valueOf(prod.getIndice()));
							}
			  				
			  				formulario.setListaProductos(listaProductos);
			  				
			  				/**
							 * Si el producto es multioferta se carga en la listaProductosMultiOfertas para
					  		 * luego actualizar la listaMultioferta de la session
					  		 */
			  				
			  				ArrayList<ProductosBean> listaProductosMultiOfertas = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS);
							if("MUL".equals(prod.getFamilia())){					
								prod.setIndexMulti(this.obtenerIndexMultiOferta(listaProductosMultiOfertas));
								if (null == listaProductosMultiOfertas){	
			  		  				listaProductosMultiOfertas = new ArrayList<ProductosBean>();
			  		  				listaProductosMultiOfertas.add(prod);
			  		  			}else{
			  		  				listaProductosMultiOfertas.add(prod);
			  		  			}
			  		  			session.setAttribute(Constantes.STRING_LISTA_MULTIOFERTAS, listaProductosMultiOfertas);
			  		  			formulario.setEstado(Constantes.STRING_CARGA_MULTIOFERTAS);
					  			formulario.setCodigo_mult(prod.getCodigo());
					  			formulario.setIndex_multi(prod.getIndexMulti());
							}
						
							
						}
						else
						{
							formulario.setEstado(Constantes.STRING_VENTA);
						}
					}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_CRISTAL_INCOMPLETO);
					}
				}
				
				//comprueba si tiene precio especial
				if (formulario.getError().equals(Constantes.STRING_ERROR)) {
					Utils util = new Utils();
					if (util.verificaPrecioEspecial(prod, formulario.getFecha())) {
						session.setAttribute(Constantes.STRING_PRODUCTO, prod.getIndice());
						formulario.setEstado(Constantes.STRING_PRODUCTO_PRECIO_ESPECIAL);
					}
					
				}	
				
				
				//AGREGO GRUPO A LAS LENTILLAS 20170529
			
				System.out.println("FAMILIA ==>"+prod.getFamilia()+" =>"+prod.getGrupo()+" ==>"+prod.getTipoFamilia());
				if(prod.getTipoFamilia().equals("L")){
				
					if(PosUtilesFacade.validaLentillaGraduable(prod.getCodigo())){
						int maxgrupo  = 0;
						int maxtemp   = 0;
						int maxgrupolen = 0;
						for (ProductosBean productos : listaProductos) {
		  					log.info("VentaPedidoHelper:actualizaProductos entrando ciclo for");
		  					if (maxgrupo < Integer.parseInt(productos.getGrupo())) {
								maxgrupo = Integer.parseInt(productos.getGrupo());
							}
		  					if (productos.getTipoFamilia().equals("L")) {
		  						System.out.println("for L =>"+productos.getGrupo());
		  						if (Integer.parseInt(productos.getGrupo()) > 0) {
		  							maxgrupolen = Integer.parseInt(productos.getGrupo());
								}
							}
						}
						System.out.println("MAX GRUPOLEN 1 =>"+maxgrupolen);
						//if(maxgrupolen == 0){
						maxtemp = maxgrupo + 1;
									 
						/*}else{
							maxtemp = maxgrupolen;
						}*/
						System.out.println("PASO GRUPO LENTILLAS==>"+maxtemp+"  MAX GRUPOLEN 2=> "+maxtemp);
						
						prod.setGrupo(String.valueOf(maxtemp));
					}
				}
				
				System.out.println("LOCAL VP ====>"+local.substring(0, 1)	);

				if(local.substring(0, 1).equals("T") || local.substring(0, 1).equals("V")) {			
					//2017-11-23 DTO PROMOPAR
					this.aplicaDescuentoProductoEsp(prod);
				}
		return listaProductos;
	}

	private boolean validaCristalRecetaADDDesdeHasta(ProductosBean prod,
			GraduacionesBean graduacion) {
		System.out.println("PASO 15 VPH");
		
		boolean estado = false;
		
		log.info("VentaPedidoHelper:traeListaTiposPedidos inicio");
		estado = PosVentaPedidoFacade.validaCristalRecetaADDDesdeHasta(prod, graduacion);
		
		return estado;
	}
	public ArrayList<TipoPedidoBean> traeListaTiposPedidos() 
	{
		System.out.println("PASO 16 VPH");
		log.info("VentaPedidoHelper:traeListaTiposPedidos inicio");
		return PosVentaPedidoFacade.traeTiposPedido();
	}


	public void traeUltimaGraduacionCliente(VentaPedidoForm formulario) {
		System.out.println("PASO 17 VPH");
		log.info("VentaPedidoHelper:traeUltimaGraduacionCliente inicio");
		GraduacionesBean graduacionBean = new GraduacionesBean();
		try {
			graduacionBean = PosGraduacionesFacade.traeUltimaGraduacionCliente(formulario.getCliente().toString());
			formulario.setGraduacion(graduacionBean);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:traeUltimaGraduacionCliente error catch",e);
		}
	}


	public void agregaSuplementosProducto(VentaPedidoForm formulario,
			Session session) {
			System.out.println("PASO 18 VPH");
			log.info("VentaPedidoHelper:agregaSuplementosProducto inicio");
			ProductosBean prod = (ProductosBean)session.getAttribute(Constantes.STRING_PRODUCTO);
			session.setAttribute(Constantes.STRING_PRODUCTO, Constantes.STRING_BLANCO);
			ArrayList<SuplementopedidoBean> lista_suplementos = (ArrayList<SuplementopedidoBean>)session.getAttribute(Constantes.STRING_LISTA_SUPLEMENTOS);
			prod.setListaSuplementos(lista_suplementos);
			formulario.getListaProductos().set(prod.getIndice(), prod);
			
	}

	
	/*
	 * LMARIN 20141001
	 * Agrego la opcion de eliminar Multiples productos de la sesion
	 *   
	 */

	public ArrayList<ProductosBean> eliminarProductos(String addProducto,
			ArrayList<ProductosBean> listaProductos, VentaPedidoForm formulario) {
		
		System.out.println("PASO 19 VPH");
		int  n = addProducto.length();
		if(n == 1){
			listaProductos.remove(Integer.parseInt(addProducto));
		}else{
			try{	
				String prod [] = addProducto.split(",");
				System.out.println("POSICION FINAL =>"+prod.length);
				for(String p : prod){
					listaProductos.remove(Integer.parseInt(p));
				}				
			}catch(Exception e){
				System.out.println("Excepcion controlada eliminar productos===>"+e.getMessage());
			}
			
		}
		
		log.info("VentaPedidoHelper:eliminarProductos inicio");
				
		
		
		return listaProductos;
	}

	/*public ArrayList<ProductosBean> eliminarProductos(String addProducto,
			ArrayList<ProductosBean> listaProductos, VentaPedidoForm formulario) {
		
		System.out.println("PASO 19 VPH");
		
		log.info("VentaPedidoHelper:eliminarProductos inicio");
				
		listaProductos.remove(Integer.parseInt(addProducto));
		
		return listaProductos;
	}*/


	public void verSuplementosProducto(VentaPedidoForm formulario,
			Session session) {
		
		System.out.println("PASO 20 VPH");
		
		log.info("VentaPedidoHelper:verSuplementosProducto inicio");
		ProductosBean prod = formulario.getListaProductos().get(Integer.parseInt(formulario.getAddProducto()));
		prod.setIndice(Integer.parseInt(formulario.getAddProducto()));
		
		this.valida_estado_suplementos(formulario, session);
		
		formulario.setEstado(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO);
		session.setAttribute(Constantes.STRING_PRODUCTO, prod);
		
		
	}


	public void valida_estado_suplementos(VentaPedidoForm formulario, Session session) {
		
		
		System.out.println("PASO 21 VPH");
		
		session.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, Constantes.STRING_BLANCO);
		
		if (Constantes.STRING_ACTION_BLOQUEA.equals(formulario.getBloquea())) {
			session.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, Constantes.STRING_ACTION_BLOQUEA);
		}
		else
		{
			ArrayList<PagoBean> pagos = this.traePagos(formulario);
			if (null != pagos && pagos.size()>0) {
				session.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, Constantes.STRING_ACTION_BLOQUEA);
			}
		}
		
	}
	public void modificaCantidad(VentaPedidoForm formulario, int index,
			int cantidad) {
		
		System.out.println("PASO 22 VPH");

		log.info("VentaPedidoHelper:modificaCantidad inicio");
		
		if (this.CompruebaPagos(formulario)) {
			formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
		}
		else
		{
			
			
			ProductosBean producto = formulario.getListaProductos().get(index);
			
			System.out.println("PRODUCTO GET PRECIO =>"+producto.getPrecio()+"<==> CANTIDAD => "+cantidad);
			producto.setCantidad(cantidad);
			if (producto.getDescuento() == 0) {
				producto.setImporte(producto.getPrecio() * cantidad);
			}
			else
			{
				int precio = producto.getPrecio() * cantidad;
				double cant = producto.getDescuento();
				double diferencia = cant / 100;
				double valor = precio * diferencia;
				double saldo = precio - valor;
				int total_linea =  (int) Math.floor(saldo);
				producto.setImporte(total_linea);
			}
			
			
			formulario.getListaProductos().set(index, producto);
		}
		
		
	}


	public void eliminaDescuentoTarificaPedido(VentaPedidoForm formulario) {
		
		System.out.println("PASO 23 VPH");

		
		log.info("VentaPedidoHelper:tarifica_Pedido inicio");

		
		//int indice = Integer.parseInt(formulario.getAddProducto());
		double cantidad = 0;
		String supervisor = formulario.getDescuento_autoriza();
		//ProductosBean producto = formulario.getListaProductos().get(indice);
		if(null != formulario.getListaProductos()){
		for(ProductosBean producto : formulario.getListaProductos()){
		if (!(Constantes.STRING_MUL.equals(producto.getFamilia())) && (Constantes.STRING_BLANCO.equals(producto.getPrevtln()))) {
		
			producto.setDescuento(cantidad);
			
			/*if (!"".equals(supervisor)) {
				formulario.setSupervisor(supervisor);
			}*/
			int precio = producto.getPrecio() * producto.getCantidad();
			double cant = producto.getDescuento();
			/*double diferencia = cant / 100;
			double valor = Math.round(precio * diferencia);*/
			double valor = Math.round((producto.getPrecio() * producto.getCantidad()) * cant / 100);
			double saldo = precio - valor;
			int total_linea =  (int) Math.floor(saldo);
			producto.setImporte(total_linea);
			
			//formulario.getListaProductos().set(indice, producto);
		
		}
		}//fin for each
		}
		
	
	}
	
	public void tarifica_Pedido(VentaPedidoForm formulario){
		
		System.out.println("PASO 24 VPH");
			
		log.info("VentaPedidoHelper:tarifica_Pedido inicio");
		ArrayList<ProductosBean> lista_productos = formulario.getListaProductos();
		int dto_seg_arm= 0,ned =0,posi  = 0,posit = 0,val=0,cont =0;

		ArrayList <Integer> arrParm = new ArrayList<Integer>(); 
		
		String cdg = formulario.getCdg().equals("")?"0":formulario.getCdg();
		
		long total = Constantes.INT_CERO;
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) 
		{
			if (lista_productos.size() != Constantes.INT_CERO)
			{				
					
					for (ProductosBean prod : lista_productos) {
						log.info("VentaPedidoHelper:tarifica_Pedido entrando ciclo for");
						
						//DTO PROMOCION EMILIO ARMAZON 20140304	
						
						try {	
							dto_seg_arm  = PosUtilesFacade.valida_dto_seg_armazon(prod.getCodigo(),formulario.getCodigo_suc(),cdg);
							
							val += dto_seg_arm;
	
							if(dto_seg_arm != 6 && dto_seg_arm != 0){//COMPRUEBO SI LA PROMOCION ESTA ACTIVA 
								if(val > 0){
									arrParm.add(prod.getPrecio()+ned);
									Collections.sort(arrParm);
									posi = arrParm.get(0);
									posit = posi%10;
									cont++;
									
									if(cont >= 2 && val >= 100){
										lista_productos.get(posit).setDescuento(100);
										lista_productos.get(posit).setImporte(0);
										val = 0;	
										
									}
									
								}
							}
						} catch (Exception e) {
							
							System.out.println(e.getMessage());
						}
						
						System.out.println(ned+" <= importe => "+prod.getImporte());
						
						
						total += prod.getImporte();
						
						ned++;
				}
				
				formulario.setSubTotal(total);
				if (total == Constantes.INT_CERO)
				{
					formulario.setDtcoPorcentaje(Constantes.INT_CERO);
					formulario.setDescuento(Constantes.INT_CERO);
					formulario.setTotal(Constantes.INT_CERO);
					formulario.setTotalPendiante(Constantes.INT_CERO);
					formulario.setAnticipo(Constantes.INT_CERO);
					formulario.setSubTotal(Constantes.INT_CERO);
				}
				else
				{
					
					formulario.setDescuento(0);
					
					formulario.setSubTotal(total);
					
					double cant = formulario.getDtcoPorcentaje();
					double diferencia = cant/100;
					double subtotal = formulario.getSubTotal();
					double valor = subtotal * diferencia ;
					
					System.out.println("Paso por tarifica ==>"+formulario.getSubTotal());
					
					formulario.setTotal(formulario.getSubTotal() - formulario.getDescuento());
					formulario.setTotalPendiante(formulario.getTotal());
					formulario.setAnticipo((formulario.getTotal() * formulario.getPorcentaje_anticipo()) / 100 );
				}
				
				if (formulario.getTotal() == 0) {
					formulario.setPedido_costo_cero(Constantes.STRING_TRUE);
				}
				else
				{
					formulario.setPedido_costo_cero(Constantes.STRING_FALSE);
				}
			}
			else
			{
				formulario.setSubTotal(Constantes.INT_CERO);
				formulario.setDescuento(Constantes.INT_CERO);
				formulario.setDtcoPorcentaje(Constantes.INT_CERO);
				formulario.setTotal(Constantes.INT_CERO);
				formulario.setAnticipo(Constantes.INT_CERO);
				formulario.setPedido_costo_cero(Constantes.STRING_FALSE);
			}
		}
		else
		{
			formulario.setSubTotal(Constantes.INT_CERO);
			formulario.setDescuento(Constantes.INT_CERO);
			formulario.setDtcoPorcentaje(Constantes.INT_CERO);
			formulario.setTotal(Constantes.INT_CERO);
			formulario.setAnticipo(Constantes.INT_CERO);
			formulario.setPedido_costo_cero(Constantes.STRING_FALSE);
		}
		
	}
	
	
	
	public void agregaDescripcion(VentaPedidoForm formulario, int index) {
		System.out.println("PASO 25 VPH");

		log.info("VentaPedidoHelper:agregaDescripcion inicio");
		ProductosBean prod = formulario.getListaProductos().get(index);
		prod.setDescripcion(formulario.getDescripcion());
		//prod.setDescripcion_manual(Constantes.STRING_FALSE);
		formulario.getListaProductos().set(index, prod);
		
	}


	public void actualizaProductosPorConvenio(VentaPedidoForm formulario,String local) {
		System.out.println("PASO 26 VPH");

		log.info("VentaPedidoHelper:actualizaProductosPorConvenio inicio");
		int valor = Constantes.INT_CERO;
		this.tarifica_Pedido(formulario);
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) {
		
			for (int i = Constantes.INT_CERO; i < formulario.getListaProductos().size(); i++) {
				log.info("VentaPedidoHelper:actualizaProductosPorConvenio entrando ciclo for");
				ProductosBean prod = formulario.getListaProductos().get(i);
				if (Constantes.STRING_BLANCO.equals(prod.getPrevtln())) {
					System.out.println("actualizaProductosPorConvenio");
					this.aplicaDescuentoConvenio(prod, formulario.getConvenio(), formulario.getForma_pago(),formulario.getListaProductos(),local);
				}
				formulario.getListaProductos().set(i, prod);
			}
		}
	}
	
	public void eliminaDescuentos(VentaPedidoForm formulario)
	{
		System.out.println("PASO 27 VPH");

		for (int i = 0; i < formulario.getListaProductos().size(); i++) {
			
			ProductosBean prod = formulario.getListaProductos().get(i);
			if (!(Constantes.STRING_MUL.equals(prod.getFamilia())) && (Constantes.STRING_BLANCO.equals(prod.getPrevtln()))) {
				prod.setDescuento(Constantes.INT_CERO);
				prod.setImporte(prod.getPrecio() * prod.getCantidad());
				prod.setTotal(prod.getPrecio() * prod.getCantidad());
			}
			formulario.getListaProductos().set(i, prod);
		}
	}


	public boolean verificaConvenioCliente(VentaPedidoForm formulario) {
		
		System.out.println("PASO 28 VPH");
		
		log.info("VentaPedidoHelper:verificaConvenioCliente inicio");
		
			//if (formulario.getPromocion().equals(Constantes.STRING_CERO)) {
				return this.verificaEstadoConvenioCliente(formulario.getConvenio(), formulario.getCliente());
			/*}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
				formulario.setConvenio(Constantes.STRING_BLANCO);
				formulario.setConvenio_det(Constantes.STRING_BLANCO);
				return false;
			}*/
	}


	public void ingresaGrupos(VentaPedidoForm formulario) {
		System.out.println("PASO 29 VPH");

		log.info("VentaPedidoHelper:ingresaGrupos inicio");
		int grupo_max = Constantes.INT_CERO;
		
		for (int i = 0; i < formulario.getGrupo().length; i++)
		{
			log.info("VentaPedidoHelper:ingresaGrupos entrando ciclo for");
			String [] indice = formulario.getGrupo();
			
			ProductosBean prod = formulario.getListaProductos().get(i);
			//if(prod.getTiene_grupo().equals(Constantes.STRING_TRUE))
			//{
				if (grupo_max < Integer.parseInt(indice[i].toString())) {
					grupo_max = Integer.parseInt(indice[i].toString());
				}
				prod.setGrupo(indice[i].toString());
				formulario.getListaProductos().set(i, prod);
			//}
		}
		
		formulario.setGrupo_max(grupo_max);
	}


	public void agrupa_valida_trios(VentaPedidoForm formulario, Session session) {
		
		System.out.println("PASO 30 VPH");

		
		log.info("VentaPedidoHelper:agrupa_valida_trios inicio");
		ArrayList<ProductosBean> lista_total_productos = new ArrayList<ProductosBean>();
			lista_total_productos.addAll(formulario.getListaProductos()); 
		
		//agrega los productos multioferta
		ArrayList<ProductosBean> listaProdMultiOferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
		if (null != listaProdMultiOferta && listaProdMultiOferta.size()>0) {
			for (ProductosBean productosBean : listaProdMultiOferta) {
				lista_total_productos.add(productosBean);
			}
		}
		
		//calcula el grupo mas alto
		formulario.setGrupo_max(Constantes.INT_CERO);
		for (ProductosBean productosBean : lista_total_productos) {
			if (Integer.parseInt(productosBean.getGrupo()) > formulario.getGrupo_max()) {
				formulario.setGrupo_max(Integer.parseInt(productosBean.getGrupo()));
			}
		}
		
		
		if (!this.valida_grupos(lista_total_productos, formulario.getGrupo_max(), formulario)) {
			formulario.setEstado(Constantes.STRING_VENTA_TECNICAMENTE_NO_FACTIBLE);
		}
		else
		{
			formulario.setEstado(Constantes.STRING_GENERA_COBRO);
		}
	}


	public String traeFechaEntrega(ArrayList<ProductosBean> listaProductos,
			String local, String fecha, String fecha_ingresada, VentaPedidoForm formulario, Session session) {
		
		System.out.println("PASO 31 VPH FECHA DE ENTREGA");

		log.info("VentaPedidoHelper:traeFechaEntrega inicio");
		
		ArrayList<ProductosBean> lista_total_prod = new ArrayList<ProductosBean>();
		lista_total_prod.addAll(listaProductos);
		
		ArrayList<ProductosBean> listaProdMultiOferta = null;
		
		try {
			listaProdMultiOferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
			if (null != listaProdMultiOferta && listaProdMultiOferta.size()>0) {
				for (ProductosBean productosBean : listaProdMultiOferta) {
					lista_total_prod.add(productosBean);
				}
			}
		} catch (Exception e) {
			listaProdMultiOferta = null;
		}

			Date fecha_formulario = null;
			if (!Constantes.STRING_BLANCO.equals(fecha_ingresada)) {
				fecha_formulario = this.formatoFechaCh(fecha_ingresada);
			}
			else
			{
				fecha_formulario = this.traeFecha();
			}
			
		
			Date fecha_entrega = new Date();
			Date fecha_ini = this.traeFecha();
			String tipo = Constantes.STRING_BLANCO;
			
		for (ProductosBean productosBean : lista_total_prod) {
			log.info("VentaPedidoHelper:traeFechaEntrega entrando ciclo for");
			tipo = Constantes.STRING_BLANCO;
			String letra = Constantes.STRING_BLANCO;
			/*LMARIN 20130912*/	
			fecha_entrega = PosVentaPedidoFacade.traeFechaEntrega(fecha, local, productosBean.getFamilia(),
					productosBean.getSubFamilia(), productosBean.getGrupoFamilia(),
					productosBean.getTipoFamilia(),productosBean.getEsfera(),productosBean.getCilindro());
			
			if(null == fecha_entrega)
			{
				fecha_entrega = fecha_ini;
			}

			long dias = fecha_entrega.getTime() - fecha_ini.getTime();
			if (dias > Constantes.INT_CERO) 
			{
				fecha_ini = fecha_entrega;
			}
		
			
		}
		
		//fecha_entrega = PosVentaPedidoFacade.traeFechaEntregaExepcionFeriados(fecha_ini, formulario.getFecha());
		fecha_entrega = fecha_ini;
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
		
		long dias = (fecha_formulario.getTime() - fecha_entrega.getTime() )/MILLSECS_PER_DAY;;
		if (dias >= Constantes.INT_CERO) 
		{
			if (dias > 365) {
				formulario.setError(Constantes.STRING_ERROR_FECHA_LIMITE);
				formulario.setEstado(Constantes.STRING_ESTADO);
			}
			else
			{
				fecha_entrega = fecha_formulario;
			}
		}
		
		return this.formatoFecha(fecha_entrega);
		
	}


	public void ingresaPedido(VentaPedidoForm formulario, String local) {
		
		System.out.println("PASO 32 VPH");
		
		log.info("VentaPedidoHelper:ingresaPedido inicio");
		//llena los campos del bean
		VentaPedidoBean pedido = new VentaPedidoBean();
		pedido.setSerie(formulario.getCodigo_suc());
		if(formulario.getCodigo().equals(Constantes.STRING_BLANCO))
		{
			pedido.setNumero(0);
			pedido.setCdg(formulario.getCodigo_suc() + Constantes.STRING_SLASH);
		}
		else
		{
			pedido.setNumero(Integer.parseInt(formulario.getCodigo().toString()));
			pedido.setCdg(formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo());
		}
		if (formulario.getCliente().equals(Constantes.STRING_BLANCO)) {
			formulario.setCliente(Constantes.STRING_CERO);
		}
		
		if(!formulario.getEncargo_garantia().equals("")){
			pedido.setPedvtant(formulario.getEncargo_garantia());
		}else{
			pedido.setPedvtant(formulario.getEncargo_padre());
		}
		
		if(!formulario.getEncargo_seguro().equals("")){
			pedido.setPedvtant(formulario.getEncargo_seguro());
		}else{
			pedido.setPedvtant(formulario.getEncargo_seguro());
		}
		if(!formulario.getEncargo_padre().equals("")){
			pedido.setPedvtant(formulario.getEncargo_padre());
		}
		//encargo_padre
		System.out.println("formulario.getEncargo_padre() ==>"+formulario.getEncargo_padre()+"<===>"+pedido.getPedvtant());
		
		pedido.setId_cliente(formulario.getCliente());
		pedido.setId_agente(formulario.getAgente());
		pedido.setDivisa(formulario.getDivisa());
		pedido.setIdioma(formulario.getIdioma());
		pedido.setDto(formulario.getDtcoPorcentaje());
		if ("0".equals(formulario.getSupervisor())) {
			pedido.setSupervisor(null);
		}
		else
		{
			pedido.setSupervisor(formulario.getSupervisor());
		}
		pedido.setFecha(formulario.getFecha());
		pedido.setFecha_entrega(formulario.getFecha_entrega());
		pedido.setForma_pago(formulario.getForma_pago());
		pedido.setCambio(formulario.getCambio());
		pedido.setNumero_sobre(formulario.getSobre());
		pedido.setAnticipo(formulario.getAnticipo());
		pedido.setNotas(formulario.getNota());
		pedido.setCerrado(Constantes.STRING_TIPO_ALB_NO);
		pedido.setRetenido(Constantes.STRING_TIPO_ALB_NO);
		pedido.setTipo_ped(Constantes.STRING_TIPO_ALB_NO);
		pedido.setEnuso(Constantes.STRING_TIPO_ALB_NO);
		pedido.setTipo_ped_2(formulario.getTipo_pedido());
		pedido.setFinalizado(Constantes.STRING_TIPO_ALB_NO);
		pedido.setHora(formulario.getHora());
		
		pedido.setAnticipo_total(formulario.getAnticipo_total());
		pedido.setAnticipo_def(formulario.getAnticipo());
		pedido.setAnticipo_total_def(formulario.getAnticipo());
		pedido.setConvenio(formulario.getConvenio());
		pedido.setAnulado(Constantes.STRING_TIPO_ALB_NO);
		pedido.setDescargado(Constantes.STRING_TIPO_ALB_NO);
		pedido.setId_promocion(formulario.getPromocion());
		pedido.setCliente_dto(formulario.getCliente_dto());
		pedido.setVenta_seguro(formulario.getVenta_seguro());
		
		PosVentaPedidoFacade.insertaPedido(pedido, local);
		
		
		formulario.setCodigo(formato_Numero_Ticket(pedido.getNumero()));
		formulario.setCdg(formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo());
		//System.out.println("cupon ===>"+formulario.getNumero_cupon());
		
		
		//Inserto Historial
		try {
			
			PosUtilesFacade.IngresaCupon(formulario.getNumero_cupon(), local,formulario.getAgente(),formulario.getCliente(), formulario.getCdg());

			if(!formulario.getEncargo_padre().equals("")){
				PosVentaPedidoFacade.inserta_historial_encargo(formulario.getTipo_pedido(),formulario.getEncargo_padre(),formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo()+Constantes.STRING_SLASH+"1", formulario.getFecha(),formulario.getNif());
			}
		} catch (Exception e) {
			System.out.println("Error controlado inserta_historial_encargo => "+e.getMessage());
		}
				
	}


	public boolean ingresaPedidoLinea(VentaPedidoForm formulario, String local) {
		
		System.out.println("PASO 33 VPH");

		
		log.info("VentaPedidoHelper:ingresaPedidoLinea inicio");
		boolean hay_multiofertas = false;
		String mensaje = Constantes.STRING_BLANCO;
		String codigo = formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo();
		int x = 1;
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
			
			try {
				for (ProductosBean productosBean : formulario.getListaProductos()) {
					log.info("VentaPedidoHelper:ingresaPedidoLinea entrando ciclo for");
					
					
					System.out.println("codigo ==>"+productosBean.getCodigo()+"<==> descripcion =>"+productosBean.getDescripcion()+" <==> Familia ==>"+productosBean.getFamilia());
					
					if (Constantes.STRING_MUL.equals(productosBean.getFamilia())) 
					{
						hay_multiofertas = true;
						//productosBean.setCdg_correlativo_multioferta(PosUtilesFacade.traeNumeroMultioferta() + 1);
						mensaje = PosVentaPedidoFacade.insertaMultiofertaCB(productosBean, codigo, x, formulario.getFecha(), Integer.parseInt(formulario.getCodigo()), local);
						log.warn("insertaMultiofertaCabecera MON/" + productosBean.getCdg_correlativo_multioferta() + " " + codigo + "/ PEDIDO");
						if (mensaje.equals("ERROR")) {
							formulario.setError("Ocurrio un problema al grabar la multioferta, intentelo nuevamente.");
							formulario.setEstado(Constantes.STRING_VENTA);
						}
						PosVentaPedidoFacade.insertaDetalle(productosBean, x, codigo, local);
					}
					else
					{
						int identidad = PosVentaPedidoFacade.insertaDetalle(productosBean, x, codigo, local);
						 if(null != productosBean.getListaSuplementos() && productosBean.getListaSuplementos().size() > 0)
						 {
							for (SuplementopedidoBean suple : productosBean.getListaSuplementos()) {
								 PosVentaPedidoFacade.insertaSuplemento(suple, productosBean, x, identidad);
							}
							
						 }
						 if (productosBean.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI)) {
							PosVentaPedidoFacade.insertaAdicionalesArcli(identidad, productosBean);
						}
					}
					x++;
				}
				
			} catch (Exception e) {
				log.error("VentaPedidoHelper:ingresaPedidoLinea error catch",e);
			}
		}
		return hay_multiofertas;
		
	}


	public void ingresaDetalleMultiofertas(
			ArrayList<ProductosBean> listaProductos, String local,
			VentaPedidoForm formulario,
			ArrayList<ProductosBean> listaProdMultiOferta) {
		System.out.println("PASO 34 VPH");

		log.info("VentaPedidoHelper:ingresaDetalleMultiofertas inicio");
		int x = 1;
		String mensaje = Constantes.STRING_BLANCO;
		try {
			for (ProductosBean productosBean : listaProductos) {
				log.info("VentaPedidoHelper:ingresaDetalleMultiofertas entrando ciclo for");
				System.out.println("Paso insertar multiofertas VPH ==> "+productosBean.getCdg_correlativo_multioferta());
				if (productosBean.getCdg_correlativo_multioferta() != Constantes.INT_CERO)
				{
					x = 1;
					for (ProductosBean productosMulti : listaProdMultiOferta) {
						log.info("VentaPedidoHelper:ingresaDetalleMultiofertas entrando ciclo for");
						if (productosMulti.getCodigoMultioferta().equals(productosBean.getCodigo())
								&& productosBean.getIndexMulti() == productosMulti.getIndexRelMulti())
						{
							mensaje = PosVentaPedidoFacade.insertaMultiofertaDetalle(productosBean.getCdg_correlativo_multioferta(), productosMulti, x, formulario.getFecha(), local, formulario.getCdg() );
							System.out.println("insertaMultiofertaDetalle MON/" + productosBean.getCdg_correlativo_multioferta() + " " + formulario.getCdg() + "/ PEDIDO");
							//log.warn("insertaMultiofertaDetalle MON/" + productosBean.getCdg_correlativo_multioferta() + " " + formulario.getCdg() + "/ PEDIDO");
							if (mensaje.equals("ERROR")) {
								formulario.setError("Ocurrio un problema al grabar la multioferta, intentelo nuevamente.");
								formulario.setEstado(Constantes.STRING_VENTA);
							}
							x++;
							if (null != productosMulti.getListaSuplementos() && productosMulti.getListaSuplementos().size()>0) {
								for (SuplementopedidoBean suple : productosMulti.getListaSuplementos()) {
									PosVentaPedidoFacade.insertaMultiofertaDetalleSuplemento(productosMulti.getIdent(), suple);
								}
							}
						}
					}
				}
				
			}
		} catch (Exception e) {
			log.error("VentaPedidoHelper:ingresaDetalleMultiofertas error catch",e);
		}
		
	}


	public ArrayList<ProductosBean> traeProductosGratuitos(ArrayList<ProductosBean> lista,
			String nombre_local, String local) {
		System.out.println("PASO 35 VPH");

		log.info("VentaPedidoHelper:traeProductosGratuitos inicio");
		return this.traeLosProductosGratuitos(lista, nombre_local, local, Constantes.STRING_PEDIDO);
		
	}



	public ArrayList<PedidosPendientesBean> traePedidosPendientes(String codigoCliente,String sucursal) {
		
		System.out.println("PASO 36 VPH");

		log.info("VentaPedidoHelper:traePedidosPendientes inicio");
		ArrayList<PedidosPendientesBean> listaPedidos=PosVentaPedidoFacade.traePedidosPendientes(codigoCliente,sucursal, null, null, null, null);
		return listaPedidos;
	}
	///ACA ESTOY PARADO
	public void ingresaPago(ArrayList<PagoBean> listaPagos,
			Session session, VentaPedidoForm formulario, String local) {
		System.out.println("PASO 38 VPH");

		log.info("VentaPedidoHelper:ingresaPago inicio");
    	String devolucion = Constantes.STRING_N;
    	String tipo_doc = session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString();    	
    	int valor = 0;
    	try {
    		for (PagoBean pago : listaPagos) {
    			log.info("VentaPedidoHelper:ingresaPago entrando ciclo for");
    			valor = pago.getCantidad();
    			PosVentaPedidoFacade.insertaPago(formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo(),
													pago.getForma_pago(),
													pago.getCantidad(),
													pago.getFecha(),
													formulario.getDivisa(), 
													formulario.getCambio(), 
													local, 
													pago.getCantidad(),
													devolucion,
													session.getAttribute(Constantes.STRING_USUARIO).toString(),
													pago.getNumero_bono(),
													pago.getDescuento(),pago.getRut_vs());
			}
    		/*long anticipo_p = formulario.getAnticipo() - valor;
    		
    		System.out.println("long anticipo_p = "+anticipo_p+" = "+formulario.getAnticipo()+" - "+valor);
    		
    		if (anticipo_p > 0) {
    			formulario.setTotalPendiante(formulario.getAnticipo() - valor);
			}*/
			
		} catch (Exception e) {
			log.error("VentaPedidoHelper:ingresaPago error catch",e);
		}
		
	}
	public String ingresaDocumento(String ticket, int documento, String tipo_doc,
			ArrayList<PagoBean> listaPagos, String fecha, String local, VentaPedidoForm formulario,String numero_isapre,String dto) {
		
		System.out.println("PASO 39 VPH");

		
		log.info("VentaPedidoHelper:ingresaPago inicio");
		long total = 0;
		String res ="";
		ArrayList<PagoBean> pago_anterior = new ArrayList<PagoBean>();
		pago_anterior = this.traePagos(formulario);
		int p1=1;
		for (PagoBean pagoBean : listaPagos) {
			log.info("VentaPedidoHelper:ingresaPago entrando ciclo for");
			boolean encontrado = false;
			for (PagoBean pago_ant : pago_anterior) {
				if (pagoBean.getFecha().equals(pago_ant.getFecha()) && pagoBean.getForma_pago().equals(pago_ant.getForma_pago())
					&& pagoBean.getCantidad() == pago_ant.getCantidad()){
					encontrado = true;
				}
			}
			if (!encontrado) {
				total = total + pagoBean.getCantidad();
				fecha=pagoBean.getFecha();
			}
		}
		try {
			p1++;
			res = PosVentaPedidoFacade.insertaDocumento(ticket, documento, tipo_doc, total, fecha, local,numero_isapre,dto);
			System.out.println("Paso n => "+p1+"veces ");
		} catch (Exception e) {
			log.error("VentaPedidoHelper:ingresaPago error catch",e);
		}
		return res;
	}
	
	public ArrayList<PagoBean> traePagos(VentaPedidoForm formulario) {
		System.out.println("PASO 40 VPH");

		log.info("ingresaDocumento:traePagos inicio");
		ArrayList<PagoBean> lista_Pagos = new ArrayList<PagoBean>();
		try {
			//ORIGINAL SE DEBE DESCOMENTAR 08/04/12
			lista_Pagos =  PosSeleccionPagoFacade.traePagos(formulario.getCodigo_suc() + "/" + formulario.getCodigo(), Constantes.STRING_PEDIDO);
			//lista_Pagos =  PosSeleccionPagoFacade.traePagos("09/0002442", Constantes.STRING_PEDIDO);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:traePagos error catch",e);
		}
		return lista_Pagos;
	}
	
	public void traePedidosSeleccionado(String codigoPedido, VentaPedidoForm formulario, Session session) {
	
		System.out.println("PASO 41 VPH");

	
		log.info("VentaPedidoHelper:traePedidosSeleccionado inicio");
		
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		VentaPedidoBean  ventaPedido = new VentaPedidoBean();
		ventaPedido = PosVentaPedidoFacade.traeGenericos(local);
		formulario.setFenix(ventaPedido.getFenix());
		
		PedidosPendientesBean pedidosSeleccionado=PosVentaPedidoFacade.traePedidosSeleccionado(codigoPedido);
		
		String []cdgS = pedidosSeleccionado.getCodigoP().split(Constantes.STRING_SLASH);
		formulario.setCodigo_suc(cdgS[0]);
		formulario.setCodigo(cdgS[1]);
		
		ClienteBean cliente = this.traeCliente(null, pedidosSeleccionado.getCliente());
		formulario.setNombre_cliente(cliente.getNombre() + " " + cliente.getApellido());
		formulario.setNif(cliente.getNif());
		formulario.setDvnif(cliente.getDvnif());
		formulario.setCliente(cliente.getCodigo());
		this.traeUltimaGraduacionCliente(formulario);
		this.traeUltimaGraduacionContactologiaCliente(formulario);
		formulario.setCerrado(pedidosSeleccionado.getCerrado());
		
		formulario.setForma_pago(pedidosSeleccionado.getForma_pagoP());
		formulario.setLocal(pedidosSeleccionado.getLocal());
		formulario.setSobre(pedidosSeleccionado.getSobreP());
		formulario.setAgente(pedidosSeleccionado.getAgenteP());
		formulario.setDivisa(pedidosSeleccionado.getDivisaP());
		formulario.setIdioma(pedidosSeleccionado.getIdiomaP());
		if (null != pedidosSeleccionado.getPedvtant() || Constantes.STRING_BLANCO.equals(pedidosSeleccionado.getPedvtant())) {
			formulario.setEncargo_garantia(pedidosSeleccionado.getPedvtant());
		}
		if (null == pedidosSeleccionado.getConvenioP()) {
			formulario.setConvenio(Constantes.STRING_BLANCO);
			formulario.setConvenio_det(Constantes.STRING_BLANCO);
			formulario.setConvenio_det(Constantes.STRING_BLANCO);
		}
		else
		{
			formulario.setConvenio(pedidosSeleccionado.getConvenioP());
			formulario.setConvenio_det(pedidosSeleccionado.getConvenio_det());
			formulario.setIsapre(pedidosSeleccionado.getIsapre());
		}
		session.setAttribute(Constantes.STRING_CONVENIO,formulario.getConvenio());
		formulario.setConvenio(pedidosSeleccionado.getConvenioP());
		formulario.setPromocion(pedidosSeleccionado.getPromocionP());
		formulario.setTipo_pedido(pedidosSeleccionado.getTipo_pedidoP());
		if(Constantes.STRING_BLANCO.equals(pedidosSeleccionado.getFechaP())||null==pedidosSeleccionado.getFechaP()){
			formulario.setFecha(Constantes.STRING_BLANCO);
		}else{
			formulario.setFecha(this.formatoFechaString(pedidosSeleccionado.getFechaP()));
		}
		formulario.setHora(pedidosSeleccionado.getHoraP());
		formulario.setCambio(Integer.parseInt(pedidosSeleccionado.getCambioP()));
		formulario.setListaProductos(pedidosSeleccionado.getListaProduc());
		formulario.setDesde_presupuesto(Constantes.STRING_FALSE);
		formulario.setNota(pedidosSeleccionado.getNota());
		String[] grupo = new String[formulario.getListaProductos().size()];
		
		int x = 0;
		for (ProductosBean prod : formulario.getListaProductos()) {
			int precio = prod.getPrecio() * prod.getCantidad();
			double cant = prod.getDescuento();
			/*double diferencia = cant / 100;
			double valor = Math.round(precio * diferencia);*/
			double valor = Math.round((prod.getPrecio() * prod.getCantidad()) * cant / 100);
			double saldo = precio - valor;
			int total_linea =  (int) Math.floor(saldo);
			prod.setImporte(total_linea);
			
			
			grupo[x] = prod.getGrupo();
			x++;
			
			if (null != prod.getPrevtln() && !(Constantes.STRING_BLANCO.equals(prod.getPrevtln()))) {
				formulario.setDesde_presupuesto(Constantes.STRING_TRUE);
			}
			if (Constantes.STRING_C.equals(prod.getTipoFamilia()) || Constantes.STRING_M.equals(prod.getTipoFamilia()) || Constantes.STRING_G.equals(prod.getTipoFamilia())) {
				prod.setTiene_grupo(Constantes.STRING_TRUE);
			}
				
			
			if (prod.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI) || prod.getCod_barra().equals(Constantes.STRING_ACTION_CCLI)) {
				
				prod.setDescripcion_manual(Constantes.STRING_FALSE);
				
				 //HABILITA DESCRIPCION MANUAL
				
				prod.setDescripcion_manual(Constantes.STRING_TRUE);    
				//formulario.setEstado(Constantes.STRING_PRODUCTO_CLIENTE);
			}
			else
			{
				prod.setDescripcion_manual(Constantes.STRING_FALSE);
			}
			
			//CONSULTA SOBRE ARTICULOS ARCLI TRAE ADICIONALES
			if (prod.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI)) 
			{
				PosVentaPedidoFacade.traeAdicionalArcli(prod);
			}
		}
		
		formulario.setFecha_entrega(pedidosSeleccionado.getFechasEntragas());
		formulario.setDtcoPorcentaje(pedidosSeleccionado.getDescuento());
		formulario.setGrupo(grupo);
		formulario.setIsapre(pedidosSeleccionado.getIsapre());
		
		ArrayList<ProductosBean> listaMultiofertaln = new ArrayList<ProductosBean>();
		
		ArrayList<ProductosBean> listaMultioferta = new ArrayList<ProductosBean>();
		
		//DESCOMENTAR PARA TRAER MULTIOFERTAS
		int i = 0; 
			for (ProductosBean prod : formulario.getListaProductos()) {
			log.info("VentaPedidoHelper:traePedidosSeleccionado entrando ciclo for");
			if (Constantes.STRING_MUL.equals(prod.getFamilia())) {				
				prod.setIndexMulti(this.obtenerIndexMultiOferta(listaMultioferta));
				listaMultioferta.add(prod);//carga la multioferta en la lista para luego ser seteada en la session de multiofertas
				PosVentaPedidoFacade.traeMultiofertacb(prod);
				PosVentaPedidoFacade.traeMultiofertaLn(prod, listaMultiofertaln);
				
				for (ProductosBean productoLn : listaMultiofertaln) {
					productoLn.setListaSuplementos(PosVentaPedidoFacade.traeTratamientosPedidoMultiofertas(productoLn.getIdent()));
					if (productoLn.getTipoFamilia().equals(Constantes.STRING_C) )
					{
						productoLn.setTiene_suple(Constantes.STRING_TRUE);
					}
					
				}
				
				//if(null != listaMultiofertaln){					
					//for(ProductosBean multiuProd: listaMultiofertaln){									
				listaMultiofertaln = this.obtenerIndexProductoMultiOfertaCargaPedido(listaMultiofertaln, prod.getCodigo(), prod.getIndexMulti());
					//}					
				//}			
				
			}
			else
			{
				prod.setListaSuplementos(PosVentaPedidoFacade.traeTratamientosPedido(prod.getIdent()));
				if (prod.getTipoFamilia().equals(Constantes.STRING_C) )
				{
					prod.setTiene_suple(Constantes.STRING_TRUE);
				}	
			}
			i++;
		}
		System.out.println("Compruebo si viene vacia el valor a setear en la session de la STRING_LISTA_MULTIOFERTAS ==> "+listaMultioferta.isEmpty()+"\n");
		System.out.println("Compruebo si viene vacia el valor a setear en la session de la STRING_LISTA_PRODUCTOS_MULTIOFERTAS ==> "+listaMultiofertaln.isEmpty()+"\n");

		session.setAttribute(Constantes.STRING_LISTA_MULTIOFERTAS, listaMultioferta);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS, listaMultiofertaln);
		
	
	}

	public boolean eliminarPedido(String codigoPedido) {
		
		System.out.println("PASO 42 VPH");

		log.info("VentaPedidoHelper:eliminarPedido inicio");
		boolean pedidosDelete =false;
		try {
			pedidosDelete =  PosVentaPedidoFacade.eliminarPedido(codigoPedido);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:eliminarPedido error catch",e);
		}
		return pedidosDelete;
	}
	public boolean entregaPedido(String codigoPedido, String sucursal,VentaPedidoForm form) {
		System.out.println("PASO 43 VPH");

		log.info("VentaPedidoHelper:entregaPedido inicio");
		boolean pedidosDelete =false;
		try {
			ArrayList<ProductosBean> listaMultiofertaln = new ArrayList<ProductosBean>();
			//PedidosPendientesBean pedidosSeleccionado=PosVentaPedidoFacade.traePedidosSeleccionado(codigoPedido);
			boolean liberado=true; 
			boolean liberadoMulti=true;
			String error="";
			
			try{
				if(null != form){
					if(null != form.getListaProductos()){						
						for(ProductosBean producto: form.getListaProductos()){
							
							if("N".equals(form.getCerrado())){
								
								if("MUL".equals(producto.getFamilia())){
									PosVentaPedidoFacade.traeMultiofertaLn(producto, listaMultiofertaln);
									if(null != listaMultiofertaln){										
										for(ProductosBean productoMulti:listaMultiofertaln){
											liberadoMulti = estaLiberado(productoMulti);
											if(!liberadoMulti){
												error="LIBERADO";
												break;
											}
											else
											{
												if (!estadoEntrega(productoMulti)) {
													
													System.out.println("Paso por multioferta error");
													liberadoMulti = false;
													error="RECEPCIONADO";
													break;
												}
											}
										}									
									}
								}else{
									liberado = estaLiberado(producto);
									if(!liberado){
										error="LIBERADO";
										break;
									}
									else
									{
										if (!estadoEntrega(producto)) {
											liberado = false;
											error="RECEPCIONADO";
											break;
										}
									}
								}
								
								
							}else{
								liberado=false;
								error = "ENTREGADO";
								break;
							}
						}	
					}
				}				
			}catch(Exception ex){
				log.error("VentaPedidoHelper:entregaPedido error catch",ex);
				liberado=false;
				error = "ERROR";
			}
			
			if(liberado && liberadoMulti){
				pedidosDelete =  PosVentaPedidoFacade.pedidoEntrega(codigoPedido, sucursal, form);
			}else{
				if("ENTREGADO".equals(error)){	
					form.setCodigo_confirmacion("6");
					form.setMsnPedidoEntrega("PROBLEMAS EL ENCARGO "+codigoPedido+" ESTA ENTREGADO");
				}else if("ERROR".equals(error)){
					form.setMsnPedidoEntrega("PROBLEMAS EN LA VALIDACION DE LOS PRODUCTOS");
					form.setCodigo_confirmacion("-1");
				}else if("LIBERADO".equals(error)){
					form.setMsnPedidoEntrega("PROBLEMAS EL ENCARGO "+codigoPedido+" NO ESTA LIBERADO");
					form.setCodigo_confirmacion("2");
				}else if("RECEPCIONADO".equals(error)){
					form.setMsnPedidoEntrega("PROBLEMAS EL ENCARGO "+codigoPedido+" DEBE SER RECEPCIONADO POR MIM");
					form.setCodigo_confirmacion("2");	
				}
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoHelper:entregaPedido error catch",e);
		}
		return pedidosDelete;
	}
		
	public boolean CompruebaPagos(VentaPedidoForm formulario) {
		System.out.println("PASO 44 VPH");
		
		try {
			log.info("VentaPedidoHelper:CompruebaPagos inicio");
			ArrayList<PagoBean> lista_pagos = this.traePagos(formulario);
						
			if (null != lista_pagos && lista_pagos.size() > 0) {
				long total = Constantes.INT_CERO;
				long total_pedido = (int)Math.floor(formulario.getTotal());
				double descuento = Constantes.INT_CERO;
				
				for (PagoBean pagoBean : lista_pagos) {
					log.info("VentaPedidoHelper:CompruebaPagos entrando ciclo for");
					descuento = pagoBean.getDescuento();
					total += pagoBean.getCantidad();
				}
				
				int total_parcial = Constantes.INT_CERO;
				
				
				
				total_parcial = (int) (total_pedido - (total_pedido * descuento / 100));
				
				System.out.println("Total parcial ==> "+total_parcial);
				
				System.out.println("("+total_pedido+"-("+total_pedido+" * "+descuento+" / "+100+"))");

				if (total_parcial == total) {
					formulario.setPagadoTotal(Constantes.STRING_TRUE);
				}
				else
				{
					formulario.setPagadoTotal(Constantes.STRING_FALSE);
				}
				return true;
			}
			else
			{
				formulario.setPagadoTotal(Constantes.STRING_FALSE);
				return false;
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoHelper:CompruebaPagos error catch",e);
			return false;
		}
		
		
	}
	
	/*
	 * LMARIN - 20140714 
	 * Metodo que comprueba si el encargo ya est� liberado
	 * @ VentaPedidoForm   
	 */
	public String CompruebaLiberacion(VentaPedidoForm formulario){
		
		System.out.println("PASO 44  CompruebaLiberacion VPH");
		
	    int lib = 0;	
	    String res = "";
	    
		try {
			for(ProductosBean prod : formulario.getListaProductos()){
		    	if(prod.getLiberado().equals("1")){
		    		lib ++;
		    	}
			}
			log.info("VentaPedidoHelper:CompruebaLiberacion inicio");
			if(!formulario.getFecha().equals(this.traeFechaHoyFormateadaString().trim())){
				 res = "ERROR_FECHA";								
			}else if(lib > 0){
				 res = "ERROR_LIBERACION";					
			}else{
				 res = "OK";
			}							  
			return res;
			
		} catch (Exception e) {
			log.error("VentaPedidoHelper:CompruebaLiberacion error catch",e);
			return "OK";
		}				
	}
	
	public void totalizaPedido(VentaPedidoForm formulario,
			ArrayList<PagoBean> listaPagos) {
		System.out.println("PASO 45 VPH");

		log.info("VentaPedidoHelper:totalizaPedido inicio");
		int total = Constantes.INT_CERO;
		//double descuento = Constantes.INT_CERO;
		if (null == listaPagos || listaPagos.size() == 0) {
			formulario.setTotalPendiante(formulario.getTotal());
			formulario.setAnticipo_pagado(Constantes.INT_CERO);
			formulario.setTiene_pagos(Constantes.STRING_FALSE);
		}
		else
		{
			formulario.setTiene_pagos(Constantes.STRING_TRUE);
			for (PagoBean pagoBean : listaPagos) {
				log.info("VentaPedidoHelper:totalizaPedido entrando ciclo for");
				total += pagoBean.getCantidad();
				//descuento = pagoBean.getDescuento();
			}
			
			//
			formulario.setAnticipo_pagado(total);
			if (total > formulario.getAnticipo_total()) {
				formulario.setAnticipo_total(0);
			}
			else
			{
				formulario.setAnticipo_total(formulario.getAnticipo_total() - total);
			}
			formulario.setTotalPendiante(formulario.getTotal() - total);
		}
		
	}
	public ArrayList<ProductosBean> agregaProductosGratuitos(
			ArrayList<ProductosBean> listaProductosAdicionales,
			ArrayList<ProductosBean> listaProductos) {
		System.out.println("PASO 46 VPH");

		log.info("VentaPedidoHelper:agregaProductosGratuitos inicio");
			for (Iterator<ProductosBean> iterator = listaProductosAdicionales.iterator(); iterator.hasNext();) {
				log.info("VentaPedidoHelper:agregaProductosGratuitos entrando ciclo for");
				ProductosBean productosBean = (ProductosBean) iterator.next();
				
				listaProductos.add(productosBean);
			}
			return listaProductos;
	}
	public ArrayList<ProductosBean> ExtraeProductosAdicionales(
			ArrayList<ProductosBean> listaProductos) {
		System.out.println("PASO 47 VPH");

		log.info("VentaPedidoHelper:ExtraeProductosAdicionales inicio");
		boolean eliminado = false;
		ArrayList<ProductosBean> lista = new ArrayList<ProductosBean>();
		
		for (ProductosBean productosBean : listaProductos) {
			log.info("VentaPedidoHelper:ExtraeProductosAdicionales entrando ciclo for");
			eliminado = false;
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_ECONOPTICAS_ESTUCHE)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_ECONOPTICAS_GAMUZA)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_GMO_ESTUCHE)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_GMO_GAMUZA)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_SUN_PLANET_ESTUCHE)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_SUN_PLANET_GAMUZA)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_ESTUCHE_ARC)) {
				eliminado = true;
			}
			if (productosBean.getCod_barra().equals(Constantes.STRING_PRODUCTO_ESTUCHE_ARX)) {
				eliminado = true;
			}
			if (!eliminado)
			{
				lista.add(productosBean);
			}
		}
		return lista;
	}
	public void modificaGrupo(VentaPedidoForm formulario, int index,
			String string) {
		System.out.println("PASO 48 VPH");

		if (this.CompruebaPagos(formulario)) 
		{
			formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);

		}
		else
		{
			ProductosBean producto = formulario.getListaProductos().get(index);
			producto.setGrupo(string);
			
			formulario.getListaProductos().set(index, producto);
		}
		
		
	}
	public void validaAperturaCaja(VentaPedidoForm formulario, String local) {

		System.out.println("PASO 49 VPH");

	    	log.info("VentaPedidoHelper:validaAperturaCaja inicio");
	        boolean aperturaCaja=false;
	    	
	    	try {
	    		aperturaCaja = PosUtilesFacade.validaAperturaCaja(local, formulario.getAddProducto());
	    		
	    		if (!aperturaCaja) {
	    			formulario.setError(Constantes.STRING_ERROR_VALIDA_CAJA);
				}
	    		else
	    		{
	    			formulario.setFecha(formulario.getAddProducto());
	    		}
			} catch (Exception e) {
				log.error("VentaPedidoHelper:validaAperturaCaja error catch",e);
			}
	}
	public void aplica_descuento(VentaPedidoForm formulario) {
		
		System.out.println("PASO 50 VPH");

		
		log.info("VentaPedidoHelper:aplica_descuento inicio");
		if (this.CompruebaPagos(formulario)) 
		{
			formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
		}
		else
		{
			/*if (formulario.getConvenio().equals(Constantes.STRING_BLANCO) || formulario.getForma_pago().equals(Constantes.STRING_FORMA_PAGO_OASD)) 
			{*/					
					if (formulario.getPromocion().equals(Constantes.STRING_TU_EDAD_TU_DESCUENTO)) 
					{	
						this.aplica_descuento_Promocion(formulario);
						
					}
					else if (formulario.getPromocion().equals(Constantes.STRING_CERO)) 
					{
						this.eliminaDescuentos(formulario);
					}
			/*}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
				formulario.setPromocion(Constantes.STRING_CERO);
			}*/
		}
	}
	public void aplica_descuento_Promocion(VentaPedidoForm formulario) {
		
		System.out.println("PASO 51 VPH");

		
		try {
			tu_edad_tu_descuento promocion = new tu_edad_tu_descuento();
			
			ClienteBean cliente = new ClienteBean();
			cliente = this.traeCliente(null, formulario.getCliente());
			if (null == cliente.getFecha_nac()) {
				formulario.setError(Constantes.STRING_ERROR_CLIENTE_SIN_FECHA);
			}
			else
			{
				int edad = this.calcula_edad(cliente.getFecha_nac());
				for (ProductosBean prod : formulario.getListaProductos()) 
				{
					if ("".equals(prod.getPrevtln())) 
					{
						if (prod.getTipoFamilia().equals("C")) 
						{
							if(PosVentaPedidoFacade.valida_promocion_edad_tu_descuento(prod, Constantes.STRING_TU_EDAD_TU_DESCUENTO))
							{
								promocion.aplica_descuento(prod, edad);
							}
						}
					}
				}
			}
		
		} catch (Exception e) {
			log.error("VentaPedidoHelper:aplica_descuento error catch",e);
		}
		
	}
	public void aplica_descuento_por_linea(VentaPedidoForm formulario) {
		
		System.out.println("PASO 52 VPH");
		
		System.out.println("Indices ===>"+formulario.getAddProducto());
		
		if(formulario.getAddProducto().split("_") != null && formulario.getAddProducto().indexOf("_") != -1 ){
			String [] sindc = formulario.getAddProducto().split("_");
			
			System.out.println("PASO  SPLIT "+sindc[0]+"<=>"+sindc[1]);
			
			double cantidad = formulario.getCantidad_descuento();
			String supervisor = formulario.getDescuento_autoriza();
			

			for(String c : sindc){
				ProductosBean producto = formulario.getListaProductos().get(Integer.parseInt(c));
				producto.setDescuento(cantidad);
				
				
				int precio = producto.getPrecio() * producto.getCantidad();
				double cant = producto.getDescuento();
				/*double diferencia = cant / 100;
				double valor = Math.round(precio * diferencia);*/
				double valor = Math.round((producto.getPrecio() * producto.getCantidad()) * cant / 100);
				double saldo = precio - valor;
				int total_linea =  (int) Math.floor(saldo);
				producto.setImporte(total_linea);
				
				formulario.getListaProductos().set(Integer.parseInt(c), producto);
			}	
			
		}else{
			 int indice = Integer.parseInt(formulario.getAddProducto());


			 double cantidad = formulario.getCantidad_descuento();
			 String supervisor = formulario.getDescuento_autoriza();
			 ProductosBean producto = formulario.getListaProductos().get(indice);
				
			 //if (!(Constantes.STRING_MUL.equals(producto.getFamilia())) && (Constantes.STRING_BLANCO.equals(producto.getPrevtln()))) {
		
			 producto.setDescuento(cantidad);
			
			 if (!"".equals(supervisor)) {
				 formulario.setSupervisor(supervisor);
			 }
			 int precio = producto.getPrecio() * producto.getCantidad();
			 double cant = producto.getDescuento();
			 /*double diferencia = cant / 100;
			 double valor = Math.round(precio * diferencia);*/
			 double valor = Math.round((producto.getPrecio() * producto.getCantidad()) * cant / 100);
			 double saldo = precio - valor;
			 int total_linea =  (int) Math.floor(saldo);
			 producto.setImporte(total_linea);
			
			 formulario.getListaProductos().set(indice, producto);
		}
		
		
		
		//}
		
	}
	
	public void aplica_descuento_total(VentaPedidoForm formulario) {
		
		System.out.println("PASO 53 VPH");

		
		int cantidad = formulario.getCantidad();
		String supervisor = formulario.getDescuento_autoriza();
		
		if (!"".equals(supervisor)) {
			formulario.setSupervisor(supervisor);
		}
		if (cantidad > 0) {
			this.eliminaDescuentos(formulario);
			this.tarifica_Pedido(formulario);
		}
		double cant = cantidad;
		double diferencia = cant/100;
		double sub_total = formulario.getSubTotal();
		double valor = sub_total * diferencia;
		formulario.setDescuento((int) Math.floor(valor));
		formulario.setTotal(formulario.getSubTotal() - formulario.getDescuento());
		formulario.setTotalPendiante(formulario.getTotal());
		formulario.setDtcoPorcentaje(cantidad);
		formulario.setAnticipo((formulario.getTotal() * formulario.getPorcentaje_anticipo()) / 100 );
	}
	public void limpiaCliente(VentaPedidoForm formulario) {
		
		System.out.println("PASO 54 VPH");

		
		formulario.setCliente(Constantes.STRING_BLANCO);
		formulario.setNombre_cliente(Constantes.STRING_BLANCO);
		formulario.setGraduacion(new GraduacionesBean());
		
	}
	public void cargaPedidoAnterior(VentaPedidoForm formulario, String local, String cdg, Session session) {
		
		System.out.println("PASO 55 VPH");

		try {
			String codigoPendiente = cdg;
			//String codigoPendiente ="78/0001893";
			this.traePedidosSeleccionado(codigoPendiente,formulario, session);
			
			formulario.setFlujo(Constantes.STRING_MODIFICAR);
			if(!(null==formulario.getConvenio())){
					for(ProductosBean prod:formulario.getListaProductos()){
						//this.aplicaDescuentoConvenio(prod, formulario.getConvenio(), formulario.getForma_pago());
					}
			}
			if (!(null==formulario.getPromocion())) {
				if (!Constantes.STRING_CERO.equals(formulario.getPromocion())) {
					//this.aplica_descuento_Promocion(formulario);
				}
			}

			this.tarifica_Pedido(formulario);
			this.CompruebaPagos(formulario);
			this.totalizaPedido(formulario, this.traePagos(formulario));
			formulario.setOcultar(Constantes.STRING_OCULTAR_MENU);
			formulario.setCantidadProductos(formulario.getListaProductos().size());
			if(!(PosUtilesFacade.validaAperturaCaja(local, formulario.getFecha()))){
				
					formulario.setBloquea(Constantes.STRING_ACTION_BLOQUEA);
			}else{
				if(PosUtilesFacade.validaEstadoPed(codigoPendiente)){
					formulario.setBloquea(Constantes.STRING_ACTION_LIBRE);
					formulario.setListaProductos(this.ExtraeProductosAdicionales(formulario.getListaProductos()));
				}
				else
				{
					formulario.setBloquea(Constantes.STRING_ACTION_BLOQUEA);
				}
			}
			if (this.validaEstadoEntregado(codigoPendiente)) {
				formulario.setEntregado(Constantes.STRING_TRUE);
			}
			else
			{
				formulario.setEntregado(Constantes.STRING_FALSE);
			}
			
			this.valida_estado_suplementos(formulario, session);
			
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
		} catch (Exception e) {
			log.error("VentaPedidoDispatchActions:IngresaVentaPedido  error catch",e);
		}
		
		
	}
	private boolean validaEstadoEntregado(String codigoPendiente) {
		System.out.println("PASO 56 VPH");

		return PosUtilesFacade.validaEstadoPedidoEntregado(codigoPendiente);
	}
	
	public void cargaPedidoDesdePresupuesto(VentaPedidoForm formulario,
			String local, String cdg, Session session) {
		System.out.println("PASO 57 VPH");

		this.cargaPedidoAnterior(formulario, local, cdg, session);
		ClienteBean cliente = this.traeCliente(null, formulario.getCliente());
		formulario.setNombre_cliente(cliente.getNombre() + " " + cliente.getApellido());
		formulario.setNif(cliente.getNif());
		formulario.setDvnif(cliente.getDvnif());
		formulario.setCliente(cliente.getCodigo());
		this.traeUltimaGraduacionCliente(formulario);
		this.traeUltimaGraduacionContactologiaCliente(formulario);
		
	}
	
	public void aplica_descuento_total_lineal(VentaPedidoForm formulario) 
	{
		System.out.println("PASO 58 VPH");

		String supervisor = formulario.getDescuento_autoriza();
		double cantidad = formulario.getCantidad_descuento();
		
		this.eliminaDescuentos(formulario);
		
		for (ProductosBean prod : formulario.getListaProductos()) {
			
			if (!(Constantes.STRING_MUL.equals(prod.getFamilia())) && (Constantes.STRING_BLANCO.equals(prod.getPrevtln()))) {
				int precio = prod.getPrecio() * prod.getCantidad();
				double cant = cantidad;
				double diferencia = cant / 100;
				double valor = Math.round(precio * diferencia);
				double saldo = precio - valor;
				int total_linea =  (int) Math.floor(saldo);
				prod.setDescuento(cantidad);
				prod.setImporte(total_linea);
				if (!"".equals(supervisor)) {
					formulario.setSupervisor(supervisor);
				}
			}
		}
		
	}
	
	public void aplica_descuento_total_lineal_monto(VentaPedidoForm formulario) 
	{
		System.out.println("PASO 59 VPH");

		//la cantidad se ira descontanto a medida que se establescan los descuentos en los productos
		int cantidad = formulario.getCantidad_linea();
		String supervisor = formulario.getDescuento_autoriza();
		
		this.eliminaDescuentos(formulario);
		
		for (ProductosBean prod : formulario.getListaProductos()) {
			
			if (!(Constantes.STRING_MUL.equals(prod.getFamilia())) && (Constantes.STRING_BLANCO.equals(prod.getPrevtln()))) {
				
				//compruebo si el producto es mayor al valor a descontar
				if(prod.getImporte() >= cantidad)
				{
					//saco la diferencia de valor para calcular el porcentaje
					int diferencia = cantidad;
					double porcentaje = ((double)diferencia * 100) / (double)prod.getImporte(); 
					porcentaje = Math.rint(porcentaje*10000)/10000;
					
					int precio = prod.getPrecio() * prod.getCantidad();
					double cant = porcentaje;
					double dif = cant / 100;
					double valor = Math.round(precio * dif);
					double saldo = precio - valor;
					int total_linea =  (int) Math.floor(saldo);
					prod.setDescuento(porcentaje);
					prod.setImporte(total_linea);
					
					//calcula la diferencia
					int total_1 = precio - total_linea;
					cantidad = cantidad - total_1;
					
					if (cantidad < 0) {
						cantidad = 0;
					}
					
										
					if (!"".equals(supervisor)) {
						formulario.setSupervisor(supervisor);
					}
					
				}
				//si el producto es de menor valor se descuenta el 100% y se descuenta a la diferencia
				else
				{
					//se descuenta la direfencia
					cantidad = cantidad - prod.getImporte();
					prod.setDescuento(100);
					prod.setImporte(0);
					
				}
			}
		}
		
	}
	
	public boolean confirma_producto_codigo_barras(ProductosBean producto,
		String codigo_confirmacion) 
	{
		System.out.println("PASO 60 VPH");

		if (codigo_confirmacion.equals(producto.getCod_barra())) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void aplica_descuento_fenix(VentaPedidoForm formulario, Session session) 
	{
		System.out.println("PASO 61 VPH");

		
		if (this.CompruebaPagos(formulario)) 
		{
			formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
		}
		else
		{
			/*if (formulario.getConvenio().equals(Constantes.STRING_BLANCO)) 
			{*/					
					/*if (formulario.getPromocion().equals(Constantes.STRING_CERO)) 
					{*/
						int grupo_1 = Constantes.INT_CERO;
						int grupo_1_valor = Constantes.INT_CERO;
						int grupo_2 = Constantes.INT_CERO;
						int grupo_2_valor = Constantes.INT_CERO;
						
						this.eliminaDescuentos(formulario);
						
						//CALCULA EL VALOR DE LOS GRUPOS
						for (ProductosBean prod : formulario.getListaProductos()) {
							if (Integer.parseInt(prod.getGrupo())== 1) {
								grupo_1_valor = grupo_1_valor + prod.getPrecio();
							}
							if (Integer.parseInt(prod.getGrupo())== 2) {
								grupo_2_valor = grupo_2_valor + prod.getPrecio();
							}
						}
						
						
						
						//CONSULTA SI EXISTEN EFECTIVAMENTE LOS DOS GRUPOS
						if (grupo_1_valor != Constantes.INT_CERO && grupo_2_valor != Constantes.INT_CERO) {
							
							//DEFINE CUAL ES EL MAS CARO PARA DEJARLO EN EL GRUPO 1 Y 2
							
							grupo_1 = 1;
							grupo_2 = 2;
							
							/*if (grupo_1_valor > grupo_2_valor) 
							{
								grupo_1 = 1;
								grupo_2 = 2;
							}
							else if (grupo_2_valor > grupo_1_valor)
							{
								grupo_1 = 2;
								grupo_2 = 1;
							}
							else
							{
								grupo_1 = 1;
								grupo_2 = 2;
							}*/
							
							//TRAE PRECIOS DEFINITIVOS
							Integer valores_1[] = new Integer[2];
							Integer valores_2[] = new Integer[2];
							
							ProductosBean productos_1[] = new ProductosBean[3];
							ProductosBean productos_2[] = new ProductosBean[3];
							
							int x =Constantes.INT_CERO;
							for (ProductosBean prod : formulario.getListaProductos()) {
								if (prod.getGrupo().equals(String.valueOf(grupo_1))) {
									productos_1[x] = prod;
									x++;
								}
							}
							
							x = Constantes.INT_CERO;
							for (ProductosBean prod : formulario.getListaProductos()) {
								if (prod.getGrupo().equals(String.valueOf(grupo_2))) {
									productos_2[x] = prod;
									x++;
								}
							}
							
							valores_1 = PosUtilesFacade.traeValoresFenix(productos_1);
							valores_2 = PosUtilesFacade.traeValoresFenix(productos_2);
							
							//ver cual de los dos valores es mayor para poder aplicar esa regla
							if (valores_1[0].longValue() > valores_2[0].longValue()) {
								
								if (valores_1[0] != Constantes.INT_CERO) {
									this.aplica_calculo_descuento_fenix(valores_1[0], productos_1);
								}
								if (valores_2[1] != Constantes.INT_CERO) {
									this.aplica_calculo_descuento_fenix(valores_2[1], productos_2);
								}
							}
							else
							{
								if (valores_1[1] != Constantes.INT_CERO) {
									this.aplica_calculo_descuento_fenix(valores_1[1], productos_1);
								}
								//menor
								if (valores_2[0] != Constantes.INT_CERO) {
									this.aplica_calculo_descuento_fenix(valores_2[0], productos_2);
								}
								
							}
							
							
							//mayor
							if (valores_1[0] != Constantes.INT_CERO) {
								this.aplica_calculo_descuento_fenix(valores_1[0], productos_1);
							}
							//menor
							if (valores_1[1] != Constantes.INT_CERO) {
								this.aplica_calculo_descuento_fenix(valores_1[1], productos_2);
							}
							
							this.tarifica_Pedido(formulario);
						}
						else
						{
							//CONSULTA SI EXISTEN EFECTIVAMENTE UN GRUPO
							if (grupo_1_valor != Constantes.INT_CERO) {
								
								grupo_1=1;
								//TRAE PRECIOS DEFINITIVOS
								Integer valores_1[] = new Integer[2];
								
								ProductosBean productos_1[] = new ProductosBean[3];
								
								int x =Constantes.INT_CERO;
								for (ProductosBean prod : formulario.getListaProductos()) {
									if (prod.getGrupo().equals(String.valueOf(grupo_1))) {
										productos_1[x] = prod;
										x++;
									}
								}
								
								valores_1 = PosUtilesFacade.traeValoresFenix(productos_1);
								
								if (valores_1[0] != Constantes.INT_CERO) {
									this.aplica_calculo_descuento_fenix(valores_1[0], productos_1);
								}
								
								this.tarifica_Pedido(formulario);
							}
						}
					/*}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
					}*/
			/*}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
				formulario.setPromocion(Constantes.STRING_CERO);
			}*/
		}
	}
	
	private void aplica_calculo_descuento_fenix(Integer valores_final,
			ProductosBean[] productos) {
		System.out.println("PASO 62 VPH");

		int valor = Constantes.INT_CERO;
		
		for (ProductosBean productosBean : productos) {
			valor += productosBean.getPrecio();
		}
		
		int diferencia = valor - valores_final;
		
		//calcula en orden los productos de mayor a menor valor
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 3; k++) {
				if (productos[i].getPrecio() > productos[k].getPrecio()) {
					ProductosBean prod = new ProductosBean();
					prod = productos[i];
					productos[i]=productos[k];
					productos[k]=prod;
				}
			}
		}
		
		//en caso de que la diferencia sea menor al producto con mayor precio
		if (diferencia <= productos[0].getPrecio()) {
			double dife = diferencia;
			
			double dto = (dife * 100) / productos[0].getPrecio();
			dto = (dto*10000)/10000;
			productos[0].setDescuento(dto);
			
			int precio = productos[0].getPrecio();
			double dif = productos[0].getDescuento() / 100;
			double val = Math.round(precio * dif);
			double saldo = precio - val;
			int total_linea =  (int) Math.floor(saldo);
			productos[0].setImporte(total_linea);
			
		}
		else //en caso de que la diferencia sea mayor al producto con mayor precio
		{
			diferencia =  diferencia - productos[0].getPrecio();
			productos[0].setDescuento(100);
			
			int precio = productos[0].getPrecio();
			double dif = productos[0].getDescuento() / 100;
			double val = Math.round(precio * dif);
			double saldo = precio - val;
			int total_linea =  (int) Math.floor(saldo);
			productos[0].setImporte(total_linea);
			
			if (diferencia <= productos[1].getPrecio()) {
				double dto = ((double)diferencia * 100) / productos[1].getPrecio();
				dto = (dto*10000)/10000;
				productos[1].setDescuento(dto);
				
				precio = productos[1].getPrecio();
				dif = productos[1].getDescuento() / 100;
				val = Math.round(precio * dif);
				saldo = precio - val;
				total_linea =  (int) Math.floor(saldo);
				productos[1].setImporte(total_linea);
				
			}
			else
			{
				diferencia =  diferencia - productos[1].getPrecio();
				productos[1].setDescuento(100);
				
				precio = productos[1].getPrecio();
				dif = productos[1].getDescuento() / 100;
				val = Math.round(precio * dif);
				saldo = precio - val;
				total_linea =  (int) Math.floor(saldo);
				productos[1].setImporte(total_linea);
				
				double dto = ((double)diferencia * 100) / productos[2].getPrecio();
				dto = (dto*10000)/10000;
				productos[2].setDescuento(dto);
				
				precio = productos[2].getPrecio();
				dif = productos[2].getDescuento() / 100;
				val = Math.round(precio * dif);
				saldo = precio - val;
				total_linea =  (int) Math.floor(saldo);
				productos[2].setImporte(total_linea);
			}
			
		}
		
		
		int suma_final = Constantes.INT_CERO;
		for (ProductosBean productosBean : productos) {
			suma_final += productosBean.getImporte();
		}
		
	}
	public void agrega_adicionales_arcli(VentaPedidoForm formulario) {
		System.out.println("PASO 63 VPH");

		int indice = Integer.parseInt(formulario.getAddProducto());
		ProductosBean prod = formulario.getListaProductos().get(indice);
		
		prod.setArcli_armazon(formulario.getTipo_armazon());
		prod.setArcli_diagonal(Integer.parseInt(formulario.getDiagonal()));
		prod.setArcli_horizontal(Integer.parseInt(formulario.getHorizontal()));
		prod.setArcli_vertical(Integer.parseInt(formulario.getVertical()));
		prod.setArcli_puente(Integer.parseInt(formulario.getPuente()));
		
		formulario.getListaProductos().set(indice, prod);
		
	}
	
	
	public void carga_formulario_adicionales_arcli(VentaPedidoForm formulario, Integer indice) {
		
		System.out.println("PASO 64 VPH");

		ProductosBean prod = formulario.getListaProductos().get(indice);
		formulario.setAddProducto(String.valueOf(indice));
		if (null == prod.getArcli_armazon()) 
		{
			formulario.setTipo_armazon(Constantes.STRING_BLANCO);
		}
		else
		{
			formulario.setTipo_armazon(prod.getArcli_armazon());
		}
		
		formulario.setPuente(String.valueOf(prod.getArcli_puente()));
		formulario.setDiagonal(String.valueOf(prod.getArcli_diagonal()));
		formulario.setVertical(String.valueOf(prod.getArcli_vertical()));
		formulario.setHorizontal(String.valueOf(prod.getArcli_horizontal()));
		
	}
	
	public ArrayList<ProductosBean> eliminarProductosMulti(String addProducto,
			ArrayList<ProductosBean> listaProductos, VentaPedidoForm formulario, Session session) {
		
		System.out.println("PASO 65 VPH");

		log.info("VentaPedidoHelper:eliminarProductos inicio");
				
		ProductosBean pro = listaProductos.get(Integer.parseInt(addProducto));
		
		if("MUL".equals(pro.getFamilia())){
			ArrayList<ProductosBean> listaMultiofertasAUX = new ArrayList<ProductosBean>();
			ArrayList<ProductosBean> listaMultiofertasproductosAUX = new ArrayList<ProductosBean>();
			ArrayList<ProductosBean> listaMultiofertas = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS);
			ArrayList<ProductosBean> listaproductosMultiofertas = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
			
			if(null != listaMultiofertas){
				for(ProductosBean multi : listaMultiofertas){
					if(!(pro.getCodigo().equals(multi.getCodigo())) || pro.getIndexMulti() != multi.getIndexMulti()){
						listaMultiofertasAUX.add(multi);
						if(null != listaproductosMultiofertas){
							for(ProductosBean productomulti : listaproductosMultiofertas){
								if((productomulti.getCodigoMultioferta().equals(multi.getCodigo())) && (productomulti.getIndexRelMulti() == multi.getIndexMulti())){
									listaMultiofertasproductosAUX.add(productomulti);
								}
							}
						}
					}
				}
				
			}
			session.setAttribute(Constantes.STRING_LISTA_MULTIOFERTAS,listaMultiofertasAUX);
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS,listaMultiofertasproductosAUX);
			session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS_AUX,listaMultiofertasproductosAUX);
		}
		
		listaProductos.remove(Integer.parseInt(addProducto));
		
		return listaProductos;
	}
	public void valida_seguro_garantia(VentaPedidoForm formulario) {
		
		System.out.println("PASO 66 VPH");

		
		String[][] resultado = null;
		try {
			resultado = PosVentaPedidoFacade.valida_seguro_garantia(formulario.getCdg(), formulario.getEncargo_garantia());
		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_seguro_garantia error catch",e);
		}
		
		if (resultado[0][0].equals(Constantes.STRING_FALSE)) {
			formulario.setError(Constantes.STRING_ERROR_SEGURO_GARANTIA_NO_VALIDO);
			formulario.setTipo_pedido(Constantes.STRING_CERO);
		}
		else
		{
			formulario.setSeguro(resultado);
			formulario.setEstado(Constantes.STRING_CONFIRMA_SG_AUTORIZACION);
			
		}
		
	}
	
	//LMARIN 20170822
	public void aplica_descuento_seguro(VentaPedidoForm formulario) {
		
		System.out.println("ENCARGO SEGURO =>"+formulario.getEncargo_seguro());
		int importe = Integer.parseInt(this.traeImporteVG(formulario.getEncargo_seguro()));
		int sumprecio = 0; 
		double dto = 0;
		
		for (ProductosBean prod : formulario.getListaProductos()) {
			sumprecio += (int)(prod.getImporte());
		}
		
		dto = sumprecio;//(double)(((importe * 100)/sumprecio)/100);
		
		System.out.println("dto prod ===> "+dto);
		
		for (ProductosBean prod : formulario.getListaProductos()) {
			
				int precio = (int)(prod.getPrecio() * (dto));
				prod.setPrecio(precio);
				prod.setImporte(precio * prod.getCantidad());
		}
		
		
		//formulario.setDescuento(70);
		
	}
	public void aplica_descuento_sg(VentaPedidoForm formulario) {
		
		System.out.println("PASO 67 VPH");

		
		ArrayList<String> lista_grupos = new ArrayList<String>();
		for (int i = 0; i < formulario.getSeguro().length; i++) {
			for (ProductosBean prod : formulario.getListaProductos()) {
				if (null != prod.getFecha_graduacion() && !Constantes.STRING_BLANCO.equals(prod.getFecha_graduacion())) {
					if (prod.getFecha_graduacion().equals(formulario.getSeguro()[i][1])
							&& String.valueOf(prod.getNumero_graduacion()).equals(formulario.getSeguro()[i][2])) {
						lista_grupos.add(prod.getGrupo());
					}
				}
			}
		}
		
		//limpio la lista de grupos
		ArrayList<String> lista_grupos_2 = new ArrayList<String>();
		
		boolean existe = false;
		for (int i = 0; i < lista_grupos.size(); i++) {
			existe = false;
			if (lista_grupos_2.size() > 0) {
				for (int k = 0; k < lista_grupos_2.size(); k++) {
					if (lista_grupos.get(i).equals(lista_grupos_2.get(k))) {
						existe = true;
					}
				}
			}
			
			if (!existe) {
				lista_grupos_2.add(lista_grupos.get(i));
			}
		}
		
		//realiza el descuento por grupo
		for (String grupo : lista_grupos_2) 
		{
			for (ProductosBean prod : formulario.getListaProductos()) {
				if (prod.getGrupo().equals(grupo)) {
					
					int precio = prod.getPrecio() * prod.getCantidad();
					double cant = formulario.getCantidad();
					double diferencia = cant / 100;
					double valor = Math.round(precio * diferencia);
					double saldo = precio - valor;
					int total_linea =  (int) Math.floor(saldo);
					prod.setImporte(total_linea);
					prod.setDescuento(formulario.getCantidad());
				}
			}
			
		}
		
		this.tarifica_Pedido(formulario);
		
	}
	public void elimina_seguro_garantia(VentaPedidoForm formulario) {
		
		System.out.println("PASO 68 VPH");

		
		String[][] resultado = null;
		
		if (!formulario.getEncargo_garantia().equals(Constantes.STRING_BLANCO)) {
			try {
				resultado = PosVentaPedidoFacade.valida_seguro_garantia(formulario.getCdg(), formulario.getEncargo_garantia());
			} catch (Exception e) {
				log.error("VentaPedidoHelper:valida_seguro_garantia error catch",e);
			}
			
			if (resultado[0][0].equals(Constantes.STRING_FALSE)) {
				formulario.setError(Constantes.STRING_ERROR_SEGURO_GARANTIA_NO_ELIMINABLE);
				formulario.setTipo_pedido(Constantes.STRING_SG);
			}
			else
			{
				formulario.setEncargo_garantia(Constantes.STRING_BLANCO);
				formulario.setSeguro(resultado);
				formulario.setCantidad(0);
				this.aplica_descuento_sg(formulario);
			}
		}
		
		
		
		
	}
	public void traeUltimaGraduacionContactologiaCliente(
			VentaPedidoForm formulario) {
		System.out.println("PASO 69 VPH");

		try{
			
			formulario.setGraduacion_lentilla(PosGraduacionesFacade.traeContactologiaClienteUltima(formulario.getCliente().toString()));
			
		}catch(Exception ex){
			log.error("VentaPedidoHelper:traeUltimaGraduacionContactologiaCliente error catch",ex);
		}	
		
		
	}
	
	
	
	public void ingresaPedidoLineaAdicionales(VentaPedidoForm formulario, String local, ArrayList<ProductosBean> lista_adicionales) {
		
		System.out.println("PASO 70 VPH");

		
		log.info("VentaPedidoHelper:ingresaPedidoLinea inicio");
		String codigo = formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo();
		int x = formulario.getListaProductos().size()+1;
		if (null != lista_adicionales && lista_adicionales.size() > 0) {
			
			try {
				for (ProductosBean productosBean : lista_adicionales) {
					log.info("VentaPedidoHelper:ingresaPedidoLinea entrando ciclo for");
					
						PosVentaPedidoFacade.insertaDetalle(productosBean, x, codigo, local);
						x++;
					}
					
			} catch (Exception e) {
				log.error("VentaPedidoHelper:ingresaPedidoLinea error catch",e);
			}
		}
		
	}
	public void validaProductosArcliDesdePresupuesto(VentaPedidoForm formulario)
	{
		System.out.println("PASO 71 VPH");

		formulario.setEstado(Constantes.STRING_VALIDA_PRODUCTOS_ARCLI_DE_PRESUPUESTO);
		
			this.valida_arcli(formulario.getListaProductos(), formulario);
		
	}
	
		
	public void valida_arcli(ArrayList<ProductosBean> listaProductos, VentaPedidoForm formulario) {
		System.out.println("PASO 72 VPH");

		int x;
		if (null == formulario.getAddProducto() || Constantes.STRING_BLANCO.equals(formulario.getAddProducto())) {
			x = 0;
		}
		else
		{
			x = Integer.valueOf(formulario.getAddProducto())+1;
		}
		for (int i = x; i < listaProductos.size(); i++) {
		
			ProductosBean producto = listaProductos.get(i);
			
			if (producto.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI))
			{
				formulario.setAddProducto(String.valueOf(i));
				formulario.setEstado(Constantes.STRING_PRODUCTO_ARCLI_PRESUPUESTO);
				break;
			}
		}
		
	}
	public boolean valida_convenio_valido(VentaPedidoForm formulario) {
		
		System.out.println("PASO 73 VPH");

		ArrayList<ConvenioBean> listaConvenios = new ArrayList<ConvenioBean>(); 
		
			if (null == formulario.getConvenio() || Constantes.STRING_BLANCO.equals(formulario.getConvenio())) {
				return true;
			}
			try {
				listaConvenios = PosUtilesFacade.traeConvenios(formulario.getConvenio(), 
															null, null);
			} catch (Exception e) {
				log.error("BusquedaConveniosHelper:valida_convenio_valido error catch",e);
			}
			
		if (null != listaConvenios && listaConvenios.size()>0) {
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public  boolean  validaTipoPedido(VentaPedidoForm form){
		
		boolean estado = false;
		try {
			estado = PosVentaPedidoFacade.validaTipoPedido(form.getTipo_pedido(),form.getCodigo_suc());
		} catch (Exception e) {
			System.out.println("Me caigo en valida Tipo Pedido  Helper");
			log.error("VentaPedidoHelper:validaTipoPedido error catch",e);
		}
		return estado;
		
	} 
	
	public int  validExPed(VentaPedidoForm form){
		
		int estado = 0;
		try {
			estado = PosUtilesFacade.validExPed(form.getEncargo_padre(),form.getNif());
		} catch (Exception e) {
			System.out.println("Me caigo en valida Tipo Pedido  Helper ==>"+e.getMessage());
			log.error("VentaPedidoHelper:validaTipoPedido error catch",e);
		}
		return estado;
		
	} 
	
	public int  validExTienda(VentaPedidoForm form){
		
		int estado = 0;
		System.out.println("codigo sucursal ==>"+form.getCodigo_suc());
		try {
			estado = PosUtilesFacade.validExTienda(form.getCodigo_suc());
			System.out.println("Estado Sucursal ==>"+estado);

		} catch (Exception e) {
			System.out.println("Me caigo en valida Tipo local  Helper ==>"+e.getMessage());
			log.error("VentaPedidoHelper:validaTipoLocal error catch",e);
		}
		return estado;
		
	} 
	
	public int  tipoConvenio(VentaPedidoForm form,String local){
		
		int dto = 0;
		ProductosBean prod = new ProductosBean();
		for (int i = Constantes.INT_CERO; i < form.getListaProductos().size(); i++) {			
				prod = form.getListaProductos().get(i);
		}
		try {
			if(form.getListaProductos().size() != 0 ){
				dto = PosUtilesFacade.traeDescuentoConvenio(prod, form.getConvenio(), form.getForma_pago(),local);
			}

		} catch (Exception e) {
			System.out.println("Me caigo en valida Tipo local  Helper ==>"+e.getMessage());
			log.error("VentaPedidoHelper:validaTipoLocal error catch",e);
		}
		return dto;
		
	} 
	
	public int  valida_tipo_convenio(VentaPedidoForm form){
		
		int estado = 0;
		try {
			estado = PosUtilesFacade.valida_tipo_convenio(form.getConvenio());

		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		return estado;
		
	} 
	
	public int  valida_permiso_mod_fpago(String user,String pass,String local){
		
		int estado = 0;
		try {
			estado = PosUtilesFacade.valida_permiso_mod_fpago(user,pass,local);

		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		return estado;
		
	} 
	
	public int  valida_mofercombos(VentaPedidoForm formulario){
		
		int estado = 0;
		try {
			System.out.println("valida_mofercombos_helper ==> "+formulario.getCdg_mofercombo());
			estado = PosUtilesFacade.traeDTOMofercombos(formulario.getCdg_mofercombo(),formulario.getCdg_mofercombo());

		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		return estado;
		
	} 
	
	/*
	 * LMARIN 20141219
	 * Metodo que invoca la impresion de los datos que se utilizaran para generar la boleta  
	 */
	public String genera_datos_belec(String tipodoc,SeleccionPagoForm formulario,String foliocl, Session session){
				
		Utils util = new Utils();
		String res = null;
		String folio = foliocl;
		
		try {					
			ArrayList<ProductosBean> listProductos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);				
			res = util.generaBoletaELEC(tipodoc,folio,formulario,listProductos);
			System.out.println("Boleta ====>===>==>=> "+res);
		} catch (IOException e) {			
			System.out.println("IOEXCEPCION  20141219 => "+ e.getMessage());
		} catch (Exception e) {
			System.out.println("EXCEPCION  20141219 => "+ e.getMessage());
		}
		return res ;
	} 
		
	/**
	 *@title ticket de cambio
	 *@author  LMARIN   	 
	 *@param SeleccionPagoForm
	 *@return String 
	 *@date 20150610
	 */
	public String ticket_cambio(SeleccionPagoForm sform){		
		String res = "";
		Utils util = new Utils();
		res = util.ticket_cambio(sform);
		return res;
	}	
	
	/**
	 *@title validaPromoLec
	 *@author  LMARIN   	 
	 *@param SeleccionPagoForm
	 *@return String 
	 *@date 20150610
	 */
	public int validaPromoLec(VentaPedidoForm formulario){		
		int estado = 0;
		try {
			estado = PosUtilesFacade.validaPromoLec(formulario.getEncargo_padre(),formulario.getConvenio());
		} catch (Exception e) {
			log.error("VentaPedidoHelper:valida_tipo_conevenio error catch",e);
		}
		return estado;
	}	
	
	/** 
	 * @AUTHOR LMARIN 20160803
	 */
	public int gruposLentillas(String encargo) {
		int estado = 0;
		try {
			estado = PosUtilesFacade.gruposLentillas(encargo);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:gruposLentillas error catch",e);
		}
		return estado;
		
	}
	
	/** 
	 * @AUTHOR LMARIN 20170821
	 * @DESCRIPCION: Metodo valida Venta Seguro
	 */
	public int validaVentaSeguro(String encargo) {
		int estado = 0;
		try {
			estado = PosUtilesFacade.validaVentaSeguro(encargo);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:validaVentaseguro error catch",e);
		}
		return estado;
		
	}
	
	/** 
	 * @AUTHOR LMARIN 20171010
	 * @DESCRIPCION: Metodo valida si aplica la liberacion automatica
	 */
	public String validaLibau(String local) {
		String estado = "";
		try {
			estado = PosUtilesFacade.validaLibau(local);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:validaLibau error catch",e);
		}
		return estado;
		
	}
	
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20171020
	 * @DESCRIPCION: Metodo que trae importe del encargo anterior en la venta seguro
	 */
	public  String traeImporteVG(String encargo)  {
		String importe = "";
		try {
			importe = PosUtilesFacade.traeImporteVG(encargo);
		} catch (Exception e) {
			log.error("VentaPedidoHelper:validaLibau error catch",e);
		}
		return importe;
		
	}
	
	public void actualizaProductosPorCupon(VentaPedidoForm formulario,String local) {
		System.out.println("PASO actualizaProductosPorCupon VPH");

		log.info("VentaPedidoHelper:actualizaProductosPorCupon inicio");
		this.tarifica_Pedido(formulario);
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) {
		
			for (int i = Constantes.INT_CERO; i < formulario.getListaProductos().size(); i++) {
				log.info("VentaPedidoHelper:actualizaProductosPorCupon entrando ciclo for");
				ProductosBean prod = formulario.getListaProductos().get(i);
				if (Constantes.STRING_BLANCO.equals(prod.getPrevtln())) {
					System.out.println("actualizaProductosPorCupon");
					this.aplicaDescuentoCupon(prod, formulario.getNumero_cupon(), formulario.getForma_pago(),formulario.getListaProductos(),local,formulario.getAgente(),formulario.getCliente(),formulario.getCdg());
				}
				formulario.getListaProductos().set(i, prod);
			}
		}
	}
	
	
	public void actualizaProductosPromoCombo(VentaPedidoForm formulario,String local) {
		System.out.println("PASO actualizaProductosPromoCombo VPH");

		log.info("VentaPedidoHelper:actualizaProductosPromoCombo inicio");
		this.tarifica_Pedido(formulario);
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) {
		
			for (int i = Constantes.INT_CERO; i < formulario.getListaProductos().size(); i++) {
				log.info("VentaPedidoHelper:actualizaProductosPromoCombo entrando ciclo for");
				ProductosBean prod = formulario.getListaProductos().get(i);
				if (Constantes.STRING_BLANCO.equals(prod.getPrevtln())) {
					System.out.println("actualizaProductosPromoCombo");
					this.aplicaDescuentoCombo(prod,formulario.getListaProductos(),local,formulario.getAgente(),formulario.getCliente());
				}
				formulario.getListaProductos().set(i, prod);
			}
		}
	}
	
	public String valida_cupon(String cupon,String nif,String cdg)  throws Exception{
			
			String valor = "";
			try {
		        valor = PosUtilesFacade.valida_cupon(cupon,nif,cdg);
		        return valor;

		    }catch (Exception e){
		        e.printStackTrace();
		        throw new Exception("VebntaPedidoHelper: valida_cupon");
		    }
	}
	
	public int valida_encargo(VentaPedidoForm formulario) throws Exception{
		
		return PosUtilesFacade.valida_encargo(formulario.getEncargo_padre(),formulario.getCliente());
	}
	
	public int valida_pcombo(String codigo) throws Exception{
		
		return PosUtilesFacade.valida_pcombo(codigo);
	}
	
	
	public String valida_promo_pares(String codigo) throws Exception{
		
		return PosUtilesFacade.valida_promo_pares(codigo);
	}
	public void actualizaProductosPromoPar(VentaPedidoForm formulario,String grupo,String index,String dto) {
		System.out.println("PASO actualizaProductosPromoCombo VPH");

		log.info("VentaPedidoHelper:actualizaProductosPromoCombo inicio");
		this.tarifica_Pedido(formulario);
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) {
		
			for (int i = Constantes.INT_CERO; i < formulario.getListaProductos().size(); i++) {
				log.info("VentaPedidoHelper:actualizaProductosPromoCombo entrando ciclo for");
				ProductosBean prod = formulario.getListaProductos().get(i);
				if (Constantes.STRING_BLANCO.equals(prod.getPrevtln())) {
					System.out.println("actualizaProductosPromoCombo");
					this.aplicaDescuentoPromoPar(prod,formulario.getListaProductos(),i,grupo,index,dto);
				}
				formulario.getListaProductos().set(i, prod);
			}
		}
	}
	
	public void actualizaProductosPromoSanValentin(VentaPedidoForm formulario,String local) throws Exception {
		System.out.println("PASO actualizaProductosPromoCombo VPH");

		log.info("VentaPedidoHelper:actualizaProductosPromoCombo inicio");
		this.tarifica_Pedido(formulario);
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) {
		
			for (int i = Constantes.INT_CERO; i < formulario.getListaProductos().size(); i++) {
				log.info("VentaPedidoHelper:actualizaProductosPromoCombo entrando ciclo for");
				ProductosBean prod = formulario.getListaProductos().get(i);
				if (Constantes.STRING_BLANCO.equals(prod.getPrevtln())) {
					System.out.println("actualizaProductosPromoSanValentin");
					this.aplicaDescuentoSanValentin(prod,formulario.getListaProductos(),local);
				}
				formulario.getListaProductos().set(i, prod);
			}
		}
	}
	
	public ArrayList<ProductosBean> validaCristal(String ojo, String tipo_fam, String familia, String subfamilia, String grupo,
	           String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local,double cilindro,double esfera,int eje) throws Exception {
		 
		 
		 ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
		 listaProductos = PosUtilesFacade.traeProductosGraduados(ojo, tipo_fam, familia, subfamilia, grupo, descripcion, codigoBusqueda, codigoBarraBusqueda, local, cilindro, esfera, eje);
		 return listaProductos;
	}
	
	
	public void insertaCliente_inter(VentaPedidoForm ven,String local) throws Exception{
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		VentaPedidoBean vbean = new VentaPedidoBean();
		vbean.setDni_pas(ven.getDni_pas());
		vbean.setNombre_inter(ven.getNombre_internacional());
		vbean.setNacionalidad(ven.getNacionalidad());
		vbean.setEmail_inter(ven.getEmail_inter());
		vbean.setCdg(ven.getCdg());
		
		impl.insertaCliente_inter(vbean,local);
	}
	
	
}
