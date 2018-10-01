package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;
import java.util.Date;

import cl.gmo.pos.venta.web.beans.InformeOpticoBean;

public interface InformeOpticoDAO {
	public ArrayList <InformeOpticoBean> traeGraduaciones(String codigo, Date fecha, String numero) throws Exception;
}
