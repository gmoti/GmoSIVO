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
import cl.gmo.pos.venta.web.Integracion.DAO.EntregaPedidoDAO;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;

public class EntregaPedidoDAOImpl implements EntregaPedidoDAO
{
	Logger log = Logger.getLogger( this.getClass() );
	public ArrayList<VentaPedidoBean> buscarPedidos(String cliente, String fecha_pedido, String nombre) throws Exception {
		log.info("EntregaPedidoDAOImpl:buscarPedidos inicio");
		ArrayList<VentaPedidoBean> lista_pedidos_entregados = new ArrayList<VentaPedidoBean>();
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Utils  util = new Utils();
		try {
			log.info("EntregaPedidoDAOImpl:buscarPedidos conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call  SP_LISTA_SEL_ENTREGA_PEDIDO(?,?,?,?)}";
			cs = con.prepareCall(sql);
			
			cs.setString(1, cliente);
			cs.setString(2, fecha_pedido);	
			cs.registerOutParameter(3, OracleTypes.CURSOR);
			cs.setString(4, nombre);
			cs.execute();
			rs = (ResultSet)cs.getObject(3);		
			
			VentaPedidoBean venta  = null;
			while (rs.next()) {
				log.debug("EntregaPedidoDAOImpl:buscarPedidos entrando ciclo while");
				venta = new VentaPedidoBean();
				
				venta.setCdg(rs.getString("CODIGO"));				
				venta.setFecha(util.formatoFecha(rs.getDate("FECHAPEDIDO")));
				venta.setFecha_entrega(util.formatoFecha(rs.getDate("FECHAENTREGA")));
				venta.setCliente_completo(rs.getString("NOMBRE_CLIENTE"));
				venta.setCliente(rs.getString("CODCLIENTE"));
				venta.setAgente(rs.getString("AGENTE"));
				
				lista_pedidos_entregados.add(venta);
			}
			
            return lista_pedidos_entregados;
            
		} catch (SQLException e) {           
			log.error("EntregaPedidoDAOImpl:buscarPedidos error controlado",e);
            throw new Exception("Error en DAO, al traer los pedidos entregados"); 
       } finally {
              try{
               if (null !=  rs){
            	   log.warn("EntregaPedidoDAOImpl:buscarPedidos cierre ResultSet");
                   rs.close();
               }
               if (null !=  cs){
            	   log.warn("EntregaPedidoDAOImpl:buscarPedidos cierre CallableStatement");
            	   cs.close();
               }  
               if (null !=  con){
            	   log.warn("EntregaPedidoDAOImpl:buscarPedidos cierre Connection");
            	   con.close();
               }  
           }catch(SQLException e){
        	   log.error("EntregaPedidoDAOImpl:buscarPedidos error", e);
        	   throw new Exception("Error en DAO, Al cerrar las conexiones"); 
           }
       }
		
		
	}
	
	
	public VentaPedidoBean traeEntregaPedido(String cdg_pedido) throws Exception{

		log.info("EntregaPedidoDAOImpl:traeEntregaPedido inicio");
		Connection con = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		CallableStatement cs = null;
		Utils  util = new Utils();
		try {
			log.info("EntregaPedidoDAOImpl:traeEntregaPedido conectando base datos");
			con = ConexionFactory.INSTANCE.getConexion();
			String sql = "{call  SP_BUSCAR_SEL_PEDIDO_ENTREGADO(?,?,?)}";
			cs = con.prepareCall(sql);			
			cs.setString(1, cdg_pedido);				
			cs.registerOutParameter(2, OracleTypes.CURSOR);
			cs.registerOutParameter(3, OracleTypes.CURSOR);			
			cs.execute();
			
			rs = (ResultSet)cs.getObject(2);		
			rs2 = (ResultSet)cs.getObject(3);
			
			VentaPedidoBean venta = new VentaPedidoBean();
			
			while (rs.next()) {				
				log.debug("EntregaPedidoDAOImpl:traeEntregaPedido entrando ciclo while");
				venta.setCdg(rs.getString("CDG"));
				venta.setForma_pago(rs.getString("FPAGO"));
				venta.setTipo_ped(rs.getString("TIPO"));
				venta.setNumero_sobre(rs.getString("NSOBRE"));
				venta.setAgente(rs.getString("AGENTE"));
				venta.setDivisa(rs.getString("DIVISA"));
				venta.setIdioma(rs.getString("IDIOMA"));
				venta.setConvenio(rs.getString("CONVENIO"));
				venta.setFecha(util.formatoFecha(rs.getDate("FECHAPEDIDO")));
				venta.setFecha_entrega(util.formatoFecha(rs.getDate("FECHA_ENTREGA")));
				venta.setHora(rs.getString("HORA"));
				venta.setCambio(rs.getInt("CAMBIO"));
				venta.setTipo_ped(rs.getString("TIPOPED"));
				venta.setCliente_completo(rs.getString("NOMBRE_CLIENTE"));
				venta.setCliente(rs.getString("CODIGO_CLIENTE"));
				if(null != rs.getString("NOTA")){
					venta.setNotas(rs.getString("NOTA"));
				}else{
					venta.setNotas(Constantes.STRING_BLANCO);
				}
				
				
			}
			venta.setLista_productos(new ArrayList<ProductosBean>());
			ProductosBean pro = null;
				
			while(rs2.next()){
				log.debug("EntregaPedidoDAOImpl:traeEntregaPedido entrando ciclo while");
				pro = new ProductosBean();
				
				pro.setIdent(rs2.getInt("IDENT"));
				pro.setCodigo(rs2.getString("CDG"));
				pro.setLinea(rs2.getString("LINEA"));
				pro.setCod_pedvtcb(rs2.getString("PEDVTCB"));
				pro.setCod_articulo(rs2.getString("ARTICULO"));
				pro.setCod_barra(rs2.getString("CODIGOBARRAS"));
				pro.setDescripcion(rs2.getString("DESCRIPCION"));
				pro.setSubAlm(rs2.getString("SUBALM"));
				pro.setCantidad(rs2.getInt("CANTIDAD"));
				pro.setPrecio(rs2.getInt("PRECIOIVA"));
				pro.setDto(rs2.getString("DTO"));
				pro.setPrecio_costo(rs2.getInt("PRECIO"));
				pro.setEstado_linea(rs2.getString("ESTADO"));
				
				
				if(null != rs2.getString("OJO")){
					pro.setOjo(rs2.getString("OJO"));
				}else{
					pro.setOjo(Constantes.STRING_BLANCO);
				}
				
				pro.setEsfera(rs2.getDouble("ESFERA"));
				pro.setCilindro(rs2.getDouble("CILINDRO"));
				pro.setDiametro(rs2.getDouble("DIAMETRO"));
				pro.setEje(rs2.getInt("EJE"));
				
				if(null != rs2.getString("GRUPO")){
					pro.setGrupo(rs2.getString("GRUPO"));
				}else{
					pro.setGrupo(Constantes.STRING_CERO);					
				}	
				
				pro.setNumero_graduacion(rs2.getInt("NUMEROGRAD"));
				pro.setFecha_graduacion(rs2.getString("FECHAGRAD"));
				
				venta.getLista_productos().add(pro);
			}
			
            return venta;
            
		} catch (SQLException e) {    
			log.error("EntregaPedidoDAOImpl:traeEntregaPedido error controlado",e);
            throw new Exception("Error en DAO, al traer los pedidos entregados"); 
       } finally {
              try{
               if (null !=  rs){
            	   log.warn("EntregaPedidoDAOImpl:traeEntregaPedido cierre ResultSet");
                   rs.close();
               }
               if (null !=  rs2){
            	   log.warn("EntregaPedidoDAOImpl:traeEntregaPedido cierre ResultSet");
                   rs2.close();
               }
               if (null !=  cs){
            	   log.warn("EntregaPedidoDAOImpl:traeEntregaPedido cierre CallableStatement");
            	   cs.close();
               }  
               if (null !=  con){
            	   log.warn("EntregaPedidoDAOImpl:traeEntregaPedido cierre Connection");
            	   con.close();
               }  
           }catch(SQLException e){
        	   log.error("EntregaPedidoDAOImpl:traeEntregaPedido error", e);
        	   throw new Exception("Error en DAO, Al cerrar las conexiones"); 
           }
       }
		
		
	}

}
