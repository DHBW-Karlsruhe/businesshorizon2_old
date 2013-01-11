package dhbw.ka.mwi.businesshorizon2.methods;

import java.io.Serializable;

/**
 * Diese Klasse bezeichnet eine Berechnungsmethode wie die Zeitreihenanalyse
 * oder den Wiener-Prozess und bietet einige Basis-Funktionalitaeten.
 * 
 * @author Christian Gahlert
 * 
 */
abstract public class AbstractStochasticMethod implements
		Comparable<AbstractStochasticMethod>, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Diese Methode gibt den Namen der jeweiligen Methode zurueck, der dann
	 * auch fuer den Nutzer lesbar angezeigt wird.
	 * 
	 * @author Christian Gahlert
	 * @return Den Namen der Berechnungsmethode
	 */
	abstract public String getName();

	/**
	 * Die Methoden werden mit Hilfe dieses Keys sortiert. Dadurch ist es
	 * moeglich die Reihenfolge der fuer den Nutzer sichtbaren
	 * Berechnungsmethoden zu aendern.
	 * 
	 * @author Christian Gahlert
	 * @return Sortierungs-Zahl
	 */
	abstract public int getOrderKey();

	/**
	 * Mit dieser Methode wird die eigentliche Berechnung gestartet. Falls es
	 * eine aufwaendigere Berechnung ist, koennen innerhalb dieser Methode
	 * weitere Threads gestartet werden. Prinzipiell ist dies aber nicht
	 * notwendig, da der Aufruf dieser Methode ohnehin in einem von Vaadin
	 * getrennten Thread stattfindet.
	 * 
	 * Dennoch sollte mit Thread.getCurrentThread().isInterrupted() in
	 * regelmaessigen Abstaenden ueberprueft werden, ob der Berechnungs-Thread
	 * (z.B. durch Klick auf "Abbrechen") unterbrochen wurde.
	 * 
	 * Darueberhinaus sollte auch in regelmaessigen Abstaenden die
	 * Callback-Methode onProgressChange() aufgerufen werden, um die Status-Bar
	 * zu aktualisieren. Die onComplete()-Methode wird von der aufrufenden
	 * Methode ausgefuehrt und sollte daher an dieser Stelle nicht zum Einsatz
	 * kommen.
	 * 
	 * @author Christian Gahlert, Kai Westerholz
	 * @param periods
	 *            Die bisher vorhanden Perioden
	 * @param callback
	 *            Das Callback-Objekt
	 * @return Das Result-Objekt dieser Berechnung
	 * @throws InterruptedException
	 */
	abstract public double[] calculate(double[] previousValues,
			int consideredPeriodsofPast, int periodsToForecast,
			int numberOfIterations, Callback callback)
			throws InterruptedException;

	/**
	 * Diese Methode wird zum Sortieren der Methoden (fuer die Anzeige beim
	 * Nutzer) verwendet.
	 * 
	 * @author Christian Gahlert
	 * @see getOrderKey()
	 */
	@Override
	public int compareTo(AbstractStochasticMethod o) {
		return this.getOrderKey() - o.getOrderKey();
	}

}
