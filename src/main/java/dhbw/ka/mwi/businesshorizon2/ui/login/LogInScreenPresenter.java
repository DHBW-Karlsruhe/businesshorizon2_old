
/*******************************************************************************
 * BusinessHorizon2
 * 
 *     Copyright (C) 2012-2013  Christian Gahlert, Florian Stier, Kai Westerholz,
 *     Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.ui.login;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

import dhbw.ka.mwi.businesshorizon2.services.authentication.AuthenticationServiceInterface;
import dhbw.ka.mwi.businesshorizon2.services.authentication.InvalidFirstNameException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.InvalidMailAdressException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.InvalidLastNameException;
import dhbw.ka.mwi.businesshorizon2.services.authentication.TrivialPasswordException;
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
	 * zurückgegeben. Bei Misserfolg werden die entsprechenden Fehler geworfen.
	 * 
	 * Weitere Implementierte Prüfungen der Anmeldedaten: 
	 * - Vorname gültig (Muss mit Großbuchstaben beginnen, nur Buchstaben und Trennzeichen erlaubt. Keine Accents o.ä. und maximal 20 Buchstaben lang.)
	 * - Nachname gültig (Muss mit Großbuchstaben beginnen, nur Buchstaben und Trennzeichen erlaubt. Keine Accents o.ä. und maximal 20 Buchstaben lang.)
	 * - Regex (regulärer Ausdruck) zum überprüfen der Mail-Adresse
	 * - Passwort zwischen 6-20 Zeichen, mind. 1 Zahl, Groß- und Kleinbuchstaben, mind. 1 Sonderzeichen
	 * 
	 * 
	 * @author Christian Scherer, Marcel Rosenberger, Annika Weis
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
			logger.debug("Der Benutzer Existiert bereits.");
			return;
		}
		catch (InvalidFirstNameException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Der Vorname ist zu lange( >20 Zeichen).");
			return;
		}
		catch (InvalidLastNameException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Der Nachname ist zu lange( >20 Zeichen).");
			return;
		}
		catch (InvalidMailAdressException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Ungültige Mailadresse.");
			return;
		}
		catch (TrivialPasswordException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Ungültiges Passwort. Passwort muss folgende Bedingungen erfüllen: 6-20 Zeichen, mind. 1 Zahl, Groß- und Kleinbuchstaben, mind. 1 Sonderzeichen");
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
							"Passwort und dessen Wiederholung stimmen nicht überein. Bitte überprüfen Sie Ihre Eingabe");
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
