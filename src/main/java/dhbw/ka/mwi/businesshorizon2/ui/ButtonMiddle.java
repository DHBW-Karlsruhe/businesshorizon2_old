package dhbw.ka.mwi.businesshorizon2.ui;

import org.apache.log4j.Logger;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Die ist die Klasse eines ButtonsLayouts in der Mitte der Anwendung.
 * Außerdem werden verschiedene Methoden zum Ändern bestimmter Aspekte zur Verfügung gestellt.
 * 
 * @author Tobias Lindner, Marco Glaser
 *
 */
public class ButtonMiddle extends HorizontalLayout{
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("ButtonMiddle.class");
	
	private Embedded icon;
	private Label label;
	private Label label2;
	private Label gap1;
	private Label gap2;
	private Label gap3;
	
	private VerticalLayout vl;
	
	LayoutClickListener lcl;
	
	/**
	 * Konstruktor für einen Button mit einzeiligem Label
	 * 
	 * @author Tobias Lindner
	 * 
	 * @param iconPfad
	 * 		String: Pfad zum Icon Image
	 * @param text
	 * 		String: Text für den Button
	 * @param lcl
	 * 		LayoutClickListener: Listener, der Aktionen bei Klick auf den ButtonMiddle ausführt.
	 */
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

		vl = new VerticalLayout();
		vl.addComponent(label);
		vl.setSizeUndefined();
		gap3 = new Label();
		gap3.setSizeFull();
		
		addComponent(gap1);
		addComponent(icon);
		addComponent(gap2);
		addComponent(vl);
		addComponent(gap3);
		setExpandRatio(gap3, 1.0f);
		
		setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
		setComponentAlignment(vl, Alignment.MIDDLE_CENTER);
		
		addListener(lcl);
	}

	/**
	 * Konstruktor für einen Button mit zweizeiliger Beschriftung
	 * 
	 * @author Tobias Lindner
	 * 
	 * @param iconPfad
	 * 		String: Pfad zum Icon Image
	 * @param text
	 * 		String: Text für die erste Zeilei
	 * @param text2
	 * 		String: Text für die zweite Zeile
	 * @param lcl
	 * 		LayoutClickListener: Listener, der Aktionen bei Klick auf den ButtonMiddle ausführt.	 
	 */
	public ButtonMiddle (String iconPfad, String text, String text2, LayoutClickListener lcl) {
		this (iconPfad, text, lcl);

		label2.setValue(text2);
		vl.addComponent(label2);
		vl.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
		vl.setComponentAlignment(label2, Alignment.MIDDLE_LEFT);
		vl.setSizeUndefined();
		
	}
	
	/**
	 * Anpassung an einem Button mit Möglichkeit zur zweizeiligen Textangabe.
	 * 
	 * @author Tobias Lindner
	 */
	public void setDetails (String iconPfad, String text, String text2, LayoutClickListener lcl) {
		setDetails(iconPfad, text, lcl);
		
		label2 = new Label (text2);
		
		vl.removeAllComponents();
		vl.addComponent(label);
		vl.addComponent(label2);
		vl.setComponentAlignment(label, Alignment.TOP_LEFT);
		vl.setComponentAlignment(label2, Alignment.BOTTOM_LEFT);
		vl.setSizeUndefined();
		
	}
	
	/**
	 * Anpassung an einem Button mit einzeiliger Textangabe.
	 * @param iconPfad
	 * @param text
	 * @param lcl
	 */
	public void setDetails (String iconPfad, String text, LayoutClickListener lcl) {
		if (vl.getComponentIndex(label2)!= -1 ){
			vl.removeComponent(label2);
		}
		this.icon.setSource(new ThemeResource(iconPfad));
		setDetails(text, lcl);
		logger.debug ("ButtonDetails geändert");
	}

	public void setDetails(String text, LayoutClickListener lcl) {
		this.setText(text);
		this.setListener(lcl);
	}
	
	public void setText(String text){
		this.label.setValue(text);
	}
	
	public void addSecondLine (String text) {
		
	}
	
	public void setListener (LayoutClickListener lcl) {
		this.removeListener(this.lcl);
		this.addListener(lcl);
	}
	
	/**
	 * Die Methode setzt den ButtonMiddle auf aktiviert (=dunkle Hintergrundfarbe).
	 * 
	 * @author Tobias Lindner
	 */
	public void setActivated () {
		addStyleName("buttonActivated");
	}
	
	/**
	 * Die Methode setzt den ButtonMIddle auf deaktiviert (=hellere Hintergrundfarbe)
	 * 
	 * @author Tobias Lindner
	 */
	public void setDeactivated () {
		removeStyleName("buttonActivated");
	}
	
	public void changeStyleClass(String styleName){
		setStyleName(styleName);
	}
	
	/**
	 * @author Marco Glaser
	 */
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

