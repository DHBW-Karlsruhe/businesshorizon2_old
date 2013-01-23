package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;
import com.vaadin.ui.Window;

import dhbw.ka.mwi.businesshorizon2.models.User;
import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserAlreadyExistsException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.UserNotFoundException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.WrongPasswordException;
import dhbw.ka.mwi.businesshorizon2.services.proxies.UserProxy;

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
	private UserProxy userProxy;

	@Autowired
	private AuthenticationServiceInterface authenticationService;

	private String emailAdress;
	private String password;
	private String firstName;
	private String lastName;
	private String company;

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
			userProxy.setSelectedUser(authenticationService.doLogin(username, password));
		} catch (UserNotFoundException e) {
			getView().showErrorMessage(e.getMessage());
			return;
		} catch (WrongPasswordException e) {
			getView().showErrorMessage(e.getMessage());
			return;
		}

		if (userProxy.getSelectedUser() != null) {
			logger.debug("LogIn erfolgreich");
			eventBus.fireEvent(new ShowUserEvent());
			logger.debug("ShowUserEvent gefeuert");
		}

	}

	/**
	 * Diese Methode wird von der LogIn Impl gerufen um zu prüfen ob es
	 * Null-Werte gibt und ob die Passwoerter gleich sind. Bei Erfolg wird der
	 * Aufruf an den Authentisierungsmechanismus weitergeleitet und das Ergebnis
	 * zurückgegeben. Bei Misserfolg werden die entsprechenden Fehler geworfen
	 * 
	 * @author Christian Scherer
	 * 
	 * 
	 */
	public void registerUser() {
		try {
			this.emailAdress = getView().getEmailAdress();
			this.password = getView().getPassword();
			this.firstName = getView().getFirstName();
			this.lastName = getView().getLastName();
			this.company = getView().getCompany();

			if (validatePassword() && validateNoNullPointer()) {

				logger.debug("Alle Eingabefelder wurden vom Anwender befuellt und die Passwoerter stimmen ueberein.");
				authenticationService.registerNewUser(emailAdress, password,
						firstName, lastName, company);
				logger.debug("Registrierung abgeschlossen.");
				getView().closeDialog(getView().getRegDialog());

			}

		} catch (UserAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Der Benutzer Existiert bereizts.");
			return;
		}

	}

	/**
	 * Prueft ob alle Eingabefelder mit mindestens einem Zeichen befuellt wurden
	 * 
	 * @author Christian Scherer
	 * @return noNullPointer Ist false wenn NullPointer bestehen (leere Strings)
	 *         und true wenn alle Strings einen Wert enthalten
	 */
	private boolean validateNoNullPointer() {
		boolean noNullPointer;
		if (emailAdress.isEmpty() || password.isEmpty() || firstName.isEmpty()
				|| lastName.isEmpty() || company.isEmpty()) {
			noNullPointer = false;
			getView().showErrorMessage("Bitte füllen Sie alle Felder aus.");
		} else {
			noNullPointer = true;
		}

		return noNullPointer;
	}

	/**
	 * Prueft ob das Passwort gleich der Passwortwiederholung ist und gibt
	 * "true" fuer uebereinstimmung und "false" fuer keine Uebereinstimmung
	 * zurueck. Bei keiner Uebereinstimmung wird zudem eine Fehlermeldung an die
	 * ViewImpl zur Ausgabe zurueckgegeben.
	 * 
	 * @author Christian Scherer
	 * @return Ob die beiden Passwörter gleich sind oder nicht
	 */
	private boolean validatePassword() {

		String password = getView().getPassword();
		String passwordRep = getView().getPasswordRep();
		boolean passwordValid;

		if (password.equals(passwordRep)) {
			passwordValid = true;
		} else {
			passwordValid = false;
			getView()
					.showErrorMessage(
							"Passwort und dessen Wiederholung stimmen nicht überein. Bitte überprüfen Sie Ihre eingabe");
		}
		return passwordValid;

	}

	/**
	 * Ruft die Methode des AutehnticationService zur Behandlung eines
	 * vergessenen Passworts auf.
	 * 
	 * @author Christian Scherer
	 * @TODO authentifizierungsmethode rufen
	 */
	public void passwordForgot() {
		// Authentifizierungssmethode für vergessenens Passwort aufrufen

	}

	/**
	 * Ruft die Methode showRegisterUserDialog() der ViewImpl auf.
	 * 
	 * @author Christian Scherer
	 */
	public void registerUserDialog() {
		getView().showRegisterUserDialog();
	}

}
