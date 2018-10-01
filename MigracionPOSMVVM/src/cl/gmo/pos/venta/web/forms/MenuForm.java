/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.SucursalesBean;

/**
 *
 * @author Advice70
 */
public class MenuForm extends GenericForm{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 4874696890859570125L;


	//Define la accion a realizar en el menu principal
    private String accion=Constantes.STRING_BLANCO;
    
    
    //menu y submenu ventas
    private String venta=Constantes.STRING_BLANCO;
    private String ventaVentaDirecta=Constantes.STRING_BLANCO;
    private String ventaPresupuesto=Constantes.STRING_BLANCO;
    private String ventaPedido=Constantes.STRING_BLANCO;
    private String ventaAlbaranes=Constantes.STRING_BLANCO;
    private String ventaLiberacionEncargo=Constantes.STRING_BLANCO;
    
    //menu y submenu mantenedores
    private String mantenedores=Constantes.STRING_BLANCO;
    private String mantenedoresClientes=Constantes.STRING_BLANCO;
    private String mantenedoresGraduacionClientes=Constantes.STRING_BLANCO;
    private String mantenedoresMedico =Constantes.STRING_BLANCO;
    private String mantenedoresDevolucion =Constantes.STRING_BLANCO;
    private String mantenedoresEntregaPedido =Constantes.STRING_BLANCO;
    private String mantenedoresCambioFolio=Constantes.STRING_BLANCO;
    
    
    //menu y submenu informe
    private String informe=Constantes.STRING_BLANCO;
    private String informeInformeTotalDia=Constantes.STRING_BLANCO;

    private String codigoSucursal;
    private ArrayList<SucursalesBean> colSucursales = new ArrayList<SucursalesBean>();
    
    private String usuario=Constantes.STRING_BLANCO;
    private String nombre_sucursal=Constantes.STRING_BLANCO;
    
	
    
	public String getMantenedoresCambioFolio() {
		return mantenedoresCambioFolio;
	}
	public void setMantenedoresCambioFolio(String mantenedoresCambioFolio) {
		this.mantenedoresCambioFolio = mantenedoresCambioFolio;
	}
	public String getMantenedoresEntregaPedido() {
		return mantenedoresEntregaPedido;
	}
	public void setMantenedoresEntregaPedido(String mantenedoresEntregaPedido) {
		this.mantenedoresEntregaPedido = mantenedoresEntregaPedido;
	}
	public String getMantenedoresDevolucion() {
		return mantenedoresDevolucion;
	}
	public void setMantenedoresDevolucion(String mantenedoresDevolucion) {
		this.mantenedoresDevolucion = mantenedoresDevolucion;
	}
	public String getCodigoSucursal() {
		return codigoSucursal;
	}
	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}
	public ArrayList<SucursalesBean> getColSucursales() {
		return colSucursales;
	}
	public void setColSucursales(ArrayList<SucursalesBean> colSucursales) {
		this.colSucursales = colSucursales;
	}
	public String getMantenedoresMedico() {
		return mantenedoresMedico;
	}
	public void setMantenedoresMedico(String mantenedoresMedico) {
		this.mantenedoresMedico = mantenedoresMedico;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getVenta() {
		return venta;
	}
	public void setVenta(String venta) {
		this.venta = venta;
	}
	public String getVentaVentaDirecta() {
		return ventaVentaDirecta;
	}
	public void setVentaVentaDirecta(String ventaVentaDirecta) {
		this.ventaVentaDirecta = ventaVentaDirecta;
	}
	public String getVentaPresupuesto() {
		return ventaPresupuesto;
	}
	public void setVentaPresupuesto(String ventaPresupuesto) {
		this.ventaPresupuesto = ventaPresupuesto;
	}
	public String getVentaPedido() {
		return ventaPedido;
	}
	public void setVentaPedido(String ventaPedido) {
		this.ventaPedido = ventaPedido;
	}
	public String getVentaAlbaranes() {
		return ventaAlbaranes;
	}
	public void setVentaAlbaranes(String ventaAlbaranes) {
		this.ventaAlbaranes = ventaAlbaranes;
	}
	public String getVentaLiberacionEncargo() {
		return ventaLiberacionEncargo;
	}
	public void setVentaLiberacionEncargo(String ventaLiberacionEncargo) {
		this.ventaLiberacionEncargo = ventaLiberacionEncargo;
	}
	public String getMantenedores() {
		return mantenedores;
	}
	public void setMantenedores(String mantenedores) {
		this.mantenedores = mantenedores;
	}
	public String getMantenedoresClientes() {
		return mantenedoresClientes;
	}
	public void setMantenedoresClientes(String mantenedoresClientes) {
		this.mantenedoresClientes = mantenedoresClientes;
	}
	public String getMantenedoresGraduacionClientes() {
		return mantenedoresGraduacionClientes;
	}
	public void setMantenedoresGraduacionClientes(
			String mantenedoresGraduacionClientes) {
		this.mantenedoresGraduacionClientes = mantenedoresGraduacionClientes;
	}
	public String getInforme() {
		return informe;
	}
	public void setInforme(String informe) {
		this.informe = informe;
	}
	public String getInformeInformeTotalDia() {
		return informeInformeTotalDia;
	}
	public void setInformeInformeTotalDia(String informeInformeTotalDia) {
		this.informeInformeTotalDia = informeInformeTotalDia;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre_sucursal() {
		return nombre_sucursal;
	}
	public void setNombre_sucursal(String nombre_sucursal) {
		this.nombre_sucursal = nombre_sucursal;
	}   
	
}
