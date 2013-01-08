package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.SortedSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface MethodViewInterface extends ContentView {
	
	public void showMethod(String methodName,Boolean implemented);
	
	public void enableOptions();

}
