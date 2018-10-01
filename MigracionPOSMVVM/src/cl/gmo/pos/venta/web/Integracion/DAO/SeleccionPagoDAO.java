package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.PagoBean;

public interface SeleccionPagoDAO {
	
	public String[] validaDocumento(String tipo_documento, String codigo_local, int numero_documento) throws Exception;
	public ArrayList<PagoBean> traePagos(String codigo_venta, String tipo)throws Exception;
}
