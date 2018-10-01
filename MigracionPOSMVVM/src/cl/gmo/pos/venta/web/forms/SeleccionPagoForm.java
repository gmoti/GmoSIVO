package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;

public class SeleccionPagoForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8148406945889510224L;
	private String accion;
	private String fecha;
	private String serie;
	private int v_total;
	private int v_total_parcial;
	private int v_a_pagar;
	private int diferencia;
	private double descuento;
	private String forma_pago;
	private ArrayList<FormaPagoBean> listaFormasPago;
	private ArrayList<GiroBean> listaGiros;
	private ArrayList<ProvinciaBean> listaProvincias;
	private ArrayList<PagoBean> listaPagos;
	private int tiene_pagos;
	private int numero_boleta;
	private char tipo_doc;
	private String giro_descripcion;
	private String provincia_descripcion;
	//datos cliente
	private String cliente_venta;
	private String codigo;
	private String nif;
	private String direccion;
	private String razon;
	private String poblacion;
	private int provincia;
	private int giro;
	private String nombre_cliente;
	private String origen;
	private String f_pago;
	private String fech_pago;
	private String exitopago;
	private String estado_formulario_origen;
	private String estado = "";
	private int anticipo_pedido;
	private int porcentaje_anticipo_pedido;
	private String tipo_pedido;
	private ConvenioBean convenio;
	private String Fpago_origen;
	private String tiene_documentos;
	private String solo_guia = Constantes.STRING_FALSE;
	private String solo_boleta = Constantes.STRING_FALSE;
	private int numero_boleta_conf = Constantes.INT_CERO;
	private ArrayList<BoletaBean> lista_boletas;
	private String tiene_fomas_pago;
	private String respuesta_fpago_albaran;
	private String tiene_pagos_actuales = Constantes.STRING_FALSE;
	private int porcentaje_descuento_max;
	private int suma_total_albaranes;
	
	/*LMARIN 20140415 AUTORIZACION FORMAS DE PAGO*/
	private String autorizador;	
	private String tipoaccion;
	private String numero_boleta_up;
	
	/*LMARIN 20140808 AGREGO EL TELEFONO DE LA TIENDA*/
	
    private String telefono_tienda = Constantes.STRING_BLANCO;
	

	//boleta
	private String boleta_cliente = Constantes.STRING_BLANCO;
	private String boleta_rut = Constantes.STRING_BLANCO;
	private String boleta_hora= Constantes.STRING_BLANCO;
	private String boleta_tienda= Constantes.STRING_BLANCO;
	private String boleta_titulo_fecha_ped= Constantes.STRING_BLANCO;
	private String boleta_fecha_ped= Constantes.STRING_BLANCO;
	private String boleta_fecha= Constantes.STRING_BLANCO;
	private String boleta_titulo_albaran= Constantes.STRING_BLANCO;
	private String boleta_albaran= Constantes.STRING_BLANCO;
	private String boleta_titulo_fecha_ent= Constantes.STRING_BLANCO;
	private String boleta_fecha_ent= Constantes.STRING_BLANCO;
	private String boleta_vendedor= Constantes.STRING_BLANCO;
	private String boleta_resumen_total= Constantes.STRING_BLANCO;
	private String boleta_resumen_anticipo= Constantes.STRING_BLANCO;
	private String boleta_resumen_pendiente= Constantes.STRING_BLANCO;
	private String boleta_titulo_resumen_total_pagar= Constantes.STRING_BLANCO;
	private String boleta_total_pagar= Constantes.STRING_BLANCO;
	private String boleta_titulo_fpago_1= Constantes.STRING_BLANCO;
	private String boleta_titulo_fpago_2= Constantes.STRING_BLANCO;
	private String boleta_titulo_fpago_3= Constantes.STRING_BLANCO;
	private String boleta_titulo_fpago_4= Constantes.STRING_BLANCO;
	private String boleta_fpago_1= Constantes.STRING_BLANCO;
	private String boleta_fpago_2= Constantes.STRING_BLANCO;
	private String boleta_fpago_3= Constantes.STRING_BLANCO;
	private String boleta_fpago_4= Constantes.STRING_BLANCO;
	private ArrayList<ProductosBean> boletaListaProductos;
	
	//guia
	private String guia_cliente = Constantes.STRING_BLANCO;
	private String guia_direccion = Constantes.STRING_BLANCO;
	private String guia_giro= Constantes.STRING_BLANCO;
	private String guia_rut= Constantes.STRING_BLANCO;
	private String guia_fecha= Constantes.STRING_BLANCO;
	private String guia_comuna= Constantes.STRING_BLANCO;
	private String guia_subtotal= Constantes.STRING_BLANCO;
	private String guia_descuento= Constantes.STRING_BLANCO;
	private String guia_total= Constantes.STRING_BLANCO;
	private String guia_convenio_titulo_diferencia=Constantes.STRING_BLANCO;
	private String guia_convenio_diferencia=Constantes.STRING_BLANCO;
	private String guia_convenio_titulo_total_facturar=Constantes.STRING_BLANCO;
	private String guia_convenio_total_facturar=Constantes.STRING_BLANCO;
	private String guia_ticket= Constantes.STRING_BLANCO;
	private String guia_pie_nombre=Constantes.STRING_BLANCO;
	private String guia_pie_rut=Constantes.STRING_BLANCO;
	
	private ArrayList<ProductosBean> guiaListaProductos;
	
	private String n_isapre = Constantes.STRING_BLANCO;

	private String cliente_dto = Constantes.STRING_BLANCO;
	
	private String codigo_convenio = Constantes.STRING_BLANCO;
	
	
	private String titulo= Constantes.STRING_BLANCO;
	
	private String rut_vs= Constantes.STRING_BLANCO;
	


	private String monto_des_vs= Constantes.STRING_BLANCO;


	public SeleccionPagoForm() {
		super();
	}

	
	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getV_total() {
		return v_total;
	}

	public void setV_total(int v_total) {
		this.v_total = v_total;
	}

	public int getV_total_parcial() {
		return v_total_parcial;
	}

	public void setV_total_parcial(int v_total_parcial) {
		this.v_total_parcial = v_total_parcial;
	}

	public int getV_a_pagar() {
		return v_a_pagar;
	}

	public void setV_a_pagar(int v_a_pagar) {
		this.v_a_pagar = v_a_pagar;
	}

	public int getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(int diferencia) {
		this.diferencia = diferencia;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public String getForma_pago() {
		return forma_pago;
	}

	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}

	public ArrayList<FormaPagoBean> getListaFormasPago() {
		return listaFormasPago;
	}

	public void setListaFormasPago(ArrayList<FormaPagoBean> listaFormasPago) {
		this.listaFormasPago = listaFormasPago;
	}

	public ArrayList<GiroBean> getListaGiros() {
		return listaGiros;
	}

	public void setListaGiros(ArrayList<GiroBean> listaGiros) {
		this.listaGiros = listaGiros;
	}

	public ArrayList<ProvinciaBean> getListaProvincias() {
		return listaProvincias;
	}

	public void setListaProvincias(ArrayList<ProvinciaBean> listaProvincias) {
		this.listaProvincias = listaProvincias;
	}

	public ArrayList<PagoBean> getListaPagos() {
		return listaPagos;
	}

	public void setListaPagos(ArrayList<PagoBean> listaPagos) {
		this.listaPagos = listaPagos;
	}

	public int getTiene_pagos() {
		return tiene_pagos;
	}

	public void setTiene_pagos(int tiene_pagos) {
		this.tiene_pagos = tiene_pagos;
	}

	public int getNumero_boleta() {
		return numero_boleta;
	}

	public void setNumero_boleta(int numero_boleta) {
		this.numero_boleta = numero_boleta;
	}

	public char getTipo_doc() {
		return tipo_doc;
	}

	public void setTipo_doc(char tipo_doc) {
		this.tipo_doc = tipo_doc;
	}

	public String getGiro_descripcion() {
		return giro_descripcion;
	}

	public void setGiro_descripcion(String giro_descripcion) {
		this.giro_descripcion = giro_descripcion;
	}

	public String getProvincia_descripcion() {
		return provincia_descripcion;
	}

	public void setProvincia_descripcion(String provincia_descripcion) {
		this.provincia_descripcion = provincia_descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getProvincia() {
		return provincia;
	}

	public void setProvincia(int provincia) {
		this.provincia = provincia;
	}

	public int getGiro() {
		return giro;
	}

	public void setGiro(int giro) {
		this.giro = giro;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getF_pago() {
		return f_pago;
	}

	public void setF_pago(String f_pago) {
		this.f_pago = f_pago;
	}

	public String getFech_pago() {
		return fech_pago;
	}

	public void setFech_pago(String fech_pago) {
		this.fech_pago = fech_pago;
	}

	public String getExitopago() {
		return exitopago;
	}

	public void setExitopago(String exitopago) {
		this.exitopago = exitopago;
	}

	public String getEstado_formulario_origen() {
		return estado_formulario_origen;
	}

	public void setEstado_formulario_origen(String estado_formulario_origen) {
		this.estado_formulario_origen = estado_formulario_origen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getAnticipo_pedido() {
		return anticipo_pedido;
	}

	public void setAnticipo_pedido(int anticipo_pedido) {
		this.anticipo_pedido = anticipo_pedido;
	}

	public int getPorcentaje_anticipo_pedido() {
		return porcentaje_anticipo_pedido;
	}

	public void setPorcentaje_anticipo_pedido(int porcentaje_anticipo_pedido) {
		this.porcentaje_anticipo_pedido = porcentaje_anticipo_pedido;
	}

	public String getTipo_pedido() {
		return tipo_pedido;
	}

	public void setTipo_pedido(String tipo_pedido) {
		this.tipo_pedido = tipo_pedido;
	}

	public ConvenioBean getConvenio() {
		return convenio;
	}

	public void setConvenio(ConvenioBean convenio) {
		this.convenio = convenio;
	}

	public String getFpago_origen() {
		return Fpago_origen;
	}

	public void setFpago_origen(String fpago_origen) {
		Fpago_origen = fpago_origen;
	}

	public String getTiene_documentos() {
		return tiene_documentos;
	}

	public void setTiene_documentos(String tiene_documentos) {
		this.tiene_documentos = tiene_documentos;
	}

	public String getSolo_guia() {
		return solo_guia;
	}

	public void setSolo_guia(String solo_guia) {
		this.solo_guia = solo_guia;
	}

	public int getNumero_boleta_conf() {
		return numero_boleta_conf;
	}

	public void setNumero_boleta_conf(int numero_boleta_conf) {
		this.numero_boleta_conf = numero_boleta_conf;
	}

	public ArrayList<BoletaBean> getLista_boletas() {
		return lista_boletas;
	}

	public void setLista_boletas(ArrayList<BoletaBean> lista_boletas) {
		this.lista_boletas = lista_boletas;
	}

	public String getTiene_fomas_pago() {
		return tiene_fomas_pago;
	}

	public void setTiene_fomas_pago(String tiene_fomas_pago) {
		this.tiene_fomas_pago = tiene_fomas_pago;
	}

	public String getRespuesta_fpago_albaran() {
		return respuesta_fpago_albaran;
	}

	public void setRespuesta_fpago_albaran(String respuesta_fpago_albaran) {
		this.respuesta_fpago_albaran = respuesta_fpago_albaran;
	}

	public int getPorcentaje_descuento_max() {
		return porcentaje_descuento_max;
	}

	public void setPorcentaje_descuento_max(int porcentaje_descuento_max) {
		this.porcentaje_descuento_max = porcentaje_descuento_max;
	}

	public int getSuma_total_albaranes() {
		return suma_total_albaranes;
	}

	public void setSuma_total_albaranes(int suma_total_albaranes) {
		this.suma_total_albaranes = suma_total_albaranes;
	}
	
	
	//boleta
	

	public String getBoleta_cliente() {
		return boleta_cliente;
	}

	public void setBoleta_cliente(String boleta_cliente) {
		this.boleta_cliente = boleta_cliente;
	}

	public String getBoleta_hora() {
		return boleta_hora;
	}

	public void setBoleta_hora(String boleta_hora) {
		this.boleta_hora = boleta_hora;
	}

	public String getBoleta_tienda() {
		return boleta_tienda;
	}

	public void setBoleta_tienda(String boleta_tienda) {
		this.boleta_tienda = boleta_tienda;
	}

	public String getBoleta_titulo_fecha_ped() {
		return boleta_titulo_fecha_ped;
	}

	public void setBoleta_titulo_fecha_ped(String boleta_titulo_fecha_ped) {
		this.boleta_titulo_fecha_ped = boleta_titulo_fecha_ped;
	}

	public String getBoleta_fecha_ped() {
		return boleta_fecha_ped;
	}

	public void setBoleta_fecha_ped(String boleta_fecha_ped) {
		this.boleta_fecha_ped = boleta_fecha_ped;
	}

	public String getBoleta_fecha() {
		return boleta_fecha;
	}

	public void setBoleta_fecha(String boleta_fecha) {
		this.boleta_fecha = boleta_fecha;
	}

	public String getBoleta_titulo_albaran() {
		return boleta_titulo_albaran;
	}

	public void setBoleta_titulo_albaran(String boleta_titulo_albaran) {
		this.boleta_titulo_albaran = boleta_titulo_albaran;
	}

	public String getBoleta_albaran() {
		return boleta_albaran;
	}

	public void setBoleta_albaran(String boleta_albaran) {
		this.boleta_albaran = boleta_albaran;
	}

	public String getBoleta_titulo_fecha_ent() {
		return boleta_titulo_fecha_ent;
	}

	public void setBoleta_titulo_fecha_ent(String boleta_titulo_fecha_ent) {
		this.boleta_titulo_fecha_ent = boleta_titulo_fecha_ent;
	}

	public String getBoleta_fecha_ent() {
		return boleta_fecha_ent;
	}

	public void setBoleta_fecha_ent(String boleta_fecha_ent) {
		this.boleta_fecha_ent = boleta_fecha_ent;
	}

	public String getBoleta_vendedor() {
		return boleta_vendedor;
	}

	public void setBoleta_vendedor(String boleta_vendedor) {
		this.boleta_vendedor = boleta_vendedor;
	}

	public String getBoleta_resumen_total() {
		return boleta_resumen_total;
	}

	public void setBoleta_resumen_total(String boleta_resumen_total) {
		this.boleta_resumen_total = boleta_resumen_total;
	}

	public String getBoleta_resumen_anticipo() {
		return boleta_resumen_anticipo;
	}

	public void setBoleta_resumen_anticipo(String boleta_resumen_anticipo) {
		this.boleta_resumen_anticipo = boleta_resumen_anticipo;
	}

	public String getBoleta_resumen_pendiente() {
		return boleta_resumen_pendiente;
	}

	public void setBoleta_resumen_pendiente(String boleta_resumen_pendiente) {
		this.boleta_resumen_pendiente = boleta_resumen_pendiente;
	}

	public String getBoleta_titulo_resumen_total_pagar() {
		return boleta_titulo_resumen_total_pagar;
	}

	public void setBoleta_titulo_resumen_total_pagar(
			String boleta_titulo_resumen_total_pagar) {
		this.boleta_titulo_resumen_total_pagar = boleta_titulo_resumen_total_pagar;
	}

	public String getBoleta_total_pagar() {
		return boleta_total_pagar;
	}

	public void setBoleta_total_pagar(String boleta_total_pagar) {
		this.boleta_total_pagar = boleta_total_pagar;
	}

	public String getBoleta_titulo_fpago_1() {
		return boleta_titulo_fpago_1;
	}

	public void setBoleta_titulo_fpago_1(String boleta_titulo_fpago_1) {
		this.boleta_titulo_fpago_1 = boleta_titulo_fpago_1;
	}

	public String getBoleta_titulo_fpago_2() {
		return boleta_titulo_fpago_2;
	}

	public void setBoleta_titulo_fpago_2(String boleta_titulo_fpago_2) {
		this.boleta_titulo_fpago_2 = boleta_titulo_fpago_2;
	}

	public String getBoleta_titulo_fpago_3() {
		return boleta_titulo_fpago_3;
	}

	public void setBoleta_titulo_fpago_3(String boleta_titulo_fpago_3) {
		this.boleta_titulo_fpago_3 = boleta_titulo_fpago_3;
	}

	public String getBoleta_titulo_fpago_4() {
		return boleta_titulo_fpago_4;
	}

	public void setBoleta_titulo_fpago_4(String boleta_titulo_fpago_4) {
		this.boleta_titulo_fpago_4 = boleta_titulo_fpago_4;
	}

	public String getBoleta_fpago_1() {
		return boleta_fpago_1;
	}

	public void setBoleta_fpago_1(String boleta_fpago_1) {
		this.boleta_fpago_1 = boleta_fpago_1;
	}

	public String getBoleta_fpago_2() {
		return boleta_fpago_2;
	}

	public void setBoleta_fpago_2(String boleta_fpago_2) {
		this.boleta_fpago_2 = boleta_fpago_2;
	}

	public String getBoleta_fpago_3() {
		return boleta_fpago_3;
	}

	public void setBoleta_fpago_3(String boleta_fpago_3) {
		this.boleta_fpago_3 = boleta_fpago_3;
	}

	public String getBoleta_fpago_4() {
		return boleta_fpago_4;
	}

	public void setBoleta_fpago_4(String boleta_fpago_4) {
		this.boleta_fpago_4 = boleta_fpago_4;
	}

	public String getBoleta_rut() {
		return boleta_rut;
	}

	public void setBoleta_rut(String boleta_rut) {
		this.boleta_rut = boleta_rut;
	}

	public ArrayList<ProductosBean> getBoletaListaProductos() {
		return boletaListaProductos;
	}

	public void setBoletaListaProductos(
			ArrayList<ProductosBean> boletaListaProductos) {
		this.boletaListaProductos = boletaListaProductos;
	}

	public String getGuia_cliente() {
		return guia_cliente;
	}

	public void setGuia_cliente(String guia_cliente) {
		this.guia_cliente = guia_cliente;
	}

	public String getGuia_direccion() {
		return guia_direccion;
	}

	public void setGuia_direccion(String guia_direccion) {
		this.guia_direccion = guia_direccion;
	}

	public String getGuia_giro() {
		return guia_giro;
	}

	public void setGuia_giro(String guia_giro) {
		this.guia_giro = guia_giro;
	}

	public String getGuia_rut() {
		return guia_rut;
	}

	public void setGuia_rut(String guia_rut) {
		this.guia_rut = guia_rut;
	}

	public String getGuia_fecha() {
		return guia_fecha;
	}

	public void setGuia_fecha(String guia_fecha) {
		this.guia_fecha = guia_fecha;
	}

	public String getGuia_subtotal() {
		return guia_subtotal;
	}

	public void setGuia_subtotal(String guia_subtotal) {
		this.guia_subtotal = guia_subtotal;
	}

	public String getGuia_descuento() {
		return guia_descuento;
	}

	public void setGuia_descuento(String guia_descuento) {
		this.guia_descuento = guia_descuento;
	}

	public String getGuia_total() {
		return guia_total;
	}

	public void setGuia_total(String guia_total) {
		this.guia_total = guia_total;
	}

	public ArrayList<ProductosBean> getGuiaListaProductos() {
		return guiaListaProductos;
	}

	public void setGuiaListaProductos(ArrayList<ProductosBean> guiaListaProductos) {
		this.guiaListaProductos = guiaListaProductos;
	}

	public String getGuia_comuna() {
		return guia_comuna;
	}

	public void setGuia_comuna(String guia_comuna) {
		this.guia_comuna = guia_comuna;
	}

	public String getGuia_convenio_titulo_diferencia() {
		return guia_convenio_titulo_diferencia;
	}

	public void setGuia_convenio_titulo_diferencia(
			String guia_convenio_titulo_diferencia) {
		this.guia_convenio_titulo_diferencia = guia_convenio_titulo_diferencia;
	}

	public String getGuia_convenio_diferencia() {
		return guia_convenio_diferencia;
	}

	public void setGuia_convenio_diferencia(String guia_convenio_diferencia) {
		this.guia_convenio_diferencia = guia_convenio_diferencia;
	}

	public String getGuia_convenio_titulo_total_facturar() {
		return guia_convenio_titulo_total_facturar;
	}

	public void setGuia_convenio_titulo_total_facturar(
			String guia_convenio_titulo_total_facturar) {
		this.guia_convenio_titulo_total_facturar = guia_convenio_titulo_total_facturar;
	}

	public String getGuia_convenio_total_facturar() {
		return guia_convenio_total_facturar;
	}

	public void setGuia_convenio_total_facturar(String guia_convenio_total_facturar) {
		this.guia_convenio_total_facturar = guia_convenio_total_facturar;
	}

	public String getGuia_ticket() {
		return guia_ticket;
	}

	public void setGuia_ticket(String guia_ticket) {
		this.guia_ticket = guia_ticket;
	}

	public String getGuia_pie_nombre() {
		return guia_pie_nombre;
	}

	public void setGuia_pie_nombre(String guia_pie_nombre) {
		this.guia_pie_nombre = guia_pie_nombre;
	}

	public String getGuia_pie_rut() {
		return guia_pie_rut;
	}

	public void setGuia_pie_rut(String guia_pie_rut) {
		this.guia_pie_rut = guia_pie_rut;
	}

	public String getSolo_boleta() {
		return solo_boleta;
	}

	public void setSolo_boleta(String solo_boleta) {
		this.solo_boleta = solo_boleta;
	}

	public String getCliente_venta() {
		return cliente_venta;
	}

	public void setCliente_venta(String cliente_venta) {
		this.cliente_venta = cliente_venta;
	}

	public String getTiene_pagos_actuales() {
		return tiene_pagos_actuales;
	}

	public void setTiene_pagos_actuales(String tiene_pagos_actuales) {
		this.tiene_pagos_actuales = tiene_pagos_actuales;
	}

	public String getAutorizador() {
		return autorizador;
	}

	public void setAutorizador(String autorizador) {
		this.autorizador = autorizador;
	}

	public String getTipoaccion() {
		return tipoaccion;
	}

	public void setTipoaccion(String tipoaccion) {
		this.tipoaccion = tipoaccion;
	}

	public String getNumero_boleta_up() {
		return numero_boleta_up;
	}

	public void setNumero_boleta_up(String numero_boleta_up) {
		this.numero_boleta_up = numero_boleta_up;
	}

	public String getN_isapre() {
		return n_isapre;
	}

	public void setN_isapre(String n_isapre) {
		this.n_isapre = n_isapre;
	}
	public String getTelefono_tienda() {
		return telefono_tienda;
	}

	public void setTelefono_tienda(String telefono_tienda) {
		this.telefono_tienda = telefono_tienda;
	}
	
	public String getCliente_dto() {
		return cliente_dto;
	}

	public void setCliente_dto(String cliente_dto) {
		this.cliente_dto = cliente_dto;
	}
	
	public String getCodigo_convenio() {
		return codigo_convenio;
	}

	public void setCodigo_convenio(String codigo_convenio) {
		this.codigo_convenio = codigo_convenio;
	}
	
	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getRut_vs() {
		return rut_vs;
	}


	public void setRut_vs(String rut_vs) {
		this.rut_vs = rut_vs;
	}


	public String getMonto_des_vs() {
		return monto_des_vs;
	}

	public void setMonto_des_vs(String monto_des_vs) {
		this.monto_des_vs = monto_des_vs;
	}
	
}
