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

package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.dialogs.ConfirmDialog;

import com.mvplite.event.EventBus;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Dies ist die Vaadin-Implementierung der Projektliste.
 * Sie erweitert ein VerticalLayout und beinhaltet alle Projekte als Komponenten.
 * Die View wird in den linken Bereich des horizontalen SplitPanel in der initialScreenView
 * eingefügt. Die Projektdetails sind nicht Bestandteil dieser View.
 * 
 * @author Christian Scherer, Mirko Göpfrich, Marco Glaser
 * 
 */
public class ProjectListViewImpl extends VerticalLayout implements
		ProjectListViewInterface, Button.ClickListener, ClickListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ProjectListViewImpl.class");

	@Autowired
	private ProjectListPresenter presenter;
	
	@Autowired
	private EventBus eventBus;

	private Project project;
	private List<Project> projects;
	
	NavigableSet<Period> periodList;

	Iterator<Project> iterator;
	
	private VerticalLayout singleProject;
	private List<VerticalLayout> singleProjectList;
	
	//Buttons, um ein Projekt hinzuzufügen
	private Button addProjectBtn;
	private Button dialogAddBtn;
	
	//Buttons, um ein Projekt zu bearbeiten
	private Button dialogEditBtn;
	
	private List<Button> editBtnList;	
	private int indexEditBtn;
	private List<Button> dialogEditBtnList;
	
	private List<Button> removeBtnList;

	private TextField tfName;
	private TextArea taDescription;

	private Window addDialog;
	private Window editDialog;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten. Danach wird die durch die
	 * generateUi Methode die festen Bestandteile der Projektliste erzeugt
	 * (Ueberschrift, leeres ProjectListPanel und Hinzufuegebutton)
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
		logger.debug("Initialisierung beendet");
	}
	
	public void generateUi() {
		setWidth(85, UNITS_PERCENTAGE);
		setHeight(Sizeable.SIZE_UNDEFINED, 0);
	}

	/**
	 * 
	 * Aufruf durch den Presenter (bei Ersterstellung oder Aenderungen durch
	 * Buttonclicks) - wobei zunächst die Projektliste aktualisiert wird.
	 * Zunaechst werden - falls vorhanden - die derzeitg existenten der View gelöscht. 
	 * Darauf folgt dann in der Schleife die Erzeugung der einzelnen VadinKomponenten 
	 * fuer jedes Projekt durch die
	 * Methode generateSingleProjectUi.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich, Marco Glaser
	 */
	@Override
	public void setProjects(List<Project> projects) {

		this.projects = projects;
		logger.debug("Projektliste aktualisiert");
		removeAllComponents();
		logger.debug("Projekt-Element-Liste geleert");

		singleProjectList = new ArrayList<VerticalLayout>();
		
		//?
		removeBtnList = new ArrayList<Button>();
		editBtnList = new ArrayList<Button>();
		dialogEditBtnList = new ArrayList<Button>();

		for (int i = 0; i < projects.size(); i++) {
			project = projects.get(i);
			
			singleProjectList.add(generateSingleProjectUI(project, i));
			addComponent(singleProjectList.get(i));
			//projectListPanel.setComponentAlignment(singleProjectPanelList.get(i), Alignment.MIDDLE_CENTER);

		}

		logger.debug("Projekt-Element-Liste erzeugt");
	}
	
	public void clearProjectList(){
		removeAllComponents();
	}

	/**
	 * Konkrete Ausprogrammierung der Darstellung eines einzelnen Projekts.
	 * Ein Projekt wird durch ein VerticalLayout dargestellt, das ein Label
	 * mit dem Projektname enthält. Außerdem bekommt es einen ClickListener,
	 * um ein Projekt als selektiert zu kennzeichnen und die Details zum Projekt
	 * anzuzeigen.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich, Marco Glaser
	 * @param project
	 *            das darzustellende Projekt und der aktuelle Index der Liste
	 * @param i
	 *            der Index der zu erstellenden Komponente (besonders fuer den
	 *            Loeschbutton relevant)
	 * @return ein VerticalLayout Objekt, das zur Eingliederung in das UI dient
	 */
	private VerticalLayout generateSingleProjectUI(Project project, int i) {
		
		final Project proj = project;
		final int a = i;
		//erzeugt eine Panel für ein Projekt
		singleProject = new VerticalLayout();
		HorizontalLayout container = new HorizontalLayout();
		container.setSizeFull();
		if(i == 0){
			singleProject.setStyleName("singleProjectSelected");
			presenter.projectSelected(project);
		}
		else{
			singleProject.setStyleName("singleProject");	
		}
		Embedded icon = new Embedded(null, new ThemeResource("./images/icons/newIcons/1418828714_editor_open_folder-128.png"));
		icon.setHeight(40, UNITS_PIXELS);
		icon.setWidth(40, UNITS_PIXELS);
		Label gap1 = new Label();
		Label gap2 = new Label();
		Label gap3 = new Label();
		gap1.setWidth("15px");
		gap2.setWidth("15px");
		gap3.setSizeFull();
		Label projectName = new Label(project.getName());
		projectName.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		projectName.setHeight(Sizeable.SIZE_UNDEFINED, 0);
		projectName.setStyleName("projectName");
		
		//Legt ein Layout für das Projekt-Panel fest
		//panelContent.setSizeFull();
		container.addComponent(gap1);
		container.addComponent(icon);
		container.addComponent(gap2);
		container.addComponent(projectName);
		container.addComponent(gap3);
		container.setExpandRatio(gap3, 1.0f);
		
		singleProject.addComponent(container);
		singleProject.setWidth(100, UNITS_PERCENTAGE);
		singleProject.setHeight(70, UNITS_PIXELS);
		container.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
		container.setComponentAlignment(projectName, Alignment.MIDDLE_CENTER);

		singleProject.addListener(new LayoutClickListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				presenter.projectSelected(proj);
				switchProjectsStyle(a);
				
				eventBus.fireEvent(new SelectProjectEvent());
			}
			
		});

//		singleProject.addListener(this);
//		projectListPanel.addComponent(singleProject);
		logger.debug("Einzelnes Projektelement erzeugt");

		return singleProject;
	}
	
	/**
	 * Diese Methode ändert die Hintergrundfarben der Projekte.
	 * Das selektierte Projekt wird hervorgehoben.
	 * 
	 * @param i
	 * : index des selektierten Projekts
	 * 
	 * @author Marco Glaser
	 */
	public void switchProjectsStyle(int i){
		int counter = 0;
		Iterator<VerticalLayout> iter = singleProjectList.iterator();
		VerticalLayout projectLayout;
		while(iter.hasNext()){
			projectLayout = iter.next();
			if(counter == i){
				projectLayout.setStyleName("singleProjectSelected");
			}
			else{
				projectLayout.setStyleName("singleProject");
			}
			counter++;
		}
	}

	/**
	 * Zeige das Projekt-Hinzufuegen-Dialogfenster, bei dem ein Eingabefeld fuer
	 * den Namen des Projekts und ein Hinzfuege-Button vorhanden ist. Funktion
	 * bei geklicktem Button siehe Clicklistener in dieser Klasse. Das
	 * horizontale Layout zur Darstellung besitzt ein Formlayout und den Button,
	 * die nebeneinander dargestellt werden.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich
	 */
	@Override
	public void showAddProjectDialog() {
		addDialog = new Window("Projekt hinzufügen");
		addDialog.setModal(true);
		addDialog.setWidth(410, UNITS_PIXELS);
		addDialog.setResizable(false);
		addDialog.setDraggable(false);

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(false);

		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		formLayout.setSpacing(true);
		
		//TextFeld für Name dem Formular hinzufügen
		tfName = new TextField("Name wählen:");
		tfName.setRequired(true);
		tfName.addValidator(new StringLengthValidator(
				"Der Projektname muss zwischen 2 und 20 Zeichen lang sein.", 2,
				20, false));
		tfName.setRequiredError("Pflichtfeld");
		tfName.setSizeFull();
		formLayout.addComponent(tfName);
		
		//TextArea für Beschreibung dem Formular hinzufügen
		taDescription = new TextArea("Beschreibung wählen");
		taDescription.setSizeFull();
		formLayout.addComponent(taDescription);
		
		//Formular dem Layout hinzufügen
		layout.addComponent(formLayout);
		
		//Hinzufüge-Button erstllen und dem Layout hinzufügen
		dialogAddBtn = new Button("Hinzufügen", this);
		layout.addComponent(dialogAddBtn);
		
		//Layout dem Dialog-Fenster hinzufügen
		addDialog.addComponent(layout);

		//Dialog dem Hauptfenster hinzufügen
		getWindow().addWindow(addDialog);
		logger.debug("Hinzufuege-Dialog erzeugt");
	}
	
	/**Methode zur Implementierung des Dialogfensters für Projekt-Änderungen.
	 * 
	 */
	@Override
	public void showEditProjectDialog(Project project) {
		editDialog = new Window("Projekt bearbeiten");
		editDialog.setModal(true);
		editDialog.setWidth(410, UNITS_PIXELS);
		editDialog.setResizable(false);
		editDialog.setDraggable(false);


		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);

		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		formLayout.setSpacing(true);
		
		//TextFeld für Name dem Formular hinzufügen
		tfName = new TextField("Name ändern:", project.getName());
		tfName.setRequired(true);
		tfName.addValidator(new StringLengthValidator(
				"Der Projektname muss zwischen 2 und 20 Zeichen lang sein.", 2,
				20, false));
		tfName.setRequiredError("Pflichtfeld");
		tfName.setSizeFull();
		formLayout.addComponent(tfName);
		
		//TextArea für Beschreibung dem Formular hinzufügen
		taDescription = new TextArea("Beschreibung ändern:", project.getDescription());
		taDescription.setSizeFull();
		formLayout.addComponent(taDescription);
		
		//Formular dem Layout hinzufügen
		layout.addComponent(formLayout);
		
		//Speichern-Button erstllen und dem Layout hinzufügen
		//TODO: ist das korrekt?
		dialogEditBtn = new Button("Speichern");
		layout.addComponent(dialogEditBtn);
		
		dialogEditBtn.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
					
				if (tfName.isValid()) {
					boolean succed = presenter.editProject(projects.get(indexEditBtn), (String) tfName.getValue(), (String) taDescription.getValue());
					if (succed) {
						getWindow().removeWindow(editDialog);
						logger.debug("Projekt-bearbeiten Dialog geschlossen");
						
					}
						
				} else {
					getWindow().showNotification(
						"",
						"Projektname ist ein Pflichtfeld. Bitte geben Sie einen Projektnamen an",
						Notification.TYPE_ERROR_MESSAGE);
				}		
			}
		});
		
		//Layout dem Dialog-Fenster hinzufügen
		editDialog.addComponent(layout);

		//Dialog dem Hauptfenster hinzufügen
		getWindow().addWindow(editDialog);
		logger.debug("Bearbeiten-Dialog erzeugt");
		
	}

	/**
	 * ClickListner Methode fuer die Reaktion auf Buttonclicks. Hier wird
	 * entsprechend auf die Button-Clicks fuer das Erzeugen weiterer Projekte
	 * reagiert, wie auch auf jene die Projekte loeschen. In der ersten
	 * If-Abfrage werden die vom Hauptfenster ausgeloeten Clicks zum Hinzufuegen
	 * eines neuen Objektes behandelt, in der zweiten If-Abfrage wird die im
	 * Dialogfenster ausgeloesten Clickst behandelt (Hierbei wird noch geprueft
	 * ob das auf "required" gesetzte Textfeld auch ausgefuellt wurde - falls
	 * nicht wird eine Fehlermeldung angezeigt) und in der Else-Verzweigung dann
	 * die Loesch-Clicks fuer das jeweilige Projekt behandelt. Hierbei wird
	 * zunÃ¤chst durch das Event in der Loesch-Buttonliste der Index
	 * identifiziert, also welches Projekt zu loeschen ist. Die jeweils folgende
	 * Logid ist in der je aufgerufen Methode des Presenters zu finden.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich
	 * @param event
	 *            Klick-event des Buttons
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		
		if (event.getButton() == addProjectBtn) {
			logger.debug("Projekt-hinzufügen Button aus dem Hauptfenster aufgerufen");
			presenter.addProjectDialog();

		} else if (event.getButton() == dialogAddBtn) {
			logger.debug("Projekt-hinzufügen Button aus dem Dialogfenster aufgerufen");

			if (tfName.isValid()) {
				presenter.addProject((String) tfName.getValue(), (String) taDescription.getValue());
				//TODO: Fenster nur schließen, wenn das Hinzufügen erfolgreich war (s. Projekt Bearbeiten).
				getWindow().removeWindow(addDialog);
				logger.debug("Projekt-hinzufügen Dialog geschlossen");
			} else {
				getWindow()
						.showNotification(
								"",
								"Projektname ist ein Pflichtfeld. Bitte geben Sie einen Projektnamen an",
								Notification.TYPE_ERROR_MESSAGE);
			}	
		}
	}

	/**
	 * LayoutClickListner Methode fuer die Reaktion auf Clicks auf die einzelnen
	 * Projekte reagiert. Konkret wird hier die Verbindung zur Prozesssicht
	 * geschaffen. Zunaechst wird geprueft von welchem Projekt der Klick kommt,
	 * und dann dieses dem Presenter uebergeben, in welchem dann das Event fuer
	 * das Anzeigen der Prozesssicht ausgeloest wird.
	 * 
	 * @author Christian Scherer
	 * @param event
	 *            - Event des Layoutclicks
	 */

	@Override
	public void click(MouseEvents.ClickEvent event) {
		int index = singleProjectList.indexOf(event.getComponent());
		logger.debug("Projekt ausgewaehlt. Projektnummer: " + (index + 1));
		presenter.projectSelected(projects.get(index));
	}

	public void showErrorMessage(String message) {
		Window.Notification notif = new Notification((String) "",
				message, Notification.TYPE_WARNING_MESSAGE);
		notif.setPosition(Window.Notification.POSITION_CENTERED_TOP);
		getWindow().showNotification(notif);
	}
	
}
