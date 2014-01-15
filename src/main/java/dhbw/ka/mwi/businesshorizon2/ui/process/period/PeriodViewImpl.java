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

package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
 * Vaadin.
 * 
 * @author Julius Hacker
 * 
 */
public class PeriodViewImpl extends VerticalLayout implements
		PeriodViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PeriodPresenter presenter;

	Panel all = new Panel();

	HorizontalSplitPanel horizontalPanel;

	private static final Logger logger = Logger
			.getLogger("PeriodViewImpl.class");

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Daniel Dengler
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Perioden"
	 * 
	 * @author Daniel Dengler
	 */
	private void generateUi() {
		this.setSizeFull();
		horizontalPanel = new HorizontalSplitPanel();
		horizontalPanel.setSplitPosition(150);
		horizontalPanel.setSizeFull();
		horizontalPanel.setLocked(true);
		this.all.addComponent(horizontalPanel);
		all.setStyleName(Reindeer.PANEL_LIGHT);
		this.addComponent(all);
	}

	@Override
	public void showView(View leftView, View rightView) {
		removeAllComponents();
		generateUi();
		horizontalPanel.setFirstComponent((Component) leftView);
		horizontalPanel.setSecondComponent((Component) rightView);
		this.setSizeFull();
		((Component) leftView).setSizeFull();
		if (rightView != null)
			((Component) rightView).setSizeFull();
		horizontalPanel.setSizeFull();
		this.all.setSizeFull();
		this.setSizeFull();
		logger.debug("Alle Komponenten neu angelegt");
	}

	@Override
	public void setSize(float max, int heightUnits) {
		logger.debug("Setting size to " + (max) + " " + heightUnits);
		this.setHeight(max, heightUnits);

	}
}
