package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import com.vaadin.data.Validator;

public class doubleValidator implements Validator {
	
	private String message;
	
	public doubleValidator(String message){
		this.message=message;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
        if (!isValid(value)) {
            throw new InvalidValueException(message);
        }

	}

	@Override
	public boolean isValid(Object value) {
		if(value==null || !(value instanceof String)){
			return false;
		}
		
		try{
			Double.parseDouble((String)value);
		}catch(Exception e){
			return false;
		}
		return true;
	}

}
