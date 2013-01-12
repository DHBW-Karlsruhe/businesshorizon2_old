package dhbw.ka.mwi.businesshorizon2.ui.process;

import com.mvplite.presenter.Presenter;
import com.mvplite.view.View;

/**
 * Diese Klasse ist die Oberklasse fuer die Presenter der Prozessmasken. Sie stellt
 * insbesondere die beiden Methoden isValid und isSelectable zur Verfuegung, die es der
 * Prozessverwaltung ermoeglichen, zu entscheiden, ob sich eine Maske in einem gueltigen
 * Zustand befindet und ob sie aufrufbar ist.
 * 
 * @author  Julius Hacker
 * @param <T> Die zum Presenter gehoerende View.
 */
public abstract class ScreenPresenter <T extends View> extends Presenter<T> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Diese Methode gibt zurueck, ob die Eingabewerte der Maske insgesamt gueltig
	 * sind oder ob auf der Maske Korrekturen vorgenommen werden muessen, um den
	 * Wizard abschliessen zu koennen.
	 * 
	 * @return true: Eingabewerte der Maske sind gueltig
	 * false: Eingabewerte der Maske sind ungueltig und benoetigen Korrektur
	 * @author Julius Hacker
	 */
	public abstract boolean isValid();
	
	/**
	 * Diese Methode gibt zurueck, ob die Maske aufrufbar ist. Hierzu muessen alle
	 * Vorbedingungen erfuellt sein, die es der Maske ermoeglichen, sich korrekt darzustellen
	 * und zu verhalten.
	 * 
	 * @return true: Maske ist aufrufbar, Vorbedingungen sind erfuellt.
	 * false: Maske ist nicht aufrufbar, Vorbedingungen sind nicht erfuellt.
	 * @author Julius Hacker
	 */
	public abstract boolean isSelectable();
	
	public abstract void validate(ValidateContentStateEvent event);
}
