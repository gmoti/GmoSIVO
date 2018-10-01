package cl.gmo.pos.venta.web.forms;



import cl.gmo.pos.venta.utils.Constantes;

abstract class GenericForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993979832602701149L;
	private String include=Constantes.STRING_BLANCO;
	private int estaGrabado = 0;
	private String paginaGrabar="NO";
	    
	   
	    
		
	public String getPaginaGrabar() {
		return paginaGrabar;
	}
	public void setPaginaGrabar(String paginaGrabar) {
		this.paginaGrabar = paginaGrabar;
	}
	public int getEstaGrabado() {
		return estaGrabado;
	}
	public void setEstaGrabado(int estaGrabado) {
		this.estaGrabado = estaGrabado;
	}
	public String getInclude() {
		return include;
	}
	public void setInclude(String include) {
		this.include = include;
	}

}
