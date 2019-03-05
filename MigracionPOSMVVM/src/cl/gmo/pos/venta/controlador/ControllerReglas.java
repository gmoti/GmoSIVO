package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import bsh.EvalError;
import bsh.Interpreter;



public class ControllerReglas implements Serializable {

	
	private static final long serialVersionUID = 3239059522354989614L;
	
	private String script;
	private String resultado;
	
	Interpreter i = new Interpreter();
	Object result;
	
	@Init
	public void inicial() {		
		script = "import cl.gmo.pos.venta.web.forms.VentaPedidoForm;" +
				 "import cl.gmo.pos.venta.web.beans.ProductosBean;"+
				 "import java.util.ArrayList;"+

				 "ArrayList  pbs = new  ArrayList();"+
				 "ProductosBean pb;"+
				 "VentaPedidoForm ventaPedidoForm;"+

				 "ventaPedidoForm = new VentaPedidoForm();"+ 
				 "pb = new ProductosBean ();"+
		         "pbs.add(pb);"+

				 "ventaPedidoForm.setListaProductos(pbs);"+

				 "int x=0;"+

		"for(ProductosBean pbw : ventaPedidoForm.getListaProductos()) {"+
		"x++;"+
		"}"+

		"resultado=x;";
		resultado= "";
	}
	
	@NotifyChange({"script","resultado"})
	@Command
	public void evaluar() {
		
		result = new Object();
		
		try {
			i.eval(script);
			i.set("a", 1);
			
			int res = (int)i.get("resultado");
			
			resultado= String.valueOf(res);
			System.out.println("salida:" + res);
		} catch (EvalError e) {			
			e.printStackTrace();
		}
		
		
	}
	
	
	public int _PI() {
		return 180;
	}
	
	
	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
}
