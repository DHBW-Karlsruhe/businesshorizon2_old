package dhbw.ka.mwi.businesshorizon2.methods;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;

/**
 * Dieses Interface beschreibt das Callback, das zur Informations-Uebertragung
 * zwischen den Threads waehrend und nach der Berechnung verwendet wird.
 * 
 * @author Christian Gahlert
 * 
 */
public interface Callback {
	public void onComplete(StochasticResultContainer result);

	public void onProgressChange(float progress);
}