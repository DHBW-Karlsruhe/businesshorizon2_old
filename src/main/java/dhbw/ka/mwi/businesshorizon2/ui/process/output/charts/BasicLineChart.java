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
package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.List;
import java.util.Map;

import org.vaadin.vaadinvisualizations.LineChart;

/**
 * Liniendiagramm zur Anzeige des Verlaufs der vergangenen bzw. vorhergesagten
 * Cashflows
 * 
 * @author Florian Stier
 * 
 */
public class BasicLineChart extends LineChart {

	private static final long serialVersionUID = 1L;

	public BasicLineChart(String xAxisLabel, List<String> lines) {

		addXAxisLabel(xAxisLabel);

		for (String line : lines) {
			addLine(line);
		}

		setOption("width", 500);
		setOption("height", 200);
		setOption("legend", "bottom");

		setOption("lineSize", 3);
		setOption("pointSize", 7);

	}

	public void addValues(Map<String, double[]> values) {
		for (Map.Entry<String, double[]> value : values.entrySet()) {
			add(value.getKey(), value.getValue());
		}
	}

}
