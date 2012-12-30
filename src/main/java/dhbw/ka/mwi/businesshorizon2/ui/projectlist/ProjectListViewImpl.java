package dhbw.ka.mwi.businesshorizon2.ui.projectlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Project;

/**
 * Dies ist die Vaadin-Implementierung der PeriodListView. Die Projekte des
 * Users werden in der generateUi Methode visuell dargestellt.
 * 
 * @author Christian Scherer
 * 
 */
public class ProjectListViewImpl extends VerticalLayout implements
		ProjectListViewInterface, Button.ClickListener, LayoutClickListener {

	private static final long serialVersionUID = 1L;
	

	@Autowired
	private ProjectListPresenter presenter;

	private List projects;

	NavigableSet<Period> periodList;

	private Project project;

	Iterator<Project> iterator;

	private VerticalLayout projectListPanel;
	private List singleProjectPanel;

	private Button addProjectBtn;
	private List removeProjectBtn;
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
	 * und initialisiert die View-Komponenten. Zuvor werden noch die
	 * anzuzeigenden Projekte des Anwenders geladen.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);

		// 2 Dummyprojects die dem User hinzugefügt werden
		Project p = new Project("Chris");
		p.setLastChanged(new Date());
		presenter.addProject(p);
		p = new Project("Chris2");
		p.setLastChanged(new Date());
		presenter.addProject(p);

		projects = presenter.getProjects();
		generateUi();

	}

	/**
	 * Konkrete Ausprogrammierung der UI Elemente. Die Notation ist dabei
	 * folgende: Überschrift, Projekte (Name, Anzahl Perioden mit Jahren,
	 * Änderungsdatum, Löschbutton) und Projekt-hinzufügen-Button. für jedes
	 * graphische Element (Projekt als "1" Element gewertet) wird der indexUi um
	 * eins erhöht umd später neue Projekte an der richtigen Stelle Einzufügen
	 * 
	 * @author Christian Scherer
	 */
	public void generateUi() {
		setSpacing(true);
		setMargin(true);

		projectListPanel = new VerticalLayout();
		projectListPanel.setSpacing(true);
		title = new Label("<h1>Meine Projekte</h1>");
		title.setContentMode(Label.CONTENT_XHTML);
		projectListPanel.addComponent(title);

		singleProjectPanel = new ArrayList<VerticalLayout>();
		removeProjectBtn = new ArrayList<Button>();

		for (int i = 0; i < projects.size(); i++) {
			project = (Project) projects.get(i);

			singleProjectPanel.add(generateSingleProjectUi(project, i));
			projectListPanel
					.addComponent((com.vaadin.ui.Component) singleProjectPanel
							.get(i));

		}

		addProjectBtn = new Button("Projekt hinzufügen", this);
		projectListPanel.addComponent(addProjectBtn);

		addComponent(projectListPanel);
	}

	/**
	 * Konkrete Ausprogrammierung der der Darstellung eines einzlenen Projekts
	 * (Name, Anzahl Perioden mit Jahren, Änderungsdatum, Löschbutton). Diese
	 * wird sowohl bei der ersten Erstellung für jedes Projekt ausgeführt wie
	 * auch bei der Hinzufügung eines neuen Projekts. Zum Schluss wird dem
	 * Layout noch ein Listener hinzugefügt, der durch die Methode LayoutClick
	 * auf Klicks auf ein jeweiliges Projekt reagiert und in die Prozesssicht
	 * des einzelnen Projekts wechselt.
	 * 
	 * @author Christian Scherer
	 * @param project
	 *            das darzustellende Projekt und der aktuelle Index der Liste
	 * @param i
	 *            der Index der zu erstellenden Komponente (besonders für den
	 *            Löschbutton relevant)
	 * @return ein VerticalLayout Objekt, das zur Eingliederung in das UI dient
	 */
	public VerticalLayout generateSingleProjectUi(Project project, int i) {
		VerticalLayout singleProject = new VerticalLayout();
		projectName = new Label(project.getName());
		periodList = project.getPeriods();
		if (periodList.size() > 1) {
			periods = new Label("" + periodList.size() + " Perioden ("
					+ periodList.first().getYear() + "-"
					+ periodList.last().getYear() + ")");
		} else if (periodList.size() == 1) {
			periods = new Label("1 Periode (" + periodList.first().getYear()
					+ ")");
		} else {
			periods = new Label("Noch keine Perioden eingetragen");
		}
		if (project.getLastChanged() == null) {
			Date d = new Date();
			lastChanged = new Label("Zuletzt geändert: " + d.toString());
		} else {
			lastChanged = new Label("Zuletzt geändert: "
					+ project.getLastChanged().toString());
		}
		removeProjectBtn.add(new Button("löschen", this));

		singleProject.addComponent(projectName);
		singleProject.addComponent(periods);
		singleProject.addComponent(lastChanged);
		singleProject.addComponent((com.vaadin.ui.Component) removeProjectBtn
				.get(i));

		singleProject.addListener(this);

		projectListPanel.addComponent(singleProject);
		return singleProject;
	}

	/**
	 * Zeige das Projekt-Hinzuegen-Dialogfenster, bei dem ein Eingabefeld für
	 * den Namen des Projekts und ein Hinzfüge-Button vorhanden ist. Funktion
	 * bei geklicktem Button siehe Clicklistener in dieser Klasse.
	 * 
	 * @author Christian Scherer
	 */
	private void showAddProjectDialog() {
		addDialog = new Window("Projekt hinzufügen");
		addDialog.setModal(true);
		addDialog.setWidth(400, UNITS_PIXELS);
		addDialog.setResizable(false);
		addDialog.setDraggable(false);

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		Label name = new Label("Bitte Name wählen: ");
		layout.addComponent(name);
		tfName = new TextField();
		tfName.setRequiredError("Bitte Projektname angeben");
		layout.addComponent(tfName);
		addDialog.addComponent(layout);

		dialogAddBtn = new Button("Hinzufügen", this);

		layout.addComponent(dialogAddBtn);

		getWindow().addWindow(addDialog);
	}

	/**
	 * ClickListner Methode für die Reaktion auf Buttonclicks. Hier wird
	 * entsprechend auf die Buttonclicks für das Erzeugen weiterer Projekte
	 * reagiert, wie auch auf jene die Projekte löschen. In der ersten If
	 * Abfrage werden die vom Hauptfenster ausgelößten Clicks zum Hinzufügen
	 * eines neuen Objektes behandelt und die Methode für das Dialogfenster
	 * erstellt. In der Zweiten If-Abfrage wird die im Dialogfenster ausgelößten
	 * Clickst behandelt, wodurch eine neues Projekt erstellt und sowohl
	 * graphisch wie auch Objektbasiert (Im User Objekt über den Presenter)
	 * erstellt wird. In der Else Verzweigung werden dann die Lösch-Clicks für
	 * das jeweilige Projekt behandelt. Hierbei wird zunächst durch das Event in
	 * der Lösch-Buttonliste der Index identifiziert. Daraufhin muss sowohl auf
	 * Objektebene und Graphisch die Listen der neuen Situation mithilfe des
	 * Projekts bzw. des Index angepasst werden.
	 * 
	 * @author Christian Scherer
	 * @param event
	 *            Klick-event des Buttons
	 */
	@Override
	public void buttonClick(ClickEvent event) {

		if (event.getButton() == addProjectBtn) {
			showAddProjectDialog();
		} else if (event.getButton() == dialogAddBtn) {
			project = new Project((String) tfName.getValue());

			projectListPanel.removeComponent(addProjectBtn);

			singleProjectPanel.add(generateSingleProjectUi(project,
					projects.size()));
			projectListPanel
					.addComponent((com.vaadin.ui.Component) singleProjectPanel
							.get(projects.size()));

			projectListPanel.addComponent(addProjectBtn);

			presenter.addProject(project);
			getWindow().removeWindow(addDialog);

		} else {
			int index = removeProjectBtn.indexOf(event.getButton());

			projectListPanel
					.removeComponent((com.vaadin.ui.Component) singleProjectPanel
							.get(index));
			singleProjectPanel.remove(index);

			requestRepaint();
			removeProjectBtn.remove(event.getButton());
			presenter.removeProject((Project) projects.get(index));

		}

	}

	/**
	 * LayoutClickListner Methode für die Reaktion auf Klicks auf die einzelnen
	 * Projekte reagiert. Konkret wird hier die Verbindung zur Prozesssicht
	 * geschaffen. Zunächst wird geprüft von welchem Projekt der Klick kommt,
	 * und dann dieses dem Presenter übergeben, in welchem dann das Event für
	 * das Anzeigen der Prozesssicht ausgelöst wird.
	 * 
	 * @author Christian Scherer
	 * @param event
	 *            - Event des Layoutclicks
	 */
	@Override
	public void layoutClick(LayoutClickEvent event) {

		int index = singleProjectPanel.indexOf(event.getComponent());
		presenter.workWithProject((Project) projects.get(index));

	}

}
