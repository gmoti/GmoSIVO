package cl.gmo.pos.venta.web.helper;


import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.SalidaArchivo;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.beans.ListaEstadosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.facade.BusquedaLiberacionesFacade;
import cl.gmo.pos.venta.web.forms.BusquedaLiberacionesForm;
import cl.gmo.pos.venta.web.forms.ContactologiaForm;

public class BusquedaLiberacionesHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public BusquedaLiberacionesHelper(){}
	
	
	public ArrayList<VentaPedidoBean> traeVentasPedidos(String local, String fecha, String fechaHasta,String estado){
		log.info("BusquedaLiberacionesHelper:traeVentasPedidos inicio");
		ArrayList<VentaPedidoBean> lista_ventas_pedido = new ArrayList<VentaPedidoBean>();
		lista_ventas_pedido = BusquedaLiberacionesFacade.traeVentasPedidos(local, fecha, fechaHasta,estado);
		BusquedaLiberacionesForm formulario = new BusquedaLiberacionesForm();
		if(null != lista_ventas_pedido){
			for(VentaPedidoBean venta : lista_ventas_pedido){
				formulario.setCodigoDetalle(venta.getCod_lista_lib());
				formulario.setGrupoDetalle(venta.getGrupo());
				formulario.setLineaDetalle(String.valueOf(venta.getLinea()));
				ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
				lista_detalle_pedido = this.traeDetalleLiberacionVentasPedidos(formulario);
				boolean isLentilla = this.isLentilla(lista_detalle_pedido);
								
				if(isLentilla){
					boolean isLentillaValida = this.isLentillaValida(lista_detalle_pedido);
					if(isLentillaValida){
						boolean respuestaOjo = this.isLentillaValidaOjo(lista_detalle_pedido);
						if(respuestaOjo){
							venta.setRespuestaValidaLiberacion("OK_L");
						}else{
							venta.setRespuestaValidaLiberacion("OJO_L");
						}
						
					}else{
						venta.setRespuestaValidaLiberacion("GRADUACION_L");
					}
					
				}else{
					boolean isTrioValido = this.isTrioValidoDetalleLiberacion(lista_detalle_pedido);
					if(isTrioValido){
						boolean tieneGraduacionTrio = this.tieneGraduacionTrio(lista_detalle_pedido);
						if(tieneGraduacionTrio){							
							venta.setRespuestaValidaLiberacion("OK");//cambiar a OK
						}else{
							//falta de graduacion
							venta.setRespuestaValidaLiberacion("GRADUACION");
						}
					}else{
						//no tiene trio valido
						venta.setRespuestaValidaLiberacion("TRIO");
					}
				}				
			}
		}
		for(VentaPedidoBean b:lista_ventas_pedido){
			System.out.println("ARTICULO traeVentasPedidos ===>"+b.getArticulo());
		}
		return lista_ventas_pedido;
		
	}
	
	public boolean isLentillaValidaOjo(ArrayList<VentaPedidoBean> lista_detalle_pedido){
		boolean respuesta=false;
		try{
			
			if(null != lista_detalle_pedido){
				for(VentaPedidoBean ventaDetalle : lista_detalle_pedido){
					if("L".equals(ventaDetalle.getTipo_familia())){
						if("D".equalsIgnoreCase(ventaDetalle.getOjo())){
							respuesta=true;
						}
						if("I".equalsIgnoreCase(ventaDetalle.getOjo())){
							respuesta=true;
						}	
					}
				}
			}
			
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			respuesta=false;
		}
		return respuesta;
	}	
	
	public boolean isLentilla(ArrayList<VentaPedidoBean> lista_detalle_pedido){
		boolean respuesta=false;
		try{
			
			if(null != lista_detalle_pedido){
				for(VentaPedidoBean ventaDetalle : lista_detalle_pedido){
					if("L".equals(ventaDetalle.getTipo_familia())){
						respuesta=true;
					}
				}
			}
			
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			respuesta=false;
		}
		return respuesta;
	}
	
	
	public boolean isLentillaValida(ArrayList<VentaPedidoBean> lista_detalle_pedido){
		boolean respuesta=false;
		try{
			
			if(null != lista_detalle_pedido){
				for(VentaPedidoBean ventaDetalle : lista_detalle_pedido){
					if("L".equals(ventaDetalle.getTipo_familia())){
						if(null != ventaDetalle.getFechagrad() && !("".equals(ventaDetalle.getFechagrad()))){
							if(0.0 < ventaDetalle.getNumerograd()){
								respuesta = true;
							}
						}						
					}
				}
			}
			
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			respuesta=false;
		}
		return respuesta;
	}
	
	/**
	 * Valida que las lineas de los cristales esten asociadas a una graduacion
	 * @param lista_detalle_pedido
	 * @return
	 */
	public boolean tieneGraduacionTrio(ArrayList<VentaPedidoBean> lista_detalle_pedido){
		boolean respuesta = false;
		boolean isValidoDerecho = false;
		boolean isValidoIzquierdo = false;
		boolean isLentilla = false;
		try{
		
			if(null != lista_detalle_pedido){
				for(VentaPedidoBean ventaDetalle : lista_detalle_pedido){
					
					if("C".equals(ventaDetalle.getTipo_familia()) && "D".equals(ventaDetalle.getOjo())){
						if(null != ventaDetalle.getFechagrad() && !("".equals(ventaDetalle.getFechagrad()))){
							if(0.0 < ventaDetalle.getNumerograd()){
								isValidoDerecho = true;
							}																					
						}
					}					
					if("C".equals(ventaDetalle.getTipo_familia()) && "I".equals(ventaDetalle.getOjo())){
						if(null != ventaDetalle.getFechagrad() && !("".equals(ventaDetalle.getFechagrad()))){
							if(0.0 < ventaDetalle.getNumerograd()){
								isValidoIzquierdo = true;
							}							
						}
					}
		
				}
				
				if(isValidoDerecho == true && isValidoIzquierdo == true ){
					respuesta=true;
				}
			}
			
			
		}catch(Exception ex){
			respuesta = false;
			System.out.println(ex.getMessage());
		}
		return respuesta;
	}
	
	/**
	 * Valida si la lista es un trio valido que se compone de una montura y dos cristales dividos en ojo derecho e izquierdo
	 * @param lista_detalle_pedido
	 * @return
	 */
	public boolean isTrioValidoDetalleLiberacion(ArrayList<VentaPedidoBean> lista_detalle_pedido){
		boolean respuesta = false;
		
		try{
			
			int contarM =0;
			int contarD =0;
			int contarI =0;
			int contar = 0;
			int res  = 0;
			if(null != lista_detalle_pedido){
				for(VentaPedidoBean ventaDetalle : lista_detalle_pedido){
					System.out.println("FAMILIA ===> "+ventaDetalle.getTipo_familia()+" <=> "+ventaDetalle.getDescripcion());
					if("M".equals(ventaDetalle.getTipo_familia()) || "G".equals(ventaDetalle.getTipo_familia())){												
						contarM++;												
					}					
					if("C".equals(ventaDetalle.getTipo_familia()) && "D".equals(ventaDetalle.getOjo())){													
						contarD++;						
					}					
					if("C".equals(ventaDetalle.getTipo_familia()) && "I".equals(ventaDetalle.getOjo())){													
						contarI++;										
					}	
					if("C".equals(ventaDetalle.getTipo_familia()) && "".equals(ventaDetalle.getOjo()) ){
						contar++;
					}
					if("C".equals(ventaDetalle.getTipo_familia()) && null == ventaDetalle.getOjo()){
						contar++;
					}
					if("C".equals(ventaDetalle.getTipo_familia()) && null == ventaDetalle.getOjo()){
						contar++;
					}
				}	
				
				if(contarM == 1 && contarD == 1 && contarI ==1){
					if(contar == 0){
						respuesta = true;
					}
				}	
			
			}		
			
		}catch(Exception ex){
			respuesta = false;
			System.out.println(ex.getMessage());
		}
		return respuesta;
	}
	
	
	
	public ArrayList<VentaPedidoBean> traeDetalleVentasPedidos(BusquedaLiberacionesForm formulario){
		log.info("BusquedaLiberacionesHelper:traeDetalleVentasPedidos inicio");
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		ArrayList<VentaPedidoBean> lista_detalle_aux = new ArrayList<VentaPedidoBean>();
		
		try{
			
			if(null != formulario.getListaPedidos()){
				for(VentaPedidoBean vta : formulario.getListaPedidos()){
					
					if(vta.isChecked()){
						lista_detalle_aux = BusquedaLiberacionesFacade.traeDetalleVentasPedidos(vta.getCod_lista_lib(), vta.getGrupo());
						
						for(VentaPedidoBean venta : lista_detalle_aux){
							lista_detalle_pedido.add(venta);
						}
					}
				}
			}
			
		}catch(Exception ex){
			log.error("BusquedaLiberacionesHelper:traeDetalleVentasPedidos error catch",ex);
			return lista_detalle_pedido;
		}
		return lista_detalle_pedido;
	}
	
public ArrayList<VentaPedidoBean> traeDetalleLiberacionVentasPedidos(BusquedaLiberacionesForm formulario){
	log.info("BusquedaLiberacionesHelper:traeDetalleLiberacionVentasPedidos inicio");
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		ArrayList<VentaPedidoBean> lista_detalle_aux = new ArrayList<VentaPedidoBean>();
		boolean isLenteContacto=false;
		try{				
					
			lista_detalle_aux = BusquedaLiberacionesFacade.traeDetalleVentasPedidos(formulario.getCodigoDetalle(), formulario.getGrupoDetalle());
			
			for(VentaPedidoBean v : lista_detalle_aux){
				
				System.out.println("ARTICULO traeDetalleLiberacionVentasPedidos==> "+v.getArticulo());
			}
			
			
			
			
			for(VentaPedidoBean venta : lista_detalle_aux){
				
				if("L".equals(venta.getTipo_familia())){
					int lineaFormulario = this.isEntero(formulario.getLineaDetalle());			
					isLenteContacto=true;
					//if(venta.getLinea() == lineaFormulario){
						Utils util = new Utils();
						
						ContactologiaForm formularioContactologia = new ContactologiaForm();
						GraduacionesHelper helperContactologia = new GraduacionesHelper();
						formularioContactologia.setNumero_graduacion(String.valueOf(util.convertirEntero(String.valueOf(venta.getNumerograd()))));
						formularioContactologia.setFecha_graduacion(venta.getFechagrad());
						formularioContactologia.setCliente(venta.getId_cliente());
						formularioContactologia = helperContactologia.traeContactologia(formularioContactologia);
						if("D".equalsIgnoreCase(venta.getOjo())){
							if(null != formularioContactologia.getO_esfera() && !("".equals(formularioContactologia.getO_esfera()))){
								venta.setEsfera(Double.parseDouble(formularioContactologia.getO_esfera()));
							}else{
								venta.setEsfera(999);
							}
							
							if(null != formularioContactologia.getO_cilindro() && !("".equals(formularioContactologia.getO_cilindro()))){
								venta.setCilindro(Double.parseDouble(formularioContactologia.getO_cilindro()));
							}else{
								venta.setCilindro(999);
							}
							
						}else{
							if("I".equalsIgnoreCase(venta.getOjo())){
								if(null != formularioContactologia.getI_esfera() && !("".equals(formularioContactologia.getI_esfera()))){
									venta.setEsfera(Double.parseDouble(formularioContactologia.getI_esfera()));
								}else{
									venta.setEsfera(999);
								}
								
								if(null != formularioContactologia.getI_cilindro() && !("".equals(formularioContactologia.getI_cilindro()))){
									venta.setCilindro(Double.parseDouble(formularioContactologia.getI_cilindro()));
								}else{
									venta.setCilindro(999);
								}
							}
						}
						lista_detalle_pedido.add(venta);
					
					//}
					
				}else{
					lista_detalle_pedido.add(venta);
					
				}
				
			}
			
			if(isLenteContacto){
				ArrayList<VentaPedidoBean> lista_detalle_aux2 = new ArrayList<VentaPedidoBean>();
				if(null != lista_detalle_pedido){
					for(VentaPedidoBean venta: lista_detalle_pedido){
						if("L".equals(venta.getTipo_familia())){
							lista_detalle_aux2.add(venta);
						}
					}
					/*for(VentaPedidoBean v : lista_detalle_aux2){
						System.out.println("CODIGO PED aux2 ===>"+v.getCdg()+" <===> LINEA aux2 =>"+v.getLinea()+" GRUPO aux2 <==> "+v.getGrupo());
					}*/
					return lista_detalle_aux2;
				}
			}
		}catch(Exception ex){
			log.error("BusquedaLiberacionesHelper:traeDetalleLiberacionVentasPedidos error catch",ex);
			return lista_detalle_pedido;
		}
		return lista_detalle_pedido;
	}
	
	public ArrayList<SuplementopedidoBean> traeSuplementosPedidos(BusquedaLiberacionesForm formulario){
		log.info("BusquedaLiberacionesHelper:traeDetalleLiberacionVentasPedidos inicio");
		ArrayList<SuplementopedidoBean> lista_suplemento_pedido = new ArrayList<SuplementopedidoBean>();
		
		try{
			
			lista_suplemento_pedido = BusquedaLiberacionesFacade.traeSuplementosPedidos(formulario.getIdentPedtv());
			
		}catch(Exception ex){
			log.error("BusquedaLiberacionesHelper:traeDetalleLiberacionVentasPedidos error catch",ex);
		}
		return lista_suplemento_pedido;
	}
	/*
	 * Actualizado por LMARIN 20130827.
	 * Se agrega un valor a estado.
	 * Este cambia automaticamente a 2 cuando el checkbox no solicitar está marcado.   
	 */
	public void creaArchivoLiberacion(BusquedaLiberacionesForm formulario, String local){
		log.info("BusquedaLiberacionesHelper:creaArchivoLiberacion inicio");
		SalidaArchivo salida = new SalidaArchivo();
		
		String [] local_new = null;
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		/*
		 * LMARIN SE AGREGA CAMBIOS PARA IMPRIMIR HISTORIAL DEVOLUCIONES
		 */
		ArrayList<VentaPedidoBean> lista_historial_pedido = new ArrayList<VentaPedidoBean>();
		Utils util = new Utils();
	
		try{
			if(null != formulario.getListaPedidos()){		
				
				String codigo_linea = formulario.getIdentPedtv();
				
				
				
				for(VentaPedidoBean venta : formulario.getListaPedidos()){
					
					local_new = venta.getCod_lista_lib().split("/");
					util.traeCodigoLocalSap(local_new[0]);
					String respuesta ="";
					if(venta.isChecked()){	
						
						String [] cod_encargo = venta.getCod_lista_lib().split("/");
						
						String nombreArchivo = "OK_"+local_new[0]+"_"+util.traeCodigoLocalSap(local_new[0])+"_"+cod_encargo[1]+"_"+venta.getGrupo()+"_"+util.traeFechaHoyFormatoString()+"_"+util.traeHoraFormatoxString()+"_"+util.numeroAleatorio()+"_FICHERO_CL.TXT";
						String nombreTemporal = "OK_"+local_new[0]+"_"+util.traeCodigoLocalSap(local_new[0])+"_"+cod_encargo[1]+"_"+venta.getGrupo()+"_"+util.traeFechaHoyFormatoString()+"_"+util.traeHoraFormatoxString()+"_"+util.numeroAleatorio()+"_FICHERO_TEMP_CL.TXT";
						lista_detalle_pedido = traeDetalleVenta(venta);
						lista_historial_pedido = BusquedaLiberacionesFacade.trae_historial_encargo(venta.getCod_lista_lib()+Constantes.STRING_SLASH+"1");
						
						//LMARIN 20131229 AGREGO HISTORIAL DEL ENCARGO 
						if(lista_historial_pedido.size() > 0){
							for(VentaPedidoBean b : lista_historial_pedido){
								venta.setNumdev("R"+b.getNumdev());
								venta.setEncargo_padre(b.getEncargo_padre());
								venta.setLineadev(b.getLineadev());
							}
						}else{
							venta.setNumdev("");
							venta.setEncargo_padre("");
							venta.setLineadev("");
						}
						
						//Paso a lentes de contacto
						if("L".equals(venta.getTipo_familia())){
							
								for(VentaPedidoBean v :lista_detalle_pedido){
									System.out.println("crea archivo lib traeDetalleVenta Codigo => "+v.getCod_detalle_vta()+" || "+v.getCod_lista_lib()+" <==> Linea =>"+v.getLinea()+" Grupo => "+v.getGrupo());
								}
								//ArrayList<VentaPedidoBean> lista_detalle_pedido_actualizado = actualizaListaDetallePedidoLiberacion(lista_detalle_pedido, venta.getLinea(), venta.getCdg());
								//System.out.println("local crea archivo L ===>"+local);
								respuesta = salida.creaArchivoLiberacion(venta, local, lista_detalle_pedido, nombreArchivo, nombreTemporal);
								//lista_detalle_pedido.clear();
								//lista_detalle_pedido.addAll(lista_detalle_pedido_actualizado);
								
								String estado = Constantes.STRING_UNO;
								if("".equals(respuesta)){
									if(null != lista_detalle_pedido){
										System.out.println("Paso por donde se actualizan las lentillas");
										//Actualización por LMARIN 20130827
										if(venta.isSin_cdd()){
											estado = Constantes.STRING_DOS;
										}else{
											estado = Constantes.STRING_UNO;
										}
										for(VentaPedidoBean encargo:lista_detalle_pedido){
											BusquedaLiberacionesFacade.cambioEstadoLiberacion(venta.getCod_lista_lib(), estado, venta.getGrupo(), encargo.getCodigo(), encargo.getArticulo());
										}
									}
									formulario.setRespuestaLiberacion(respuesta);
								}else{
									formulario.setRespuestaLiberacion(respuesta + " encargo: "+ venta.getCod_lista_lib());
									break;
								}								
						}else{	
							ArrayList<VentaPedidoBean> arr = new ArrayList<VentaPedidoBean>();
							arr= BusquedaLiberacionesFacade.traeDetalleVentasPedidos(venta.getCod_lista_lib(), venta.getGrupo());

							//System.out.println("local crea archivo n ===>"+local);
								respuesta = salida.creaArchivoLiberacion(venta, local, arr, nombreArchivo, nombreTemporal);						
								
								String estado = Constantes.STRING_UNO;
								if("".equals(respuesta)){
									if(null != lista_detalle_pedido){
										for(VentaPedidoBean encargo:lista_detalle_pedido){
											BusquedaLiberacionesFacade.cambioEstadoLiberacion(venta.getCod_lista_lib(), estado, venta.getGrupo(), encargo.getCodigo(), encargo.getArticulo());//metodo para poder cambiar el estado a liberado
										}
									}
									formulario.setRespuestaLiberacion(respuesta);
								}else{
									formulario.setRespuestaLiberacion(respuesta + " encargo: "+ venta.getCod_lista_lib()+"\n");
									break;
								}
						}					
				   }
			}
		}
			
		}catch(Exception ex){
			log.error("BusquedaLiberacionesHelper:creaArchivoLiberacion error catch",ex);
		}
	}
	
	public ArrayList<VentaPedidoBean> actualizaListaDetallePedidoLiberacion(ArrayList<VentaPedidoBean> lista_detalle_pedido, int linea, String codigo){
		ArrayList<VentaPedidoBean> lista_detalle_pedido_actualizado = new ArrayList<VentaPedidoBean>();
		try{
			
			if(null != lista_detalle_pedido){
				for(VentaPedidoBean venta : lista_detalle_pedido){
					if(linea == venta.getLinea()){
						lista_detalle_pedido_actualizado.add(venta);
					}
				}
			}
			
		}catch(Exception ex){
			log.error("BusquedaLiberacionesHelper:actualizaListaDetallePedidoLiberacion error catch",ex);
		}
		return lista_detalle_pedido_actualizado;
	}
	
	public ArrayList<VentaPedidoBean> traeDetalleVenta(VentaPedidoBean venta){
		log.info("BusquedaLiberacionesHelper:traeDetalleVenta inicio");
		ArrayList<VentaPedidoBean> lista_detalle_pedido = new ArrayList<VentaPedidoBean>();
		ArrayList<VentaPedidoBean> lista_detalle_pedido_ordenada = new ArrayList<VentaPedidoBean>();
		
		try{
			
			
			lista_detalle_pedido = BusquedaLiberacionesFacade.traeDetalleVentasPedidos(venta.getCod_lista_lib(), venta.getGrupo());
			
			for(VentaPedidoBean v :lista_detalle_pedido){
				System.out.println("antes traeDetalleVenta Codigo => "+v.getCod_detalle_vta()+" || "+v.getCod_lista_lib()+" <==> Linea =>"+v.getLinea()+" Grupo => "+v.getGrupo());
			}
			
			if(null != lista_detalle_pedido){
				
				for(VentaPedidoBean vta:lista_detalle_pedido){
					
					if("M".equals(vta.getTipo_familia())  || "G".equals(vta.getTipo_familia())){
						lista_detalle_pedido_ordenada.add(0, vta);
					}
					
					if("C".equals(vta.getTipo_familia()) && "D".equals(vta.getOjo())){
						lista_detalle_pedido_ordenada.add(1, vta);
					}
					
					if("C".equals(vta.getTipo_familia()) && "I".equals(vta.getOjo())){
						lista_detalle_pedido_ordenada.add(2, vta);
					}
					if("L".equals(vta.getTipo_familia())){
						lista_detalle_pedido_ordenada.add(vta);
					}
				}
			}		

			for(VentaPedidoBean v :lista_detalle_pedido_ordenada){
				System.out.println("despues traeDetalleVenta Codigo => "+v.getCod_detalle_vta()+" || "+v.getCod_lista_lib()+" <==> Linea =>"+v.getLinea()+" Grupo => "+v.getGrupo());
			}
		}catch(Exception ex){
			log.error("BusquedaLiberacionesHelper:traeDetalleVenta error catch",ex);
			return lista_detalle_pedido;
		}
		return lista_detalle_pedido_ordenada;
	}

	/*
	 * Se desactiva validacion 20130827 LMARIN a pedido de MLOHSE 
	 */
	public boolean valida_campos_cdd(BusquedaLiberacionesForm formulario) {
		
		/*for(VentaPedidoBean venta : formulario.getListaPedidos()){
			
			if(venta.isChecked()){	
				if (venta.isMostrar()) {
					if (!venta.isSin_cdd())
					{
						if (venta.getCantidad_cdd() == 0 || venta.getCantidad_cdd() > venta.getCantidad_lentillas()){
							return false;
						}
					}
				}
			}
		}*/
		return true;
	}


	public boolean validaEncargosSeleccionados(
			ArrayList<VentaPedidoBean> listaPedidos) {
		
		for (VentaPedidoBean pedido : listaPedidos) {
			if (pedido.isChecked()) {
				return true;
			}
		}
		return false;
	}


	public void valida_trios_encargos_erroneos(
			BusquedaLiberacionesForm formulario){
		String cdg = Constantes.STRING_BLANCO;
		for (int i = 0; i < formulario.getListaPedidos().size(); i++) 
		{
			if ((!formulario.getListaPedidos().get(i).getRespuestaValidaLiberacion().equalsIgnoreCase("OK")) && 
					(!formulario.getListaPedidos().get(i).getRespuestaValidaLiberacion().equalsIgnoreCase("OK_L"))) 
			{
				formulario.getListaPedidos().get(i).setConTriosValidos(Constantes.STRING_FALSE);
				cdg = formulario.getListaPedidos().get(i).getCod_lista_lib();
				for (int j = 1; j < formulario.getListaPedidos().size(); j++) 
				{
					if (formulario.getListaPedidos().get(j).getCod_lista_lib().equals(cdg)) 
					{
						formulario.getListaPedidos().get(j).setConTriosValidos(Constantes.STRING_FALSE);
					}
				}
			}
		}
		
	}
	
    public  ArrayList<ListaEstadosBean> traeEstadosEncargo(){
   	 ArrayList<ListaEstadosBean> lista_estado = new ArrayList<ListaEstadosBean>();
        try {
            lista_estado = BusquedaLiberacionesFacade.traeEstadosEncargo();
            
        } catch (Exception e) {
            e.printStackTrace();
            lista_estado = null;
        }
        return lista_estado;
   }
	
	
}
