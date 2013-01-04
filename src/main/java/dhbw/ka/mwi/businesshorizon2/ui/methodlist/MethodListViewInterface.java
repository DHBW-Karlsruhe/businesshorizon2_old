package dhbw.ka.mwi.businesshorizon2.ui.methodlist;

import java.util.SortedSet;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractMethod;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface MethodListViewInterface extends View {

	/**
	 * Uebergibt die anzuzeigenden Methoden in sortierter Reihenfolge.
	 * 
	 * @author Christian Gahlert
	 * @param methods
	 */
	public void setMethods(SortedSet<AbstractMethod> methods);

}
