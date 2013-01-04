package dhbw.ka.mwi.businesshorizon2.ui.method;

import com.mvplite.view.View;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractMethod;
import dhbw.ka.mwi.businesshorizon2.methods.Result;

/**
 * Dieses Interface zeigt die von der View zur Verfuegung stehenden Methoden,
 * mit denen der Presenter mit der View kommunizieren kann.
 * 
 * @author Christian Gahlert
 *
 */
public interface MethodViewInterface extends View {

	/**
	 * Eine Methode zur Anzeige uebergeben.
	 *
	 * @author Christian Gahlert
	 * @param method Die anzuzeigende Methode
	 */
	public void showMethod(AbstractMethod method);
	
	/**
	 * Zeige die Progressbar, waehrend die Berechnung laeuft.
	 * 
	 * @author Christian Gahlert
	 */
	public void showProgress();

	/**
	 * Update die Progressbar. Dazu wird eine float-Zahl zwischen 0 und 1 
	 * uebergeben. (0 = 0%, 1 = 100%). 
	 * 
	 * Achtung: Diese Methode wird aus einem anderen Thread aufgerufen,
	 * somit muss darauf geachtet werden, dass eine Thread-sichere 
	 * Implementierung verwendet wird.
	 * 
	 * @author Christian Gahlert
	 * @param progress Der Fortschritt
	 */
	public void setProgress(float progress);

	/**
	 * Verstecke die Progressbar, wenn die Berechnung abgeschlossen ist.
	 * 
	 * Achtung: Diese Methode wird aus einem anderen Thread aufgerufen,
	 * somit muss darauf geachtet werden, dass eine Thread-sichere 
	 * Implementierung verwendet wird.
	 * 
	 * @author Christian Gahlert
	 */
	public void hideProgress();

	/**
	 * Zeige das Ergebnis der Berechnung an.
	 * 
	 * Achtung: Diese Methode wird aus einem anderen Thread aufgerufen,
	 * somit muss darauf geachtet werden, dass eine Thread-sichere 
	 * Implementierung verwendet wird.
	 * 
	 * @param result Das anzuzeigende Result.
	 */
	public void showResult(Result result);

}
