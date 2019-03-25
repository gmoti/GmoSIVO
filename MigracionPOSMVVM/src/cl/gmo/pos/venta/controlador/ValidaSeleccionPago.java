package cl.gmo.pos.venta.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import cl.gmo.pos.venta.controlador.ventaDirecta.SeleccionPagoDispatchActions;
import cl.gmo.pos.venta.utils.Constantes;
import cl.gmo.pos.venta.web.beans.FormaPagoBean;
import cl.gmo.pos.venta.web.beans.PagoBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.forms.SeleccionPagoForm;

public class ValidaSeleccionPago implements Serializable {

	
	private static final long serialVersionUID = 6314747521747452576L;
	Session sess = Sessions.getCurrent();
	
	private boolean pagarReadOnly=false;
	private boolean isapreVisible=false;
	private String  sIP="";
	private String 	sucursal;
	private SeleccionPagoDispatchActions seleccionPagoDispatchActions;
	
	public ValidaSeleccionPago() {
		
		sucursal 	= sess.getAttribute(Constantes.STRING_SUCURSAL).toString();	
		seleccionPagoDispatchActions = new SeleccionPagoDispatchActions();
	}
	
	public ArrayList<FormaPagoBean> postCarga(String convenio, String isapre, 
			Integer diferencia, ArrayList<ProductosBean> listaProductos, 
			SeleccionPagoForm seleccionPagoForm) {		
		
		int paso_grp = 0;
	 	int cont = 0;
	 	int dent = 0;
	 	String tmp;
	 	int i=0;
	 	
	 	Optional<String> con = Optional.ofNullable(convenio);
	 	Optional<String> isa = Optional.ofNullable(isapre);
	 	Optional<String> tieneDoc = Optional.ofNullable(seleccionPagoForm.getTiene_documentos());
	 	
	 	seleccionPagoForm.setTiene_documentos(tieneDoc.orElse(""));
	 	
	 	
	 	convenio = con.orElse("");
	 	isapre = isa.orElse("");
	 	
	 	if (seleccionPagoForm.getListaPagos() == null)
	 		seleccionPagoForm.setListaPagos(new ArrayList<PagoBean>());
	 	
	 	for(PagoBean pb : seleccionPagoForm.getListaPagos()) {	 		
	 		if (pb.getForma_pago().equals("GRPON") || pb.getForma_pago().equals("ISAPR")) 
	 			paso_grp = 1; 		
	 	}
	 	
	 	for(ProductosBean pb : listaProductos) {	 		
	 		if (pb.getFamilia().equals("DES")){	 			
	 			
	 			i=0;
	 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {	 			
	 				if(fpb.getId().equals("GAR") || fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED") || fpb.getId().equals("CRB")) {
	 					fpb.setActivo(true);
	 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
	 				}
	 				i++;
	 			} 			
	 		}	 		
	 	}
	 	
	 	
	 	if (paso_grp != 1) {
	 		
	 	    if(!sess.getAttribute("_Convenio").equals("sdg")){
	 		
		 		if(convenio.equals("50368") || 
		 				convenio.equals("50369") || 
		 				convenio.equals("50472") || 
		 				convenio.equals("50473") || 
		 				convenio.equals("50474") ){
		 			i=0;
		 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
		 				if(!fpb.getId().equals("GRPON") && !fpb.getId().equals("0")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 				}	
		 				i++;
		 			} 		 			
		 			
		 			sess.setAttribute("_Convenio", "sdg");			  
					  
				 }else{
					 seleccionPagoForm.setListaFormasPago(borra_grpn(seleccionPagoForm));
				 }
	 		
		 		if(convenio.equals("51001") || convenio.equals("51002")){
		 			i=0;
		 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
		 				if(!fpb.getId().equals("OA") && !fpb.getId().equals("0")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 				}	
		 				i++;
		 			} 	 			
		 			
					sess.setAttribute("_Convenio", "sdg");	
				 }	
	 		
		 		if(convenio.equals("50472")){
					//$j("#sumaPagar").val("80000").attr("readonly",true);
					seleccionPagoForm.setV_a_pagar(80000);
					pagarReadOnly=true;
				 }
		 		
				 if(convenio.equals("50473")){
					//$j("#sumaPagar").val("120000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(120000);
					 pagarReadOnly=true;
				 }
				 if(convenio.equals("50474")){
					//$j("#sumaPagar").val("160000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(160000);
					 pagarReadOnly=true;
				 }
				 if(convenio.equals("50368")){
					//$j("#sumaPagar").val("30000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(30000);
					 pagarReadOnly=true;
				 }
				 if(convenio.equals("50369")){
					//$j("#sumaPagar").val("15000").attr("readonly",true);
					 seleccionPagoForm.setV_a_pagar(15000);
					 pagarReadOnly=true;
				 }	
	 			
				 if(convenio.equals("51001")){
					 
					 seleccionPagoForm.setV_total_parcial(41650); 
					 seleccionPagoForm.setV_a_pagar(41650);
					 seleccionPagoForm.setDiferencia(41650);
					 seleccionPagoForm.setV_total(41650);
					 seleccionPagoForm.setDiferencia(41650);
					 pagarReadOnly=true;
					 
					/*$j("#sumaParcial").val("41650").attr("readonly",true);
					$j("#sumaPagar").val("41650").attr("readonly",true);
					$j("#diferencia").val("41650").attr("readonly",true);
					$j("#v_total").val("41650").attr("readonly",true);
					$j("#diferencia_total").val("41650").attr("readonly",true);*/
					}
				 
				 if(convenio.equals("51002")){	
					 
					 seleccionPagoForm.setV_total_parcial(83300); 
					 seleccionPagoForm.setV_a_pagar(83300);
					 seleccionPagoForm.setDiferencia(83300);
					 seleccionPagoForm.setV_total(83300);
					 seleccionPagoForm.setDiferencia(83300);	
					 pagarReadOnly=true;
					 
					/*$j("#sumaParcial").val("83300").attr("readonly",true);
					$j("#sumaPagar").val("83300").attr("readonly",true);
					$j("#diferencia").val("83300").attr("readonly",true);
					$j("#v_total").val("83300").attr("readonly",true);
					$j("#diferencia_total").val("83300").attr("readonly",true);*/
				 	}
	 			
				 // CRUZ BLANCA
				 // 20141203 - SE MODIFICA A PETICION DE PAULO BARRERA.
				 if (seleccionPagoForm.getTiene_documentos().equals("false")){
					 if(isapre.equals("S")){
						 isapreVisible=true;
						 i=0;
						 for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
							 if(!fpb.getId().equals("ISAPR") && !fpb.getId().equals("EXCED") && !fpb.getId().equals("0")) {
				 					fpb.setActivo(true);
				 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
				 			}
							i++; 
				 		 } 
						 
					 }else{
						 i=0;
						 for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
							 if(fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED")) {
				 					fpb.setActivo(true);
				 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
				 			 }	
							 i++;
				 		 } 			 
						
					 }					 
					 
				 }else{
					 seleccionPagoForm.setListaFormasPago(borra_crb(seleccionPagoForm));		 		
				 		
				 }
	 			
	 	      }	 //cookie("convenio") != "sdg"				 
				 
	 		}else{//paso_grp != 1
	 			seleccionPagoForm.setListaFormasPago(borra_grpn(seleccionPagoForm));
			}
	 	
	 	
	 	//valida doc convenio cruz blanca
	 	if (seleccionPagoForm.getTiene_documentos().equals("false")) {
	 		
	 		if(isapre.equals("S")) {
	 			isapreVisible=true;
	 			i=0;
	 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
	 				if(!fpb.getId().equals("ISAPR") && !fpb.getId().equals("EXCED") && !fpb.getId().equals("0")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 			}
	 				i++;
		 		 } 		
	 			
	 		}else {
	 			i=0;
	 			for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {
	 				if(fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED")) {
		 					fpb.setActivo(true);
		 					seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
		 			}
	 				i++;
		 		 }  			
	 						
	 		}
	 	}else{
	 		seleccionPagoForm.setListaFormasPago(borra_crb(seleccionPagoForm));
	 	}
	 	
	 	
	 	//valida ip
	 	String data="";
	 	
	 	/* Validacion ip para puntos transbank
	 	try {
	 		
	 		seleccionPagoForm.setIp(sIP);
	 		seleccionPagoForm.setLocal(sucursal);
	 		
	 		data = seleccionPagoDispatchActions.valida_ip(seleccionPagoForm, sess).trim();
	 		
	 		switch (data.trim()) {	 		
	 		case "P":
 				//
	 			break;
			case "S":
				//COBRO TERMINAL SECUNDARIO
				i=0;
				for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {					
					if(!fpb.getId().equals("1") && !fpb.getId().equals("OA") && !fpb.getId().equals("OASD")){
						seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
			  		}
					i++;
				}
				
				break;	

			default:
				//COBRO TERMINAL SECUNDARIO
				i=0;
				for(FormaPagoBean fpb : seleccionPagoForm.getListaFormasPago()) {					
					if(!fpb.getId().equals("1") && !fpb.getId().equals("OA") && !fpb.getId().equals("OASD")){
						seleccionPagoForm.getListaFormasPago().get(i).setActivo(true);
			  		}
					i++;
				}				
				
				break;
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}*/
	 	
	 	
	 	return seleccionPagoForm.getListaFormasPago();
	} //fin metodo principal
	
	
	private ArrayList<FormaPagoBean> borra_grpn(SeleccionPagoForm seleccionPago){
		int i=0;
		for(FormaPagoBean fpb : seleccionPago.getListaFormasPago()) {
			if(fpb.getId().equals("GRPON")) {
 					fpb.setActivo(true);
 					seleccionPago.getListaFormasPago().get(i).setActivo(true);
 			}
			i++;
 		 } 	
		
		return seleccionPago.getListaFormasPago();
	}
	
	//GROUPON 
	private ArrayList<FormaPagoBean> borra_crb(SeleccionPagoForm seleccionPago){
		//$j.cookie("crb","crb_2");
		String alb = seleccionPago.getOrigen();
		
		if(alb.toLowerCase().indexOf("albaran") < 0){
			int i=0;
			for(FormaPagoBean fpb : seleccionPago.getListaFormasPago()) {
				if(fpb.getId().equals("ISAPR") || fpb.getId().equals("EXCED")) {
	 					fpb.setActivo(true);
	 			}
				i++;
	 		 } 			
		}	
		
		return seleccionPago.getListaFormasPago();
	}

	
	
	

}
