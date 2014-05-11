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

import java.security.InvalidParameterException;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.StochasticResultContainer;

/**
 * Diese Klasse ist zur eigentlichen Ausfuehrung der Berechnungen gedacht. Dabei
 * wird fuer die Berechnung ein eigener Thread verwendet.
 * 
 * @author Christian Gahlert
 * 
 */
public class MethodRunner extends Thread {

	private AbstractStochasticMethod method;

	private CallbackInterface callback;
	private Project project;

	/**
	 * Der Konstruktor - diesem sollte die zur Berechnung zu verwendende Methode
	 * sowie die Perioden uebergeben werden. Zuletzt sollte noch das
	 * Callback-Objekt uebergeben werden.
	 * 
	 * @author Christian Gahlert
	 * @param method
	 *            Die zu verwendende Methode
	 * @param periods
	 *            Die zu berechnenden Perioden
	 * @param callback
	 *            Das Callback
	 */

	public MethodRunner(AbstractStochasticMethod method, Project project, CallbackInterface callback) {
		if (method == null || project == null || callback == null) {
			throw new InvalidParameterException("No null parameters are allowed here");
		}

		this.method = method;
		this.project = project;
		this.callback = callback;
	}

	/**
	 * Diese Methode startet die Ausfuehrung. Diese sollte nicht direkt
	 * aufgerufen werden. Stattdessen sollte die Ausfuehrung des Threads mit
	 * MethodRunner.start() gestartet werden. Anschliessend wird dann innerhalb
	 * des neuen Threads diese Methode aufgerufen.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void run(){
		try {
			StochasticResultContainer result = method.calculate(project, callback);
			callback.onComplete(result, method);
		} catch (InterruptedException e) {
			callback.onComplete(null, method);
		} catch (StochasticMethodException e) {
			callback.onComplete(null, method);
		}		
	}
}
