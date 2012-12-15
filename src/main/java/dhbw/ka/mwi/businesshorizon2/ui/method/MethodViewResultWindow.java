package dhbw.ka.mwi.businesshorizon2.ui.method;

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.methods.Result;


public class MethodViewResultWindow extends Window {
	private static final long serialVersionUID = 1L;

	public MethodViewResultWindow(Result result) {
		setCaption("Ergebnis");
		setWidth(80, UNITS_PERCENTAGE);
		setHeight(80, UNITS_PERCENTAGE);
		center();
		
		
		addComponent(new Label(result.toString()));
	}
	
}
