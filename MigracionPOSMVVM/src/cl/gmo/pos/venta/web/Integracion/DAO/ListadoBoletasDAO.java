package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.ListadoBoletasBean;


public interface ListadoBoletasDAO {
	public ArrayList<ListadoBoletasBean> traeListasBoletas(String fecha,String local, String numeroBoleta) throws Exception;

}
