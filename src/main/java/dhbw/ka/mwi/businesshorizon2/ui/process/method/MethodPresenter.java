package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;
import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;


import dhbw.ka.mwi.businesshorizon2.ui.process.method.CheckMethodTypeEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.ProjectInputType;
import dhbw.ka.mwi.businesshorizon2.ui.process.InvalidStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;

import dhbw.ka.mwi.businesshorizon2.ui.process.ValidStateEvent;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenSelectableEvent;

import dhbw.ka.mwi.businesshorizon2.ui.process.ShowErrorsOnScreenEvent;

import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Auswahl der Berechnungsmethoden. 
 * 
 * @author Julius Hacker
 *
 */

public class MethodPresenter extends ScreenPresenter<MethodViewInterface> {

	private static final long serialVersionUID = 1L;


	private Logger logger = Logger.getLogger(MethodPresenter.class);

	private Boolean showError = false;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private Project project;
	
	private SortedSet<AbstractStochasticMethod> methods;
	private ProjectInputType projectInputType;
	

	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);

		this.methods = project.getMethods();
		projectInputType = project.getProjectInputType();
		logger.debug("test");

	}

	@Override
	public boolean isValid() {
		boolean valid = false;
		if (projectInputType.getStochastic()) {
			
			for (AbstractStochasticMethod m: methods){
				if (m.getSelected()) {
					valid = true;
				}
				
			}
			
			if (showError){
				getView().showErrorNoMethodSelected(valid);
			}
		
		} else if (projectInputType.getDeterministic()){
			
			valid = true;	
			
			if (showError){
				getView().showErrorNothingSelected(valid);
			}
		}
		
		return valid;
	}
	
	public void toggleMethodType(Boolean stochastic,Boolean checked){
		eventBus.fireEvent(new CheckMethodTypeEvent(stochastic,checked));

		
		
		getView().showInputMethodSelection(stochastic, checked);
		
		if (stochastic){
			projectInputType.setStochastic(checked);
			getView().enableMethodSelection(checked);
			
		}
		else if (!stochastic){
			projectInputType.setDeterministic(checked);
		}
		
		this.validate(new ValidateContentStateEvent());
	}
	
	public void toggleMethod(Set<AbstractStochasticMethod> checkedMethods){
		eventBus.fireEvent(new CheckMethodEvent(checkedMethods));
		
		for (AbstractStochasticMethod m : methods){
			m.setSelected(false);
			if (checkedMethods.contains(m)){
				m.setSelected(true);
			}
				
		}
		this.validate(new ValidateContentStateEvent());

	}
	
	public void toggleMethodTypeInput(Boolean stochastic, InputType newSelected){
		eventBus.fireEvent(new InputTypeChangedEvent());
		if (stochastic){
			projectInputType.setStochasticInput(newSelected);
		}
		else{
			projectInputType.setDeterministicInput(newSelected);
		}
	}
	
	@EventHandler
	public void onShowMethod(ShowMethodViewEvent event){		
	
		for (AbstractStochasticMethod m : methods) {
			getView().showMethod(m);
		}
		
		Boolean state = projectInputType.getStochastic();
		
		if (state != null){
			getView().enableMethodSelection(state);
		}
		else{
			projectInputType.setStochastic(false);
			getView().enableMethodSelection(false);
		}
		
		getView().showInputMethodSelection(true, projectInputType.getStochastic());
		getView().showInputMethodSelection(false, projectInputType.getDeterministic());
		getView().selectInput(true, projectInputType.getStochasticInput().getCaption());
		getView().selectInput(false, projectInputType.getDeterministicInput().getCaption());
		
		eventBus.fireEvent(new ScreenSelectableEvent(NavigationSteps.METHOD,true));
		
	}
	

	

	@Override
	@EventHandler
	public void validate(ValidateContentStateEvent event) {
		if (!this.isValid()){
			eventBus.fireEvent(new InvalidStateEvent(NavigationSteps.METHOD,showError));
		}
		else {
			eventBus.fireEvent(new ValidStateEvent(NavigationSteps.METHOD));
		}
		
	}


	@EventHandler
	@Override
	public void handleShowErrors(ShowErrorsOnScreenEvent event) {

		if (event.getStep()==NavigationSteps.METHOD){
			showError = true;
		}
		
	}

}
