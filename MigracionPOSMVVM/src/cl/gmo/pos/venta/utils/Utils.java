/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.gmo.pos.venta.utils;


import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import cl.gmo.pos.venta.web.beans.AgenteBean;
import cl.gmo.pos.venta.web.beans.AlbaranBean;
import cl.gmo.pos.venta.web.beans.BoletaBean;
import cl.gmo.pos.venta.web.beans.ClienteBean;
import cl.gmo.pos.venta.web.beans.ConvenioBean;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.GiroBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.OftalmologoBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.PrecioEspecialBean;
import cl.gmo.pos.venta.web.beans.PrismaBaseBean;
import cl.gmo.pos.venta.web.beans.PrismaCantidadBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.ProvinciaBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.TiendaBean;
import cl.gmo.pos.venta.web.beans.TipoMotivoDevolucionBean;
import cl.gmo.pos.venta.web.beans.VentaPedidoBean;
import cl.gmo.pos.venta.web.facade.BusquedaLiberacionesFacade;
import cl.gmo.pos.venta.web.facade.PosBusquedaMedicosFacade;
import cl.gmo.pos.venta.web.facade.PosClientesFacade;
import cl.gmo.pos.venta.web.facade.PosGraduacionesFacade;
import cl.gmo.pos.venta.web.facade.PosProductosFacade;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.facade.PosVentaPedidoFacade;
import cl.gmo.pos.venta.web.forms.ClienteForm;
import cl.gmo.pos.venta.web.forms.DevolucionForm;
import cl.gmo.pos.venta.web.forms.EntregaPedidoForm;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;
import cl.gmo.pos.venta.web.forms.VentaDirectaForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;
import cl.gmo.pos.venta.web.wscl.CoreSoapProxy;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Advise64
 */
public class Utils {

	Logger log = Logger.getLogger(this.getClass());
	private int flag;
	public int numero;
	public String importe_parcial;
	public String num;
	public String num_letra;
	public String num_letras;
	public String num_letram;
	public String num_letradm;
	public String num_letracm;
	public String num_letramm;
	public String num_letradmm;
	
	public String buscaFormaPago(ArrayList<FormaPagoBean> ListaFormaPago,PagoBean pagoIndice ){
		log.info("ReportesHelper:buscaFormaPago inicio");
		String medioPago=Constantes.STRING_REPORTER_MEDIO_PAGO_INDEF;
		for(FormaPagoBean forma:ListaFormaPago){
			log.info("ReportesHelper:buscaFormaPago entrando ciclo for");
			if(forma.getId().equals(pagoIndice.getForma_pago())){
				medioPago= forma.getDescripcion();
				break;
			}
		}
		return medioPago;
	}
	
	public boolean validaCristalADD(ProductosBean prod,
			GraduacionesBean graduacion) {
		
		//System.out.println("FAMILIA ==> "+prod.getFamilia());
		if ((prod.getFamilia().substring(0, 2).equals(Constantes.STRING_ADD_FAMILIA_85)
				|| prod.getFamilia().substring(0, 2).equals(Constantes.STRING_ADD_FAMILIA_86)
				|| prod.getFamilia().substring(0, 2).equals(Constantes.STRING_ADD_FAMILIA_87)
				|| prod.getFamilia().substring(0, 2).equals(Constantes.STRING_ADD_FAMILIA_88)
				|| prod.getFamilia().substring(0, 2).equals(Constantes.STRING_ADD_FAMILIA_89))
				&& !prod.getFamilia().equals(Constantes.STRING_FAMILIA_899))
		{
			return true;			
		}
		else
		{
			return false;
		}
		
		
		
	}
	
	public boolean validaCristalRecetaADD(ProductosBean prod,
			GraduacionesBean graduacion) {
		
		if (prod.getOjo().equals(Constantes.STRING_IZQUIERDO)) {
			if (null != graduacion.getOI_adicion() && graduacion.getOI_adicion().doubleValue()>0) {
				System.out.println("validaCristalRecetaADD IZQ =>"+graduacion.getOI_adicion().doubleValue());
				return true;
				
			}
			else
			{
				System.out.println("IZQUIERDO FALSE");
				return false;
				
			}
		}
		else if(prod.getOjo().equals(Constantes.STRING_DERECHO)) {
			if (null != graduacion.getOD_adicion() && graduacion.getOD_adicion().doubleValue()>0) {
				System.out.println("validaCristalRecetaADD DER=>"+graduacion.getOD_adicion().doubleValue());
				return true;
			}
			else
			{
				System.out.println("DERECHO FALSE");
				return false;
			}
		}
		else
		{
			System.out.println("POR DEFECTO FALSE");
			return false;
			
		}
		
		
		
	}
	

	public String traeNombreAgente(String user, ArrayList<AgenteBean> lista_agentes) {
		String nombre = Constantes.STRING_BLANCO;
		for (AgenteBean agente : lista_agentes) {
			if (agente.getUsuario().equals(user)) {
				nombre = agente.getNombre_completo();
			}
		}
		return nombre;
	}
	
	public boolean isNumeric(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public ArrayList<SuplementopedidoBean> traeSuplementosPedidoLiberacion(
			int identificadorLineaPedido) {
		ArrayList<SuplementopedidoBean> listaSuple = new ArrayList<SuplementopedidoBean>();
		try {

			listaSuple = PosVentaPedidoFacade.traeTratamientosPedido(identificadorLineaPedido);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaSuple;
	}

	public String formatoValoresReportes(int numero) {
		NumberFormat formato = new DecimalFormat("'$ '###,###,###,###");
		String numero2 = formato.format(numero);
		return numero2;

	}

	public String foramtoValoresRerpoteFichaTecnicaDosDecimales(String numero) {

		String numeroFormato = "";
		try {
			if (null != numero && ("" != numero)) {
				double numdoub = Double.parseDouble(numero);
				NumberFormat df = DecimalFormat.getInstance();
				df.setMinimumFractionDigits(2);
				df.setMaximumFractionDigits(4);
				df.setRoundingMode(RoundingMode.DOWN);
				numeroFormato = df.format(numdoub);
			}

		} catch (Exception ex) {
			numeroFormato = numero;
		}

		return numeroFormato;
	}

	public int calcula_edad(String fecha_nac) {
		int anos = Constantes.INT_CERO;
		try {
			log.info("Utils:calcula_edad inicio");

			Date fechaActual = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String fecha_Nacimiento = this.formatoFechaString(fecha_nac);
			String hoy = formato.format(fechaActual);
			String[] dat1 = fecha_Nacimiento.split("/");
			String[] dat2 = hoy.split("/");
			anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
			int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
			if (mes < 0) {
				anos = anos - 1;
			} else if (mes == 0) {
				int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
				if (dia > 0) {
					anos = anos - 1;
				}
			}
		} catch (Exception ex) {
			log.error("Utils:calcula_edad error catch", ex);
		}
		return anos;
	}

	public Properties leeConfiguracion() {

		try {
			log.info("Utils:leeConfiguracion inicio");

			Properties config = new Properties();

			config.load(getClass().getResourceAsStream("/config.properties"));

			if (!config.isEmpty()) {
				return config;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("Utils:leeConfiguracion error catch", ex);
			return null;
		}

	}

	public String isEntero(double x) {
		String respuesta = "";
		try {

			int x_entero = (int) x;

			double res = x - x_entero;

			if (res == 0 || res == 0.0) {
				DecimalFormat df = new DecimalFormat("0");
				respuesta = df.format(x);
			} else {
				DecimalFormat df = new DecimalFormat("0.00");
				respuesta = df.format(x);
			}
		} catch (Exception ex) {
			respuesta = "";
		}
		return respuesta.replace(",", ".");
	}

	public ArrayList<TipoMotivoDevolucionBean> traeMotivoDevolucion() {
		ArrayList<TipoMotivoDevolucionBean> lista_mot = new ArrayList<TipoMotivoDevolucionBean>();
		try {
			lista_mot = PosUtilesFacade.traeMotivoDevolucion();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista_mot;
	}

	public int numeroAleatorio() {
		int numeroAleatorio = 0;
		try {
			numeroAleatorio = Math.abs((int) (Math.random() * 1000000 + 1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return numeroAleatorio;
	}

	public String formato_Numero_Ticket(int numero) {
		DecimalFormat format = new DecimalFormat(
				Constantes.STRING_FORMAT_DECIMAL);
		return format.format(numero);
	}

	public Date traeFecha() {
		Calendar calendario = GregorianCalendar.getInstance();
		//RESTO UNA HORA 20180513
		calendario.add(Calendar.HOUR_OF_DAY,-1);
		
		Date fecha = calendario.getTime();

		return fecha;
	}

	/**
	 * Retorna un String con la fecha actual en formato dd/mm/yyyy
	 * 
	 * @return
	 */
	public String traeFechaHoyFormateadaString() {
		Date fechaHoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat(
				Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
		return formato.format(fechaHoy);
	}

	/**
	 * Retorna un String con la fecha actual en formato dd-mm-yyyy
	 * 
	 * @return
	 */
	public String traeFechaHoyFormatoString() {
		Date fechaHoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat(
				Constantes.STRING_FORMAT_SIMPLE_DATE_STRING_FORMAT);
		return formato.format(fechaHoy);
	}

	public String formatoFecha(Date fecha) {
		String fechaF = Constantes.STRING_BLANCO;
		try {

			if (null != fecha && !("".equals(fecha))) {
				SimpleDateFormat formatoFecha = new SimpleDateFormat(
						Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
				fechaF = formatoFecha.format(fecha);
				System.out.println("Fecha formateada ==> "+fechaF);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fechaF;
	}

	public String formatoHora(Date fecha) {
		String horaF = Constantes.STRING_BLANCO;
		SimpleDateFormat formatoHora = new SimpleDateFormat(
				Constantes.STRING_FORMAT_HORA);
		horaF = formatoHora.format(fecha);
		return horaF;
	}

	public String formatoFechaEspecial(String fecha) {
		if (null == fecha || "".equals(fecha)) {
			return "";
		} else {
			String anho = fecha.substring(0, 4);
			String mes = fecha.substring(5, 7);
			String dia = fecha.substring(8, 10);
			return dia + "-" + mes + "-" + anho;
		}

	}

	public String formatoFechaString(String fecha) {
		if (null == fecha || "".equals(fecha)) {
			return "";
		} else {
			String anho = fecha.substring(0, 4);
			String mes = fecha.substring(5, 7);
			String dia = fecha.substring(8, 10);
			return dia + "/" + mes + "/" + anho;
		}

	}

	public ArrayList<ProductosBean> eliminarProducto(
			String codigo_multi_eliminar, int index_multioferta_eliminar,
			ArrayList<ProductosBean> lista_productos) {
		ProductosBean productoEliminado = new ProductosBean();
		if (null != lista_productos) {
			for (ProductosBean productoBean : lista_productos) {
				if (productoBean.getCodigo().equals(codigo_multi_eliminar)
						&& productoBean.getIndexMulti() == index_multioferta_eliminar) {
					productoEliminado = productoBean;
					lista_productos.remove(productoEliminado);
					break;
				}
			}

		}
		return lista_productos;
	}

	public ArrayList<ProductosBean> eliminarProducto(String producto,
			ArrayList<ProductosBean> listaProductos) {
		ProductosBean productoEliminado = new ProductosBean();
		if (null != listaProductos) {
			for (ProductosBean productoBean : listaProductos) {
				if (productoBean.getCodigo().equals(producto)) {
					productoEliminado = productoBean;
				}
			}

			listaProductos.remove(productoEliminado);
		}
		return listaProductos;
	}

	/**
	 * Elimina el articulo que se encuentra dentro de una multioferta.
	 * 
	 * @param index_producto_multi
	 * @param indexMulti
	 * @param producto
	 * @param listaProductos
	 * @return ArrayList<ProductosBean>
	 */
	public ArrayList<ProductosBean> eliminarProductoMultioferta(
			int index_producto_multi, int indexMulti, String producto,
			ArrayList<ProductosBean> listaProductos) {
		ProductosBean productoEliminado = null;

		if (null != listaProductos) {
			for (ProductosBean productoBean : listaProductos) {
				if (productoBean.getCodigo().equals(producto)
						&& productoBean.getIndexRelMulti() == indexMulti
						&& index_producto_multi == productoBean
								.getIndexProductoMulti()) {
					productoEliminado = productoBean;
					listaProductos.remove(productoEliminado);
					break;
				}

			}

		}
		return listaProductos;
	}

	public ArrayList<ProductosBean> eliminarProductoMultioferta(
			String codigo_multi_eliminar, int index_multioferta_eliminar,
			ArrayList<ProductosBean> lista_productos_multiofertas) {
		ProductosBean productoEliminado = null;
		ArrayList lista = new ArrayList();
		if (null != lista_productos_multiofertas) {
			for (int i = 0; i < lista_productos_multiofertas.size(); i++) {
				ProductosBean productoBean = lista_productos_multiofertas
						.get(i);
				if (productoBean.getCodigoMultioferta().equals(
						codigo_multi_eliminar)
						&& productoBean.getIndexRelMulti() == index_multioferta_eliminar) {
					productoEliminado = productoBean;
					lista.add(productoEliminado);
				}
			}

			lista_productos_multiofertas.removeAll(lista);
		}
		return lista_productos_multiofertas;
	}

	public ArrayList<ProductosBean> eliminarMultioferta(
			String codigo_multi_eliminar, int index_multioferta_eliminar,
			ArrayList<ProductosBean> lista_multiofertas) {
		ProductosBean productoEliminado = null;

		if (null != lista_multiofertas) {
			for (ProductosBean multioferta : lista_multiofertas) {
				if (multioferta.getCodigo().equals(codigo_multi_eliminar)
						&& multioferta.getIndexMulti() == index_multioferta_eliminar) {
					productoEliminado = multioferta;
					lista_multiofertas.remove(productoEliminado);
					break;
				}

			}

		}
		return lista_multiofertas;
	}

	/*
	 * public ArrayList<ProductosBean> eliminarProductoMultiofertas(String
	 * producto, ArrayList<ProductosBean> listaProductos) { ProductosBean
	 * productoEliminado = new ProductosBean(); if(null != listaProductos){ for
	 * (ProductosBean productoBean : listaProductos) {
	 * if(productoBean.getCodigoMultioferta().equals(producto)) {
	 * productoEliminado = productoBean; } }
	 * 
	 * listaProductos.remove(productoEliminado); } return listaProductos; }
	 */
	public ProductosBean seleccionarProductoLista(String producto,
			ArrayList<ProductosBean> listaProductos) {
		ProductosBean productoSelecionado = new ProductosBean();
		for (ProductosBean productoBean : listaProductos) {
			if (productoBean.getCodigo().equals(producto)) {
				productoSelecionado = productoBean;
			}
		}
		return productoSelecionado;

	}

	public ArrayList<AgenteBean> traeAgentes(String local) throws Exception {
		ArrayList<AgenteBean> listaAgentes = new ArrayList<AgenteBean>();
		listaAgentes = PosUtilesFacade.traeAgentes(local);
		return listaAgentes;
	}

	public String traeHoraFormatoxString() {

		GregorianCalendar hoy = new GregorianCalendar();

		int hora = hoy.get(Calendar.HOUR_OF_DAY);
		int minuto = hoy.get(Calendar.MINUTE);
		int segundo = hoy.get(Calendar.SECOND);
		String horaDia = Integer.toString(hora) + "" + Integer.toString(minuto)
				+ "" + Integer.toString(segundo);
		return horaDia;
	}

	public String formato_hora_segundos(int numero) {
		DecimalFormat format = new DecimalFormat("00");
		return format.format(numero);
	}

	/**
	 * Retorna un String con la hora actual en formato hh:mm:ss
	 * 
	 * @return
	 */
	public String traeHoraString() {

		GregorianCalendar hoy = new GregorianCalendar();

		int hora = hoy.get(Calendar.HOUR_OF_DAY);
		int minuto = hoy.get(Calendar.MINUTE);
		int segundo = hoy.get(Calendar.SECOND);

		String horaDia = formato_hora_segundos(hora) + ":"
				+ formato_hora_segundos(minuto) + ":"
				+ formato_hora_segundos(segundo);
		return horaDia;
	}

	public String getNumber(String number) {
		if (null != number) {
			if (number.indexOf("$") > 0 || number.indexOf(".") > 0) {
				return number;
			}
			if ("".equals(number) || null == number) {
				return "";
			} else {

				double value;
				String numberFormat = Constantes.STRING_FORMAT_NUMBER;
				DecimalFormat formatter = new DecimalFormat(numberFormat);
				try {
					value = Double.parseDouble(number);
				} catch (Throwable t) {
					return null;
				}
				return formatter.format(value);
			}
		} else {
			return "";
		}

	}

	public String fechaEntregaLiberacion(String fechaString) {

		try {

			Date fecha = formatoFechaCh(fechaString);

			Calendar cal = new GregorianCalendar();
			cal.setTime(fecha);			
			cal.add(cal.DATE, -1);
			
			fechaString = formatoFecha(cal.getTime());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fechaString;
	}

	public Date convierteFechaStrina(String fechaString) {
		Date fecha = null;
		try {
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
					"yyyy-MM-dd");
			fecha = formatoDelTexto.parse(fechaString);

		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return fecha;
	}

	public Date formatoFechaCh(String fechaString) {
		Date fecha = null;
		try {
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
					"dd/MM/yyyy");
			fecha = formatoDelTexto.parse(fechaString);

		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return fecha;
	}

	public String traeCodigoLocalSap(String local) throws Exception {

		return PosUtilesFacade.traeCodigoLocalSap(local);
	}

	public String traeCodigoAgente(String agente) throws Exception {

		return PosUtilesFacade.traeCodigoAgente(agente);
	}

	public ClienteBean traeCliente(String nif, String codigo) {

		return PosClientesFacade.traeCliente(nif, codigo);
	}

	public ProductosBean traeProducto(String producto, String local) {

		return PosProductosFacade.traeProducto(producto, 0, local, "PEDIDO",
				null);
	}
	
	public ProductosBean traeProductoEsp(String producto,String tienda,String encargo,String grupo,String cdg) {

		return PosProductosFacade.traeProductoEsp(producto,tienda,encargo,grupo,cdg);
	}

	public ProductosBean traeInfoproducto(String producto) {

		return PosProductosFacade.traeInfoproducto(producto);
	}

	public ArrayList<VentaPedidoBean> traeDetalleLiberacionMulti(String codigo,
			String grupo) {

		return BusquedaLiberacionesFacade.traeDetalleLiberacionMulti(codigo,
				grupo);
	}

	public GraduacionesBean traeGraduacionPedido(String cliente, String fecha,
			double numero) {

		return BusquedaLiberacionesFacade.traeGraduacionPedido(cliente, fecha,
				numero);
	}

	public ArrayList<OftalmologoBean> traeDoctor() throws Exception {
		return PosUtilesFacade.traeDoctor();

	}

	public ArrayList<ProvinciaBean> traeProvincias() {
		ArrayList<ProvinciaBean> lista_provincias = new ArrayList<ProvinciaBean>();
		try {
			lista_provincias = PosClientesFacade.traeProvincias();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista_provincias;

	}
	
	public ArrayList<ProvinciaBean> traeProvinciasdev() {
		ArrayList<ProvinciaBean> lista_provincias = new ArrayList<ProvinciaBean>();
		try {
			lista_provincias = PosClientesFacade.traeProvinciasdev();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista_provincias;

	}

	
	public String traeTipoLente(String abreviatura) {

		String tipo = "";
		if (null == abreviatura || "".equals(abreviatura)) {
			tipo = "";
		} else {
			if ("C".equals(abreviatura.toUpperCase())) {
				tipo = "Cerca";
			} else if ("L".equals(abreviatura.toUpperCase())) {
				tipo = "Lejos";
			} else if ("B".equals(abreviatura.toUpperCase())) {
				tipo = "Bifocal";
			} else if ("P".equals(abreviatura.toUpperCase())) {
				tipo = "Progresiva";
			} else {
				tipo = "";
			}

		}

		return tipo;
	}

	public String isCeroString(String var) {

		if (var.equals("0")) {
			var = "";
		}
		return var;
	}

	public int traeDescuentoConvenio(ProductosBean productosBean,
			String convenio, String fpago,String local) {
		int valor = 0;

		try {
			valor = PosUtilesFacade.traeDescuentoConvenio(productosBean,
					convenio, fpago,local);
		} catch (Exception e) {
			e.printStackTrace();
			valor = 0;
		}

		return valor;

	}

	public boolean verificaEstadoConvenioCliente(String convenio, String cliente) {
		boolean estado = false;
		try {
			estado = PosUtilesFacade.validaEstadoConvenioCliente(convenio,
					cliente);
		} catch (Exception e) {
			e.printStackTrace();
			estado = false;
		}
		return estado;
	}
	
	public int traeMontoMinDescuento(ProductosBean prod ,String convenio,String fpago,int dto) {
		int valor = 0;

		try {
			valor = PosUtilesFacade.traeMontoMinDescuento(prod,convenio,fpago,dto);
		} catch (Exception e) {
			e.printStackTrace();
			valor = 0;
		}

		return valor;

	}

	/*
	 * LMARIN 20141015
	 * SE CAMBIA LA FORMA DE APLICAR LOS DESCUENTOS, AHORA SE REALIZAN POR LINEA
	 * OBVIA EL DTO POS JAVA Y LO HACE A TRAVES DE JAVASCRIPT 
	 */
	
	public void aplicaDescuentoConvenio(ProductosBean prod, String convenio,
			String formaPago,ArrayList<ProductosBean> listaProd,String local) {
		
		int valor = 0;
		int total_prod = prod.getPrecio() * prod.getCantidad();
		int total = 0;
		double dtonew = 0;
		int ttemp =0 ;
		int ttemp1 =0 ;
		int cont_soc = 0;
		int cont_lc = 0;
		int precio_mon = 0;
		int precio_gaf1 = 0;
		int precio_gaf2 = 0;
		int precio_gaf = 0;
		int grupo_mon= 0;
		int grupo_gaf1 = 0;
		int grupo_gaf2 = 0;
		
	
		int grupo_val_min = 0;
	
		//DESCUENTOS POR CONVENIO
		if(!convenio.equals("")){
			if(convenio.equals("50436") || convenio.equals("50437") || convenio.equals("50438")
			   || convenio.equals("50439") || convenio.equals("50440") || convenio.equals("50441") || convenio.equals("50487") 
			 ){
					prod.setDescuento(0);
					prod.setPrecio(prod.getPrecio());
					prod.setDescuento(valor);
					total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
			}else{
					//CALCULO EL TOTAL
					for(ProductosBean p : listaProd){
						if(!p.getSubFamilia().equals("CHL")){
							ttemp += ((int)p.getPrecio() * (int)p.getCantidad());
						}
					}
					
					valor = this.traeDescuentoConvenio(prod, convenio, formaPago,local);
					
					//TRAE MONTO MINIMO NECESITADO PARA REALIZAR EL DESCUENTO
					int min = this.traeMontoMinDescuento(prod,convenio,formaPago,valor);	
					
					System.out.println("Monto Min ===> "+min);
					
					if(min != 0){
						  if( String.valueOf(min).length() >= 3){
							  
							    System.out.println("Paso Por ACA 1"+ String.valueOf(min).length()+" <==> "+ min);
							    
								//Entro a promocion con tope de dto en dinero
								if(ttemp >= min){
									if(String.valueOf(valor).length() > 3){
										if(convenio.equals("51001") || convenio.equals("51002")){
											System.out.println("paso por 51001 (1) ===>"+ttemp);
											
											dtonew = 100 - ((double)(valor*100)/ttemp);
											
											prod.setDescuento(dtonew);
											total = (int) ((prod.getPrecio() * prod.getCantidad()) * (dtonew/100));
											System.out.println("valor ===> " +total);
										}else{
											System.out.println("no paso por 51001 (1)");
											dtonew = ((double)(valor*100)/(double)ttemp);
											total  =  total_prod - (int)Math.round(((total_prod * (dtonew)) / 100));
											prod.setDescuento(dtonew);
										}
										
									}else{
										
										prod.setDescuento(valor);
										total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
									}
								}
									
							}else if(String.valueOf(min).length() <= 2){
								
								 System.out.println("Paso Por ACA 2"+ String.valueOf(min).length()+" <==> "+ min);
								//Entro a promocio1n LEC + SOC
								
								
								for(ProductosBean p : listaProd){
									//Calculo si hay algiun SOC
									if(p.getFamilia().equals("SOC")){
										cont_soc++;
									}
									//Calculo si hay algï¿½n dto por linea
									if(prod.getCantidad() >= this.traeMontoMinDescuento(prod,convenio,formaPago,valor)){
										cont_lc++;
									}
								}
								
								//CALCULO EL TOTAL
								for(ProductosBean p : listaProd){
									if(!p.getSubFamilia().equals("CHL") && !p.getFamilia().equals("SOC")){
										if(p.getCantidad() >= this.traeMontoMinDescuento(prod,convenio,formaPago,valor)){
											ttemp1 += ((int)p.getPrecio() * (int)p.getCantidad());
										}
									}
								}
								if(cont_soc >= 1 && cont_lc >=1){
									dtonew = ((double)(valor*100)/(double)ttemp1);
									total  =  total_prod - (int)Math.round(((total_prod * (dtonew)) / 100));
									prod.setDescuento(dtonew);
								}else{
									prod.setPrecio(prod.getPrecio());
									prod.setDescuento(0);
									total = total_prod;
								}
								
							}else{
								 System.out.println("Paso Por ACA 3"+ String.valueOf(min).length()+" <==> "+ min);
								// CALCULA TOTAL DE DESCUENTO PROMOCIONES A PRECIO LLENO 20140328			
								if(String.valueOf(valor).length() > 3){
									
									if(convenio.equals("51001") || convenio.equals("51002")){
										System.out.println("paso por 51001 (2)");
										dtonew = 100 - ((double)(valor*100)/total_prod);
										total  =  valor;
										prod.setDescuento(dtonew);
									}else{
										System.out.println("no paso por 51001 (2)");
										dtonew = ((double)(valor*100)/(double)ttemp);
										total  =  total_prod - (int)Math.round(((total_prod * (dtonew)) / 100));
										prod.setDescuento(dtonew);
									}
									
								}else{
									prod.setDescuento(valor);
									total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
								}
							}	
					}else{
						prod.setDescuento(valor);
						total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
					}
			}
		}else{
			prod.setPrecio(prod.getPrecio());
			prod.setDescuento(valor);
			total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
		}
		
		
		prod.setImporte(total);
	}
	
	
	public ConvenioBean traeConvenio(String conveniocb) {
		ArrayList<ConvenioBean> lista = PosUtilesFacade.traeConvenios(
				conveniocb, null, null);
		return lista.get(0);
	}

	public boolean valida_grupos(ArrayList<ProductosBean> lista_productos,
			int grupo_max, VentaPedidoForm formulario) {
		
		
		boolean estado = true;
		
		try{
			
	
		// recorre por numero de grupo
		if (grupo_max != 0) {
			for (int i = 1; i <= grupo_max; i++) {

				int mon = 0;
				ProductosBean montura = null;
				int cri_d = 0;
				ProductosBean cristal_derecho = null;
				int cri_i = 0;
				ProductosBean cristal_izquierdo = null;
				// int cristalcli = 0;

				// recorre productos
				for (ProductosBean prod : lista_productos) {

					// consulta si el producto pertenece al grupo
					if (Integer.parseInt(prod.getGrupo()) == i) {

						// valida si es montura
						if (prod.getTipoFamilia().equals("M")) {
							mon++;
							montura = prod;
						}
						if (prod.getTipoFamilia().equals("G")) {
							mon++;
							montura = prod;
						}
						if (prod.getTipoFamilia().equals("C")
								&& prod.getOjo().equals("derecho")) {
							cri_d++;
							cristal_derecho = prod;
						}
						if (prod.getTipoFamilia().equals("C")
								&& prod.getOjo().equals("izquierdo")) {
							cri_i++;
							cristal_izquierdo = prod;
						}
						// if (prod.getFamilia().equals("899")) {
						// cristalcli += 1;
						// }
					}
				}
				boolean validaCrisMon = true;
				// comprueba si el grupo esta ok
				// if (cristalcli > 0) {
				// if (cristalcli != 2)
				// {
				// formulario.setError("GRUPO " + i
				// +": El trï¿½o esta mal armado, verifique los cristales cliente");
				// return false;
				// }
				// }

				if (mon == 1 && cri_d == 1 && cri_i == 1) {

					if (montura.getCod_barra().equals("ARCLI")) // si es montura
																// cliente no
																// entra al
																// validacrismon
					{
						validaCrisMon = false;
					}
					if (montura.getTipoFamilia().equals("G")) // si es gafa de
																// sol, debe
																// validar si es
																// graduable
					{
						validaCrisMon = false;
						try {
							if (!PosUtilesFacade.validaGafaGraduable(montura
									.getCodigo())) { // verifica si gafa es
														// graduable
								formulario
										.setError("GRUPO "
												+ i
												+ ": El lente de sol no es graduable | validacion gafa graduable |");
								return false;
							}
						} catch (Exception e) {
							formulario
									.setError("GRUPO "
											+ i
											+ ":El lente de sol no es graduable | validacion gafa graduable |");
							return false;
						}
					}
					// VALIDACION DE ESFERA MAS CILINDRO derecho 
					if (!cristal_derecho.getCod_barra().equals("CCLI")) {
						
						//valida cristal derecho esfera mas cilindro mas adicion
						if (cristal_derecho.getFamilia().substring(0, 2).toString().equals(Constantes.STRING_ADD_FAMILIA_85)) {
							
							double valor = PosUtilesFacade.validaEsferaMasCilindroMasAdd(
									cristal_derecho.getFamilia(),
									cristal_derecho.getSubFamilia(),
									cristal_derecho.getGrupoFamilia(),
									cristal_derecho.getEsfera(),
									cristal_derecho.getCilindro(),
									formulario.getGraduacion().getOD_adicion().doubleValue());
							
							if (valor > 0) {
								
								formulario
								.setError("GRUPO "
										+ i
										+ ":Cristal derecho | Error al validar la Esfera + Cilindro + ADD, no puede ser superior a " + valor);
								return false;
							}
							
						}
						else
						{
							if (!PosUtilesFacade.validaEsferaMasCilindro(
									cristal_derecho.getFamilia(),
									cristal_derecho.getSubFamilia(),
									cristal_derecho.getGrupoFamilia(),
									cristal_derecho.getEsfera(),
									cristal_derecho.getCilindro())) {
								formulario
										.setError("GRUPO "
												+ i
												+ ":Producto no disponible en proveedor para esta Receta | validacion esfera mas cilindro |");
								return false;
							}
						}
					}
					
					// VALIDACION DE ESFERA MAS CILINDRO izquierdo
					if (!cristal_izquierdo.getCod_barra().equals("CCLI")) {
						
						//valida cristal derecho esfera mas cilindro mas adicion
						if (cristal_izquierdo.getFamilia().substring(0, 2).toString().equals(Constantes.STRING_ADD_FAMILIA_85)) {
							
							double valor = PosUtilesFacade.validaEsferaMasCilindroMasAdd(
									cristal_izquierdo.getFamilia(),
									cristal_izquierdo.getSubFamilia(),
									cristal_izquierdo.getGrupoFamilia(),
									cristal_izquierdo.getEsfera(),
									cristal_izquierdo.getCilindro(),
									formulario.getGraduacion().getOI_adicion().doubleValue());
							
							if (valor>0) {
								
								formulario
								.setError("GRUPO "
										+ i
										+ ":Cristal Izquierdo | Error al validar la Esfera + Cilindro + ADD, no puede ser superior a " + valor);
								return false;
							}
							
						}
						else
						{
							if (!PosUtilesFacade.validaEsferaMasCilindro(
									cristal_izquierdo.getFamilia(),
									cristal_izquierdo.getSubFamilia(),
									cristal_izquierdo.getGrupoFamilia(),
									cristal_izquierdo.getEsfera(),
									cristal_izquierdo.getCilindro())) {
								formulario
										.setError("GRUPO "
												+ i
												+ ":Producto no disponible en proveedor para esta Receta | validacion esfera mas cilindro |");
								return false;
							}
						}
						
					}
					// VALIDA AMBOS CRISTALES SON COMPATIBLES ENTRE SI
					if (!(cristal_derecho.getCod_barra().equals("CCLI") || cristal_izquierdo
							.getCod_barra().equals("CCLI"))) {
						// VALIDA QUE AMBOS CRISTALES SEAN DE LEJOS O CERCA
						if (!cristal_derecho.getTipo().equals(
								cristal_izquierdo.getTipo())) {
							formulario
									.setError("GRUPO "
											+ i
											+ ":Los ojos son de diferente tipo: Lejos o Cerca");
							return false;
						} else {
							// VALIDA EL SUPLEMENTO TEï¿½IDO EN AMBOS OJOS
							boolean derecho_con_suple = false;
							boolean izquierdo_con_suple = false;
							String derecho_valor = "";
							String izquierdo_valor = "";
							if (null != cristal_derecho.getListaSuplementos()) {
								for (SuplementopedidoBean suple : cristal_derecho
										.getListaSuplementos()) {
									if (suple.getTratami().equals("TE")) {
										derecho_con_suple = true;
										derecho_valor = suple.getValor();
									}
								}
							}
							if (null != cristal_izquierdo.getListaSuplementos()) {
								for (SuplementopedidoBean suple : cristal_izquierdo
										.getListaSuplementos()) {
									if (suple.getTratami().equals("TE")) {
										izquierdo_con_suple = true;
										izquierdo_valor = suple.getValor();
									}
								}
							}

							if (izquierdo_con_suple && derecho_con_suple) {
								if (!derecho_valor.equals(izquierdo_valor)) {
									formulario
											.setError("GRUPO "
													+ i
													+ ":se ingresï¿½ diferentes valores en Suplemento Teï¿½ido");
									return false;
								}
							} else if (izquierdo_con_suple) {
								formulario
										.setError("GRUPO "
												+ i
												+ ":se ingresï¿½ diferentes valores en Suplemento Teï¿½ido");
								return false;
							} else if (derecho_con_suple) {
								formulario
										.setError("GRUPO "
												+ i
												+ ":se ingresï¿½ diferentes valores en Suplemento Teï¿½ido");
								return false;
							}
						}
						// VALIDA QUE AMBOS CRISTALES SEAN COMPATIBLES ENTRE SI
						boolean agrupacion_excepcion = false;
						if (cristal_derecho.getFamilia().equals("812")
								&& cristal_derecho.getSubFamilia()
										.equals("ARS")
								&& cristal_derecho.getGrupoFamilia().equals(
										"002")) {
							if (cristal_izquierdo.getFamilia().equals("812")
									&& cristal_izquierdo.getSubFamilia()
											.equals("ARG")
									&& cristal_izquierdo.getGrupoFamilia()
											.equals("039")) {
								agrupacion_excepcion = true;
							}
						}
						if (cristal_derecho.getFamilia().equals("812")
								&& cristal_derecho.getSubFamilia()
										.equals("ARG")
								&& cristal_derecho.getGrupoFamilia().equals(
										"039")) {
							if (cristal_izquierdo.getFamilia().equals("812")
									&& cristal_izquierdo.getSubFamilia()
											.equals("ARS")
									&& cristal_izquierdo.getGrupoFamilia()
											.equals("002")) {
								agrupacion_excepcion = true;
							}
						}
						if (!agrupacion_excepcion) {
							if (!(cristal_derecho.getFamilia().equals(
									cristal_izquierdo.getFamilia())
									&& cristal_derecho.getSubFamilia().equals(
											cristal_izquierdo.getSubFamilia()) && cristal_derecho
									.getGrupoFamilia()
									.equals(cristal_izquierdo.getGrupoFamilia()))) {

								formulario
										.setError("GRUPO "
												+ i
												+ ": Existen cristales de diferente tipo");
								return false;
							}
						}

					}

					// VALIDA CONTRA VALIDACRISMON (CRISTALES + MONTURA +
					// DIOPTRIA) Y (CRISTALES + MONTURA + SUPLEMENTO)
					if (validaCrisMon) {
						try {
							// VALIDACRISMON
							if (!cristal_izquierdo.getCod_barra()
									.equals("CCLI")) {
								
								if (!PosUtilesFacade.validaCrisMon(
										cristal_izquierdo.getFamilia(),
										cristal_izquierdo.getSubFamilia(),
										cristal_izquierdo.getGrupoFamilia(),
										montura.getCodigo(),
										cristal_izquierdo.getEsfera(),
										cristal_izquierdo.getCilindro())) // ojo
																			// izquierdo
								{
									formulario
											.setError("GRUPO "
													+ i
													+ ": La montura no es compatible con los cristales");
									return false; // validacion ojo izquierdo
													// fallido
								}
								
								//valida PROHIBCRISARM
								if (PosUtilesFacade.validaProhibCrisArm(
										cristal_izquierdo.getFamilia(),
										cristal_izquierdo.getSubFamilia(),
										cristal_izquierdo.getGrupoFamilia(),
										montura.getCodigo())) {
									
									System.out.println("Paos por esta validacion (1)");
									
									formulario
											.setError("GRUPO "
													+ i
													+ ": Tipo de Armazon no es comptible con Cristal");
									return false;
								}
							}
							if (!cristal_derecho.getCod_barra().equals("CCLI")) {
								if (!PosUtilesFacade.validaCrisMon(
										cristal_derecho.getFamilia(),
										cristal_derecho.getSubFamilia(),
										cristal_derecho.getGrupoFamilia(),
										montura.getCodigo(),
										cristal_derecho.getEsfera(),
										cristal_derecho.getCilindro())) // ojo
																		// derecho
								{
									formulario
											.setError("GRUPO "
													+ i
													+ ": La montura no es compatible con los cristales");
									return false;
								}
								
								//valida PROHIBCRISARM
								if (PosUtilesFacade.validaProhibCrisArm(
										cristal_derecho.getFamilia(),
										cristal_derecho.getSubFamilia(),
										cristal_derecho.getGrupoFamilia(),
										montura.getCodigo())) {
									
									System.out.println("Paso por esta validacion (2)");
									
									formulario
											.setError("GRUPO "
													+ i
													+ ": Tipo de Armazon no es comptible con Cristal");
									return false;
								}
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							// VALIDACRISMONSUP
							if (!cristal_derecho.getCod_barra().equals("CCLI")) {
								String msje = PosUtilesFacade.validaCrisMonSup(
										cristal_derecho, montura);
								if (!msje.equals(Constantes.STRING_TRUE)) {
									if (msje.equals(Constantes.STRING_BLANCO)) {
										formulario
												.setError("GRUPO "
														+ i
														+ ": Error al validar montura + cristal + suplemento");
										return false;
									} else {
										formulario.setError("GRUPO " + i + ": "
												+ msje);
										return false;
									}
								}// ojo derecho
							}

							if (!cristal_izquierdo.getCod_barra()
									.equals("CCLI")) {
								String msje = PosUtilesFacade.validaCrisMonSup(
										cristal_izquierdo, montura);

								if (!msje.equals(Constantes.STRING_TRUE)) // ojo
																			// izquierdo
								{
									if (msje.equals(Constantes.STRING_BLANCO)) {
										formulario
												.setError("GRUPO "
														+ i
														+ ": Error al validar montura + cristal + suplemento");
										return false;
									} else {
										formulario.setError("GRUPO " + i + ": "
												+ msje);
										return false; // validacion ojo
														// izquierdo fallido
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//VERIFICA SI DEBE TENER SUPLEMENTOS OBLIGATORIOS
						ArrayList<SuplementopedidoBean> suplementos = new ArrayList<SuplementopedidoBean>();
						try {
							suplementos = PosUtilesFacade.traeSuplementosObligatorios(cristal_derecho.getCod_barra());
						} catch (Exception e) {
							log.error("VentaPedidoHelper:actualizaProductos error catch",e);
						} 
						
						boolean estado_suple = false;
						
						if (null != suplementos && suplementos.size() != Constantes.INT_CERO)
						{
							cristal_derecho.setTiene_suple(Constantes.STRING_TRUE);
							if (null != cristal_derecho.getListaSuplementos() && cristal_derecho.getListaSuplementos().size() > 0) {
								for (SuplementopedidoBean suple : suplementos) {
									estado_suple = false;
									for (SuplementopedidoBean suplemento : cristal_derecho.getListaSuplementos()) {
										if (suple.getTratami().equals(suplemento.getTratami())) {
											estado_suple = true;
										}
									}
									if (!estado_suple) {
										formulario
										.setError("GRUPO "
												+ i
												+ ": Error al validar suplemento: el articulo tiene suplementos obligatorios.");
										return false;
									}
								}
							}
							else
							{
								formulario
								.setError("GRUPO "
										+ i
										+ ": Error al validar suplemento: el articulo tiene suplementos obligatorios.");
								return false;
							}
						}
						
						
						try {
							suplementos = PosUtilesFacade.traeSuplementosObligatorios(cristal_izquierdo.getCod_barra());
						} catch (Exception e) {
							log.error("VentaPedidoHelper:actualizaProductos error catch",e);
						} 

						estado_suple = false;
						
						if (null != suplementos && suplementos.size() != Constantes.INT_CERO)
						{
							cristal_izquierdo.setTiene_suple(Constantes.STRING_TRUE);
							if (null != cristal_izquierdo.getListaSuplementos() && cristal_izquierdo.getListaSuplementos().size() > 0) {
								for (SuplementopedidoBean suple : suplementos) {
									estado_suple = false;
									for (SuplementopedidoBean suplemento : cristal_izquierdo.getListaSuplementos()) {
										if (suple.getTratami().equals(suplemento.getTratami())) {
											estado_suple = true;
										}
									}
									if (!estado_suple) {
										formulario
										.setError("GRUPO "
												+ i
												+ ": Error al validar suplemento: el articulo tiene suplementos obligatorios.");
										return false;
									}
								}
							}
							else
							{
								formulario
								.setError("GRUPO "
										+ i
										+ ": Error al validar suplemento: el articulo tiene suplementos obligatorios.");
								return false;
							}
						}

					}

				} else // grupo no ok
				{
					//LMARIN 20160809
					int ret = 0;
					for (ProductosBean prod : lista_productos) {
						
						if(prod.getTipoFamilia().equals("L")){
							ret = 1;
						}
					}
					if(ret == 1){
						return true;
					}else{
						// if (cristalcli != 2) {
						formulario.setError("GRUPO " + i
								+ ": El trï¿½o esta mal armado, verifique los datos");
						return false;
					}
					// }
				}
			}
		}
		
		}catch(Exception e){
			
			e.getStackTrace();
			
		}
		return estado;
	}

	public ArrayList<ProductosBean> traeLosProductosGratuitos(
			ArrayList<ProductosBean> listaProductos, String nombre_local,
			String codigo_local, String tipo) {
		ArrayList<ProductosBean> listaProductosAdicionales = new ArrayList<ProductosBean>();
		
		
        try{
        	        	
				ProductosBean estuche = new ProductosBean();
				ProductosBean gamuza = new ProductosBean();
				ProductosBean bolsa = new ProductosBean();
				Vector<ProductosBean> productos = new Vector<ProductosBean>();
				int res = 0;
				res = PosUtilesFacade.valida_productos_gratuitos(listaProductos.get(0).getCod_pedvtcb());
			    if(res == 0){
			    	
			    
						for (Iterator<ProductosBean> iterator = listaProductos.iterator(); iterator
								.hasNext();) {
							
							
							ProductosBean productoBean = (ProductosBean) iterator.next();
							estuche = new ProductosBean();
							gamuza = new ProductosBean();
							bolsa  = new ProductosBean();
											
							
							/*LMARIN 20160113
							 * Se agregan suplementos a las multiofertas
							 * */ 
							
							if("MUL".equals(productoBean.getFamilia())){
								if ("SOL".equals(productoBean.getSubFamilia())) 
								{
									if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
										estuche = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"620000092");
										gamuza = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"630120008");
									}
									if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
										estuche = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"2560000000070");
										gamuza = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"630120009");
					
									}
									if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
										gamuza = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"2560000000461");
									}
								}
								if ("OPT".equals(productoBean.getSubFamilia())) 
								{
									if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
										estuche = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"620020071");
										gamuza = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"630120008");
									}
									if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
										estuche = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"620000050");
										gamuza = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"630120009");					
									}
									if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
										gamuza = PosProductosFacade.traeProducto(null, 1,
												codigo_local, tipo,
												"2560000000461");
									}
								}
								
								
								if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {	
									
									boolean repetido = false;
									if (null != gamuza.getCod_barra()) {
										// para gamuza
										for (ProductosBean prodBean : productos) {
											if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
												repetido = true;
												prodBean.setCantidad(prodBean.getCantidad()
														+ gamuza.getCantidad());
											}
										}
										if (!repetido) {
											productos.add(gamuza);
										}
									}
								}else{
									
								if (null != estuche.getCod_barra()) {
										estuche.setPrecio(0);
										gamuza.setPrecio(0);
										estuche.setCantidad(productoBean.getCantidad());
										gamuza.setCantidad(productoBean.getCantidad());
						
										boolean repetido = false;
						
										// para estuche
										for (ProductosBean prodBean : productos) {
											if (prodBean.getCodigo().equals(estuche.getCodigo())) {
												repetido = true;
												prodBean.setCantidad(prodBean.getCantidad()
														+ estuche.getCantidad());
											}
										}
										if (!repetido) {
											productos.add(estuche);
										}
						
										repetido = false;
										// para gamuza
										for (ProductosBean prodBean : productos) {
											if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
												repetido = true;
												prodBean.setCantidad(prodBean.getCantidad()
														+ gamuza.getCantidad());
											}
										}
										if (!repetido) {
											productos.add(gamuza);
										}
						
									}
																																									
								}
								
							}else{
									if (Constantes.STRING_FAMILIA_SOP.equals(productoBean.getFamilia())) // LENTE DE SOL PROPIO o LICENCIA
									{
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_GMO_ESTUCHE);
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_GMO_GAMUZA);
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_ECONOPTICAS_ESTUCHE);
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_ECONOPTICAS_GAMUZA);
						
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_SUN_PLANET) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_SUN_PLANET_ESTUCHE);
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_SUN_PLANET_GAMUZA);
										}
										if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_SUNGLASS_HUT_GAMUZA);
										}
									}
									
									if (Constantes.STRING_FAMILIA_ARX.equals(productoBean.getFamilia())) // MONTURAS
																										// SAFILO
									{
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
											
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_GMO_GAMUZA);
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
											
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_ECONOPTICAS_GAMUZA);
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_SUN_PLANET) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_ESTUCHE_ARX);
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_SUN_PLANET_GAMUZA);
										}
										if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_SUNGLASS_HUT_GAMUZA);
										}
									}
									
									if (Constantes.STRING_FAMILIA_ARP.equals(productoBean.getFamilia()))																				// SAFILO
									{
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_GMO_ESTUCHE);
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_GMO_GAMUZA);
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_ECONOPTICAS_ESTUCHE);
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_ECONOPTICAS_GAMUZA);
										}				
										if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
											gamuza = PosProductosFacade.traeProducto(null, 1,
													codigo_local, tipo,
													Constantes.STRING_PRODUCTO_SUNGLASS_HUT_GAMUZA);
										}
									}
									
									if (Constantes.STRING_FAMILIA_SOP.equals(productoBean.getFamilia())) // MONTURAS
										
									{
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
											estuche = PosProductosFacade.traeProducto(null, 1,
													  codigo_local, tipo,
													  Constantes.STRING_PRODUCTO_GMO_ESTUCHE_SOL);
											gamuza =  PosProductosFacade.traeProducto(null, 1,
													  codigo_local, tipo,
													  Constantes.STRING_PRODUCTO_GMO_GAMUZA);
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
											 estuche = PosProductosFacade.traeProducto(null, 1,
													 codigo_local, tipo,
													 Constantes.STRING_PRODUCTO_ECONOPTICAS_ESTUCHE_SOL);
											         gamuza = PosProductosFacade.traeProducto(null, 1,
									         codigo_local, tipo,
											         Constantes.STRING_PRODUCTO_ECONOPTICAS_GAMUZA);
										}				
										if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
											gamuza = PosProductosFacade.traeProducto(null, 1,
											codigo_local, tipo,
											Constantes.STRING_PRODUCTO_SUNGLASS_HUT_GAMUZA);
										}
									}
									
									if (Constantes.STRING_FAMILIA_ARF.equals(productoBean.getFamilia())) // MONTURAS
										
									{
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1) {
											
											 gamuza =  PosProductosFacade.traeProducto(null, 1,
													  codigo_local, tipo,
													  Constantes.STRING_PRODUCTO_GMO_GAMUZA);
										}
										if (nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1) {
												
									         gamuza = PosProductosFacade.traeProducto(null, 1,
											          codigo_local, tipo,
											          Constantes.STRING_PRODUCTO_ECONOPTICAS_GAMUZA);
										}				
										if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {					
											 gamuza = PosProductosFacade.traeProducto(null, 1,
													 codigo_local, tipo,
											         Constantes.STRING_PRODUCTO_SUNGLASS_HUT_GAMUZA);
										}
									}
									
									
									
									if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {	
										
										boolean repetido = false;
										if (null != gamuza.getCod_barra()) {
											// para gamuza
											for (ProductosBean prodBean : productos) {
												if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
													repetido = true;
													prodBean.setCantidad(prodBean.getCantidad()
															+ gamuza.getCantidad());
												}
											}
											if (!repetido) {
												productos.add(gamuza);
											}
										}
									}else{
										
										if (null != estuche.getCod_barra()) {
											estuche.setPrecio(0);
											gamuza.setPrecio(0);
											estuche.setCantidad(productoBean.getCantidad());
											gamuza.setCantidad(productoBean.getCantidad());
							
											boolean repetido = false;
							
											// para estuche
											for (ProductosBean prodBean : productos) {
												if (prodBean.getCodigo().equals(estuche.getCodigo())) {
													repetido = true;
													prodBean.setCantidad(prodBean.getCantidad()
															+ estuche.getCantidad());
												}
											}
											if (!repetido) {
												productos.add(estuche);
											}
							
											repetido = false;
											// para gamuza
											for (ProductosBean prodBean : productos) {
												if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
													repetido = true;
													prodBean.setCantidad(prodBean.getCantidad()
															+ gamuza.getCantidad());
												}
											}
											if (!repetido) {
												productos.add(gamuza);
											}
							
										}
																																										
									}
							}	
								
							//BOLSAS
							/*if(nombre_local.indexOf(Constantes.STRING_LOCAL_SUNGLASS_HUT) != -1) {	
								bolsa = PosProductosFacade.traeProducto(null, 1,
										codigo_local, tipo,
										Constantes.STRING_PRODUCTO_BOLSA_SUNGLASS_HUT);
											
							}else if(nombre_local.indexOf(Constantes.STRING_LOCAL_GMO) != -1){
								bolsa = PosProductosFacade.traeProducto(null, 1,
										codigo_local, tipo,
										Constantes.STRING_PRODUCTO_BOLSA_GMO);
								
							    if(Constantes.STRING_FAMILIA_ARX.equals(productoBean.getFamilia())){
									
									gamuza.setPrecio(0);
									gamuza.setCantidad(productoBean.getCantidad());									
					
									boolean repetido = false;
									// para gamuza
									for (ProductosBean prodBean : productos) {
										if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
											repetido = true;
											prodBean.setCantidad(prodBean.getCantidad()
													+ gamuza.getCantidad());
										}
									}
									if (!repetido) {
										productos.add(gamuza);
									}
							    	
							    }
						    	if(Constantes.STRING_FAMILIA_ARF.equals(productoBean.getFamilia())){
									
									gamuza.setPrecio(0);
									gamuza.setCantidad(productoBean.getCantidad());									
					
									boolean repetido = false;
									// para gamuza
									for (ProductosBean prodBean : productos) {
										if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
											repetido = true;
											prodBean.setCantidad(prodBean.getCantidad()
													+ gamuza.getCantidad());
										}
									}
									if (!repetido) {
										productos.add(gamuza);
									}
							    	
							    }
								
							}else if(nombre_local.indexOf(Constantes.STRING_LOCAL_ECONOPTICAS) != -1){
								bolsa = PosProductosFacade.traeProducto(null, 1,
										codigo_local, tipo,
										Constantes.STRING_PRODUCTO_BOLSA_ECONOPTICAS);
								if(Constantes.STRING_FAMILIA_ARX.equals(productoBean.getFamilia())){
									
									gamuza.setPrecio(0);
									gamuza.setCantidad(productoBean.getCantidad());									
					
									boolean repetido = false;
									// para gamuza
									for (ProductosBean prodBean : productos) {
										if (prodBean.getCodigo().equals(gamuza.getCodigo())) {
											repetido = true;
											prodBean.setCantidad(prodBean.getCantidad()
													+ gamuza.getCantidad());
										}
									}
									if (!repetido) {
										productos.add(gamuza);
									}
							    	
							    }
							}else if(nombre_local.indexOf(Constantes.STRING_LOCAL_RAYBAN) != -1){
								bolsa = PosProductosFacade.traeProducto(null, 1,
										codigo_local, tipo,
										Constantes.STRING_PRODUCTO_RAYBAN_BOLSA);
								
							}*/
							boolean repetido = false;				
							// para bolsa
							/*for (ProductosBean prodBean : productos) {
								if (prodBean.getCodigo().equals(bolsa.getCodigo())) {
									repetido = true;
									prodBean.setCantidad(1);
								}
							}
							if (!repetido) {
								productos.add(bolsa);
							}*/
							
						}
				
						for (ProductosBean prod : productos) {
							listaProductosAdicionales.add(prod);
						}
			    }
				
        }catch(Exception e){
        	System.out.println("Excepcion controlada ==>"+e.getMessage());
        }
        
        /*Remuevo todos los nulos*/
        listaProductosAdicionales.removeAll(Collections.singleton(null));
        
        return listaProductosAdicionales;
	}

	public void tarifica_Pedido(EntregaPedidoForm formulario) {
		ArrayList<ProductosBean> lista_productos = formulario
				.getListaProductos();

		long sub_total = 0;
		long descuento = 0;
		long total = 0;
		try{	
				if (lista_productos.size() != 0) {
					for (ProductosBean prod : lista_productos) {
						sub_total += prod.getTotal();
						descuento += prod.getDescuento();
						total += prod.getImporte();
					}
		
					formulario.setSubTotal(sub_total);
					formulario.setDescuento(total - sub_total);
					formulario.setDtcoPorcentaje((int) Math.floor(100 - ((total * 100) / sub_total)));
					formulario.setTotal(total);
					formulario.setAnticipo(0);
					formulario.setTotalPendiante(formulario.getAnticipo());
				} else {
					formulario.setSubTotal(0);
					formulario.setDescuento(0);
					formulario.setDtcoPorcentaje(0);
					formulario.setTotal(0);
					formulario.setAnticipo(0);
				}
		}catch(Exception e){
			System.out.println("me caigo al calcular convenio");
		}

	}

	public void traeDatosFormulario(EntregaPedidoForm formulario,
			HttpSession session) {
		String local = session.getAttribute(Constantes.STRING_SUCURSAL)
				.toString();

		// DATOS GENERICOS DESDE CONFIGURACION Y SESSION

		VentaPedidoBean ventaPedido = new VentaPedidoBean();
		ventaPedido = PosVentaPedidoFacade.traeGenericos(local);
		formulario.setPorcentaje_anticipo(ventaPedido.getPorcentaje_anticipo());
		formulario.setPorcentaje_descuento_max(ventaPedido
				.getPorcentaje_descuento_maximo());
		formulario.setCaja(ventaPedido.getNumero_caja());
	}

	public ArrayList<PrismaCantidadBean> traeListaCantidad() {
		ArrayList<PrismaCantidadBean> listaCantidad = new ArrayList<PrismaCantidadBean>();
		try {
			PrismaCantidadBean cant = null;
			cant = new PrismaCantidadBean();
			cant.setCodigo(-1);
			cant.setDescripcion("Seleccione");
			listaCantidad.add(cant);
			for (int i = 1; i <= 8; i++) {
				cant = new PrismaCantidadBean();
				cant.setCodigo(i);
				cant.setDescripcion(String.valueOf(i));
				listaCantidad.add(cant);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaCantidad;
	}

	public ArrayList<PrismaBaseBean> traeListaBase() {
		ArrayList<PrismaBaseBean> listaBase = new ArrayList<PrismaBaseBean>();
		ArrayList<String> lista = new ArrayList<String>();
		try {
			lista.add("Seleccione");
			lista.add("Superi");
			lista.add("Inferi");
			lista.add("Tempor");
			lista.add("Nasal");

			PrismaBaseBean pri = null;

			for (String st : lista) {
				pri = new PrismaBaseBean();
				pri.setDescripcion(st);
				listaBase.add(pri);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return listaBase;
	}
	
	public boolean valida_existe_error_cristal(ProductosBean prod,
			String tipo, String ojo) {
		
		boolean estado = false;
		
		if (prod.getTipoFamilia().equals(Constantes.STRING_C)) {
			if(!Constantes.STRING_LEJOS_OPT.equals(prod.getTipo()))
			{
				if (!Constantes.STRING_CERCA_OPT.equals(prod.getTipo())) 
				{
					return true;
				}
			}
		}
		
		if(!Constantes.STRING_DERECHO.equals(prod.getOjo()))
		{
			if (!Constantes.STRING_IZQUIERDO.equals(prod.getOjo())) {
				return true;
			}
		}
		if (null == prod.getFecha_graduacion() || Constantes.STRING_BLANCO.equals(prod.getFecha_graduacion())) 
		{
			return true;
		}
		return estado;
		
		
	}

	public boolean esLenteContactoGraduable(ProductosBean producto) {
		 if((producto.getFamilia().equals("LEC") && producto.getSubFamilia().equals("LDO") && producto.getGrupoFamilia().equals("VIS")) ){
			   
			   return false;
			   
	     }else{
			 if (producto.getFamilia().equals("LEC") || producto.getFamilia().equals("LEP") ) {
					if (!(producto.getFamilia().equals("LEP") && producto.getSubFamilia().equals("LDC") && producto.getGrupoFamilia().equals("A67")))
					{
						if (producto.getSubFamilia().equals("0") || producto.getGrupoFamilia().equals("0") ) {
							return false;
						}
						else
						{
							return true;
						}
					} else {
						return false;
					}
				}
				else
				{
					return false;
				}
		 }
	}

	public ClienteForm busquedaGiro(ClienteForm formulario) {

		try {
			ArrayList<GiroBean> lista = null;

			String giro = null;

			if (null != formulario.getGiro()) {
				if (!Constantes.STRING_BLANCO.equals(formulario.getGiro()
						.trim())) {
					giro = formulario.getGiro();
				}
			}
			String descripcionGiro = null;
			if (null != formulario.getDescripcionGiro()) {
				if (!Constantes.STRING_BLANCO.equals(formulario
						.getDescripcionGiro().trim())) {
					descripcionGiro = formulario.getDescripcionGiro();
				}
			}

			lista = PosUtilesFacade.busquedaGiro(giro, descripcionGiro);

			if (null != lista) {
				formulario.setListaGiros(lista);
			} else {
				formulario.setListaGiros(new ArrayList<GiroBean>());
			}

		} catch (Exception ex) {

		}

		return formulario;
	}

	public int isEntero(String x) {
		int respuesta = 0;
		try {
			respuesta = Integer.parseInt(x);

		} catch (Exception ex) {
			respuesta = -1;
		}
		return respuesta;
	}

	public ClienteBean traeClienteSeleccionado(String nif, String codigo)

	{
		log.info("ClienteHelper:traeClienteSeleccionado inicio");
		ClienteBean cliente;
		try {
			cliente = PosClientesFacade.traeCliente(nif, codigo);
			if (!(Constantes.STRING_BLANCO.equals(cliente.getFecha_nac()))
					&& null != cliente.getFecha_nac()) {

				SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
						Constantes.STRING_FORMAT_FECHA_ANO_MES_DIA);
				Date fecha = null;
				fecha = formatoDelTexto.parse(cliente.getFecha_nac());
				formatoDelTexto = new SimpleDateFormat(
						Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
				cliente.setFecha_nac(formatoDelTexto.format(fecha));
			}

			if ((Constantes.STRING_BLANCO.equals(cliente.getFono_casa()))
					&& null == cliente.getFono_casa()) {
				cliente.setFono_casa(Constantes.STRING_BLANCO);
			}

		} catch (Exception ex) {
			log.error("ClienteHelper:traeClienteSeleccionado error catch", ex);
			return new ClienteBean();
		}
		return cliente;
	}

	public GraduacionesBean traeUltimaGraduacionCliente(String codigo_cliente) {
		log.info("VentaPedidoHelper:traeUltimaGraduacionCliente inicio");
		GraduacionesBean graduacionBean = new GraduacionesBean();
		try {
			graduacionBean = PosGraduacionesFacade
					.traeUltimaGraduacionCliente(codigo_cliente);
		} catch (Exception e) {
			log.error(
					"VentaPedidoHelper:traeUltimaGraduacionCliente error catch",
					e);
		}
		return graduacionBean;
	}

	public ArrayList<OftalmologoBean> traeMedicos(String nifdoctor) {
		log.info("BusquedaMedicosHelper:traeMedicos inicio");
		ArrayList<OftalmologoBean> listaMedicos = new ArrayList<OftalmologoBean>();
		try {
			String nif = null;
			String apellido = null;
			String nombre = null;
			String codigo = null;

			if (null != nifdoctor
					&& !(Constantes.STRING_BLANCO.equals(nifdoctor))) {
				nif = nifdoctor.toUpperCase();
			}

			listaMedicos = PosBusquedaMedicosFacade.traeMedicos(null, nif,
					null, null);

		} catch (Exception ex) {
			log.error("BusquedaMedicosHelper:traeMedicos error catch", ex);
		}
		return listaMedicos;
	}

	public int obtenerIndexMultiOferta(ArrayList<ProductosBean> lista) {
		int index = 0;
		try {

			if (null != lista) {
				if (lista.size() > 0) {
					for (ProductosBean pro : lista) {
						if ("MUL".equals(pro.getFamilia())) {
							index = pro.getIndexMulti();
							index = index + 1;
						}
					}
				} else {
					index = 1;
				}
			} else {
				index = 1;
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return index;
	}

	public int obtenerIndexProductoMultiOferta(ArrayList<ProductosBean> lista,
			String codigo_multioferta, int indexRel_Multioferta) {
		int index = 0;
		try {

			if (null != lista) {
				if (lista.size() > 0) {
					for (ProductosBean pro : lista) {
						if (pro.getCodigoMultioferta().equals(codigo_multioferta)&& pro.getIndexRelMulti() == indexRel_Multioferta) {
							index = pro.getIndexProductoMulti();
							index = index + 1;
						}
					}
				} else {
					index = 1;
				}
			} else {
				index = 1;
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
		if (index == 0) {
			index = 1;
		}
		return index;
	}
	
	public ArrayList<ProductosBean> obtenerIndexProductoMultiOfertaCargaPedido(ArrayList<ProductosBean> lista,
			String codigo_multioferta, int indexRel_Multioferta) {
		int index = 1;
		try {

			if (null != lista) {
				if (lista.size() > 0) {
					for (ProductosBean pro : lista) {
						if (pro.getCodigoMultioferta().equals(codigo_multioferta)&& pro.getIndexRelMulti() == indexRel_Multioferta) {
							pro.setIndexProductoMulti(index);
							index++;
						}
					}
				} else {
					index = 1;
				}
			} else {
				index = 1;
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
		if (index == 0) {
			index = 1;
		}
		return lista;
	}


	public ArrayList<BoletaBean> traeListaBoletas(String cdg_vta) {
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		try {
			lista_boletas = PosUtilesFacade.traeListaBoletas(cdg_vta);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista_boletas;
	}

	public ArrayList<BoletaBean> traeListaBoletasAlbaranes(String cdg_vta,
			String tipo) {
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		try {
			lista_boletas = PosUtilesFacade.traeListaBoletasAlbaranes(cdg_vta,
					tipo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista_boletas;
	}

	public boolean validaCaja(String local, String fecha) {
		boolean respuesta = false;
		try {

			respuesta = PosUtilesFacade.validaAperturaCaja(local, fecha);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}

	public boolean eliminaAlbaran(String cdg, String fecha, String local,
			String tipo_albaran) {
		boolean respuesta = false;
		try {
			respuesta = PosUtilesFacade.eliminaAlbaran(cdg, fecha, local,
					tipo_albaran);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}

	public int convertirEntero(String n) {
		int numero = 0;
		try {
			numero = (int) Double.parseDouble(n);

		} catch (Exception ex) {
			numero = 0;
		}
		return numero;
	}

	public boolean isController(String agente) {
		boolean respuesta = false;
		try {

			respuesta = PosUtilesFacade.isController(agente);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}

	public ArrayList<BoletaBean> traeListaBoletasFechas(String codigo_0,
			String fecha) {
		ArrayList<BoletaBean> lista_boletas = new ArrayList<BoletaBean>();
		try {
			lista_boletas = PosUtilesFacade.traeListaBoletasFechas(codigo_0,
					fecha);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista_boletas;
	}

	public AlbaranBean traeCodigoAlbaran(String cdg_pedvtcb) {
		AlbaranBean alb = null;
		try {

			alb = PosUtilesFacade.traeCodigoAlbaran(cdg_pedvtcb);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return alb;
	}
	
	public boolean estadoEntrega(ProductosBean producto){
		boolean estadoEntrega = true;		
		
		if("M".equals(producto.getTipoFamilia()) && (!"0".equals(producto.getGrupo())) ||  "C".equals(producto.getTipoFamilia())){									
			if("2".equals(producto.getLiberado()) || "4".equals(producto.getLiberado())){
				estadoEntrega=true;
			}else{				
				estadoEntrega=false;				
			}
			
		}else if("L".equals(producto.getTipoFamilia())){
			if(esLenteContactoGraduable(producto)){
				if("2".equals(producto.getLiberado()) || "4".equals(producto.getLiberado())){
					estadoEntrega=true;
				}else{					
					estadoEntrega=false;					
				}
			}else{
				estadoEntrega=true;
			}
		}else{
			estadoEntrega=true;
		}
	
		
		
		
		return estadoEntrega;
	}
	
	
	public boolean estaLiberado(ProductosBean producto){
		boolean liberado = true;		
		
		
		if("M".equals(producto.getTipoFamilia()) && (!"0".equals(producto.getGrupo())) ||  "C".equals(producto.getTipoFamilia())){									
			if("1".equals(producto.getLiberado()) || "2".equals(producto.getLiberado()) || "4".equals(producto.getLiberado())){
				liberado=true;
			}else{				
				liberado=false;				
			}
			
		}else if("L".equals(producto.getTipoFamilia())){
			if(esLenteContactoGraduable(producto)){
				if("1".equals(producto.getLiberado()) || "2".equals(producto.getLiberado()) || "4".equals(producto.getLiberado())){
					liberado=true;
				}else{					
					liberado=false;					
				}
			}else{
				liberado=true;
			}
		}else{
			liberado=true;
		}
	
		
		
		
		return liberado;
	}
	
	public String  restarDiasFecha(int dias){
		String resultFecha="";
		try{
			resultFecha = this.traeFechaHoyFormateadaString();
			SimpleDateFormat formato = new SimpleDateFormat(Constantes.STRING_FORMAT_SIMPLE_DATE_FORMAT);
		
			Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.DAY_OF_MONTH, -dias);
		    Date fecha = calendar.getTime();
		    resultFecha = formato.format(fecha);
		    
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			resultFecha = this.traeFechaHoyFormateadaString();
		}
		return resultFecha;
	}
	
	public int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String fechaInicioString = df.format(fechaInicial);
		try {
		fechaInicial = df.parse(fechaInicioString);
		}
		catch (ParseException ex) {
		}

		String fechaFinalString = df.format(fechaFinal);
		try {
		fechaFinal = df.parse(fechaFinalString);
		}
		catch (ParseException ex) {
		}

		long fechaInicialMs = fechaInicial.getTime();
		long fechaFinalMs = fechaFinal.getTime();
		long diferencia = fechaFinalMs - fechaInicialMs;
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		return ( (int) dias);
	}


	public ArrayList<SuplementopedidoBean> traeSuplementosPedidoLiberacionMultioferta(
			int codigo) {
		ArrayList<SuplementopedidoBean> listaSuple = new ArrayList<SuplementopedidoBean>();
		try {

			listaSuple = PosVentaPedidoFacade.traeTratamientosPedidoMultiofertas(codigo);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listaSuple;
	}
	
	public boolean validaProductosMultiofertaBD(ArrayList<ProductosBean> listaProductos, String venta,String tipo ) {
		String mje = Constantes.STRING_BLANCO;
		for (ProductosBean prod : listaProductos) {
			if (prod.getCdg_correlativo_multioferta()!=0) {
				try {
					mje = PosUtilesFacade.validaProductosMultiofertaBD(prod.getCdg_correlativo_multioferta(), venta, tipo);
					
					if(mje.equals("ERROR"))
					{
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean validaProductosMultiofertaBD(ProductosBean prod, String venta,String tipo ) {
		String mje = Constantes.STRING_BLANCO;
		
			if (prod.getCdg_correlativo_multioferta()!=0) {
				try {
					mje = PosUtilesFacade.validaProductosMultiofertaBD(prod.getCdg_correlativo_multioferta(), venta, tipo);
					
					if(mje.equals("ERROR"))
					{
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
		
		return true;
	}
	
	public ArrayList<ProductosBean> ListaProductosBoleta(ArrayList<ProductosBean> listProductos,
			ArrayList<ProductosBean> listCabeceraMulrioferta,
			ArrayList<ProductosBean> listDetalleMulrioferta) {
		
		ArrayList<ProductosBean> lista_total_productos = new ArrayList<ProductosBean>();
		
		if(null != listProductos && listProductos.size()>0){
		
			for (ProductosBean productosBean : listProductos) {  
				
				lista_total_productos.add(productosBean);
		        
		        if(null != listCabeceraMulrioferta){//saber si la lista de multiofertas es nula
			        for (ProductosBean multi : listCabeceraMulrioferta){//recorrer la lista de multiofertas
			        	if(productosBean.getCodigo().equals(multi.getCodigo())&& productosBean.getIndexMulti() == multi.getIndexMulti()){//preguntar si el producto multioferta es igual al producto que existe en la listaMultioferta	
			        		if(null != listDetalleMulrioferta){
				        		for (ProductosBean prodmulti : listDetalleMulrioferta){
				        			if(prodmulti.getCodigoMultioferta().equals(productosBean.getCodigo()) && prodmulti.getIndexRelMulti() == productosBean.getIndexMulti()){
				        				ProductosBean proM = new ProductosBean();
				        				
				        				proM.setCantidad(prodmulti.getCantidad());
				        				proM.setCod_barra("PROM " + productosBean.getCod_barra()+ ":	 " + prodmulti.getCod_barra());
				        				proM.setCodigo("PROM " + productosBean.getCod_barra()+ ":	 " + prodmulti.getCod_barra());
				        				proM.setDescripcion(prodmulti.getDescripcion());
				        				proM.setImporte(0);
				        				proM.setPrecio(0);
				        				proM.setDescuento(0);
				        				
				        				lista_total_productos.add(proM);
				        			}
				        		}
			        		}       		
			        	}
			        }    
		        }
			}
		}
		
		return lista_total_productos;
		
		// TODO Apï¿½ndice de mï¿½todo generado automï¿½ticamente
		
	}


	public double formato_Decimal(String dto) {

		DecimalFormat formateador = new DecimalFormat("###.####");
		DecimalFormatSymbols dfs = formateador.getDecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		formateador.setDecimalFormatSymbols(dfs);
		double valor;
		
		Number numero;
		try {
			numero = formateador.parse(dto);
			valor = numero.doubleValue();
		} catch (ParseException e) {
			valor = 0;
		}
		
		
		return valor;
	}
	
	public ProductosBean aplicaPrecioEspecial(ProductosBean producto, String fecha) {
		
		ArrayList<PrecioEspecialBean> precios = new ArrayList<PrecioEspecialBean>();
		try {
			precios = PosUtilesFacade.traePrecioEspecial(producto.getCod_barra(), producto.getCantidad(), fecha);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("precios => "+precios.get(0).getPrecio());
		
		//if (!precios.isEmpty() && precios.get(0).getPrecio()>0 ) {
			System.out.println("paso a setear nuevo precio");
			for (PrecioEspecialBean precio : precios) {
				//if (precio.getCantidad()== producto.getCantidad()) {
					System.out.println("precio normal => "+precio.getPrecio()+"<==> precio dto =>"+precio.getDto());
					producto.setPrecio(precio.getPrecio()*(int)(1-precio.getDto()/100));
					producto.setPrecioEspecial(true);
					break;
				//}			
			}
			
			System.out.println("Importe ===> "+producto.getPrecio()+" * "+producto.getCantidad());
			
			producto.setImporte(((int)producto.getPrecio()*(int)producto.getCantidad()));
		//}
		
		System.out.println("Importe final  ==>"+producto.getImporte());
		return producto;
	}
	
	public ProductosBean aplicaDescuentoSeguro(ProductosBean producto) {
		
		producto.setPrecio((int)(producto.getPrecio()*(0.7)));
		//producto.setPrecioEspecial(true);
				
		System.out.println("Importe ===> "+producto.getPrecio()+" * "+producto.getCantidad());
		
		producto.setImporte(((int)producto.getPrecio()*(int)producto.getCantidad()));
		
		
		System.out.println("Importe final  ==>"+producto.getImporte());
		return producto;
	}
	
	public boolean verificaPrecioEspecial(ProductosBean producto, String fecha) {
		ArrayList<PrecioEspecialBean> precios = new ArrayList<PrecioEspecialBean>();
		try {
			precios = PosUtilesFacade.traePrecioEspecial(producto.getCod_barra(), producto.getCantidad(), fecha);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if (null != precios && precios.size()>0) {
			for (PrecioEspecialBean precio : precios) {
				//if (precio.getCantidad() == producto.getCantidad())
				//{
					return true;
				//}
			}
			return false;
		}
		else
		{
			return false;
		}
	}
	
	

	/*
	 * LMARIN 20140617
	 * Se setea con la variable  productosBean.getPrecio() a  productosBean.setPrecio()
	 * 
	 */
	public ProductosBean eliminaPrecioEspecial(ProductosBean productosBean) {
		//productosBean.setPrecio(productosBean.getTotal());
		productosBean.setPrecio(productosBean.getTotal());
		
		productosBean.setImporte(productosBean.getPrecio() * productosBean.getCantidad());
		productosBean.setPrecioEspecial(false);
		return productosBean;
	}
	
	public void validaDiametroESFCL(ProductosBean productosBean) {
		
		PosUtilesFacade util = new PosUtilesFacade();
		int valor = util.validaDiametroESFCL(productosBean);
		if (valor != 0) {
			productosBean.setDesdediametro(valor);
		}
	}
	/*
	 * LMARIN 20140808 
	 * SE LE SUMA UNO A LA FECHA DE ENTREGA 
	 */
	
	public String fechaExcepMasUno(String fecha) throws Exception {
		Date fechatmp = null;
		String fechaout = "";
		//String [] fecstring = fecha.split("/");
		
		System.out.println("Paso FECHAIN ==> "+fecha);
		
		fechatmp =  PosUtilesFacade.agregaDias(fecha);
		fechaout = this.formatoFecha(fechatmp);
		
		System.out.println("Paso FECHAOUT ==> "+fechaout);
		return fechaout; 
	}
	
	public int retnumero(String n){
		int res = 0;
		if (n == null || n.length() == 0) {
		       return 0;
		}else{
			String r  = n.replaceAll("[^0-9]+", "").trim();
			res = Integer.parseInt(r);
		}
		return res;
	}
	
	 /**
	 * @autor LMARIN
	 * @date 20141218
	 * @desc Metodo que genera el String con datos que se envia al WebService para generar boletas
	 * @param String tipodoc,
	 * @param String folio ,
	 * @param SeleccionPagoForm formulario
	 * @param ArrayList<ProductosBean> prod
	 * @param tipoimp
	 * @return String
	 * @throws Exception 
	 */
	public String generaBoletaELEC(String tipodoc,String folio ,SeleccionPagoForm formulario,ArrayList<ProductosBean> prod) throws Exception{

		Utils util = new Utils();
		
	    CoreSoapProxy cr = new CoreSoapProxy();
		
	    String respuesta="",linea1="",linea2="",linea3="",linea4="",linea5="",tipoimp ="",resout="",out="",salidafinal="";
		int cont = 0,peti = 0;
		
		System.out.println("Entro a generaBoletaELEC");
		Properties prop = util.leeConfiguracion();
		
		
		//DATOS CABECERA GMO
		String nombreempresa = prop.getProperty("descripcion.nombre").trim();
		String razonsocial = prop.getProperty("descripcion.razonsocial").trim();
		String rut = prop.getProperty("descripcion.rut").trim();
		String version = prop.getProperty("descripcion.version").trim();
		String direccion_tienda = "", localidad_tienda="";
		
		
		String fechaentrega= this.fechaExcepMasUno(formulario.getBoleta_fecha_ent());
		String [] fecharr = formulario.getFecha().split("/"); 
		String [] tienda = formulario.getBoleta_tienda().split("-");
		String ftpago1 = (!formulario.getBoleta_titulo_fpago_1().equals("") && formulario.getBoleta_titulo_fpago_1() != null) ?formulario.getBoleta_titulo_fpago_1()+" - ":"";
		int fpago1  = (!formulario.getBoleta_fpago_1().equals("") && formulario.getBoleta_fpago_1() != null) ?retnumero(formulario.getBoleta_fpago_1()):0;
		String ftpago2 = (!formulario.getBoleta_titulo_fpago_2().equals("") && formulario.getBoleta_titulo_fpago_2() != null) ?formulario.getBoleta_titulo_fpago_2()+" - ":"";
		int fpago2  = (!formulario.getBoleta_fpago_2().equals("") && formulario.getBoleta_fpago_2() != null) ?retnumero(formulario.getBoleta_fpago_2()):0;
		String ftpago3 = (!formulario.getBoleta_titulo_fpago_3().equals("") && formulario.getBoleta_titulo_fpago_3() != null) ?formulario.getBoleta_titulo_fpago_3()+" - ":"";
		int fpago3  = (!formulario.getBoleta_fpago_3().equals("") && formulario.getBoleta_fpago_3() != null) ?retnumero(formulario.getBoleta_fpago_3()):0;
		String ftpago4 = (!formulario.getBoleta_titulo_fpago_4().equals("") && formulario.getBoleta_titulo_fpago_4() != null) ?formulario.getBoleta_titulo_fpago_4()+" - ":"";
		int fpago4  = (!formulario.getBoleta_fpago_4().equals("") && formulario.getBoleta_fpago_4() != null) ?retnumero(formulario.getBoleta_fpago_4()):0;
		int total = formulario.getV_total();
		int sumfpagos = fpago1+fpago2+fpago3+fpago4;
		int bpagar = retnumero(formulario.getBoleta_total_pagar());
		double punit = 0;
		boolean vanticipo =false;
		

		try {
			ArrayList<TiendaBean> tb = PosUtilesFacade.traeDatosTienda(tienda[0].trim());
			
			tipoimp = tb.get(0).getTipo_impresion();	
			direccion_tienda = tb.get(0).getDireccion();
			localidad_tienda = tb.get(0).getLocalidad();
			
			System.out.println("tipo impresion ==>"+tipoimp);
			
			System.out.println("UTIL BOLETA ==> " +total +"<===>"+sumfpagos);
		
		
			System.out.println("Saldo pendiente =====>"+formulario.getBoleta_resumen_pendiente()+"<=>"+formulario.getBoleta_resumen_anticipo()+" TOTAL A PAGAR ==>"+formulario.getBoleta_total_pagar());

			
		   if(!formulario.getBoleta_resumen_pendiente().equals("") && !formulario.getBoleta_resumen_anticipo().equals("")){		
			
			  if( total == sumfpagos){
			    linea1 ="A:"+version+"|39|"+folio+"|"+fecharr[2]+"-"+fecharr[1]+"-"+fecharr[0]+"|1|"+rut+"|"+nombreempresa+"|"+razonsocial+"||"+direccion_tienda+"|"+localidad_tienda+"|"+localidad_tienda+"|"+formulario.getNif()+"|"+formulario.getNombre_cliente()+"||"+formulario.getDireccion()+"|||||||"+bpagar+"\n";
					
				for(ProductosBean b: prod){
				
					if(b.getImporte() == 0){
						linea2 += "B:"+cont+"|INT1|"+b.getCod_barra()+"|0||"+b.getDescripcion()+"||"+b.getCantidad()+"||0|0\n";																					
					}else{
						if(!b.getFamilia().equals("DES")){
							punit = ((double)b.getImporte()/(double)b.getCantidad());
							linea2 += "B:"+cont+"|INT1|"+b.getCod_barra()+"|0||"+b.getDescripcion()+"||"+b.getCantidad()+"|"+Math.round(punit)+"|0|"+((int)b.getImporte())+"\n";																					
						}
					}
					cont++;
				}	
									
				int sumfpagos2 = 0;			

				if(fpago1 != 0 && fpago2 == 0 && fpago3 == 0 && fpago4 == 0){
					sumfpagos2 = 0; ftpago2 =""; ftpago3 ="";ftpago4 =""; fpago2 = 0; fpago3 =0;fpago4 =0;
				}else if(fpago1 != 0 && fpago2 != 0 && fpago3 == 0 && fpago4 == 0){
					sumfpagos2 = fpago1; ftpago1 = ""; ftpago3=""; ftpago4=""; fpago1 = 0; fpago3=0; fpago4=0;
				}else if(fpago1 != 0 && fpago2 != 0 && fpago3 != 0 && fpago4 == 0){
					sumfpagos2 = fpago1+fpago2; ftpago1 = ""; ftpago2=""; ftpago4=""; fpago1 = 0; fpago2= 0;fpago4=0;
				}else if(fpago1 != 0 && fpago2 != 0 && fpago3 != 0 && fpago4 != 0){
					if((fpago4+fpago3+fpago2) == bpagar){
						sumfpagos2 = fpago1;
						ftpago1="";  fpago1=0;
					}else if((fpago4+fpago3) == bpagar){
						sumfpagos2 = fpago1+fpago2;
						ftpago1 = ""; ftpago2=""; fpago1=0; fpago2=0;
					}else{
						sumfpagos2 = fpago1+fpago2+fpago3; ftpago1 = ""; ftpago3="";ftpago2=""; fpago1 = 0; fpago3=0;fpago2=0;
					}
					
				}
				for(ProductosBean b: prod){
					if(b.getFamilia().equals("DES")){
						vanticipo =true;
					}
				}
				if(vanticipo == false){
					linea3 ="D:1|D|ANTICIPOS|$|"+sumfpagos2+"|1\n";
				}else{
					linea3 ="D:\n";
				}
				
								
			}else if(total > sumfpagos){
				
				linea1 ="A:"+version+"|39|"+folio+"|"+fecharr[2]+"-"+fecharr[1]+"-"+fecharr[0]+"|1|"+rut+"|"+nombreempresa+"|"+razonsocial+"||"+direccion_tienda+"|"+localidad_tienda+"|"+localidad_tienda+"|"+formulario.getNif()+"|"+formulario.getNombre_cliente()+"||"+formulario.getDireccion()+"|||||||"+bpagar+"\n";
				linea2 += "B:1|INT1|ANT|0||ANTICIPO||1|"+bpagar+"|0|"+bpagar+"\n";
				
				if(fpago1 != 0 && fpago2 == 0 && fpago3 == 0 && fpago4 == 0){
					ftpago2 =""; ftpago3 ="";ftpago4 =""; fpago2 =0; fpago3 =0;fpago4 =0;
				}else if(fpago1 != 0 && fpago2 != 0 && fpago3 == 0 && fpago4 == 0){
					if((fpago1+fpago2) == bpagar){
						 ftpago3=""; ftpago4=""; fpago3=0; fpago4=0;
					}else{
						ftpago1 = ""; ftpago3=""; ftpago4=""; fpago1 = 0; fpago3=0; fpago4=0;
					}
				}else if(fpago1 !=0 && fpago2 != 0 && fpago3 != 0 && fpago4 ==0){
					if((fpago1+fpago2+fpago3) == bpagar){
						ftpago4="";  fpago4=0;
					}else if((fpago2+fpago3) == bpagar){
						ftpago1 = ""; ftpago4=""; fpago1=0; fpago4=0;
					}else{
						ftpago1 = ""; ftpago2="";ftpago4=""; fpago1 = 0; fpago2=0;fpago4=0;
					}
				}
				linea3 ="D:\n";
			}
		 }else{
			 linea1 ="A:"+version+"|39|"+folio+"|"+fecharr[2]+"-"+fecharr[1]+"-"+fecharr[0]+"|1|"+rut+"|"+nombreempresa+"|"+razonsocial+"||"+direccion_tienda+"|"+localidad_tienda+"|"+localidad_tienda+"|"+formulario.getNif()+"|"+formulario.getNombre_cliente()+"||"+formulario.getDireccion()+"|||||||"+bpagar+"\n";
				
				for(ProductosBean b: prod){
					if(b.getImporte() == 0){
						linea2 += "B:"+cont+"|INT1|"+b.getCod_barra()+"|0||"+b.getDescripcion()+"||"+b.getCantidad()+"||0|0\n";																					
					}else{
						if(!b.getFamilia().equals("DES")){
							punit = ((double)b.getImporte()/(double)b.getCantidad());
							linea2 += "B:"+cont+"|INT1|"+b.getCod_barra()+"|0||"+b.getDescripcion()+"||"+b.getCantidad()+"|"+Math.round(punit)+"|0|"+((int)b.getImporte())+"\n";																					
						}
					}
					cont++;
				}	
				linea3 ="D:\n";
		 }
		   
		 linea4 ="E:\n";
		 linea5 ="C:"+formulario.getSerie()+"|FECHA DE ENTREGA :"+fechaentrega+" despues de las  18:00.|"+formulario.getBoleta_tienda()+"|"+formulario.getBoleta_vendedor()+"|"+formulario.getSerie()+"|"+ftpago1+fpago1+"|"+ftpago2+fpago2+"|"+ftpago3+fpago3+"|"+ftpago4+fpago4+"||"+tipoimp+"|"+formulario.getBoleta_hora();			
		 out = "true";
		 
		 while(peti <= 5){
			 try{
			 		Thread.sleep(1800);
			 		
				    respuesta = linea1.concat(linea2).concat(linea3).concat(linea4).concat(linea5);
				    
				    //System.out.println("TXT ==> "+respuesta);
				     
				    //log.warn("generaBoletaELEC respuesta resout XML ==> "+respuesta);
				     
					resout = cr.convertDocument(Constantes.STRING_WS_AREA, Constantes.STRING_WS_PASSWD, Constantes.STRING_WS_DTYPE, respuesta);
					
					log.warn("generaBoletaELEC respuesta resout XML ==> "+resout);
					
					//System.out.println("generaBoletaELEC respuesta resout XML ==> "+resout);
					 
					Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				            .parse(new InputSource(new StringReader(resout)));
					
				    NodeList errNode = doc.getElementsByTagName("ProcessResult");
				    
				    if (errNode.getLength() > 0) {
				         Element err = (Element)errNode.item(0);
				         out = err.getElementsByTagName("HasError").item(0).getTextContent().trim().toLowerCase();				    
				    }

					if(out.equals("false")){
						//System.out.println("OUT ==> "+out);
						break;
					}
				    
				   // System.out.println("TXT ==> "+respuesta);
				    
			 
			 	}catch(Exception e){
			 		log.error("error en el while generaBoletaELEC => "+e.getMessage());
			 	}
		     	//System.out.println("Contador peticion webservice ==> "+peti);
				peti++;
				
		  }
		  
		} catch (Exception e) {			
			log.error("error generaBoletaELEC => "+e.getMessage());
		}
		if(formulario.getNif().equals("") || formulario.getNif()==null){
			formulario.setNif("0");
		} 
		salidafinal = out+"_"+formulario.getNif();
		
		
		return salidafinal; 
	}
	
	 /**
		 * @autor LMARIN
		 * @date 20141218
		 * @desc Metodo que genera el String con datos que se envia al WebService para generar Notas de Crï¿½dito
		 * @param String tipodoc,
		 * @param String folio ,
		 * @param SeleccionPagoForm formulario
		 * @param ArrayList<ProductosBean> prod
		 * @param tipoimp
		 * @return String
	    */
		public String genera_notacredito(String tipodoc,String folio ,ArrayList<PagoBean> listaPagos,DevolucionForm devform, Session session,DevolucionForm formulario2){
			
			Utils util = new Utils();
			
		    CoreSoapProxy cr = new CoreSoapProxy();
		    
			String linea1 ="",linea2="",linea3="",linea4="",ltotal="",resout="", out ="",res="";
			
			int sumalb = Math.abs(devform.getSumaTotalAlabaranes());
			int sumdest = 0,sneto = 0,sumades = 0,dtofinal =0,sumiva=0,sumfinal=0;
			double dto=0;
			int peti = 0;
			
			
			System.out.println("PASO PRIMERA PARTE NC");	
			
			for(PagoBean b : listaPagos){
				if(b.getForma_pago().equals("12") || b.getForma_pago().equals("OA") 
				   || b.getForma_pago().equals("OASD") || b.getForma_pago().equals("SEGCO") 
				   || b.getForma_pago().equals("CRB")){
					System.out.println(b.getForma_pago()+"<=>"+b.getCantidad()+"<=>"+b.getCantidad_divisa()+"<=>"+b.getDevolucion()+"<=>"+b.getDetalle_forma_pago()+"<=>"+b.getV_a_pagar());
					sumdest += b.getCantidad();
				}
			}
			sneto = (int) Math.round(sumalb/1.19);

			
			if(sumdest != 0){
				sumades = sumalb - sumdest ;
			}else{
				sumades = sumalb;
			}
			dtofinal = (int) Math.round(sumdest/1.19);
			sumiva= ((sumalb-sumdest) -sneto)+dtofinal;
			sumfinal = (sneto-dtofinal)+sumiva;
			String rutcl = devform.getNif()+"-"+devform.getDvnif();
			String nombrecl= (!devform.getNombreCliente().equals("") && devform.getNombreCliente() != null)?devform.getNombreCliente():""; 
			String girocl= (!devform.getGiro().equals("") && devform.getGiro() != null)?devform.getGiro():""; 
			String [] procli =  formulario2.getComu_cli().split("_");
			String direcciocl = formulario2.getDireccion_cli().toUpperCase(); 
			String numerocli = formulario2.getNdireccion_cli();
					
			try{
				
				String [] fecharr = devform.getFecha().split("/"); 
				String [] fechaenorigen = devform.getFecha().split("/");
				linea1 =  "E0161|"+folio+"|"+fecharr[2]+"-"+fecharr[1]+"-"+fecharr[0]+"|||||||||||||||||||||\n";
				linea1 += "E02\n"; 
				linea1 += "E0396891370-0|Opticas GMO Chile S.A.|Optica||||||||||Avenida Santa Clara 249, Ciudad Empresarial.|Huechuraba|Santiago|||\n";

				linea1 += "E04"+rutcl+"|"+session.getAttribute(Constantes.STRING_SUCURSAL).toString()+"|"+nombrecl+"||||"+girocl+"|||"+direcciocl.concat("-").concat(numerocli)+"|"+procli[1]+"|"+procli[1]+"|||||\n";
				linea1 += "E05|||||||\n";
				linea1 += "E06||||||||||||||||||||||\n";
				linea1 += "E07\n";
				linea1 += "E08"+sneto+"||||19|"+sumiva+"|||||||||||"+(sumalb-sumdest)+"||||\n";
				linea1 += "E09\n";
				linea1 += "E10|||||||||\n";
				linea1 += "E11\n";
				
				System.out.println("CABECERA DESCRIPTIVA  NC linea1 =>"+linea1);	

				
				for(ProductosBean b: devform.getLista_productos()){
					System.out.println("DESCRIPCION ==>"+b.getDescripcion()+" PRECIO =>"+b.getPrecio()+" IMPORTE=>"+b.getImportedef()+" DTO =>"+b.getDto()+" - "+b.getDescuento());
				    dto = Double.parseDouble(b.getDto())/100;
				    double predto = Math.round(b.getPrecio() * dto);
				    int precio = (int) Math.round(((b.getPrecio() - predto)/1.19));
				    int cantidad = Math.abs(b.getCantidad()); 
				    int importe  = (int)Math.round(cantidad * precio);
				    
				    System.out.println("PRECIO CAL =>"+precio+"<==> Cantidad =>"+cantidad+" Importe ==>"+importe);
					if(b.getPrecio() == 0 || precio == 0){
						linea2 += "DINT1|"+b.getCod_barra()+"|||||||||||||||"+b.getDescripcion()+"||1|UN||1|||UN|||||||||||||0\n";																					
					}else{
						linea2 += "DINT1|"+b.getCod_barra()+"|||||||||||||||"+b.getDescripcion()+"||"+cantidad+"|UN||"+cantidad+"|||UN|"+precio+"||||||||||||"+importe+"\n";																					
					}
				}	
				System.out.println("CABECERA DESCRIPTIVA  NC linea2 =>"+linea2);	

				linea3 = "AD|DIF|$|"+dtofinal+"|||\n";
				
				System.out.println("CABECERA DESCRIPTIVA  NC linea3 =>"+linea3);	

				linea4 = "R39||"+devform.getNumero_boleta_guia()+"|"+fechaenorigen[2]+"-"+fechaenorigen[1]+"-"+fechaenorigen[0]+"|1||";
				
				System.out.println("CABECERA DESCRIPTIVA  NC linea4 =>"+linea4);
				
				
				 while(peti <= 5){
				
					Thread.sleep(1800);
					
					ltotal = linea1.concat(linea2).concat(linea3).concat(linea4);
							
					System.out.println("LINEA TOTAL ==> "+ ltotal);
					log.warn("LINEA TOTAL NC ==> "+ ltotal);
					
					resout = cr.convertDocument(Constantes.STRING_WS_AREA_NC, Constantes.STRING_WS_PASSWD, Constantes.STRING_WS_DTYPE_NC, ltotal);
					
					log.warn("NC XML ==> "+resout);
					 
					Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				            .parse(new InputSource(new StringReader(resout)));
				 
				    NodeList errNode = doc.getElementsByTagName("ProcessResult");
				    if (errNode.getLength() > 0) {
				         Element err = (Element)errNode.item(0);
				         out = ((Node) err.getElementsByTagName("HasError").item(0)).getTextContent();
				    }
				    
				    if(out.equals("false")){						
						break;
					}
					
					//out = "false";
				
					peti++;
				 }
				
			}catch(Exception e){
				log.warn("NOTA DE CREDITO ERROR ==> "+e);
				System.out.println("Mensaje excepcion genera_notacredito  ===> "+e.getMessage());
				return res = "true_"+devform.getNif()+"-"+devform.getDvnif();

			}
			
			res = out+"_"+devform.getNif()+"-"+devform.getDvnif();
			return res;
	}
	
	public String ticket_cambio(SeleccionPagoForm form){
		
		String res = "-1";
		
		//Genero Variables
		String fecha = (!form.getBoleta_fecha().equals("") && form.getBoleta_fecha() != null)?form.getBoleta_fecha():"" ; 
		String hora = (!form.getBoleta_hora().equals("") && form.getBoleta_hora() != null)?form.getBoleta_hora():"" ; 
		String local = (!form.getBoleta_tienda().equals("") && form.getBoleta_tienda()!= null)?form.getBoleta_tienda():"" ;
		String vendedor = (!form.getBoleta_vendedor().equals("") && form.getBoleta_vendedor()!= null)?form.getBoleta_vendedor():"" ; 
		String numero_encargo = (!form.getSerie().equals("") && form.getSerie()!= null) ? form.getSerie():"" ; 
		int numero_boleta = form.getNumero_boleta() != 0 ? form.getNumero_boleta():0 ; 
		
		try {													
				// Creo docuemnto
				PDDocument document = new PDDocument();
		      	PDPage pagina = new PDPage();
		      	document.addPage(pagina);
		      	
		      	PDFont font = PDType1Font.COURIER;
		    	PDFont font1 = PDType1Font.COURIER_BOLD;
		      	
		      	PDPageContentStream cts;
				
				cts = new PDPageContentStream(document, pagina);
							        			        			      			
		      	cts.beginText();
		      	cts.setFont(font1, 10);
		      	cts.moveTextPositionByAmount(0, 780);
		     	cts.drawString("___________________________________________________");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 10);
		      	cts.moveTextPositionByAmount(50, 760);
		      	cts.drawString("Opticas GMO S.A");
		      	cts.endText();
		      
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 10);
		      	cts.moveTextPositionByAmount(0, 740);
		     	cts.drawString("___________________________________________________");
		      	cts.endText();
		      	
		      	
		      	
		      	cts.beginText();
		      	cts.setFont(font, 9);
		      	cts.moveTextPositionByAmount(0, 700);
		      	cts.drawString("FECHA : "+fecha+"		 	  HORA : "+hora+"	   ");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font, 9);
		      	cts.moveTextPositionByAmount(0, 680);
		      	cts.drawString("LOCAL : "+local);
		      	cts.endText();
		   
		      	cts.beginText();
		      	cts.setFont(font, 9);
		      	cts.moveTextPositionByAmount(0, 660);
		      	cts.drawString("N. Encargo : "+numero_encargo);
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font, 9);
		      	cts.moveTextPositionByAmount(0, 640);
		      	cts.drawString("N. BOLETA SII : "+numero_boleta);
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font, 9);
		      	cts.moveTextPositionByAmount(0, 620);
		      	cts.drawString("VENDEDOR : "+vendedor);
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 10);
		      	cts.moveTextPositionByAmount(0, 600);
		     	cts.drawString("___________________________________________________");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 580);
		      	cts.drawString("Vï¿½lido durante 10 dï¿½as a partir de la fecha");
		      	cts.endText();
		      	
		    	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 570);
		      	cts.drawString("indicada en este ticket, sï¿½lo para 1 cambio");
		      	cts.endText();
		      	
		    	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 560);
		      	cts.drawString("en anteojos de sol respetando el precio de");
		      	cts.endText();
		      	
		    	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 550);
		      	cts.drawString("compra. Los anteojos deben estar sin uso y en");
		      	cts.endText();
		      	
		    	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 540);
		      	cts.drawString("perfecto estado con su estuche original. No");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 530);
		      	cts.drawString("vï¿½lido para lentes de contacto, armazones ni");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 520);
		      	cts.drawString("anteojos con receta mï¿½dica. Exluye tiendas");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 7);
		      	cts.moveTextPositionByAmount(0, 510);
		      	cts.drawString("outlet , mï¿½dulos Ripley Y La Polar.");
		      	cts.endText();
		      
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 10);
		      	cts.moveTextPositionByAmount(0, 500);
		     	cts.drawString("___________________________________________________");
		      	cts.endText();
		      	
		      	cts.beginText();
		      	cts.setFont(font1, 11);
		      	cts.moveTextPositionByAmount(40, 470);
		      	cts.drawString("TICKET PARA CAMBIO");
		      	cts.endText();
		      	
		      	cts.close();
		      	if(!numero_encargo.equals("")){
			      	document.save("\\\\E:\\TICKET_CAMBIO\\"+numero_encargo.replace("/","_")+".pdf");
			      	
				    //Imprimo Documento
			      	
			      	PDDocument doc = PDDocument.load("\\\\E:\\TICKET_CAMBIO\\"+numero_encargo.replace("/","_")+".pdf");
				
			  		PrinterJob trabajo = PrinterJob.getPrinterJob();
				    	  			      	
			      	PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
				      		
			      	for (PrintService printer : printServices){
			        	  if(printer.getName().equals("termal")){
			        		  trabajo.setPrintService(printer.createPrintJob().getPrintService());
			          	  }			       
			        }    	          
				      	
					doc.silentPrint(trabajo);
					
					doc.close();
						
					//Elimino Archivo
					File file = new File("\\\\E:\\TICKET_CAMBIO\\"+numero_encargo.replace("/","_")+".pdf");
					
					/*if(file.delete()){
						res = "2";
			  			System.out.println("Eliminado");
			  		}else{
			  			res = "1";
			  			System.out.println("El documento no se pudo eliminar.");
			  		}*/
		      	}else{
		      		res = "-1";
		      	}
		     
		
		} catch (IOException e) {
			   res = "-1";
		} catch (COSVisitorException e) {
			   res = "-1";
		} catch (PrinterException e) {
			   res = "-1";
		}		  
		res = "2";	
		return res;			
			
	}
	
	/*
	 *@Title: DTO PROMOPAR 
	 *@Author: LMARIN
	 *@Fecha: 2017-11-23
	 */
	public ProductosBean aplicaDescuentoProductoEsp(ProductosBean producto) {
		
		
		
		//producto.setPrecio((int)(producto.getPrecio()*((double)(100-Integer.parseInt(producto.getPromopar().trim()))/100)  ));
		//producto.setPrecioEspecial(true);
				
		//System.out.println("aplicaDescuentoProductoEsp (20171123)===> "+producto.getPrecio()+" * "+producto.getCantidad());
		
		//producto.setImporte(((int)producto.getPrecio()*(int)producto.getCantidad()));
		
		
		System.out.println("Importe final  ==>"+producto.getImporte());
		return producto;
	}
	
	public int traeDescuentoCupon(ProductosBean productosBean,
			String cupon, String fpago,String local,String agente, String cliente,String encargo) {
		int valor = 0;

		try {
			valor = PosUtilesFacade.traeDescuentoCupon(productosBean,
					cupon, fpago,local,agente,cliente,encargo);
		} catch (Exception e) {
			e.printStackTrace();
			valor = 0;
		}

		return valor;

	}
	
	/*
	 * AUTHOR : LMARIN
	 * FECHA : 20180220
	 */
	public void aplicaDescuentoCupon(ProductosBean prod, String cupon,
			String formaPago,ArrayList<ProductosBean> listaProd,String local,String agente,String cliente,String encargo) {
		int valor = 0;
		double valorp = 0;
		int total_prod = prod.getPrecio() * prod.getCantidad();
		int total = 0;
		int cont_val = 0;
		
		valor = this.traeDescuentoCupon(prod, cupon, formaPago,local,agente,cliente,encargo);
		 
		prod.setPrecio(prod.getPrecio());
		
		for(ProductosBean b : listaProd) {
			if(!b.getTipoFamilia().equals("G") || !b.getTipoFamilia().equals("M") ||!b.getTipoFamilia().equals("C")) {
				cont_val++;
			}
		}
		
		if(valor == 7000 && cont_val == 0){
			if(listaProd.size() == 3) {
				prod.setDescuento(50);
				total = total_prod - (int)Math.round(((total_prod * (50)) / 100));
				prod.setImporte(total);
			}else if(listaProd.size() == 1) {
				prod.setDescuento(30);
				total = total_prod - (int)Math.round(((total_prod * (30)) / 100));
				prod.setImporte(total);
			}
		}else{
			if(String.valueOf(valor).length() >= 3 ){
				
				valorp = ((double)(valor * 100)/(double)total_prod);
				if((int)valorp > 100) {
					valorp = 100;
					total = 0;
				}else {
					total = total_prod - valor;
				}
				System.out.println(valorp);
				
				prod.setDescuento(valorp);
				prod.setImporte(total);
				
			}else {
				
				prod.setDescuento(valor);
				total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
				prod.setImporte(total);
				
			}
		}
		
	}
	
	public double traeDescuentoCombo(ProductosBean productosBean,String local,String agente, String cliente) {
		double valor = 0;

		try {
			valor = PosUtilesFacade.traeDescuentoCombo(productosBean,local,agente,cliente);
		} catch (Exception e) {
			e.printStackTrace();
			valor = 0;
		}

		return valor;

	}
	
	
	/*
	 * AUTHOR : LMARIN
	 * FECHA : 20180227
	 */
	public void aplicaDescuentoCombo(ProductosBean prod,ArrayList<ProductosBean> listaProd,String local,String agente,String cliente) {
		double valor = 0;
		int total = 0;
		int total_prod = prod.getPrecio() * prod.getCantidad();
		
		valor = this.traeDescuentoCombo(prod, local,agente,cliente);
		
		prod.setPrecio(prod.getPrecio());
		
		prod.setDescuento(valor);
		total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
	
		System.out.println("valor ==>"+valor+"<==> total ==>"+total);
		prod.setImporte(total);
			
	}
	
	/*
	 * AUTHOR : LMARIN
	 * FECHA : 20180325
	 */
	public void aplicaDescuentoPromoPar(ProductosBean prod,ArrayList<ProductosBean> listaProd,int indice,String grupo,String index,String dto) {
		double valor = 0;
		int total = 0;


		valor = Integer.parseInt(dto.trim());
		
		int total_prod = prod.getPrecio() * prod.getCantidad();
		System.out.println("MULTICACHO ========>"+indice+"<=====>"+index);
		if(grupo.equals("0")) {
			
			if(prod.getGrupo().equals(grupo) && indice == Integer.parseInt(index)) {
				prod.setDescuento(valor);
				total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
				prod.setImporte(total);
			}
			
		}else {
			if(prod.getGrupo().equals(grupo)) {
				prod.setDescuento(valor);
				total = total_prod - (int)Math.round(((total_prod * (valor)) / 100));
				prod.setImporte(total);
			}
			
		}	
	}
	
	
	/*
	 * AUTHOR : LMARIN
	 * FECHA : 20190204
	 * 
	 */
	public void aplicaDescuentoSanValentin(ProductosBean prod,ArrayList<ProductosBean> listaProd,String local) throws Exception {
		
		int dto = 0,total=0, dtobd= 0;
		String cadena = "";
		
		if(local.contains("T")){
			System.out.println("SAN CALENTIN ==>  TIPOFAMILIA ==> "+prod.getTipoFamilia()+" FAMILIA==>"+prod.getFamilia()+" local ==>"+local+" SUBFAM==>"+prod.getSubFamilia());
			if((prod.getTipoFamilia().equals("M") || prod.getTipoFamilia().equals("G")) && prod.getDescuento() <= 0){
				if(prod.getImporte() >= 89900 && prod.getImporte() <= 109900) {
					dto = 15;
				}else if(prod.getImporte() >= 119900 && prod.getImporte() <= 139900) {
					dto = 20;
				}else if(prod.getImporte() >= 149900) {
					dto = 25;
				}
			
			}
		}else if(local.contains("V")) {
			if(prod.getFamilia().equals("SOC") && prod.getDescuento() <= 0 && (prod.getSubFamilia().equals("ANT") || prod.getSubFamilia().equals("OKY") || prod.getSubFamilia().equals("RBN") || prod.getSubFamilia().equals("VOG"))){
					dto = 25;
			}
		}else if(local.contains("R") || local.contains("S")) {
			//System.out.println("R O S ==> "+prod.getCod_barra()+" "+prod.getCod_articulo());
			//cadena = local.contains("R") ? "RBN":"SGH";
			dtobd = PosUtilesFacade.articulosPromoSanValentin(prod.getCod_barra(), local);
			
		}
		
		prod.setDescuento(dto);
		total = prod.getImporte() - (int)Math.round(((prod.getImporte() * (dto)) / 100));
		prod.setImporte(total-dtobd);
	}
	
	
	
	/**
	 *@AUTHOR :LMARIN
	 *@DESC: Metodo que envia la venta en tiempo real
	 *@DATE: 20180328
	 */
    public String enviaVentaAeropuerto() {
	   
	    return null;
    }
   
    
    /**
   	 *@throws Exception 
     * @AUTHOR :LMARIN
   	 *@DESC: Metodo que genera el XML que consume el Aeropuerto
   	 *@DATE: 20180328
   	 */
    public void generaXMLAeropuerto(SeleccionPagoForm spago,String foliocl,Session session) throws Exception {
       	
	       	DocumentBuilderFactory doc_fac = DocumentBuilderFactory.newInstance();
	       	DocumentBuilder doc_buil = doc_fac.newDocumentBuilder();
	       	
	   		String [] tienda = spago.getBoleta_tienda().split("-");
	
	   		ArrayList<TiendaBean> tb = PosUtilesFacade.traeDatosTienda(tienda[0].trim());
	   		ArrayList<ProductosBean> listProductos = (ArrayList<ProductosBean>)session.getAttribute(Constantes.STRING_LISTA_PRODUCTOS);				
	   		
	   		
	       	Utils util = new Utils();
	       	Properties prop = util.leeConfiguracion();
	       	String ruta_abs= "",ruta_abs_res ="",rzns="OPTICAS GMO CHILE S.A SUNGLASS HUT",bupla="",cdgsii="",terminal="";
	       			
	       	//RUTA XML
	       	if(tienda[0].trim().equals("S064")) {//S064
	       		ruta_abs = prop.getProperty("liberacion.rutaXml3").trim();
	       		ruta_abs_res = prop.getProperty("liberacion.rutaXml4").trim();
	       		bupla = "0434";
	       		terminal ="434";
	       		cdgsii ="081160165";
	       	}else if(tienda[0].trim().equals("S035")){//S035
	       		ruta_abs = prop.getProperty("liberacion.rutaXml1").trim();
	       		ruta_abs_res = prop.getProperty("liberacion.rutaXml2").trim();
	       		bupla = "0394";
	       		terminal ="394";
	       		cdgsii ="079085778";
	       	}else if(tienda[0].trim().equals("S070")){//S070
	       		ruta_abs = prop.getProperty("liberacion.rutaXml5").trim();
	       		ruta_abs_res = prop.getProperty("liberacion.rutaXml6").trim();
	       		bupla = "0215";
	       		terminal ="001";
	       		cdgsii ="519000";
	       	}
	       	
	       	String rutaXml1 = ruta_abs;
	       	String rutaXml2 = ruta_abs_res;
	       	
	   		//PAGOS
	   		
	   		int fpago1  = (!spago.getBoleta_fpago_1().equals("") && spago.getBoleta_fpago_1() != null) ?retnumero(spago.getBoleta_fpago_1()):0;
	   		int fpago2  = (!spago.getBoleta_fpago_2().equals("") && spago.getBoleta_fpago_2() != null) ?retnumero(spago.getBoleta_fpago_2()):0;
	   		int fpago3  = (!spago.getBoleta_fpago_3().equals("") && spago.getBoleta_fpago_3() != null) ?retnumero(spago.getBoleta_fpago_3()):0;
	   		int fpago4  = (!spago.getBoleta_fpago_4().equals("") && spago.getBoleta_fpago_4() != null) ?retnumero(spago.getBoleta_fpago_4()):0;
	
	   		// <!--> TOTAL CANCELADO -->
	   		int total = fpago1 + fpago2 + fpago3 + fpago4; 
	   		int totalsd = 0;
	   		int siva = (int)(total/1.19);
	   		int iva = (int)(total * 0.19);
	   	
	   		//Calculo total sin DTO
	   		for(ProductosBean p : listProductos) {
	   			totalsd += (p.getPrecio() * p.getCantidad());
	   		}
	   		System.out.println("TOTAL SD =====> "+totalsd);
	   		
	   		int dtomnt = totalsd - total;
	   		double por = 100 - ((double)((total * 100)/totalsd));		
	   		
	   		
	   		String montop = convertirLetras(total).toUpperCase()+" PESOS.-";
	   		String fecha [] = spago.getFecha().split("/");
	   		String fecha1 = (String.valueOf(fecha[0]).length() <= 1)?"0"+String.valueOf(fecha[0])+"."+fecha[1].toString()+"."+fecha[2].toString():String.valueOf(fecha[0])+"."+fecha[1].toString()+"."+fecha[2].toString(); 
	   		String fecha2 = (String.valueOf((Integer.parseInt(fecha[0])+1)).length() <= 1)?"0"+String.valueOf((Integer.parseInt(fecha[0])+1))+"."+fecha[1].toString()+"."+fecha[2].toString():String.valueOf((Integer.parseInt(fecha[0])+1))+"."+fecha[1].toString()+"."+fecha[2].toString(); 
	   		String hora [] = spago.getBoleta_hora().split(":");
	   		String dir_cli =  (spago.getDireccion() != null &&   !spago.getDireccion().trim().equals("-"))? spago.getDireccion() : tb.get(0).getDireccion();
	   		String locali_cli = (spago.getProvincia_descripcion()!= null  && !spago.getDireccion().trim().equals(""))? spago.getProvincia_descripcion() : tb.get(0).getLocalidad();
	
	   		int a = 1;
	   		
	   		String xmlres ="<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	   		
	   		xmlres +="<TRANSACCION>";
	   		xmlres +="  <DOCUMENTO>";
	   		xmlres +="    <ENCABEZADO>";
	   		xmlres +="      <IDDOC>";
	   		xmlres +="        <BLART>BO</BLART>";
	   		xmlres +="        <ZNUMD>"+foliocl+"</ZNUMD>";
	   		xmlres +="        <ZARRI> </ZARRI>";
	   		xmlres +="        <WAERS>CLP</WAERS>";
	   		xmlres +="        <BLDAT>"+fecha1+"</BLDAT>";
	   		xmlres +="        <ZHORA>"+spago.getBoleta_hora()+"</ZHORA>";
	   		xmlres +="        <BUDAT>"+fecha2+"</BUDAT>";
	   		xmlres +="        <BUPLA>"+bupla+"</BUPLA>";
	   		xmlres +="        <ZSECT> </ZSECT>";
	   		xmlres +="        <TVORG> </TVORG>";
	   		xmlres +="        <MWSKZ>C0</MWSKZ>";
	   		xmlres +="        <VKONT> </VKONT>";
	   		xmlres +="      </IDDOC>";
	   		xmlres +="      <EMISOR>";
	   		xmlres +="        <RUTEMISOR>96891370-0</RUTEMISOR>";
	   		xmlres +="        <RZNSOCEMISOR>OPTICAS GMO</RZNSOCEMISOR>";
	   		xmlres +="        <GIROEMISOR>COMERCIALIZACION,IMPORTACION</GIROEMISOR>";
	   		xmlres +="        <CDGSIISUCUR>"+cdgsii.substring(0,5)+"</CDGSIISUCUR>";
	   		xmlres +="        <DIRORIGEN>"+tb.get(0).getDireccion().substring(0,14)+"</DIRORIGEN>";
	   		xmlres +="        <CMNAORIGEN>"+tb.get(0).getLocalidad()+"</CMNAORIGEN>";
	   		xmlres +="        <CIUDADORIGEN>SANTIAGO</CIUDADORIGEN>";
	   		xmlres +="      </EMISOR>";
	   		xmlres +="      <RECEPTOR>";
	   		xmlres +="        <RUTRECEP>"+spago.getNif()+"</RUTRECEP>";
	   		xmlres +="        <RZNSOCRECEP>"+spago.getNombre_cliente()+"</RZNSOCRECEP>";
	   		xmlres +="        <CONTACTO> </CONTACTO>";
	   		xmlres +="        <DIRRECEP> </DIRRECEP>";
	   		xmlres +="        <CMNARECEP> </CMNARECEP>";
	   		xmlres +="        <CIUDADRECEP> </CIUDADRECEP>";
	   		xmlres +="      </RECEPTOR>";
	   		xmlres +="    </ENCABEZADO>";
	   		//xmlres +="  <DETALLE>";
	   		
	   		for(ProductosBean p:listProductos) {
	   			
	   				int tot= (int)((p.getPrecio() * p.getCantidad())/1.19);
	   				String dto_prod = (!p.getDto().equals("") && p.getDto() != null)? p.getDto():"0";
	   				String descrip = (String) ((p.getDescripcion().length() > 24) ? p.getDescripcion().substring(0,24):p.getDescripcion());
	   				xmlres +="  <DETALLE>";
	   				xmlres +="      <NROLINDET>"+a+"</NROLINDET>";
	   				xmlres +="      <CDGITEM>";
	   				xmlres +="        <ZCANT>"+p.getCantidad()+"</ZCANT>";
	   				xmlres +="        <ZCODP>2205283</ZCODP>";
	   				xmlres +="        <ZCATE>Optica</ZCATE>";
	   				xmlres +="        <ZSUBC>Optica</ZSUBC>";
	   				xmlres +="        <ZSEGM>Lentes de sol</ZSEGM>";
	   				xmlres +="        <ZSUBS>"+descrip+"</ZSUBS>";
	   				xmlres +="        <BETRW>"+String.valueOf(tot)+"</BETRW>";
	   				xmlres +="        <DESCUENTOMONTO>"+dto_prod+"</DESCUENTOMONTO>";
	   				xmlres +="        <ZIMAD>0</ZIMAD>";
	   				xmlres +="      </CDGITEM>";
	   				xmlres +="  </DETALLE>";
	   				a++;
	   		}		
	   		//xmlres +="    </DETALLE>";
	   		xmlres +="    <TOTALES>";
	   		xmlres +="      <MNTNETO>"+String.valueOf(siva)+"</MNTNETO>";
	   		xmlres +="      <TASAIVA>19</TASAIVA>";
	   		xmlres +="      <IVA>"+iva+"</IVA>";
	   		xmlres +="      <MNTTOTAL>"+total+"</MNTTOTAL>";
	   		xmlres +="    </TOTALES>";
	   		xmlres +="    <DSCRCGGLOBAL>";
	   		xmlres +="      <NROLINDR>1</NROLINDR>";
	   		xmlres +="      <TPOMOV>D</TPOMOV>";
	   		xmlres +="      <GLOSADR> </GLOSADR>";
	   		xmlres +="      <TPOVALOR>%</TPOVALOR>";
	   		xmlres +="      <VALORDR>"+dtomnt+"</VALORDR>";
	   		xmlres +="    </DSCRCGGLOBAL>";
	   		xmlres +="    <PARAMETROS>";
	   		xmlres +="      <MONTO_ESCRITO> </MONTO_ESCRITO>";
	   		xmlres +="      <REFER> </REFER>";
	   		xmlres +="      <NETO>"+siva+"</NETO>";
	   		xmlres +="		<ESPECIFICACIONES></ESPECIFICACIONES>"; 
	   		xmlres +="    </PARAMETROS>";
	   		xmlres +="  </DOCUMENTO>";
	   		xmlres +="</TRANSACCION>";
	
	   		System.out.println("IMPRIMO XML ==>"+xmlres); 
	   		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	   		DocumentBuilder builder;  
	   		try {  
	   		    builder = factory.newDocumentBuilder();  
	   		    Document doc = builder.parse(new InputSource(new StringReader(xmlres)));  
	
	   			String nombreArchivo = "TRX"+terminal+bupla+fecha[2].substring(2,4)+fecha[1]+fecha[0]+hora[0]+hora[1]+foliocl+".xml";
	   			TransformerFactory transformerFactory = TransformerFactory.newInstance();
	   			Transformer transformer = transformerFactory.newTransformer();
	   			
	   			DOMSource source1 = new DOMSource(doc);
	   			StreamResult result1 = new StreamResult(new File(rutaXml1+nombreArchivo));
	
	   			DOMSource source2 = new DOMSource(doc);
	   			StreamResult result2 = new StreamResult(new File(rutaXml2+nombreArchivo));
	   						
	   			transformer.transform(source1, result1);
	   			transformer.transform(source2, result2);	
	   		    
	   		} catch (Exception e) {  
	   		    System.out.println("ERROR XML ==>"+e.getMessage().toString());  
	   		} 
	   		
    }
    
    private String unidad(int numero){
		
		switch (numero){
		case 9:
				num = "nueve";
				break;
		case 8:
				num = "ocho";
				break;
		case 7:
				num = "siete";
				break;
		case 6:
				num = "seis";
				break;
		case 5:
				num = "cinco";
				break;
		case 4:
				num = "cuatro";
				break;
		case 3:
				num = "tres";
				break;
		case 2:
				num = "dos";
				break;
		case 1:
				if (flag == 0)
					num = "uno";
				else 
					num = "un";
				break;
		case 0:
				num = "";
				break;
		}
		return num;
	}
	
	private String decena(int numero){
	
		if (numero >= 90 && numero <= 99)
		{
			num_letra = "noventa ";
			if (numero > 90)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 90));
		}
		else if (numero >= 80 && numero <= 89)
		{
			num_letra = "ochenta ";
			if (numero > 80)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 80));
		}
		else if (numero >= 70 && numero <= 79)
		{
			num_letra = "setenta ";
			if (numero > 70)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 70));
		}
		else if (numero >= 60 && numero <= 69)
		{
			num_letra = "sesenta ";
			if (numero > 60)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 60));
		}
		else if (numero >= 50 && numero <= 59)
		{
			num_letra = "cincuenta ";
			if (numero > 50)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 50));
		}
		else if (numero >= 40 && numero <= 49)
		{
			num_letra = "cuarenta ";
			if (numero > 40)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 40));
		}
		else if (numero >= 30 && numero <= 39)
		{
			num_letra = "treinta ";
			if (numero > 30)
				num_letra = num_letra.concat("y ").concat(unidad(numero - 30));
		}
		else if (numero >= 20 && numero <= 29)
		{
			if (numero == 20)
				num_letra = "veinte ";
			else
				num_letra = "veinti".concat(unidad(numero - 20));
		}
		else if (numero >= 10 && numero <= 19)
		{
			switch (numero){
			case 10:

				num_letra = "diez ";
				break;

			case 11:

				num_letra = "once ";
				break;

			case 12:

				num_letra = "doce ";
				break;

			case 13:

				num_letra = "trece ";
				break;

			case 14:

				num_letra = "catorce ";
				break;

			case 15:
			
				num_letra = "quince ";
				break;
			
			case 16:
			
				num_letra = "dieciseis ";
				break;
			
			case 17:
			
				num_letra = "diecisiete ";
				break;
			
			case 18:
			
				num_letra = "dieciocho ";
				break;
			
			case 19:
			
				num_letra = "diecinueve ";
				break;
			
			}	
		}
		else
			num_letra = unidad(numero);

	return num_letra;
	}	

	private String centena(int numero){
		if (numero >= 100)
		{
			if (numero >= 900 && numero <= 999)
			{
				num_letra = "novecientos ";
				if (numero > 900)
					num_letra = num_letra.concat(decena(numero - 900));
			}
			else if (numero >= 800 && numero <= 899)
			{
				num_letra = "ochocientos ";
				if (numero > 800)
					num_letra = num_letra.concat(decena(numero - 800));
			}
			else if (numero >= 700 && numero <= 799)
			{
				num_letra = "setecientos ";
				if (numero > 700)
					num_letra = num_letra.concat(decena(numero - 700));
			}
			else if (numero >= 600 && numero <= 699)
			{
				num_letra = "seiscientos ";
				if (numero > 600)
					num_letra = num_letra.concat(decena(numero - 600));
			}
			else if (numero >= 500 && numero <= 599)
			{
				num_letra = "quinientos ";
				if (numero > 500)
					num_letra = num_letra.concat(decena(numero - 500));
			}
			else if (numero >= 400 && numero <= 499)
			{
				num_letra = "cuatrocientos ";
				if (numero > 400)
					num_letra = num_letra.concat(decena(numero - 400));
			}
			else if (numero >= 300 && numero <= 399)
			{
				num_letra = "trescientos ";
				if (numero > 300)
					num_letra = num_letra.concat(decena(numero - 300));
			}
			else if (numero >= 200 && numero <= 299)
			{
				num_letra = "doscientos ";
				if (numero > 200)
					num_letra = num_letra.concat(decena(numero - 200));
			}
			else if (numero >= 100 && numero <= 199)
			{
				if (numero == 100)
					num_letra = "cien ";
				else
					num_letra = "ciento ".concat(decena(numero - 100));
			}
		}
		else
			num_letra = decena(numero);
		
		return num_letra;	
	}	

	private String miles(int numero){
		if (numero >= 1000 && numero <2000){
			num_letram = ("mil ").concat(centena(numero%1000));
		}
		if (numero >= 2000 && numero <10000){
			flag=1;
			num_letram = unidad(numero/1000).concat(" mil ").concat(centena(numero%1000));
		}
		if (numero < 1000)
			num_letram = centena(numero);
		
		return num_letram;
	}		

	private String decmiles(int numero){
		if (numero == 10000)
			num_letradm = "diez mil";
		if (numero > 10000 && numero <20000){
			flag=1;
			num_letradm = decena(numero/1000).concat("mil ").concat(centena(numero%1000));		
		}
		if (numero >= 20000 && numero <100000){
			flag=1;
			num_letradm = decena(numero/1000).concat(" mil ").concat(miles(numero%1000));		
		}
		
		
		if (numero < 10000)
			num_letradm = miles(numero);
		
		return num_letradm;
	}		

	private String cienmiles(int numero){
		if (numero == 100000)
			num_letracm = "cien mil";
		if (numero >= 100000 && numero <1000000){
			flag=1;
			num_letracm = centena(numero/1000).concat(" mil ").concat(centena(numero%1000));		
		}
		if (numero < 100000)
			num_letracm = decmiles(numero);
		return num_letracm;
	}		

	private String millon(int numero){
		if (numero >= 1000000 && numero <2000000){
			flag=1;
			num_letramm = ("Un millon ").concat(cienmiles(numero%1000000));
		}
		if (numero >= 2000000 && numero <10000000){
			flag=1;
			num_letramm = unidad(numero/1000000).concat(" millones ").concat(cienmiles(numero%1000000));
		}
		if (numero < 1000000)
			num_letramm = cienmiles(numero);
		
		return num_letramm;
	}		
	
	private String decmillon(int numero){
		if (numero == 10000000)
			num_letradmm = "diez millones";
		if (numero > 10000000 && numero <20000000){
			flag=1;
			num_letradmm = decena(numero/1000000).concat("millones ").concat(cienmiles(numero%1000000));		
		}
		if (numero >= 20000000 && numero <100000000){
			flag=1;
			num_letradmm = decena(numero/1000000).concat(" milllones ").concat(millon(numero%1000000));		
		}
		
		
		if (numero < 10000000)
			num_letradmm = millon(numero);
		
		return num_letradmm;
	}		

	public String convertirLetras(int numero){
		num_letras = decmillon(numero);
		return num_letras;
	} 	
   
	//Cambios para operar TransBank
	
	public String retornaIp(Session request) {
		 String remoteAddr = "";
		 
		 InetAddress addr;
		 String ipAddress = "";
				
		//X-Real-IP 
		//X-FORWARDED-FOR
		//remoteAddr = Executions.getCurrent().getHeader("X-Real-IP");		
		//remoteAddr = "10.216.3.13";
		 
		 try {
			addr 	  = InetAddress.getLocalHost();
			ipAddress = addr.getHostAddress();
			remoteAddr= ipAddress;
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		}

        /*if (remoteAddr != null) {
            //remoteAddr = request.getHeader("X-FORWARDED-FOR"); //Buscar sustituto de getHeadr
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }*/

        return remoteAddr;		
	}
	
	public Map<String, String> getRequestHeadersInMap(Session request) {

	        Map<String, String> result = new HashMap<>();

	        /*Enumeration headerNames = request.getHeaderNames(); //buscar equivalente
	        
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = request.getHeader(key);
	            System.out.println(key+"<==>"+value);
	            result.put(key, value);
	        }*/

	        return result;
	 }

	public boolean creaFicheroTbk(int monto,String boleta) {
		boolean ret = false;
		String nombre_archivo = boleta+".txt";
		String msjtbk = monto+"|"+boleta;
		System.out.println(nombre_archivo+"<===>"+msjtbk);
		try {
			File file = new File("C:\\ct_transbank\\entrada\\"+nombre_archivo);
			ret = file.createNewFile();
			if(file.exists()) {
				FileWriter fw = new FileWriter(file,true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(msjtbk);
				fw.flush();
				pw.flush();
				
				fw.close();
				pw.close();
			
			}
		} catch (IOException e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}

	
	
	
}
