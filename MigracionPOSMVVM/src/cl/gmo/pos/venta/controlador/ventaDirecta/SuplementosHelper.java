package cl.gmo.pos.venta.controlador.ventaDirecta;



import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Session;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.utils.Utils;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.SuplementopedidoBean;
import cl.gmo.pos.venta.web.beans.SuplementosValores;
import cl.gmo.pos.venta.web.facade.PosUtilesFacade;
import cl.gmo.pos.venta.web.forms.SuplementosForm;

public class SuplementosHelper extends Utils{
	Logger log = Logger.getLogger( this.getClass() );
	public void cargaFormulario(SuplementosForm formulario, Session session) 
	{
		log.info("SuplementosHelper:cargaFormulario inicio");
		formulario.setListaSuplementos((ArrayList<SuplementopedidoBean>)session.getAttribute(Constantes.STRING_LISTA_SUPLEMENTOS));
		try {
			ArrayList<SuplementopedidoBean> suple_obli = PosUtilesFacade.traeSuplementosObligatorios(formulario.getProducto());
			ArrayList<SuplementopedidoBean> suple_opc = PosUtilesFacade.traeSuplementosOpcionales(formulario.getProducto());
			formulario.setError(Constantes.STRING_SIN_ERROR);
			
			if (null == suple_opc || suple_opc.size() == 0) {
				formulario.setSuplementos(suple_obli);
			}
			else
			{
				if(null == suple_obli)
				{
					formulario.setSuplementos(suple_opc);
				}else
				{
					for (SuplementopedidoBean suple : suple_opc) {
						log.info("SuplementosHelper:cargaFormulario entrando ciclo for");
						for (SuplementopedidoBean sup: suple_obli) {
							log.info("SuplementosHelper:cargaFormulario entrando ciclo for");
							if (suple.getTratami().equals(sup.getTratami())) {
								suple.setObligatorio(Constantes.STRING_SI);
								suple.setDescripcion("*** " +sup.getDescripcion() + " *** << OBLIGATORIO >>");
							}
						}
					}
					formulario.setSuplementos(suple_opc);
				}
			}
		} catch (Exception e) {
			log.error("SuplementosHelper:cargaFormulario error catch",e);
		}
		//valida y se elimina los repetidos en suplementos opcionales
	}

	public void agregaSuplemento(SuplementosForm formulario, Session session) {
		log.info("SuplementosHelper:agregaSuplemento inicio");

		boolean con_DE_75 = false;
		boolean con_DC = false;
		
		SuplementopedidoBean sup = new SuplementopedidoBean();
			
		sup.setTratami(formulario.getSuplemento());
		
		sup.setValor(formulario.getValor());
		
		sup.setDescripcion(formulario.getSuplemento_desc());
		
		formulario.setError(Constantes.STRING_SIN_ERROR);
		ArrayList<SuplementopedidoBean> lista_sup = (ArrayList<SuplementopedidoBean>)session.getAttribute(Constantes.STRING_LISTA_SUPLEMENTOS);
		
		if (null == lista_sup){
			lista_sup = new ArrayList<SuplementopedidoBean>();
			lista_sup.add(sup);
		}
		else
		{
			for (SuplementopedidoBean suple : lista_sup) {
				log.info("SuplementosHelper:agregaSuplemento entrando ciclo for");
				if (suple.getTratami().equals(sup.getTratami())) {
					formulario.setError(Constantes.STRING_ERROR_SUPLEMENTO_REPETIDO);
				}
				
				//valida la combinacion suplemento DE con DC
				if (suple.getTratami().equalsIgnoreCase("DE") && suple.getValor().equals("75,00")) {
					con_DE_75 = true;
				}
				if (suple.getTratami().equalsIgnoreCase("DC")) {
					con_DC = true;
				}
			}
			
			//valida la combinacion suplemento DE con DC
			if (con_DE_75 && sup.getTratami().equalsIgnoreCase("DC")) {
				formulario.setError(Constantes.STRING_ERROR_SUPLEMENTO_DC_NO_INGRESAR);
			}
			else if (con_DC && sup.getTratami().equalsIgnoreCase("DE") && sup.getValor().equalsIgnoreCase("75,00")) {
				formulario.setError(Constantes.STRING_ERROR_SUPLEMENTO_DE_NO_INGRESAR);
			}
			
			
			if (formulario.getError().equals(Constantes.STRING_SIN_ERROR))
			{
				lista_sup.add(sup);
			}
			
		}
		
		session.setAttribute(Constantes.STRING_LISTA_SUPLEMENTOS, lista_sup);
		formulario.setListaSuplementos(lista_sup);
		formulario.setSuplemento(Constantes.STRING_CERO);
	}

	public void validaCierre(SuplementosForm formulario, Session session) {
		log.info("SuplementosHelper:validaCierre inicio");
		formulario.setError(Constantes.STRING_SIN_ERROR);
		
		ArrayList<SuplementopedidoBean> lista_suplementos = (ArrayList<SuplementopedidoBean>) session.getAttribute(Constantes.STRING_LISTA_SUPLEMENTOS);
		ArrayList<SuplementopedidoBean> suple_obli = null;
		try {
			suple_obli = PosUtilesFacade.traeSuplementosObligatorios(formulario.getProducto());
		} catch (Exception e) {
			log.error("SuplementosHelper:validaCierre error catch",e);
		}
		
		int elementos = 0;
		int elementos_lista = 0;
		
		 
		if(null != suple_obli)
		{
			elementos = suple_obli.size();
			if (elementos != 0) 
			{
				if (null != lista_suplementos) 
				{
					for (SuplementopedidoBean sup : lista_suplementos) 
					{
						 for (SuplementopedidoBean suple_O : suple_obli) 
						 {
							if(sup.getTratami().equals(suple_O.getTratami()))
							{
								elementos_lista++;
							}
						}
					}
					if (elementos == elementos_lista) 
					{
						formulario.setError(Constantes.STRING_CIERRE_OK);
					}else{
						formulario.setError(Constantes.STRING_CIERRE_NO_OK);
					}
				}
				else
				{
					formulario.setError(Constantes.STRING_CIERRE_NO_OK);
				}
			}else
			{
				formulario.setError(Constantes.STRING_CIERRE_OK);
			}
			
		}
		else
		{
			formulario.setError(Constantes.STRING_CIERRE_OK);
		}
	}

	public void cargaValores(SuplementosForm formulario, Session session, ProductosBean producto) {
		log.info("SuplementosHelper:cargaValores inicio");

		formulario.setSuplemento(formulario.getSuplemento());
		formulario.setSuplemento_desc(formulario.getSuplemento_desc());
		
		try {
			formulario.setLista_valores_suplementos(PosUtilesFacade.traeValoresSuplementos(formulario.getSuplemento(),producto.getCodigo()));
		} catch (Exception e) {
			log.error("SuplementosHelper:cargaValores error catch",e);
		}
		
		//valido si se selecciono el suplemento DE
		if (formulario.getSuplemento().equalsIgnoreCase("DE")) {
			//valida si corresponde a las familias que debe tener habilitado, si es falso debe eliminar el valor
			if (!(producto.getFamilia().equalsIgnoreCase("81S") && producto.getSubFamilia().equalsIgnoreCase("ORG") && producto.getGrupoFamilia().equalsIgnoreCase("ST"))
				&& !(producto.getFamilia().equalsIgnoreCase("81S") && producto.getSubFamilia().equalsIgnoreCase("ORG") && producto.getGrupoFamilia().equalsIgnoreCase("AR")))
			{
				ArrayList<SuplementosValores> valores_suple = new ArrayList<SuplementosValores>();
				for (SuplementosValores valores : formulario.getLista_valores_suplementos()) {
					if (valores.getDescripcion().equals("75,00")) {
					}
					else
					{
						valores_suple.add(valores);
					}
					
				}
				formulario.setLista_valores_suplementos(valores_suple);
			}
			
		}
		
		
	}

	public void eliminarSuplemento(SuplementosForm formulario) {
		log.info("SuplementosHelper:eliminarSuplemento inicio");
		formulario.setSuplemento(Constantes.STRING_CERO);
		formulario.getListaSuplementos().remove(Integer.parseInt(formulario.getSuplemento_desc().toString()));
		
	}
}
