package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface MethodViewInterface extends ContentView {
	
	public void showMethod(AbstractStochasticMethod method);
	
	public void enableOptions();
	
	public void enableMethodSelection(Boolean state);
	
	public void showInputMethodSelection(Boolean stochastic, Boolean checked);
	
	public void selectInput(Boolean stochastic,InputType selected);
	
	public void showErrorNoMethodSelected(Boolean state);
	
	public void showErrorNothingSelected(Boolean state);

}
