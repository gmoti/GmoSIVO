package cl.gmo.pos.venta.web.forms;

import java.sql.Date;
import java.util.ArrayList;


import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ListadoBoletasBean;

public class ListadoBoletasForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2088979049681515803L;
	private String fecha_inicio;
	private Date fecha_fin;
	private String numero_caja;
	private String numero_venta;
	private String numero_boleta=Constantes.STRING_CERO;
	private ArrayList<ListadoBoletasBean> listaBoletas;
	
	
	
	public String getNumero_boleta() {
		return numero_boleta;
	}
	public void setNumero_boleta(String numero_boleta) {
		this.numero_boleta = numero_boleta;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public Date getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	public String getNumero_caja() {
		return numero_caja;
	}
	public void setNumero_caja(String numero_caja) {
		this.numero_caja = numero_caja;
	}
	public String getNumero_venta() {
		return numero_venta;
	}
	public void setNumero_venta(String numero_venta) {
		this.numero_venta = numero_venta;
	}
	public ArrayList<ListadoBoletasBean> getListaBoletas() {
		return listaBoletas;
	}
	public void setListaBoletas(ArrayList<ListadoBoletasBean> listaBoletas) {
		this.listaBoletas = listaBoletas;
	}
	
	
	

}
