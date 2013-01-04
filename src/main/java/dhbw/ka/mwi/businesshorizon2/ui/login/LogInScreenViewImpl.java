package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.Window;

/**
 * Dies ist die Vaadin-Implementierung des LogIn Screens. Diese stützt sich vor
 * allem auf das hinzufügen eines LogIn-Forms von Vaadin welches die Darstellung
 * wie auch Übermittlung der Werte Username und Password sicherstellt.
 * 
 * @author Christian Scherer
 * 
 */
public class LogInScreenViewImpl extends Window implements
		LogInScreenViewInterface {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("LogInScreenViewImpl.class");

	@Autowired
	private LogInScreenPresenter presenter;

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
		generateUi();
	}

	/**
	 * Diese Methode setzt den Titel (im Browser-Fenster) zu
	 * "Business Horizon 2" und erstellt die LogIn Maske mit Listener. Der
	 * Listener prüft ruft die im LogIn Event gesammelten LogIn-Daten und
	 * übergibt sie dem presenter zur Kontrolle. Je nach ausgang der Konrolle
	 * wird dann eine Fehlermeldung aufgerufen.
	 * 
	 * @author Christian Scherer
	 */
	private void generateUi() {
		setCaption("Business Horizon 2");
		logger.debug("Überschrift für Browser erstellt");

		LoginForm login = new LoginForm();
		login.setWidth("100%");
		login.setHeight("300px");
		login.addListener(new LoginForm.LoginListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void onLogin(LoginEvent event) {
				getWindow().showNotification(
						"New Login",
						"Username: " + event.getLoginParameter("username")
								+ ", password: "
								+ event.getLoginParameter("password"));
				boolean logInCheck = presenter.checkLogin(
						event.getLoginParameter("username"),
						event.getLoginParameter("password"));
				if (!logInCheck) {
					logger.debug("LogIn fehlgeschlagen");
					getWindow()
							.showNotification(
									(String) "Anmeldung fehlgeschlagen",
									(String) "Bitte erneut mit gültigen Anmeldedaten probieren",
									Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});

		addComponent(login);
		logger.debug("LogIn UI erstellt und Listener gesetzt");

	}

}
