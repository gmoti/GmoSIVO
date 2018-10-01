package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.FichaTecnicaDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.FichaTecnicaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;


public class FichaTecnicaDAOImpl implements FichaTecnicaDAO {
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public FichaTecnicaBean traeFichataller(String codigo_venta_pedido, int codigo_cliente) {
		log.info("FichaTecnicaDAOImpl:traeFichataller inicio");
		Connection con = null;
		ResultSet rsCabecera = null;
		ResultSet rsLineas = null;
		ResultSet rsGraduacion = null;
		CallableStatement cs = null;
		Utils util = new Utils();
		FichaTecnicaBean ficha_taller= new FichaTecnicaBean();
		ArrayList<FichaTecnicaBean> lista_fichas = new ArrayList<FichaTecnicaBean>();
		ArrayList<ProductosBean> lista_lineas = new ArrayList<ProductosBean>();
		
		try{
			log.info("FichaTecnicaDAOImpl:traeFichataller conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			System.out.println("codigo venta pedido =>"+codigo_venta_pedido+"<==>codigo cliente ==>"+codigo_cliente);
			String sql = "{call SP_INFORME_SEL_FICHA_TECNICA(?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			cs.setString(1, codigo_venta_pedido);
			cs.setInt(2, codigo_cliente);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			
			cs.execute();
			
			ficha_taller.setLista_cabeceras(new ArrayList<FichaTecnicaBean>());
			ficha_taller.setLista_armazones(new ArrayList<FichaTecnicaBean>());
			ficha_taller.setLista_cristales(new ArrayList<FichaTecnicaBean>());

			
			rsCabecera = (ResultSet)cs.getObject(3);
			rsLineas = (ResultSet)cs.getObject(4);
			rsGraduacion = (ResultSet)cs.getObject(5);
			
			
			
			FichaTecnicaBean ficha=null;
			while(rsCabecera.next()){
				log.debug("FichaTecnicaDAOImpl:traeFichataller entrando ciclo while");
				ficha = new FichaTecnicaBean();
				
				ficha.setNumero_encargo(rsCabecera.getString("CODIGO"));
				ficha.setGrupo(rsCabecera.getInt("GRUPO"));
				ficha.setAgente(rsCabecera.getString("AGENTE"));
				ficha.setFecha_encargo(util.formatoFecha(rsCabecera.getDate("FECHAPEDIDO")));
				ficha.setFecha_entrega(util.formatoFecha(rsCabecera.getDate("FECHAENTREGA")));
				ficha.setCodigo_cliente(rsCabecera.getInt("CODCLIENTE"));
				ficha.setNombre_cliente(rsCabecera.getString("NOMBRE_CLIENTE"));
				ficha.setApellido_cliente(rsCabecera.getString("APELLI_CLIENTE"));
				ficha.setTelefono_cliente(rsCabecera.getString("FONO_CLIENTE"));
				ficha.setRut_cliente(rsCabecera.getString("RUT")+"-"+rsCabecera.getString("LETRA"));
				ficha.setCodigo_medico(rsCabecera.getString("COD_DOCTOR"));
				ficha.setNota(rsCabecera.getString("NOTA"));
				if (null == ficha.getCodigo_medico()) {
					ficha.setCodigo_medico("");
				}
				ficha.setMedico(rsCabecera.getString("NOMBRE_DOC"));
				if (null == ficha.getMedico()) {
					ficha.setMedico("");
				}
				ficha.setTelefono_medico(rsCabecera.getString("TELEFONO_DOC"));
				if (null == ficha.getTelefono_medico()) {
					ficha.setTelefono_medico("");
				}
				ficha_taller.getLista_cabeceras().add(ficha);
			}	
			
					
			while(rsLineas.next()){
				log.debug("FichaTecnicaDAOImpl:traeFichataller entrando ciclo while");
				ficha = new FichaTecnicaBean();
				
				ficha.setIdent_linea_armazon(rsLineas.getInt("IDENT"));
				ficha.setGrupo_armazon(String.valueOf(rsLineas.getString("GRUPO")));				
				ficha.setCod_pedvtcb(rsLineas.getString("PEDVTCB"));				
				ficha.setCod_armazon(rsLineas.getString("ARTICULO"));
				ficha.setDescripcion_armazon(rsLineas.getString("DESCRIPCION"));
				ficha.setColor_armazon(rsLineas.getString("COLOR"));
				ficha.setMaterial_armazon(rsLineas.getString("MATERIAL"));
				ficha.setSexo_armazon(rsLineas.getString("SEXO_ARM"));
				ficha.setCalibre_armazon(rsLineas.getString("CALIBRE"));
				ficha.setPuente_armazon(rsLineas.getString("PUENTE"));
				ficha.setAltura_armazon(rsLineas.getString("ALTURA"));
				ficha.setEstilo_armazon(rsLineas.getString("ESTILO"));
				
				
				ficha_taller.getLista_armazones().add(ficha);			
			}
			
			
			
			while(rsGraduacion.next()){
				log.debug("FichaTecnicaDAOImpl:traeFichataller entrando ciclo while");
				ficha = new FichaTecnicaBean();				
					
				ficha.setIdent_linea_cristalD(rsGraduacion.getInt("IDENT_LN"));					
				ficha.setGrupo_cristalD(rsGraduacion.getString("GRUPO"));	
				ficha.setCod_pedvtcb_cristalD(rsGraduacion.getString("PEDVTCB"));
				ficha.setCod_critalD(rsGraduacion.getString("ARTICULO"));
				ficha.setDescripcion_critalD(rsGraduacion.getString("DESCRIPCION"));
				ficha.setOjo_cristalD(rsGraduacion.getString("OJO"));
				ficha.setEsMultioferta(rsGraduacion.getString("ESMULTI"));
				
				if(null != rsGraduacion.getString("ESFERA")){
					ficha.setEsfera_cristalD(new BigDecimal(rsGraduacion.getString("ESFERA")).toString());
				}else{
					ficha.setEsfera_cristalD(Constantes.STRING_BLANCO);
				}				
				
				
				if(null != rsGraduacion.getString("CILINDRO")){
					ficha.setCilindro_cristalD(new BigDecimal(rsGraduacion.getString("CILINDRO")).toString());
				}else{
					ficha.setCilindro_cristalD(Constantes.STRING_BLANCO);
				}
				
				
				if(null != rsGraduacion.getString("EJE")){
					ficha.setEje_critalD(new BigDecimal(rsGraduacion.getString("EJE")).toString());
				}else{
					
				}
				
				
				if(null != rsGraduacion.getString("OD_DNPL")){
					ficha.setDnpl_critalD(new BigDecimal(rsGraduacion.getString("OD_DNPL")).toString());
				}else{
					ficha.setDnpl_critalD(Constantes.STRING_BLANCO);
				}
				
								
				if(null != rsGraduacion.getString("OD_DNPC")){
					ficha.setDnpc_critalD(new BigDecimal(rsGraduacion.getString("OD_DNPC")).toString());
				}else{
					ficha.setDnpc_critalD(Constantes.STRING_BLANCO);
				}
				
				
				if(null != rsGraduacion.getString("OD_ADICION")){
					ficha.setAdicion_critalD(new BigDecimal(rsGraduacion.getString("OD_ADICION")).toString());
				}else{
					ficha.setAdicion_critalD(Constantes.STRING_BLANCO);
				}
				
				
				ficha.setColor_cristalD(rsGraduacion.getString("COLOR"));
				ficha.setTipo_cristalD(rsGraduacion.getString("TIPO"));
				ficha.setInd_cristalD(rsGraduacion.getString("INDICE_REF"));
				ficha.setDiametro_cristalD(rsGraduacion.getString("DIAMETRO"));	
				
				//ficha.setTrat_cristalD(rsGraduacion.getString("TRATAMIENTO"));
				//ficha.setSuplemento_cristaD(rsGraduacion.getString("DESCR_TRATAMI"));
				//ficha.setValor_suple_cristalD(rsGraduacion.getString("VALOR_TRATAMI"));
				ficha.setTrat_cristalD(Constantes.STRING_BLANCO);
				ficha.setSuplemento_cristaD(Constantes.STRING_BLANCO);
				ficha.setValor_suple_cristalD(Constantes.STRING_BLANCO);	
				
				
				if(null != rsGraduacion.getString("OI_DNPL")){
					ficha.setDnpl_critalI(new BigDecimal(rsGraduacion.getString("OI_DNPL")).toString());
				}else{
					ficha.setDnpl_critalI(Constantes.STRING_BLANCO);
				}
				
				
				if(null != rsGraduacion.getString("OI_DNPC")){
					ficha.setDnpc_critalI(new BigDecimal(rsGraduacion.getString("OI_DNPC")).toString());
				}else{
					ficha.setDnpc_critalI(Constantes.STRING_BLANCO);
				}
				
				ficha.setAdicion_critalI(rsGraduacion.getString("OI_ADICICION"));
				
				ficha_taller.getLista_cristales().add(ficha);				
			}
			
			
			
		}catch(Exception ex){
			log.error("FichaTecnicaDAOImpl:traeFichataller error controlado",ex);
		}finally {
            try{
                if (null !=  rsCabecera){
                	log.warn("FichaTecnicaDAOImpl:traeFichataller cierre ResultSet");
                	rsCabecera.close();
                }
                if (null !=  rsLineas){
                	log.warn("FichaTecnicaDAOImpl:traeFichataller cierre ResultSet");
                	rsLineas.close();
                }
                if (null !=  rsGraduacion){
                	log.warn("FichaTecnicaDAOImpl:traeFichataller cierre ResultSet");
                	rsGraduacion.close();
                }
                if (null !=  cs){
                	log.warn("FichaTecnicaDAOImpl:traeFichataller cierre CallableStatement");
             	   cs.close();
                }  
                if (null !=  con){
                	log.warn("FichaTecnicaDAOImpl:traeFichataller cierre Connection");
             	   con.close();
                }  
            }catch(SQLException e){
            	log.error("FichaTecnicaDAOImpl:traeFichataller error", e);
            }
        }		
		
		return ficha_taller;
	}

	
}
