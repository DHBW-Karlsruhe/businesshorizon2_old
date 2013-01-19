package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatz;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Period.CostOfSalesMethodPeriod;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.periodFormFactory;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt.DirektViewImpl;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class UmsatzViewImpl extends VerticalLayout implements UmsatzViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UmsatzPresenter presenter;
	

private GridLayout panel = new GridLayout(2,1);
	
	private Logger logger = Logger.getLogger(UmsatzViewImpl.class);
	

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Daniel Dengler
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
	}

	/**
	 * Erstelle das GUI zum zur Eingabe
	 * 
	 * @author Daniel Dengler
	 */
	

	@Override
	public void addInputField(String pd, double initialContent) {
		TextField tf = new TextField(pd,""+initialContent);
		tf.setWriteThrough(true);
		
		tf.addListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				TextField tf = (TextField)event.getProperty();
				presenter.validateChange((String) tf.getValue(), panel.getComponentArea(tf).getColumn1(),panel.getComponentArea(tf).getRow1(), tf.getCaption());
			}
		});
		panel.addComponent(tf);
		tf.setTextChangeEventMode(TextChangeEventMode.EAGER);
	}

	@Override
	public void setWrong(int textFieldColumn,int Row, boolean b) {
		if(b)
		((TextField)panel.getComponent(textFieldColumn, Row)).setComponentError(new UserError("Die Eingabe muss eine Zahl sein!"));
	}

	@Override
	public void initForm() {
		this.removeAllComponents();
		panel.removeAllComponents();
		this.addComponent(panel);
		panel.setSpacing(true);
		panel.setMargin(true);
	}
}
