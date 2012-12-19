package dhbw.ka.mwi.businesshorizon2.ui.periodlist;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Period;
import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.ShowPeriodEditEvent;

/**
 * Dies ist der Presenter zur Auflistung, dem Hinzufuegen und dem Loeschen von 
 * Perioden. Die Liste der Perioden wird dabei der Spring-Injected Project-
 * Bean entnommen, die lediglich einmal pro Session existiert, und in dem 
 * project-Property gespeichert wird.
 * 
 * @author Christian Gahlert
 *
 */
public class PeriodListPresenter extends Presenter<PeriodListViewInteface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	private Period currentPeriod = null;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	private void init() {
		eventBus.addHandler(this);
	}
	
	/**
	 * Diese Methode ueberprueft, ob noch weitere Jahre zur Verfuegung stehen und aktiviert
	 * bzw. deaktiviert den "Hinzufuegen"-Button entsprechend.
	 * 
	 * Wenn eine Methode selektiert ist, wird der "Entfernen"-Button angezeigt. Sonst nicht.
	 * 
	 * @author Christian Gahlert
	 * @see Project.getAvailableYears()
	 */
	public void updateButtons() {
		if(project.getAvailableYears().size() > 0) {
			getView().setShowAddPeriodButton(true);
		} else {
			getView().setShowAddPeriodButton(false);
		}
		
		if(currentPeriod == null) {
			getView().setShowRemovePeriodButton(false);
		} else {
			getView().setShowRemovePeriodButton(true);
		}
	}
	
	/**
	 * Diese Method erstellt eine neue Periode fuer das angegebene Jahr und speichert diese
	 * in der Project-Bean ab. Anschliessend wird ein Event abgesetzt, um andere Komponenten
	 * ueber die Aenderung zu informieren.
	 * 
	 * Zuletzt wird die Anzeige der Perioden und des Hinzufuegen-Buttons aktualisiert.
	 * 
	 * @author Christian Gahlert
	 * @param year Das Jahr, fuer das eine Periode erstellt werden soll
	 */
	public void addPeriod(int year) {
		Period period = new Period(year);
		project.getPeriods().add(period);
		
		eventBus.fireEvent(new PeriodAddEvent(period));
		
		currentPeriod = period;
		updatePeriods();
		updateButtons();
	}

	/**
	 * Diese Methode entfernt eine Periode aus der Projekt-Bean und setzt einen Event dafuer
	 * ab. Anschliessend wird die Anzeige der Perioden und des Hinzufuegen-Buttons aktualsiert.
	 * 
	 * @author Christian Gahlert
	 * @param period
	 */
	public void removePeriod(Period period) {
		project.getPeriods().remove(period);
		eventBus.fireEvent(new PeriodRemoveEvent(period));
		
		currentPeriod = null;
		updatePeriods();
		updateButtons();
	}
	
	/**
	 * Diese Methode wird nach jeder Aenderung an den Perioden aufgerufen. Dabei werden die 
	 * anzuzeigenden Perioden, die selektierte Methode sowie die verfuegbaren Jahre fuer
	 * neue Perioden an die View uebergeben.
	 * 
	 * @author Christian Gahlert
	 */
	private void updatePeriods() {
		getView().setAvailableYears(project.getAvailableYears());
		getView().setPeriods(project.getPeriods(), currentPeriod);
	}

	/**
	 * Diese Methode wird von der View aufgerufen, wenn eine Period ausgewaehlt wurde. Wenn
	 * die Periode nicht null ist, wird ein Event abgesetzt, um die Bearbeitungs-Ansicht 
	 * einer Periode zu oeffnen.
	 * 
	 * @author Christian Gahlert
	 * @param period Die zu bearbeitende Periode
	 */
	public void selectPeriod(Period period) {
		currentPeriod = period;
		updateButtons();
		
		if(period != null) {
			eventBus.fireEvent(new ShowPeriodEditEvent(period));
		}
	}
	
	/**
	 * Dieser Event wird vom MainPresenter bei der Anzeige der Start-Ansicht abgesetzt.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowPeriodList(ShowPeriodListEvent event) {
		updatePeriods();
	}
	
	/**
	 * Dieser Event wird vom MethodListPresenter abgesetzt. Dabei wird eine ggf. ausgewaehlte
	 * Periode deselektiert, um eine spaeter Neu-Auswahl zu ermoeglichen.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowMethod(ShowMethodEvent event) {
		currentPeriod = null;
		updatePeriods();
	}
	
}
