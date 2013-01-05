package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.ArrayList;

import dhbw.ka.mwi.businesshorizon2.methods.Result;

/**
 * Diese Klasse bildet das Ergebnis der Zeitreihenanalyse ab. Das Ergebnis der
 * Zeitreiehenanalyse ist eine Liste von verschiedenen prognostizierten
 * Zeitreihen.
 * 
 * @author Kai Westerholz
 * 
 */
public class TimeseriesResult extends Result {
	private static final long serialVersionUID = 1L;
	private ArrayList<dhbw.ka.mwi.businesshorizon2.models.Timeseries> resultList;

	public TimeseriesResult(
			dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries) {
		this.resultList = new ArrayList<dhbw.ka.mwi.businesshorizon2.models.Timeseries>();
		this.resultList.add(timeseries);
	}

	/**
	 * 
	 * Dieser Methode fügt eine prognostizierte Zeitreihe zur Ergebnisliste
	 * hinzu.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param timeseries
	 */
	public void add(dhbw.ka.mwi.businesshorizon2.models.Timeseries timeseries) {
		this.resultList.add(timeseries);
	}

	/**
	 * Diese Methode liefert die Liste aller prognostizierten Zeitreihen zurück.
	 * ACHTUNG: Sollte erst aufgerufen werden, wenn alle prognostizierten
	 * Zeitreihen eingefügt wurden.
	 * 
	 * @return Ergebnisliste
	 */
	public ArrayList<dhbw.ka.mwi.businesshorizon2.models.Timeseries> getResultList() {
		return this.resultList;
	}
}
