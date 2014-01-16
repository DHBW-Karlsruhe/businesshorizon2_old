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

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.Period.Period;

/**
 * Dies ist die Vaadin-Implementierung der PeriodListView. Die Rahmendarstellung
 * wird in der generateUi Methode visuell dargestellt. Die einzelnen Projekte
 * mit Namen, Perioden, Aenderungsdatum und Loeschmethode werde in den Methoden
 * setProjects und generateSingleProject erzeugt. Die Click-Events verschiedener
 * werden gesammelt in der Methode buttonClick verwertet und falls Logik von
 * noeten ist vom Presenter weiter ausgefuehrt. Die Auswahl eines Projekts wird
 * hier mittels der layoutClick Methode realisiert und fuehrt zum
 * Projekt-Wizard.
 * 
 * @author Christian Scherer, Mirko Göpfrich
 * 
 */
public class ProjectListViewImpl extends VerticalSplitPanel implements
		ProjectListViewInterface, Button.ClickListener, ClickListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("ProjectListViewImpl.class");

	@Autowired
	private ProjectListPresenter presenter;

	private Project project;
	private List<Project> projects;
	
	NavigableSet<Period> periodList;

	Iterator<Project> iterator;
	
	private HorizontalLayout projectListHead;
	private VerticalLayout projectListPanel;
	
	private Panel singleProject;
	private List<Panel> singleProjectPanelList;
	

	private Button addProjectBtn;
	private Button dialogAddBtn;
	
	private Button removeButton;
	private List<Button> removeButtonList;
	
	private Button editButton;
	private List<Button> editButtonList;
	
	private Button dialogEditBtn;
	private List<Button> dialogEditBtnList;

	private Label projectName;
	private Label periods;
	private Label lastChanged;
	private Label title;

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
		generateUI();
		logger.debug("Initialisierung beendet");
	}

	/**
	 * Konkrete Ausprogrammierung der festen UI Elemente (Ueberschrift, leeres
	 * ProjectListPanel und Hinzufuegebutton). Erst spaeter wird durch die
	 * Methode setProjects das ProjectListPanel mit konkreten Projekten
	 * gefuellt.
	 * 
	 * @author Mirko Göpfrich
	 */
	private void generateUI() {
		setMargin(false);
		
		//Teilt die View vertikal in zwei Bereiche auf und erstellt eine horizontale Trennlinie (nicht verstellbar).
		setSizeFull();
		setSplitPosition(60, Sizeable.UNITS_PIXELS);
		setLocked(true);
		logger.debug("Neues Vertikales SplitPanel erstellt");
		this.
		
		//Layout für oberes Panel erstellen
		projectListHead = new HorizontalLayout();
		projectListHead.setSizeFull();
		setFirstComponent(projectListHead);
		
		//Layout für unteres Panel erstellen
		projectListPanel = new VerticalLayout();
		setSecondComponent(projectListPanel);
		projectListPanel.setSpacing(false);

		logger.debug("Leeres ProjektList Panel erstellt");

		//Überschrift im oberen Panel hinzufügen
		title = new Label("<h1>Meine Projekte</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		projectListHead.addComponent(title);
		logger.debug("Ueberschrift erstellt");
		
		//Hinzufügen-Button im oberen Panel hinzufügen
		addProjectBtn = new Button("Projekt hinzufügen", this);
		projectListHead.addComponent(addProjectBtn);
		logger.debug("Hinzufuege-Button erzeugt");
		projectListHead.setComponentAlignment(addProjectBtn, Alignment.MIDDLE_RIGHT);

		logger.debug("Feste UI Elemente dem Fenster hinzugefuegt");
	}

	/**
	 * 
	 * Aufruf durch den Presenter (bei Ersterstellung oder Aenderungen durch
	 * Buttonclicks) - wobei zunächst die Projektliste aktualisiert wird.
	 * Zunaechst werden - falls vorhanden - die derzeitg existenten Elemente des
	 * projectListPanel geloescht und die Liste der Projekt Layouts und
	 * Loeschbuttons neu erstellt. Darauf folgt dann in der Schleife die
	 * Erzeugung der einzelnen VadinKomponenten fuer jedes Projekt durch die
	 * Methode generateSingleProjectUi.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich
	 */
	@Override
	public void setProjects(List<Project> projects) {

		this.projects = projects;
		logger.debug("Projektliste aktualisiert");
		projectListPanel.removeAllComponents();
		logger.debug("Projekt-Element-Liste geleert");

		singleProjectPanelList = new ArrayList<Panel>();
		
		//?
		removeButtonList = new ArrayList<Button>();
		editButtonList = new ArrayList<Button>();
		dialogEditBtnList = new ArrayList<Button>();
	

		for (int i = 0; i < projects.size(); i++) {
			project = projects.get(i);
			
			singleProjectPanelList.add(generateSingleProjectUI(project, i));
			projectListPanel.addComponent(singleProjectPanelList.get(i));
			//projectListPanel.setComponentAlignment(singleProjectPanelList.get(i), Alignment.MIDDLE_CENTER);

		}

		logger.debug("Projekt-Element-Liste erzeugt");
	}

	/**
	 * Konkrete Ausprogrammierung der der Darstellung eines einzlenen Projekts
	 * (Name, Anzahl Perioden mit Jahren, Aenderungsdatum, Loeschbutton). Diese
	 * wird sowohl bei der ersten Erstellung des UIs fuer jedes Projekt
	 * ausgefuehrt. Die Loeschbuttons werden einer Liste an Loeschbuttons
	 * hinzufgefuegt umd spaeter eine identifikation der Buttons in der Methode
	 * buttonClick zu gewaehrleisten. Zum Schluss wird dem Layout noch ein
	 * Listener hinzugefuegt, der durch die Methode LayoutClick auf Klicks auf
	 * ein jeweiliges Projekt reagiert und in die Prozesssicht des einzelnen
	 * Projekts wechselt und das VerticalLayout dem projectListPanel
	 * hinzgefuegt.
	 * 
	 * @author Christian Scherer, Mirko Göpfrich
	 * @param project
	 *            das darzustellende Projekt und der aktuelle Index der Liste
	 * @param i
	 *            der Index der zu erstellenden Komponente (besonders fuer den
	 *            Loeschbutton relevant)
	 * @return ein VerticalLayout Objekt, das zur Eingliederung in das UI dient
	 */
	private Panel generateSingleProjectUI(Project project, int i) {
		
		//erzeugt eine Panel für ein Projekt
		singleProject = new Panel(project.getName());
		
		//Legt ein Layout für das Projekt-Panel fest
		HorizontalLayout panelContent = new HorizontalLayout();
		panelContent.setSizeFull();
		singleProject.setContent(panelContent);
		panelContent.addStyleName("projectListPanel");
		
		//Legt ein linkes und ein rechtes Layout innerhalb des Panels an.
		VerticalLayout panelContentLeft = new VerticalLayout();
		HorizontalLayout panelContentRight = new HorizontalLayout();
		panelContent.addComponent(panelContentLeft);
		panelContent.addComponent(panelContentRight);
		panelContent.setComponentAlignment(panelContentLeft, Alignment.BOTTOM_LEFT);
		panelContent.setComponentAlignment(panelContentRight, Alignment.BOTTOM_RIGHT);

		periodList = (NavigableSet<Period>) project.getPeriods();

		// String fuer saubere Periodenausgabe erstellen. Bsp:
		// "3 Perioden (2009-2012)"
		String periodString;
		switch (periodList.size()) {
		case (0):
			periodString = "Noch keine Perioden eingetragen";
			break;
		case (1):
			periodString = "1 Periode (" + periodList.first().getYear() + ")";
			break;
		default: // also Anzahl der Perioden groesser 1
			periodString = "" + periodList.size() + " Perioden ("
					+ periodList.first().getYear() + "-"
					+ periodList.last().getYear() + ")";
			break;
		}
		periods = new Label(periodString);

		// String fuer Ausgabe des letzten Aenderungsdatum
		String lastChangedString;
		if (project.getLastChanged() == null) {
			Date d = new Date();
			lastChangedString = "Zuletzt geändert: " + d.toString();
		} else {
			lastChangedString = "Zuletzt geädert: "
					+ project.getLastChanged().toString();
		}
		lastChanged = new Label(lastChangedString);

		//Buttons erstellen
		removeButton = new Button("", this);
		editButton = new Button("", this);
		removeButton.addStyleName("borderless");
		editButton.addStyleName("borderless");
		removeButton.setIcon(new ThemeResource("images/icons/trash.png"));
		editButton.setIcon(new ThemeResource("images/icons/pen.png"));
		
		dialogEditBtn = new Button("Speichern", this);
		
		// Liste für Buttons zur spaeteren Identifikation
		removeButtonList.add(removeButton);
		editButtonList.add(editButton);
		dialogEditBtnList.add(dialogEditBtn);
		logger.debug("editButtonList.size():" + editButtonList.size());

		//Inhalt dem Panel hinzufügen
		panelContentLeft.addComponent(periods);
		panelContentLeft.addComponent(lastChanged);
		panelContentRight.addComponent(removeButtonList.get(i));
		panelContentRight.addComponent(editButtonList.get(i));
		logger.debug("Edit-Button " + i + " hinzugefuegt");
		
		
		//CLick-Listener für das Panel hinzuüfgen
		singleProject.addListener(this);

		projectListPanel.addComponent(singleProject);
		logger.debug("Einzelnes Projektelement erzeugt");

		return singleProject;
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
		addDialog.setWidth(440, UNITS_PIXELS);
		addDialog.setResizable(false);
		addDialog.setDraggable(false);

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);

		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(false);
		formLayout.setSpacing(false);
		
		//TextFeld für Name dem Formular hinzufügen
		tfName = new TextField();
		tfName.setCaption("Bitte Namen wählen:");
		tfName.setRequired(true);
		tfName.addValidator(new StringLengthValidator(
				"Der Projektname muss zwischen 2 und 20 Zeichen lang sein.", 2,
				20, false));
		tfName.setRequiredError("Pflichtfeld");
		formLayout.addComponent(tfName);
		
		//TextArea für Beschreibung dem Formular hinzufügen
		taDescription = new TextArea();
		taDescription.setCaption("Beschreibung");
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
		addDialog = new Window("Projekt bearbeiten");
		addDialog.setModal(true);
		addDialog.setWidth(440, UNITS_PIXELS);
		addDialog.setResizable(false);
		addDialog.setDraggable(false);

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);

		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(false);
		formLayout.setSpacing(true);
		
		//TextFeld für Name dem Formular hinzufügen
		tfName = new TextField();
		tfName.setCaption("Name ändern:");
		tfName.setRequired(true);
		tfName.addValidator(new StringLengthValidator(
				"Der Projektname muss zwischen 2 und 20 Zeichen lang sein.", 2,
				20, false));
		tfName.setRequiredError("Pflichtfeld");
		tfName.setValue(project.getName());
		formLayout.addComponent(tfName);
		
		//TextArea für Beschreibung dem Formular hinzufügen
		taDescription = new TextArea();
		taDescription.setCaption("Beschreibung ändern:");
		taDescription.setValue(project.getDescription());
		formLayout.addComponent(taDescription);
		
		//Formular dem Layout hinzufügen
		layout.addComponent(formLayout);
		
		//Speichern-Button erstllen und dem Layout hinzufügen
		layout.addComponent(dialogEditBtn);
		
		//Layout dem Dialog-Fenster hinzufügen
		addDialog.addComponent(layout);

		//Dialog dem Hauptfenster hinzufügen
		getWindow().addWindow(addDialog);
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
				getWindow().removeWindow(addDialog);
				logger.debug("Projekt-hinzufügen Dialog geschlossen");
			} else {
				getWindow()
						.showNotification(
								"",
								"Projektname ist ein Pflichtfeld. Bitte geben Sie einen Projektnamen an",
								Notification.TYPE_ERROR_MESSAGE);
			}

		} else if (event.getButton() == editButton){
			final int index = editButtonList.indexOf(event.getButton());
			
			logger.debug("Projekt-bearbeiten Button aus dem Hauptfenster aufgerufen. Projektnummer: "
					+ (index + 1));
			logger.debug("editButtonList.size():" + editButtonList.size());
			presenter.editProjectDialog(projects.get(index));
			
		} else if (event.getButton() == dialogEditBtn){	
			final int index2 = dialogEditBtnList.indexOf(event.getButton());
			logger.debug("dialogEditBtnList.size() " + dialogEditBtnList.size());
			logger.debug("INDEX: " + index2);
			logger.debug("projects.size(): " + projects.size());
			
			if (tfName.isValid()) {
				logger.debug("tfName: " + (String) tfName.getValue());
				logger.debug("taDescription: " + (String) taDescription.getValue());
				presenter.editProject(projects.get(index2), (String) tfName.getValue(), (String) taDescription.getValue());
				getWindow().removeWindow(editDialog);
				logger.debug("Projekt-bearbeiten Dialog geschlossen");
				
			} else {
				getWindow()
						.showNotification(
								"",
								"Projektname ist ein Pflichtfeld. Bitte geben Sie einen Projektnamen an",
								Notification.TYPE_ERROR_MESSAGE);
			}
			
		} else if (event.getButton() == removeButton){	 

			final int index3 = removeButtonList.indexOf(event.getButton());
			
			logger.debug("removeButtonList.indexOf(event.getButton()): " + removeButtonList.indexOf(event.getButton()));
			logger.debug("removeButtonList.size(): " + removeButtonList.size());
			logger.debug("Projekt-loeschen Button aus dem Hauptfenster aufgerufen. Projektnummer: "
					+ (index3 + 1));

			ConfirmDialog.show(getWindow(), projects.get(index3).getName()
					+ " löschen?", "Wollen sie das Projekt wirklich löschen?",
					"Ja", "Nein", new ConfirmDialog.Listener() {
						@Override
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								presenter.removeProject(projects.get(index3));
							} else {

							}
						}
					});
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
	public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		// TODO Auto-generated method stub
		int index = singleProjectPanelList.indexOf(event.getComponent());
		logger.debug("Projekt ausgewaehlt. Projektnummer: " + (index + 1));
		presenter.projectSelected(projects.get(index));
	}
}
