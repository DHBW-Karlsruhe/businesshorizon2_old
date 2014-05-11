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
package dhbw.ka.mwi.businesshorizon2.ui.process.navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * Dieses Enum bildet die verfuegbaren Prozessschritte ab. Sie stellt
 * hierzu passende Namen sowie Buttonbeschriftungen bereit.
 * 
 * @author Julius Hacker
 */
public enum NavigationSteps {
	METHOD("1. Methoden", 1), PARAMETER("2. Parameter", 2), PERIOD("3. Perioden", 3), SCENARIO("4. Szenarien", 4), OUTPUT("5. Ausgabe", 5);
	
	private String caption;
	private Integer number;
	
	 private static final Map<Integer, NavigationSteps> lookup = new HashMap<Integer, NavigationSteps>();
     static {
         for (NavigationSteps step : NavigationSteps.values())
             lookup.put(step.getNumber(), step);
     }
	
	private NavigationSteps(String caption, int number) {
		this.caption = caption;
		this.number = number;
	}
	
	/**
	 * Gibt die Buttonueberschrift des Prozessschrittes zurueck.
	 * 
	 * @return String mit der Buttonueberschrift des Prozessschrittes.
	 */
	public String getCaption() {
		return this.caption;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public static NavigationSteps getByNumber(int number) {
		return lookup.get(new Integer(number));
	}
	
	public static int getStepCount() {
		return lookup.size();
	}
}
