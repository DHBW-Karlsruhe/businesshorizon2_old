package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.ui.infos.InfosViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.infos.ShowInfosEvent;
import dhbw.ka.mwi.businesshorizon2.ui.projectlist.ProjectListViewInterface;
import dhbw.ka.mwi.businesshorizon2.ui.projectlist.ShowProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.projectlist.ShowProjectListEvent;

/**
 * Dieser Presenter stellt die Einganseite der Applikation darf. Er ist dafuer
 * verantwortlich, die jeweils anzuzeigenden Fenster korrekt in der View zu
 * setzen. Somit ist es notwenig, dass er fuer jedes Anzuzeigende (Teil-)Fenster
 * einen entsprechenden EventHandler fuer den jeweiligen Show*Event registriert.
 * 
 * @author Christian Scherer
 * 
 */

public class InitialScreenPresenter extends
		Presenter<InitialScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private EventBus eventBus;

	@Autowired
	private Project project;

	@Autowired
	private ProjectListViewInterface projectListView;

	@Autowired
	private InfosViewInterface infosView;

	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert lediglich sich selbst als
	 * einen EventHandler.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		eventBus.addHandler(this);
	}

	/**
	 * Dieser Event wird zu Beginn von der BHApplication (nach dem Setzen des
	 * Fensters) abgesetzt. Dabei wird in auf der linken Seite die Projekt-Liste
	 * und auf der rechten Seite die Anwenderinformationen dargestellt.
	 * 
	 * @author Christian Scherer
	 * @param event
	 */
	@EventHandler
	public void onShowInitialScreen(ShowInitialScreenViewEvent event) {
		getView().showView(projectListView, infosView);
		eventBus.fireEvent(new ShowProjectListEvent());
		eventBus.fireEvent(new ShowInfosEvent());
	}

	/**
	 * Dieser Event wird von dem ProjectListPresenter nach dem Klick auf ein
	 * Projekt abgesetzt. Darauf folgend soll der Wizard mit dem angeklickten
	 * Projekt aufgerufen werden.
	 * 
	 * @author Christian Scheer
	 * @param event
	 */
	@EventHandler
	public void onShowProject(ShowProjectEvent event) {
		// Hier muss nun der Aufruf zum Wizard implementiert werden
		// siehe main aufruf
		// getView().showView(periodListView, methodListView, methodView);
	}
}