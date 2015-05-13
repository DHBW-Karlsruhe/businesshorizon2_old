/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.vaadin.ui.Label;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.discountedCashflow.APV;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.services.persistence.ProjectAlreadyExistsException;

/**
 * Bei dieser Klasse handelt es sich um eine Art Container-Objekt. Dieses Objekt
 * wird via Spring einmal pro Session erstellt und bleibt somit bestehen. Sollte
 * z.B. vorher gespeicherte Daten geladen werden, dann muessen diese an dieses
 * Objekt uebergeben werden (d.h. es ist nicht moeglich, dieses Objekt zu
 * ersetzen).
 * 
 * @author Christian Gahlert
 * 
 */
public class Project implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 9199799755347070847L;

	private static final Logger logger = Logger
			.getLogger("Project.class");

	protected TreeSet<? extends Period> periods = new TreeSet<>();

	private Date lastChanged;

	private User createdFrom;

	private String name;
	private String typ;

	private String description;

	private AbstractPeriodContainer stochasticPeriods, deterministicPeriods;

	public AbstractPeriodContainer getStochasticPeriods() {
		return stochasticPeriods;
	}

	public void setStochasticPeriods(AbstractPeriodContainer stochasticPeriods) {
		this.stochasticPeriods = stochasticPeriods;
	}

	public AbstractPeriodContainer getDeterministicPeriods() {
		return deterministicPeriods;
	}

	public void setDeterministicPeriods(AbstractPeriodContainer deterministicPeriods) {
		this.deterministicPeriods = deterministicPeriods;
	}

	private double CashFlowProbabilityOfRise;
	private double CashFlowStepRange;
	private double BorrowedCapitalProbabilityOfRise;
	private double BorrowedCapitalStepRange;

	private int periodsToForecast;
	private int periodsToForecast_deterministic;//Annika Weis
	private int specifiedPastPeriods;
	private int relevantPastPeriods;
	private int iterations;
	private int basisYear;
	private ProjectInputType projectInputType;

	private SortedSet<AbstractStochasticMethod> methods;

	/**
	 * Die AbstractStochasticMethod wird benötigt um die zukünftigen CashFlows zu simulieren
	 * Die AbstractDeterministicMethod wird benötigt, um an Hand von zukünftigen Cashflows den Unternehmenswert zu berechnen, 
	 * egal ob die CashFlows deterministisch eingegeben werden oder vorher mit der AbstractStochasticMethod berechnet wurden.
	 */
	private AbstractStochasticMethod stoMethod;
	private AbstractDeterministicMethod calcMethod;
	//Annika Weis
	private SortedSet<AbstractDeterministicMethod> methods_deterministic;

	protected List<Szenario> scenarios = new ArrayList<Szenario>();

	private double companyValue;

	/**
	 * Konstruktor des Projekts, mit dessen der Name gesetzt wird. 
	 * Außerdem werden analog zum Fachkonzept die Default-Werte der Parameter gesetzt.
	 * 
	 * @author Christian Scherer, Marcel Rosenberger
	 * @param Der
	 *            Name des Projekts
	 */
	public Project(String name, String description) {
		this.name = name;
		this.setDescription(description);
		this.projectInputType = new ProjectInputType();
		this.iterations = 10000;
		this.specifiedPastPeriods = 6;
		this.relevantPastPeriods = 5;
		this.periodsToForecast = 3;
		this.periodsToForecast_deterministic = 3;
		
		//Default Wert Berechnungsmethode (APV, weil oben in der RadioButton-Liste im UI)
		this.setCalculationMethod(new APV());
		
		//Default Wert Eingabemethode (FCF, weil oben in der RadioButton-Liste im UI)
		this.setStochasticPeriods(new CashFlowPeriodContainer());

	}

	/**
	 * Standardkonstruktor des Projekt
	 * 
	 * @author Christian Scherer
	 */

	public Project() {
		this.projectInputType = new ProjectInputType();

	}

	/**
	 * Gibt die Erhöhungswahrscheinlichkeit des CashFlows zurueck.
	 * 
	 * @author Kai Westerholz
	 * @return
	 */
	public double getCashFlowProbabilityOfRise() {
		return CashFlowProbabilityOfRise;
	}

	/**
	 * Setzt die Erhöhungswahrscheinlichkeit des CashFlows.
	 * 
	 * @author Kai Westerholz
	 * @param cashFlowProbabilityOfRise
	 */
	public void setCashFlowProbabilityOfRise(double cashFlowProbabilityOfRise) {
		CashFlowProbabilityOfRise = cashFlowProbabilityOfRise;
	}

	/**
	 * Gibt die Schrittweise des CashFlows zurueck.
	 * 
	 * @author Kai Westerholz
	 * @return
	 */
	public double getCashFlowStepRange() {
		return CashFlowStepRange;
	}

	/**
	 * Setzt die Schrittweise vom CashFlow.
	 * 
	 * @author Kai Westerholz
	 * @param cashFlowStepRange
	 */
	public void setCashFlowStepRange(double cashFlowStepRange) {
		CashFlowStepRange = cashFlowStepRange;
	}

	/**
	 * Gibt die Erhöhungswahrscheinlichkeit des CashFlows zurueck.
	 * 
	 * @author Kai Westerholz
	 * @return Erhöhungswahrscheinlichkeit CashFlow
	 */

	public double getBorrowedCapitalProbabilityOfRise() {
		return BorrowedCapitalProbabilityOfRise;
	}

	/**
	 * Setzt die Wahrscheinlichkeit der Erhoehung des Fremdkapitals.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @param borrowedCapitalProbabilityOfRise
	 */
	public void setBorrowedCapitalProbabilityOfRise(double borrowedCapitalProbabilityOfRise) {
		BorrowedCapitalProbabilityOfRise = borrowedCapitalProbabilityOfRise;
	}

	/**
	 * Gibt die Schrittweite des Fremdkapitals an.
	 * 
	 * @author Kai Westerholz
	 * 
	 * @return Schrittweite
	 */
	public double getBorrowedCapitalStepRange() {
		return BorrowedCapitalStepRange;
	}

	/**
	 * Setzt die Schrittweite des Fremdkapitals.
	 * 
	 * @author Kai Westerholz
	 * @param borrowedCapitalStepRange
	 */
	public void setBorrowedCapitalStepRange(double borrowedCapitalStepRange) {
		BorrowedCapitalStepRange = borrowedCapitalStepRange;
	}

	/**
	 * Gibt die Perioden in einem sortierten NavigableSet zurueck.
	 * 
	 * @author Christian Gahlert
	 * @return Die Perioden
	 */
	public NavigableSet<? extends Period> getPeriods() {
		return periods;
	}


	/**
	 * @deprecated Bitte getter für die stochastiPeriods und DeterministicPeriods
	 *             verwenden Ueberschreibt die bisher verwendeten Methoden. Die
	 *             Perioden muessen in Form eines sortierten NavigableSet
	 *             vorliegen.
	 * 
	 * @param periods
	 *            Die Perioden
	 */
	@Deprecated
	public void setPeriods(TreeSet<? extends Period> periods) {
		this.periods = periods;
	}

	/**
	 * Diese Methode soll lediglich eine Liste von verfuegbaren Jahren
	 * zurueckgeben. Ein Jahr ist verfuegbar, wenn noch keine Periode fuer das
	 * Jahr existiert. Es wird beim aktuellen Jahr begonnen und dann
	 * schrittweise ein Jahr runtergezaehlt. Dabei sollte z.B. nach 10 oder 20
	 * Jahren schluss sein (mehr Jahre wird wohl niemand eingeben wollen).
	 * 
	 * @author Christian Gahlert
	 * @return Die verfuegbaren Jahre absteigend sortiert
	 */
	public List<Integer> getAvailableYears() {
		ArrayList<Integer> years = new ArrayList<Integer>();

		int start = Calendar.getInstance().get(Calendar.YEAR);
		boolean contains;

		for (int i = start; i > start - 5; i--) {
			contains = false;

			for (Period period : periods) {
				if (period.getYear() == i) {
					contains = true;
					break;
				}
			}

			if (!contains) {
				years.add(i);
			}
		}

		return years;
	}

	/**
	 * Liesst das Datum der letzten Bearbeitung aus. Wird benötigt für die
	 * Anwenderinformation auf dem Auswahl-screen für Projekte.
	 * 
	 * @author Christian Scherer
	 * @return Datum der letzten Aenderung
	 */
	public Date getLastChanged() {
		return lastChanged;
	}

	/**
	 * Wird aufgerufen, wenn Aenderungen am Projekt vorgenommen wurden und
	 * aktualisiert dann das bisherige Aenderungsdatum.
	 * 
	 * @author Christian Scherer
	 * @param heutiges
	 *            Datum (Aenderungszeitpunkt)
	 */
	public void setLastChanged(Date lastChanged) {
		this.lastChanged = lastChanged;
	}

	/**
	 * Gibt den Namen des Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Name des Projekts
	 */
	public String getName() {
		return name;
	}

	public void setMethods(SortedSet<AbstractStochasticMethod> methods) {
		this.methods = methods;
	}

	public SortedSet<AbstractStochasticMethod> getMethods() {
		return methods;
	}

	//Annika Weis
	public void setMethods_deterministic(SortedSet<AbstractDeterministicMethod> methods_deterministic) {
		this.methods_deterministic = methods_deterministic;
	}

	//Annika Weis
	public SortedSet<AbstractDeterministicMethod> getMethods_deterministic() {
		return methods_deterministic;
	}

	public void setStochasticMethod(AbstractStochasticMethod method){
		this.stoMethod = method;
	}

	public AbstractStochasticMethod getStochasticMethod(){
		return this.stoMethod;
	}

	public void setCalculationMethod(AbstractDeterministicMethod method){
		this.calcMethod = method;
	}

	public AbstractDeterministicMethod getCalculationMethod(){
		return this.calcMethod;
	}

	public ProjectInputType getProjectInputType() {
		return projectInputType;
	}

	public void setProjectInputType(ProjectInputType projectInputType) {
		this.projectInputType = projectInputType;
	}

	/**
	 * Setzt den Namen des Projekts.
	 * 
	 * @author Christian Scherer
	 * @param name
	 *            Name des Projekts
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Anzahl vorherzusagender Perioden des Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Anzahl vorherzusagender Perioden
	 */
	public int getPeriodsToForecast() {
		return periodsToForecast;
	}

	/**
	 * Setzt die Anzahl vorherzusagender Perioden des Projekts.
	 * 
	 * @author Christian Scherer
	 * @param periodsToForecast
	 *            Anzahl vorherzusagender Perioden
	 */
	public void setPeriodsToForecast(int periodsToForecast) {
		this.periodsToForecast = periodsToForecast;
	}


	/**
	 * Gibt die Anzahl vorherzusagender deterinistischer Perioden des Projekts zurück.
	 * 
	 * @author Annika Weis
	 * @return Anzahl vorherzusagender Perioden
	 */
	public int getPeriodsToForecast_deterministic() {
		return periodsToForecast_deterministic;
	}

	/**
	 * Setzt die Anzahl vorherzusagender deterministischer Perioden des Projekts.
	 * 
	 * @author Annika Weis
	 * @param periodsToForecast_deterministic
	 *            Anzahl vorherzusagender Perioden (deterministisch)
	 */
	public void setPeriodsToForecast_deterministic(int periodsToForecast_deterministic) {
		if(this.getDeterministicPeriods() != null){
			if(this.getDeterministicPeriods().getPeriods() != null){
				this.periodsToForecast_deterministic = periodsToForecast_deterministic;
				logger.debug("Perioden Soll: "+periodsToForecast_deterministic+", Perioden Ist: "+this.getDeterministicPeriods().getPeriods().size());
				if(this.getDeterministicPeriods().getPeriods().size() > (periodsToForecast_deterministic)){
					int size = this.getDeterministicPeriods().getPeriods().size();
					TreeSet<Period> _periods = (TreeSet<Period>)this.deterministicPeriods.getPeriods();
					for(int i = size; i > periodsToForecast_deterministic; i--){
						logger.debug("Periode gelöscht");
						logger.debug(""+_periods.last().getYear());
						_periods.remove(_periods.last());
					}
					this.deterministicPeriods.setPeriods(_periods);
				}
			}
		}
	}

	/**
	 * Setzt die Anzahl der anzugebenden vergangenen Perioden des Projekts.
	 * 
	 * @author Marcel Rosenberger
	 * @param specifiedPastPeriods
	 *            Anzahl der anzugebenden vergangenen Perioden
	 */
	public void setSpecifiedPastPeriods(int specifiedPastPeriods) {
		this.specifiedPastPeriods = specifiedPastPeriods;
	}

	/**
	 * Gibt die Anzahl der anzugebenden vergangenen Perioden des Projekts zurück.
	 * 
	 * @author Marcel Rosenberger
	 * @return Anzahl der anzugebenden vergangenen Perioden
	 */
	public int getSpecifiedPastPeriods() {
		return specifiedPastPeriods;
	}

	/**
	 * Setzt die Anzahl der vergangenen relevanten Perioden des Projekts.
	 * 
	 * @author Christian Scherer
	 * @param relevantPastPeriods
	 *            Anzahl der vergangenen relevanten Perioden
	 */
	public void setRelevantPastPeriods(int relevantPastPeriods) {
		if(this.getStochasticPeriods() != null){
			if(this.getStochasticPeriods().getPeriods() != null){
				this.relevantPastPeriods = relevantPastPeriods;
				if(this.getStochasticPeriods().getPeriods().size() > (relevantPastPeriods + 1)){
					int size = this.getStochasticPeriods().getPeriods().size();
					TreeSet<Period> _periods = (TreeSet<Period>) this.stochasticPeriods.getPeriods();
					for(int i = size; i > (relevantPastPeriods + 1); i--){
						_periods.remove(_periods.first());
					}
					this.stochasticPeriods.setPeriods(_periods);
				}
			}
		}
	}

	/**
	 * Gibt die Anzahl der vergangenen relevanten Perioden des Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Anzahl der vergangenen relevanten Perioden
	 */
	public int getRelevantPastPeriods() {
		return relevantPastPeriods;
	}

	/**
	 * Gibt die Anzahl der Wiederholungen fuer die Zeitreihenanalyse des
	 * Projekts zurück.
	 * 
	 * @author Christian Scherer
	 * @return Anzahl der Wiederholungen fuer die Zeitreihenanalyse
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Setzt die Anzahl der Wiederholungen fuer die Zeitreihenanalyse des
	 * Projekts.
	 * 
	 * @author Christian Scherer
	 * @param iterations
	 *            Anzahl der Wiederholungen fuer die Zeitreihenanalyse
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Setzt Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 * muessen.
	 * 
	 * @author Christian Scherer
	 * @param basisYear
	 *            Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 *            muessen
	 */
	public void setBasisYear(int basisYear) {
		this.basisYear = basisYear;
	}

	/**
	 * Gibt Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 * muessen zurück.
	 * 
	 * @author Christian Scherer
	 * @return Basis-Jahr des Projekts auf das die Cashflows abgezinst werden
	 *         muessen
	 */
	public int getBasisYear() {
		return basisYear;
	}

	public List<Szenario> getScenarios() {
		return this.scenarios;
	}

	/**
	 * Gibt nur die einbezogenen Szenarios eines Projektes zurück.
	 * 
	 * @author Marcel Rosenberger
	 * 
	 * @return alle einbezogenen Szenarios
	 */
	public List<Szenario> getIncludedScenarios() {
		List<Szenario> includedScenarios = new ArrayList<Szenario>();

		for (Szenario szenario : this.scenarios){
			if(szenario.isIncludeInCalculation()){
				includedScenarios.add(szenario);
			}
		}
		return includedScenarios;
	}

	public void addScenario(Szenario scenario) {
		this.scenarios.add(scenario);

	}

	public User getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(User user) {
		createdFrom = user;		
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public int getTotalPeriods (){
		if (projectInputType.isDeterministic()==true){
			return periodsToForecast_deterministic;
		}
		return specifiedPastPeriods ;
	}

	public String getTypMethod(){

		if	(projectInputType.isDeterministic()==true){
			typ = "Deterministische Eingabe";
		}
		else
		{
			typ = "Stochastische Eingabe";
		}
		return typ;
	}

	public void setCompanyValue(double value){
		this.companyValue = value;
	}

	public double getCompanyValue(){
		return this.companyValue;
	}


}
