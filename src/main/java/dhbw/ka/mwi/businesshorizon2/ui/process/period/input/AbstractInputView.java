package dhbw.ka.mwi.businesshorizon2.ui.process.period.input;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ReadOnlyException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public abstract class AbstractInputView extends VerticalLayout implements
InputViewInterface  {
	private static final long serialVersionUID = 1L;

	
	 protected AbstractInputPresenter presenter;

	 DecimalFormat df = new DecimalFormat(",##0.00");
	 
	 Panel all = new Panel();

	 GridLayout panel = new GridLayout(2, 1);

	 protected Logger logger;



	/**
	 * Erstelle das GUI zum zur Eingabe
	 * 
	 * @author Daniel Dengler
	 */

	public void addInputField(String pd, double initialContent) {
		TextField tf = new TextField(pd, df.format(initialContent));
		df.format(initialContent);
		tf.setImmediate(true);

		tf.addListener(new Property.ValueChangeListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
	public void addInputField(String pd) {
		TextField tf = new TextField(pd);
		tf.setImmediate(true);
		
		tf.addListener(new Property.ValueChangeListener() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
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

	public void setWrong(int textFieldColumn, int Row, boolean b) {
		if (b)
			((TextField) panel.getComponent(textFieldColumn, Row))
					.setComponentError(new UserError(
							"Die Eingabe muss eine Zahl sein!"));
		else
			((TextField) panel.getComponent(textFieldColumn, Row))
					.setComponentError(null);
	}

	public void initForm() {
		this.removeAllComponents();
		all.setStyleName(Reindeer.PANEL_LIGHT);
		all.removeAllComponents();
		panel.removeAllComponents();
		panel.setSpacing(true);
		panel.setMargin(true);
	}
	public void addHeader(int year) {
		Label l = new Label("<h2>       Jahr: "+year+"</h2>");
		l.setContentMode(Label.CONTENT_XHTML);
		this.all.addComponent(l);
		this.all.addComponent(panel);
		this.addComponent(all);
		
	}


}
