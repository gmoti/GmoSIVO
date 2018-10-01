/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;

/**
 *
 * @author Advice70
 */
public class VentaPedidoBean {
	
	//CAMPOS VENTA PEDIDO BD
	private int codigo;
	private String cdg;
	private String fecha;
    private String hora;
    private String id_cliente;
    private String forma_pago;
    private String numero_sobre;
    private int cambio;
    private String id_promocion;
    private String divisa;
    private String idioma;
    private String id_agente;
    private String fecha_entrega;
    private String serie;
    private int numero;
    private int dto;
    private double anticipo;
    private String notas;
    private String cerrado;
    private String retenido;
    private String tipo_ped;
    private String enuso;
    private String tipo_ped_2;
    private String finalizado;
    private double anticipo_total;
    private int numero_caja;
    private double anticipo_def;
    private double anticipo_total_def;
    private String convenio;
    private String anulado;
    private String descargado;
    private String supervisor;
    private int porcentaje_descuento_maximo;
    private int porcentaje_anticipo;
    private String fenix;
    private String pedvtant;
    private String conTriosValidos = Constantes.STRING_TRUE;
    
    private boolean mostrar = false;
    private boolean sin_cdd = false;
    private int cantidad_cdd;
    private String modifica_mostrar = Constantes.STRING_FALSE;
    private int cantidad_lentillas;
    
    private String tipo_familia;
    
    
    
    //CAMPOS PEDIDO REPORTES
    private int ident;
    private String cod_detalle_vta;
    private String cod_montacb;
    private String articulo;
    private String descripcion;
    private String unahora;
    private String ojo;
    private double esfera;
    private double eje;
    private double  cilindro;
    private int cantidad;
    private String cod_lista_lib;
    private String nombre_agente;
    private ArrayList<ProductosBean> lista_productos;
    private int id_receta;
    private String agente;
    private String cliente;
    private String grupo;
    private String cliente_completo;
    private String rut_cliente;
    private String dv_cliente;
    private int linea;
    private double dnpcerca;    
    private double dnplejos;
    private double diametro;
    private double numerograd;
    private String fechagrad;
    private boolean checked;
    
    //CAMPOS PEDIDO REPORTES
    private String numdev;
	private String encargo_padre;
    private String lineadev;
    private String respuestaValidaLiberacion;
    private String cliente_dto;
	private String local;
    private String localsap;
	private String venta_seguro;
	
	private String encargo_seguro;
	
	private String numero_cupon;

	private int ftaller;

	public String getConTriosValidos() {
		return conTriosValidos;
	}

	public void setConTriosValidos(String conTriosValidos) {
		this.conTriosValidos = conTriosValidos;
	}

	public String getRespuestaValidaLiberacion() {
		return respuestaValidaLiberacion;
	}

	public void setRespuestaValidaLiberacion(String respuestaValidaLiberacion) {
		this.respuestaValidaLiberacion = respuestaValidaLiberacion;
	}

	public int getCantidad_lentillas() {
		return cantidad_lentillas;
	}

	public void setCantidad_lentillas(int cantidad_lentillas) {
		this.cantidad_lentillas = cantidad_lentillas;
	}

	public String getModifica_mostrar() {
		return modifica_mostrar;
	}

	public void setModifica_mostrar(String modifica_mostrar) {
		this.modifica_mostrar = modifica_mostrar;
	}

	public boolean isMostrar() {
		return mostrar;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	public boolean isSin_cdd() {
		return sin_cdd;
	}

	public void setSin_cdd(boolean sin_cdd) {
		this.sin_cdd = sin_cdd;
	}

	public int getCantidad_cdd() {
		return cantidad_cdd;
	}

	public void setCantidad_cdd(int cantidad_cdd) {
		this.cantidad_cdd = cantidad_cdd;
	}

	public String getPedvtant() {
		return pedvtant;
	}

	public void setPedvtant(String pedvtant) {
		this.pedvtant = pedvtant;
	}

	public String getFenix() {
		return fenix;
	}

	public void setFenix(String fenix) {
		this.fenix = fenix;
	}

	public int getPorcentaje_anticipo() {
		return porcentaje_anticipo;
	}

	public void setPorcentaje_anticipo(int porcentaje_anticipo) {
		this.porcentaje_anticipo = porcentaje_anticipo;
	}

	public int getPorcentaje_descuento_maximo() {
		return porcentaje_descuento_maximo;
	}

	public void setPorcentaje_descuento_maximo(int porcentaje_descuento_maximo) {
		this.porcentaje_descuento_maximo = porcentaje_descuento_maximo;
	}

	public String getCdg() {
		return cdg;
	}

	public void setCdg(String cdg) {
		this.cdg = cdg;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getDto() {
		return dto;
	}

	public void setDto(int dto) {
		this.dto = dto;
	}

	public double getAnticipo() {
		return anticipo;
	}

	public void setAnticipo(double anticipo) {
		this.anticipo = anticipo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getCerrado() {
		return cerrado;
	}

	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
	}

	public String getRetenido() {
		return retenido;
	}

	public void setRetenido(String retenido) {
		this.retenido = retenido;
	}

	public String getTipo_ped() {
		return tipo_ped;
	}

	public void setTipo_ped(String tipo_ped) {
		this.tipo_ped = tipo_ped;
	}

	public String getEnuso() {
		return enuso;
	}

	public void setEnuso(String enuso) {
		this.enuso = enuso;
	}

	public String getTipo_ped_2() {
		return tipo_ped_2;
	}

	public void setTipo_ped_2(String tipo_ped_2) {
		this.tipo_ped_2 = tipo_ped_2;
	}

	public String getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(String finalizado) {
		this.finalizado = finalizado;
	}

	public double getAnticipo_total() {
		return anticipo_total;
	}

	public void setAnticipo_total(double anticipo_total) {
		this.anticipo_total = anticipo_total;
	}

	public int getNumero_caja() {
		return numero_caja;
	}

	public void setNumero_caja(int numero_caja) {
		this.numero_caja = numero_caja;
	}

	public double getAnticipo_def() {
		return anticipo_def;
	}

	public void setAnticipo_def(double anticipo_def) {
		this.anticipo_def = anticipo_def;
	}

	public double getAnticipo_total_def() {
		return anticipo_total_def;
	}

	public void setAnticipo_total_def(double anticipo_total_def) {
		this.anticipo_total_def = anticipo_total_def;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getAnulado() {
		return anulado;
	}

	public void setAnulado(String anulado) {
		this.anulado = anulado;
	}

	public String getDescargado() {
		return descargado;
	}

	public void setDescargado(String descargado) {
		this.descargado = descargado;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public double getNumerograd() {
		return numerograd;
	}

	public void setNumerograd(double numerograd) {
		this.numerograd = numerograd;
	}

	public String getFechagrad() {
		return fechagrad;
	}

	public void setFechagrad(String fechagrad) {
		this.fechagrad = fechagrad;
	}

	public String getCod_montacb() {
		return cod_montacb;
	}

	public void setCod_montacb(String cod_montacb) {
		this.cod_montacb = cod_montacb;
	}

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getDnpcerca() {
		return dnpcerca;
	}

	public void setDnpcerca(double dnpcerca) {
		this.dnpcerca = dnpcerca;
	}

	public double getDnplejos() {
		return dnplejos;
	}

	public void setDnplejos(double dnplejos) {
		this.dnplejos = dnplejos;
	}

	public double getEje() {
		return eje;
	}

	public void setEje(double eje) {
		this.eje = eje;
	}

	public void setId_receta(int id_receta) {
        this.id_receta = id_receta;
    }

    public void setLista_productos(ArrayList<ProductosBean> lista_productos) {
        this.lista_productos = lista_productos;
    }

    public int getId_receta() {
        return id_receta;
    }

    public ArrayList<ProductosBean> getLista_productos() {
        return lista_productos;
    }

    public void setCambio(int cambio) {
        this.cambio = cambio;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public void setId_agente(String id_agente) {
        this.id_agente = id_agente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setId_promocion(String id_promocion) {
        this.id_promocion = id_promocion;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNombre_agente(String nombre_agente) {
        this.nombre_agente = nombre_agente;
    }

    public void setNumero_sobre(String numero_sobre) {
        this.numero_sobre = numero_sobre;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getCambio() {
        return cambio;
    }

    public String getDivisa() {
        return divisa;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public String getId_agente() {
        return id_agente;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public String getId_promocion() {
        return id_promocion;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getNombre_agente() {
        return nombre_agente;
    }

    public String getNumero_sobre() {
        return numero_sobre;
    }

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCod_lista_lib() {
		return cod_lista_lib;
	}

	public void setCod_lista_lib(String cod_lista_lib) {
		this.cod_lista_lib = cod_lista_lib;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public String getCod_detalle_vta() {
		return cod_detalle_vta;
	}

	public void setCod_detalle_vta(String cod_detalle_vta) {
		this.cod_detalle_vta = cod_detalle_vta;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUnahora() {
		return unahora;
	}

	public void setUnahora(String unahora) {
		this.unahora = unahora;
	}

	public String getOjo() {
		return ojo;
	}

	public void setOjo(String ojo) {
		this.ojo = ojo;
	}

	public double getEsfera() {
		return esfera;
	}

	public void setEsfera(double esfera) {
		this.esfera = esfera;
	}

	public double   getCilindro() {
		return cilindro;
	}

	public void setCilindro(double  cilindro) {
		this.cilindro = cilindro;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCliente_completo() {
		return cliente_completo;
	}

	public void setCliente_completo(String cliente_completo) {
		this.cliente_completo = cliente_completo;
	}

	public String getRut_cliente() {
		return rut_cliente;
	}

	public void setRut_cliente(String rut_cliente) {
		this.rut_cliente = rut_cliente;
	}

	public String getDv_cliente() {
		return dv_cliente;
	}

	public void setDv_cliente(String dv_cliente) {
		this.dv_cliente = dv_cliente;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public String getTipo_familia() {
		return tipo_familia;
	}

	public void setTipo_familia(String tipo_familia) {
		this.tipo_familia = tipo_familia;
	}
	public String getNumdev() {
			return numdev;
	}

	public void setNumdev(String numdev) {
		this.numdev = numdev;
	}

	public String getEncargo_padre() {
		return encargo_padre;
	}

	public void setEncargo_padre(String encargo_padre) {
		this.encargo_padre = encargo_padre;
	}

	public String getLineadev() {
		return lineadev;
	}

	public void setLineadev(String lineadev) {
		this.lineadev = lineadev;
	}

	public String getCliente_dto() {
		return cliente_dto;
	}

	public void setCliente_dto(String cliente_dto) {
		this.cliente_dto = cliente_dto;
	}
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getLocalsap() {
		return localsap;
	}

	public void setLocalsap(String localsap) {
		this.localsap = localsap;
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
	public String getNumero_cupon() {
		return numero_cupon;
	}

	public void setNumero_cupon(String numero_cupon) {
		this.numero_cupon = numero_cupon;
	}
	
	public int getFtaller() {
		return ftaller;
	}

	public void setFtaller(int ftaller) {
		this.ftaller = ftaller;
	}

}
