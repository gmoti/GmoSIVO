package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.facade.PosVentaPedidoFacade;
import cl.gmo.pos.venta.web.forms.BusquedaPedidosForm;

public class BusquedaPedidosHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	
	public ArrayList<AgenteBean> traeAgentes_Local(String local) {
		ArrayList<AgenteBean> lista = new ArrayList<AgenteBean>();
		try {
			lista = this.traeAgentes(local);
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			log.error("BusquedaPedidosHelper:traeAgentes_Local error catch",e);
		}
		return lista;
	}

	public void traePedidos(BusquedaPedidosForm formulario, String local, Session session) {
		
		String cliente = null;
		String pedido = null;
		String agente = null;
		String fecha = null;
		
		if (!Constantes.STRING_BLANCO.equals(formulario.getCliente())) {
			cliente = formulario.getCliente();
		}
		if (!Constantes.STRING_BLANCO.equals(formulario.getEncargo())) {
			pedido = formulario.getEncargo();
		}
		if (!Constantes.STRING_CERO.equals(formulario.getAgente())) {
			agente = formulario.getAgente();
		}
		if (!Constantes.STRING_BLANCO.equals(formulario.getFecha())) {
			fecha = formulario.getFecha();
		}
		
		try {
			formulario.setLista_pedidos(PosVentaPedidoFacade.traePedidosPendientes(null, local, pedido, agente, cliente, fecha));
		} catch (Exception e) {
			log.error("BusquedaPedidosHelper:traePedidos error catch",e);
		}
		
		if (formulario.getLista_pedidos().isEmpty())
        {
        	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_BLANCO);
        }
        else
        {
        	session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ESTADO, Constantes.STRING_LISTA_PRODUCTOS_ESTADO);
        }
		
	}

	
}
