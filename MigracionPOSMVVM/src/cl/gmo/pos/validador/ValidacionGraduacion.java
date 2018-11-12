package cl.gmo.pos.validador;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.zkoss.zul.Messagebox;

import cl.gmo.pos.venta.web.forms.GraduacionesForm;

public class ValidacionGraduacion implements Serializable {

	
	private static final long serialVersionUID = 2027012303185061783L;
	
	private BeanGraduaciones  beanGraduaciones;
	private GraduacionesForm  graduacionesForm;
	
	
	public ValidacionGraduacion(){
		beanGraduaciones = new BeanGraduaciones();
		graduacionesForm = new GraduacionesForm();
	} 
	
	
	public void validaEsfera(float elemento, String lado){
		
		
		float mult = 0.25F;
		float cont = 0;		
		float esfera= 0.0F;	
		float elementoadicion=0;
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
			
		if(elemento != 0){
			
			esfera = elemento;
			
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
						 elementoadicion = beanGraduaciones.getOD_adicion();
						 validaAdicion(elementoadicion, lado);
						 
					 }else if(lado.equals("izquierda")){
						 elementoadicion = beanGraduaciones.getOI_adicion();
						 validaAdicion(elementoadicion, lado);
					 }
				}else{	
					
					 elemento = esfera;	
					 
					 if(lado.equals("derecha")){
						 elementoadicion = beanGraduaciones.getOD_adicion();
						 validaAdicion(elementoadicion, lado);
						 
					 }else if(lado.equals("izquierda")){
						 elementoadicion = beanGraduaciones.getOI_adicion();
						 validaAdicion(elementoadicion, lado);
					 }
				}
			}else{
				Messagebox.show("El valor esfera "+lado+" esta fuera del rango permitido -30 y 30");
				elemento=0;
				//elemento.focus();
			}
		}else{
			esfera = 0;
			Messagebox.show("Debe ingresar valores entre -30 y 30");
			elemento=0;
			//elemento.value = parseFloat(esfera).toFixed(2);
			//elemento.focus();
		}		
	}
	
	
	public void validaAdicion(float elemento, String lado){
		
		float esfera = 0;
		float dnpl = 0;
		float adicion =0;
		
		
		adicion = elemento;		
		
		
		if(lado.equals("derecha")){	
			esfera = beanGraduaciones.getOD_esfera();			
			dnpl = beanGraduaciones.getOD_dnpl();
			
		}else if(lado.equals("izquierda")){
			esfera = beanGraduaciones.getOI_esfera();			
			dnpl = beanGraduaciones.getOI_dnpl();			
		}		
		
		if(esfera != 0){	
			
			if(lado.equals("derecha")){
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(adicion != 0){
					
					if(adicion >0 && adicion <=4){
						
						float cerca = adicion + esfera;
						
						//document.getElementById('OD_cerca').value = cerca.toFixed(2);						
						elemento = adicion;//.toFixed(2);
						dnpl = beanGraduaciones.getOD_dnpl();
						validaDNPL(dnpl, lado);
						
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
						}
					}	
					
				}else{
					beanGraduaciones.setOD_cerca(0);				
					beanGraduaciones.setOD_dnpl(0);
					beanGraduaciones.setOD_dnpc(0);			
				}		
				
			}else if(lado.equals("izquierda")){
				
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(adicion != 0){
					
					if(adicion >0 && adicion <=4){
						
						float cerca = adicion + esfera;
						//document.getElementById('OI_cerca').value = cerca.toFixed(2);
						elemento = adicion; //.toFixed(2);
						dnpl = beanGraduaciones.getOI_dnpl();
						validaDNPL(dnpl, lado);
					}else{
						if(adicion <= 0 ){						
							Messagebox.show("El valor de la adicion no puede ser menor o igual a 0");
						}else{
							Messagebox.show("El valor de la adicion no puede ser mayor a 4");
						}
						
					}					
				}else{
					beanGraduaciones.setOI_cerca(0);
					beanGraduaciones.setOI_dnpl(0);
					beanGraduaciones.setOI_dnpc(0);
				}			
			}	
		}else{
			Messagebox.show("Debe ingresar esfera "+lado+"");		
		}
	}
	
	
	public boolean validaDNPL(float elemento, String lado){
		
		float dnpl = elemento;	
		float adicion = 0;
		float res=0;
		
		if(dnpl != 0){
			
			if(dnpl >= 20 && dnpl <= 40){
				
				//dnpl = parseFloat(elemento.value).toFixed(2);	
				
				if(lado.equals("derecha")){		
					
					res = dnpl - 1;
					adicion = beanGraduaciones.getOD_adicion();
			
					if(adicion != 0){
						beanGraduaciones.setOD_dnpc(res);
						//document.getElementById('OD_dnpc').value = parseFloat(res).toFixed(2);
					}else{
						beanGraduaciones.setOD_dnpc(0);
					}
					
					//elemento = parseFloat(dnpl).toFixed(2);
					elemento = dnpl;
					return true;
					
				}else if(lado.equals("izquierda")){
					
					res = dnpl - 1;				
					adicion = beanGraduaciones.getOI_adicion();
					
					if(adicion != 0){
						beanGraduaciones.setOI_dnpc(res);
						//document.getElementById('OI_dnpc').value = parseFloat(res).toFixed(2);
					}else{
						//document.getElementById('OI_dnpc').value="";
						beanGraduaciones.setOI_dnpc(0);
					}				
					
					//elemento.value = parseFloat(dnpl).toFixed(2);
					elemento = dnpl;
					return true;	
				}
			}else{			
				Messagebox.show("Distancia Naso pupilar, valor esta fuera de rango");
				return false;	
			}
		}else{		
			if(lado.equals("derecha")){					
				beanGraduaciones.setOD_dnpc(0);
				
			}else if(lado.equals("izquierda")){
				beanGraduaciones.setOI_dnpc(0);
				
			}
			return false;	
		}	
		
		
		return false;	
	}


	
	public void validaCilindro(float elemento, String lado){
		
		float cilindro=0; 	
		float mult = 0.25F;
		float cont = 0;
		float intCilindro=0;
			
		if(cilindro != 0){
				
				cilindro = elemento;
				
				if(cilindro >= -8 && cilindro <= 8){
					
					//cilindro = parseFloat(elemento.value).toFixed(2);
					
					if (cilindro%mult != 0){	
						
						 while((cilindro%mult != 0) && (cont < 55)){				 
							 
							 if(cilindro > 0){
								 cilindro = cilindro + 0.01F;
							 }else{
								 cilindro = cilindro + (-0.01F);
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
					elemento=0;
					//elemento.focus();
				}
			}else{
				cilindro = 0;
				Messagebox.show("Debe ingresar valores entre -8 y 8");
				elemento = 0;
				//elemento.value = parseFloat(cilindro).toFixed(2);
				//elemento.focus();
			}
			
			intCilindro = cilindro;
			
			if(((intCilindro >= -8 && intCilindro < 0) || (intCilindro > 0 && intCilindro <= 8)) || (intCilindro >= -8.00 && intCilindro < 0.00) || (intCilindro > 0.00 && intCilindro <= 8.00) ){
				
				if(lado.equals("derecho")){				
					// document.getElementById('OD_eje').disabled =false;	
					
				}else if(lado.equals("izquierda")){
					//document.getElementById('OI_eje').disabled =false;
				}			
			}else{
				
				if(lado.equals("derecho")){	
					beanGraduaciones.setOD_eje(0);					
					//document.getElementById('OD_eje').disabled =true;		
					
				}else if(lado.equals("izquierda")){				
					beanGraduaciones.setOI_eje(0);
					//document.getElementById('OI_eje').disabled =true;
				}
			}			
	}
	
	
	public void validaEje(float elemento, String lado){
		
		float eje = elemento;
		boolean esnumero=false;
		
		if(eje != 0){		
			
			//esnumero = validarSiNumero(eje);
			
			//if(true == esnumero){
				if(eje < 0 || eje >180){
					Messagebox.show("El valor del eje "+lado+" esta fuera de rango [0..100]");
					eje = 0;
					elemento = 0;
				}
			/*}else{
				Messagebox.show("Debe ingresar solo numeros entre 0 y 180");
			}	*/	
		}	
	}
	
	
	public void validacionCerca(float elemento, String lado){
		
		float esfera = 0;
		float cerca = elemento;	
		float adicion = 0;
		float dnpl = 0;
		
		if(lado.equals("derecha")){	
			esfera = beanGraduaciones.getOD_esfera();			
			
		}else if(lado.equals("izquierda")){
			esfera = beanGraduaciones.getOI_esfera();
			
		}		
		
		if(esfera != 0){		
			if(lado.equals("derecha")){	
				
				//esfera = parseFloat(document.getElementById('OD_esfera').value).toFixed(2);
				
				if(cerca !=0){
					
					if(cerca > esfera){
						
						adicion = cerca - esfera;
						beanGraduaciones.setOD_adicion(adicion);
						//document.getElementById('OD_adicion').value = adicion.toFixed(2);
						//elemento.value = parseFloat(cerca).toFixed(2);
						elemento=cerca;
						dnpl = beanGraduaciones.getOD_dnpl();
						validaDNPL(dnpl, lado);
						
					}else{
						Messagebox.show("El valor de esfera de cerca no puede ser menor o igual a esfera");
					}	
					
				}		
				
			}else if(lado.equals("izquierda")){
				//esfera = parseFloat(document.getElementById('OI_esfera').value).toFixed(2);
				
				if(cerca != 0){
					if(cerca > esfera){
						adicion = cerca - esfera;
						beanGraduaciones.setOI_adicion(adicion);
						//document.getElementById('OI_adicion').value = adicion.toFixed(2);
						//elemento.value = parseFloat(cerca).toFixed(2); 
						elemento=cerca;
						dnpl = beanGraduaciones.getOI_dnpl();
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


	public boolean validaDNPC(float elemento, String lado){
		
		float dnpc = elemento;	
		float adicion = 0;
		
		if(dnpc != 0){
			
			if(dnpc >= 20 && dnpc <= 40){
			
				if(lado.equals("derecha")){			
					adicion = beanGraduaciones.getOD_adicion();
					
					if(adicion == 0){
						Messagebox.show("Debe ingresar receta de cerca");
						beanGraduaciones.setOD_dnpc(0);						
						return false;
					}
					
				}else if("izquierda" == lado){
					
					adicion = beanGraduaciones.getOI_adicion();
					
					if(adicion == 0){
						Messagebox.show("Debe ingresar receta de cerca");
						beanGraduaciones.setOI_dnpc(0);						
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

	
	public boolean validaGraduacion(){
		
		float esferaD = 0;
		float cilindroD = 0;
		float ejeD = 0;
		float adicionD=0;
		float OD_dnpl=0;
		
		float esferaI = 0;
		float cilindroI = 0;
		float ejeI = 0;
		float adicionI=0;
		float OI_dnpl=0;
		float intCilindro=0;
		
		/********* VALIDACIONES OJO DERECHO ********/
		esferaD = beanGraduaciones.getOD_esfera();		
		cilindroD = beanGraduaciones.getOD_cilindro();		
		ejeD = beanGraduaciones.getOD_eje();			
		adicionD = beanGraduaciones.getOD_adicion();	
		OD_dnpl = beanGraduaciones.getOD_dnpl();
				
		
		if(esferaD != 0){
			if(esferaD < -30 || esferaD > 30){
				Messagebox.show("El valor esfera derecha esta fuera del rango permitido -30 y 30");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar esfera derecha.");
			return false;
		}		
		
		if(cilindroD != 0){		
			if(cilindroD < -8  || cilindroD > 8){
				Messagebox.show("El valor cilindro derecho esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro derecho");
			return false;
		}
		
		
		intCilindro = cilindroD;
		
		if(cilindroD != 0){
			if(ejeD == 0){
				Messagebox.show("Debe ingresar eje derecho");
				return false;
			}
		}
		
		
		if(ejeD != 0){
			if(cilindroD == 0){
				Messagebox.show("Debe ingresar cilindro derecho");
				return false;
			}
		}	
		
		if(ejeD != 0){
			if(ejeD < 0 || ejeD >180){
				Messagebox.show("El valor del eje derecho esta fuera de rango");
				return false;
			}
		}
		
		
		if(adicionD != 0){
			if(adicionD < 0.50 || adicionD > 4){
				Messagebox.show("El valor de la adicion derecha esta fuera de rango");
				return false;
			}
		}
		
		if(OD_dnpl == 0){
			Messagebox.show("Debe ingresar distancia naso pupilar derecha.");
			return false;
		}else{
			boolean respuesta = validaDNPL(beanGraduaciones.getOD_dnpl(), "derecha");
			if(respuesta == false){
				return false;
			}
		}
		
		
		
		/********* FIN VALIDACIONES OJO DERECHO ********/
		
		
		
		/********* VALIDACIONES OJO IZQUIERDO ********/
		
		esferaI = beanGraduaciones.getOI_esfera();		
		cilindroI = beanGraduaciones.getOI_cilindro();	
		ejeI = beanGraduaciones.getOI_eje();		
		adicionI = beanGraduaciones.getOI_adicion();		
		OI_dnpl = beanGraduaciones .getOI_dnpl();
		
		
		if(esferaI != 0){
			if(esferaI < -30 || esferaI >30){
				Messagebox.show("El valor esfera izquierda esta fuera de rango");
				return false;
			}	
		}else{
			Messagebox.show("Debe ingresar esfera izquierda.");
			return false;
		}
		
		if(cilindroI != 0){		
			if(cilindroI < -8  || cilindroI > 8){
				Messagebox.show("El valor cilindro izquierdo esta fuera de rango");
				return false;
			}		
		}else{
			Messagebox.show("Debe ingresar valor del cilindro izquierdo");
			return false;
		}
		
		if(cilindroI != 0){
			if(ejeI == 0){
				Messagebox.show("Debe ingresar eje izquierdo");
				return false;
			}
		}	
		
		if(ejeI != 0){
			if(cilindroI == 0){
				Messagebox.show("Debe ingresar cilindro izquierdo");
				return false;
			}
		}
		if(ejeI != 0){
			if(ejeI < 0 || ejeI >180){
				Messagebox.show("El valor del eje izquierdo esta fuera de rango");
				return false;
			}
		}
		
		
		if(adicionI != 0){
			if(adicionI <= 0 || adicionI > 4){
				Messagebox.show("El valor de la adicion izquierda esta fuera de rango");
				return false;
			}
		}
		
		if(OI_dnpl == 0){
			Messagebox.show("Debe ingresar distancia naso pupilar izquierda.");
			return false;
		}else{
			boolean respuesta = validaDNPL(beanGraduaciones.getOI_dnpl(), "izquierda");
			if(respuesta == false){
				return false;
			}
		}
		
		boolean respuestadnpc = validaDNPC(beanGraduaciones.getOD_dnpc(), "derecha");
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




}
