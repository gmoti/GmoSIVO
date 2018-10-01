package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;

public class PresupuestoForm extends GenericForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1816703902931014581L;
	private  ArrayList<DivisaBean> listaDivisas;
    private ArrayList<ConvenioBean> listaConvenios;
    private ArrayList<AgenteBean> listaAgentes;
    private ArrayList<IdiomaBean> listaIdiomas;
    private ArrayList<FormaPagoBean>  listaFormasPago;
    private ArrayList<PromocionBean> listaPromociones;
    private ArrayList<ProductosBean> listaProductos;
    private ArrayList<TipoPedidoBean> listaTiposPedidos;
    private String tipo_presupuesto;
    private String accion;
	private String addProducto;
	private String presupuesto;
	private String codigo_suc;
	private int sobre;
	private int cantidad;
    private String forma_pago;
    private String idioma;
    private String agente;
    private String convenio;
    private String convenio_det;
    private String divisa;
    private String promocion;
    private String fecha;
    private String fecha_entrega;
    private String descripcion = Constantes.STRING_BLANCO;
    private String hora;
    private int cambio;
    private String codigo;
    private String cliente;
    private String nombre_cliente;
    private String nota;
    private double subTotal;
    private double descuento;
    private double dtcoPorcentaje;
    private double total;
    private double anticipo;
    private double totalPendiante;
    private GraduacionesBean graduacion;
    private ContactologiaBean graduacion_lentilla;
    private String ojo;
    private String estado = "venta";
    private ArrayList<PresupuestosBean> listaPresupuestos;
    private String flujo = "formulario";
    private String error = "error";
    private boolean cerrado;
    private String tipo ="";
    private int convenio_ln;
    private String descuento_autoriza = Constantes.STRING_BLANCO;
    private int porcentaje_descuento_max;
    private String supervisor = Constantes.STRING_BLANCO;
    private int cantidad_linea = Constantes.INT_CERO;
    private double cantidad_descuento = Constantes.INT_CERO;    
  //Informacion del cliente.
	private String nif;
	private String dvnif;
	
	//FQuiroz 
	//06/08/2018 Se agrega contructor
	public PresupuestoForm() {
		
		listaDivisas = new ArrayList<DivisaBean>();
		listaConvenios = new ArrayList<ConvenioBean>();
		listaAgentes = new ArrayList<AgenteBean>();
		listaIdiomas = new ArrayList<IdiomaBean>();
		listaFormasPago = new ArrayList<FormaPagoBean>();
		listaPromociones = new ArrayList<PromocionBean>();
		listaProductos = new ArrayList<ProductosBean>();
		listaTiposPedidos = new ArrayList<TipoPedidoBean>();
		
		subTotal = Constantes.INT_CERO;
	    descuento = Constantes.INT_CERO;
	    dtcoPorcentaje = Constantes.INT_CERO;
	    total = Constantes.INT_CERO;
	    anticipo = Constantes.INT_CERO;
	    totalPendiante = Constantes.INT_CERO;
	    cantidad_descuento = Constantes.INT_CERO;
	    sobre = Constantes.INT_CERO;
		cantidad = Constantes.INT_CERO;
		
	}
	
    
	public ContactologiaBean getGraduacion_lentilla() {
		return graduacion_lentilla;
	}
	
	public int getCantidad_linea() {
		return cantidad_linea;
	}

	public void setCantidad_linea(int cantidad_linea) {
		this.cantidad_linea = cantidad_linea;
	}

	public void setGraduacion_lentilla(ContactologiaBean graduacion_lentilla) {
		this.graduacion_lentilla = graduacion_lentilla;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public int getConvenio_ln() {
		return convenio_ln;
	}

	public void setConvenio_ln(int convenio_ln) {
		this.convenio_ln = convenio_ln;
	}

	public String getDescuento_autoriza() {
		return descuento_autoriza;
	}

	public void setDescuento_autoriza(String descuento_autoriza) {
		this.descuento_autoriza = descuento_autoriza;
	}

	public int getPorcentaje_descuento_max() {
		return porcentaje_descuento_max;
	}

	public void setPorcentaje_descuento_max(int porcentaje_descuento_max) {
		this.porcentaje_descuento_max = porcentaje_descuento_max;
	}

	public String getConvenio_det() {
		return convenio_det;
	}

	public void setConvenio_det(String convenio_det) {
		this.convenio_det = convenio_det;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDvnif() {
		return dvnif;
	}

	public void setDvnif(String dvnif) {
		this.dvnif = dvnif;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isCerrado() {
		return cerrado;
	}
	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
	}
	public String getFlujo() {
		return flujo;
	}
	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ArrayList<PresupuestosBean> getListaPresupuestos() {
		return listaPresupuestos;
	}
	public void setListaPresupuestos(ArrayList<PresupuestosBean> listaPresupuestos) {
		this.listaPresupuestos = listaPresupuestos;
	}
	public ArrayList<DivisaBean> getListaDivisas() {
		return listaDivisas;
	}
	public void setListaDivisas(ArrayList<DivisaBean> listaDivisas) {
		this.listaDivisas = listaDivisas;
	}
	public ArrayList<ConvenioBean> getListaConvenios() {
		return listaConvenios;
	}
	public void setListaConvenios(ArrayList<ConvenioBean> listaConvenios) {
		this.listaConvenios = listaConvenios;
	}
	public ArrayList<AgenteBean> getListaAgentes() {
		return listaAgentes;
	}
	public void setListaAgentes(ArrayList<AgenteBean> listaAgentes) {
		this.listaAgentes = listaAgentes;
	}
	public ArrayList<IdiomaBean> getListaIdiomas() {
		return listaIdiomas;
	}
	public void setListaIdiomas(ArrayList<IdiomaBean> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}
	public ArrayList<FormaPagoBean> getListaFormasPago() {
		return listaFormasPago;
	}
	public void setListaFormasPago(ArrayList<FormaPagoBean> listaFormasPago) {
		this.listaFormasPago = listaFormasPago;
	}
	public ArrayList<PromocionBean> getListaPromociones() {
		return listaPromociones;
	}
	public void setListaPromociones(ArrayList<PromocionBean> listaPromociones) {
		this.listaPromociones = listaPromociones;
	}
	public ArrayList<ProductosBean> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(ArrayList<ProductosBean> listaProductos) {
		this.listaProductos = listaProductos;
	}
	public ArrayList<TipoPedidoBean> getListaTiposPedidos() {
		return listaTiposPedidos;
	}
	public void setListaTiposPedidos(ArrayList<TipoPedidoBean> listaTiposPedidos) {
		this.listaTiposPedidos = listaTiposPedidos;
	}
	public String getTipo_presupuesto() {
		return tipo_presupuesto;
	}
	public void setTipo_presupuesto(String tipo_presupuesto) {
		this.tipo_presupuesto = tipo_presupuesto;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getAddProducto() {
		return addProducto;
	}
	public void setAddProducto(String addProducto) {
		this.addProducto = addProducto;
	}
	public String getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}
	public String getCodigo_suc() {
		return codigo_suc;
	}
	public void setCodigo_suc(String codigo_suc) {
		this.codigo_suc = codigo_suc;
	}
	public int getSobre() {
		return sobre;
	}
	public void setSobre(int sobre) {
		this.sobre = sobre;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getPromocion() {
		return promocion;
	}
	public void setPromocion(String promocion) {
		this.promocion = promocion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFecha_entrega() {
		return fecha_entrega;
	}
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getCambio() {
		return cambio;
	}
	public void setCambio(int cambio) {
		this.cambio = cambio;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNombre_cliente() {
		return nombre_cliente;
	}
	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public double getDtcoPorcentaje() {
		return dtcoPorcentaje;
	}
	public void setDtcoPorcentaje(double dtcoPorcentaje) {
		this.dtcoPorcentaje = dtcoPorcentaje;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(double anticipo) {
		this.anticipo = anticipo;
	}
	public double getTotalPendiante() {
		return totalPendiante;
	}
	public void setTotalPendiante(double totalPendiante) {
		this.totalPendiante = totalPendiante;
	}
	public GraduacionesBean getGraduacion() {
		return graduacion;
	}
	public void setGraduacion(GraduacionesBean graduacion) {
		this.graduacion = graduacion;
	}
	public String getOjo() {
		return ojo;
	}
	public void setOjo(String ojo) {
		this.ojo = ojo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void cleanForm() {
		this.listaDivisas = null;
		this.listaConvenios = null;
		this.listaAgentes = null;
		this.listaIdiomas = null;
		this.listaFormasPago = null;
		this.listaPromociones = null;
		this.listaProductos = null;
		this.listaTiposPedidos = null;
		this.tipo_presupuesto = "";
		this.accion = "";
		this.addProducto = "";
		this.presupuesto = "";
		this.codigo_suc = "";
		this.sobre = 0;
		this.cantidad = 0;
		this.forma_pago = "";
		this.idioma = "";
		this.agente = "";
		this.convenio = "";
		this.divisa = "";
		this.promocion = "";
		this.fecha = "";
		this.fecha_entrega = "";
		this.hora = "";
		this.cambio = 0;
		this.codigo = "";
		//this.cliente = "";
		//this.nombre_cliente = "";
		this.nota = "";
		this.subTotal = 0;
		this.descuento = 0;
		this.dtcoPorcentaje = 0;
		this.total = 0;
		this.anticipo = 0;
		this.totalPendiante = 0;
		//this.graduacion = null;
		this.estado = "venta";
	    this.error = "error";
	    this.cerrado = false;
	    this.convenio_det = "";
	    this.flujo = "formulario";
	    this.tipo ="";
	    this.convenio_ln= Constantes.INT_CERO;
	    this.descuento_autoriza= Constantes.STRING_BLANCO;
	    this.porcentaje_descuento_max= Constantes.INT_CERO;
	    this.supervisor = Constantes.STRING_BLANCO;
	}

	public double getCantidad_descuento() {
		return cantidad_descuento;
	}

	public void setCantidad_descuento(double cantidad_descuento) {
		this.cantidad_descuento = cantidad_descuento;
	}
}