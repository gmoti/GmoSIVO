/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package cl.gmo.pos.venta.web.Integracion.DAO;
 
import java.math.BigDecimal;
import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.PrecioEspecialBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.MenuBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
 
/**
*
* @author Advice70
*/
public interface  UtilesDAO {
	
	public BigDecimal traeDecuento(String usuario, String pass, String tipo) throws Exception;
	
	public ArrayList<TipoAlbaranBean> traeTipoAlbaranes() throws Exception;
   
    public ArrayList<DivisaBean> traeDivisas() throws Exception;
   
    public ArrayList<FormaPagoBean> traeFormasPago() throws Exception;
   
    public ArrayList<IdiomaBean> traeIdiomas() throws Exception;
   
    public ArrayList<AgenteBean> traeAgentes(String local) throws Exception;
   
    public ArrayList<ConvenioBean> traeConvenios() throws Exception;
   
    public ArrayList<PromocionBean> traePromociones() throws Exception;
   
    public ArrayList<FamiliaBean> traeFamilias(String form_origen) throws Exception;
   
    public ArrayList<SubFamiliaBean> traeSubfamilias(String familia) throws Exception;
   
    public ArrayList<GrupoFamiliaBean> traeGruposFamilias(String familia, String subfamilia) throws Exception;
   
    public ArrayList<ProveedorBean> traeProveedores() throws Exception;
   
    public boolean validaCaja(String sucursal, String fecha) throws Exception;
   
    public MenuBean llenaMenu(String usuario) throws Exception;
    
    public ArrayList<ProvinciaBean> traeProvincias() throws Exception;
    
    public ArrayList<GiroBean> traeGiros() throws Exception;
    
	public ArrayList<SuplementopedidoBean> traeSuplementosOpcionales (String producto) throws Exception;
	
	public ArrayList<SuplementopedidoBean> traeSuplementosObligatorios (String producto) throws Exception;
	
	public boolean validaEstadoPed(String codigo) throws Exception ;
	
	public  ArrayList<PrecioEspecialBean> traePrecioEspecial(String codigoBarra, int cantidad, String fecha) throws Exception ;

	    
    

}