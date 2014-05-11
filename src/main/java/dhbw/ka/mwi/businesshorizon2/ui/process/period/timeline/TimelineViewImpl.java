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

package dhbw.ka.mwi.businesshorizon2.ui.process.period.timeline;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
* Diese Klasse implementiert die GUI fuer den PeriodenZeitstrahl in dem
* Prozessschritt "Perioden"
*
* @author Daniel Dengler
*
*/
public class TimelineViewImpl extends VerticalLayout implements
                TimelineViewInterface {
        private static final long serialVersionUID = 1L;

        @Autowired
        private TimelinePresenter presenter;
        
        GridLayout layout = new GridLayout(2, 1);

        NativeButton delPast, delFuture;

        Button past, future;
        
        Panel p = new Panel();

        PeriodButton pB;

        private static final Logger logger = Logger.getLogger(TimelineViewImpl.class);

        /**
         * Dies ist der Konstruktor, der von Spring nach der Initialierung der
         * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
         * und initialisiert die View-Komponenten.
         *
         * @author Daniel Dengler
         */
        @PostConstruct
        public void init() {
                presenter.setView(this);
                generateUi();
        }

        /**
         * Erstelle das GUI
         *
         * @author Daniel Dengler
         */
        private void generateUi() {
                p.setScrollable(true);
                p.setStyleName("borderless light");
                delPast = new NativeButton("X", new Button.ClickListener() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void buttonClick(ClickEvent event) {
                                layout.getComponent(0, 0);
                                presenter.removeLastPastPeriod(((PeriodButton) layout
                                                .getComponent(0, 0)).getPeriod());
                        }
                });
                delFuture = new NativeButton("X", new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                presenter.removeLastFuturePeriod(((PeriodButton) layout
                                                .getComponent(0, layout.getRows() - 1)).getPeriod());
                        }
                });

                past = new Button("+", new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                presenter.addPastPeriod();
                        }
                });
                this.p.addComponent(past);
                this.p.addComponent(layout);
                future = new Button("+", new Button.ClickListener() {

                        @Override
                        public void buttonClick(ClickEvent event) {
                                presenter.addFuturePeriod();
                        }
                });

                this.p.addComponent(future);
                this.addComponent(p);
        }

	/**
	 * Entfernt die letzte zukuenftige Periode und verschiebt den entferne Knopf
	 */
	@Override
	public void removeFuturePeriod() {
		layout.removeRow(layout.getRows() - 1);
		try {
			if (((PeriodButton) layout.getComponent(0, layout.getRows() - 1))
					.getCaption().startsWith("Basis")) {
			} else
				layout.addComponent(delFuture, 1, layout.getRows() - 1);			
		} catch(Exception e){
		}
	}


        /**
         * Entfernt die letzte vergangene Periode und verschiebt den entferne Knopf
         */
        @Override
        public void removePastPeriod() {
                layout.removeRow(0);
                try {
                        logger.debug("removePastPeriod: " + layout.getComponent(0, 0).getCaption());
                        if (layout.getComponent(0, 0).getCaption().startsWith("Basis")) {
                                
                        } else
                                layout.addComponent(delPast, 1, 0);                        
                } catch (Exception e){
                        
                }
        }

        /*
         * @Override public void setPeriodValid(int year, boolean isValid) { // TODO
         * Auto-generated method stub
         *
         * }
         */
        @Override
        public void setPastButtonAccess(boolean usable) {
                past.setEnabled(usable);

        }

        @Override
        public void setFutureButtonAccess(boolean usable) {
                future.setEnabled(usable);
        }

        /*
         * Annika Weis
         */
        @Override
        public void setPastDeleteButtonAccess(boolean usable) {
                delPast.setEnabled(usable);

        }

        /*
         * Annika Weis
         */
        @Override
        public void setFutureDeleteButtonAccess(boolean usable) {
                delFuture.setEnabled(usable);
        }
        
        
        @Override
        public void addBasePeriod(Period period) {

                layout.removeComponent(pB);
                pB = new PeriodButton("Basisjahr: " + period.getYear(),
                                new Button.ClickListener() {

                                        @Override
                                        public void buttonClick(ClickEvent event) {
                                                // TODO Auto-generated method stub
                                                presenter.periodClicked(((PeriodButton) event
                                                                .getButton()).getPeriod());

                                        }
                                });
                pB.setPeriod(period);

                layout.addComponent(pB);

        }

        @Override
        public void addFuturePeriod(Period period) {
                PeriodButton pB = new PeriodButton("" + period.getYear(),
                                new Button.ClickListener() {

                                        @Override
                                        public void buttonClick(ClickEvent event) {
                                                // TODO Auto-generated method stub
                                                presenter.periodClicked(((PeriodButton) event
                                                                .getButton()).getPeriod());

                                        }
                                });
                pB.setPeriod(period);
                layout.setRows(layout.getRows() + 1);
                layout.addComponent(pB, 0, layout.getRows() - 1);
                layout.removeComponent(delFuture);
                layout.addComponent(delFuture, 1, layout.getRows() - 1);

        }


	@Override
	public void addPastPeriod(Period period) {
		logger.debug("Button past angelegt");
		// TODO Auto-generated method stub
		PeriodButton pB = new PeriodButton("" + period.getYear(),
				new Button.ClickListener() {

                                        @Override
                                        public void buttonClick(ClickEvent event) {
                                                // TODO Auto-generated method stub
                                                presenter.periodClicked(((PeriodButton) event
                                                                .getButton()).getPeriod());

                                        }
                                });
                pB.setPeriod(period);
                layout.insertRow(0);
                layout.addComponent(pB, 0, 0);
                layout.removeComponent(delPast);
                layout.addComponent(delPast, 1, 0);

        }

        @Override
        public void setButtonWrong(int year, boolean isWrong) {
                Iterator<Component> it = layout.getComponentIterator();
                while(it.hasNext()){
                        Component c = it.next();
                        if(c instanceof PeriodButton){
                                PeriodButton pb = (PeriodButton) c;
                                if(pb.getPeriod().getYear() == year){
                                        if(isWrong)
                                        pb.setComponentError(new UserError("Weitere Eingaben erwartet"));
                                        else
                                                pb.setComponentError(null);
                                }
                        }
                }
        }
}

