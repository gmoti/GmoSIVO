package cl.gmo.pos.venta.respuesta;

import java.io.Serializable;
import org.zkoss.zul.Messagebox;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;


//Evalua la respuesta de los formularios
//Form: VentaDirectaForm

public class RespuestaVentaDirecta implements Serializable {
	
	private static final long serialVersionUID = 8261007366490274439L;
	
	public RespuestaVentaDirecta() {
		
	}
	
	
	public VentaDirectaForm evaluaEstado(VentaDirectaForm form) {
		
		VentaDirectaForm formulario=form;
		String estado="";		
		
		estado = formulario.getEstado();		
		
		switch (estado) {
		case Constantes.STRING_PRODUCTOS_NO_ENCONTRADO:	
			mensajeBasico("Producto no encontrado");
			break;
		
		case Constantes.STRING_PRODUCTO_PRECIO_ESPECIAL:
			/*boton = confirm("El producto tiene precio especial, ¿desea aplicarlo?");
			if (boton)
			{
			  	document.getElementById('accion').value = "aplicaPrecioEspecial";
    			document.ventaDirectaForm.submit();
			}*/
			
			break;
			
		case Constantes.STRING_FIN:
			mensajeBasico("Venta almacenada con éxito");
			/*estado = est;
	 		alert("Venta almacenada con éxito");
	 		var cmb_tipo_albaran = document.getElementById('tipo_albaran');
	 		var cmb_cajero = document.getElementById('cajero');
	 		var cmb_agente = document.getElementById('agente');
	 		
	 		cmb_agente.disabled= true;
	 		cmb_cajero.disabled= true;
	 		cmb_tipo_albaran.disabled= true;
	 		
	 		parent.carga_url_padre('<%=request.getContextPath()%>/Menu.do?method=CargaMenu');*/
			
			break;

		case Constantes.STRING_GUARDADO:
			mensajeBasico("Venta almacenada con éxito");
			break;
		
		case "ERROR_GUARDADO":
			mensajeBasico("La venta no se pudo guardar. intentelo nuevamente");
			break;	
		
		case "ERROR_VALIDACION_MULTIOFERTA":
			mensajeBasico("la multioferta no se guardo correctamente");
			break;
			
		case "VALIDACION_MULTIOFERTA_OK":
			//genera_venta();
			break;
		
		case Constantes.STRING_PRODUCTOS_GRADUABLE:
			mensajeBasico("Los Lentes de Contacto con Graduación no se pueden registrar por Venta Directa");
			break;
			
		case Constantes.STRING_CARGA_MULTIOFERTAS:	
			/*var codigo = document.getElementById('codigo_mult').value;
			var index = document.getElementById('index_multi').value;
	 		showPopWin("<%=request.getContextPath()%>/BusquedaProductosMultiOfertas.do?method=cargaBusquedaProductosMultiOfertas&formulario=DIRECTA&codigoMultioferta="+codigo+"&index_multi="+index+"", 714, 425, null, false);
			*/
			break;
			
		default:
			mensajeBasico("Mensaje no definido en Venta Directa");
			break;
		}
				
		return formulario;
	}
	
	
	private void mensajeBasico(String mensaje) {	
		Messagebox.show(mensaje);
	}	
	
	

}
