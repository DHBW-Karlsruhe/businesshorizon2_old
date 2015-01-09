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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.description;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ButtonsMiddlePresenter;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ButtonsMiddleViewInterface;

/**
 * Dies ist die Vaadin-Implementierung der ParameterDescrition Bereicht. Sie stellt den rechten Bereich mit der Beschreibung dar.
 * 
 * @author Tobias Lindner
 * 
 */
public class DescriptionViewImpl extends VerticalLayout implements DescriptionViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("DescriptionViewImpl.class");

	@Autowired
	private DescriptionPresenter presenter;
	
	private Label info;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten. 
	 * 
	 * @author Tobias Lindner
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUI();
		logger.debug("Initialisierung beendet");
	}

	/**
	 * Konkrete Ausprogrammierung der UI-Elemente.
	 * 
	 * @author Tobias Lindner
	 */
	private void generateUI() {
		info = new Label ("setTexts nicht ausgeführt");
		addComponent(info);

	}
	
	/**
	 * Diese Methode wird vom Presenter aufgerufen und ändert die Anzeige Texte.
	 * 
	 * @author Tobias Lindner
	 * @param screen
	 * 		der aktuelle Schritt
	 */
	public void setTexts (screen e) {
		switch (e) {
		case METHODSELECTION:
			removeAllComponents();
			Label info = new Label ("Für diesen Schritt ist noch keine Beschreibung hinterlegt.");
			info.setContentMode(Label.CONTENT_XHTML);
			addComponent(info);
			break;
			
		case PARAMETER:
			removeAllComponents();
			Label infoText0 = new Label ("<h2>Methode zur Parametereingabe</h2>");
			infoText0.setContentMode(Label.CONTENT_XHTML);
			
			setMargin(true);
			Label infoText1 = new Label ("<h3>Stochastische Eingabe</h3>");
			infoText1.setContentMode(Label.CONTENT_XHTML);
			Label infoText2 = new Label ("Diese Methode hat zum Ziel den zukünftigen Cashflow auf Basis der vergangenen Jahre zur berechnen. "
					+ "Dabei werden die Werte der vergangenen Jahre von Ihnen angegeben und die Cashflows für die nächsten Jahre mit dem AR(p) Verfahren berechnet.");
			Label infoText3 = new Label  ("<h3>Deterministische Eingabe</h3>");
			infoText3.setContentMode(Label.CONTENT_XHTML);
			Label infoText4 = new Label ("Bei der deterministischen Berechnung werden die von Ihnen angegebenen Cashflows der nächsten Perioden verwendet.");
			
			addComponent(infoText0);
			addComponent(infoText1);
			addComponent(infoText2);
			addComponent(infoText3);
			addComponent(infoText4);
			
			break;
			
		case PERIODS:
			removeAllComponents();
			info = new Label ("Für diesen Schritt ist noch keine Beschreibung hinterlegt.");
			info.setContentMode(Label.CONTENT_XHTML);
			addComponent(info);
			
			break;
			
		case SCENARIOS:
			removeAllComponents();
			info = new Label ("Für diesen Schritt ist noch keine Beschreibung hinterlegt.");
			info.setContentMode(Label.CONTENT_XHTML);
			addComponent(info);
			
			break;
			
		case RESULT:
			removeAllComponents();
			info = new Label ("Für diesen Schritt ist noch keine Beschreibung hinterlegt.");
			info.setContentMode(Label.CONTENT_XHTML);
			addComponent(info);
			
			break;
			
		default:
			break;
			
		}
	}
	
}
