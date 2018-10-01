package cl.gmo.pos.venta.reporte.nuevo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import oracle.jdbc.driver.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.ListadoTotalDiaDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ListaTotalDiaBean;
import cl.gmo.pos.venta.web.beans.ListasTotalesDiaBean;

public class ListadoTotalDiaDAOImpl implements ListadoTotalDiaDAO {
	Utils util= new Utils();
	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ListasTotalesDiaBean traeListasTotales(String fecha,String local) throws Exception {
		log.info("ListadoTotalDiaDAOImpl:traeListasTotales inicio");
		ListasTotalesDiaBean listasTotalesDia = new ListasTotalesDiaBean();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;
		ResultSet rs7 = null;
		
			
		try {
			log.info("ListadoTotalDiaDAOImpl:traeListasTotales conectando base datos");
			System.out.println("FECHA ==>"+fecha+"<===> LOCAL ==>"+local);
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_INFORME_SEL_VTA_POR_DIA(?,?,?,?,?,?,?,?)}");
			cs.setString(1, fecha);
			cs.setString(2, local);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			cs.registerOutParameter(7, OracleTypes.CURSOR);
			cs.registerOutParameter(8, Types.NUMERIC);
			cs.execute();
			
			rs3 = (ResultSet) cs.getObject(3);
			rs4 = (ResultSet) cs.getObject(4);
			rs5 = (ResultSet) cs.getObject(5);
			rs6 = (ResultSet) cs.getObject(6);
			rs7 = (ResultSet) cs.getObject(7);
			listasTotalesDia.setNumero_movimientos(cs.getInt(8));
			
			ArrayList<ListaTotalDiaBean> totalVentaDia = new ArrayList<ListaTotalDiaBean>();
			ArrayList<ListaTotalDiaBean> totalEncargoDia = new ArrayList<ListaTotalDiaBean>();
			ArrayList<ListaTotalDiaBean> totalEntregaDia = new ArrayList<ListaTotalDiaBean>();
			ArrayList<ListaTotalDiaBean> totalDevolucionDia = new ArrayList<ListaTotalDiaBean>();
			ArrayList<ListaTotalDiaBean> totalAnticipoDia = new ArrayList<ListaTotalDiaBean>();
			
			 String codigo_0 =Constantes.STRING_BLANCO;
			 String codigo_1 =Constantes.STRING_BLANCO;
			 Integer cantidad_1 = Constantes.INT_CERO;
			 Integer cantidad_0 = Constantes.INT_CERO;
			 String numero_doc = Constantes.STRING_BLANCO;
			 String numero_doc_0 = Constantes.STRING_BLANCO;
			 
			while (rs3.next())
            {
				log.debug("ListadoTotalDiaDAOImpl:traeListasTotales entrando ciclo while");
			  ListaTotalDiaBean listaTMP = new ListaTotalDiaBean();
			  System.out.println("PASO rs3.next()");
			  codigo_1 = rs3.getString("CDG");
				  
			  if (!codigo_0.equals(Constantes.STRING_BLANCO))
			  {  
				 
				if (codigo_0.equals(codigo_1)) {
					
					numero_doc_0 = rs3.getString("NUMERO");
					
					if (numero_doc.equals(numero_doc_0)) {
						listaTMP.setTipo(Constantes.STRING_BLANCO);
						listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
						listaTMP.setMontoDoc(Constantes.STRING_BLANCO);
						
					}
					else
					{
						listaTMP.setTipo(rs3.getString("TIPO"));
						listaTMP.setNumeroDoc(rs3.getString("NUMERO"));
						listaTMP.setMontoDoc(rs3.getString("IMPORTEDEF"));
					}
					
					  listaTMP.setCodigo(Constantes.STRING_BLANCO);
					  listaTMP.setTipoAgente(Constantes.STRING_BLANCO);
					  listaTMP.setTotal(Constantes.STRING_BLANCO);  
					  listaTMP.setTotal_num(0);
					  	
					  if(null == rs3.getString("CANTIDAD")|| "".equals(rs3.getString("CANTIDAD"))){
						  listaTMP.setCobrado(Constantes.STRING_CERO);  
						  listaTMP.setCobrado_num(0);
					  }else{
						  listaTMP.setCobradoFormat(rs3.getString("CANTIDAD"));
						  listaTMP.setCobrado_num(rs3.getInt("CANTIDAD"));
					  }
					 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
					  listaTMP.setfPagado(rs3.getString("DESCRIPCION"));
					  
					  numero_doc = numero_doc_0;
					  
					  totalVentaDia.add(listaTMP);
					  
				}
				else
				{
					numero_doc = rs3.getString("NUMERO");
					codigo_0 = rs3.getString("CDG");
					
					  listaTMP.setCodigo(rs3.getString("CDG"));
					  listaTMP.setTipoAgente(rs3.getString("AGENTE"));
					  listaTMP.setTipo(rs3.getString("TIPO"));
					  listaTMP.setNumeroDoc(rs3.getString("NUMERO"));
					  
					  if(null == rs3.getString("IMPORTE") || "".equals(rs3.getString("IMPORTE"))){
						  listaTMP.setTotal(Constantes.STRING_BLANCO);  
						  listaTMP.setTotal_num(0);
					  }else{
						  listaTMP.setTotalFormat(String.valueOf((int)Math.rint(rs3.getDouble("IMPORTE"))));
						  listaTMP.setTotal_num(rs3.getInt("IMPORTE"));
					  }
					  	
					  if(null == rs3.getString("CANTIDAD")|| "".equals(rs3.getString("CANTIDAD"))){
						  listaTMP.setCobrado(Constantes.STRING_CERO);  
						  listaTMP.setCobrado_num(0);
					  }else{
						  listaTMP.setCobradoFormat(rs3.getString("CANTIDAD"));
						  listaTMP.setCobrado_num(rs3.getInt("CANTIDAD"));
					  }
					 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
					  listaTMP.setfPagado(rs3.getString("DESCRIPCION"));
					  listaTMP.setMontoDoc(rs3.getString("IMPORTEDEF"));
					  
					  totalVentaDia.add(listaTMP);
				}
			  }
			  else
			  {
				  numero_doc = rs3.getString("NUMERO");
				  codigo_0 = rs3.getString("CDG");
				  
				  listaTMP.setCodigo(rs3.getString("CDG"));
				  listaTMP.setTipoAgente(rs3.getString("AGENTE"));
				  listaTMP.setTipo(rs3.getString("TIPO"));
				  listaTMP.setNumeroDoc(rs3.getString("NUMERO"));
				  
				  if(null == rs3.getString("IMPORTE") || "".equals(rs3.getString("IMPORTE"))){
					  listaTMP.setTotal(Constantes.STRING_CERO);  
					  listaTMP.setTotal_num(0);
				  }else{
					  listaTMP.setTotalFormat(String.valueOf((int)Math.rint(rs3.getDouble("IMPORTE"))));
					  listaTMP.setTotal_num(rs3.getInt("IMPORTE"));
				  }
				  	
				  if(null == rs3.getString("CANTIDAD")|| "".equals(rs3.getString("CANTIDAD"))){
					  listaTMP.setCobrado(Constantes.STRING_CERO);  
					  listaTMP.setCobrado_num(0);
				  }else{
					  listaTMP.setCobradoFormat(rs3.getString("CANTIDAD"));
					  listaTMP.setCobrado_num(rs3.getInt("CANTIDAD"));
				  }
				 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
				  listaTMP.setfPagado(rs3.getString("DESCRIPCION"));
				  listaTMP.setMontoDoc(rs3.getString("IMPORTEDEF"));
				  
				  totalVentaDia.add(listaTMP);
				  
			  }
			  
            }
			
			
			codigo_0 =Constantes.STRING_BLANCO;
			codigo_1 =Constantes.STRING_BLANCO;
			numero_doc = Constantes.STRING_BLANCO;
			numero_doc_0 = Constantes.STRING_BLANCO;
			 
			while (rs4.next())
            {
				
				log.debug("ListadoTotalDiaDAOImpl:traeListasTotales entrando ciclo while");
			  ListaTotalDiaBean listaTMP = new ListaTotalDiaBean();
			  System.out.println("PASO rs4.next()");

			  codigo_1 = rs4.getString("CDG");
			  cantidad_1 = rs4.getInt("CANTIDAD_PAGOS");
			  
			  
			  if (null != codigo_0 && !codigo_0.equals(Constantes.STRING_BLANCO))
			  {  
				 
				if (codigo_0.equals(codigo_1)) {
					
					numero_doc_0 = rs4.getString("NUMERO");
					
					if (numero_doc.equals(numero_doc_0)) {
						listaTMP.setTipo(Constantes.STRING_BLANCO);
						listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
						listaTMP.setMontoDoc(Constantes.STRING_BLANCO);
						
					}
					else
					{
						listaTMP.setTipo(rs4.getString("TIPO"));
						listaTMP.setNumeroDoc(rs4.getString("NUMERO"));
						listaTMP.setMontoDoc(rs4.getString("IMPORTEDEF"));
					}
					
					  listaTMP.setCodigo(Constantes.STRING_BLANCO);
					  listaTMP.setTipoAgente(Constantes.STRING_BLANCO);
					  listaTMP.setTotal(Constantes.STRING_BLANCO);  
					  listaTMP.setTotal_num(0);
					  	
					  if(null == rs4.getString("CANTIDAD")|| "".equals(rs4.getString("CANTIDAD"))){
						  listaTMP.setCobrado(Constantes.STRING_CERO);  
						  listaTMP.setCobrado_num(0);
					  }else{
						  listaTMP.setCobradoFormat(rs4.getString("CANTIDAD"));
						  listaTMP.setCobrado_num(rs4.getInt("CANTIDAD"));
					  }
					 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
					  listaTMP.setfPagado(rs4.getString("DESCRIPCION"));
					  
					  numero_doc = numero_doc_0;
					  
					  
					  if (listaTMP.getMontoDoc().equals(Constantes.STRING_CERO)) { listaTMP.setMontoDoc(Constantes.STRING_BLANCO);}
					  if (listaTMP.getNumeroDoc().equals(Constantes.STRING_CERO)) { 
						  listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
						  listaTMP.setTipo(Constantes.STRING_BLANCO);}
					  if (listaTMP.getCobrado().equals(Constantes.STRING_CERO)) { listaTMP.setCobrado(Constantes.STRING_BLANCO);}
					  if (listaTMP.getTotal().equals(Constantes.STRING_CERO)) { listaTMP.setTotal(Constantes.STRING_BLANCO);}
					  
					  totalEncargoDia.add(listaTMP);
					  
				}
				else
				{
					
					//cuando es distinto el codigo quiere decir que es un encargo nuevo, por lo tanto trae las boletas del encargo anterior
					if (cantidad_0 > 1)
					{
						Utils util = new Utils();
						ArrayList<BoletaBean> boletas = util.traeListaBoletasFechas(codigo_0, fecha);
						
						
						for (BoletaBean boletaBean : boletas) {
							ListaTotalDiaBean listaTMP_b = new ListaTotalDiaBean();
							
							listaTMP_b.setNumeroDoc(String.valueOf(boletaBean.getNumero()));
							listaTMP_b.setTipo(boletaBean.getTipo());
							listaTMP_b.setMontoDoc(String.valueOf(boletaBean.getImporte()));
							
							totalEncargoDia.add(listaTMP_b);
						}
						//fin boletas encargo anterior
					}
					
				
				
					numero_doc = rs4.getString("NUMERO");
					codigo_0 = rs4.getString("CDG");
					cantidad_0 = rs4.getInt("CANTIDAD_PAGOS");
					
					
					  listaTMP.setCodigo(rs4.getString("CDG"));
					  listaTMP.setTipoAgente(rs4.getString("AGENTE"));
					  listaTMP.setTipo(rs4.getString("TIPO"));
					  listaTMP.setNumeroDoc(rs4.getString("NUMERO"));
					  
					  if(null == rs4.getString("IMPORTE") || "".equals(rs4.getString("IMPORTE"))){
						  listaTMP.setTotal(Constantes.STRING_BLANCO);  
						  listaTMP.setTotal_num(0);
					  }else{
						  listaTMP.setTotalFormat(String.valueOf((int)Math.rint(rs4.getDouble("IMPORTE"))));
						  listaTMP.setTotal_num(rs4.getInt("IMPORTE"));
					  }
					  	
					  if(null == rs4.getString("CANTIDAD")|| "".equals(rs4.getString("CANTIDAD"))){
						  listaTMP.setCobrado(Constantes.STRING_CERO);  
						  listaTMP.setCobrado_num(0);
					  }else{
						  listaTMP.setCobradoFormat(rs4.getString("CANTIDAD"));
						  listaTMP.setCobrado_num(rs4.getInt("CANTIDAD"));
					  }
					 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
					  listaTMP.setfPagado(rs4.getString("DESCRIPCION"));
					  
					  if(null != rs4.getString("IMPORTEDEF")){
						  listaTMP.setMontoDoc(rs4.getString("IMPORTEDEF")); 
					  }else{
						  listaTMP.setMontoDoc("");
					  }
					 
					  
					  if (cantidad_0 == 1) {
						  	Utils util = new Utils();
							ArrayList<BoletaBean> boletas_1 = util.traeListaBoletasFechas(codigo_0, fecha);
							
							
							for (BoletaBean boletaBean : boletas_1) {
								
								listaTMP.setNumeroDoc(String.valueOf(boletaBean.getNumero()));
								listaTMP.setTipo(boletaBean.getTipo());
								listaTMP.setMontoDoc(String.valueOf(boletaBean.getImporte()));
							}
					  }
					  else
					  {
						  if (listaTMP.getMontoDoc().equals(Constantes.STRING_CERO)) { listaTMP.setMontoDoc(Constantes.STRING_BLANCO);}
						  if (listaTMP.getNumeroDoc().equals(Constantes.STRING_CERO)) { 
							  listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
							  listaTMP.setTipo(Constantes.STRING_BLANCO);}
						  if (listaTMP.getCobrado().equals(Constantes.STRING_CERO)) { listaTMP.setCobrado(Constantes.STRING_BLANCO);}
						  if (listaTMP.getTotal().equals(Constantes.STRING_CERO)) { listaTMP.setTotal(Constantes.STRING_BLANCO);}
					  }
					  
					  
					  if (listaTMP.getMontoDoc().equals(Constantes.STRING_CERO)) { listaTMP.setMontoDoc(Constantes.STRING_BLANCO);}
					  if (listaTMP.getNumeroDoc().equals(Constantes.STRING_CERO)) { 
						  listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
						  listaTMP.setTipo(Constantes.STRING_BLANCO);}
					  if (listaTMP.getCobrado().equals(Constantes.STRING_CERO)) { listaTMP.setCobrado(Constantes.STRING_BLANCO);}
					  if (listaTMP.getTotal().equals(Constantes.STRING_CERO)) { listaTMP.setTotal(Constantes.STRING_BLANCO);}
					  
					  totalEncargoDia.add(listaTMP);
					}
			  }
			  else
			  {
				  numero_doc = rs4.getString("NUMERO");
				  codigo_0 = rs4.getString("CDG");
				  cantidad_0 = rs4.getInt("CANTIDAD_PAGOS");
				  
				  listaTMP.setCodigo(rs4.getString("CDG"));
				  listaTMP.setTipoAgente(rs4.getString("AGENTE"));
				  listaTMP.setTipo(rs4.getString("TIPO"));
				  listaTMP.setNumeroDoc(rs4.getString("NUMERO"));
				  
				  if(null == rs4.getString("IMPORTE") || "".equals(rs4.getString("IMPORTE"))){
					  listaTMP.setTotal(Constantes.STRING_CERO);  
					  listaTMP.setTotal_num(0);
				  }else{
					  listaTMP.setTotalFormat(String.valueOf((int)Math.rint((rs4.getDouble("IMPORTE")))));
					  listaTMP.setTotal_num(rs4.getInt("IMPORTE"));
				  }
				  	
				  if(null == rs4.getString("CANTIDAD")|| "".equals(rs4.getString("CANTIDAD"))){
					  listaTMP.setCobrado(Constantes.STRING_CERO);  
					  listaTMP.setCobrado_num(0);
				  }else{
					  listaTMP.setCobradoFormat(rs4.getString("CANTIDAD"));
					  listaTMP.setCobrado_num(rs4.getInt("CANTIDAD"));
				  }
				 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
				  listaTMP.setfPagado(rs4.getString("DESCRIPCION"));
				  if(null != rs4.getString("IMPORTEDEF")){
					  listaTMP.setMontoDoc(rs4.getString("IMPORTEDEF")); 
				  }else{
					  listaTMP.setMontoDoc("");
				  }
				  
				  
				  if (cantidad_0 == 1) {
					  
					  Utils util = new Utils();
						ArrayList<BoletaBean> boletas = util.traeListaBoletasFechas(codigo_0, fecha);
												
						for (BoletaBean boletaBean : boletas) {
							
							listaTMP.setNumeroDoc(String.valueOf(boletaBean.getNumero()));
							listaTMP.setTipo(boletaBean.getTipo());
							listaTMP.setMontoDoc(String.valueOf(boletaBean.getImporte()));
						}
				  }
				  else
				  {
					  if (listaTMP.getMontoDoc().equals(Constantes.STRING_CERO)) { listaTMP.setMontoDoc(Constantes.STRING_BLANCO);}
					  if (listaTMP.getNumeroDoc().equals(Constantes.STRING_CERO)) { 
						  listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
						  listaTMP.setTipo(Constantes.STRING_BLANCO);}
					  if (listaTMP.getCobrado().equals(Constantes.STRING_CERO)) { listaTMP.setCobrado(Constantes.STRING_BLANCO);}
					  if (listaTMP.getTotal().equals(Constantes.STRING_CERO)) { listaTMP.setTotal(Constantes.STRING_BLANCO);}
				  }
				  
				  totalEncargoDia.add(listaTMP);
				  
			  }
            }
			
			if (cantidad_0 > 1){
				//carga los pagos del ultimo registro
				Utils util = new Utils();
				ArrayList<BoletaBean> boletas = util.traeListaBoletas(codigo_1);
				
				
				for (BoletaBean boletaBean : boletas) {
					ListaTotalDiaBean listaTMP_b = new ListaTotalDiaBean();
					
					listaTMP_b.setNumeroDoc(String.valueOf(boletaBean.getNumero()));
					listaTMP_b.setTipo(boletaBean.getTipo());
					listaTMP_b.setMontoDoc(String.valueOf(boletaBean.getImporte()));
					
					totalEncargoDia.add(listaTMP_b);
				}
				//fin boletas pagos del ultimo registro
			}
			while (rs5.next())
            {
				log.debug("ListadoTotalDiaDAOImpl:traeListasTotales entrando ciclo while");
			  ListaTotalDiaBean listaTMP = new ListaTotalDiaBean();
			  System.out.println("PASO rs5.next()");

			  listaTMP.setCodigo(rs5.getString("CDG"));
			  listaTMP.setTipoAgente(rs5.getString("AGENTE"));
			  listaTMP.setTotalFormat(String.valueOf((int)Math.rint((rs5.getDouble("ANTICIPO")))));

			  
			  totalEntregaDia.add(listaTMP);
            }
			codigo_0 =Constantes.STRING_BLANCO;
			codigo_1 =Constantes.STRING_BLANCO;
			while (rs6.next())
            {
				log.debug("ListadoTotalDiaDAOImpl:traeListasTotales entrando ciclo while");
				codigo_0=rs6.getString("CDG").trim();
			  ListaTotalDiaBean listaTMP = new ListaTotalDiaBean();
			  System.out.println("PASO rs6.next()");

			  if (codigo_0.equals(codigo_1)) {
				  listaTMP.setCodigo(Constantes.STRING_BLANCO);
				  listaTMP.setTipoAgente(Constantes.STRING_BLANCO);
				  if(null != rs6.getString("COBRADO")){
					  listaTMP.setCobradoFormat(rs6.getString("COBRADO"));
				  }else{
					  listaTMP.setCobradoFormat("");
				  }
				  
				  listaTMP.setTotalFormat(Constantes.STRING_BLANCO);			  
				  listaTMP.setCobrado_num(rs6.getInt("COBRADO"));
				  listaTMP.setTotal_num(Constantes.INT_CERO);
				  if(null != rs6.getString("DESCRIPCION")){
					  listaTMP.setfPagado(rs6.getString("DESCRIPCION")); 
				  }else{
					  listaTMP.setfPagado("");
				  }
				  
				  codigo_1=rs6.getString("CDG").trim();
			  }
			  else
			  {
				  listaTMP.setCodigo(rs6.getString("CDG"));
				  listaTMP.setTipoAgente(rs6.getString("AGENTE"));
				  if(null != rs6.getString("COBRADO")){
					  listaTMP.setCobradoFormat(rs6.getString("COBRADO"));
				  }else{
					  listaTMP.setCobradoFormat("");
				  }
				  
				  listaTMP.setTotalFormat(rs6.getString("CANTIDAD"));			  
				  listaTMP.setCobrado_num(rs6.getInt("COBRADO"));
				  listaTMP.setTotal_num(rs6.getInt("CANTIDAD"));
				  if(null != rs6.getString("DESCRIPCION")){
					  listaTMP.setfPagado(rs6.getString("DESCRIPCION")); 
				  }else{
					  listaTMP.setfPagado("");
				  }
				  
				  codigo_1=rs6.getString("CDG").trim();
				  
			  }
			  listaTMP.setTipo(rs6.getString("TIPO"));
			  listaTMP.setNumeroDoc(rs6.getString("NUMERO"));
			  listaTMP.setMontoDoc(rs6.getString("IMPORTE"));
			  
			  totalDevolucionDia.add(listaTMP);
            }
			
			codigo_0 =Constantes.STRING_BLANCO;
			codigo_1 =Constantes.STRING_BLANCO;
			numero_doc = Constantes.STRING_BLANCO;
			numero_doc_0 = Constantes.STRING_BLANCO;
			
			while (rs7.next())
            {
				log.debug("ListadoTotalDiaDAOImpl:traeListasTotales entrando ciclo while");
			  ListaTotalDiaBean listaTMP = new ListaTotalDiaBean();
			  System.out.println("PASO rs7.next()");

			  
			  codigo_1 = rs7.getString("CDG");
			  
			  if (null != codigo_0 && !codigo_0.equals(Constantes.STRING_BLANCO))
			  {  
				 
				if (codigo_0.equals(codigo_1)) {
					
					numero_doc_0 = rs7.getString("NUMERO");
					
					if (numero_doc.equals(numero_doc_0)) {
						listaTMP.setTipo(Constantes.STRING_BLANCO);
						listaTMP.setNumeroDoc(Constantes.STRING_BLANCO);
						listaTMP.setMontoDoc(Constantes.STRING_BLANCO);
						
					}
					else
					{
						listaTMP.setTipo(rs7.getString("TIPO"));
						listaTMP.setNumeroDoc(rs7.getString("NUMERO"));
						listaTMP.setMontoDoc(rs7.getString("IMPORTE"));
					}
					
					  listaTMP.setCodigo(Constantes.STRING_BLANCO);
					  listaTMP.setTipoAgente(Constantes.STRING_BLANCO);
					  listaTMP.setTotal(Constantes.STRING_BLANCO);  
					  listaTMP.setTotal_num(0);
					  	
					  if(null == rs7.getString("CANTIDAD")|| "".equals(rs7.getString("CANTIDAD"))){
						  listaTMP.setCobrado(Constantes.STRING_CERO);  
						  listaTMP.setCobrado_num(0);
					  }else{
						  listaTMP.setCobradoFormat(rs7.getString("CANTIDAD"));
						  listaTMP.setCobrado_num(rs7.getInt("CANTIDAD"));
					  }
					 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
					  listaTMP.setfPagado(rs7.getString("DESCRIPCION"));
					  
					  numero_doc = numero_doc_0;
					  
					  totalAnticipoDia.add(listaTMP);
					  
				}
				else
				{
					numero_doc = rs7.getString("NUMERO");
					codigo_0 = rs7.getString("CDG");
					
					  listaTMP.setCodigo(rs7.getString("CDG"));
					  listaTMP.setTipoAgente(rs7.getString("AGENTE"));
					  listaTMP.setTipo(rs7.getString("TIPO"));
					  listaTMP.setNumeroDoc(rs7.getString("NUMERO"));
					  
					  if(null == rs7.getString("ANTICIPOTOT") || "".equals(rs7.getString("ANTICIPOTOT"))){
						  listaTMP.setTotal(Constantes.STRING_BLANCO);  
						  listaTMP.setTotal_num(0);
					  }else{
						  listaTMP.setTotalFormat(rs7.getString("ANTICIPOTOT"));
						  listaTMP.setTotal_num(rs7.getInt("ANTICIPOTOT"));
					  }
					  	
					  if(null == rs7.getString("CANTIDAD")|| "".equals(rs7.getString("CANTIDAD"))){
						  listaTMP.setCobrado(Constantes.STRING_CERO);  
						  listaTMP.setCobrado_num(0);
					  }else{
						  listaTMP.setCobradoFormat(rs7.getString("CANTIDAD"));
						  listaTMP.setCobrado_num(rs7.getInt("CANTIDAD"));
					  }
					 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
					  listaTMP.setfPagado(rs7.getString("DESCRIPCION"));					 
					  listaTMP.setMontoDoc(rs7.getString("IMPORTE"));
					  
					  totalAnticipoDia.add(listaTMP);
					 }
			  }
			  else
			  {
				  numero_doc = rs7.getString("NUMERO");
				  codigo_0 = rs7.getString("CDG");
				  
				  listaTMP.setCodigo(rs7.getString("CDG"));
				  listaTMP.setTipoAgente(rs7.getString("AGENTE"));
				  listaTMP.setTipo(rs7.getString("TIPO"));
				  listaTMP.setNumeroDoc(rs7.getString("NUMERO"));
				  
				  if(null == rs7.getString("ANTICIPOTOT") || "".equals(rs7.getString("ANTICIPOTOT"))){
					  listaTMP.setTotal(Constantes.STRING_CERO);  
					  listaTMP.setTotal_num(0);
				  }else{
					  listaTMP.setTotalFormat(rs7.getString("ANTICIPOTOT"));
					  listaTMP.setTotal_num(rs7.getInt("ANTICIPOTOT"));
				  }
				  	
				  if(null == rs7.getString("CANTIDAD")|| "".equals(rs7.getString("CANTIDAD"))){
					  listaTMP.setCobrado(Constantes.STRING_CERO);  
					  listaTMP.setCobrado_num(0);
				  }else{
					  listaTMP.setCobradoFormat(rs7.getString("CANTIDAD"));
					  listaTMP.setCobrado_num(rs7.getInt("CANTIDAD"));
				  }
				 // listaTMP.setCobrado(rs3.getString("IMPORTE"));
				  listaTMP.setfPagado(rs7.getString("DESCRIPCION"));
				  listaTMP.setMontoDoc(rs7.getString("IMPORTE"));
				  
				  totalAnticipoDia.add(listaTMP);
				  
			  }
            }
			listasTotalesDia.setListaTotalDiaVenta(totalVentaDia);
			listasTotalesDia.setListaTotalDiaEncargo(totalEncargoDia);
			listasTotalesDia.setListaTotalDiaEntrega(totalEntregaDia);
			listasTotalesDia.setListaTotalDiaDevolucion(totalDevolucionDia);
			listasTotalesDia.setListaTotalDiaAnticipo(totalAnticipoDia);
		//	encabezado_ticket = cs.getString(2);
		} catch (Exception e) {
			log.error("ListadoTotalDiaDAOImpl:traeListasTotales error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_INFORME_SEL_VTA_POR_DIA"); 
		} finally {
            try{
            if(null != rs3){
   				 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
   				 rs3.close();
   			 }
   		     if(null != rs4){
   		    	 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
   				 rs4.close();
   			 }
   		     if(null != rs5){
   				 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
   				 rs5.close();
   			 }
   		     if(null != rs6){
   				 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
   				 rs6.close();
   			 }
   		     if(null != rs7){
   				 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre ResultSet");
   				 rs7.close();
   			 }
             if (null != cs){
            	 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre CallableStatement");
                 cs.close();
             }     
             if (null != con){
            	 log.warn("ListadoTotalDiaDAOImpl:traeListasTotales cierre Connection");
		    	   con.close();
	         } 
			 
         }catch(Exception e){
        	 log.error("ListadoTotalDiaDAOImpl:traeListasTotales error", e);
         }
		}
		return listasTotalesDia;
	}

}
