package dhbw.ka.mwi.businesshorizon2;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.sample.SampleBeanInterface;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Das ist die Haupt-Einstiegsklasse der Anwendung. Es ist vergleichbar mit der statischen main()-
 * Methode in normalen Java-Anwendungen. Die Initialisierung erfolgt in der Klasse
 * SpringApplicationServlet. Dabei werden auch die Autowired-Annotations aufgeloest.
 * 
 * @author Christian Gahlert
 */
public class BHApplication extends Application
{
	private static final long serialVersionUID = 1L;

	private Window window;

    @Autowired
    private SampleBeanInterface bean;
    
    /**
     * Diese Methode ist dafuer verantwortlich, das Haupt-Fenster zu laden und den gemanagten Windows
     * hinzuzufuegen.
     * 
     * @author Christian Gahlert
     */
    @Override
    public void init()
    {
        window = new Window("Business Horizon 2");
        setMainWindow(window);
        Button button = new Button("Click Me");
        
        button.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
                window.addComponent(new Label(bean.get()));
            }
        });
        window.addComponent(button);
        
    }
    
    
    
}
