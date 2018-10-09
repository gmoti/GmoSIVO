package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.zkoss.bind.BindUtils;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.BusquedaProductosDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;


public class ControllerSearchProductPres implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7360268478883882968L;
	
	Session sess = Sessions.getCurrent();

	@Wire("#winBuscaProducto")
	private Window win;
	
	
	private FamiliaBean familiaBean;
	private SubFamiliaBean subFamiliaBean;
	private GrupoFamiliaBean grupoFamiliaBean;
	private ProductosBean productoBean;

	private List<FamiliaBean> familiaBeans;
	private ArrayList<SubFamiliaBean> subFamiliaBeans;
	private ArrayList<GrupoFamiliaBean> grupoFamiliaBeans;
	private List<ProductosBean> productos;
	
	private UtilesDAOImpl utilesDaoImpl;

	
	private String winVisibleBusqueda;
	private PresupuestoForm presupuesto;
	private VentaPedidoForm ventaPedido;
	private BusquedaProductosForm busquedaProductosForm;
	private BusquedaProductosDispatchActions busquedaProductosDispatchActions;
	
	private boolean ojoDerecho;
	private boolean ojoIzquierdo;
	//private boolean cerca;
	private String busquedaAvanzada;
	private String busquedaAvanzadaLentilla;
	
	private int instancia=0;
	HashMap<String,Object> objetos;
	
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view, 
			//@ExecutionArgParam("presupuestoForm")PresupuestoForm arg) {
			@ExecutionArgParam("objetoForm")Object arg) {
		
		Selectors.wireComponents(view, this, false);
		
		if (arg instanceof PresupuestoForm) {
			presupuesto = new PresupuestoForm();
			presupuesto = (PresupuestoForm)arg;
			sess.setAttribute(Constantes.STRING_GRADUACION, presupuesto.getGraduacion());
			sess.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, presupuesto.getGraduacion_lentilla());
			instancia=1;
		}
		
        if (arg instanceof VentaPedidoForm) {
        	ventaPedido = new VentaPedidoForm();
        	ventaPedido = (VentaPedidoForm)arg;
			sess.setAttribute(Constantes.STRING_GRADUACION, ventaPedido.getGraduacion());
			sess.setAttribute(Constantes.STRING_GRADUACION_LENTILLA, ventaPedido.getGraduacion_lentilla());
			instancia=2;
		}
		
		
		winVisibleBusqueda = "TRUE";		
		busquedaProductosForm = new BusquedaProductosForm(); 
		busquedaProductosDispatchActions = new BusquedaProductosDispatchActions();	
		
		familiaBean = new FamiliaBean();
		subFamiliaBean = new SubFamiliaBean();
		grupoFamiliaBean = new GrupoFamiliaBean();
		productoBean = new ProductosBean();
		
		familiaBeans = new ArrayList<>();
		subFamiliaBeans = new ArrayList<>();
		grupoFamiliaBeans = new ArrayList<>();
		productos = new ArrayList<>();
		
		utilesDaoImpl = new UtilesDAOImpl();
		
		
		ojoDerecho=false;
		ojoIzquierdo=false;
		//cerca=false;
		busquedaAvanzada = "false";
		busquedaAvanzadaLentilla= "false";		
		
		
		if(instancia==1)		
			sess.setAttribute(Constantes.STRING_FORMULARIO, Constantes.STRING_ACTION_PRESUPUESTO_MAY);	
		else
			sess.setAttribute(Constantes.STRING_FORMULARIO, Constantes.STRING_PEDIDO);	
			
		busquedaProductosForm = busquedaProductosDispatchActions.cargaBusquedaProductos(busquedaProductosForm, sess);
		busquedaProductosForm.setChk_cerca(false);
			
	}
	
	
	@NotifyChange({"winVisibleBusqueda","busquedaProductosForm"})
	@Command
	public void seleccionaProducto(@BindingParam("producto")ProductosBean producto) {	
		
		
		String tipo="";
		objetos = new HashMap<String,Object>();
		
		if (busquedaAvanzadaLentilla.equals("true")) {
			
			if (ojoDerecho)
				producto.setOjo("derecho");
			else
				producto.setOjo("izquierdo");	
			
		}else if(busquedaAvanzada.equals("true")) {
			
			if (ojoDerecho)
				producto.setOjo("derecho");
			else
				producto.setOjo("izquierdo");
			
			if (busquedaProductosForm.isChk_cerca())
				tipo="Cerca";
			else
				tipo="Lejos";
			
		}	
		
		
		
		objetos.put("producto",producto);
		objetos.put("tipo",tipo);
		
		
		try {
			busquedaProductosForm.setTipofamilia(familiaBean.getTipo_fam());			
			busquedaProductosForm.setCodigo_barras(producto.getCod_barra());
			
			
			producto.setTipoFamilia(familiaBean.getTipo_fam());
			producto.setGrupo("0");
			
			//busquedaProductosForm.setFamilia(familiaBean.getCodigo());
			//busquedaProductosForm.setSubFamilia(subFamiliaBean.getCodigo());
			//busquedaProductosForm.setGrupo(grupoFamiliaBean.getCodigo());
			
			boolean tieneSuple = busquedaProductosDispatchActions.tiene_suple(busquedaProductosForm, sess);
			
			winVisibleBusqueda="FALSE";	
			
			
			if (instancia==1)
				BindUtils.postGlobalCommand(null, null, "actProdGridPresupuesto", objetos);
				
			if (instancia==2)
				BindUtils.postGlobalCommand(null, null, "actProdGridVentaPedido", objetos);				
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
		
	}
	
	@NotifyChange("winVisibleBusqueda")
	@Command
	public void cierraVentana() {	
		
		winVisibleBusqueda="FALSE";
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
			busquedaProductosForm.setGrupo (grufam.get().getCodigo());		    	
		else
			busquedaProductosForm.setGrupo("0");
		
	    busquedaProductosForm.setAccion(arg);     	
     	
     	if (arg.equals("buscar")) { 
     		
     		if (busquedaAvanzada.equals("true")) {
	     		if (!isOjoDerecho() && !isOjoIzquierdo()) {
	     			
	     			Messagebox.show("Debe seleccionar un ojo, para realizar la busqueda.");
	     			busquedaProductosForm.setAccion("error");
	     			//busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);					
	     		}
	     		else {	     			
	     			
	     			busquedaProductosForm.setAccion("busqueda_graduada");
	     			busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);	     			
	     		}
     		}    		
			
			if (busquedaAvanzadaLentilla.equals("true")) {
				if (!isOjoDerecho() && !isOjoIzquierdo()) {
					Messagebox.show("Debe seleccionar un ojo, para realizar la busqueda.");
					busquedaProductosForm.setAccion("error");
	     			//busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);
				}
			}			
		}
     	
     	//busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);	
     	//inicializo la busqueda
     	busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
     	busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);
	}
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void cleanProducts() {
		
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
	}
	
	
	
	@NotifyChange({"busquedaProductosForm","busquedaAvanzada","busquedaAvanzadaLentilla","subFamiliaBeans","subFamiliaBean","grupoFamiliaBean"})
	@Command
	public void cargaSubFamilias() {	
		try {
			
			//al cambiar el padre se limpia la busqueda previamente ejecutada			
			busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
			
			//Inicializo los combos inferiores
			subFamiliaBean  = null;
			grupoFamiliaBean= null;
			// --->
			
			subFamiliaBeans = utilesDaoImpl.traeSubfamilias(familiaBean.getCodigo());
			busquedaProductosForm.setListaSubFamilias(subFamiliaBeans);	
			
			
			if (familiaBean.getTipo_fam().equals("C")) {
				setBusquedaAvanzada("true");
				cleanProducts();
			}
			else
				setBusquedaAvanzada("false");
			
			if (familiaBean.getTipo_fam().equals("L")) {
				setBusquedaAvanzadaLentilla("true");
				cleanProducts();
			}
			else
				setBusquedaAvanzadaLentilla("false");
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	
	@NotifyChange({"grupoFamiliaBeans","busquedaProductosForm","grupoFamiliaBean"})
	@Command
	public void cargaGrupoFamilias() {	
		
		//al cambiar el padre se limpia la busqueda previamente ejecutada			
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
		
		//Inicializo los combos inferiores		
		grupoFamiliaBean= null;
		// --->
		
		try {
			grupoFamiliaBeans = utilesDaoImpl.traeGruposFamilias(familiaBean.getCodigo(), subFamiliaBean.getCodigo());
			busquedaProductosForm.setListaGruposFamilias(grupoFamiliaBeans);					
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void cambiaGrupoFamilias() {
		//al cambiar el padre se limpia la busqueda previamente ejecutada			
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());		
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
	public void seleccionaGraduacion(@BindingParam("codigo")int codigo){       
        busquedaProductosForm.setIndex_graduacion(codigo);
        busquedaProductosForm.setAccion("selecciona_graduacion");
        busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);	
		
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

	public List<FamiliaBean> getFamiliaBeans() {
		return familiaBeans;
	}
	public void setFamiliaBeans(List<FamiliaBean> familiaBeans) {
		this.familiaBeans = familiaBeans;
	}

	public ArrayList<SubFamiliaBean> getSubFamiliaBeans() {
		return subFamiliaBeans;
	}
	public void setSubFamiliaBeans(ArrayList<SubFamiliaBean> subFamiliaBeans) {
		this.subFamiliaBeans = subFamiliaBeans;
	}

	public ArrayList<GrupoFamiliaBean> getGrupoFamiliaBeans() {
		return grupoFamiliaBeans;
	}
	public void setGrupoFamiliaBeans(ArrayList<GrupoFamiliaBean> grupoFamiliaBeans) {
		this.grupoFamiliaBeans = grupoFamiliaBeans;
	}

	public List<ProductosBean> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductosBean> productos) {
		this.productos = productos;
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

	public boolean isOjoDerecho() {
		return ojoDerecho;
	}

	public void setOjoDerecho(boolean ojoDerecho) {
		this.ojoDerecho = ojoDerecho;
	}

	public boolean isOjoIzquierdo() {
		return ojoIzquierdo;
	}

	public void setOjoIzquierdo(boolean ojoIzquierdo) {
		this.ojoIzquierdo = ojoIzquierdo;
	}

	public String getBusquedaAvanzada() {
		return busquedaAvanzada;
	}

	public void setBusquedaAvanzada(String busquedaAvanzada) {
		this.busquedaAvanzada = busquedaAvanzada;
	}

	public String getBusquedaAvanzadaLentilla() {
		return busquedaAvanzadaLentilla;
	}

	public void setBusquedaAvanzadaLentilla(String busquedaAvanzadaLentilla) {
		this.busquedaAvanzadaLentilla = busquedaAvanzadaLentilla;
	}

	/*public boolean isCerca() {
		return cerca;
	}

	public void setCerca(boolean cerca) {
		this.cerca = cerca;
	} */

	
}
