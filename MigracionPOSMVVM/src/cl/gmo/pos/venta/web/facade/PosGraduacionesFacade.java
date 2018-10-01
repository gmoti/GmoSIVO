package cl.gmo.pos.venta.web.facade;

import java.util.ArrayList;

import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.GraduacionesDAOImpl;
import cl.gmo.pos.venta.web.Integracion.DAO.DAOImpl.VentaPedidoDAOImpl;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.forms.ContactologiaForm;

public class PosGraduacionesFacade {

		
	public static ArrayList<GraduacionesBean> traeGraduacionesClientes(String cliente) throws Exception{
		
		GraduacionesDAOImpl grad = new GraduacionesDAOImpl();
		ArrayList<GraduacionesBean> lista_Graduaciones = new ArrayList<GraduacionesBean>();
		try {
			lista_Graduaciones = grad.traeGraduacionesCliente(cliente);
		} catch (Exception e){
            e.printStackTrace();
            throw new Exception("PosGraduacionesFacade: traeGraduacionesClientes");
        }
		return lista_Graduaciones;
	}
	
	public static GraduacionesBean traeUltimaGraduacionCliente(String cliente) throws Exception{
		
		GraduacionesDAOImpl grad = new GraduacionesDAOImpl();
		GraduacionesBean graduacion = new GraduacionesBean();
		try {
			graduacion = grad.traeUltimaGraduacionCliente(cliente);
		} catch (Exception e){
            e.printStackTrace();
            throw new Exception("PosGraduacionesFacade: traeUltimaGraduacionCliente");
        }
		return graduacion;
	}

	public static boolean ingresaGraduacion(GraduacionesBean graduacion){
		GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
		boolean respuesta = false;
		try{
			
			respuesta = graduacionDao.ingresaGraduacion(graduacion);
			
		}catch(Exception ex){
			ex.printStackTrace();
			respuesta = false;
		}
		return respuesta;
	}

	public static GraduacionesBean traeGraduacionPedido(String cliente, String fecha, Double numero){
		
		GraduacionesBean graduacion = new GraduacionesBean();
		try {
            VentaPedidoDAOImpl ventaPeridoimpl = new VentaPedidoDAOImpl();
            graduacion = ventaPeridoimpl.traeGraduacionPedido(cliente, fecha, numero);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return graduacion;
	}
	
	public static int traeNumeroGraduacion(String cliente, String fecha){
		
		int numero = 0;
		try {
            GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
            numero = graduacionDao.traeNumeroGraduacion(cliente, fecha);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return numero;
	}
	
	public static boolean ingresaContactologia(ContactologiaBean contacto){
		boolean respuesta=false;
		try{
			
			GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
			respuesta = graduacionDao.ingresaContactologia(contacto);			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return respuesta;
	}
	
	public static ArrayList<ContactologiaBean> traeContactologiaCliente(String cliente){
		ArrayList<ContactologiaBean> lista_contacto = new ArrayList<ContactologiaBean>();
		GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
		try{			
			
			lista_contacto = graduacionDao.traeContactologiaCliente(cliente);
			
		}catch(Exception ex){
			System.out.println(ex);
		}		
		return lista_contacto;
	}
	
	public static ContactologiaBean traeContactologiaClienteUltima(String cliente){
		ContactologiaBean contacto = new ContactologiaBean();
		GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
		try{			
			
			contacto = graduacionDao.traeContactologiaClienteUltima(cliente);
			
		}catch(Exception ex){
			System.out.println(ex);
		}		
		return contacto;
	}

	
	 public static boolean existeContactologiaPresEncargo(int numero, String fecha, String cliente, String pagina){
		 boolean respuesta=false;
		 try{			 
			 GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
			 respuesta = graduacionDao.existeContactologiaPresEncargo(numero, fecha, cliente, pagina);			
		 }catch(Exception ex){
			ex.printStackTrace(); 
		 }
		 
		 return respuesta;
	 }
	 
	 public static boolean modificaContactologia(ContactologiaBean contacto){
			boolean respuesta=false;
			try{
				
				GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
				respuesta = graduacionDao.modificaContactologia(contacto);			
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			return respuesta;
		}
	 
	 public static boolean modificaGraduacion(GraduacionesBean graduacion){
			GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
			boolean respuesta = false;
			try{
				
				respuesta = graduacionDao.modificaGraduacion(graduacion);
				
			}catch(Exception ex){
				ex.printStackTrace();
				respuesta = false;
			}
			return respuesta;
		}
	 public static ContactologiaBean traeContactologia(int numero, String fecha, String cliente){
		 ContactologiaBean graduacion = new ContactologiaBean();
		 GraduacionesDAOImpl graduacionDao = new GraduacionesDAOImpl();
			boolean respuesta = false;
			try{
				
				graduacion = graduacionDao.traeContactologia(numero, fecha, cliente);
				
			}catch(Exception ex){
				ex.printStackTrace();
				respuesta = false;
			}
			return graduacion;
	 }
}
