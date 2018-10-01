package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.beans.PresupuestosBean;

public interface PresupuestoDAO {
	
	public PresupuestosBean traeGenericosFormulario(String local) throws Exception ;
	public int traeCodigoVenta(String local) throws Exception ;
	public String traeCodigo_Suc(String local) throws Exception ;

}
