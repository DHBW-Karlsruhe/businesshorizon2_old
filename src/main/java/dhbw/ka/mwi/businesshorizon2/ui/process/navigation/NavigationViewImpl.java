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


package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;

/**
 * Diese View stellt die Vaadin-Implementierung der Navigation zur Prozessansicht dar.
 * Sie nutzt hierzu insbesondere Vaadings Buttons.
 * 
 * @author Julius Hacker
 *
 */
public class NavigationViewImpl extends HorizontalLayout implements NavigationViewInterface {
	private static final long serialVersionUID = -6649221675778809749L;

	private Logger logger = Logger.getLogger("NavigationViewImpl.class");
	
	@Autowired
	private NavigationPresenter presenter;
	@Autowired
	private UserProxy userProxy;
	
	private HorizontalLayout layout;
	private HorizontalLayout innerlayout;
	
	private AuthenticationServiceInterface authenticationService;
	
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
	 * @author Julius Hacker
	 */
	private void generateUi() {
		setSizeFull();
		
		this.layout = new HorizontalLayout();
		this.layout.setSizeFull();
		
		this.innerlayout = new HorizontalLayout();
		
		this.addOverviewButton();
		
		this.addProjectName();
		
		this.addNavigationButton(NavigationSteps.METHOD);
		this.addNavigationButton(NavigationSteps.PARAMETER);
		this.addNavigationButton(NavigationSteps.PERIOD);
		this.addNavigationButton(NavigationSteps.SCENARIO);
		this.addNavigationButton(NavigationSteps.OUTPUT);
		this.addLogoutButton("Logout");
		
		layout.addComponent(innerlayout);
		layout.setComponentAlignment(innerlayout, Alignment.BOTTOM_CENTER);
		this.addComponent(layout);
	}
	
	public void showNavigation() {
		this.removeAllComponents();
		generateUi();

	}

	private void addProjectName() {
		Label projectName = new Label(presenter.getProjectName());
		
		this.innerlayout.addComponent(projectName);
		
	}

	private void addOverviewButton() {
		Button overviewButton = new Button("Zur Projektliste");
		overviewButton.addListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showProjectList();				
			}
		});
		
		this.innerlayout.addComponent(overviewButton);
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
		
		this.innerlayout.addComponent(navigationButton);
		this.innerlayout.setComponentAlignment(navigationButton, Alignment.BOTTOM_CENTER);
		
	}
	private void addLogoutButton(String text) {
		Button navigationButton = new Button(text);
		navigationButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7411091035775152765L;

			@Override
			public void buttonClick(ClickEvent event) {
				//do the logout
				try {
					//TODO :autowired user is not set
					authenticationService.doLogout(userProxy.getSelectedUser());
				} catch (UserNotLoggedInException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		navigationButton.setEnabled(true);
		this.innerlayout.addComponent(navigationButton);
		this.innerlayout.setComponentAlignment(navigationButton, Alignment.TOP_RIGHT);
		
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
