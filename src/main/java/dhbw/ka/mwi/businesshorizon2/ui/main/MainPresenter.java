package dhbw.ka.mwi.businesshorizon2.ui.main;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.method.MethodView;
import dhbw.ka.mwi.businesshorizon2.ui.method.ShowMethodEvent;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.MethodListView;
import dhbw.ka.mwi.businesshorizon2.ui.methodlist.ShowMethodListEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.PeriodEditView;
import dhbw.ka.mwi.businesshorizon2.ui.periodedit.ShowPeriodEditEvent;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.PeriodListView;
import dhbw.ka.mwi.businesshorizon2.ui.periodlist.ShowPeriodListEvent;

/**
 * Dieser Presenter ist das Kernstueck der Applikation. Er ist dafuer verantwortlich,
 * die jeweils anzuzeigenden Fenster korrekt in der View zu setzen. Somit ist es 
 * notwenig, dass er fuer jedes Anzuzeigende (Teil-)Fenster einen entsprechenden
 * EventHandler fuer den jeweiligen Show*Event registriert.
 * 
 * @author Christian Gahlert
 *
 */
public class MainPresenter extends Presenter<MainView>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EventBus eventBus;
	
	@Autowired
	private Project project;
	
	@Autowired
	private ContentView methodView;

	@Autowired
	private ContentView parameterView;

	@Autowired
	private ContentView periodView;

	@Autowired
	private ContentView processingView;

	@Autowired
	private ContentView outputView;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der Dependencies 
	 * aufgerufen wird. Er registriert lediglich sich selbst als einen EventHandler.
	 * 
	 * @author Christian Gahlert
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	/**
	 * Dieser Event wird zu Beginn von der BHApplication (nach dem Setzen des Fensters) 
	 * abgesetzt. Dabei wird links oben die Perioden-Liste, links unten die Methoden-
	 * Liste und rechts die Start-Ansicht angezeigt.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowMain(ShowMainViewEvent event) {
		getView().setActiveTab(0);
		eventBus.fireEvent(new ShowMethodListEvent());
		eventBus.fireEvent(new ShowPeriodListEvent());
	}
	
	/**
	 * Dieser Event wird von dem PeriodListPresenter nach dem Klick auf eine Periode
	 * abgesetzt. Dabei wird links oben die Perioden-Liste, links unten die Methoden-
	 * Liste und rechts die Bearbeitungs-Ansicht fuer eine Periode angezeigt.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowPeriodEdit(ShowPeriodEditEvent event) {
		//getView().showView(periodListView, methodListView, periodEditView);
	}
	
	/**
	 * Dieser Event wird von dem MethodListPresenter nach dem Klick auf eine Methode
	 * abgesetzt. Dabei wird links oben die Perioden-Liste, links unten die Methoden-
	 * Liste und rechts die Bearbeitungs-Ansicht fuer eine Methode angezeigt.
	 * 
	 * @author Christian Gahlert
	 * @param event
	 */
	@EventHandler
	public void onShowMethod(ShowMethodEvent event) {
		//getView().showView(periodListView, methodListView, methodView);
	}
	
}
