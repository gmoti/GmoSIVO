package cl.gmo.pos.venta.web.Integracion.DAO;

public interface HistorialRequerimientosDAO {
	
	public int insertarHistorial(String familia,String subfamilia,String grupofamilia,String esfera,String cilindro)throws Exception;

}
