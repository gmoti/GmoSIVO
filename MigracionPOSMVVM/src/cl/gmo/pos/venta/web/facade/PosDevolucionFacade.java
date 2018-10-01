package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.DevolucionDAOImpl;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.forms.DevolucionForm;

public class PosDevolucionFacade {
	
	
		
	public static DevolucionBean traeDevoluciones(String numeroDocumento,
		String tipoDocumento)throws Exception{
		
		DevolucionDAOImpl dev = new DevolucionDAOImpl();
		DevolucionBean devolucion = new DevolucionBean();
		try {
			devolucion = dev.traeDevolucion(numeroDocumento, tipoDocumento);
		} catch (Exception e){
            e.printStackTrace();
            throw new Exception("PosGraduacionesFacade: traeUltimaGraduacionCliente");
        }
		return devolucion;
	}

	
	public static String  traeCodigoDevolucion(String local) throws Exception{
		
		DevolucionDAOImpl dev = new DevolucionDAOImpl();
		String codigo=Constantes.STRING_BLANCO;
		try {
			codigo = dev.traeCodigoDevolucion(local);
		} catch (Exception e){
            e.printStackTrace();
            throw new Exception("PosGraduacionesFacade: traeUltimaGraduacionCliente");
        }
		
		return  codigo;
	}
	
	public static DevolucionBean  realizaDevolucion(int boleta, String tipoDocumento, String tipoMotivo, String fecha, String tipoDevo, String cdg_alb, String local, String serie, int numero_cab, String codigo_cliente, String agente){
		boolean respuesta =false; 
		DevolucionDAOImpl dev = new DevolucionDAOImpl();
		DevolucionBean respDevo = new DevolucionBean();
		try{
			respDevo = dev.realizaDevolucion(boleta, tipoDocumento, tipoMotivo, fecha, tipoDevo, cdg_alb, local, serie, numero_cab, codigo_cliente, agente);
		}catch(Exception ex){
			 ex.printStackTrace();
			 respuesta =false;
		}		 
		return respDevo;
	 }
	
	public static void realizaDevolucionMulti(int boleta, String tipoDocumento, String tipoMotivo, String fecha, String tipoDevo, String cdg_alb){
		DevolucionDAOImpl dev = new DevolucionDAOImpl();
		try{
			 dev.realizaDevolucionMulti(boleta, tipoDocumento, tipoMotivo, fecha, tipoDevo, cdg_alb);
		}catch(Exception ex){
			 ex.printStackTrace();
		}
	}
	
	public static ArrayList<AlbaranBean> buscarAlbaranes(String cdg, String nif, String fecha, String agente, String local){
		 
		 ArrayList<AlbaranBean> lista_albaranes = null;
		 DevolucionDAOImpl dev = new DevolucionDAOImpl();
		 try{
			 
			 lista_albaranes = dev.buscarAlbaranes(cdg, nif, fecha, agente, local);
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return lista_albaranes;
	 }
	
	public static AlbaranBean traeAlbaran(String cdg,String fecha, String agente, String local){
		DevolucionDAOImpl dev = new DevolucionDAOImpl();	 
		AlbaranBean alb= null;
		 try{
			 
			 alb = dev.traeAlbaran(cdg, local);
			 
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return alb;
	 }


	public static boolean ValidaAutorizacionKodak(String usuario) {
		DevolucionDAOImpl dev = new DevolucionDAOImpl();
		boolean estado = false;
	
		 try{
			 estado = dev.ValidaAutorizacionKodak(usuario);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 return estado;
	}	
	
	//LMARIN NOTA DE CREDITO 20150604
	
	public  static String insertaDocumento(String ticket, int documento,
			String tipo_documento, int total,String fecha,String local) 
	{
		 String res ="";
		 DevolucionDAOImpl dev = new DevolucionDAOImpl();
		 try{
			 res = dev.insertaDocumento(ticket, documento, tipo_documento, total, fecha, local);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return res;	
	}
	
	//LMARIN NOTA DE CREDITO 20180209
	
	public  static int validarFechaNC(int numdevo) 
	{
		 int res =0;
		 DevolucionDAOImpl dev = new DevolucionDAOImpl();
		 try{
			 res = dev.validarFechaNC(numdevo);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return res;	
	}
}
