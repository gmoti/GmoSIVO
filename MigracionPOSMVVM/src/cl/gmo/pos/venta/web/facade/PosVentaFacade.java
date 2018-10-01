package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;
import java.util.Date;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.InformeBusquedaProductoDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.InformeOpticoDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaDAOImpl;
import cl.gmo.pos.venta.web.beans.InformeBusquedaProductoBean;
import cl.gmo.pos.venta.web.beans.InformeOpticoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaDirectaBean;



/**
 *
 * @author Advise68
 */
public class PosVentaFacade {
	
	
	public static VentaDirectaBean traeDatosGenericosVentaDirecta(String local) throws Exception
	{
		VentaDirectaBean ventaBean = new VentaDirectaBean();
		try {
			VentaDAOImpl DAO = new VentaDAOImpl();
			ventaBean = DAO.traeDatosGenericosVentaDirecta(local);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return ventaBean;
	}
	public static String traeEncabezadoTicket(String local)throws Exception
	{
		String encabezado_ticket = Constantes.STRING_BLANCO;
		try {
			VentaDAOImpl DAO = new VentaDAOImpl();
			encabezado_ticket = DAO.traeEncabezado_Ticket(local);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return encabezado_ticket;
	}
	public static int traeNumeroTicket(String codigo_sucursal) throws Exception
	{
		int numero_ticket = Constantes.INT_CERO;
		try {
			VentaDAOImpl DAO = new VentaDAOImpl();
			numero_ticket = DAO.traeCodigoVenta(codigo_sucursal);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return numero_ticket;
		
	}
		 
	 public static VentaDirectaBean traeNumerosCaja(String local) throws Exception {
		 VentaDirectaBean ventaDirecta = new VentaDirectaBean();
		 
		 try{
		        VentaDAOImpl utilDao = new VentaDAOImpl();         
		        ventaDirecta = utilDao.traeNumerosCaja(local);
		    }catch (Exception e){
		        e.printStackTrace();
		    }
		 
		 
		 return ventaDirecta;
	 }
	 public static void insertaVenta(VentaDirectaBean ventaDirecta, String local, String tipo_documento) throws Exception
	 {
		try {
			VentaDAOImpl ventaDao = new VentaDAOImpl();
			ventaDao.insertaVenta(ventaDirecta, local, tipo_documento);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
	 }
	 public static void insertaDetalle(ProductosBean producto, int indice, String codigo_albaran, String local)
	 {
		 try {
			VentaDAOImpl ventaDao = new VentaDAOImpl();
			ventaDao.ingresaDetalle(producto, indice, codigo_albaran, local);
		} catch (Exception e) {
			 e.printStackTrace();
		}
	 }
	 public static boolean insertaPago(String codigo_venta, String forma_pago, int cantidad, String fecha,
				String divisa, int cambio, int caja, int cantidaddiv, String devolucion,
				String confidencial, String agente, String numero_bono, double descuento, String tipo_documento)throws Exception
	{
		 try {
			 VentaDAOImpl ventaDao = new VentaDAOImpl();
			return ventaDao.insertaPago(codigo_venta, forma_pago, cantidad, fecha, divisa, cambio, caja, cantidaddiv, devolucion, confidencial, agente, numero_bono, descuento, tipo_documento);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosVentaFacade: insertaPago");
		}
	}
	public static String insertaDocumento(String ticket, int documento,
			String tipo_documento, int total, String fecha,String local) throws Exception
		{
		String res ="";
		try {
			 VentaDAOImpl ventaDao = new VentaDAOImpl();
			 res = ventaDao.insertaDocumento(ticket, documento, tipo_documento, fecha, total, fecha,local);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosVentaFacade: insertaDocumento");
		}
		return res;
	}
	public static String insertaMultiofertaCB(ProductosBean producto, String codigo_venta, int linea, String fecha, int numero_venta, String local) throws Exception
	 {
		String mensaje = Constantes.STRING_BLANCO;
		try {
			VentaDAOImpl ventaDao = new VentaDAOImpl();
			mensaje = ventaDao.insertaMultiofertaCB(producto, codigo_venta, linea, fecha, numero_venta, local);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return mensaje;
	 }
	
	public static String insertaMultiofertaDetalle(int numero_multioferta ,ProductosBean producto, int linea, String fecha, String local, String vta_codigo) throws Exception
	 {
		String vta = Constantes.STRING_BLANCO;
		try {
			VentaDAOImpl ventaDao = new VentaDAOImpl();
			vta = ventaDao.insertaMultiofertaDetalle(numero_multioferta, producto, linea, fecha, local, vta_codigo);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return vta;
	 }
	public static ArrayList <InformeBusquedaProductoBean> traeInformeBusquedaProducto(String codigo, String descripcion) throws Exception
	 {
		ArrayList <InformeBusquedaProductoBean> productos = new ArrayList <InformeBusquedaProductoBean>();
		try {
			InformeBusquedaProductoDAOImpl informeBusquedaProductoDAO = new InformeBusquedaProductoDAOImpl();
			productos = informeBusquedaProductoDAO.traeProductos(codigo, descripcion);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return productos;
	 }
	
	public static ArrayList <InformeOpticoBean>  traeInformeOptico(String codigo, Date fecha, String numero) throws Exception
	 {
		ArrayList <InformeOpticoBean>  listaOptico = new ArrayList <InformeOpticoBean> ();
		try {
			InformeOpticoDAOImpl informeOpticoDAOImpl = new InformeOpticoDAOImpl();
			listaOptico = informeOpticoDAOImpl.traeGraduaciones(codigo, fecha, numero);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return listaOptico;
	 }
	
	 public static boolean insertaPagoAlbaran(String codigo_venta, String forma_pago, int cantidad, String fecha,
				String divisa, int cambio, int caja, int cantidaddiv, String devolucion,
				String confidencial, String agente, String numero_bono, double descuento, String tipo_documento)throws Exception
	{
		 try {
			 VentaDAOImpl ventaDao = new VentaDAOImpl();
			return ventaDao.insertaPagoAlbaran(codigo_venta, forma_pago, cantidad, fecha, divisa, cambio, caja, cantidaddiv, devolucion, confidencial, agente, numero_bono, descuento, tipo_documento);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosVentaFacade: insertaPago");
		}
	}
}
