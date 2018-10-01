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
public class ProductosBean {
    
    private String codigo;
	private int ident;
    private String cod_pedvtcb;
    private String montacb;
    private String cod_barra;
    private String descripcion;
    private String subAlm;
    private int precio;
    private String supl;
    private double descuento;
    private int cantidad;
    private int importe;
    private int precio_costo;
    private int precio_sin_iva;
    private String prevtln = Constantes.STRING_BLANCO;
    private String grupo = "0";
    private String tipo;    
    private int estado;
    private String familia;
    private String subFamilia;
    private String grupoFamilia;
    private int total;
    private String codigoMultioferta;
    private String tipoFamilia;
    private String tiene_suple = "false";
    private int cdg_correlativo_multioferta;
    private String codigoSap;
    private String color;
    private int indice;
    private float desdediametro;
	private String una_hora;
    private String linea;
    private int numero_graduacion;
    private String fecha_graduacion;
    private String cod_articulo;
    private String dto;
    private String estado_linea;
    //parametros de venta pedido
    private String ojo;
    private double esfera;
    private double diametro;
    private double cilindro;
    private int eje;
    private String grad_fecha;
    private int grad_numero;
    private String tiene_grupo = Constantes.STRING_FALSE;
    private String descripcion_manual;
    private String liberado;
    private double indiceArticuloLib;
    private boolean precioEspecial = false;
    
    private int indexMulti;
    private int indexRelMulti;
    private int indexProductoMulti;
    
    private String arcli_armazon;
    private int arcli_puente;
    private int arcli_diagonal;
    private int arcli_horizontal;
    private int arcli_vertical;
    private String descatalogado;
    
    //campos utilizados en el mantenedor de albaranes
    private String identpedalbaran;
    private String tipo_albaram;
    
    //campos para las multiofertas
    private String serie;
    private String numero;
    private String fecha;
    private String montado;
    private String albvtcb;
    private String albvtln;
    private String albped;
    private int importedef;
    private ArrayList<ProductosBean>listaProductosMultiofertas;
    private String articulo;
    private ProductosBean productoMultioferta;
    
    //Agrego campo que describe el tipo de tarifa devuelto 20150319
    private String tipotarifa;

    private int preciomul;
    
    private String tarifalocal;

    private String  dtototal;
    
    private String  tipoe;
    

	private String  promopar;
	

	public String getDtototal() {
		return dtototal;
	}

	public void setDtototal(String dtototal) {
		this.dtototal = dtototal;
	}

	public boolean isPrecioEspecial() {
		return precioEspecial;
	}

	public void setPrecioEspecial(boolean precioEspecial) {
		this.precioEspecial = precioEspecial;
	}

	public ProductosBean getProductoMultioferta() {
		return productoMultioferta;
	}

	public void setProductoMultioferta(ProductosBean productoMultioferta) {
		this.productoMultioferta = productoMultioferta;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public ArrayList<ProductosBean> getListaProductosMultiofertas() {
		return listaProductosMultiofertas;
	}

	public void setListaProductosMultiofertas(
			ArrayList<ProductosBean> listaProductosMultiofertas) {
		this.listaProductosMultiofertas = listaProductosMultiofertas;
	}

	public int getImportedef() {
		return importedef;
	}

	public void setImportedef(int importedef) {
		this.importedef = importedef;
	}

	public String getAlbped() {
		return albped;
	}

	public void setAlbped(String albped) {
		this.albped = albped;
	}

	public String getAlbvtln() {
		return albvtln;
	}

	public void setAlbvtln(String albvtln) {
		this.albvtln = albvtln;
	}

	public String getAlbvtcb() {
		return albvtcb;
	}

	public void setAlbvtcb(String albvtcb) {
		this.albvtcb = albvtcb;
	}

	public String getMontado() {
		return montado;
	}

	public void setMontado(String montado) {
		this.montado = montado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getDescatalogado() {
		return descatalogado;
	}

	public void setDescatalogado(String descatalogado) {
		this.descatalogado = descatalogado;
	}

	public String getTipo_albaram() {
		return tipo_albaram;
	}

	public void setTipo_albaram(String tipo_albaram) {
		this.tipo_albaram = tipo_albaram;
	}

	public String getIdentpedalbaran() {
		return identpedalbaran;
	}

	public void setIdentpedalbaran(String identpedalbaran) {
		this.identpedalbaran = identpedalbaran;
	}

	public String getArcli_armazon() {
		return arcli_armazon;
	}

	public void setArcli_armazon(String arcli_armazon) {
		this.arcli_armazon = arcli_armazon;
	}

	public int getArcli_puente() {
		return arcli_puente;
	}

	public void setArcli_puente(int arcli_puente) {
		this.arcli_puente = arcli_puente;
	}

	public int getArcli_diagonal() {
		return arcli_diagonal;
	}

	public void setArcli_diagonal(int arcli_diagonal) {
		this.arcli_diagonal = arcli_diagonal;
	}

	public int getArcli_horizontal() {
		return arcli_horizontal;
	}

	public void setArcli_horizontal(int arcli_horizontal) {
		this.arcli_horizontal = arcli_horizontal;
	}

	public int getArcli_vertical() {
		return arcli_vertical;
	}

	public void setArcli_vertical(int arcli_vertical) {
		this.arcli_vertical = arcli_vertical;
	}

	public String getMontacb() {
		return montacb;
	}

	public void setMontacb(String montacb) {
		this.montacb = montacb;
	}

	public String getPrevtln() {
		return prevtln;
	}

	public void setPrevtln(String prevtln) {
		this.prevtln = prevtln;
	}

	public int getIndexProductoMulti() {
		return indexProductoMulti;
	}

	public void setIndexProductoMulti(int indexProductoMulti) {
		this.indexProductoMulti = indexProductoMulti;
	}

	public int getIndexRelMulti() {
		return indexRelMulti;
	}

	public void setIndexRelMulti(int indexRelMulti) {
		this.indexRelMulti = indexRelMulti;
	}

	public int getIndexMulti() {
		return indexMulti;
	}

	public void setIndexMulti(int indexMulti) {
		this.indexMulti = indexMulti;
	}

	public double getIndiceArticuloLib() {
		return indiceArticuloLib;
	}

	public void setIndiceArticuloLib(double indiceArticuloLib) {
		this.indiceArticuloLib = indiceArticuloLib;
	}

	public String getLiberado() {
		return liberado;
	}

	public void setLiberado(String liberado) {
		this.liberado = liberado;
	}

	private ArrayList<SuplementopedidoBean> listaSuplementos;
    
    
    
    public String getEstado_linea() {
		return estado_linea;
	}

	public void setEstado_linea(String estado_linea) {
		this.estado_linea = estado_linea;
	}

	public String getDto() {
		return dto;
	}

	public void setDto(String dto) {
		this.dto = dto;
	}

	public String getCod_articulo() {
		return cod_articulo;
	}

	public void setCod_articulo(String cod_articulo) {
		this.cod_articulo = cod_articulo;
	}

	public String getFecha_graduacion() {
		return fecha_graduacion;
	}

	public void setFecha_graduacion(String fecha_graduacion) {
		this.fecha_graduacion = fecha_graduacion;
	}

	public int getNumero_graduacion() {
		return numero_graduacion;
	}

	public void setNumero_graduacion(int numero_graduacion) {
		this.numero_graduacion = numero_graduacion;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getUna_hora() {
		return una_hora;
	}

	public void setUna_hora(String una_hora) {
		this.una_hora = una_hora;
	}

	public String getCod_pedvtcb() {
		return cod_pedvtcb;
	}

	public void setCod_pedvtcb(String cod_pedvtcb) {
		this.cod_pedvtcb = cod_pedvtcb;
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}
    public String getDescripcion_manual() {
		return descripcion_manual;
	}

	public void setDescripcion_manual(String descripcion_manual) {
		this.descripcion_manual = descripcion_manual;
	}

	public String getTiene_suple() {
		return tiene_suple;
	}

	public void setTiene_suple(String tiene_suple) {
		this.tiene_suple = tiene_suple;
	}

	public ArrayList<SuplementopedidoBean> getListaSuplementos() {
		return listaSuplementos;
	}

	public void setListaSuplementos(ArrayList<SuplementopedidoBean> listaSuplementos) {
		this.listaSuplementos = listaSuplementos;
	}

	public String getTiene_grupo() {
		return tiene_grupo;
	}

	public void setTiene_grupo(String tiene_grupo) {
		this.tiene_grupo = tiene_grupo;
	}

	public String getGrad_fecha() {
		return grad_fecha;
	}

	public void setGrad_fecha(String grad_fecha) {
		this.grad_fecha = grad_fecha;
	}

	public int getGrad_numero() {
		return grad_numero;
	}

	public void setGrad_numero(int grad_numero) {
		this.grad_numero = grad_numero;
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

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getCilindro() {
		return cilindro;
	}

	public void setCilindro(double cilindro) {
		this.cilindro = cilindro;
	}

	public int getEje() {
		return eje;
	}

	public void setEje(int eje) {
		this.eje = eje;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public float getDesdediametro() {
		return desdediametro;
	}

	public void setDesdediametro(float desdediametro) {
		this.desdediametro = desdediametro;
	}

	public String getCodigoSap() {
		return codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	public int getCdg_correlativo_multioferta() {
		return cdg_correlativo_multioferta;
	}

	public void setCdg_correlativo_multioferta(int cdg_correlativo_multioferta) {
		this.cdg_correlativo_multioferta = cdg_correlativo_multioferta;
	}

	public int getPrecio_costo() {
		return precio_costo;
	}

	public void setPrecio_costo(int precio_costo) {
		this.precio_costo = precio_costo;
	}

	public int getPrecio_sin_iva() {
		return precio_sin_iva;
	}

	public void setPrecio_sin_iva(int precio_sin_iva) {
		this.precio_sin_iva = precio_sin_iva;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public void setSubAlm(String subAlm) {
        this.subAlm = subAlm;
    }

    public void setSupl(String supl) {
        this.supl = supl;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getDescuento() {
        return descuento;
    }

    public int getEstado() {
        return estado;
    }

    public String getGrupo() {
        return grupo;
    }

    public int getImporte() {
        return importe;
    }

    public String getSubAlm() {
        return subAlm;
    }

    public String getSupl() {
        return supl;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCod_barra() {
        return cod_barra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecio() {
        return precio;
    }

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getSubFamilia() {
		return subFamilia;
	}

	public void setSubFamilia(String subFamilia) {
		this.subFamilia = subFamilia;
	}

	public String getGrupoFamilia() {
		return grupoFamilia;
	}

	public void setGrupoFamilia(String grupoFamilia) {
		this.grupoFamilia = grupoFamilia;
	}

	public String getCodigoMultioferta() {
		return codigoMultioferta;
	}

	public void setCodigoMultioferta(String codigoMultioferta) {
		this.codigoMultioferta = codigoMultioferta;
	}

	public String getTipoFamilia() {
		return tipoFamilia;
	}

	public void setTipoFamilia(String tipoFamilia) {
		this.tipoFamilia = tipoFamilia;
	}
	
	public String getTipotarifa() {
		return tipotarifa;
	}

	public void setTipotarifa(String tipotarifa) {
		this.tipotarifa = tipotarifa;
	}
	public int getPreciomul() {
		return preciomul;
	}

	public void setPreciomul(int preciomul) {
		this.preciomul = preciomul;
	}

	public String getTarifalocal() {
		return tarifalocal;
	}

	public void setTarifalocal(String tarifalocal) {
		this.tarifalocal = tarifalocal;
	}
	public String getTipoe() {
		return tipoe;
	}

	public void setTipoe(String tipoe) {
		this.tipoe = tipoe;
	}

	public String getPromopar() {
		return promopar;
	}

	public void setPromopar(String promopar) {
		this.promopar = promopar;
	}
}
