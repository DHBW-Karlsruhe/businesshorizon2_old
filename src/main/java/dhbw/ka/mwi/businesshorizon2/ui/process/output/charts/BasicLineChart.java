package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.List;
import java.util.Map;

import org.vaadin.vaadinvisualizations.LineChart;

public class BasicLineChart extends LineChart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicLineChart(String xAxisLabel, List<String> lines) {

		addXAxisLabel(xAxisLabel);

		for (String line : lines) {
			addLine(line);
		}

		setOption("width", 500);
		// setOption("height", 200);
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
