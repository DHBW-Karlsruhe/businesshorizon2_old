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

package dhbw.ka.mwi.businesshorizon2.ui.parameterScreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;
import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.*;

import com.mvplite.view.View;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component.Event;
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
public class ParameterScreenViewImpl extends Window implements ParameterScreenViewInterface {
private static final long serialVersionUID = 1L;

private static final Logger logger = Logger.getLogger("ParameterScreenViewImpl.class");

@Autowired
private ParameterScreenPresenter presenter;

@Autowired
private UserProxy userProxy;

private VerticalSplitPanel verticalSplitPanel;

private HorizontalSplitPanel horizontalSplitPanel;

private HorizontalSplitPanel horizontalSplitPanelRight;

private VerticalLayout header;

private HorizontalLayout horizontal;

//linke Seite
private Embedded logo;
private Embedded titleIcon;

private Label homeButtonLabel;
private Label accountButtonLabel;
private Label gap;
private Label middleGap;
private Label bottomGap;

private VerticalLayout left;
private VerticalLayout descriptionLayout;
private VerticalLayout leftLogolayout;
private VerticalLayout homeButtonLayout;
private VerticalLayout accountButtonLayout;
private HorizontalLayout menuButtonsLayout;

private Button homeButton;
private Button accountButton;

private HorizontalLayout middle;

private HorizontalLayout right;

private Label title;

private Label leftL;

private Label rightTopL;

private Label rightBottomL;

private Label leftBottomL;

private Label userData;

private Label seitenLabel;
private Label seitenProjectName;
private Label descriptionLabel;
private Label descriptionLabel2;
private Label splitter;
private Label splitter2;
//Ende Linke Seite

private VerticalLayout mainLayout = new VerticalLayout();
private HorizontalLayout leftLayout = new HorizontalLayout();
private VerticalLayout rightLayout = new VerticalLayout();
private HorizontalLayout topRightLayout = new HorizontalLayout();
private VerticalLayout leftContentLayout = new VerticalLayout();
private VerticalLayout bottomLeftLayout = new VerticalLayout();
private VerticalLayout bottomRightLayout = new VerticalLayout();
private VerticalLayout bottomLayout = new VerticalLayout();
private Label logoLabel = new Label();



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
	
	mainLayout.setSizeFull();
	mainLayout.setStyleName("mainLayout");
	leftLayout.setSizeFull();
	leftLayout.setStyleName("leftContainer");
	rightLayout.setSizeFull();
	bottomLayout.setSizeFull();
	bottomLeftLayout.setSizeFull();
	//leftContentLayout.setWidth(85, UNITS_PERCENTAGE);
	//leftContentLayout.setHeight(100, UNITS_PERCENTAGE);
	topRightLayout.setStyleName("topBar");
	leftContentLayout.setSizeFull();
	
	horizontalSplitPanel = new HorizontalSplitPanel();
	horizontalSplitPanel.setSplitPosition(30, UNITS_PERCENTAGE);
	horizontalSplitPanel.setLocked(true);
	horizontalSplitPanel.setStyleName("horizontalMain");
	verticalSplitPanel = new VerticalSplitPanel();
	verticalSplitPanel.setSplitPosition(15, UNITS_PERCENTAGE);
	verticalSplitPanel.setLocked(true);
	verticalSplitPanel.setWidth(90, UNITS_PERCENTAGE);
	verticalSplitPanel.setHeight(100, UNITS_PERCENTAGE);
	horizontalSplitPanelRight = new HorizontalSplitPanel();
	horizontalSplitPanelRight.setSplitPosition(30, UNITS_PERCENTAGE);
	horizontalSplitPanelRight.setLocked(true);
	horizontalSplitPanelRight.addStyleName("horizontalBottom");
	horizontalSplitPanelRight.setHeight(90, UNITS_PERCENTAGE);
	horizontalSplitPanelRight.setWidth(100, UNITS_PERCENTAGE);
	
	leftL = new Label("links");
	rightTopL = new Label("rechts Oben");
	rightBottomL = new Label("rechts Unten");
	leftBottomL = new Label("links Unten");
	
	rightLayout.addComponent(verticalSplitPanel);
//	topRightLayout.addComponent(rightTopL);
//	bottomRightLayout.addComponent(rightBottomL);
//	bottomLeftLayout.addComponent(leftBottomL);
	bottomLayout.addComponent(horizontalSplitPanelRight);
	
	horizontalSplitPanel.addComponent(leftLayout);
	horizontalSplitPanel.addComponent(rightLayout);
	
	verticalSplitPanel.addComponent(topRightLayout);
	verticalSplitPanel.addComponent(bottomLayout);
	
	horizontalSplitPanelRight.addComponent(bottomLeftLayout);
	horizontalSplitPanelRight.addComponent(bottomRightLayout);
	
	rightLayout.setComponentAlignment(verticalSplitPanel, Alignment.MIDDLE_CENTER);
	bottomLayout.setComponentAlignment(horizontalSplitPanelRight, Alignment.MIDDLE_CENTER);
	
	mainLayout.addComponent(horizontalSplitPanel);
	
	setContent(mainLayout);
	
	//linke Seite Logo
	leftLogolayout = new VerticalLayout();
	leftLogolayout.setWidth(Sizeable.SIZE_UNDEFINED, 0);
	leftLogolayout.setHeight(100, UNITS_PERCENTAGE);
	
	logo = new Embedded (null, new ThemeResource ("images/Logo_businesshorizon.png"));
	
	leftLogolayout.addComponent(logo);
	leftLogolayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
	
	//linke Seite Infos
	gap = new Label();
	gap.setHeight("10px");
	
	titleIcon  = new Embedded(null, new ThemeResource("./images/icons/newIcons/1418775155_device_board_presentation_content_chart-128.png"));
	titleIcon.setWidth(70, UNITS_PIXELS);
	titleIcon.setHeight(70, UNITS_PIXELS);
	
	seitenLabel = new Label("Schritt 2");
	seitenLabel.setStyleName("seitenLabel");
	seitenLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
	
	splitter = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
	splitter.setWidth(98, UNITS_PERCENTAGE);
	
	descriptionLabel = new Label("Stochastische Methode:");
	descriptionLabel.setStyleName("descriptionLabel");
	descriptionLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
	
	descriptionLabel2 = new Label("Bitte geben Sie die Parameter ein");
	descriptionLabel2.setStyleName("descriptionLabel");
	descriptionLabel2.setWidth(Sizeable.SIZE_UNDEFINED, 0);
	
	descriptionLayout = new VerticalLayout();
	descriptionLayout.addComponent(descriptionLabel);
	descriptionLayout.addComponent(descriptionLabel2);
	descriptionLayout.setComponentAlignment(descriptionLabel, Alignment.TOP_CENTER);
	descriptionLayout.setComponentAlignment(descriptionLabel2, Alignment.MIDDLE_CENTER);	
	descriptionLayout.setWidth(100, UNITS_PERCENTAGE);
	descriptionLayout.setHeight(60, UNITS_PIXELS);
	
	splitter2 = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
	splitter2.setWidth(98, UNITS_PERCENTAGE);
	
	middleGap = new Label ();
	middleGap.setHeight("10px");
	
	menuButtonsLayout = new HorizontalLayout();
	menuButtonsLayout.setWidth(100, UNITS_PERCENTAGE);
	menuButtonsLayout.setHeight(Sizeable.SIZE_UNDEFINED, 0);
	
	homeButtonLayout = new VerticalLayout();
	homeButtonLayout.setSizeFull();
	
	homeButton = new Button();
	homeButton.setHeight(30, UNITS_PIXELS);
	homeButton.setWidth(30, UNITS_PIXELS);
	homeButton.setStyleName("homeButton");
	
	homeButtonLabel = new Label("Startseite");
	homeButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
	homeButtonLabel.setStyleName("topBarButtonLabel");
	
	homeButtonLayout.addComponent(homeButton);
	homeButtonLayout.addComponent(homeButtonLabel);
	homeButtonLayout.setComponentAlignment(homeButton, Alignment.TOP_CENTER);
	homeButtonLayout.setComponentAlignment(homeButtonLabel, Alignment.MIDDLE_CENTER);
	
	menuButtonsLayout.addComponent(homeButtonLayout);

	accountButtonLayout = new VerticalLayout();
	accountButtonLayout.setSizeFull();
	
	accountButton = new Button();
	accountButton.setHeight(30, UNITS_PIXELS);
	accountButton.setWidth(30, UNITS_PIXELS);
	accountButton.setStyleName("accountButton");
	
	accountButtonLabel = new Label("Mein Konto");
	accountButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
	accountButtonLabel.setStyleName("topBarButtonLabel");
	
	accountButtonLayout.addComponent(accountButton);
	accountButtonLayout.addComponent(accountButtonLabel);
	accountButtonLayout.setComponentAlignment(accountButton, Alignment.TOP_CENTER);;
	accountButtonLayout.setComponentAlignment(accountButtonLabel, Alignment.MIDDLE_CENTER);
	
	menuButtonsLayout.addComponent(accountButtonLayout);
	
	bottomGap = new Label();
	bottomGap.setHeight("380px");

	leftContentLayout.addComponent(gap);
	leftContentLayout.addComponent(titleIcon);
	leftContentLayout.addComponent(seitenLabel);
	leftContentLayout.addComponent(splitter);
	leftContentLayout.addComponent(descriptionLayout);
	leftContentLayout.addComponent(splitter2);
	leftContentLayout.addComponent(middleGap);
	leftContentLayout.addComponent(menuButtonsLayout);
	leftContentLayout.addComponent(bottomGap);
	
	leftContentLayout.setComponentAlignment(seitenLabel, Alignment.TOP_CENTER);
	leftContentLayout.setComponentAlignment(titleIcon, Alignment.TOP_CENTER);
	
	leftLayout.addComponent(leftLogolayout);
	leftLayout.addComponent(leftContentLayout);	
	leftLayout.setExpandRatio(leftContentLayout, 1.0f);
	
	
	//Buttonleiste
	
	TopBarButton button = new TopBarButton("saveButton", "Speichern");
	addTopButton(button.getButtonComponent());
	button = new TopBarButton("resetInput", "Daten zurücksetzen");
	addTopButton(button.getButtonComponent());
	button = new TopBarButton("back", "Zurück");
	addTopButton(button.getButtonComponent());
	button=new TopBarButton("abbort", "Abbrechen");
	addTopButton(button.getButtonComponent());
	button.getButton().addListener(new Button.ClickListener () {

		@Override
		public void buttonClick(ClickEvent event) {
			presenter.abbrechen();			
		}
		
	});

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
	bottomLeftLayout.addComponent((Component) leftView);
	//horizontalSplitPanel.setSecondComponent((Component) rightView);
	bottomRightLayout.removeAllComponents();
	bottomRightLayout.addComponent((Component) rightView);
	}
	
	public void addTopButton(Component button){
		topRightLayout.addComponent(button);
	}
	
	public void setTopButton(Component button, int index){
		Component comp = topRightLayout.getComponent(index);
		topRightLayout.removeComponent(comp);
		topRightLayout.addComponent(button, index);
	}
	
	
	}