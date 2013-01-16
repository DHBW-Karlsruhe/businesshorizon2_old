package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * Die Deterministic komponiert die Ausgabe der Ergebnisse des deterministischen
 * Verfahrens. Es können mehrere Diagramme sowie Labels hinzugefügt und im
 * Gridlayout angeordnet werden.
 * 
 * @author Florian Stier
 * 
 */
public class DeterministicChartArea extends GridLayout {

	private static final long serialVersionUID = 1L;

	public DeterministicChartArea() {

		super(2, 2);

		Label headline = new Label("<h2>Deterministisches Verfahren</h2>");
		headline.setContentMode(Label.CONTENT_XHTML);

		List<String> columns = new ArrayList<String>();
		columns.add("Unverschuldetes Unternehmen");
		columns.add("Steuervorteil");

		Map<String, double[]> values = new LinkedHashMap<String, double[]>();
		values.put("Unverschuldetes Unternehmen und Steuervorteil", new double[] { 100, 200 });

		StackedColumnChart chart = new StackedColumnChart("Chart", columns);
		chart.addValues(values);

		this.addComponent(headline, 0, 0);
		this.addComponent(chart, 0, 1);
		this.setHeight("600px");
		this.setWidth("1000px");

	}

}
