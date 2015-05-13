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

package dhbw.ka.mwi.businesshorizon2.ui.periodscreen.direkteeingabe;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.process.period.input.AbstractInputView;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in
 * Vaadin.
 * 
 * @author Daniel Dengler
 * 
 */
public class DirektViewImpl extends VerticalLayout implements DirektViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DirektPresenter presenter;

	private Label headerLabel;

	private Table inputTable;

	private Label gap;

	private Label expandingGap;

	private Project project;

	private static final Logger logger = Logger.getLogger("DirektViewImpl.class");

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Daniel Dengler
	 */


	@PostConstruct
	public void init() {
		setSizeFull();
		presenter.setView(this);
		logger.debug("init aufgerufen");
		generateUi();
	}

	private void generateUi() {
		logger.debug("GenerateUi aufgerufen");
		headerLabel = new Label("Direkte Eingabe der Cash-Flows (in EUR)");
		gap = new Label();
		inputTable = new Table();
		expandingGap = new Label();

		headerLabel.setStyleName("periodHeaderLabel");
		gap.setHeight("15px");
		inputTable.setWidth(100, UNITS_PERCENTAGE);
		inputTable.setStyleName("fcfTable");
		inputTable.setPageLength(3);
		expandingGap.setHeight(100, UNITS_PERCENTAGE);

		addComponent(headerLabel);
		addComponent(gap);
		addComponent(inputTable);
		addComponent(expandingGap);

		setExpandRatio(expandingGap, 1.0f);

	}

	public void setProject(Project project){
		this.project = project;
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
		}

		inputTable.removeAllItems();

		inputTable.addContainerProperty("first", String.class, null);
		inputTable.setColumnHeader("first", "");
		if(project.getProjectInputType().isStochastic()){
			createStochasticTable(pastPeriods, baseYear, pastYear);
		}else if(project.getProjectInputType().isDeterministic()){
			createDeterministicTable(periodsToForecast, baseYear);
		}

		Object itemId = inputTable.addItem();
		Item row1 = inputTable.getItem(itemId);
		AbstractDeterministicMethod method = project.getCalculationMethod();

		if(method.getName().equals("Flow-to-Equity (FTE)")) {
			row1.getItemProperty("first").setValue("Ausschüttung");
		}
		else {
			row1.getItemProperty("first").setValue("Cashflow");
		}
		
		itemId = inputTable.addItem();
		Item row2 = inputTable.getItem(itemId);
		row2.getItemProperty("first").setValue("Bilanzwert Fremdkapital");
		if(project.getProjectInputType().isStochastic()){
			currYear = pastYear;
			currYear = createTextFields((pastPeriods+1), currYear, row1, row2);
		}else if(project.getProjectInputType().isDeterministic()){
			currYear = baseYear;
			createTextFields(periodsToForecast, currYear, row1, row2);
		}
	}

	private int createTextFields(int pastPeriods, int currYear, Item row1, Item row2) {
		for(int i = 0; i < pastPeriods; i++){
			final int year = currYear;
			TextField field1 = new TextField();
			field1.setWidth(50, UNITS_PIXELS);
			field1.setImmediate(true);
			field1.addListener(new Property.ValueChangeListener(){

				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					String value = (String) event.getProperty().getValue();
					double dValue;
					try {
						dValue = Double.parseDouble(value);
						presenter.setValid();
					}
					catch (Exception e){
						dValue = 0.0;
						getWindow().showNotification((String) "", "Ihre Eingabe des Cashlflows/der Ausschüttung für das Jahr" + year + "ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.",
								Notification.TYPE_WARNING_MESSAGE);
						presenter.setInvalid();	
					}
						
					presenter.setCashFlowValue(dValue, year);
//					presenter.checkValid();

				}

			});
			field1.setValue(presenter.getCashFlow(year));
			
			
			TextField field2 = new TextField();
			field2.setWidth(50, UNITS_PIXELS);
			field2.setImmediate(true);
			field2.addListener(new Property.ValueChangeListener() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					String value = (String) event.getProperty().getValue();
					double dValue;
					try {
						dValue = Double.parseDouble(value);
						presenter.setValid();						
					}
					catch (Exception e) {
						dValue = 0.0;
						getWindow().showNotification((String) "", "Ihre Eingabe des Bilanzwertes für das Jahr" + year + "ist keine valide Kommazahl. Bitte überprüfen Sie ihre Eingabe.",
								Notification.TYPE_WARNING_MESSAGE);
						presenter.setInvalid();
					}
					presenter.setCapitalStockValue(dValue, year);
//					presenter.checkValid();
					
				}
			});
			field2.setValue(presenter.getCapitalStock(year));
			row1.getItemProperty(currYear).setValue(field1);
			row2.getItemProperty(currYear).setValue(field2);
			currYear++;
		}
		return currYear;
	}

	private void createDeterministicTable(int periodsToForecast, int baseYear){
		int currYear = baseYear;

		for(int i = 0; i < periodsToForecast; i++){
			inputTable.addContainerProperty(currYear, TextField.class, null);
			inputTable.setColumnAlignment(currYear, Table.ALIGN_CENTER);
			currYear++;
			logger.debug("Property "+currYear+" wurde hinzugefügt");
		}

	}

	private void createStochasticTable(int pastPeriods, int baseYear, int pastYear){
		int currYear = pastYear;
		for(int i = 0; i < pastPeriods; i++){
			inputTable.addContainerProperty(currYear, TextField.class, null);
			inputTable.setColumnAlignment(currYear, Table.ALIGN_CENTER);
			currYear++;
			logger.debug("Property "+currYear+" wurde hinzugefügt");
		}
		inputTable.addContainerProperty(baseYear, TextField.class, null);
		inputTable.setColumnAlignment(baseYear, Table.ALIGN_CENTER);
	}


}

