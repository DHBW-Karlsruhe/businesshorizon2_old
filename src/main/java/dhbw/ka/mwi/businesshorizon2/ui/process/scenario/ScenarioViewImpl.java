package dhbw.ka.mwi.businesshorizon2.ui.process.scenario;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class ScenarioViewImpl extends VerticalLayout implements ScenarioViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ScenarioPresenter presenter;

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
		generateUi();
	}

	/**
	 * Erstelle das GUI zum Prozessschritt "Szenarien"
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		Label testlabel = new Label("szenarientest");
		this.addComponent(testlabel);
	}

}
