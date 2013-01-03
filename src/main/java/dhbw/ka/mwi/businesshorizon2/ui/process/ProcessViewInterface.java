package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.view.View;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface ProcessViewInterface extends View {
	
	/**
	 * Diese Methode setzt die obere und untere View in der Prozessansicht.
	 * 
	 * @param topView Die obere View-Komponente, gedacht fuer die Navigation
	 * @param bottomView Die untere View-Komponente, gedacht fuer die anzuzeigenden Masken.
	 * @author Julius Hacker
	 */
	public void showView(View topView, View bottomView);
}
