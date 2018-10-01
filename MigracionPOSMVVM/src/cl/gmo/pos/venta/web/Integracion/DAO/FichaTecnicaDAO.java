package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;

public interface FichaTecnicaDAO {
	
	public FichaTecnicaBean traeFichataller(String codigo_venta_pedido, int codigo_cliente);
		
	

}
