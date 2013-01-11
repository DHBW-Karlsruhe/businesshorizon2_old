package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;
import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.ui.process.method.CheckMethodTypeEvent;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.ProjectInputType;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Auswahl der Berechnungsmethoden. 
 * 
 * @author Julius Hacker
 *
 */

public class MethodPresenter extends ScreenPresenter<MethodViewInterface> {

	private static final long serialVersionUID = 1L;
	

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
	}

	@Override
	public boolean isValid() {
		if (projectInputType.getStochastic())
			for (AbstractStochasticMethod m: methods){
				if (m.getSelected())
					return true;
			}
		return false;
	}
	
	
	
	@Override
	public boolean isSelectable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void toggleMethodType(Boolean stochastic,Boolean checked){
		eventBus.fireEvent(new CheckMethodTypeEvent(stochastic,checked));
	}
	
	public void toggleMethod(Set<String> checkedMethods){
		eventBus.fireEvent(new CheckMethodEvent(checkedMethods));
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
			getView().showMethod(m.getName(),m.getImplemented(),m.getSelected());
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
		
	}
	
	@EventHandler
	public void onCheckMethodType(CheckMethodTypeEvent event){
		
		Boolean check = event.getChecked();
		Boolean stoch = event.getStochastic();
		
		getView().showInputMethodSelection(stoch, check);
		
		if (stoch){
			projectInputType.setStochastic(check);
			getView().enableMethodSelection(check);
		}
		else if (!stoch){
			projectInputType.setDeterministic(check);
		}
		
	}
	
	@EventHandler
	public void onCheckMethod(CheckMethodEvent event){
		Set<String> checkedUIMethods = event.getCheckedMethods();
		
		for (AbstractStochasticMethod m : methods){
			m.setSelected(false);
			for (String n : checkedUIMethods){				
				if (m.getName().equals(n)){
					m.setSelected(true);
				}
				
			}
		}
		
	}
}
