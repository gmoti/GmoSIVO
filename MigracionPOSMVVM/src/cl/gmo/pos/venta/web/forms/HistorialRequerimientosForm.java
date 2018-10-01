package cl.gmo.pos.venta.web.forms;

import java.util.ArrayList;
import cl.gmo.pos.venta.web.beans.ContactologiaBean;
import cl.gmo.pos.venta.web.beans.FamiliaBean;
import cl.gmo.pos.venta.web.beans.GraduacionesBean;
import cl.gmo.pos.venta.web.beans.GrupoFamiliaBean;
import cl.gmo.pos.venta.web.beans.ProductosBean;
import cl.gmo.pos.venta.web.beans.ProveedorBean;
import cl.gmo.pos.venta.web.beans.SubFamiliaBean;
import cl.gmo.pos.venta.web.beans.TipoFamiliaBean;

public class HistorialRequerimientosForm{

	
	//private static final long serialVersionUID = 6404694682434278282L;
	private ArrayList<FamiliaBean> listaFamilias;
	private ArrayList<SubFamiliaBean> listaSubFamilias;
    private ArrayList<GrupoFamiliaBean> listaGruposFamilias;
    private ArrayList<TipoFamiliaBean> listaTipoFamilia;
    private String familia;
    private String subFamilia;
    private String grupo;
   
	private String esfera;
    private String cilindro;
    private String[] grupos;
    
	//private static final long serialVersionUID = 8843759622770274837L;
	
    public ArrayList<FamiliaBean> getListaFamilias() {
		return listaFamilias;
	}
	public void setListaFamilias(ArrayList<FamiliaBean> listaFamilias) {
		this.listaFamilias = listaFamilias;
	}
	public ArrayList<SubFamiliaBean> getListaSubFamilias() {
		return listaSubFamilias;
	}
	public void setListaSubFamilias(ArrayList<SubFamiliaBean> listaSubFamilias) {
		this.listaSubFamilias = listaSubFamilias;
	}
	public ArrayList<GrupoFamiliaBean> getListaGruposFamilias() {
		return listaGruposFamilias;
	}
	public void setListaGruposFamilias(
			ArrayList<GrupoFamiliaBean> listaGruposFamilias) {
		this.listaGruposFamilias = listaGruposFamilias;
	}
	public ArrayList<TipoFamiliaBean> getListaTipoFamilia() {
		return listaTipoFamilia;
	}
	public void setListaTipoFamilia(ArrayList<TipoFamiliaBean> listaTipoFamilia) {
		this.listaTipoFamilia = listaTipoFamilia;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public String getSubFamilia() {
		return subFamilia;
	}
	public void setSubFamilia(String subFamilia) {
		this.subFamilia = subFamilia;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String[] getGrupos() {
		return grupos;
	}
	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}
	
	public String getEsfera() {
			return esfera;
	}
	public void setEsfera(String esfera) {
		this.esfera = esfera;
	}
	public String getCilindro() {
		return cilindro;
	}
	public void setCilindro(String cilindro) {
		this.cilindro = cilindro;
	}
	
	/*public static long getSerialversionuid() {
		return serialVersionUID;
	}*/
	
    
	
}
