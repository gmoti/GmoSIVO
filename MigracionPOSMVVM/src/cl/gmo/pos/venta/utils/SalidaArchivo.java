
package cl.gmo.pos.venta.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;



import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.facade.BusquedaLiberacionesFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaPedidoFacade;
import cl.gmo.pos.venta.web.forms.ContactologiaForm;
import cl.gmo.pos.venta.web.helper.GraduacionesHelper;

/*ACTUALIZACIÓN SAP 20160726*/
public class SalidaArchivo {
	
	public SalidaArchivo(){}
	
	public String creaArchivoLiberacion(VentaPedidoBean venta, String local, ArrayList<VentaPedidoBean> lista_detalle_pedido, String nombreArchivo, String nombreTemporal){
		
		String respuesta = "";
		nombreArchivo = nombreArchivo.trim();		
		File f = null, f1 = null;
		FileWriter fw = null,fw1 = null;
		PrintWriter pw = null ,pw1 = null;
		Utils util = new Utils();
		boolean escribir = true;
		String ruta = "",rutat="";
		String armazon_ep="",cristal_ep_od ="",cristal_ep_oi="";
		try{
				Properties prop = util.leeConfiguracion();
				String values = prop.getProperty("liberacion.ruta");
				InputStream is = null;
				ruta=values.trim();
				
				
				Properties prop1 = util.leeConfiguracion();
				String values1 = prop1.getProperty("liberacion.rutares");
				InputStream is1 = null;
				rutat = values1.trim();
				
				boolean existe_archivo = false;
				String isTrio = "ZENC";
				String isLentilla="";
				
				String mcli="",ccli="";
				
				String nombreTemporal1 = rutat+nombreTemporal;
				String nombreArchivo1 = (rutat+nombreArchivo).trim();
				
				nombreTemporal = ruta+nombreTemporal;
				nombreArchivo = (ruta+nombreArchivo).trim();
				
				
				
				String [] cod_lista_lib_temp  = venta.getCod_lista_lib().split("/");
				String codLocalSap = util.traeCodigoLocalSap(cod_lista_lib_temp[0]);
				//String [] cod_lista_lib_temp  = venta.getCod_lista_lib().split("/");
				String cod_lista_lib_sap = codLocalSap+"/"+cod_lista_lib_temp[1]; 
				
				PedidosPendientesBean pedidosSeleccionado = PosVentaPedidoFacade.traePedidosSeleccionado(venta.getCod_lista_lib());
				//ArrayList<ProductosBean> lprod =  pedidosSeleccionado.getListaProduc();
				
				String linea1 = "1;"+isTrio+";1000;50;10;"+venta.getFecha_entrega()+";T;"+venta.getFecha()+";"+cod_lista_lib_sap+"/"+venta.getGrupo()+";CLP;1;"+venta.getFecha_entrega()+";;"+venta.getHora()+";"+venta.getFecha()+";"+util.traeHoraString()+";"+venta.getNumdev()+";"+venta.getEncargo_padre()+";"+venta.getLineadev()+";";
				
				String linea2 = "2;AG;"+codLocalSap+";;;;;;";
				String codigoSAP = util.traeCodigoAgente(venta.getAgente());
				int codigoSap =0;
				if(null != codigoSAP){
					codigoSap = Integer.parseInt(codigoSAP);
				}
				
				String linea3 =	"2;ZV;"+util.formato_Numero_Ticket(codigoSap)+";;;;;;";
				ClienteBean cliente = util.traeCliente(null, venta.getId_cliente());
				String telefonoCasaCliente = "";
				String direcciondesp = "";
				if(null != cliente.getFono_casa()){
					telefonoCasaCliente = cliente.getFono_casa();
				}
				
				String linea4 = "2;ZC;1000001;"+cliente.getNombre()+" "+cliente.getApellido()+";"+cliente.getNif()+";"+telefonoCasaCliente+";;;"; //+direcciondesp+";";
				String linea5 = Constantes.STRING_BLANCO;
				String linea6 = Constantes.STRING_BLANCO;
				String linea7 =  Constantes.STRING_BLANCO;
				String linea =  Constantes.STRING_BLANCO;
				String lineatmp1 =  Constantes.STRING_BLANCO;
				String lineatmp2 =  Constantes.STRING_BLANCO;
				String lineatmp3 =  Constantes.STRING_BLANCO;
				String lineatmp4 =  Constantes.STRING_BLANCO;

				int condiciones=0;
				
				//correr la lista de detalles.
				if(null != lista_detalle_pedido){				
					
					for(VentaPedidoBean vta : lista_detalle_pedido){
						
						Date  d = new Date();
						
						
						
						String isMulti="N";
						String cdg_ident=vta.getCod_detalle_vta();
						String [] linea_enc = vta.getCod_detalle_vta().split("/");

						ProductosBean producto = util.traeProductoEsp(vta.getArticulo(),local,venta.getCod_lista_lib(),venta.getGrupo(),venta.getCod_lista_lib().concat("/").concat(linea_enc[2]));
						
						if("S".equals(producto.getDescatalogado())){
							//escribir = false;
							//break;
						}
						
						String codigo=cdg_ident.substring(0, 3);
						//if("MUL".equals(producto.getFamilia())){
						if("MON".equals(codigo)){
							
							isMulti="S";
							
							//ArrayList<VentaPedidoBean> lista_detalle_multi = util.traeDetalleLiberacionMulti(venta.getCod_lista_lib(), venta.getGrupo());
							ArrayList<VentaPedidoBean> lista_detalle_multi = new ArrayList<VentaPedidoBean>();
							if(null != lista_detalle_multi){
								VentaPedidoBean vtamul = vta;
								//for(VentaPedidoBean vtamul : lista_detalle_multi){
									
									GraduacionesBean graduacion  = util.traeGraduacionPedido(cliente.getCodigo(), vta.getFechagrad(), vta.getNumerograd());
																		
									ProductosBean productomulti = util.traeProductoEsp(vtamul.getArticulo(),local,venta.getCod_lista_lib(),venta.getGrupo(),venta.getCod_lista_lib().concat("/").concat(linea_enc[2]));
									
									int preciosiva=0,preciodto = 0,preciodif =0;

									double  ddto = Double.parseDouble(productomulti.getDto())/100;
									preciosiva = productomulti.getImporte()*2;
									
									preciodto = (int) (preciosiva * ddto);
									
									if(productomulti.getTarifalocal().equals("N")){
										preciodif = (int) Math.round((productomulti.getImporte()*1.19)-productomulti.getImporte());
									}
									
									System.out.println("MULTIOFERTAS ===> Precios DTO ==>"+ddto+" preciosiva ==>"+preciosiva+" preciodto =>"+preciodto);

									
									if("S".equals(productomulti.getDescatalogado())){
										//escribir = false;
										//break;
									}
									cdg_ident=String.valueOf(vtamul.getCodigo());
									
									//*************************									
									if("M".equals(productomulti.getTipoFamilia()) || "G".equals(producto.getTipoFamilia())){
										if (Constantes.STRING_ACTION_ARCLI.equals(producto.getCod_barra())) {
											mcli ="X";
										}
										
										linea5 = "10;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vtamul.getCantidad()+";UN;"+((int)productomulti.getImporte()==0?1:(int)productomulti.getImporte())+";"+preciodif+";"+productomulti.getPrecio_costo()+";"+mcli+";"+codLocalSap+";"+productomulti.getCodigoSap()+";"+productomulti.getDescripcion()+";TEXTOPOSICION;;;;;;";
										condiciones++;
										//pw.println(linea5);
										
										
									}else if("C".equals(productomulti.getTipoFamilia()) && "D".equals(vtamul.getOjo())){
										
										
										//linea7 C D
										ProductosBean inforProducto = util.traeInfoproducto(vtamul.getArticulo());
										String color  = inforProducto.getColor();
										if(null == color){
											color = "";
										}
										
										ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacionMultioferta(vtamul.getCodigo());								
										String infoSuplementos="";
										String EB="";
										String CB="";
										String PR="";
										String PRC="";
										String DE="";
										String DC="";
										String AL="";
										String EC="";
										String TE="";
										
										if(null != listaSuple){
											
											for(SuplementopedidoBean suple:listaSuple){
												
												if("EB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EB = valor;
													}												
													
												}else if("CB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														CB = valor;
													}													
													
												}else if("PR".equals(suple.getTratami())){
													String valor = suple.getValor().replace(".", ",");
													if("GRADUACION".equals(valor)){
														if(null != graduacion.getOD_base()){
															PR = graduacion.getOD_base();
														}
														if(null != graduacion.getOD_cantidad()){
															PRC = graduacion.getOD_cantidad();
														}												
													}
												}else if("DE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DE = valor;
													}
													
												}else if("DC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DC = valor;
													}
													
												}else if("AL".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														AL = valor;
													}													
													
												}else if("EC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EC = valor;
													}
													
												}else if("TE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														TE = valor;
													}													
												}									
											}
											infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
											
										}else{
											infoSuplementos=";;;;;;;;";
										}
										
										//VALIDA DIAMETRO
										inforProducto.setEsfera(vta.getEsfera());
										inforProducto.setCilindro(vta.getCilindro());
										
										util.validaDiametroESFCL(inforProducto);
										String diametroStr = util.isEntero(inforProducto.getDesdediametro());
									
										
										String ejeStr = util.isEntero(vtamul.getEje());
										String esferaStr = util.isEntero(vta.getEsfera());
										String cilindroStr = util.isEntero(vta.getCilindro());
										String OD_nStr =""; 
										if(null != graduacion.getOD_n()){
											OD_nStr=util.isEntero(graduacion.getOD_n());
										}
											
										
										String OD_pStr = "";
										if(null != graduacion.getOD_p()){
											OD_pStr=util.isEntero(graduacion.getOD_p());
										}
										String OD_adicion="";
										if(null != graduacion.getOD_adicion()){
											OD_adicion = util.isEntero(graduacion.getOD_adicion());
											OD_adicion=OD_adicion.replace(",", ".");
										}
										/*if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
											ccli = "X";
										}*/
										if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
											ccli = "X";
											cristal_ep_oi = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",venta.getGrupo());
											producto.setCodigoSap(cristal_ep_oi);
										}
										
										//STRING_ACTION_CCLI 
										linea7 = "20;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vtamul.getCantidad()+";UN;"+((int)((productomulti.getImporte())*2)==0?1:(int)((productomulti.getImporte())*2))+";"+(preciodif*2)+";"+(productomulti.getPrecio_costo()*2)+";"+ccli+";"+codLocalSap+";"+productomulti.getCodigoSap()+";"+productomulti.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vtamul.getOjo()+";"+diametroStr+";"+ejeStr+";"+OD_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vtamul.getUnahora()+";"+OD_nStr.replace(",", ".")+";"+OD_pStr.replace(",", ".")+";";
										condiciones++;
										
									}else if("C".equals(productomulti.getTipoFamilia()) && "I".equals(vtamul.getOjo())){
										
										
										//linea6 C I 
										ProductosBean inforProducto = util.traeInfoproducto(vtamul.getArticulo());
										String color  = inforProducto.getColor();
										if(null == color){
											color = "";
										}
										ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacionMultioferta(vtamul.getCodigo());								
										String infoSuplementos="";
										String EB="";
										String CB="";
										String PR="";
										String PRC="";
										String DE="";
										String DC="";
										String AL="";
										String EC="";
										String TE="";
										
										if(null != listaSuple){
											
											for(SuplementopedidoBean suple:listaSuple){
												
												if("EB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EB = valor;
													}												
													
												}else if("CB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														CB = valor;
													}													
													
												}else if("PR".equals(suple.getTratami())){
													String valor = suple.getValor().replace(".", ",");
													if("GRADUACION".equals(valor)){
														if(null != graduacion.getOI_base()){
															PR = graduacion.getOI_base();
														}
														if(null != graduacion.getOI_cantidad()){
															PRC = graduacion.getOI_cantidad();
														}												
													}
												}else if("DE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DE = valor;
													}
													
												}else if("DC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DC = valor;
													}
													
												}else if("AL".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														AL = valor;
													}													
													
												}else if("EC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EC = valor;
													}
													
												}else if("TE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														TE = valor;
													}													
												}									
											}
											infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
											
										}else{
											infoSuplementos=";;;;;;;;";
										}
										//VALIDA DIAMETRO
										inforProducto.setEsfera(vta.getEsfera());
										inforProducto.setCilindro(vta.getCilindro());
										
										util.validaDiametroESFCL(inforProducto);
										String diametroStr = util.isEntero(inforProducto.getDesdediametro());
										String ejeStr = util.isEntero(vtamul.getEje());
										String esferaStr = util.isEntero(vta.getEsfera());
										String cilindroStr = util.isEntero(vta.getCilindro());
										String OI_nStr =""; 
										if(null != graduacion.getOI_n()){
											OI_nStr=util.isEntero(graduacion.getOI_n());
										}
											
										
										String OI_pStr = "";
										if(null != graduacion.getOI_p()){
											OI_pStr=util.isEntero(graduacion.getOI_p());
										}
										String OI_adicion="";
										if(null != graduacion.getOI_adicion()){
											OI_adicion = util.isEntero(graduacion.getOI_adicion());
											OI_adicion=OI_adicion.replace(",", ".");
										}
										/*if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
											ccli = "X";
										}*/
										
										if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
											ccli = "X";
											cristal_ep_oi = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",venta.getGrupo());
											producto.setCodigoSap(cristal_ep_oi);
										}
										
										linea6 = "20;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vtamul.getCantidad()+";UN;"+((int)((productomulti.getImporte())*2)==0?1:(int)((productomulti.getImporte())*2))+";"+(preciodif *2)+";"+(productomulti.getPrecio_costo()*2)+";"+ccli+";"+codLocalSap+";"+productomulti.getCodigoSap()+";"+productomulti.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vtamul.getOjo()+";"+diametroStr+";"+ejeStr+";"+OI_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vtamul.getUnahora()+";"+OI_pStr.replace(",", ".")+";"+OI_nStr.replace(",", ".")+";";
										condiciones++;
										//pw.println(linea6);
										
										
									}else if("L".equals(producto.getTipoFamilia())){
										
										isLentilla="OK";
										ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
										String color  = inforProducto.getColor();
										if(null == color){
											color = "";
										}
										ContactologiaForm formularioContactologia = new ContactologiaForm();
										GraduacionesHelper helperContactologia = new GraduacionesHelper();
										formularioContactologia.setNumero_graduacion(String.valueOf(vta.getNumerograd()));
										formularioContactologia.setFecha_graduacion(vta.getFechagrad());
										formularioContactologia.setCliente(venta.getId_cliente());
										formularioContactologia = helperContactologia.traeContactologia(formularioContactologia);
										
										String ejeStr = "";
										String diametroStr = "";
										if("D".equalsIgnoreCase(vta.getOjo())){
											if(null != formularioContactologia.getO_colo()){
												color = formularioContactologia.getO_colo();
											}else{
												color="";
											}
											
											//VALIDA DIAMETRO contactologia
											diametroStr = formularioContactologia.getO_diamt();
											ejeStr = formularioContactologia.getO_eje();
										}else{
											if("I".equalsIgnoreCase(vta.getOjo())){
												if(null != formularioContactologia.getI_colo()){
													color = formularioContactologia.getI_colo();
												}else{
													color = "";
												}
												
												diametroStr = formularioContactologia.getI_diamt();
												ejeStr = formularioContactologia.getI_eje();
											}
										}			
																	
										
										
										int cantidad = 0;
										int cantidad_vendida = vta.getCantidad();
										
										if(venta.isSin_cdd()){
											cantidad = cantidad_vendida - venta.getCantidad_cdd();
										}else{
											cantidad = 0;
										}
										
										//linea para las lentillas graduables
										linea1 = "1;ZENK;1000;50;10;"+venta.getFecha_entrega()+";T;"+venta.getFecha()+";"+cod_lista_lib_sap+"/"+venta.getGrupo()+";CLP;1;"+util.fechaEntregaLiberacion(venta.getFecha_entrega())+";;"+venta.getHora()+";"+util.traeFechaHoyFormateadaString()+";"+util.traeHoraString()+";"+venta.getNumdev()+";"+venta.getEncargo_padre()+";"+venta.getLineadev()+";";
										//LMARIN 20130912
										//LMARIN 20180327 , SE ELIMINA LA linea6 , YA QUE SE LIBERARAN INDIVIDUALMENTE LAS LENTILLAS

										if(producto.getCodigo().equals("2746400000000") || producto.getCodigo().equals("2749000000000") || producto.getCodigo().equals("2746800000000")){
											if("D".equals(vta.getOjo())){
												linea5 = "30;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(productomulti.getImporte()*2)+";"+Math.round(((productomulti.getImporte()*2)*1.19)-(productomulti.getImporte()*2))+";"+preciodto+";"+mcli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;;;;;"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
											}else{
												linea5 = "30;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(productomulti.getImporte()*2)+";"+Math.round(((productomulti.getImporte()*2)*1.19)-(productomulti.getImporte()*2))+";"+preciodto+";"+mcli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;;;;;"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
											}	
										}else{
											if("D".equals(vta.getOjo())){
												linea5 ="30;"+formularioContactologia.getO_radio2().replace(",", ".")+";";//+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
												//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color;
												//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getO_radio1().replace(",", ".")+";"+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
											}else{
												linea5 ="30;"+formularioContactologia.getI_radio2().replace(",", ".")+";";//+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
												//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color;
												//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getI_radio1().replace(",", ".")+";"+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
											}	
										}	
										
										 
									}
									//*************************
									
								//}//fin for vtamul
								
							}
							
							
							
						}else{//ARTICULOS NO SON MULTIOFERTAS.
							
							int preciosiva=0,preciodto = 0,productoimpdto=0,prodimp=0;

							double  ddto = Double.parseDouble(producto.getDto())/100;
							preciosiva = (int) Math.round((producto.getPrecio()/1.19));
							productoimpdto = (int) (producto.getPrecio() * ddto);
							preciodto = (int) (preciosiva * ddto);

							//preciodto = (int) (preciosiva * ddto);
							if(producto.getTarifalocal().trim().equals("N")){
								prodimp = (int)((producto.getPrecio()-productoimpdto) -(preciosiva-preciodto));
							}
							
							
							System.out.println("Precios DTO ==>"+ddto+" preciosiva ==>"+preciosiva+" productoimpdto =>"+productoimpdto+" preciodto =>"+preciodto+" prodimp =>"+prodimp);

							GraduacionesBean graduacion  = util.traeGraduacionPedido(cliente.getCodigo(), vta.getFechagrad(), vta.getNumerograd());
							
							//EN CASO DE SER ARCLI
							boolean arcli = false;
							String armazon = "";
							String puente = "";
							String diagonal = "";
							String vertical = "";
							String horizontal = "";
							
							if("M".equals(producto.getTipoFamilia()) || "G".equals(producto.getTipoFamilia())){
								
								//SI MONTURA ES ARCLI DEBE AGREGAR LOS DATOS ADICIONALES
								if (Constantes.STRING_ACTION_ARCLI.equals(producto.getCod_barra())) {
									
									ProductosBean prod = new ProductosBean();
									prod.setIdent(vta.getCodigo());
									PosVentaPedidoFacade.traeAdicionalArcli(prod);
									
									arcli = true;
									mcli="X";
									armazon = prod.getArcli_armazon();
									puente = String.valueOf(prod.getArcli_puente());
									diagonal = String.valueOf(prod.getArcli_diagonal());
									horizontal = String.valueOf(prod.getArcli_horizontal());
									vertical = String.valueOf(prod.getArcli_vertical());
									//Rescato armazon asociado al encargo_padre 20160830
									/*armazon_ep = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"M",venta.getGrupo());
									if(!armazon_ep.equals("") && armazon_ep != null){
										producto.setCodigoSap(armazon_ep);
									}*/
								}
							
								linea5 = "10;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+((preciosiva- preciodto)==0?1:(preciosiva -preciodto))+";"+prodimp+";"+preciodto+";"+mcli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;"+armazon+";"+puente+";"+diagonal+";"+horizontal+";"+vertical+";";
										
								condiciones++;
								
							}else if("C".equals(producto.getTipoFamilia()) && "D".equals(vta.getOjo())){
								
								int a = 0,b = 0,c = 0;

								double  ddtoc =(1 - Double.parseDouble(producto.getDtototal())/100);
								preciosiva = (int) Math.round((producto.getPrecio()/1.19));
								
								a = (int)(preciosiva+(preciosiva * ddtoc));
								c = (int)((preciosiva*2) - (preciosiva+(preciosiva * ddtoc)));
								
								if(producto.getTarifalocal().trim().equals("N")){
									b = (int)( (producto.getPrecio()+(producto.getPrecio()* ddtoc)) - (preciosiva+(preciosiva * ddtoc)));
								}
								
								System.out.println("a =>"+a+" b =>"+b+" c==>"+c);
								
								//linea7 C D
								ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
								String color  = inforProducto.getColor();
								if(null == color){
									color = "";
								}
								
								ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacion(vta.getCodigo());								
								String infoSuplementos="";
								String EB="";
								String CB="";
								String PR="";
								String PRC="";
								String DE="";
								String DC="";
								String AL="";
								String EC="";
								String TE="";
								
								if(null != listaSuple){
									
									for(SuplementopedidoBean suple:listaSuple){
										
										if("EB".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												EB = valor;
											}												
											
										}else if("CB".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												CB = valor;
											}													
											
										}else if("PR".equals(suple.getTratami())){
											String valor = suple.getValor().replace(".", ",");
											if("GRADUACION".equals(valor)){
												if(null != graduacion.getOD_base()){
													PR = graduacion.getOD_base();
												}
												if(null != graduacion.getOD_cantidad()){
													PRC = graduacion.getOD_cantidad();
												}												
											}
										}else if("DE".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												DE = valor;
											}
											
										}else if("DC".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												DC = valor;
											}
											
										}else if("AL".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												AL = valor;
											}													
											
										}else if("EC".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												EC = valor;
											}
											
										}else if("TE".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												TE = valor;
											}													
										}									
									}
									infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
									
								}else{
									infoSuplementos=";;;;;;;;";
								}
								
								//VALIDA DIAMETRO
								inforProducto.setEsfera(vta.getEsfera());
								inforProducto.setCilindro(vta.getCilindro());
								
								util.validaDiametroESFCL(inforProducto);
								String diametroStr = util.isEntero(inforProducto.getDesdediametro());
								String ejeStr = util.isEntero(vta.getEje());
								String esferaStr = util.isEntero(vta.getEsfera());
								String cilindroStr = util.isEntero(vta.getCilindro());
								String OD_nStr =""; 
								if(null != graduacion.getOD_n()){
									OD_nStr=util.isEntero(graduacion.getOD_n());
								}
									
								
								String OD_pStr = "";
								if(null != graduacion.getOD_p()){
									OD_pStr=util.isEntero(graduacion.getOD_p());
								}
								String OD_adicion="";
								if(null != graduacion.getOD_adicion()){
									OD_adicion = util.isEntero(graduacion.getOD_adicion());
									OD_adicion=OD_adicion.replace(",", ".");
								}
								
								if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
									ccli = "X";
									cristal_ep_od = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",venta.getGrupo());
									producto.setCodigoSap(cristal_ep_od);
								}
				
								linea7 = "20;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(a==0?1:a)+";"+b+";"+c+";"+ccli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OD_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vta.getUnahora()+";"+OD_nStr.replace(",", ".")+";"+OD_pStr.replace(",", ".")+";";
									
								condiciones++;
								
							}else if("C".equals(producto.getTipoFamilia()) && "I".equals(vta.getOjo())){
								
								int a = 0,b = 0,c = 0;

								double  ddtoc =(1 - Double.parseDouble(producto.getDtototal())/100);
								preciosiva = (int) Math.round((producto.getPrecio()/1.19));
								
								a = (int)(preciosiva+(preciosiva * ddtoc));
								c = (int)((preciosiva*2) - (preciosiva+(preciosiva * ddtoc)));
								
								if(producto.getTarifalocal().trim().equals("N")){
									b = (int)( (producto.getPrecio()+(producto.getPrecio()* ddtoc)) - (preciosiva+(preciosiva * ddtoc)));
								}
								
								System.out.println("a =>"+a+" b =>"+b+" c==>"+c);
								
								//linea6 C I 
								ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
								String color  = inforProducto.getColor();
								if(null == color){
									color = "";
								}
								
								ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacion(vta.getCodigo());								
								String infoSuplementos="";
								String EB="";
								String CB="";
								String PR="";
								String PRC="";
								String DE="";
								String DC="";
								String AL="";
								String EC="";
								String TE="";
								if(null != listaSuple){
									
									for(SuplementopedidoBean suple:listaSuple){
										
										if("EB".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												EB = valor;
											}												
											
										}else if("CB".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												CB = valor;
											}													
											
										}else if("PR".equals(suple.getTratami())){
											String valor = suple.getValor().replace(".", ",");
											if("GRADUACION".equals(valor)){
												if(null != graduacion.getOI_base()){
													PR = graduacion.getOI_base();
												}
												if(null != graduacion.getOI_cantidad()){
													PRC = graduacion.getOI_cantidad();
												}												
											}
										}else if("DE".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												DE = valor;
											}
											
										}else if("DC".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												DC = valor;
											}
											
										}else if("AL".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												AL = valor;
											}													
											
										}else if("EC".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												EC = valor;
											}
											
										}else if("TE".equals(suple.getTratami())){
											
											if(null != suple.getValor()){
												String valor = suple.getValor().replace(".", ",");
												TE = valor;
											}													
										}									
									}
									infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
									
								}else{
									infoSuplementos=";;;;;;;;";
								}
								
								//infoSuplementos="EB;CB;PR;;DE;DC;AL;EC;TE;";
								//VALIDA DIAMETRO
								inforProducto.setEsfera(vta.getEsfera());
								inforProducto.setCilindro(vta.getCilindro());
								
								util.validaDiametroESFCL(inforProducto);
								String diametroStr = util.isEntero(inforProducto.getDesdediametro());
								String ejeStr = util.isEntero(vta.getEje());
								String esferaStr = util.isEntero(vta.getEsfera());
								String cilindroStr = util.isEntero(vta.getCilindro());
								
								String OI_nStr =""; 
								if(null != graduacion.getOI_n()){
									OI_nStr=util.isEntero(graduacion.getOI_n());
								}
									
								
								String OI_pStr = "";
								if(null != graduacion.getOI_p()){
									OI_pStr=util.isEntero(graduacion.getOI_p());
								}
								
								String OI_adicion="";
								if(null != graduacion.getOI_adicion()){
									OI_adicion = util.isEntero(graduacion.getOI_adicion());
									OI_adicion=OI_adicion.replace(",", ".");
								}
								
								if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
									ccli = "X";
									cristal_ep_oi = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",venta.getGrupo());
									producto.setCodigoSap(cristal_ep_oi);
								}
								
								linea6 = "20;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(a==0?1:a)+";"+b+";"+c+";"+ccli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OI_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vta.getUnahora()+";"+OI_nStr.replace(",", ".")+";"+OI_pStr.replace(",", ".")+";";
								
							
								condiciones++;
								
							}else if("L".equals(producto.getTipoFamilia())){
								System.out.println("Paso por lentilla");
								isLentilla = "OK";
								ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
								String color  = "";
								if(null == color){
									color = "";
								}
								ContactologiaForm formularioContactologia = new ContactologiaForm();
								GraduacionesHelper helperContactologia = new GraduacionesHelper();
								int num = (int) vta.getNumerograd();
								formularioContactologia.setNumero_graduacion(String.valueOf(num));
								formularioContactologia.setFecha_graduacion(vta.getFechagrad());
								formularioContactologia.setCliente(venta.getId_cliente());
								
								formularioContactologia = helperContactologia.traeContactologia(formularioContactologia);
								
								String ejeStr = "";
								//VALIDA DIAMETRO contactologia
								String diametroStr = "";
								if("D".equalsIgnoreCase(vta.getOjo())){
									if(null != formularioContactologia.getO_colo()){
										color = formularioContactologia.getO_colo();
									}else{
										color = "";
									}
									
									diametroStr = formularioContactologia.getO_diamt();
									ejeStr = formularioContactologia.getO_eje();
								}else{
									if("I".equalsIgnoreCase(vta.getOjo())){
										if(null != formularioContactologia.getI_colo()){
											color = formularioContactologia.getI_colo();
										}else{
											color = "";
										}
										
										diametroStr = formularioContactologia.getI_diamt();
										ejeStr = formularioContactologia.getI_eje();
									}
								}
								
								
								int cantidad = 0;
								int cantidad_vendida = vta.getCantidad();
								
								if(venta.isSin_cdd()){
									cantidad = cantidad_vendida - venta.getCantidad_cdd();
								}else{
									cantidad = 0;
								}
								
								String OI_adicion_len="";
								if(null != formularioContactologia.getI_adic()){
									OI_adicion_len = formularioContactologia.getI_adic();
									OI_adicion_len = OI_adicion_len.replace(",", ".");
								}
								
								String OD_adicion_len="";
								if(null != formularioContactologia.getO_adic()){
									OD_adicion_len = formularioContactologia.getO_adic();
									OD_adicion_len = OD_adicion_len.replace(",", ".");
								}
								
								//linea para las lentillas graduables
								linea1 = "1;ZENK;1000;50;10;"+venta.getFecha_entrega()+";T;"+venta.getFecha()+";"+cod_lista_lib_sap+"/"+venta.getGrupo()+";CLP;1;"+util.fechaEntregaLiberacion(venta.getFecha_entrega())+";;"+venta.getHora()+";"+util.traeFechaHoyFormateadaString()+";"+util.traeHoraString()+";"+venta.getNumdev()+";"+venta.getEncargo_padre()+";"+venta.getLineadev()+";";
								
								//LMARIN 20130912
								//LMARIN 20180327 , SE ELIMINA LA linea6 , YA QUE SE LIBERARAN INDIVIDUALMENTE LAS LENTILLAS

								if(producto.getCodigo().equals("2746400000000") || producto.getCodigo().equals("2749000000000") || producto.getCodigo().equals("2746800000000")){
									if("D".equals(vta.getOjo())){
										linea5 = "30;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;"+OD_adicion_len+";;;;"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
									}else{
										linea5 = "30;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;"+OI_adicion_len+";;;;"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
									}
								}else{
									///aca estamos !!!!!!!!!!!!!!!!
									if("D".equals(vta.getOjo())){
										linea5 = "30;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OD_adicion_len+";;"+formularioContactologia.getO_radio1().replace(",", ".")+";"+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
									}else{
										linea5 = "30;"+cod_lista_lib_sap+"/"+venta.getGrupo()+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OI_adicion_len+";;"+formularioContactologia.getI_radio1().replace(",", ".")+";"+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
									}
								}
																	
								/*if("D".equals(vta.getOjo())){
									linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getO_radio1().replace(",", ".")+";"+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
								}else{
									linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getI_radio1().replace(",", ".")+";"+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
								}*/																																																		
								
							}						
								
						}// fin else					
					
					}//fin for
					
					//*****************************************************************************************
					
					//LMARIN 20160831 / MODIFICO PARA COMPROBAR QUE LAS LINEAS DE LAS LENTILLAS, VENGAN COMPLETAS
					if("OK".equals(isLentilla)){
						if(!(Constantes.STRING_BLANCO.equals(linea5))){
							//pw.println(linea5);											
						}else{
							linea5="";
							//respuesta = "ERROR_ESCRITURA_LINEA 5";
						}
						
						if(!(Constantes.STRING_BLANCO.equals(linea6))){
							//pw.println(linea6);
						}else{
							linea6="";
							//pw.println(linea6);
							//respuesta = "ERROR_ESCRITURA_LINEA 6";
						}		
						/*if(!(Constantes.STRING_BLANCO.equals(linea))){
							//pw.println(linea);
						}else{
							respuesta = "ERROR_ESCRITURA_LINEA";
						}*/								
					}else{
						if(!(Constantes.STRING_BLANCO.equals(linea5))){
							//pw.println(linea5);											
						}else{
							respuesta = "ERROR_ESCRITURA_LINEA 5";
						}
						if(!(Constantes.STRING_BLANCO.equals(linea7))){
							//pw.println(linea7);
						}else{
							respuesta = "ERROR_ESCRITURA_LINEA 7";
						}
						if(!(Constantes.STRING_BLANCO.equals(linea6))){
							//pw.println(linea6);
						}else{
							respuesta = "ERROR_ESCRITURA_LINEA 6";
						}								
					}					
					
					//****************************************************************************************************
					//NUEVA LOGICA*************************
							
					if("OK".equals(isLentilla)){
							if("".equals(respuesta)){
								
								//f = new File(ruta+nombreArchivo.trim());
								f = new File(nombreArchivo);
								f1 = new File(nombreArchivo1);
								
								if(!(f.exists())){							
									f = new File(nombreArchivo);
									f.createNewFile();
									
									f1 = new File(nombreArchivo1);
									f1.createNewFile();
								}
								
								//LMARIN 20160831 / MODIFICO PARA QUE SE IMPRIMAN HASTA 6 LINEAS EEN EL ARCHIVO DE LAS LENTILLAS
								
								if(f.exists()){
									
									fw = new FileWriter(f,true);
									pw = new PrintWriter(fw);
									
									fw1 = new FileWriter(f1,true);
									pw1 = new PrintWriter(fw1);
									
									pw.println(linea1);
									pw.println(linea2);
									pw.println(linea3);
									pw.println(linea4);
									pw.println(linea5);
									pw.println(linea6);
									fw.flush();
									pw.flush();
									
									pw1.println(linea1);
									pw1.println(linea2);
									pw1.println(linea3);
									pw1.println(linea4);
									pw1.println(linea5);
									pw1.println(linea6);
									fw1.flush();
									pw1.flush();
									
								}						
							}
					}else{
							
							if("".equals(respuesta)){
								
								//f = new File(ruta+nombreArchivo.trim());
								f = new File(nombreArchivo);
								f1 = new File(nombreArchivo1);
								
								if(!(f.exists())){							
									f = new File(nombreArchivo);
									f.createNewFile();
									
									f1 = new File(nombreArchivo1);
									f1.createNewFile();
								}
								
								if(f.exists()){
									
									fw = new FileWriter(f,true);
									pw = new PrintWriter(fw);	
									
									fw1 = new FileWriter(f1,true);
									pw1 = new PrintWriter(fw1);		
									
									pw.println(linea1);
									pw.println(linea2);
									pw.println(linea3);
									pw.println(linea4);
									pw.println(linea5);
									pw.println(linea7);
									pw.println(linea6);
									fw.flush();
									pw.flush();
									
									
									pw1.println(linea1);
									pw1.println(linea2);
									pw1.println(linea3);
									pw1.println(linea4);
									pw1.println(linea5);
									pw1.println(linea7);
									pw1.println(linea6);
									fw1.flush();
									pw1.flush();
								}						
							}						
						}							
					
					//FIN NUEVA LOGICA*********************
					
				}else{
					respuesta = "SIN_ARTICULOS";
				}					
			
		}catch(Exception ex){			
			respuesta = "ERROR_LIBERACION =>"+ex;			
		}finally{
			
			try{
				
				if(null != f){
					f = null;
					f1 = null;
				}
				if(null != fw){
					fw.close();
					fw = null;
					
					fw1.close();
					fw1 = null;
				}
				if(null != pw){
					pw.close();
					pw = null;
					
					pw1.close();
					pw1 = null;
				}
				
			}catch(Exception ex){				
				ex.printStackTrace();
			}
		}
		
		return respuesta;
	}


	public String creaArchivoLiberacionnew(String clientein ,String codigon, String grupo){
		
		System.out.println("creaArchivoLiberacionnew => "+codigon+" <==> "+grupo);
		ArrayList<VentaPedidoBean> venta = BusquedaLiberacionesFacade.traeDetalleVentasPedidosAM(codigon,grupo);
		String respuesta = "";
		
		if(venta != null){
				File f = null, f1 = null;
				FileWriter fw = null,fw1 = null;
				PrintWriter pw = null ,pw1 = null;
				Utils util = new Utils();
				boolean escribir = true;
				
				String nombreArchivo = "OK_"+venta.get(0).getLocal()+"_"+venta.get(0).getLocalsap()+"_"+venta.get(0).getCod_lista_lib().replace("/","_")+"_"+venta.get(0).getGrupo()+"_"+util.traeFechaHoyFormatoString().replace("-","")+"_"+util.traeHoraFormatoxString().replace("-","")+"_"+util.numeroAleatorio()+"_FICHERO_CL.TXT";
				String nombreTemporal = "OK_"+venta.get(0).getLocal()+"_"+venta.get(0).getLocalsap()+"_"+venta.get(0).getCod_lista_lib().replace("/","_")+"_"+venta.get(0).getGrupo()+"_"+util.traeFechaHoyFormatoString().replace("-","")+"_"+util.traeHoraFormatoxString().replace("-","")+"_"+util.numeroAleatorio()+"_FICHERO_TEMP_CL.TXT";
				String ruta = "",rutat="";
				System.out.println("Archivo ===> "+nombreArchivo+"<=>"+nombreTemporal);
				
				String armazon_ep="",cristal_ep_od ="",cristal_ep_oi="";
				try{
						Properties prop = util.leeConfiguracion();
						String values = prop.getProperty("liberacion.ruta");
						InputStream is = null;
						ruta=values.trim();
						
						
						Properties prop1 = util.leeConfiguracion();
						String values1 = prop1.getProperty("liberacion.rutares");
						InputStream is1 = null;
						rutat = values1.trim();
						
						boolean existe_archivo = false;
						String isTrio = "ZENC";
						String isLentilla="";
						
						String mcli="",ccli="";
						
						String nombreTemporal1 = rutat+nombreTemporal;
						String nombreArchivo1 = (rutat+nombreArchivo).trim();
						
						nombreTemporal = ruta+nombreTemporal;
						nombreArchivo = (ruta+nombreArchivo).trim();
						
						
						
						String [] cod_lista_lib_temp  = venta.get(0).getCod_lista_lib().split("/");
						String codLocalSap = venta.get(0).getLocalsap();
						String cod_lista_lib_sap = codLocalSap+"/"+cod_lista_lib_temp[1]; 
						
						PedidosPendientesBean pedidosSeleccionado = PosVentaPedidoFacade.traePedidosSeleccionado(codigon);
						
						String linea1 = "1;"+isTrio+";1000;50;10;"+venta.get(0).getFecha_entrega()+";T;"+venta.get(0).getFecha()+";"+cod_lista_lib_sap+"/"+grupo+";CLP;1;"+venta.get(0).getFecha_entrega()+";;"+venta.get(0).getHora()+";"+venta.get(0).getFecha()+";"+util.traeHoraString()+";"+""+";"+""+";"+""+";";
						
						String linea2 = "2;AG;"+codLocalSap+";;;;;;";
						String codigoSAP = util.traeCodigoAgente(venta.get(0).getAgente());
						int codigoSap =0;
						if(null != codigoSAP){
							codigoSap = Integer.parseInt(codigoSAP);
						}
						
						String linea3 =	"2;ZV;"+util.formato_Numero_Ticket(codigoSap)+";;;;;;";
						ClienteBean cliente = util.traeCliente(null,clientein);
						String telefonoCasaCliente = "";
						String direcciondesp = "";
						if(null != cliente.getFono_casa()){
							telefonoCasaCliente = cliente.getFono_casa();
						}
						
						String linea4 = "2;ZC;1000001;"+cliente.getNombre()+" "+cliente.getApellido()+";"+cliente.getNif()+";"+telefonoCasaCliente+";;;"; //+direcciondesp+";";
						String linea5 = Constantes.STRING_BLANCO;
						String linea6 = Constantes.STRING_BLANCO;
						String linea7 =  Constantes.STRING_BLANCO;
						String linea =  Constantes.STRING_BLANCO;
						String lineatmp1 =  Constantes.STRING_BLANCO;
						String lineatmp2 =  Constantes.STRING_BLANCO;
						String lineatmp3 =  Constantes.STRING_BLANCO;
						String lineatmp4 =  Constantes.STRING_BLANCO;
		
						int condiciones=0;
						
						//correr la lista de detalles.
						if(null != venta){				
							
							for(VentaPedidoBean vta : venta){
								
								Date  d = new Date();
								
								
								
								String isMulti="N";
								String cdg_ident=vta.getCod_detalle_vta();
								String [] linea_enc = vta.getCod_detalle_vta().split("/");
		
								ProductosBean producto = util.traeProductoEsp(vta.getArticulo(),vta.getLocal(),codigon,grupo,codigon.concat("/").concat(linea_enc[2]));
								
								if("S".equals(producto.getDescatalogado())){
									//escribir = false;
									//break;
								}
								String codigo=codigon.substring(0, 3);
								System.out.println("TIPO ENCARGO ====> "+producto.getTipoe());
								if("MUL".equals(producto.getTipoe())){
									
									isMulti="S";
									
								
									ArrayList<VentaPedidoBean> lista_detalle_multi = util.traeDetalleLiberacionMulti(codigon, grupo);
									System.out.println("traeDetalleLiberacionMulti  codigon ==>"+codigon+"+ grupo ==>"+grupo);
									
									//ArrayList<VentaPedidoBean> lista_detalle_multi = new ArrayList<VentaPedidoBean>();
									if(null != lista_detalle_multi){
										VentaPedidoBean vtamul = vta;
										//for(VentaPedidoBean vtamul : lista_detalle_multi){
											
											GraduacionesBean graduacion  = util.traeGraduacionPedido(cliente.getCodigo(), vta.getFechagrad(), vta.getNumerograd());
																				
											ProductosBean productomulti = util.traeProductoEsp(vtamul.getArticulo(),vta.getLocal(),codigon,grupo,codigon.concat("/").concat(linea_enc[2]));
											
											int preciosiva=0,preciodto = 0,preciodif =0;
		
											double  ddto = Double.parseDouble(productomulti.getDto())/100;
											preciosiva = productomulti.getImporte()*2;
											
											preciodto = (int) (preciosiva * ddto);
											
											if(productomulti.getTarifalocal().equals("N")){
												preciodif = (int) Math.round((productomulti.getImporte()*1.19)-productomulti.getImporte());
											}
											
											System.out.println("MULTIOFERTAS ===> Precios DTO ==>"+ddto+" preciosiva ==>"+preciosiva+" preciodto =>"+preciodto);
		
											
											if("S".equals(productomulti.getDescatalogado())){
												//escribir = false;
												//break;
											}
											cdg_ident=String.valueOf(vtamul.getCodigo());
											
											//*************************									
											if("M".equals(productomulti.getTipoFamilia()) || "G".equals(producto.getTipoFamilia())){
												if (Constantes.STRING_ACTION_ARCLI.equals(producto.getCod_barra())) {
													mcli ="X";
												}
												
												linea5 = "10;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vtamul.getCantidad()+";UN;"+(productomulti.getImporte()==0?1:productomulti.getImporte())+";"+preciodif+";"+productomulti.getPrecio_costo()+";"+mcli+";"+codLocalSap+";"+productomulti.getCodigoSap()+";"+productomulti.getDescripcion()+";TEXTOPOSICION;;;;;;";
												condiciones++;
												//pw.println(linea5);
												
												
											}else if("C".equals(productomulti.getTipoFamilia()) && "D".equals(vtamul.getOjo())){
												
												
												//linea7 C D
												ProductosBean inforProducto = util.traeInfoproducto(vtamul.getArticulo());
												String color  = inforProducto.getColor();
												if(null == color){
													color = "";
												}
												
												ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacionMultioferta(vtamul.getCodigo());								
												String infoSuplementos="";
												String EB="";
												String CB="";
												String PR="";
												String PRC="";
												String DE="";
												String DC="";
												String AL="";
												String EC="";
												String TE="";
												
												if(null != listaSuple){
													
													for(SuplementopedidoBean suple:listaSuple){
														
														if("EB".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																EB = valor;
															}												
															
														}else if("CB".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																CB = valor;
															}													
															
														}else if("PR".equals(suple.getTratami())){
															String valor = suple.getValor().replace(".", ",");
															if("GRADUACION".equals(valor)){
																if(null != graduacion.getOD_base()){
																	PR = graduacion.getOD_base();
																}
																if(null != graduacion.getOD_cantidad()){
																	PRC = graduacion.getOD_cantidad();
																}												
															}
														}else if("DE".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																DE = valor;
															}
															
														}else if("DC".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																DC = valor;
															}
															
														}else if("AL".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																AL = valor;
															}													
															
														}else if("EC".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																EC = valor;
															}
															
														}else if("TE".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																TE = valor;
															}													
														}									
													}
													infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
													
												}else{
													infoSuplementos=";;;;;;;;";
												}
												
												//VALIDA DIAMETRO
												inforProducto.setEsfera(vta.getEsfera());
												inforProducto.setCilindro(vta.getCilindro());
												
												util.validaDiametroESFCL(inforProducto);
												String diametroStr = util.isEntero(inforProducto.getDesdediametro());
											
												
												String ejeStr = util.isEntero(vtamul.getEje());
												String esferaStr = util.isEntero(vta.getEsfera());
												String cilindroStr = util.isEntero(vta.getCilindro());
												String OD_nStr =""; 
												if(null != graduacion.getOD_n()){
													OD_nStr=util.isEntero(graduacion.getOD_n());
												}
													
												
												String OD_pStr = "";
												if(null != graduacion.getOD_p()){
													OD_pStr=util.isEntero(graduacion.getOD_p());
												}
												String OD_adicion="";
												if(null != graduacion.getOD_adicion()){
													OD_adicion = util.isEntero(graduacion.getOD_adicion());
													OD_adicion=OD_adicion.replace(",", ".");
												}
												/*if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
													ccli = "X";
												}*/
												if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
													ccli = "X";
													cristal_ep_oi = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",grupo);
													producto.setCodigoSap(cristal_ep_oi);
												}
												
												//STRING_ACTION_CCLI 
												linea7 = "20;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vtamul.getCantidad()+";UN;"+((productomulti.getImporte()*2)==0?1:(productomulti.getImporte()*2))+";"+(preciodif*2)+";"+(productomulti.getPrecio_costo()*2)+";"+ccli+";"+codLocalSap+";"+productomulti.getCodigoSap()+";"+productomulti.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vtamul.getOjo()+";"+diametroStr+";"+ejeStr+";"+OD_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vtamul.getUnahora()+";"+OD_nStr.replace(",", ".")+";"+OD_pStr.replace(",", ".")+";";
												condiciones++;
												
											}else if("C".equals(productomulti.getTipoFamilia()) && "I".equals(vtamul.getOjo())){
												
												
												//linea6 C I 
												ProductosBean inforProducto = util.traeInfoproducto(vtamul.getArticulo());
												String color  = inforProducto.getColor();
												if(null == color){
													color = "";
												}
												ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacionMultioferta(vtamul.getCodigo());								
												String infoSuplementos="";
												String EB="";
												String CB="";
												String PR="";
												String PRC="";
												String DE="";
												String DC="";
												String AL="";
												String EC="";
												String TE="";
												
												if(null != listaSuple){
													
													for(SuplementopedidoBean suple:listaSuple){
														
														if("EB".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																EB = valor;
															}												
															
														}else if("CB".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																CB = valor;
															}													
															
														}else if("PR".equals(suple.getTratami())){
															String valor = suple.getValor().replace(".", ",");
															if("GRADUACION".equals(valor)){
																if(null != graduacion.getOI_base()){
																	PR = graduacion.getOI_base();
																}
																if(null != graduacion.getOI_cantidad()){
																	PRC = graduacion.getOI_cantidad();
																}												
															}
														}else if("DE".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																DE = valor;
															}
															
														}else if("DC".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																DC = valor;
															}
															
														}else if("AL".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																AL = valor;
															}													
															
														}else if("EC".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																EC = valor;
															}
															
														}else if("TE".equals(suple.getTratami())){
															
															if(null != suple.getValor()){
																String valor = suple.getValor().replace(".", ",");
																TE = valor;
															}													
														}									
													}
													infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
													
												}else{
													infoSuplementos=";;;;;;;;";
												}
												//VALIDA DIAMETRO
												inforProducto.setEsfera(vta.getEsfera());
												inforProducto.setCilindro(vta.getCilindro());
												
												util.validaDiametroESFCL(inforProducto);
												String diametroStr = util.isEntero(inforProducto.getDesdediametro());
												String ejeStr = util.isEntero(vtamul.getEje());
												String esferaStr = util.isEntero(vta.getEsfera());
												String cilindroStr = util.isEntero(vta.getCilindro());
												String OI_nStr =""; 
												if(null != graduacion.getOI_n()){
													OI_nStr=util.isEntero(graduacion.getOI_n());
												}
													
												
												String OI_pStr = "";
												if(null != graduacion.getOI_p()){
													OI_pStr=util.isEntero(graduacion.getOI_p());
												}
												String OI_adicion="";
												if(null != graduacion.getOI_adicion()){
													OI_adicion = util.isEntero(graduacion.getOI_adicion());
													OI_adicion=OI_adicion.replace(",", ".");
												}
												/*if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
													ccli = "X";
												}*/
												
												if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
													ccli = "X";
													cristal_ep_oi = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",grupo);
													producto.setCodigoSap(cristal_ep_oi);
												}
												
												linea6 = "20;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vtamul.getCantidad()+";UN;"+((productomulti.getImporte()*2)==0?1:(productomulti.getImporte()*2))+";"+(preciodif *2)+";"+(productomulti.getPrecio_costo()*2)+";"+ccli+";"+codLocalSap+";"+productomulti.getCodigoSap()+";"+productomulti.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vtamul.getOjo()+";"+diametroStr+";"+ejeStr+";"+OI_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vtamul.getUnahora()+";"+OI_pStr.replace(",", ".")+";"+OI_nStr.replace(",", ".")+";";
												condiciones++;
												//pw.println(linea6);
												
												
											}else if("L".equals(producto.getTipoFamilia())){
												
												isLentilla="OK";
												ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
												String color  = inforProducto.getColor();
												if(null == color){
													color = "";
												}
												ContactologiaForm formularioContactologia = new ContactologiaForm();
												GraduacionesHelper helperContactologia = new GraduacionesHelper();
												formularioContactologia.setNumero_graduacion(String.valueOf(vta.getNumerograd()));
												formularioContactologia.setFecha_graduacion(vta.getFechagrad());
												formularioContactologia.setCliente(clientein);
												formularioContactologia = helperContactologia.traeContactologia(formularioContactologia);
												
												String ejeStr = "";
												String diametroStr = "";
												if("D".equalsIgnoreCase(vta.getOjo())){
													if(null != formularioContactologia.getO_colo()){
														color = formularioContactologia.getO_colo();
													}else{
														color="";
													}
													
													//VALIDA DIAMETRO contactologia
													diametroStr = formularioContactologia.getO_diamt();
													ejeStr = formularioContactologia.getO_eje();
												}else{
													if("I".equalsIgnoreCase(vta.getOjo())){
														if(null != formularioContactologia.getI_colo()){
															color = formularioContactologia.getI_colo();
														}else{
															color = "";
														}
														
														diametroStr = formularioContactologia.getI_diamt();
														ejeStr = formularioContactologia.getI_eje();
													}
												}			
																			
												
												
												int cantidad = 0;
												int cantidad_vendida = vta.getCantidad();
												
												/*if(venta.isSin_cdd()){
													cantidad = cantidad_vendida - venta.getCantidad_cdd();
												}else{
													cantidad = 0;
												}*/
												
												//linea para las lentillas graduables
												linea1 = "1;ZENK;1000;50;10;"+vta.getFecha_entrega()+";T;"+vta.getFecha()+";"+cod_lista_lib_sap+"/"+grupo+";CLP;1;"+util.fechaEntregaLiberacion(vta.getFecha_entrega())+";;"+vta.getHora()+";"+util.traeFechaHoyFormateadaString()+";"+util.traeHoraString()+";;;;";
												//LMARIN 20130912
												//LMARIN 20180327 , SE ELIMINA LA linea6 , YA QUE SE LIBERARAN INDIVIDUALMENTE LAS LENTILLAS

												if(producto.getCodigo().equals("2746400000000") || producto.getCodigo().equals("2749000000000") || producto.getCodigo().equals("2746800000000")){
													if("D".equals(vta.getOjo())){
														linea5 = "30;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+((productomulti.getImporte()*2)==0?1:(productomulti.getImporte()*2))+";"+Math.round(((productomulti.getImporte()*2)*1.19)-(productomulti.getImporte()*2))+";"+preciodto+";"+mcli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;;;;;"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
													}else{
														linea5 = "30;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+((productomulti.getImporte()*2)==0?1:(productomulti.getImporte()*2))+";"+Math.round(((productomulti.getImporte()*2)*1.19)-(productomulti.getImporte()*2))+";"+preciodto+";"+mcli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;;;;;"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
													}	
												}else{
													if("D".equals(vta.getOjo())){
														linea5 ="30;"+formularioContactologia.getO_radio2().replace(",", ".")+";";//+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
														//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color;
														//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getO_radio1().replace(",", ".")+";"+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
													}else{
														linea5 ="30;"+formularioContactologia.getI_radio2().replace(",", ".")+";";//+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
														//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color;
														//linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getI_radio1().replace(",", ".")+";"+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
													}	
												}	
												
												 
											}
											//*************************
											
										//}//fin for vtamul
										
									}
									
									
									
								}else{//ARTICULOS NO SON MULTIOFERTAS.
									
									int preciosiva=0,preciodto = 0,productoimpdto=0,prodimp=0;
		
									double  ddto = Double.parseDouble(producto.getDto())/100;
									preciosiva = (int) Math.round((producto.getPrecio()/1.19));
									productoimpdto = (int) (producto.getPrecio() * ddto);
									preciodto = (int) (preciosiva * ddto);

									//preciodto = (int) (preciosiva * ddto);
									if(producto.getTarifalocal().trim().equals("N")){
										prodimp = (int)((producto.getPrecio()-productoimpdto) -(preciosiva-preciodto));
									}
									
									System.out.println("Precios DTO ==>"+ddto+" preciosiva ==>"+preciosiva+" productoimpdto =>"+productoimpdto+" preciodto =>"+preciodto+" prodimp =>"+prodimp);
		
									GraduacionesBean graduacion  = util.traeGraduacionPedido(cliente.getCodigo(), vta.getFechagrad(), vta.getNumerograd());
									
									//EN CASO DE SER ARCLI
									boolean arcli = false;
									String armazon = "";
									String puente = "";
									String diagonal = "";
									String vertical = "";
									String horizontal = "";
									
									if("M".equals(producto.getTipoFamilia()) || "G".equals(producto.getTipoFamilia())){
										
										//SI MONTURA ES ARCLI DEBE AGREGAR LOS DATOS ADICIONALES
										if (Constantes.STRING_ACTION_ARCLI.equals(producto.getCod_barra())) {
											
											ProductosBean prod = new ProductosBean();
											prod.setIdent(vta.getCodigo());
											PosVentaPedidoFacade.traeAdicionalArcli(prod);
											
											arcli = true;
											mcli="X";
											armazon = prod.getArcli_armazon();
											puente = String.valueOf(prod.getArcli_puente());
											diagonal = String.valueOf(prod.getArcli_diagonal());
											horizontal = String.valueOf(prod.getArcli_horizontal());
											vertical = String.valueOf(prod.getArcli_vertical());
											//Rescato armazon asociado al encargo_padre 20160830
											/*armazon_ep = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"M",venta.getGrupo());
											if(!armazon_ep.equals("") && armazon_ep != null){
												producto.setCodigoSap(armazon_ep);
											}*/
										}
									
										linea5 = "10;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+((preciosiva- preciodto)==0?1:(preciosiva- preciodto))+";"+prodimp+";"+preciodto+";"+mcli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;"+armazon+";"+puente+";"+diagonal+";"+horizontal+";"+vertical+";";
												
										condiciones++;
										
									}else if("C".equals(producto.getTipoFamilia()) && "D".equals(vta.getOjo())){
										
										int a = 0,b = 0,c = 0;

										double  ddtoc =(1 - Double.parseDouble(producto.getDtototal())/100);
										preciosiva = (int) Math.round((producto.getPrecio()/1.19));
										
										a = (int)(preciosiva+(preciosiva * ddtoc));
										c = (int)((preciosiva*2) - (preciosiva+(preciosiva * ddtoc)));
										
										if(producto.getTarifalocal().trim().equals("N")){
											b = (int)( (producto.getPrecio()+(producto.getPrecio()* ddtoc)) - (preciosiva+(preciosiva * ddtoc)));
										}
										
										System.out.println("a =>"+a+" b =>"+b+" c==>"+c);
										
										//linea7 C D
										ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
										String color  = inforProducto.getColor();
										if(null == color){
											color = "";
										}
										
										ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacion(vta.getCodigo());								
										String infoSuplementos="";
										String EB="";
										String CB="";
										String PR="";
										String PRC="";
										String DE="";
										String DC="";
										String AL="";
										String EC="";
										String TE="";
										
										if(null != listaSuple){
											
											for(SuplementopedidoBean suple:listaSuple){
												
												if("EB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EB = valor;
													}												
													
												}else if("CB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														CB = valor;
													}													
													
												}else if("PR".equals(suple.getTratami())){
													String valor = suple.getValor().replace(".", ",");
													if("GRADUACION".equals(valor)){
														if(null != graduacion.getOD_base()){
															PR = graduacion.getOD_base();
														}
														if(null != graduacion.getOD_cantidad()){
															PRC = graduacion.getOD_cantidad();
														}												
													}
												}else if("DE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DE = valor;
													}
													
												}else if("DC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DC = valor;
													}
													
												}else if("AL".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														AL = valor;
													}													
													
												}else if("EC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EC = valor;
													}
													
												}else if("TE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														TE = valor;
													}													
												}									
											}
											infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
											
										}else{
											infoSuplementos=";;;;;;;;";
										}
										
										//VALIDA DIAMETRO
										inforProducto.setEsfera(vta.getEsfera());
										inforProducto.setCilindro(vta.getCilindro());
										
										util.validaDiametroESFCL(inforProducto);
										String diametroStr = util.isEntero(inforProducto.getDesdediametro());
										String ejeStr = util.isEntero(vta.getEje());
										String esferaStr = util.isEntero(vta.getEsfera());
										String cilindroStr = util.isEntero(vta.getCilindro());
										String OD_nStr =""; 
										if(null != graduacion.getOD_n()){
											OD_nStr=util.isEntero(graduacion.getOD_n());
										}
											
										
										String OD_pStr = "";
										if(null != graduacion.getOD_p()){
											OD_pStr=util.isEntero(graduacion.getOD_p());
										}
										String OD_adicion="";
										if(null != graduacion.getOD_adicion()){
											OD_adicion = util.isEntero(graduacion.getOD_adicion());
											OD_adicion=OD_adicion.replace(",", ".");
										}
										
										if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
											ccli = "X";
											cristal_ep_od = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",grupo);
											producto.setCodigoSap(cristal_ep_od);
										}
						
										linea7 = "20;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(a==0?1:a)+";"+b+";"+c+";"+ccli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OD_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vta.getUnahora()+";"+OD_nStr.replace(",", ".")+";"+OD_pStr.replace(",", ".")+";";
											
										condiciones++;
										
									}else if("C".equals(producto.getTipoFamilia()) && "I".equals(vta.getOjo())){
										
										int a = 0,b = 0,c = 0;

										double  ddtoc =(1 - Double.parseDouble(producto.getDtototal())/100);
										preciosiva = (int) Math.round((producto.getPrecio()/1.19));
										
										a = (int)(preciosiva+(preciosiva * ddtoc));
										c = (int)((preciosiva*2) - (preciosiva+(preciosiva * ddtoc)));
										
										if(producto.getTarifalocal().trim().equals("N")){
											b = (int)( (producto.getPrecio()+(producto.getPrecio()* ddtoc)) - (preciosiva+(preciosiva * ddtoc)));
										}
										
										System.out.println("a =>"+a+" b =>"+b+" c==>"+c);
										
										//linea6 C I 
										ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
										String color  = inforProducto.getColor();
										if(null == color){
											color = "";
										}
										
										ArrayList<SuplementopedidoBean> listaSuple = util.traeSuplementosPedidoLiberacion(vta.getCodigo());								
										String infoSuplementos="";
										String EB="";
										String CB="";
										String PR="";
										String PRC="";
										String DE="";
										String DC="";
										String AL="";
										String EC="";
										String TE="";
										if(null != listaSuple){
											
											for(SuplementopedidoBean suple:listaSuple){
												
												if("EB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EB = valor;
													}												
													
												}else if("CB".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														CB = valor;
													}													
													
												}else if("PR".equals(suple.getTratami())){
													String valor = suple.getValor().replace(".", ",");
													if("GRADUACION".equals(valor)){
														if(null != graduacion.getOI_base()){
															PR = graduacion.getOI_base();
														}
														if(null != graduacion.getOI_cantidad()){
															PRC = graduacion.getOI_cantidad();
														}												
													}
												}else if("DE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DE = valor;
													}
													
												}else if("DC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														DC = valor;
													}
													
												}else if("AL".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														AL = valor;
													}													
													
												}else if("EC".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														EC = valor;
													}
													
												}else if("TE".equals(suple.getTratami())){
													
													if(null != suple.getValor()){
														String valor = suple.getValor().replace(".", ",");
														TE = valor;
													}													
												}									
											}
											infoSuplementos=""+EB+";"+CB+";"+PRC+";"+PR+";"+DE+";"+DC+";"+AL+";"+EC+";"+TE+"";
											
										}else{
											infoSuplementos=";;;;;;;;";
										}
										
										//infoSuplementos="EB;CB;PR;;DE;DC;AL;EC;TE;";
										//VALIDA DIAMETRO
										inforProducto.setEsfera(vta.getEsfera());
										inforProducto.setCilindro(vta.getCilindro());
										
										util.validaDiametroESFCL(inforProducto);
										String diametroStr = util.isEntero(inforProducto.getDesdediametro());
										String ejeStr = util.isEntero(vta.getEje());
										String esferaStr = util.isEntero(vta.getEsfera());
										String cilindroStr = util.isEntero(vta.getCilindro());
										
										String OI_nStr =""; 
										if(null != graduacion.getOI_n()){
											OI_nStr=util.isEntero(graduacion.getOI_n());
										}
											
										
										String OI_pStr = "";
										if(null != graduacion.getOI_p()){
											OI_pStr=util.isEntero(graduacion.getOI_p());
										}
										
										String OI_adicion="";
										if(null != graduacion.getOI_adicion()){
											OI_adicion = util.isEntero(graduacion.getOI_adicion());
											OI_adicion=OI_adicion.replace(",", ".");
										}
										
										if (Constantes.STRING_ACTION_CCLI.equals(producto.getCod_barra())) {
											ccli = "X";
											cristal_ep_oi = PosUtilesFacade.traeArticuloRel(cod_lista_lib_temp[0]+"/"+cod_lista_lib_temp[1],"C",grupo);
											producto.setCodigoSap(cristal_ep_oi);
										}
										
										linea6 = "20;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(a==0?1:a)+";"+b+";"+c+";"+ccli+";"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+inforProducto.getIndiceArticuloLib()+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OI_adicion+";"+infoSuplementos+";"+esferaStr.replace(",", ".")+";"+cilindroStr.replace(",", ".")+";"+vta.getUnahora()+";"+OI_nStr.replace(",", ".")+";"+OI_pStr.replace(",", ".")+";";
										
									
										condiciones++;
										
									}else if("L".equals(producto.getTipoFamilia())){
										System.out.println("Paso por lentilla");
										isLentilla = "OK";
										ProductosBean inforProducto = util.traeInfoproducto(vta.getArticulo());
										String color  = "";
										if(null == color){
											color = "";
										}
										ContactologiaForm formularioContactologia = new ContactologiaForm();
										GraduacionesHelper helperContactologia = new GraduacionesHelper();
										int num = (int) vta.getNumerograd();
										formularioContactologia.setNumero_graduacion(String.valueOf(num));
										formularioContactologia.setFecha_graduacion(vta.getFechagrad());
										formularioContactologia.setCliente(clientein);
										
										formularioContactologia = helperContactologia.traeContactologia(formularioContactologia);
										
										String ejeStr = "";
										//VALIDA DIAMETRO contactologia
										String diametroStr = "";
										if("D".equalsIgnoreCase(vta.getOjo())){
											if(null != formularioContactologia.getO_colo()){
												color = formularioContactologia.getO_colo();
											}else{
												color = "";
											}
											
											diametroStr = formularioContactologia.getO_diamt();
											ejeStr = formularioContactologia.getO_eje();
										}else{
											if("I".equalsIgnoreCase(vta.getOjo())){
												if(null != formularioContactologia.getI_colo()){
													color = formularioContactologia.getI_colo();
												}else{
													color = "";
												}
												
												diametroStr = formularioContactologia.getI_diamt();
												ejeStr = formularioContactologia.getI_eje();
											}
										}
										
										
										int cantidad = 0;
										int cantidad_vendida = vta.getCantidad();
										
										/*if(venta.isSin_cdd()){
											cantidad = cantidad_vendida - venta.getCantidad_cdd();
										}else{
											cantidad = 0;
										}*/
										
										String OI_adicion_len="";
										if(null != formularioContactologia.getI_adic()){
											OI_adicion_len = formularioContactologia.getI_adic();
											OI_adicion_len = OI_adicion_len.replace(",", ".");
										}
										
										String OD_adicion_len="";
										if(null != formularioContactologia.getO_adic()){
											OD_adicion_len = formularioContactologia.getO_adic();
											OD_adicion_len = OD_adicion_len.replace(",", ".");
										}
										
										//linea para las lentillas graduables
										linea1 = "1;ZENK;1000;50;10;"+vta.getFecha_entrega()+";T;"+vta.getFecha()+";"+cod_lista_lib_sap+"/"+grupo+";CLP;1;"+util.fechaEntregaLiberacion(vta.getFecha_entrega())+";;"+vta.getHora()+";"+util.traeFechaHoyFormateadaString()+";"+util.traeHoraString()+";;;;";
										
										//LMARIN 20130912
										//LMARIN 20180327 , SE ELIMINA LA linea6 , YA QUE SE LIBERARAN INDIVIDUALMENTE LAS LENTILLAS
										if(producto.getCodigo().equals("2746400000000") || producto.getCodigo().equals("2749000000000") || producto.getCodigo().equals("2746800000000")){
											if("D".equals(vta.getOjo())){
												linea5 = "30;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;"+OD_adicion_len+";;;;"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
											}else{
												linea5 = "30;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";;"+OI_adicion_len+";;;;"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".").concat("0")+";"+"0.00"+";"+cantidad+"";
											}
										}else{
											///aca estamos !!!!!!!!!!!!!!!!
											if("D".equals(vta.getOjo())){
												linea5 = "30;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OD_adicion_len+";;"+formularioContactologia.getO_radio1().replace(",", ".")+";"+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
											}else{
												linea5= "30;"+cod_lista_lib_sap+"/"+grupo+";"+isMulti+";"+cod_lista_lib_sap+"/"+linea_enc[2]+";"+vta.getCantidad()+";UN;"+(((preciosiva- preciodto)*vta.getCantidad())==0?1:((preciosiva- preciodto)*vta.getCantidad()))+";"+(prodimp*vta.getCantidad())+";"+(preciodto*vta.getCantidad())+";;"+codLocalSap+";"+producto.getCodigoSap()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";"+OI_adicion_len+";;"+formularioContactologia.getI_radio1().replace(",", ".")+";"+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
											}
										}
																			
										/*if("D".equals(vta.getOjo())){
											linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getO_radio1().replace(",", ".")+";"+formularioContactologia.getO_radio2().replace(",", ".")+";"+formularioContactologia.getO_radio3().replace(",", ".")+";"+formularioContactologia.getO_diamp().replace(",", ".")+";"+formularioContactologia.getO_esfera().replace(",", ".")+";"+formularioContactologia.getO_cilindro().replace(",", ".")+";"+cantidad+"";
										}else{
											linea = "30;"+cdg_ident+";"+isMulti+";"+cdg_ident+";"+vta.getCantidad()+";UN;"+codLocalSap+";"+producto.getCodigo()+";"+producto.getDescripcion()+";TEXTOPOSICION;;"+color+";"+vta.getOjo()+";"+diametroStr+";"+ejeStr+";;;"+formularioContactologia.getI_radio1().replace(",", ".")+";"+formularioContactologia.getI_radio2().replace(",", ".")+";"+formularioContactologia.getI_radio3().replace(",", ".")+";"+formularioContactologia.getI_diamp().replace(",", ".")+";"+formularioContactologia.getI_esfera().replace(",", ".")+";"+formularioContactologia.getI_cilindro().replace(",", ".")+";"+cantidad+"";
										}*/																																																		
										
									}						
										
								}// fin else					
							
							}//fin for
							
							//*****************************************************************************************
							
							//LMARIN 20160831 / MODIFICO PARA COMPROBAR QUE LAS LINEAS DE LAS LENTILLAS, VENGAN COMPLETAS
							if("OK".equals(isLentilla)){
								if(!(Constantes.STRING_BLANCO.equals(linea5))){
									//pw.println(linea5);											
								}else{
									linea5="";
									//respuesta = "ERROR_ESCRITURA_LINEA 5";
								}
								
								if(!(Constantes.STRING_BLANCO.equals(linea6))){
									//pw.println(linea6);
								}else{
									linea6="";
									//pw.println(linea6);
									//respuesta = "ERROR_ESCRITURA_LINEA 6";
								}		
								/*if(!(Constantes.STRING_BLANCO.equals(linea))){
									//pw.println(linea);
								}else{
									respuesta = "ERROR_ESCRITURA_LINEA";
								}*/								
							}else{
								if(!(Constantes.STRING_BLANCO.equals(linea5))){
									//pw.println(linea5);											
								}else{
									respuesta = "ERROR_ESCRITURA_LINEA 5";
								}
								if(!(Constantes.STRING_BLANCO.equals(linea7))){
									//pw.println(linea7);
								}else{
									respuesta = "ERROR_ESCRITURA_LINEA 7";
								}
								if(!(Constantes.STRING_BLANCO.equals(linea6))){
									//pw.println(linea6);
								}else{
									respuesta = "ERROR_ESCRITURA_LINEA 6";
								}								
							}					
							
							//****************************************************************************************************
							//NUEVA LOGICA*************************
									
							if("OK".equals(isLentilla)){
									if("".equals(respuesta)){
										
										//f = new File(ruta+nombreArchivo.trim());
										f = new File(nombreArchivo);
										f1 = new File(nombreArchivo1);
										
										if(!(f.exists())){							
											f = new File(nombreArchivo);
											f.createNewFile();
											
											f1 = new File(nombreArchivo1);
											f1.createNewFile();
										}
										
										//LMARIN 20160831 / MODIFICO PARA QUE SE IMPRIMAN HASTA 6 LINEAS EEN EL ARCHIVO DE LAS LENTILLAS
										
										if(f.exists()){
											
											fw = new FileWriter(f,true);
											pw = new PrintWriter(fw);
											
											fw1 = new FileWriter(f1,true);
											pw1 = new PrintWriter(fw1);
											
											pw.println(linea1);
											pw.println(linea2);
											pw.println(linea3);
											pw.println(linea4);
											pw.println(linea5);
											pw.println(linea6);
											fw.flush();
											pw.flush();
											
											pw1.println(linea1);
											pw1.println(linea2);
											pw1.println(linea3);
											pw1.println(linea4);
											pw1.println(linea5);
											pw1.println(linea6);
											fw1.flush();
											pw1.flush();
											
										}						
									}
							}else{
									
									if("".equals(respuesta)){
										
										//f = new File(ruta+nombreArchivo.trim());
										f = new File(nombreArchivo);
										f1 = new File(nombreArchivo1);
										
										if(!(f.exists())){							
											f = new File(nombreArchivo);
											f.createNewFile();
											
											f1 = new File(nombreArchivo1);
											f1.createNewFile();
										}
										
										if(f.exists()){
											
											fw = new FileWriter(f,true);
											pw = new PrintWriter(fw);	
											
											fw1 = new FileWriter(f1,true);
											pw1 = new PrintWriter(fw1);		
											
											pw.println(linea1);
											pw.println(linea2);
											pw.println(linea3);
											pw.println(linea4);
											pw.println(linea5);
											pw.println(linea7);
											pw.println(linea6);
											fw.flush();
											pw.flush();
											
											
											pw1.println(linea1);
											pw1.println(linea2);
											pw1.println(linea3);
											pw1.println(linea4);
											pw1.println(linea5);
											pw1.println(linea7);
											pw1.println(linea6);
											fw1.flush();
											pw1.flush();
										}						
									}						
								}							
							
							//FIN NUEVA LOGICA*********************
							
						}else{
							respuesta = "SIN_ARTICULOS";
						}					
					
				}catch(Exception ex){			
					respuesta = "ERROR_LIBERACION =>"+ex;			
				}finally{
					
					try{
						
						if(null != f){
							f = null;
							f1 = null;
						}
						if(null != fw){
							fw.close();
							fw = null;
							
							fw1.close();
							fw1 = null;
						}
						if(null != pw){
							pw.close();
							pw = null;
							
							pw1.close();
							pw1 = null;
						}
						
					}catch(Exception ex){				
						ex.printStackTrace();
					}
				}
				return respuesta;
		}else{
			respuesta = "LIBERADO";
			return respuesta;
		}
		
	}

	

}
