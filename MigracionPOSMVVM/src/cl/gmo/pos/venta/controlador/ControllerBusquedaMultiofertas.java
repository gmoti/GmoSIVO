package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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
	
	
	@Init
	public void inicial(@ExecutionArgParam("busquedaProductos")BusquedaProductosForm arg,
						@ExecutionArgParam("origen")String arg1,
						@ExecutionArgParam("beanProducto")ProductosBean arg2){
						//@ExecutionArgParam("ventaPedido")VentaPedidoForm arg3) {	
		
		busquedaProductosMultiOfertasDispatchActions = new BusquedaProductosMultiOfertasDispatchActions();
		
		tipoFamiliaBean = new TipoFamiliaBean();
		familiaBean     = new FamiliaBean();
		subFamiliaBean  = new SubFamiliaBean();
		grupoFamiliaBean= new GrupoFamiliaBean();
		productosBean   = new ProductosBean();	
		
		busquedaProductosForm = arg;
		productosBean = arg2;
		
		ojoDerecho = false;
		ojoIzquierdo= false;
		cerca = false;
		verGraduacion="false";	
		
		busquedaProductosMultiOfertasDispatchActions.cargaBusquedaProductosMultiOfertas(busquedaProductosForm, sess);
	}
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void buscarProducto() {
		
		Optional<TipoFamiliaBean>  tfam   = Optional.ofNullable(tipoFamiliaBean);
		Optional<FamiliaBean>      fam    = Optional.ofNullable(familiaBean);
		Optional<SubFamiliaBean>   subfam = Optional.ofNullable(subFamiliaBean);
		Optional<GrupoFamiliaBean> grufam = Optional.ofNullable(grupoFamiliaBean);
		
		
		if (tfam.isPresent())		
			busquedaProductosForm.setTipofamilia(tfam.get().getCodigo());
		else
			busquedaProductosForm.setTipofamilia("");
		
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
			busquedaProductosForm.setGrupo("0");
		
		
		
		if (tipoFamiliaBean.getCodigo().equals("C") || tipoFamiliaBean.getCodigo().equals("L")) {			
			if (!ojoDerecho && !ojoIzquierdo) {				
				Messagebox.show("Debe seleccionar un ojo, para realizar la busqueda.");
				return;
			}		
		}
		
		if (tipoFamiliaBean.getCodigo().equals("C")) {				
			if (busquedaProductosForm.getFamilia().equals("") || busquedaProductosForm.getSubFamilia().equals("") || busquedaProductosForm.getGrupo().equals("")) {				
				Messagebox.show("Debes seleccionar todos los filtros");
				return;
			} 
			
		}	
		
		
			
		busquedaProductosForm.setOjo((ojoDerecho?"derecho":"izquierdo"));
		//busquedaProductosForm.setCodigoBusqueda("");
		//busquedaProductosForm.setCodigoBarraBusqueda("");
		//busquedaProductosForm.setDescripcion("");
			
		
		busquedaProductosForm.setAccion("buscar");
		busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);		
	}
	
	
	@NotifyChange({"busquedaProductosForm"})
	@Command
	public void pasarProductoMultioferta(@BindingParam("producto")ProductosBean producto) {
		
		ArrayList<ProductosBean> prod;
		
		prod = busquedaProductosForm.getListaProductosMultioferta();
		
		Optional<ArrayList<ProductosBean>> p = Optional.ofNullable(prod);
		
		if (p.isPresent()) {
			producto.setCantidad(1);
			prod.add(producto);
		}	
		else {
			prod = new ArrayList<ProductosBean>();
			producto.setCantidad(1);
			prod.add(producto);
		}		
		
		busquedaProductosForm.setAccion(Constantes.STRING_PASAR_MULTIOFERTA);	
		busquedaProductosForm.setProducto(producto.getCod_barra());
		
		busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
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
	
	
	@NotifyChange({"busquedaProductosForm","verGraduacion"})
	@Command
	public void manejoComboBox(@BindingParam("accion")String arg) {
		
		
		if (arg.equals(Constantes.STRING_TIPO_FAMILIA)) {
			busquedaProductosForm.setAccion(Constantes.STRING_TIPO_FAMILIA);
			busquedaProductosForm.setTipofamilia(tipoFamiliaBean.getCodigo());
			busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);			
		}
		
		if (arg.equals(Constantes.STRING_FAMILIA)) {
			busquedaProductosForm.setAccion(Constantes.STRING_FAMILIA);
			busquedaProductosForm.setTipofamilia(tipoFamiliaBean.getCodigo());
			busquedaProductosForm.setFamilia(familiaBean.getCodigo());
			busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);			
		}
		
		if (arg.equals(Constantes.STRING_SUBFAMILIA)) {
			busquedaProductosForm.setAccion(Constantes.STRING_SUBFAMILIA);
			busquedaProductosForm.setTipofamilia(tipoFamiliaBean.getCodigo());
			busquedaProductosForm.setFamilia(familiaBean.getCodigo());
			busquedaProductosForm.setSubFamilia(subFamiliaBean.getCodigo());
			busquedaProductosMultiOfertasDispatchActions.buscarMultioferta(busquedaProductosForm, sess);
		}	
		
		if (tipoFamiliaBean.getCodigo().equals("C") || tipoFamiliaBean.getCodigo().equals("L"))
			verGraduacion="true";
		else
			verGraduacion="false";
		
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
		objetos.put("index",0);		
		
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
		
		busquedaProductosForm.getListaProductos().get(index).setGrupo("0");
	}
	
	
	private void actualiza_grupo() {}
	
	
	/*
	function verificaNumero(campo){
     	if (campo.value == "") {
			campo.value = "0";
		}
     }
	
	function actualiza_grupo(indexmulti, index)
	{					
		document.getElementById('accion').value = "grupo";
		document.getElementById('productoSeleccionadoSuplemento').value = indexmulti;
		document.getElementById("indexProductos").value = index;
		document.busquedaProductosForm.submit();	        	
    }
	
*/
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
