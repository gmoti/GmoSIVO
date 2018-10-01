package cl.gmo.pos.venta.web.Integracion.DAO;

import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;

public interface CopiaGuiaBoletaDAO {
	public CopiaGuiaBoletaForm traeCopiaGuiaBoleta(String numeroDocumento, String tipoDocumento) throws Exception;
}
