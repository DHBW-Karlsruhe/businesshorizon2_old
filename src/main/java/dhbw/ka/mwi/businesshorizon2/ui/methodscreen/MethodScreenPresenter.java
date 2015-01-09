package dhbw.ka.mwi.businesshorizon2.ui.methodscreen;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.services.proxies.ProjectProxy;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.SelectProjectEvent;

public class MethodScreenPresenter extends Presenter<MethodScreenViewInterface>{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger("MethodScreenPresenter.class");
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired 
	private ProjectProxy projectProxy;
	
	private Project project;
	
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
	
	@EventHandler
	public void onProjectSelected(SelectProjectEvent event){
		project = projectProxy.getSelectedProject();
		getView().setProject(project);
	}

	public void setInputMethod(InputType value, boolean deterministic) {
		if(deterministic){
			project.getProjectInputType().setDeterministicInput(value);
		}
		else{
			project.getProjectInputType().setStochasticInput(value);
		}
		
	}
	
	public void setPrognoseMethode(boolean deterministic){
		if(deterministic){
			project.getProjectInputType().setDeterministic(deterministic);;
			project.getProjectInputType().setStochastic(false);
		}
		else{
			project.getProjectInputType().setDeterministic(deterministic);
			project.getProjectInputType().setStochastic(true);
		}
	}

}
