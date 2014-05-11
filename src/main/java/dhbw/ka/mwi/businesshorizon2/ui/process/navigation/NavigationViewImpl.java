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

package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;

/**
 * Diese View stellt die Vaadin-Implementierung der Navigation zur Prozessansicht dar.
 * Sie nutzt hierzu insbesondere Vaadings Buttons.
 * 
 * @author Julius Hacker, Marcel Rosenberger
 *
 */
public class NavigationViewImpl extends HorizontalLayout implements NavigationViewInterface {
	private static final long serialVersionUID = -6649221675778809749L;

	private static final Logger logger = Logger.getLogger("NavigationViewImpl.class");
	
	@Autowired
	private NavigationPresenter presenter;
	
	@Autowired
	private UserProxy userProxy;
	
	private HorizontalLayout full;
	
	private VerticalLayout layout;
	
	private HorizontalLayout innerlayout;
	
	private VerticalLayout topbar;
	
	private VerticalLayout topbarinnerlayout;
	
	private Label arrow;
	
	@Autowired
	private AuthenticationServiceInterface authenticationService;
	
	@Autowired
	private PersistenceServiceInterface persistenceService;
	
	private Map<NavigationSteps, Button> navigationButtons = new HashMap<NavigationSteps, Button>();
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
	}

	/**
	 * Diese Methode baut die Navigationsoberflaeche auf.
	 * 
	 * @author Julius Hacker, Mirko Göpfrich
	 */
	private void generateUi() {
		this.setSizeFull();
		
		//this.arrow = new Label("&#10144;");
		//arrow.setContentMode(Label.CONTENT_XHTML);
		
		this.full = new HorizontalLayout();
		//this.full.setSizeFull();
		this.full.setWidth("270px");
		
		this.layout = new VerticalLayout();
		this.layout.setStyleName("navigation");
		//this.layout.setSizeFull();
		//this.layout.setWidth("300px");
		
		
		this.innerlayout = new HorizontalLayout();
		innerlayout.setStyleName("LayoutNavigationsButtons");
		
		this.topbar = new VerticalLayout();
		//this.topbar.setSizeFull();
		this.topbar.setWidth("600px");
		
		this.topbarinnerlayout = new VerticalLayout();
		
		this.addOverviewButton();
		
		this.addProjectName();
		
		
		
		this.addNavigationButton(NavigationSteps.METHOD);
		this.addNavigationButton(NavigationSteps.PARAMETER);
		this.addNavigationButton(NavigationSteps.PERIOD);
		this.addNavigationButton(NavigationSteps.SCENARIO);
		this.addNavigationButton(NavigationSteps.OUTPUT);
		this.addLogoutButton("Logout");
		
		
		full.addComponent(topbar);
		full.addComponent(layout);
		this.addComponent(full);
		
		
		topbar.addComponent(topbarinnerlayout);
		topbar.setComponentAlignment(topbarinnerlayout, Alignment.TOP_LEFT);
		this.addComponent(topbar);
		
		topbar.addComponent(innerlayout);
		topbar.setComponentAlignment(innerlayout, Alignment.BOTTOM_CENTER);
		this.addComponent(layout);
		
		
	}
	
	public void showNavigation() {
		this.removeAllComponents();
		generateUi();

	}

	private void addProjectName() {
		Label projectName = new Label("Sie bearbeiten derzeit das Projekt: " + presenter.getProjectName());
		projectName.setStyleName("projectname");
		this.full.addComponent(projectName);
		this.full.setComponentAlignment(projectName, Alignment.MIDDLE_CENTER);
		
	}

	private void addOverviewButton() {
		Button overviewButton = new Button("Projektübersicht");
		overviewButton.addStyleName("default");
		overviewButton.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showProjectList();
				
				
			}
		});
		
		this.topbarinnerlayout.addComponent(overviewButton);
		this.topbarinnerlayout.setComponentAlignment(overviewButton, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Diese Methode fuegt der Navigation einen Navigationsbutton hinzu und registriert
	 * einen passenden ClickListener auf ihn.
	 * 
	 * @param value Der Prozessschritt, der durch den Navigationsbutton repraesentiert werden soll.
	 * @author Julius Hacker
	 */
	@Override
	public void addNavigationButton(final NavigationSteps navigationStep) {
		//this.innerlayout.addComponent(arrow);
		Button navigationButton = new Button(navigationStep.getCaption());
		this.navigationButtons.put(navigationStep, navigationButton);
		
		navigationButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7411091035775152765L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showStep(navigationStep);
			}
		});
		
		navigationButton.setEnabled(false);
		navigationButton.setWidth(Sizeable.SIZE_UNDEFINED, 5);
		this.innerlayout.addComponent(navigationButton);
		this.innerlayout.setComponentAlignment(navigationButton, Alignment.BOTTOM_CENTER);
		
	}
	private void addLogoutButton(String text) {
		Button logoutButton = new Button(text);
		logoutButton.addStyleName("default");
		logoutButton.setVisible(true);
		logoutButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7411091035775152765L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				//Callback-Methode, ruft die eigentliche Logout-Methode im Presenter auf
				presenter.doLogout();
			}
		});
		
		logoutButton.setEnabled(true);
		this.layout.addComponent(logoutButton);
		this.layout.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
	
		
	}
	
	public void setButtonToInvalid(NavigationSteps navigationStep, boolean invalid) {
		logger.debug("Setze Fehlerzustand von Navigationsbutton");
		if(invalid) {
			this.navigationButtons.get(navigationStep).setComponentError(new UserError("Ungueltige Daten - Aenderungen noetig!"));
		}
		else {
			this.navigationButtons.get(navigationStep).setComponentError(null);
		}
	}
	
	public void setButtonActive(NavigationSteps navigationStep, boolean active) {
			this.navigationButtons.get(navigationStep).setEnabled(active);
	}

}
