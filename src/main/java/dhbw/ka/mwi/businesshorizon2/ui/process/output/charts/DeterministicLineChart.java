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

import org.apache.log4j.Logger;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * Die DeterministicChartArea komponiert die Ausgabe der Ergebnisse des
 * deterministischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Annika Weis
 * 
 */
public class DeterministicLineChart extends GridLayout {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger("DeterministicLineChart.class");
	
	public DeterministicLineChart(String methodName, double companyValue, String linienBeschriftung, int[] xWerte, double[] ywerte) {
		super(2, 2);

		Label headline = new Label("<h2>Deterministisches Verfahren - " + methodName + "</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);
		this.addComponent(headline, 0, 0);

		//Beschriftung der Linie hinzufügen
		List<String> cvKeyColumns = new ArrayList<String>();
		cvKeyColumns.add(linienBeschriftung);

		//Werte hinzufügen
		Map<String, double[]> cvKeyValues = new LinkedHashMap<String, double[]>();
		double anzahlWerte = ywerte.length;
		for (int i = 0; i<anzahlWerte;i++){
			cvKeyValues.put("" +  xWerte[i], new double[] { ywerte[i]});
			logger.debug("DeterministicLineChart: " + ywerte[i]);
		}


		BasicLineChart cvKeyChart = new BasicLineChart("Chart", cvKeyColumns);
		cvKeyChart.addValues(cvKeyValues);

		this.addComponent(cvKeyChart, 0, 1);

		this.setHeight("600px");
		this.setWidth("1000px");

	}
	
	
	/**
	 * @author Annika Weis
	 */
	public void addElement(Label label){
		this.addComponent(label);
	}
}
