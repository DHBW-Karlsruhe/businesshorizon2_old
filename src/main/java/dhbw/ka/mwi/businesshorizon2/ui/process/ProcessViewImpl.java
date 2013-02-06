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


package dhbw.ka.mwi.businesshorizon2.ui.process;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der ProcessView (dem Prozess-Fenster).
 * Sie rendert die Ansicht der Navigation als obere View und den entsprechenden
 * Masken als untere View
 * 
 * @author Christian Gahlert, Julius Hacker
 *
 */
public class ProcessViewImpl extends Window implements ProcessViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ProcessPresenter presenter;
	
	private VerticalSplitPanel splitpanel;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}
	
	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu "Business Horizon 2" und
	 * erstellt das zugehoerige Vertikale Splitpanel, in dem oben die Navigation und
	 * unten die anzuzeigende Maske eingefuegt werden koennen.
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		setCaption("Business Horizon 2"); 
		
		splitpanel = new VerticalSplitPanel();
		splitpanel.setSplitPosition(75, Sizeable.UNITS_PIXELS);
		
		setContent(splitpanel);
	}
	
	/**
	 * Diese Methode setzt die obere und untere View in der Prozessansicht.
	 * 
	 * @param topView Die obere View-Komponente, gedacht fuer die Navigation
	 * @param bottomView Die untere View-Komponente, gedacht fuer die anzuzeigenden Masken.
	 * @author Julius Hacker
	 */
	@Override
	public void showView(View topView, View bottomView) {
		splitpanel.setFirstComponent((Component) topView);
		splitpanel.setSecondComponent((Component) bottomView);
		
	}
}
