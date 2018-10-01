package cl.gmo.pos.venta.web.helper;

import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.facade.PosMedicoFacade;
import cl.gmo.pos.venta.web.forms.MedicoForm;

public class MedicoHelper extends Utils{

	Logger log = Logger.getLogger( this.getClass() );
	
	public int ingresaMedico(MedicoForm formulario){
		log.info("MedicoHelper:ingresaMedico inicio");
		int respuesta = -1;
		OftalmologoBean medico = new OftalmologoBean();
		
		try{
			
			medico.setCodigo(formulario.getCodigo());
			medico.setNombre(formulario.getNombres());
			medico.setDireccion(formulario.getDireccion());
			medico.setLocali(formulario.getLocali());
			medico.setCodpos(formulario.getCodpos());
			medico.setTfno(formulario.getTfno());
			medico.setEmail(formulario.getEmail());
			medico.setFax(formulario.getFax());
			medico.setApelli(formulario.getApellidos());
			medico.setProvinci(formulario.getProvinci());
			if(null != formulario.getExterno()){
				medico.setExterno(formulario.getExterno());
			}else{
				medico.setExterno(Constantes.STRING_TIPO_ALB_NO);
			}
			medico.setNif(formulario.getRut());
			medico.setLnif(formulario.getDv());
						
			respuesta=PosMedicoFacade.ingresaMedico(medico);
			
			if (respuesta == 0) {
				formulario.setCodigo(medico.getCodigo());
			}
			
		}catch(Exception ex){
			log.error("MedicoHelper:ingresaMedico error catch",ex);
			respuesta = -1;
		}
		
		return respuesta;
	}
	
	public String traeCodigoDoctor(){
		log.info("MedicoHelper:traeCodigoDoctor inicio");
		String codigo = new String();
		try{
			codigo = PosMedicoFacade.traeCodigoDoctor();
			if(!(Constantes.STRING_BLANCO.equals(codigo)) && null != codigo ){
				int cdg = Integer.parseInt(codigo);				
				codigo = String.valueOf(cdg);
			}
			
		}catch(Exception ex){
			log.error("MedicoHelper:traeCodigoDoctor error catch",ex);
		}
		return codigo;
	}

	public MedicoForm traeMedico(MedicoForm formulario){
		log.info("MedicoHelper:traeMedico inicio");
		OftalmologoBean medico = new OftalmologoBean();
		try{
			medico = PosMedicoFacade.traeMedico(formulario.getCodigo_medico_agregado());
			
			formulario.setCodigo(medico.getCodigo());
			formulario.setRut(medico.getNif());
			formulario.setDv(medico.getLnif());
			formulario.setApellidos(medico.getApelli());
			formulario.setNombres(medico.getNombre());
			formulario.setDireccion(medico.getDireccion());
			formulario.setLocali(medico.getLocali());
			formulario.setProvinci(medico.getProvinci());
			formulario.setExterno(medico.getExterno());
			formulario.setTfno(medico.getTfno());
			formulario.setFax(medico.getFax());
			formulario.setEmail(medico.getEmail());
			formulario.setCodpos(medico.getCodpos());
			
		}catch(Exception ex){
			log.error("MedicoHelper:traeMedico error catch",ex);
		}		
		return formulario;
	}
}
