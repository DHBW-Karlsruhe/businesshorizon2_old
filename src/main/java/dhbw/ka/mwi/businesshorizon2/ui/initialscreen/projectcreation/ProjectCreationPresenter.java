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
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.TopBarButton;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectdetails.ProjectDetailsViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectListEvent;

public class ProjectCreationPresenter extends Presenter<ProjectCreationViewInterface>{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("InitialScreenPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	private Window window;
	
	private InitialScreenViewInterface initialScreenView;

	@PostConstruct
	public void init(){
		eventBus.addHandler(this);
		logger.debug("Eventhandler Hinzugefügt");
		
	}
	
	
	@EventHandler
	public void onShowScreen(ShowProjectCreationButtonsEvent event){
		final User user = event.getUser();
		TopBarButton saveButton = new TopBarButton("saveProjectButton", "Projekt speichern");
		TopBarButton cancelButton = new TopBarButton("cancelButton", "Abbrechen");
		initialScreenView.setTopButton(saveButton, 0, new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				
				
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

}
