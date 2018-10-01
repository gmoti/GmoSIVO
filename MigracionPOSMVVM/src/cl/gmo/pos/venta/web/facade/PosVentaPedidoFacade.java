/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;
import java.util.Date;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoPendientesDAOImpl;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.PedidosPendientesBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TipoPedidoBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;

/**
 *
 * @author Advice70
 */
public class PosVentaPedidoFacade {
    
    public static int traeCodigoVenta(String local)
    {
        int codigo = Constantes.INT_CERO;
        try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            codigo = ventaPedidoimpl.traeCodigoVenta(local);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigo;
    }

	public static String traeCodigoSuc(String local) {
		String codigo = Constantes.STRING_BLANCO;
        try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            codigo = ventaPedidoimpl.traeCodigo_Suc(local);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigo;
	}

	public static VentaPedidoBean traeGenericos(String local) {
		VentaPedidoBean bean = new VentaPedidoBean();
        try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            bean = ventaPedidoimpl.traeGenericosFormulario(local);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
	}

	public static ArrayList<TipoPedidoBean> traeTiposPedido() {
		ArrayList<TipoPedidoBean> listaTiposPedidos = new ArrayList<TipoPedidoBean>();
        try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            listaTiposPedidos = ventaPedidoimpl.traeTiposPedidos();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTiposPedidos;
	}

	public static Date traeFechaEntrega(String fecha, String local,
			String familia, String subFamilia, String grupoFamilia, String tipo,double esfera,double cilindro ) {
		Date fecha_e = new Date();
		try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            fecha_e = ventaPedidoimpl.traeFechaEntrega(fecha, local, familia, subFamilia, grupoFamilia, tipo,esfera,cilindro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return fecha_e;
	}

	public static Date traeFechaEntregaExepcionFeriados(Date fecha_ini, String fecha_enc) {
		
		Date fecha_e = new Date();
		try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            fecha_e = ventaPedidoimpl.traeFechaEntregaExepcionFeriados(fecha_ini, fecha_enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return fecha_e;
	}

	public static void insertaPedido(VentaPedidoBean pedido, String local) {
		try {
            VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
            ventaPedidoimpl.insertaPedido(pedido, local);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static String insertaMultiofertaCB(ProductosBean producto,
			String codigo_venta, int linea, String fecha, int numero_venta, String local) {
			String mensaje = Constantes.STRING_BLANCO;
		try {
			VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
			mensaje=ventaPedidoimpl.insertaMultiofertaCB(producto, codigo_venta, linea, fecha, numero_venta, local);		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return mensaje;
		
	}

	public static int insertaDetalle(ProductosBean productosBean, int x,
			String codigo, String local) {
		int ident = Constantes.INT_CERO;
		try {
			VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
			ident = ventaPedidoimpl.insertaPedidoDetalle(productosBean, x, codigo, local);
		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return ident;
		
	}

	public static String insertaMultiofertaDetalle(
			int cdg_correlativo_multioferta, ProductosBean productosMulti,
			int linea, String fecha, String local, String codigo) {
		String mje = Constantes.STRING_BLANCO;
		try {
			VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
			mje= ventaPedidoimpl.insertaMultiofertaDetalle(cdg_correlativo_multioferta, productosMulti, linea, fecha, local, codigo);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return mje;
		
	}

	public static void insertaSuplemento(SuplementopedidoBean suple,
			ProductosBean productosBean, int linea, int identidad) {
		try {
			VentaPedidoDAOImpl ventaPedidoimpl = new VentaPedidoDAOImpl();
			ventaPedidoimpl.insertaSuplementosLinea(suple, productosBean, linea, identidad);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
	}	
	public static ArrayList<PedidosPendientesBean> traePedidosPendientes(String codigoCliente,String sucursal, String pedido, String agente, String nif_cliente, String fecha) {
		ArrayList<PedidosPendientesBean> listaPedidos = new ArrayList<PedidosPendientesBean>();
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		try {
			listaPedidos = ventaPedidoPendientesDAOImpl.traePedidosPendientes(codigoCliente,sucursal, pedido, agente, nif_cliente, fecha);
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return listaPedidos;
	}

	public static void insertaPago(String codigo, String forma_pago,
			int cantidad, String fecha, String divisa, int cambio,
			String local, int cantidad2, String devolucion, String usuario,
			String numero_bono, Double dto,String rut_vs) {
		try {
			VentaPedidoDAOImpl.insertaPago(codigo, forma_pago, cantidad, fecha, divisa, cambio, local, cantidad2, devolucion, usuario, numero_bono, dto,rut_vs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static String insertaDocumento(String ticket, int documento,
			String tipo_doc, long total, String fecha, String local,String nisapre,String dto) {
		String res ="";
		try {
			res = VentaPedidoDAOImpl.insertaDocumento(ticket, documento, tipo_doc, total, fecha, local,nisapre,dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public static PedidosPendientesBean traePedidosSeleccionado(String codigoPedido) {
		PedidosPendientesBean pedidosSelec = new PedidosPendientesBean();
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		try {
			pedidosSelec = ventaPedidoPendientesDAOImpl.traePedidosSeleccionado(codigoPedido);
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return pedidosSelec;
	}
	public static ProductosBean traeMultiofertacb(ProductosBean prod)
	{
		ProductosBean producto = new ProductosBean();
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		try {
			producto = ventaPedidoPendientesDAOImpl.traeMultiofertaCB(prod);
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return producto;
		
	}
	public static boolean eliminarPedido(String codigoPedido) {
		boolean pedidosDelete = false;
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		try {
			pedidosDelete = ventaPedidoPendientesDAOImpl.eliminarPedido(codigoPedido);
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return pedidosDelete;
	}
	public static boolean pedidoEntrega(String codigoPedido, String sucursal, VentaPedidoForm form) {
		boolean pedidosDelete = false;
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		try {
			pedidosDelete = ventaPedidoPendientesDAOImpl.pedidoEntrega(codigoPedido, sucursal, form);
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
		return pedidosDelete;
	}

	public static void traeMultiofertaLn(ProductosBean prod, ArrayList<ProductosBean> lista_multi) {
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		try {
			System.out.println("Checked traeMultiofertaLn ==> "+prod.getCantidad()+"<=> LISTA "+lista_multi.size()+"\n");
			ventaPedidoPendientesDAOImpl.traeMultiofertaLN(prod, lista_multi);
		} catch (Exception e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
	}

	public static ArrayList<SuplementopedidoBean> traeTratamientosPedido(
			int ident) {
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		ArrayList<SuplementopedidoBean> listaSuple = new ArrayList<SuplementopedidoBean>();
		try {
			listaSuple = ventaPedidoPendientesDAOImpl.traeTratamientosPedido(ident);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaSuple;
	}

	public static void insertaAdicionalesArcli(int identidad,
			ProductosBean productosBean) 
	{
			try {
				VentaPedidoDAOImpl.insertaAdicionalesArcli(identidad, productosBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
	}

	public static void traeAdicionalArcli(ProductosBean prod) {
		try {
			VentaPedidoDAOImpl.traeAdicionalesArcli(prod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static String[][] valida_seguro_garantia(String cdg,
			String encargo_garantia) {
		String[][] estado = null;
		try {
			estado = VentaPedidoDAOImpl.ValidaSeguroGarantia(cdg, encargo_garantia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}

	public static void insertaMultiofertaDetalleSuplemento(int indice,
			SuplementopedidoBean suple) {
		VentaPedidoDAOImpl pedido = new VentaPedidoDAOImpl();
		try {
			pedido.insertaMultiofertaDetalleSuplemento(indice, suple);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static ArrayList<SuplementopedidoBean> traeTratamientosPedidoMultiofertas(
			int ident) {
		VentaPedidoPendientesDAOImpl ventaPedidoPendientesDAOImpl = new VentaPedidoPendientesDAOImpl();
		ArrayList<SuplementopedidoBean> listaSuple = new ArrayList<SuplementopedidoBean>();
		try {
			listaSuple = ventaPedidoPendientesDAOImpl.traeTratamientosPedidoMultiofertas(ident);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaSuple;
	}

	public static boolean valida_promocion_edad_tu_descuento(ProductosBean prod, String codigo_promocion) {
		boolean estado = false;
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		try {
			estado = impl.valida_promocion_edad_tu_descuento(prod, codigo_promocion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}

	public static boolean validaCristalRecetaADDDesdeHasta(ProductosBean prod, GraduacionesBean graduacion) {
		boolean estado = false;
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		try {
			estado = impl.validaCristalRecetaADDDesdeHasta(prod, graduacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}
	/*
	 * LMARIN 20131220 
	 */
	public static boolean  validaTipoPedido(String encargo,String tienda) throws Exception{
		boolean estado = false;		
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		return impl.validaTipoPedido(encargo,tienda);
	} 
	
	public static int  inserta_historial_encargo(String tipo_dev,String encargo_padre,String encargo,String fecha,String nif) throws Exception{
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		return impl.inserta_historial_encargo(tipo_dev,encargo_padre,encargo,fecha,nif);
	} 
	
	/*
	* LMARIN 20130417
	*/
	public static int  reimprimeboleta(String codigo, String encargo,String fecha) throws Exception{
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		return impl.reimprimeboleta(codigo, encargo, fecha);
	} 
	/*
	* LMARIN 20150421
	*/
	public static ArrayList<PromocionBean> traePromociones(){
		VentaPedidoDAOImpl impl = new VentaPedidoDAOImpl();
		ArrayList<PromocionBean> promo = new ArrayList<PromocionBean>();
		try{
			promo = impl.traePromociones();
		}catch(Exception e){
			e.printStackTrace();
		}
		return promo;
	} 
}
