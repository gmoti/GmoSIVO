package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

public interface EntregaPedidoDAO {
	
	public ArrayList<VentaPedidoBean> buscarPedidos(String cliente, String fecha_pedido, String nombre)throws Exception;

}
