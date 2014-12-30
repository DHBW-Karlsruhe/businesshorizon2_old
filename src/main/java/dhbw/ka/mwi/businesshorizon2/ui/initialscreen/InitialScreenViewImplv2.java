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
import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.*;

import com.mvplite.view.View;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der InitalScreenView (dem
 * Eingangs-Fenster).
 *
 * @author Christian Scherer, Marcel Rosenberger, Mirko Göpfrich, Marco Glaser
 *
 */
public class InitialScreenViewImplv2 extends Window implements InitialScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenViewImpl.class");

	@Autowired
	private InitialScreenPresenter presenter;

	@Autowired
	private UserProxy userProxy;
	private VerticalSplitPanel verticalSplitPanel;
	private HorizontalSplitPanel horizontalSplitPanel;
	private HorizontalSplitPanel horizontalSplitPanelRight;
	private HorizontalLayout middle;
	private HorizontalLayout right;
	private VerticalLayout mainLayout;
	private HorizontalLayout leftLayout;
	private VerticalLayout rightLayout;
	private HorizontalLayout topRightLayout;
	private VerticalLayout leftContentLayout;
	private VerticalLayout bottomLeftLayout;
	private VerticalLayout bottomRightLayout;
	private VerticalLayout bottomLayout;
	private Embedded homeIcon = new Embedded(null, new ThemeResource("./images/icons/newIcons/1418766062_house_home-128.png"));
	private Label seitenLabel;
	private Label descriptionLabel;
	private VerticalLayout leftLogoLayout;
	private Embedded logo;
	private Label gap;
	private VerticalLayout topBarSpacing;
	private VerticalLayout leftContainerSpacing;
	private Label splitter;
	private Label splitter2;
	private HorizontalLayout menuButtonsLayout;
	private VerticalLayout homeButtonLayout;
	private VerticalLayout accountButtonLayout;
	private Button homeButton;
	private Button accountButton;
	private Label homeButtonLabel;
	private Label accountButtonLabel;
	private Label userData;



	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 *
	 * @author Christian Scherer, Mirko Göpfrich, Marco Glaser
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}

	/**
	 * Diese Methode setzt das Layout für den Screen fest sowie den Titel der Anwendung.
	 *
	 * @author Christian Scherer, Mirko Göpfrich, Marco Glaser
	 */
	private void generateUi() {

		mainLayout = new VerticalLayout();
		leftLayout = new HorizontalLayout();
		rightLayout = new VerticalLayout();
		topRightLayout = new HorizontalLayout();
		leftContentLayout = new VerticalLayout();
		bottomLeftLayout = new VerticalLayout();
		bottomRightLayout = new VerticalLayout();
		bottomLayout = new VerticalLayout();
		gap = new Label();
		leftContainerSpacing = new VerticalLayout();
		splitter = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
		splitter2 = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
		menuButtonsLayout = new HorizontalLayout();
		homeButtonLayout = new VerticalLayout();
		accountButtonLayout = new VerticalLayout();
		homeButton = new Button();
		accountButton = new Button();
		homeButtonLabel = new Label("Startseite");
		accountButtonLabel = new Label("Mein Konto");

		mainLayout.setSizeFull();
		mainLayout.setStyleName("mainLayout");
		leftLayout.setSizeFull();
		leftLayout.setStyleName("leftContainer");
		rightLayout.setSizeFull();
		bottomLayout.setSizeFull();
		bottomLeftLayout.setSizeFull();
//		bottomRightLayout.setWidth(90, UNITS_PERCENTAGE);
//		bottomRightLayout.setStyleName("projectDetailsLayout");
		topRightLayout.setSizeFull();
		leftContentLayout.setSizeFull();
		//	leftContentLayout.setHeight(Sizeable.SIZE_UNDEFINED, 0);

		leftLogoLayout = new VerticalLayout();
		logo = new Embedded(null, new ThemeResource("images/Logo_businesshorizon.png"));

		gap.setHeight("4px");
		leftContainerSpacing.setSizeFull();

		homeIcon.setWidth(50, UNITS_PIXELS);
		homeIcon.setHeight(50, UNITS_PIXELS);

		seitenLabel = new Label("Startseite");
		seitenLabel.setStyleName("seitenLabel");
		seitenLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		descriptionLabel = new Label("Übersicht über alle Projekte");
		descriptionLabel.setStyleName("descriptionLabel");
		descriptionLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		splitter.setWidth(98, UNITS_PERCENTAGE);
		splitter2.setWidth(98, UNITS_PERCENTAGE);
		menuButtonsLayout.setWidth(100, UNITS_PERCENTAGE);
		menuButtonsLayout.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		homeButtonLayout.setSizeFull();
		accountButtonLayout.setSizeFull();
		homeButton.setHeight(30, UNITS_PIXELS);
		homeButton.setWidth(30, UNITS_PIXELS);
		homeButton.setStyleName("homeButton");
		accountButton.setHeight(30, UNITS_PIXELS);
		accountButton.setWidth(30, UNITS_PIXELS);
		accountButton.setStyleName("accountButton");
		homeButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		homeButtonLabel.setStyleName("topBarButtonLabel");
		accountButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		accountButtonLabel.setStyleName("topBarButtonLabel");

		horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setSplitPosition(30, UNITS_PERCENTAGE);
		horizontalSplitPanel.setLocked(true);
		horizontalSplitPanel.setStyleName("horizontalMain");
		verticalSplitPanel = new VerticalSplitPanel();
		verticalSplitPanel.setSplitPosition(126, UNITS_PIXELS);
		verticalSplitPanel.setLocked(true);
		verticalSplitPanel.setWidth(90, UNITS_PERCENTAGE);
		verticalSplitPanel.setHeight(100, UNITS_PERCENTAGE);
		horizontalSplitPanelRight = new HorizontalSplitPanel();
		horizontalSplitPanelRight.setSplitPosition(30, UNITS_PERCENTAGE);
		horizontalSplitPanelRight.setLocked(true);
		horizontalSplitPanelRight.addStyleName("horizontalBottom");
		horizontalSplitPanelRight.setHeight(90, UNITS_PERCENTAGE);
		horizontalSplitPanelRight.setWidth(100, UNITS_PERCENTAGE);

		leftLogoLayout.addComponent(logo);
		leftContentLayout.addComponent(gap);
		leftContentLayout.addComponent(homeIcon);
		leftContentLayout.addComponent(seitenLabel);
		leftContentLayout.addComponent(descriptionLabel);
		leftContentLayout.addComponent(splitter);
		leftContentLayout.addComponent(menuButtonsLayout);
		leftContentLayout.addComponent(splitter2);
		leftContentLayout.addComponent(leftContainerSpacing);
		leftContentLayout.setExpandRatio(leftContainerSpacing, 1.0f);
		leftLayout.addComponent(leftLogoLayout);
		leftLayout.addComponent(leftContentLayout);
		leftLayout.setExpandRatio(leftContentLayout, 1.0f);
		leftLogoLayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		leftContentLayout.setComponentAlignment(homeIcon, Alignment.TOP_CENTER);
		leftContentLayout.setComponentAlignment(seitenLabel, Alignment.TOP_CENTER);
		leftContentLayout.setComponentAlignment(descriptionLabel, Alignment.TOP_CENTER);
		menuButtonsLayout.addComponent(homeButtonLayout);
		menuButtonsLayout.addComponent(accountButtonLayout);
		homeButtonLayout.addComponent(homeButton);
		homeButtonLayout.addComponent(homeButtonLabel);
		accountButtonLayout.addComponent(accountButton);
		accountButtonLayout.addComponent(accountButtonLabel);

		homeButtonLayout.setComponentAlignment(homeButton, Alignment.TOP_CENTER);
		homeButtonLayout.setComponentAlignment(homeButtonLabel, Alignment.MIDDLE_CENTER);
		accountButtonLayout.setComponentAlignment(accountButton, Alignment.TOP_CENTER);
		accountButtonLayout.setComponentAlignment(accountButtonLabel, Alignment.MIDDLE_CENTER);

		leftLogoLayout.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		leftLogoLayout.setHeight(100, UNITS_PERCENTAGE);
		leftContentLayout.setSizeFull();

		rightLayout.addComponent(verticalSplitPanel);
		bottomLayout.addComponent(horizontalSplitPanelRight);

		horizontalSplitPanel.addComponent(leftLayout);
		horizontalSplitPanel.addComponent(rightLayout);

		verticalSplitPanel.addComponent(topRightLayout);
		verticalSplitPanel.addComponent(bottomLayout);

//		horizontalSplitPanelRight.setSecondComponent(bottomRightLayout);

		rightLayout.setComponentAlignment(verticalSplitPanel, Alignment.MIDDLE_CENTER);
		bottomLayout.setComponentAlignment(horizontalSplitPanelRight, Alignment.MIDDLE_CENTER);

		mainLayout.addComponent(horizontalSplitPanel);

		setContent(mainLayout);

		TopBarButton button = new TopBarButton("addProjectButton", "Neues Projekt hinzufügen");
		addTopButton(button);
		button = new TopBarButton("editProjectButton", "Projekt bearbeiten");
		addTopButton(button);
		button = new TopBarButton("deleteProjectButton", "Projekt löschen");
		addTopButton(button);

		topBarSpacing = new VerticalLayout();
		topBarSpacing.setSizeFull();
		topRightLayout.addComponent(topBarSpacing);
		topRightLayout.setExpandRatio(topBarSpacing, 1.0f);

	}

	/**
	 * Methode zum Darstellen der Userdaten im Header
	 *
	 * @param username
	 * Der angezeigte Username
	 * @author Mirko Göpfrich
	 */
	public void showUserData(String username) {
		/*
		 * Wenn schon ein UserData-String angezeigt wird, muss dieser zunaechst entfernt werden.
		 * Ansonsten werden mehrere UserData-Strings angezeigt, wenn zwischen Projektuebersicht
		 * und Prozesssicht gesprungen wird.
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
	 * : Die PeriodenListe
	 * @param rightView
	 * : Die Infoanzeige
	 * @author Christian Scherer, Marco Glaser
	 */
	@Override
	public void showView(View leftView, View rightView) {
		horizontalSplitPanelRight.setFirstComponent((Component) leftView);
		horizontalSplitPanelRight.setSecondComponent((Component) rightView);
	}

	/**
	 * Diese Methode fügt einen Button zur Button-Leiste (topRightLayout) hinzu.
	 *
	 * @param button
	 * : Der Button
	 * @author Marco Glaser
	 */
	public void addTopButton(Component button){
		topRightLayout.addComponent(button);
		topRightLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Diese Methode fügt einen Button zur Button-Leiste (topRightLayout) an der gewünschten Stelle hinzu
	 * und löscht den Button der vorher an dieser Stelle war.
	 *
	 * @param button
	 * : Der Button
	 * @param index
	 * : Stelle, wo der Button gesetzt werden soll
	 * @author Marco Glaser
	 */
	public void setTopButton(Component button, int index){
		Component comp = topRightLayout.getComponent(index);
		if(comp != null){
			topRightLayout.removeComponent(comp);
		}
		topRightLayout.addComponent(button, index);
	}


}