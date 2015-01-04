package dhbw.ka.mwi.businesshorizon2.ui;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TopBarButton{
	
	private VerticalLayout layout;
	private Button button;
	private Label label;
	
	public TopBarButton(String styleName, String sLabel){
		layout = new VerticalLayout();
		layout.setWidth(150, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		layout.setHeight(80, com.vaadin.terminal.Sizeable.UNITS_PERCENTAGE);
		layout.setStyleName("topBarButtonContainer");
		button = new Button();
		button.setWidth(40, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		button.setHeight(40, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		button.setStyleName(styleName);
		label = new Label(sLabel);
		label.setStyleName("topBarButtonLabel");
		layout.addComponent(button);
		layout.addComponent(label);
		layout.setComponentAlignment(button, Alignment.TOP_CENTER);
		layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}
	
	public Component getComponent(){
		return layout;
	}
	
	public Button getButton() {
		return button;
	}

}
