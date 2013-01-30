/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/


package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatz;

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
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
 * Vaadin.
 * 
 * @author Julius Hacker
 * 
 */
public class UmsatzViewImpl extends VerticalLayout implements
		UmsatzViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UmsatzPresenter presenter;

	private DecimalFormat df = new DecimalFormat(",##0.00");

	private GridLayout panel = new GridLayout(2, 1);

	private Logger logger = Logger.getLogger(UmsatzViewImpl.class);

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
		panel.setSpacing(true);
		panel.setMargin(true);
	}

	@Override
	public void addHeader(int year) {
		Label l = new Label("<h2>Jahr: "+year+"</h2>");
		l.setContentMode(Label.CONTENT_XHTML);
		this.addComponent(l);
		this.addComponent(panel);
		
	}
}
