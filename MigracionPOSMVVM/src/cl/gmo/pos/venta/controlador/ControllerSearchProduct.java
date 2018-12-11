package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
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
	
	private FamiliaBean familiaBean;
	private SubFamiliaBean subFamiliaBean;
	private GrupoFamiliaBean grupoFamiliaBean;
	private ProductosBean productoBean;		
	
	private UtilesDAOImpl utilesDaoImpl;
	private BusquedaProductosHelper busquedaProdhelper;	
	
	private BusquedaProductosForm busquedaProductosForm;
	private BusquedaProductosDispatchActions busquedaProductosDispatchActions;
	
	private String winVisibleBusqueda;
	HashMap<String,Object> objetos;
	
	@Init
	public void inicial(@ExecutionArgParam("familiaBeans")List<FamiliaBean> arg) {		
		
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
	
	
	@NotifyChange({"busquedaProductosForm","subFamiliaBean","grupoFamiliaBean"})
	@Command
	public void cargaSubFamilias() {
		
		String codInicial="0";
		
		try {					
			busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
			busquedaProductosForm.setCodigoBarraBusqueda("");
			busquedaProductosForm.setCodigoBusqueda("");			
			
			busquedaProductosForm.setListaSubFamilias(utilesDaoImpl.traeSubfamilias(familiaBean.getCodigo()));
			
			Optional<SubFamiliaBean> a = busquedaProductosForm.getListaSubFamilias().stream().filter(s -> codInicial.equals(s.getCodigo())).findFirst();
			subFamiliaBean = a.get();
			
			GrupoFamiliaBean gfb = new GrupoFamiliaBean();
			gfb.setCodigo("0");
			gfb.setDescripcion("SELECCIONAR");
			gfb.setFamilia("");
			gfb.setSubfamilia("");
			
			busquedaProductosForm.setListaGruposFamilias(new ArrayList<GrupoFamiliaBean>());
			busquedaProductosForm.getListaGruposFamilias().add(gfb);
			Optional<GrupoFamiliaBean> b = busquedaProductosForm.getListaGruposFamilias().stream().filter(s -> codInicial.equals(s.getCodigo())).findFirst();
			grupoFamiliaBean = b.get();	
			
			//cleanProducts();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	
	@NotifyChange({"busquedaProductosForm","grupoFamiliaBean"})
	@Command
	public void cargaGrupoFamilias() {	
		
		String codInicial="0";
		
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
		busquedaProductosForm.setCodigoBarraBusqueda("");
		busquedaProductosForm.setCodigoBusqueda("");
		
		try {			
			busquedaProductosForm.setListaGruposFamilias(utilesDaoImpl.traeGruposFamilias(familiaBean.getCodigo(), subFamiliaBean.getCodigo() ));
			
			Optional<GrupoFamiliaBean> b = busquedaProductosForm.getListaGruposFamilias().stream().filter(s -> codInicial.equals(s.getCodigo())).findFirst();
			grupoFamiliaBean = b.get();
			//cleanProducts();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	@NotifyChange("busquedaProductosForm")
	@Command
	public void despachador() {	
		
		Optional<String> fam    = Optional.ofNullable(familiaBean.getCodigo());
		Optional<String> subfam = Optional.ofNullable(subFamiliaBean.getCodigo());
		Optional<String> grufam = Optional.ofNullable(grupoFamiliaBean.getCodigo());
		
		Optional<String> codbus = Optional.ofNullable(busquedaProductosForm.getCodigoBusqueda());
		Optional<String> codbusbar = Optional.ofNullable(busquedaProductosForm.getCodigoBarraBusqueda());
		
		if (fam.isPresent())		
			busquedaProductosForm.setFamilia(familiaBean.getCodigo());
		else
			busquedaProductosForm.setFamilia("");
		
		if(subfam.isPresent())
			busquedaProductosForm.setSubFamilia(subFamiliaBean.getCodigo());
		else	
			busquedaProductosForm.setSubFamilia("");
		
		if(grufam.isPresent())		
			busquedaProductosForm.setGrupo(grupoFamiliaBean.getCodigo());
		else
			busquedaProductosForm.setGrupo("");
		
		//if(!codbus.isPresent()) busquedaProductosForm.setCodigoBusqueda("");
		//if(!codbusbar.isPresent()) busquedaProductosForm.setCodigoBarraBusqueda("");
		
		busquedaProductosForm.setCodigoBusqueda(codbus.orElse("").toUpperCase().trim());
		busquedaProductosForm.setCodigoBarraBusqueda(codbusbar.orElse("").toUpperCase().trim());
		
		busquedaProductosForm.setAccion("buscar"); 
		busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);
		
	}
	
	
	/*@NotifyChange("busquedaProductosForm")
	@Command
	public void buscarProducto(@BindingParam("arg")FamiliaBean arg,
			@BindingParam("arg2")SubFamiliaBean arg2,
			@BindingParam("arg3")GrupoFamiliaBean arg3,
			@BindingParam("arg4")String arg4, @BindingParam("arg5")String arg5){
		
				
			
		busquedaProductosForm.setListaProductos(busquedaProdhelper.traeProductos(arg.getCodigo(), arg2.getCodigo(), 
					arg3.getCodigo(), "", "", "", arg4, arg5, SUCURSAL, TIPO_BUSQUEDA));		
	}*/
	
	
	@NotifyChange("winVisibleBusqueda")
	@Command
	public void seleccionaProducto(@BindingParam("producto")ProductosBean producto) {	
		
		objetos = new HashMap<String,Object>();
		objetos.put("producto",producto);
		
		winVisibleBusqueda="FALSE";
		
		BindUtils.postGlobalCommand(null, null, "actProdGrid", objetos);		
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
	
	@NotifyChange("winVisibleBusqueda")
	@Command
	public void cierraVentana() {	
		
		winVisibleBusqueda="FALSE";
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
