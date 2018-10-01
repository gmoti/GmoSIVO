package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaDirectaBean;

public interface VentaDAO {

	public VentaDirectaBean traeNumerosCaja(String local) throws Exception;
	public int traeCodigoVenta(String codigo_sucursal) throws Exception;
	public String traeEncabezado_Ticket(String local) throws Exception;
	public void insertaVenta(VentaDirectaBean ventaDirecta, String local, String tipo_documento)throws Exception;
	public void ingresaDetalle(ProductosBean producto, int indice, String codigo_albaran, String local) throws Exception;
	public Boolean insertaPago(String codigo_venta, String forma_pago, int cantidad, String fecha,
			String divisa, int cambio, int caja, int cantidaddiv, String devolucion,
			String confidencial, String agente, String numero_bono, double descuento, String tipo_documento)throws Exception;
	public String insertaDocumento(String ticket, int documento,
			String tipo_documento, String fecha, int total, String fecha2,String local) throws Exception;
	public String insertaMultiofertaCB(ProductosBean producto, String codigo_venta, int linea,
										String fecha, int numero_venta, String local)throws Exception;
	public String insertaMultiofertaDetalle(int numero_multioferta, ProductosBean producto, int linea, String fecha,
												String local, String vta_codigo)throws Exception;

}
