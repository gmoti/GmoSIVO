package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;


public interface VentaPedidoPendientesDAO {

	public ArrayList<PedidosPendientesBean> traePedidosPendientes(String codigoCliente, String sucursal, String pedido, String agente, String nif_cliente, String fecha) throws Exception;
	public PedidosPendientesBean traePedidosSeleccionado(String codigoPedido) throws Exception;
	public boolean eliminarPedido(String codigoCliente) throws Exception;
	public boolean pedidoEntrega(String codigoPedido, String sucursal,VentaPedidoForm form) throws Exception;
}
