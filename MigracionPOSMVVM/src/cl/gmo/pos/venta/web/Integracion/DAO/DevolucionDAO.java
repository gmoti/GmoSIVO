package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.beans.DevolucionBean;

public interface DevolucionDAO {
	public DevolucionBean traeDevolucion(String numeroDocumento, String tipoDocumento) throws Exception;
	public String  traeCodigoDevolucion(String local);
}
