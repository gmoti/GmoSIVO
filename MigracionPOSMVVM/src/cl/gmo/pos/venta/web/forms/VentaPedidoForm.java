/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

/**
 *
 * @author Advice70
 */
public class VentaPedidoForm extends GenericForm{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3072728056340319624L;



	
    
    private  ArrayList<DivisaBean> listaDivisas;
    private ArrayList<ConvenioBean> listaConvenios;
    private ArrayList<AgenteBean> listaAgentes;
    private ArrayList<IdiomaBean> listaIdiomas;
    private ArrayList<FormaPagoBean>  listaFormasPago;
    private ArrayList<PromocionBean> listaPromociones;
    private ArrayList<ProductosBean> listaProductos;
    private ArrayList<TipoPedidoBean> listaTiposPedidos;
	private ArrayList<VentaPedidoBean> listaGrupos;
	
    private String tipo_pedido;
    private String accion;
	private String addProducto;
	private String pedido;
	private String codigo_suc;
	private String sobre;
	private int cantidad;
    private String forma_pago;
    private String idioma;
    private String agente;
    private String convenio;
    private String convenio_det;
    private int convenio_ln;
    private String divisa;
    private String promocion;
    private String fecha;
    private String fecha_entrega;
    private String hora;
    private int cambio;
    private String codigo;
    private String cliente;
    private String nombre_cliente;
    private String nota;
    private long subTotal;
    private long descuento;
    private int dtcoPorcentaje;
    private long total;
    private long anticipo;
    private long totalPendiante;
    private GraduacionesBean graduacion;
    private ContactologiaBean graduacion_lentilla;
    private String ojo;
    private String estado = Constantes.STRING_VENTA;
    private String descripcion = "";
    private String[] grupo;
    private int grupo_max = Constantes.INT_CERO;
    private String error = "error";
	private String supervisor;
	private long anticipo_total;
	private long anticipo_def;
	private int porcentaje_anticipo;
	private int porcentaje_descuento_max;
	private int caja;
	private String cdg;
	private String ocultar;
	private String bloquea;
	private String eliminarPedid;
	private String msnPedidoEntrega;
	private String flujo = "formulario";
	private String pagadoTotal = Constantes.STRING_FALSE;
	private int cantidadProductos = Constantes.INT_CERO;
	private String mostrarFichaTecnica;
	private String acceso = Constantes.STRING_BLANCO;
	private int descuento_autorizado = Constantes.INT_CERO;
	private int temp_descuento_ingresado = Constantes.INT_CERO;
	private int temp_indice = Constantes.INT_CERO;
	private int cantidad_linea = Constantes.INT_CERO;
	private String descuento_autoriza = Constantes.STRING_BLANCO;
	private String pedido_costo_cero = Constantes.STRING_FALSE;
	private String desde_presupuesto = Constantes.STRING_FALSE;
	private String codigo_confirmacion = Constantes.STRING_BLANCO;
	private String tipo_armazon = Constantes.STRING_BLANCO;
	private String puente = Constantes.STRING_FALSE;
	private String diagonal = Constantes.STRING_FALSE;
	private String horizontal = Constantes.STRING_BLANCO;
	private String vertical = Constantes.STRING_BLANCO;
	private String fenix = Constantes.STRING_FALSE;
	private String encargo_garantia= Constantes.STRING_BLANCO;
	private int anticipo_pagado = Constantes.INT_CERO;
	private String estadoVentaMulti=Constantes.STRING_BLANCO;
	private String tiene_pagos=Constantes.STRING_FALSE;
	private String cerrado = Constantes.STRING_N;
	private double cantidad_descuento = 0;
	private String otra_tienda = Constantes.STRING_FALSE;
	private String local=Constantes.STRING_BLANCO;
	private String entregado = Constantes.STRING_FALSE;

	//Informacion del cliente.
	private String nif;
	private String dvnif;
    private String codigo_mult;
    private int index_multi;
    
    //Historial del Encargo
    private boolean mostrarDev;
    private String encargo_padre=Constantes.STRING_BLANCO;
    private String encargo_padre_valido=Constantes.STRING_BLANCO;
	private String grupoSing=Constantes.STRING_BLANCO;
	
	//Segundo cristal
	private String seg_cristal;

    private String cdg_mofercombo;
   
	private String encargo_seguro="";

	private String promopar="";

	//para calcular seguro garantia
	String [][] seguro;
	
	//LMARIN 20141029
	String cliente_dto;
	
	//LMARIN 20150531
	private String estado_boleta;
	private String tipoimp;	
	
	//LMARIN 20151104
	private String isapre;
	
	//LMARIN 20170614
	private String venta_seguro;
	
	//LMARIN 20180220
	private String numero_cupon=Constantes.STRING_BLANCO;
	

	private String muestra_ftaller;	
	private String valor_comodin;
	private String segCris;
	
	
	public VentaPedidoForm(){
		
		 listaDivisas = new ArrayList<DivisaBean>();
		 listaConvenios = new ArrayList<ConvenioBean>();
		 listaAgentes = new ArrayList<AgenteBean>();
		 listaIdiomas = new ArrayList<IdiomaBean>();
		 listaFormasPago = new ArrayList<FormaPagoBean>();
		 listaPromociones = new ArrayList<PromocionBean>();
		 listaProductos = new ArrayList<ProductosBean>();
		 listaTiposPedidos = new ArrayList<TipoPedidoBean>();
		 listaGrupos = new ArrayList<VentaPedidoBean>();
		 
		 nif="";
		 dvnif="";
		 codigo_mult="";
		 index_multi=0;
		 fecha="";
		 codigo="";
		 cliente="";
		 nombre_cliente="";
		
	}
	
	
	
	public String getEntregado() {
		return entregado;
	}


	public void setEntregado(String entregado) {
		this.entregado = entregado;
	}


	public String getEstadoVentaMulti() {
		return estadoVentaMulti;
	}

	
	public void setEstadoVentaMulti(String estadoVentaMulti) {
		this.estadoVentaMulti = estadoVentaMulti;
	}

	public int getAnticipo_pagado() {
		return anticipo_pagado;
	}

	public void setAnticipo_pagado(int anticipo_pagado) {
		this.anticipo_pagado = anticipo_pagado;
	}

	public String getCodigo_mult() {
		return codigo_mult;
	}

	public void setCodigo_mult(String codigo_mult) {
		this.codigo_mult = codigo_mult;
	}

	public int getIndex_multi() {
		return index_multi;
	}

	public void setIndex_multi(int index_multi) {
		this.index_multi = index_multi;
	}

	public ContactologiaBean getGraduacion_lentilla() {
		return graduacion_lentilla;
	}

	public void setGraduacion_lentilla(ContactologiaBean graduacion_lentilla) {
		this.graduacion_lentilla = graduacion_lentilla;
	}

	public String[][] getSeguro() {
		return seguro;
	}

	public void setSeguro(String[][] seguro) {
		this.seguro = seguro;
	}

	public String getEncargo_garantia() {
		return encargo_garantia;
	}

	public void setEncargo_garantia(String encargo_garantia) {
		this.encargo_garantia = encargo_garantia;
	}

	public String getFenix() {
		return fenix;
	}

	public void setFenix(String fenix) {
		this.fenix = fenix;
	}

	public String getTipo_armazon() {
		return tipo_armazon;
	}

	public void setTipo_armazon(String tipo_armazon) {
		this.tipo_armazon = tipo_armazon;
	}

	public String getPuente() {
		return puente;
	}

	public void setPuente(String puente) {
		this.puente = puente;
	}

	public String getDiagonal() {
		return diagonal;
	}

	public void setDiagonal(String diagonal) {
		this.diagonal = diagonal;
	}

	public String getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(String horizontal) {
		this.horizontal = horizontal;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public String getCodigo_confirmacion() {
		return codigo_confirmacion;
	}

	public void setCodigo_confirmacion(String codigo_confirmacion) {
		this.codigo_confirmacion = codigo_confirmacion;
	}

	public String getDesde_presupuesto() {
		return desde_presupuesto;
	}

	public void setDesde_presupuesto(String desde_presupuesto) {
		this.desde_presupuesto = desde_presupuesto;
	}

	public int getConvenio_ln() {
		return convenio_ln;
	}

	public void setConvenio_ln(int convenio_ln) {
		this.convenio_ln = convenio_ln;
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

	public String getPedido_costo_cero() {
		return pedido_costo_cero;
	}

	public void setPedido_costo_cero(String pedido_costo_cero) {
		this.pedido_costo_cero = pedido_costo_cero;
	}

	public String getDescuento_autoriza() {
		return descuento_autoriza;
	}

	public void setDescuento_autoriza(String descuento_autoriza) {
		this.descuento_autoriza = descuento_autoriza;
	}

	public int getCantidad_linea() {
		return cantidad_linea;
	}

	public void setCantidad_linea(int cantidad_linea) {
		this.cantidad_linea = cantidad_linea;
	}

	public int getTemp_descuento_ingresado() {
		return temp_descuento_ingresado;
	}

	public void setTemp_descuento_ingresado(int temp_descuento_ingresado) {
		this.temp_descuento_ingresado = temp_descuento_ingresado;
	}

	public int getTemp_indice() {
		return temp_indice;
	}

	public void setTemp_indice(int temp_indice) {
		this.temp_indice = temp_indice;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	public int getDescuento_autorizado() {
		return descuento_autorizado;
	}

	public void setDescuento_autorizado(int descuento_autorizado) {
		this.descuento_autorizado = descuento_autorizado;
	}

	public String getMostrarFichaTecnica() {
		return mostrarFichaTecnica;
	}

	public void setMostrarFichaTecnica(String mostrarFichaTecnica) {
		this.mostrarFichaTecnica = mostrarFichaTecnica;
	}

	public int getCantidadProductos() {
		return cantidadProductos;
	}

	public void setCantidadProductos(int cantidadProductos) {
		this.cantidadProductos = cantidadProductos;
	}

	public String getPagadoTotal() {
		return pagadoTotal;
	}

	public void setPagadoTotal(String pagadoTotal) {
		this.pagadoTotal = pagadoTotal;
	}

	public String getFlujo() {
		return flujo;
	}

	public void setFlujo(String flujo) {
		this.flujo = flujo;
	}
	
	public String getMsnPedidoEntrega() {
		return msnPedidoEntrega;
	}

	public void setMsnPedidoEntrega(String msnPedidoEntrega) {
		this.msnPedidoEntrega = msnPedidoEntrega;
	}

	public String getEliminarPedid() {
		return eliminarPedid;
	}

	public void setEliminarPedid(String eliminarPedid) {
		this.eliminarPedid = eliminarPedid;
	}

	public String getBloquea() {
		return bloquea;
	}

	public void setBloquea(String bloquea) {
		this.bloquea = bloquea;
	}

	public String getOcultar() {
		return ocultar;
	}

	public void setOcultar(String ocultar) {
		this.ocultar = ocultar;
	}

	public String getCdg() {
		return cdg;
	}

	public void setCdg(String cdg) {
		this.cdg = cdg;
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

	public int getPorcentaje_anticipo() {
		return porcentaje_anticipo;
	}

	public void setPorcentaje_anticipo(int porcentaje_anticipo) {
		this.porcentaje_anticipo = porcentaje_anticipo;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public long getAnticipo_total() {
		return anticipo_total;
	}

	public void setAnticipo_total(long anticipo_total) {
		this.anticipo_total = anticipo_total;
	}

	public long getAnticipo_def() {
		return anticipo_def;
	}

	public void setAnticipo_def(long anticipo_def) {
		this.anticipo_def = anticipo_def;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getGrupo_max() {
		return grupo_max;
	}

	public void setGrupo_max(int grupo_max) {
		this.grupo_max = grupo_max;
	}

	public String[] getGrupo() {
		return grupo;
	}

	public void setGrupo(String[] grupo) {
		this.grupo = grupo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getOjo() {
		return ojo;
	}

	public void setOjo(String ojo) {
		this.ojo = ojo;
	}

	public GraduacionesBean getGraduacion() {
		return graduacion;
	}

	public void setGraduacion(GraduacionesBean graduacion) {
		this.graduacion = graduacion;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public ArrayList<TipoPedidoBean> getListaTiposPedidos() {
		return listaTiposPedidos;
	}

	public void setListaTiposPedidos(ArrayList<TipoPedidoBean> listaTiposPedidos) {
		this.listaTiposPedidos = listaTiposPedidos;
	}

	public String getTipo_pedido() {
		return tipo_pedido;
	}

	public void setTipo_pedido(String tipo_pedido) {
		this.tipo_pedido = tipo_pedido;
	}

	public int getCambio() {
		return cambio;
	}

	public void setCambio(int cambio) {
		this.cambio = cambio;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getCodigo_suc() {
		return codigo_suc;
	}

	public void setCodigo_suc(String codigo_suc) {
		this.codigo_suc = codigo_suc;
	}

	public String getSobre() {
		return sobre;
	}

	public void setSobre(String sobre) {
		this.sobre = sobre;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
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

    
    public void setListaProductos(ArrayList<ProductosBean> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<ProductosBean> getListaProductos() {
        return listaProductos;
    }

    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCliente() {
        return cliente;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public String getPromocion() {
        return promocion;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setListaFormasPago(ArrayList<FormaPagoBean> listaFormasPago) {
        this.listaFormasPago = listaFormasPago;
    }

    public void setListaPromociones(ArrayList<PromocionBean> listaPromociones) {
        this.listaPromociones = listaPromociones;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public ArrayList<FormaPagoBean> getListaFormasPago() {
        return listaFormasPago;
    }

    public ArrayList<PromocionBean> getListaPromociones() {
        return listaPromociones;
    }
    
    public void setAgente(String agente) {
        this.agente = agente;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setListaAgentes(ArrayList<AgenteBean> listaAgentes) {
        this.listaAgentes = listaAgentes;
    }

    public void setListaConvenios(ArrayList<ConvenioBean> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public void setListaDivisas(ArrayList<DivisaBean> listaDivisas) {
        this.listaDivisas = listaDivisas;
    }

    public void setListaIdiomas(ArrayList<IdiomaBean> listaIdiomas) {
        this.listaIdiomas = listaIdiomas;
    }

    public String getAgente() {
        return agente;
    }

    public String getConvenio() {
        return convenio;
    }

    public String getDivisa() {
        return divisa;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public String getIdioma() {
        return idioma;
    }

    public ArrayList<AgenteBean> getListaAgentes() {
        return listaAgentes;
    }

    public ArrayList<ConvenioBean> getListaConvenios() {
        return listaConvenios;
    }

    public ArrayList<DivisaBean> getListaDivisas() {
        return listaDivisas;
    }

    public ArrayList<IdiomaBean> getListaIdiomas() {
        return listaIdiomas;
    }

    public void setListaFormaPago(ArrayList<FormaPagoBean> listaFormaPago) {
        this.listaFormasPago = listaFormaPago;
    }

    public ArrayList<FormaPagoBean> getListaFormaPago() {
        return listaFormasPago;
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
		this.tipo_pedido = "";
		this.accion = "";
		this.addProducto = "";
		this.pedido = "";
		this.codigo_suc = "";
		this.sobre = "";
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
		this.pagadoTotal = Constantes.STRING_FALSE;
		this.cantidadProductos = 0;
		this.flujo = "formulario";
		this.ojo = "";
		this.descripcion =  "";;
		this.grupo_max =  0;
		this.error = "error";
		this.supervisor =  "";
		this.anticipo_total = 0;
		this.anticipo_def = 0;
		this.porcentaje_anticipo = 0;
		this.porcentaje_descuento_max = 0;
		this.caja = 0;
		this.cdg = Constantes.STRING_BLANCO;
		this.ocultar = Constantes.STRING_BLANCO;
		this.bloquea = Constantes.STRING_BLANCO;
		this.eliminarPedid = Constantes.STRING_BLANCO;
		this.msnPedidoEntrega = Constantes.STRING_BLANCO;
		this.descuento_autoriza = Constantes.STRING_BLANCO;
		this.pedido_costo_cero = Constantes.STRING_FALSE;
		this.nif = Constantes.STRING_BLANCO;
		this.dvnif = Constantes.STRING_BLANCO;
		this.convenio = Constantes.STRING_BLANCO;
		this.convenio_det = Constantes.STRING_BLANCO;
		this.convenio_ln = Constantes.INT_CERO;
		this.desde_presupuesto = Constantes.STRING_FALSE;
		this.codigo_confirmacion = Constantes.STRING_BLANCO;
		this.tipo_armazon = Constantes.STRING_BLANCO;
		this.puente = Constantes.STRING_BLANCO;
		this.vertical = Constantes.STRING_BLANCO;
		this.horizontal = Constantes.STRING_BLANCO;
		this.diagonal = Constantes.STRING_BLANCO;
		this.fenix = Constantes.STRING_FALSE;
		this.encargo_garantia = Constantes.STRING_BLANCO;
		this.seguro = null;
		this.codigo_mult = Constantes.STRING_BLANCO;
		this.index_multi = Constantes.INT_CERO;
		this.anticipo_pagado = Constantes.INT_CERO;
		this.estadoVentaMulti=Constantes.STRING_BLANCO;
		this.otra_tienda=Constantes.STRING_FALSE;
		this.entregado = Constantes.STRING_FALSE;
		this.mostrarDev = false;
		this.encargo_padre = "";
	}

	public String getTiene_pagos() {
		return tiene_pagos;
	}

	public void setTiene_pagos(String tiene_pagos) {
		this.tiene_pagos = tiene_pagos;
	}

	public String getCerrado() {
		return cerrado;
	}

	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
	}

	public double getCantidad_descuento() {
		return cantidad_descuento;
	}

	public void setCantidad_descuento(double cantidad_descuento) {
		this.cantidad_descuento = cantidad_descuento;
	}


	public String getOtra_tienda() {
		return otra_tienda;
	}


	public void setOtra_tienda(String otra_tienda) {
		this.otra_tienda = otra_tienda;
	}


	public String getLocal() {
		return local;
	}


	public void setLocal(String local) {
		this.local = local;
	}
    
	public boolean getMostrarDev() {
		return mostrarDev;
	}


	public void setMostrarDev(boolean mostrarDev) {
		this.mostrarDev = mostrarDev;
	}
    
	public String getEncargo_padre() {
		return encargo_padre;
	}


	public void setEncargo_padre(String encargo_padre) {
		this.encargo_padre = encargo_padre;
	}
	public String getEncargo_padre_valido() {
		return encargo_padre_valido;
	}


	public void setEncargo_padre_valido(String encargo_padre_valido) {
		this.encargo_padre_valido = encargo_padre_valido;
	}
	
	public ArrayList<VentaPedidoBean> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(ArrayList<VentaPedidoBean> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
	public String getGrupoSing() {
		return grupoSing;
	}
	public void setGrupoSing(String grupoSing) {
		this.grupoSing = grupoSing;
	}
	
	public String getSeg_cristal() {
		return seg_cristal;
	}


	public void setSeg_cristal(String seg_cristal) {
		this.seg_cristal = seg_cristal;
	}
	
	public String getCliente_dto() {
		return cliente_dto;
	}

	public void setCliente_dto(String cliente_dto) {
		this.cliente_dto = cliente_dto;
	}

	public String getCdg_mofercombo() {
		return cdg_mofercombo;
	}

	public void setCdg_mofercombo(String cdg_mofercombo) {
		this.cdg_mofercombo = cdg_mofercombo;
	}
	
	public String getEstado_boleta() {
		return estado_boleta;
	}

	public void setEstado_boleta(String estado_boleta) {
		this.estado_boleta = estado_boleta;
	}
	public String getTipoimp() {
		return tipoimp;
	}
	public void setTipoimp(String tipoimp) {
		this.tipoimp = tipoimp;
	}
	public String getIsapre() {
		return isapre;
	}
	public void setIsapre(String isapre) {
		this.isapre = isapre;
	}
	
	public String getVenta_seguro() {
		return venta_seguro;
	}

	public void setVenta_seguro(String venta_seguro) {
		this.venta_seguro = venta_seguro;
	}
	
	public String getEncargo_seguro() {
			return encargo_seguro;
	}
	
	public void setEncargo_seguro(String encargo_seguro) {
		this.encargo_seguro = encargo_seguro;
	}
	
	public String getPromopar() {
		return promopar;
	}

	public void setPromopar(String promopar) {
		this.promopar = promopar;
	}
	
	public String getNumero_cupon() {
		return numero_cupon;
	}

	public void setNumero_cupon(String numero_cupon) {
		this.numero_cupon = numero_cupon;
	}


	public String getMuestra_ftaller() {
		return muestra_ftaller;
	}

	public void setMuestra_ftaller(String muestra_ftaller) {
		this.muestra_ftaller = muestra_ftaller;
	}
	
	public String getValor_comodin() {
		return valor_comodin;
	}

	public void setValor_comodin(String valor_comodin) {
		this.valor_comodin = valor_comodin;
	}
	
	public String getSegCris() {
		return segCris;
	}

	public void setSegCris(String segCris) {
		this.segCris = segCris;
	}

	
}
