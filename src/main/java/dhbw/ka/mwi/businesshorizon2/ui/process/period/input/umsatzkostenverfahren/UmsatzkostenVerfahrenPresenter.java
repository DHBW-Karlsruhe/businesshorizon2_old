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

package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.umsatzkostenverfahren;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;

import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.AbstractInputPresenter;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.ShowUKVEvent;

/**
 * Der Presenter fuer die Maske des Prozessschrittes zur Eingabe der Perioden.
 * 
 * @author Marcel Rosenberger
 * 
 */

public class UmsatzkostenVerfahrenPresenter extends
		AbstractInputPresenter<UmsatzkostenVerfahrenViewInterface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	EventBus eventBus;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Marcel Rosenberger
	 */

	public void init() {
		eventBus.addHandler(this);
		shownProperties = new String[] { "capitalStock", "umsatzerlöse",
				"herstellungskosten", "vertriebskosten", "forschungskosten",
				"verwaltungskosten", "sonstigerertrag", "sonstigeraufwand",
				"wertpapiererträge", "zinsenundaufwendungen",
				"außerordentlicheerträge", "außerordentlicheaufwände",
				"abschreibungen", "pensionsrückstellungen" };
		germanNamesProperties = new String[] { "Fremdkapital",
				"Umsatzerl\u00f6se", "Herstellungskosten des Umsatzes",
				"Vertriebskosten", "Kosten F und E", "Verwaltungskosten",
				"Sonstiger Ertrag", "Sonstiger Aufwand",
				"Ertr\u00e4ge aus Wertpapieren",
				"Zinsen und \u00e4hnliche Aufwendungen",
				"Außerordentliche Ertr\u00e4ge",
				"Außerordentliche Aufwendungen", "Abschreibungen",
				"Pensionsr\u00fcckstellungen" };
	}

	/**
	 * Faengt das ShowEvent ab und sorgt dafuer das die View die benoetigten
	 * Eingabefelder erstellt und mit den bisherigen Daten befuellt.
	 * <p>
	 * Hierzu wird die Periode aus dem Event genommen und auf ihre Propertys mit
	 * vorhandenen Gettern&Settern geprueft. Die gefundenen Propertys werden als
	 * Eingabefelder zur verfuegung gestellt.
	 * <p>
	 * Wichtig ist das Stringarray "shownProperties". Dieses enthaelt die Namen
	 * der anzuzeigenden Felder.
	 * 
	 * @param event
	 */

	@EventHandler
	public void onShowEvent(ShowUKVEvent event) {
		processEvent(event);
	}

}
