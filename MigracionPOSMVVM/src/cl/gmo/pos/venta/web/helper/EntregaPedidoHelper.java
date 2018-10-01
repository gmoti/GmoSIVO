package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.facade.BusquedaLiberacionesFacade;
import cl.gmo.pos.venta.web.facade.PosEntregaPedidosFacade;
import cl.gmo.pos.venta.web.forms.EntregaPedidoForm;

public class EntregaPedidoHelper extends Utils {
	Logger log = Logger.getLogger( this.getClass() );
	public EntregaPedidoForm buscarPedidos(EntregaPedidoForm formulario){
		log.info("EntregaPedidoHelper:buscarPedidos inicio");
		ArrayList<VentaPedidoBean> lista_pedidos_entregados = new ArrayList<VentaPedidoBean>(); 
		try{
			String cliente = formulario.getCliente();
			String fecha_pedido = formulario.getFecha_pedido();
			String nombre = formulario.getNombre_cliente();
						
			lista_pedidos_entregados = PosEntregaPedidosFacade.buscarPedidos(cliente, fecha_pedido, nombre);
			formulario.setListaPedidos(lista_pedidos_entregados);	
			
			
		}catch(Exception ex){
			log.error("EntregaPedidoHelper:buscarPedidos error catch",ex);
		}	
		return formulario;
	}
	
	public EntregaPedidoForm traeEntregaPedido(EntregaPedidoForm formulario, HttpSession session){
		log.info("EntregaPedidoHelper:traeEntregaPedido inicio");
		VentaPedidoBean vta = null;
		try{
			String cdg_pedido = formulario.getCdg_busqueda();						
			vta = PosEntregaPedidosFacade.traeEntregaPedido(cdg_pedido);
			
			if(null != vta.getCdg()){
				String []codSuc = vta.getCdg().split(Constantes.STRING_SLASH);
				formulario.setCodigo_suc(codSuc[0]);
				formulario.setCodigo(codSuc[1]);
			}else{
				formulario.setCodigo_suc(Constantes.STRING_BLANCO);
				formulario.setCodigo(Constantes.STRING_BLANCO);
			}
			
			formulario.setCliente(vta.getCliente());
			formulario.setNombre_cliente(vta.getCliente_completo());
			formulario.setDivisa(vta.getDivisa());
			formulario.setFecha_pedido(vta.getFecha());
			formulario.setFecha_entrega(vta.getFecha_entrega());
			formulario.setForma_pago(vta.getForma_pago());
			formulario.setIdioma(vta.getIdioma());
			formulario.setHora(vta.getHora());
			formulario.setTipo_pedido(vta.getTipo_ped());
			formulario.setConvenio(vta.getConvenio());
			formulario.setSobre(vta.getNumero_sobre());
			formulario.setAgente(vta.getAgente());
			formulario.setCambio(String.valueOf(vta.getCambio()));	
			
			formulario.setListaProductos(new ArrayList<ProductosBean>());
			String fecha_graduacion = Constantes.STRING_BLANCO;
			int  numero_graduacion = Constantes.INT_CERO;
			
			if(null != vta.getLista_productos()){
				
				for(ProductosBean pro : vta.getLista_productos()){	
					//aplicaDescuentoConvenio(pro, formulario.getConvenio(),formulario.getForma_pago());					
					formulario.getListaProductos().add(pro);
					
					if(0 < pro.getNumero_graduacion()){
						numero_graduacion = pro.getNumero_graduacion();
					}
					
					if(null != pro.getFecha_graduacion()){
						fecha_graduacion = pro.getFecha_graduacion();
					}
					
				}
				
				this.traeDatosFormulario(formulario, session);
				this.tarifica_Pedido(formulario);
			}
			
			
			formulario.setGraduacion(BusquedaLiberacionesFacade.traeGraduacionPedido(formulario.getCliente(), fecha_graduacion, numero_graduacion));
			
						
		}catch(Exception ex){
			log.error("EntregaPedidoHelper:traeEntregaPedido error catch",ex);
		}	
		
		return formulario;
	}

}
