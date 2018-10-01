package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.EntregaPedidoDAOImpl;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

public class PosEntregaPedidosFacade {

	public static ArrayList<VentaPedidoBean> buscarPedidos(String cliente, String fecha_pedido, String nombre){
		EntregaPedidoDAOImpl dao = new EntregaPedidoDAOImpl();
		ArrayList<VentaPedidoBean> lista_pedidos_entregados = new ArrayList<VentaPedidoBean>(); 
		try{
			
			lista_pedidos_entregados = dao.buscarPedidos(cliente, fecha_pedido, nombre);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return lista_pedidos_entregados;
	}
	
	
	public static VentaPedidoBean traeEntregaPedido(String cdg_pedido){
		EntregaPedidoDAOImpl dao = new EntregaPedidoDAOImpl();
		VentaPedidoBean vta = null;
		try{
				
			vta =  dao.traeEntregaPedido(cdg_pedido);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		return vta;
	}
}
