package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ClienteDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.TipoViaBean;

public class PosClientesFacade {

	public static int traeCodigoCliente(int valRangoInicio, int valRangoFinal){
		
		int codigo_cliente =Constantes.INT_CERO;
		try{
			ClienteDAOImpl clienteDao = new ClienteDAOImpl();
			codigo_cliente = clienteDao.traeCodigoCliente(valRangoInicio, valRangoFinal);
			
			return codigo_cliente;
		}catch(Exception ex){
			return codigo_cliente;
		}
	}
	
	public static String traeCodigoLocal(String local){
		
		String codigoLocal ="";
		try{
			ClienteDAOImpl clienteDao = new ClienteDAOImpl();
			codigoLocal = clienteDao.traeCodigoLocal(local);
			
			return codigoLocal;
		}catch(Exception ex){
			return codigoLocal;
		}
		
	}
	
	public static ArrayList<ClienteBean> traeClientes(String nif,
			String nombre, String apellido, String codigo) {
		
		ArrayList<ClienteBean> listaClientes = new ArrayList<ClienteBean>();
		try {
			ClienteDAOImpl clienteDao = new ClienteDAOImpl();
			listaClientes = clienteDao.traeClientes(nif, nombre, apellido, codigo);
			
		} catch (Exception e) {
			e.printStackTrace();
			listaClientes = null;
		}
		return listaClientes;
	}
	
	public static ClienteBean traeCliente(String nif, String codigo)
	{
		ClienteBean cliente = new ClienteBean();
		try {
			ClienteDAOImpl clienteDao = new ClienteDAOImpl();
				cliente = clienteDao.traeCliente( nif,  codigo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return cliente;
	}
	
	public static ClienteBean traeClienteFact(String nif, String codigo)
	{
		ClienteBean cliente = new ClienteBean();
		try {
			ClienteDAOImpl clienteDao = new ClienteDAOImpl();
				cliente = clienteDao.traeClienteFacturable(nif, codigo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cliente;
	}


	public static ArrayList<AgenteBean> traeAgentes(String local)
    {
        ArrayList<AgenteBean> lista_Agentes = new ArrayList<AgenteBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Agentes = utiles.traeAgentes(local);
        } catch (Exception e) {
            e.printStackTrace();
            lista_Agentes = null;
        }
        return lista_Agentes;
    }
	
	public static ArrayList<TipoViaBean> traeTipoVias()
    {
        ArrayList<TipoViaBean> listaTipoVias = new ArrayList<TipoViaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            listaTipoVias = utiles.traeTipoVias();
        } catch (Exception e) {
            e.printStackTrace();
            listaTipoVias = null;
        }
        return listaTipoVias;
    }
	
	public static ArrayList<ProvinciaBean> traeProvincias()
    {
        ArrayList<ProvinciaBean> lista_provincias = new ArrayList<ProvinciaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_provincias = utiles.traeProvincias();
        } catch (Exception e) {
            e.printStackTrace();
            lista_provincias = null;
        }
        return lista_provincias;
    }
	
	public static ArrayList<ProvinciaBean> traeProvinciasdev()
    {
        ArrayList<ProvinciaBean> lista_provincias = new ArrayList<ProvinciaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_provincias = utiles.traeProvinciasdev();
        } catch (Exception e) {
            e.printStackTrace();
            lista_provincias = null;
        }
        return lista_provincias;
    }

	public static int ingresoCliente(ClienteBean cliente){
		
		int respuesta = Constantes.INT_CERO;
		try{
			
			ClienteDAOImpl clienteDao = new ClienteDAOImpl();
			respuesta = clienteDao.ingresoCliente(cliente);
			
		}catch(Exception ex){
			respuesta = -1;
		}
		return respuesta;
	}
}
