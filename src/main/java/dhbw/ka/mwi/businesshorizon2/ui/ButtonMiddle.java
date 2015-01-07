package dhbw.ka.mwi.businesshorizon2.ui;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ButtonMiddle extends HorizontalLayout{
	
	private Embedded icon;
	private Label label;
	private Label gap1;
	private Label gap2;
	private Label gap3;
	
	private String hoehe = "50px";
	private String breite = "340px";	
	
	public ButtonMiddle (String iconPfad, String text, LayoutClickListener lcl) {
		setHeight(95, UNITS_PIXELS);
		setWidth(100, UNITS_PERCENTAGE);
		setStyleName("buttonMiddle");
		
		gap1 = new Label ();
		gap1.setWidth(15, UNITS_PIXELS);
		icon = new Embedded(null, new ThemeResource(iconPfad));
		icon.setWidth(40, UNITS_PIXELS);
		icon.setStyleName("buttonIconMiddle");
		
		gap2 = new Label();
		gap2.setWidth(15, UNITS_PIXELS);
		
		label = new Label (text);
		label.setStyleName("buttonLabelMiddle");
		label.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		label.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		
		gap3 = new Label();
		gap3.setSizeFull();

		
		
		addComponent(gap1);
		addComponent(icon);
		addComponent(gap2);
		addComponent(label);
		addComponent(gap3);
		setExpandRatio(gap3, 1.0f);
		
		setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
		setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		
		addListener(lcl);
	}

}
