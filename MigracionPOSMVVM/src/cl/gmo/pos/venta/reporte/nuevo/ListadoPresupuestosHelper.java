package cl.gmo.pos.venta.reporte.nuevo;

import java.util.ArrayList;



import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoCabeceraBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoLineaBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestoTotalBean;
import cl.gmo.pos.venta.web.beans.ListaPresupuestosBean;
import cl.gmo.pos.venta.web.beans.PresupuestoBean;
import cl.gmo.pos.venta.web.facade.PosListadosFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.ListadoPresupuestosForm;

public class ListadoPresupuestosHelper extends Utils {
	Logger log = Logger.getLogger( this.getClass() );
	
	public ListadoPresupuestosForm cargaListadoPresupuestos(ListadoPresupuestosForm form,String sucursal){
		log.info("ListadoPresupuestosHelper:cargaListadoPresupuestos inicio");
		ListaPresupuestosBean listaPresupuestosBean = PosListadosFacade.traeListadoPresupuestos(sucursal,form.getCliente(), form.getCerrado(), String.valueOf(form.getCodigo()),"", form.getFechaInicio(), form.getFechaTermino(), form.getAgente(), form.getDivisa(), form.getForma_pago());
		
		ArrayList<ListaPresupuestoCabeceraBean> listaPresupuestoCabeceraBean = listaPresupuestosBean.getListaPresupuestoCabeceras();
		ArrayList<ListaPresupuestoLineaBean> listaPresupuestoLineaBean = listaPresupuestosBean.getListaPresupuestoLineas();
		ArrayList<ListaPresupuestoTotalBean> listaPresupuestoTotalBean = listaPresupuestosBean.getListaPresupuestoTotales();
	 
		ArrayList<PresupuestoBean> presupuestosBean = new ArrayList<PresupuestoBean>();
		
		for(ListaPresupuestoCabeceraBean tmpCabecera:listaPresupuestoCabeceraBean){
			log.info("ListadoPresupuestosHelper:cargaListadoPresupuestos entrando ciclo for");
			PresupuestoBean presupuesto = new PresupuestoBean();
			ArrayList<ListaPresupuestoLineaBean> lineaForm = new ArrayList<ListaPresupuestoLineaBean>();
			
			for(ListaPresupuestoLineaBean tmpLinea:listaPresupuestoLineaBean){
				log.info("ListadoPresupuestosHelper:cargaListadoPresupuestos entrando ciclo for");
				if(tmpCabecera.getCodigoCabecera().equals(tmpLinea.getCodigo())){
					ListaPresupuestoLineaBean linea = new ListaPresupuestoLineaBean();
					
					linea.setCodigo(tmpLinea.getCodigo());
					linea.setDescripcion(tmpLinea.getDescripcion());
					linea.setCantidad(tmpLinea.getCantidad());
					linea.setPrecioIva(tmpLinea.getPrecioIva());
					linea.setDescuento(tmpLinea.getDescuento());
					lineaForm.add(linea);
					
					presupuesto.setLineas(lineaForm);
					presupuesto.setNumero(tmpCabecera.getCodigoCabecera());
					presupuesto.setFecha(tmpCabecera.getFecha());
					presupuesto.setAgente(tmpCabecera.getAgente());
					presupuesto.setNombres(tmpCabecera.getNombre());
					presupuesto.setApellido(tmpCabecera.getApellido());
					presupuesto.setDescuento(tmpCabecera.getDescuento());
					presupuesto.setForma_pago(tmpCabecera.getCambio());
					presupuesto.setNif_cliente(tmpCabecera.getNif_cliente());
				}
			}
			for(ListaPresupuestoTotalBean tmpTotal:listaPresupuestoTotalBean){
				log.info("ListadoPresupuestosHelper:cargaListadoPresupuestos entrando ciclo for");
				if(tmpCabecera.getCodigoCabecera().equals(tmpTotal.getCodigoTotal())){
					presupuesto.setTotal(tmpTotal.getTotal());
				}
			}
 			presupuestosBean.add(presupuesto);
			form.setListaPresupuestos(presupuestosBean);
		}
		
		return form;
	}

	public void traeDatosFormulario(ListadoPresupuestosForm formulario,
			Session session) {
		log.info("ListadoPresupuestosHelper:traeDatosFormulario inicio");
		formulario.setListaDivisas(PosUtilesFacade.traeDivisas());
		formulario.setListaFormasPago(PosUtilesFacade.traeFormasPago());
		
	} 
	

}
