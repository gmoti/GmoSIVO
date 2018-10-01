package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.OftalmologoBean;

public interface MedicoDao {

	public int ingresaMedico(OftalmologoBean medico);	
	
	public String traeCdgDoctor();
	
	public ArrayList<OftalmologoBean> traeDoctores(String codigo, String nif, String apellido, String nombre);
}
