/*******************************************************************************
* BusinessHorizon2
*
* Copyright (C) 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
* Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
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


package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;
import dhbw.ka.mwi.businesshorizon2.ui.process.contentcontainer.ContentView;

public interface TimelineViewInterface extends ContentView {

        public void addFuturePeriod(Period period);

        public void removeFuturePeriod();

        public void addPastPeriod(Period period);

        public void removePastPeriod();

        // public void setPeriodValid(int year, boolean isValid);

        public void setPastButtonAccess(boolean usable);

        public void setFutureButtonAccess(boolean usable);
        
        //Annika Weis
        public void setPastDeleteButtonAccess(boolean usable);
        
        //Annika Weis
        public void setFutureDeleteButtonAccess(boolean usable);

        void addBasePeriod(Period period);

        public void setButtonWrong(int year, boolean isWrong);
        
    	/**
    	 * Setzt eine Fehleranzeige an das Entsprechende Feld bzw. entfernt diese
    	 * wieder je nach Parametriesierung
    	 * 
    	 * @author Christian Scherer, Annika Weis
    	 * @param setError
    	 *            true, wenn eine Fehleranzeige gezeigt werden soll und false,
    	 *            wenn die Fehleranzeige geloescht werden soll
    	 * @param component
    	 *            Identifiziert den Componenten, bei dem die Fehleranzeige
    	 *            angezeigt bzw. entfernt werden soll
    	 * @param message
    	 *            Fehlermeldung die neben dem Componenten gezeigt werden soll
    	 * 
    	 */
    	void setComponentError(boolean setError, String component, String message);
    	
    	
    	/**
    	 * Gibt eine Fehlermeldung an den Benutzer aus.
    	 * 
    	 * @author Christian Scherer, Annika Weis
    	 * @param message
    	 *            Fehlermeldung die der Methode zur Ausgabe uebergeben wird
    	 */
    	void showErrorMessage(String message);

}