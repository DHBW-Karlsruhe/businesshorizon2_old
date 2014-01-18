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


package dhbw.ka.mwi.businesshorizon2.models;

import java.io.Serializable;

public enum InputType implements Serializable {
	DIRECT("Direkte Eingabe"),DIRECTCALCULATION("Direkte Berechnung des Cashflows"),INDIRECTCALCULATION("Indirekte Berechnung des Cashflows");
	
	private String caption;
	

	private InputType(String caption){
		this.caption = caption;
	}
	
	public String getCaption() {
		return caption;
	}
	
	@Override
	public String toString(){
		
		return this.getCaption();
	}

}
