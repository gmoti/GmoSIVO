package cl.gmo.pos.validador;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Messagebox;

import com.google.javascript.jscomp.Var;

import cl.gmo.pos.venta.web.forms.GraduacionesForm;
import cl.gmo.pos.venta.web.forms.MedicoForm;

public class _ValidacionGraduacion implements Serializable {
	
	private static final long serialVersionUID = 2027012303185061783L;
	private GraduacionesForm graduacionesForm;
	private MedicoForm medicoForm;
	
	
	/*
	private void trim(s){
		s = s.replace(/\s+/gi, ''); //sacar espacios revalidaDNPCpetidos dejando solo uno
		s = s.replace(/^\s+|\s+$/gi, ''); //sacar espacios blanco principio y final
		return s;
	}*/

	private void validaInformacion(){
		
		String nombre_cliente = graduacionesForm.getNombre_cliente();
		nombre_cliente = nombre_cliente.trim();
		
		if(nombre_cliente.equals("")){
			Messagebox.show("Debe ingresar cliente");
			return;
		}
		
		String codigo_cliente = graduacionesForm.getCodigo_cliente_agregado();
		codigo_cliente = codigo_cliente.trim();
		
		if(codigo_cliente.equals("") || codigo_cliente.equals("0")){		
			Messagebox.show("Debe ingresar un cliente");
			return;
		}		
		
		String doctor = graduacionesForm.getDoctor();
		if(doctor.equals("")){
			Messagebox.show("Debe ingresar un doctor");
			return;		
		}
		
		String agente = graduacionesForm.getAgente();
		if(agente.equals("-1")){
			Messagebox.show("Debe Seleccionar agente");
			return;		
		}
		
		/*var fechaEmision = document.getElementById('fechaEmision').value;
		fechaEmision = trim(fechaEmision);
		if("" == fechaEmision){
			Messagebox.show("Debe ingresar fecha emisi\u00F3n");
			return false;
		}*/
		
		String OD_cantidad = graduacionesForm.getOD_cantidad();
		OD_cantidad = OD_cantidad.trim();
		if(!OD_cantidad.equals("")  && !OD_cantidad.equals("-1") ){
			
			String OD_base = graduacionesForm.getOD_base();
			
			if(OD_base.equals("")  || OD_base.equals("Seleccione") ){
				Messagebox.show("Debe seleccionar Base de Prisma Derecho");
				return;
			}
			
		}
		
		String OI_cantidad = graduacionesForm.getOI_cantidad();
		OI_cantidad = OI_cantidad.trim();
		if(!OI_cantidad.equals("")  && !OI_cantidad.equals("-1") ){
			
			String OI_base = graduacionesForm.getOI_base();
			
			if(OI_base.equals("")  || OI_base.equals("Seleccione") ){
				Messagebox.show("Debe seleccionar Base de Prisma Izquierdo");
				return;
			}
			
		}
		
		String OD_base = graduacionesForm.getOD_base();
		if(!OD_base.equals("")  && !OD_base.equals("Seleccione") ){
			
			OD_cantidad = graduacionesForm.getOD_cantidad();		
			
			if(OD_cantidad.equals("")  || OD_cantidad.equals("-1") ){
				Messagebox.show("Debe seleccionar Cantidad de Prisma Derecho");
				return;
			}
		}
		
		String OI_base = graduacionesForm.getOI_base();
		if(!OI_base.equals("")  &&  OI_base.equals("Seleccione") ){
			
			OI_cantidad = graduacionesForm.getOI_cantidad();
			if(OI_cantidad.equals("")  || OI_cantidad.equals("-1") ){
				Messagebox.show("Debe seleccionar Cantidad de Prisma Izquierdo");
				return;
			}
		}
		
		return; //true si llega hasta aqui
	}
	
	
	
	

	public boolean validaGraduacion(){
		
		/********* VALIDACIONES OJO DERECHO ********/
		String esferaD = graduacionesForm.getOD_esfera();
		esferaD = esferaD.trim();
		
		String cilindroD = graduacionesForm.getOD_cilindro();
		cilindroD = cilindroD.trim();
		
		String ejeD = graduacionesForm.getOD_eje();
		ejeD = ejeD.trim();
				
		String adicionD = graduacionesForm.getOD_adicion();
		adicionD = adicionD.trim();
		
		String OD_dnpl = graduacionesForm.getOD_dnpl();
		OD_dnpl = OD_dnpl.trim();		
		
		if(!esferaD.equals("") && !esferaD.equals(null)){
			if(Float.parseFloat(esferaD) < -30 || Float.parseFloat(esferaD) > 30){
				Messagebox.show("El valor esfera derecha esta fuera del rango permitido -30 y 30");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar esfera derecha.");
			return false;
		}		
		
		if(!cilindroD.equals("") && !cilindroD.equals(null)){		
			if(Float.parseFloat(cilindroD) < -8  || Float.parseFloat(cilindroD) > 8){
				Messagebox.show("El valor cilindro derecho esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro derecho");
			return false;
		}
		
		
		int intCilindro = Integer.parseInt(cilindroD);
		if((!cilindroD.equals("") && !cilindroD.equals(null)) && (0.00 != Float.parseFloat(cilindroD) && 0 != Integer.parseInt(cilindroD)) ){
			if(ejeD.equals("") || ejeD.equals(null)){
				Messagebox.show("Debe ingresar eje derecho");
				return false;
			}
		}
		
		
		if(!ejeD.equals("") && !ejeD.equals(null)){
			if(cilindroD.equals("") || cilindroD.equals(null)){
				Messagebox.show("Debe ingresar cilindro derecho");
				return false;
			}
		}	
		
		if(!ejeD.equals("") && !ejeD.equals(null)){
			if(Float.parseFloat(ejeD) < 0 || Float.parseFloat(ejeD) >180){
				Messagebox.show("El valor del eje derecho esta fuera de rango");
				return false;
			}
		}
		
		
		if(!adicionD.equals("") && !adicionD.equals(null)){
			if(Float.parseFloat(adicionD) < 0.50 || Float.parseFloat(adicionD) > 4){
				Messagebox.show("El valor de la adicion derecha esta fuera de rango");
				return false;
			}
		}
		
		if(OD_dnpl.equals("") || OD_dnpl.equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar derecha.");
			return false;
		}else{
			boolean respuesta = validaDNPL(graduacionesForm.getOD_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}
		
		
		
		/********* FIN VALIDACIONES OJO DERECHO ********/
		
		
		
		/********* VALIDACIONES OJO IZQUIERDO ********/
		
		String esferaI = graduacionesForm.getOI_esfera();
		esferaI = esferaI.trim();
		
		String cilindroI = graduacionesForm.getOI_cilindro();
		cilindroI = cilindroI.trim();
		
		String ejeI = graduacionesForm.getOI_eje();
		ejeI = ejeI.trim();
		
		String adicionI = graduacionesForm.getOI_adicion();
		adicionI = adicionI.trim();
		
		String OI_dnpl = graduacionesForm .getOI_dnpl();
		OI_dnpl = OI_dnpl.trim();
		
		if(!esferaI.equals("") && !esferaI.equals(null)){
			if(Float.parseFloat(esferaI) < -30 || Float.parseFloat(esferaI) >30){
				Messagebox.show("El valor esfera izquierda esta fuera de rango");
				return false;
			}	
		}else{
			Messagebox.show("Debe ingresar esfera izquierda.");
			return false;
		}
		
		if(!cilindroI.equals("") && cilindroI.equals(null)){		
			if(Float.parseFloat(cilindroI) < -8  || Float.parseFloat(cilindroI) > 8){
				Messagebox.show("El valor cilindro izquierdo esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro izquierdo");
			return false;
		}
		
		if(!cilindroI.equals("") && cilindroI.equals(null) && (0.00 != Float.parseFloat(cilindroI) && 0 != Integer.parseInt(cilindroI))){
			if(ejeI.equals("") || ejeI.equals(null)){
				Messagebox.show("Debe ingresar eje izquierdo");
				return false;
			}
		}	
		
		if(!ejeI.equals("") && !ejeI.equals(null)){
			if(cilindroI.equals("") || cilindroI.equals(null)){
				Messagebox.show("Debe ingresar cilindro izquierdo");
				return false;
			}
		}
		if(!ejeI.equals("") && !ejeI.equals(null)){
			if(Float.parseFloat(ejeI) < 0 || Float.parseFloat(ejeI) >180){
				Messagebox.show("El valor del eje izquierdo esta fuera de rango");
				return false;
			}
		}
		
		
		if(!adicionI.equals("") && !adicionI.equals(null)){
			if(Float.parseFloat(adicionI) <= 0 || Float.parseFloat(adicionI) > 4){
				Messagebox.show("El valor de la adicion izquierda esta fuera de rango");
				return false;
			}
		}
		
		if(OI_dnpl.equals("") || OI_dnpl.equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar izquierda.");
			return false;
		}else{
			boolean respuesta = validaDNPL(graduacionesForm.getOI_dnpl(), "izquierda");
			if(respuesta == false){
				return false;
			}
		}
		
		boolean respuestadnpc = validaDNPC(graduacionesForm.getOD_dnpc(), "derecha");
		if(respuestadnpc == false){
			return false;
		}
		
		/********* FIN VALIDACIONES OJO IZQUIERDO ********/	
		
		
		String fechaProxRevision = graduacionesForm.getFechaProxRevision();
		String fechaEmision = graduacionesForm.getFechaEmision();
			
		fechaEmision = fechaEmision.trim();
		if(fechaEmision.equals("")){
			Messagebox.show("Debe ingresar fecha de emision");
			return false;
		}
		
		fechaProxRevision = fechaProxRevision.trim();
		if(fechaProxRevision.equals("")){
			Messagebox.show("Debe ingresar fecha de revisi\u00F3n");
			return false;
		}
		
		return true;//CAMBIAR  a true
	}
	

	public void validaEsfera(String elemento, String lado){
		
				
		float mult = (float) 0.25;
		float cont = 0;		
		float esfera= (float) 0.0;	
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
			
		if(!elemento.equals("")){
			
			esfera = Float.valueOf(elemento.trim());
			
			if(esfera >= -30 && esfera <= 30){
				
				//esfera = parseFloat(elemento.value).toFixed(2);
				
				if (esfera%mult != 0){
					
					 while((esfera%mult !=0 ) && (cont < 55)){
						 
						 if(esfera > 0){				 
							 esfera = (float) (esfera + 0.01);
						 }else{
							 esfera = (float) (esfera + -0.01);
						 }
						 //esfera = parseFloat(esfera.toFixed(2));
						 cont++;
					 }				
					 
					 //elemento.value=esfera.toFixed(2);
					 
					 if(lado.equals("derecha")){
						 String elementoadicion = graduacionesForm.getOD_adicion();
						 validaAdicion(elementoadicion, lado);
						 
					 }else if(lado.equals("izquierda")){
						 String elementoadicion = graduacionesForm.getOI_adicion();
						 validaAdicion(elementoadicion, lado);
					 }
				}else{	
					
					 elemento = String.valueOf(esfera);	
					 
					 if(lado.equals("derecha")){
						 String elementoadicion = graduacionesForm.getOD_adicion();
						 validaAdicion(elementoadicion, lado);
						 
					 }else if(lado.equals("izquierda")){
						 String elementoadicion = graduacionesForm.getOI_adicion();
						 validaAdicion(elementoadicion, lado);
					 }
				}
			}else{
				Messagebox.show("El valor esfera "+lado+" esta fuera del rango permitido -30 y 30");
				elemento="";
				//elemento.focus();
			}
		}else{
			esfera = (float) 0;
			Messagebox.show("Debe ingresar valores entre -30 y 30");
			elemento="";
			//elemento.value = parseFloat(esfera).toFixed(2);
			//elemento.focus();
		}		
	}

	public void validaCilindro(String elemento, String lado){
		
		float cilindro; 	
		float mult = 0.25;
		float cont = 0;
			
			if("" != cilindro){
				
				cilindro = Float.parseFloat(elemento);
				
				if(cilindro >= -8 && cilindro <= 8){
					cilindro = parseFloat(elemento.value).toFixed(2);
					if (cilindro%mult!=0){			 
						 while((cilindro%mult!=0) && (cont < 55)){				 
							 
							 if(cilindro > 0){
								 cilindro = cilindro + 0.01;
							 }else{
								 cilindro = cilindro + (-0.01);
							 }
							 //cilindro = parseFloat(cilindro.toFixed(2));
							 cont++;
						 }				 
						 //elemento.value=cilindro.toFixed(2);
					}else{			
						 elemento = cilindro;			
					}
				}else{
					Messagebox.show("El valor cilindro "+lado+" esta fuera del rango permitido -8 y 8");
					elemento.value = "";
					//elemento.focus();
				}
			}else{
				cilindro = 0;
				Messagebox.show("Debe ingresar valores entre -8 y 8");
				elemento = "";
				//elemento.value = parseFloat(cilindro).toFixed(2);
				//elemento.focus();
			}
			
			var intCilindro = cilindro;			
			if(((intCilindro >= -8 && intCilindro < 0) || (intCilindro > 0 && intCilindro <= 8)) || (intCilindro >= -8.00 && intCilindro < 0.00) || (intCilindro > 0.00 && intCilindro <= 8.00) ){
				
				if(lado.equals("derecho")){				
					 document.getElementById('OD_eje').disabled =false;	
					
				}else if(lado.equals('izquierda')){
					document.getElementById('OI_eje').disabled =false;
				}			
			}else{
				
				if(lado.equals("derecho")){				
					document.getElementById('OD_eje').value="";
					document.getElementById('OD_eje').disabled =true;		
					
				}else if(lado.equals('izquierda')){				
					document.getElementById('OI_eje').value="";
					document.getElementById('OI_eje').disabled =true;
				}
			}
			
	}

	public void validaEje(String elemento, String lado){
		
		String eje = elemento;	
		eje = eje.trim();
		
		if(!eje.equals("")){		
			boolean esnumero = validarSiNumero(eje);
			
			if(true == esnumero){
				if(eje < 0 || eje >180){
					Messagebox.show("El valor del eje "+lado+" esta fuera de rango");
					eje = 0;
					elemento = "";
				}
			}else{
				Messagebox.show("Debe ingresar solo numeros entre 0 y 180");
			}		
		}	
	}

	public void validaAdicion(String elemento, String lado){
		
		float esfera = 0;
		float dnpl = 0;
		float adicion =0;
		//= elemento.value;
		//adicion = trim(adicion);
		
		
		
		if(lado.equals("derecha")){	
			esfera = Float.parseFloat(graduacionesForm.getOD_esfera().trim());			
			dnpl = Float.parseFloat(graduacionesForm.getOD_dnpl().trim());
			
		}else if(lado.equals("izquierda")){
			esfera = Float.parseFloat(graduacionesForm.getOI_esfera().trim());			
			dnpl = Float.parseFloat(graduacionesForm.getOI_dnpl().trim());			
		}		
		
		if(!esfera.equals("")){	
			
			if(lado.equals("derecha")){
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(!adicion.equals("")){
					if(adicion >0 && adicion <=4){
						
						float cerca = adicion + esfera;
						
						//document.getElementById('OD_cerca').value = cerca.toFixed(2);						
						elemento = String.valueOf(adicion);//.toFixed(2);
						dnpl = Float.parseFloat(graduacionesForm.getOD_dnpl());
						validaDNPL(dnpl, lado);
						
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
						}
					}	
					
				}else{
					graduacionesForm.setOD_cerca("");				
					graduacionesForm.setOD_dnpl("");
					graduacionesForm.setOD_dnpc("");			
				}		
				
			}else if(lado.equals("izquierda")){
				
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(!adicion.equals("")){
					
					if(adicion >0 && adicion <=4){
						
						float cerca = adicion + esfera;
						//document.getElementById('OI_cerca').value = cerca.toFixed(2);
						elemento = String.valueOf(adicion); //.toFixed(2);
						dnpl = Float.parseFloat(graduacionesForm.getOI_dnpl());
						validaDNPL(dnpl, lado);
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
						}
						
					}					
				}else{
					graduacionesForm.setOI_cerca("");
					graduacionesForm.setOI_dnpl("");
					graduacionesForm.setOI_dnpc("");
				}			
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");		
		}
	}

	public void validacionCerca(String elemento, String lado){
		
		String esfera = "0";
		String cerca = elemento.trim();		
		
		if(lado.equals("derecha")){	
			esfera = graduacionesForm.getOD_esfera().trim();			
			
		}else if(lado.equals("izquierda")){
			esfera = graduacionesForm.getOI_esfera().trim();
			
		}
		
		
		if(!esfera.equals("")){		
			if(lado.equals("derecha")){	
				
				esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(!cerca.equals("")){
					if(cerca > esfera){
						
						var adicion = parseFloat(cerca) -  parseFloat(esfera);
						document.getElementById('OD_adicion').value = adicion.toFixed(2);
						elemento.value = parseFloat(cerca).toFixed(2);
						var dnpl = document.getElementById('OD_dnpl');
						validaDNPL(dnpl, lado);
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");
					}	
					
				}		
				
			}else if(lado.equals("izquierda")){
				esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(!cerca.equals("")){
					if(cerca > esfera){
						var adicion = parseFloat(cerca) -  parseFloat(esfera);
						document.getElementById('OI_adicion').value = adicion.toFixed(2);
						elemento.value = parseFloat(cerca).toFixed(2); 
						var dnpl = document.getElementById('OI_dnpl');
						validaDNPL(dnpl, lado);
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");
					}				
				}	
				
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");
		}
		
	}

	public boolean validaDNPL(String elemento, String lado){
		
		String dnpl = elemento.trim();		
		
		if(!dnpl.equals("")){
			if(dnpl >= 20 && dnpl <= 40){
				
				dnpl = parseFloat(elemento.value).toFixed(2);	
				
				if(lado.equals("derecha")){		
					
					var res = parseFloat(dnpl) - parseFloat(1);
					String adicion = graduacionesForm.getOD_adicion();
					
					if(!adicion.equals("")){
						document.getElementById('OD_dnpc').value = parseFloat(res).toFixed(2);
					}else{
						document.getElementById('OD_dnpc').value="";
					}
					elemento.value = parseFloat(dnpl).toFixed(2);
					return true;
					
				}else if(lado.equals("izquierda")){
					
					var res = parseFloat(dnpl) - parseFloat(1);				
					org.zkoss.zhtml.Var adicion = document.getElementById('OI_adicion').value;
					
					if(!adicion.equals("")){
						document.getElementById('OI_dnpc').value = parseFloat(res).toFixed(2);
					}else{
						document.getElementById('OI_dnpc').value="";
					}				
					elemento.value = parseFloat(dnpl).toFixed(2);
					return true;	
				}
			}else{			
				Messagebox.show("Distancia Naso pupilar, valor esta fuera de rango");
				return false;	
			}
		}else{		
			if(lado.equals("derecha")){				
				graduacionesForm.setOD_dnpc("");
				
			}else if(lado.equals("izquierda")){
				
				graduacionesForm.setOI_dnpc("");
			}
			return false;	
		}	
			
	}

	public boolean validaDNPC(String elemento, String lado){
		
		String dnpc = elemento.trim();		
		
		if(!dnpc.equals("")){
			if(dnpc >= 20 && dnpc <= 40){
			
				if(lado.equals("derecha")){			
					String adicion = graduacionesForm.getOD_adicion().trim();
					
					if(adicion.equals("")){
						Messagebox.show("Debe ingresar receta de cerca");
						graduacionesForm.setOD_dnpc("");						
						return false;
					}
					
				}else if("izquierda" == lado){
					
					String adicion = graduacionesForm.getOI_adicion().trim();
					
					if(adicion.equals("")){
						Messagebox.show("Debe ingresar receta de cerca");
						graduacionesForm.setOI_dnpc("");						
						return false;
					}
				}		
			
			}else{
				Messagebox.show("Distancia Naso pupilar cerca, valor esta fuera de rango");
				return false;	
			}
		}	
		return true;
	}

	public boolean validarSiNumero(String numero){
	   // if (!/^([0-9])*$/.test(numero)){
	    if (Pattern.matches("/^([0-9])*$/", numero)) {	
	    	return false;
	    }else{
	    	return true;
	    }
	}

/*
	public void limpiar_pantalla(){
		
		document.getElementById('OD_esfera').value = "";				
		document.getElementById('OD_cilindro').value = "";				
		document.getElementById('OD_eje').value = "";	
		document.getElementById('OD_cerca').value = "";			
		document.getElementById('OD_adicion').value = "";	
		document.getElementById('OD_dnpl').value = "";
		document.getElementById('OD_dnpc').value = "";
		document.getElementById('OD_avsc').value = "";
		document.getElementById('OD_avcc').value = "";		
					
		document.getElementById('OI_esfera').value = "";				
		document.getElementById('OI_cilindro').value = "";				
		document.getElementById('OI_eje').value = "";
		document.getElementById('OI_cerca').value = "";						
		document.getElementById('OI_adicion').value = "";
		document.getElementById('OI_dnpl').value = "";
		document.getElementById('OI_dnpc').value = "";
		document.getElementById('OI_avsc').value = "";
		document.getElementById('OI_avcc').value = "";
		
		document.getElementById('OD_cantidad').value = "";
		document.getElementById('OD_base').value = "";
		document.getElementById('OD_altura').value = "";
		
		document.getElementById('OI_cantidad').value = "";
		document.getElementById('OI_base').value = "";
		document.getElementById('OI_altura').value = "";
		
	}
*/
	public boolean valida_informacion_doctor(){
		
		String rut = medicoForm.getRut().trim();	
		String apellidos = medicoForm.getApellidos().trim();		
		String nombres = medicoForm.getNombres().trim();		
		String provincia = medicoForm.getProvinci();
		
		if(rut.equals("")){
			Messagebox.show("Debe ingresar rut");
			return false;
		}
		
		if(apellidos.equals("")){
			Messagebox.show("Debe ingresar apellido");
			return false;
		}
		
		if(nombres.equals("")){
			Messagebox.show("Debe ingresar nombre");
			return false;
		}
		
		if(provincia.equals("-1")){
			Messagebox.show("Debe seleccionar provincia");
			return false;
		}
		
		return true;
	}

	
	//original
	
private boolean validaInformacion() {
		
		String nombre_cliente="";	
		String codigo_cliente="";
		String doctor="";
		String agente="";
		
		String OD_cantidad="";
		String OI_cantidad="";
		String OD_base="";
		String OI_base="";
		String fechaEmision="";
	
		nombre_cliente = graduacionesForm.getNombre_cliente().trim();	
		
		if(nombre_cliente.equals("")){
			Messagebox.show("Debe ingresar cliente");
			return false;
		}
		
		codigo_cliente = graduacionesForm.getNombre_cliente().trim();	
		
		if(codigo_cliente.equals("") || codigo_cliente.equals("0")){
			
			if(codigo_cliente.equals("0")){
				Messagebox.show("Debe buscar un cliente");
			}else{
				Messagebox.show("Debe ingresar un cliente");
			}
			
			return false;
		}
		
		doctor = graduacionesForm.getDoctor();
		if(doctor.equals("")){
			Messagebox.show("Debe ingresar un doctor");
			return false;		
		}
		
		agente = graduacionesForm.getAgente();
		if(graduacionesForm.getAgente().equals("-1")){
			Messagebox.show("Debe Seleccionar agente");
			return false;		
		}
		
		fechaEmision = graduacionesForm.getFechaEmision().trim();		
		
		if(fechaEmision.equals("")){
			Messagebox.show("Debe ingresar fecha emision");
			return false;
		}
		
		OD_cantidad = graduacionesForm.getOD_cantidad().trim();		
		
		if(!OD_cantidad.equals("")  && !OD_cantidad.equals("-1") ){
			
			OD_base = graduacionesForm.getOD_base().trim();
			
			if(OD_base.equals("")  || OD_base.equals("Seleccione")){				
				Messagebox.show("Debe seleccionar Base de Prisma Derecho");
				return false;
			}
			
		}
		
		OI_cantidad = graduacionesForm.getOI_cantidad().trim();
		
		if(!OI_cantidad.equals("")  && !OI_cantidad.equals("-1") ){
			
			OI_base = graduacionesForm.getOI_base().trim();
			
			if(OI_base.equals("")  || OI_base.equals("Seleccione")){
				Messagebox.show("Debe seleccionar Base de Prisma Izquierdo");
				return false;
			}
			
		}
		
		OD_base = graduacionesForm.getOD_base().trim();
		
		if(!OD_base.equals("")  &&  !OD_base.equals("Seleccione")){
			
			OD_cantidad = graduacionesForm.getOD_cantidad().trim();	
			
			if(OD_cantidad.equals("")  || OD_cantidad.equals("-1") ){
				
				Messagebox.show("Debe seleccionar Cantidad de Prisma Derecho");
				return false;
			}
		}
		
		OI_base = graduacionesForm.getOI_base().trim();
		
		if(!OI_base.equals("") && !OI_base.equals("Seleccione")){
			
			OI_cantidad = graduacionesForm.getOI_cantidad().trim();
			
			if(OI_cantidad.equals("") || OI_cantidad.equals("-1") ){
				
				Messagebox.show("Debe seleccionar Cantidad de Prisma Izquierdo");
				return false;
			}
		}	
		
		return true;
	}
	
	
	private boolean validaGraduacion(){
		
		String esferaD="";
		String cilindroD="";
		String ejeD="";
		String adicionD="";
		String OD_dnpl="";
		
		String esferaI = "";				
		String cilindroI = "";			
		String ejeI = "";		
		String adicionI = "";		
		String OI_dnpl = "";	
		
		boolean respuesta=false;
		boolean respuestadnpc=false;		
		
		/********* VALIDACIONES OJO DERECHO ********/
		esferaD = graduacionesForm.getOD_esfera().trim();		
		cilindroD = graduacionesForm.getOD_cilindro().trim();		
		ejeD = graduacionesForm.getOD_eje().trim();			
		adicionD = graduacionesForm.getOD_adicion().trim();		
		OD_dnpl = graduacionesForm.getOD_dnpl().trim();		
		
		if(!esferaD.equals("") && !esferaD.equals(null)){
			
			int esfera_D = Integer.parseInt(esferaD);
			
			if(Integer.parseInt(esferaD) < -30 || Integer.parseInt(esferaD) >30){
				Messagebox.show("El valor esfera derecha esta fuera del rango permitido -30 y 30");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar esfera derecha.");
			return false;
		}	
		
		
		if(!cilindroD.equals("") && !cilindroD.equals(null)){		
			
			if(Integer.parseInt(cilindroD) < -8  || Integer.parseInt(cilindroD) > 8){
				Messagebox.show("El valor cilindro derecho esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro derecho");
			return false;
		}
		
		
		int intCilindro = Integer.parseInt(cilindroD);
		
		if((!cilindroD.equals("") && !cilindroD.equals(null)) && (intCilindro != 0)){
			if(ejeD.equals("") || ejeD.equals(null)){
				Messagebox.show("Debe ingresar eje derecho");
				return false;
			}
		}		
		
		if(!ejeD.equals("") && !ejeD.equals(null)){
			if(cilindroD.equals("") || cilindroD.equals(null)){
				Messagebox.show("Debe ingresar cilindro derecho");
				return false;
			}
		}	
		
		if(!ejeD.equals("") && !ejeD.equals(null)){
			if(Integer.parseInt(ejeD) < 0 || Integer.parseInt(ejeD) >180){
				Messagebox.show("El valor del eje derecho esta fuera de rango");
				return false;
			}
		}
		
		
		if(!adicionD.equals("") && !adicionD.equals(null)){
			if(Float.valueOf(adicionD) < 0.50 || Float.valueOf(adicionD) > 4){
				Messagebox.show("El valor de la adicion derecha esta fuera de rango");
				return false;
			}
		}
		
		if(OD_dnpl.equals("") || OD_dnpl.equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar derecha.");
			return false;
		}else{
			
			respuesta = validaDNPL(graduacionesForm.getOD_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}
		
		/********* FIN VALIDACIONES OJO DERECHO ********/
		
		
		
		/********* VALIDACIONES OJO IZQUIERDO ********/
		
		esferaI = graduacionesForm.getOI_esfera().trim();		
		cilindroI = graduacionesForm.getOI_cilindro().trim();		
		ejeI = graduacionesForm.getOI_eje().trim();			
		adicionI = graduacionesForm.getOI_adicion().trim();		
		OI_dnpl = graduacionesForm.getOI_dnpl().trim();		
		
		if(!esferaI.equals("") && !esferaI.equals(null)){
			if(Integer.parseInt(esferaI) < -30 || Integer.parseInt(esferaI) >30){
				Messagebox.show("El valor esfera izquierda esta fuera de rango");
				return false;
			}	
		}else{
			Messagebox.show("Debe ingresar esfera izquierda.");
			return false;
		}
		
		if(!cilindroI.equals("") && !cilindroI.equals(null)){		
			if(Integer.parseInt(cilindroI) < -8  || Integer.parseInt(cilindroI) > 8){
				Messagebox.show("El valor cilindro izquierdo esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro izquierdo");
			return false;
		}
		
		if(!cilindroI.equals("") && !cilindroI.equals(null) && (Integer.parseInt(cilindroI)!=0)){
			if(ejeI.equals("") || ejeI.equals(null)){
				Messagebox.show("Debe ingresar eje izquierdo");
				return false;
			}
		}	
		
		if(!ejeI.equals("") && !ejeI.equals(null)){
			if(cilindroI.equals("") || cilindroI.equals(null)){
				Messagebox.show("Debe ingresar cilindro izquierdo");
				return false;
			}
		}
		
		if(!ejeI.equals("") && !ejeI.equals(null)){
			if(Integer.parseInt(ejeI) < 0 || Integer.parseInt(ejeI) >180){
				Messagebox.show("El valor del eje izquierdo esta fuera de rango");
				return false;
			}
		}
		
		
		if(!adicionI.equals("") && !adicionI.equals(null)){
			if(Integer.parseInt(adicionI) <= 0 || Integer.parseInt(adicionI) > 4){
				Messagebox.show("El valor de la adicion izquierda esta fuera de rango");
				return false;
			}
		}
		
		if(OI_dnpl.equals("") || OI_dnpl.equals(null)){
			Messagebox.show("Debe ingresar distancia naso pupilar izquierda.");
			return false;
		}else{
			 respuesta = validaDNPL(graduacionesForm.getOI_dnpl(), "izquierda");
			if(respuesta == false){
				return false;
			}
		}
		
		respuestadnpc = validaDNPC(graduacionesForm.getOD_dnpl(), "derecha");
		
		if(false){
			return false;
		}
		
		/********* FIN VALIDACIONES OJO IZQUIERDO ********/		
		
		String fechaProxRevision = graduacionesForm.getFechaProxRevision().trim();
		String fechaEmision = graduacionesForm.getFechaEmision().trim();			
		
		if(fechaEmision.equals("")){
			Messagebox.show("Debe ingresar fecha de emision");
			return false;
		}		
		
		if(fechaProxRevision.equals("")){
			Messagebox.show("Debe ingresar fecha de revision");
			return false;
		}
		
		return true;
}
	
	private boolean validaDNPL(String elemento, String lado){
		
		String dnpl = elemento.trim();
		String adicion="";
		Float res = Float.valueOf("0.00");
		
		if(!dnpl.equals("")){
			
			if(Integer.parseInt(dnpl) >= 20 && Integer.parseInt(dnpl) <= 40){
				
				dnpl = elemento;
				
				if(lado.equals("derecha")){	
					
					res = Float.valueOf(dnpl) - 1;					
					adicion = graduacionesForm.getOD_adicion();
					
					if(!adicion.equals("")){						
						graduacionesForm.setOD_dnpc(res.toString());
					}else{
						graduacionesForm.setOD_dnpc("");
					}
					
					elemento = dnpl;
					return true;
					
				}else if(lado.equals("izquierda")){
					
					res = Float.valueOf(dnpl) - 1;					
					adicion = graduacionesForm.getOI_adicion();
					
					if(!adicion.equals("")){						
						graduacionesForm.setOI_dnpc(res.toString());
					}else{
						graduacionesForm.setOI_dnpc("");
					}			
					
					elemento = dnpl;
					return true;	
				}
			}else{			
				Messagebox.show("Distancia Naso pupilar, valor esta fuera de rango");
				return false;	
			}
		}else{		
			if(lado.equals("derecha")){				
				graduacionesForm.setOD_dnpc("");
				
			}else if(lado.equals("izquierda")){
				graduacionesForm.setOI_dnpc("");
			}
			
			return false;
		}	
		
		return false;
			
	}

	private boolean validaDNPC(String elemento, String lado){
		
		String dnpc = elemento.trim();	
		String adicion="";
		
		if(!dnpc.equals("")){
			if(Integer.parseInt(dnpc) >= 20 && Integer.parseInt(dnpc) <= 40){
			
				if(lado.equals("derecha")){			
					adicion = graduacionesForm.getOD_adicion().trim();					
					
					if(adicion.equals("")){
						Messagebox.show("Debe ingresar receta de cerca");						
						graduacionesForm.setOD_dnpc("");
						return false;
					}
					
				}else if(lado.equals("izquierda")){
					adicion = graduacionesForm.getOI_adicion().trim();					
					
					if(adicion.equals("")){
						Messagebox.show("Debe ingresar receta de cerca");
						graduacionesForm.setOD_dnpc("");
						return false;
					}
				}		
			
			}else{
				Messagebox.show("Distancia Naso pupilar cerca, valor esta fuera de rango");
				return false;	
			}
		}	
		return true;
	}

	
	
	

}
