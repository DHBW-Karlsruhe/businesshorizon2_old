package dhbw.ka.mwi.businesshorizon2.methods;

/**
 * Dieses Interface beschreibt das Callback, das zur Informations-Uebertragung
 * zwischen den Threads waehrend und nach der Berechnung verwendet wird.
 * 
 * @author Christian Gahlert
 * 
 */
public interface Callback {
	public void onComplete(double[][] result);

	public void onProgressChange(float progress);
}