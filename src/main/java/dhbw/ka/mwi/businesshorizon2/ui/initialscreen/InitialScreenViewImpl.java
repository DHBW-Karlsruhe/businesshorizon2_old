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


package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import javax.annotation.PostConstruct;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.*;


import com.mvplite.view.View;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der InitalScreenView (dem
 * Eingangs-Fenster).
 * 
 * @author Christian Scherer, Marcel Rosenberger
 * 
 */
public class InitialScreenViewImpl extends Window implements InitialScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenViewImpl.class");

	@Autowired
	private InitialScreenPresenter presenter;
	
	@Autowired
	private UserProxy userProxy;

	private VerticalSplitPanel verticalPanel;

	private HorizontalSplitPanel horizontalPanel;

	private HorizontalLayout heading;
	
	private HorizontalLayout left;

	private Label title;

	private Label userData;
	
	

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu
	 * "Business Horizon 2" und erstellt die Überschrift sowie die zwei
	 * variablen Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	private void generateUi() {
		setCaption("Business Horizon 2.1");
		logger.debug("Überschrift für Browser erstellt");

		verticalPanel = new VerticalSplitPanel();
		verticalPanel.setSizeFull();
		verticalPanel.setSplitPosition(150, UNITS_PIXELS);
		verticalPanel.setLocked(true);
		logger.debug("Neues Vertikales Panel erstellt für Überschrift");

		heading = new HorizontalLayout();
		left = new HorizontalLayout();
		
		
		
		title = new Label("<h1>Business Horizon 2</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		heading.addComponent(title);
		heading.addComponent(left);
		heading.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

		verticalPanel.setFirstComponent(heading);
		logger.debug("Überschrift hinzugefügt und dem vertikalen Panel übergeben");

		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSizeFull();
		horizontalPanel.setSplitPosition(150, UNITS_PERCENTAGE);
		horizontalPanel.setLocked(true);

		verticalPanel.setSecondComponent(horizontalPanel);
		logger.debug("Horizontales Panel für Projkte und Infos erstellt und an das vertikale Panel übergeben");

		setContent(verticalPanel);
		logger.debug("Vertikales Panel mit allen Elementen an an das Hauptfenster übergeben");

		
		this.addLogoutButton("");
	}

	private void addLogoutButton(String text) {
		Button logoutButton = new Button("");
		logoutButton.addStyleName(Reindeer.BUTTON_LINK);
		logoutButton.setIcon(new ThemeResource("images/logout.jpg"));
		logoutButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7411091035775152765L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				//Callback-Methode, ruft die eigentliche Logout-Methode im Presenter auf
				presenter.doLogout();
			}
		});
		
		//logout button hinzufuegen
		
		logoutButton.setEnabled(true);
		left.addComponent(logoutButton);
		left.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
		
	}


	/**
	 * Diese Methode setzt nun die übergebenen zwei Views das Horizontale Layout
	 * unter der Überschrift.
	 * 
	 * @param leftView
	 *            : Die PeriodenListe
	 * @param rightView
	 *            : Die Infoanzeige
	 * @author Christian Scherer
	 */
	@Override
	public void showView(View leftView, View rightView) {
		horizontalPanel.setFirstComponent((Component) leftView);
		horizontalPanel.setSecondComponent((Component) rightView);
	}

	/**
	 * Methode zum Darstellen der Userdaten im Header
	 * 
	 * @param username
	 *            Der angezeigte Username
	 */
	public void showUserData(String username) {
		/*
		 *  Wenn schon ein UserData-String angezeigt wird, muss dieser zunaechst entfernt werden.
		 *  Ansonsten werden mehrere UserData-Strings angezeigt, wenn zwischen Projektuebersicht
		 *  und Prozesssicht gesprungen wird. 
		 */
		if(userData != null) {
			heading.removeComponent(userData);
		}
		
		userData = new Label("<h2>" + username + "</h2>");
		userData.setContentMode(Label.CONTENT_XHTML);
		userData.setVisible(false);
		heading.addComponent(userData);
	}
}
