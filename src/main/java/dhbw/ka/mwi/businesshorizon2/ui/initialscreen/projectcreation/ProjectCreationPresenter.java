package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.persistence.PersistenceServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.persistence.ProjectAlreadyExistsException;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialTopButtonsEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowProcessStepEvent.screen;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ProjectDetailsViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectAddEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;

/**
 * Dieser Presenter ist für die Darstellung des Projekterstellungs Screens zuständig.
 * Der Projekterstellungs Screen wird auch für das Bearbeiten eines Projektes verwendet.
 *
 * @author Marco Glaser, Tobias Lindner
 */
public class ProjectCreationPresenter extends Presenter<ProjectCreationViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	private Window window;

	private InitialScreenViewInterface initialScreenView;

	@Autowired
	private ProjectListViewInterface projectListView;

	@Autowired
	private PersistenceServiceInterface persistenceService;

	private User theUser;

	private boolean edit;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 *
	 * @author Marco Glaser
	 */
	@PostConstruct
	public void init(){
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");

	}

	/**
	 * Wenn das Event ShowProjectEditButtonsEvent ausgelöst wird, werden in dieser Methode
	 * die zwei Buttons zum Speichern und zum Abbrechen gesetzt.
	 * Außerdem wird eine Methode aufgerufen, die in der View die aktuellen Daten des Projektes
	 * in die Felder setzt.
	 *
	 * @param event
	 * : ShowProjectEditButtonsEvent
	 *
	 * @author Marco Glaser, Tobias Lindner
	 */
	@EventHandler
	public void onShowEditScreen(ShowProjectEditButtonsEvent event){
		final User user = event.getUser();
		theUser = user;
		edit = true;
		getView().setProjectData();
		TopBarButton saveButton = new TopBarButton("saveProjectButton", "Projekt speichern");
		TopBarButton cancelButton = new TopBarButton("cancelButton", "Abbrechen");
		initialScreenView.setTopButton(saveButton, 0, new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				getView().editProject(0);
//				eventBus.fireEvent(new ShowInitialTopButtonsEvent());
			}

		});
		initialScreenView.setTopButton(cancelButton, 1, new ClickListener(){

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
		initialScreenView.clearUnusedButtons(2);
	}

	@EventHandler
	public void onSaveProject(SaveProjectEvent event){
		if(edit == true){
			getView().editProject(1);
		}
		else{
			getView().addProject(1);
		}
	}

	/**
	 * Wenn das Event ShowProjectCreationButtonsEvent ausgelöst wird, werden in dieser Methode
	 * die zwei Buttons zum Speichern und zum Abbrechen gesetzt.
	 * Nur die Listener der Buttons unterscheiden sich zu denen der obigen Methode.
	 *
	 * @param event
	 * : ShowProjectCreationButtonsEvent
	 *
	 * @author Marco Glaser, Tobias Lindner
	 */
	@EventHandler
	public void onShowCreationScreen(ShowProjectCreationButtonsEvent event){
		final User user = event.getUser();
		theUser = user;
		getView().clearProjectData();
		edit = false;
		TopBarButton saveButton = new TopBarButton("saveProjectButton", "Projekt speichern");
		TopBarButton cancelButton = new TopBarButton("cancelButton", "Abbrechen");
		initialScreenView.setTopButton(saveButton, 0, new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				getView().addProject(0);
//				eventBus.fireEvent(new ShowInitialTopButtonsEvent());
			}

		});
		initialScreenView.setTopButton(cancelButton, 1, new ClickListener(){

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
		initialScreenView.clearUnusedButtons(2);
	}

	/**
	 * Wird von der View aufgerufen, um diesem Presenter das initialScreenView Objekt
	 * zu übergeben. Wird in den beiden EventHandlern benötigt, um die Buttons in der View zu ändern.
	 * ?Könnte auch durch Events ersetzt werden?
	 *
	 * @param view
	 * : Die InitialsScreenView der Anwendung
	 *
	 * @author Marco Glaser
	 */
	public void setInitialScreenView(InitialScreenViewInterface view){
		this.initialScreenView = view;
	}

	/**
	 * Diese Methode fügt das neu erstellte Projekt hinzu und feuert das Event,
	 * um die View wieder in den initialen Zustand mit der Projektliste anzuzeigen.
	 *
	 * @param name
	 * : Projektname
	 * @param description
	 * : Projektbeschreibung
	 *
	 * @author Marco Glaser, Tobias Lindner
	 */
	public void addProject(String name, String description, int nextStep) {

		Project project = new Project(name, description);
		project.setLastChanged(new Date());
		project.setCreatedFrom(this.theUser);
		try {
			persistenceService.addProject(this.theUser, project);
			
			projectListView.setProjects(theUser.getProjects());
			
			if (nextStep == 0) {
				eventBus.fireEvent(new ShowInitialTopButtonsEvent());
				eventBus.fireEvent(new ShowInitialScreenViewEvent(this.theUser));
			}
			
			if (nextStep == 1) {
				eventBus.fireEvent(new ShowInitialScreenViewEvent(this.theUser));
				eventBus.fireEvent(new ShowProcessStepEvent(screen.METHODSELECTION));	
			}
			
			
		} catch (ProjectAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Projektname bereits vorhanden.");

		}
		logger.debug("Neues Projekt wurde dem User hinzugefuegt");

		

		logger.debug("Neues Projekt an hinterster Stelle eingefuegt");

		//		eventBus.fireEvent(new ProjectAddEvent(project));
		logger.debug("ShowAddEvent gefeuert");

	}

	/**
	 * Diese Methode führt die Änderungen der Projektbeschreibung und des Namens durch
	 * und feuert das Event, um den initialen Zustand der View anzuzeigen.
	 *
	 * @param project
	 * : Projektobjekt
	 * @param name
	 * : Projektname
	 * @param description
	 * : Projektbeschreibung
	 *
	 * @author Marco Glaser, Tobias Lindner
	 */
	public boolean editProject(Project project, String name, String description, int nextStep) {


		try {
			//Wenn der Name beibehalten wurde, erfolgt keine Überprüfung.
			if (project.getName().equals(name)) {
				logger.debug("nur Projekt-Beschreibung geändert");
			}
			//Andernfalls muss überprüft werben, ob es den Namen bereits gibt.
			else {
				for (Project projektName : theUser.getProjects()) {
					if (projektName.getCreatedFrom().getEmailAdress()
							.equals(theUser.getEmailAdress())) {
						if (projektName.getName().equals(name)) {
							throw new ProjectAlreadyExistsException(
									"Projekt mit dem Namen " + name
									+ " existiert bereits.");
						}
					}
				}
			}
			project.setName(name);
			project.setDescription(description);
			project.setLastChanged(new Date());
			persistenceService.saveProjects();
			projectListView.setProjects(theUser.getProjects());
			
			if (nextStep == 0) {
				eventBus.fireEvent(new ShowInitialTopButtonsEvent());
				eventBus.fireEvent(new ShowInitialScreenViewEvent(this.theUser));
			}
			
			if (nextStep == 1) {
				eventBus.fireEvent(new ShowInitialScreenViewEvent(this.theUser));
				eventBus.fireEvent(new ShowProcessStepEvent(screen.METHODSELECTION));	
			}
			
			logger.debug ("ShowInitialScreenViewEvent geworfen.");
			return true;
		} catch (ProjectAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Projektname bereits vorhanden.");
			return false;
		}

	}

}
