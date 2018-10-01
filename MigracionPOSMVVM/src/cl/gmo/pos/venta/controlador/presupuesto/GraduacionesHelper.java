package cl.gmo.pos.venta.controlador.presupuesto;

import java.util.ArrayList;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.facade.BusquedaLiberacionesFacade;
import cl.gmo.pos.venta.web.facade.PosGraduacionesFacade;
import cl.gmo.pos.venta.web.facade.PosMedicoFacade;
import cl.gmo.pos.venta.web.forms.ContactologiaForm;
import cl.gmo.pos.venta.web.forms.GraduacionesForm;

public class GraduacionesHelper extends Utils{
	
		
	public void realiza_Trasposicion (GraduacionesBean graduacion)
	{
		double esfera = 0.0;
		double cilindro = 0.0;
		int eje = Constantes.INT_CERO;
		
		try{
				//OJO DERECHO
				
				esfera = graduacion.getOD_esfera();
				cilindro = graduacion.getOD_cilindro();
				eje = graduacion.getOD_eje();
				graduacion.setTipo(Constantes.STRING_L);
				
				if (cilindro < Constantes.INT_CERO) {
					
					graduacion.setOD_cilindro_traspuesto(Math.abs(cilindro));
					graduacion.setOD_esfera_traspuesto(esfera + cilindro);
					
					if (eje >= 0 && eje <=90) {
						graduacion.setOD_eje_traspuesto(eje + 90);
					}
					else if(eje >= 91 && eje <=180)
					{
						graduacion.setOD_eje_traspuesto(eje - 90);
					}
				}
				else
				{
					graduacion.setOD_cilindro_traspuesto(cilindro);
					graduacion.setOD_esfera_traspuesto(esfera);
					graduacion.setOD_eje_traspuesto(eje);
				}
				
				
				
				//OJO IZQUIERDO
				
				esfera = graduacion.getOI_esfera();
				cilindro = graduacion.getOI_cilindro();
				eje = graduacion.getOI_eje();
				
				if (cilindro < 0) {
					
					graduacion.setOI_cilindro_traspuesto(Math.abs(cilindro));
					graduacion.setOI_esfera_traspuesto(esfera + cilindro);
					
					if (eje >= 0 && eje <=90) {
						graduacion.setOI_eje_traspuesto(eje + 90);
					}
					else if(eje >= 91 && eje <=180)
					{
						graduacion.setOI_eje_traspuesto(eje - 90);
					}
				}
				else
				{
					graduacion.setOI_cilindro_traspuesto(cilindro);
					graduacion.setOI_esfera_traspuesto(esfera);
					graduacion.setOI_eje_traspuesto(eje);
				}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void realiza_Trasposicion_cerca(GraduacionesBean graduacion) {
		
		double esfera = 0.0;
		double cilindro = 0.0;
		int eje = Constantes.INT_CERO;
		
		//OJO DERECHO
		
		esfera = graduacion.getOD_esfera_cerca();
		cilindro = graduacion.getOD_cilindro();
		eje = graduacion.getOD_eje();
		graduacion.setTipo(Constantes.STRING_C);
		
		if (cilindro < 0) {
			
			graduacion.setOD_cilindro_traspuesto(Math.abs(cilindro));
			graduacion.setOD_esfera_traspuesto(esfera + cilindro);
			
			if (eje >= 0 && eje <=90) {
				graduacion.setOD_eje_traspuesto(eje + 90);
			}
			else if(eje >= 91 && eje <=180)
			{
				graduacion.setOD_eje_traspuesto(eje - 90);
			}
		}
		else
		{
			graduacion.setOD_cilindro_traspuesto(cilindro);
			graduacion.setOD_esfera_traspuesto(esfera);
			graduacion.setOD_eje_traspuesto(eje);
		}
		
		
		
		//OJO IZQUIERDO
		
		esfera = graduacion.getOI_esfera_cerca();
		cilindro = graduacion.getOI_cilindro();
		eje = graduacion.getOI_eje();
		
		if (cilindro < 0) {
			
			graduacion.setOI_cilindro_traspuesto(Math.abs(cilindro));
			graduacion.setOI_esfera_traspuesto(esfera + cilindro);
			
			if (eje >= 0 && eje <=90) {
				graduacion.setOI_eje_traspuesto(eje + 90);
			}
			else if(eje >= 91 && eje <=180)
			{
				graduacion.setOI_eje_traspuesto(eje - 90);
			}
		}
		else
		{
			graduacion.setOI_cilindro_traspuesto(cilindro);
			graduacion.setOI_esfera_traspuesto(esfera);
			graduacion.setOI_eje_traspuesto(eje);
		}
		
		
	}

	public ArrayList<GraduacionesBean> traeGraduacionesCliente(String cliente) throws Exception{
		
		ArrayList<GraduacionesBean> lista_graduaciones = new ArrayList<GraduacionesBean>();
		lista_graduaciones = PosGraduacionesFacade.traeGraduacionesClientes(cliente);
		
		return lista_graduaciones;
	}

	
	public GraduacionesForm traeGraduacionPedido(GraduacionesForm formulario) {
		
		GraduacionesBean graduacion = new GraduacionesBean();
		
		try{
			
			graduacion = BusquedaLiberacionesFacade.traeGraduacionPedido(String.valueOf(formulario.getCliente()), formulario.getFecha_graduacion(), formulario.getNumero_graduacion());
			int numerograd = 0;
			
			if(0 != formulario.getNumero_graduacion() && !("".equals(formulario.getNumero_graduacion()))){
				numerograd = (int) formulario.getNumero_graduacion();
			}else{
				numerograd =-1;
			}			
			 
			String fech = formulario.getFecha_graduacion() ;
			String cliente = String.valueOf(formulario.getCliente());
			String pagina = "OPTOMETRIA";
			
			//se utiliza para saber si la receta esta asociada a un encargo o presupuesto, primeramente se debia bloquear por requerimientos de GMO
			//sin embargo GMO decidio no bloquar la receta. cambio realizado el 04-03-2013
			//boolean existeContactologia = PosGraduacionesFacade.existeContactologiaPresEncargo(numerograd, fech, cliente, pagina);
			boolean existeContactologia = false;
			if(existeContactologia){
				formulario.setExiste_graduacion("true");
			}else{
				formulario.setExiste_graduacion("false");
			}
			formulario.setCod_doctor(graduacion.getCod_doctor());
			formulario.setTipo(graduacion.getTipo());
			formulario.setFechaEmision(graduacion.getFecha_emision());
			formulario.setFechaProxRevision(graduacion.getFecha_prox_revision());
			formulario.setAgente(graduacion.getAgente());
			formulario.setDoctor(graduacion.getDoctor());
			
			if(null !=graduacion.getOD_esfera()){
				formulario.setOD_esfera(graduacion.getOD_esfera().toString());
			}else{
				formulario.setOD_esfera(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_cilindro()){
				formulario.setOD_cilindro(graduacion.getOD_cilindro().toString());
			}else{
				formulario.setOD_cilindro(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_eje()){
				formulario.setOD_eje(graduacion.getOD_eje().toString());
			}else{
				formulario.setOD_eje(Constantes.STRING_BLANCO);			
			}
			
			if(null != graduacion.getOD_esfera_cerca()){
				formulario.setOD_cerca(graduacion.getOD_esfera_cerca().toString());
			}else{
				formulario.setOD_cerca(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_adicion()){
				formulario.setOD_adicion(graduacion.getOD_adicion().toString());
			}else{
				formulario.setOD_adicion(Constantes.STRING_BLANCO);		
			}
			
			if(null != graduacion.getOD_n()){
				formulario.setOD_dnpl(graduacion.getOD_n().toString());
			}else{
				formulario.setOD_dnpl(Constantes.STRING_BLANCO);			
			}
			
			if(null != graduacion.getOD_p()){
				formulario.setOD_dnpc(graduacion.getOD_p().toString());
			}else{
				formulario.setOD_dnpc(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_avsc()){
				formulario.setOD_avsc(graduacion.getOD_avsc().toString());
			}else{
				formulario.setOD_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOD_avcc()){
				formulario.setOD_avcc(graduacion.getOD_avcc().toString());
			}else{
				formulario.setOD_avcc(Constantes.STRING_BLANCO);
			}			
			formulario.setOD_cantidad(graduacion.getOD_cantidad());
			formulario.setOD_base(graduacion.getOD_base());
			
			if(null != graduacion.getOD_altura()){
				formulario.setOD_altura(graduacion.getOD_altura());
			}else{
				formulario.setOD_altura(Constantes.STRING_BLANCO);
			}
			
			
			formulario.setOD_observaciones(graduacion.getOD_obser());
			
			if(null != graduacion.getOI_esfera()){
				formulario.setOI_esfera(graduacion.getOI_esfera().toString());
			}else{
				formulario.setOI_esfera(Constantes.STRING_BLANCO);
			}
						
			if(null != graduacion.getOI_cilindro()){
				formulario.setOI_cilindro(graduacion.getOI_cilindro().toString());
			}else{
				formulario.setOI_cilindro(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_eje()){
				formulario.setOI_eje(graduacion.getOI_eje().toString());
			}else{
				formulario.setOI_eje(Constantes.STRING_BLANCO);
			}
			
			if(null !=graduacion.getOI_esfera_cerca()){
				formulario.setOI_cerca(graduacion.getOI_esfera_cerca().toString());
			}else{
				formulario.setOI_cerca(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_adicion()){
				formulario.setOI_adicion(graduacion.getOI_adicion().toString());
			}else{
				formulario.setOI_adicion(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_n()){
				formulario.setOI_dnpl(graduacion.getOI_n().toString());
			}else{
				formulario.setOI_dnpl(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_p()){
				formulario.setOI_dnpc(graduacion.getOI_p().toString());
			}else{
				formulario.setOI_dnpc(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_avsc()){
				formulario.setOI_avsc(graduacion.getOI_avsc().toString());
			}else{
				formulario.setOI_avsc(Constantes.STRING_BLANCO);
			}
			
			if(null != graduacion.getOI_avcc()){
				formulario.setOI_avcc(graduacion.getOI_avcc().toString());
			}else{
				formulario.setOI_avcc(Constantes.STRING_BLANCO);			
			}
			
			formulario.setDiferenteAdd(graduacion.isDiferente_add());
			
			
			formulario.setOI_cantidad(graduacion.getOI_cantidad());
			
			
			formulario.setOI_base(graduacion.getOI_base());
			
			if(null != graduacion.getOI_altura()){
				formulario.setOI_altura(graduacion.getOI_altura());
			}else{
				formulario.setOI_altura(Constantes.STRING_BLANCO);
			}
			formulario.setOI_observaciones(graduacion.getOI_obser());
			
			OftalmologoBean medico = PosMedicoFacade.traeMedico(formulario.getCod_doctor());
			
			formulario.setNifdoctor(medico.getNif());
			formulario.setDvnifdoctor(medico.getLnif());
			formulario.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return formulario;
	}

	public GraduacionesForm traeUltimaGraduacionListaGraduacion(GraduacionesForm formulario) {
		
		GraduacionesBean graduacion = null;
		
		try{
			
			if(null != formulario.getListaGraduaciones() && formulario.getListaGraduaciones().size()>0){			
				
				graduacion  = formulario.getListaGraduaciones().get(0);		
				String fecha_graduacion = graduacion.getFecha();
				int numero = graduacion .getNumero();
				graduacion = new GraduacionesBean();
				graduacion = BusquedaLiberacionesFacade.traeGraduacionPedido(String.valueOf(formulario.getCliente()), fecha_graduacion, numero);
				
				formulario.setNumero_graduacion(graduacion.getNumero());
				formulario.setFecha_graduacion(graduacion.getFecha());
				int numerograd = 0;
				
				if(0 != formulario.getNumero_graduacion() && !("".equals(formulario.getNumero_graduacion()))){
					numerograd = (int) formulario.getNumero_graduacion();
				}else{
					numerograd =-1;
				}			
				 
				String fech = formulario.getFecha_graduacion() ;
				String cliente = String.valueOf(formulario.getCliente());
				String pagina = "OPTOMETRIA";
				
				//se utiliza para saber si la receta esta asociada a un encargo o presupuesto, primeramente se debia bloquear por requerimientos de GMO
				//sin embargo GMO decidio no bloquar la receta. cambio realizado el 04-03-2013
				//boolean existeContactologia = PosGraduacionesFacade.existeContactologiaPresEncargo(numerograd, fech, cliente, pagina);
				boolean existeContactologia = false;
				if(existeContactologia){
					formulario.setExiste_graduacion("true");
				}else{
					formulario.setExiste_graduacion("false");
				}
				formulario.setCod_doctor(graduacion.getCod_doctor());
				formulario.setTipo(graduacion.getTipo());
				formulario.setFechaEmision(graduacion.getFecha_emision());
				formulario.setFechaProxRevision(graduacion.getFecha_prox_revision());
				formulario.setAgente(graduacion.getAgente());
				formulario.setDoctor(graduacion.getDoctor());
				formulario.setDiferenteAdd(graduacion.isDiferente_add());
				
				if(null !=graduacion.getOD_esfera()){
					formulario.setOD_esfera(graduacion.getOD_esfera().toString());
				}else{
					formulario.setOD_esfera(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOD_cilindro()){
					formulario.setOD_cilindro(graduacion.getOD_cilindro().toString());
				}else{
					formulario.setOD_cilindro(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOD_eje()){
					formulario.setOD_eje(graduacion.getOD_eje().toString());
				}else{
					formulario.setOD_eje(Constantes.STRING_BLANCO);			
				}
				
				if(null != graduacion.getOD_esfera_cerca()){
					formulario.setOD_cerca(graduacion.getOD_esfera_cerca().toString());
				}else{
					formulario.setOD_cerca(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOD_adicion()){
					formulario.setOD_adicion(graduacion.getOD_adicion().toString());
				}else{
					formulario.setOD_adicion(Constantes.STRING_BLANCO);		
				}
				
				if(null != graduacion.getOD_n()){
					formulario.setOD_dnpl(graduacion.getOD_n().toString());
				}else{
					formulario.setOD_dnpl(Constantes.STRING_BLANCO);			
				}
				
				if(null != graduacion.getOD_p()){
					formulario.setOD_dnpc(graduacion.getOD_p().toString());
				}else{
					formulario.setOD_dnpc(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOD_avsc()){
					formulario.setOD_avsc(graduacion.getOD_avsc().toString());
				}else{
					formulario.setOD_avsc(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOD_avcc()){
					formulario.setOD_avcc(graduacion.getOD_avcc().toString());
				}else{
					formulario.setOD_avcc(Constantes.STRING_BLANCO);
				}			
				formulario.setOD_cantidad(graduacion.getOD_cantidad());
				formulario.setOD_base(graduacion.getOD_base());
				
				if(null != graduacion.getOD_altura()){
					formulario.setOD_altura(graduacion.getOD_altura());
				}else{
					formulario.setOD_altura(Constantes.STRING_BLANCO);
				}
				
				
				formulario.setOD_observaciones(graduacion.getOD_obser());
				
				if(null != graduacion.getOI_esfera()){
					formulario.setOI_esfera(graduacion.getOI_esfera().toString());
				}else{
					formulario.setOI_esfera(Constantes.STRING_BLANCO);
				}
							
				if(null != graduacion.getOI_cilindro()){
					formulario.setOI_cilindro(graduacion.getOI_cilindro().toString());
				}else{
					formulario.setOI_cilindro(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOI_eje()){
					formulario.setOI_eje(graduacion.getOI_eje().toString());
				}else{
					formulario.setOI_eje(Constantes.STRING_BLANCO);
				}
				
				if(null !=graduacion.getOI_esfera_cerca()){
					formulario.setOI_cerca(graduacion.getOI_esfera_cerca().toString());
				}else{
					formulario.setOI_cerca(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOI_adicion()){
					formulario.setOI_adicion(graduacion.getOI_adicion().toString());
				}else{
					formulario.setOI_adicion(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOI_n()){
					formulario.setOI_dnpl(graduacion.getOI_n().toString());
				}else{
					formulario.setOI_dnpl(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOI_p()){
					formulario.setOI_dnpc(graduacion.getOI_p().toString());
				}else{
					formulario.setOI_dnpc(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOI_avsc()){
					formulario.setOI_avsc(graduacion.getOI_avsc().toString());
				}else{
					formulario.setOI_avsc(Constantes.STRING_BLANCO);
				}
				
				if(null != graduacion.getOI_avcc()){
					formulario.setOI_avcc(graduacion.getOI_avcc().toString());
				}else{
					formulario.setOI_avcc(Constantes.STRING_BLANCO);			
				}
				
				
				
				
				formulario.setOI_cantidad(graduacion.getOI_cantidad());
				
				
				formulario.setOI_base(graduacion.getOI_base());
				
				if(null != graduacion.getOI_altura()){
					formulario.setOI_altura(graduacion.getOI_altura());
				}else{
					formulario.setOI_altura(Constantes.STRING_BLANCO);
				}
				formulario.setOI_observaciones(graduacion.getOI_obser());
				
				OftalmologoBean medico = PosMedicoFacade.traeMedico(formulario.getCod_doctor());
				
				formulario.setNifdoctor(medico.getNif());
				formulario.setDvnifdoctor(medico.getLnif());
				formulario.setNombre_doctor(medico.getNombre() + " " + medico.getApelli());
			
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return formulario;
	}

	
	public boolean ingresaGraduacion(GraduacionesForm formulario){
		
		GraduacionesBean graduacion = new GraduacionesBean();
		boolean respuesta = false;
		try{
			
			int numero = PosGraduacionesFacade.traeNumeroGraduacion(String.valueOf(formulario.getCliente()), traeFechaHoyFormateadaString());			
			
			graduacion.setCliente(formulario.getCliente());
			graduacion.setFecha(traeFechaHoyFormateadaString());			
			graduacion.setNumero(-1);
			graduacion.setDoctor(formulario.getCod_doctor());	
			graduacion.setFecha_prox_revision(formulario.getFechaProxRevision());
			graduacion.setTipo(formulario.getTipo());
			
			if(null != formulario.getOD_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_esfera()))){
				graduacion.setOD_esfera(Double.valueOf(formulario.getOD_esfera()));
			}else{
				graduacion.setOD_esfera(null);
			}
				
			if(null != formulario.getOD_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cilindro()))){
				graduacion.setOD_cilindro(Double.valueOf(formulario.getOD_cilindro()));
			}else{
				graduacion.setOD_cilindro(null);
			}	
			
			graduacion.setAgente(formulario.getAgente());
			
			if(null != formulario.getOD_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_eje()))){
				graduacion.setOD_eje(Integer.valueOf(formulario.getOD_eje()));
			}else{
				graduacion.setOD_eje(null);
			}
			
			if(null != formulario.getOD_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_adicion()))){
				graduacion.setOD_adicion(Double.valueOf(formulario.getOD_adicion()));
			}else{
				graduacion.setOD_adicion(null);
			}
			
			if(null != formulario.getOD_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cerca()))){
				graduacion.setOD_esfera_cerca(Double.valueOf(formulario.getOD_cerca()));
			}else{
				graduacion.setOD_esfera_cerca(null);
			}
			
			if(null !=formulario.getOD_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpl())) ){
				graduacion.setOD_n(Double.valueOf(formulario.getOD_dnpl()));
			}else{
				graduacion.setOD_n(null);
			}
			
			if(null != formulario.getOD_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpc()))){
				graduacion.setOD_p(Double.valueOf(formulario.getOD_dnpc()));
			}else{
				graduacion.setOD_p(null);
			}
			
			
			
			if(null != formulario.getOD_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_observaciones()))){
				graduacion.setOD_obser(formulario.getOD_observaciones().toUpperCase());
			}else{
				graduacion.setOD_obser(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOD_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avsc()))){
				graduacion.setOD_avsc(Double.valueOf(formulario.getOD_avsc()));
			}else{
				graduacion.setOD_avsc(null);
			}
			
			if(null != formulario.getOD_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcc()))){
				graduacion.setOD_avcc(Double.valueOf(formulario.getOD_avcc()));
			}else{
				graduacion.setOD_avcc(null);
			}			
			
			graduacion.setOD_cantidad(formulario.getOD_cantidad());
			graduacion.setOD_base(formulario.getOD_base());
			graduacion.setOD_altura(formulario.getOD_altura());
			
			if(null != formulario.getOI_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_esfera()))){
				graduacion.setOI_esfera(Double.valueOf(formulario.getOI_esfera()));
			}else{
				graduacion.setOI_esfera(null);
			}
			
			
			if(null != formulario.getOI_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cilindro()))){
				graduacion.setOI_cilindro(Double.valueOf(formulario.getOI_cilindro()));
			}else{
				graduacion.setOI_cilindro(null);
			}
			
			if(null != formulario.getOI_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_eje()))){
				graduacion.setOI_eje(Integer.valueOf(formulario.getOI_eje()));
			}else{
				graduacion.setOI_eje(null);
			}
			
			if(null != formulario.getOI_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_adicion()))){
				graduacion.setOI_adicion(Double.valueOf(formulario.getOI_adicion()));
			}else{
				graduacion.setOI_adicion(null);
			}	
			
			if(null !=formulario.getOI_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cerca()))){
				graduacion.setOI_esfera_cerca(Double.valueOf(formulario.getOI_cerca()));
			}else{
				graduacion.setOI_esfera_cerca(null);
			}
			
			if(null !=formulario.getOI_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpl())) ){
				graduacion.setOI_n(Double.valueOf(formulario.getOI_dnpl()));
			}else{
				graduacion.setOI_n(null);
			}
			
			if(null != formulario.getOI_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpc()))){
				graduacion.setOI_p(Double.valueOf(formulario.getOI_dnpc()));
			}else{
				graduacion.setOI_p(null);
			}
			
			
			
			if(null != formulario.getOI_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_observaciones()))){
				graduacion.setOI_obser(formulario.getOI_observaciones().toUpperCase());
			}else{
				graduacion.setOI_obser(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOI_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avsc()))){
				graduacion.setOI_avsc(Double.valueOf(formulario.getOI_avsc()));
			}else{
				graduacion.setOI_avsc(null);
			}
			
			if(null != formulario.getOI_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcc()))){
				graduacion.setOI_avcc(Double.valueOf(formulario.getOI_avcc()));
			}else{
				graduacion.setOI_avcc(null);
			}
			
			
			
			
			graduacion.setOI_cantidad(formulario.getOI_cantidad());
			graduacion.setOI_base(formulario.getOI_base());
			graduacion.setOI_altura(formulario.getOI_altura());
			
			graduacion.setFecha_emision(formulario.getFechaEmision());
			graduacion.setDiferente_add(formulario.isDiferenteAdd());
			
			respuesta = PosGraduacionesFacade.ingresaGraduacion(graduacion);		
			
		}catch(Exception ex){
			respuesta = false;
			ex.printStackTrace();
		}
		
		return respuesta;
	}

	public boolean ingresaContactologia(ContactologiaForm formulario){
		boolean respuesta  = false;
		ContactologiaBean bean = new ContactologiaBean();
		Utils util = new Utils();
		
		try{
			
				if(null != formulario.getCliente() && !(Constantes.STRING_BLANCO.equals(formulario.getCliente()))){				
					bean.setCliente(Integer.parseInt(formulario.getCliente()));
				}	
				
				bean.setFecha(util.traeFechaHoyFormateadaString());
				
				bean.setNumero(-1);
				formulario.setTipo(Constantes.STRING_D);
				bean.setTipo(formulario.getTipo());
				bean.setClidefini_doctor(formulario.getDoctor());
				
				if(null != formulario.getO_radio1()&& !(Constantes.STRING_BLANCO.equals(formulario.getO_radio1()))){
					bean.setOdradio1(Double.parseDouble(formulario.getO_radio1()));
				}
				
				if(null != formulario.getO_radio2() && !(Constantes.STRING_BLANCO.equals(formulario.getO_radio2()))){
					bean.setOdradio2(Double.parseDouble(formulario.getO_radio2()));
				}
				
				if(null != formulario.getO_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getO_esfera()))){
					bean.setOdesfera(Double.parseDouble(formulario.getO_esfera()));
				}
				
				if(null != formulario.getO_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getO_cilindro()))){
					bean.setOdcilindro(Double.parseDouble(formulario.getO_cilindro()));
				}
				
				if(null != formulario.getO_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getO_eje()))){
					bean.setOdeje(Integer.parseInt(formulario.getO_eje()));
				}
				
				if(null != formulario.getO_diamt() && !(Constantes.STRING_BLANCO.equals(formulario.getO_diamt()))){
					bean.setOddiamt(Double.parseDouble(formulario.getO_diamt()));
				}
				
				if(null != formulario.getO_diaz() && !(Constantes.STRING_BLANCO.equals(formulario.getO_diaz()))){
					bean.setOddiamz0(Double.parseDouble(formulario.getO_diaz()));
				}
				
				bean.setOdbandas(formulario.getO_bandas());
				bean.setOdesp(formulario.getO_esp());
				bean.setOdtipo(formulario.getO_radio3());
				bean.setOdmaterial(formulario.getO_diamp());
				bean.setOdhidr(formulario.getO_colo());
				bean.setOdadic(formulario.getO_adic());
				
				//************************OJO IZQUIERDO*******************************
				
				if(null != formulario.getI_radio1() && !(Constantes.STRING_BLANCO.equals(formulario.getI_radio1()))){
					bean.setOiradio1(Double.parseDouble(formulario.getI_radio1()));
				}
				
				if(null != formulario.getI_radio2() && !(Constantes.STRING_BLANCO.equals(formulario.getI_radio2()))){
					bean.setOiradio2(Double.parseDouble(formulario.getI_radio2()));
				}
				
				if(null != formulario.getI_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getI_esfera()))){
					bean.setOiesfera(Double.parseDouble(formulario.getI_esfera()));
				}
				
				if(null != formulario.getI_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getI_cilindro()))){
					bean.setOicilindro(Double.parseDouble(formulario.getI_cilindro()));
				}
				
				if(null != formulario.getI_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getI_eje()))){
					bean.setOieje(Integer.parseInt(formulario.getI_eje()));
				}
				
				if(null != formulario.getI_diamt() && !(Constantes.STRING_BLANCO.equals(formulario.getI_diamt()))){
					bean.setOidiamt(Double.parseDouble(formulario.getI_diamt()));
				}
				
				if(null != formulario.getI_diaz() && !(Constantes.STRING_BLANCO.equals(formulario.getI_diaz()))){
					bean.setOidiamz0(Double.parseDouble(formulario.getI_diaz()));
				}
				
				bean.setOibandas(formulario.getI_bandas());
				bean.setOiesp(formulario.getI_esp());
				bean.setOitipo(formulario.getI_radio3());
				bean.setOimaterial(formulario.getO_diamp());
				bean.setOihidr(formulario.getO_colo());
				bean.setOiadic(formulario.getI_adic());
				
				bean.setOtros(formulario.getOtros());
				bean.setLaboratorio(formulario.getLaboratorio());
				bean.setCalculos(formulario.getCalculo_opt());
				
				
				if(null != formulario.getFecha_pedido() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_pedido()))){
					bean.setFecped(formulario.getFecha_pedido());
				}else{
					bean.setFecped(null);
				}
				
				if(null != formulario.getFecha_recepcion() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_recepcion()))){
					bean.setFecrec(formulario.getFecha_recepcion());
				}else{
					bean.setFecrec(null);
				}
				
				if(null != formulario.getFecha_entrega() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_entrega()))){
					bean.setFecent(formulario.getFecha_entrega());
				}else{
					bean.setFecent(null);
				}
				
				if(null != formulario.getFecha_caducidad() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_caducidad()))){
					bean.setFeccad(formulario.getFecha_caducidad());
				}else{
					bean.setFeccad(null);
				}
				
				if(null != formulario.getRevision1() && !(Constantes.STRING_BLANCO.equals(formulario.getRevision1()))){
					bean.setRevision(formulario.getRevision1());
				}else{
					bean.setRevision(null);
				}
				
				if(null != formulario.getOd1() && !(Constantes.STRING_BLANCO.equals(formulario.getOd1()))){
					bean.setOdprecio(Double.parseDouble(formulario.getOd1()));
				}
				
				if(null != formulario.getOi1() && !(Constantes.STRING_BLANCO.equals(formulario.getOi1()))){
					bean.setOiprecio(Double.parseDouble(formulario.getOi1()));					
				}
				
				if(null != formulario.getOd2() && !(Constantes.STRING_BLANCO.equals(formulario.getOd2()))){
					bean.setOdprecrep(Double.parseDouble(formulario.getOd2()));
				}				
				
				if(null != formulario.getOi2() && !(Constantes.STRING_BLANCO.equals(formulario.getOi2()))){
					bean.setOiprecrep(Double.parseDouble(formulario.getOi2()));
				}
				
				
				
								
				
				bean.setLimpiador(formulario.getLimpiador());
				bean.setConserva(formulario.getConservador());
				bean.setEnzimatico(formulario.getEnzimatico());
				bean.setOtrosprod(formulario.getOtro());							
				
				if(null != formulario.getSeguro() && !("".equals(formulario.getSeguro()))){
					bean.setSeguro(formulario.getSeguro());
				}else{
					bean.setSeguro("N");
				}
				
				bean.setRecomendacion(formulario.getRecomendaciones());				
				System.out.println("Entro al ingreso de contactologia helper");
				respuesta = PosGraduacionesFacade.ingresaContactologia(bean);		
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return respuesta;
	}
	
	public ArrayList<ContactologiaBean> traeContactologiaCliente(ContactologiaForm formulario){
		ArrayList<ContactologiaBean> lista_contacto = new ArrayList<ContactologiaBean>();
		try{
			
			lista_contacto = PosGraduacionesFacade.traeContactologiaCliente(formulario.getCliente());
			
		}catch(Exception ex){
			System.out.println(ex);
		}		
		return lista_contacto;
	}

	public ContactologiaBean traeContactoCliente(ContactologiaForm formulario){
		
		ContactologiaBean contacto=null;
		boolean existeContactologia=false;
		try{		
			String pagina = "CONTACTOLOGIA";
			String numero = formulario.getNumero_graduacion();
			String fech = formulario.getFecha_graduacion();
			String cliente = formulario.getCliente();
			int numerograd=-1;
			if(null != numero){
				numerograd = Integer.parseInt(numero);
			}
			
			existeContactologia = PosGraduacionesFacade.existeContactologiaPresEncargo(numerograd, fech, cliente, pagina);
			
			if(existeContactologia){
				formulario.setExisteContactologia("true");
			}else{
				formulario.setExisteContactologia("false");
			}
			
			ArrayList<ContactologiaBean> lista = formulario.getListaContactologia();
			int n=-1;
			if(null != numero && !(Constantes.STRING_BLANCO.equals(numero))){
				n=Integer.parseInt(numero);
			}
			
			if(null != lista){
				
				for(ContactologiaBean bean : lista){
					
					if((bean.getFecha().equals(fech)) && (bean.getNumero()==n)){
						contacto = bean;
						break;
					}					
				}				
			}
			
			if(null != contacto){
				
				if(null != contacto.getOdradio1()){
					formulario.setO_radio1(String.valueOf(contacto.getOdradio1()));		
				}else{
					formulario.setO_radio1(Constantes.STRING_BLANCO);
				}
				
				formulario.setDoctor(contacto.getClidefini_doctor());
				formulario.setNifdoctor(contacto.getNifdoctor());
				formulario.setDvnifdoctor(contacto.getDvnifdoctor());
				formulario.setNombre_doctor(contacto.getNombre_doctor());
				
				if(null != contacto.getOdradio2()){
					formulario.setO_radio2(String.valueOf(contacto.getOdradio2()));
				}else{
					formulario.setO_radio2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdesfera()){
					formulario.setO_esfera(String.valueOf(contacto.getOdesfera()));
				}else{
					formulario.setO_esfera(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOdcilindro()){
					formulario.setO_cilindro(String.valueOf(contacto.getOdcilindro()));
				}else{
					formulario.setO_cilindro(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOdeje()){
					formulario.setO_eje(String.valueOf(contacto.getOdeje()));
				}else{
					formulario.setO_eje(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOddiamt()){
					formulario.setO_diamt(String.valueOf(contacto.getOddiamt()));
				}else{
					formulario.setO_diamt(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOddiamz0()){
					formulario.setO_diaz(String.valueOf(contacto.getOddiamz0()));
				}else{
					formulario.setO_diaz(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdbandas()){
					formulario.setO_bandas(String.valueOf(contacto.getOdbandas()));
				}else{
					formulario.setO_bandas(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdesp() ){
					formulario.setO_esp(contacto.getOdesp());
				}else{
					formulario.setO_esp(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdtipo()){
					formulario.setO_radio3(String.valueOf(contacto.getOdtipo()));
				}else{
					formulario.setO_radio3(Constantes.STRING_BLANCO);
				}
				
				formulario.setO_diamp(contacto.getOdmaterial());
				formulario.setO_colo(contacto.getOdhidr());
				formulario.setO_adic(contacto.getOdadic());
				
				
				if(null != contacto.getOiradio1()){
					formulario.setI_radio1(String.valueOf(contacto.getOiradio1()));
				}else{
					formulario.setI_radio1(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOiradio2()){
					formulario.setI_radio2(String.valueOf(contacto.getOiradio2()));
				}else{
					formulario.setI_radio2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOiesfera()){
					formulario.setI_esfera(String.valueOf(contacto.getOiesfera()));
				}else{
					formulario.setI_esfera(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOicilindro()){
					formulario.setI_cilindro(String.valueOf(contacto.getOicilindro()));
				}else{
					formulario.setI_cilindro(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOieje()){
					formulario.setI_eje(String.valueOf(contacto.getOieje()));
				}else{
					formulario.setI_eje(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOidiamt()){
					formulario.setI_diamt(String.valueOf(contacto.getOidiamt()));
				}else{
					formulario.setI_diamt(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOidiamz0()){
					formulario.setI_diaz(String.valueOf(contacto.getOidiamz0()));
				}else{
					formulario.setI_diaz(Constantes.STRING_BLANCO);
				}
				
				formulario.setI_bandas(contacto.getOibandas());
				formulario.setI_esp(contacto.getOiesp());
				formulario.setI_radio3(contacto.getOitipo());
				formulario.setI_diamp(contacto.getOimaterial());
				formulario.setI_colo(contacto.getOihidr());
				formulario.setI_adic(contacto.getOiadic());
				
				formulario.setOtros(contacto.getOtros());
				formulario.setLaboratorio(contacto.getLaboratorio());
				
				//fechas
				formulario.setRevision1(contacto.getRevision());
				formulario.setFecha_caducidad(contacto.getFeccad());
				formulario.setFecha_pedido(contacto.getFecped());
				formulario.setFecha_recepcion(contacto.getFecrec());
				formulario.setFecha_entrega(contacto.getFecent());
				
				
				formulario.setCalculo_opt(contacto.getCalculos());
				formulario.setSeguro(contacto.getSeguro());
				
				
				if(null != contacto.getOdprecio()){
					formulario.setOd1(String.valueOf(contacto.getOdprecio()));
				}else{
					formulario.setOd1(Constantes.STRING_BLANCO);
				}
				
				if(null !=contacto.getOdprecrep()){
					formulario.setOd2(String.valueOf(contacto.getOdprecrep()));
				}else{
					formulario.setOd2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getMarcaod()){
					formulario.setOd3(contacto.getMarcaod());
				}else{
					formulario.setOd3(Constantes.STRING_BLANCO);
				}
				
				
				if(null != contacto.getOiprecio()){
					formulario.setOi1(String.valueOf(contacto.getOiprecio()));
				}else{
					formulario.setOi1(Constantes.STRING_BLANCO);
				}
				
				if(null !=contacto.getOiprecrep()){
					formulario.setOi2(String.valueOf(contacto.getOiprecrep()));
				}else{
					formulario.setOi2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getMarcaoi()){
					formulario.setOi3(contacto.getMarcaoi());
				}else{
					formulario.setOi3(Constantes.STRING_BLANCO);
				}

				formulario.setRecomendaciones(contacto.getRecomendacion());
				formulario.setLimpiador(contacto.getLimpiador());
				formulario.setConservador(contacto.getConserva());
				formulario.setEnzimatico(contacto.getEnzimatico());
				
								
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			return  null;
		}		
		return contacto;
	}
	
	public boolean modificaContactologia(ContactologiaForm formulario){
		boolean respuesta  = false;
		ContactologiaBean bean = new ContactologiaBean();
		Utils util = new Utils();
		
		try{
			
				if(null != formulario.getCliente() && !(Constantes.STRING_BLANCO.equals(formulario.getCliente()))){				
					bean.setCliente(Integer.parseInt(formulario.getCliente()));
				}	
				
				bean.setFecha(traeFechaHoyFormateadaString());
				bean.setFecha_ant(formulario.getFecha_graduacion());
				
				if(null != formulario.getNumero_graduacion() && !("".equals(formulario.getNumero_graduacion()))){
					bean.setNumero(Integer.parseInt(formulario.getNumero_graduacion()));
				}
				
				
				formulario.setTipo(Constantes.STRING_D);
				bean.setTipo(formulario.getTipo());
				bean.setClidefini_doctor(formulario.getDoctor());
				
				if(null != formulario.getO_radio1()&& !(Constantes.STRING_BLANCO.equals(formulario.getO_radio1()))){
					bean.setOdradio1(Double.parseDouble(formulario.getO_radio1()));
				}
				
				if(null != formulario.getO_radio2() && !(Constantes.STRING_BLANCO.equals(formulario.getO_radio2()))){
					bean.setOdradio2(Double.parseDouble(formulario.getO_radio2()));
				}
				
				if(null != formulario.getO_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getO_esfera()))){
					bean.setOdesfera(Double.parseDouble(formulario.getO_esfera()));
				}
				
				if(null != formulario.getO_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getO_cilindro()))){
					bean.setOdcilindro(Double.parseDouble(formulario.getO_cilindro()));
				}
				
				if(null != formulario.getO_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getO_eje()))){
					bean.setOdeje(Integer.parseInt(formulario.getO_eje()));
				}
				
				if(null != formulario.getO_diamt() && !(Constantes.STRING_BLANCO.equals(formulario.getO_diamt()))){
					bean.setOddiamt(Double.parseDouble(formulario.getO_diamt()));
				}
				
				if(null != formulario.getO_diaz() && !(Constantes.STRING_BLANCO.equals(formulario.getO_diaz()))){
					bean.setOddiamz0(Double.parseDouble(formulario.getO_diaz()));
				}
				
				bean.setOdbandas(formulario.getO_bandas());
				bean.setOdesp(formulario.getO_esp());
				bean.setOdtipo(formulario.getO_radio3());
				bean.setOdmaterial(formulario.getO_diamp());
				bean.setOdhidr(formulario.getO_colo());
				bean.setOdadic(formulario.getO_adic());
				
				//************************OJO IZQUIERDO*******************************
				
				if(null != formulario.getI_radio1() && !(Constantes.STRING_BLANCO.equals(formulario.getI_radio1()))){
					bean.setOiradio1(Double.parseDouble(formulario.getI_radio1()));
				}
				
				if(null != formulario.getI_radio2() && !(Constantes.STRING_BLANCO.equals(formulario.getI_radio2()))){
					bean.setOiradio2(Double.parseDouble(formulario.getI_radio2()));
				}
				
				if(null != formulario.getI_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getI_esfera()))){
					bean.setOiesfera(Double.parseDouble(formulario.getI_esfera()));
				}
				
				if(null != formulario.getI_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getI_cilindro()))){
					bean.setOicilindro(Double.parseDouble(formulario.getI_cilindro()));
				}
				
				if(null != formulario.getI_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getI_eje()))){
					bean.setOieje(Integer.parseInt(formulario.getI_eje()));
				}
				
				if(null != formulario.getI_diamt() && !(Constantes.STRING_BLANCO.equals(formulario.getI_diamt()))){
					bean.setOidiamt(Double.parseDouble(formulario.getI_diamt()));
				}
				
				if(null != formulario.getI_diaz() && !(Constantes.STRING_BLANCO.equals(formulario.getI_diaz()))){
					bean.setOidiamz0(Double.parseDouble(formulario.getI_diaz()));
				}
				
				bean.setOibandas(formulario.getI_bandas());
				bean.setOiesp(formulario.getI_esp());
				bean.setOitipo(formulario.getI_radio3());
				bean.setOimaterial(formulario.getO_diamp());
				bean.setOihidr(formulario.getO_colo());
				bean.setOiadic(formulario.getI_adic());
				
				bean.setOtros(formulario.getOtros());
				bean.setLaboratorio(formulario.getLaboratorio());
				bean.setCalculos(formulario.getCalculo_opt());
				
				
				if(null != formulario.getFecha_pedido() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_pedido()))){
					bean.setFecped(formulario.getFecha_pedido());
				}else{
					bean.setFecped(null);
				}
				
				if(null != formulario.getFecha_recepcion() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_recepcion()))){
					bean.setFecrec(formulario.getFecha_recepcion());
				}else{
					bean.setFecrec(null);
				}
				
				if(null != formulario.getFecha_entrega() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_entrega()))){
					bean.setFecent(formulario.getFecha_entrega());
				}else{
					bean.setFecent(null);
				}
				
				if(null != formulario.getFecha_caducidad() && !(Constantes.STRING_BLANCO.equals(formulario.getFecha_caducidad()))){
					bean.setFeccad(formulario.getFecha_caducidad());
				}else{
					bean.setFeccad(null);
				}
			
				
				if(null != formulario.getOd1() && !(Constantes.STRING_BLANCO.equals(formulario.getOd1()))){
					bean.setOdprecio(Double.parseDouble(formulario.getOd1()));
				}
				
				if(null != formulario.getOi1() && !(Constantes.STRING_BLANCO.equals(formulario.getOi1()))){
					bean.setOiprecio(Double.parseDouble(formulario.getOi1()));					
				}
				
				if(null != formulario.getOd2() && !(Constantes.STRING_BLANCO.equals(formulario.getOd2()))){
					bean.setOdprecrep(Double.parseDouble(formulario.getOd2()));
				}				
				
				if(null != formulario.getOi2() && !(Constantes.STRING_BLANCO.equals(formulario.getOi2()))){
					bean.setOiprecrep(Double.parseDouble(formulario.getOi2()));
				}
				
				
				if(null != formulario.getRevision1() && !(Constantes.STRING_BLANCO.equals(formulario.getRevision1()))){
					bean.setRevision(formulario.getRevision1());
				}else{
					bean.setRevision(null);
				}
				
				
				
				bean.setLimpiador(formulario.getLimpiador());
				bean.setConserva(formulario.getConservador());
				bean.setEnzimatico(formulario.getEnzimatico());
				bean.setOtrosprod(formulario.getOtro());							
				
				if(null != formulario.getSeguro() && !("".equals(formulario.getSeguro()))){
					bean.setSeguro(formulario.getSeguro());
				}else{
					bean.setSeguro("N");
				}
				
				bean.setRecomendacion(formulario.getRecomendaciones());				
				System.out.println("Entro a modificar de contactologia helper");
				respuesta = PosGraduacionesFacade.modificaContactologia(bean);		
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return respuesta;
	}

	
public boolean modificaGraduacion(GraduacionesForm formulario){
		
		GraduacionesBean graduacion = new GraduacionesBean();
		boolean respuesta = false;
		try{
			
			int numero = PosGraduacionesFacade.traeNumeroGraduacion(String.valueOf(formulario.getCliente()), traeFechaHoyFormateadaString());			
			
			graduacion.setCliente(formulario.getCliente());
			
			
			graduacion.setFecha(traeFechaHoyFormateadaString());
			graduacion.setFecha_ant(formulario.getFecha_graduacion());
			
			int numer = (int) formulario.getNumero_graduacion();
			graduacion.setNumero(numer);
			
			
			graduacion.setDoctor(formulario.getCod_doctor());	
			graduacion.setFecha_prox_revision(formulario.getFechaProxRevision());
			graduacion.setTipo(formulario.getTipo());
			
			if(null != formulario.getOD_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_esfera()))){
				graduacion.setOD_esfera(Double.valueOf(formulario.getOD_esfera()));
			}else{
				graduacion.setOD_esfera(null);
			}
				
			if(null != formulario.getOD_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cilindro()))){
				graduacion.setOD_cilindro(Double.valueOf(formulario.getOD_cilindro()));
			}else{
				graduacion.setOD_cilindro(null);
			}	
			
			graduacion.setAgente(formulario.getAgente());
			
			if(null != formulario.getOD_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_eje()))){
				graduacion.setOD_eje(Integer.valueOf(formulario.getOD_eje()));
			}else{
				graduacion.setOD_eje(null);
			}
			
			if(null != formulario.getOD_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_adicion()))){
				graduacion.setOD_adicion(Double.valueOf(formulario.getOD_adicion()));
			}else{
				graduacion.setOD_adicion(null);
			}
			
			if(null != formulario.getOD_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_cerca()))){
				graduacion.setOD_esfera_cerca(Double.valueOf(formulario.getOD_cerca()));
			}else{
				graduacion.setOD_esfera_cerca(null);
			}
			
			if(null !=formulario.getOD_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpl())) ){
				graduacion.setOD_n(Double.valueOf(formulario.getOD_dnpl()));
			}else{
				graduacion.setOD_n(null);
			}
			
			if(null != formulario.getOD_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_dnpc()))){
				graduacion.setOD_p(Double.valueOf(formulario.getOD_dnpc()));
			}else{
				graduacion.setOD_p(null);
			}
			
			
			
			if(null != formulario.getOD_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_observaciones()))){
				graduacion.setOD_obser(formulario.getOD_observaciones().toUpperCase());
			}else{
				graduacion.setOD_obser(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOD_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avsc()))){
				graduacion.setOD_avsc(Double.valueOf(formulario.getOD_avsc()));
			}else{
				graduacion.setOD_avsc(null);
			}
			
			if(null != formulario.getOD_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOD_avcc()))){
				graduacion.setOD_avcc(Double.valueOf(formulario.getOD_avcc()));
			}else{
				graduacion.setOD_avcc(null);
			}			
			
			graduacion.setOD_cantidad(formulario.getOD_cantidad());
			graduacion.setOD_base(formulario.getOD_base());
			graduacion.setOD_altura(formulario.getOD_altura());
			
			if(null != formulario.getOI_esfera() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_esfera()))){
				graduacion.setOI_esfera(Double.valueOf(formulario.getOI_esfera()));
			}else{
				graduacion.setOI_esfera(null);
			}
			
			
			if(null != formulario.getOI_cilindro() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cilindro()))){
				graduacion.setOI_cilindro(Double.valueOf(formulario.getOI_cilindro()));
			}else{
				graduacion.setOI_cilindro(null);
			}
			
			if(null != formulario.getOI_eje() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_eje()))){
				graduacion.setOI_eje(Integer.valueOf(formulario.getOI_eje()));
			}else{
				graduacion.setOI_eje(null);
			}
			
			if(null != formulario.getOI_adicion() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_adicion()))){
				graduacion.setOI_adicion(Double.valueOf(formulario.getOI_adicion()));
			}else{
				graduacion.setOI_adicion(null);
			}	
			
			if(null !=formulario.getOI_cerca() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_cerca()))){
				graduacion.setOI_esfera_cerca(Double.valueOf(formulario.getOI_cerca()));
			}else{
				graduacion.setOI_esfera_cerca(null);
			}
			
			if(null !=formulario.getOI_dnpl() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpl())) ){
				graduacion.setOI_n(Double.valueOf(formulario.getOI_dnpl()));
			}else{
				graduacion.setOI_n(null);
			}
			
			if(null != formulario.getOI_dnpc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_dnpc()))){
				graduacion.setOI_p(Double.valueOf(formulario.getOI_dnpc()));
			}else{
				graduacion.setOI_p(null);
			}
			
			
			
			if(null != formulario.getOI_observaciones() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_observaciones()))){
				graduacion.setOI_obser(formulario.getOI_observaciones().toUpperCase());
			}else{
				graduacion.setOI_obser(Constantes.STRING_BLANCO);
			}
			
			if(null != formulario.getOI_avsc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avsc()))){
				graduacion.setOI_avsc(Double.valueOf(formulario.getOI_avsc()));
			}else{
				graduacion.setOI_avsc(null);
			}
			
			if(null != formulario.getOI_avcc() && !(Constantes.STRING_BLANCO.equals(formulario.getOI_avcc()))){
				graduacion.setOI_avcc(Double.valueOf(formulario.getOI_avcc()));
			}else{
				graduacion.setOI_avcc(null);
			}
					
			graduacion.setOI_cantidad(formulario.getOI_cantidad());
			graduacion.setOI_base(formulario.getOI_base());
			graduacion.setOI_altura(formulario.getOI_altura());
			graduacion.setDiferente_add(formulario.isDiferenteAdd());
					
			graduacion.setFecha_emision(formulario.getFechaEmision());
			graduacion.setDiferente_add(formulario.isDiferenteAdd());
			
			respuesta = PosGraduacionesFacade.modificaGraduacion(graduacion);		
			
		}catch(Exception ex){
			respuesta = false;
			ex.printStackTrace();
		}
		
		return respuesta;
	}

	
	public ContactologiaForm traeContactologia(ContactologiaForm formulario){
		
		ContactologiaBean contacto=null;
		boolean existeContactologia=false;
		try{		
			String pagina = "CONTACTOLOGIA";
			String numero = formulario.getNumero_graduacion();
			String fech = formulario.getFecha_graduacion();
			String cliente = formulario.getCliente();
			int numerograd=-1;
			try{
			if(null != numero){
				numerograd = Integer.parseInt(numero);
			}
			}catch(Exception ex){
				numerograd=-1;
			}
			
			contacto = PosGraduacionesFacade.traeContactologia(numerograd, fech, cliente);
			
			
			
			
			
			if(null != contacto){
				
				if(null != contacto.getOdradio1()){
					formulario.setO_radio1(String.valueOf(contacto.getOdradio1()));		
				}else{
					formulario.setO_radio1(Constantes.STRING_BLANCO);
				}
				
				formulario.setDoctor(contacto.getClidefini_doctor());
				formulario.setNifdoctor(contacto.getNifdoctor());
				formulario.setDvnifdoctor(contacto.getDvnifdoctor());
				formulario.setNombre_doctor(contacto.getNombre_doctor());
				
				if(null != contacto.getOdradio2()){
					formulario.setO_radio2(String.valueOf(contacto.getOdradio2()));
				}else{
					formulario.setO_radio2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdesfera()){
					formulario.setO_esfera(String.valueOf(contacto.getOdesfera()));
				}else{
					formulario.setO_esfera(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOdcilindro()){
					formulario.setO_cilindro(String.valueOf(contacto.getOdcilindro()));
				}else{
					formulario.setO_cilindro(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOdeje()){
					formulario.setO_eje(String.valueOf(contacto.getOdeje()));
				}else{
					formulario.setO_eje(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOddiamt()){
					formulario.setO_diamt(String.valueOf(contacto.getOddiamt()));
				}else{
					formulario.setO_diamt(Constantes.STRING_BLANCO);			
				}
				
				if(null != contacto.getOddiamz0()){
					formulario.setO_diaz(String.valueOf(contacto.getOddiamz0()));
				}else{
					formulario.setO_diaz(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdbandas()){
					formulario.setO_bandas(String.valueOf(contacto.getOdbandas()));
				}else{
					formulario.setO_bandas(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdesp() ){
					formulario.setO_esp(contacto.getOdesp());
				}else{
					formulario.setO_esp(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOdtipo()){
					formulario.setO_radio3(String.valueOf(contacto.getOdtipo()));
				}else{
					formulario.setO_radio3(Constantes.STRING_BLANCO);
				}
				if(null != contacto.getOdmaterial()){
					formulario.setO_diamp(contacto.getOdmaterial());
				}else{
					formulario.setO_diamp("");
				}
				
				formulario.setO_colo(contacto.getOdhidr());
				formulario.setO_adic(contacto.getOdadic());
				
				
				if(null != contacto.getOiradio1()){
					formulario.setI_radio1(String.valueOf(contacto.getOiradio1()));
				}else{
					formulario.setI_radio1(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOiradio2()){
					formulario.setI_radio2(String.valueOf(contacto.getOiradio2()));
				}else{
					formulario.setI_radio2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOiesfera()){
					formulario.setI_esfera(String.valueOf(contacto.getOiesfera()));
				}else{
					formulario.setI_esfera(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOicilindro()){
					formulario.setI_cilindro(String.valueOf(contacto.getOicilindro()));
				}else{
					formulario.setI_cilindro(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOieje()){
					formulario.setI_eje(String.valueOf(contacto.getOieje()));
				}else{
					formulario.setI_eje(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOidiamt()){
					formulario.setI_diamt(String.valueOf(contacto.getOidiamt()));
				}else{
					formulario.setI_diamt(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOidiamz0()){
					formulario.setI_diaz(String.valueOf(contacto.getOidiamz0()));
				}else{
					formulario.setI_diaz(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getOitipo()){
					formulario.setI_radio3(contacto.getOitipo());
				}else{
					formulario.setI_radio3("");
				}
				
				if(null != contacto.getOimaterial()){
					formulario.setI_diamp(contacto.getOimaterial());
				}else{
					formulario.setI_diamp("");
				}
				
				
				formulario.setI_bandas(contacto.getOibandas());
				formulario.setI_esp(contacto.getOiesp());
				
				
				formulario.setI_colo(contacto.getOihidr());
				formulario.setI_adic(contacto.getOiadic());
				
				formulario.setOtros(contacto.getOtros());
				formulario.setLaboratorio(contacto.getLaboratorio());
				
				//fechas
				formulario.setRevision1(contacto.getRevision());
				formulario.setFecha_caducidad(contacto.getFeccad());
				formulario.setFecha_pedido(contacto.getFecped());
				formulario.setFecha_recepcion(contacto.getFecrec());
				formulario.setFecha_entrega(contacto.getFecent());
				
				
				formulario.setCalculo_opt(contacto.getCalculos());
				formulario.setSeguro(contacto.getSeguro());
				
				
				if(null != contacto.getOdprecio()){
					formulario.setOd1(String.valueOf(contacto.getOdprecio()));
				}else{
					formulario.setOd1(Constantes.STRING_BLANCO);
				}
				
				if(null !=contacto.getOdprecrep()){
					formulario.setOd2(String.valueOf(contacto.getOdprecrep()));
				}else{
					formulario.setOd2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getMarcaod()){
					formulario.setOd3(contacto.getMarcaod());
				}else{
					formulario.setOd3(Constantes.STRING_BLANCO);
				}
				
				
				if(null != contacto.getOiprecio()){
					formulario.setOi1(String.valueOf(contacto.getOiprecio()));
				}else{
					formulario.setOi1(Constantes.STRING_BLANCO);
				}
				
				if(null !=contacto.getOiprecrep()){
					formulario.setOi2(String.valueOf(contacto.getOiprecrep()));
				}else{
					formulario.setOi2(Constantes.STRING_BLANCO);
				}
				
				if(null != contacto.getMarcaoi()){
					formulario.setOi3(contacto.getMarcaoi());
				}else{
					formulario.setOi3(Constantes.STRING_BLANCO);
				}
	
				formulario.setRecomendaciones(contacto.getRecomendacion());
				formulario.setLimpiador(contacto.getLimpiador());
				formulario.setConservador(contacto.getConserva());
				formulario.setEnzimatico(contacto.getEnzimatico());
				
								
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			return  null;
		}		
		return formulario;
	}

	

}
