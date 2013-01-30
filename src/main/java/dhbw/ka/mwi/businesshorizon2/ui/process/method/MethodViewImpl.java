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


package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;



/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */

public class MethodViewImpl extends HorizontalSplitPanel implements MethodViewInterface {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MethodPresenter presenter;
	
	private Panel methodPanel;

	
	private VerticalLayout methodList;
	private VerticalLayout inputMethod;

	private OptionGroup stochasticInput;
	private OptionGroup deterministicInput;
	
	private OptionGroup methods;
			
	private CheckBox stochastic; //= new CheckBox("Stochastische Berechnung");
	private CheckBox deterministic; //= new CheckBox("Deterministische Eingabe");
	
	private Panel stoInput;
	private Panel detInput;
	
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Julius Hacker
	 */
	
	@PostConstruct
	public void init() {
		presenter.setView(this);
	}
	
	public void showMethodView() {
		this.removeAllComponents();
		
		stochastic = new CheckBox("Stochastische Berechnung");
		deterministic = new CheckBox("Deterministische Eingabe");
		
		stochastic.setImmediate(true);
		deterministic.setImmediate(true);
		
		methodPanel = new Panel();
		methodPanel.addStyleName(Reindeer.PANEL_LIGHT);
		
		stochastic.addListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.toggleMethodType(true,event.getButton().booleanValue());				
			}
		});
		deterministic.addListener(new Button.ClickListener() {
	
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.toggleMethodType(false,event.getButton().booleanValue());
				
			}
		});

		methods = new OptionGroup();
		
		methods.setMultiSelect(true);
		methods.setImmediate(true);
		methods.addListener(new Property.ValueChangeListener() {
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {
				presenter.toggleMethod((Set<AbstractStochasticMethod>) methods.getValue());
			}
		});

		stochasticInput = new OptionGroup();
		stochasticInput.setImmediate(true);
		stochasticInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleMethodTypeInput(true,selected);	
			}
		});
		
		deterministicInput = new OptionGroup();
		deterministicInput.setImmediate(true);
		deterministicInput.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				InputType selected = (InputType) event.getProperty().getValue();
				presenter.toggleMethodTypeInput(false,selected);				
			}
		});
		
		initOptionGroups();
		generateUi();
		inputMethod.setSizeFull();
	}

	private void initOptionGroups() {
		detInput = new Panel();
		Label detCaption = new Label ("Zukünftige Perioden (deterministisch):");
		detInput.addComponent(detCaption);
		deterministicInput.addItem(InputType.DIRECT);
		deterministicInput.addItem(InputType.REVENUE);
		deterministicInput.addItem(InputType.TOTAL);
		detInput.addStyleName(Reindeer.PANEL_LIGHT);
		detInput.addComponent(deterministicInput);
		
		stoInput = new Panel();
		Label stoCaption= new Label ("Vergangene Perioden (stochastisch):");
		stoInput.addComponent(stoCaption);
		stochasticInput.addItem(InputType.DIRECT);
		stochasticInput.addItem(InputType.REVENUE);
		stochasticInput.addItem(InputType.TOTAL);
		stoInput.addStyleName(Reindeer.PANEL_LIGHT);
		stoInput.addComponent(stochasticInput);
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Methoden"
	 * 
	 * @author Julius Hacker
	 */
	
	
	private void generateUi() {
		methodList = new VerticalLayout();
		inputMethod = new VerticalLayout();
		methodList.setSizeFull();
		inputMethod.setSizeFull();
		
		this.setSizeFull();
		
		this.setFirstComponent(methodList);
		this.setSecondComponent(inputMethod);
		
		methodPanel.addComponent(methods);
		Label methodCaption = new Label("Herkunft der Cashflows w\u00E4hlen:");
		methodList.addComponent(methodCaption);
		methodList.addComponent(stochastic);
		methodList.addComponent(methodPanel);		
		methodList.addComponent(deterministic);
		
		Label inputCaption = new Label("Eingabemethode der Cashflows w\u00E4hlen:");
		inputMethod.addComponent(inputCaption);
		inputMethod.addComponent(stoInput);
		inputMethod.addComponent(detInput);

		
	}

	@Override
	public void showMethod(AbstractStochasticMethod method) {
		
		methods.addItem(method);
		methods.setItemEnabled(method, method.getImplemented());
		
		if (method.getSelected()){
			methods.select(method);
		}
		


	}

	@Override
	public void enableOptions() {
		
		
	}

	@Override
	public void enableMethodSelection(Boolean state) {
		methods.setEnabled(state);
		
	}

	@Override
	public void showInputMethodSelection(Boolean stochasticBool, Boolean checked) {
	
		if (stochasticBool){
			stoInput.setVisible(checked);
		}
		else{
			detInput.setVisible(checked);
		}		
	}

	public void setStochastic(Boolean checked) {
		this.stochastic.setValue(checked);
	}
	
	public void setDeterministic(Boolean checked) {
		this.deterministic.setValue(checked);
	}
	
	@Override
	public void selectInput(Boolean stochastic,InputType selected) {
		if (stochastic){
			stochasticInput.select(selected);
		}
		else{
			deterministicInput.select(selected);
		}
		
	}

	@Override
	public void showErrorNoMethodSelected(Boolean state) {
		if (!state){
		this.methods.setComponentError(new UserError("Wenn stochastisch gew\u00E4hlt wurde, muss mindestens eine Methode ausw\u00E4hlt werden"));
		}
		else {
			this.methods.setComponentError(null);
		}
	}

	@Override
	public void showErrorNothingSelected(Boolean state) {
		
		if (!state) {
			this.stochastic.setComponentError(new UserError("Es muss mindestens eine Cashflowherkunft ausgew\u00E4hlt werden"));
		}
		else {
			this.stochastic.setComponentError(null);
		}
		
	}
}
