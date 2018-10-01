package cl.gmo.pos.validador;

import java.util.Map;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.bind.Property;



public class ValidacionVentaDirecta extends AbstractValidator {

	@Override
	public void validate(ValidationContext ctx) {
		
		Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
	}
	
	
	public void getValidaNif(ValidationContext ctx) {
		
	}
	
	
	
	

}
