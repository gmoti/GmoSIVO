package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.ClienteBean;

public interface ClienteDAO {

	public ArrayList<ClienteBean> traeClientes(String nif, String nombre, String apellido, String codigo) throws Exception;
	
	public ClienteBean traeCliente(String nif, String codigo) throws Exception;
	
	public ClienteBean traeClienteFacturable(String nif, String codigo) throws Exception;

}
