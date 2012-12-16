package dhbw.ka.mwi.businesshorizon2.ui.methodlist;

import java.util.SortedSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import dhbw.ka.mwi.businesshorizon2.methods.Method;

/**
 * Dies ist die Vaadin-Implementierung der MethodListView.
 * 
 * @author Christian Gahlert
 *
 */
public class MethodListViewImpl extends VerticalLayout implements MethodListView {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MethodListPresenter presenter;

	private VerticalLayout methodPanel;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert sich selbst beim Presenter und initialisiert die 
	 * View-Komponenten.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		generateUi();
	}

	/**
	 * Es wird der Titel gesetzt und ein Panel fuer die  Darstellung der Methoden zur 
	 * spaeteren Befuellung erstellt.
	 * 
	 * @author Christian Gahlert
	 */
	private void generateUi() {
		setSpacing(true);
		setMargin(true);

		addComponent(new Label("Berechnungsmethoden"));
		
		methodPanel = new VerticalLayout();
		methodPanel.setSpacing(true);
		addComponent(methodPanel);
	}

	
	/**
	 * {@inheritDoc}
	 * 
	 * In diesem Fall werden die Methoden als Buttons angezeigt, die jeweils einen
	 * eigenen ClickListener bekommen. Wird dieser aufgerufen, wird dem Presenter
	 * die ausgewaehlte Methode mitgeteilt.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void setMethods(SortedSet<Method> methods) {
		methodPanel.removeAllComponents();
		
		for(final Method method : methods) {
			Button btn = new Button(method.getName());
			btn.setSizeFull();
			
			btn.addListener(new Button.ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					presenter.selectMethod(method);
				}
			});
			
			methodPanel.addComponent(btn);
		}
	}
}
