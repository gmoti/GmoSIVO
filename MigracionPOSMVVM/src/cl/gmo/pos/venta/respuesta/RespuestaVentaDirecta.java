package cl.gmo.pos.venta.respuesta;

import java.io.Serializable;

import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;


//Evalua la respuesta de los formularios
//Form: VentaDirectaForm

public class RespuestaVentaDirecta implements Serializable {
	
	private static final long serialVersionUID = 8261007366490274439L;
	
	public RespuestaVentaDirecta() {
		
	}
	
	
	private VentaDirectaForm evaluaEstado(VentaDirectaForm form) {
		
		VentaDirectaForm formulario=form;
		String estado="";		
		
		estado = formulario.getEstado();		
		
		switch (estado) {
		case Constantes.STRING_PRODUCTOS_NO_ENCONTRADO:
			
			break;

		default:
			break;
		}
		
		
		
		
		
		/*
		if (est == "producto_no_encontrado") {
			alert("Producto no encontrado");
		}
		if (est == "producto_precio_especial") 
		{
			boton = confirm("El producto tiene precio especial, ¿desea aplicarlo?");
			if (boton)
			{
			  	document.getElementById('accion').value = "aplicaPrecioEspecial";
    			document.ventaDirectaForm.submit();
			}
		}
	 	
	 	if (est == "fin") {
	 		estado = est;
	 		alert("Venta almacenada con éxito");
	 		var cmb_tipo_albaran = document.getElementById('tipo_albaran');
	 		var cmb_cajero = document.getElementById('cajero');
	 		var cmb_agente = document.getElementById('agente');
	 		
	 		cmb_agente.disabled= true;
	 		cmb_cajero.disabled= true;
	 		cmb_tipo_albaran.disabled= true;
	 		
	 		parent.carga_url_padre('<%=request.getContextPath()%>/Menu.do?method=CargaMenu');
		}
		if (est== "guardado")
		{
			alert("Venta almacenada con éxito");
		}
		if (est== "ERROR_GUARDADO")
		{
			alert("La venta no se pudo guardar. intentelo nuevamente");
		}
		if (est== "ERROR_VALIDACION_MULTIOFERTA")
		{
			alert("la multioferta no se guardo correctamente");
		}
		if (est == "VALIDACION_MULTIOFERTA_OK")
		{
			genera_venta();
		}
		
		if (est=='producto_graduable') {
			alert("Los Lentes de Contacto con Graduación no se pueden registrar por Venta Directa");
		}
		if (est=='carga_multioferta') 
		{	
			var codigo = document.getElementById('codigo_mult').value;
			var index = document.getElementById('index_multi').value;
	 		showPopWin("<%=request.getContextPath()%>/BusquedaProductosMultiOfertas.do?method=cargaBusquedaProductosMultiOfertas&formulario=DIRECTA&codigoMultioferta="+codigo+"&index_multi="+index+"", 714, 425, null, false);
	 	}
		*/
		
		
		
		
		
		return formulario;
	}
	

}
