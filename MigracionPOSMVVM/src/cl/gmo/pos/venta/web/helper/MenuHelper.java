/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.helper;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.MenuBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.MenuForm;

public class MenuHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
    public boolean validaAperturaCaja(String sucursal){
    	log.info("MenuHelper:validaAperturaCaja inicio");
        boolean aperturaCaja=false;
    	
    	try {
    		aperturaCaja = PosUtilesFacade.validaAperturaCaja(sucursal, this.formatoFecha(this.traeFecha()));
		} catch (Exception e) {
			log.error("MenuHelper:validaAperturaCaja error catch",e);
		}
        return aperturaCaja;
    }
    

    public MenuForm llenaMenu(MenuForm actionForm, String usuario){
    	log.info("MenuHelper:llenaMenu inicio");

    	MenuBean menu= new MenuBean();
		try {
			menu = PosUtilesFacade.llenaMenu(usuario);
		} catch (Exception e) {
			log.error("MenuHelper:llenaMenu error catch",e);
		}	
		actionForm.setVenta(menu.getVenta());
        actionForm.setVentaVentaDirecta(menu.getVentaVentaDirecta());
        actionForm.setVentaPresupuesto(menu.getVentaPresupuesto());
        actionForm.setVentaPedido(menu.getVentaPedido());
        actionForm.setVentaAlbaranes(menu.getVentaAlbaranes());
        actionForm.setVentaLiberacionEncargo(menu.getVentaLiberacionEncargo());
        
        actionForm.setMantenedores(menu.getMantenedores());
        actionForm.setMantenedoresClientes(menu.getMantenedoresClientes());
        actionForm.setMantenedoresGraduacionClientes(menu.getMantenedoresGraduacionClientes());
        actionForm.setMantenedoresMedico(menu.getMantenedoresMedico());
        actionForm.setMantenedoresDevolucion(menu.getDevolucion());
        actionForm.setMantenedoresDevolucion(menu.getDevolucion());  
        actionForm.setMantenedoresEntregaPedido(menu.getEntregaPedido());
        actionForm.setMantenedoresCambioFolio(menu.getMantenedoresCambioFolio());
        
        actionForm.setInforme(menu.getInforme());
        actionForm.setInformeInformeTotalDia(menu.getInformeInformeTotalDia());
        
        
    	return actionForm;
    }
}
