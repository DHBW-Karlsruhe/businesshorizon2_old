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

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotLoggedInException;
import dhbw.ka.mwi.businesshorizon2.services.persistence.ImportUploadFinishedEvent;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;
import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.buttonsMiddle.ButtonsMiddleViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.description.DescriptionViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.description.ShowDescriptionEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos.InfosViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.infos.ShowInfosEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation.ProjectCreationViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation.ShowProjectCreationButtonsEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation.ShowProjectEditButtonsEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation.StartCalculationButtonViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ProjectDetailsViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ShowProjectDetailsEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectRemoveEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.LogoutEvent;
import dhbw.ka.mwi.businesshorizon2.ui.login.ShowLogInScreenEvent;
import dhbw.ka.mwi.businesshorizon2.ui.methodscreen.MethodScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.ParameterScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.parameterScreen.input.ParameterInputViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.periodscreen.PeriodScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.ResultScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.resultscreen.ShowOutputViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.scenarioscreen.ScenarioScreenViewInterface;

/**
 * Dieser Presenter stellt die Eingangseite der Applikation darf. Er ist dafuer
 * verantwortlich, die jeweils anzuzeigenden Fenster korrekt in der View zu
 * setzen. Somit ist es notwenig, dass er fuer jedes Anzuzeigende (Teil-)Fenster
 * einen entsprechenden EventHandler fuer den jeweiligen Show*Event registriert.
 *
 * @author Christian Scherer, Marcel Rosenberger, Marco Glaser
 *
 */

public class InitialScreenPresenter extends Presenter<InitialScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private User user;

	@Autowired
	private UserProxy userProxy;

	private static final Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	private Project project;
	
	@Autowired
	private ProjectProxy projectProxy;

	@Autowired
	private ProjectListViewInterface projectListView;

	@Autowired
	private ProjectDetailsViewInterface projectDetailsView;

	@Autowired
	private ProjectCreationViewInterface projectCreationView;

	@Autowired
	private StartCalculationButtonViewInterface startCalculationButtonView;

	@Autowired
	private ButtonsMiddleViewInterface buttonsMiddleView;

	@Autowired
	private MethodScreenViewInterface methodScreenView;
	
	@Autowired
	private PeriodScreenViewInterface periodScreenView;
	
	@Autowired
	private ScenarioScreenViewInterface scenarioScreenView;
	
	@Autowired
	private ResultScreenViewInterface resultScreenView;

	@Autowired
	private InfosViewInterface infosView;

	@Autowired
	private AuthenticationServiceInterface authenticationService;

	@Autowired
	private PersistenceServiceInterface persistenceService;

	@Autowired
	private ParameterInputViewInterface parameterInputView;
	
	@Autowired
	private DescriptionViewInterface descriptionView;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 *
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
	}

	/**
	 * Dieser Event wird zu Beginn von der BHApplication (nach dem Einloggen)
	 * abgesetzt. Dabei wird auf der linken Seite die Projekt-Liste und auf
	 * der rechten Seite der Detailscreen für die Projekte eingefügt
	 *
	 * @author Christian Scherer, Marco Glaser
	 * 
	 * @param event
	 * :das ShowInitialScreenViewEvent, welches das angemeldete
	 * User-Objekt beinhaltet
	 * 
	 */
	@EventHandler
	public void onShowInitialScreen(ShowInitialScreenViewEvent event) {
		logger.debug("ShowInitialScreenViewEvent empfangen");
		user = userProxy.getSelectedUser();
		//getView().showUserData(user.getFullName());
		getView().showView(projectListView, projectDetailsView);
		getView().clearProgressBar();
		projectDetailsView.clearProjectDetails();
		logger.debug("Views mit Projekt und Infoview geladen");
		eventBus.fireEvent(new ShowProjectListEvent(user));
		logger.debug("ShowProjectListEvent gefeuert");
		//eventBus.fireEvent(new ShowInfosEvent());
		logger.debug("ShowInfosEvent gefeuert");

	}

	//wird durch den Click-Listener des Logout-Button in der InitinalScreen-View aufgerufen
	public void doLogout() {
		//speichert die Projekte in der externen Datei
		persistenceService.saveProjects();
		logger.debug("Projekte gespeichert");
		try {
			//ruft doLogout im Authentication Service auf und entfernt User aus allen eingeloggten Usern
			authenticationService.doLogout(userProxy.getSelectedUser());
			logger.debug("LogoutEvent gefeuert");
			eventBus.fireEvent(new LogoutEvent());
		} catch (UserNotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	/**
	 * Diese Methode löscht das übergeben Projekt und aktualisiert die View.
	 *
	 * @param project
	 * : Das zu entfernende Projekt
	 * @author Marco Glaser
	 */
	public void removeProject(Project project) {
		persistenceService.removeProject(this.user, project);
		logger.debug("Projekt aus User entfernt");
		projectListView.setProjects(user.getProjects());
		//		eventBus.fireEvent(new ProjectRemoveEvent(project));
		getView().showView(projectListView, projectDetailsView);
		eventBus.fireEvent(new ShowProjectDetailsEvent());
		logger.debug("ProjekteRemove Event gefeuert");

	}

	/**
	 * Diese Methode setzt die View zum Erstellen eines neuen Projektes
	 * in den rechten Bereich und feuert ein Event um die Buttons in der Buttonleiste
	 * anzupassen.
	 *
	 * @author Marco Glaser
	 */
	public void showProjectCreationScreen(){
		getView().showView(startCalculationButtonView, projectCreationView);
		projectCreationView.setInitialScreen(this.getView());
		eventBus.fireEvent(new ShowProjectCreationButtonsEvent(userProxy.getSelectedUser()));
	}

	/**
	 * Diese Methode setzt die View zum Bearbeiten eines neuen Projektes
	 * in den rechten Bereich und feuert ein Event um die Buttons in der Buttonleiste
	 * anzupassen.
	 *
	 * @author Marco Glaser
	 */
	public void showProjectEditScreen(){
		getView().showView(startCalculationButtonView, projectCreationView);
		projectCreationView.setInitialScreen(this.getView());
		eventBus.fireEvent(new ShowProjectEditButtonsEvent(userProxy.getSelectedUser()));
	}

	/**
	 * Diese Methode behandelt das Event die initialen Buttons in der Leiste wiederherzustellen
	 * und ruft die entsprechende Methode in der View auf. Außerdem wird auch die
	 * Seitenbeschreibung auf den initialen Stand zurückgesetzt.
	 * 
	 * @param event
	 * : ShowInitialTopButtonsEvent
	 *
	 * @author Marco Glaser
	 */
	@EventHandler
	public void onShowInitialTopButtons(ShowInitialTopButtonsEvent event){
		getView().setInitialTopButtons();
		getView().setInitialPageDescription();
	}
	/**
	 * Diese Methode setzt die Description auf die rechte Seite.
	 * 
	 * @author Tobias Lindner
	 * @param event
	 * 		ShowDescriptionEvent
	 */
	@EventHandler
	public void onShowDescription (ShowDescriptionEvent event) {
		getView().showProjectCreationScreen(descriptionView);
		getView().deleteTopButton(1);
	}
	

	
	@EventHandler
	public void onShowMethodSelectionView(ShowProcessStepEvent event){
		project = projectProxy.getSelectedProject();
		persistenceService.saveProjects();
		
		switch (event.getScreen()) {
		case METHODSELECTION:
			buttonsMiddleView.setInitialButtons();
			methodScreenView.setRadioValues();
			getView().showView(buttonsMiddleView, methodScreenView);
			getView().setPageDescription("./images/icons/newIcons/1418831828_editor_memo_note_pad-128.png", "Schritt 1", new String[] {"Wählen Sie die Methoden", "zur Berechnung"});
			getView().setProgress("./images/progressBar/progress_1.png");
			setScreen1Buttons();
			break;

		case PARAMETER:
			buttonsMiddleView.setGoToStep(3);
			if (projectProxy.getSelectedProject().getProjectInputType().isStochastic()) {
				buttonsMiddleView.setStochasticParameter();
				getView().setPageDescription("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Schritt 2", new String[] {"Stochastische Methode", "Bitte geben Sie die Parameter ein"});
				logger.debug("Stochastische Buttons gesetzt");
			}
			
			else {
				buttonsMiddleView.setDeterministicParameter();
				getView().setPageDescription("./images/icons/newIcons/1418831298_common_calendar_month-128.png", "Schritt 2", new String[] {"Deterministische Methode", "Bitte geben Sie die Parameter ein"});
				logger.debug ("Deterministische Buttons gesetzt");
			}
			getView().showView(buttonsMiddleView, parameterInputView);
			getView().setProgress("./images/progressBar/progress_2.png");
			setScreen2Buttons();
			break;

		case PERIODS:
			buttonsMiddleView.setGoToStep(4);
			getView().showView(buttonsMiddleView, periodScreenView);
			periodScreenView.setMethod();
			String text = periodScreenView.getPageDescription();
			getView().setPageDescription("./images/icons/newIcons/1418831563_circle_backup_time_history_recent_time-machine_-128.png", "Schritt 3", new String[] {text, "Bitte geben Sie die Parameter ein"});
			getView().setProgress("./images/progressBar/progress_3.png");
			setScreen3Buttons();

			break;

		case SCENARIOS:
			buttonsMiddleView.setGoToStep(5);
			getView().showView(buttonsMiddleView, scenarioScreenView);
			getView().setPageDescription("./images/icons/newIcons/1418831239_editor_attachment_paper_clip_2-128.png", "Schritt 4", new String[] {"Bitte geben Sie Szenarien an", "falls erwünscht"});
			getView().setProgress("./images/progressBar/progress_4.png");
			setScreen4Buttons();
			break;

		case RESULT:
			buttonsMiddleView.hideStepButton();
			if (project.getScenarios().size()>1) {
				getView().showExtendedView(resultScreenView);
			}
			else {
				getView().showView(buttonsMiddleView, resultScreenView);				
			}
			getView().setPageDescription("./images/icons/newIcons/1418775155_device_board_presentation_content_chart-128.png", "Schritt 5", "Ergebnisausgabe");
			getView().setProgress("./images/progressBar/progress_5.png");
			setScreen5Buttons();
			eventBus.fireEvent(new ShowOutputViewEvent());
			break;

		default:
			break;
		}
	}
	
	private void setScreen5Buttons(){
		getView().setTopButton(new TopBarButton("backButton", "Zurück"), 2, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				eventBus.fireEvent(new ShowProcessStepEvent(screen.SCENARIOS));

			}

		});
	}
	
	private void setScreen4Buttons(){
		getView().setTopButton(new TopBarButton("backButton", "Zurück"), 2, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				eventBus.fireEvent(new ShowProcessStepEvent(screen.PERIODS));

			}

		});
	}
	
	private void setScreen3Buttons(){
		getView().setTopButton(new TopBarButton("backButton", "Zurück"), 2, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				eventBus.fireEvent(new ShowProcessStepEvent(screen.PARAMETER));

			}

		});
	}

	private void setScreen2Buttons() {
		setScreen1Buttons();
		getView().setTopButton(new TopBarButton("backButton", "Zurück"), 2, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				eventBus.fireEvent(new ShowProcessStepEvent(screen.METHODSELECTION));

			}

		});
		getView().setTopButton(new TopBarButton("cancelButton", "Abbrechen"), 3, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(event.getButton().getWindow(), "Warnung", "Beim Abbruch gehen Ihre Eingaben verloren!",
						"Okay", "Abbrechen", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							eventBus.fireEvent(new ShowInitialScreenViewEvent(user));
							eventBus.fireEvent(new ShowInitialTopButtonsEvent());
						} else {

						}
					}
				});

			}

		});
		
	}

	private void setScreen1Buttons() {
		getView().setTopButton(new TopBarButton("saveProjectButton", "Speichern"), 0, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				persistenceService.saveProjects();

			}

		});
		getView().setTopButton(new TopBarButton("deleteProjectButton", "Daten zurücksetzen").setButtonWidth(25), 1, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {


			}

		});
		getView().setTopButton(new TopBarButton("cancelButton", "Abbrechen"), 2, new ClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(event.getButton().getWindow(), "Warnung", "Beim Abbruch gehen Ihre Eingaben verloren!",
						"Okay", "Abbrechen", new ConfirmDialog.Listener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (dialog.isConfirmed()) {
							eventBus.fireEvent(new ShowInitialScreenViewEvent(user));
							eventBus.fireEvent(new ShowInitialTopButtonsEvent());
						} else {

						}
					}
				});

			}

		});

		getView().clearUnusedButtons(3);

	}

	public void showInitialScreen() {

		eventBus.fireEvent(new ShowInitialScreenViewEvent(user));
		eventBus.fireEvent(new ShowInitialTopButtonsEvent());

	}
	
	/**
	 * Diese Methode per Event nach dem Upload der zu importierenden Projektdatei aufgerufen. Sie löst das Auslesen der in der Datei enthaltenen Projektdaten und das Importieren aus.
	 * Nach dem Import wird das ShowProjectListEvent geworfen, um die angezeigte Liste zu aktualisieren.
	 * 
	 * @param event
	 * 		ImportUploadFinishedEvent, dass als Parameter den Dateinamen der hochgeladenen Datei enthält.
	 * 
	 * @author Tobias Lindner
	 */
	@EventHandler
	public void onUploadFinishedImport (ImportUploadFinishedEvent event) {
		String notImported = null; //in diesen String wird der Rückgabewert, der String mit den Projektnamen, die nicht importiert werden konnten gespeichert
		logger.debug("ImportUploadFinishedEvent empfangen");
		
		notImported = persistenceService.importAllProjects(user, event.getfileName());
		logger.debug ("PersistenceService Import-Funktion im Presenter aufgerufen");
		
//		//Ausgabe der Fehlermeldung, falls nicht alle Projekte importiert werden konnten
//		if (notImported != null) {
//			getView().showErrorMessage(notImported);
//		}
		
		//Aktualisieren der Antwort
		eventBus.fireEvent(new ShowProjectListEvent (user));
		logger.debug ("ShowProjectListEvent geworfen");
	}
	
	/**
	 * Aufruf aus dem ClickListener der Impl. Es wird die Erstellung der ExportDatei angestoßen.
	 * Das erzeugte File wird an die View zum Download zurückgeliefert.
	 * 
	 * @return exportFile
	 * 			Das erzeugte ExportFile wird zum Download durch den Client an die View zurückgeliefert.
	 * 
	 * @author Tobias Lindner
	 */
	public File exportProjects () {
		File exportFile;
		
		exportFile = new File (persistenceService.exportUserProjects(user));
		logger.debug("Presenter: Export-Datei erstellt");
		
		return exportFile;
	}

}


