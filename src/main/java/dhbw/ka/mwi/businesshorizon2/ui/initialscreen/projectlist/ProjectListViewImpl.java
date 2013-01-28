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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

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
 * @author Christian Scherer
 * 
 */
public class ProjectListViewImpl extends VerticalLayout implements
		ProjectListViewInterface, Button.ClickListener, LayoutClickListener {

	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger("ProjectListViewImpl.class");

	@Autowired
	private ProjectListPresenter presenter;

	private List<Project> projects;

	NavigableSet<Period> periodList;

	private Project project;

	Iterator<Project> iterator;

	private VerticalLayout projectListPanel;
	private List<VerticalLayout> singleProjectPanel;

	private Button addProjectBtn;
	private List<Button> removeProjectBtn;
	private Button dialogAddBtn;

	private Label projectName;
	private Label periods;
	private Label lastChanged;
	private Label title;

	private TextField tfName;

	private Window addDialog;

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

	/**
	 * Konkrete Ausprogrammierung der festen UI Elemente (Ueberschrift, leeres
	 * ProjectListPanel und Hinzufuegebutton). Erst spaeter wird durch die
	 * Methode setProjects das ProjectListPanel mit konkreten Projekten
	 * gefuellt.
	 * 
	 * @author Christian Scherer
	 */
	private void generateUi() {
		setSpacing(true);
		setMargin(true);

		title = new Label("<h1>Meine Projekte</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		addComponent(title);
		logger.debug("Ueberschrift erstellt");

		projectListPanel = new VerticalLayout();
		projectListPanel.setSpacing(true);
		addComponent(projectListPanel);
		logger.debug("Leeres ProjektList Panel erstellt");

		addProjectBtn = new Button("Projekt hinzufügen", this);
		addComponent(addProjectBtn);
		logger.debug("Hinzufuege-Button erzeugt");

		logger.debug("Feste UI Elemente dem Fenster hinzugefuegt");

	}

	/**
	 * 
	 * Aufruf durch den Presenter (bei Ersterstellung oder Aenderungen durch
	 * Buttonclicks) - wobei zunÃ¤chst die Projektliste aktualisiert wird.
	 * Zunaechst werden - falls vorhanden - die derzeitg existenten Elemente des
	 * projectListPanel geloescht und die Liste der Projekt Layouts und
	 * Loeschbuttons neu erstellt. Darauf folgt dann in der Schleife die
	 * Erzeugung der einzelnen VadinKomponenten fuer jedes Projekt durch die
	 * Methode generateSingleProjectUi.
	 * 
	 * @author Christian Scherer
	 */
	@Override
	public void setProjects(List<Project> projects) {

		this.projects = projects;
		logger.debug("Projektliste aktualisiert");
		projectListPanel.removeAllComponents();
		logger.debug("Projekt-Element-Liste geleert");

		singleProjectPanel = new ArrayList<VerticalLayout>();
		removeProjectBtn = new ArrayList<Button>();

		for (int i = 0; i < projects.size(); i++) {
			project = projects.get(i);

			singleProjectPanel.add(generateSingleProjectUi(project, i));
			projectListPanel.addComponent(singleProjectPanel.get(i));

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
	 * @author Christian Scherer
	 * @param project
	 *            das darzustellende Projekt und der aktuelle Index der Liste
	 * @param i
	 *            der Index der zu erstellenden Komponente (besonders fuer den
	 *            Loeschbutton relevant)
	 * @return ein VerticalLayout Objekt, das zur Eingliederung in das UI dient
	 */
	private VerticalLayout generateSingleProjectUi(Project project, int i) {

		VerticalLayout singleProject = new VerticalLayout();

		projectName = new Label(project.getName());
		periodList = (NavigableSet<Period>) project.getPeriods();

		// String fuer saubere Periodenausgebe erstellen. Bsp:
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

		// Liste an Buttons zur spaeteren identifikation
		removeProjectBtn.add(new Button("löschen", this));

		singleProject.addComponent(projectName);
		singleProject.addComponent(periods);
		singleProject.addComponent(lastChanged);
		singleProject.addComponent(removeProjectBtn.get(i));

		singleProject.addListener(this);

		projectListPanel.addComponent(singleProject);
		logger.debug("Einzelnes Projektelement erzeugt");

		return singleProject;
	}

	/**
	 * Zeige das Projekt-Hinzuegen-Dialogfenster, bei dem ein Eingabefeld fuer
	 * den Namen des Projekts und ein Hinzfuege-Button vorhanden ist. Funktion
	 * bei geklicktem Button siehe Clicklistener in dieser Klasse. Das
	 * horizontale Layout zur Darstellung besitzt ein Formlayout und den Button,
	 * die nebeneinander dargestellt werden.
	 * 
	 * @author Christian Scherer
	 */
	@Override
	public void showAddProjectDialog() {
		addDialog = new Window("Projekt hinzufügen");
		addDialog.setModal(true);
		addDialog.setWidth(430, UNITS_PIXELS);
		addDialog.setResizable(false);
		addDialog.setDraggable(false);

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);

		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(false);
		formLayout.setSpacing(false);

		tfName = new TextField();
		tfName.setCaption("Bitte Namen wählen:");
		tfName.setRequired(true);
		tfName.addValidator(new StringLengthValidator(
				"Der Projektname muss zwischen 2 und 20 Zeichen lang sein.", 2,
				20, false));
		tfName.setRequiredError("Pflichtfeld");
		formLayout.addComponent(tfName);
		layout.addComponent(formLayout);

		dialogAddBtn = new Button("Hinzufügen", this);
		layout.addComponent(dialogAddBtn);

		addDialog.addComponent(layout);

		getWindow().addWindow(addDialog);
		logger.debug("Hinzufuege-Dialog erzeugt");

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
	 * @author Christian Scherer
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
				presenter.addProject((String) tfName.getValue());
				getWindow().removeWindow(addDialog);
			} else {
				getWindow()
						.showNotification(
								"",
								"Projektname ist ein Pflichtfeld. Bitte geben Sie einen Projektnamen an",
								Notification.TYPE_ERROR_MESSAGE);
			}

		} else {

			final int index = removeProjectBtn.indexOf(event.getButton());

			logger.debug("Projekt-loeschen Button aus dem Hauptfenster aufgerufen. Projektnummer: "
					+ (index + 1));

			ConfirmDialog.show(getWindow(), projects.get(index).getName()
					+ " löschen?", "Wollen sie das Projekt wirklich löschen?",
					"Ja", "Nein", new ConfirmDialog.Listener() {
						@Override
						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								presenter.removeProject(projects.get(index));
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
	public void layoutClick(LayoutClickEvent event) {

		int index = singleProjectPanel.indexOf(event.getComponent());
		logger.debug("Projekt ausgewaehlt. Projektnummer: " + (index + 1));
		presenter.projectSelected(projects.get(index));

	}

}
