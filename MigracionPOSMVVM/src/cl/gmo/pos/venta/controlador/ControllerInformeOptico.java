package cl.gmo.pos.venta.controlador;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;
import cl.gmo.pos.venta.reporte.nuevo.InformeOpticoHelper;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.forms.InformeOpticoForm;


public class ControllerInformeOptico implements Serializable{
	
	
	private static final long serialVersionUID = 3911700019027519574L;

	Session sess = Sessions.getCurrent();
	
	
	private AMedia fileContent;	
	private String nif;	
	private byte[] bytes;
	
    private InformeOpticoHelper informeOpticoHelper;
    private InformeOpticoForm informeOpticoForm;
    private ReportesHelper reportesHelper;
    private ClienteBean clienteBean;
    
    private String usuario;	
	private String sucursalDes;
	private String local;
	private String nombreSucural;
 
	
	@Init
	public void inicial()  { 		
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);
		local = (String) sess.getAttribute("sucursal");	
		//nombreSucural = (String)sess.getAttribute("nombreSucural");
		
		informeOpticoHelper = new InformeOpticoHelper();
		informeOpticoForm = new InformeOpticoForm();
		reportesHelper = new ReportesHelper(); 
		
		clienteBean = new ClienteBean();				
		
	}
	
	@Command
	public void buscarCliente() {
		
		HashMap<String,Object> objetos = new HashMap<String,Object>();		
		objetos.put("retorno","buscarClienteInfOptico");		
		
		Window winBusquedaClientes = (Window)Executions.createComponents(
                "/zul/general/BusquedaClientes.zul", null, objetos);
		
		winBusquedaClientes.doModal();	
		
	}
	
	@NotifyChange({"informeOpticoForm"})
	@GlobalCommand
	public void buscarClienteInfOptico(@BindingParam("cliente")ClienteBean cliente) {
		
		informeOpticoForm.setNombreCli(cliente.getNombre() + " " + cliente.getApellido());
		clienteBean = cliente;
	}
	
	
	@NotifyChange("fileContent")
	@Command
	public void reporte() {  		
		
		Optional<String> cli = Optional.ofNullable(clienteBean.getCodigo());
		
		if (cli.isPresent())
			informeOpticoForm.setCdgCli(clienteBean.getCodigo());
		else {
			Messagebox.show("Debe un ingresar un cliente");
			return;
		}
		
		try {		
			
			informeOpticoForm = informeOpticoHelper.traeInformeOptico(informeOpticoForm.getCdgCli(), null, null, informeOpticoForm);
			sess.setAttribute("InformeOptico", informeOpticoForm);			
			bytes = reportesHelper.creaListadoOptico(sess);
					
			final AMedia media = new AMedia("prueba.pdf", "pdf", "application/pdf", bytes);			
			
			fileContent = media;			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public InformeOpticoForm getInformeOpticoForm() {
		return informeOpticoForm;
	}

	public void setInformeOpticoForm(InformeOpticoForm informeOpticoForm) {
		this.informeOpticoForm = informeOpticoForm;
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
	
	
	


}
