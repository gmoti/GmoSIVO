package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.HashMap;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import cl.gmo.pos.venta.controlador.presupuesto.BusquedaConveniosDispatchActions;
import cl.gmo.pos.venta.web.beans.ConvenioLnBean;
import cl.gmo.pos.venta.web.forms.BusquedaConveniosForm;

public class ControllerSeleccionaConvenio implements Serializable {

	
	private static final long serialVersionUID = -4293822807417186796L;
	Session sess = Sessions.getCurrent();
	
	private BusquedaConveniosForm busquedaConveniosForm;
	private BusquedaConveniosDispatchActions busquedaConveniosDispatchActions;
	private ConvenioLnBean convenioLnBean;
	
	private String pVentana;
	private String pOrigen;
	
	@Wire
	private Window winSeleccionaConvenio;	
	
	private Window busquedaConvenio;
	
	HashMap<String,Object> objetos;
	
	@Init
	public void inicial(@ContextParam(ContextType.VIEW) Component view,
						@ExecutionArgParam("busquedaConvenios")BusquedaConveniosForm arg,
						@ExecutionArgParam("ventana")String arg2,
						@ExecutionArgParam("origen")String arg3,
						@ExecutionArgParam("win")Window arg4) {
		
		Selectors.wireComponents(view, this, false);
		
		busquedaConveniosForm = new BusquedaConveniosForm(); 
		convenioLnBean        = new ConvenioLnBean();
		
		busquedaConveniosForm = arg;
		pVentana=arg2;
		pOrigen=arg3;
		busquedaConvenio=arg4;
		 
		
		if (pOrigen.equals("presupuesto")) {
			busquedaConveniosDispatchActions = new BusquedaConveniosDispatchActions();		
			busquedaConveniosDispatchActions.selecciona_convenio(busquedaConveniosForm, sess);
		}
		
		if (pOrigen.equals("encargo")) {
			busquedaConveniosDispatchActions = new BusquedaConveniosDispatchActions();		
			busquedaConveniosDispatchActions.selecciona_convenio(busquedaConveniosForm, sess);
		}
		
		
	}
	
	@NotifyChange({"convenioLnBean","busquedaConveniosForm"})
	@Command
	public void seleccionaConvenio(@BindingParam("arg")ConvenioLnBean arg,
									@BindingParam("index")int arg2) {
		
		convenioLnBean = arg;		
		busquedaConveniosForm.setIndice(String.valueOf(arg2));
		busquedaConveniosForm.setAccion("desplegar_familias");
		busquedaConveniosDispatchActions.selecciona_convenio(busquedaConveniosForm, sess);
		
	}
	
	@Command
	public void aceptaConvenio() {
		
		objetos = new HashMap<String,Object>();	
		objetos.put("busquedaConvenios",busquedaConveniosForm);
		
		if (pVentana.equals("presupuesto")) {
			
			if (pOrigen.equals("presupuesto")) {
				BindUtils.postGlobalCommand(null, null, "respVentanaConvenioPres", objetos);
				winSeleccionaConvenio.detach();
				busquedaConvenio.detach();
			}else {
				BindUtils.postGlobalCommand(null, null, "respVentanaConvenioPres", objetos);
				winSeleccionaConvenio.detach();				
			}
			
		}else if (pVentana.equals("encargo")) {
			
			if (pOrigen.equals("encargo")) {
				BindUtils.postGlobalCommand(null, null, "respVentanaConvenioPedido", objetos);
				winSeleccionaConvenio.detach();
				busquedaConvenio.detach();
			}else {
				BindUtils.postGlobalCommand(null, null, "respVentanaConvenioPedido", objetos);
				winSeleccionaConvenio.detach();				
			}			
		} 		
	}
	

	
	//================ Getter ans Setter =============

	public BusquedaConveniosForm getBusquedaConveniosForm() {
		return busquedaConveniosForm;
	}

	public void setBusquedaConveniosForm(BusquedaConveniosForm busquedaConveniosForm) {
		this.busquedaConveniosForm = busquedaConveniosForm;
	}

	public ConvenioLnBean getConvenioLnBean() {
		return convenioLnBean;
	}

	public void setConvenioLnBean(ConvenioLnBean convenioLnBean) {
		this.convenioLnBean = convenioLnBean;
	}	
	
	
}
