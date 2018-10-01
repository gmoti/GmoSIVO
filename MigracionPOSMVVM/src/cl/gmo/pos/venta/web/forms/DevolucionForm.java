package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.TipoMotivoDevolucionBean;



public class DevolucionForm extends GenericForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4878430502300260237L;
	
	private String accion=Constantes.STRING_BLANCO;
	private String codigo1=Constantes.STRING_BLANCO;
	private String codigo2=Constantes.STRING_BLANCO;
	private String fecha=Constantes.STRING_BLANCO;
	private String hora=Constantes.STRING_BLANCO;
	private String tipoAlbaran=Constantes.STRING_BLANCO;
	private ArrayList<TipoAlbaranBean>listaTipoAlbaranes;
	private String boleta_guia;
	private String numero_boleta_guia;
	private String codigo_cliente;
	private String nombreCliente=Constantes.STRING_BLANCO;
	private String nif=Constantes.STRING_BLANCO;
	private String dvnif=Constantes.STRING_BLANCO;
	private String kodak;
	private String idioma=Constantes.STRING_BLANCO;
	private ArrayList<IdiomaBean>listaIdiomas;
	private String cambio=Constantes.STRING_BLANCO;
	private ArrayList<AgenteBean>listaAgentes;
	private String agente=Constantes.STRING_BLANCO;
	private String modificado;
	private String modificado_text;
	private ArrayList<DivisaBean> listaDivisas;
	private String divisa=Constantes.STRING_BLANCO;
	private String montador=Constantes.STRING_BLANCO;
	private String facturado=Constantes.STRING_BLANCO;
	private String facturado_text =Constantes.STRING_BLANCO;
	private String motivo=Constantes.STRING_BLANCO;
	private ArrayList<FormaPagoBean>  listaFormasPago;
	private String formaPago=Constantes.STRING_BLANCO;
	private String confidencial=Constantes.STRING_BLANCO;
	private String dto=Constantes.STRING_BLANCO;
	private String fecha_garantia;
	private ArrayList<ConvenioBean> listaConvenios;
	private String convenio = Constantes.STRING_BLANCO;
	private String importePend =Constantes.STRING_BLANCO;
	private ArrayList<ProductosBean> lista_productos;
	private String cdg_venta;
	private String cargado=Constantes.STRING_BLANCO;
	private String existeBoleta=Constantes.STRING_BLANCO;
	private ArrayList<TipoMotivoDevolucionBean>lista_mot_devo;
	private String respuestaDevolucion;
	private ArrayList<AlbaranBean> lista_albaranes;
	private String estado_lista_albaran = "false";
	private String entrega;
	private String inicio_pagina;
	private String fecha_albaran_devolucion;
	private String numero_cab=Constantes.STRING_BLANCO;

	
	
	
	//atributos para determinar que iconos mostrar en 
	//la pantalla de albaranes.
	private String elimina_albaran;
	private String estadoCaja;
	private String mostrarIconos;
	private String tipo_albaran;
	private int sumaTotalAlabaranes;
	private int sumaTotal;
	private String respuestaPagoAlbaran;
	private String tieneArmCrisContacto;
	private String devolver_vta;
	private String usuario;
	private String isController;
	private String respuestaEliminaAlbaran;
	private String mensajeRetornoSp;
	
	private String clienteagregadoId;
	private String nifagregadoId;
	private String tipo_via_cliente;
	private String via_cliente;
	private String numero_via_cliente;
	private String provincia_cliente;
	private String validaCliente;
	private String albaranDevolcionPago;
	private String agenteSeleccionado;
	private String respuestaValidaMultiofertas;
	private String autorizacionKodak = Constantes.STRING_BLANCO;
	
	private String nombre_cli = Constantes.STRING_BLANCO;
	
	private String apellido_cli = Constantes.STRING_BLANCO;
	private String direccion_cli = Constantes.STRING_BLANCO;
	private String ndireccion_cli = Constantes.STRING_BLANCO;
	private String locali_cli= Constantes.STRING_BLANCO;
	private String comu_cli= Constantes.STRING_BLANCO;
	
	private ArrayList <ProvinciaBean> listaProvincia;
	
	

	//LMARIN NOTA DE CREDITO 20150602
	private String estado_boleta;
	
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	private String direccion;
	private String giro ;
	private String ciudad;
	private String provincia;
	

	public String getAutorizacionKodak() {
		return autorizacionKodak;
	}
	public void setAutorizacionKodak(String autorizacionKodak) {
		this.autorizacionKodak = autorizacionKodak;
	}
	
	public void cleanForm(){
		
		this.accion=Constantes.STRING_BLANCO;
		this.codigo1=Constantes.STRING_BLANCO;
		this.codigo2=Constantes.STRING_BLANCO;
		this.fecha=Constantes.STRING_BLANCO;
		this.hora=Constantes.STRING_BLANCO;
		this.tipoAlbaran=Constantes.STRING_BLANCO;
		this.listaTipoAlbaranes=null;
		this.boleta_guia=Constantes.STRING_BLANCO;
		this.numero_boleta_guia=Constantes.STRING_BLANCO;
		this.codigo_cliente=Constantes.STRING_BLANCO;
		this.nombreCliente=Constantes.STRING_BLANCO;
		this.kodak=Constantes.STRING_BLANCO;
		this.idioma=Constantes.STRING_BLANCO;
		this.listaIdiomas=null;
		this.cambio=Constantes.STRING_BLANCO;
		this.listaAgentes = null;
		this.agente=Constantes.STRING_BLANCO;
		this.modificado=Constantes.STRING_BLANCO;
		this.modificado_text=Constantes.STRING_BLANCO;
		this.listaDivisas=null;
		this.divisa=Constantes.STRING_BLANCO;
		this.montador=Constantes.STRING_BLANCO;
		this.facturado=Constantes.STRING_BLANCO;
		this.facturado_text =Constantes.STRING_BLANCO;
		this.motivo=Constantes.STRING_BLANCO;
		this.listaFormasPago=null;
		this.formaPago=Constantes.STRING_BLANCO;
		this.confidencial=Constantes.STRING_BLANCO;
		this.dto=Constantes.STRING_BLANCO;
		this.fecha_garantia=Constantes.STRING_BLANCO;
		this.listaConvenios= null;
		this.convenio = Constantes.STRING_BLANCO;
		this.importePend =Constantes.STRING_BLANCO;
		this.lista_productos=new ArrayList<ProductosBean>();
		this.cdg_venta=null;
		this.cargado="false";
		this.existeBoleta="false";
		this.respuestaDevolucion="";
		this.lista_mot_devo = new ArrayList<TipoMotivoDevolucionBean>();
		this.cdg_venta=Constantes.STRING_BLANCO;
		this.fecha=Constantes.STRING_BLANCO;
		this.agente=Constantes.STRING_BLANCO;
		this.elimina_albaran=Constantes.STRING_BLANCO;
		this.estadoCaja=Constantes.STRING_BLANCO;
		this.mostrarIconos=Constantes.STRING_BLANCO;
		this.tipo_albaran=Constantes.STRING_BLANCO;
		this.respuestaPagoAlbaran=Constantes.STRING_BLANCO;
		this.devolver_vta=Constantes.STRING_BLANCO;
		
		this.clienteagregadoId=Constantes.STRING_BLANCO;
		this.nifagregadoId=Constantes.STRING_BLANCO;
		this.via_cliente=Constantes.STRING_BLANCO;
		this.provincia_cliente=Constantes.STRING_BLANCO;
		this.tipo_via_cliente=Constantes.STRING_BLANCO;
		this.numero_via_cliente=Constantes.STRING_BLANCO;
		this.nif=Constantes.STRING_BLANCO;
		this.dvnif=Constantes.STRING_BLANCO;
		this.numero_cab=Constantes.STRING_BLANCO;
		this.respuestaEliminaAlbaran=Constantes.STRING_BLANCO;
		this.albaranDevolcionPago=Constantes.STRING_BLANCO;
		this.agenteSeleccionado=Constantes.STRING_BLANCO;
		this.mensajeRetornoSp=Constantes.STRING_BLANCO;
		this.respuestaValidaMultiofertas=Constantes.STRING_BLANCO;
	}
	
	
	
	public String getRespuestaValidaMultiofertas() {
		return respuestaValidaMultiofertas;
	}



	public void setRespuestaValidaMultiofertas(String respuestaValidaMultiofertas) {
		this.respuestaValidaMultiofertas = respuestaValidaMultiofertas;
	}



	public String getMensajeRetornoSp() {
		return mensajeRetornoSp;
	}



	public void setMensajeRetornoSp(String mensajeRetornoSp) {
		this.mensajeRetornoSp = mensajeRetornoSp;
	}



	public String getAgenteSeleccionado() {
		return agenteSeleccionado;
	}



	public void setAgenteSeleccionado(String agenteSeleccionado) {
		this.agenteSeleccionado = agenteSeleccionado;
	}



	public String getAlbaranDevolcionPago() {
		return albaranDevolcionPago;
	}



	public void setAlbaranDevolcionPago(String albaranDevolcionPago) {
		this.albaranDevolcionPago = albaranDevolcionPago;
	}



	public String getRespuestaEliminaAlbaran() {
		return respuestaEliminaAlbaran;
	}



	public void setRespuestaEliminaAlbaran(String respuestaEliminaAlbaran) {
		this.respuestaEliminaAlbaran = respuestaEliminaAlbaran;
	}



	public String getNumero_cab() {
		return numero_cab;
	}



	public void setNumero_cab(String numero_cab) {
		this.numero_cab = numero_cab;
	}



	public String getValidaCliente() {
		return validaCliente;
	}



	public void setValidaCliente(String validaCliente) {
		this.validaCliente = validaCliente;
	}



	public String getTipo_via_cliente() {
		return tipo_via_cliente;
	}



	public void setTipo_via_cliente(String tipo_via_cliente) {
		this.tipo_via_cliente = tipo_via_cliente;
	}



	public String getVia_cliente() {
		return via_cliente;
	}



	public void setVia_cliente(String via_cliente) {
		this.via_cliente = via_cliente;
	}



	public String getNumero_via_cliente() {
		return numero_via_cliente;
	}



	public void setNumero_via_cliente(String numero_via_cliente) {
		this.numero_via_cliente = numero_via_cliente;
	}



	public String getProvincia_cliente() {
		return provincia_cliente;
	}



	public void setProvincia_cliente(String provincia_cliente) {
		this.provincia_cliente = provincia_cliente;
	}



	public String getClienteagregadoId() {
		return clienteagregadoId;
	}

	public void setClienteagregadoId(String clienteagregadoId) {
		this.clienteagregadoId = clienteagregadoId;
	}

	public String getNifagregadoId() {
		return nifagregadoId;
	}

	public void setNifagregadoId(String nifagregadoId) {
		this.nifagregadoId = nifagregadoId;
	}

	public String getFecha_albaran_devolucion() {
		return fecha_albaran_devolucion;
	}

	public void setFecha_albaran_devolucion(String fecha_albaran_devolucion) {
		this.fecha_albaran_devolucion = fecha_albaran_devolucion;
	}

	public String getIsController() {
		return isController;
	}

	public void setIsController(String isController) {
		this.isController = isController;
	}

	public String getInicio_pagina() {
		return inicio_pagina;
	}

	public void setInicio_pagina(String inicio_pagina) {
		this.inicio_pagina = inicio_pagina;
	}

	public String getEntrega() {
		return entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDevolver_vta() {
		return devolver_vta;
	}

	public void setDevolver_vta(String devolver_vta) {
		this.devolver_vta = devolver_vta;
	}

	public String getTieneArmCrisContacto() {
		return tieneArmCrisContacto;
	}

	public void setTieneArmCrisContacto(String tieneArmCrisContacto) {
		this.tieneArmCrisContacto = tieneArmCrisContacto;
	}

	public String getRespuestaPagoAlbaran() {
		return respuestaPagoAlbaran;
	}

	public void setRespuestaPagoAlbaran(String respuestaPagoAlbaran) {
		this.respuestaPagoAlbaran = respuestaPagoAlbaran;
	}

	public int getSumaTotal() {
		return sumaTotal;
	}

	public void setSumaTotal(int sumaTotal) {
		this.sumaTotal = sumaTotal;
	}

	public int getSumaTotalAlabaranes() {
		return sumaTotalAlabaranes;
	}

	public void setSumaTotalAlabaranes(int sumaTotalAlabaranes) {
		this.sumaTotalAlabaranes = sumaTotalAlabaranes;
	}

	public String getTipo_albaran() {
		return tipo_albaran;
	}

	public void setTipo_albaran(String tipo_albaran) {
		this.tipo_albaran = tipo_albaran;
	}

	public String getMostrarIconos() {
		return mostrarIconos;
	}

	public void setMostrarIconos(String mostrarIconos) {
		this.mostrarIconos = mostrarIconos;
	}

	public String getEstadoCaja() {
		return estadoCaja;
	}

	public void setEstadoCaja(String estadoCaja) {
		this.estadoCaja = estadoCaja;
	}

	public String getElimina_albaran() {
		return elimina_albaran;
	}

	public void setElimina_albaran(String elimina_albaran) {
		this.elimina_albaran = elimina_albaran;
	}

	public String getEstado_lista_albaran() {
		return estado_lista_albaran;
	}

	public void setEstado_lista_albaran(String estado_lista_albaran) {
		this.estado_lista_albaran = estado_lista_albaran;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDvnif() {
		return dvnif;
	}

	public void setDvnif(String dvnif) {
		this.dvnif = dvnif;
	}

	public String getRespuestaDevolucion() {
		return respuestaDevolucion;
	}

	public void setRespuestaDevolucion(String respuestaDevolucion) {
		this.respuestaDevolucion = respuestaDevolucion;
	}

	public ArrayList<TipoMotivoDevolucionBean> getLista_mot_devo() {
		return lista_mot_devo;
	}

	public void setLista_mot_devo(ArrayList<TipoMotivoDevolucionBean> lista_mot_devo) {
		this.lista_mot_devo = lista_mot_devo;
	}

	public String getExisteBoleta() {
		return existeBoleta;
	}

	public void setExisteBoleta(String existeBoleta) {
		this.existeBoleta = existeBoleta;
	}

	public String getCdg_venta() {
		return cdg_venta;
	}
	public void setCdg_venta(String cdg_venta) {
		this.cdg_venta = cdg_venta;
	}
	public ArrayList<ProductosBean> getLista_productos() {
		return lista_productos;
	}
	public void setLista_productos(ArrayList<ProductosBean> lista_productos) {
		this.lista_productos = lista_productos;
	}
	public String getImportePend() {
		return importePend;
	}
	public void setImportePend(String importePend) {
		this.importePend = importePend;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getCodigo1() {
		return codigo1;
	}
	public void setCodigo1(String codigo1) {
		this.codigo1 = codigo1;
	}
	public String getCodigo2() {
		return codigo2;
	}
	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getTipoAlbaran() {
		return tipoAlbaran;
	}
	public void setTipoAlbaran(String tipoAlbaran) {
		this.tipoAlbaran = tipoAlbaran;
	}
	
	public ArrayList<TipoAlbaranBean> getListaTipoAlbaranes() {
		return listaTipoAlbaranes;
	}

	public void setListaTipoAlbaranes(ArrayList<TipoAlbaranBean> listaTipoAlbaranes) {
		this.listaTipoAlbaranes = listaTipoAlbaranes;
	}

	public ArrayList<AlbaranBean> getLista_albaranes() {
		return lista_albaranes;
	}

	public void setLista_albaranes(ArrayList<AlbaranBean> lista_albaranes) {
		this.lista_albaranes = lista_albaranes;
	}

	public String getBoleta_guia() {
		return boleta_guia;
	}
	public void setBoleta_guia(String boleta_guia) {
		this.boleta_guia = boleta_guia;
	}
	public String getNumero_boleta_guia() {
		return numero_boleta_guia;
	}
	public void setNumero_boleta_guia(String numero_boleta_guia) {
		this.numero_boleta_guia = numero_boleta_guia;
	}
	public String getCodigo_cliente() {
		return codigo_cliente;
	}
	public void setCodigo_cliente(String codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getKodak() {
		return kodak;
	}
	public void setKodak(String kodak) {
		this.kodak = kodak;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public ArrayList<IdiomaBean> getListaIdiomas() {
		return listaIdiomas;
	}
	public void setListaIdiomas(ArrayList<IdiomaBean> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}
	public String getCambio() {
		return cambio;
	}
	public void setCambio(String cambio) {
		this.cambio = cambio;
	}
	public ArrayList<AgenteBean> getListaAgentes() {
		return listaAgentes;
	}
	public void setListaAgentes(ArrayList<AgenteBean> listaAgentes) {
		this.listaAgentes = listaAgentes;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getModificado() {
		return modificado;
	}
	public void setModificado(String modificado) {
		this.modificado = modificado;
	}
	public String getModificado_text() {
		return modificado_text;
	}
	public void setModificado_text(String modificado_text) {
		this.modificado_text = modificado_text;
	}
	public ArrayList<DivisaBean> getListaDivisas() {
		return listaDivisas;
	}
	public void setListaDivisas(ArrayList<DivisaBean> listaDivisas) {
		this.listaDivisas = listaDivisas;
	}
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	public String getMontador() {
		return montador;
	}
	public void setMontador(String montador) {
		this.montador = montador;
	}
	public String getFacturado() {
		return facturado;
	}
	public void setFacturado(String facturado) {
		this.facturado = facturado;
	}
	public String getFacturado_text() {
		return facturado_text;
	}
	public void setFacturado_text(String facturado_text) {
		this.facturado_text = facturado_text;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public ArrayList<FormaPagoBean> getListaFormasPago() {
		return listaFormasPago;
	}
	public void setListaFormasPago(ArrayList<FormaPagoBean> listaFormasPago) {
		this.listaFormasPago = listaFormasPago;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public String getConfidencial() {
		return confidencial;
	}
	public void setConfidencial(String confidencial) {
		this.confidencial = confidencial;
	}	
	public String getDto() {
		return dto;
	}
	public void setDto(String dto) {
		this.dto = dto;
	}
	public String getFecha_garantia() {
		return fecha_garantia;
	}
	public void setFecha_garantia(String fecha_garantia) {
		this.fecha_garantia = fecha_garantia;
	}
	public ArrayList<ConvenioBean> getListaConvenios() {
		return listaConvenios;
	}
	public void setListaConvenios(ArrayList<ConvenioBean> listaConvenios) {
		this.listaConvenios = listaConvenios;
	}
	public String getConvenio() {
		return convenio;
	}
	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getCargado() {
		return cargado;
	}

	public void setCargado(String cargado) {
		this.cargado = cargado;
	}
	
	public String getEstado_boleta() {
		return estado_boleta;
	}

	public void setEstado_boleta(String estado_boleta) {
		this.estado_boleta = estado_boleta;
	}
	
	public String getNombre_cli() {
		return nombre_cli;
	}
	public void setNombre_cli(String nombre_cli) {
		this.nombre_cli = nombre_cli;
	}
	public String getApellido_cli() {
		return apellido_cli;
	}
	public void setApellido_cli(String apellido_cli) {
		this.apellido_cli = apellido_cli;
	}
	public String getDireccion_cli() {
		return direccion_cli;
	}
	public void setDireccion_cli(String direccion_cli) {
		this.direccion_cli = direccion_cli;
	}
	public String getNdireccion_cli() {
		return ndireccion_cli;
	}
	public void setNdireccion_cli(String ndireccion_cli) {
		this.ndireccion_cli = ndireccion_cli;
	}
	public String getLocali_cli() {
		return locali_cli;
	}
	public void setLocali_cli(String locali_cli) {
		this.locali_cli = locali_cli;
	}
	public String getComu_cli() {
		return comu_cli;
	}
	public void setComu_cli(String comu_cli) {
		this.comu_cli = comu_cli;
	}

	public ArrayList<ProvinciaBean> getListaProvincia() {
		return listaProvincia;
	}
	public void setListaProvincia(ArrayList<ProvinciaBean> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}
	
}
