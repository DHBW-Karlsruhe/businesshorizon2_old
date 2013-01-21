package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.direkt;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
 * Vaadin.
 * 
 * @author Julius Hacker
 * 
 */
public class DirektViewImpl extends VerticalLayout implements
		DirektViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DirektPresenter presenter;

	private DecimalFormat df = new DecimalFormat(",##0.00");

	private GridLayout panel = new GridLayout(2, 1);

	private Logger logger = Logger.getLogger(DirektViewImpl.class);

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
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
		TextField tf = new TextField(pd, df.format(initialContent));
		df.format(initialContent);
		tf.setImmediate(true);

		tf.addListener(new Property.ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				TextField tf = (TextField) event.getProperty();
				presenter.validateChange((String) tf.getValue(), panel
						.getComponentArea(tf).getColumn1(), panel
						.getComponentArea(tf).getRow1(), tf.getCaption());
				try {
					tf.setValue(df.format(df.parse((String) tf.getValue()).doubleValue()));
				} catch (ReadOnlyException | ConversionException
						| ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel.addComponent(tf);
		tf.setTextChangeEventMode(TextChangeEventMode.EAGER);
	}

	@Override
	public void setWrong(int textFieldColumn, int Row, boolean b) {
		if (b)
			((TextField) panel.getComponent(textFieldColumn, Row))
					.setComponentError(new UserError(
							"Die Eingabe muss eine Zahl sein!"));
		else
			((TextField) panel.getComponent(textFieldColumn, Row))
					.setComponentError(null);
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
