/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.controlador.ventaDirecta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.controlador.BeanGlobal;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.SalidaArchivo;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;
import cl.gmo.pos.venta.web.helper.BusquedaProductosHelper;


/**
 *
 * @author Advice70
 */
public class VentaPedidoDispatchActions {
	Logger log = Logger.getLogger( this.getClass() );
	VentaPedidoHelper helper = new VentaPedidoHelper();
	private String agentePago;
	public VentaPedidoDispatchActions(){}

	public VentaPedidoForm cargaInicial(VentaPedidoForm formulario, String local, Session session)
	{
		log.info("VentaPedidoDispatchActions:cargaInicial  inicio");
		formulario.setListaFormaPago(helper.traeFormasPago());
		formulario.setListaAgentes(helper.traeAgentes(local));
		formulario.setListaConvenios(helper.traeConvenios());
		formulario.setListaDivisas(helper.traeDivisas());
		formulario.setListaIdiomas(helper.traeIdiomas());
		formulario.setListaPromociones(helper.traePromociones());
		formulario.setListaTiposPedidos(helper.traeListaTiposPedidos());
		formulario.setEliminarPedid(Constantes.STRING_BLANCO);
		formulario.setBloquea(Constantes.STRING_BLANCO);
		formulario.setOcultar(Constantes.STRING_BLANCO);
		formulario.setSeg_cristal(Constantes.STRING_BLANCO);
		formulario.setCliente_dto(Constantes.STRING_BLANCO);
		formulario.setVenta_seguro(Constantes.STRING_BLANCO);
		formulario.setEncargo_garantia(Constantes.STRING_BLANCO);
		
		session.setAttribute("DTOWEB","0");
		session.setAttribute("se_encargo_padre","");	
		session.setAttribute("se_cupon","");
		

		helper.traeDatosFormulario(formulario, session);
		log.info("VentaPedidoDispatchActions:cargaInicial  fin");
		
		return formulario;
	}

	public VentaPedidoForm IngresaVentaPedido(VentaPedidoForm form,	Session request) throws Exception
	{
		
		log.info("VentaPedidoDispatchActions:IngresaVentaPedido  inicio");

		VentaPedidoForm formulario = (VentaPedidoForm)form;
		Session session = request;
		
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString().trim();
		formulario.setEstado(Constantes.STRING_VENTA);
		formulario.setMsnPedidoEntrega(Constantes.STRING_BLANCO);
		formulario.setError(Constantes.STRING_ERROR);
		session.setAttribute(Constantes.STRING_FECHA_ENTREGA, formulario.getFecha_entrega());
		formulario.setEstaGrabado(2);

		session.setAttribute(Constantes.STRING_SESSION_FORMULARIO_VTA_PEDIDO, formulario);//Guardar el session el formulario para ser usado en la ficha cliente.
		boolean nestado = false;
		
		//GUARDO EN SESSION NUMERO ENCARGO REPROCESO
		if(!formulario.getEncargo_padre().equals("")){
			session.setAttribute("se_encargo_padre",formulario.getEncargo_padre());//+Constantes.STRING_SLASH+formulario.getGrupoSing());	
		}
		if(!formulario.getNumero_cupon().equals("")){
			session.setAttribute("se_cupon",formulario.getNumero_cupon());
		}
		System.out.println("Paso 1 VP ==>"+formulario.getAccion());
		
		if (Constantes.STRING_AGREGA_ADICIONALES_ARCLI_PRESUPUESTO.equals(formulario.getAccion())) {
			helper.agrega_adicionales_arcli(formulario);
			formulario.setAccion(Constantes.STRING_VALIDA_PRODUCTOS_ARCLI_DE_PRESUPUESTO);
		}
		if (Constantes.STRING_VALIDA_PRODUCTOS_ARCLI_DE_PRESUPUESTO.equals(formulario.getAccion())) {
			helper.validaProductosArcliDesdePresupuesto(formulario);
		}
		if (Constantes.STRING_VALIDA_SG.equals(formulario.getAccion())) {
			formulario.setSupervisor("0");//f
			formulario.setDescuento_autoriza("");//f
			helper.eliminaDescuentoTarificaPedido(formulario);//f
			helper.tarifica_Pedido(formulario);//f
			helper.valida_seguro_garantia(formulario);
		}
		if (Constantes.STRING_ELIMINA_SG.equals(formulario.getAccion())) {
			formulario.setSupervisor("0");//f
			formulario.setDescuento_autoriza("");//f
			nestado = helper.validaTipoPedido(formulario);
			helper.eliminaDescuentoTarificaPedido(formulario);//f
			helper.tarifica_Pedido(formulario);//f
			helper.elimina_seguro_garantia(formulario);
			formulario.setMostrarDev(nestado);
			//formulario.setEncargo_padre("");
			
		}
		if (Constantes.STRING_APLICA_PRECIO_ESPECIAL.equals(formulario.getAccion()))
		{
			int indice = Integer.parseInt(session.getAttribute(Constantes.STRING_PRODUCTO).toString());
			formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
			ProductosBean producto = formulario.getListaProductos().get(indice);
			
			formulario.getListaProductos().set(indice, helper.aplicaPrecioEspecial(producto, formulario.getFecha()));
			
			helper.tarifica_Pedido(formulario);

		}
		if (Constantes.STRING_DESCUENTO_SG.equals(formulario.getAccion())) {
			helper.aplica_descuento_sg(formulario);
		}
		if (Constantes.STRING_ADICIONALES_ARCLI.equals(formulario.getAccion())) {
			helper.agrega_adicionales_arcli(formulario);
		}
		if (Constantes.STRING_DESCUENTO_FENIX.equals(formulario.getAccion())) {
			helper.aplica_descuento_fenix(formulario, session);
		}
		if(Constantes.STRING_CONFIRMA_PRODUCTO.equals(formulario.getAccion()))
		{
			ProductosBean producto = (ProductosBean) session.getAttribute(Constantes.STRING_PRODUCTO);
			if (Constantes.STRING_CONFIRMA.equals(formulario.getAddProducto())) {
				if (helper.confirma_producto_codigo_barras(producto, formulario.getCodigo_confirmacion())) {
					//return mapping.findForward(Constantes.FORWARD_PEDIDO);
					return formulario;
				}
				else
				{
					formulario.setError(Constantes.STRING_ERROR_CONFIRMA_PRODUCTO);
					helper.eliminarProductos(Integer.toString(producto.getIndice()), formulario.getListaProductos(), formulario);
					//return mapping.findForward(Constantes.FORWARD_PEDIDO);
					return formulario;
				}
			}
			else
			{
				helper.eliminarProductos(Integer.toString(producto.getIndice()), formulario.getListaProductos(), formulario);
				//return mapping.findForward(Constantes.FORWARD_PEDIDO);
				return formulario;
			}
		}
		if (Constantes.STRING_APLICA_DESCUENTO_PROMOCION.equals(formulario.getAccion())) 
		{
			helper.eliminaDescuentos(formulario);
			helper.tarifica_Pedido(formulario);
			/*if (helper.CompruebaPagos(formulario)) {
				formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
			}
			else
			{
					if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
					 //helper.aplica_descuento(formulario);
					 //helper.tarifica_Pedido(formulario);
					}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_SIN_PROD_PROMO);
						formulario.setPromocion(Constantes.STRING_CERO);
					}
			}*/
			
		}
		if (Constantes.STRING_ELIMINA_CONVENIO.equals(formulario.getAccion()))
		{
			if (helper.CompruebaPagos(formulario)) {
				formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
			}
			else
			{
				if (!Constantes.STRING_BLANCO.equals(formulario.getConvenio())) {
					
					if (Constantes.STRING_FALSE.equals(formulario.getDesde_presupuesto())) {
						if (null != formulario.getListaProductos() && formulario.getListaProductos().size() != 0) {
							helper.eliminaDescuentos(formulario);
							formulario.setConvenio(Constantes.STRING_BLANCO);
							formulario.setConvenio_det(Constantes.STRING_BLANCO);
							formulario.setIsapre(Constantes.STRING_BLANCO);
							formulario.setConvenio_ln(Constantes.INT_CERO);
							helper.tarifica_Pedido(formulario);
						}
						else
						{
							formulario.setConvenio(Constantes.STRING_BLANCO);
							formulario.setConvenio_det(Constantes.STRING_BLANCO);
							formulario.setConvenio_ln(Constantes.INT_CERO);
							formulario.setIsapre(Constantes.STRING_BLANCO);
						}
						
					}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_NO_MODIFICABLE_PRESUPUESTO);
					}
					
				}
			}
		}
		if (Constantes.STRING_APLICA_DESCUENTO_LINEA.equals(formulario.getAccion())) 
		{
			//Se comenta mientras se repara la multiofertra. por ahora se habilita el descuento por linea con convenio
			//if (Constantes.STRING_BLANCO.equals(formulario.getConvenio()) && "0".equals(formulario.getPromocion()) && formulario.getDtcoPorcentaje() == 0) {
			//if ("0".equals(formulario.getPromocion()) && formulario.getDtcoPorcentaje() == 0) {
				if (helper.CompruebaPagos(formulario)) {
					formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO_MOD);
				}
				else
				{
						helper.aplica_descuento_por_linea(formulario);
						helper.tarifica_Pedido(formulario);
				}
			/*}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
			}*/
			
			//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
			return formulario;
		}
		
		if (Constantes.STRING_APLICA_DESCUENTO_TOTAL.equals(formulario.getAccion())) 
		{
			//Se comenta mientras se repara la multiofertra. por ahora se habilita el descuento por total con convenio
			//if (Constantes.STRING_BLANCO.equals(formulario.getConvenio()) && "0".equals(formulario.getPromocion())) {
			//if ("0".equals(formulario.getPromocion())) {
				if (helper.CompruebaPagos(formulario)) {
					formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO_MOD);
				}
				else
				{
					helper.aplica_descuento_total_lineal(formulario);
					helper.tarifica_Pedido(formulario);
				}
			/*}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
			}*/
			
			//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
			return formulario;
		}
		
		if (Constantes.STRING_APLICA_DESCUENTO_TOTAL_MONTO.equals(formulario.getAccion())) 
		{
			//Se comenta mientras se repara la multiofertra. por ahora se habilita el descuento por total con convenio
			//if (Constantes.STRING_BLANCO.equals(formulario.getConvenio()) && "0".equals(formulario.getPromocion())) {
			//if ("0".equals(formulario.getPromocion())) {	
				if (helper.CompruebaPagos(formulario)) {
					formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO_MOD);
				}
				else
				{
					helper.aplica_descuento_total_lineal_monto(formulario);
					helper.tarifica_Pedido(formulario);
				}
			/*}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_DESCUENTO_NO_ACUMULABLE);
			}*/
			
			//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
			return formulario;
		}
		
		if (Constantes.STRING_CAMBIO_CONVENIO.equals(formulario.getAccion())) {
			
			if (helper.CompruebaPagos(formulario)) {
				formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
			}
			else
			{
				//if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
					if (helper.verificaConvenioCliente(formulario)) {
						helper.actualizaProductosPorConvenio(formulario,local);	
						helper.tarifica_Pedido(formulario);
					}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_CONVENIO_CLIENTE);
						formulario.setConvenio(Constantes.STRING_BLANCO);
						formulario.setConvenio_det(Constantes.STRING_BLANCO);
						formulario.setIsapre(Constantes.STRING_BLANCO);
					}
				//}
				//else
				//{
				//	formulario.setError(Constantes.STRING_ERROR_SIN_PROD_CONVE);
				//	formulario.setConvenio(Constantes.STRING_BLANCO);
				//	formulario.setConvenio_det(Constantes.STRING_BLANCO);
				//}
			}
			
		}
		
		
		
		if (Constantes.STRING_MODIFICA_FECHA.equals(formulario.getAccion()))
		{
			helper.validaAperturaCaja(formulario, local);
		}

		if (Constantes.STRING_CANTIDAD.equals(formulario.getAccion())) 
		{
						
			int index = Integer.parseInt(formulario.getAddProducto());
			int cantidad = formulario.getCantidad_linea();
							
			helper.modificaCantidad(formulario, index, cantidad);
			//session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
			
			helper.actualizaProductosPorConvenio(formulario,local);	
			
			helper.tarifica_Pedido(formulario);
			
			//comprueba si tiene precio especial
			Utils util = new Utils();
			
			if (util.verificaPrecioEspecial(formulario.getListaProductos().get(index), formulario.getFecha())) 
			{
				session.setAttribute(Constantes.STRING_PRODUCTO, index);
				formulario.setEstado(Constantes.STRING_PRODUCTO_PRECIO_ESPECIAL);
			}
			else
			{
				formulario.getListaProductos().set(index, util.eliminaPrecioEspecial(formulario.getListaProductos().get(index)));
			}
			
			helper.tarifica_Pedido(formulario);
			
			//return mapping.findForward(Constantes.FORWARD_PEDIDO);
			return formulario;
		}
		if (Constantes.STRING_VOLVER.equals(formulario.getAccion())) {
			helper.totalizaPedido(formulario, helper.traePagos(formulario));
			if (formulario.getTiene_pagos().equals(Constantes.STRING_FALSE)) {
				session.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, Constantes.STRING_BLANCO);
			}
			if (formulario.getPagadoTotal().equals(Constantes.STRING_FALSE)) {
				formulario.setListaProductos(helper.ExtraeProductosAdicionales(formulario.getListaProductos()));
			}
		}
		if (Constantes.STRING_GRUPO.equals(formulario.getAccion())) {
			
			int index = Integer.parseInt(formulario.getAddProducto());
			String [] indice = formulario.getGrupo();
			
			helper.modificaGrupo(formulario, index, indice[index]);
		}
		if (Constantes.STRING_AGREGA_DESCRIPCION.equals(formulario.getAccion())) {
			
			int index = Integer.parseInt(formulario.getAddProducto());
			helper.agregaDescripcion(formulario, index);
		}
		if (Constantes.STRING_AGREGAR_PRODUCTOS.equals(formulario.getAccion())) {
			
			if (helper.CompruebaPagos(formulario)) 
			{
				formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
			}
			else
			{
				formulario.setGraduacion((GraduacionesBean)session.getAttribute(Constantes.STRING_GRADUACION));
				formulario.setGraduacion_lentilla((ContactologiaBean)session.getAttribute(Constantes.STRING_GRADUACION_LENTILLA));
				
				formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
				formulario.setListaProductos(helper.actualizaProductos(formulario, null, formulario.getCantidad() ,formulario.getListaProductos(), 
							local, Constantes.STRING_PEDIDO, formulario.getAddProducto(), formulario.getGraduacion(), formulario.getOjo(), formulario.getDescripcion(), session));
				session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
				
				/*System.out.println("Paso a Ingresa Venta Pedido Guarda session");
				session.setAttribute("se_encargo_padre", formulario.getEncargo_padre());
				System.out.println("Pruebo La salida por session ===>"+session.getAttribute("se_encargo_padre"));
				formulario.setEncargo_padre(session.getAttribute("se_encargo_padre").toString());*/
				
				
				if (null == formulario.getListaProductos()) {
					formulario.setCantidadProductos(0);
				}
				else
				{
					formulario.setCantidadProductos(formulario.getListaProductos().size());
				}				
				helper.tarifica_Pedido(formulario);
			}
			
		}
		if (Constantes.STRING_AGREGAR_CLIENTES.equals(formulario.getAccion())) {
			session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());			
			helper.traeUltimaGraduacionCliente(formulario);
			helper.traeUltimaGraduacionContactologiaCliente(formulario);
			session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
			session.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, formulario.getGraduacion_lentilla());
			//return mapping.findForward(Constantes.FORWARD_PEDIDO);
			return formulario;
		}
		if (Constantes.STRING_ELIMINAR_PRODUCTO.equals(formulario.getAccion()))
		{
			if (helper.CompruebaPagos(formulario)) 
			{
				formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
			}
			else
			{
				formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
				formulario.setListaProductos(helper.eliminarProductos(formulario.getAddProducto(), formulario.getListaProductos(), formulario));
				session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, formulario.getListaProductos());
				helper.tarifica_Pedido(formulario); 
				formulario.setCantidadProductos(formulario.getListaProductos().size());
			}
			
		}
		if (Constantes.STRING_ELIMINAR_PRODUCTO_MULTI.equals(formulario.getAccion()))
		{
			if (helper.CompruebaPagos(formulario)) 
			{
				formulario.setError(Constantes.STRING_TEXTO_ERROR_PAGO_VTA_PEDIDO);
			}
			else
			{
				formulario.setListaProductos((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS));
				formulario.setListaProductos(helper.eliminarProductosMulti(formulario.getAddProducto(), formulario.getListaProductos(), formulario, session));
				session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, formulario.getListaProductos());
				helper.tarifica_Pedido(formulario);
				formulario.setCantidadProductos(formulario.getListaProductos().size());
			}
			
		}
		if (Constantes.STRING_AGREGAR_SUPLEMENTOS.equals(formulario.getAccion())) {
			helper.agregaSuplementosProducto(formulario, session);
		}
		if (Constantes.STRING_VER_SUPLEMENTOS.equals(formulario.getAccion())) {
			helper.verSuplementosProducto(formulario, session);
		}
		if (Constantes.STRING_INGRESA_PEDIDO.equals(formulario.getAccion())) 
		{
			if (helper.validaCaja(local, formulario.getFecha())) 
			{
				//valida convenio valido
				if (helper.valida_convenio_valido(formulario)) {
					if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
						ArrayList<PagoBean> pagos_anteriores = helper.traePagos(formulario);
						if (null == pagos_anteriores || pagos_anteriores.size()==0) {
							if (formulario.getTotal()==0) 
							{
								helper.ingresaGrupos(formulario);
								helper.agrupa_valida_trios(formulario, session);
								if (formulario.getEstado().equals(Constantes.STRING_GENERA_COBRO)) {
									
									//LMARIN 20170822
									
										formulario.setEstado(Constantes.STRING_GUARDADO);
										formulario.setFlujo(Constantes.STRING_MODIFICAR);
										formulario.setEstaGrabado(1);
										session.setAttribute("venta_Seguro",formulario.getVenta_seguro());
										this.actualizaPedido(formulario, local, session);
									
								}
								//helper.gruposLentillas(formulario.getCdg());
							}
							else
							{
								//LMARIN 20170822 DTO SEG 
								if(formulario.getTipo_pedido().equals("SEG")){
									session.setAttribute("TIPO_PEDIDO", "SEG");
									//helper.aplica_descuento_seguro(formulario);
									helper.tarifica_Pedido(formulario);
								}	
						
								formulario.setEstado(Constantes.STRING_GUARDADO);
								formulario.setFlujo(Constantes.STRING_MODIFICAR);
								formulario.setEstaGrabado(1);
								this.actualizaPedido(formulario, local, session);
								helper.gruposLentillas(formulario.getCdg());
							}
						}
						else
						{
							formulario.setError(Constantes.STRING_ERROR_MODIFICACION_ENCARGO);
						}
					}
					else
					{
						formulario.setError(Constantes.STRING_ERROR_SIN_PROD_PEDIDO);
					}
				}
				else
				{
					formulario.setConvenio(Constantes.STRING_BLANCO);
					formulario.setConvenio_det(Constantes.STRING_BLANCO);
					formulario.setIsapre(Constantes.STRING_BLANCO);
					formulario.setConvenio_ln(0);
					formulario.setError(Constantes.STRING_ERROR_CONVENIO_NO_VALIDO);
				}
				
			}
			else
			{
				formulario.setError(Constantes.STRING_ERROR_VALIDA_CAJA);
			}
		}
		if (Constantes.STRING_VALIDA_PEDIDO.equals(formulario.getAccion())) {
			
			//se graba el pedido definitivo
			if (!Constantes.STRING_ACTION_BLOQUEA.equals(formulario.getBloquea())) {
				ArrayList<PagoBean> pagos_anteriores = helper.traePagos(formulario);
				if (null == pagos_anteriores || pagos_anteriores.size()==0) {
					this.actualizaPedido(formulario, local, session);
					String cdg = formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo();
					helper.cargaPedidoAnterior(formulario, local, cdg, session);
				}
			}
			//la condicion se ingreso para solucionar las validaciones de encargos anteriores a la fecha 
			Utils util = new Utils();
			if (util.formatoFechaCh(formulario.getFecha()).after(util.formatoFechaCh("14/11/2012"))) {
				helper.ingresaGrupos(formulario);
				helper.agrupa_valida_trios(formulario, session);
				session.setAttribute(Constantes.STRING_FECHA_ENTREGA, formulario.getFecha_entrega());
				if (formulario.getEstado().equals(Constantes.STRING_GENERA_COBRO)) {
					if (helper.validaProductosMultiofertaBD(formulario.getListaProductos(), formulario.getCdg(), "P"))
			    	{
						formulario.setEstado(Constantes.STRING_GENERA_COBRO);
			    	}
			    	else
			    	{
			    		formulario.setEstado(Constantes.STRING_VENTA);
			    		formulario.setError("Ocurrio un problema al grabar la multioferta, intentelo nuevamente.");
			    	}
				}
				
			}
			else
			{
				if (helper.validaProductosMultiofertaBD(formulario.getListaProductos(), formulario.getCdg(), "P"))
		    	{
					formulario.setEstado(Constantes.STRING_GENERA_COBRO);
		    	}
		    	else
		    	{
		    		formulario.setEstado(Constantes.STRING_VENTA);
		    		formulario.setError("Ocurrio un problema al grabar la multioferta, intentelo nuevamente.");
		    	}
			}
			
			
		}	
		if (Constantes.STRING_PAGO_EXITOSO.equals(formulario.getAccion())) {
			ArrayList<ProductosBean> listaProductosAdicionales = new  ArrayList<ProductosBean>();
			listaProductosAdicionales =	(ArrayList<ProductosBean>) session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES);
			ArrayList<PagoBean> listaPagos = new ArrayList<PagoBean>();
			listaPagos = (ArrayList<PagoBean>)session.getAttribute(Constantes.STRING_LISTA_PAGOS);
			helper.totalizaPedido(formulario, listaPagos);
			
			if (!Constantes.STRING_ACTION_BLOQUEA.equals(formulario.getBloquea()) && formulario.getTotalPendiante() == 0) {
				if (listaProductosAdicionales.size() > 0) {
					helper.ingresaPedidoLineaAdicionales(formulario, local, listaProductosAdicionales);
					formulario.setListaProductos(helper.agregaProductosGratuitos(listaProductosAdicionales, formulario.getListaProductos()));
				}
			}
			//se comenta grabar encargo al momento de guardar el pago, se cambioa al generar pedido
			/*if (!Constantes.STRING_ACTION_BLOQUEA.equals(formulario.getBloquea())) {
				//verifica si tiene pagos anteriores
				ArrayList<PagoBean> pagos_anteriores = helper.traePagos(formulario);
				if (null == pagos_anteriores || pagos_anteriores.size()==0) {
					
					this.actualizaPedido(formulario, local, session);
				}
				
			}*/
			
			if (formulario.getError().equals(Constantes.STRING_ERROR)) {
				
				/*
				* LMARIN 20150107
				* SE MODIFICA NUMERO DE DOCUMENTOS , YA QUE NO SE INGRESA SE SETEA PARA QUE NO SE CAIGA POR NULL
				*/				
				
				Utils utils = new Utils();
				String res="",out="";
				
				String dtocl = (session.getAttribute("DTOWEB").toString() != null && !session.getAttribute("DTOWEB").toString().equals(""))?session.getAttribute("DTOWEB").toString():"9-0";
				String [] dtoweb =  dtocl.split("-");
				String rutdto =""; 
				System.out.println("DTOWEB ==> "+ session.getAttribute("DTOWEB").toString() );
				if(dtoweb[0].equals("2")){
					rutdto = dtoweb[1];
				}else{
					rutdto = "0";
				}
				
				res = helper.ingresaDocumento(session.getAttribute(Constantes.STRING_TICKET).toString(),
											Integer.parseInt(session.getAttribute(Constantes.STRING_DOCUMENTO).toString()),
											session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString(),
											listaPagos,
											utils.formatoFecha(utils.traeFecha()),
											local, formulario,session.getAttribute("N_ISAPRE").toString(),rutdto);	
				
				String [] folio = res.split("_");
				
				//GENERA BOLETA
				SeleccionPagoForm spagoform = (SeleccionPagoForm)session.getAttribute("SeleccionPagoForm");				
			    if(session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString().trim().equals("B")){
					if(!folio[0].equals("0")){	
				    	spagoform.setNumero_boleta(Integer.parseInt(folio[1]));
				    	out = helper.genera_datos_belec("BOLETA-1", spagoform, folio[1], session);
				    	res = res +"_"+out;	
				    	
				    	formulario.setEstado_boleta(res);
						
						//GRABA PAGO
						String agenteTemporal = session.getAttribute(Constantes.STRING_USUARIO).toString();
						session.setAttribute(Constantes.STRING_USUARIO,agentePago);
						helper.ingresaPago(listaPagos, session, formulario, local);
						
						if(local.equals("S035") || local.equals("S064")){
							try {
								System.out.println("PASO POR GENERA XML");
								utils.generaXMLAeropuerto(spagoform, folio[1], session);
							    log.warn("generaXMLAeropuerto  PEDIDO ==> "+local);
							  
							}catch(Exception e) {
								System.out.println("Se cae generacion XML ==>"+e.getMessage().toString());
				            	log.error("generaXMLAeropuerto error", e);
							}
						}
						session.setAttribute(Constantes.STRING_USUARIO,agenteTemporal);
						
						session.setAttribute("DTOWEB","0");
						
						formulario.setEstado(Constantes.STRING_GUARDADO);
						
						helper.valida_estado_suplementos(formulario, session);
						helper.CompruebaPagos(formulario);		
						
						//LMARIN 20171010 / GENERACION ARCHIVO LIBERACION AUTOMATICO.
						System.out.println("PASO helper.validaLibau("+local+") =>"+helper.validaLibau(local));
						if(helper.validaLibau(local).equals("S")){
					
							System.out.println("PASO LIBAU 111");
							SalidaArchivo salida = new SalidaArchivo();
					
							try{
								System.out.println("Grupo MAX ==> "+formulario.getGrupo_max()+" <====> "+formulario.getIndex_multi());
	
								for(int i = 1;i <= formulario.getGrupo_max();i++){
									salida.creaArchivoLiberacionnew(formulario.getCliente(),formulario.getCdg(),String.valueOf(i));
								}
							}catch(Exception e){
								System.out.println("No se genera archivo de liberacion automatico"+e.toString());
							}
						}
					}else{
				    	res = "0_1_TRUE";
				    	formulario.setEstado_boleta(res);
					}
			   
			    }    
			    
				
			}
			//SE CORRIGE PROBLEMA CONVENIOS
			if(session.getAttribute(Constantes.STRING_TIPO_DOCUMENTO).toString().equals("G")){
				helper.ingresaPago(listaPagos, session, formulario, local);
			}
			helper.gruposLentillas(formulario.getCdg());

			//return mapping.findForward(Constantes.FORWARD_PEDIDO);
			return formulario;
			
		}
		if (Constantes.STRING_ACTION_CARGA_PEDIDO_SELECCION.equals(formulario.getAccion())) 
		{
			String cdg = (String) request.getAttribute(Constantes.STRING_ACTION_CDG);
			session.setAttribute(Constantes.STRING_ESTADO_FORM_SUPLEMENTOS, Constantes.STRING_BLANCO);
			helper.cargaPedidoAnterior(formulario, local, cdg, session);
			if (!local.equals(formulario.getLocal())) {
				formulario.setOtra_tienda(Constantes.STRING_TRUE);
			}
			else
			{
				formulario.setOtra_tienda(Constantes.STRING_FALSE);
			}
			agentePago = formulario.getAgente();
			session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
			session.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, formulario.getGraduacion_lentilla());
			session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());
			//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
			return formulario;
		}
		if (Constantes.STRING_ACTION_ELIMINAR_PEDIDO_SELECCION.equals(formulario.getAccion())) 
		{
			//LMARIN 20140714			
			if(helper.CompruebaLiberacion(formulario).equals("ERROR_FECHA")){	
				formulario.setError("No se puede eliminar un encargo cobrado ya liberado con fecha anterior(1).");	
			}else if(helper.CompruebaLiberacion(formulario).equals("ERROR_LIBERACION")){
				formulario.setError("No se puede eliminar un encargo cobrado ya liberado con fecha anterior(2)");
			}else{
				if (helper.CompruebaPagos(formulario)) 
				{
					formulario.setError(Constantes.STRING_TEXTO_ERROR_ELIMINAR_PAGO_VTA_PEDIDO);
				}
				else
				{
					if (!formulario.getBloquea().equals(Constantes.STRING_ACTION_BLOQUEA)) 
					{
						ArrayList<PagoBean> listaPagos = (ArrayList<PagoBean>)session.getAttribute(Constantes.STRING_LISTA_PAGOS);
						//String codigoPendiente =(String) request.getParameter(Constantes.STRING_ACTION_CDG);
						String codigoPendiente = formulario.getCodigo_suc()+"/"+formulario.getCodigo();
							if(null==listaPagos|| listaPagos.size()<1){
								boolean isDelete = 	helper.eliminarPedido(codigoPendiente);
								if(!(isDelete)){
									formulario.setEliminarPedid(Constantes.STRING_ACTION_NOOKSP);
								}else{
									formulario.setFlujo(Constantes.STRING_FORMULARIO);
									this.CargaFormulario(formulario, request);
								}  
							}else{
								formulario.setEliminarPedid(Constantes.STRING_ACTION_NOK);
							}
					}
					else
					{
						formulario.setError(Constantes.STRING_TEXTO_ERROR_ELIMINAR_LIBERADO);
					}
		
				}
			}
		}
		if (Constantes.STRING_ACTION_PEDIDO_ENTREGA.equals(formulario.getAccion())) 
		{
			String codigoPedido =formulario.getCodigo_suc()+"/"+formulario.getCodigo();
			if (helper.entregaPedido(codigoPedido, local, formulario)) {
				
				session.setAttribute(Constantes.STRING_CDG, formulario.getCodigo_confirmacion());
				//return mapping.findForward(Constantes.FORWARD_ENTREGA);
				return formulario;
			}
			
		}
		
		//VALIDA CUPON
		if (Constantes.STRING_ACTION_APLICA_CUPON.equals(formulario.getAccion())) 
		{
			helper.actualizaProductosPorCupon(formulario,local);	
			helper.tarifica_Pedido(formulario);
		}
		
		//PROMO COMBO 20180227
		if (Constantes.STRING_ACTION_APLICA_PROMOCOMBO.equals(formulario.getAccion())) 
		{
			helper.actualizaProductosPromoCombo(formulario,local);	
			helper.tarifica_Pedido(formulario);
		}
		
		//PROMO COMBO 20180325
		if (Constantes.STRING_ACTION_APLICA_PROMOPAR.equals(formulario.getAccion())) 
		{
			String [] varpromopar = formulario.getNumero_cupon().split("_");
			System.out.println("varpromopar ==> "+String.valueOf(varpromopar[1].charAt(0))+"<==>"+String.valueOf(varpromopar[1].charAt(1))+"<==>"+varpromopar[0]);
			helper.actualizaProductosPromoPar(formulario,String.valueOf(varpromopar[1].charAt(0)),String.valueOf(varpromopar[1].charAt(1)),varpromopar[0]);	
			helper.tarifica_Pedido(formulario);
		}
		//LIBERACION DE GARANTIAS
		if (Constantes.STRING_LIBERA_GARANTIAS.equals(formulario.getAccion())) 
		{
			
			 //LMARIN 20171010 / GENERACION ARCHIVO LIBERACION AUTOMATICO.
			  System.out.println("PASO helper.validaLibau("+local+") =>"+helper.validaLibau(local));
			  if(helper.validaLibau(local).equals("S")){
			
					System.out.println("PASO garantiaLib LIBAU 111");
					SalidaArchivo salida = new SalidaArchivo();
			
					try{
						System.out.println("Grupo MAX ==> "+formulario.getGrupo_max()+" <====> "+formulario.getIndex_multi());

						for(int i = 1;i <= formulario.getGrupo_max();i++){
							salida.creaArchivoLiberacionnew(formulario.getCliente(),formulario.getCdg(),String.valueOf(i));
						}
					}catch(Exception e){
						System.out.println("No se genera archivo de liberacion automatico"+e.toString());
					}
				}	
			
		}
		
		
		log.info("VentaPedidoDispatchActions:IngresaVentaPedido  fin");
		//return mapping.findForward(Constantes.FORWARD_PEDIDO);
		return formulario;
	}
	
	public void carga_confirmacion()
	{
		System.out.println("Paso 2 VP");
		//return mapping.findForward(Constantes.FORWARD_CONFIRMACION_PRODUCTO);
		return;
	}
	
	public VentaPedidoForm carga_adicionales_arcli(VentaPedidoForm form, Session request)
	{
		System.out.println("Paso 3 VP");
		VentaPedidoForm formulario = (VentaPedidoForm)form;
		Integer indice = Integer.parseInt(request.getAttribute("indice").toString());
		
		helper.carga_formulario_adicionales_arcli(formulario, indice);
		formulario.setEstaGrabado(2);
		//return mapping.findForward(Constantes.FORWARD_CONFIRMACION_ARCLI);
		return formulario;
		
	}
	

	public void actualizaPedido(VentaPedidoForm formulario, String local, Session session) {
		System.out.println("Paso 4 VP");
			log.info("VentaPedidoDispatchActions:actualizaPedido  inicio");
			if (!formulario.getBloquea().equals(Constantes.STRING_ACTION_BLOQUEA)) 
			{
					formulario.setEstaGrabado(2);
					formulario.setFecha_entrega(helper.traeFechaEntrega(formulario.getListaProductos(), local, formulario.getFecha(), formulario.getFecha_entrega(), formulario, session));
					
					
					/*Se define el encargo padre relacionado al encargo actual*/
					formulario.setEncargo_padre(session.getAttribute("se_encargo_padre").toString());	
					formulario.setNumero_cupon(session.getAttribute("se_cupon").toString());	
					helper.ingresaPedido(formulario, local);
										
					agentePago = formulario.getAgente();
					boolean hay_multioferta = false;
					hay_multioferta = helper.ingresaPedidoLinea(formulario, local);
					if (hay_multioferta)
					{
						if (formulario.getError().equals(Constantes.STRING_ERROR)) {
							ArrayList<ProductosBean> listaProdMultiOferta = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
							helper.ingresaDetalleMultiofertas(formulario.getListaProductos(), local, formulario, listaProdMultiOferta);
						}		

					}

				
			}
			log.info("VentaPedidoDispatchActions:actualizaPedido  fin");
	}
	
	public VentaPedidoForm CargaFormulario(VentaPedidoForm form, Session request)
	{
		System.out.println("Paso 5 VP");
		log.info("VentaPedidoDispatchActions:CargaFormulario  inicio");
		VentaPedidoForm formulario = (VentaPedidoForm)form;
		formulario.setEstaGrabado(0);
		Session session = request;
		session.removeAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.cleanForm();
		this.cargaInicial(formulario, local, session);
		helper.limpiaPreliminar(formulario, session);
		formulario.setGraduacion(new GraduacionesBean());
		formulario.setBloquea(Constantes.STRING_ACTION_BLOQUEA);
		formulario.setFlujo(Constantes.STRING_FORMULARIO);
		helper.limpiaCliente(formulario);
		log.info("VentaPedidoDispatchActions:CargaFormulario  fin");
		formulario.setDesde_presupuesto(Constantes.STRING_FALSE);
		formulario.setEstado_boleta("-1");
		//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
		return formulario;
	}

	public VentaPedidoForm nuevoFormulario(VentaPedidoForm form, Session request)
	{
		System.out.println("Paso 6 VP");
		log.info("VentaPedidoDispatchActions:nuevoFormulario  inicio");
		VentaPedidoForm formulario = (VentaPedidoForm)form;
		Session session = request;
		formulario.setEstaGrabado(2);
		formulario.setEstado_boleta("-1");
		formulario.setNumero_cupon("");
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		String nif=formulario.getNif();
		String dvnif = formulario.getDvnif();
		String codigo_cliente = formulario.getCliente();
		String nombre_cliente = formulario.getNombre_cliente();
		formulario.cleanForm();
		helper.limpiaPreliminar(formulario, session);
		helper.preCarga(formulario, local);
		this.cargaInicial(formulario, local, session);
		formulario.setAgente(Constantes.STRING_CERO);
		
		formulario.setNif(nif);
		formulario.setDvnif(dvnif);
		formulario.setCliente(codigo_cliente);
		formulario.setNombre_cliente(nombre_cliente);
		
		//se elimina la opcion de que grabe al inicio el encargo, ya que se almacena posteriormente
		//helper.ingresaPedido(formulario, local);
		formulario.setDesde_presupuesto(Constantes.STRING_FALSE);
		formulario.setTiene_pagos(Constantes.STRING_FALSE);
		formulario.setFlujo(Constantes.STRING_NUEVO);
		log.info("VentaPedidoDispatchActions:nuevoFormulario  fin");
		//return mapping.findForward(Constantes.FORWARD_PEDIDO);
		return formulario;
	}
	
	public VentaPedidoForm cargaPedidoAnterior(VentaPedidoForm form, Session request)
	{
		System.out.println("Paso 7 VP");
		log.info("VentaPedidoDispatchActions:cargaPedidoAnterior  inicio");
		VentaPedidoForm formulario = (VentaPedidoForm)form;
		formulario.setEstaGrabado(2);
		Session session = request;
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		request.setAttribute(Constantes.STRING_ACTION_LISTA_PEDIDOS, helper.traePedidosPendientes(formulario.getCliente(),local));
		log.info("VentaPedidoDispatchActions:cargaPedidoAnterior  fin");
		//return mapping.findForward(Constantes.STRING_ACTION_PEDIDO_CLIENTE); 
		return formulario;
	}
	
	
	public VentaPedidoForm generaVentaPedido(VentaPedidoForm form, Session request)
    {  	
		System.out.println("Paso 8 VP");
		log.info("VentaPedidoDispatchActions:generaVentaPedido  inicio");
    	VentaPedidoForm formulario = (VentaPedidoForm)form;
    	formulario.setEstaGrabado(2);
    	//formulario.setSumaTotalFinal(formulario.getTotal());
    	Session session = request;
    	session.setAttribute(Constantes.STRING_TOTAL, (int)Math.floor(formulario.getTotal()));
    	session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());
    	session.setAttribute(Constantes.STRING_CLIENTE_VENTA, formulario.getCliente());
    	session.setAttribute(Constantes.STRING_TICKET, formulario.getCodigo_suc() + "/" + formulario.getCodigo());
    	session.setAttribute(Constantes.STRING_DIVISA, formulario.getDivisa()); 
		session.setAttribute(Constantes.STRING_CAMBIO, formulario.getCambio());
		session.setAttribute(Constantes.STRING_ESTADO_FORM, formulario.getBloquea()); 
		session.setAttribute(Constantes.STRING_TIPO_ALBARAN, formulario.getTipo_pedido());
		session.setAttribute(Constantes.STRING_ORIGEN, Constantes.STRING_PEDIDO);
		session.setAttribute(Constantes.STRING_LISTA_PAGOS, helper.traePagos(formulario));
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, formulario.getListaProductos());
		session.setAttribute(Constantes.STRING_PORCENTAJE_ANTICIPO, formulario.getPorcentaje_anticipo());
		session.setAttribute(Constantes.STRING_FECHA, formulario.getFecha());
		session.setAttribute(Constantes.STRING_CONVENIO, formulario.getConvenio());
		session.setAttribute(Constantes.STRING_FORMA_PAGO_ORIGEN, formulario.getForma_pago());
		session.setAttribute(Constantes.STRING_AGENTE, helper.traeNombreAgente(formulario.getAgente(), formulario.getListaAgentes()));
    	
    	//verifica y carga los estuches y gamuzas en una lista totalizada (independiente de la inicial)
    	ArrayList<ProductosBean> lista = new ArrayList<ProductosBean>();
    	lista = formulario.getListaProductos();
    	if (formulario.getBloquea().equals(Constantes.STRING_ACTION_BLOQUEA)) {
            session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, new ArrayList<ProductosBean>());

		}
    	else
    	{
    		System.out.println("local adicionales gratuitos==>"+session.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL).toString());
            session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, helper.traeProductosGratuitos(lista , session.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL).toString(), session.getAttribute(Constantes.STRING_SUCURSAL).toString()));

    	}
    	log.info("VentaPedidoDispatchActions:generaVentaPedido  fin");

    	//return mapping.findForward(Constantes.FORWARD_GENERA_VENTA);
    	return formulario;
    }
	
	public VentaPedidoForm IngresaVentaPedidoDesdePresupuesto(VentaPedidoForm form, Session request)
	{
		System.out.println("Paso 9 VP");
		VentaPedidoForm formulario = (VentaPedidoForm)form;
		Session session = request;
		formulario.setEstaGrabado(2);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		String cdg = session.getAttribute(Constantes.STRING_ACTION_CDG).toString();
		formulario.cleanForm();
		this.cargaInicial(formulario, local, session);
		helper.limpiaPreliminar(formulario, session);
		formulario.setGraduacion(new GraduacionesBean());
		helper.limpiaCliente(formulario);
		
		helper.cargaPedidoDesdePresupuesto(formulario, local, cdg, session);
		helper.validaProductosArcliDesdePresupuesto(formulario);
		
		//LMARIN 20150624 - DEFINO ESTADO DE BOLETA
		formulario.setEstado_boleta("-1");
		//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
		return formulario;
		
	}

	public VentaPedidoForm IngresaVentaPedidoGraduacion(VentaPedidoForm form, Session request)
    { 
		System.out.println("Paso 10 VP");
		log.info("VentaPedidoDispatchActions:IngresaVentaPedidoGraduacion  inicio");
		VentaPedidoForm formulario = (VentaPedidoForm)form;
		Session session = request;
		formulario.setEstaGrabado(2);
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, null);
		
		String cliente = formulario.getCliente();
		String nombre_cliente = formulario.getNombre_cliente();
		formulario.cleanForm();
		ClienteBean clienteObj = helper.traeClienteSeleccionado(null,cliente);
		if(null != clienteObj){
			formulario.setNif(clienteObj.getNif());
			formulario.setDvnif(clienteObj.getDvnif());
			formulario.setCliente(cliente);
			formulario.setNombre_cliente(nombre_cliente);
		}else{
			
			formulario.setNif("");
			formulario.setDvnif("");
			formulario.cleanForm();
			formulario.setCliente("");
			formulario.setNombre_cliente("");
		}
		
		helper.limpiaPreliminar(formulario, session);
		helper.preCarga(formulario, local);
		this.cargaInicial(formulario, local, session);
		
		//LMARIN 20150624 - DEFINO ESTADO DE BOLETA
		formulario.setEstado_boleta("-1");
		
		formulario.setGraduacion(new GraduacionesBean());
		
		//se elimina la opcion de que grabe al inicio el encargo, ya que se almacena posteriormente
		//helper.ingresaPedido(formulario, local);		
		formulario.setFlujo(Constantes.STRING_NUEVO);
		formulario.setDesde_presupuesto(Constantes.STRING_FALSE);
		formulario.setTiene_pagos(Constantes.STRING_FALSE);
		
		session.setAttribute(Constantes.STRING_CLIENTE, formulario.getCliente());
		helper.traeUltimaGraduacionCliente(formulario);
		helper.traeUltimaGraduacionContactologiaCliente(formulario);
		session.setAttribute(Constantes.STRING_GRADUACION, formulario.getGraduacion());
		session.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, formulario.getGraduacion_lentilla());
		
		
		log.info("VentaPedidoDispatchActions:IngresaVentaPedidoGraduacion  fin");		
		//return mapping.findForward(Constantes.FORWARD_PEDIDO); 	
		return formulario;
    }
	
	
	
	 public String validaTrioMultioferta(Session request){
		 
			System.out.println("Paso 11 VP");
	    	Session session = request;
	    	String local = String.valueOf(session.getAttribute(Constantes.STRING_SUCURSAL));    		
			Utils util = new Utils();
			String retVal;
  
        	ArrayList<ProductosBean> listaProductosMultiofertaAux = new ArrayList<ProductosBean>();
        	ArrayList<ProductosBean> listaProductosMultiofertas = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS);
        	ArrayList<ProductosBean> lista_total_productos = new ArrayList<ProductosBean>();
        	
        	int cantidadgrupos=0;
        	HashMap hm = new HashMap();
        	
        	if(null != listaProductosMultiofertas){
        		
        		listaProductosMultiofertaAux.addAll(listaProductosMultiofertas);
            	
            	if(null != listaProductosMultiofertaAux){
            		
            		lista_total_productos.addAll(listaProductosMultiofertaAux);
            		
            		for(ProductosBean pro : listaProductosMultiofertaAux){
            			int grupo=util.isEntero(pro.getGrupo());
            			if(grupo > 0){
            				cantidadgrupos++;            				
            			}
            			if(cantidadgrupos >= 3){
            				break;
            			}
            		}        		
            	}
        	}
        	
        	ArrayList<ProductosBean> listaProduc = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);
        	
        	for(ProductosBean pro : listaProduc )
        	{	
    			lista_total_productos.add(pro);
    		}
        	
        	//agregar validacion combinada de todos los productos
        	if (null != lista_total_productos && lista_total_productos.size()>0) {
        		for(ProductosBean pro : lista_total_productos){
        			int grupo=util.isEntero(pro.getGrupo());
        			if(grupo > 0){
        				cantidadgrupos++;            				
        			}
        			if(cantidadgrupos >= 3){
        				break;
        			}
        		} 
			}
        	
        	
        	
        	boolean respuestaContacto=false;
        	if(cantidadgrupos < 3 ){
        		
	        	if(null != listaProduc){        		
	        		for(ProductosBean pro : listaProduc ){	        			
	        			respuestaContacto = util.esLenteContactoGraduable(pro);
	        			if(respuestaContacto){
	        				cantidadgrupos=3;
	        				break;
	        			}
	        		}
	        	}
        	
        	}
        	
        	        	
        	if(cantidadgrupos >=3){
        		hm.put("tieneTrio", "ok");
        		retVal="ok";
        	}else{
        		hm.put("tieneTrio", "no");
        		retVal="no";
        	}			 
			//JSONObject json = JSONObject.fromObject(hm);
			//response.setHeader("X-JSON", json.toString());
			
	    	log.info("ClienteDispatchActions:ingresoCliente fin");
	    	//return mapping.findForward(Constantes.STRING_SUCCESS);
	    	return retVal;
	    }
	 
	  public BeanGlobal validaCantidadProductosMultiofertas(VentaPedidoForm form, Session request)
	  {
		  
		    BeanGlobal global = new BeanGlobal();
		    
			System.out.println("Paso 12 VP");
	    	Session session = request;
	    	BusquedaProductosHelper helper = new BusquedaProductosHelper();
	    	VentaPedidoForm formulario = (VentaPedidoForm) form;
	    	HashMap hm = new HashMap();
	    	boolean estado = true;
	      	formulario.setEstado_boleta("-1");
	    	ArrayList<ProductosBean> listaMultiofertas = new ArrayList<ProductosBean>();
	    	try{
	    		
	    		listaMultiofertas = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_MULTIOFERTAS);
	    		
	    		ArrayList<ProductosBean> listaProductosMultioferta = new ArrayList<ProductosBean>();
	        	listaProductosMultioferta.addAll((ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS_MULTIOFERTAS));
	        	int contador =0;
	        	 if(null != listaMultiofertas){
	        		
	        		 for (ProductosBean multi : listaMultiofertas){
	        			 contador=0;
	        			 ArrayList<TipoFamiliaBean> listaTipo_familias = helper.traeTipoFamilias("", multi.getCodigo());   
	        			 for(TipoFamiliaBean tfam : listaTipo_familias){
		        			 if(null != listaProductosMultioferta){
		        				 for (ProductosBean prodmulti : listaProductosMultioferta){
		        					 if(prodmulti.getCodigoMultioferta().equals(multi.getCodigo()) && prodmulti.getIndexRelMulti() == multi.getIndexMulti()){
		        						
		        						 if(tfam.getCodigo().equals(prodmulti.getTipoFamilia())){
		        		        				contador++;
		        		        		 }  
		 		        			}
		        				 }
		        			 }  
		        			 //validar si la contador es menor a la cantidad permitida de prodcutos segun la multioferta.
		        			 if(contador < tfam.getCantidad()){
		        				 hm.put("cantidad", "menor");
		        				 hm.put("codigoMulti", multi.getCodigo());
		        				 
		        				 global.setObj_1("menor");
		        				 global.setObj_2(multi.getCodigo());
		        				 
		        				 estado = false;
		        				 break;
		        			 }
	        			 
	        			 }
	        		 }
	        		 
	        	 } 
	        	 if(estado){
		        	 hm.put("cantidad", "ok");
					 hm.put("codigoMulti", "");
					 
					 global.setObj_1("ok");
					 global.setObj_2("");
	        	 }   	
	        	
	    	}catch(Exception ex){
	    		if (listaMultiofertas != null && listaMultiofertas.size()>0) {
	    			hm.put("cantidad", "menor");
					hm.put("codigoMulti", Constantes.STRING_BLANCO);
					
					global.setObj_1("menor");
   				 	global.setObj_2(Constantes.STRING_BLANCO);
				}
	    		
				 estado = false;
	    	}
	    	//JSONObject json = JSONObject.fromObject(hm);
			//response.setHeader("X-JSON", json.toString());
			//return mapping.findForward(Constantes.FORWARD_PEDIDO); 
	    	return global;
	    }
	
	  public VentaPedidoForm historial_encargo(VentaPedidoForm form, Session request ) throws Exception
	  {
		  VentaPedidoForm formulario = (VentaPedidoForm)form;
		  
		  if(Constantes.STRING_CARGA_GRUPOS.equals(formulario.getAccion())){			
			  formulario.setListaGrupos(helper.traeGruposEncargo(formulario.getEncargo_padre()));			
	  	  }
			
		  //return mapping.findForward(Constantes.STRING_HISTORIAL_ENCARGO);
		  return formulario;
	  }
	  
	  public int valida_ped_ex(VentaPedidoForm form,  Session request) throws Exception
	  {
		  VentaPedidoForm formulario = (VentaPedidoForm)form;
		  String r ="";
		  int estado = 0;
		  estado = helper.validExPed(formulario);
		 
		  //response.getWriter().print(estado);		
		  return estado;
	  }
	  
	  //validExTienda
	  public boolean validaTipoPedido(VentaPedidoForm form, Session request) throws Exception
	  {
		  VentaPedidoForm formulario = (VentaPedidoForm)form;
		  boolean estado = false;
		  estado = helper.validaTipoPedido(formulario);
		 
		  //response.getWriter().print(estado);		
		  return estado;
	  }
	  
	  /*
	   * Metodo que valida_tipo_convenio
	   */
	  public int  valida_tipo_convenio(VentaPedidoForm form, Session request) throws Exception
	  {
		  VentaPedidoForm formulario = (VentaPedidoForm)form;
		  int dto = 0;
		 
		  dto = helper.valida_tipo_convenio(formulario);
		  
		  //response.getWriter().print(dto);		
		  return dto;
	  }
	  
	  /*
	   *Metodo que valida permisos de modificacion de la forma de pago 
	   */
	  
	  public int  valida_permiso_mod_fpago(Session request) throws Exception
	  {
		  int valor = 0;
		  Session session = request;
		  
		  String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		  String usuario = request.getAttribute(Constantes.STRING_USUARIO).toString();
		  String pass = request.getAttribute(Constantes.STRING_PASS).toString();
		  
		  ArrayList<PagoBean> listaPagos = (ArrayList<PagoBean>) session.getAttribute(Constantes.STRING_LISTA_PAGOS);

		 
		  //System.out.println("local ==>"+local+"|| usuario ==>"+usuario+"|| pass ==>"+pass +"|| tipo pago "+listaPagos.get(0).getForma_pago()+"|| Numero de boleta => "+listaPagos.get(0).getDetalle_forma_pago());
		  
		  valor = helper.valida_permiso_mod_fpago(usuario,pass,local);
		  
		  //response.getWriter().print(valor);		
		  return valor;
	  }
	 
	  /*
	   * Metodo que trae dto en pesos de la multioferta  
	   */
	  
	  public int  trae_descuento_mofercombos(VentaPedidoForm form, Session request) throws Exception
	  {
		  VentaPedidoForm formulario = (VentaPedidoForm)form;
		  int res = 0;
		  
		  System.out.println("trae_descuento_mofercombos dispatchaction ==>"+formulario.getCdg_mofercombo());
		  
		  res = helper.valida_mofercombos(formulario);
		 
		  //response.getWriter().print(res);		
		  return res;
	    }
	  
	    /*
		*LMARIN TICKET DE CAMBIO 20150616 
		*
		*/
		public String imprime_ticket_cambio(SeleccionPagoForm form, Session request) throws Exception
	    {
		  Session session = request;
		  String tc= "";
		 
	     SeleccionPagoForm spagoform = (SeleccionPagoForm)session.getAttribute("SeleccionPagoForm");

	      spagoform.setBoleta_tienda(session.getAttribute(Constantes.STRING_SUCURSAL).toString());	  
	    
	      tc = helper.ticket_cambio(spagoform);		 
		  
		 //response.getWriter().print(tc);		
		  return tc;
		 		  
	    }	
		
		/**
		 * AUTHOR : LMARIN
		 * CONVENIO JJ LEC 
		 * FECHA 20151110
		 */
		public void encargo_padre() throws Exception
		  {
			  
			 /*if(Constantes.STRING_CARGA_GRUPOS.equals(formulario.getAccion())){			
				  formulario.setListaGrupos(helper.traeGruposEncargo(formulario.getEncargo_padre()));			
		  	  }*/
				
			  //return mapping.findForward(Constantes.STRING_ENCARGO_PADRE);
			  return;
		  }
		
		 /**
		  * AUTHOR : LMARIN
		  * CONVENIO JJ LEC 
		  * FECHA 20151110
		  */
		
		  public int validaPromoLec(VentaPedidoForm form, Session request) throws Exception
		  {
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  int estado = 0;
			  estado = helper.validaPromoLec(formulario);
			 
			  //response.getWriter().print(estado);		
			  return estado;
		  }
		  
		  /**
		  * @AUTHOR : LMARIN
		  * @DESCRIPCION: Metodo que valida el encarggo asociado en la venta seguro
		  * @FECHA 20170821
		  */
		  public int validaVentaSeguro(VentaPedidoForm form, Session request) throws Exception
		  {
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  int estado = 0;
			  System.out.println("formulario.getEncargo_garantia()==>)"+formulario.getEncargo_seguro());
			  estado = helper.validaVentaSeguro(formulario.getEncargo_seguro());
			  System.out.println("validaVentaSeguro Estado ==> "+estado);
			  //response.getWriter().print(estado);
			  
			  return estado;
		  }
		  
		  /**
		  * AUTHOR : LMARIN
		  * VALIDA CUPON
		  * FECHA 20180219
	      */
		 public String valida_cupon(VentaPedidoForm form,
		            Session request) throws Exception
	     {
			  
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  String estado = "";
			  System.out.println("formulario.getNumero_cupon()==>)"+formulario.getNumero_cupon().trim());
			  estado = helper.valida_cupon(formulario.getNumero_cupon().trim(),formulario.getNif(),formulario.getCdg());
			  System.out.println("validaCupon Estado ==> "+estado);
			  //response.getWriter().print(estado);		
			  return estado;
			  //return mapping.findForward(Constantes.STRING_ACTION_VALIDA__CUPON);
	     }
	 
		  /**
		  * AUTHOR : LMARIN
		  * VALIDA CUPON
		  * FECHA 20180219
	      */
		 public void abre_valida_cupon() throws Exception
	     {
			  //return mapping.findForward(Constantes.STRING_ACTION_VALIDA__CUPON);
			 return;
	     }
		 
		 /**
		  * AUTHOR : LMARIN
		  * VALIDA CUPON
		  * FECHA 20180501
	      */
		 public void abre_valida_usuario_vp() throws Exception
	     {
			  //return mapping.findForward(Constantes.STRING_ACTION_VALIDA__USUARIO_VP);
			  return;
			  
	     }
		 
		 
		 public int valida_encargo(VentaPedidoForm form, Session request) throws Exception
		 {
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  int estado = 0;
			  estado = helper.valida_encargo(formulario);
			  //response.getWriter().print(estado);		
			  return estado;
		 }
		 
		 public int valida_promocombo(VentaPedidoForm form, Session request) throws Exception
		 {
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  int estado = 0;
			  System.out.println("formulario.getNumero_cupon()==>)"+formulario.getNumero_cupon().trim());
			  estado = helper.valida_pcombo(formulario.getNumero_cupon());
			  System.out.println("validaCupon Estado ==> "+estado);
			  //response.getWriter().print(estado);	
			  
			  return estado;
		 }
		 
		 public String valida_promo_pares(VentaPedidoForm form, Session request) throws Exception
		 {
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  String estado = "";
			  System.out.println("formulario.getValor_comodin()==>)"+formulario.getValor_comodin().trim());
			  estado = helper.valida_promo_pares(formulario.getValor_comodin().trim());
			  System.out.println("validaCupon Estado ==> "+estado);
			  //response.getWriter().print(estado);	
			  
			  return estado;
		 }
		 
		 //LMARIN 20180419
		 public String valida_seg_cris(VentaPedidoForm form, Session request) throws Exception
		 {
			  Session session = request;
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString(), codigo="0";
			  
			  ArrayList<ProductosBean> prod = new ArrayList<ProductosBean>();
			  
			  System.out.println("formulario.getValor_comodin()==>)"+formulario.getSegCris().trim());
			  //2629000210002,izquierdo,lejos,4.75,-2.5,75
			  String [] res  = formulario.getSegCris().trim().split(",");
			  /*String ojo, String tipo_fam, String familia, String subfamilia, String grupo,
	           String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local,double cilindro,double esfera,int eje*/
			  prod = helper.validaCristal(res[1],"C","81T","null","null","","",res[0].replace(".", "+"),local,Double.parseDouble(res[3]), Double.parseDouble(res[4]), Integer.parseInt(res[5]));
			  
			  if(!prod.isEmpty()) {
				 for(ProductosBean p:prod) { 
				  codigo = p.getCod_barra();
				  System.out.println("validaCupon Estado ==> "+p.getCod_barra()+","+p.getCod_articulo()+","+p.getCodigo());
				 }
			  }
			  
			  System.out.println("validaCupon Estado ==> "+codigo);
			  //response.getWriter().print(codigo);		
			  return codigo;
		 }
		 
		 public String garantiaLib(VentaPedidoForm form, Session request) throws Exception
		 {
			  Session session = request;
			  VentaPedidoForm formulario = (VentaPedidoForm)form;
			  String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString(), codigo="0";
			  
			  String  res ="true";
			  
			  //LMARIN 20171010 / GENERACION ARCHIVO LIBERACION AUTOMATICO.
			  System.out.println("PASO helper.validaLibau("+local+") =>"+helper.validaLibau(local));
			  if(helper.validaLibau(local).equals("S")){
			
					System.out.println("PASO garantiaLib LIBAU 111");
					SalidaArchivo salida = new SalidaArchivo();
			
					try{
						System.out.println("Grupo MAX ==> "+formulario.getGrupo_max()+" <====> "+formulario.getIndex_multi());

						for(int i = 1;i <= formulario.getGrupo_max();i++){
							salida.creaArchivoLiberacionnew(formulario.getCliente(),formulario.getCdg(),String.valueOf(i));
						}
					}catch(Exception e){
						res = "false";
						System.out.println("No se genera archivo de liberacion automatico"+e.toString());
					}
				}	
			  
			  	//response.getWriter().print(res);		

				return res;
			  
		 }
		 
		
}
