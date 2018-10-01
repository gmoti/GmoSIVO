package cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.DAO.VentaPedidoPendientesDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;



public class VentaPedidoPendientesDAOImpl implements VentaPedidoPendientesDAO{
	Utils util= new Utils();

	Logger log = Logger.getLogger( this.getClass() );
	@Override
	public ArrayList<PedidosPendientesBean> traePedidosPendientes(String codigoCliente, String sucursal, String pedido_cdg, String agente, String nif_cliente, String fecha) throws Exception {
		log.info("VentaPedidoPendientesDAOImpl:traePedidosPendientes inicio");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ArrayList<PedidosPendientesBean> listaPedidos = new ArrayList<PedidosPendientesBean>();
		try {
			log.info("VentaPedidoPendientesDAOImpl:traePedidosPendientes conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_BUSCAR_SEL_PED_PEND(?,?,?,?,?,?,?)}");
			System.out.println("PPENDIENTES => "+codigoCliente+"|"+null+"|"+pedido_cdg+"|"+agente+"|"+nif_cliente+"|"+fecha);
			cs.setString(1, codigoCliente);
			cs.setString(2, null);
			cs.setString(3, pedido_cdg);
			cs.setString(4, agente);
			cs.setString(5, nif_cliente);
			cs.setString(6, fecha);
			cs.registerOutParameter(7, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(7);
			while (rs.next()) {
				log.debug("VentaPedidoPendientesDAOImpl:traePedidosPendientes entrando ciclo while");
				PedidosPendientesBean pedido = new PedidosPendientesBean();
				pedido.setHoraP(rs.getString("HORA"));
				Utils util = new Utils();
				if(null==rs.getString("AGENTE")){
					pedido.setAgente(Constantes.STRING_ACTION_SF);
				}else{
					pedido.setAgente(rs.getString("AGENTE"));
				}
				if(null==rs.getString("ANTICIPOTOT")){
					pedido.setAnticipo(Constantes.STRING_ACTION_SF);
				}else{
					pedido.setAnticipo(util.getNumber(rs.getString("ANTICIPOTOT")));
				}
				if(null==rs.getString("FECHAENTREGA")){
					pedido.setFechasEntragas(Constantes.STRING_ACTION_SF);
				}else{
					pedido.setFechasEntragas(rs.getString("FECHAENTREGA"));
				}
				if(null==rs.getString("FECHAPEDIDO")){
					pedido.setFechaPedido(Constantes.STRING_ACTION_SF);
				}else{
					pedido.setFechaPedido(rs.getString("FECHAPEDIDO"));
				}
					pedido.setCdg(rs.getString("CDG"));
				listaPedidos.add(pedido);
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:traePedidosPendientes error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_BUSCAR_SEL_PED_PEND"); 
		} finally {
            try{
            	if(null != rs){
               	 log.warn("VentaPedidoPendientesDAOImpl:traePedidosPendientes cierre ResultSet");
               	 rs.close();
                }
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:traePedidosPendientes cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:traePedidosPendientes cierre Connection");
		    	   con.close();
	           }  
             
         }catch(Exception e){
        	 log.error("VentaPedidoPendientesDAOImpl:traePedidosPendientes error", e);
         }
		}
		
		return listaPedidos;
	}
	public ProductosBean traeMultiofertaCB(ProductosBean producto)throws Exception{
		log.info("VentaPedidoPendientesDAOImpl:traeMultiofertaCB inicio");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			log.info("VentaPedidoPendientesDAOImpl:traeMultiofertaCB conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_BUSCAR_SEL_PEDIDO_MULTI_CB(?,?,?,?)}");
			System.out.println("SP_BUSCAR_SEL_PEDIDO_MULTI_CB ==> "+producto.getCod_pedvtcb()+"<==>"+ producto.getLinea()+"\n");
			cs.setString(1, producto.getCod_pedvtcb());
			cs.setString(2, producto.getLinea());
			cs.setString(3, Constantes.STRING_P);
			cs.registerOutParameter(4, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(4);
			
			while (rs.next()) {
				log.debug("VentaPedidoPendientesDAOImpl:traeMultiofertaCB entrando ciclo while");
				producto.setCodigoMultioferta(rs.getString("CDG"));
				producto.setCdg_correlativo_multioferta(rs.getInt("NUMERO"));
			}
			
		}catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:traeMultiofertaCB error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_BUSCAR_SEL_PEDIDO_MULTI_CB"); 
		} finally {
            try{

                if(null != rs){
               	 log.warn("VentaPedidoPendientesDAOImpl:traeMultiofertaCB cierre ResultSet");
               	 rs.close();
                }
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeMultiofertaCB cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeMultiofertaCB cierre Connection");
		    	   con.close();
	           } 
	         }catch(Exception e){
	        	 log.error("VentaPedidoPendientesDAOImpl:traeMultiofertaCB error", e);
	         }
		}
		
		return producto;
			
	}
	@Override
	public PedidosPendientesBean traePedidosSeleccionado(String codigoPedido) throws Exception{
			log.info("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado inicio");	
			Connection con = null;
			CallableStatement cs = null;
			ResultSet rscb = null;
			ResultSet rsln = null;
			PedidosPendientesBean pedido = new PedidosPendientesBean();
			try {
				log.info("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado conectando base datos");
				con = ConexionFactory.INSTANCE.getConexion();
				cs = con.prepareCall("{ call SP_BUSCAR_SEL_PEDIDO(?,?,?)}");
				cs.setString(1, codigoPedido);
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.execute();
				rscb = (ResultSet)cs.getObject(2);
				rsln = (ResultSet)cs.getObject(3);
				while (rscb.next()) { 
					log.debug("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado entrando ciclo while");
						pedido.setCodigoP(rscb.getString("CDG"));
						pedido.setCliente(rscb.getString("CLIENTE"));
						pedido.setForma_pagoP(rscb.getString("FPAGO"));
						pedido.setSobreP(rscb.getString("NSOBRE"));
						pedido.setAgenteP(rscb.getString("AGENTE"));
						pedido.setDivisaP(rscb.getString("DIVISA"));
						pedido.setNota(rscb.getString("NOTAS"));
						pedido.setIdiomaP(rscb.getString("IDIOMA"));
						pedido.setConvenioP(rscb.getString("CONVENIO"));
						pedido.setConvenio_det(rscb.getString("CONVENIODET"));
						pedido.setPromocionP(rscb.getString("OFERTA"));
						pedido.setFechaP(rscb.getString("FECHAPEDIDO"));
						pedido.setHoraP(rscb.getString("HORA"));
						pedido.setCambioP(rscb.getString("CAMBIO"));
						pedido.setTipo_pedidoP(rscb.getString("TIPOPED2"));
						pedido.setFechasEntragas(util.formatoFecha(rscb.getDate("FECHAENTREGA")));
						pedido.setDescuento(rscb.getInt("DTO"));
						pedido.setPedvtant(rscb.getString("PEDVTANT"));
						if (null == pedido.getPedvtant()) {
							pedido.setPedvtant(Constantes.STRING_BLANCO);
						}
						pedido.setCerrado(rscb.getString("CERRADO"));
						pedido.setLocal(rscb.getString("LOCAL"));
						pedido.setIsapre(rscb.getString("ISAPRE"));
						System.out.println("ISAPRE <===> "+rscb.getString("ISAPRE"));
						
				}
				ArrayList<ProductosBean> listaProductos = new ArrayList<ProductosBean>();
				while (rsln.next()) {
					log.debug("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado entrando ciclo while");
					ProductosBean producto= new ProductosBean();
					producto.setIdent(rsln.getInt("IDENT"));
					producto.setCod_barra(rsln.getString("CODIGOBARRAS"));
					producto.setCodigo(rsln.getString("ARTICULO"));
					producto.setCod_articulo(rsln.getString("ARTICULO"));
					producto.setDescripcion(rsln.getString("DESCRIPCION"));
					producto.setCantidad(rsln.getInt("CANTIDAD"));
					producto.setPrecio(rsln.getInt("PRECIOIVA"));
					producto.setFamilia(rsln.getString("FAMILIA"));
					producto.setSubFamilia(rsln.getString("SUBFAM"));
					producto.setTipoFamilia(rsln.getString("TIPOFAM"));
					producto.setFecha_graduacion(util.formatoFecha(rsln.getDate("FECHAGRAD")));
					producto.setNumero_graduacion(rsln.getInt("NUMEROGRAD"));
					producto.setTipo(rsln.getString("TIPO"));
					if (null != producto.getTipo()) {
						if (Constantes.STRING_C.equals(producto.getTipo())) {
							producto.setTipo(Constantes.STRING_CERCA_OPT);
						}
						if (Constantes.STRING_L.equals(producto.getTipo())) {
							producto.setTipo(Constantes.STRING_LEJOS_OPT);
						}
					}
					producto.setOjo(rsln.getString("OJO"));
					if (null != producto.getOjo()) {
						if (producto.getOjo().equals(Constantes.STRING_D)) {
							producto.setOjo(Constantes.STRING_DERECHO);
						}
						if (producto.getOjo().equals(Constantes.STRING_I)) {
							producto.setOjo(Constantes.STRING_IZQUIERDO);
						}
					}
					else
					{
						producto.setOjo(Constantes.STRING_BLANCO);
					}
					producto.setPrevtln(rsln.getString("PREVTLN"));
					if (null == producto.getPrevtln()) {
						producto.setPrevtln(Constantes.STRING_BLANCO);
					}
					
					producto.setImporte(producto.getCantidad()*producto.getPrecio());
					producto.setTotal(producto.getCantidad()*producto.getPrecio());
					if(Constantes.STRING_BLANCO.equals(rsln.getString("GRUPO"))||null==rsln.getString("GRUPO")){
						producto.setGrupo(Constantes.STRING_CERO);
					}else{
						producto.setGrupo(rsln.getString("GRUPO"));
					}
					producto.setLiberado(rsln.getString("LIBERADO"));
					producto.setGrupoFamilia(rsln.getString("GRUPOFAM"));
					producto.setEsfera(rsln.getDouble("ESFERA"));
					producto.setCilindro(rsln.getDouble("CILINDRO"));
					producto.setEje(rsln.getInt("EJE"));
					producto.setDiametro(rsln.getDouble("DIAMETRO"));
					producto.setDto(rsln.getString("DTO"));
					producto.setDescuento(util.formato_Decimal(rsln.getString("DTO")));
					producto.setCod_pedvtcb(codigoPedido);
					producto.setLinea(String.valueOf(rsln.getInt("LINEA")));
					producto.setTotal(rsln.getInt("VALOR_ORIGINAL"));
					
					if (producto.getPrecio() != producto.getTotal()) {
						producto.setPrecioEspecial(true);
					}
					
					listaProductos.add(producto);
				}
				pedido.setListaProduc(listaProductos);
				
			} catch (Exception e) {
				log.error("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado error controlado",e);
	            throw new Exception("Error en DAO, al ejecutar SP: SP_BUSCAR_SEL_PEDIDO"); 
			} finally {
	            try{
	            	if(null != rscb){
		 				log.warn("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado cierre ResultSet");
		 				rscb.close();
		 			}
		 			if(null != rsln){
		 				log.warn("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado cierre ResultSet");
		 				rsln.close();
		 			}
	             if (null != cs){
	            	 log.warn("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado cierre CallableStatement");
	                 cs.close();
	             }           
	             if (null != con){
	            	 log.warn("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado cierre Connection");
			    	   con.close();
		           } 
	       
	 			
	             
	         }catch(Exception e){
	        	 log.error("VentaPedidoPendientesDAOImpl:traePedidosSeleccionado error", e);
	         }
			}
			
			return pedido;
	}

	@Override
	public boolean eliminarPedido(String codigo) throws Exception {
		log.info("VentaPedidoPendientesDAOImpl:eliminarPedido inicio");
		boolean isDelete = false;
		Connection con = null;
		CallableStatement cs = null;
		try {
			log.info("VentaPedidoPendientesDAOImpl:eliminarPedido conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_UTIL_DEL_ELIMINAR_PED(?,?)}");
			cs.setString(1, codigo);
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.execute();
			String delete = cs.getString(2);
			if(Constantes.STRING_ACTION_NOOKSP.equals(delete)){
				isDelete=false;
			}else{
				isDelete=true;
			}
		} catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:eliminarPedido error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_UTIL_DEL_ELIMINAR_PED"); 
		} finally {
            try{
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:eliminarPedido cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:eliminarPedido cierre Connection");
		    	   con.close();
	           } 
             
         }catch(Exception e){
        	 log.error("VentaPedidoPendientesDAOImpl:eliminarPedido error", e);
         }
		}
		
		return isDelete;
	}
	@Override
	public boolean pedidoEntrega(String codigoPedido, String sucursal,VentaPedidoForm form)throws Exception {
		log.info("VentaPedidoPendientesDAOImpl:pedidoEntrega inicio");
		boolean isDelete = false;
		Connection con = null;
		CallableStatement cs = null;
		try {
			log.info("VentaPedidoPendientesDAOImpl:pedidoEntrega conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_VTA_PEDI_COPIAR_ENTREGA(?,?,?,?,?)}");
			cs.setString(1, codigoPedido);
			cs.setString(2, sucursal);
			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			
			cs.execute();
			String codigoTraspaso = cs.getString(3);
			String msnTraspaso = cs.getString(4);
			form.setCodigo_confirmacion(cs.getString(5));
			
			if(Constantes.STRING_CERO.equals(codigoTraspaso)){
				isDelete=true;
				form.setMsnPedidoEntrega(msnTraspaso);
			}else{
				isDelete=false;
				form.setMsnPedidoEntrega(msnTraspaso);
				System.out.println(msnTraspaso);
			}
		} catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:pedidoEntrega error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_VTA_PEDI_COPIAR_ENTREGA"); 
		} finally {
            try{
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:pedidoEntrega cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:pedidoEntrega cierre Connection");
		    	   con.close();
	           } 
             
         }catch(Exception e){
        	 log.error("VentaPedidoPendientesDAOImpl:pedidoEntrega error", e);
         }
		}
		
		return isDelete;
	}
	public ArrayList<ProductosBean> traeMultiofertaLN(ProductosBean prod, ArrayList<ProductosBean> lista_multi) throws Exception {
		log.info("VentaPedidoPendientesDAOImpl:traeMultiofertaLN inicio");
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			log.info("VentaPedidoPendientesDAOImpl:traeMultiofertaLN conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_BUSCAR_SEL_PEDIDO_MULTI_LN(?,?)}");
			
			System.out.println("MULTI_LN ==>"+prod.getCodigoMultioferta()+"\n");
			cs.setString(1, prod.getCodigoMultioferta());
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			
			while (rs.next()) {
				log.debug("VentaPedidoPendientesDAOImpl:traeMultiofertaLN entrando ciclo while");
				ProductosBean producto = new ProductosBean();
				producto.setIdent(rs.getInt("IDENT"));
				producto.setLinea(String.valueOf(rs.getInt("LINEA")));
				producto.setCodigo(rs.getString("ARTISUBP"));
				producto.setCod_articulo(rs.getString("ARTISUBP"));
				producto.setMontacb(prod.getCodigoMultioferta());
				producto.setCodigoMultioferta(prod.getCodigo());
				producto.setIndexRelMulti(prod.getIndexMulti());
				producto.setCantidad(rs.getInt("CANTIDAD"));
				producto.setImporte(rs.getInt("IMPORTE"));
				producto.setCod_barra(rs.getString("CODIGOBARRAS"));
				producto.setOjo(rs.getString("OJO"));
				if (null != producto.getOjo() && producto.getOjo().equals(Constantes.STRING_D)) {
					producto.setOjo(Constantes.STRING_DERECHO);
				}
				else
				{
					if (null != producto.getOjo() && producto.getOjo().equals(Constantes.STRING_I)) {
						producto.setOjo(Constantes.STRING_IZQUIERDO);
					}
					else
					{
						producto.setOjo(Constantes.STRING_BLANCO);
					}
				}
				producto.setEsfera(rs.getDouble("ESFERA"));
				producto.setCilindro(rs.getDouble("CILINDRO"));
				producto.setDiametro(rs.getDouble("DIAMETRO"));
				producto.setEje(rs.getInt("EJE"));
				producto.setGrupo(String.valueOf(rs.getInt("GRUPO")));
				producto.setTipo(rs.getString("TIPO"));
				if (null != producto.getTipo()) {
					if (Constantes.STRING_C.equals(producto.getTipo())) {
						producto.setTipo(Constantes.STRING_CERCA_OPT);
					}
					if (Constantes.STRING_L.equals(producto.getTipo())) {
						producto.setTipo(Constantes.STRING_LEJOS_OPT);
					}
				}
				
				producto.setFecha_graduacion(util.formatoFechaString(rs.getString("FECHAGRAD")));
				producto.setNumero_graduacion(rs.getInt("NUMEROGRAD"));
				producto.setDescripcion(rs.getString("DESCRIPCION"));
				producto.setLiberado(rs.getString("LIBERADO"));
				producto.setFamilia(rs.getString("FAMILIA"));
				producto.setTipoFamilia(rs.getString("TIPOFAM"));
				producto.setGrupoFamilia(rs.getString("GRUPOFAM"));
				producto.setSubFamilia(rs.getString("SUBFAM"));
				
				lista_multi.add(producto);
			}
			
			return  lista_multi;
			
		}catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:traeMultiofertaLN error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_BUSCAR_SEL_PEDIDO_MULTI_LN"); 
		} finally {
            try{
            	if(null!=rs){
               	 log.warn("VentaPedidoPendientesDAOImpl:traeMultiofertaLN cierre ResultSet");
               	 rs.close();
                }
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeMultiofertaLN cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeMultiofertaLN cierre Connection");
		    	   con.close();
	           } 
             
	         }catch(Exception e){
	        	 log.error("VentaPedidoPendientesDAOImpl:traeMultiofertaLN error", e);
	         }
		}
			
	}
	public ArrayList<SuplementopedidoBean> traeTratamientosPedido(int ident) throws Exception {
		log.info("VentaPedidoPendientesDAOImpl:traeTratamientosPedido inicio");
		ArrayList<SuplementopedidoBean> listaSuple = new ArrayList<SuplementopedidoBean>();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			log.info("VentaPedidoPendientesDAOImpl:traeTratamientosPedido conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_BUSCAR_SEL_PEDIDO_TRATAMI(?,?)}");
			System.out.println("trat ped cod =>"+ident);
			cs.setInt(1, ident);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			
			while (rs.next()) {
				log.debug("VentaPedidoPendientesDAOImpl:traeTratamientosPedido entrando ciclo while");
				SuplementopedidoBean suple = new SuplementopedidoBean();
				suple.setTratami(rs.getString("TRATAMI"));
				suple.setValor(rs.getString("VALOR"));
				suple.setDescripcion(rs.getString("DESCRIPCION"));
				
				listaSuple.add(suple);
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:traeTratamientosPedido error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_BUSCAR_SEL_PEDIDO_TRATAMI"); 
		} finally {
            try{
            	if(null != rs){
               	 log.warn("VentaPedidoPendientesDAOImpl:traeTratamientosPedido cierre ResultSet");
               	 rs.close();
                }
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeTratamientosPedido cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeTratamientosPedido cierre Connection");
		    	   con.close();
	           } 
             
	         }catch(Exception e){
	        	 log.error("VentaPedidoPendientesDAOImpl:traeTratamientosPedido error", e);
	         }
		}
		return listaSuple;
	}
	public ArrayList<SuplementopedidoBean> traeTratamientosPedidoMultiofertas(
			int ident) throws Exception {
		log.info("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas inicio");
		ArrayList<SuplementopedidoBean> listaSuple = new ArrayList<SuplementopedidoBean>();
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try {
			log.info("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			cs = con.prepareCall("{ call SP_BUSCAR_SEL_PEDIDO_TRAT_MULT(?,?)}");
			
			cs.setInt(1, ident);
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet)cs.getObject(2);
			
			while (rs.next()) {
				log.debug("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas entrando ciclo while");
				SuplementopedidoBean suple = new SuplementopedidoBean();
				suple.setTratami(rs.getString("TRATAMI"));
				suple.setValor(rs.getString("VALOR"));
				suple.setDescripcion(rs.getString("DESCRIPCION"));
				
				listaSuple.add(suple);
			}
			
		} catch (Exception e) {
			log.error("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas error controlado",e);
            throw new Exception("Error en DAO, al ejecutar SP: SP_BUSCAR_SEL_PEDIDO_TRAT_MULT"); 
		} finally {
            try{
            	if(null != rs){
               	 log.warn("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas cierre ResultSet");
               	 rs.close();
                }
             if (null != cs){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas cierre CallableStatement");
                 cs.close();
             }           
             if (null != con){
            	 log.warn("VentaPedidoPendientesDAOImpl:traeTratamientosPedidoMultiofertas cierre Connection");
		    	   con.close();
	           } 
             
	         }catch(Exception e){
	        	 log.error("VentaPedidoPendientesDAOImpl:traeTratamientosPedido error", e);
	         }
		}
		return listaSuple;
	}
	
}
