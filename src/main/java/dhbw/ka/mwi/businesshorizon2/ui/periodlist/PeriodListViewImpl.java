package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import java.util.List;
import java.util.NavigableSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.models.Period;

/**
 * Dies ist die Vaadin-Implementierung der PeriodListView.
 * 
 * @author Christian Gahlert
 *
 */
public class PeriodListViewImpl extends VerticalLayout implements PeriodListView, Button.ClickListener, Property.ValueChangeListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PeriodListPresenter presenter;
	
	private ListSelect listSelect;

	private Button addPeriodBtn;

	private Button removePeriodBtn;

	private List<Integer> availableYears;

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
	 * Zunaechst wird eine Ueberschrift gesetzt, dann eine Liste fuer die Perioden
	 * erstellt und anschliessend die Buttons zum Hinzufuegen/Entfernen unter der
	 * Liste nebeneinander angezeigt.
	 * 
	 * @author Christian Gahlert
	 */
	private void generateUi() {
		setSizeFull();
		setSpacing(true);
		setMargin(true);

		Label titleLabel = new Label("Perioden");
		addComponent(titleLabel);
		setExpandRatio(titleLabel, 0);
		
		listSelect = new ListSelect();
		listSelect.setImmediate(true);
		listSelect.setSizeFull();
		listSelect.setNullSelectionAllowed(false);
		listSelect.addListener(this);
		addComponent(listSelect);
		setExpandRatio(listSelect, 1);
		

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		addComponent(buttonLayout);
		setExpandRatio(buttonLayout, 0);
		
		addPeriodBtn = new Button("Periode hinzufügen", this);
		buttonLayout.addComponent(addPeriodBtn);
		
		removePeriodBtn = new Button("Periode entfernen", this);
		buttonLayout.addComponent(removePeriodBtn);
	}

	/**
	 * Der ClickListener fuer die Hinzufuegen/Entfernen-Buttons. Beim Hinzufuegen wird 
	 * zunaechst ein Dialog-Fenster fuer die Auswahl des Jahres angezeigt. Beim Entfernen
	 * wird der Presenter angewiesen die ausgewaehlte Periode zu entfernen.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton() == addPeriodBtn) {
			showAddPeriodDialog();
		} else if(event.getButton() == removePeriodBtn) {
			presenter.removePeriod((Period) listSelect.getValue());
		}
	}
	
	/**
	 * Zeige das Periode-Hinzuegen-Dialogfenster, wo zunaechst ein Text steht, dann ein 
	 * Select fuer das Jahr ist und anschliessend ein Button. Beim Klick auf den Button
	 * wird der Presenter darueber informiert, dass er eine neue Periode fuer das 
	 * selektierte Jahr erstellen sollen. Gleichzeitig wird das Dialogfenster wieder 
	 * entfernt.
	 * 
	 * @author Christian Gahlert
	 */
	private void showAddPeriodDialog() {
		final Window addDialog = new Window("Periode hinzufügen");
		addDialog.setModal(true);
		addDialog.setWidth(300, UNITS_PIXELS);
		addDialog.setResizable(false);
		addDialog.setDraggable(false);
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.addComponent(new Label("Bitte Jahr wählen: "));
		addDialog.addComponent(layout);
		
		final NativeSelect yearSelect = new NativeSelect(null, availableYears);
		yearSelect.setNullSelectionAllowed(false);
		yearSelect.setValue((int) availableYears.get(0));
		layout.addComponent(yearSelect);
		
		final Button addBtn = new Button("Hinzufügen", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.addPeriod((int) yearSelect.getValue());
				getWindow().removeWindow(addDialog);
			}
		});
		layout.addComponent(addBtn);
		
		getWindow().addWindow(addDialog);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setShowAddPeriodButton(boolean flag) {
		addPeriodBtn.setEnabled(flag);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setShowRemovePeriodButton(boolean flag) {
		removePeriodBtn.setEnabled(flag);
	}

	/**
	 * Dies ist der ValueChange-Listener, der auf die Selektion einer Periode wartet.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void valueChange(ValueChangeEvent event) {
		presenter.selectPeriod((Period) listSelect.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPeriods(NavigableSet<Period> periods, Period selected) {
		final Container c = new IndexedContainer();
        for (Period period : periods.descendingSet()) {
            c.addItem(period);
        }
        listSelect.setContainerDataSource(c);
        listSelect.select(selected);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAvailableYears(List<Integer> availableYears) {
		this.availableYears = availableYears;
	}
}
