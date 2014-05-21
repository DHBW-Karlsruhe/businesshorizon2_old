/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
 * Vaadin.
 * 
 * @author Julius Hacker
 * 
 */
public class PeriodViewImpl extends HorizontalSplitPanel implements
		PeriodViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PeriodPresenter presenter;


	HorizontalSplitPanel horizontalPanel;

	private static final Logger logger = Logger
			.getLogger("PeriodViewImpl.class");

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
	 * Erstelle das GUI zum Prozessschritt "Perioden"
	 * 
	 * @author Daniel Dengler
	 */
	private void generateUi() {
		setSizeFull();
		setSplitPosition(60, UNITS_PERCENTAGE);
		setSizeFull();
		setLocked(true);
		setStyleName("small");
		setMargin(true);
		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSplitPosition(35, UNITS_PERCENTAGE);
		horizontalPanel.setSizeFull();
		horizontalPanel.setLocked(true);
		horizontalPanel.setStyleName("small");
		setFirstComponent(horizontalPanel);
		
		VerticalLayout infoBox = new VerticalLayout();
		infoBox.setMargin(true);
		Label infoText1 = new Label ("<h3>Eingabe der Perioden</h3>");
		infoText1.setContentMode(Label.CONTENT_XHTML);
		Label infoText2 = new Label("Der Zeitstrahl ist gemäß Ihren getätigten Eingaben vorgegeben. Mit einem Klick auf den „Jahresbutton“ können Sie die entsprechenden Werte eintragen.");
		Label infoText3 = new Label  ("<h3>Direkte Eingabe der Cashflows:</h3>");
		infoText3.setContentMode(Label.CONTENT_XHTML);
		Label infoText4 = new Label ("Wählen Sie nun bitte die links angezeigten Perioden aus und geben Sie für jede Periode sowohl den Cashflow als auch das Fremdkapital der jeweiligen Periode an.");
		Label infoText5 = new Label  ("<h3>Gesamtkostenverfahren:</h3>");
		infoText5.setContentMode(Label.CONTENT_XHTML);
		Label infoText6 = new Label ("Wählen Sie nun bitte die links angezeigten Perioden aus und geben Sie für jede Periode die abgefragten Daten zur Berechnung des Cashflows an. Die benötigten Datensätze entnehmen Sie bitte Ihrer Gewinn- und Verlustrechnung, erstellt nach dem Gesamtkostenverfahren.");
		Label infoText7 = new Label  ("<h3>Umsatzkostenverfahren:</h3>");
		infoText7.setContentMode(Label.CONTENT_XHTML);
		Label infoText8 = new Label ("Wählen Sie nun bitte die links angezeigten Perioden aus und geben Sie für jede Periode die abgefragten Daten zur Berechnung des Cashflows an. Die benötigten Datensätze entnehmen Sie bitte Ihrer Gewinn- und Verlustrechnung, erstellt nach dem Umsatzkostenverfahren.");
		
		infoBox.addComponent(infoText1);
		infoBox.addComponent(infoText2);
		infoBox.addComponent(infoText3);
		infoBox.addComponent(infoText4);
		infoBox.addComponent(infoText5);
		infoBox.addComponent(infoText6);
		infoBox.addComponent(infoText7);
		infoBox.addComponent(infoText8);
		
		
		setSecondComponent(infoBox);
	
		
	}

	@Override
	public void showView(View leftView, View rightView) {
		removeAllComponents();
		generateUi();
		horizontalPanel.setFirstComponent((Component) leftView);
		horizontalPanel.setSecondComponent((Component) rightView);
		this.setSizeFull();
		((Component) leftView).setSizeFull();
		if (rightView != null)
			((Component) rightView).setSizeFull();
		horizontalPanel.setSizeFull();
		this.setSizeFull();
		logger.debug("Alle Komponenten neu angelegt");
	}

	@Override
	public void setSize(float max, int heightUnits) {
		logger.debug("Setting size to " + (max) + " " + heightUnits);
		this.setHeight(max, heightUnits);

	}
}
