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

package dhbw.ka.mwi.businesshorizon2.methods.timeseries;

import java.util.TreeSet;

import org.apache.log4j.Logger;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.methods.CallbackInterface;
import dhbw.ka.mwi.businesshorizon2.methods.StochasticMethodException;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;

import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowPeriod;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;


import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;

import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.CashFlowPeriodContainer;


/**
* Diese Klasse steuert den Aufruf der Zeitreihenanalyse. Es gibt drei Szenarien
* die betrachtet werden muesen. Bei CashFlowPerioden wird die Zeitreihenanalyse
* einmal fuer den CashFlow aufgerufen und prognostiziert diesen. Die anderen
* beiden Varianten unterscheiden die BilanzPerioden auf Gesamtkostenverfahren
* und Umsatzkostenverfahren. Hierbei muessen jeweils alle Bilanzposten einzeln
* mit der Zeitreihenanalyse prognostiziert werden. Je nach Anzahld der Posten
* weist dies eine schlechtere Performance auf.
*
* @author Kai Westerholz
*
*/

public class TimeseriesCalculator extends AbstractStochasticMethod {
        private static final long serialVersionUID = 1L;
        private static final Logger logger = Logger
                        .getLogger("TimeseriesCalculator.class");
        public AbstractPeriodContainer periodsBSI;

        private StochasticResultContainer expectedValues;

        private double modellabweichung;

        @Override
        public String getName() {
                return "Zeitreihenanalyse";
        }

        @Override
        public int getOrderKey() {
                return 1;
        }


        /**
         * Diese Methode speichert pro Periode den Erwartungswert aller
         * prognostizierten Casfhlows und Fremdkapitalbeträge in einen
         * StochasticResultContainer.
         *
         * @author Marcel Rosenberger
         *
         * @param fremdkapital
         * die erwarteten Fremdkapitalbeträge
         * @param cashflows
         * die erwarteten Cashflows
         * @param project
         * das Projekt für das die Zeitreihenanalyse durchgeführt wurde
         *
         * @return StochasticResultContainer der Container, in dem die Prognosewerte
         * enthalten sind
         *
         */
        public void setExpectedValues(double[] fremdkapital, double[] cashflows,
                        Project project) throws StochasticMethodException {


                StochasticResultContainer resultContainer;

                CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();


                // wird pro Periode einmal durchlaufen und speichert jeweils den
                // Erwartungswert der Cashflows und Fremdkapitalbeträge in einen
                // Perioden-Container
                for (int i = 1; i <= cashflows.length; i++) {
                        CashFlowPeriod cfPeriod = new CashFlowPeriod(project.getBasisYear()
                                        + (i));
                        // als Cashflow der Periode wird der Erwartungswert eingesetzt
                        cfPeriod.setFreeCashFlow(cashflows[i - 1]);
                        cfPeriod.setCapitalStock(fremdkapital[i - 1]);
                        cFContainer.getPeriods().add(cfPeriod);
                }


                TreeSet<CashFlowPeriodContainer> periodContainer = new TreeSet<CashFlowPeriodContainer>();
                periodContainer.add(cFContainer);
                resultContainer = new StochasticResultContainer(periodContainer);

                this.expectedValues = resultContainer;
        }

        /**
         * Diese Methode ruft die Zeitreihenanalyse auf und speichert die
         * enthaltenen Werte anschließend in einem StochasticResultContainer.
         *
         * @author Kai Westerholz, Marcel Rosenberger, Maurizio Di Nunzio
         *
         * @param project
         * das Projekt für das die Zeitreihenanalyse durchgeführt werden
         * soll
         * @param callback
         * das CallbackInterface, das für die Kommunikation zwischen den
         * Threads zuständig ist
         * @return StochasticResultContainer der Container, in dem die Prognosewerte
         * enthalten sind
         *
         */
        @Override
        public StochasticResultContainer calculate(Project project,
                        CallbackInterface callback) throws InterruptedException,
                        ConsideredPeriodsOfPastException, VarianceNegativeException,
                        StochasticMethodException {

                TreeSet<AbstractPeriodContainer> resultPeriods = new TreeSet<AbstractPeriodContainer>();
                StochasticResultContainer resultContainer = null;

                /**
                 * Die Zeitreihenanalyse kann nur durchgefuehrt werden, wenn die Anzahl
                 * der beruecksichtigten Vergangenheitsperioden kleiner ist als die
                 * Anzahl der eingegebenen Vergangenheitsperioden
                 */
                if (project.getRelevantPastPeriods() > project.getStochasticPeriods()
                                .getPeriods().size() - 1) {
                        logger.debug("Anzahl der betrachteten Perioden der Vergangenheit ist zu groß!");
                        throw new ConsideredPeriodsOfPastException(
                                        "Die Anzahl der betrachteten Perioden der Vergangenheit muss kleiner sein als die Azahl der beobachteten Perioden.");
                }


                // Nachfolgend wird die Zeitreihenanalyse fuer CashFlowPerioden
                // ausgefuehrt



                TreeSet<CashFlowPeriod> alleperioden = (TreeSet<CashFlowPeriod>) project
                                .getStochasticPeriods().getPeriods();

                TreeSet<? super CashFlowPeriodContainer> cFResultContainer = resultPeriods;



                logger.debug("Übergebene Periodenanzahl: "
                                + project.getStochasticPeriods().getPeriods().size());
                double[] previousCashflows = new double[project.getStochasticPeriods()
                                .getPeriods().size()];
                double[] previousFremdkapital = new double[project
                                .getStochasticPeriods().getPeriods().size()];
                AnalysisTimeseries timeseries = new AnalysisTimeseries();
                int counter = 0;



                // Umwandlung der Perioden in ein Double-Arrays
                for (Period period : (TreeSet<Period>) project
                                .getStochasticPeriods().getPeriods()) {
                        previousFremdkapital[counter] = period.getCapitalStock();
                        logger.debug("Fremdkapital: " + previousFremdkapital[counter]);
                        previousCashflows[counter] = period.getFreeCashFlow();
                        logger.debug("Cashflow: " + previousCashflows[counter]);
                        counter++;
                }
                // Durchfuehrung der Zeitreihenanalyse
                double[][] resultTimeseriesBorrowedCapital = timeseries.calculate(
                                previousFremdkapital, project.getRelevantPastPeriods(),
                                project.getPeriodsToForecast(), project.getIterations(),
                                callback, true);
                double abweichungfk = timeseries.getAbweichung();
                double[][] resultTimeseries = timeseries.calculate(previousCashflows,
                                project.getRelevantPastPeriods(),
                                project.getPeriodsToForecast(), project.getIterations(),
                                callback, false);
                double abweichungcf = timeseries.getAbweichung();

                // Modellabweichung berechnen (Durchschnitt Abweichung Fremdkapital
                // und Cashflow)
                this.setModellabweichung((abweichungfk + abweichungcf) / 2);

                // berechnet die zu erwartenden Fremdkapital bzw. Cashflow-Werte
                setExpectedValues(timeseries.getErwartetesFremdkapital(),
                                timeseries.getErwarteteCashFlows(), project);
                logger.debug("Zu erwartende Cashflows und Fremdkapitalwerte berechnet.");
                // Diese Schleife wird sooft durchlaufen, wie die Zeitreihenanalyse
                // durchgeführt wurde
                for (int prognosedurchlauf = 0; prognosedurchlauf < (project
                                .getIterations() - 1); prognosedurchlauf++) {
                        /*
                         * Pro Schleifen- (und somit auch Prognose-)durchlauf wird ein neuer
                         * Cashflow-Period-Container (= eine Zeitreihe von
                         * Cashflow-Perioden) erstellt. In einer inneren Schleife werden
                         * diesem dann soviele Perioden hinzugefügt wie durch die
                         * Zeitreihenanalyse prognostiziert werden sollten. Die Menge an
                         * Cashflow-Period-Container wird anschließend in einen
                         * übergreifenden StochasticResultContainer eingefügt.
                         */

                        CashFlowPeriodContainer cFContainer = new CashFlowPeriodContainer();
                        // die innere Schleife wird sooft durchlaufen wie Perioden
                        // prognostiziert werden sollen
                        // resultTimeseries.length = Anzahl der zu prognostizierenden
                        // Perioden (Wert im ersten Array)
                        for (int periode = 0; periode < resultTimeseries[0].length; periode++) {

                                // eine neue Cashflow-Periode wird erstellt und mit dem Jahr
                                // der aktuellen Periode initialisiert
                                CashFlowPeriod cfPeriod = new CashFlowPeriod(
                                                project.getBasisYear() + (periode + 1));
                                // der Cashflow des aktuellen Prognosedurchlauf pro Periode
                                // wird gesetzt
                                cfPeriod.setFreeCashFlow(resultTimeseries[prognosedurchlauf][periode]);
                                // das Fremdkapital des aktuellen Prognosedurchlauf pro
                                // Periode wird gesetzt
                                cfPeriod.setCapitalStock(resultTimeseriesBorrowedCapital[prognosedurchlauf][periode]);
                                // die Periode mit den gesetzten Werten wird dem
                                // Cashflow-Perioden-Container hinzugefügt
                                cFContainer.getPeriods().add(cfPeriod);
                        }
                        // der Cashflow-Perioden-Container wird mit all seinen Perioden
                        // dem Cashflow-Result-Container
                        // hinzugefügt.
                        cFResultContainer.add(cFContainer);
                }
                logger.debug("Ergebnisse der Zeitreihenanalyse dem Result-Container hinzugefügt.");
                // der Cashflow-Result-Container wird in einen
                // StochasticResultContainer kopiert
                StochasticResultContainer src = new StochasticResultContainer(
                                resultPeriods);

                // bereits auskommentierter Code vom Vorjahr, Verwendung noch unklar
                // if (callback != null) {
                // callback.onComplete(src);
                // }
                return src;

        }

        @Override
        public Boolean getImplemented() {
                return true;
        }

        public StochasticResultContainer getExpectedValues() {
                return this.expectedValues;
        }

        public double getModellabweichung() {
                return modellabweichung;
        }

        public void setModellabweichung(double modellabweichung) {
                this.modellabweichung = modellabweichung;
        }


}
