package dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow;

import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.DeterministicMethodException;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;

/**
 * Die Klasse FTE stellt die Methoden zur Berechnung des Unternehmenswerts mittels des FTE-Ansatzes zur Verfuegung.
 *
 * @author Tobias Blasy
 * @date 15.01.2015
 */
public class FTE extends AbstractDeterministicMethod {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger("FTE.class");
	private double unternehmenswert;
	private double fremdkapital;

	@Override
	public String getName() {
		return "Flow-to-Equity (FTE)";
	}

	@Override
	public int getOrderKey() {

		return 4;
	}

	@Override
	public Boolean getImplemented() {
		return true;
	}

	@Override
	public DeterministicResultContainer calculate(Project project, CallbackInterface callback) throws InterruptedException,	DeterministicMethodException {
		TreeSet<CashFlowPeriodContainer> prognose = new TreeSet<CashFlowPeriodContainer>();
		DeterministicResultContainer drc = new DeterministicResultContainer(prognose);
		return drc;
	}

	/**
	 * berechnet das Eigenkapital der vorletzten Periode (= T) (wenn man die ewige Rente als letzte Periode nimmt)
	 * 
	 * @author Tobias Blasy
	 * @param double fteIndexTplusEins
	 * @param double eigenkapitalRenditeUnverschuldet
	 * @param double fremdkapitalRendite
	 * @param double fremdkapitalIndexT
	 * @param double steuersatz
	 * @return double ekLetztePeriode
	 */
	public double berechneEkVorletztePeriode(double fteIndexTplusEins, double eigenkapitalRenditeUnverschuldet, double fremdkapitalRendite, double fremdkapitalIndexT, double steuersatz) {
		return ((fteIndexTplusEins - fremdkapitalIndexT * (eigenkapitalRenditeUnverschuldet - fremdkapitalRendite) * (1 - steuersatz))/eigenkapitalRenditeUnverschuldet);
	}

	/**
	 * berechnet das Eigenkapital der übrigen Perioden 0 bis T-2 (wenn man die ewige Rente (= T+1) als letzte Periode nimmt)
	 * 
	 * @author Tobias Blasy
	 * @param int t
	 * @param int indexT
	 * @param double fteIndextPlusEins
	 * @param double eigenkapitalwertIndextPlusEins
 	 * @param double eigenkapitalRenditeUnverschuldet
	 * @param double fremdkapitalRendite
	 * @param double steuersatz 
	 * @param double[] fremdkapital
	 * @return double ekRestlichePerioden
	 */
	public double berechneEkRestlichePerioden(int t, int indexT, double fteIndextPlusEins, double eigenkapitalwertIndextPlusEins, double eigenkapitalRenditeUnverschuldet, double fremdkapitalRendite, double steuersatz, double[] fremdkapital) {
		return ((fteIndextPlusEins + eigenkapitalwertIndextPlusEins - (eigenkapitalRenditeUnverschuldet - fremdkapitalRendite) * (fremdkapital[t] - berechneSumme(t, indexT, steuersatz, fremdkapitalRendite, fremdkapital) - (steuersatz * fremdkapital[indexT]) / (Math.pow(1 + fremdkapitalRendite, indexT - t))))
				/
				(1 + eigenkapitalRenditeUnverschuldet));
	}

	/**
	 * berechnet eine Summe die für die Berechnung des Eigenkapitals der restlichen Perioden benötigt wird
	 * 
	 * @author Tobias Blasy
	 * @param int t
	 * @param int indexT
	 * @param double steuersatz
	 * @param double fremdkapitalRendite
	 * @param double[] fremdkapital
	 * @return double summe
	 */
	public double berechneSumme(int t, int indexT, double steuersatz, double fremdkapitalRendite, double[] fremdkapital) {
		double sum = 0;
		for(int j = t+1; j <= indexT; j++) {
			sum += ((steuersatz * fremdkapitalRendite * fremdkapital[j-1])/(Math.pow(1 + fremdkapitalRendite, j-t)));
		}
		return sum;
	}
	
	/**
	 * ermittelt aus den übergebenen Cashflows, den Fremdkapitalangaben und dem Szenario auf rekursivem Weg die Unternehmenswerte und gibt den entsprechenden Wert dieser (der 0.) Periode zurück
	 *
	 * @author Tobias Blasy
	 * @param double[] cashflow
	 * @param double[] fremdkapital,
	 * @param Szenario szenario
	 * @return double unternehmenswert
	 */
	public double calculateValues(double[] cashflow, double[] fremdkapital,	Szenario szenario) {
		double fremdkapitalRendite = szenario.getRateReturnCapitalStock() / 100;
		double eigenkapitalRenditeUnverschuldet = szenario.getRateReturnEquity() / 100;
		// ToDo die Berechnung des Steuersatzes muss noch überprüft werden
		double steuersatz = 0.75 * szenario.getBusinessTax() / 100 + szenario.getCorporateAndSolitaryTax() / 100;
		double[] eigenkapitalwerte = new double[cashflow.length];
		int indexT = eigenkapitalwerte.length-2;
		
		//zunächst werden aus den FCF die Ausschüttungen für die Perioden errechnet
		for(int i = 1; i < cashflow.length; i++) {
			cashflow[i] = cashflow[i] + (fremdkapitalRendite * steuersatz * fremdkapital[i-1]) - (fremdkapitalRendite * fremdkapital[i-1]) + (fremdkapital[i] - fremdkapital[i-1]);
		}

		eigenkapitalwerte[indexT] = berechneEkVorletztePeriode(cashflow[indexT+1], eigenkapitalRenditeUnverschuldet, fremdkapitalRendite, fremdkapital[indexT], steuersatz);
		eigenkapitalwerte[indexT+1] = eigenkapitalwerte[indexT];

		for(int t = indexT-1; t >= 0; t--) {
			eigenkapitalwerte[t] = berechneEkRestlichePerioden(t, indexT, cashflow[t+1], eigenkapitalwerte[t+1], eigenkapitalRenditeUnverschuldet, fremdkapitalRendite, steuersatz, fremdkapital);
		}
		this.setFremdkapital(fremdkapital[0]);
		this.setUnternehmenswert(eigenkapitalwerte[0]);
		return eigenkapitalwerte[0];
	}

	public List<String> getPeriodenNamen(Project project) {
		List<String> perioden = null;

		return perioden;
	}
	
	public double getUnternehmenswert() {
		return this.unternehmenswert;
	}
	
	public void setUnternehmenswert(double unternehmenswert) {
		this.unternehmenswert = unternehmenswert;
	}
	
	public double getFremdkapital() {
		return this.fremdkapital;
	}
	
	public void setFremdkapital(double fremdkapital) {
		this.fremdkapital = fremdkapital;
	}	
}
