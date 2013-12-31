/**
 * 
 */
package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * @author Annika Weis
 * @date 29.12.2013
 * 
 */
public class APV_2 extends AbstractDeterministicMethod {

	@Override
	public String getName() {

		return "APV";
	}

	@Override
	public int getOrderKey() {

		return 5;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	@Override
	public DeterministicResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			DeterministicMethodException {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		DeterministicResultContainer drc = new DeterministicResultContainer(
				prognose);
		return drc;
	}

	public DeterministicResultContainer calculateValues(
			StochasticResultContainer srContainer, Szenario szenario) {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();

		double gk = 0;
		double v = 0;
		double unternehmenswert = 0;
		double sSteuersatz;
		double sKS;
		double sZinsen;
		double sEK;

		CashFlowPeriod first_period = null;
		CashFlowPeriod period;
		CashFlowPeriod lastPeriod = null;
		double jahr = 1;

		sKS = szenario.getCorporateAndSolitaryTax() / 100;
		sSteuersatz = 0.75 * szenario.getBusinessTax() / 100 + sKS;
		sEK = szenario.getRateReturnEquity() / 100;
		sZinsen = szenario.getRateReturnCapitalStock() / 100;

		for (AbstractPeriodContainer i : srContainer.getPeriodContainers()) {

			TreeSet<? extends Period> periods = i.getPeriods();
			Iterator<? extends Period> iter = periods.iterator();// descendingIterator();
			int durchlauf = 0;
			while (iter.hasNext()) {
				period = (CashFlowPeriod) iter.next();

				if (durchlauf == 0) { // Basisjahr
					first_period = period;
				} else if (durchlauf +1 == periods.size()) { //letztes Jahr wird nach der Schleife extra berechnet
				} else {
					gk += abzinsen(period.getFreeCashFlow(),sEK,durchlauf);;
					//period.getFreeCashFlow() / Math.pow(1 + sEK, durchlauf);
					v += (sSteuersatz * sZinsen * lastPeriod.getCapitalStock())
							/ Math.pow(1 + sZinsen, durchlauf);
				}
				lastPeriod = period;

				durchlauf++;
				jahr = durchlauf;
			}
		}
		//Berechnung des letzten Jahres
		gk = gk + lastPeriod.getFreeCashFlow()
				/ (sEK * Math.pow(1 + sEK, jahr-2));
		v = v + (sSteuersatz * sZinsen * lastPeriod.getCapitalStock())
				/ (sZinsen * Math.pow(1 + sZinsen, jahr-2));
		
		//Unternehmenswert gesamt berechnen
		unternehmenswert = gk + v - first_period.getCapitalStock();
		System.out.println("Endergebnis APV:" + unternehmenswert);

		DeterministicResultContainer drc = new DeterministicResultContainer(
				prognose);
		return drc;
	}
	
	/**
	 * @author Annika Weis
	 * @param wert
	 * @param zinssatz
	 * @param jahre
	 * @return
	 */
	private double abzinsen(double wert, double zinssatz, int jahre) {
		return wert
				/ Math.pow(1 + zinssatz, jahre);
	}
}
