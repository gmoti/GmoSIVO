package cl.gmo.pos.venta.web.beans;

import cl.gmo.pos.venta.utils.Constantes;

public class MenuBean {
 
    
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
    private String mantenedoresMedico=Constantes.STRING_BLANCO;
    private String mantenedoresCambioFolio=Constantes.STRING_BLANCO;
    
    //menu y submenu informe
    private String informe=Constantes.STRING_BLANCO;
    private String informeInformeTotalDia=Constantes.STRING_BLANCO;
    
    //menu devolucion
    private String utilidad=Constantes.STRING_BLANCO;
    private String devolucion=Constantes.STRING_BLANCO;
    private String entregaPedido=Constantes.STRING_BLANCO;
    
    
    
	public String getMantenedoresCambioFolio() {
		return mantenedoresCambioFolio;
	}
	public void setMantenedoresCambioFolio(String mantenedoresCambioFolio) {
		this.mantenedoresCambioFolio = mantenedoresCambioFolio;
	}
	public String getEntregaPedido() {
		return entregaPedido;
	}
	public void setEntregaPedido(String entregaPedido) {
		this.entregaPedido = entregaPedido;
	}
	public String getUtilidad() {
		return utilidad;
	}
	public void setUtilidad(String utilidad) {
		this.utilidad = utilidad;
	}
	public String getDevolucion() {
		return devolucion;
	}
	public void setDevolucion(String devolucion) {
		this.devolucion = devolucion;
	}
	public String getMantenedoresMedico() {
		return mantenedoresMedico;
	}
	public void setMantenedoresMedico(String mantenedoresMedico) {
		this.mantenedoresMedico = mantenedoresMedico;
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
    
    
}
