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

package dhbw.ka.mwi.businesshorizon2.ui.periodscreen.gesamtkostenverfahren;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.AbstractInputView;

/**
* Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
* Vaadin.
*
* @author Marcel Rosenberger
*
*/
public class GesamtkostenVerfahrenViewImpl extends VerticalLayout implements GesamtkostenVerfahrenViewInterface {
        private static final long serialVersionUID = 1L;

        @Autowired
        private GesamtkostenVerfahrenPresenter presenter;

		private Project project;

		private Label headerLabel;

		private Label gap;

		private Table inputTable;

		private Label expandingGap;

		private Table capitalStockInput;

		private Label gap2;

		private Label headerLabel2;

		private Label gap3;
        
        private static final Logger logger = Logger.getLogger("GesamtkostenVerfahrenViewImpl.class");
        

        /**
         * Dies ist der Konstruktor, der von Spring nach der Initialierung der
         * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
         * einen EventHandler.
         *
         * @author Marcel Rosenberger
         */

        @PostConstruct
        public void init() {
        	setHeight(Sizeable.SIZE_UNDEFINED, 0);
                presenter.setView(this);
                logger.debug("Gesamt presenter set");
                generateUi();
                
        }


		private void generateUi() {
			logger.debug("GenerateUi aufgerufen");
			headerLabel = new Label("Eingabe der Werte zur Cash-Flow-Berechnung (in EUR)");
			headerLabel2 = new Label("Eingabe des Fremdkapitals (in EUR)");
			gap = new Label();
			inputTable = new Table();
			gap2 = new Label();
			gap3 = new Label();
			capitalStockInput = new Table();
			expandingGap = new Label();

			headerLabel.setStyleName("periodHeaderLabel");
			headerLabel2.setStyleName("periodHeaderLabel");
			gap.setHeight("15px");
			gap2.setHeight("15px");
			gap3.setHeight("15px");
			inputTable.setWidth(100, UNITS_PERCENTAGE);
			inputTable.setStyleName("fcfTable");
			inputTable.setPageLength(15);
			capitalStockInput.setWidth(100, UNITS_PERCENTAGE);
			capitalStockInput.setStyleName("fcfTable");
			capitalStockInput.setPageLength(1);
			expandingGap.setHeight(100, UNITS_PERCENTAGE);

			addComponent(headerLabel);
			addComponent(gap);
			addComponent(inputTable);
			addComponent(gap2);
			addComponent(headerLabel2);
			addComponent(gap3);
			addComponent(capitalStockInput);
//			addComponent(expandingGap);

//			setExpandRatio(expandingGap, 1.0f);
			
		}
		
		public void setColumnWidth(){
			int columnWidth = inputTable.getColumnWidth("first");
			logger.debug("Breite der Spalte: "+columnWidth);
			capitalStockInput.setColumnWidth("first", columnWidth);
		}
		
		public void generateTable(){

			logger.debug("generateTable aufgerufen");
			int pastPeriods = project.getRelevantPastPeriods();
			int baseYear = project.getBasisYear();
			int periodsToForecast = project.getPeriodsToForecast_deterministic();
			int pastYear = baseYear - pastPeriods;
			int currYear = pastYear;

			Collection properties = inputTable.getContainerPropertyIds();
			Iterator it = properties.iterator();
			Object[] tempProp = new Object[properties.size()];
			int a = 0;
			while(it.hasNext()){
				tempProp[a] = it.next();
				a++;
			}
			for(Object o : tempProp){
				logger.debug("Property "+o+" wurde entfernt");
				inputTable.removeContainerProperty(o);
				capitalStockInput.removeContainerProperty(o);
			}

			inputTable.removeAllItems();
			capitalStockInput.removeAllItems();

			inputTable.addContainerProperty("first", String.class, null);
			inputTable.setColumnHeader("first", "");
			
			capitalStockInput.addContainerProperty("first", String.class, null);
			capitalStockInput.setColumnHeader("first", "");
			if(project.getProjectInputType().isStochastic()){
				createStochasticTable(pastPeriods, baseYear, pastYear);
			}else if(project.getProjectInputType().isDeterministic()){
				createDeterministicTable(periodsToForecast, baseYear);
			}

			AbstractDeterministicMethod method = project.getCalculationMethod();
			
			Object itemId = inputTable.addItem();
			Item row1 = inputTable.getItem(itemId);			
			row1.getItemProperty("first").setValue("Umsatzerlöse");
			itemId = inputTable.addItem();
			Item row2 = inputTable.getItem(itemId);
			row2.getItemProperty("first").setValue("Erhöhung Bestand fertiger Erzeugnisse");
			itemId = inputTable.addItem();
			Item row3 = inputTable.getItem(itemId);
			row3.getItemProperty("first").setValue("Verminderung Bestand fertiger Erzeugnisse");
			itemId = inputTable.addItem();
			Item row4 = inputTable.getItem(itemId);
			row4.getItemProperty("first").setValue("Materialaufwand");
			itemId = inputTable.addItem();
			Item row5 = inputTable.getItem(itemId);
			row5.getItemProperty("first").setValue("Löhne/Gehälter");
			itemId = inputTable.addItem();
			Item row6 = inputTable.getItem(itemId);
			row6.getItemProperty("first").setValue("Einstellungskosten");
			itemId = inputTable.addItem();
			Item row7 = inputTable.getItem(itemId);
			row7.getItemProperty("first").setValue("Pensionsrückstellungen");
			itemId = inputTable.addItem();
			Item row8 = inputTable.getItem(itemId);
			row8.getItemProperty("first").setValue("Sonstige Personalkosten");
			itemId = inputTable.addItem();
			Item row9 = inputTable.getItem(itemId);
			row9.getItemProperty("first").setValue("Abschreibungen");
			itemId = inputTable.addItem();
			Item row10 = inputTable.getItem(itemId);
			row10.getItemProperty("first").setValue("Sonstige Aufwendungen");
			itemId = inputTable.addItem();
			Item row11 = inputTable.getItem(itemId);
			row11.getItemProperty("first").setValue("Sonstige Erträge");
			itemId = inputTable.addItem();
			Item row12 = inputTable.getItem(itemId);
			row12.getItemProperty("first").setValue("Wertpapiererträge");
			itemId = inputTable.addItem();
			Item row13 = inputTable.getItem(itemId);
			row13.getItemProperty("first").setValue("Zinsen und ähnliche Aufwendungen");
			itemId = inputTable.addItem();
			Item row14 = inputTable.getItem(itemId);
			row14.getItemProperty("first").setValue("Außerordentliche Erträge");
			itemId = inputTable.addItem();
			Item row15 = inputTable.getItem(itemId);
			row15.getItemProperty("first").setValue("Außerordentliche Aufwendungen");
			
			Object capitalItemId = capitalStockInput.addItem();
			Item rowCapital = capitalStockInput.getItem(capitalItemId);
			rowCapital.getItemProperty("first").setValue("Fremdkapital");
			
			if(project.getProjectInputType().isStochastic()){
				currYear = pastYear;
				currYear = createTextFields((pastPeriods+1), currYear, rowCapital, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14, row15);
			}else if(project.getProjectInputType().isDeterministic()){
				currYear = baseYear;
				createTextFields(periodsToForecast, currYear, rowCapital, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14, row15);
			}
			
		}

		private int createTextFields(int pastPeriods, int currYear, Item capitalRow, Item row1, Item row2, Item row3, Item row4, Item row5, Item row6, Item row7, Item row8, Item row9, Item row10, Item row11, Item row12, Item row13, Item row14, Item row15) {
			for(int i = 0; i < pastPeriods; i++){
				final int year = currYear;
				TextField field0 = new TextField();
				field0.setWidth(50, UNITS_PIXELS);
				field0.setImmediate(true);
				field0.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setFremdkapital(dValue, year);

					}

				});
				field0.setValue(presenter.getFremdkapital(year));
				capitalRow.getItemProperty(currYear).setValue(field0);
				
				TextField field1 = new TextField();
				field1.setWidth(50, UNITS_PIXELS);
				field1.setImmediate(true);
				field1.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setUmsatzerloese(dValue, year);

					}

				});
				field1.setValue(presenter.getUmsatzerloese(year));
				TextField field2 = new TextField();
				field2.setWidth(50, UNITS_PIXELS);
				field2.setImmediate(true);
				field2.addListener(new Property.ValueChangeListener() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setBestanderhoehung(dValue, year);
						
					}
				});
				field2.setValue(presenter.getBestanderhoehung(year));
				TextField field3 = new TextField();
				field3.setWidth(50, UNITS_PIXELS);
				field3.setImmediate(true);
				field3.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setBestandverminderung(dValue, year);

					}

				});
				field3.setValue(presenter.getBestandminderung(year));
				TextField field4 = new TextField();
				field4.setWidth(50, UNITS_PIXELS);
				field4.setImmediate(true);
				field4.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setMaterialaufwand(dValue, year);

					}

				});
				field4.setValue(presenter.getMaterialaufwand(year));
				TextField field5 = new TextField();
				field5.setWidth(50, UNITS_PIXELS);
				field5.setImmediate(true);
				field5.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setLoehne(dValue, year);

					}

				});
				field5.setValue(presenter.getLoehne(year));
				TextField field6 = new TextField();
				field6.setWidth(50, UNITS_PIXELS);
				field6.setImmediate(true);
				field6.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setEinstellungskosten(dValue, year);

					}

				});
				field6.setValue(presenter.getEinstellungskosten(year));
				TextField field7 = new TextField();
				field7.setWidth(50, UNITS_PIXELS);
				field7.setImmediate(true);
				field7.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setPensionrueckstellungen(dValue, year);

					}

				});
				field7.setValue(presenter.getPensionsrueckstellungen(year));
				TextField field8 = new TextField();
				field8.setWidth(50, UNITS_PIXELS);
				field8.setImmediate(true);
				field8.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setSonstigPersonal(dValue, year);

					}

				});
				field8.setValue(presenter.getSonstigPersonalkosten(year));
				TextField field9 = new TextField();
				field9.setWidth(50, UNITS_PIXELS);
				field9.setImmediate(true);
				field9.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setAbschreibungen(dValue, year);

					}

				});
				field9.setValue(presenter.getAbschreibungen(year));
				TextField field10 = new TextField();
				field10.setWidth(50, UNITS_PIXELS);
				field10.setImmediate(true);
				field10.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setSonstigAufwand(dValue, year);

					}

				});
				field10.setValue(presenter.getSonstigAufwand(year));
				TextField field11 = new TextField();
				field11.setWidth(50, UNITS_PIXELS);
				field11.setImmediate(true);
				field11.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setSonstigErtrag(dValue, year);

					}

				});
				field11.setValue(presenter.getSonstigErtrag(year));
				TextField field12 = new TextField();
				field12.setWidth(50, UNITS_PIXELS);
				field12.setImmediate(true);
				field12.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setWertpapierErtrag(dValue, year);

					}

				});
				field12.setValue(presenter.getWertpapierErtrag(year));
				TextField field13 = new TextField();
				field13.setWidth(50, UNITS_PIXELS);
				field13.setImmediate(true);
				field13.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setZinsaufwand(dValue, year);

					}

				});
				field13.setValue(presenter.getZinsaufwand(year));
				TextField field14 = new TextField();
				field14.setWidth(50, UNITS_PIXELS);
				field14.setImmediate(true);
				field14.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setAusserordentlichErtrag(dValue, year);

					}

				});
				field14.setValue(presenter.getAusserordentlichErtrag(year));
				TextField field15 = new TextField();
				field15.setWidth(50, UNITS_PIXELS);
				field15.setImmediate(true);
				field15.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						double dValue = Double.parseDouble(value);
						presenter.setAusserordentlichAufwand(dValue, year);

					}

				});
				field15.setValue(presenter.getAusserordentlichAufwand(year));
				
				row1.getItemProperty(currYear).setValue(field1);
				row2.getItemProperty(currYear).setValue(field2);
				row3.getItemProperty(currYear).setValue(field3);
				row4.getItemProperty(currYear).setValue(field4);
				row5.getItemProperty(currYear).setValue(field5);
				row6.getItemProperty(currYear).setValue(field6);
				row7.getItemProperty(currYear).setValue(field7);
				row8.getItemProperty(currYear).setValue(field8);
				row9.getItemProperty(currYear).setValue(field9);
				row10.getItemProperty(currYear).setValue(field10);
				row11.getItemProperty(currYear).setValue(field11);
				row12.getItemProperty(currYear).setValue(field12);
				row13.getItemProperty(currYear).setValue(field13);
				row14.getItemProperty(currYear).setValue(field14);
				row15.getItemProperty(currYear).setValue(field15);
				currYear++;
			}
			return currYear;
		}
		
		private void createDeterministicTable(int periodsToForecast, int baseYear){
			int currYear = baseYear;

			for(int i = 0; i < periodsToForecast; i++){
				inputTable.addContainerProperty(currYear, TextField.class, null);
				inputTable.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				capitalStockInput.addContainerProperty(currYear, TextField.class, null);
				capitalStockInput.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				currYear++;
				logger.debug("Property "+currYear+" wurde hinzugefügt");
			}

		}

		private void createStochasticTable(int pastPeriods, int baseYear, int pastYear){
			int currYear = pastYear;
			for(int i = 0; i < pastPeriods; i++){
				inputTable.addContainerProperty(currYear, TextField.class, null);
				inputTable.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				capitalStockInput.addContainerProperty(currYear, TextField.class, null);
				capitalStockInput.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				currYear++;
				logger.debug("Property "+currYear+" wurde hinzugefügt");
			}
			inputTable.addContainerProperty(baseYear, TextField.class, null);
			inputTable.setColumnAlignment(baseYear, Table.ALIGN_CENTER);
			capitalStockInput.addContainerProperty(baseYear, TextField.class, null);
			capitalStockInput.setColumnAlignment(baseYear, Table.ALIGN_CENTER);
		}


		@Override
		public void setProject(Project project) {
			this.project = project;
			
		}
}

