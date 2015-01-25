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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.ui.ButtonMiddle;

/**
 * Dies ist die Vaadin-Implementierung der ParameterButtonsMiddleView. Sie stellt das mittlere Layout des Parameterscreens dar.
 * 
 * @author Tobias Lindner
 * 
 */
public class ButtonsMiddleViewImpl extends VerticalLayout implements
ButtonsMiddleViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ButtonsMiddleViewImpl.class");

	@Autowired
	private ButtonsMiddlePresenter presenter;

	private ButtonMiddle button1;
	private ButtonMiddle button2;
	private ButtonMiddle button3;

	private Label gap;

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
	 * Konkrete Ausprogrammierung der UI-Elemente. Erzeugung der drei Buttons.
	 * 
	 * @author Tobias Lindner
	 */
	private void generateUI() {
		setSpacing(true);

		setWidth(85, UNITS_PERCENTAGE);
		setHeight(100, UNITS_PERCENTAGE);

		button1 = new ButtonMiddle ("./images/icons/newIcons/1418831828_editor_memo_note_pad-128.png", "Methodenauswahl", new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showMethodScreen();
			}

		});
		addComponent(button1);
		button1.setActivated();

		button2 = new ButtonMiddle ("./images/icons/newIcons/1418766020_editor_documents_files-128.png", "Beschreibung", new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button1.setDeactivated();
				button2.setActivated();
				presenter.showDescription();
			}

		});
		addComponent(button2);

		gap = new Label ();
		gap.setSizeFull();
		addComponent(gap);
		setExpandRatio(gap, 1.0f);

		button3 = new ButtonMiddle ("./images/icons/newIcons/1418766041_circle_arrow-forward_next-128.png", "Weiter zu Schritt 2", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.showParameterScreen();
				button2.setDeactivated();
				button1.setActivated();
			}

		});

		button3.changeStyleClass("buttonBottom");
		button3.changeDirection();
		button3.setVisible(true);
		addComponent(button3);

	}
	
	public void setInitialButtons() {
		this.button1.setDetails("./images/icons/newIcons/1418831828_editor_memo_note_pad-128.png", "Methodenauswahl", new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showMethodScreen();
			}

		});
		
		button1.setActivated();
		button2.setDeactivated();
		
		this.button2.setVisible(true);
		
		setGoToStep(2);
	}
	
	/**
	 * Ändert die Buttons für die Eingabe der Stochastischen Parameter.
	 * 
	 * @author Tobias Lindner
	 */
	public void setStochasticParameter () {

		this.button1.setDetails("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Stochastische", "Methode", new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showParameterScreen();
			}

		});
		
		this.button2.setVisible(true);

		logger.debug("View: setStochasticParameter");
	}


	/**
	 * Ändert die Buttons für die Eingabe der Deterministischen Parameter.
	 * 
	 * @author Tobias Lindner
	 * 
	 */
	public void setDeterministicParameter () {

		
		this.button1.setDetails("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Deterministische", "Methode", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showParameterScreen();

			}

		});
		
		this.button2.setVisible(true);

		logger.debug ("View: setDeterministicParameter");
	}
	
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons an FCF an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setFCFButton() {	
		this.button1.setDetails("./images/icons/newIcons/1418831563_circle_backup_time_history_recent_time-machine_-128.png", "FCF", "Free Cash Flow", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showPeriodScreen();
			}
		});

		this.button2.setVisible(true);
//		setExpandRatio(gap, 1.0f);
		
		logger.debug ("View: setFCFButton");
		
	}
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons an GKV an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setGKVButton() {
		this.button1.setDetails("./images/icons/newIcons/1418831563_circle_backup_time_history_recent_time-machine_-128.png", "GKV", "Gesamtkostenverfahren", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showPeriodScreen();
			}
		});

		this.button2.setVisible(true);
		
		logger.debug ("View: setGKVButton");
	}
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons an UKV an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setUKVButton() {
		this.button1.setDetails("./images/icons/newIcons/1418831563_circle_backup_time_history_recent_time-machine_-128.png", "UKV", "Umsatzkostenverfahren", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showPeriodScreen();
			}
		});

		this.button2.setVisible(true);
		
		logger.debug ("View: setUKVButton");
	}
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons für den ScenarioScreen an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setScenarioButton() {
		this.button1.setDetails("./images/icons/newIcons/1418831239_editor_attachment_paper_clip_2-128.png", "Szenarien", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showScenarioScreen();
			}
		});
		
		this.button2.setVisible(true);

		logger.debug ("View: setScenarioButton");
	}
	
	/**
	 * Diese Methode passt die Beschriftung des mittleren Buttons für den Result an.
	 * 
	 * @author Tobias Lindner
	 */
	public void setResultButton() {
		this.button1.setDetails("./images/icons/newIcons/1418775155_device_board_presentation_content_chart-128.png", "Ergebnis", new LayoutClickListener () {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				button2.setDeactivated();
				button1.setActivated();
				presenter.showResultScreen();;
			}
		});
		
		this.button2.setVisible(false);

		logger.debug ("View: setResultButton");
	}
	
	public void hideStepButton(){
		button3.setVisible(false);
	}

	public void setGoToStep(int step){
		LayoutClickListener lcl;
		switch (step) {
		case 2:
			lcl = generateListener2();
			break;
		case 3:
			lcl = generateListener3();
			break;
		case 4:
			lcl = generateListener4();
			break;
		case 5:
			lcl = generateListener5();
			break;

		default:
			lcl = null;
			break;
		}
		button3.setVisible(true);
		button3.setDetails("Weiter zu Schritt "+step, lcl);
	}

	private LayoutClickListener generateListener2() {
		LayoutClickListener lcl = new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.showParameterScreen();
				button2.setDeactivated();
				button1.setActivated();
			}

		};
		return lcl;
	}
	
	private LayoutClickListener generateListener3() {
		LayoutClickListener lcl = new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.showPeriodScreen();
				button2.setDeactivated();
				button1.setActivated();
			}

		};
		return lcl;
	}
	
	private LayoutClickListener generateListener4() {
		LayoutClickListener lcl = new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.showScenarioScreen();
				button2.setDeactivated();
				button1.setActivated();
			}

		};
		return lcl;
	}
	
	private LayoutClickListener generateListener5() {
		LayoutClickListener lcl = new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.showResultScreen();
				button2.setDeactivated();
				button1.setActivated();
			}

		};
		return lcl;
	}
	
	public void enableNext () {
		button3.setEnabled(true);
	}
	
	public void disableNext() {
		button3.setEnabled(false);
	}

}
