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

package dhbw.ka.mwi.businesshorizon2.ui.process.period.input.gesamtkostenverfahren;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.AbstractInputView;

/**
* Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
* Vaadin.
*
* @author Marcel Rosenberger
*
*/
public class GesamtkostenVerfahrenViewImpl extends AbstractInputView implements GesamtkostenVerfahrenViewInterface {
        private static final long serialVersionUID = 1L;

        @Autowired
        private GesamtkostenVerfahrenPresenter presenterTemp;
        

        /**
         * Dies ist der Konstruktor, der von Spring nach der Initialierung der
         * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
         * einen EventHandler.
         *
         * @author Marcel Rosenberger
         */

        @PostConstruct
        public void init() {
                logger = Logger.getLogger(GesamtkostenVerfahrenViewImpl.class);
                presenter = presenterTemp;
                presenter.setView(this);
                logger.debug("Umsatz presenter set");
                
        }
}

