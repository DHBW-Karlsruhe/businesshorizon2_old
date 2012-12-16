package dhbw.ka.mwi.businesshorizon2;


import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.vaadin.Application;

import dhbw.ka.mwi.businesshorizon2.ui.main.MainViewImpl;
import dhbw.ka.mwi.businesshorizon2.ui.main.ShowMainViewEvent;

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

	@Autowired
	private MainViewImpl mainView;

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
	 * den ShowMainViewEvent abzusetzen. Durch diesen werden die Listener (z.B. der MainPresenter)
	 * darueber informiert, dass das Hauptfenster angezeigt wird.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void init() {
		setMainWindow(mainView);
		eventBus.fireEvent(new ShowMainViewEvent());
	}
	

}
