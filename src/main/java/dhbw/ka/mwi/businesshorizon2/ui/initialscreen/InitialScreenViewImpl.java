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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der InitalScreenView (dem
 * Eingangs-Fenster).
 * 
 * @author Christian Scherer
 * 
 */
public class InitialScreenViewImpl extends Window implements InitialScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("InitialScreenViewImpl.class");

	@Autowired
	private InitialScreenPresenter presenter;
	
	@Autowired
	private UserProxy userProxy;

	private VerticalSplitPanel verticalPanel;

	private HorizontalSplitPanel horizontalPanel;

	private VerticalLayout heading;

	private Label title;

	private Label userData;
	
	private AuthenticationServiceInterface authenticationService;

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
		verticalPanel.setSplitPosition(100, UNITS_PIXELS);
		verticalPanel.setLocked(true);
		logger.debug("Neues Vertikales Panel erstellt für Überschrift");

		heading = new VerticalLayout();
		title = new Label("<h1>Business Horizon 2</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		heading.addComponent(title);

		verticalPanel.setFirstComponent(heading);
		logger.debug("Überschrift hinzugefügt und dem vertikalen Panel übergeben");

		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSizeFull();
		horizontalPanel.setSplitPosition(50, UNITS_PERCENTAGE);
		horizontalPanel.setLocked(true);

		verticalPanel.setSecondComponent(horizontalPanel);
		logger.debug("Horizontales Panel für Projkte und Infos erstellt und an das vertikale Panel übergeben");

		setContent(verticalPanel);
		logger.debug("Vertikales Panel mit allen Elementen an an das Hauptfenster übergeben");
		//logout button hintzufuegen
		this.addButton("Logout");
	}

	private void addButton(String text) {
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
		heading.addComponent(navigationButton);
		heading.setComponentAlignment(navigationButton, Alignment.TOP_RIGHT);
		
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
		heading.addComponent(userData);
	}
}
