package cl.gmo.pos.venta.web.helper;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.PresupuestosBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.facade.PosGraduacionesFacade;
import cl.gmo.pos.venta.web.facade.PosPresupuestoFacade;
import cl.gmo.pos.venta.web.facade.PosProductosFacade;
import cl.gmo.pos.venta.web.forms.PresupuestoForm;
import cl.gmo.pos.venta.web.forms.VentaPedidoForm;


public class PresupuestoHelper extends VentaPedidoHelper{
	Logger log = Logger.getLogger( this.getClass() );
	public void traeDatosFormulario(PresupuestoForm formulario,
			HttpSession session) {
		log.info("PresupuestoHelper:traeDatosFormulario inicio");
		String local = session.getAttribute(Constantes.STRING_SUCURSAL).toString();
		formulario.setCodigo_suc(PosPresupuestoFacade.traeCodigoSuc(local));
		
		//DATOS GENERICOS DESDE CONFIGURACION Y SESSION
		
		PresupuestosBean  presupuesto = new PresupuestosBean();
		presupuesto = PosPresupuestoFacade.traeGenericos(local);
		formulario.setIdioma(presupuesto.getIdioma());
		formulario.setDivisa(presupuesto.getDivisa());
		formulario.setForma_pago(presupuesto.getForma_pago());
		formulario.setCambio(presupuesto.getCambio());
		formulario.setPorcentaje_descuento_max(presupuesto.getPorcentaje_descuento_max());
		if (null == presupuesto.getAgente() || Constantes.STRING_BLANCO.equals(formulario.getAgente()) || presupuesto.getAgente().equals(Constantes.STRING_CERO)) {
			String agente = session.getAttribute(Constantes.STRING_USUARIO).toString();
			formulario.setAgente(agente);
		}
		else
		{
			formulario.setAgente(presupuesto.getAgente());
		}
		
		
	}
	
	public ArrayList<ProductosBean> eliminarProductos(String addProducto,
			ArrayList<ProductosBean> listaProductos) {
		log.info("PresupuestoHelper:eliminarProductos inicio");
		listaProductos.remove(Integer.parseInt(addProducto));
		
		return listaProductos;
	}

	public ArrayList<ProductosBean> actualizaProductos(
			PresupuestoForm formulario, String addProducto,
			int cantidad, ArrayList<ProductosBean> listaProductos,
			String local, String tipo_busqueda, String cod_barra, GraduacionesBean graduacion, String ojo, String tipo, HttpSession session) {
		log.info("PresupuestoHelper:actualizaProductos inicio");
		
		//REALIZA TRASPOSICION
		try{
			boolean cristal_validado = true;
			
			GraduacionesHelper helper_graduacion = new GraduacionesHelper();
			if (Constantes.STRING_LEJOS_OPT.equals(tipo)) {
				helper_graduacion.realiza_Trasposicion(graduacion);
			}
			if (Constantes.STRING_CERCA_OPT.equals(tipo)) {
				helper_graduacion.realiza_Trasposicion_cerca(graduacion);
			}
	
			ProductosBean prod = new ProductosBean();
			prod  = PosProductosFacade.traeProducto(addProducto, cantidad, local, tipo_busqueda, cod_barra);
			prod.setCod_pedvtcb(formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo());
			
			//para lentillas
			if (Constantes.STRING_L.equals(prod.getTipoFamilia())) {
				session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, prod);
				ContactologiaBean contactologia = new ContactologiaBean();
				contactologia = formulario.getGraduacion_lentilla();
				
					if (ojo.equals(Constantes.STRING_DERECHO)) {
						prod.setOjo(ojo);
						prod.setEsfera(contactologia.getOdesfera());
						prod.setEje(contactologia.getOdeje());
						prod.setCilindro(contactologia.getOdcilindro());
					}
					else if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
						prod.setOjo(ojo);
						prod.setEsfera(contactologia.getOiesfera());
						prod.setEje(contactologia.getOieje());
						prod.setCilindro(contactologia.getOicilindro());
					}
					else
					{
						prod.setOjo(Constantes.STRING_BLANCO);
					}
					prod.setGrad_fecha(contactologia.getFecha());
					prod.setGrad_numero(contactologia.getNumero());
					prod.setFecha_graduacion(contactologia.getFecha());
					prod.setNumero_graduacion(contactologia.getNumero());
					
					if (this.valida_existe_error_cristal(prod, "Lejos", ojo)) {
						cristal_validado = false;
					}
					
					if (tipo.equals(Constantes.STRING_LEJOS_OPT)) {
						prod.setTipo(Constantes.STRING_L);
					}
					else if (tipo.equals(Constantes.STRING_CERCA_OPT)) {
						prod.setTipo(Constantes.STRING_C);
					}
					else 
					{
						prod.setTipo(tipo);
					}
					
			}
			//para cristales
			
			if (prod.getTipoFamilia().equals(Constantes.STRING_C)) {
				
				session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, prod);
				if (ojo.equals(Constantes.STRING_DERECHO)) {
					prod.setEsfera(graduacion.getOD_esfera_traspuesto());
					prod.setEje(graduacion.getOD_eje_traspuesto());
					prod.setCilindro(graduacion.getOD_cilindro_traspuesto());
					prod.setOjo(ojo);
				}
				if (ojo.equals(Constantes.STRING_IZQUIERDO)) {
					prod.setEsfera(graduacion.getOI_esfera_traspuesto());
					prod.setEje(graduacion.getOI_eje_traspuesto());
					prod.setCilindro(graduacion.getOI_cilindro_traspuesto());
					prod.setOjo(ojo);
				}
				prod.setGrad_fecha(graduacion.getFecha());
				prod.setGrad_numero(graduacion.getNumero());
				prod.setFecha_graduacion(graduacion.getFecha());
				prod.setNumero_graduacion(graduacion.getNumero());
				prod.setTiene_grupo(Constantes.STRING_TRUE);
				prod.setTipo(tipo);
				
				
				if (this.valida_existe_error_cristal(prod, tipo, ojo)) {
					cristal_validado = false;
				}
				
				/*
				if (prod.getTipoFamilia().equals("C")) {
					prod.setTiene_suple("true");
				}
				*/
				//VERIFICA SI DEBE TENER SUPLEMENTOS
				
				
				ArrayList<SuplementopedidoBean> suplementos = new ArrayList<SuplementopedidoBean>();
				/*  
				try {
					suplementos = PosUtilesFacade.traeSuplementosObligatorios(cod_barra);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				if (null != suplementos && suplementos.size() != 0)
				{
					 formulario.setEstado(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO);
					 session.setAttribute(Constantes.STRING_PRODUCTO, prod);
				}
				*/
				
			}
			if (prod.getTipoFamilia().equals(Constantes.STRING_C) || prod.getTipoFamilia().equals(Constantes.STRING_M)) 
			{
				if (prod.getCod_barra().equals(Constantes.STRING_ACTION_ARCLI) || prod.getCod_barra().equals(Constantes.STRING_ACTION_CCLI)) {
					
					prod.setDescripcion_manual(Constantes.STRING_FALSE);
					
					 //HABILITA DESCRIPCION MANUAL
					
					prod.setDescripcion_manual(Constantes.STRING_TRUE);    
					//formulario.setEstado(Constantes.STRING_PRODUCTO_CLIENTE);
				}
				else
				{
					prod.setDescripcion_manual(Constantes.STRING_FALSE);
				}
			}
			
				if(null != prod){
					if (cristal_validado) 
					{
			  			if (null == listaProductos)
			  			{
			  				listaProductos = new ArrayList<ProductosBean>();
			  				prod.setIndice(0);
			  				listaProductos.add(0,prod);
			  			}
			  			else
			  			{
			  				prod.setIndice(listaProductos.size());
			  				listaProductos.add(listaProductos.size(),prod);
			  			}
						
						if (!("".equals(formulario.getConvenio())))
						{
							this.actualizaProductosPorConvenio(formulario,local);
						}
					}
					else
					{
						if (null == listaProductos)
			  			{
							listaProductos = new ArrayList<ProductosBean>();
			  			}
						formulario.setEstado(Constantes.STRING_ERROR);
						formulario.setError(Constantes.STRING_ERROR_CRISTAL_INCOMPLETO);
					}
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return listaProductos;
	}
	public void traeUltimaGraduacionCliente(PresupuestoForm formulario) {
		log.info("PresupuestoHelper:traeUltimaGraduacionCliente inicio");
		GraduacionesBean graduacionBean = new GraduacionesBean();
		try {
			graduacionBean = PosGraduacionesFacade.traeUltimaGraduacionCliente(formulario.getCliente().toString());
			formulario.setGraduacion(graduacionBean);
		} catch (Exception e) {
			log.error("PresupuestoHelper:traeUltimaGraduacionCliente error catch",e);
		}
		
	}

	public void agregaSuplementosProducto(PresupuestoForm formulario,
			HttpSession session) {
		log.info("PresupuestoHelper:agregaSuplementosProducto inicio");
		ProductosBean prod = (ProductosBean)session.getAttribute(Constantes.STRING_PRODUCTO);
		session.setAttribute(Constantes.STRING_PRODUCTO, Constantes.STRING_BLANCO);
		ArrayList<SuplementopedidoBean> lista_suplementos = (ArrayList<SuplementopedidoBean>)session.getAttribute(Constantes.STRING_LISTA_SUPLEMENTOS);
		prod.setListaSuplementos(lista_suplementos);
		formulario.getListaProductos().set(prod.getIndice(), prod);
		
	}

	public void verSuplementosProducto(PresupuestoForm formulario,
			HttpSession session) {
		log.info("PresupuestoHelper:verSuplementosProducto inicio");
		ProductosBean prod = formulario.getListaProductos().get(Integer.parseInt(formulario.getAddProducto()));
		prod.setIndice(Integer.parseInt(formulario.getAddProducto()));
		
		formulario.setEstado(Constantes.STRING_PRODUCTO_CON_SUPLEMENTO);
		session.setAttribute(Constantes.STRING_PRODUCTO, prod);
		
	}

	public void tarifica_Presupuesto(PresupuestoForm formulario) {
		log.info("PresupuestoHelper:tarifica_Presupuesto inicio");
		ArrayList<ProductosBean> lista_productos = formulario.getListaProductos();
		
		double sub_total = 0;
		
		if (lista_productos.size() != 0)
		{
			for (ProductosBean prod : lista_productos) {
				log.info("PresupuestoHelper:tarifica_Presupuesto entrando ciclo for");
				int precio = prod.getPrecio() * prod.getCantidad();
				if (prod.getDescuento() > Constantes.INT_CERO) 
				{
					double cant = prod.getDescuento();
					double diferencia = cant / 100;
					double valor = Math.rint(precio * diferencia);
					double saldo = precio - valor;
					int total_linea =  (int) Math.floor(saldo);
					prod.setImporte(total_linea);
				}
				else
				{
					prod.setImporte(precio);
				}
				sub_total += prod.getImporte();
			}
			
			formulario.setSubTotal(sub_total);
			if (sub_total == Constantes.INT_CERO)
			{
				formulario.setDtcoPorcentaje(Constantes.INT_CERO);
				formulario.setDescuento(Constantes.INT_CERO);
				formulario.setTotal(Constantes.INT_CERO);
				formulario.setTotalPendiante(Constantes.INT_CERO);
				formulario.setAnticipo(Constantes.INT_CERO);
				formulario.setSubTotal(Constantes.INT_CERO);
			}
			else
			{
				
				double cant = formulario.getDtcoPorcentaje();
				double diferencia = cant/100;
				double subtotal = formulario.getSubTotal();
				double valor = subtotal * diferencia;
				formulario.setDescuento((int) Math.floor(valor));
				formulario.setTotal(formulario.getSubTotal() - formulario.getDescuento());
			}
			
		}
		else
		{
			formulario.setSubTotal(Constantes.INT_CERO);
			formulario.setDescuento(Constantes.INT_CERO);
			formulario.setDtcoPorcentaje(Constantes.INT_CERO);
			formulario.setTotal(Constantes.INT_CERO);
		}
		
		
		
	}

	public boolean ingresaPresupuesto(PresupuestoForm formulario, String local) {
		log.info("PresupuestoHelper:ingresaPresupuesto inicio");
		PresupuestosBean presupuesto = new PresupuestosBean();
		if(formulario.getCodigo().equals(Constantes.STRING_BLANCO))
		{
			presupuesto.setNumero(0);
			presupuesto.setCodigo(formulario.getCodigo_suc() + Constantes.STRING_SLASH);
		}
		else
		{
			presupuesto.setNumero(Integer.parseInt(formulario.getCodigo().toString()));
			presupuesto.setCodigo(formulario.getCodigo_suc() + Constantes.STRING_SLASH + formulario.getCodigo());
		}
		
		if (formulario.getCliente().equals(Constantes.STRING_BLANCO)) {
			formulario.setCliente(Constantes.STRING_CERO);
		}
		presupuesto.setSerie(formulario.getCodigo_suc());
		presupuesto.setAgente(formulario.getAgente());
		presupuesto.setDivisa(formulario.getDivisa());
		presupuesto.setIdioma(formulario.getIdioma());
		presupuesto.setFecha(formulario.getFecha());
		presupuesto.setCambio(formulario.getCambio());
		presupuesto.setNotas(formulario.getNota());
		presupuesto.setForma_pago(formulario.getForma_pago());
		presupuesto.setConvenio(formulario.getConvenio());
		presupuesto.setDcto(formulario.getDtcoPorcentaje());
		if(null == formulario.getCliente() || formulario.getCliente().equals(""))
		{
			presupuesto.setCliente(0);
		}
		else
		{
			presupuesto.setCliente(Integer.parseInt(formulario.getCliente()));
		}
		try {
			boolean estado = PosPresupuestoFacade.insertaPresupuesto(presupuesto,local);
			if (Constantes.STRING_NUEVO.equals(formulario.getFlujo())) {
				formulario.setCodigo(this.formato_Numero_Ticket(presupuesto.getNumero()));
			}
			return estado;
		} catch (Exception e) {
			log.error("PresupuestoHelper:ingresaPresupuesto error catch",e);
			return false;
		}
		
		
	}

	public void ingresaPresupuestoDetalle(PresupuestoForm formulario, String local) {
		log.info("PresupuestoHelper:ingresaPresupuestoDetalle inicio");
		ArrayList<ProductosBean> lista_productos = formulario.getListaProductos();
		
		PresupuestosBean presupuesto = new PresupuestosBean();
		presupuesto.setCodigo(formulario.getCodigo_suc() + "/" + this.formato_Numero_Ticket(Integer.parseInt(formulario.getCodigo())));
		presupuesto.setSerie(formulario.getCodigo_suc());
		presupuesto.setNumero(Integer.parseInt(formulario.getCodigo()));
		presupuesto.setAgente(formulario.getAgente());
		presupuesto.setDivisa(formulario.getDivisa());
		presupuesto.setIdioma(formulario.getIdioma());
		presupuesto.setDcto(formulario.getDescuento());
		presupuesto.setFecha(formulario.getFecha());
		presupuesto.setCambio(formulario.getCambio());
		presupuesto.setNotas(formulario.getNota());
		presupuesto.setForma_pago(formulario.getForma_pago());
		presupuesto.setCliente(Integer.parseInt(formulario.getCliente()));

		int x = 0;
		for (ProductosBean prod : lista_productos) {
			log.info("PresupuestoHelper:ingresaPresupuestoDetalle entrando ciclo for");
			x++;
			
			try {
				PosPresupuestoFacade.insertaPresupuestoDetalle(prod, presupuesto, x, local);
			} catch (Exception e) {
				log.error("PresupuestoHelper:ingresaPresupuestoDetalle error catch",e);
			}
			
			
		}
		
		
	}

	public void preCarga(PresupuestoForm formulario, String local) {
		log.info("PresupuestoHelper:preCarga inicio");
		formulario.setFecha(this.formatoFecha(this.traeFecha()));
		formulario.setHora(this.formatoHora(this.traeFecha()));
		//formulario.setCodigo(this.formato_Numero_Ticket(PosPresupuestoFacade.traeCodigoVenta(local)));
		formulario.setCodigo(Constantes.STRING_BLANCO);
	}

	public void modificaCantidad(PresupuestoForm formulario, int index,
			int cantidad) {
		log.info("PresupuestoHelper:modificaCantidad inicio");
		
		ProductosBean producto = formulario.getListaProductos().get(index);
		producto.setCantidad(cantidad);
		if (producto.getDescuento() == 0) {
			producto.setImporte(producto.getPrecio() * cantidad);
		}
		else
		{
			int precio = producto.getPrecio() * cantidad;
			double cant = producto.getDescuento();
			double diferencia = cant / 100;
			double valor = precio * diferencia;
			double saldo = precio - valor;
			int total_linea =  (int) Math.floor(saldo);
			producto.setImporte(total_linea);
		}
		
		formulario.getListaProductos().set(index, producto);
	}

	public void traeListaPresupuestos(PresupuestoForm formulario, String local) {
		log.info("PresupuestoHelper:traeListaPresupuestos inicio");
		String cliente = formulario.getCliente();
		try {
			formulario.setListaPresupuestos(PosPresupuestoFacade.traeListaPresupuestos(cliente, local));
		} catch (Exception e) {
			log.error("PresupuestoHelper:traeListaPresupuestos error catch",e);
		}
		
		
	}

	public void traePresupuesto(PresupuestoForm formulario, int indice) {
		log.info("PresupuestoHelper:traePresupuesto inicio");
		PresupuestosBean presupuesto = formulario.getListaPresupuestos().get(indice);
		
		formulario.setCodigo_suc(presupuesto.getSerie());
		formulario.setCodigo(this.formato_Numero_Ticket(presupuesto.getNumero()));
		formulario.setForma_pago(presupuesto.getForma_pago());
		formulario.setAgente(presupuesto.getId_agente());
		formulario.setDivisa(presupuesto.getDivisa());
		formulario.setIdioma(presupuesto.getIdioma());
		formulario.setFecha(presupuesto.getFecha());
		formulario.setHora(this.traeHoraString());
		formulario.setCambio(presupuesto.getCambio());
		formulario.setFecha_entrega(presupuesto.getFecha_entrega());
		formulario.setEstado(Constantes.STRING_VENTA);
		formulario.setNota(presupuesto.getNotas());
		formulario.setDtcoPorcentaje(presupuesto.getDcto());
		if (null == presupuesto.getConvenio()) {
			formulario.setConvenio(Constantes.STRING_BLANCO);
			formulario.setConvenio_det(Constantes.STRING_BLANCO);
		}
		else
		{
			formulario.setConvenio(presupuesto.getConvenio());
			formulario.setConvenio_det(presupuesto.getConvenio_det());
		}
		if (Constantes.STRING_N.equals(presupuesto.getCerrado())) 
		{
			formulario.setCerrado(false);
		}
		else
		{
			formulario.setCerrado(true);
			formulario.setEstado(Constantes.STRING_CERRADO);
		}
		
		try {
			formulario.setListaProductos(PosPresupuestoFacade.traeDetallePresupuesto(formulario.getCodigo_suc() + "/" + formulario.getCodigo()));
			
			if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
				for (ProductosBean prod : formulario.getListaProductos()) {
					if (Constantes.STRING_M.equals(prod.getTipoFamilia())) {
						prod.setTiene_grupo(Constantes.STRING_TRUE);
					}
					
					if (prod.getTipoFamilia().equals(Constantes.STRING_C)) {
						prod.setTiene_grupo(Constantes.STRING_TRUE);

					}
				}
			}
			
		} catch (Exception e) {
			log.error("PresupuestoHelper:traePresupuesto error catch",e);
		}
	}

	public void limpiaPreliminar(PresupuestoForm formulario, HttpSession session) {
		log.info("PresupuestoHelper:limpiaPreliminar inicio");
		formulario.setCodigo(Constantes.STRING_BLANCO);
		formulario.setCambio(Constantes.INT_CERO);
		formulario.setCodigo_suc(Constantes.STRING_BLANCO);
		formulario.setForma_pago(Constantes.STRING_CERO);
		formulario.setAgente(Constantes.STRING_CERO);
		formulario.setDivisa(Constantes.STRING_CERO);
		formulario.setIdioma(Constantes.STRING_CERO);
		//formulario.setGraduacion(new GraduacionesBean());
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS, null);
		session.setAttribute(Constantes.STRING_LISTA_PRODUCTOS_ADICIONALES, null);
		session.setAttribute(Constantes.STRING_PRECARGA_BUSQUEDA_OPTICO, null);
		
	}

	public String traspasoPedido(PresupuestoForm formulario, String local, HttpSession session)
	{
		log.info("PresupuestoHelper:traspasoPedido inicio");
		String[] msje = PosPresupuestoFacade.traspadoPedido(formulario.getCodigo_suc() + "/" + formulario.getCodigo(), local);
		if (msje[0].indexOf(Constantes.STRING_ACTION_EXITO) != -1) {
			formulario.setCerrado(true);
			formulario.setEstado(Constantes.STRING_CERRADO);
			formulario.setError(msje[0]);
			session.setAttribute(Constantes.STRING_ACTION_CDG, msje[1].trim());
			return Constantes.FORWARD_ENCARGO;
		}
		else
		{
			formulario.setCerrado(false);
			formulario.setEstado(Constantes.STRING_ERROR);
			formulario.setError(msje[1]);
			return Constantes.FORWARD_PRESUPUESTO;
		}
	}

	public boolean eliminaPresupuesto(PresupuestoForm formulario) {
		
		
		boolean estado = false;
		String codigo = formulario.getCodigo_suc().toString() + "/" + formulario.getCodigo().toString();
		try {
			estado = PosPresupuestoFacade.eliminarPresupuesto(codigo);
		} catch (Exception e) {
			log.error("PresupuestoHelper:eliminaPresupuesto error catch",e);
		}
		return estado;
	}

	public void agregaDescripcion(PresupuestoForm formulario, int index) {
			log.info("PresupuestoHelper:agregaDescripcion inicio");
			ProductosBean prod = formulario.getListaProductos().get(index);
			prod.setDescripcion(formulario.getDescripcion());
			//prod.setDescripcion_manual(Constantes.STRING_FALSE);
			formulario.getListaProductos().set(index, prod);
		
	}

	public void limpiaCliente(PresupuestoForm formulario) {
		formulario.setCliente(Constantes.STRING_BLANCO);
		formulario.setNombre_cliente(Constantes.STRING_BLANCO);
		formulario.setNif(Constantes.STRING_BLANCO);
		formulario.setDvnif(Constantes.STRING_BLANCO);
		formulario.setGraduacion(new GraduacionesBean());
		
	}

	public void aplica_descuento_por_linea(PresupuestoForm formulario) {
			
			int indice = Integer.parseInt(formulario.getAddProducto());
			double cantidad = formulario.getCantidad_descuento();
			String supervisor = formulario.getDescuento_autoriza();
			
			ProductosBean producto = formulario.getListaProductos().get(indice);
			producto.setDescuento(cantidad);
			
			if (!"".equals(supervisor)) {
				formulario.setSupervisor(supervisor);
			}
			
			int precio = producto.getPrecio() * producto.getCantidad();
			double cant = cantidad;
			double diferencia = cant / 100;
			double valor = precio * diferencia;
			double saldo = precio - valor;
			int total_linea =  (int) Math.floor(saldo);
			producto.setImporte(total_linea);
			
			formulario.getListaProductos().set(indice, producto);
			
	}

	public void aplica_descuento_total(PresupuestoForm formulario) {
		int cantidad = formulario.getCantidad();
		String supervisor = formulario.getDescuento_autoriza();
		
		if (!"".equals(supervisor)) {
			formulario.setSupervisor(supervisor);
		}
		if (cantidad > 0) {
			this.eliminaDescuentos(formulario);
			this.tarifica_Presupuesto(formulario);
		}
		double cant = cantidad;
		double diferencia = cant/100;
		double sub_total = formulario.getSubTotal();
		double valor = sub_total * diferencia;
		formulario.setDescuento((int) Math.floor(valor));
		formulario.setDtcoPorcentaje(cantidad);
		formulario.setTotal(formulario.getSubTotal() - formulario.getDescuento());
		formulario.setTotalPendiante(formulario.getTotal());
		
	}
	public void aplica_descuento_total_lineal(PresupuestoForm formulario) {
		
		double cantidad = formulario.getCantidad_descuento();
		
		this.eliminaDescuentos(formulario);
		
		for (ProductosBean prod : formulario.getListaProductos()) {
			
			if (!Constantes.STRING_MUL.equals(prod.getFamilia())) {
				int precio = prod.getPrecio() * prod.getCantidad();
				double cant = cantidad;
				double diferencia = cant / 100;
				double valor = precio * diferencia;
				double saldo = precio - valor;
				int total_linea =  (int) Math.floor(saldo);
				prod.setDescuento(cantidad);
				prod.setImporte(total_linea);
			}
		}
		
	}
	
	public void eliminaDescuentos(PresupuestoForm formulario)
	{
		for (int i = 0; i < formulario.getListaProductos().size(); i++) {
			
			ProductosBean prod = formulario.getListaProductos().get(i);
			prod.setDescuento(Constantes.INT_CERO);
			prod.setImporte(prod.getPrecio() * prod.getCantidad());
			prod.setTotal(prod.getPrecio() * prod.getCantidad());
			formulario.getListaProductos().set(i, prod);
		}
	}
	

	public boolean verificaConvenioCliente(PresupuestoForm formulario) {
		log.info("PresupuestoHelper:verificaConvenioCliente inicio");
		
			return this.verificaEstadoConvenioCliente(formulario.getConvenio(), formulario.getCliente());
	}

	public void actualizaProductosPorConvenio(PresupuestoForm formulario,String local) {
		log.info("PresupuestoHelper:actualizaProductosPorConvenio inicio");
		int valor = Constantes.INT_CERO;
		this.eliminaDescuentoTotal(formulario);
		if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > Constantes.INT_CERO) {
			for (int i = Constantes.INT_CERO; i < formulario.getListaProductos().size(); i++) {
				log.info("PresupuestoHelper:actualizaProductosPorConvenio entrando ciclo for");
				ProductosBean prod = formulario.getListaProductos().get(i);
				this.aplicaDescuentoConvenio(prod, formulario.getConvenio(), formulario.getForma_pago(),formulario.getListaProductos(),local);
				formulario.getListaProductos().set(i, prod);
			}
		}
		
	}

	public void eliminaDescuentoTotal(PresupuestoForm formulario) {
		formulario.setDtcoPorcentaje(Constantes.INT_CERO);
		formulario.setDescuento(Constantes.INT_CERO);
		formulario.setTotal(formulario.getSubTotal());
	}

	public void traeUltimaGraduacionContactologiaCliente(PresupuestoForm formulario) {
		try{
			
			formulario.setGraduacion_lentilla(PosGraduacionesFacade.traeContactologiaClienteUltima(formulario.getCliente().toString()));
			
		}catch(Exception ex){
			log.error("PresupuestoHelper:traeUltimaGraduacionContactologiaCliente error catch",ex);
		}
		
	}

	public boolean comprueba_presupuesto(PresupuestoForm formulario) {
		
		ArrayList<ProductosBean> lista_guardada = PosPresupuestoFacade.traeDetallePresupuesto(formulario.getCodigo_suc()+'/'+ formulario.getCodigo());
		if (lista_guardada.size() == formulario.getListaProductos().size()) {
			return true;
		}
		else
		{
			return false;
		}
	}

	public void aplica_descuento_total_monto(PresupuestoForm formulario)
	{
		//la cantidad se ira descontanto a medida que se establescan los descuentos en los productos
		int cantidad = formulario.getCantidad_linea();
		
		this.eliminaDescuentos(formulario);
		
		for (ProductosBean prod : formulario.getListaProductos()) {
			
			if (!(Constantes.STRING_MUL.equals(prod.getFamilia()))) {
				
				//compruebo si el producto es mayor al valor a descontar
				if(prod.getImporte() >= cantidad)
				{
					//saco la diferencia de valor para calcular el porcentaje
					int diferencia = cantidad;
					double porcentaje = ((double)diferencia * 100) / (double)prod.getImporte(); 
					porcentaje = Math.rint(porcentaje*10000)/10000;
					
					int precio = prod.getPrecio() * prod.getCantidad();
					double cant = porcentaje;
					double dif = cant / 100;
					double valor = Math.rint(precio * dif);
					double saldo = precio - valor;
					int total_linea =  (int) Math.floor(saldo);
					prod.setDescuento(porcentaje);
					prod.setImporte(total_linea);
					
					//calcula la diferencia
					int total_1 = precio - total_linea;
					cantidad = cantidad - total_1;
					
					if (cantidad < 0) {
						cantidad = 0;
					}
					
				}
				//si el producto es de menor valor se descuenta el 100% y se descuenta a la diferencia
				else
				{
					//se descuenta la direfencia
					cantidad = cantidad - prod.getImporte();
					prod.setDescuento(100);
					prod.setImporte(0);
					
				}
			}
		}
		
	}

	public void traePresupuestoSeleccionado(PresupuestoForm formulario) {
		
		PresupuestosBean presupuesto = PosPresupuestoFacade.traePresupuestoSeleccionado(formulario.getAddProducto());
		
		formulario.setCodigo_suc(presupuesto.getSerie());
		formulario.setCodigo(this.formato_Numero_Ticket(presupuesto.getNumero()));
		formulario.setForma_pago(presupuesto.getForma_pago());
		formulario.setAgente(presupuesto.getId_agente());
		formulario.setDivisa(presupuesto.getDivisa());
		formulario.setIdioma(presupuesto.getIdioma());
		formulario.setFecha(presupuesto.getFecha());
		formulario.setHora(this.traeHoraString());
		formulario.setCambio(presupuesto.getCambio());
		formulario.setFecha_entrega(presupuesto.getFecha_entrega());
		formulario.setEstado(Constantes.STRING_VENTA);
		formulario.setCliente(String.valueOf(presupuesto.getCliente()));
		formulario.setNota(presupuesto.getNotas());
		formulario.setDtcoPorcentaje(presupuesto.getDcto());
		if (null == presupuesto.getConvenio()) {
			formulario.setConvenio(Constantes.STRING_BLANCO);
			formulario.setConvenio_det(Constantes.STRING_BLANCO);
		}
		else
		{
			formulario.setConvenio(presupuesto.getConvenio());
			formulario.setConvenio_det(presupuesto.getConvenio_det());
		}
		if (Constantes.STRING_N.equals(presupuesto.getCerrado())) 
		{
			formulario.setCerrado(false);
		}
		else
		{
			formulario.setCerrado(true);
			formulario.setEstado(Constantes.STRING_CERRADO);
		}
		
		try {
			formulario.setListaProductos(PosPresupuestoFacade.traeDetallePresupuesto(formulario.getCodigo_suc() + "/" + formulario.getCodigo()));
			
			if (null != formulario.getListaProductos() && formulario.getListaProductos().size() > 0) {
				for (ProductosBean prod : formulario.getListaProductos()) {
					if (Constantes.STRING_M.equals(prod.getTipoFamilia())) {
						prod.setTiene_grupo(Constantes.STRING_TRUE);
					}
					
					if (prod.getTipoFamilia().equals(Constantes.STRING_C)) {
						prod.setTiene_grupo(Constantes.STRING_TRUE);

					}
				}
			}
			
		} catch (Exception e) {
			log.error("PresupuestoHelper:traePresupuesto error catch",e);
		}
	}

}
