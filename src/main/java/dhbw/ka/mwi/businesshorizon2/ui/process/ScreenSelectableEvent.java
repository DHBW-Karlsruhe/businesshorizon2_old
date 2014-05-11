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

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.ui.process.navigation.NavigationSteps;

/**
 * Der Event wird von den einzelnen Prozess-Screens gefeuert, um zu siganlisieren,
 * dass sie ab sofort aufrufbar sind. Initial wird hierbei angenommen, dass der Screen
 * nicht aufrufbar ist.
 * 
 * Dies ist insbesondere fuer die Navigation wichtig, da dort die Buttons entsprechend
 * aktiviert bzw. deaktiviert werden muessen. Hierzu wird dort dieses Event abgefangen
 * und der zum Prozesschritt gehoerige Button aktiviert. Hierrueber wird es ermoeglicht,
 * dass zu Beginn die Navigationsbuttons deaktiviert sind und so schrittweise aktiviert
 * werden, dass einerseits ein gefuehrter Prozess entsteht, der Nutzer aber nach dem
 * Ausfuellen der Masken wieder wahlweise zurueckspringen kann, um Korrekturen
 * vorzunehmen.
 * 
 * 
 * @author Julius Hacker
 */
public class ScreenSelectableEvent extends Event {
	private static final long serialVersionUID = 1L;

	private NavigationSteps navigationStep;
	
	private boolean isSelectable;
	
	public ScreenSelectableEvent(NavigationSteps navigationStep, boolean isSelectable) {
		this.setNavigationStep(navigationStep);
		this.setSelectable(isSelectable);
	}

	public NavigationSteps getNavigationStep() {
		return navigationStep;
	}

	public void setNavigationStep(NavigationSteps navigationStep) {
		this.navigationStep = navigationStep;
	}

	public boolean isSelectable() {
		return isSelectable;
	}

	public void setSelectable(boolean isSelectable) {
		this.isSelectable = isSelectable;
	}
	
	
}
