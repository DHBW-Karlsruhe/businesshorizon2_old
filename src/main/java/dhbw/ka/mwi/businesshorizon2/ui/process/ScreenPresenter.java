package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;

/**
 * Diese Klasse ist die Oberklasse fuer die Presenter der Prozessmasken. Sie
 * stellt insbesondere die beiden Methoden isValid und isSelectable zur
 * Verfuegung, die es der Prozessverwaltung ermoeglichen, zu entscheiden, ob
 * sich eine Maske in einem gueltigen Zustand befindet und ob sie aufrufbar ist.
 * 
 * @author Julius Hacker
 * @param <T>
 *            Die zum Presenter gehoerende View.
 */
public abstract class ScreenPresenter<T extends View> extends Presenter<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * Diese Methode gibt zurueck, ob die Eingabewerte der Maske insgesamt
	 * gueltig sind oder ob auf der Maske Korrekturen vorgenommen werden
	 * muessen, um den Wizard abschliessen zu koennen.
	 * 
	 * @return true: Eingabewerte der Maske sind gueltig false: Eingabewerte der
	 *         Maske sind ungueltig und benoetigen Korrektur
	 * @author Julius Hacker
	 */
	public abstract boolean isValid();

	/**
	 * Diese Methode ueberprueft auf Anforderung durch ein Event, ob die
	 * Eingaben im aktuellen Screen korrekt oder inkorrekt sind. Bei validen
	 * Eingaben sollte sie ein ValidStateEvent feuern, bei invaliden Eingaben
	 * ein InvalidStateEvent.
	 * 
	 * 
	 * @param event
	 *            Das gefeuerte ValidContentStateEvent
	 * @author Julius Hacker
	 */
	public abstract void validate(ValidateContentStateEvent event);

	/**
	 * Diese Methode kuemmert sich um das ShowErrorsOnScreenEvent. Dieses sagt
	 * insbesondere aus, dass ab sofort in dem Screen, der im Event angegeben
	 * ist, Fehlermeldungen angezeigt werden sollen. Die Methode sollte
	 * ueberpruefen, ob das Event fuer ihren Screen relevant ist (per
	 * event.getStep()) und gegebenenfalls das Verhalten des Screens
	 * entsprechend umstellen, sodass Fehlermeldungen angezeigt werden.
	 * 
	 * @param event
	 */
	public abstract void handleShowErrors(ShowErrorsOnScreenEvent event);
}
