package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.SeleccionPagoDAOImpl;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.PagoBean;

public class PosSeleccionPagoFacade {
	
	 public static String[] validaDocumento(String tipo_documento, int numero_documento, String local) throws Exception
	    {
		 
		 	String resultado[] = new String[6];
	    	try{
		        SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();         
		        resultado = pagoDao.validaDocumento(tipo_documento, local, numero_documento);
		        return resultado;
		        
		    }catch (Exception e){
		        e.printStackTrace();
		        throw new Exception("PosSeleccionPagoFacade: validaDocumento");
		    }
	    }
	    
	 public static ArrayList<PagoBean> traePagos(String codigo_venta, String tipo) throws Exception
	 {
		 try {
			SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
			return  pagoDao.traePagos(codigo_venta, tipo);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosSeleccionPagoFacade: traePagos");
		}
	 }
	 
	 /*
	  * LMARIN 20140708
	  * Se modifica el método para diferenciar distintos caso al modificar formas de pago o anular encargo.
	  */
	 public static String  eliminaFormaPago(String cdg_vta, String fecha_pedido, String f_pago, String local, String fech_pago,String tipo_accion,String autorizador){
		 	SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
		 	String respuesta  = "";
			try{
				
				respuesta = pagoDao.eliminaFormaPago(cdg_vta, fecha_pedido, f_pago, local, fech_pago,tipo_accion,autorizador);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}	
			return respuesta;
	}
	 
	 public static boolean  eliminaFormaPagoAlbaranes(String cdg_vta, String fecha_pedido, String f_pago, String local, String fech_pago, String tipo){
		 	SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
		 	boolean respuesta  = false;
			try{
				
				respuesta = pagoDao.eliminaFormaPagoAlbaranes(cdg_vta, fecha_pedido, f_pago, local, fech_pago, tipo);
				
			}catch(Exception ex){
				ex.printStackTrace();
			}	
			return respuesta;
	}

	public static String TieneDocumentos(String codigo) {
		SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
		String tiene = Constantes.STRING_BLANCO;
		try {
			tiene =  pagoDao.TieneDocumentos(codigo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tiene;
	}

	public static boolean cambiaFolioDocumento(String cdg, String fecha,
			String tipo_negocio, String documento, int numero_boleta) {
		SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
		boolean estado = false;
		try {
			estado =  pagoDao.cambiaFolioDocumento(cdg, fecha, tipo_negocio, documento, numero_boleta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}
	
	
	
	/*
	 * LMARIN 20150525
	 * retorno datos boleta
	 */
	public static ArrayList<BoletaBean> reimpresionBoletaelec(String fecha,String encargo,String local){
		ArrayList<BoletaBean> boleta = new ArrayList<BoletaBean>();
		SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
		try {
			boleta =  pagoDao.reimpresionBoletaelec(fecha,encargo,local);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boleta;
	}
	
	/*
	 * LMARIN 20141023
	 * Se solicita voucher de comprobación
	 */
	public static String validaDtoCliente(String rut,String convenio){
		String ret = "";
		SeleccionPagoDAOImpl pagoDao = new SeleccionPagoDAOImpl();
		try {
			ret =  pagoDao.validaDtoCliente(rut,convenio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
