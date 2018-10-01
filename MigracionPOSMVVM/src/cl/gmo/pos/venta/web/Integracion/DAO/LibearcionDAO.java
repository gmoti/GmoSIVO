package cl.gmo.pos.venta.web.Integracion.DAO;

public interface LibearcionDAO {

	public boolean cambioEstadoLiberacion(String codigo_venta, String estado, String grupo, int identPedvtln, String articulo);
	
}
