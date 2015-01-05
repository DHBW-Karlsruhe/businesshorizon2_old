package dhbw.ka.mwi.businesshorizon2.ui;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenPresenter;

public class TopBarButton extends VerticalLayout{

	private static final long serialVersionUID = 1L;
	
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
	
	public void setButtonWidth(float width){
		button.setWidth(width, UNITS_PIXELS);
	}
	
	public Button getButtonComponent(){
		return button;
	}

}
