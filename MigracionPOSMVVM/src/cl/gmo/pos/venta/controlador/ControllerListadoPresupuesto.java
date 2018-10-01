package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.protobuf.Message;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.OptimizedAccessorFactory;

import cl.gmo.pos.venta.reporte.dispatch.ListadoPresupuestosDispatchActions;
import cl.gmo.pos.venta.reporte.nuevo.ReportesHelper;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.forms.ListadoPresupuestosForm;




public class ControllerListadoPresupuesto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 984154573804131507L;

	Session sess = Sessions.getCurrent();
	
	@Wire("#reporte4")
	private Window win;
	
	private AMedia fileContent;
	private byte[] bytes;
	private Date fechaInicio;
	private Date fechaFin;
	
	private DivisaBean divisaBean;
	private FormaPagoBean formaPagoBean;

	private ListadoPresupuestosForm listadoPresupuestosForm;
	private ReportesHelper reportesHelper;
	private ListadoPresupuestosDispatchActions listadoPresupuestosDispatchActions;
	
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)	             {
	     Selectors.wireComponents(view, this, false);
	     
	}
	
	@Init
	public void inicial()  { 	
		
		divisaBean = new DivisaBean() ;
		formaPagoBean = new FormaPagoBean();		
		
		listadoPresupuestosForm = new ListadoPresupuestosForm() ;		
		listadoPresupuestosDispatchActions = new ListadoPresupuestosDispatchActions() ;
		reportesHelper = new ReportesHelper() ;
			
		listadoPresupuestosDispatchActions.cargaFormulario(listadoPresupuestosForm, sess);
		
		fechaInicio = new Date(System.currentTimeMillis());
		fechaFin    = new Date(System.currentTimeMillis());
		
	}
	
	@NotifyChange({"*"})
	@Command
	public void reporte() {	
		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaI = dt.format(fechaInicio);
		String fechaF = dt.format(fechaFin);
		
		sess.setAttribute(Constantes.STRING_REPORTER_NOMBRE_SUCURSAL, sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL));
		
		Optional<DivisaBean> divisa = Optional.ofNullable(divisaBean);	
		Optional<FormaPagoBean> fpago  = Optional.ofNullable(formaPagoBean);	
		Optional<Integer> codigo = Optional.ofNullable(listadoPresupuestosForm.getCodigo());
		Optional<String> cliente = Optional.ofNullable(listadoPresupuestosForm.getCliente());
		Optional<String> cerrado = Optional.ofNullable(listadoPresupuestosForm.getCerrado());
		
		if (divisa.isPresent())
			listadoPresupuestosForm.setDivisa(divisaBean.getId());
		else	
			listadoPresupuestosForm.setDivisa("0");
		
		if (fpago.isPresent())
			listadoPresupuestosForm.setDivisa(formaPagoBean.getId());
		else	
			listadoPresupuestosForm.setDivisa("0");
		
		if(!codigo.isPresent())
			listadoPresupuestosForm.setCodigo(0);
		
		if(!cliente.isPresent())
			listadoPresupuestosForm.setCliente("");
		
		if(!cerrado.isPresent())
			listadoPresupuestosForm.setCerrado("0");
		
		listadoPresupuestosForm.setFechaInicio(fechaI);
		listadoPresupuestosForm.setFechaTermino(fechaF);		
		listadoPresupuestosForm.setAgente("");		
		
		try {
			listadoPresupuestosDispatchActions.buscar(listadoPresupuestosForm, sess);
			bytes =  reportesHelper.creaListadoPresupuestos(sess);
			
			final AMedia media = new AMedia("ListadoPresupuesto.pdf", "pdf", "application/pdf", bytes);		
			fileContent = media;
		}catch(Exception e) {
			
			Messagebox.show("No se encuentra información para el reporte");
			
		}
		
		
		fechaInicio = new Date(System.currentTimeMillis());
		fechaFin    = new Date(System.currentTimeMillis());
		
		divisaBean   = null;
		formaPagoBean= null;
		listadoPresupuestosForm.setCodigo(0);		
		listadoPresupuestosForm.setCliente("");			
	}
	
	@NotifyChange({"divisaBean","formaPagoBean"})
	@Command
	public void comboSetNull(@BindingParam("objetoBean")Object arg) {
		
		if (arg instanceof DivisaBean) 
			divisaBean=null;			
		
		if (arg instanceof FormaPagoBean)
			formaPagoBean=null;				
		
				
			
	}

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	public ListadoPresupuestosForm getListadoPresupuestosForm() {
		return listadoPresupuestosForm;
	}

	public void setListadoPresupuestosForm(ListadoPresupuestosForm listadoPresupuestosForm) {
		this.listadoPresupuestosForm = listadoPresupuestosForm;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public DivisaBean getDivisaBean() {
		return divisaBean;
	}

	public void setDivisaBean(DivisaBean divisaBean) {
		this.divisaBean = divisaBean;
	}

	public FormaPagoBean getFormaPagoBean() {
		return formaPagoBean;
	}

	public void setFormaPagoBean(FormaPagoBean formaPagoBean) {
		this.formaPagoBean = formaPagoBean;
	}
	
	
	
	
	

}
