package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.LiberacionDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoDAOImpl;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.ListaEstadosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

public class BusquedaLiberacionesFacade {
	
	public BusquedaLiberacionesFacade(){}
	
	
	public static ArrayList<VentaPedidoBean> traeDetalleLiberacionMulti(String codigo, String grupo){
		
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		VentaPedidoDAOImpl ventaDao = new VentaPedidoDAOImpl();
		
		lista_detalle_pedido = ventaDao.traeDetalleLiberacionMulti(codigo, grupo);
		
		return lista_detalle_pedido;
		
	}
	
	public static ArrayList<VentaPedidoBean> traeVentasPedidos(String local, String fecha, String fechaHasta,String estado){
		
		ArrayList<VentaPedidoBean> lista_ventas_pedido = new ArrayList<VentaPedidoBean>();
		VentaPedidoDAOImpl ventaDao = new VentaPedidoDAOImpl();
		
		lista_ventas_pedido = ventaDao.traeVentasPedidos(local, fecha, fechaHasta,estado);
		
		return lista_ventas_pedido;
		
	}
	
	public static ArrayList<VentaPedidoBean> traeDetalleVentasPedidos(String codigo, String grupo){
		
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		VentaPedidoDAOImpl ventaDao = new VentaPedidoDAOImpl();
		
		lista_detalle_pedido = ventaDao.traeDetalleVentasPedidos(codigo, grupo);
		
		return lista_detalle_pedido;
		
	}
	
	public static ArrayList<SuplementopedidoBean> traeSuplementosPedidos(String codigo) {
		ArrayList<SuplementopedidoBean> listaSuplementosPedidos = new ArrayList<SuplementopedidoBean>();
        try {
            VentaPedidoDAOImpl ventaPeridoimpl = new VentaPedidoDAOImpl();
            listaSuplementosPedidos = ventaPeridoimpl.traeSuplementosPedidos(codigo);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaSuplementosPedidos;
	}
	
	public static GraduacionesBean traeGraduacionPedido(String cliente, String fecha, double numero){
		
		GraduacionesBean graduacion = new GraduacionesBean();
		try {
            VentaPedidoDAOImpl ventaPeridoimpl = new VentaPedidoDAOImpl();
            graduacion = ventaPeridoimpl.traeGraduacionPedido(cliente, fecha, numero);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return graduacion;
	}
	
	public static boolean cambioEstadoLiberacion(String codigo_venta, String estado, String grupo, int identPedvtln, String articulo){
		LiberacionDAOImpl libera = new LiberacionDAOImpl();
		boolean respuesta = false;
		try{
			respuesta = libera.cambioEstadoLiberacion(codigo_venta, estado, grupo, identPedvtln, articulo);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return respuesta;
	}
	/*
	 * LMARIN 20131229
	 */
	public static ArrayList<VentaPedidoBean> trae_historial_encargo(String codigo_venta){
		UtilesDAOImpl udao = new UtilesDAOImpl();
		ArrayList<VentaPedidoBean> respuesta = new ArrayList<VentaPedidoBean>();
		try{
			respuesta = udao.trae_historial_encargo(codigo_venta);
			
		}catch(Exception ex){
			System.out.println("Mensaje de error =>"+ex.getMessage());
		}		
		return respuesta;
	}
	
	public static ArrayList<VentaPedidoBean> traeDetalleVentasPedidosAM(String codigo, String grupo){
		
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		UtilesDAOImpl udao = new UtilesDAOImpl();
		
		lista_detalle_pedido = udao.traeDetalleVentasPedidosAM(codigo, grupo);
		
		return lista_detalle_pedido;
		
	}
	public  static ArrayList<ListaEstadosBean> traeEstadosEncargo(){
	   	 ArrayList<ListaEstadosBean> lista_estado = new ArrayList<ListaEstadosBean>();
	        try {
	            UtilesDAOImpl utiles = new UtilesDAOImpl();
	            lista_estado = utiles.traeEstadosEncargo();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            lista_estado = null;
	        }
	        return lista_estado;
	 }

}
