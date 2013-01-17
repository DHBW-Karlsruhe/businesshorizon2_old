package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

public class periodFormFactory extends DefaultFieldFactory {

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field f;
		f = super.createField(item, propertyId, uiContext);
		TextField tf = (TextField) f;
		tf.setRequired(true);
		tf.setNullRepresentation("0");
		tf.setNullSettingAllowed(false);
		tf.addValidator(new doubleValidator("Wert muss eine Zahl sein!"));
		tf.setWidth("20em");
		
		/*
		if ("freeCashFlow".equals(propertyId)) {
			tf.setRequired(true);
			tf.setNullRepresentation("0");
			tf.setNullSettingAllowed(false);
			tf.addValidator(new doubleValidator("Cashflow must be double!"));
			tf.setWidth("20em");
		}else if("borrowedCapital".equals(propertyId)){
			tf.setRequired(true);
			tf.setNullRepresentation("0");
			tf.setNullSettingAllowed(false);
			tf.addValidator(new doubleValidator("capitalStock must be double!"));
			tf.setWidth("20em");
		}
		*/
		return f;

	}

}