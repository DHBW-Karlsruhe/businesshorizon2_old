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

	LayoutClickListener lcl;
	
	public ButtonMiddle (String iconPfad, String text, LayoutClickListener lcl) {
		this.lcl = lcl;
		setHeight(95, UNITS_PIXELS);
		setWidth(100, UNITS_PERCENTAGE);
		setStyleName("buttonMiddle");
		
		gap1 = new Label ();
		gap1.setWidth(15, UNITS_PIXELS);
		icon = new Embedded(null, new ThemeResource(iconPfad));
		icon.setWidth(40, UNITS_PIXELS);
		icon.setStyleName("buttonIconMiddle");
		
		gap2 = new Label();
		gap2.setWidth(10, UNITS_PIXELS);
		
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
	
	public void setDetails (String iconPfad, String text, LayoutClickListener lcl) {
		this.icon.setIcon(new ThemeResource(iconPfad));
		setDetails(text, lcl);
	}

	public void setDetails(String text, LayoutClickListener lcl) {
		this.setText(text);
		this.setListener(lcl);
	}
	
	public void setText(String text){
		this.label.setValue(text);
	}
	
	public void setListener (LayoutClickListener lcl) {
		this.removeListener(this.lcl);
		this.addListener(lcl);
	}
	
	public void setActivated () {
		addStyleName("buttonActivated");
	}
	
	public void setDeactivated () {
		removeStyleName("buttonActivated");
	}
	
	public void changeStyleClass(String styleName){
		setStyleName(styleName);
	}
	
	public void changeDirection(){
		removeAllComponents();
		
		addComponent(gap3);
		addComponent(label);
		addComponent(gap2);
		addComponent(icon);
		addComponent(gap1);
		setExpandRatio(gap3, 1.0f);
		setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
		setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}
	
}

