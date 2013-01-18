package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.DeterministicChartArea;
import dhbw.ka.mwi.businesshorizon2.ui.process.output.charts.StochasticChartArea;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Ausgabe" in
 * Vaadin.
 * 
 * @author Florian Stier
 * 
 */
public class OutputViewImpl extends VerticalLayout implements OutputViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private OutputPresenter presenter;

	private VerticalLayout outputArea;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Florian Stier
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Ausgabe"
	 * 
	 * @author Florian Stier
	 */
	private void generateUi() {

		outputArea = new VerticalLayout();
		addComponent(outputArea);

	}

	@Override
	public void addStochasticChartArea(StochasticChartArea chartArea) {
		outputArea.addComponent(chartArea);
	}

	@Override
	public void addDeterministicChartArea(DeterministicChartArea chartArea) {
		outputArea.addComponent(chartArea);
	}

	@Override
	public void showErrorMessge(String message) {
		getWindow().showNotification((String) "Berechnung fehlgeschlagen", message, Notification.TYPE_ERROR_MESSAGE);

	}

	@Override
	public void changeProgress(float progress) {
		// TODO Auto-generated method stub

	}

}
