package cl.gmo.pos.venta.reporte.nuevo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import cl.gmo.pos.venta.reportes.CreaReportes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaPedidoFacade;
import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;
import cl.gmo.pos.venta.web.forms.CopiaGuiaLineaBoletaForm;


public class CreaCopiaGuiaBoletaHelper extends Utils {
	
	 Logger log = Logger.getLogger( this.getClass() );	
		
	 public byte[] traeCopiaGuiaBoleta(String numero, String tipo){
		 
		 byte[] bytes=null;
		 
		 try {
			 
			 log.info("CreaCopiaGuiaBoletaHelper:traeCopiaGuiaBoleta inicio");
			 CopiaGuiaBoletaForm copiaGuiaBoletas = PosUtilesFacade.traeCopiaGuiaBoleta(numero, tipo);
			 InputStream io = ReportesHelper.class.getResourceAsStream("copiaBoletaGuia.jasper");
			 
			 //ordena los productos y verifica si hay multiofertas
			 ArrayList<CopiaGuiaLineaBoletaForm> productos = new ArrayList<CopiaGuiaLineaBoletaForm>();
			 ArrayList<ProductosBean> lista_prod_mul;
			 for (CopiaGuiaLineaBoletaForm lineas : copiaGuiaBoletas.getLineas()) {
				 String codigoDescripcion  = lineas.getCodigo() + " " + lineas.getDescripcion();
				 lineas.setDescripcion(codigoDescripcion);
				 productos.add(lineas);
				 lista_prod_mul = new ArrayList<ProductosBean>();
				if (null != lineas.getMultioferta() && lineas.getMultioferta()!= Constantes.STRING_BLANCO)
				{
					ProductosBean prod = new ProductosBean();
					prod.setCodigoMultioferta(lineas.getMultioferta());
					
					PosVentaPedidoFacade.traeMultiofertaLn(prod, lista_prod_mul);
				}
				if ( null != lista_prod_mul && lista_prod_mul.size()>0) {
					
					for (ProductosBean produc : lista_prod_mul) {
						CopiaGuiaLineaBoletaForm lin = new CopiaGuiaLineaBoletaForm();
						lin.setDescripcion(produc.getCod_barra() + " " + produc.getDescripcion());
						lin.setCantidad(String.valueOf(produc.getCantidad()));
						lin.setPrecio("0");
						lin.setDescuento("0");
						lin.setTotal("0");
						lin.setEncargo(lineas.getEncargo());
						lin.setCodigo("PROM " + lineas.getCodigo() + ":");
						productos.add(lin);
					}
					
				}
				
			} 
			 
			 copiaGuiaBoletas.setLineas(productos);
			 
			 Map<String, String> parametros = new HashMap<String, String>();
			 parametros.put(Constantes.STRING_REPORTER_FECHA,this.formatoFechaString(copiaGuiaBoletas.getFecha()));
			 parametros.put(Constantes.STRING_REPORTER_RUT, copiaGuiaBoletas.getRut());
			 parametros.put(Constantes.STRING_ACTION_NUMERO_ALBARAN, copiaGuiaBoletas.getNumeroAlbaran());
			 parametros.put(Constantes.STRING_REPORTER_FECHA_PEDIDO,this.formatoFechaString(copiaGuiaBoletas.getFechaPedido()));
			 parametros.put(Constantes.STRING_FECHA_ENTREGA, this.formatoFechaString(copiaGuiaBoletas.getFechaEntrega()));
			 parametros.put(Constantes.STRING_REPORTER_HORA,copiaGuiaBoletas.getHora());
			 parametros.put(Constantes.STRING_REPORTER_TIENDA,copiaGuiaBoletas.getTienda());
			 parametros.put(Constantes.STRING_REPORTER_VENDEDOR, copiaGuiaBoletas.getVendedor());
			 parametros.put(Constantes.STRING_REPORTER_NUMERO_BOLETA, numero);
			 parametros.put(Constantes.STRING_REPORTER_TOTAL_VENTA, this.formatoValoresReportes(Integer.parseInt(copiaGuiaBoletas.getTotalVenta())));
			 parametros.put(Constantes.STRING_REPORTER_ANTICIPO, Constantes.STRING_CERO);
			
			 if("ENCARGO".equalsIgnoreCase(copiaGuiaBoletas.getTipoVenta())){
				 int pendiente = 0;
				 if(-1 != this.isEntero(copiaGuiaBoletas.getTotalVenta()) && -1 != this.isEntero(copiaGuiaBoletas.getTotalAnticipo())){
					 pendiente = this.isEntero(copiaGuiaBoletas.getTotalVenta()) - this.isEntero(copiaGuiaBoletas.getTotalAnticipo());
				 }				  
				 parametros.put("textoPendiente", "Pendiente");
				 parametros.put(Constantes.STRING_REPORTER_PENDIENTE, String.valueOf(pendiente));
				 parametros.put("textoTipoVenta", "N° Encargo");
			 }else{
				 if("ALBARAN".equalsIgnoreCase(copiaGuiaBoletas.getTipoVenta())){
					 parametros.put("textoPendiente", "");
					 parametros.put(Constantes.STRING_REPORTER_PENDIENTE, "");
					 parametros.put("textoTipoVenta", "N° Albaran");
				 }else{
					 if("GUIA".equalsIgnoreCase(copiaGuiaBoletas.getTipoVenta())){
						 int pendiente = 0;
						 if(-1 != this.isEntero(copiaGuiaBoletas.getTotalVenta()) && -1 != this.isEntero(copiaGuiaBoletas.getTotalAnticipo())){
							 pendiente = this.isEntero(copiaGuiaBoletas.getTotalVenta()) - this.isEntero(copiaGuiaBoletas.getTotalAnticipo());
						 }				  
						 parametros.put("textoPendiente", "Pendiente");
						 parametros.put(Constantes.STRING_REPORTER_PENDIENTE, String.valueOf(pendiente));
						 parametros.put("textoTipoVenta", "N° Encargo");
					 }
				 }
			 }
			 
			 parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGADO, this.formatoValoresReportes(Integer.parseInt(copiaGuiaBoletas.getTotalVenta())));
			 parametros.put(Constantes.STRING_REPORTER_CAJA, copiaGuiaBoletas.getCaja());
			 parametros.put(Constantes.STRING_REPORTER_CLIENTE, copiaGuiaBoletas.getCliente());
			 parametros.put(Constantes.STRING_REPORTER_TIPO, copiaGuiaBoletas.getTipo());
			 
			
			 
			if("".equals(copiaGuiaBoletas.getTotalAnticipo())||null==copiaGuiaBoletas.getTotalAnticipo()){
				 bytes = new CreaReportes().obtenerJasper(parametros, io,copiaGuiaBoletas.getLineas());
			}else{
				if(Integer.parseInt(copiaGuiaBoletas.getTotalVenta())==Integer.parseInt(copiaGuiaBoletas.getTotalAnticipo())){
					bytes = new CreaReportes().obtenerJasper(parametros, io,copiaGuiaBoletas.getLineas());
				}else{
					int pendiente = Integer.parseInt(copiaGuiaBoletas.getTotalVenta())-Integer.parseInt(copiaGuiaBoletas.getTotalAnticipo());
					parametros.put(Constantes.STRING_REPORTER_ANTICIPO,  this.formatoValoresReportes(Integer.parseInt(copiaGuiaBoletas.getTotalAnticipo())));
					//parametros.put(Constantes.STRING_REPORTER_TXT_PENDIENTE, "Pendiente");
					parametros.put(Constantes.STRING_REPORTER_PENDIENTE, this.formatoValoresReportes(pendiente));
					 parametros.put(Constantes.STRING_REPORTER_TOTAL_PAGADO, this.formatoValoresReportes(Integer.parseInt(copiaGuiaBoletas.getTotalPagadoBoleta())));
 					copiaGuiaBoletas.deleteLineas();
 
					bytes = new CreaReportes().obtenerJasper(parametros, io,copiaGuiaBoletas.getLineas());
				}
				 
			}
			 
			 //byte[] bytes = new CreaReportes().obtenerJasperSinPar(parametros, io,new JREmptyDataSource());
		 
					/*response.setContentType(Constantes.STRING_REPORTER_APPLICATION_PDF);
					response.setContentLength(bytes.length);
					response.setHeader(Constantes.STRING_REPORTER_CONTENT_DISPOSITION, Constantes.STRING_REPORTER_BOLETA_PDF);
					ServletOutputStream servletOutputStream;
					try {
						servletOutputStream = response.getOutputStream();
						servletOutputStream.write(bytes, 0, bytes.length);
						servletOutputStream.flush();
						servletOutputStream.close();
					} catch (IOException e) {
						log.error("CreaCopiaGuiaBoletaHelper:traeCopiaGuiaBoleta error catch",e);
					}*/
			 
			 
		 } catch (Exception e) {
			 log.error("CreaCopiaGuiaBoletaHelper:traeCopiaGuiaBoleta error catch",e);
		}		 
		 
		return bytes; 		
	 }
	 
	 
	public boolean valida_documento(String numero, String tipo) {
		// TODO Apéndice de método generado automáticamente
		boolean estado = false;
		try {
			log.info("CreaCopiaGuiaBoletaHelper:valida_documento inicio");
			  estado = PosUtilesFacade.validaCopiaGuiaBoleta(numero, tipo);
			log.info("CreaCopiaGuiaBoletaHelper:valida_documento fin");
		} catch (Exception e) {
			log.error("CreaCopiaGuiaBoletaHelper:valida_documento error catch",e);
		}
			return estado;
		
	}
	 
	 
}
