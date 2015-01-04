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
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ProjectDetailsViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectAddEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ProjectListViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;

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

	@PostConstruct
	public void init(){
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
		
	}
	
	@EventHandler
	public void onShowEditScreen(ShowProjectEditButtonsEvent event){
		final User user = event.getUser();
		theUser = user;
		getView().setProjectData();
		TopBarButton saveButton = new TopBarButton("saveProjectButton", "Projekt speichern");
		TopBarButton cancelButton = new TopBarButton("cancelButton", "Abbrechen");
		initialScreenView.setTopButton(saveButton, 0, new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				getView().editProject();
				eventBus.fireEvent(new ShowInitialTopButtonsEvent());
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
		initialScreenView.deleteTopButton(2);
	}
	
	@EventHandler
	public void onShowCreationScreen(ShowProjectCreationButtonsEvent event){
		final User user = event.getUser();
		theUser = user;
		TopBarButton saveButton = new TopBarButton("saveProjectButton", "Projekt speichern");
		TopBarButton cancelButton = new TopBarButton("cancelButton", "Abbrechen");
		initialScreenView.setTopButton(saveButton, 0, new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				getView().addProject();
				eventBus.fireEvent(new ShowInitialTopButtonsEvent());
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
		initialScreenView.deleteTopButton(2);
	}
	
	public void setInitialScreenView(InitialScreenViewInterface view){
		this.initialScreenView = view;
	}
	
	public void addProject(String name, String description) {

		Project project = new Project(name, description);
		project.setLastChanged(new Date());
		project.setCreatedFrom(this.theUser);
		try {
			persistenceService.addProject(this.theUser, project);
		} catch (ProjectAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Projektname bereits vorhanden.");

		}
		logger.debug("Neues Projekt wurde dem User hinzugefuegt");

		projectListView.setProjects(theUser.getProjects());

		logger.debug("Neues Projekt an hinterster Stelle eingefuegt");

		eventBus.fireEvent(new ShowInitialScreenViewEvent(this.theUser));
//		eventBus.fireEvent(new ProjectAddEvent(project));
		logger.debug("ShowAddEvent gefeuert");

	}
	
	public boolean editProject(Project project, String name, String description) {


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
			eventBus.fireEvent(new ShowInitialScreenViewEvent(this.theUser));
			return true;
		} catch (ProjectAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Projektname bereits vorhanden.");
			return false;
		}


		//eventBus.fireEvent(new ProjectEditEvent(project));
		//logger.debug("ShowEdditEvent gefeuert");



	}

}
