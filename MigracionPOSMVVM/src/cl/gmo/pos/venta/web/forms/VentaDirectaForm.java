/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.forms;
import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.CajaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

/**
 *
 * @author Advice70
 */
/**
 * @author UsuarioAdvise
 *
 */
public class VentaDirectaForm extends GenericForm{
    
    
	private static final long serialVersionUID = 1124195189070323121L;
	private int numero_caja;
    private String encabezado_ticket = Constantes.STRING_BLANCO;
    private String numero_ticket;
    private String cajero=Constantes.STRING_BLANCO;
    private String fecha =Constantes.STRING_BLANCO;
    private String hora =Constantes.STRING_BLANCO;
    private String agente =Constantes.STRING_BLANCO;
    /*SE DESACTIVAN POR QUE SE SE VA A GUARDAR AL USUARIO*/
    private int cliente;
    private String nombreCliente;
    
    private String tipoAlbaran;
    private String divisa =Constantes.STRING_BLANCO;
    private int cambio ;
    private String accion = Constantes.STRING_BLANCO;
    private String nombreCajero = Constantes.STRING_BLANCO;
    private ArrayList<AgenteBean> listaAgentes;
    private ArrayList<ProductosBean> listaProductos;
    private ArrayList<TipoAlbaranBean> listaAlbaranes;
    private ArrayList<CajaBean> listaCajas;
    private String addProducto =Constantes.STRING_BLANCO;
    private int cantidad;
    private int sumaTotal;
    private int sumaTotalFinal;
    private double descuentoTotal;
    private String estado;
    private int porcentaje_descuento_max;
    private int cantidad_productos;
    private String codigo_mult;
    private int index_multi;
    
    private int index_multi_eliminar;
   
    /*LMARIN 20140819 - SE AGREGAN ESTOS CAMPOS POR QUE AHORA SE GUARDARA EL CLIENTE*/
    private String nif;
	private String dv;
	private String codigo_cliente;
	private String nombre;
	
	//BOLETA ELECTRONICA 20150601
	private String tipoimp;
	private String estado_boleta;
    
	
	public VentaDirectaForm() {
		
		listaAgentes = new ArrayList<AgenteBean>();
		listaProductos = new ArrayList<ProductosBean>();
		listaAlbaranes = new ArrayList<TipoAlbaranBean>();
		listaCajas = new ArrayList<CajaBean>();
		
		numero_caja=0;
		cliente=0;
		cambio=0;
		cantidad=0;
		sumaTotal=0;
		sumaTotalFinal=0;
		descuentoTotal=0;
		porcentaje_descuento_max=0;
		cantidad_productos=0;
		index_multi=0;
		index_multi_eliminar=0;
	}
	

	public String getEstado_boleta() {
		return estado_boleta;
	}

	public void setEstado_boleta(String estado_boleta) {
		this.estado_boleta = estado_boleta;
	}

	public String getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(String codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
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

	public int getIndex_multi_eliminar() {
		return index_multi_eliminar;
	}

	public void setIndex_multi_eliminar(int index_multi_eliminar) {
		this.index_multi_eliminar = index_multi_eliminar;
	}

	public int getCantidad_productos() {
		return cantidad_productos;
	}

	public void setCantidad_productos(int cantidad_productos) {
		this.cantidad_productos = cantidad_productos;
	}

	public int getPorcentaje_descuento_max() {
		return porcentaje_descuento_max;
	}

	public void setPorcentaje_descuento_max(int porcentaje_descuento_max) {
		this.porcentaje_descuento_max = porcentaje_descuento_max;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoAlbaran() {
		return tipoAlbaran;
	}

	public void setTipoAlbaran(String tipoAlbaran) {
		this.tipoAlbaran = tipoAlbaran;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public ArrayList<TipoAlbaranBean> getListaAlbaranes() {
		return listaAlbaranes;
	}

	public void setListaAlbaranes(ArrayList<TipoAlbaranBean> listaAlbaranes) {
		this.listaAlbaranes = listaAlbaranes;
	}

	public ArrayList<CajaBean> getListaCajas() {
		return listaCajas;
	}

	public void setListaCajas(ArrayList<CajaBean> listaCajas) {
		this.listaCajas = listaCajas;
	}

	public int getSumaTotalFinal() {
		return sumaTotalFinal;
	}

	public void setSumaTotalFinal(int sumaTotalFinal) {
		this.sumaTotalFinal = sumaTotalFinal;
	}

	public double getDescuentoTotal() {
		return descuentoTotal;
	}

	public void setDescuentoTotal(double descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
	}

	public int getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(int sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getAddProducto() {
		return addProducto;
	}

	public void setAddProducto(String addProducto) {
		this.addProducto = addProducto;
	}

	public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public void setCambio(int cambio) {
        this.cambio = cambio;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public void setEncabezado_ticket(String encabezado_ticket) {
        this.encabezado_ticket = encabezado_ticket;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setNumero_caja(int numero_caja) {
        this.numero_caja = numero_caja;
    }

    public void setNumero_ticket(String numero_ticket) {
        this.numero_ticket = numero_ticket;
    }

    public String getAgente() {
        return agente;
    }

    public String getCajero() {
        return cajero;
    }

    public int getCambio() {
        return cambio;
    }

    public int getCliente() {
        return cliente;
    }

    public String getDivisa() {
        return divisa;
    }

    public String getEncabezado_ticket() {
        return encabezado_ticket;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public int getNumero_caja() {
        return numero_caja;
    }

    public String getNumero_ticket() {
        return numero_ticket;
    }
    
	public ArrayList<AgenteBean> getListaAgentes() {
		return listaAgentes;
	}

	public void setListaAgentes(ArrayList<AgenteBean> listaCajeros) {
		this.listaAgentes = listaCajeros;
	}

	public String getNombreCajero() {
		return nombreCajero;
	}

	public void setNombreCajero(String nombreCajero) {
		this.nombreCajero = nombreCajero;
	}

	public ArrayList<ProductosBean> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ArrayList<ProductosBean> listaProductos) {
		this.listaProductos = listaProductos;
	}
	public String getNif() {
		return nif;
	}
	
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	public String getDv() {
		return dv;
	}
	
	public void setDv(String dv) {
		this.dv = dv;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipoimp() {
		return tipoimp;
	}

	public void setTipoimp(String tipoimp) {
		this.tipoimp = tipoimp;
	}

	public void cleanForm() {
	      this.listaAgentes=null;
	      this.listaProductos=null;
	      this.listaAlbaranes=null;
	      this.listaCajas=null;
	      this.sumaTotal=0;
	      this.sumaTotalFinal=0;
	      this.encabezado_ticket = Constantes.STRING_BLANCO;
	      this.numero_ticket=Constantes.STRING_BLANCO;
	      this.fecha =Constantes.STRING_BLANCO;
	      this.hora =Constantes.STRING_BLANCO;
	      this.agente =Constantes.STRING_BLANCO;
	      this.cliente =0;
	      this.nombreCliente=Constantes.STRING_BLANCO;
	      this.tipoAlbaran=Constantes.STRING_BLANCO;
	      this.divisa =Constantes.STRING_BLANCO;
	      this.cambio =1;
	      this.accion = Constantes.STRING_BLANCO;
	      this.addProducto =Constantes.STRING_BLANCO;
	      this.cantidad = 0;
	      this.descuentoTotal = 0;
	      this.estado = "inicio";
	      this.porcentaje_descuento_max = 0;
	      this.codigo_mult = Constantes.STRING_BLANCO;
	      this.index_multi = Constantes.INT_CERO;
	      this.nif = Constantes.STRING_BLANCO;
	      this.dv = Constantes.STRING_BLANCO;
	      this.nombre = Constantes.STRING_BLANCO;
	}
	
    
}
