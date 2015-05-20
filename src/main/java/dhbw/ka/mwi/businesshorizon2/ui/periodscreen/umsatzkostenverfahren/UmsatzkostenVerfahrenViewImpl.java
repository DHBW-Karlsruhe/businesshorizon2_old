/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 2014-2015 Marco Glaser, Tobias Lindner
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

package dhbw.ka.mwi.businesshorizon2.ui.periodscreen.umsatzkostenverfahren;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
* Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
* Vaadin.
*
* @author Marco Glaser, Tobias Lindner
*
*/
public class UmsatzkostenVerfahrenViewImpl extends VerticalLayout implements UmsatzkostenVerfahrenViewInterface{
        private static final long serialVersionUID = 1L;

        @Autowired
        private UmsatzkostenVerfahrenPresenter presenter;

		private Label headerLabel;

		private Label gap;

		private Table inputTable;

		private Label expandingGap;

		private Project project;

		private Label headerLabel2;

		private Label gap2;

		private Label gap3;

		private Table capitalStockInput;
		
		private ArrayList<TextField> allTextFields = new ArrayList<TextField>();

		private Table cashFlowValues;

		private Label gap4;

		private Label headerLabel3;

		private Label gap5;
        
        private static final Logger logger = Logger.getLogger("UmsatzkostenVerfahrenViewImpl.class");

        /**
         * Dies ist der Konstruktor, der von Spring nach der Initialierung der
         * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
         * einen EventHandler.
         *
         * @author Marco Glaser
         */

        @PostConstruct
        public void init() {
        	setHeight(Sizeable.SIZE_UNDEFINED, 0);
            presenter.setView(this);
            logger.debug("init beendet");
            generateUi();
        }

        /**
         * @author Marco Glaser
         */
		private void generateUi() {
			logger.debug("GenerateUi aufgerufen");
			headerLabel = new Label("Eingabe der Werte zur Jahresüberschuss-Berechnung (in EUR)");
			headerLabel2 = new Label("Eingabe des Fremdkapitals (in EUR)");
			headerLabel3 = new Label("Eingabe der Werte zur Cash-Flow-Berechnung (in EUR)");
			gap = new Label();
			inputTable = new Table();
			gap2 = new Label();
			gap3 = new Label();
			gap4 = new Label();
			gap5 = new Label();
			cashFlowValues = new Table();
			capitalStockInput = new Table();
			expandingGap = new Label();

			headerLabel.setStyleName("periodHeaderLabel");
			headerLabel2.setStyleName("periodHeaderLabel");
			headerLabel3.setStyleName("periodHeaderLabel");
			gap.setHeight("5px");
			gap2.setHeight("15px");
			gap3.setHeight("5px");
			gap4.setHeight("15px");
			gap5.setHeight("5px");
			inputTable.setWidth(100, UNITS_PERCENTAGE);
			inputTable.setStyleName("fcfTable");
			inputTable.setPageLength(14);
			cashFlowValues.setWidth(100, UNITS_PERCENTAGE);
			cashFlowValues.setStyleName("fcfTable");
			cashFlowValues.setPageLength(2);
			capitalStockInput.setWidth(100, UNITS_PERCENTAGE);
			capitalStockInput.setStyleName("fcfTable");
			capitalStockInput.setPageLength(1);
			expandingGap.setHeight(100, UNITS_PERCENTAGE);

			addComponent(headerLabel);
			addComponent(gap);
			addComponent(inputTable);
			addComponent(gap2);
			addComponent(headerLabel3);
			addComponent(gap3);
			addComponent(cashFlowValues);
			addComponent(gap4);
			addComponent(headerLabel2);
			addComponent(gap5);
			addComponent(capitalStockInput);
//			addComponent(expandingGap);
//
//			setExpandRatio(expandingGap, 1.0f);
			
		}

		/**
         * @author Marco Glaser
         */
		@Override
		public void setProject(Project project) {
			this.project = project;
			
		}

		/**
         * @author Marco Glaser
         */
		@Override
		public void generateTable() {
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

				inputTable.removeContainerProperty(o);
				capitalStockInput.removeContainerProperty(o);
				cashFlowValues.removeContainerProperty(o);
			}

			inputTable.removeAllItems();
			capitalStockInput.removeAllItems();
			cashFlowValues.removeAllItems();

			inputTable.addContainerProperty("first", String.class, null);
			inputTable.setColumnHeader("first", "");
			cashFlowValues.addContainerProperty("first", String.class, null);
			cashFlowValues.setColumnHeader("first", "");
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
			row2.getItemProperty("first").setValue("Herstellungskosten");
			itemId = inputTable.addItem();
			Item row3 = inputTable.getItem(itemId);
			row3.getItemProperty("first").setValue("Vertriebskosten");
			itemId = inputTable.addItem();
			Item row4 = inputTable.getItem(itemId);
			row4.getItemProperty("first").setValue("Allgemeine Verwaltungskosten");
			itemId = inputTable.addItem();
			Item row5 = inputTable.getItem(itemId);
			row5.getItemProperty("first").setValue("Sonstige betriebliche Aufwendungen");
			itemId = inputTable.addItem();
			Item row6 = inputTable.getItem(itemId);
			row6.getItemProperty("first").setValue("Sonstige betriebliche Erträge");
			
			itemId = inputTable.addItem();
			Item row7 = inputTable.getItem(itemId);
			row7.getItemProperty("first").setValue("Erträge aus Beteiligungen");
			itemId = inputTable.addItem();
			Item row8 = inputTable.getItem(itemId);
			row8.getItemProperty("first").setValue("Erträge aus Wertpapieren");
			itemId = inputTable.addItem();
			Item row9 = inputTable.getItem(itemId);
			row9.getItemProperty("first").setValue("Sonstige Zinsen und ähnliche Erträge");
			itemId = inputTable.addItem();
			Item row10 = inputTable.getItem(itemId);
			row10.getItemProperty("first").setValue("Abschreibungen auf Finanzanlagen");
			itemId = inputTable.addItem();
			Item row11 = inputTable.getItem(itemId);
			row11.getItemProperty("first").setValue("Zinsen und ähnliche Aufwendungen");
			itemId = inputTable.addItem();
			Item row12 = inputTable.getItem(itemId);
			row12.getItemProperty("first").setValue("Außerordentliche Erträge");
			itemId = inputTable.addItem();
			Item row13 = inputTable.getItem(itemId);
			row13.getItemProperty("first").setValue("Außerordentliche Aufwendungen");
			itemId = inputTable.addItem();
			Item row14 = inputTable.getItem(itemId);
			row14.getItemProperty("first").setValue("Steueraufwand");
			
			Object capitalItemId = capitalStockInput.addItem();
			Item rowCapital = capitalStockInput.getItem(capitalItemId);
			rowCapital.getItemProperty("first").setValue("Fremdkapital");
			
			Object cashFlowItemId = cashFlowValues.addItem();
			Item rowCashflow = cashFlowValues.getItem(cashFlowItemId);
			rowCashflow.getItemProperty("first").setValue("Abschreibungen");
			cashFlowItemId = cashFlowValues.addItem();
			Item rowCashflow2 = cashFlowValues.getItem(cashFlowItemId);
			rowCashflow2.getItemProperty("first").setValue("Brutto-Investitionen");
			
			if(project.getProjectInputType().isStochastic()){
				currYear = pastYear;
				currYear = createTextFields((pastPeriods+1), currYear, rowCapital, rowCashflow, rowCashflow2, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14);
			}else if(project.getProjectInputType().isDeterministic()){
				currYear = baseYear;
				createTextFields(periodsToForecast, currYear, rowCapital, rowCashflow, rowCashflow2, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14);
			}
			
		}
		
		/**
         * @author Marco Glaser, Tobias Lindner
         */
		private int createTextFields(int pastPeriods, int currYear, Item capitalRow, Item rowCashflow, Item rowCashflow2, Item row1, Item row2, Item row3, Item row4, Item row5, Item row6, Item row7, Item row8, Item row9, Item row10, Item row11, Item row12, Item row13, Item row14) {
			for(int i = 0; i < pastPeriods; i++){
				final int year = currYear;
				
				//Fremdkapital
				final TextField field0 = new TextField();
				field0.setWidth(50, UNITS_PIXELS);
				field0.setImmediate(true);
				field0.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setFremdkapital(dValue, year);
							field0.setComponentError(null);
							field0.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe des Fremdkapitals im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field0.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field0.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field0.setValue(presenter.getFremdkapital(year));
				capitalRow.getItemProperty(currYear).setValue(field0);
				
				//Umsatzerlöse
				final TextField field1 = new TextField();
				field1.setWidth(50, UNITS_PIXELS);
				field1.setImmediate(true);
				field1.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setUmsatzerloese(dValue, year);
							field1.setComponentError(null);
							field1.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Umsatzerlöse im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field1.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field1.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field1.setValue(presenter.getUmsatzerloese(year));
				
				//Herstellungskosten
				final TextField field2 = new TextField();
				field2.setWidth(50, UNITS_PIXELS);
				field2.setImmediate(true);
				field2.addListener(new Property.ValueChangeListener() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setHerstellkosten(dValue, year);
							field2.setComponentError(null);
							field2.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe Herstellungskosten im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field2.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field2.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}
				});
				field2.setValue(presenter.getHerstellkosten(year));
				
				//Vertriebskosten
				final TextField field3 = new TextField();
				field3.setWidth(50, UNITS_PIXELS);
				field3.setImmediate(true);
				field3.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setVertriebskosten(dValue, year);
							field3.setComponentError(null);
							field3.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Vertriebskosten im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field3.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field3.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field3.setValue(presenter.getVertriebskosten(year));
				
				//Verwaltungskosten
				final TextField field4 = new TextField();
				field4.setWidth(50, UNITS_PIXELS);
				field4.setImmediate(true);
				field4.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setVerwaltungskosten(dValue, year);
							field4.setComponentError(null);
							field4.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Verwaltungskosten im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field4.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field4.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field4.setValue(presenter.getVerwaltungskosten(year));
				
				//Sonstige Aufwendungen
				final TextField field5 = new TextField();
				field5.setWidth(50, UNITS_PIXELS);
				field5.setImmediate(true);
				field5.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setSonstigAufwand(dValue, year);
							field5.setComponentError(null);
							field5.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der sonstigen Aufwendungen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field5.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field5.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field5.setValue(presenter.getSonstigAufwand(year));
				
				//Sonstige Erträge
				final TextField field6 = new TextField();
				field6.setWidth(50, UNITS_PIXELS);
				field6.setImmediate(true);
				field6.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setSonstigErtrag(dValue, year);
							field6.setComponentError(null);
							field6.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der sonstigen Erträge im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field6.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field6.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field6.setValue(presenter.getSonstigErtrag(year));
				
				//Erträge aus Beteiligungen
				final TextField field7 = new TextField();
				field7.setWidth(50, UNITS_PIXELS);
				field7.setImmediate(true);
				field7.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setBeteiligungenErtrag(dValue, year);
							field7.setComponentError(null);
							field7.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Erträge aus Beteiligungen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field7.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field7.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field7.setValue(presenter.getBeteiligungenErtrag(year));
				
				//Erträge aus Werpapieren
				final TextField field8 = new TextField();
				field8.setWidth(50, UNITS_PIXELS);
				field8.setImmediate(true);
				field8.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setWertpapierErtrag(dValue, year);
							field8.setComponentError(null);
							field8.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Erträge aus Wertpapieren im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field8.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field8.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field8.setValue(presenter.getWertpapierErtrag(year));
				
				//Zinserträge
				final TextField field9 = new TextField();
				field9.setWidth(50, UNITS_PIXELS);
				field9.setImmediate(true);
				field9.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setZinsertrag(dValue, year);
							field9.setComponentError(null);
							field9.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Zinserträge im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field9.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field9.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field9.setValue(presenter.getZinsertrag(year));
				
				//Abschreibungen auf Finanzanlagen
				final TextField field10 = new TextField();
				field10.setWidth(50, UNITS_PIXELS);
				field10.setImmediate(true);
				field10.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setAbschreibungenFinanzanlagen(dValue, year);
							field10.setComponentError(null);
							field10.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Abschreibungen auf Finanzanlagen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field10.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field10.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field10.setValue(presenter.getAbschreibungenFinanzanlagen(year));
				
				//Zinsen und ähnliche Aufwendungen
				final TextField field11 = new TextField();
				field11.setWidth(50, UNITS_PIXELS);
				field11.setImmediate(true);
				field11.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setZinsaufwand(dValue, year);
							field11.setComponentError(null);
							field11.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Zinsen und änhlichen Aufwendungen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field11.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field11.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field11.setValue(presenter.getZinsaufwand(year));
				
				//Außerordentliche Aufwendungen
				final TextField field13 = new TextField();
				field13.setWidth(50, UNITS_PIXELS);
				field13.setImmediate(true);
				field13.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setAusserordentlichAufwand(dValue, year);
							field13.setComponentError(null);
							field13.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der außerordentlichen Aufwendungen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field13.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field13.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field13.setValue(presenter.getAusserordentlichAufwand(year));
				
				//Außerordentliche Erträge
				final TextField field12 = new TextField();
				field12.setWidth(50, UNITS_PIXELS);
				field12.setImmediate(true);
				field12.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setAusserordentlichErtrag(dValue, year);
							field12.setComponentError(null);
							field12.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der außerordentlichen Erträge im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field12.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field12.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field12.setValue(presenter.getAusserordentlichErtrag(year));
				
				//Steueraufwendungen
				final TextField field14 = new TextField();
				field14.setWidth(50, UNITS_PIXELS);
				field14.setImmediate(true);
				field14.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setSteueraufwand(dValue, year);
							field14.setComponentError(null);
							field14.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Steueraufwendungen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field14.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field14.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field14.setValue(presenter.getSteueraufwand(year));
				
				//Abschreibungen
				final TextField field15 = new TextField();
				field15.setWidth(50, UNITS_PIXELS);
				field15.setImmediate(true);
				field15.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setAbschreibungen(dValue, year);
							field15.setComponentError(null);
							field15.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Abschreibungen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field15.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field15.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field15.setValue(presenter.getAbschreibungen(year));
				
				//Brutto-Investitionen
				final TextField field16 = new TextField();
				field16.setWidth(50, UNITS_PIXELS);
				field16.setImmediate(true);
				field16.addListener(new Property.ValueChangeListener(){

					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						String value = (String) event.getProperty().getValue();
						value = value.replace(',', '.');
						
						try {
							double dValue = Double.parseDouble(value);
							presenter.setBruttoinvestitionen(dValue, year);
							field16.setComponentError(null);
							field16.removeStyleName("showErrors");
							presenter.validateUKVInput();
						}
						
						catch (Exception e) {
							notificateWarning("Ihre Eingabe der Brutto-Investitionen im Jahr " + year + " ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.");
							field16.setComponentError(new UserError ("Bitte geben Sie eine Kommazahl an."));
							field16.addStyleName("showErrors");
							presenter.validateUKVInput();
						}
					}

				});
				field16.setValue(presenter.getBruttoinvestitionen(year));
				
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
				
				rowCashflow.getItemProperty(currYear).setValue(field15);
				rowCashflow2.getItemProperty(currYear).setValue(field16);
				
				allTextFields.add(field0);
				allTextFields.add(field1);
				allTextFields.add(field2);
				allTextFields.add(field3);
				allTextFields.add(field4);
				allTextFields.add(field5);
				allTextFields.add(field6);
				allTextFields.add(field7);
				allTextFields.add(field8);
				allTextFields.add(field9);
				allTextFields.add(field10);
				allTextFields.add(field11);
				allTextFields.add(field12);
				allTextFields.add(field13);
				allTextFields.add(field14);
				allTextFields.add(field15);
				allTextFields.add(field16);
				
				currYear++;
			}
			return currYear;
		}
		
		/**
         * @author Marco Glaser
         */
		private void createDeterministicTable(int periodsToForecast, int baseYear){
			int currYear = baseYear;

			for(int i = 0; i < periodsToForecast; i++){
				inputTable.addContainerProperty(currYear, TextField.class, null);
				inputTable.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				cashFlowValues.addContainerProperty(currYear, TextField.class, null);
				cashFlowValues.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				capitalStockInput.addContainerProperty(currYear, TextField.class, null);
				capitalStockInput.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				currYear++;

			}

		}

		/**
         * @author Marco Glaser
         */
		private void createStochasticTable(int pastPeriods, int baseYear, int pastYear){
			int currYear = pastYear;
			for(int i = 0; i < pastPeriods; i++){
				inputTable.addContainerProperty(currYear, TextField.class, null);
				inputTable.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				cashFlowValues.addContainerProperty(currYear, TextField.class, null);
				cashFlowValues.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				capitalStockInput.addContainerProperty(currYear, TextField.class, null);
				capitalStockInput.setColumnAlignment(currYear, Table.ALIGN_CENTER);
				currYear++;

			}
			inputTable.addContainerProperty(baseYear, TextField.class, null);
			inputTable.setColumnAlignment(baseYear, Table.ALIGN_CENTER);
			cashFlowValues.addContainerProperty(baseYear, TextField.class, null);
			cashFlowValues.setColumnAlignment(baseYear, Table.ALIGN_CENTER);
			capitalStockInput.addContainerProperty(baseYear, TextField.class, null);
			capitalStockInput.setColumnAlignment(baseYear, Table.ALIGN_CENTER);
		}
		
		/**
		 * Diese Methode zeigt eine Notification vom Type Warning_Message an.
		 * 
		 * @author Tobias Lindner
		 * @param warningText
		 * 			Textinhalt der Warnmeldung
		 */
		private void notificateWarning (String warningText) {
			getWindow().showNotification((String) "", warningText,	Notification.TYPE_WARNING_MESSAGE);
		}
		
		/**
		 * Diese Methode überprüft, ob für ein Textfeld der Tabelle ein Fehlermarker gesetzt ist. Falls dies der Falls ist wird true zurückgegeben, anderenfalls false. 
		 * 
		 * @author Tobias Lindner
		 * @return boolean
		 * 			Besteht ein Component Error?: Ja --> true, nein --> false
		 */
		public boolean isComponentError () {
			Iterator<TextField> it = allTextFields.iterator();
			while(it.hasNext()){
				TextField temp = (TextField) it.next();
				if (temp.getComponentError()!=null) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Diese Methode überprüft, ob in mindestens einem Textfeld durch den User ein Wert gesetzt wurde.
		 * 
		 * @author Tobias Lindner
		 * @return boolean
		 * 			Wurde mindestens der Wert eines Textfeldes gesetzt?: Ja --> true, nein --> false
		 */
		public boolean oneTextFieldIsSet () {
			Iterator<TextField> it = allTextFields.iterator();
			while(it.hasNext()){
				TextField temp = (TextField) it.next();
				if (!temp.getValue().equals("0.0")) {
					return true;
				}
			}
			return false;
		}
}
