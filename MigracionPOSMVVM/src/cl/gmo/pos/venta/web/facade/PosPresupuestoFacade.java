package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.PresupuestoDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoPendientesDAOImpl;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.PresupuestoBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

public class PosPresupuestoFacade {

	public static PresupuestosBean traeGenericos(String local) {
		PresupuestosBean bean = new PresupuestosBean();
        try {
            PresupuestoDAOImpl Presupuestoimpl = new PresupuestoDAOImpl();
            bean = Presupuestoimpl.traeGenericosFormulario(local);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
	}

	public static int traeCodigoVenta(String local) {
		int codigo = Constantes.INT_CERO;
        try {
        	PresupuestoDAOImpl Presupuestoimpl = new PresupuestoDAOImpl();
            codigo = Presupuestoimpl.traeCodigoVenta(local);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigo;
	}

	public static String traeCodigoSuc(String local) {
		String codigo = Constantes.STRING_BLANCO;
        try {
        	PresupuestoDAOImpl Presupuestoimpl = new PresupuestoDAOImpl();
            codigo = Presupuestoimpl.traeCodigo_Suc(local);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigo;
	}

	public static boolean insertaPresupuesto(PresupuestosBean presupuesto, String local) {
		try {
			PresupuestoDAOImpl pre = new PresupuestoDAOImpl();
			return pre.insertaPresupuesto(presupuesto, local);
		} catch (Exception e) {
			return false;
		}
		
	}

	public static void insertaPresupuestoDetalle(ProductosBean prod,
			PresupuestosBean presupuesto, int x, String local) {
		
		try {
			PresupuestoDAOImpl pre = new PresupuestoDAOImpl();
			pre.insertaPresupuestoDetalle(prod, presupuesto, x,  local);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static ArrayList<PresupuestosBean> traeListaPresupuestos(
			String cliente, String local) 
	{
		ArrayList<PresupuestosBean> lista = new ArrayList<PresupuestosBean>();
		try {
			PresupuestoDAOImpl pre = new PresupuestoDAOImpl();
			lista =  pre.traeListaPresupuestos(cliente, local);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static ArrayList<ProductosBean> traeDetallePresupuesto(String codigo) {
		ArrayList<ProductosBean> lista = new ArrayList<ProductosBean>();
		try {
			PresupuestoDAOImpl pre = new PresupuestoDAOImpl();
			lista =  pre.traeListaProductos(codigo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static String[] traspadoPedido(String codigo, String local) {
		String[] msje = null;
		try {
			PresupuestoDAOImpl pre = new PresupuestoDAOImpl();
			msje =  pre.traspasoPresupuesto(codigo, local);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msje;
		
	}

	public static boolean eliminarPresupuesto(String codigo) {
		boolean estado = false;
		try {
			PresupuestoDAOImpl pre = new PresupuestoDAOImpl();
			estado = pre.eliminarPresupuesto(codigo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}

	public static ArrayList<PresupuestosBean> traePresupuestos(
			String local, String presupuesto, String agente, String cliente,
			String fecha) {
		ArrayList<PresupuestosBean> listaPresupuesto = new ArrayList<PresupuestosBean>();
		PresupuestoDAOImpl presupestoDAOImpl = new PresupuestoDAOImpl();
		try {
			listaPresupuesto = presupestoDAOImpl.traePresupuestos(local, presupuesto, agente, cliente, fecha);
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		return listaPresupuesto;
	}

	public static PresupuestosBean traePresupuestoSeleccionado(
			String cdg) {
		PresupuestosBean presupuesto = new PresupuestosBean();
		PresupuestoDAOImpl presupestoDAOImpl = new PresupuestoDAOImpl();
		try {
			presupuesto = presupestoDAOImpl.traePresupuestoSeleccionado(cdg);
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		return presupuesto;
	}

}
