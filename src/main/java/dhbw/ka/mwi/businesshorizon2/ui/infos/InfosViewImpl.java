package dhbw.ka.mwi.businesshorizon2.ui.infos;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Dies ist die Vaadin-Implementierung der InfoView mit Rich Text für eine adequate Ausgabe.
 * 
 * @author Christian Scherer
 *
 */
public class InfosViewImpl extends VerticalLayout implements InfosViewInterface {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("InfosViewImpl.class");
	
	@Autowired
	private InfosPresenter presenter;

	private Label textLabel;
	
	/**
	 * Dies ist der Konstruktor, der von Spring nach der Initialierung der
	 * Dependencies aufgerufen wird. Er registriert sich selbst beim Presenter
	 * und initialisiert die View-Komponenten.
	 * 
	 * @author Christian Scherer
	 */
	@PostConstruct
	public void init() {
		presenter.setView(this);
		logger.debug("View durch Presenter gesetzt");
		generateUi();

	}

	public void generateUi() {
		
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		
		textLabel = new Label("<h1>Good to know...</h1>"
                + "<p>Diese <b>brandneue</b> Applikation <sup>funktioniert</sup> folgendermaßen:</p>"
                + "<p> blabla <i>bla</i>!</p>");
		textLabel.setContentMode(Label.CONTENT_XHTML);
		addComponent(textLabel);
		logger.debug("Rich text erzeugt");


	}

}
