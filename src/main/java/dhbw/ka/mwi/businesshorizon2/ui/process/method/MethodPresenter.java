package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;
import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.ui.process.method.CheckMethodTypeEvent;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Auswahl der Berechnungsmethoden. 
 * 
 * @author Julius Hacker
 *
 */

public class MethodPresenter extends ScreenPresenter<MethodViewInterface> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger("MethodPresenter.class");

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private SortedSet<AbstractStochasticMethod> methods;

	
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
		
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	@Override
	public boolean isSelectable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void toggleMethodType(){
		eventBus.fireEvent(new CheckMethodTypeEvent());
	}
	
	public void toggleMethod(Set<String> checkedMethods){
		eventBus.fireEvent(new CheckMethodEvent(checkedMethods));
	}
	
	@EventHandler
	public void onShowMethod(ShowMethodViewEvent event){		
	

		for (AbstractStochasticMethod m : methods) {
			getView().showMethod(m.getName(),m.getImplemented(),m.getSelected());

		}
		
	}
	
	@EventHandler
	public void onCheckMethodType(CheckMethodTypeEvent event){
		
		
		
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
		
		logger.debug("Angewählte Methoden "+ methods.toString());
	}
}
