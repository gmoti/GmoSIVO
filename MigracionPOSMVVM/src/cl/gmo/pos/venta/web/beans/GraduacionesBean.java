package cl.gmo.pos.venta.web.beans;

public class GraduacionesBean {

	private int cliente;
	private String agente;
	private String cod_doctor;
	private String tipo;
	private Double OD_esfera;
	private Double OD_cilindro;
	private Double OD_diametro;
	private Integer OD_eje;
	private Double OD_adicion;
	private Double OD_esfera_cerca;
	private Double OD_n;
	private Double OD_p;
	private Double OD_avsc;
	private Double OD_avcc;
	private String OD_obser;
	private String OD_cantidad;
	private String OD_base;
	private String OD_altura;
		
	private double OD_esfera_traspuesto;
	private double OD_cilindro_traspuesto;
	private int OD_eje_traspuesto;
	
	private Double OI_esfera;
	private Double OI_cilindro;
	private Integer OI_eje;
	private Double OI_adicion;
	private Double OI_diametro;
	private Double OI_esfera_cerca;
	private Double OI_n;
	private Double OI_p;
	private Double OI_avsc;
	private Double OI_avcc;
	private String OI_obser;
	private String OI_cantidad;
	private String OI_base;
	private String OI_altura; 
	private boolean Diferente_add;
	
	private double OI_esfera_traspuesto;
	private double OI_cilindro_traspuesto;
	private int OI_eje_traspuesto;
	
	private int numero;
	private String fecha = "";
	private int codigo;
	private String orden = "";
	private String doctor;
	private String fecha_emision;
	private String fecha_prox_revision;
	private String fecha_ant;
	
	private int ident;
	private int grupo;
	private String cod_pedtvcb;
	private int ident_tratami;
	private String cod_pedttvln;
	private String tratami;
	
		
	public boolean isDiferente_add() {
		return Diferente_add;
	}
	public void setDiferente_add(boolean diferente_add) {
		Diferente_add = diferente_add;
	}
	public String getFecha_ant() {
		return fecha_ant;
	}
	public void setFecha_ant(String fecha_ant) {
		this.fecha_ant = fecha_ant;
	}
	public Double getOI_avcc() {
		return OI_avcc;
	}
	public void setOI_avcc(Double oI_avcc) {
		OI_avcc = oI_avcc;
	}
	public int getCliente() {
		return cliente;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public String getAgente() {
		return agente;
	}
	public void setAgente(String agente) {
		this.agente = agente;
	}
	public String getCod_doctor() {
		return cod_doctor;
	}
	public void setCod_doctor(String cod_doctor) {
		this.cod_doctor = cod_doctor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getOD_esfera() {
		return OD_esfera;
	}
	public void setOD_esfera(Double oD_esfera) {
		OD_esfera = oD_esfera;
	}
	public Double getOD_cilindro() {
		return OD_cilindro;
	}
	public void setOD_cilindro(Double oD_cilindro) {
		OD_cilindro = oD_cilindro;
	}
	public Double getOD_diametro() {
		return OD_diametro;
	}
	public void setOD_diametro(Double oD_diametro) {
		OD_diametro = oD_diametro;
	}
	public Integer getOD_eje() {
		return OD_eje;
	}
	public void setOD_eje(Integer oD_eje) {
		OD_eje = oD_eje;
	}
	public Double getOD_adicion() {
		return OD_adicion;
	}
	public void setOD_adicion(Double oD_adicion) {
		OD_adicion = oD_adicion;
	}
	public Double getOD_esfera_cerca() {
		return OD_esfera_cerca;
	}
	public void setOD_esfera_cerca(Double oD_esfera_cerca) {
		OD_esfera_cerca = oD_esfera_cerca;
	}
	public Double getOD_n() {
		return OD_n;
	}
	public void setOD_n(Double oD_n) {
		OD_n = oD_n;
	}
	public Double getOD_p() {
		return OD_p;
	}
	public void setOD_p(Double oD_p) {
		OD_p = oD_p;
	}
	public Double getOD_avsc() {
		return OD_avsc;
	}
	public void setOD_avsc(Double oD_avsc) {
		OD_avsc = oD_avsc;
	}
	public Double getOD_avcc() {
		return OD_avcc;
	}
	public void setOD_avcc(Double oD_avcc) {
		OD_avcc = oD_avcc;
	}
	public String getOD_obser() {
		return OD_obser;
	}
	public void setOD_obser(String oD_obser) {
		OD_obser = oD_obser;
	}
	public String getOD_cantidad() {
		return OD_cantidad;
	}
	public void setOD_cantidad(String oD_cantidad) {
		OD_cantidad = oD_cantidad;
	}
	public String getOD_base() {
		return OD_base;
	}
	public void setOD_base(String oD_base) {
		OD_base = oD_base;
	}
	public String getOD_altura() {
		return OD_altura;
	}
	public void setOD_altura(String oD_altura) {
		OD_altura = oD_altura;
	}
	public double getOD_esfera_traspuesto() {
		return OD_esfera_traspuesto;
	}
	public void setOD_esfera_traspuesto(double oD_esfera_traspuesto) {
		OD_esfera_traspuesto = oD_esfera_traspuesto;
	}
	public double getOD_cilindro_traspuesto() {
		return OD_cilindro_traspuesto;
	}
	public void setOD_cilindro_traspuesto(double oD_cilindro_traspuesto) {
		OD_cilindro_traspuesto = oD_cilindro_traspuesto;
	}
	public int getOD_eje_traspuesto() {
		return OD_eje_traspuesto;
	}
	public void setOD_eje_traspuesto(int oD_eje_traspuesto) {
		OD_eje_traspuesto = oD_eje_traspuesto;
	}
	public Double getOI_esfera() {
		return OI_esfera;
	}
	public void setOI_esfera(Double oI_esfera) {
		OI_esfera = oI_esfera;
	}
	public Double getOI_cilindro() {
		return OI_cilindro;
	}
	public void setOI_cilindro(Double oI_cilindro) {
		OI_cilindro = oI_cilindro;
	}
	public Integer getOI_eje() {
		return OI_eje;
	}
	public void setOI_eje(Integer oI_eje) {
		OI_eje = oI_eje;
	}
	public Double getOI_adicion() {
		return OI_adicion;
	}
	public void setOI_adicion(Double oI_adicion) {
		OI_adicion = oI_adicion;
	}
	public Double getOI_diametro() {
		return OI_diametro;
	}
	public void setOI_diametro(Double oI_diametro) {
		OI_diametro = oI_diametro;
	}
	public Double getOI_esfera_cerca() {
		return OI_esfera_cerca;
	}
	public void setOI_esfera_cerca(Double oI_esfera_cerca) {
		OI_esfera_cerca = oI_esfera_cerca;
	}
	public Double getOI_n() {
		return OI_n;
	}
	public void setOI_n(Double oI_n) {
		OI_n = oI_n;
	}
	public Double getOI_p() {
		return OI_p;
	}
	public void setOI_p(Double oI_p) {
		OI_p = oI_p;
	}
	public Double getOI_avsc() {
		return OI_avsc;
	}
	public void setOI_avsc(Double oI_avsc) {
		OI_avsc = oI_avsc;
	}
	
	public String getOI_obser() {
		return OI_obser;
	}
	public void setOI_obser(String oI_obser) {
		OI_obser = oI_obser;
	}
	public String getOI_cantidad() {
		return OI_cantidad;
	}
	public void setOI_cantidad(String oI_cantidad) {
		OI_cantidad = oI_cantidad;
	}
	public String getOI_base() {
		return OI_base;
	}
	public void setOI_base(String oI_base) {
		OI_base = oI_base;
	}
	public String getOI_altura() {
		return OI_altura;
	}
	public void setOI_altura(String oI_altura) {
		OI_altura = oI_altura;
	}
	public double getOI_esfera_traspuesto() {
		return OI_esfera_traspuesto;
	}
	public void setOI_esfera_traspuesto(double oI_esfera_traspuesto) {
		OI_esfera_traspuesto = oI_esfera_traspuesto;
	}
	public double getOI_cilindro_traspuesto() {
		return OI_cilindro_traspuesto;
	}
	public void setOI_cilindro_traspuesto(double oI_cilindro_traspuesto) {
		OI_cilindro_traspuesto = oI_cilindro_traspuesto;
	}
	public int getOI_eje_traspuesto() {
		return OI_eje_traspuesto;
	}
	public void setOI_eje_traspuesto(int oI_eje_traspuesto) {
		OI_eje_traspuesto = oI_eje_traspuesto;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getFecha_emision() {
		return fecha_emision;
	}
	public void setFecha_emision(String fecha_emision) {
		this.fecha_emision = fecha_emision;
	}
	public String getFecha_prox_revision() {
		return fecha_prox_revision;
	}
	public void setFecha_prox_revision(String fecha_prox_revision) {
		this.fecha_prox_revision = fecha_prox_revision;
	}
	public int getIdent() {
		return ident;
	}
	public void setIdent(int ident) {
		this.ident = ident;
	}
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	public String getCod_pedtvcb() {
		return cod_pedtvcb;
	}
	public void setCod_pedtvcb(String cod_pedtvcb) {
		this.cod_pedtvcb = cod_pedtvcb;
	}
	public int getIdent_tratami() {
		return ident_tratami;
	}
	public void setIdent_tratami(int ident_tratami) {
		this.ident_tratami = ident_tratami;
	}
	public String getCod_pedttvln() {
		return cod_pedttvln;
	}
	public void setCod_pedttvln(String cod_pedttvln) {
		this.cod_pedttvln = cod_pedttvln;
	}
	public String getTratami() {
		return tratami;
	}
	public void setTratami(String tratami) {
		this.tratami = tratami;
	}
	
	
	
	
}
