package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;

/**
 * Diese Klasse beschreibt eine Periode, also ein Geschaeftsjahr.
 * 
 * @author Christian Gahlert
 * 
 */
public abstract class Period implements Serializable, Comparable<Period> {
	private static final long serialVersionUID = 1L;

	protected int year;

	protected double cashFlow;
	protected double capitalStock;

	abstract double getCashflow();

	abstract double getWert();

	/**
	 * Beim Erstellen einer Periode ist es umbedingt notwendig, das
	 * Geschaeftsjahr, das die Periode beschreibt, mit anzugeben. Dies kann
	 * spaeter nicht mehr geaendert werden.
	 * 
	 * @author Christian Gahlert
	 * @param year
	 *            Das Geschaeftsjahr
	 */

	/**
	 * Gibt das Geschaeftsjahr der Periode zurueck.
	 * 
	 * @author Christian Gahlert
	 * @return Das Geschaeftsjahr
	 */
	public abstract int getYear();

	/**
	 * Diese Methode gibt an, was angezeigt werden soll, wenn eine Periode als
	 * String verwendet wird. Dies kommt z.B. bei der Anzeige der jeweiligen
	 * Perioden (als Liste) zum Einsatz.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public String toString() {
		return String.valueOf(getYear());
	}

	/**
	 * Um die Perioden nach dem Geschaeftsjahr sortieren zu koennen, wird das
	 * Comparable- Interface verwendet. Spaeter erfolgt eine automatische
	 * Sortierung mit Hilfe eines SortedSets.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public int compareTo(Period o) {
		return this.getYear() - o.getYear();
	}

}
