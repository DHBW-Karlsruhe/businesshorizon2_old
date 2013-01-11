package dhbw.ka.mwi.businesshorizon2;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.vaadin.Application;

import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.InitialScreenViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist.ShowProjectEvent;
import dhbw.ka.mwi.businesshorizon2.ui.process.ProcessViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.process.ShowProcessViewEvent;

/**
 * Das ist die Haupt-Einstiegsklasse der Anwendung. Es ist vergleichbar mit der
 * statischen main()- Methode in normalen Java-Anwendungen. Die Initialisierung
 * erfolgt in der Klasse SpringApplicationServlet. Dabei werden auch die
 * Autowired-Annotations aufgeloest.
 * 
 * @author Christian Gahlert
 */
public class BHApplication extends Application {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger("BHApplication.class");

	@Autowired
	private ProcessViewImpl processView;
	
	@Autowired
	private InitialScreenViewImpl initialScreenView;

	@Autowired
	private EventBus eventBus;
	
	/**
	 * Der Konstruktor. Hier wird zunaechst das Theme gesetzt, so dass das Stylesheet 
	 * 		WebContent/VAADIN/themes/bh2/styles.css
	 * verwendet wird. 
	 * 
	 * @author Christian Gahlert
	 */
	public BHApplication() {
		setTheme("bh2");
	}
	
	/**
	 * Diese Methode ist dafuer verantwortlich, das Haupt-Fenster zu laden und
	 * den ShowInitialScreenViewEvent abzusetzen. Durch diesen werden die Listener
	 * darueber informiert, dass das Hauptfenster angezeigt wird.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void init() {
		eventBus.addHandler(this);
		
		setMainWindow(initialScreenView);
		eventBus.fireEvent(new ShowInitialScreenViewEvent());
		
	}

	/**
	 * Die Methode triggert die Anzeige der Prozessansicht, sobald an einer Stelle
	 * des Programmes ein Projekt angezeigt wurde.
	 * 
	 * @author Julius Hacker
	 * @param event Der ausgeloeste ShowProjectEvent
	 */
	@EventHandler
	public void showProcessView(ShowProjectEvent event) {
		setMainWindow(processView);
		this.removeWindow(initialScreenView);
		eventBus.fireEvent(new ShowProcessViewEvent());
		logger.debug("ShowMainViewEvent gefeuert");
	}
}
