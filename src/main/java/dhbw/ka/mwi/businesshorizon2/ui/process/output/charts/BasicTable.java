package dhbw.ka.mwi.businesshorizon2.ui.process.output.charts;

import java.util.List;
import java.util.Map;

import org.vaadin.vaadinvisualizations.Table;

/**
 * Tabelle zur Anzeige des Eigen- sowie Fremdkapital und des Unternehmenswertes
 * im APV
 * 
 * @author Florian Stier
 * 
 */
public class BasicTable extends Table {

	private static final long serialVersionUID = 1L;

	public BasicTable(Map<String, String> columns) {
		setOption("allowHTML", true);
		setOption("width", 500);

		for (Map.Entry<String, String> column : columns.entrySet()) {
			addTableColumn(column.getKey(), column.getValue());
		}
	}

	public void addValues(List<String[]> values) {
		for (String[] value : values) {
			add(value);
		}
	}

}
