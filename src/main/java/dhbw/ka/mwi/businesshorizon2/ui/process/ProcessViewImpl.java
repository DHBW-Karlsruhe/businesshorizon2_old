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
package dhbw.ka.mwi.businesshorizon2.ui.process;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung der ProcessView (dem Prozess-Fenster).
 * Sie rendert die Ansicht der Navigation als obere View und den entsprechenden
 * Masken als untere View
 * 
 * @author Christian Gahlert, Julius Hacker, Mirko Göpfrich
 *
 */
public class ProcessViewImpl extends Window implements ProcessViewInterface {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ProcessViewImpl.class");
	
	@Autowired
	private ProcessPresenter presenter;
	
	private VerticalSplitPanel verticalSplitPanel;

	private HorizontalSplitPanel horizontalSplitPanel;

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
		logger.debug("Initialisierung beendet");
	}
	
	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu "Business Horizon 2.1" und
	 * erstellt das zugehoerige Vertikale Splitpanel, in dem oben die Navigation und
	 * unten die anzuzeigende Maske eingefuegt werden koennen.
	 * 
	 * @author Julius Hacker, Mirko Göpfrich
	 */
	private void generateUi() {
		setCaption("Business Horizon 2.1");
		logger.debug("Ueberschrift fuer Browser erstellt");
		
		//Teilt das Fenster vertikal in zwei Bereiche auf und erstellt eine horizontale Trennlinie (nicht verstellbar).
		verticalSplitPanel = new VerticalSplitPanel();
		verticalSplitPanel.setSplitPosition(100, Sizeable.UNITS_PIXELS);
		verticalSplitPanel.setLocked(true);
		verticalSplitPanel.setStyleName("small");
		logger.debug("Neues Vertikales SplitPanel erstellt");

		// Setzt das vertikale Splitpanel (äußeres Panel) inkl innere Panels als Inhalt für das Fenster.
		setContent(verticalSplitPanel);
		logger.debug("Vertikales SplitPanel mit allen Elementen an das Hauptfenster übergeben");
		
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
		verticalSplitPanel.setFirstComponent((Component) topView);
		verticalSplitPanel.setSecondComponent((Component) bottomView);
		
	}
	
	/**
	 * Diese Methode setzt die obere und die zwei unteren Views in der Prozessansicht.
	 * 
	 * @author: Mirko Göpfrich
	 */
	
	@Override
	public void showView(View topView, View bottomLeftView, View bottomRigthView) {
		verticalSplitPanel.setFirstComponent((Component) topView);
		
		//Teilt das Panel horizontal un zwei gleiche Bereiche auf und ertstellt eine vertiakel Trennlinie (nicht verstellbar.)
		horizontalSplitPanel = new HorizontalSplitPanel();
		horizontalSplitPanel.setSizeFull();
		horizontalSplitPanel.setSplitPosition(50, UNITS_PERCENTAGE);
		horizontalSplitPanel.setLocked(true);
		horizontalSplitPanel.setStyleName("small");
	
		horizontalSplitPanel.setFirstComponent((Component) bottomLeftView);
		horizontalSplitPanel.setSecondComponent((Component) bottomRigthView);
		
		//fügt dem unteren vertikalen Panel ein horizontales SplitPanel hinzu.
		verticalSplitPanel.setSecondComponent(horizontalSplitPanel);
		logger.debug("Horizontales SplitPanel für Prozessschritte und Infos erstellt und an das untere vertikale Panel übergeben");
	
	}

}

