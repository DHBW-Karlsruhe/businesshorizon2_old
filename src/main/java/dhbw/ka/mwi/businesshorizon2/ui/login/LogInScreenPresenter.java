package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserAlreadyExistsException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotFoundException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.WrongPasswordException;

/**
 * 
 * Dies ist der Presenter, der hier besonders zum Durchreichen des
 * Authentifizierungsmechanismus gebraucht wird.
 * 
 * @author Christian Scherer
 * 
 */

public class LogInScreenPresenter extends Presenter<LogInScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("LogInScreenPresenter.class");

	@Autowired
	private EventBus eventBus;

	@Autowired
	private User user;

	@Autowired
	private AuthenticationServiceInterface authenticationService;

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
	 * Fensters) abgesetzt. Es müssen derzeit keine Objekte hierbei geladen
	 * werden.
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
	 * 
	 * @TODO In der Methode statt einen neuen User zu erzeugen die
	 *       authentifizierungsmethode rufen (siehe Kommentare in der Methode)
	 * 
	 */
	public void doLogin(String username, String password) {
		try {
			user = authenticationService.doLogin(username, password);
		} catch (UserNotFoundException e) {
			getView().showErrorMessage(e.getMessage());
			return;
		} catch (WrongPasswordException e) {
			getView().showErrorMessage(e.getMessage());
			return;
		}

		if (user != null) {
			logger.debug("LogIn erfolgreich");
			eventBus.fireEvent(new ShowUserEvent(user));
			logger.debug("ShowUserEvent gefeuert");
		}

	}

	/**
	 * Diese Methode wird von der LogIn Impl gerufen um zu prüfen ob ein ... Der
	 * Aufruf wird hierbei nur an den Authentisierungsmechanismus weitergeleitet
	 * und das Ergebnis zurückgegeben.
	 * 
	 * @author Christian Scherer
	 * @param firstName
	 *            Vorname, der eingegeben wurde
	 * @param lastName
	 *            Nachname, der eingegeben wurde
	 * @param emailAdress
	 *            Mailadresse, die eingegeben wurde
	 * @param company
	 *            Unternehmen, das eingegeben wurde
	 * @param password
	 *            Passwort, das eingegeben wurde
	 * 
	 * @TODO In der Methode statt einen neuen User zu erzeugen die
	 *       authentifizierungsmethode rufen (siehe Kommentare in der Methode)
	 * 
	 */
	public void registerUser(String firstName, String lastName, String company, String emailAdress, String password) {
		try {
			authenticationService.registerNewUser(emailAdress, password, firstName, lastName, company);
			logger.debug("Registrierung abgeschlossen.");
		} catch (UserAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			return;
		}

	}

	/**
	 * Prueft ob das Passwort gleich der Passwortwiederholung ist und gibt
	 * "true" fuer uebereinstimmung und "false" fuer keine Uebereinstimmung
	 * zurueck
	 * 
	 * @author Christian Scherer
	 * @return Ob die beiden Passwörter gleich sind oder nicht
	 */
	public boolean validatePassword(String password, String passwordRep) {
		return password.equals(passwordRep);

	}

	/**
	 * Ruft die Methode des AutehnticationService zur Behandlung eines
	 * vergessenen Passworts auf.
	 * 
	 * @author Christian Scherer
	 * @TODO authentifizierungsmethode rufen
	 */
	public void passwordForgot() {
		// Authentifizierungssmethode für vergessenens PAsswort aufrufen

	}

}
