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

package dhbw.ka.mwi.businesshorizon2.methods;

import java.io.Serializable;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.DeterministicResultContainer;

//Annika Weis


/**
 * Diese Klasse bezeichnet eine Berechnungsmethode wie die Zeitreihenanalyse
 * oder den Wiener-Prozess und bietet einige Basis-Funktionalitaeten.
 * 
 * @author Christian Gahlert
 * 
 */
abstract public class AbstractDeterministicMethod implements
		Comparable<AbstractDeterministicMethod>, Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Boolean selected = false;
	
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	abstract public Boolean getImplemented(); 
	/**
	 * Diese Methode gibt den Namen der jeweiligen Methode zurueck, der dann
	 * auch fuer den Nutzer lesbar angezeigt wird.
	 * 
	 * @author Christian Gahlert
	 * @return Den Namen der Berechnungsmethode
	 */
	abstract public String getName();

	/**
	 * Die Methoden werden mit Hilfe dieses Keys sortiert. Dadurch ist es
	 * moeglich die Reihenfolge der fuer den Nutzer sichtbaren
	 * Berechnungsmethoden zu aendern.
	 * 
	 * @author Christian Gahlert
	 * @return Sortierungs-Zahl
	 */
	abstract public int getOrderKey();

	/**
	 * Mit dieser Methode wird die eigentliche Berechnung gestartet. Falls es
	 * eine aufwaendigere Berechnung ist, koennen innerhalb dieser Methode
	 * weitere Threads gestartet werden. Prinzipiell ist dies aber nicht
	 * notwendig, da der Aufruf dieser Methode ohnehin in einem von Vaadin
	 * getrennten Thread stattfindet.
	 * 
	 * Dennoch sollte mit Thread.getCurrentThread().isInterrupted() in
	 * regelmaessigen Abstaenden ueberprueft werden, ob der Berechnungs-Thread
	 * (z.B. durch Klick auf "Abbrechen") unterbrochen wurde.
	 * 
	 * Darueberhinaus sollte auch in regelmaessigen Abstaenden die
	 * Callback-Methode onProgressChange() aufgerufen werden, um die Status-Bar
	 * zu aktualisieren. Die onComplete()-Methode wird von der aufrufenden
	 * Methode ausgefuehrt und sollte daher an dieser Stelle nicht zum Einsatz
	 * kommen.
	 * 
	 * @author Christian Gahlert, Kai Westerholz
	 * @param periods
	 *            Die bisher vorhanden Perioden
	 * @param callback
	 *            Das Callback-Objekt
	 * @return Das Result-Objekt dieser Berechnung
	 * @throws InterruptedException
	 */
	abstract public DeterministicResultContainer calculate(Project project,
			CallbackInterface callback) throws InterruptedException,
			DeterministicMethodException;

	/**
	 * Diese Methode wird zum Sortieren der Methoden (fuer die Anzeige beim
	 * Nutzer) verwendet.
	 * 
	 * @author Christian Gahlert
	 * @see getOrderKey()
	 */
	@Override
	public int compareTo(AbstractDeterministicMethod o) {
		return this.getOrderKey() - o.getOrderKey();
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
