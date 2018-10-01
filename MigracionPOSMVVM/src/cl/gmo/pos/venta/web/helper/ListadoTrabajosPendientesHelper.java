package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesCabeceraBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesLineaBean;
import cl.gmo.pos.venta.web.beans.ListadosTrabajosPendientesTotalBean;
import cl.gmo.pos.venta.web.beans.SucursalesBean;
import cl.gmo.pos.venta.web.beans.TrabajosPendientesBean;
import cl.gmo.pos.venta.web.facade.PosListadosFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.ListadoTrabajosPendientesForm;

public class ListadoTrabajosPendientesHelper extends Utils {
	Logger log = Logger.getLogger( this.getClass() );
	public void cargaListadosTrabajosPendientes(ListadoTrabajosPendientesForm form,String sucursal){
		log.info("ListadoTrabajosPendientesHelper:cargaListadosTrabajosPendientes inicio");
		ListadosTrabajosPendientesBean listadosTrabajosPendientes = PosListadosFacade.traeListadosTrabajosPendientes(form.getCodigo(), form.getCliente(),form.getAgente(), form.getDivisa(), form.getFechaPedidoIni(), form.getFechaPedidoTer(), form.getFormaPago(), sucursal, form.getCerrado(), form.getListadoDetallado(), form.getTipoPedido(), form.getAnulado()); 
		
		ArrayList<ListadosTrabajosPendientesCabeceraBean> cabeceraLista = listadosTrabajosPendientes.getCabecera();;
		ArrayList<ListadosTrabajosPendientesLineaBean> lineaLista = listadosTrabajosPendientes.getLinea();
		ArrayList<ListadosTrabajosPendientesTotalBean> totalLista = listadosTrabajosPendientes.getTotal();

		ArrayList<TrabajosPendientesBean> trabajosPendientesBean = new ArrayList<TrabajosPendientesBean>();

		for(ListadosTrabajosPendientesCabeceraBean tmpCa: cabeceraLista){
			log.info("ListadoTrabajosPendientesHelper:cargaListadosTrabajosPendientes entrando ciclo for");
			TrabajosPendientesBean pendientes = new TrabajosPendientesBean();
			ArrayList<ListadosTrabajosPendientesLineaBean>  lineaForm = new ArrayList<ListadosTrabajosPendientesLineaBean> ();

			for(ListadosTrabajosPendientesLineaBean tmpLi: lineaLista){
				log.info("ListadoTrabajosPendientesHelper:cargaListadosTrabajosPendientes entrando ciclo for");
				if(tmpCa.getSerieNumero().equals(tmpLi.getCodigo())){
					ListadosTrabajosPendientesLineaBean linea = new ListadosTrabajosPendientesLineaBean();
					linea.setArticulo(tmpLi.getArticulo());
					linea.setDescripcion(tmpLi.getDescripcion());
					linea.setCantidad(tmpLi.getCantidad());
					linea.setPrecio(getNumber(tmpLi.getPrecio()));
					linea.setDescuento(tmpLi.getDescuento());
					linea.setTipo(traeTipoLente(tmpLi.getTipo()));
					linea.setCodigo(tmpLi.getCodigo());
					lineaForm.add(linea);

					pendientes.setLineas(lineaForm);
					pendientes.setSerie(tmpCa.getSerieNumero());
					pendientes.setFecha(tmpCa.getFecha());
					pendientes.setNumeroCaja(tmpCa.getNumeroCaja());
					pendientes.setCliente(tmpCa.getCliente());
					pendientes.setNombre(tmpCa.getNombre());
					pendientes.setApellidos(tmpCa.getApellido());
					pendientes.setDescuento1(tmpCa.getDescuento());
					pendientes.setfPago(tmpCa.getFormaPago());
					if(null==tmpCa.getAlbaran()||"".equals(tmpCa.getAlbaran())){
						pendientes.setAlbaran("Sin Albarán");
					}else{
						pendientes.setAlbaran(tmpCa.getAlbaran());
					}
					
				}
			}
			for(ListadosTrabajosPendientesTotalBean tmpTo: totalLista){
				log.info("ListadoTrabajosPendientesHelper:cargaListadosTrabajosPendientes entrando ciclo for");
				if(tmpTo.getCodigoTotal().equals(tmpCa.getSerieNumero())){
					pendientes.setTotal(getNumber(tmpTo.getTotal()));
					pendientes.setNotas(tmpTo.getNota());
					pendientes.setNumeroBoleta(tmpTo.getNumeroBoleta());
				}
			}
			trabajosPendientesBean.add(pendientes);
			form.setListaPendientes(trabajosPendientesBean);
		}
	}

	public void cargaInicial(ListadoTrabajosPendientesForm formulario, HttpSession session) {
		log.info("ListadoTrabajosPendientesHelper:cargaInicial inicio");
		try {
			formulario.setListaDivisas(PosUtilesFacade.traeDivisas());
			String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
			ArrayList<SucursalesBean> listaSucursales = new ArrayList<SucursalesBean>();
			listaSucursales.addAll(PosUtilesFacade.traeNombreSucursal(null));
			for (SucursalesBean sucursal : listaSucursales) {
				if (sucursal.getSucursal().equals(local)) {
					formulario.setColSucursales(new ArrayList<SucursalesBean>());
					formulario.getColSucursales().add(sucursal);
				}
			}
			
		} catch (Exception e) {
			log.error("ListadoTrabajosPendientesHelper:cargaInicial error catch",e);
		}
		
	} 

}
