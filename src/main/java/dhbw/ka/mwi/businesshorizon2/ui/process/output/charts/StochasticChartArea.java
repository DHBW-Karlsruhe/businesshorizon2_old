package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * Die StochasticChartArea komponiert die Ausgabe der Ergebnisse des
 * stochastischen Verfahrens. Es können mehrere Diagramme sowie Labels
 * hinzugefügt und im Gridlayout angeordnet werden.
 * 
 * @author Florian Stier
 * 
 */
public class StochasticChartArea extends GridLayout {

	private static final long serialVersionUID = 1L;

	public StochasticChartArea() {

		super(3, 2);

		Label headline = new Label("<h2>Stochastisches Verfahren</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);

		List<String> columns = new ArrayList<String>();
		columns.add("Unternehmenswert");

		BasicColumnChart chart = new BasicColumnChart("xAxis", columns);

		Map<String, double[]> values = new LinkedHashMap<String, double[]>();

		values.put("1400", new double[] { 50 });
		values.put("1600", new double[] { 60 });
		values.put("1800", new double[] { 80 });
		values.put("2000", new double[] { 100 });
		values.put("2200", new double[] { 80 });
		values.put("2400", new double[] { 60 });
		values.put("2600", new double[] { 50 });

		chart.addValues(values);

		List<String> lines = new ArrayList<String>();
		lines.add("Verlauf der Cashflows");

		BasicLineChart chart2 = new BasicLineChart("xAxis", lines);

		Map<String, double[]> values2 = new LinkedHashMap<String, double[]>();

		values2.put("2008", new double[] { 50 });
		values2.put("2009", new double[] { 60 });
		values2.put("2010", new double[] { 80 });
		values2.put("2011", new double[] { 100 });
		values2.put("2012", new double[] { 80 });
		values2.put("2013", new double[] { 60 });
		values2.put("2014", new double[] { 50 });

		chart2.addValues(values2);

		Map<String, String> tColumns = new LinkedHashMap<String, String>();
		tColumns.put("", "string");
		tColumns.put("t=0", "number");
		tColumns.put("t=1", "number");
		tColumns.put("t=2", "number");
		tColumns.put("t=3", "number");
		tColumns.put("t=4", "number");
		tColumns.put("t=5", "number");
		tColumns.put("t=6", "number");

		BasicTable table = new BasicTable(tColumns);

		List<String[]> tValues = new ArrayList<String[]>();
		tValues.add(new String[] { "Gesamtkapital", "2000", "2000", "2000", "2000", "2000", "2000", "2000" });
		tValues.add(new String[] { "- Fremdkapital", "1000", "1000", "1000", "1000", "1000", "1000", "1000" });
		tValues.add(new String[] { "Unternehmenswert", "1000", "1000", "1000", "1000", "1000", "1000", "1000" });

		table.addValues(tValues);

		this.addComponent(headline, 0, 0);
		this.addComponent(chart, 0, 1);
		this.addComponent(table, 1, 1);
		this.setHeight("600px");
		this.setWidth("2000px");
	}
}
