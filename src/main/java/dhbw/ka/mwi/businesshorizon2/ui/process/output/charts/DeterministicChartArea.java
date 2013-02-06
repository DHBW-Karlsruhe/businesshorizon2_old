/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * Die DeterministicChartArea komponiert die Ausgabe der Ergebnisse des
 * deterministischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier
 * 
 */
public class DeterministicChartArea extends GridLayout {

	private static final long serialVersionUID = 1L;

	public DeterministicChartArea(double debitFreeCompanyValue, double taxBenefits, double companyValue,
			double capitalStock) {

		super(2, 2);

		Label headline = new Label("<h2>Deterministisches Verfahren</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);
		this.addComponent(headline, 0, 0);

		// Chart zur Aufschlüsselung des Unternehmenswert

		List<String> cvKeyColumns = new ArrayList<String>();
		cvKeyColumns.add("Unverschuldetes Unternehmen");
		cvKeyColumns.add("Steuervorteil");
		cvKeyColumns.add("Unternehmenswert");
		cvKeyColumns.add("Fremdkapital");

		Map<String, double[]> cvKeyValues = new LinkedHashMap<String, double[]>();
		cvKeyValues.put("Unverschuldetes Unternehmen und Steuervorteil", new double[] { debitFreeCompanyValue,
				taxBenefits, 0, 0 });
		cvKeyValues.put("Unternehmenswert und Fremdkapital", new double[] { 0, 0, companyValue, capitalStock });

		StackedColumnChart cvKeyChart = new StackedColumnChart("Chart", cvKeyColumns);
		cvKeyChart.addValues(cvKeyValues);

		this.addComponent(cvKeyChart, 0, 1);

		// Chart zur Kapitalstruktur

		this.setHeight("600px");
		this.setWidth("1000px");

	}

}
