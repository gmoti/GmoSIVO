package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.ventaDirecta.BusquedaProductosMultiOfertasDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.forms.BusquedaProductosForm;


public class ControllerBusquedaMultiofertas implements Serializable{

	
	private static final long serialVersionUID = -7654896376507908040L;
	
	Session sess = Sessions.getCurrent();
	
	private BusquedaProductosMultiOfertasDispatchActions busquedaProductosMultiOfertasDispatchActions;
	private BusquedaProductosForm  busquedaProductosForm;
	private TipoFamiliaBean tipoFamiliaBean;
	private FamiliaBean familiaBean;
	private SubFamiliaBean subFamiliaBean;
	private GrupoFamiliaBean grupoFamiliaBean;
	private ProductosBean productosBean;
	
	
	private boolean ojoDerecho;
	private boolean ojoIzquierdo;
	private boolean cerca;
	private String verGraduacion;
	
	HashMap<String,Object> objetos;
	private int indiceGral=0;
	
	
	
	@Init
	public void inicial(@ExecutionArgParam("busquedaProductos")BusquedaProductosForm arg,
						@ExecutionArgParam("origen")String arg1,
						@ExecutionArgParam("beanProducto")ProductosBean arg2,
						@ExecutionArgParam("index")int arg3){
						//@ExecutionArgParam("ventaPedido")VentaPedidoForm arg3) {	
		
		busquedaProductosMultiOfertasDispatchActions = new BusquedaProductosMultiOfertasDispatchActions();
		
		tipoFamiliaBean = new TipoFamiliaBean();
		familiaBean     = new FamiliaBean();
		subFamiliaBean  = new SubFamiliaBean();
		grupoFamiliaBean= new GrupoFamiliaBean();
		productosBean   = new ProductosBean();	
		
		busquedaProductosForm = arg;
		productosBean = arg2;
		indiceGral = arg3;
		
		ojoDerecho = false;
		ojoIzquierdo= false;
		cerca = false;
		verGraduacion="false";	
		
		busquedaProductosForm.setCodigoBusqueda("");
		busquedaProductosForm.setCodigoBarraBusqueda("");
		
		busquedaProductosMultiOfertasDispatchActions.cargaBusquedaProductosMultiOfertas(busquedaProductosForm, sess);		
	}
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void despachador() {
		
		Optional<String> tfam   = Optional.ofNullable(tipoFamiliaBean.getCodigo());
		Optional<String> fam    = Optional.ofNullable(familiaBean.getCodigo());
		Optional<String> subfam = Optional.ofNullable(subFamiliaBean.getCodigo());
		Optional<String> grufam = Optional.ofNullable(grupoFamiliaBean.getCodigo());		
		Optional<String> codbus = Optional.ofNullable(busquedaProductosForm.getCodigoBusqueda());
		Optional<String> codbusbar = Optional.ofNullable(busquedaProductosForm.getCodigoBarraBusqueda());	
		
		busquedaProductosForm.setTipofamilia(tfam.orElse("0"));
		busquedaProductosForm.setFamilia(fam.orElse("0"));
		busquedaProductosForm.setSubFamilia(subfam.orElse("0"));
		busquedaProductosForm.setGrupo(grufam.orElse("0"));
		busquedaProductosForm.setCodigoBusqueda(codbus.orElse(""));
		busquedaProductosForm.setCodigoBarraBusqueda(codbusbar.orElse(""));			
		
		if (tipoFamiliaBean.getCodigo().equals("C") || tipoFamiliaBean.getCodigo().equals("L")) {			
			if (!ojoDerecho && !ojoIzquierdo) {				
				Messagebox.show("Debe seleccionar un ojo, para realizar la busqueda.");
				return;
			}		
		}
		
		if (tipoFamiliaBean.getCodigo().equals("C")) {				
			if (busquedaProductosForm.getFamilia().equals("0") || busquedaProductosForm.getSubFamilia().equals("0") || busquedaProductosForm.getGrupo().equals("0")) {				
				Messagebox.show("Debes seleccionar todos los filtros");
				return;
			} 
			
		}		
			
		
		if(isOjoDerecho())
			busquedaProductosForm.setOjo("derecho");
		
		if(isOjoIzquierdo())
			busquedaProductosForm.setOjo("izquierdo");
		
		busquedaProductosForm.setAccion("buscar");
		busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
		
		busquedaProductosForm.setCodigoBusqueda("");
		busquedaProductosForm.setCodigoBarraBusqueda("");	
		
	}
	
	
	@NotifyChange({"busquedaProductosForm","grupoFamiliaBean","subFamiliaBean","familiaBean"})
	@Command
	public void pasarProductoMultioferta(@BindingParam("producto")ProductosBean producto, @BindingParam("index")int index) {		
		
		if (busquedaProductosForm.isChk_cerca())
			busquedaProductosForm.setDescripcion(Constantes.STRING_CERCA_OPT);
		
		if (!busquedaProductosForm.isChk_cerca())
			busquedaProductosForm.setDescripcion(Constantes.STRING_LEJOS_OPT);	
		
		if(isOjoDerecho())
			busquedaProductosForm.setOjo("derecho");
		
		if(isOjoIzquierdo())
			busquedaProductosForm.setOjo("izquierdo");
		
		busquedaProductosForm.setCodigoBusqueda(producto.getCod_barra());		
		busquedaProductosForm.setProducto(producto.getCodigo());
		busquedaProductosForm.setCantidad(1);	
		
		//antes de pasar el producto debo validar el grupo
		//
		actualiza_grupo(producto.getIndexMulti(), index);
		
		busquedaProductosForm.setAccion(Constantes.STRING_PASAR_MULTIOFERTA);		
		busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
		
		//inicializo
		busquedaProductosForm.setCodigoBusqueda("");
		busquedaProductosForm.setCodigoBarraBusqueda("");		
	}	
	
	
	@NotifyChange({"tipoFamiliaBean","familiaBean","subFamiliaBean","grupoFamiliaBean"})
	@Command
	public void comboSetNull(@BindingParam("objetoBean")Object arg) {
		
		if (arg instanceof TipoFamiliaBean) 
			tipoFamiliaBean=null;
		
		if (arg instanceof FamiliaBean) 
			familiaBean=null;			
		
		if (arg instanceof SubFamiliaBean)
			subFamiliaBean=null;				
		
		if (arg instanceof GrupoFamiliaBean) 
			grupoFamiliaBean=null;				
			
	}
	
	
	//@NotifyChange({"busquedaProductosForm","verGraduacion"})
	@NotifyChange("*")
	@Command
	public void manejoComboBox(@BindingParam("accion")String arg) {	
			
		
		if (arg.equals(Constantes.STRING_TIPO_FAMILIA)) {
			busquedaProductosForm.setAccion(Constantes.STRING_TIPO_FAMILIA);
			busquedaProductosForm.setTipofamilia(tipoFamiliaBean.getCodigo());					
			
			busquedaProductosForm.setCodigoBarraBusqueda("");
			busquedaProductosForm.setCodigoBusqueda("");
			
			//al cambiar el padre se limpia la busqueda previamente ejecutada			
			busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
			busquedaProductosForm.setListaFamilias(new ArrayList<FamiliaBean>());
			busquedaProductosForm.setListaSubFamilias(new ArrayList<SubFamiliaBean>());
			busquedaProductosForm.setListaGruposFamilias(new ArrayList<GrupoFamiliaBean>());
			
			busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
			
			posicionaCombo(4);
		}
		
		if (arg.equals(Constantes.STRING_FAMILIA)) {
			busquedaProductosForm.setAccion(Constantes.STRING_FAMILIA);
			busquedaProductosForm.setTipofamilia(tipoFamiliaBean.getCodigo());
			busquedaProductosForm.setFamilia(familiaBean.getCodigo());				
			
			busquedaProductosForm.setCodigoBarraBusqueda("");
			busquedaProductosForm.setCodigoBusqueda("");
			
			//al cambiar el padre se limpia la busqueda previamente ejecutada			
			busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
			busquedaProductosForm.setListaSubFamilias(new ArrayList<SubFamiliaBean>());
			busquedaProductosForm.setListaGruposFamilias(new ArrayList<GrupoFamiliaBean>());
			
			busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
			
			posicionaCombo(3);
		}
		
		if (arg.equals(Constantes.STRING_SUBFAMILIA)) {
			busquedaProductosForm.setAccion(Constantes.STRING_SUBFAMILIA);
			busquedaProductosForm.setTipofamilia(tipoFamiliaBean.getCodigo());
			busquedaProductosForm.setFamilia(familiaBean.getCodigo());
			busquedaProductosForm.setSubFamilia(subFamiliaBean.getCodigo());		
			
			busquedaProductosForm.setCodigoBarraBusqueda("");
			busquedaProductosForm.setCodigoBusqueda("");
			
			//al cambiar el padre se limpia la busqueda previamente ejecutada			
			busquedaProductosForm.setListaProductos(new ArrayList<ProductosBean>());
			busquedaProductosForm.setListaGruposFamilias(new ArrayList<GrupoFamiliaBean>());
			
			busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
			
			posicionaCombo(2);
		}	
		
		if (tipoFamiliaBean.getCodigo().equals("C") || tipoFamiliaBean.getCodigo().equals("L"))
			verGraduacion="true";
		else
			verGraduacion="false";
		
	}
	
	
	@NotifyChange({"*"})
	@Command
	public void posicionaCombo(int i) { 	
		
		FamiliaBean fBean = new FamiliaBean();
		SubFamiliaBean sfBean = new SubFamiliaBean();
		GrupoFamiliaBean gfBean = new GrupoFamiliaBean();
		
		fBean.setCodigo("0");
		fBean.setDescripcion("SELECCIONE");
		fBean.setTipo_fam("0");
		
		sfBean.setCodigo("0");
		sfBean.setDescripcion("SELECCIONE");
		
		gfBean.setCodigo("0");
		gfBean.setDescripcion("SELECCIONE");
		gfBean.setFamilia("0");
		gfBean.setSubfamilia("0");
		
		if (i >= 4) {			
			ArrayList<FamiliaBean> afBean = new ArrayList<FamiliaBean>();			
			afBean.add(fBean);
			
			ArrayList<FamiliaBean> newList = (ArrayList<FamiliaBean>) Stream.concat(afBean.stream(), busquedaProductosForm.getListaFamilias().stream()).collect(Collectors.toList());			
			busquedaProductosForm.setListaFamilias(newList);
			
			Optional<FamiliaBean> fb = busquedaProductosForm.getListaFamilias().stream().filter(s -> fBean.getCodigo().equals(s.getCodigo())).findFirst();
			familiaBean = fb.orElse(new FamiliaBean());
		}
		if (i >= 3) {			
			ArrayList<SubFamiliaBean> asfBean = new ArrayList<SubFamiliaBean>();
			asfBean.add(sfBean);
			
			ArrayList<SubFamiliaBean> newList = (ArrayList<SubFamiliaBean>) Stream.concat(asfBean.stream(), busquedaProductosForm.getListaSubFamilias().stream()).collect(Collectors.toList());
			busquedaProductosForm.setListaSubFamilias(newList);
			
			Optional<SubFamiliaBean> sfb = busquedaProductosForm.getListaSubFamilias().stream().filter(s -> sfBean.getCodigo().equals(s.getCodigo())).findFirst();
			subFamiliaBean = sfb.orElse(new SubFamiliaBean());
		}
		if (i >= 2) {			
			ArrayList<GrupoFamiliaBean> agfBean = new ArrayList<GrupoFamiliaBean>();
			agfBean.add(gfBean);
			
			ArrayList<GrupoFamiliaBean> newList = (ArrayList<GrupoFamiliaBean>) Stream.concat(agfBean.stream(), busquedaProductosForm.getListaGruposFamilias().stream()).collect(Collectors.toList());
			busquedaProductosForm.setListaGruposFamilias(newList);
			
			Optional<GrupoFamiliaBean> gfb = busquedaProductosForm.getListaGruposFamilias().stream().filter(s -> gfBean.getCodigo().equals(s.getCodigo())).findFirst();
			grupoFamiliaBean = gfb.orElse(new GrupoFamiliaBean());
		}
		
		
	}
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void AgregarSuplementos(@BindingParam("producto")ProductosBean producto,
								   @BindingParam("index")int index) {		
		
		// verificar si tiene suplementos
		busquedaProductosForm.setAccion("ver_Suplementos");		
		busquedaProductosForm.setAddProducto(String.valueOf(index));
		busquedaProductosForm.setIndex_multi(producto.getIndexMulti());
		busquedaProductosForm.setEstado("");
		busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
		
		if(isOjoDerecho())
			producto.setOjo("derecho");
		
		if(isOjoIzquierdo())
			producto.setOjo("izquierdo");
		
		sess.setAttribute(Constantes.STRING_PRODUCTO, producto);
		
		//producto con suplemento obligatorio
		
		//if (busquedaProductosForm.getEstado().equals(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO)) {
			
			objetos = new HashMap<String,Object>();		
			objetos.put("producto",producto);
			objetos.put("index",index);
			objetos.put("origen","MULTIOFERTA");
			objetos.put("busquedaProductos",busquedaProductosForm);
			
			Window windowAgregaSuplemento = (Window)Executions.createComponents(
	                "/zul/encargos/AgregaSuplemento.zul", null, objetos);
			
			windowAgregaSuplemento.doModal(); 			
		//}	
		
	}	
	
	
	//salir de la ventana y mantener la persistencia
	@Command
	public void salir(@BindingParam("win")Window win) {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("productosMulti",busquedaProductosForm.getListaProductosMultioferta());
		objetos.put("producto",productosBean);
		objetos.put("index",indiceGral);		
		
		BindUtils.postGlobalCommand(null, null, "actualizaListaProductosMulti", objetos);
		
		win.detach();
	}	
	
	
	//=========== Mantengo la persistencia de lista de splementos=======
	//==================================================================
	@NotifyChange({"busquedaProductosForm"})
	@GlobalCommand
	public void actulizaListaSuplementosMulti(@BindingParam("suplementos")ArrayList<SuplementopedidoBean> suplementos,
										 	  @BindingParam("producto")ProductosBean producto,
										 	  @BindingParam("index")int index) {		
		
		busquedaProductosForm.getListaProductosMultioferta().get(index).setListaSuplementos(suplementos);			
	} 
	
	
	
	//============ Validaciones varias ==================
	//===================================================
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void prueba(@BindingParam("grupo")String grupo , @BindingParam("index")int index) {
		
		//busquedaProductosForm.getListaProductos().get(index).setGrupo("0");
	}
	
	
	@NotifyChange({"busquedaProductosForm"})
	private void actualiza_grupo(int indexmulti, int index) {
		
		ProductosBean pb = busquedaProductosForm.getListaProductos().get(index);
		String [] grupos = new String[busquedaProductosForm.getListaProductos().size()];
		int i = 0;
		
		for(ProductosBean pbg : busquedaProductosForm.getListaProductos()) {			
			grupos[i] = pbg.getGrupo();
			busquedaProductosForm.getListaProductos().get(i).setGrupo("0");
			grupos[i] = "0";
			i++;
		}		
		
		busquedaProductosForm.setGrupos(grupos);		 
		
		/*if (pb.getGrupo().equals("")) 
			busquedaProductosForm.getListaProductos().get(index).setGrupo("0");*/
		
		busquedaProductosForm.setAccion("grupo");
		busquedaProductosForm.setAddProducto(String.valueOf(indexmulti));
		busquedaProductosForm.setIndexProductos(index);
		busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
		
	}
	
	//======= metodos getter and setter =================

	public BusquedaProductosForm getBusquedaProductosForm() {
		return busquedaProductosForm;
	}
	
	public void setBusquedaProductosForm(BusquedaProductosForm busquedaProductosForm) {
		this.busquedaProductosForm = busquedaProductosForm;
	}

	public TipoFamiliaBean getTipoFamiliaBean() {
		return tipoFamiliaBean;
	}

	public void setTipoFamiliaBean(TipoFamiliaBean tipoFamiliaBean) {
		this.tipoFamiliaBean = tipoFamiliaBean;
	}

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

	public boolean isCerca() {
		return cerca;
	}

	public void setCerca(boolean cerca) {
		this.cerca = cerca;
	}

	public String getVerGraduacion() {
		return verGraduacion;
	}

	public void setVerGraduacion(String verGraduacion) {
		this.verGraduacion = verGraduacion;
	}
	

}
