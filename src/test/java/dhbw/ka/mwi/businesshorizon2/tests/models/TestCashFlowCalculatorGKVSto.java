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

package dhbw.ka.mwi.businesshorizon2.tests.models;

import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;
import dhbw.ka.mwi.businesshorizon2.models.Szenario;
import dhbw.ka.mwi.businesshorizon2.models.Period.CashFlowCalculator;
import dhbw.ka.mwi.businesshorizon2.models.Period.GesamtkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.GesamtkostenVerfahrenCashflowPeriodContainer;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode
 * dar. Entspricht letztendlich dem Testfall "TestCashFlowCalculatorGKVDet";
 * zusätzlicher Unit-Test aber sinnvoll, da CashFlowCalculator unterschiedliche
 * Einstiegspunkte hat je nach dem ob Deterministische oder Stochastische Berechnung verwendet wird.
 * 
 * @author Marcel Rosenberger
 * 
 */

public class TestCashFlowCalculatorGKVSto extends TestCase {
	private static final Logger logger = Logger
			.getLogger("TestCashFlowCalculatorGKVSto.class");

	@Test
	public void testCashFlowCalculatorGKVSto() {

		// Eingangswerte bereitstellen

		GesamtkostenVerfahrenCashflowPeriod gkvcfp = new GesamtkostenVerfahrenCashflowPeriod(
				2013);

		gkvcfp.setUmsatzerlöse(142.80);
		gkvcfp.setBestandserhöhung(0);
		gkvcfp.setBestandsverminderung(20.68);
		gkvcfp.setMaterialaufwand(25.50);
		gkvcfp.setLöhne(38.67);
		gkvcfp.setEinstellungskosten(2.76);
		gkvcfp.setPensionsrückstellungen(1.93);
		gkvcfp.setSonstigepersonalkosten(16.24);
		gkvcfp.setAbschreibungen(7.15);
		gkvcfp.setSonstigeraufwand(28.92);
		gkvcfp.setSonstigerertrag(0);
		gkvcfp.setWertpapiererträge(0.5);
		gkvcfp.setZinsenundaufwendungen(0.35);
		gkvcfp.setAußerordentlicheerträge(0);
		gkvcfp.setAußerordentlicheaufwände(0);

		TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
		AbstractPeriodContainer apc = new GesamtkostenVerfahrenCashflowPeriodContainer();
		apc.addPeriod(gkvcfp);
		periodContainer.add(apc);
		StochasticResultContainer src = new StochasticResultContainer(
				periodContainer);

		double rateReturnEquity = 0.0;
		double rateReturnCapitalStock = 0.0;
		double businessTax = 45.0;
		double corporateAndSolitaryTax = 0.0;
		boolean includeInCalculation = true;

		Szenario szenario = new Szenario(rateReturnEquity,
				rateReturnCapitalStock, businessTax, corporateAndSolitaryTax,
				includeInCalculation);

		// Erwartete Ausgabe CashFlow
		double ergebnisVorgabe = 9.685000000000006;

		CashFlowCalculator.calculateCashflows(src, szenario);

		double ergebnis = src.getPeriodContainers().first().getPeriods()
				.first().getFreeCashFlow();

		logger.debug(ergebnisVorgabe);
		logger.debug(ergebnis);

		double delta = 0;
		assertEquals(ergebnisVorgabe, ergebnis, delta);
		logger.debug("Delta: " + delta);

	}

}
