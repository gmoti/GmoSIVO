package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.beans.ListaPresupuestosBean;

public interface ListadoPresupuestosDAO {
	
	public ListaPresupuestosBean traeListadoPresupuestos(String sucursal, String nifCliente, String cerrado, String codigoPresupuesto, String codigoProducto, String fInicio, String fTermino, String Agente, String divisa, String fpago) throws Exception;

}
