package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.ui.initialscreen.ShowInitialScreenViewEvent;

public class LogInScreenPresenter extends Presenter<LogInScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("LogInScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private User user;

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
		logger.debug("Eventhandler Hinzugefügt");

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
	public void onShowLogInScreen(ShowLogInScreenEvent event) {

	}

	/**
	 * Diese Methode wird von der LogIn Impl gerufen um zu prüfen ob ein ... Der
	 * Aufruf wird hierbei nur an den Authentisierungsmechanismus weitergeleitet
	 * und das Ergebnis zurückgegeben.
	 * 
	 * @author Christian Scherer
	 * @param username
	 *            Benutzername, der eingegeben wurde
	 * @param password
	 *            Passwort, das eingegeben wurde
	 */
	public boolean checkLogin(String username, String password) {
		// TODO: auth service rufen: user = ???.doLogin(username, password);
		boolean confirmed = false;
		
		//DUMMY!
		user = new User();
		if(user!=null){
			eventBus.fireEvent(new ShowUserEvent(user));
			confirmed = true;
		}
			
		return confirmed;
	}

}
