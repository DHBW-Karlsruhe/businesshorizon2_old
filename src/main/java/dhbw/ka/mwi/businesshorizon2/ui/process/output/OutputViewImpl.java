
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

package dhbw.ka.mwi.businesshorizon2.ui.process.output;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.ProgressIndicator;
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

	private ProgressIndicator progressIndicator;

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

	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Ausgabe"
	 * 
	 * @author Florian Stier
	 */
	private void generateUi() {

		progressIndicator = new ProgressIndicator();
		progressIndicator.setIndeterminate(true);
		progressIndicator.setEnabled(true);

		outputArea = new VerticalLayout();

		addComponent(progressIndicator);
		addComponent(outputArea);

	}

	@Override

	public void showOutputView() {
		this.removeAllComponents();
		generateUi();
		
	}


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
		if (progress == 1) {
			progressIndicator.setEnabled(false);
		} else {
			progressIndicator.setEnabled(true);
		}

	}

}
