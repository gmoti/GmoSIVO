package cl.gmo.pos.venta.web.facade;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.FichaTecnicaDAOImpl;
import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;

public class PosFichaTecnicaFacade {

	public static FichaTecnicaBean traeFichaTaller(String codigo_venta_pedido, int codigo_cliente){
		FichaTecnicaBean ficha = new FichaTecnicaBean();
		FichaTecnicaDAOImpl fdao = new FichaTecnicaDAOImpl();
		try{
			ficha = fdao.traeFichataller(codigo_venta_pedido, codigo_cliente);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return ficha;
	}
}
