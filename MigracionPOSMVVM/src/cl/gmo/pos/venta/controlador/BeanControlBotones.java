package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

public class BeanControlBotones implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5538560204021875073L;
	private String enableGrid;
	private String enableNew;
	private String enablePagar;
	private String enableGrabar;
	private String enableBuscar;
	private String enableEliminar;
	
	private String enablePrint;
	private String enableCliente;
	private String enableTaller;
	private String enableListar;	
	private String enableMulti;
	
	private String enableGenerico1;
	private String enableGenerico2;
	private String enableGenerico3;
	
	
	public BeanControlBotones() {
		
		enableGrid="false";
		enableNew="false";
		enablePagar="false";
		enableGrabar="false";
		enableBuscar="false";	
		enablePrint="false";
		enableCliente="false";
		enableTaller="false";
		enableListar="false";
		enableEliminar="false";
		enableMulti="false";
		
		
		enableGenerico1="false";
		enableGenerico2="false";
		enableGenerico3="false";	
		
	}
	
	
	
	public String getEnableGrid() {
		return enableGrid;
	}
	public void setEnableGrid(String enableGrid) {
		this.enableGrid = enableGrid;
	}
	public String getEnableNew() {
		return enableNew;
	}
	public void setEnableNew(String enableNew) {
		this.enableNew = enableNew;
	}
	public String getEnablePagar() {
		return enablePagar;
	}
	public void setEnablePagar(String enablePagar) {
		this.enablePagar = enablePagar;
	}
	public String getEnableGrabar() {
		return enableGrabar;
	}
	public void setEnableGrabar(String enableGrabar) {
		this.enableGrabar = enableGrabar;
	}
	public String getEnableBuscar() {
		return enableBuscar;
	}
	public void setEnableBuscar(String enableBuscar) {
		this.enableBuscar = enableBuscar;
	}
	public String getEnableGenerico1() {
		return enableGenerico1;
	}
	public void setEnableGenerico1(String enableGenerico1) {
		this.enableGenerico1 = enableGenerico1;
	}
	public String getEnableGenerico2() {
		return enableGenerico2;
	}
	public void setEnableGenerico2(String enableGenerico2) {
		this.enableGenerico2 = enableGenerico2;
	}
	public String getEnableGenerico3() {
		return enableGenerico3;
	}
	public void setEnableGenerico3(String enableGenerico3) {
		this.enableGenerico3 = enableGenerico3;
	}

	public String getEnablePrint() {
		return enablePrint;
	}
	
	public void setEnablePrint(String enablePrint) {
		this.enablePrint = enablePrint;
	}

	public String getEnableCliente() {
		return enableCliente;
	}

	public void setEnableCliente(String enableCliente) {
		this.enableCliente = enableCliente;
	}
	
	public String getEnableTaller() {
		return enableTaller;
	}

	public void setEnableTaller(String enableTaller) {
		this.enableTaller = enableTaller;
	}
	
	public String getEnableListar() {
		return enableListar;
	}

	public void setEnableListar(String enableListar) {
		this.enableListar = enableListar;
	}
	
	public String getEnableEliminar() {
		return enableEliminar;
	}

	public void setEnableEliminar(String enableEliminar) {
		this.enableEliminar = enableEliminar;
	}

	public String getEnableMulti() {
		return enableMulti;
	}

	public void setEnableMulti(String enableMulti) {
		this.enableMulti = enableMulti;
	}

}
