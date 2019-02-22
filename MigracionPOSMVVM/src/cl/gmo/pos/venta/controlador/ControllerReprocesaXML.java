package cl.gmo.pos.venta.controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.Integracion.Factory.ConexionFactory;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import oracle.jdbc.OracleTypes;

public class ControllerReprocesaXML implements Serializable {

	
	private static final long serialVersionUID = -4127068240768361820L;
	Logger log = Logger.getLogger( this.getClass() );	
	
	Session sess = Sessions.getCurrent();	
	private String usuario;	
	private String sucursalDes;	
	
	private String local;
	private Date fecha;
	private String folio;
	
	Utils utils = new Utils();
	SeleccionPagoForm seleccionPago = new SeleccionPagoForm();
	
	Connection con = null;
	
	
	@Init
	public void inicial() {	
		
		usuario = (String)sess.getAttribute(Constantes.STRING_USUARIO);		
		sucursalDes = (String)sess.getAttribute(Constantes.STRING_NOMBRE_SUCURSAL);		
		
	}
	
	
	
	@Command
	public void procesar(@BindingParam("pLocal")String pLocal,@BindingParam("pFecha")Date pFecha ) {
		
		String fecha="";
		String local="";
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");		
		
		fecha = dt.format(pFecha);
		local = pLocal;		
		String cdg="";
		String serie="";
		String numero="";
		
		Statement statement=null;
		String sql="";
		
		sql="select cdg,serie,numero from ALBVTCB where fecha = to_date('"+fecha+"','yyyyMMdd') and serie='"+local+"'";
		
    	String usuario="gmo";
    	String pass="249gmo.,";
    	String driver="oracle.jdbc.driver.OracleDriver";
    	
    	try{	
    		//Class.forName(driver).newInstance();
    		//PROD
    		con =DriverManager.getConnection("jdbc:oracle:thin:@10.216.4.34:1521:POSCL",usuario,pass);
    		
    		//QA
    		//con =DriverManager.getConnection("jdbc:oracle:thin:@10.216.4.134:1521:POSCL",usuario,pass);
    		  
    		  statement= con.prepareStatement(sql);
    		  ResultSet rs  = statement.executeQuery(sql);
    		  
    		  
    			
    		  while(rs.next()) {
    			  
    			  cdg= rs.getString(1);
    			  serie= rs.getString(2);
    			  numero= rs.getString(3);
    			  System.out.println(cdg);
    			  creaArchivo(cdg,serie,numero,fecha);
    			 
    		  }   		 
    		  
    		  statement.close();
      			con.close();
    		
    	   
    	}catch(Exception e) {    		
    		 System.out.println("Error de conexion");
    	}		
	}
	
	
	public void creaArchivo(String param, String param2, String param3,String param4) {		
		
		CallableStatement cs = null;
		ArrayList<TiendaBean> tienda = new ArrayList<TiendaBean>();
		String salidaXML  =null;
		
		String ruta = "c://archivos//" + param4 + "_" + param2 + "_" +param3 + ".xml";
        File archivo = new File(ruta);
        FileWriter fw=null;
        BufferedWriter bw =null;
		
		con = ConexionFactory.INSTANCE.getConexion();
		
		try {
			cs = con.prepareCall("{call  SP_GENERA_XML_AERO(?,?)}");
			cs.setString(1,param);		
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.execute();
			
			salidaXML = (String) cs.getObject(2); 
		     
			archivo.createNewFile();
			fw = new FileWriter(archivo);
            bw = new BufferedWriter(fw);
            bw.write(salidaXML);
            bw.close();			
			
			System.out.println(salidaXML);
			
			con.close();
			
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
		}		
		
	}	
	
	
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSucursalDes() {
		return sucursalDes;
	}

	public void setSucursalDes(String sucursalDes) {
		this.sucursalDes = sucursalDes;
	}
	
	public String getLocal() {
		return local;
	}
	
	public void setLocal(String local) {
		this.local = local;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

}
