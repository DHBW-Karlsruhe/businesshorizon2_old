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
package dhbw.ka.mwi.businesshorizon2.ui.process.method;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import dhbw.ka.mwi.businesshorizon2.methods.AbstractDeterministicMethod;
import dhbw.ka.mwi.businesshorizon2.methods.AbstractStochasticMethod;
import dhbw.ka.mwi.businesshorizon2.models.InputType;



/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker, Annika Weis, Mirko Göpfrich
 *
 */

public class MethodViewImpl extends HorizontalSplitPanel implements MethodViewInterface {

        private static final long serialVersionUID = 1L;

        @Autowired
        private MethodPresenter presenter;


        private Panel methodPanel_stochastic;
        private Panel methodPanel_deterministic;

        
        private VerticalLayout methodList;
        private VerticalLayout infoBox;

        private OptionGroup stochasticInput;
        private OptionGroup deterministicInput;

        private OptionGroup methods_stochastic;
        private OptionGroup methods_deterministic;
                        
        private CheckBox stochastic;
        private CheckBox deterministic;
        
        private Panel stoInput;
        private Panel detInput;
        
        
        /**
         * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
         * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
         * View-Komponenten.
         */
        
        @PostConstruct
        public void init() {
                presenter.setView(this);
        }
        
        public void showMethodView() {
                this.removeAllComponents();
                
                stochastic = new CheckBox("Stochastische Berechnung");
                deterministic = new CheckBox("Berechnungsmethode (Herkunft Cashflow: Deterministisch)");
                
                stochastic.setImmediate(true);
                deterministic.setImmediate(true);
                
                methodPanel_stochastic = new Panel();
                methodPanel_stochastic.addStyleName("light");
                
                methodPanel_deterministic = new Panel();
                methodPanel_deterministic.addStyleName("light");

                
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
                                presenter.toggleMethod_deterministicType(true,event.getButton().booleanValue());
                                
                        }
                });


                methods_stochastic = new OptionGroup();
                methods_stochastic.setMultiSelect(true);
                methods_stochastic.setImmediate(true);
                methods_stochastic.addListener(new Property.ValueChangeListener() {
                        
                        private static final long serialVersionUID = 1L;

                        @SuppressWarnings("unchecked")
                        @Override
                        public void valueChange(ValueChangeEvent event) {
                                presenter.toggleMethod((Set<AbstractStochasticMethod>) methods_stochastic.getValue());
                        }
                });


                methods_deterministic = new OptionGroup();
                methods_deterministic.setMultiSelect(true);
                methods_deterministic.setImmediate(true);
                methods_deterministic.addListener(new Property.ValueChangeListener() {
                        
                        private static final long serialVersionUID = 1L;

                        @SuppressWarnings("unchecked")
                        @Override
                        public void valueChange(ValueChangeEvent event) {
                                presenter.toggleMethod_deterministic((Set<AbstractDeterministicMethod>) methods_deterministic.getValue());
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
                                presenter.toggleMethod_deterministicTypeInput(true,selected);                                
                        }
                });
                
                initOptionGroups();
                generateUi();
                infoBox.setSizeFull();
        }

        private void initOptionGroups() {
                detInput = new Panel("Zukünftige Perioden (deterministisch):");
                deterministicInput.addItem(InputType.DIRECT);
                deterministicInput.addItem(InputType.GESAMTKOSTENVERFAHREN);
                deterministicInput.addItem(InputType.UMSATZKOSTENVERFAHREN);
                detInput.addStyleName(Reindeer.PANEL_LIGHT);
                detInput.addComponent(deterministicInput);
                
                stoInput = new Panel("Vergangene Perioden (stochastisch):");
                stochasticInput.addItem(InputType.DIRECT);
                stochasticInput.addItem(InputType.GESAMTKOSTENVERFAHREN);
                stochasticInput.addItem(InputType.UMSATZKOSTENVERFAHREN);
                stoInput.addStyleName(Reindeer.PANEL_LIGHT);
                stoInput.addComponent(stochasticInput);
        }

        /**
         * Erstelle das GUI zum Prozessschritt "Methoden"
         * 
         * @author Julius Hacker
         */  
        private void generateUi() {
        		setMargin(true);
                methodList = new VerticalLayout();
                infoBox = new VerticalLayout();
                
                this.setLocked(true);
                this.addStyleName("small");
                this.setSizeFull();
                
                this.setFirstComponent(methodList);
                this.setSecondComponent(infoBox);

                methodPanel_stochastic.addComponent(methods_stochastic);
                methodPanel_deterministic.addComponent(methods_deterministic);
                Label methodCaption = new Label("Herkunft der Cashflows w\u00E4hlen:");
                methodList.addComponent(methodCaption);
                methodList.addComponent(stochastic);
                methodList.addComponent(methodPanel_stochastic);
                methodList.addComponent(deterministic);
                methodList.addComponent(methodPanel_deterministic);                
                
                Label inputCaption = new Label("Eingabemethode der Cashflows w\u00E4hlen:");
                methodList.addComponent(inputCaption);
                methodList.addComponent(stoInput);
                methodList.addComponent(detInput);

                VerticalLayout infoBox = new VerticalLayout();
        		infoBox.setMargin(true);
        		Label infoText1 = new Label ("<h3>Herkunft der Cashflows:</h3>");
        		infoText1.setContentMode(Label.CONTENT_XHTML);
        		Label infoText2 = new Label ("Nun müssen Sie sich zwischen der stochastischen Berechnung der Cashflows auf Basis vergangener Cashflows und der Deterministischen Bestimmung, also der Eingabe der zukünftigen Cashflows entscheiden. Entscheiden Sie sich für die stochastische Berechnung, müssen Sie in den nächsten Prozessschritten die Anzahl der einzubeziehenden vergangenen Perioden, sowie die Cashflows aus den vergangenen Perioden angeben (Bei Auswahl des Adjusted-Present-Value Verfahrens unter anderem auch das Fremdkapital). Haben Sie sich hingegen für die deterministische Eingabe entschieden, müssen Sie Ihre zukünftigen Cashflows für die in die Berechnung einbezogenen zukünftigen Jahre angeben.");
        		Label infoText3 = new Label  ("<h3>Berechnungsmethode:</h3>");
        		infoText3.setContentMode(Label.CONTENT_XHTML);
        		Label infoText4 = new Label ("Hier können Sie die Berechnungsmethode wählen. Bei der Adjusted-Present-Value Methode berechnet sich der Unternehmenswert aus dem Wert des rein eigenfinanzierten Unternehmens zuzüglich der Steuervorteile durch das verzinsliche Fremdkapital, abzüglich des verzinslichen Fremdkapitals. Die Flow-to-Equity Methode diskontiert alle zukünftigen Cashflows auf einen Stichtag und addiert den diskontierten Restwert. Folglich betrachtet dieses Verfahren für die Berechnung des Unternehmenswertes ausschließlich das Eigenkapital. Die Zeitreihenanalyse verwendet zur Berechnung ebenfalls die APV-Methode.");
        		Label infoText5 = new Label  ("<h3>Eingabemethode:</h3>");
        		infoText5.setContentMode(Label.CONTENT_XHTML);
        		Label infoText6 = new Label ("Hier haben Sie die Wahl zwischen der „Eingabe der Cashflows“ und einer „direkten-“ oder „indirekten Berechnung der Cashflows“ auf Basis der Bilanz, sowie Gewinn- und Verlustrechnung (GuV). Bei der „Eingabe der Cashflows“ muss der Benutzer die Cashflows und den Bilanzwert des Fremdkapitals eingeben. Zur „direkten Berechnung des Cashflows“ werden bestimmte Posten aus der Bilanz und GuV benötigt, welche für jede Periode eingegeben werden müssen. Die „indirekte Berechnung des Cashflows“ unterscheidet sich durch die benötigten Posten von der „direkten Berechnung“.");
        		
        		infoBox.addComponent(infoText1);
        		infoBox.addComponent(infoText2);
        		infoBox.addComponent(infoText3);
        		infoBox.addComponent(infoText4);
        		infoBox.addComponent(infoText5);
        		infoBox.addComponent(infoText6);
        		
        		setSecondComponent(infoBox);
                
        }

        @Override
        public void showMethod(AbstractStochasticMethod method) {
                methods_stochastic.addItem(method);
                methods_stochastic.setItemEnabled(method, method.getImplemented());
                
                if (method.getSelected()){
                        methods_stochastic.select(method);
                }

        }

        @Override
        public void showMethod_deterministic(AbstractDeterministicMethod method) {
                methods_deterministic.addItem(method);
                methods_deterministic.setItemEnabled(method, method.getImplemented());
                
                if (method.getSelected()){
                        methods_deterministic.select(method);
                }

        }
        
        @Override
        public void enableOptions() {
                
                
        }

        @Override
        public void enableMethodSelection(Boolean state) {
                methods_stochastic.setEnabled(state);
        }
        
        @Override
        public void enableMethod_deterministicSelection(Boolean state) {
                methods_deterministic.setEnabled(state);
                
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
        
        @Override
        public void showInputMethod_deterministicSelection(Boolean deterministicBool, Boolean checked) {
        
                if (deterministicBool){
                        detInput.setVisible(checked);
                }
                else{
                        stoInput.setVisible(checked);
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
                //this.methods.setComponentError(new UserError("Wenn stochastisch gew\u00E4hlt wurde, muss mindestens eine Methode ausw\u00E4hlt werden"));
                this.methods_stochastic.setComponentError(new UserError("Es muss mindestens eine Methode ausw\u00E4hlt werden"));
                }
                else {
                        this.methods_stochastic.setComponentError(null);
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
