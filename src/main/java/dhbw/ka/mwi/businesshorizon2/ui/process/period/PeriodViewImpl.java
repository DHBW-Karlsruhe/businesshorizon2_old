package dhbw.ka.mwi.businesshorizon2.ui.process.period;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.view.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

/**
 * Diese Klasse implementiert das GUI fuer den Prozessschritt "Methoden" in Vaadin.
 * 
 * @author Julius Hacker
 *
 */
public class PeriodViewImpl extends HorizontalSplitPanel implements PeriodViewInterface {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PeriodPresenter presenter;


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
	 * Erstelle das GUI zum Prozessschritt "Perioden"
	 * 
	 * @author Julius Hacker
	 */
	private void generateUi() {
		this.setSizeFull();
		this.setSplitPosition(25);
		this.setSizeFull();
	}

	@Override
	public void showView(View leftView, View rightView) {

		this.setFirstComponent((Component) leftView);
		this.setSecondComponent((Component) rightView);
		
	}
}
