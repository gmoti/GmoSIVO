/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

/**
 *
 * @author Advice70
 */
public class VentaDirectaBean {
    
    private int numero_caja;
    private String encabezado_ticket;
    private int numero_ticket;
    private String cajero;
    private String fecha;
    private String hora;
    private String tipo_albaran;
    private double descuento;
    private int valor_total;
    private String agente;
    private int cliente;
    private String nombre_cliente;
    private String divisa;
    private int cambio;
    private String forma_pago;
    private ArrayList<ProductosBean> lista_productos;
    private ArrayList<CajaBean> listaCajas;
    private int porcentaje_descuento_max;
    
    
    
    
    public int getPorcentaje_descuento_max() {
		return porcentaje_descuento_max;
	}

	public void setPorcentaje_descuento_max(int porcentaje_descuento_max) {
		this.porcentaje_descuento_max = porcentaje_descuento_max;
	}

	public String getForma_pago() {
		return forma_pago;
	}

	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}

	public String getTipo_albaran() {
		return tipo_albaran;
	}

	public void setTipo_albaran(String tipo_albaran) {
		this.tipo_albaran = tipo_albaran;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public int getValor_total() {
		return valor_total;
	}

	public void setValor_total(int valor_total) {
		this.valor_total = valor_total;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public ArrayList<CajaBean> getListaCajas() {
		return listaCajas;
	}

	public void setListaCajas(ArrayList<CajaBean> listaCajas) {
		this.listaCajas = listaCajas;
	}

	public VentaDirectaBean(){} 

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

    public void setLista_productos(ArrayList<ProductosBean> lista_productos) {
        this.lista_productos = lista_productos;
    }

    public void setNumero_caja(int numero_caja) {
        this.numero_caja = numero_caja;
    }

    public void setNumero_ticket(int numero_ticket) {
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

    public ArrayList<ProductosBean> getLista_productos() {
        return lista_productos;
    }

    public int getNumero_caja() {
        return numero_caja;
    }

    public int getNumero_ticket() {
        return numero_ticket;
    }
    
    
    
    
}
