package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

public class EntregaPedidoForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7578079851245881906L;
	
	private String codigo_suc;
	private String codigo;
	private String cdg_vta_pedido;
	private String cdg_busqueda;
	private String fecha_pedido;
	private String fecha_entrega;
	private String cliente;
	private String dv_cliente;
	private String nombre_cliente;
	private String cod_cliente;
	private String agente;
	private String accion;
	private ArrayList<VentaPedidoBean> listaPedidos;
	private String pagina;
	private String hora;
	private String sobre;
	private String divisa;
	private String forma_pago;
	private String idioma;
	private String tipo_pedido;
	private String convenio;
	private String promocion;
	private String cambio;
	private String productos;

	private String nota;
	
	private long subTotal;
	private long descuento;
	private int dtcoPorcentaje;
	private long total;
	private long anticipo;
	private long totalPendiante;
	private int porcentaje_anticipo;
	private int porcentaje_descuento_max;
	private int caja;
	
	private  ArrayList<DivisaBean> listaDivisas;
    private ArrayList<ConvenioBean> listaConvenios;
    private ArrayList<AgenteBean> listaAgentes;
    private ArrayList<IdiomaBean> listaIdiomas;
    private ArrayList<FormaPagoBean>  listaFormasPago;
    private ArrayList<PromocionBean> listaPromociones;
    private ArrayList<ProductosBean> listaProductos;
    private ArrayList<TipoPedidoBean> listaTiposPedidos;
	    
    // informacion graduacion;
    private GraduacionesBean graduacion;
    
    
    
    
	public GraduacionesBean getGraduacion() {
		return graduacion;
	}
	public void setGraduacion(GraduacionesBean graduacion) {
		this.graduacion = graduacion;
	}
	public int getCaja() {
		return caja;
	}
	public void setCaja(int caja) {
		this.caja = caja;
	}
	public int getPorcentaje_descuento_max() {
		return porcentaje_descuento_max;
	}
	public void setPorcentaje_descuento_max(int porcentaje_descuento_max) {
		this.porcentaje_descuento_max = porcentaje_descuento_max;
	}
	public long getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(long subTotal) {
		this.subTotal = subTotal;
	}
	public long getDescuento() {
		return descuento;
	}
	public void setDescuento(long descuento) {
		this.descuento = descuento;
	}
	public int getDtcoPorcentaje() {
		return dtcoPorcentaje;
	}
	public void setDtcoPorcentaje(int dtcoPorcentaje) {
		this.dtcoPorcentaje = dtcoPorcentaje;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getAnticipo() {
		return anticipo;
	}
	public void setAnticipo(long anticipo) {
		this.anticipo = anticipo;
	}
	public long getTotalPendiante() {
		return totalPendiante;
	}
	public void setTotalPendiante(long totalPendiante) {
		this.totalPendiante = totalPendiante;
	}
	public int getPorcentaje_anticipo() {
		return porcentaje_anticipo;
	}
	public void setPorcentaje_anticipo(int porcentaje_anticipo) {
		this.porcentaje_anticipo = porcentaje_anticipo;
	}
	public String getProductos() {
		return productos;
	}
	public void setProductos(String productos) {
		this.productos = productos;
	}
	public String getCambio() {
		return cambio;
	}
	public void setCambio(String cambio) {
		this.cambio = cambio;
	}
	public String getPagina() {
		return pagina;
	}
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	public String getCdg_busqueda() {
		return cdg_busqueda;
	}
	public void setCdg_busqueda(String cdg_busqueda) {
		this.cdg_busqueda = cdg_busqueda;
	}
	public String getDv_cliente() {
		return dv_cliente;
	}
	public void setDv_cliente(String dv_cliente) {
		this.dv_cliente = dv_cliente;
	}
	public String getNombre_cliente() {
		return nombre_cliente;
	}
	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}
	public String getCod_cliente() {
		return cod_cliente;
	}
	public void setCod_cliente(String cod_cliente) {
		this.cod_cliente = cod_cliente;
	}
	public String getFecha_entrega() {
		return fecha_entrega;
	}
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	public String getCdg_vta_pedido() {
		return cdg_vta_pedido;
	}
	public void setCdg_vta_pedido(String cdg_vta_pedido) {
		this.cdg_vta_pedido = cdg_vta_pedido;
	}
	public String getFecha_pedido() {
		return fecha_pedido;
	}
	public void setFecha_pedido(String fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public ArrayList<VentaPedidoBean> getListaPedidos() {
		return listaPedidos;
	}
	public void setListaPedidos(ArrayList<VentaPedidoBean> listaPedidos) {
		this.listaPedidos = listaPedidos;
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
	public String getCodigo_suc() {
		return codigo_suc;
	}
	public void setCodigo_suc(String codigo_suc) {
		this.codigo_suc = codigo_suc;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getSobre() {
		return sobre;
	}
	public void setSobre(String sobre) {
		this.sobre = sobre;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
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
	public String getTipo_pedido() {
		return tipo_pedido;
	}
	public void setTipo_pedido(String tipo_pedido) {
		this.tipo_pedido = tipo_pedido;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	public String getPromocion() {
		return promocion;
	}
	public void setPromocion(String promocion) {
		this.promocion = promocion;
	}

	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	
}
