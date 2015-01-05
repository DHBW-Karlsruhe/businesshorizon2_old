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

package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.buttonsMiddle;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.FileResource;
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

/**
 * Dies ist die Vaadin-Implementierung der ParameterButtonsMiddleView. Sie stellt das mittlere Layout des Parameterscreens dar.
 * 
 * @author Tobias Lindner
 * 
 */
public class ParameterButtonsMiddleViewImpl extends VerticalLayout implements
		ParameterButtonsMiddleViewInterface {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ParameterButtonsMiddleViewImpl.class");

	@Autowired
	private ParameterButtonsMiddlePresenter presenter;
	
	private HorizontalLayout layoutButton1;
	private HorizontalLayout layoutButton2;
	private HorizontalLayout layoutButton3;
	
	private Embedded methodIcon;
	
	private Label methodLabel;
	private Button method;
	private Button description;
	private Button next;
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
	 * Konkrete Ausprogrammierung der UI-Elemente.
	 * 
	 * @author Tobias Lindner
	 */
	private void generateUI() {
		setSpacing(true);
		
		float hoehe = 95;
		float breite = 260;	
		
		/**String hoehe2 = "95px";
		String breite2 = "260px";
		
		layoutButton1 = new HorizontalLayout();
		layoutButton1.setHeight(hoehe2);
		layoutButton1.setWidth(breite2);
		layoutButton1.setStyleName("paramButtonActivated");
		
		methodIcon  = new Embedded(null, new ThemeResource("./images/icons/newIcons/1418831298_common_calendar_month-128.png"));
		
		layoutButton1.addComponent(methodIcon);
		
		methodLabel = new Label ("deterministische Methode");
		layoutButton1.addComponent(methodLabel);
		
		layoutButton1.setComponentAlignment(methodIcon, Alignment.MIDDLE_LEFT);
		layoutButton1.setComponentAlignment(methodLabel, Alignment.BOTTOM_CENTER);
		
		addComponent(layoutButton1);
		setComponentAlignment(layoutButton1, Alignment.TOP_CENTER);
		*/
		
		method = new Button ("deterministische Methode");
		method.setStyleName("paramButtonActivated");
		method.setHeight(hoehe, UNITS_PIXELS);
		method.setWidth(breite, UNITS_PIXELS);
		method.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showInput();
			}
			
		});
		method.setIcon(new ThemeResource ("images/icons/newIcons/1418831298_common_calendar_month-128.png"));
		addComponent(method);
		setComponentAlignment(method, Alignment.TOP_CENTER);

		description = new Button ("Kurzbeschreibung");
		description.setStyleName("paramButton");
		description.setHeight(hoehe, UNITS_PIXELS);
		description.setWidth(breite, UNITS_PIXELS);
		description.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showDescription();
			}
			
		});
		description.setIcon(new ThemeResource ("images/icons/newIcons/1418766020_editor_documents_files-128.png"));
		addComponent(description);
		setComponentAlignment(description, Alignment.MIDDLE_CENTER);
		
		gap = new Label();
		gap.setHeight("200px");
		addComponent(gap);
		
		next = new Button ("Weiter zu Schritt 3");
		next.setStyleName("paramButton");
		next.setHeight(hoehe, UNITS_PIXELS);
		next.setWidth(breite, UNITS_PIXELS);
		next.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showStep3();
			}
			
		});
		next.setIcon(new ThemeResource ("images/icons/newIcons/1418766041_circle_arrow-forward_next-128.png"));
		addComponent(next);
		setComponentAlignment(next, Alignment.BOTTOM_CENTER);

	}
	
	public void setMethodButtonActivated () {
		method.setStyleName("paramButtonActivated");
		description.setStyleName("paramButton");
		method.requestRepaint();
		description.requestRepaint();
	}
	
	public void setDescriptionButtonActivated () {
		method.setStyleName("paramButton");
		description.setStyleName("paramButtonActivated");
		method.requestRepaint();
		description.requestRepaint();
	}
	
}
