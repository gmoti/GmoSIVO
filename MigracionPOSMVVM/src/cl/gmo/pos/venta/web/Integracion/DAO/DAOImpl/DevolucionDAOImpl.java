package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;




import oracle.jdbc.OracleTypes;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.DevolucionDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.DevolucionBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;

public class DevolucionDAOImpl implements DevolucionDAO {

	Logger log = Logger.getLogger( this.getClass() );
	
	public  void realizaDevolucionMulti(int boleta, String tipoDocumento, String tipoMotivo, String fecha, String tipoDevo, String cdg_alb) throws SQLException{
		log.info("DevolucionDAOImpl:realizaDevolucionMulti inicio");
		Connection con = null;		
		CallableStatement cs = null;
		
		try{
			log.info("DevolucionDAOImpl:realizaDevolucionMulti conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call nombre sp devolucion multi(?,?,?,?,?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			cs.setInt(1, boleta);
			cs.setString(2, tipoDocumento);
			cs.setString(3, tipoMotivo);
			cs.setInt(4, boleta);
			cs.setString(5, tipoDocumento);
			cs.setString(6, tipoDevo);
			cs.setString(7, fecha);
			cs.setString(8, cdg_alb);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			
			cs.execute();
			
			
		}catch(Exception ex){
			log.error("DevolucionDAOImpl:realizaDevolucionMulti error controlado",ex);
		}finally{
			if (null != cs){
				log.warn("DevolucionDAOImpl:realizaDevolucionMulti cierre CallableStatement");
          	   cs.close();
             }  
             if (null != con){
            	 log.warn("DevolucionDAOImpl:realizaDevolucionMulti cierre Connection");
          	   con.close();
             }  
		}
		
	}
	
	public  DevolucionBean  realizaDevolucion(int boleta, String tipoDocumento, String tipoMotivo, String fecha, String tipoDevo, String cdg_alb, String local, String serie, int numero_cab, String codigo_cliente, String agente) throws SQLException{
		log.info("DevolucionDAOImpl:realizaDevolucion inicio");
		Connection con = null;		
		CallableStatement cs = null;
		boolean resp = false;
		DevolucionBean respDevo = new DevolucionBean();
		
		try{
			
			Utils util = new Utils();
			int codigo_cliente_int = util.isEntero(codigo_cliente);
			
			log.info("DevolucionDAOImpl:realizaDevolucion conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_DEV_INS_DEVOLUCION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			System.out.println("SP_DEV_INS_DEVOLUCION("+boleta+","+tipoDocumento+","+tipoMotivo+","+boleta+","+tipoDocumento+","+tipoDevo+","+fecha+","+cdg_alb+","+"PAREMTRO SALIDA"+","+local+","+serie+","+numero_cab+","+13+","+"PAREMTRO SALIDA"+","+codigo_cliente_int+","+"PAREMTRO SALIDA"+","+"PAREMTRO SALIDA"+","+agente);

			
			cs.setInt(1, boleta);
			cs.setString(2, tipoDocumento);
			cs.setString(3, tipoMotivo);
			cs.setInt(4, boleta);
			cs.setString(5, tipoDocumento);
			cs.setString(6, tipoDevo);
			cs.setString(7, fecha);
			cs.setString(8, cdg_alb);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.setString(10, local);
			cs.setString(11, serie);
			cs.setInt(12, numero_cab);
			cs.registerOutParameter(13, OracleTypes.VARCHAR);
			cs.setInt(14, codigo_cliente_int);
			cs.registerOutParameter(15, OracleTypes.VARCHAR);
			cs.registerOutParameter(16, OracleTypes.VARCHAR);
			cs.setString(17, agente);
			cs.execute();
			
			String respuesta = (String)cs.getObject(13);
			String serie_albaran =  (String)cs.getObject(15);
			String codigo_albaran =  (String)cs.getObject(16);		
			
			if("TRUE".equals(respuesta)){
				respDevo.setRespuestaDevolucion(true);
				respDevo.setCodigo1(codigo_albaran);
				respDevo.setSerie_vta(serie_albaran);
			}else{
				respDevo.setRespuestaDevolucion(false);
				respDevo.setCodigo1(null);
				respDevo.setSerie_vta(null);
			}
			
		}catch(Exception ex){
			log.error("DevolucionDAOImpl:realizaDevolucion error controlado",ex);
		}finally{
			if (null != cs){
				log.warn("DevolucionDAOImpl:realizaDevolucion cierre CallableStatement");
          	   cs.close();
             }  
             if (null != con){
            	 log.warn("DevolucionDAOImpl:vrealizaDevolucion cierre Connection");
          	   con.close();
             }  
		}
		
		return respDevo;
	}
	
	
	@Override
	public DevolucionBean traeDevolucion(String numeroDocumento,
			String tipoDocumento) throws Exception {

		log.info("DevolucionDAOImpl:traeDevolucioninicio");
		DevolucionBean devolucion = new DevolucionBean();
 		
 		devolucion =traeBoleta(numeroDocumento, tipoDocumento);
 		
		return devolucion;
	}
	private DevolucionBean traeBoleta(String numeroDocumento, String tipoDocumento){
		log.info("DevolucionDAOImpl:traeBoleta inicio");
		DevolucionBean dev = new DevolucionBean();
		
		Connection con = null;		
		ResultSet albvt_cabecera = null;
		ResultSet albvt_lineas = null;
		CallableStatement cs = null;
		int numeroBoleta = Constantes.INT_CERO;
		try{
			if(null != numeroDocumento && !(Constantes.STRING_BLANCO.equals(numeroDocumento))){
				numeroBoleta=Integer.parseInt(numeroDocumento.trim());
			}		
			log.info("DevolucionDAOImpl:traeBoleta conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_DEVOLUCION(?,?,?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			System.out.println("Numero Boleta => "+numeroBoleta+"<=> tipoDoc=>"+tipoDocumento);
			
			cs.setInt(1, numeroBoleta);
			
			cs.setString(2, tipoDocumento);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();
			
			String respuesta = (String)cs.getObject(5);
			String existeBoleta = (String)cs.getObject(6);
			String retornoAlbaran = (String)cs.getObject(7);
			
			if(Constantes.STRING_TRUE_MAY.equals(respuesta)){
				albvt_cabecera = (ResultSet)cs.getObject(3);
				albvt_lineas = (ResultSet)cs.getObject(4);
			}else{				
				dev.setExisteBoleta(existeBoleta);
				dev.setMensajeRetornoSp("");
			}
			
			if(Constantes.STRING_TRUE_MAY.equals(existeBoleta)){				
				dev.setLista_productos(new ArrayList<ProductosBean>());
				dev.setExisteBoleta(Constantes.STRING_TRUE);
				dev.setMensajeRetornoSp(retornoAlbaran);
				
			}else{
				Utils util = new Utils();
				if(null != albvt_cabecera){
					dev.setExisteBoleta(Constantes.STRING_FALSE);
					while(albvt_cabecera.next()){	
						log.debug("DevolucionDAOImpl:traeBoleta entrando ciclo while");
						dev.setCodigo_venta(albvt_cabecera.getString("CDG_ALCB"));
						dev.setSerie_vta(albvt_cabecera.getString("SERIE"));
						dev.setCodigo_cliente(albvt_cabecera.getString("COD_CLIENTE"));
						dev.setNombreCliente(albvt_cabecera.getString("NOMBRE_CLIENTE"));
						dev.setApellido_cliente(albvt_cabecera.getString("APELLIDO"));
						dev.setIdioma(albvt_cabecera.getString("IDIOMA"));
						dev.setAgente(albvt_cabecera.getString("AGENTE"));
						dev.setDivisa(albvt_cabecera.getString("DIVISA"));
						dev.setDto(albvt_cabecera.getString("DTO"));
						dev.setFormaPago(albvt_cabecera.getString("FPAGO"));
						dev.setCambio(albvt_cabecera.getString("CAMBIO"));
						dev.setFacturado(albvt_cabecera.getString("FACTURADO"));
						dev.setConvenio(albvt_cabecera.getString("CONVENIO"));
						dev.setConfidencial(albvt_cabecera.getString("CONFIDENCIAL"));
						dev.setModificado(albvt_cabecera.getString("MODIFICADO"));
						dev.setMontador(albvt_cabecera.getString("MONTADOR"));	
						dev.setFecha_garantia(albvt_cabecera.getString("FECHA_GARANTIA"));
						dev.setImportePend(albvt_cabecera.getString("IMPORTE"));
						dev.setFecha(util.formatoFecha(albvt_cabecera.getDate("FECHA")));
					}
				
				}
				dev.setLista_productos(new ArrayList<ProductosBean>());
				ProductosBean pro=null;
				if(null != albvt_lineas){
				
					while(albvt_lineas.next()){	
						log.debug("DevolucionDAOImpl:traeBoleta entrando ciclo while");
						pro = new ProductosBean();
					
						
						if(null != albvt_lineas.getString("IDENT_ALB_LN")){
							pro.setIdent(Integer.parseInt(albvt_lineas.getString("IDENT_ALB_LN")));
						}
						
						pro.setCodigo(albvt_lineas.getString("CDG_LN"));
						pro.setCod_pedvtcb(albvt_lineas.getString("ALBVTCB_LN"));
						pro.setCod_articulo(albvt_lineas.getString("COD_ARTICULO"));
						pro.setCod_barra(albvt_lineas.getString("COD_BARRA_ART"));
						pro.setDescripcion(albvt_lineas.getString("DESCRIPCION_ART"));
						pro.setSubAlm(albvt_lineas.getString("SUBALM"));
						
						int precio_iva=0;
						if(null != albvt_lineas.getString("PRECIO_IVA") && !("".equals(albvt_lineas.getString("PRECIO_IVA"))) ){
							precio_iva = Integer.parseInt(albvt_lineas.getString("PRECIO_IVA"));
							pro.setPrecio(precio_iva);
						}
						
						if(null != albvt_lineas.getString("DTO") && !("".equals(albvt_lineas.getString("DTO")))){
							pro.setDto(albvt_lineas.getString("DTO"));
						}else{
							pro.setDto("0");
						}
						
						
						double descuentoBD = albvt_lineas.getDouble("DTO");
						
						
						int cantidad = 0;
						if(null != albvt_lineas.getString("CANTIDAD") && !("".equals(albvt_lineas.getString("CANTIDAD"))) ){
							cantidad = (Integer.parseInt(albvt_lineas.getString("CANTIDAD"))*-1);
							pro.setCantidad(cantidad);
						}
						
						int precio = pro.getPrecio() * (pro.getCantidad()*-1);
						//double cant = Double.parseDouble(pro.getDto());
						double cant =descuentoBD;
						double diferencia = cant / 100;
						double valor = Math.round(precio * diferencia);
						double saldo = precio - valor;
						int precio_importe = (int)saldo;				  
						
										
						//int precio_importe = cantidad * precio_iva;				
						pro.setPrecio_costo(precio_importe);
						pro.setFamilia(albvt_lineas.getString("FAMILIA"));
						
						dev.getLista_productos().add(pro);
					}
				}
			}
		}catch(Exception ex){
			log.error("DevolucionDAOImpl:traeBoleta error controlado",ex);
			System.out.println(ex.getMessage());
		} finally {
            try{
                if (null != albvt_cabecera){
                	log.warn("DevolucionDAOImpl:traeBoleta cierre ResultSet");
                	albvt_cabecera.close();
                }
                if (null != albvt_lineas){
                	log.warn("DevolucionDAOImpl:traeBoleta cierre ResultSet");
                	albvt_lineas.close();
                }
                if (null != cs){
                	log.warn("DevolucionDAOImpl:traeBoleta cierre CallableStatement");
             	   cs.close();
                }  
                if (null != con){
                	log.warn("DevolucionDAOImpl:traeBoleta cierre Connection");
             	   con.close();
                }  
            }catch(SQLException e){
            	log.error("DevolucionDAOImpl:traeBoleta error", e);
         	   try {
				throw new Exception("Error en DAO, Al cerrar las conexiones");
			} catch (Exception e1) {
				log.error("DevolucionDAOImpl:traeBoleta error", e1);
			} 
            }
        }
		return dev;

	}


	public String  traeCodigoDevolucion(String local){
		log.info("DevolucionDAOImpl:traeCodigoDevolucion inicio");
		Connection con = null;
		CallableStatement cs = null;		
		String serie = Constantes.STRING_BLANCO;
		String codigo =Constantes.STRING_BLANCO;
		try{
			log.info("DevolucionDAOImpl:traeCodigoDevolucion conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_DEV_SEL_COD_DEVOLUCION(?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, local);
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			
			cs.execute();
			
			serie = cs.getObject(2).toString();
			codigo = cs.getObject(3).toString();		
			
		}catch(Exception ex){
			log.error("DevolucionDAOImpl:traeCodigoDevolucion error controlado",ex);
		}finally{
			try{               
                if (null != cs){
                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre CallableStatement");
             	   cs.close();
                }  
                if (null != con){
                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre Connection");
             	   con.close();
                }  
            }catch(SQLException e){
            	log.error("DevolucionDAOImpl:traeCodigoDevolucion error", e);
            }
		}
		
		return serie +Constantes.STRING_SLASH+codigo;
	}
	
	public ArrayList<AlbaranBean> buscarAlbaranes(String cdg, String nif, String fecha, String agente, String local){
		 
		 ArrayList<AlbaranBean> lista_albaranes = new ArrayList<AlbaranBean>();
		 Connection con = null;		
		 ResultSet rs = null;		 
		 CallableStatement cs = null;
		 try{
			 
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_ALBARANES(?,?,?,?,?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, cdg);
			cs.setString(2, nif);
			cs.setString(3, fecha);
			cs.setString(4, agente);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.setString(6, local);
			cs.execute();
			rs = (ResultSet)cs.getObject(5);
			AlbaranBean alb = null;
			Utils util = new Utils();
			while(rs.next()){				
				alb = new AlbaranBean();
				
				alb.setCodigo_albaran(rs.getString("CDG"));
				alb.setAgente_albaran(rs.getString("AGENTE"));
				alb.setFecha_albaran(util.formatoFecha(rs.getDate("FECHA")));
				alb.setHora_albaran(rs.getString("HORA"));
				alb.setNif_cliente(rs.getString("NIF_CLIENTE"));
				alb.setDv_nif(rs.getString("DVNIF"));
				lista_albaranes.add(alb);
			}
			
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 finally{
				try{ 
					if(null != rs){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre resultset");
	                	rs.close();
	                }
	                if (null != cs){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre CallableStatement");
	             	   cs.close();
	                }  
	                if (null != con){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre Connection");
	             	   con.close();
	                }  
	                
	            }catch(SQLException e){
	            	log.error("DevolucionDAOImpl:traeCodigoDevolucion error", e);
	            }
			}
		 return lista_albaranes;
	 }
	
	public AlbaranBean traeAlbaran(String cdg,String local){
		
		 Connection con = null;		
		 ResultSet rsCabecera = null;	
		 ResultSet rsLineas = null;
		 ResultSet rsCabeceraMulti = null;	
		 ResultSet rsLineasMulti = null;
		 CallableStatement cs = null;
		 AlbaranBean alb= null;
		 try{
			 
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_BUSCAR_SEL_ALBARAN(?,?,?,?,?,?,?,?)}";
			cs = con.prepareCall(sql); 
			cs.setString(1, cdg);
			cs.setString(2, local);
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.registerOutParameter(8, OracleTypes.CURSOR);
			
			cs.execute();
			int error = cs.getInt(5);
			String msg_error = cs.getString(6);
			String serie_albaran = cs.getString(7);
			rsCabecera = (ResultSet)cs.getObject(3);
			rsLineas = (ResultSet)cs.getObject(4);
			rsCabeceraMulti = (ResultSet)cs.getObject(8);
			
			VentaPedidoPendientesDAOImpl vtapedido = new VentaPedidoPendientesDAOImpl();
			
			
			
			
			
			Utils util = new Utils();
			if(error == 0){
				
				
				while(rsCabecera.next()){					
					alb = new AlbaranBean();
					
					alb.setCodigo_albaran(rsCabecera.getString("CDG"));
					alb.setSerie(rsCabecera.getString("SERIE"));
					alb.setNumero(rsCabecera.getInt("NUMERO"));
					alb.setFecha_albaran(util.formatoFecha(rsCabecera.getDate("FECHA")));
					alb.setHora_albaran(rsCabecera.getString("HORA"));
					alb.setTipoalb(rsCabecera.getString("TIPOALB"));
					alb.setCliente(rsCabecera.getString("CLIENTE"));
					alb.setNif_cliente(rsCabecera.getString("NIF"));
					alb.setDv_nif(rsCabecera.getString("DVNIF"));
					alb.setIdioma(rsCabecera.getString("IDIOMA"));
					alb.setCambio(rsCabecera.getDouble("CAMBIO"));
					alb.setAgente_albaran(rsCabecera.getString("AGENTE"));
					alb.setModificado(rsCabecera.getString("MONTADOR"));
					alb.setFpago(rsCabecera.getString("FPAGO"));
					alb.setFecgarant(util.formatoFecha(rsCabecera.getDate("FECGARAN")));
					alb.setConvenio(rsCabecera.getString("CONVENIO"));
					alb.setNombrecli(rsCabecera.getString("NOMBRE_CLIENTE"));
					alb.setApellidocli(rsCabecera.getString("APELLIDO"));
					alb.setDto(rsCabecera.getDouble("DTO"));
					alb.setDivisa(rsCabecera.getString("DIVISA"));
					alb.setTipomotdev(rsCabecera.getString("TIPOMOTDEV"));
					
				}
				
				//carga las multiofertas dentro del articulo
				ArrayList<ProductosBean> listaMultiofertas = new ArrayList<ProductosBean>();
				if(null != rsCabeceraMulti){						
					while(rsCabeceraMulti.next()){							
						ProductosBean proCabeceraMulti = new ProductosBean();
						proCabeceraMulti.setListaProductosMultiofertas(new ArrayList<ProductosBean>());
						
						proCabeceraMulti.setCodigo(rsCabeceraMulti.getString("CDG"));
						proCabeceraMulti.setCodigoMultioferta(rsCabeceraMulti.getString("CDG"));
						proCabeceraMulti.setSerie(rsCabeceraMulti.getString("SERIE"));
						proCabeceraMulti.setNumero(rsCabeceraMulti.getString("NUMERO"));
						proCabeceraMulti.setCdg_correlativo_multioferta(rsCabeceraMulti.getInt("NUMERO"));
						proCabeceraMulti.setFecha(util.formatoFecha(rsCabeceraMulti.getDate("FECHA")));
						proCabeceraMulti.setArticulo(rsCabeceraMulti.getString("ARTICULO"));
						proCabeceraMulti.setMontado(rsCabeceraMulti.getString("MONTADO"));
						proCabeceraMulti.setAlbvtcb(rsCabeceraMulti.getString("ALBVTCB"));
						proCabeceraMulti.setAlbvtln(rsCabeceraMulti.getString("ALBVTLN"));
						proCabeceraMulti.setAlbped(rsCabeceraMulti.getString("ALBPED"));							
						
						vtapedido.traeMultiofertaLN(proCabeceraMulti, proCabeceraMulti.getListaProductosMultiofertas());
						
						proCabeceraMulti.setCod_barra(rsCabeceraMulti.getString("CODIGOBARRAS"));
						listaMultiofertas.add(proCabeceraMulti);
					}					
				}
				
				
				
				ProductosBean pro = null;
				if(null != alb){
					alb.setLista_productos_albaran(new ArrayList<ProductosBean>());	
					while(rsLineas.next()){
						
										
						pro = new ProductosBean();
											
						if(null != rsLineas.getString("IDENT_ALB_LN")){
							pro.setIdent(Integer.parseInt(rsLineas.getString("IDENT_ALB_LN")));
						}
						pro.setLinea(rsLineas.getString("LINEA"));
						
						pro.setCodigo(rsLineas.getString("CDG_LN"));
						pro.setCod_pedvtcb(rsLineas.getString("ALBVTCB_LN"));
						pro.setCod_articulo(rsLineas.getString("COD_ARTICULO"));
						pro.setCod_barra(rsLineas.getString("COD_BARRA_ART"));
						pro.setDescripcion(rsLineas.getString("DESCRIPCION_ART"));
						pro.setSubAlm(rsLineas.getString("SUBALM"));
						
						int precio_iva=0;
						if(null != rsLineas.getString("PRECIO_IVA") && !("".equals(rsLineas.getString("PRECIO_IVA"))) ){
							precio_iva = Integer.parseInt(rsLineas.getString("PRECIO_IVA"));
							pro.setPrecio(precio_iva);
						}
						
						
						if(null != rsLineas.getString("DTO") && !("".equals(rsLineas.getString("DTO")))){
							pro.setDto(rsLineas.getString("DTO"));
						}else{
							pro.setDto("0");
						}	
						
						
						
						int cantidad = 0;
						if(null != rsLineas.getString("CANTIDAD") && !("".equals(rsLineas.getString("CANTIDAD"))) ){
							cantidad = (Integer.parseInt(rsLineas.getString("CANTIDAD")));
							pro.setCantidad(cantidad);
						}
						int precio_importe = 0;
						
						if("D".equals(alb.getTipoalb())){
							int precio = pro.getPrecio() * (pro.getCantidad()*-1);
							double cant = Double.parseDouble(pro.getDto());
							double diferencia = cant / 100;
							double valor = Math.round(precio * diferencia);
							double saldo = precio - valor;
							precio_importe = (int) Math.floor(saldo);							
						}else{
							int precio = pro.getPrecio() * (pro.getCantidad());
							double cant = Double.parseDouble(pro.getDto());
							double diferencia = cant / 100;
							double valor = Math.round(precio * diferencia);
							double saldo = precio - valor;
							precio_importe = (int) Math.floor(saldo);
						}							
										
									
						pro.setPrecio_costo(precio_importe);
						pro.setFamilia(rsLineas.getString("FAMILIA"));
						
						if(null != rsLineas.getString("IDENTPED")){
							pro.setIdentpedalbaran(rsLineas.getString("IDENTPED"));
						}else{
							pro.setIdentpedalbaran("");
						}					
						
						
											
							for(ProductosBean proCabeceraMulti : listaMultiofertas){							
								
								if(pro.getLinea().equals(proCabeceraMulti.getAlbvtln()) && pro.getCod_barra().equals(proCabeceraMulti.getCod_barra())){
									pro.setProductoMultioferta(proCabeceraMulti);
								}
												
							}
						
						if(null != alb.getLista_productos_albaran()){
							alb.getLista_productos_albaran().add(pro);
						}
						
						
					}
					
					/*
					if(null != alb.getLista_productos_albaran()){
						
						for(ProductosBean prodLinea : alb.getLista_productos_albaran()){
							for(ProductosBean proCabeceraMulti: listaMultiofertas){
								
							}
						}						
					}
					*/
					
					
							
			
						//determina el tipo de albaran DEVOLUCION, ENTREGA o DIRECTA
						if(alb.getSerie().trim().equals(serie_albaran.trim()) && alb.getTipoalb().trim().equals("D")){
							alb.setTipo_albaran("DEVOLUCION");
						}else if(alb.getSerie().trim().equals(serie_albaran.trim()) && alb.getTipoalb().trim().equals("N")){
							alb.setTipo_albaran("ENTREGA");
						}else{
							alb.setTipo_albaran("DIRECTA");
						}
				}
			}else if(error == -1){
				//Error en la obtencion del albaran.
			}else if(error == -2){
				
			}			
			
			 
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 finally{
				try{ 
					if(null != rsCabecera){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre resultset");
	                	rsCabecera.close();
	                }
	                if(null != rsLineas){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre resultset");
	                	rsLineas.close();
	                }
	                if (null != cs){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre CallableStatement");
	             	   cs.close();
	                }  
	                if (null != con){
	                	log.warn("DevolucionDAOImpl:traeCodigoDevolucion cierre Connection");
	             	   con.close();
	                }  
	                
	            }catch(SQLException e){
	            	log.error("DevolucionDAOImpl:traeCodigoDevolucion error", e);
	            }
			}
		 return alb; 
	 }

	public boolean ValidaAutorizacionKodak(String usuario) {
		 Connection con = null;		
		 ResultSet rs = null;		 
		 CallableStatement cs = null;
		 boolean estado = false;
		 try{
			 
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call SP_DEV_SEL_VAL_KODAK(?,?)}";
			cs = con.prepareCall(sql);
			cs.setString(1, usuario);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			
			if (rs.next()) {
				estado = true;
			}
			
			 
		 }catch(Exception ex){
		 }
		 finally{
				try{ 
					if(null != rs){
	                	log.warn("DevolucionDAOImpl:ValidaAutorizacionKodak cierre resultset");
	                	rs.close();
	                }
	                if (null != cs){
	                	log.warn("DevolucionDAOImpl:ValidaAutorizacionKodak cierre CallableStatement");
	             	   cs.close();
	                }  
	                if (null != con){
	                	log.warn("DevolucionDAOImpl:ValidaAutorizacionKodak cierre Connection");
	             	   con.close();
	                }  
	                
	            }catch(SQLException e){
	            	log.error("DevolucionDAOImpl:ValidaAutorizacionKodak error", e);
	            }
			}
		 return estado;
	}	 
	
	//LMARIN NOTA DE CREDITO 04-06-2015
	
	public  String insertaDocumento(String ticket, int documento,
			String tipo_documento, int total, String fecha,String local) throws Exception
	{
		log.info("DevolucionDAOImpl::insertaDocumento inicio");
		Connection con = null;
		CallableStatement cs = null;
		String res = "";
		try {
			log.info("DevolucionDAOImpl:insertaDocumento conectando base datos");
			System.out.println("devolucion ===> SP_PAGO_INS_DOCUMENTO_BE('"+ticket+"','"+documento+"','"+total+"','"+tipo_documento+"','"+local+"','"+fecha+"');");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_PAGO_INS_DOCUMENTO_BE(?,?,?,?,?,?,?)}");
			cs.setString(1, ticket);
			cs.setInt(2, documento);
			cs.setInt(3, total);
			cs.setString(4, tipo_documento);
			cs.setString(5, local);
			cs.setString(6, fecha);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();
			res = cs.getString(7);
		}  catch (Exception e) {
			log.error("DevolucionDAOImpl:insertaDocumento error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_PAGO_INS_DOCUMENTO"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("DevolucionDAOImpl:insertaDocumento cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("DevolucionDAOImpl:insertaDocumento cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("DevolucionDAOImpl:insertaDocumento error", e);
           }
       }
	   System.out.println("DevolucionDAOImpl:insertaDocumento Directa => "+res);
	   return res;
	}
	
	//LMARIN VALIDACION DE LA FECHA DE REALIZACION DE LA NOTA DE CREDITO / 2018-02-08
	
	public  int validarFechaNC(int numdevo) throws Exception
	{
		log.info("DevolucionDAOImpl::insertaDocumento inicio");
		Connection con = null;
		CallableStatement cs = null;
		int res = 0;
		try {
			log.info("DevolucionDAOImpl:insertaDocumento conectando base datos");
			System.out.println("devolucion ===> SP_VALIDA_FECHANC('"+numdevo+"');");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{call SP_VALIDA_FECHANC(?,?)}");
			cs.setInt(1, numdevo);
			cs.registerOutParameter(2, OracleTypes.NUMBER);
			cs.execute();
			res = cs.getInt(2);
		}  catch (Exception e) {
			log.error("DevolucionDAOImpl:validarFechaNC error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VALIDA_FECHANC"); 
       } finally {
              try{
               if (null != cs){
            	   log.warn("DevolucionDAOImpl:validarFechaNC cierre CallableStatement");
                   cs.close();
               }  
               if (null != con){
            	   log.warn("DevolucionDAOImpl:validarFechaNC cierre Connection");
            	   con.close();
               }
           }catch(Exception e){
        	   log.error("DevolucionDAOImpl:validarFechaNC error", e);
           }
       }
	   System.out.println("DevolucionDAOImpl:validarFechaNC Directa => "+res);
	   return res;
	}


}
