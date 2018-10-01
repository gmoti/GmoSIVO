package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

public class BeanGlobal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 560122016634691174L;
	
	private Object obj_1;
	private Object obj_2;
	
	private Object obj_3;
	private Object obj_4;
	
	private Object obj_5;
	private Object obj_6;
	
	public BeanGlobal(Object obj_1, Object obj_2, Object obj_3) {		
		this.obj_1 = obj_1;
		this.obj_2 = obj_2;
		this.obj_3 = obj_3;
	}
	
	public BeanGlobal() {}
	
	public Object getObj_1() {
		return obj_1;
	}

	public void setObj_1(Object obj_1) {
		this.obj_1 = obj_1;
	}

	public Object getObj_2() {
		return obj_2;
	}

	public void setObj_2(Object obj_2) {
		this.obj_2 = obj_2;
	}

	public Object getObj_3() {
		return obj_3;
	}

	public void setObj_3(Object obj_3) {
		this.obj_3 = obj_3;
	}

	public Object getObj_4() {
		return obj_4;
	}

	public void setObj_4(Object obj_4) {
		this.obj_4 = obj_4;
	}

	public Object getObj_5() {
		return obj_5;
	}

	public void setObj_5(Object obj_5) {
		this.obj_5 = obj_5;
	}

	public Object getObj_6() {
		return obj_6;
	}

	public void setObj_6(Object obj_6) {
		this.obj_6 = obj_6;
	}	
	
	

}
