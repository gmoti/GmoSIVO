package cl.gmo.pos.venta.web.beans;

import java.util.ArrayList;

public class FichaTecnicaBean {
	
	
	//Cabecera de la Ficha Taller
	private String numero_encargo="";
	private String numero_encargo2="";
	private String imagen="";
	private String imagen_barra="";
	private int grupo=0;
	private String caja="";
	private String agente="";
	private String nombre_agente="";
	private String fecha_encargo="";
	private String fecha_entrega="";
	private int codigo_cliente=0;
	private String nombre_cliente="";
	private String apellido_cliente="";
	private String telefono_cliente="";
	private String rut_cliente="";
	private String varchar="";
	private String especialidad="";
	private int saldo=0;
	private String codigo_medico="";
	private String medico="";
	private String telefono_medico="";
	private String esMultioferta = "";
	private String nota="";
	
	//Lineas de la Ficha Taller
	//Armazon
	private int ident_linea_armazon=0;
	private String grupo_armazon = "";
	private String cod_pedvtcb = "";
	private String codigo_pedvtln = ""; 
	private String cod_armazon = "";
	private String descripcion_armazon = "";
	private String color_armazon = "";
	private String material_armazon = "";
	private String sexo_armazon = "";
	private String calibre_armazon = "";
	private String puente_armazon = "";
	private String altura_armazon = "";
	private String estilo_armazon = "";
	
	//Cristal D
	private int ident_linea_cristalD=0;
	private String grupo_cristalD = "";
	private String cod_pedvtcb_cristalD = "";
	private String cod_critalD = "";
	private String descripcion_critalD = "";
	private String ojo_cristalD = "";
	private String esfera_cristalD = "";
	private String cilindro_cristalD = "";
	private String eje_critalD = "";
	private String dnpl_critalD = "";
	private String dnpc_critalD = "";
	private String adicion_critalD = "";
	private String color_cristalD = "";
	private String trat_cristalD = "";
	private String tipo_cristalD = "";
	private String ind_cristalD = "";
	private String diametro_cristalD = "";
	
	
	private String suplemento_cristaD = "";
	private String valor_suple_cristalD = "";
	
	//Cristal I
	private int ident_linea_cristalI=0;
	private String grupo_cristalI = "";
	private String cod_cristalI = "";
	private String cod_pedvtcb_cristalI = "";
	private String descripcion_critalI = "";
	private String ojo_cristalI = "";
	private String esfera_cristalI = "";
	private String cilindro_cristalI = "";
	private String eje_critalI = "";
	private String dnpl_critalI = "";
	private String dnpc_critalI = "";
	private String adicion_critalI = "";
	private String color_cristalI = "";
	private String trat_cristalI = "";
	private String tipo_cristalI = "";
	private String ind_cristalI = "";
	private String diametro_cristalI = "";
	
	private String suplemento_cristaI = "";
	private String valor_suple_cristalI = "";
	
	
	private ArrayList<FichaTecnicaBean> lista_cabeceras;
	private ArrayList<FichaTecnicaBean> lista_armazones;
	private ArrayList<FichaTecnicaBean> lista_cristales;
	
	//LMARIN 20140903
	private String region = "";	
	
	
	


	public ArrayList<FichaTecnicaBean> getLista_armazones() {
		return lista_armazones;
	}

	
	public void setLista_armazones(ArrayList<FichaTecnicaBean> lista_armazones) {
		this.lista_armazones = lista_armazones;
	}

	public ArrayList<FichaTecnicaBean> getLista_cristales() {
		return lista_cristales;
	}
	
	
	
	public String getCodigo_medico() {
		return codigo_medico;
	}

	public void setCodigo_medico(String codigo_medico) {
		this.codigo_medico = codigo_medico;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public String getTelefono_medico() {
		return telefono_medico;
	}

	public void setTelefono_medico(String telefono_medico) {
		this.telefono_medico = telefono_medico;
	}

	public String getNumero_encargo2() {
		return numero_encargo2;
	}

	public void setNumero_encargo2(String numero_encargo2) {
		this.numero_encargo2 = numero_encargo2;
	}

	public void setLista_cristales(ArrayList<FichaTecnicaBean> lista_cristales) {
		this.lista_cristales = lista_cristales;
	}

	public int getIdent_linea_cristalD() {
		return ident_linea_cristalD;
	}

	public void setIdent_linea_cristalD(int ident_linea_cristalD) {
		this.ident_linea_cristalD = ident_linea_cristalD;
	}

	public String getGrupo_cristalD() {
		return grupo_cristalD;
	}

	public void setGrupo_cristalD(String grupo_cristalD) {
		this.grupo_cristalD = grupo_cristalD;
	}

	

	public String getCod_pedvtcb_cristalD() {
		return cod_pedvtcb_cristalD;
	}

	public void setCod_pedvtcb_cristalD(String cod_pedvtcb_cristalD) {
		this.cod_pedvtcb_cristalD = cod_pedvtcb_cristalD;
	}

	public String getCod_pedvtcb_cristalI() {
		return cod_pedvtcb_cristalI;
	}

	public void setCod_pedvtcb_cristalI(String cod_pedvtcb_cristalI) {
		this.cod_pedvtcb_cristalI = cod_pedvtcb_cristalI;
	}

	public int getIdent_linea_cristalI() {
		return ident_linea_cristalI;
	}

	public void setIdent_linea_cristalI(int ident_linea_cristalI) {
		this.ident_linea_cristalI = ident_linea_cristalI;
	}

	public String getGrupo_cristalI() {
		return grupo_cristalI;
	}

	public void setGrupo_cristalI(String grupo_cristalI) {
		this.grupo_cristalI = grupo_cristalI;
	}

	public String getCod_cristalI() {
		return cod_cristalI;
	}

	public void setCod_cristalI(String cod_cristalI) {
		this.cod_cristalI = cod_cristalI;
	}

	public String getCod_pedvtcb() {
		return cod_pedvtcb;
	}

	public void setCod_pedvtcb(String cod_pedvtcb) {
		this.cod_pedvtcb = cod_pedvtcb;
	}

	

	public ArrayList<FichaTecnicaBean> getLista_cabeceras() {
		return lista_cabeceras;
	}

	public void setLista_cabeceras(ArrayList<FichaTecnicaBean> lista_cabeceras) {
		this.lista_cabeceras = lista_cabeceras;
	}

	public String getApellido_cliente() {
		return apellido_cliente;
	}

	public void setApellido_cliente(String apellido_cliente) {
		this.apellido_cliente = apellido_cliente;
	}

	public String getNumero_encargo() {
		return numero_encargo;
	}

	public void setNumero_encargo(String numero_encargo) {
		this.numero_encargo = numero_encargo;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public String getNombre_agente() {
		return nombre_agente;
	}

	public void setNombre_agente(String nombre_agente) {
		this.nombre_agente = nombre_agente;
	}

	public String getFecha_encargo() {
		return fecha_encargo;
	}

	public void setFecha_encargo(String fecha_encargo) {
		this.fecha_encargo = fecha_encargo;
	}

	public String getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public int getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(int codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	public String getNombre_cliente() {
		return nombre_cliente;
	}

	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	public String getTelefono_cliente() {
		return telefono_cliente;
	}

	public void setTelefono_cliente(String telefono_cliente) {
		this.telefono_cliente = telefono_cliente;
	}

	public String getRut_cliente() {
		return rut_cliente;
	}

	public void setRut_cliente(String rut_cliente) {
		this.rut_cliente = rut_cliente;
	}

	public String getVarchar() {
		return varchar;
	}

	public void setVarchar(String varchar) {
		this.varchar = varchar;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}


	public int getIdent_linea_armazon() {
		return ident_linea_armazon;
	}

	public void setIdent_linea_armazon(int ident_linea_armazon) {
		this.ident_linea_armazon = ident_linea_armazon;
	}

	public String getGrupo_armazon() {
		return grupo_armazon;
	}

	public void setGrupo_armazon(String grupo_armazon) {
		this.grupo_armazon = grupo_armazon;
	}

	public String getCodigo_pedvtln() {
		return codigo_pedvtln;
	}

	public void setCodigo_pedvtln(String codigo_pedvtln) {
		this.codigo_pedvtln = codigo_pedvtln;
	}

	public String getCod_armazon() {
		return cod_armazon;
	}

	public void setCod_armazon(String cod_armazon) {
		this.cod_armazon = cod_armazon;
	}

	public String getDescripcion_armazon() {
		return descripcion_armazon;
	}

	public void setDescripcion_armazon(String descripcion_armazon) {
		this.descripcion_armazon = descripcion_armazon;
	}

	public String getColor_armazon() {
		return color_armazon;
	}

	public void setColor_armazon(String color_armazon) {
		this.color_armazon = color_armazon;
	}

	public String getMaterial_armazon() {
		return material_armazon;
	}

	public void setMaterial_armazon(String material_armazon) {
		this.material_armazon = material_armazon;
	}

	public String getSexo_armazon() {
		return sexo_armazon;
	}

	public void setSexo_armazon(String sexo_armazon) {
		this.sexo_armazon = sexo_armazon;
	}

	public String getCalibre_armazon() {
		return calibre_armazon;
	}

	public void setCalibre_armazon(String calibre_armazon) {
		this.calibre_armazon = calibre_armazon;
	}

	public String getPuente_armazon() {
		return puente_armazon;
	}

	public void setPuente_armazon(String puente_armazon) {
		this.puente_armazon = puente_armazon;
	}

	public String getAltura_armazon() {
		return altura_armazon;
	}

	public void setAltura_armazon(String altura_armazon) {
		this.altura_armazon = altura_armazon;
	}

	public String getEstilo_armazon() {
		return estilo_armazon;
	}

	public void setEstilo_armazon(String estilo_armazon) {
		this.estilo_armazon = estilo_armazon;
	}

	public String getCod_critalD() {
		return cod_critalD;
	}

	public void setCod_critalD(String cod_critalD) {
		this.cod_critalD = cod_critalD;
	}

	public String getDescripcion_critalD() {
		return descripcion_critalD;
	}

	public void setDescripcion_critalD(String descripcion_critalD) {
		this.descripcion_critalD = descripcion_critalD;
	}

	public String getOjo_cristalD() {
		return ojo_cristalD;
	}

	public void setOjo_cristalD(String ojo_cristalD) {
		this.ojo_cristalD = ojo_cristalD;
	}

	public String getEsfera_cristalD() {
		return esfera_cristalD;
	}

	public void setEsfera_cristalD(String esfera_cristalD) {
		this.esfera_cristalD = esfera_cristalD;
	}

	public String getCilindro_cristalD() {
		return cilindro_cristalD;
	}

	public void setCilindro_cristalD(String cilindro_cristalD) {
		this.cilindro_cristalD = cilindro_cristalD;
	}

	public String getEje_critalD() {
		return eje_critalD;
	}

	public void setEje_critalD(String eje_critalD) {
		this.eje_critalD = eje_critalD;
	}

	public String getDnpl_critalD() {
		return dnpl_critalD;
	}

	public void setDnpl_critalD(String dnpl_critalD) {
		this.dnpl_critalD = dnpl_critalD;
	}

	public String getDnpc_critalD() {
		return dnpc_critalD;
	}

	public void setDnpc_critalD(String dnpc_critalD) {
		this.dnpc_critalD = dnpc_critalD;
	}

	public String getAdicion_critalD() {
		return adicion_critalD;
	}

	public void setAdicion_critalD(String adicion_critalD) {
		this.adicion_critalD = adicion_critalD;
	}

	public String getColor_cristalD() {
		return color_cristalD;
	}

	public void setColor_cristalD(String color_cristalD) {
		this.color_cristalD = color_cristalD;
	}

	public String getTrat_cristalD() {
		return trat_cristalD;
	}

	public void setTrat_cristalD(String trat_cristalD) {
		this.trat_cristalD = trat_cristalD;
	}

	public String getTipo_cristalD() {
		return tipo_cristalD;
	}

	public void setTipo_cristalD(String tipo_cristalD) {
		this.tipo_cristalD = tipo_cristalD;
	}

	public String getInd_cristalD() {
		return ind_cristalD;
	}

	public void setInd_cristalD(String ind_cristalD) {
		this.ind_cristalD = ind_cristalD;
	}

	public String getDiametro_cristalD() {
		return diametro_cristalD;
	}

	public void setDiametro_cristalD(String diametro_cristalD) {
		this.diametro_cristalD = diametro_cristalD;
	}

	public String getSuplemento_cristaD() {
		return suplemento_cristaD;
	}

	public void setSuplemento_cristaD(String suplemento_cristaD) {
		this.suplemento_cristaD = suplemento_cristaD;
	}

	public String getValor_suple_cristalD() {
		return valor_suple_cristalD;
	}

	public void setValor_suple_cristalD(String valor_suple_cristalD) {
		this.valor_suple_cristalD = valor_suple_cristalD;
	}

	

	public String getDescripcion_critalI() {
		return descripcion_critalI;
	}

	public void setDescripcion_critalI(String descripcion_critalI) {
		this.descripcion_critalI = descripcion_critalI;
	}

	public String getOjo_cristalI() {
		return ojo_cristalI;
	}

	public void setOjo_cristalI(String ojo_cristalI) {
		this.ojo_cristalI = ojo_cristalI;
	}

	public String getEsfera_cristalI() {
		return esfera_cristalI;
	}

	public void setEsfera_cristalI(String esfera_cristalI) {
		this.esfera_cristalI = esfera_cristalI;
	}

	public String getCilindro_cristalI() {
		return cilindro_cristalI;
	}

	public void setCilindro_cristalI(String cilindro_cristalI) {
		this.cilindro_cristalI = cilindro_cristalI;
	}

	public String getEje_critalI() {
		return eje_critalI;
	}

	public void setEje_critalI(String eje_critalI) {
		this.eje_critalI = eje_critalI;
	}

	public String getDnpl_critalI() {
		return dnpl_critalI;
	}

	public void setDnpl_critalI(String dnpl_critalI) {
		this.dnpl_critalI = dnpl_critalI;
	}

	public String getDnpc_critalI() {
		return dnpc_critalI;
	}

	public void setDnpc_critalI(String dnpc_critalI) {
		this.dnpc_critalI = dnpc_critalI;
	}

	public String getAdicion_critalI() {
		return adicion_critalI;
	}

	public void setAdicion_critalI(String adicion_critalI) {
		this.adicion_critalI = adicion_critalI;
	}

	public String getColor_cristalI() {
		return color_cristalI;
	}

	public void setColor_cristalI(String color_cristalI) {
		this.color_cristalI = color_cristalI;
	}

	public String getTrat_cristalI() {
		return trat_cristalI;
	}

	public void setTrat_cristalI(String trat_cristalI) {
		this.trat_cristalI = trat_cristalI;
	}

	public String getTipo_cristalI() {
		return tipo_cristalI;
	}

	public void setTipo_cristalI(String tipo_cristalI) {
		this.tipo_cristalI = tipo_cristalI;
	}

	public String getInd_cristalI() {
		return ind_cristalI;
	}

	public void setInd_cristalI(String ind_cristalI) {
		this.ind_cristalI = ind_cristalI;
	}

	public String getDiametro_cristalI() {
		return diametro_cristalI;
	}

	public void setDiametro_cristalI(String diametro_cristalI) {
		this.diametro_cristalI = diametro_cristalI;
	}

	public String getSuplemento_cristaI() {
		return suplemento_cristaI;
	}

	public void setSuplemento_cristaI(String suplemento_cristaI) {
		this.suplemento_cristaI = suplemento_cristaI;
	}

	public String getValor_suple_cristalI() {
		return valor_suple_cristalI;
	}

	public void setValor_suple_cristalI(String valor_suple_cristalI) {
		this.valor_suple_cristalI = valor_suple_cristalI;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	public String getEsMultioferta() {
		return esMultioferta;
	}


	public void setEsMultioferta(String esMultioferta) {
		this.esMultioferta = esMultioferta;
	}


	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getImagen_barra() {
		return imagen_barra;
	}

	public void setImagen_barra(String imagen_barra) {
		this.imagen_barra = imagen_barra;
	}
	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}
	
}
