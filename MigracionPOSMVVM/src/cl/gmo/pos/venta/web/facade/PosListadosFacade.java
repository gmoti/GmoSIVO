package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ListaPresupuestosDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ListadoBoletasDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ListadoTotalDiaDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ListadoTrabajosPendientesDAOImpl;
import cl.gmo.pos.venta.web.beans.ListaPresupuestosBean;
import cl.gmo.pos.venta.web.beans.ListadoBoletasBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesBean;
import cl.gmo.pos.venta.web.beans.ListasTotalesDiaBean;

public class PosListadosFacade {

	public static ListasTotalesDiaBean traeListasTotales(String fecha,String local) {

		ListasTotalesDiaBean listasTotalesDia;
		try {
			ListadoTotalDiaDAOImpl ListadoTotalDiaDAO = new ListadoTotalDiaDAOImpl();
			listasTotalesDia=ListadoTotalDiaDAO.traeListasTotales(fecha,local);
		} catch (Exception e) {
			e.printStackTrace();
			listasTotalesDia = null;
		}
		return listasTotalesDia;
	}
	public static ArrayList<ListadoBoletasBean> traeListadoBoletas(String fecha,String local, String boleta) {

		ArrayList<ListadoBoletasBean> listasBoletas;
		try {
			ListadoBoletasDAOImpl listadoBoletasDAO = new ListadoBoletasDAOImpl();
			listasBoletas=listadoBoletasDAO.traeListasBoletas(fecha, local, boleta);
		} catch (Exception e) {
			e.printStackTrace();
			listasBoletas = null;
		}
		return listasBoletas;
	}
	
	public static ListaPresupuestosBean traeListadoPresupuestos(String sucursal, String nifCliente, String cerrado, String codigoPresupuesto, String codigoProducto, String fInicio, String fTermino, String Agente, String divisa, String fpago) {

		ListaPresupuestosBean listaPresupuestosBean = new ListaPresupuestosBean();
		try {
			ListaPresupuestosDAOImpl listaPresupuestoDAO = new ListaPresupuestosDAOImpl();
			listaPresupuestosBean = listaPresupuestoDAO.traeListadoPresupuestos(sucursal, nifCliente, cerrado, codigoPresupuesto, codigoProducto, fInicio, fTermino, Agente, divisa, fpago);
		} catch (Exception e) {
			e.printStackTrace();
		//	listasBoletas = null;
		}
		
		
		return listaPresupuestosBean;
	}
	public static ListadosTrabajosPendientesBean traeListadosTrabajosPendientes(String codigo, String nifCliente, String agente, 
			String divisa, String fInicio, String fTermino, String fpago, String sucursal, String cerrado, String listadoDetallado, String tipoPedido,
			String anulado) {

		ListadosTrabajosPendientesBean listadosTrabajosPendientesBean = new ListadosTrabajosPendientesBean();
		
		try {
			ListadoTrabajosPendientesDAOImpl listadoTrabajosPendientesDAO=new ListadoTrabajosPendientesDAOImpl();
			listadosTrabajosPendientesBean=listadoTrabajosPendientesDAO.traeListadosTrabajosPendientes(codigo, nifCliente, agente, divisa, fInicio, fTermino, fpago, sucursal, cerrado, listadoDetallado, tipoPedido, anulado);
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		
		return listadosTrabajosPendientesBean;
	}
}
