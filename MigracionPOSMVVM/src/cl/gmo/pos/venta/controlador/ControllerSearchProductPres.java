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
import org.zkoss.zul.Messagebox;
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
	
	
	private static final long serialVersionUID = -7360268478883882968L;
	
	Session sess = Sessions.getCurrent();
	
	
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
	public void inicial(@ExecutionArgParam("objetoForm")Object arg) {		
		
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
			
		busquedaProductosDispatchActions.cargaBusquedaProductos(busquedaProductosForm, sess);
		busquedaProductosForm.setChk_cerca(false);
		
		//preload de graduaciones
		busquedaProductosForm.setAccion("buscar_graduacion");	
		busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);
		
	}
	
	
	@NotifyChange({"winVisibleBusqueda","busquedaProductosForm"})
	@Command
	public void seleccionaProducto(@BindingParam("producto")ProductosBean producto) throws Exception {			
		
		String tipo="";
		objetos = new HashMap<String,Object>();		
		String cod="";		
		
		String familia="";
		String seg_arm=""; 
		String cris_esp="";
		String cris_esp_seg="";	
		boolean seg_cristal=false;
		
		cod = producto.getCod_barra();	
		familia = busquedaProductosForm.getFamilia();
		
		if (cod.equals("")) {
			Messagebox.show("El codigo no es valido");
			return;
		}		
		
		busquedaProductosForm.setCodigo_barras(cod);		
		boolean tieneSuple = busquedaProductosDispatchActions.tiene_suple(busquedaProductosForm, sess);
		
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
		
		if (tieneSuple) {		
			seg_arm  = "2";
		    cris_esp = "1";
		    cris_esp_seg = "0";	
		    seg_cristal = true;
		}else {
			
			if(familia.indexOf("8") !=-1 ){	   			
		    	seg_arm = "2";
		    	cris_esp= "2";
		    	cris_esp_seg = "0";
		    	seg_cristal = true;
		    }else{				    	 
		    	 //$q("#seg_cristal",window.parent.document).val("");
		    	 seg_arm="0";
		    	 cris_esp="0";
		    	 cris_esp_seg="0";
		    	 seg_cristal = false;
		    }
			
		}			
		
		objetos.put("producto",producto);
		objetos.put("tipo",tipo);			
		
		busquedaProductosForm.setTipofamilia(familiaBean.getTipo_fam());			
		busquedaProductosForm.setCodigo_barras(producto.getCod_barra());			
		
		producto.setTipoFamilia(familiaBean.getTipo_fam());
		producto.setGrupo("0");			
		
		winVisibleBusqueda="FALSE";			
		
		if (instancia==1)
			BindUtils.postGlobalCommand(null, null, "actProdGridPresupuesto", objetos);
			
		if (instancia==2) {
			objetos.put("seg_arm",seg_arm);
			objetos.put("cris_esp",cris_esp);
			objetos.put("cris_esp_seg",cris_esp_seg);
			objetos.put("seg_cristal",seg_cristal); 
			BindUtils.postGlobalCommand(null, null, "actProdGridVentaPedido", objetos);
		}
				
		
	}
	
	@NotifyChange("winVisibleBusqueda")
	@Command
	public void cierraVentana() {	
		
		winVisibleBusqueda="FALSE";
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
			busquedaProductosForm.setFamilia("0");
		
		if(subfam.isPresent())
			busquedaProductosForm.setSubFamilia(subFamiliaBean.getCodigo());
		else	
			busquedaProductosForm.setSubFamilia("0");
		
		if(grufam.isPresent())		
			busquedaProductosForm.setGrupo (grupoFamiliaBean.getCodigo());		    	
		else
			busquedaProductosForm.setGrupo("0");		
		
		//if(!codbus.isPresent()) busquedaProductosForm.setCodigoBusqueda("");
		//if(!codbusbar.isPresent()) busquedaProductosForm.setCodigoBarraBusqueda("");
		
		busquedaProductosForm.setCodigoBusqueda(codbus.orElse("").toUpperCase());
		busquedaProductosForm.setCodigoBarraBusqueda(codbusbar.orElse("").toUpperCase());
		
		
	    busquedaProductosForm.setAccion("buscar");      	
     		
 		if (busquedaAvanzada.equals("true")) {
     		if (!isOjoDerecho() && !isOjoIzquierdo()) {
     			
     			Messagebox.show("Debe seleccionar un ojo, para realizar la busqueda.");
     			busquedaProductosForm.setAccion("error");
     			//busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);					
     		}
     		else {	     			
     			
     			busquedaProductosForm.setAccion("busqueda_graduada");
     			//busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);	     			
     		}
 		}    		
		
		if (busquedaAvanzadaLentilla.equals("true")) {
			if (!isOjoDerecho() && !isOjoIzquierdo()) {
				Messagebox.show("Debe seleccionar un ojo, para realizar la busqueda.");
				busquedaProductosForm.setAccion("error");
     			//busquedaProductosForm = busquedaProductosDispatchActions.buscar(busquedaProductosForm, sess);
				return;
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
	
	
	
	@NotifyChange({"busquedaProductosForm","subFamiliaBean","grupoFamiliaBean","busquedaAvanzada","busquedaAvanzadaLentilla"})
	@Command
	public void cargaSubFamilias() {	
		
		String codInicial="0";
		
		try {
			
			//al cambiar el padre se limpia la busqueda previamente ejecutada			
			busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
			
			//Inicializo los combos inferiores
			//subFamiliaBean  = null;
			//grupoFamiliaBean= null;			
			busquedaProductosForm.setCodigoBarraBusqueda("");
			busquedaProductosForm.setCodigoBusqueda("");
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
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	
	@NotifyChange({"grupoFamiliaBeans","busquedaProductosForm","grupoFamiliaBean"})
	@Command
	public void cargaGrupoFamilias() {	
		
		String codInicial="0";
		
		//al cambiar el padre se limpia la busqueda previamente ejecutada			
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
		
		//Inicializo los combos inferiores		
		//grupoFamiliaBean= null;		
		busquedaProductosForm.setCodigoBarraBusqueda("");
		busquedaProductosForm.setCodigoBusqueda("");
		// --->
		
		try {
			grupoFamiliaBeans = utilesDaoImpl.traeGruposFamilias(familiaBean.getCodigo(), subFamiliaBean.getCodigo());
			busquedaProductosForm.setListaGruposFamilias(grupoFamiliaBeans);			
						
			Optional<GrupoFamiliaBean> b = busquedaProductosForm.getListaGruposFamilias().stream().filter(s -> codInicial.equals(s.getCodigo())).findFirst();
			grupoFamiliaBean = b.get();
			
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void cambiaGrupoFamilias() {
		//al cambiar el padre se limpia la busqueda previamente ejecutada			
		busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
		busquedaProductosForm.setCodigoBarraBusqueda("");
		busquedaProductosForm.setCodigoBusqueda("");
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
	
		
	@Command
	public void inicioCombo() {
		
		String codInicial="0";		
		
		Optional<SubFamiliaBean> a = busquedaProductosForm.getListaSubFamilias().stream().filter(s -> codInicial.equals(s.getCodigo())).findFirst();
		subFamiliaBean = a.get();
		
		Optional<GrupoFamiliaBean> b = busquedaProductosForm.getListaGruposFamilias().stream().filter(s -> codInicial.equals(s.getCodigo())).findFirst();
		grupoFamiliaBean = b.get();		
		
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
