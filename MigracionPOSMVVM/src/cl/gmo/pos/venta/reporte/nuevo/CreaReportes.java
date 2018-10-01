package cl.gmo.pos.venta.reporte.nuevo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class CreaReportes {
	Logger log = Logger.getLogger( this.getClass() );
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] obtenerJasper( Map parametros, InputStream io, Collection  data)  {
		log.info("CreaReportes:obtenerJasper  inicio");
		JasperReport archivoReporte = null;
		byte[] bytes = null;
		try{
			
			JRBeanCollectionDataSource articulosDS = new JRBeanCollectionDataSource(data);
			archivoReporte = (JasperReport) JRLoader.loadObject(io);
			JasperPrint jp0 = JasperFillManager.fillReport(archivoReporte,  parametros, articulosDS );
			List<JasperPrint> lr = new ArrayList<JasperPrint>();
			lr.add(jp0);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			
			JRPdfExporter p = new JRPdfExporter();
			p.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, lr);
			p.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, "");
			p.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, out);			
			p.exportReport();
			
			out.close();
			bytes = out.toByteArray();			
			
			
		}catch(Exception e) {
			log.error("VentaPedidoDispatchActions:CreaReportes  error catch",e);
		 }
		log.info("CreaReportes:obtenerJasper  fin");
		 return bytes;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] obtenerJasperSinPar( Map parametros, InputStream io, JREmptyDataSource data)  {
		log.info("CreaReportes:obtenerJasperSinPar  inicio");
		JasperReport archivoReporte = null;
		byte[] bytes = null;
		try{
			
			archivoReporte = (JasperReport) JRLoader.loadObject(io);
			JasperPrint jp0 = JasperFillManager.fillReport(archivoReporte,  parametros, data );
			List<JasperPrint> lr = new ArrayList<JasperPrint>();
			lr.add(jp0);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			
			JRPdfExporter p = new JRPdfExporter();
			p.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, lr);
			p.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, "");
			p.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, out);
			p.exportReport();
	

			out.close();
			bytes = out.toByteArray();	
			
			
			
			
		}catch(Exception e) {
			log.error("VentaPedidoDispatchActions:obtenerJasperSinPar  error catch",e);
		 }
		
		 return bytes;
	}

	public byte[] obtenerJasperNuevo(Map parametros, InputStream io) {
		log.info("CreaReportes:obtenerJasper  inicio");
		JasperReport archivoReporte = null;
		byte[] bytes = null;
		try{
			
			Connection jdbcConnection = null;
			JasperPrint jp0 = null;
			
			
			jdbcConnection = ConexionFactory.INSTANCE.getConexion();
				try {
					archivoReporte = (JasperReport) JRLoader.loadObject(io);
				} catch (JRException e1) {
					// TODO Bloque catch generado automáticamente
					e1.printStackTrace();
				}
				
				

			try {
				jp0 = JasperFillManager.fillReport(archivoReporte, parametros, jdbcConnection);
			} catch (JRException e1) {
				// TODO Bloque catch generado automáticamente
				e1.printStackTrace();
			}
			List<JasperPrint> lr = new ArrayList<JasperPrint>();
			lr.add(jp0);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			JRPdfExporter p = new JRPdfExporter();
			p.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, lr);
			p.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, "");
			p.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, out);
			p.exportReport();
			
			out.close();
			bytes = out.toByteArray();	
			
			
			
			
		}catch(Exception e) {
			log.error("VentaPedidoDispatchActions:CreaReportes  error catch",e);
		 }
		log.info("CreaReportes:obtenerJasper  fin");
		 return bytes;
	}
}
