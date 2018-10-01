package cl.gmo.pos.venta.web.beans;

public class ConvenioLnBean {
	
	private int ident;
	private String conveniocb;
	private String forma_pago;
	private String forma_pago_desc;
	private int dto;
	
	
	public String getForma_pago_desc() {
		return forma_pago_desc;
	}
	public void setForma_pago_desc(String forma_pago_desc) {
		this.forma_pago_desc = forma_pago_desc;
	}
	public int getIdent() {
		return ident;
	}
	public void setIdent(int ident) {
		this.ident = ident;
	}
	public String getConveniocb() {
		return conveniocb;
	}
	public void setConveniocb(String conveniocb) {
		this.conveniocb = conveniocb;
	}
	public String getForma_pago() {
		return forma_pago;
	}
	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}
	public int getDto() {
		return dto;
	}
	public void setDto(int dto) {
		this.dto = dto;
	}
	
	

}
