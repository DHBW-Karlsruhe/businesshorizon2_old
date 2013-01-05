package dhbw.ka.mwi.businesshorizon2.models;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.methods.Result;
import dhbw.ka.mwi.businesshorizon2.methods.timeseries.CalculateTideInterface;

/**
 * Diese Klasse stellt das Datenobjet der Zeitreiehenanalyse und die damit
 * verbundenen Operationen dar. Der Wert an der Stelle 0 ist die am weitesten
 * Entfernte Periode in der Verangenheit! Der letzte Wert entspricht dem
 * Beobachtungswert t=0 Die Abfolge der Perioden sieht also wie folgt aus
 * (Beispiel: 5 perioden): [t=-4] [t=-3] [t=-2] [t=-1] [t=0] Handelt es sich um
 * prognostizierte Werte bleibt die Reihenfolge gleich. der aktuelle Werte ist
 * weiterhin an der Stelle 0. Der letzte Wert ist der am weitesten entfernte
 * Wert in der Zukunft Beispiel: [t=0] [t=1] [t=2] [t=3] [t=4]
 * 
 * @author Kai Westerholz
 * 
 */

public class Timeseries extends Result {
	private static final long serialVersionUID = 1L;

	@Autowired
	private CalculateTideInterface calculateTide;

	private SortedSet<Period> timeseries;
	private int yearOfPeriodZero;

	/**
	 * Dieser Konstruktor nimmt eine ArrayListe mit double Werte endgegen und
	 * speichert diese und generiert gleichzeitig ein SortedSet mit Perioden den
	 * Ausgangswerten
	 * 
	 * @author Kai Westerholz
	 * @param timeseries
	 * @param yearOfPeriodZero
	 */
	public Timeseries(ArrayList<Double> timeseries, int yearOfPeriodZero) {
		this.yearOfPeriodZero = yearOfPeriodZero;
		this.timeseries = new TreeSet<Period>();
		for (int i = 0; i < timeseries.size(); i++) {
			Period period = new Period(yearOfPeriodZero
					- (timeseries.size() - (i + 1)));
			period.setCashFlow(timeseries.get(i));
			this.timeseries.add(period);
		}
	}

	/**
	 * Dieser Konstruktor nimmt ein SortedSet mit Perioden entgegen und wandelt
	 * diese in eine ArrayList um. Die letzte Periode stellt hierbei das Jahr
	 * der Beobachtung t=0 dar.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param timeseriesSorted
	 */
	public Timeseries(SortedSet<Period> timeseriesSorted) {
		this.timeseries = timeseriesSorted;
		this.yearOfPeriodZero = timeseriesSorted.last().getYear();
	}

	/**
	 * Diese Methode bereinigt die Zeitreihe um die Trendgerade und berechnet
	 * diese wenn nötig. Die Formel der Bereinigung lautet: Y*(t) = T(t) - Y(t)
	 * 
	 * @author Kai Westerholz
	 */
	public void reduceTide() {
		calculateTide.setTimeseries(this);
		calculateTide.reduceTide();

	}

	/**
	 * Diese Methode addiert zur Zeitreihe die Werte der Trendgerade nach
	 * folgender Formel: Y(t) = T(t) + Y*(t)
	 * 
	 * @author Kai Westerholz
	 */

	public void addTide() {
		calculateTide.setTimeseries(this);
		calculateTide.addTide();
	}

	/**
	 * Diese Methode gibt die Zeitreihe in Form "Jahreszahl : Wert " zurück
	 * 
	 * @author Kai Westerholz
	 * @return String zeitreihe
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String out = "Zeitreihe: ";
		/*
		 * for (int i = 0; i < this.timeseriesSorted.size(); i++) { out += "\""
		 * + (yearOfPeriodZero - (this.timeseriesSorted.size() - (i + 1))) +
		 * "\": " + df.format(this.timeseriesSorted.get(i)) + "\t"; }
		 */

		Iterator<Period> it = this.timeseries.iterator();
		while (it.hasNext()) {
			Period period = it.next();
			out += "\"" + period.getYear() + "\": "
					+ df.format(period.getCashFlow()) + "\t";
		}

		return out;
	}

	public SortedSet<Period> getTimeseries() {
		return this.timeseries;
	}

	/**
	 * Diese Methode konvertiert das SortedSet der Perioden in eine ArrayListe
	 * mit den Werten der CashFlows. Der erste Wert ist stets der am weitesten
	 * zurückliegende Wert.
	 * 
	 * @author Kai Westerholz
	 * @return ArrayListe der Double-Werte
	 */
	public ArrayList<Double> getArrayList() {
		ArrayList<Double> timeseries = new ArrayList<Double>();
		Iterator<Period> it = this.timeseries.iterator();
		while (it.hasNext()) {
			timeseries.add(it.next().getCashFlow());
		}
		return timeseries;
	}

	public int getYearofPeriodZero() {
		return this.yearOfPeriodZero;
	}
}