/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.web.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.CopiaGuiaBoletaImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.LoginDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.UtilesDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DTO.SucursalDTO;
import cl.gmo.pos.venta.web.Integracion.DTO.UsuarioDTO;
import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.PrecioEspecialBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.beans.TipoAlbaranBean;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.ConvenioFamBean;
import cl.gmo.pos.venta.web.beans.ConvenioLnBean;
import cl.gmo.pos.venta.web.beans.DivisaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.IdiomaBean;
import cl.gmo.pos.venta.web.beans.ListaEstadosBean;
import cl.gmo.pos.venta.web.beans.MenuBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.PromocionBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.SucursalesBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.SuplementosValores;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;
import cl.gmo.pos.venta.web.beans.TipoMotivoDevolucionBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.forms.ClienteForm;
import cl.gmo.pos.venta.web.forms.CopiaGuiaBoletaForm;

/**
 *
 * @author Advice70
 */
public class PosUtilesFacade{
	
	public static ArrayList<TipoMotivoDevolucionBean> traeMotivoDevolucion(){
		UtilesDAOImpl util = new UtilesDAOImpl();
		ArrayList<TipoMotivoDevolucionBean> lista_mot = new ArrayList<TipoMotivoDevolucionBean>();
		try{
			
			lista_mot = util.traeMotivoDevolucion();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lista_mot;
	}
	
	public static BigDecimal traeDescuento(String usuario, String pass, String tipo) {
		
		BigDecimal valor;
	        try {
	            
	        	UtilesDAOImpl utiles = new UtilesDAOImpl();
	        	valor = utiles.traeDecuento(usuario, pass, tipo);
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            valor = new BigDecimal(-1);
	        }
	        return valor;
		
	}
	public static ArrayList<TipoAlbaranBean> traeTipoAlbaranes() {
		
		 ArrayList<TipoAlbaranBean> lista_TipoAlbaranes = new ArrayList<TipoAlbaranBean>();
	        try {
	            
	            UtilesDAOImpl utiles = new UtilesDAOImpl();
	            lista_TipoAlbaranes = utiles.traeTipoAlbaranes();
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            lista_TipoAlbaranes = null;
	        }
	        return lista_TipoAlbaranes;
		
	}
    public static ArrayList<DivisaBean> traeDivisas()
    {
        ArrayList<DivisaBean> lista_Divisas = new ArrayList<DivisaBean>();
        try {
            
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Divisas = utiles.traeDivisas();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            lista_Divisas = null;
        }
        return lista_Divisas;
    }
    public static ArrayList<PromocionBean> traePromociones()
    {
        ArrayList<PromocionBean> listaPromociones = new ArrayList<PromocionBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            listaPromociones = utiles.traePromociones();
        } catch (Exception e) {
            e.printStackTrace();
            listaPromociones = null;
        }
        return listaPromociones;
    }
    public static ArrayList<FormaPagoBean> traeFormasPago()
    {
        ArrayList<FormaPagoBean> lista_formasPago = new ArrayList<FormaPagoBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_formasPago = utiles.traeFormasPago();
            
        } catch (Exception e) {
            e.printStackTrace();
            lista_formasPago = null;
        }
        return lista_formasPago;
    }
    public static ArrayList<IdiomaBean> traeIdiomas()
    {
        ArrayList<IdiomaBean> lista_idiomas = new ArrayList<IdiomaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_idiomas = utiles.traeIdiomas();
        } catch (Exception e) {
            e.printStackTrace();
            lista_idiomas = null;
        }
        return lista_idiomas;
    }
    public static ArrayList<AgenteBean> traeAgentes(String local)
    {
        ArrayList<AgenteBean> lista_Agentes = new ArrayList<AgenteBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Agentes = utiles.traeAgentes(local);
        } catch (Exception e) {
            e.printStackTrace();
            lista_Agentes = null;
        }
        return lista_Agentes;
    }
    public static ArrayList<ConvenioBean> traeConvenios()
    {
        ArrayList<ConvenioBean> lista_Convenios = new ArrayList<ConvenioBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Convenios = utiles.traeConvenios();
        } catch (Exception e) {
            e.printStackTrace();
            lista_Convenios = null;
        }
        return lista_Convenios;
    } 
    public static ArrayList<ConvenioBean> traeConvenios(String codigo, String nombre, String empresa)
    {
        ArrayList<ConvenioBean> lista_Convenios = new ArrayList<ConvenioBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Convenios = utiles.traeConveniosParametros(codigo, nombre, empresa);
        } catch (Exception e) {
            e.printStackTrace();
            lista_Convenios = null;
        }
        return lista_Convenios;
    } 
    public static ArrayList<FamiliaBean> traeFamilias( String form_origen)
     {
         ArrayList<FamiliaBean> lista_Familias = new ArrayList<FamiliaBean>();
         try {
             UtilesDAOImpl utiles = new UtilesDAOImpl();
             lista_Familias = utiles.traeFamilias(form_origen);
         } catch (Exception e) {
             e.printStackTrace();
             lista_Familias = null;
         }
         return lista_Familias;
     }
    
    public static ArrayList<TipoFamiliaBean> traeTipoFamilias( String form_origen, String codigoMultioferta)
    {
        ArrayList<TipoFamiliaBean> lista_tipo_Familias = new ArrayList<TipoFamiliaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_tipo_Familias = utiles.traeTipoFamilia(form_origen, codigoMultioferta);
        } catch (Exception e) {
            e.printStackTrace();
            lista_tipo_Familias = null;
        }
        return lista_tipo_Familias;
    }
    
    public static ArrayList<FamiliaBean> traeFamiliasMultiofertas( String form_origen, String codigoMultioferta, String tipoFamilia)
    {
        ArrayList<FamiliaBean> lista_Familias = new ArrayList<FamiliaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Familias = utiles.traeFamiliasMultiofertas(form_origen, codigoMultioferta, tipoFamilia);
        } catch (Exception e) {
            e.printStackTrace();
            lista_Familias = null;
        }
        return lista_Familias;
    }
    
    public static ArrayList<SubFamiliaBean> traeSubFamilias(String familia)
     {
         ArrayList<SubFamiliaBean> lista_SubFamilias = new ArrayList<SubFamiliaBean>();
         try {
             UtilesDAOImpl utiles = new UtilesDAOImpl();
             lista_SubFamilias = utiles.traeSubfamilias(familia);
         } catch (Exception e) {
             e.printStackTrace();
             lista_SubFamilias = null;
         }
         return lista_SubFamilias;
     }
    
    public static ArrayList<SubFamiliaBean> traeSubFamiliasMultiofertas(String familia, String codigoMultioferta)
    {
        ArrayList<SubFamiliaBean> lista_SubFamilias = new ArrayList<SubFamiliaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_SubFamilias = utiles.traeSubfamiliasMultiofertas(familia, codigoMultioferta);
        } catch (Exception e) {
            e.printStackTrace();
            lista_SubFamilias = null;
        }
        return lista_SubFamilias;
    }
    
    public static ArrayList<GrupoFamiliaBean> traeGrupoFamilias(String familia, String subfamilia)
     {
         ArrayList<GrupoFamiliaBean> lista_GrupoFamilias = new ArrayList<GrupoFamiliaBean>();
         try {
             UtilesDAOImpl utiles = new UtilesDAOImpl();
             lista_GrupoFamilias = utiles.traeGruposFamilias(familia, subfamilia);
         } catch (Exception e) {
             e.printStackTrace();
             lista_GrupoFamilias = null;
         }
         return lista_GrupoFamilias;
     }
    
    public static ArrayList<GrupoFamiliaBean> traeGrupoFamiliasMultiofertas(String familia, String subfamilia, String codigoMultioferta)
    {
        ArrayList<GrupoFamiliaBean> lista_GrupoFamilias = new ArrayList<GrupoFamiliaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_GrupoFamilias = utiles.traeGruposFamiliasMultioferta(familia, subfamilia, codigoMultioferta);
        } catch (Exception e) {
            e.printStackTrace();
            lista_GrupoFamilias = null;
        }
        return lista_GrupoFamilias;
    }
    
    public static ArrayList<ProvinciaBean> traeProvincias()
    {
        ArrayList<ProvinciaBean> lista_Provincias = new ArrayList<ProvinciaBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Provincias = utiles.traeProvincias();
        } catch (Exception e) {
            e.printStackTrace();
            lista_Provincias = null;
        }
        return lista_Provincias;
    }
    public static ArrayList<GiroBean> traeGiros()
    {
        ArrayList<GiroBean> lista_Giros = new ArrayList<GiroBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_Giros = utiles.traeGiros();
        } catch (Exception e) {
            e.printStackTrace();
            lista_Giros = null;
        }
        return lista_Giros;
    }
    public static ArrayList<ProveedorBean> traeProveedores()
     {
         ArrayList<ProveedorBean> lista_Proveedores = new ArrayList<ProveedorBean>();
         try {
             UtilesDAOImpl utiles = new UtilesDAOImpl();
             lista_Proveedores = utiles.traeProveedores();
         } catch (Exception e) {
             e.printStackTrace();
             lista_Proveedores = null;
         }
         return lista_Proveedores;
     }
    public static UsuarioDTO validaUsuario(String nombreUsuario, String clave) throws Exception{
         
         UsuarioDTO usuario = new UsuarioDTO();
         
         try{
             LoginDAOImpl login = new LoginDAOImpl();
             usuario = login.encuentraUsuario(nombreUsuario, clave);
             
         }catch (Exception e){
             throw new Exception("PosUtilesFacade: validaUsuario");
         }
         return usuario;
         //return LoginDAOImpl.encuentraUsuario(nombreUsuario, clave);        
     }
    public static ArrayList<SucursalesBean> traeNombreSucursal(String nombreUsuario) throws Exception{
         try{
         LoginDAOImpl login = new LoginDAOImpl();         
         UsuarioDTO usuarioSucursalDTO = login.traeSucursal(nombreUsuario);
         ArrayList<SucursalesBean> listaSucursales = new ArrayList<SucursalesBean>();
         
         for (SucursalDTO sucursalesDTO: usuarioSucursalDTO.getSucursales()){
             SucursalesBean sucursales= new SucursalesBean();
             sucursales.setDescripcion(sucursalesDTO.getDescripcion());
             sucursales.setSucursal(sucursalesDTO.getSucursal());
             sucursales.setTelefono(sucursalesDTO.getTelefono());
             sucursales.setTipo_boleta(sucursalesDTO.getTipo_boleta());
             listaSucursales.add(sucursales);
         }
         
         return listaSucursales;
         
     }catch (Exception e){
         throw new Exception("PosUtilesFacade: traeNombreSucursal");
     }
  
  }
    public static boolean validaAperturaCaja(String sucursal, String fecha) throws Exception{
	        try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
	        boolean aperturaCaja = utilDao.validaCaja(sucursal, fecha);
	       
	        return aperturaCaja;
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaAperturaCaja");
	    }
	  
	 }
    public static boolean validaEstadoPed(String codigo) throws Exception{
        try{
        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
        boolean aperturaCaja = utilDao.validaEstadoPed(codigo);
       
        return aperturaCaja;
        
    }catch (Exception e){
        e.printStackTrace();
        throw new Exception("PosUtilesFacade: validaEstadoPed");
    }
  
 }
    public static MenuBean llenaMenu(String usuario) throws Exception{
	        try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
	        MenuBean menu = utilDao.llenaMenu(usuario);
	       
	        return menu;
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: llenaMenu");
	    }
	 
	 }
    
    public static String  traeCodigoLocalSap(String local) throws Exception{
    	
	    try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
	        String codigoSap = utilDao.traeCodigoLocalSap(local);
	       
	        return codigoSap;
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeCodigoLocalSap");
	    }
 
    }
    

	public static String traeCodigoAgente(String agente)throws Exception{
		
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
	        String codigoAgente = utilDao.traeCodigoAgente(agente);
	       
	        return codigoAgente;
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeCodigoAgente");
	    }
		
	}
	public static CopiaGuiaBoletaForm traeCopiaGuiaBoleta(String numero, String tipo)throws Exception{
		
		try{
			CopiaGuiaBoletaImpl copiaGuiaBoleta = new CopiaGuiaBoletaImpl();
			CopiaGuiaBoletaForm copiaGuiaBoletas = copiaGuiaBoleta.traeCopiaGuiaBoleta(numero, tipo);
	        return copiaGuiaBoletas;
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeCodigoAgente");
	    }
		
	}
	
	public static ArrayList<SuplementopedidoBean> traeSuplementosOpcionales (String producto) throws Exception
	{
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
	        return  utilDao.traeSuplementosOpcionales(producto);
	        
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeSuplementosOpcionales");
	    }
		
	}
	
	public static ArrayList<SuplementopedidoBean> traeSuplementosObligatorios (String producto) throws Exception
		{
			try{
		        UtilesDAOImpl utilDao = new UtilesDAOImpl();         
		        return  utilDao.traeSuplementosObligatorios(producto);
		        
		    }catch (Exception e){
		        e.printStackTrace();
		        throw new Exception("PosUtilesFacade: traeSuplementosObligatorios");
		    }
			
		}
	public static ArrayList<OftalmologoBean> traeDoctor() throws Exception{
		
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        ArrayList<OftalmologoBean> lista_doctores = new ArrayList<OftalmologoBean>();
	        lista_doctores = utilDao.traeDoctor();
	       
	        return lista_doctores;
	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeDoctor");

	    }
		
	}
	public static int traeDescuentoConvenio(ProductosBean productosBean,
			String convenio, String fpago,String local)  throws Exception{
		
		int valor = 0;
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        valor = utilDao.traeDescuentoConvenio(productosBean, convenio, fpago,local);
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeDescuentoConvenio");
	    }
	}
	public static boolean validaEstadoConvenioCliente(String convenio,
			String cliente)  throws Exception{
		
		Boolean estado = false;
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        estado = utilDao.validaEstadoConvenioCliente(convenio, cliente);
	        return estado;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaEstadoConvenio");
	    }
	}
	public static ArrayList<SuplementosValores> traeValoresSuplementos(
			String suplemento,String codigo) throws Exception{
		
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.traeValoresSuplementos(suplemento,codigo);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeValoresSuplementos");
		}
	}
	public static boolean validaCrisMon(String familia, String subFamilia,
			String grupoFamilia, String codigo_montura, double esfera, double cilindro) throws Exception{
		
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.validaCrisMon(familia, subFamilia, grupoFamilia, codigo_montura, esfera, cilindro);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaCrisMon");
		}
	}
	public static boolean validaGafaGraduable(String codigo) throws Exception {
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.validaGafaGraduable(codigo);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaGafaGraduable");
		}
	}
	public static int traeNumeroMultioferta() {
		int numero = 0;
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			numero = utilDao.traeCodigoMultioferta();
		} catch (Exception e) {
			 e.printStackTrace();
		} 
		return numero;
	}


	public static GiroBean traeDescripGiroCliente(int cod_giro)
    {
		GiroBean giro = new GiroBean();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            giro = utiles.traeDescripGiroCliente(cod_giro);
        } catch (Exception e) {
            e.printStackTrace();
            giro = null;
        }
        return giro;
    }


	public static ProvinciaBean traeDescripProvinciasCliente(String codigo_provincia)
    {
        ProvinciaBean provincia = new ProvinciaBean();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            provincia = utiles.traeDescripProvinciasCliente(codigo_provincia);
        } catch (Exception e) {
            e.printStackTrace();
            provincia = null;
        }
        return provincia;
    }

	public static boolean validaCopiaGuiaBoleta(String numero, String tipo) {
		boolean estado = false;
		try {
			UtilesDAOImpl utiles = new UtilesDAOImpl();
			estado = utiles.validaCopiaGuiaBoleta(numero, tipo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return estado;
	}

	public static boolean validaEsferaMasCilindro(String familia,
			String subFamilia, String grupoFamilia, double esfera,
			double cilindro) {
		boolean estado = false;
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			estado = util.validaEsferaMasCilindro(familia, subFamilia, grupoFamilia, esfera, cilindro);
		} catch (Exception e) {
			e.printStackTrace();
			estado = false;
		}
		return estado;
	}

	public static String validaCrisMonSup(ProductosBean cristal, ProductosBean montura) {
		String estado = Constantes.STRING_BLANCO;
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			 estado = util.validaCrisMonSup(cristal, montura);
		} catch (Exception e) {
			e.printStackTrace();
			estado = Constantes.STRING_BLANCO;;
		}
		return estado;
	}


	public static ArrayList<GiroBean> busquedaGiro(String giro, String descripcionGiro){
		ArrayList<GiroBean> lista = new ArrayList<GiroBean>();
		UtilesDAOImpl util = new UtilesDAOImpl();
	    try{	
	    	
			lista = util.busquedaGiro(giro, descripcionGiro);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return lista;
	}

	public static ArrayList<ConvenioLnBean> traeDescuentosConvenios(String id) {
		ArrayList<ConvenioLnBean> lista = new ArrayList<ConvenioLnBean>();
		UtilesDAOImpl util = new UtilesDAOImpl();
	    try{	
			lista = util.traeConvenioLineas(id);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return lista;
	}

	public static ArrayList<ConvenioFamBean> traeDescuentosConveniosFamilias(
			int ident) {
		ArrayList<ConvenioFamBean> lista = new ArrayList<ConvenioFamBean>();
		UtilesDAOImpl util = new UtilesDAOImpl();
	    try{	
			lista = util.traeConvenioLineasFamilias(ident);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return lista;
	}

	public static Integer[] traeValoresFenix(ProductosBean[] productos) {
		
		Integer valores[] = new Integer[2];
		UtilesDAOImpl util = new UtilesDAOImpl();
		try {
			valores = util.traeValoresFenix(productos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return valores;
		
	}
    
	public static ArrayList<BoletaBean> traeListaBoletas(String cdg_vta){
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		try{
			UtilesDAOImpl util = new UtilesDAOImpl();
			lista_boletas = util.traeListaBoletas(cdg_vta);
		}catch(Exception ex){
			
		}
		return lista_boletas;
	}
	
	public static ArrayList<BoletaBean> traeListaBoletasAlbaranes(String cdg_vta, String tipo){
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		try{
			UtilesDAOImpl util = new UtilesDAOImpl();
			lista_boletas = util.traeListaBoletasAlbaranes(cdg_vta, tipo);
		}catch(Exception ex){
			
		}
		return lista_boletas;
	}
	
	public static boolean eliminaAlbaran(String cdg, String fecha, String local, String tipo_albaran){
		boolean respuesta=false;
		UtilesDAOImpl util = new UtilesDAOImpl();
		try{
			
			respuesta = util.eliminaAlbaran(cdg, fecha, local, tipo_albaran);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return respuesta;
	}
	
	public static boolean isController(String agente){
		boolean respuesta = false;
		UtilesDAOImpl util = new UtilesDAOImpl();
		try{
			
			respuesta = util.isController(agente);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	public static ArrayList<BoletaBean> traeListaBoletasFechas(String codigo_0,
			String fecha) {
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		try{
			UtilesDAOImpl util = new UtilesDAOImpl();
			lista_boletas = util.traeListaBoletasFechas(codigo_0, fecha);
		}catch(Exception ex){
			
		}
		return lista_boletas;
	}
	
	public static AlbaranBean traeCodigoAlbaran(String cdg_pedvtcb){
		AlbaranBean alb = null;
		
		try{
			
			UtilesDAOImpl util = new UtilesDAOImpl();
			alb = util.traeCodigoAlbaran(cdg_pedvtcb);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return alb;
	}

	public static String validaProductosMultiofertaBD(
			int cdg_correlativo_multioferta, String vta, String tipo) {
		String mje = Constantes.STRING_BLANCO;
		
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			mje = util.validaProductosMultiofertaBD(cdg_correlativo_multioferta, vta, tipo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return mje;
	}
	
	public static ArrayList<PrecioEspecialBean> traePrecioEspecial(String codigoBarras, int cantidad, String fecha){
		ArrayList<PrecioEspecialBean> listaPrecios = new ArrayList<PrecioEspecialBean>();
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			listaPrecios = util.traePrecioEspecial(codigoBarras, cantidad, fecha);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return listaPrecios;
	}

	public int validaDiametroESFCL(ProductosBean productosBean) {
		int valor = 0;
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			valor = util.validaDiametroESFCL(productosBean);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return valor;
	}

	public static boolean validaEstadoPedidoEntregado(String codigoPendiente) {
		boolean estado = false;
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			estado = util.validaEstadoPedidoEntregado(codigoPendiente);
		} catch (Exception e) {
			estado = false;
		}
		return estado;
	}

	public static double validaEsferaMasCilindroMasAdd(String familia,
			String subFamilia, String grupoFamilia, double esfera,
			double cilindro, double adicion) {
		double valor;
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			valor = util.validaEsferaMasCilindroMasAdd(familia, subFamilia, grupoFamilia, esfera, cilindro, adicion);
		} catch (Exception e) {
			valor = 0;
		}
		return valor;
	}

	public static boolean validaProhibCrisArm(String familia,
			String subFamilia, String grupoFamilia, String codigo) {
		boolean valor;
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			valor = util.validaProhibCrisArm(familia, subFamilia, grupoFamilia, codigo);
		} catch (Exception e) {
			valor = false;
		}
		return valor;
	}
		
	/*
	 * LMARIN 20131224 
	 */
	public static int  validExPed(String encargo,String nif) throws Exception{
		boolean estado = false;		
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.validExPed(encargo,nif);
	} 
	
	/*LMARIN 20140122*/
	
	public static ArrayList<VentaPedidoBean> trae_grupos_encargo(String encargo){
		ArrayList<VentaPedidoBean> listaGrupos = new ArrayList<VentaPedidoBean>();
		try {
			UtilesDAOImpl util = new UtilesDAOImpl();
			listaGrupos = util.trae_grupos_encargo(encargo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return listaGrupos;
	}
	
	/*LMARIN 20140122*/
	
	public static int  validExTienda(String numero) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.validExTienda(numero);
	} 
	
	/*LMARIN 20140305*/
	
	public static int  valida_dto_seg_armazon(String cdg,String local,String enc) throws Exception{
		System.out.println("Paso por  ====> Facade  valida_dto_seg_armazon");
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_dto_seg_armazon(cdg,local,enc);
	} 
	 
	/*LMARIN 20140331*/
	
	public static int  valida_tipo_convenio(String tipoconvenio) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_tipo_convenio(tipoconvenio);
	} 
	
	
	/*LMARIN 20140414*/	
	public static int  valida_permiso_mod_fpago(String user,String pass,String local) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_permiso_mod_fpago(user,pass,local);
	} 
	
	/*LMARIN 20140414*/	
	public static int  respaldo_boleta(String encargo,String responsable,String fecha,String tipo) throws Exception{
		
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.respaldo_boleta(encargo, responsable,fecha,tipo);
		
	} 
	
	/*LMARIN 20140428*/	
	public static int  valida_productos_gratuitos(String encargo ) throws Exception{
		
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_productos_gratuitos(encargo);
		
	} 
	
	/*
	 * LMARIN 20140613
	 */
	public static int  traeMontoMinDescuento(ProductosBean prod,String convenio,String fpago,int dto) throws Exception{
		
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.traeMontoMinDescuento(prod,convenio,fpago,dto);
		
	} 
	
	/*
	 * M�todo que devuelve un Objeto con los datos de la tienda
	 * LMARIN 20140807
	 * @param String local
	 * @return ArrayList<TiendaBean>
	 */
	public static ArrayList<TiendaBean> traeDatosTienda(String local) throws Exception{
		
		UtilesDAOImpl impl = new UtilesDAOImpl();
		System.out.println("Traigo datos desde tienda facade ==> "+ local );
		return impl.traeDatosTienda(local);
		
	} 
	/*
	 * M�todo que devuelve un Objeto con los datos de la tienda
	 * LMARIN 20140821
	 * @param String fecha
	 * @return int dias 
	 */
	public static Date agregaDias(String fecha) throws Exception{
		
		UtilesDAOImpl impl = new UtilesDAOImpl();	
		return impl.agregaDias(fecha);
		
	} 
	/* 
	 * LMARIN 20140828
	 * M�todo que devuelve las multiofertas asociadas a un trio
	 * @param String armazon
	 * @param String cristal
	 * @param String tienda
	 * @return ArrayList<ProductosBean> codigo multiofertas
	 */
	
	public static ArrayList<ProductosBean> traeMultiofertasAso(String armazon,String cristal,String tienda) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();	
		return impl.traeMultiofertasAso(armazon, cristal, tienda);
	} 
	
	
	/* 
	 * LMARIN 20150327
	 * M�todo que devuelve DTO asociado a los combos de multiofertas seleccionadas
	 * @ param String mofer1,mofer2
	 * @ return int DTO Combos Multiofertas
	 */
	public static int traeDTOMofercombos(String mofer1,String mofer2) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.traeDTOMofercombos(mofer1, mofer2);
	}
	
	/** 
	 * @AUTHOR LMARIN 20151110
	 * M�todo que devuelve DTO asociado A PROMO LEC
	 * @param String encargo
	 * @return int DTO promo LEC
	 */
	public static int validaPromoLec(String encargo,String convenio) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.traeMontoDTOPromoLec(encargo,convenio);
	}
	
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20160803
	 */
	public static int gruposLentillas(String encargo) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.gruposLentillas(encargo);
		
	}
	
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20160830
	 */
	public static String traeArticuloRel(String encargo,String tipofam,String grupo) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.traeArticuloRel(encargo,tipofam,grupo);
		
	}
	
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20170526
	 */
	public static boolean validaLentillaGraduable(String codigo) throws Exception {
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.validaLentillaGraduable(codigo);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaGafaGraduable");
		}
	}
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20170821
	 * @DESCRIPCION: Metodo que valida Venta Seguro
	 */
	public static int validaVentaSeguro(String codigo) throws Exception {
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.validaVentaSeguro(codigo);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaVentaSeguro");
		}
	}
	
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20171010
	 * @DESCRIPCION: Metodo que valida la liberacion automatica
	 */
	public static String validaLibau(String local) throws Exception {
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.validaLibau(local);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: validaLibau");
		}
	}
	
	/** 
	 * @throws Exception 
	 * @AUTHOR LMARIN 20171020
	 * @DESCRIPCION: Metodo que trae importe del encargo anterior en la venta seguro
	 */
	public static String traeImporteVG(String encargo) throws Exception {
		try {
			UtilesDAOImpl utilDao = new UtilesDAOImpl();
			return utilDao.traeImporteVG(encargo);
		} catch (Exception e) {
			e.printStackTrace();
	        throw new Exception("PosUtilesFacade: traeImporteVG");
		}
	}
	
    public static ArrayList<FormaPagoBean> traeFormasPagoTienda(String local)
    {
        ArrayList<FormaPagoBean> lista_formasPago = new ArrayList<FormaPagoBean>();
        try {
            UtilesDAOImpl utiles = new UtilesDAOImpl();
            lista_formasPago = utiles.traeFormasPagoTienda(local);
            
        } catch (Exception e) {
            e.printStackTrace();
            lista_formasPago = null;
        }
        return lista_formasPago;
    }
    
    public static int traeDescuentoCupon(ProductosBean productosBean,
			String cupon, String fpago,String local,String agente, String cliente,String encargo)  throws Exception{
		
		int valor = 0;
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        valor = utilDao.traeDescuentoCupon(productosBean, cupon, fpago,local,agente,cliente,encargo);
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: valida_cupon");
	    }
	}
    
    public static String valida_cupon(String cupon,String nif,String cdg)  throws Exception{
		
		String valor = "";
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        valor = utilDao.valida_cupon(cupon,nif,cdg);
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: valida_cupon");
	    }
	}
    
    public static int IngresaCupon(String cupon,String local,String agente, String cliente,String encargo)  throws Exception{
		
		int valor = 0;
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        valor = utilDao.IngresaCupon(cupon,local,agente,cliente,encargo);
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: IngresaCupon");
	    }
	}
    
    public static int valida_encargo(String encargo,String cliente) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_encargo(encargo,cliente);
	}
    
    public static double traeDescuentoCombo(ProductosBean productosBean,String local,String agente, String cliente)  throws Exception{
		
		double valor = 0;
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        valor = utilDao.traeDescuentoCombo(productosBean,local,agente,cliente);
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: valida_cupon");
	    }
	}
    
    public static int valida_pcombo(String codigo) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_armazon_pcombo(codigo);
	}
    
    public static String valida_promo_pares(String codigo) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.valida_promo_pares(codigo);
	}
   
    public static ArrayList<ProductosBean> traeProductosGraduados(String ojo, String tipo_fam, String familia, String subfamilia, String grupo,
	           String descripcion, String codigoBusqueda, String codigoBarraBusqueda, String local,double cilindro,double esfera,int eje) throws Exception{
		UtilesDAOImpl impl = new UtilesDAOImpl();
		return impl.traeProductosGraduados(ojo,tipo_fam,familia,subfamilia,grupo,
		           descripcion, codigoBusqueda, codigoBarraBusqueda, local, cilindro, esfera,eje);
	}
    
    public static String valida_usuario_vp(String encargo,String nif,int monto)  throws Exception{
		
		String valor = "";
		try{
	        UtilesDAOImpl utilDao = new UtilesDAOImpl();
	        valor = utilDao.valida_usuario_vp(encargo,nif,monto);
	        return valor;

	    }catch (Exception e){
	        e.printStackTrace();
	        throw new Exception("PosUtilesFacade: valida_cupon");
	    }
	} 
    
    public static boolean validaEstadoLiberado(String encargo) throws Exception{
    	  boolean valor = false;
    	  UtilesDAOImpl utilDao = new UtilesDAOImpl();
	      valor = utilDao.validaEstadoLiberado(encargo);
	      return valor;
    }
 
}

