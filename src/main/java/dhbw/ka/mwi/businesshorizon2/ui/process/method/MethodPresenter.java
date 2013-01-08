package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

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

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	

	@Autowired
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
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	@Override
	public boolean isSelectable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void activateMethodType(){
		eventBus.fireEvent(new CheckMethodTypeEvent());
	}
	
	@EventHandler
	public void onShowMethod(ShowMethodViewEvent event){		
		
		for (AbstractStochasticMethod m : methods) {
			getView().showMethod(m.getName(),true);
		}
		
	}
	
	@EventHandler
	public void onCheckMethodType(CheckMethodTypeEvent event){

		
	}
}
