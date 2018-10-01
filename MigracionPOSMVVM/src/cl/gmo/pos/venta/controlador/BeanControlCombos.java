package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

public class BeanControlCombos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2229168658148253684L;
	
	
	private String comboAgenteEnable;	
	private String comboIdiomaEnable;
	private String comboDivisaEnable;
	private String comboFpagoEnable;
	private String comboPromoEnable;
	private String comboTiposEnable;
	

	
	public BeanControlCombos() {		
		this.comboAgenteEnable = "false";
		this.comboIdiomaEnable = "false";
		this.comboDivisaEnable = "false";
		this.comboFpagoEnable = "false";
		this.comboPromoEnable = "false";
		this.comboTiposEnable = "false";
	}


	public String getComboAgenteEnable() {
		return comboAgenteEnable;
	}


	public void setComboAgenteEnable(String comboAgenteEnable) {
		this.comboAgenteEnable = comboAgenteEnable;
	}


	public String getComboIdiomaEnable() {
		return comboIdiomaEnable;
	}


	public void setComboIdiomaEnable(String comboIdiomaEnable) {
		this.comboIdiomaEnable = comboIdiomaEnable;
	}


	public String getComboDivisaEnable() {
		return comboDivisaEnable;
	}


	public void setComboDivisaEnable(String comboDivisaEnable) {
		this.comboDivisaEnable = comboDivisaEnable;
	}


	public String getComboFpagoEnable() {
		return comboFpagoEnable;
	}


	public void setComboFpagoEnable(String comboFpagoEnable) {
		this.comboFpagoEnable = comboFpagoEnable;
	}


	public String getComboPromoEnable() {
		return comboPromoEnable;
	}


	public void setComboPromoEnable(String comboPromoEnable) {
		this.comboPromoEnable = comboPromoEnable;
	}


	public String getComboTiposEnable() {
		return comboTiposEnable;
	}


	public void setComboTiposEnable(String comboTiposEnable) {
		this.comboTiposEnable = comboTiposEnable;
	}
	
	
	
	
	
	
	
	

}
