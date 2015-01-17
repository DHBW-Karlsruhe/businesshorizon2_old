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

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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
		int pastPeriods = project.getSpecifiedPastPeriods();
		int baseYear = project.getBasisYear();
		int pastYear = baseYear - pastPeriods;
		int currYear = pastYear;
		
		inputTable.removeAllItems();

		inputTable.addContainerProperty("", String.class, null);
		for(int i = 0; i < pastPeriods; i++){
			inputTable.addContainerProperty(currYear, TextField.class, null);
			currYear++;
		}
		inputTable.addContainerProperty(baseYear, TextField.class, null);
		
		currYear = pastYear;
		Object itemId = inputTable.addItem();
		Item row1 = inputTable.getItem(itemId);
		row1.getItemProperty("").setValue("Cashflow");
		itemId = inputTable.addItem();
		Item row2 = inputTable.getItem(itemId);
		row2.getItemProperty("").setValue("Bilanzwert Fremdkapital");
		for(int i = 0; i < (pastPeriods + 1); i++){
			row1.getItemProperty(currYear).setValue(new TextField());
			row2.getItemProperty(currYear).setValue(new TextField());
			currYear++;
		}
	}


}

