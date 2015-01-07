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
import dhbw.ka.mwi.businesshorizon2.models.Period.UmsatzkostenVerfahrenCashflowPeriod;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.AbstractPeriodContainer;
import dhbw.ka.mwi.businesshorizon2.models.PeriodContainer.UmsatzkostenVerfahrenCashflowPeriodContainer;

/**
 * Diese Klasse stellt den jUnit-Test der im Klassenname aufgeführten Methode
 * dar. Entspricht letztendlich dem Testfall "TestCashFlowCalculatorUKVDet";
 * zusätzlicher Unit-Test aber sinnvoll, da CashFlowCalculator unterschiedliche
 * Einstiegspunkte hat je nach dem ob Deterministische oder Stochastische Berechnung verwendet wird.
 * 
 * @author Marcel Rosenberger
 * 
 */

public class TestCashFlowCalculatorUKVSto extends TestCase {
	private static final Logger logger = Logger
			.getLogger("TestCashFlowCalculatorUKVSto.class");

	@Test
	public void testCashFlowCalculatorUKVSto() {

		// Eingangswerte bereitstellen

		UmsatzkostenVerfahrenCashflowPeriod ukvcfp = new UmsatzkostenVerfahrenCashflowPeriod(
				2013);

		ukvcfp.setUmsatzerlöse(142.80);
		ukvcfp.setHerstellungskosten(113.32);
		ukvcfp.setVertriebskosten(12.87);
		ukvcfp.setForschungskosten(5.72);
		ukvcfp.setVerwaltungskosten(9.95);
		ukvcfp.setSonstigeraufwand(0);
		ukvcfp.setSonstigerertrag(0);
		ukvcfp.setWertpapiererträge(0.5);
		ukvcfp.setZinsenundaufwendungen(0.35);
		ukvcfp.setAußerordentlicheaufwände(0);
		ukvcfp.setAußerordentlicheaufwände(0);
		ukvcfp.setAbschreibungen(7.15);
		ukvcfp.setPensionsrückstellungen(1.93);
		

		TreeSet<AbstractPeriodContainer> periodContainer = new TreeSet<AbstractPeriodContainer>();
		AbstractPeriodContainer apc = new UmsatzkostenVerfahrenCashflowPeriodContainer();
		apc.addPeriod(ukvcfp);
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
		double ergebnisVorgabe = 9.679500000000013;

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
