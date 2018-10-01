package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.InformeBusquedaProductoBean;

public interface InformeBusquedaProductoDAO {
	public ArrayList <InformeBusquedaProductoBean> traeProductos(String codigo, String descripcion) throws Exception;
}
