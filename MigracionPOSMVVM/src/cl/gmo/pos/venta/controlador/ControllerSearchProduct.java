package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.BusquedaProductosDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.helper.BusquedaProductosHelper;

public class ControllerSearchProduct implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3072799490102569407L;
	
	Session sess = Sessions.getCurrent();

	@Wire("#winBuscaProducto")
	private Window win;	
	
	//constantes
	private final String SUCURSAL="T002";
	private final String MONEDA="PESO";
	private final String TIPO_BUSQUEDA="DIRECTA";

	private FamiliaBean familiaBean;
	private SubFamiliaBean subFamiliaBean;
	private GrupoFamiliaBean grupoFamiliaBean;
	private ProductosBean productoBean;		
	
	private UtilesDAOImpl utilesDaoImpl;
	private BusquedaProductosHelper busquedaProdhelper;	
	
	private BusquedaProductosForm busquedaProductosForm;
	private BusquedaProductosDispatchActions busquedaProductosDispatchActions;
	
	private String winVisibleBusqueda;
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view, 
						@ExecutionArgParam("familiaBeans")List<FamiliaBean> arg) {	
		
		Selectors.wireComponents(view, this, false);
		
		winVisibleBusqueda = "TRUE";
		busquedaProductosForm = new BusquedaProductosForm(); 
		busquedaProductosDispatchActions = new BusquedaProductosDispatchActions();
		
		familiaBean = new FamiliaBean();
		subFamiliaBean = new SubFamiliaBean();
		grupoFamiliaBean = new GrupoFamiliaBean();
		productoBean = new ProductosBean();
		
		utilesDaoImpl = new UtilesDAOImpl();
		busquedaProdhelper = new BusquedaProductosHelper();	
			
		sess.setAttribute(Constantes.STRING_FORMULARIO, Constantes.STRING_DIRECTA);	
		busquedaProductosForm = busquedaProductosDispatchActions.cargaBusquedaProductos(busquedaProductosForm, sess);
		//cargaFamilias();	
	}
	
	/*@NotifyChange("busquedaProductosForm")
	public void cargaFamilias() {		
		try {			
			busquedaProductosForm.setListaFamilias(utilesDaoImpl.traeFamilias(TIPO_BUSQUEDA));
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}*/
	
	@NotifyChange("busquedaProductosForm")
	@Command
	public void cargaSubFamilias() {	
		try {					
			busquedaProductosForm.setListaSubFamilias(utilesDaoImpl.traeSubfamilias(familiaBean.getCodigo()));
			cleanProducts();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	
	@NotifyChange("busquedaProductosForm")
	@Command
	public void cargaGrupoFamilias() {	
		try {			
			busquedaProductosForm.setListaGruposFamilias(utilesDaoImpl.traeGruposFamilias(familiaBean.getCodigo(), subFamiliaBean.getCodigo() ));
			cleanProducts();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	@NotifyChange("busquedaProductosForm")
	@Command
	public void despachador(@BindingParam("arg")String arg) {	
		
		Optional<FamiliaBean> fam    = Optional.ofNullable(familiaBean);
		Optional<SubFamiliaBean> subfam = Optional.ofNullable(subFamiliaBean);
		Optional<GrupoFamiliaBean> grufam = Optional.ofNullable(grupoFamiliaBean);
		
		if (fam.isPresent())		
			busquedaProductosForm.setFamilia(fam.get().getCodigo());
		else
			busquedaProductosForm.setFamilia("");
		
		if(subfam.isPresent())
			busquedaProductosForm.setSubFamilia(subfam.get().getCodigo());
		else	
			busquedaProductosForm.setSubFamilia("");
		
		if(grufam.isPresent())		
			busquedaProductosForm.setGrupo(grufam.get().getCodigo());
		else
			busquedaProductosForm.setGrupo("");
		
		busquedaProductosForm.setAccion(arg); 
		busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);
		
	}
	
	
	@NotifyChange("busquedaProductosForm")
	@Command
	public void buscarProducto(@BindingParam("arg")FamiliaBean arg,
			@BindingParam("arg2")SubFamiliaBean arg2,
			@BindingParam("arg3")GrupoFamiliaBean arg3,
			@BindingParam("arg4")String arg4, @BindingParam("arg5")String arg5){
		
				
			
		busquedaProductosForm.setListaProductos(busquedaProdhelper.traeProductos(arg.getCodigo(), arg2.getCodigo(), 
					arg3.getCodigo(), "", "", "", arg4, arg5, SUCURSAL, TIPO_BUSQUEDA));		
	}
	
	
	@NotifyChange("winVisibleBusqueda")
	@Command
	public void seleccionaProducto(@BindingParam("win")Window win) {		
		//win.detach();
		winVisibleBusqueda="FALSE";
	}

	
	@NotifyChange({"familiaBean","subFamiliaBean","grupoFamiliaBean"})
	@Command
	public void comboSetNull(@BindingParam("objetoBean")Object arg) {
		
		if (arg instanceof FamiliaBean) 
			familiaBean=null;			
		
		if (arg instanceof SubFamiliaBean)
			subFamiliaBean=null;				
		
		if (arg instanceof GrupoFamiliaBean) 
			grupoFamiliaBean=null;				
			
	}
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void cleanProducts() {
		
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
	}

	//---------getter and setter-----------
	//-------------------------------------

	public FamiliaBean getFamiliaBean() {
		return familiaBean;
	}
	public void setFamiliaBean(FamiliaBean familiaBean) {
		this.familiaBean = familiaBean;
	}

	public SubFamiliaBean getSubFamiliaBean() {
		return subFamiliaBean;
	}
	public void setSubFamiliaBean(SubFamiliaBean subFamiliaBean) {
		this.subFamiliaBean = subFamiliaBean;
	}

	public GrupoFamiliaBean getGrupoFamiliaBean() {
		return grupoFamiliaBean;
	}
	public void setGrupoFamiliaBean(GrupoFamiliaBean grupoFamiliaBean) {
		this.grupoFamiliaBean = grupoFamiliaBean;
	}

	public ProductosBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductosBean productoBean) {
		this.productoBean = productoBean;
	}

	public BusquedaProductosForm getBusquedaProductosForm() {
		return busquedaProductosForm;
	}

	public void setBusquedaProductosForm(BusquedaProductosForm busquedaProductosForm) {
		this.busquedaProductosForm = busquedaProductosForm;
	}

	public String getWinVisibleBusqueda() {
		return winVisibleBusqueda;
	}

	public void setWinVisibleBusqueda(String winVisibleBusqueda) {
		this.winVisibleBusqueda = winVisibleBusqueda;
	}

	
}
