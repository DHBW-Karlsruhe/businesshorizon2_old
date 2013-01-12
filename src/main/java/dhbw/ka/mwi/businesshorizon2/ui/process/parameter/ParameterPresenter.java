package dhbw.ka.mwi.businesshorizon2.ui.process.parameter;

import javax.annotation.PostConstruct;

import dhbw.ka.mwi.businesshorizon2.ui.process.ScreenPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.ValidateContentStateEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.parameter.ParameterViewInterface;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Parameter.
 * 
 * @author Julius Hacker
 *
 */

public class ParameterPresenter extends ScreenPresenter<ParameterViewInterface> {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
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


	@Override
	public void validate(ValidateContentStateEvent event) {
		// TODO Auto-generated method stub
		
	}
}
