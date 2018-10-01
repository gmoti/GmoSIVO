/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.Integracion.DAO;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

/**
 *
 * @author Advice70
 */
public interface VentaPedidoDAO {
    
    
	public int traeCodigoVenta(String local) throws Exception;
	public String traeCodigo_Suc(String local)  throws Exception;
	public ArrayList<TipoPedidoBean> traeTiposPedidos() throws Exception;
	public VentaPedidoBean traeGenericosFormulario(String local) throws Exception;
	public ArrayList<VentaPedidoBean> traeVentasPedidos(String local, String fecha, String fechaHasta,String estado)throws Exception;
}
