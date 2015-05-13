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

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.persistence.Downloader;
import dhbw.ka.mwi.businesshorizon2.services.persistence.UploadReceiver;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;
import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.*;

import com.mvplite.event.EventBus;
import com.mvplite.view.View;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der InitalScreenView (dem
 * Eingangs-Fenster). Die View dient als Container für den gesamten Inhalt der Anwendung.
 * Die einzelnen Views werden in das Layout dieser View eingefügt.
 *
 * @author Christian Scherer, Marcel Rosenberger, Mirko Göpfrich, Marco Glaser
 *
 */
public class InitialScreenViewImpl extends Window implements InitialScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenViewImpl.class");

	@Autowired
	private InitialScreenPresenter presenter;

	@Autowired
	private UserProxy userProxy;
	
	@Autowired
	private EventBus eventBus;

	@Autowired
	private ProjectProxy projectProxy;

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
	private VerticalLayout descriptionLayout;
	private Embedded homeIcon;
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

	private TopBarButton editProjectButton;

	private TopBarButton deleteProjectButton;

	private TopBarButton addProjectButton;

	private ClickListener addProjectButtonListener;

	private ClickListener deleteProjectButtonListener;

	private ClickListener editProjectButtonListener;

	private Label bottomGap;

	private HorizontalLayout faqLayout;

	private VerticalLayout faqManualLayout;

	private HorizontalLayout manualLayout;

	private Label faqLabel;

	private Label manualLabel;

	private Button faqButton;

	private Button manualButton;

	private VerticalLayout faqLayoutVertical;

	private VerticalLayout manualLayoutVertical;

	private Label faqGap;

	private Label manualGap;

	private Label middleGap;

	private VerticalLayout importButton;

	private TopBarButton exportButton;

	private ClickListener exportButtonListener;

	private VerticalLayout logoutButtonLayout;

	private Button logoutButton;

	private Label logoutButtonLabel;

	private Embedded progressBar;

	private Label progressBarGap;

	private Label splitterVertical;



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
	 * Zusätzlich wird hier noch das Menü erzeugt und die Buttons, um ein Projekt zu bearbeiten,
	 * ein neues anzulegen oder ein bestehendes zu löschen.
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
		bottomRightLayout.setHeight(90, UNITS_PERCENTAGE);
		bottomRightLayout.setWidth(100, UNITS_PERCENTAGE);
		bottomRightLayout.addStyleName("horizontalBottom");
		bottomLayout = new VerticalLayout();
		gap = new Label();
		leftContainerSpacing = new VerticalLayout();
		progressBar = new Embedded(null);
		progressBarGap = new Label();
		splitter = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
		splitter2 = new Label("<hr style='border:none;background-color:black;height:2px'>", Label.CONTENT_XHTML);
		menuButtonsLayout = new HorizontalLayout();
		homeButtonLayout = new VerticalLayout();
		accountButtonLayout = new VerticalLayout();
		logoutButtonLayout = new VerticalLayout();
		homeButton = new Button();
		accountButton = new Button();
		logoutButton = new Button();
		homeButtonLabel = new Label("Startseite");
		accountButtonLabel = new Label("Mein Konto");
		logoutButtonLabel = new Label("Ausloggen");
		descriptionLayout = new VerticalLayout();
		bottomGap = new Label();
		faqManualLayout = new VerticalLayout();
		faqLayout = new HorizontalLayout();
		faqLayoutVertical = new VerticalLayout();
		manualLayout = new HorizontalLayout();
		manualLayoutVertical = new VerticalLayout();
		faqLabel = new Label("FAQ");
		manualLabel = new Label("User Manual");
		faqButton = new Button();
		manualButton = new Button();
		faqGap = new Label();
		manualGap = new Label();
		homeIcon  = new Embedded(null, new ThemeResource("./images/icons/newIcons/1418766062_house_home-128.png"));
		middleGap = new Label();

		mainLayout.setSizeFull();
		mainLayout.setStyleName("mainLayout");
		leftLayout.setSizeFull();
		leftLayout.setStyleName("leftContainer");
		rightLayout.setSizeFull();
		rightLayout.setStyleName("rightContainer");
		bottomLayout.setSizeFull();
		bottomLeftLayout.setSizeFull();
		//		bottomRightLayout.setWidth(90, UNITS_PERCENTAGE);
		//		bottomRightLayout.setStyleName("projectDetailsLayout");
		topRightLayout.setSizeFull();
		leftContentLayout.setSizeFull();
		progressBar.setSizeUndefined();
		progressBar.setVisible(false);
		//	leftContentLayout.setHeight(Sizeable.SIZE_UNDEFINED, 0);

		leftLogoLayout = new VerticalLayout();
		logo = new Embedded(null, new ThemeResource("images/logo_businesshorizon_vertical.png"));

		gap.setHeight("10px");
		bottomGap.setHeight("40px");
		faqGap.setWidth("50px");
		manualGap.setWidth("50px");
		middleGap.setHeight("10px");
		progressBarGap.setHeight("15px");
		leftContainerSpacing.setSizeFull();

		homeIcon.setWidth(70, UNITS_PIXELS);
		homeIcon.setHeight(70, UNITS_PIXELS);

		seitenLabel = new Label("Startseite");
		seitenLabel.setStyleName("seitenLabel");
		seitenLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		descriptionLabel = new Label("Übersicht über alle Projekte");
		descriptionLabel.setStyleName("descriptionLabel");
		descriptionLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		descriptionLayout.setWidth(100, UNITS_PERCENTAGE);
		descriptionLayout.setHeight(80, UNITS_PIXELS);
		splitter.setWidth(98, UNITS_PERCENTAGE);
		splitter2.setWidth(98, UNITS_PERCENTAGE);
		menuButtonsLayout.setWidth(100, UNITS_PERCENTAGE);
		menuButtonsLayout.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		homeButtonLayout.setSizeFull();
		accountButtonLayout.setSizeFull();
		logoutButtonLayout.setSizeFull();
		homeButton.setHeight(30, UNITS_PIXELS);
		homeButton.setWidth(30, UNITS_PIXELS);
		homeButton.setStyleName("homeButton");
		accountButton.setHeight(30, UNITS_PIXELS);
		accountButton.setWidth(30, UNITS_PIXELS);
		accountButton.setStyleName("accountButton");
		logoutButton.setHeight(30, UNITS_PIXELS);
		logoutButton.setWidth(30, UNITS_PIXELS);
		logoutButton.setStyleName("logoutButton");
		homeButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		homeButtonLabel.setStyleName("topBarButtonLabel");
		accountButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		accountButtonLabel.setStyleName("topBarButtonLabel");
		logoutButtonLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		logoutButtonLabel.setStyleName("topBarButtonLabel");
		faqManualLayout.setWidth(85, UNITS_PERCENTAGE);
		faqManualLayout.setHeight(SIZE_UNDEFINED, 0);
		faqManualLayout.setStyleName("faqManualLayout");
		manualLayout.setWidth(100, UNITS_PERCENTAGE);
		manualLayout.setHeight(50, UNITS_PIXELS);
		manualLayoutVertical.setSizeFull();
		faqLayout.setWidth(100, UNITS_PERCENTAGE);
		faqLayout.setHeight(50, UNITS_PIXELS);
		faqLayoutVertical.setSizeFull();
		faqButton.setWidth(30, UNITS_PIXELS);
		faqButton.setHeight(30, UNITS_PIXELS);
		faqButton.setStyleName("faqButton");
		manualButton.setWidth(30, UNITS_PIXELS);
		manualButton.setHeight(30, UNITS_PIXELS);
		manualButton.setStyleName("manualButton");
		faqLabel.setWidth(SIZE_UNDEFINED, 0);
		faqLabel.setStyleName("faqLabel");
		manualLabel.setWidth(SIZE_UNDEFINED, 0);
		manualLabel.setStyleName("faqLabel");

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

		descriptionLayout.addComponent(descriptionLabel);
		leftLogoLayout.addComponent(logo);
		leftContentLayout.addComponent(gap);
		leftContentLayout.addComponent(homeIcon);
		leftContentLayout.addComponent(seitenLabel);
		leftContentLayout.addComponent(splitter);
		leftContentLayout.addComponent(descriptionLayout);
		leftContentLayout.addComponent(splitter2);
		leftContentLayout.addComponent(middleGap);
		leftContentLayout.addComponent(menuButtonsLayout);
		leftContentLayout.addComponent(progressBarGap);
		leftContentLayout.addComponent(progressBar);
		leftContentLayout.addComponent(leftContainerSpacing);
		leftContentLayout.addComponent(faqManualLayout);
		leftContentLayout.addComponent(bottomGap);
		leftContentLayout.setExpandRatio(leftContainerSpacing, 1.0f);
		leftLayout.addComponent(leftLogoLayout);
		leftLayout.addComponent(leftContentLayout);
		leftLayout.setExpandRatio(leftContentLayout, 1.0f);
		descriptionLayout.setComponentAlignment(descriptionLabel, Alignment.MIDDLE_CENTER);
		leftLogoLayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		leftContentLayout.setComponentAlignment(homeIcon, Alignment.TOP_CENTER);
		leftContentLayout.setComponentAlignment(seitenLabel, Alignment.TOP_CENTER);
		leftContentLayout.setComponentAlignment(faqManualLayout, Alignment.BOTTOM_CENTER);
		leftContentLayout.setComponentAlignment(progressBar, Alignment.MIDDLE_CENTER);
		//		leftContentLayout.setComponentAlignment(descriptionLabel, Alignment.TOP_CENTER);
		menuButtonsLayout.addComponent(homeButtonLayout);
		menuButtonsLayout.addComponent(accountButtonLayout);
		menuButtonsLayout.addComponent(logoutButtonLayout);
		homeButtonLayout.addComponent(homeButton);
		homeButtonLayout.addComponent(homeButtonLabel);
		accountButtonLayout.addComponent(accountButton);
		accountButtonLayout.addComponent(accountButtonLabel);
		logoutButtonLayout.addComponent(logoutButton);
		logoutButtonLayout.addComponent(logoutButtonLabel);
		faqManualLayout.addComponent(manualLayout);
		faqManualLayout.addComponent(faqLayout);
		manualLayout.addComponent(manualLayoutVertical);
		manualLayout.addComponent(manualButton);
		manualLayout.addComponent(manualGap);
		faqLayout.addComponent(faqLayoutVertical);
		faqLayout.addComponent(faqButton);
		faqLayout.addComponent(faqGap);
		manualLayoutVertical.addComponent(manualLabel);
		faqLayoutVertical.addComponent(faqLabel);
		faqLayout.setExpandRatio(faqLayoutVertical, 1.0f);
		manualLayout.setExpandRatio(manualLayoutVertical, 1.0f);

		homeButtonLayout.setComponentAlignment(homeButton, Alignment.TOP_CENTER);
		homeButtonLayout.setComponentAlignment(homeButtonLabel, Alignment.MIDDLE_CENTER);
		accountButtonLayout.setComponentAlignment(accountButton, Alignment.TOP_CENTER);
		accountButtonLayout.setComponentAlignment(accountButtonLabel, Alignment.MIDDLE_CENTER);
		logoutButtonLayout.setComponentAlignment(logoutButton, Alignment.TOP_CENTER);
		logoutButtonLayout.setComponentAlignment(logoutButtonLabel, Alignment.MIDDLE_CENTER);
		manualLayoutVertical.setComponentAlignment(manualLabel, Alignment.MIDDLE_RIGHT);
		manualLayout.setComponentAlignment(manualButton, Alignment.MIDDLE_CENTER);
		faqLayoutVertical.setComponentAlignment(faqLabel, Alignment.MIDDLE_RIGHT);
		faqLayout.setComponentAlignment(faqButton, Alignment.MIDDLE_CENTER);

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

		homeButton.addListener(new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(event.getButton().getWindow(), "Warnung", "Beim Abbruch gehen Ihre Eingaben verloren! Möchten Sie zur Startseite zurückkehren?",
						"Okay", "Abbrechen", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							presenter.showInitialScreen();
						} else {

						}
					}
				});
				
			}
			
		});
		
		logoutButton.addListener(new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(event.getButton().getWindow(), "Warnung", "Möchten Sie sich wirklich ausloggen?",
						"Okay", "Abbrechen", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							presenter.doLogout();
						} else {

						}
					}
				});
			}
			
		});
		
		setContent(mainLayout);
		

		addProjectButton = new TopBarButton("addProjectButton", "Neues Projekt");
		addProjectButton.addLabel("hinzufügen");
		addProjectButtonListener = new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showProjectCreationScreen();
				String[] desc = new String[2];
				desc[0] = "Geben Sie hier den Namen und";
				desc[1] = "eine Beschreibung ein";
				setPageDescription("./images/icons/newIcons/1418831401_circle_add_plus-128.png", "Neues Projekt anlegen", desc);
			}

		};
		addTopButton(addProjectButton, addProjectButtonListener);
		
		editProjectButton = new TopBarButton("editProjectButton", "Projekt bearbeiten");
		editProjectButtonListener = new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.showProjectEditScreen();
				String[] desc = new String[2];
				desc[0] = "Ändern Sie hier Name oder";
				desc[1] = "Beschreibung des Projekts";
				setPageDescription("./images/icons/newIcons/1418765965_editor_pencil_pen_edit_write-128.png", "Projekt bearbeiten", desc);
			}

		};
		addTopButton(editProjectButton, editProjectButtonListener);
		deleteProjectButton = new TopBarButton("deleteProjectButton", "Projekt löschen");
		deleteProjectButton.setButtonWidth(25);
		deleteProjectButtonListener = new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				final Project project = projectProxy.getSelectedProject();

				logger.debug("Projekt-loeschen Button aus dem Hauptfenster aufgerufen.");

				ConfirmDialog.show(getWindow(), project.getName()
						+ " löschen?", "Wollen sie das Projekt wirklich löschen?",
						"Ja", "Nein", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							presenter.removeProject(project);
						} else {

						}
					}
				});

			}

		};
		addTopButton(deleteProjectButton, deleteProjectButtonListener);
		
		createImportButton();
		topRightLayout.addComponent(importButton);
		topRightLayout.setComponentAlignment(importButton, Alignment.MIDDLE_CENTER);
		
		exportButton = new TopBarButton("exportButton", "Projekte");
		exportButton.addLabel("exportieren");
		exportButtonListener = new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				File exportFile = presenter.exportProjects();
				event.getButton().getWindow().open(new Downloader(exportFile, getApplication()));
			}
			
		};
		addTopButton(exportButton, exportButtonListener);

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
	 * Diese Methode setzt die zweit übergebenen Views in den unteren rechten Bereich
	 * des Layouts. Also sozusagen den aktuellen Content der Anwendung.
	 * Der rechte Bereich dieses SplitPanels dient als Detailbereich zum linken Bereich.
	 * Beispielsweise beim Aufrufen der Anwendung links die Projektliste und rechts
	 * die Details zum jeweils ausgewählten Projekt.
	 *
	 * @param leftView
	 * : linker Bereich
	 * @param rightView
	 * : rechter Bereich (Details zum linken Bereich)
	 * @author Christian Scherer, Marco Glaser
	 */
	@Override
	public void showView(View leftView, View rightView) {
		if (bottomLayout.getComponentIndex(horizontalSplitPanelRight)==-1) {
			bottomLayout.replaceComponent(bottomRightLayout, horizontalSplitPanelRight);
			bottomLayout.setComponentAlignment(horizontalSplitPanelRight, Alignment.MIDDLE_CENTER);
		}
		horizontalSplitPanelRight.setFirstComponent((Component) leftView);
		horizontalSplitPanelRight.setSecondComponent((Component) rightView);
	}
	
	/**
	 * @author Tobias Lindner
	 */
	public void showExtendedView (View exView) {
		bottomLayout.replaceComponent(horizontalSplitPanelRight, bottomRightLayout);
		bottomLayout.setComponentAlignment(bottomRightLayout, Alignment.MIDDLE_CENTER);
		bottomRightLayout.addComponent((Component)exView);
	}

	/**
	 * Diese Methode setzt die übergebene View in den rechten Bereich des
	 * horizontalen SplitPanels.
	 * Kann aufgerufen werden um nur den rechten Bereich zu ändern aber nicht
	 * den linken Bereich.
	 *
	 * @param view
	 * : rechter Bereich (Details zum linken Bereich)
	 * @author Marco Glaser
	 */
	public void showProjectCreationScreen(View view){
		horizontalSplitPanelRight.setSecondComponent((Component) view);
	}

	/**
	 * Diese Methode fügt einen Button zur Button-Leiste (topRightLayout) zusammen 
	 * mit einem ClickListener hinzu.
	 *
	 * @param button
	 * : Der Button
	 * @param listener
	 * : Der ClickListener
	 * @author Marco Glaser
	 */
	public void addTopButton(TopBarButton button, ClickListener listener){
		if(listener != null){
			button.getButtonComponent().addListener(listener);
		}
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
	 * @param listener
	 * : Der ClickListener
	 * @author Marco Glaser
	 */
	public void setTopButton(TopBarButton button, int index, ClickListener listener){
		if(listener != null){
			button.getButtonComponent().addListener(listener);
		}
		int maxIndex = topRightLayout.getComponentCount() - 1; //1 abziehen wegen dem Spacing rechts
		if(index < maxIndex){
			Component comp = topRightLayout.getComponent(index);
			if(comp != null){
				topRightLayout.replaceComponent(comp, button);
			}
			
		}
		else{
			topRightLayout.addComponent(button, index);;
		}
		topRightLayout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Diese Methode löscht einen Button aus der Buttonleiste.
	 * Es wird der Button gelöscht, der sich an der Stelle index befindet.
	 *
	 * @param index
	 * : Die Stelle von dem Button, der gelöscht werden soll
	 * @author Marco Glaser
	 */
	public void deleteTopButton(int index){
		Component comp = topRightLayout.getComponent(index);
		if(comp != null){
			topRightLayout.removeComponent(comp);
		}
	}
	
	public void createImportButton(){
		importButton = new VerticalLayout();
		importButton.setWidth(150, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		importButton.setHeight(80, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		importButton.setStyleName("topBarButtonContainer");
		UploadReceiver receiver = new UploadReceiver(eventBus);
		Upload upload = new Upload(null, receiver);
		upload.setButtonCaption("");
		upload.setImmediate(true);
		upload.addListener(receiver);
		upload.setStyleName("importButton");
		upload.setWidth(30, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		upload.setHeight(30, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		Label gap = new Label();
		gap.setHeight("5px");
		Label label = new Label("Projekte");
		label.setStyleName("topBarButtonLabel");
		label.setSizeUndefined();
		Label label2 = new Label("importieren");
		label2.setStyleName("topBarButtonLabel");
		label2.setSizeUndefined();
		VerticalLayout labelLayout = new VerticalLayout();
		labelLayout.setHeight(45, com.vaadin.terminal.Sizeable.UNITS_PIXELS);
		labelLayout.setWidth(100, UNITS_PERCENTAGE);
		importButton.addComponent(upload);
		labelLayout.addComponent(label);
		labelLayout.addComponent(label2);
		importButton.addComponent(gap);
		importButton.addComponent(labelLayout);
		importButton.setComponentAlignment(upload, Alignment.TOP_CENTER);
		labelLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		labelLayout.setComponentAlignment(label2, Alignment.MIDDLE_CENTER);
	}
	
	public void setImportButton(){
		int index = 3;
		int maxIndex = topRightLayout.getComponentCount() - 1; //1 abziehen wegen dem Spacing rechts
		if(index < maxIndex){
			Component comp = topRightLayout.getComponent(index);
			if(comp != null){

			}
			topRightLayout.replaceComponent(comp, importButton);
		}
		else{
			topRightLayout.addComponent(importButton, index);;
		}
		topRightLayout.setComponentAlignment(importButton, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Diese Methode setzt die ursprünglichen 3 Buttons Projekt-hinzufügen,
	 * Projekt-bearbeiten und Projekt-löschen in die Button leiste.
	 *
	 * @author Marco Glaser
	 */
	public void setInitialTopButtons(){
		setTopButton(addProjectButton, 0, addProjectButtonListener);
		setTopButton(editProjectButton, 1, editProjectButtonListener);
		setTopButton(deleteProjectButton, 2, deleteProjectButtonListener);
		setImportButton();
		setTopButton(exportButton, 4, exportButtonListener);
		clearUnusedButtons(5);
	}
	
	public void clearUnusedButtons(int count){
		int componentCount = topRightLayout.getComponentCount();
		if(componentCount > (count + 1)){
			for(int i = count; i < (componentCount - 1); i++){
				topRightLayout.removeComponent(topRightLayout.getComponent(count));
				logger.debug("Komponente an Stelle "+i+" gelöscht");
			}
		}
	}

	/**
	 * Diese Methode ändert den oberen Teil im Menü: Das Icon, von der Seite die aktuell
	 * rechts als Content angezeigt wird, sowie die zugehörige Beschreibung dazu.
	 *
	 * @param source
	 * : Quelle des Icons als String
	 * @param page
	 * : Name der Seite
	 * @param description
	 * : Beschreibung der Seite
	 * 
	 * @author Marco Glaser
	 */
	public void setPageDescription(String source, String page, String description){
		int labelCount = descriptionLayout.getComponentCount();
		homeIcon.setSource(new ThemeResource(source));
		seitenLabel.setValue(page);
		descriptionLabel.setValue(description);
		descriptionLayout.setComponentAlignment(descriptionLabel, Alignment.MIDDLE_CENTER);
		for(int i = 1; i < labelCount; i++){
			descriptionLayout.removeComponent(descriptionLayout.getComponent(i));
		}
	}

	public void setPageDescription(String source, String page, String[] description){
		int labelCount = descriptionLayout.getComponentCount();
		Label oldLabel;
		int i;
		homeIcon.setSource(new ThemeResource(source));
		seitenLabel.setValue(page);
		for(i = 0; i < labelCount && i < description.length; i++){
			oldLabel = (Label) descriptionLayout.getComponent(i);
			oldLabel.setValue(description[i]);
			if(i == 0){
				descriptionLayout.setComponentAlignment(oldLabel, Alignment.BOTTOM_CENTER);
			}
			else{
				descriptionLayout.setComponentAlignment(oldLabel, Alignment.TOP_CENTER);
			}
		}
		//		descriptionLabel.setValue(description[0]);
		for(int a = i; a < description.length; a++){
			Label newLabel = new Label(description[a]);
			newLabel.setStyleName("descriptionLabel");
			newLabel.setWidth(Sizeable.SIZE_UNDEFINED, 0);
			descriptionLayout.addComponent(newLabel);
			descriptionLayout.setComponentAlignment(newLabel, Alignment.TOP_CENTER);
		}
		for(int b = i; b < labelCount; b++){
			descriptionLayout.removeComponent(descriptionLayout.getComponent(b));
		}
	}

	/**
	 * Diese Methode setzt den oberen Teil im Menp auf den initalien Stand zurück (Startseite).
	 *
	 * @author Marco Glaser
	 */
	public void setInitialPageDescription(){
		setPageDescription("./images/icons/newIcons/1418766062_house_home-128.png", "Startseite", "Übersicht über alle Projekte");
	}

	public HorizontalLayout getButtonBarLayout(){
		return this.topRightLayout;
	}

	public void setProgress(String resource){
		progressBar.setSource(new ThemeResource(resource));
		progressBar.setVisible(true);
	}
	
	public void clearProgressBar(){
		progressBar.setSource(null);
		progressBar.setVisible(false);
	}
}