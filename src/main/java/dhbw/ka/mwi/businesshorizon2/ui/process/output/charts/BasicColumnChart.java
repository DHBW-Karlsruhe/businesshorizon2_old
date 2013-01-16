package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.List;
import java.util.Map;

import org.vaadin.vaadinvisualizations.ColumnChart;

/**
 * Balkendiagramm zur Anzeige der Häufigkeitsverteilung der Unternehmenswerte im
 * stochastischen Verfahren
 * 
 * @author Florian Stier
 * 
 */
public class BasicColumnChart extends ColumnChart {

	private static final long serialVersionUID = 1L;

	public BasicColumnChart(String xAxisLabel, List<String> columns) {

		addXAxisLabel(xAxisLabel);

		for (String column : columns) {
			addColumn(column);
		}

		// setOption("width", 500);
		// setOption("height", 200);
		setOption("legend", "bottom");

	}

	public void addValues(Map<String, double[]> values) {

		for (Map.Entry<String, double[]> value : values.entrySet()) {
			add(value.getKey(), value.getValue());
		}

	}

}
