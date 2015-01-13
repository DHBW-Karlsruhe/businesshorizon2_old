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

import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
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
	
	private GridLayout gl;
	
	private Label info;
	
	private Label infoText0;
	private Label infoText1;
	private Label infoText2;
	private Label infoText3;
	private Label infoText4;
	private Label infoText5;
	
	private Embedded icon1;
	private Embedded icon2;

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
		setWidth(95, UNITS_PERCENTAGE);
		setStyleName("projectDetailsLayout");
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
			infoText0 = new Label ("<h2>Prognosemethode</h2>");
			infoText0.setContentMode(Label.CONTENT_XHTML);
			infoText1 = new Label ("<h2>Methode zur Parametereingabe</h2>");
			infoText1.setContentMode(Label.CONTENT_XHTML);
			
			gl = new GridLayout(2,4);
			gl.setSizeFull();
			gl.setColumnExpandRatio(1, 1.0f);
			
			//Absatz 1
			icon1 = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
			icon1.setWidth(30, UNITS_PIXELS);
			icon1.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void click(ClickEvent event) {
					if (infoText3.isVisible()) {
						infoText3.setVisible(false);
						icon1.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
					}
					else {
						infoText3.setVisible(true);
						icon1.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
					}
				}
			});
			
			setMargin(true);
			infoText2 = new Label ("<h3>Stochastische Eingabe</h3>");
			infoText2.setContentMode(Label.CONTENT_XHTML);
			infoText2.addStyleName("descriptionHeadline");
			infoText3 = new Label ("Diese Methode hat zum Ziel den zukünftigen Cashflow auf Basis der vergangenen Jahre zur berechnen. "
					+ "Dabei werden die Werte der vergangenen Jahre von Ihnen angegeben und die Cashflows für die nächsten Jahre mit dem AR(p) Verfahren berechnet.");
			infoText3.addStyleName("wrap");
			infoText3.addStyleName("descriptionText");
			
			gl.addComponent(icon1, 0, 0);
			gl.setComponentAlignment(icon1, Alignment.MIDDLE_CENTER);
			gl.addComponent(infoText2, 1, 0);
			gl.addComponent(infoText3, 1,1);
			
			//Absatz 2
			icon2 = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
			icon2.setWidth(30, UNITS_PIXELS);
			icon2.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void click(ClickEvent event) {
					if (infoText5.isVisible()) {
						infoText5.setVisible(false);
						icon2.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
					}
					else {
						infoText5.setVisible(true);
						icon2.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
					}
				}
			});
			infoText4 = new Label  ("<h3>Deterministische Eingabe</h3>");
			infoText4.setContentMode(Label.CONTENT_XHTML);
			infoText4.addStyleName("descriptionHeadline");
			infoText5 = new Label ("Bei der deterministischen Berechnung werden die von Ihnen angegebenen Cashflows der nächsten Perioden verwendet.");
			infoText5.addStyleName("wrap");
			infoText5.addStyleName("descriptionText");
			
			gl.addComponent(icon2,0, 2);
			gl.setComponentAlignment(icon2, Alignment.MIDDLE_CENTER);
			gl.addComponent(infoText4, 1,2);
			gl.addComponent(infoText5, 1, 3);
			
			addComponent(infoText0);
			addComponent(infoText1);
			addComponent(gl);
			break;
			
		case PARAMETER:
			removeAllComponents();
			infoText0 = new Label ("<h2>Prognosemethode</h2>");
			infoText0.setContentMode(Label.CONTENT_XHTML);
			infoText1 = new Label ("<h2>Methode zur Parametereingabe</h2>");
			infoText1.setContentMode(Label.CONTENT_XHTML);
			
			gl = new GridLayout(2,4);
			gl.setSizeFull();
			gl.setColumnExpandRatio(1, 1.0f);
			
			//Absatz 1
			icon1 = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
			icon1.setWidth(30, UNITS_PIXELS);
			icon1.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void click(ClickEvent event) {
					if (infoText3.isVisible()) {
						infoText3.setVisible(false);
						icon1.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
					}
					else {
						infoText3.setVisible(true);
						icon1.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
					}
				}
			});
			
			setMargin(true);
			infoText2 = new Label ("<h3>Stochastische Eingabe</h3>");
			infoText2.setContentMode(Label.CONTENT_XHTML);
			infoText2.addStyleName("descriptionHeadline");
			infoText3 = new Label ("Diese Methode hat zum Ziel den zukünftigen Cashflow auf Basis der vergangenen Jahre zur berechnen. "
					+ "Dabei werden die Werte der vergangenen Jahre von Ihnen angegeben und die Cashflows für die nächsten Jahre mit dem AR(p) Verfahren berechnet.");
			infoText3.addStyleName("wrap");
			infoText3.addStyleName("descriptionText");
			
			gl.addComponent(icon1, 0, 0);
			gl.setComponentAlignment(icon1, Alignment.MIDDLE_CENTER);
			gl.addComponent(infoText2, 1, 0);
			gl.addComponent(infoText3, 1,1);
			
			//Absatz 2
			icon2 = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
			icon2.setWidth(30, UNITS_PIXELS);
			icon2.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void click(ClickEvent event) {
					if (infoText5.isVisible()) {
						infoText5.setVisible(false);
						icon2.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
					}
					else {
						infoText5.setVisible(true);
						icon2.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
					}
				}
			});
			infoText4 = new Label  ("<h3>Deterministische Eingabe</h3>");
			infoText4.setContentMode(Label.CONTENT_XHTML);
			infoText4.addStyleName("descriptionHeadline");
			infoText5 = new Label ("Bei der deterministischen Berechnung werden die von Ihnen angegebenen Cashflows der nächsten Perioden verwendet.");
			infoText5.addStyleName("wrap");
			infoText5.addStyleName("descriptionText");
			
			gl.addComponent(icon2,0, 2);
			gl.setComponentAlignment(icon2, Alignment.MIDDLE_CENTER);
			gl.addComponent(infoText4, 1,2);
			gl.addComponent(infoText5, 1, 3);
			
			addComponent(infoText0);
			addComponent(infoText1);
			addComponent(gl);
			
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
