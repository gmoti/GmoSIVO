package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.general.BusquedaClientesDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.actions.GraduacionesDispatchActions;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.forms.BusquedaClientesForm;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;

public class ControllerGraduacionCliente implements Serializable{	
	
	private static final long serialVersionUID = 4892447350424455478L;
	Session sess = Sessions.getCurrent();	
	
	private String usuario;	
	private String sucursalDes;
	
	GraduacionesForm graduacionesForm ;
	GraduacionesBean graduacionesBean ;
	BusquedaClientesForm busquedaClientesForm;
	
	GraduacionesDispatchActions graduacionesDispatch; 	
	BusquedaClientesDispatchActions busquedaClientesDispatch;
	
	private Date fechaEmision;
	private Date fechaProxRevision;
	
	HashMap<String,Object> objetos;
	

	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		graduacionesForm = new GraduacionesForm();
		graduacionesBean = new GraduacionesBean();
		busquedaClientesForm = new BusquedaClientesForm();
		
		graduacionesDispatch = new GraduacionesDispatchActions();
		busquedaClientesDispatch = new BusquedaClientesDispatchActions();
		
		graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
		
	}
	
	//==========Operaciones Principales de la clase ================
	//==============================================================
	@NotifyChange({"graduacionesForm"})
	@Command
	public void nuevaGraduacion() {
		
		//solo limpia los campos visualmente
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void ingresoPresupuesto(@BindingParam("win")Window win) {
		
		sess.setAttribute("cliente",graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente",graduacionesForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "graduacion");	
		
		win.detach();
		
		Window winPresup = (Window)Executions.createComponents(
                "/zul/presupuestos/presupuesto.zul", null, objetos);			
		winPresup.doModal();
		
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void ingresoEncargo(@BindingParam("win")Window win) {
		
		sess.setAttribute("cliente",graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente",graduacionesForm.getNombre_cliente());
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();	
		objetos.put("origen", "graduacion");	
		
		win.detach();
		
		Window winEncargo = (Window)Executions.createComponents(
                "/zul/encargos/encargos.zul", null, objetos);			
		winEncargo.doModal();
		
	}
	
	@Command
	public void ingresoContactologia(){
		
		sess.setAttribute(Constantes.STRING_CLIENTE, graduacionesForm.getCliente());
		sess.setAttribute("nombre_cliente", graduacionesForm.getNombre_cliente());
		
		Window winContactologia = (Window)Executions.createComponents(
                "/zul/mantenedores/Contactologia.zul", null, null);
		
		winContactologia.doModal();			
	}
	
	@NotifyChange({"graduacionesForm"})
	@Command
	public void insertarGraduacion(){
		
		/*document.getElementById('accion').value='insertarGraduacion';
		var pagina = document.getElementById('pagina').value;
		var existe_graduacion = document.getElementById('existe_graduacion').value;
		
		var respuesta1 = validaInformacion();
		var respuesta  = true;
		if(respuesta1 == true){
			respuesta  = validaGraduacion();
		}
		
		//valida diferente adicion
		var check = document.getElementById('diferenteADD');
		var pasaValidacionADD = true;
		if (!check.checked) {
			if ("" != document.getElementById('OI_adicion').value || "" != document.getElementById('OD_adicion').value) {
				if (document.getElementById('OI_adicion').value != 	document.getElementById('OD_adicion').value)
				{
					pasaValidacionADD = false;
				}
			}
			
		}
		//fin
		
		if (pasaValidacionADD) {
			if("true" != existe_graduacion){	
				
				if(respuesta == true && respuesta1 == true){
					if("NOGRABAR" != pagina){
						var OD_esfera = document.getElementById('OD_esfera').value;
						var OD_cilindro = document.getElementById('OD_cilindro').value;
						var OD_eje = document.getElementById('OD_eje').value;
						var OD_cerca = document.getElementById('OD_cerca').value; 
						var OD_adicion = document.getElementById('OD_adicion').value;
						var OD_dnpl = document.getElementById('OD_dnpl').value;
						var OD_dnpc = document.getElementById('OD_dnpc').value;
						
						if(""==OD_eje){
							OD_eje="      ";
						}
						if(""==OD_cerca){
							OD_cerca="       ";
						}
						if(""==OD_adicion){
							OD_adicion="       ";
						}
						if(""==OD_dnpl){
							OD_dnpl="       ";
						}
						if(""==OD_dnpc){
							OD_dnpc="       ";
						}
						
						var OI_esfera = document.getElementById('OI_esfera').value;
						var OI_cilindro = document.getElementById('OI_cilindro').value;
						var OI_eje = document.getElementById('OI_eje').value;
						var OI_cerca = document.getElementById('OI_cerca').value; 
						var OI_adicion = document.getElementById('OI_adicion').value;
						var OI_dnpl = document.getElementById('OI_dnpl').value;
						var OI_dnpc = document.getElementById('OI_dnpc').value;
						
						if(""==OI_eje){
							OI_eje="      ";
						}
						if(""==OI_cerca){
							OI_cerca="       ";
						}
						if(""==OI_adicion){
							OI_adicion="       ";
						}
						if(""==OI_dnpl){
							OI_dnpl="       ";
						}
						if(""==OI_dnpc){
							OI_dnpc="       ";
						}
						
						
						var mensaje1 = "Estos son los datos de la receta registrados:    \n ";
						var mensaje2 = "  Ojo   Esf         Cil       Eje       Cerca         Add         DNPL         DNPC  \n ";
						var mensaje3 = "   D     "+OD_esfera+"       "+OD_cilindro+"       "+OD_eje+"          "+OD_cerca+"           "+OD_adicion+"         "+OD_dnpl+"          "+OD_dnpc+"\n ";
						var mensaje4 = "    I      "+OI_esfera+"       "+OI_cilindro+"       "+OI_eje+"          "+OI_cerca+"           "+OI_adicion+"         "+OI_dnpl+"          "+OI_dnpc+"\n ";
						var mensaje5 = "\u00BFEst\u00E1 seguro(a) que esta correctos?";
						var mensaje = mensaje1 + mensaje2 + mensaje3 + mensaje4 + mensaje5;
						var respuesta3 = confirm(mensaje);		
						if(respuesta3){
							document.forms[0].submit();
						}
					
					}else if("NOGRABAR" == pagina){
						
						var respuesta4 = confirm("Desea modificar la receta?");							
						if(respuesta4 == true){						
							var OD_esfera = document.getElementById('OD_esfera').value;
							var OD_cilindro = document.getElementById('OD_cilindro').value;
							var OD_eje = document.getElementById('OD_eje').value;
							var OD_cerca = document.getElementById('OD_cerca').value; 
							var OD_adicion = document.getElementById('OD_adicion').value;
							var OD_dnpl = document.getElementById('OD_dnpl').value;
							var OD_dnpc = document.getElementById('OD_dnpc').value;
							
							if(""==OD_eje){
								OD_eje="      ";
							}
							if(""==OD_cerca){
								OD_cerca="       ";
							}
							if(""==OD_adicion){
								OD_adicion="       ";
							}
							if(""==OD_dnpl){
								OD_dnpl="       ";
							}
							if(""==OD_dnpc){
								OD_dnpc="       ";
							}
							
							var OI_esfera = document.getElementById('OI_esfera').value;
							var OI_cilindro = document.getElementById('OI_cilindro').value;
							var OI_eje = document.getElementById('OI_eje').value;
							var OI_cerca = document.getElementById('OI_cerca').value; 
							var OI_adicion = document.getElementById('OI_adicion').value;
							var OI_dnpl = document.getElementById('OI_dnpl').value;
							var OI_dnpc = document.getElementById('OI_dnpc').value;
							
							if(""==OI_eje){
								OI_eje="      ";
							}
							if(""==OI_cerca){
								OI_cerca="       ";
							}
							if(""==OI_adicion){
								OI_adicion="       ";
							}
							if(""==OI_dnpl){
								OI_dnpl="       ";
							}
							if(""==OI_dnpc){
								OI_dnpc="       ";
							}
							
							var mensaje1 = "Estos son los datos de la receta registrados:    \n ";
							var mensaje2 = "  Ojo   Esf         Cil       Eje       Cerca         Add         DNPL         DNPC  \n ";
							var mensaje3 = "   D     "+OD_esfera+"          "+OD_cilindro+"        "+OD_eje+"          "+OD_cerca+"             "+OD_adicion+"          "+OD_dnpl+"           "+OD_dnpc+"\n ";
							var mensaje4 = "    I      "+OI_esfera+"          "+OI_cilindro+"        "+OI_eje+"          "+OI_cerca+"             "+OI_adicion+"          "+OI_dnpl+"           "+OI_dnpc+"\n ";
							var mensaje5 = "\u00BFEst\u00E1 seguro(a) que esta correctos?";
							var mensaje = mensaje1 + mensaje2 + mensaje3 + mensaje4 + mensaje5;
							var respuesta3 = confirm(mensaje);		
							if(respuesta3){
								document.getElementById('accion').value='modificarGraduacion';
								document.forms[0].submit();
							}
						}	
					}
					
				}			
			}else{
				//if("NOGRABAR" == pagina)
				alert("La receta esta asociada a una venta, no puede ser modificada, debe generar una nueva receta");
			}
		}
		else
			{
				alert("La ADD para cada Ojo es distinta");
			}*/
		
		
	}
	
	
	
	
	/*{
		
		
		graduacionesDispatch.IngresaGraduacion(graduacionesForm, sess);
	}*/
	
	@Command
	public void cerrar(@BindingParam("win")  Window win) {
	    win.detach();
	}
	
	
	
	//===================================================
	//=======Metodos secundarios ========================
	//===================================================
	@Command
	public void busquedaCliente() {
		
		objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteGraduacion");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();		
	}	
	

	@NotifyChange({"graduacionesForm"})
	@GlobalCommand
	public void buscarClienteGraduacion(@BindingParam("cliente")ClienteBean cliente) {
		
		graduacionesForm.setCliente(Integer.parseInt(cliente.getCodigo()));
		graduacionesDispatch.cargaFormulario(graduacionesForm, sess);
		
		graduacionesForm.setNombre_cliente(cliente.getNombre()+ " " + cliente.getApellido());
	}
	
	
	
	
	
	
	// Getter and Setter ================================
	// ==================================================

	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getSucursalDes() {
		return sucursalDes;
	}
	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}
	
	public GraduacionesForm getGraduacionesForm() {
		return graduacionesForm;
	}
	
	public void setGraduacionesForm(GraduacionesForm graduacionesForm) {
		this.graduacionesForm = graduacionesForm;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaProxRevision() {
		return fechaProxRevision;
	}

	public void setFechaProxRevision(Date fechaProxRevision) {
		this.fechaProxRevision = fechaProxRevision;
	}
	
}
