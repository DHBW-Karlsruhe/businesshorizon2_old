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
import com.vaadin.terminal.Sizeable;
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
 * @author Christian Scherer, Marcel Rosenberger, Mirko Göpfrich
 * 
 */
public class InitialScreenViewImpl extends Window implements InitialScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenViewImpl.class");

	@Autowired
	private InitialScreenPresenter presenter;
	
	@Autowired
	private UserProxy userProxy;

	private VerticalSplitPanel verticalSplitPanel;

	private HorizontalSplitPanel horizontalSplitPanel;

	private VerticalLayout header;
	
	private HorizontalLayout horizontal;
	
	private VerticalLayout left;
	
	private HorizontalLayout middle;
	
	private HorizontalLayout right;
	
	private Label title;

	private Label userData;
	
	

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}

	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu
	 * "Business Horizon 2.1" und erstellt die Überschrift sowie die zwei
	 * variablen Komponenten.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich
	 */
	private void generateUi() {
		setCaption("Business Horizon 2.1");
		logger.debug("Ueberschrift fuer Browser erstellt");

		//Teilt das Fenster vertikal in zwei Bereiche auf und erstellt eine horizontale Trennlinie (nicht verstellbar).
		verticalSplitPanel = new VerticalSplitPanel();
		verticalSplitPanel.setSplitPosition(100, Sizeable.UNITS_PIXELS);
		verticalSplitPanel.setLocked(true);
		verticalSplitPanel.setStyleName("small");
		logger.debug("Neues Vertikales SplitPanel erstellt");

		//erzeugt die Layouts
		header = new VerticalLayout();
		horizontal = new HorizontalLayout();
		left = new VerticalLayout();
		middle = new HorizontalLayout();
		right = new HorizontalLayout();
		
		
		header.setSizeFull();
		horizontal.setSizeFull();
		left.setWidth(50, UNITS_PERCENTAGE);
		
		//verschachtelt die Layouts
		header.addComponent(horizontal);
		horizontal.addComponent(left);
		horizontal.addComponent(middle);
		horizontal.addComponent(right);
		
		
		horizontal.setComponentAlignment(right, Alignment.TOP_RIGHT);
		
		//fügt dem oberen Panel die Überschrift hinzu
		verticalSplitPanel.setFirstComponent(header);
		title = new Label("<h1>Business Horizon 2.1</h1>");
		title.setStyleName("ueberschriften");
		title.setContentMode(Label.CONTENT_XHTML);
		left.addComponent(title);
		left.setComponentAlignment(title, Alignment.TOP_LEFT);
		logger.debug("Überschrift hinzugefügt und dem oberen vertikalen Panel übergeben");
		
		//Teilt das Panel horizontal un zwei gleiche Bereiche auf und ertstellt eine vertiakel Trennlinie (nicht verstellbar.)
		horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setSizeFull();
		horizontalSplitPanel.setSplitPosition(50, UNITS_PERCENTAGE);
		horizontalSplitPanel.setLocked(true);
		horizontalSplitPanel.setStyleName("small");
		
		//fügt dem unteren vertikalen Panel ein horizontales SplitPanel hinzu.
		verticalSplitPanel.setSecondComponent(horizontalSplitPanel);
		logger.debug("Horizontales SplitPanel für Projekte und Infos erstellt und an das untere vertikale Panel übergeben");

		// Setzt das vertikale Splitpanel (äußeres Panel) inkl innere Panels als Inhalt für das Fenster.
		setContent(verticalSplitPanel);
		logger.debug("Vertikales SplitPanel mit allen Elementen an das Hauptfenster übergeben");
		
		//ruft die Methode auf, welche den LogoutButton zum Sreen hinzufügt
		this.addLogoutButton();
		
	}
	
	/**
	 * Methode zum Darstellen der Userdaten im Header
	 * 
	 * @param username
	 *            Der angezeigte Username
	 * @author Mirko Göpfrich
	 */
	public void showUserData(String username) {
		/*
		 *  Wenn schon ein UserData-String angezeigt wird, muss dieser zunaechst entfernt werden.
		 *  Ansonsten werden mehrere UserData-Strings angezeigt, wenn zwischen Projektuebersicht
		 *  und Prozesssicht gesprungen wird. 
		 */
		Label userInfo = new Label("Sie sind angemeldet als: ");
		middle.addComponent(userInfo);
		middle.setComponentAlignment(userInfo, Alignment.BOTTOM_LEFT);
		
		
		if(userData != null) {
			middle.removeComponent(userData);
			middle.removeComponent(userInfo);
		}
		
		userData = new Label(username);
		userData.setContentMode(Label.CONTENT_XHTML);
		userData.setVisible(true);
		middle.addComponent(userData);
		middle.setComponentAlignment(userData, Alignment.BOTTOM_LEFT);;
	}
	
	/**
	 * 
	 */
	private void addLogoutButton() {
		Button logoutButton = new Button("Logout");
		logoutButton.setStyleName("default");
		logoutButton.addListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 7411091035775152765L;
			
			@Override
			public void buttonClick(ClickEvent event) {
				//Callback-Methode, ruft die eigentliche Logout-Methode im Presenter auf
				presenter.doLogout();
			}
		});
		
		//LogoutButton hinzufügen und ausrichten
		logoutButton.setEnabled(true);
		right.addComponent(logoutButton);
		right.setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);
		
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
		horizontalSplitPanel.setFirstComponent((Component) leftView);
		horizontalSplitPanel.setSecondComponent((Component) rightView);
	}


}
