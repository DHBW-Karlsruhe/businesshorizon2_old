package dhbw.ka.mwi.businesshorizon2;


import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.vaadin.Application;
import com.vaadin.ui.Window;

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
	
	public BHApplication() {
		setTheme("bh2");
		Window window = new Window("Business Horizon 2");
		setMainWindow(window);
	}
	
	/**
	 * Diese Methode ist dafuer verantwortlich, das Haupt-Fenster zu laden und
	 * den gemanagten Windows hinzuzufuegen.
	 * 
	 * @author Christian Gahlert
	 */
	@Override
	public void init() {
		setMainWindow(mainView);
		eventBus.fireEvent(new ShowMainViewEvent());
	}
	

}
