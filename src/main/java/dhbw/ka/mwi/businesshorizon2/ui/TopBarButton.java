package dhbw.ka.mwi.businesshorizon2.ui;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TopBarButton extends VerticalLayout{
	
	private Button button;
	private Label label;
	
	public TopBarButton(String styleName, String sLabel){
		setWidth(150, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		setHeight(50, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		setStyleName("topBarButtonContainer");
		button = new Button();
		button.setWidth(30, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		button.setHeight(30, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		button.setStyleName(styleName);
		label = new Label(sLabel);
		label.setStyleName("topBarButtonLabel");
		label.setSizeUndefined();
		addComponent(button);
		addComponent(label);
		setComponentAlignment(button, Alignment.TOP_CENTER);
		setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}

}
