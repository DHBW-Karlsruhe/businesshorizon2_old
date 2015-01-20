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
	
	private Label headline0;
	private Label headline1;
	private Label stochasticHeadline;
	private Label stochasticText;
	private Label deterministicHeadline;
	private Label deterministicText;
	private Label fcfHeadline;
	private Label fcfText;
	private Label ukvHeadline;
	private Label ukvText;
	private Label gkvHeadline;
	private Label gkvText;
	private Label apvHeadline;
	private Label apvText;
	private Label fteHeadline;
	private Label fteText;
	private Label waccHeadline;
	private Label waccText;
	
	private Label scenarioHeadline;
	private Label scenarioText;
	
	private Embedded iconStochastic;
	private Embedded iconDeterministic;
	private Embedded iconFCF;
	private Embedded iconGKV;
	private Embedded iconUKV;
	private Embedded iconAPV;
	private Embedded iconFTE;
	private Embedded iconWACC;

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
		initializeTextsHeadlinesAndIcons();
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
			
			addComponent(getPrognoseMethodenInfos());
			Label gap = new Label ();
			gap.setHeight(10, UNITS_PIXELS);
			addComponent(gap);
			addComponent(getEingabeMethodeInfo());
			Label gap2 = new Label();
			gap2.setHeight(10, UNITS_PIXELS);
			addComponent(getBerechnungsMethodeInfo());
			
			break;
			
		case PARAMETER:
			removeAllComponents();
			addComponent(getPrognoseMethodenInfos());	
			break;
			
		case PERIODS:
			removeAllComponents();
			addComponent(getEingabeMethodeInfo());
			break;
			
		case SCENARIOS:
			removeAllComponents();
			addComponent(scenarioHeadline);
			addComponent(scenarioText);
			break;
			
		default:
			logger.debug("keine Case-Übereinstimmung in setTexts");
			break;
			
		}
	}
	
	private VerticalLayout getPrognoseMethodenInfos () {
		VerticalLayout vl = new VerticalLayout();
		infoText0 = new Label ("<h1>Prognosemethode</h1>");
		infoText0.setContentMode(Label.CONTENT_XHTML);
		infoText1 = new Label ("<h2>Methode zur Parametereingabe</h2>");
		infoText1.setContentMode(Label.CONTENT_XHTML);
		
		gl = new GridLayout(2,4);
		gl.setSizeFull();
		gl.setColumnExpandRatio(1, 1.0f);
					
		gl.addComponent(iconStochastic, 0, 0);
		gl.setComponentAlignment(iconStochastic, Alignment.MIDDLE_CENTER);
		gl.addComponent(stochasticHeadline, 1, 0);
		gl.addComponent(stochasticText, 1,1);
					
		gl.addComponent(iconDeterministic,0, 2);
		gl.setComponentAlignment(iconDeterministic, Alignment.MIDDLE_CENTER);
		gl.addComponent(deterministicHeadline, 1,2);
		gl.addComponent(deterministicText, 1, 3);
		
		vl.addComponent(infoText0);
		vl.addComponent(infoText1);
		vl.addComponent(gl);
		
		return vl;
	}
	
	private VerticalLayout getEingabeMethodeInfo () {
		VerticalLayout vl = new VerticalLayout();
		headline0 = new Label ("<h1>Eingabemethode</h1>");
		headline0.setContentMode(Label.CONTENT_XHTML);
		headline1 = new Label ("<h2>Methode zur Berechnung des Unternehmenswertes</h2>");
		headline1.setContentMode(Label.CONTENT_XHTML);
		
		gl = new GridLayout (2,6);
		gl.setSizeFull();
		gl.setColumnExpandRatio(1,  1.0f);
		
		gl.addComponent(iconFCF, 0, 0);
		gl.setComponentAlignment(iconFCF, Alignment.MIDDLE_CENTER);
		gl.addComponent(fcfHeadline, 1, 0);
		gl.addComponent(fcfText, 1, 1);
		
		gl.addComponent(iconUKV, 0, 2);
		gl.setComponentAlignment(iconUKV, Alignment.MIDDLE_CENTER);
		gl.addComponent(ukvHeadline, 1, 2);
		gl.addComponent(ukvText, 1, 3);
		
		gl.addComponent(iconGKV, 0 , 4);
		gl.setComponentAlignment(iconGKV, Alignment.MIDDLE_CENTER);
		gl.addComponent(gkvHeadline, 1, 4);
		gl.addComponent(gkvText, 1, 5);
		
		vl.addComponent(headline0);
		vl.addComponent(headline1);
		vl.addComponent(gl);
		
		return vl;
	}
	
	private VerticalLayout getBerechnungsMethodeInfo () {
		VerticalLayout vl = new VerticalLayout();
		headline0 = new Label ("<h1>Berechnungsmethoden</h1>");
		headline0.setContentMode(Label.CONTENT_XHTML);
		headline1 = new Label ("<h2>Methode zur Prognoseerstellung</h2>");
		headline1.setContentMode(Label.CONTENT_XHTML);
		
		gl = new GridLayout (2,6);
		gl.setSizeFull();
		gl.setColumnExpandRatio(1,  1.0f);
		
		gl.addComponent(iconAPV, 0, 0);
		gl.setComponentAlignment(iconAPV, Alignment.MIDDLE_CENTER);
		gl.addComponent(apvHeadline, 1, 0);
		gl.addComponent(apvText, 1, 1);
		
		gl.addComponent(iconFTE, 0, 2);
		gl.setComponentAlignment(iconFTE, Alignment.MIDDLE_CENTER);
		gl.addComponent(fteHeadline, 1, 2);
		gl.addComponent(fteText, 1, 3);
		
		gl.addComponent(iconWACC, 0 , 4);
		gl.setComponentAlignment(iconWACC, Alignment.MIDDLE_CENTER);
		gl.addComponent(waccHeadline, 1, 4);
		gl.addComponent(waccText, 1, 5);
		
		vl.addComponent(headline0);
		vl.addComponent(headline1);
		vl.addComponent(gl);
		
		return vl;
	}


	/**
	 * Die Methode initialisiert alle benötigten Labels, und Icons.
	 * 
	 * @author Tobias Lindner
	 */
	private void initializeTextsHeadlinesAndIcons () {
		int anzPixelIcon = 27;

		//Stochastische Eingabe Beschreibung
		stochasticHeadline = new Label ("<h3>Stochastische Eingabe</h3>");
		stochasticHeadline.setContentMode(Label.CONTENT_XHTML);
		stochasticHeadline.addStyleName("descriptionHeadline");
		stochasticText = new Label ("Diese Methode hat zum Ziel den zukünftigen Cashflow auf Basis der Werte aus vergangenen Methoden zu berechnen. "
				+ "Dabei werden die Werte der vergangenen Methoden von Ihnen angegeben und die potenziellen Cashflows für die nächsten Jahre mit dem AR(p) Modell berechnet.");
		stochasticText.addStyleName("wrap");
		stochasticText.addStyleName("descriptionText");
		iconStochastic = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconStochastic.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconStochastic.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (stochasticText.isVisible()) {
					stochasticText.setVisible(false);
					iconStochastic.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					stochasticText.setVisible(true);
					iconStochastic.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});
		
		//Deterministische Eingabe Beschreibung
		deterministicHeadline = new Label  ("<h3>Deterministische Eingabe</h3>");
		deterministicHeadline.setContentMode(Label.CONTENT_XHTML);
		deterministicHeadline.addStyleName("descriptionHeadline");
		deterministicText = new Label ("Bei der deterministischen Berechnung werden die von Ihnen geschätzten Cashflows der nächsten Perioden angegeben.");
		deterministicText.addStyleName("wrap");
		deterministicText.addStyleName("descriptionText");
		iconDeterministic = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconDeterministic.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconDeterministic.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (deterministicText.isVisible()) {
					deterministicText.setVisible(false);
					iconDeterministic.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					deterministicText.setVisible(true);
					iconDeterministic.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});


		//FCF Beschreibung
		fcfHeadline = new Label ("<h3>FCF</h3>");
		fcfHeadline.setContentMode(Label.CONTENT_XHTML);
		fcfHeadline.addStyleName ("descriptionHeadline");
		fcfText = new Label ("Beim FCF Verfahren wird der freie Cash Flow aus dem operativen Cash-Flow abzüglich des Saldos aus Investitionstätigkeiten errechnet.");
		fcfText.addStyleName("wrap");
		fcfText.addStyleName("descriptionText");
		iconFCF = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconFCF.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconFCF.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (fcfText.isVisible()) {
					fcfText.setVisible(false);
					iconFCF.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					fcfText.setVisible(true);
					iconFCF.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});

		//UKV Beschreibung
		ukvHeadline = new Label ("<h3>UKV</h3>");
		ukvHeadline.setContentMode (Label.CONTENT_XHTML);
		ukvHeadline.addStyleName ("descriptionHeadline");
		ukvText = new Label ("Beim Umsatz-Kosten-Verfahren wird der Cashflow aus Werten aus GuV und Bilanz berechnet. Die dafür notwendigen Posten sind: "
				+ "Umsatzerlöse, Herstellkosten des Umsatzes, Kosten F&E, Verwaltungskosten, Sonstiger Aufwand, Ertrag, Erträge aus Wertpapieren, Zinsen und ähnliche Aufwendungen, Außerordentliche Erträge & Aufwendungen, Abschreibungen, Pensionsrückstellungen");
		ukvText.addStyleName ("wrap");
		ukvText.addStyleName ("descriptionText");
		iconUKV = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconUKV.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconUKV.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (ukvText.isVisible()) {
					ukvText.setVisible(false);
					iconUKV.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					ukvText.setVisible(true);
					iconUKV.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});

		//GKV Beschreibung
		gkvHeadline = new Label ("<h3>GKV</h3>");
		gkvHeadline.setContentMode(Label.CONTENT_XHTML);
		gkvHeadline.setStyleName("descriptionHeadline");
		gkvText = new Label ("Beim Gesamt-Kosten-Verfahren wird der Cashflow aus Werten aus GuV und Bilanz berechnet. Die dafür notwendigen Posten sind: "
				+ "Umsatzerlöse, Erhöhung des Bestandes an fertigen Erzeugnissen, Verminderung des Bestandes an fertigen Erzeugnissen, Materialaufwand, Personalaufwand mit Löhne/Gehälter, "
				+ "Einstellungs-/Entlassungskosten, Pensionsrückstellungen, Sonstige Personalkosten, Abschreibungen, Sonstiger Aufwand, Ertrag, Erträge aus Wertpapieren, Zinsen und ähnliche Aufwendungen, "
				+ "Außerordentliche Erträge, Außerordentliche Aufwendungen, Ertragssteuern, Abschreibungen, Pensionsrückstellungen");
		gkvText.addStyleName("wrap");
		gkvText.addStyleName("descriptionText");
		iconGKV = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconGKV.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconGKV.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (gkvText.isVisible()) {
					gkvText.setVisible(false);
					iconGKV.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					gkvText.setVisible(true);
					iconGKV.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});


		//APV Beschreibung
		apvHeadline = new Label ("<h3>APV</h3>");
		apvHeadline.setContentMode(Label.CONTENT_XHTML);
		apvHeadline.setStyleName("descriptionHeadline");	
		apvText = new Label ("Nach dieser Methode berechnet sich der Unternehmenswert aus dem Wert des rein eigenfinanzierten Unternehmens "
				+ "zuzüglich der Steuervorteile durch das verzinsliche Fremdkapital, abzüglich des verzinslichen Fremdkapitals.");
		apvText.addStyleName("wrap");
		apvText.addStyleName("descriptionText");
		iconAPV = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconAPV.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconAPV.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (apvText.isVisible()) {
					apvText.setVisible(false);
					iconAPV.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					apvText.setVisible(true);
					iconAPV.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});

		//FTE Beschreibung
		fteHeadline = new Label ("<h3>FTE</h3>");
		fteHeadline.setContentMode(Label.CONTENT_XHTML);
		fteHeadline.addStyleName("descriptionHeadline");
		fteText = new Label("Die Flow-to-Equity Methode diskontiert alle zukünftigen Cashflows auf einen Stichtag und addiert den diskontierten Restwert. "
				+ "Folglich betrachtet dieses Verfahren für die Berechnung des Unternehmenswertes ausschließlich das Eigenkapital.");
		fteText.addStyleName("wrap");
		fteText.addStyleName("descriptionText");
		iconFTE = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconFTE.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconFTE.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (fteText.isVisible()) {
					fteText.setVisible(false);
					iconFTE.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					fteText.setVisible(true);
					iconFTE.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});

		//WACC Beschreibung
		waccHeadline = new Label ("<h3>WACC</h3>");
		waccHeadline.setContentMode(Label.CONTENT_XHTML);
		waccHeadline.setStyleName("descriptionHeadline");		
		waccText = new Label ("WACC sind gewichtete Kapitalkosten. Sie werden durch das gewichtete Mittel der Eigen- und Fremdkapitalkosten berechnet, "
				+ "wobei die Fremdkapitalkosten um den Steuervorteil zu reduzieren sind: Mit Hilfe der nach WACC berechneten Kapitalkosten lässt sich der Unternehmenswert durch das Free-Cash-Flow-Verfahren (FCF) ermitteln. "
				+ "Dabei wird der Marktwert des Fremdkapitals von den diskontierten FCF-Werten subtrahiert.");
		waccText.addStyleName("wrap");
		waccText.addStyleName("descriptionText");
		iconWACC = new Embedded (null, new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
		iconWACC.setWidth(anzPixelIcon, UNITS_PIXELS);
		iconWACC.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void click(ClickEvent event) {
				if (waccText.isVisible()) {
					waccText.setVisible(false);
					iconWACC.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128.png"));
				}
				else {
					waccText.setVisible(true);
					iconWACC.setSource(new ThemeResource("./images/icons/newIcons/1421209133_circle_next_arrow_disclosure-128_rotated.png"));
				}
			}
		});


		//Szenario Beschreibung
		scenarioHeadline = new Label ("<h1>Szenarien</h1>");
		scenarioHeadline.setContentMode(Label.CONTENT_XHTML);
		scenarioHeadline.setStyleName("descriptionHeadline");
		
		scenarioText = new Label ("Für jedes Szenario können Sie unterschiedliche Berechnungswerte für die Eigen- und Fremdkapitalrendite, sowie die einzelnen Steuersätze angeben. "
				+ "Bei dem Flow-to-Equity Verfahren beschränken sich die geforderten Werte auf die Eigenkapitalkosten. Sie müssen mindestens ein Szenario in die Berechnung einbeziehen.");
		scenarioText.addStyleName("wrap");
		scenarioText.addStyleName("descriptionText");
	
		
	}

	
}
