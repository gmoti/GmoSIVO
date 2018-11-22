package cl.gmo.pos.venta.controlador;

import java.io.Serializable;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import cl.gmo.pos.venta.controlador.general.CambioFolioDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.CambioFolioForm;

public class ControllerCambioFolio implements Serializable {

	
	private static final long serialVersionUID = -523816914750757157L;
	
	Session sess = Sessions.getCurrent();	
	
	private String usuario;	
	private String sucursalDes;
	private boolean habilitaCampo=true; 
	
	private CambioFolioForm cambioFolioForm;
	private CambioFolioDispatchActions CambioFolioDispatch;
	
	
	
	@Init	
	public void inicial() {
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		
		cambioFolioForm = new CambioFolioForm();
		CambioFolioDispatch = new CambioFolioDispatchActions();
		
		
		CambioFolioDispatch.cargarPagina(cambioFolioForm, sess);
	}

	
	@NotifyChange({"cambioFolioForm","habilitaCampo"})
	@Command
	public void guardarFolio(){
		
		//var habilitaCampo = document.getElementById('habilitaCampo').value;	
		boolean respuesta = false;
		
		int desdeGuia = cambioFolioForm.getDesdeGuia();
		int hastaGuia = cambioFolioForm.getHastaGuia();
		respuesta = validaDesdeHasta(desdeGuia, hastaGuia, "Guia");
		
		
		int desdeBoleta = cambioFolioForm.getDesdeBoleta();
		int hastaBoleta = cambioFolioForm.getHastaBoleta();
		
		if(respuesta){
			respuesta = validaDesdeHasta(desdeGuia, hastaGuia, "Boleta");
		}else return;		
		
		int desdeNota = cambioFolioForm.getDesdeNota();
		int hastaNota = cambioFolioForm.getHastaNota();	
		
		if(respuesta){
			respuesta = validaDesdeHasta(desdeNota, hastaNota, "Nota de credito");
		}else return;
		
		if(respuesta){		
			if(habilitaCampo){				
				cambioFolioForm.setAccion("guardarFolio");
				
				Messagebox.show("Notificacion", "Realmente desea modificar los folios",
						Messagebox.YES | 
						Messagebox.NO, 
						Messagebox.QUESTION, new EventListener<Event>() {			
					@Override
					public void onEvent(Event e) throws Exception {								
						
							if( ((Integer) e.getData()).intValue() == Messagebox.YES ) {								
								
								CambioFolioDispatch.cambioFolio(cambioFolioForm, sess);
								
							}						
						}
				});
				
				habilitaCampo=true;
				
			}else{
				Messagebox.show("Debe habilitar los Folios para ser modificados");
			}	
		}
	}

	private boolean validaDesdeHasta(int desde, int hasta, String texto){
		
		if(desde > hasta){
			Messagebox.show("El inicio de "+texto+" no puede ser mayor al t\u00E9rmino.");
			return false;
		}else{
			int diferencia = hasta - desde;
			if(diferencia > 500){
				Messagebox.show("La cantidad de Folios de "+texto+" no puede ser mayor a 500");
				return false;
			}
		}	
		return true;
	}
	
	@NotifyChange({"habilitaCampo"})
	@Command
	public void habilitarCampos(){
	
		habilitaCampo=false;
	}
	
	//Getter and Setter

	public CambioFolioForm getCambioFolioForm() {
		return cambioFolioForm;
	}

	public void setCambioFolioForm(CambioFolioForm cambioFolioForm) {
		this.cambioFolioForm = cambioFolioForm;
	}
	
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


	public boolean isHabilitaCampo() {
		return habilitaCampo;
	}

	public void setHabilitaCampo(boolean habilitaCampo) {
		this.habilitaCampo = habilitaCampo;
	}
	
	

}
