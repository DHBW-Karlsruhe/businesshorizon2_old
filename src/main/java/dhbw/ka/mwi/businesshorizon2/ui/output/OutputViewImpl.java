package dhbw.ka.mwi.businesshorizon2.ui.output;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Ausgabe" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class OutputViewImpl extends VerticalLayout implements OutputView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private OutputPresenter presenter;

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
	 * Erstelle das GUI zum Prozessschritt "Ausgabe"
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		Label testlabel = new Label("outputtest");
		this.addComponent(testlabel);
	}



}
