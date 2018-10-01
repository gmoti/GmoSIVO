package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesBean;

public interface ListadoTrabajosPendientesDAO {

	public ListadosTrabajosPendientesBean traeListadosTrabajosPendientes(String codigo, String nifCliente, String agente, 
			String divisa, String fInicio, String fTermino, String fpago, String sucursal, String cerrado, String listadoDetallado, String tipoPedido,
			String anulado) throws Exception;
}
