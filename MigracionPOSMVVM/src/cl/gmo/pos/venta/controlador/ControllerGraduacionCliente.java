package cl.gmo.pos.venta.controlador;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;


import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.actions.ClienteDispatchActions;
import cl.gmo.pos.venta.web.actions.GraduacionesDispatchActions;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.forms.ClienteForm;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;

public class ControllerGraduacionCliente extends GraduacionesForm{
	
	
	private boolean diferenteAdicion = false;
	private boolean prismaInterno= false;
	private boolean prismaExterno= false;
	private String sagente = "Seleccione Agente";
	
	GraduacionesForm cform ;
	GraduacionesBean gradb ;
	GraduacionesDispatchActions grad_dis = new GraduacionesDispatchActions();
	Utils util = new Utils();
	Session sesion = Sessions.getCurrent();

	@Init	
	public void inicial() {
		 //String local = sesion.getAttribute("sucursal").toString();
		cform = grad_dis.cargaFormulario("0","T002");
		this.setListaAgentes(cform.getListaAgentes());
		this.setListaCantidadOD(cform.getListaCantidadOD());
		this.setListaCantidadOI(cform.getListaCantidadOI());
		this.setListaBaseOD(cform.getListaBaseOD());
		this.setListaBaseOI(cform.getListaBaseOI());
	}
	@Command
	@NotifyChange({"*"})
	public void buscarGrad(@BindingParam("arg1")  String nif) {
		
		cform = grad_dis.cargaFormulario(nif,"T002");
		this.setNombre_cliente(cform.getNombre_cliente());
		//OJO DERECHO
	
		this.setOD_esfera(cform.getOD_esfera());
		this.setOD_cilindro(cform.getOD_cilindro());
		this.setOD_eje(cform.getOD_eje());
		this.setOD_cerca(cform.getOD_cerca());
		this.setOD_adicion(cform.getOD_adicion());
		this.setOD_dnpl(cform.getOD_dnpl());
		this.setOD_dnpc(cform.getOD_dnpc());
		this.setOD_avsc(cform.getOD_avsc());
		this.setOD_avcc(cform.getOD_avcc());
		this.setOD_observaciones(cform.getOD_observaciones());

		//OJO IZQUIERDO
		this.setOI_esfera(cform.getOI_esfera());
		this.setOI_cilindro(cform.getOI_cilindro());
		this.setOI_eje(cform.getOI_eje());
		this.setOI_cerca(cform.getOI_cerca());
		this.setOI_adicion(cform.getOI_adicion());
		this.setOI_dnpl(cform.getOI_dnpl());
		this.setOI_dnpc(cform.getOI_dnpc());
		this.setOI_avsc(cform.getOI_avsc());
		this.setOI_avcc(cform.getOI_avcc());
		this.setOI_observaciones(cform.getOI_observaciones());
		
	
	}
		
	@Command
	public void cerrar(@BindingParam("arg1")  Window x) {
	    x.detach();
	}

	public boolean isDiferenteAdicion() {
		return diferenteAdicion;
	}

	public void setDiferenteAdicion(boolean diferenteAdicion) {
		this.diferenteAdicion = diferenteAdicion;
	}

	public boolean isPrismaInterno() {
		return prismaInterno;
	}

	public void setPrismaInterno(boolean prismaInterno) {
		this.prismaInterno = prismaInterno;
	}

	public boolean isPrismaExterno() {
		return prismaExterno;
	}

	public void setPrismaExterno(boolean prismaExterno) {
		this.prismaExterno = prismaExterno;
	}

	public String getSagente() {
		return sagente;
	}

	public void setSagente(String sagente) {
		this.sagente = sagente;
	}
}
