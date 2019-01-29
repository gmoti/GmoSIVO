package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.utils.Constantes;

public class ControllerMenuPrincipal implements Serializable{	
	
	private static final long serialVersionUID = 7591341824630787025L;
	Session sess = Sessions.getCurrent();
	
	private Window window;	
	HashMap<String,Object> objetos;
	
	private String usuario;
	private String sucursal;
	private String sucursalDes;
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view) {
		
		Selectors.wireComponents(view, this, false);
		
		window=null;
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);
		sucursal = (String)sess.getAttribute(Constantes.STRING_SUCURSAL);
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		
	}
	
	
	@Command
	public void seleccionMenu(@BindingParam("arg")String arg ) {
		
		switch (arg) {
		
			//transacciones
		case "M1_1":
			window = (Window)Executions.createComponents(
	                "/zul/venta_directa/VentaDirectaBS.zul", null, null);
			window.doModal();
	        break;
			
		case "M1_2":
			objetos = new HashMap<String,Object>();
			objetos.put("origen", "menu");
			window = (Window)Executions.createComponents(
	                "/zul/presupuestos/PresupuestoBS.zul", null, objetos);			
	        window.doModal();
	        break;
	        
		case "M1_3":
			objetos = new HashMap<String,Object>();
			objetos.put("origen", "menu");
			window = (Window)Executions.createComponents(
	                "/zul/encargos/encargosBS.zul", null, objetos);			
	        window.doModal();
	        break; 
	        
		case "M1_4":			
			window = (Window)Executions.createComponents(
	                "/zul/mantenedores/LiberacionEncargos.zul", null, null);			
	        window.doModal();
	        break;    
			
	        //mantenedores
		case "M2_1":	
			window = (Window)Executions.createComponents(
	                "/zul/mantenedores/ClientesBS.zul", null, null);
			
	        window.doModal();
	        break;
		
		case "M2_2":	
			window = (Window)Executions.createComponents(
	                "/zul/mantenedores/GraduacionClientesBS.zul", null, null);
			
	        window.doModal();
	        break;    
	        
		case "M2_3":	
			window = (Window)Executions.createComponents(
	                "/zul/mantenedores/MedicoBS.zul", null, null);
			
	        window.doModal();
	        break; 
	        
		case "M2_4":	
			objetos = new HashMap<String,Object>();
			objetos.put("origen", "menu");
			window = (Window)Executions.createComponents(
	                "/zul/mantenedores/Albaran.zul", null, objetos);
			
	        window.doModal();
	        break; 
	    
		case "M2_5":	
			window = (Window)Executions.createComponents(
	                "/zul/mantenedores/CambioFolio.zul", null, null);
			
	        window.doModal();
	        break;    
	        
	        
	        //reportes
		case "M3_1":			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/ListadoInformeOptico.zul", null, null);
			
	        window.doModal();			
			break;
			
		case "M3_2":
			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/ListadoTotalDia.zul", null, null);
			
	        window.doModal();			
			break;
			
		case "M3_3":
			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/ListadoBoletas.zul", null, null);
			
	        window.doModal();			
			break;
			
		case "M3_4":
			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/ListadoPresupuestos.zul", null, null);
			
	        window.doModal();			
			break;
			
		case "M3_5":
			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/ListadoTrabajosPendientes.zul", null, null);
			
	        window.doModal();			
			break;
			
		case "M3_6":
			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/BusquedaGeneralArticulos.zul", null, null);
			
	        window.doModal();			
			break;	
			
		case "M3_7":
			
			window = (Window)Executions.createComponents(
	                "/zul/reportes/CopiaGuiaBoleta.zul", null, null);
			
	        window.doModal();			
			break;	
			

		default:
			break;
		}		
	}
	
	@Command
	public void seleccionMenuEspecial(@BindingParam("arg")String arg, @BindingParam("win")Window win ) {
		
		switch (arg) {
		
		case "M4_1":
			
			win.detach();	        			
			break;
			
		case "M4_2":
			
			win.detach();			
			break;	
		
		default:
			break;		
		}
		
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getSucursal() {
		return sucursal;
	}


	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}


	public String getSucursalDes() {
		return sucursalDes;
	}


	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}
	
	
	
	

}
