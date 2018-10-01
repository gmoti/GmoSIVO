/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;

/**
 *
 * @author Advice70
 */
public class BusquedaProductosForm extends GenericForm{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8843759622770274837L;
	private ArrayList<FamiliaBean> listaFamilias;
    private ArrayList<SubFamiliaBean> listaSubFamilias;
    private ArrayList<GrupoFamiliaBean> listaGruposFamilias;
    private ArrayList<ProveedorBean> listaProveedores;
    private ArrayList<TipoFamiliaBean> listaTipoFamilia;
    private String familia;
    private String subFamilia;
    private String grupo;
    private String[] grupos;
    private String proveedor;
    private String fabricante;
    private String descripcion;
    private ArrayList<ProductosBean> listaProductos;
    private String producto;
    private String accion;
    private int cantidad;
    private String codigoBusqueda;
    private String codigoBarraBusqueda;
    private String codigoMultioferta;
    private String tipofamilia;   
    private ArrayList<ProductosBean> listaMultioferta;
    private ArrayList<ProductosBean> listaProductosMultioferta;
    private ArrayList<GraduacionesBean> listaGraduaciones;
    private String erroMultioferta;
    private String productoEliminar;
    private String ojo;
    private int index_graduacion;
    private GraduacionesBean graduacion;
    private ContactologiaBean graduacion_lentilla;
    private boolean chk_cerca = false;
    private int index_multi;
	private int index_producto_multi;
	private String form_origen;
	private String cliente;
	private String fecha_graduacion;
	private double numero_graduacion;
	private String estado;
	private String estadoCercaMulti;
	private String addProducto;
	private int indexProductos;
	private String cantidad_prod;
	private String bloquea;
	private String estadoEncargo;
	private String cdg;
	private String tienePagos;
	private String codigo_barras;
	
	
	/*public BusquedaProductosForm() {
		
		 ArrayList<FamiliaBean> listaFamilias =new ArrayList<FamiliaBean>();
	     ArrayList<SubFamiliaBean> listaSubFamilias =new ArrayList<SubFamiliaBean>();
	     ArrayList<GrupoFamiliaBean> listaGruposFamilias =new ArrayList<GrupoFamiliaBean>();
	     ArrayList<ProveedorBean> listaProveedores =new ArrayList<ProveedorBean>();
	     ArrayList<TipoFamiliaBean> listaTipoFamilia =new ArrayList<TipoFamiliaBean>();
	     ArrayList<ProductosBean> listaProductos =new ArrayList<ProductosBean>();
	     ArrayList<ProductosBean> listaMultioferta =new ArrayList<ProductosBean>();
	     ArrayList<ProductosBean> listaProductosMultioferta =new ArrayList<ProductosBean>();
	     ArrayList<GraduacionesBean> listaGraduaciones=new  ArrayList<GraduacionesBean>();
		
	}*/
	
	public String getTienePagos() {
		return tienePagos;
	}

	public void setTienePagos(String tienePagos) {
		this.tienePagos = tienePagos;
	}

	public String getCdg() {
		return cdg;
	}

	public void setCdg(String cdg) {
		this.cdg = cdg;
	}

	public String getEstadoEncargo() {
		return estadoEncargo;
	}

	public void setEstadoEncargo(String estadoEncargo) {
		this.estadoEncargo = estadoEncargo;
	}

	public String getBloquea() {
		return bloquea;
	}

	public void setBloquea(String bloquea) {
		this.bloquea = bloquea;
	}

	public String getCantidad_prod() {
		return cantidad_prod;
	}

	public void setCantidad_prod(String cantidad_prod) {
		this.cantidad_prod = cantidad_prod;
	}

	public ContactologiaBean getGraduacion_lentilla() {
		return graduacion_lentilla;
	}

	public void setGraduacion_lentilla(ContactologiaBean graduacion_lentilla) {
		this.graduacion_lentilla = graduacion_lentilla;
	}

	public int getIndexProductos() {
		return indexProductos;
	}

	public void setIndexProductos(int indexProductos) {
		this.indexProductos = indexProductos;
	}

	public String[] getGrupos() {
		return grupos;
	}

	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}

	public String getAddProducto() {
		return addProducto;
	}

	public void setAddProducto(String addProducto) {
		this.addProducto = addProducto;
	}

	public String getEstadoCercaMulti() {
		return estadoCercaMulti;
	}

	public void setEstadoCercaMulti(String estadoCercaMulti) {
		this.estadoCercaMulti = estadoCercaMulti;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getNumero_graduacion() {
		return numero_graduacion;
	}

	public void setNumero_graduacion(double numero_graduacion) {
		this.numero_graduacion = numero_graduacion;
	}

	public String getFecha_graduacion() {
		return fecha_graduacion;
	}

	public void setFecha_graduacion(String fecha_graduacion) {
		this.fecha_graduacion = fecha_graduacion;
	}	

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getForm_origen() {
		return form_origen;
	}

	public void setForm_origen(String form_origen) {
		this.form_origen = form_origen;
	}

	public int getIndex_producto_multi() {
		return index_producto_multi;
	}

	public void setIndex_producto_multi(int index_producto_multi) {
		this.index_producto_multi = index_producto_multi;
	}

	public int getIndex_multi() {
		return index_multi;
	}

	public void setIndex_multi(int index_multi) {
		this.index_multi = index_multi;
	}

	public boolean isChk_cerca() {
		return chk_cerca;
	}

	public void setChk_cerca(boolean chk_cerca) {
		this.chk_cerca = chk_cerca;
	}

	public int getIndex_graduacion() {
		return index_graduacion;
	}

	public void setIndex_graduacion(int index_graduacion) {
		this.index_graduacion = index_graduacion;
	}

	public GraduacionesBean getGraduacion() {
		return graduacion;
	}

	public void setGraduacion(GraduacionesBean graduacion) {
		this.graduacion = graduacion;
	}

	public ArrayList<GraduacionesBean> getListaGraduaciones() {
		return listaGraduaciones;
	}

	public void setListaGraduaciones(ArrayList<GraduacionesBean> listaGraduaciones) {
		this.listaGraduaciones = listaGraduaciones;
	}

	public String getOjo() {
		return ojo;
	}

	public void setOjo(String Ojo) {
		ojo = Ojo;
	}

	public String getCodigoBarraBusqueda() {
		return codigoBarraBusqueda;
	}

	public void setCodigoBarraBusqueda(String codigoBarraBusqueda) {
		this.codigoBarraBusqueda = codigoBarraBusqueda;
	}

	public String getCodigoBusqueda() {
		return codigoBusqueda;
	}

	public void setCodigoBusqueda(String codigoBusqueda) {
		this.codigoBusqueda = codigoBusqueda;
	}

	public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAccion() {
        return accion;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setListaProductos(ArrayList<ProductosBean> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<ProductosBean> getListaProductos() {
        return listaProductos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    

    public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public ArrayList<FamiliaBean> getListaFamilias() {
        return listaFamilias;
    }

    public void setListaFamilias(ArrayList<FamiliaBean> listaFamilias) {
        this.listaFamilias = listaFamilias;
    }

    public ArrayList<GrupoFamiliaBean> getListaGruposFamilias() {
        return listaGruposFamilias;
    }

    public void setListaGruposFamilias(ArrayList<GrupoFamiliaBean> listaGruposFamilias) {
        this.listaGruposFamilias = listaGruposFamilias;
    }

    public ArrayList<ProveedorBean> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(ArrayList<ProveedorBean> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public ArrayList<SubFamiliaBean> getListaSubFamilias() {
        return listaSubFamilias;
    }

    public void setListaSubFamilias(ArrayList<SubFamiliaBean> listaSubFamilias) {
        this.listaSubFamilias = listaSubFamilias;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getSubFamilia() {
        return subFamilia;
    }

    public void setSubFamilia(String subFamilia) {
        this.subFamilia = subFamilia;
    }

	public String getCodigoMultioferta() {
		return codigoMultioferta;
	}

	public void setCodigoMultioferta(String codigoMultioferta) {
		this.codigoMultioferta = codigoMultioferta;
	}

	public ArrayList<TipoFamiliaBean> getListaTipoFamilia() {
		return listaTipoFamilia;
	}

	public void setListaTipoFamilia(ArrayList<TipoFamiliaBean> listaTipoFamilia) {
		this.listaTipoFamilia = listaTipoFamilia;
	}

	public String getTipofamilia() {
		return tipofamilia;
	}

	public void setTipofamilia(String tipofamilia) {
		this.tipofamilia = tipofamilia;
	}
	
	public ArrayList<ProductosBean> getListaMultioferta() {
		return listaMultioferta;
	}

	public void setListaMultioferta(ArrayList<ProductosBean> listaMultioferta) {
		this.listaMultioferta = listaMultioferta;
	}

	public ArrayList<ProductosBean> getListaProductosMultioferta() {
		return listaProductosMultioferta;
	}

	public void setListaProductosMultioferta(
			ArrayList<ProductosBean> listaProductosMultioferta) {
		this.listaProductosMultioferta = listaProductosMultioferta;
	}

	public String getErroMultioferta() {
		return erroMultioferta;
	}

	public void setErroMultioferta(String erroMultioferta) {
		this.erroMultioferta = erroMultioferta;
	}

	public String getProductoEliminar() {
		return productoEliminar;
	}

	public void setProductoEliminar(String productoEliminar) {
		this.productoEliminar = productoEliminar;
	}
	
	public String getCodigo_barras() {
		return codigo_barras;
	}

	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras;
	}
	
	public void cleanForm() {
		this.listaFamilias = null;
		this.listaSubFamilias = null;
		this.listaGruposFamilias = null;
		this.listaProveedores = null;
		this.listaTipoFamilia = null;
		this.familia = null;
		this.subFamilia = null;
		this.grupo = null;
		this.proveedor = null;
		this.fabricante = null;
		this.descripcion = null;
		this.listaProductos = null;
		this.producto = null;
		this.accion = null;
		this.cantidad = 1;
		this.codigoBusqueda = null;
		this.codigoBarraBusqueda = null;
		this.codigoMultioferta = null;
		this.tipofamilia = null;
		this.listaMultioferta = null;
		this.listaProductosMultioferta = null;
		this.listaGraduaciones = null;
		this.erroMultioferta = null;
		this.productoEliminar = null;
		this.ojo = "";
		this.index_graduacion = 0;
		this.graduacion = null;
		this.graduacion_lentilla = null;
		this.chk_cerca = false;
		this.cantidad_prod = Constantes.STRING_FALSE;
		this.bloquea="";
		this.estadoEncargo="";
		this.cdg="";
		this.tienePagos="";
	}
	
}
