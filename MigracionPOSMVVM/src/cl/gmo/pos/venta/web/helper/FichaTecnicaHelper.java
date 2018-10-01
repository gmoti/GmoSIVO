package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.ProductosDAOImpl;
import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.facade.PosFichaTecnicaFacade;

public class FichaTecnicaHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public ArrayList<FichaTecnicaBean> traeFichaTaller(String codigo_venta_pedido, int codigo_cliente, int saldo){
		log.info("FichaTecnicaHelper:traeFichaTaller inicio");
				
		ArrayList<FichaTecnicaBean> lista_fichas = new ArrayList<FichaTecnicaBean>();
		try{
			Utils util = new Utils();
			FichaTecnicaBean fichaf = PosFichaTecnicaFacade.traeFichaTaller(codigo_venta_pedido, codigo_cliente);
			
			for(FichaTecnicaBean fcabecera : fichaf.getLista_cabeceras()){
				FichaTecnicaBean ficha = new FichaTecnicaBean();
				ficha.setNumero_encargo(fcabecera.getNumero_encargo()+"/"+fcabecera.getGrupo());
				ficha.setNumero_encargo2(fcabecera.getNumero_encargo());
				ficha.setGrupo(fcabecera.getGrupo());
				ficha.setAgente(fcabecera.getAgente());
				ficha.setFecha_encargo(fcabecera.getFecha_encargo());
				ficha.setFecha_entrega(fcabecera.getFecha_entrega());
				ficha.setCodigo_cliente(fcabecera.getCodigo_cliente());
				ficha.setNombre_cliente(fcabecera.getNombre_cliente());
				ficha.setApellido_cliente(fcabecera.getApellido_cliente());
				ficha.setNota("Nota: "+fcabecera.getNota());
				if(null != fcabecera.getTelefono_cliente()){
					ficha.setTelefono_cliente(fcabecera.getTelefono_cliente());
				}else{
					ficha.setTelefono_cliente("");
				}
				
				
				ficha.setRut_cliente(fcabecera.getRut_cliente());
				
				if(null != fcabecera.getCodigo_medico()){
					ficha.setCodigo_medico(fcabecera.getCodigo_medico());
				}else{
					ficha.setCodigo_medico(Constantes.STRING_BLANCO);
				}
				
				if(null != fcabecera.getMedico()){
					ficha.setMedico(fcabecera.getMedico());
				}else{
					ficha.setMedico(Constantes.STRING_BLANCO);
				}
				
				if(null != fcabecera.getTelefono_medico()){
					ficha.setTelefono_medico(fcabecera.getTelefono_medico());
				}else{
					ficha.setTelefono_medico(Constantes.STRING_BLANCO);
				}
				
				ficha.setSaldo(saldo);
				
				
				
				for(FichaTecnicaBean fichamont:fichaf.getLista_armazones()){
					log.info("FichaTecnicaHelper:traeFichaTaller entrando ciclo for");
					if(fichamont.getGrupo_armazon().trim().equals(String.valueOf(ficha.getGrupo()))){
						
						ficha.setIdent_linea_armazon(fichamont.getIdent_linea_armazon());
						ficha.setGrupo_armazon(fichamont.getGrupo_armazon());				
						ficha.setCod_pedvtcb(fichamont.getCod_pedvtcb());				
						ficha.setCod_armazon(fichamont.getCod_armazon());
						ficha.setDescripcion_armazon(fichamont.getDescripcion_armazon());
						
						if(null != fichamont.getColor_armazon()){
							ficha.setColor_armazon(fichamont.getColor_armazon());
						}else{
							ficha.setColor_armazon(Constantes.STRING_BLANCO);
						}
						
						if(null != fichamont.getMaterial_armazon()){
							ficha.setMaterial_armazon(fichamont.getMaterial_armazon());
						}else{
							ficha.setMaterial_armazon(Constantes.STRING_BLANCO);
						}
						
						if(null != fichamont.getSexo_armazon()){
							ficha.setSexo_armazon(fichamont.getSexo_armazon());
						}else{
							ficha.setSexo_armazon(Constantes.STRING_BLANCO);
						}
						
						if(null != fichamont.getCalibre_armazon()){
							ficha.setCalibre_armazon(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fichamont.getCalibre_armazon()));
						}else{
							ficha.setCalibre_armazon(Constantes.STRING_BLANCO);
						}
						
						if(null != fichamont.getPuente_armazon()){
							ficha.setPuente_armazon(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fichamont.getPuente_armazon()));
						}else{
							ficha.setPuente_armazon(Constantes.STRING_BLANCO);
						}
						
						if(null != fichamont.getAltura_armazon()){
							ficha.setAltura_armazon(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fichamont.getAltura_armazon()));
						}else{
							ficha.setAltura_armazon(Constantes.STRING_BLANCO);
						}
						
						if(null != fichamont.getEstilo_armazon()){
							ficha.setEstilo_armazon(fichamont.getEstilo_armazon());
						}else{
							ficha.setEstilo_armazon(Constantes.STRING_BLANCO);
						}
						
						
					}					
				}//fin for each (FichaTecnicaBean pro:fichat.getLista_armazones())
				
				
				for(FichaTecnicaBean fcristal:fichaf.getLista_cristales()){
					log.info("FichaTecnicaHelper:traeFichaTaller entrando ciclo for");
					if(fcristal.getGrupo_cristalD().trim().equals(String.valueOf(ficha.getGrupo())) && fcristal.getOjo_cristalD().trim().equals(Constantes.STRING_D)){					
						
						ficha.setIdent_linea_cristalD(fcristal.getIdent_linea_cristalD());					
						ficha.setGrupo_cristalD(fcristal.getGrupo_cristalD());	
						ficha.setCod_pedvtcb_cristalD(fcristal.getCod_pedvtcb_cristalD());
						ficha.setCod_critalD(fcristal.getCod_critalD());
						ficha.setDescripcion_critalD(fcristal.getDescripcion_critalD());
						ficha.setOjo_cristalD(fcristal.getOjo_cristalD());
						
						if(null != fcristal.getEsfera_cristalD()){
							ficha.setEsfera_cristalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getEsfera_cristalD()));
						}else{
							ficha.setEsfera_cristalD(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getCilindro_cristalD()){
							ficha.setCilindro_cristalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getCilindro_cristalD()));
						}else{
							ficha.setCilindro_cristalD(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getEje_critalD()){
							ficha.setEje_critalD(fcristal.getEje_critalD());
						}else{
							ficha.setEje_critalD(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getDnpl_critalD()){
							ficha.setDnpl_critalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getDnpl_critalD()));
						}else{
							ficha.setDnpl_critalD(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getDnpc_critalD()){
							ficha.setDnpc_critalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getDnpc_critalD()));		
						}else{
							ficha.setDnpc_critalD(Constantes.STRING_BLANCO);		
						}
						
						if(null != fcristal.getAdicion_critalD()){
							ficha.setAdicion_critalD(fcristal.getAdicion_critalD());
						}else{
							ficha.setAdicion_critalD(Constantes.STRING_BLANCO);
						}
							
						if(null != fcristal.getColor_cristalD()){
							ficha.setColor_cristalD(fcristal.getColor_cristalD());
						}else{
							ficha.setColor_cristalD("");
						}
						
						
						if(null != fcristal.getTrat_cristalD()){
							ficha.setTrat_cristalD(fcristal.getTrat_cristalD());
						}else{
							ficha.setTrat_cristalD(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getTipo_cristalD()){
							ficha.setTipo_cristalD(fcristal.getTipo_cristalD());
						}else{
							ficha.setTipo_cristalD(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getInd_cristalD()){
							ficha.setInd_cristalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getInd_cristalD()));
						}else{
							ficha.setInd_cristalD(Constantes.STRING_BLANCO);
						}
						
						//agregar validacion
						ProductosBean prod = new ProductosBean();
						prod = util.traeInfoproducto(fcristal.getCod_critalD());
						try {
							prod.setEsfera(Double.parseDouble(fcristal.getEsfera_cristalD()));
						} catch (Exception e) {
							prod.setEsfera(0D);
						}
						try {
							prod.setCilindro(Double.parseDouble(fcristal.getCilindro_cristalD()));
						} catch (Exception e) {
							prod.setCilindro(0D);
						}
						util.validaDiametroESFCL(prod);
						fcristal.setDiametro_cristalD(String.valueOf(prod.getDiametro()));
						
						
												
						if(null != fcristal.getDiametro_cristalD()){
							ficha.setDiametro_cristalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getDiametro_cristalD()));	
						}else{
							ficha.setDiametro_cristalD(Constantes.STRING_BLANCO);	
						}
						
						if(null != fcristal.getSuplemento_cristaD()){
							ficha.setSuplemento_cristaD(fcristal.getSuplemento_cristaD());
						}else{
							ficha.setSuplemento_cristaD(Constantes.STRING_BLANCO);
						}
									
						if(null != fcristal.getValor_suple_cristalD() ){
							ficha.setValor_suple_cristalD(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getValor_suple_cristalD()));
						}else{
							ficha.setValor_suple_cristalD(Constantes.STRING_BLANCO);
						}
						
						ArrayList<SuplementopedidoBean> Suplementos = new ArrayList<SuplementopedidoBean>(); 
						
						if (fcristal.getEsMultioferta().equals("SI")) {
							Suplementos = util.traeSuplementosPedidoLiberacionMultioferta(fcristal.getIdent_linea_cristalD());
						}
						else
						{
							Suplementos = util.traeSuplementosPedidoLiberacion(fcristal.getIdent_linea_cristalD());
						}
						
						String detalle = "";
						if(null != Suplementos && Suplementos.size()>0) 
						{
							for (SuplementopedidoBean supl : Suplementos) 
							{
								detalle = detalle + supl.getDescripcion().trim() + " ( "  + supl.getTratami() + " ) : " + supl.getValor() + "	";
							}
							ficha.setSuplemento_cristaD(detalle);
						}
						else
						{
							ficha.setSuplemento_cristaD("");
						}	
						
						
						
						
					}else if(fcristal.getGrupo_cristalD().trim().equals(String.valueOf(ficha.getGrupo())) && fcristal.getOjo_cristalD().trim().equals("I")){
						
						ficha.setIdent_linea_cristalI(fcristal.getIdent_linea_cristalD());					
						ficha.setGrupo_cristalI(fcristal.getGrupo_cristalD());	
						ficha.setCod_pedvtcb_cristalI(fcristal.getCod_pedvtcb_cristalD());
						ficha.setCod_cristalI(fcristal.getCod_critalD());
						ficha.setDescripcion_critalI(fcristal.getDescripcion_critalD());
						ficha.setOjo_cristalI(fcristal.getOjo_cristalD());
						
						
						if(null != fcristal.getEsfera_cristalD()){
							ficha.setEsfera_cristalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getEsfera_cristalD()));
						}else{
							ficha.setEsfera_cristalI(Constantes.STRING_BLANCO);
						}
						
						
						if(null != fcristal.getCilindro_cristalD()){
							ficha.setCilindro_cristalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getCilindro_cristalD()));
						}else{
							ficha.setCilindro_cristalI(Constantes.STRING_BLANCO);
						}
						
						
						if(null != fcristal.getEje_critalD()){
							ficha.setEje_critalI(fcristal.getEje_critalD());
						}else{
							ficha.setEje_critalI(Constantes.STRING_BLANCO);
						}
						
						
						if(null != fcristal.getDnpl_critalI()){
							ficha.setDnpl_critalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getDnpl_critalI()));
						}else{
							ficha.setDnpl_critalI(Constantes.STRING_BLANCO);
						}
						
						
							
						if(null != fcristal.getDnpc_critalI()){
							ficha.setDnpc_critalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getDnpc_critalI()));
						}else{
							ficha.setDnpc_critalI(Constantes.STRING_BLANCO);
						}
						
						
						if(null != fcristal.getAdicion_critalI()){
							ficha.setAdicion_critalI(fcristal.getAdicion_critalI());
						}else{
							ficha.setAdicion_critalI(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getColor_cristalD()){
							ficha.setColor_cristalI(fcristal.getColor_cristalD());
						}else{
							ficha.setColor_cristalI("");
						}
											
						
						if(null != fcristal.getTrat_cristalD()){
							ficha.setTrat_cristalI(fcristal.getTrat_cristalD());
						}else{
							ficha.setTrat_cristalI(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getTipo_cristalD()){
							ficha.setTipo_cristalI(fcristal.getTipo_cristalD());
						}else{
							ficha.setTipo_cristalI(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getInd_cristalD()){
							ficha.setInd_cristalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getInd_cristalD()));
						}else{
							ficha.setInd_cristalI(Constantes.STRING_BLANCO);
						}
						
						//agregar validacion
						ProductosBean prod = new ProductosBean();
						prod = util.traeInfoproducto(fcristal.getCod_cristalI());
						try {
							prod.setEsfera(Double.parseDouble(fcristal.getEsfera_cristalI()));
						} catch (Exception e) {
							prod.setEsfera(0D);
						}
						try {
							prod.setCilindro(Double.parseDouble(fcristal.getCilindro_cristalI()));
						} catch (Exception e) {
							prod.setCilindro(0D);
						}
						util.validaDiametroESFCL(prod);
						fcristal.setDiametro_cristalI(String.valueOf(prod.getDiametro()));
						
						
						if(null != fcristal.getDiametro_cristalD()){
							ficha.setDiametro_cristalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getDiametro_cristalD()));
						}else{
							ficha.setDiametro_cristalI(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getSuplemento_cristaD()){
							ficha.setSuplemento_cristaI(fcristal.getSuplemento_cristaD());
						}else{
							ficha.setSuplemento_cristaI(Constantes.STRING_BLANCO);
						}
						
						if(null != fcristal.getValor_suple_cristalD()){
							ficha.setValor_suple_cristalI(util.foramtoValoresRerpoteFichaTecnicaDosDecimales(fcristal.getValor_suple_cristalD()));
						}else{
							ficha.setValor_suple_cristalI(Constantes.STRING_BLANCO);
						}
						
						ArrayList<SuplementopedidoBean> Suplementos = new ArrayList<SuplementopedidoBean>();
						
						if (fcristal.getEsMultioferta().equals("SI")) {
							Suplementos = util.traeSuplementosPedidoLiberacionMultioferta(fcristal.getIdent_linea_cristalD());
						}
						else
						{
							Suplementos = util.traeSuplementosPedidoLiberacion(fcristal.getIdent_linea_cristalD());
						}
						
						String detalle = "";
						if(null != Suplementos && Suplementos.size()>0) 
						{
							for (SuplementopedidoBean supl : Suplementos) 
							{
								detalle = detalle + supl.getDescripcion().trim() + " ( "  + supl.getTratami() + " ) : " + supl.getValor() + "	";
							}
							ficha.setSuplemento_cristaI(detalle);
						}
						else
						{
							ficha.setSuplemento_cristaI("");
						}	
						
						
					}
					
				}
				
				lista_fichas.add(ficha);
			
			}//fin (FichaTecnicaBean fichat : fichaf.getLista_cabeceras())
			
		}catch(Exception ex){
			log.error("FichaTecnicaHelper:traeFichaTaller error catch",ex);
		}
		return lista_fichas;
		
	}

}
