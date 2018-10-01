package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.GraduacionesBean;

public interface GraduacionesDAO {
	
	public ArrayList<GraduacionesBean> traeGraduacionesCliente(String cliente) throws Exception;
	public GraduacionesBean traeUltimaGraduacionCliente(String cliente) throws Exception;

}
