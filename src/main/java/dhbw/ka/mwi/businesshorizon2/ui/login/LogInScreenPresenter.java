/*******************************************************************************
 * BusinessHorizon2
 *
 * Copyright (C) 
 * 2012-2013 Christian Gahlert, Florian Stier, Kai Westerholz,
 * Timo Belz, Daniel Dengler, Katharina Huber, Christian Scherer, Julius Hacker
 * 2013-2014 Marcel Rosenberger, Mirko Göpfrich, Annika Weis, Katharina Narlock, 
 * Volker Meier
 * 
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package dhbw.ka.mwi.businesshorizon2.ui.login;

import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.EventBus;
import com.mvplite.event.EventHandler;
import com.mvplite.presenter.Presenter;

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
 * @author Christian Scherer, Marcel Rosenberger
 * 
 */

public class LogInScreenPresenter extends Presenter<LogInScreenViewInterface> {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger("LogInScreenPresenter.class");

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
		logger.debug("ShowLogInScreenEvent erhalten");
		logger.debug("LogInScreenPresenter.onShowLogInScreen aufgerufen");

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
			userProxy.setSelectedUser(authenticationService.doLogin(username,
					password));
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
	 * Weitere Implementierte Prüfungen der Anmeldedaten: - Vorname gültig (Muss
	 * mit Großbuchstaben beginnen, nur Buchstaben und Trennzeichen erlaubt.
	 * Keine Accents o.ä. und maximal 20 Buchstaben lang.) - Nachname gültig
	 * (Muss mit Großbuchstaben beginnen, nur Buchstaben und Trennzeichen
	 * erlaubt. Keine Accents o.ä. und maximal 20 Buchstaben lang.) - Regex
	 * (regulärer Ausdruck) zum überprüfen der Mail-Adresse - Passwort zwischen
	 * 6-20 Zeichen, mind. 1 Zahl, Groß- und Kleinbuchstaben, mind. 1
	 * Sonderzeichen
	 * 
	 * 
	 * @author Christian Scherer, Marcel Rosenberger, Annika Weis
	 * 
	 * 
	 */
	public boolean registerUser() {
		try {
			this.emailAdress = getView().getEmailAdress();
			this.password = getView().getPassword();
			this.firstName = getView().getFirstName();
			this.lastName = getView().getLastName();
			this.company = getView().getCompany();

			// Prüfungen werden durchgeführt, bevor die Methode ausgeführt wird
			logger.debug("Alle Eingabefelder wurden vom Anwender gültig befuellt und die Passwoerter stimmen überein.");
			
				if (validateNoNullPointer() && validateFirstName() &&
						 validateLastName() && validateMailAdress() &&
						 validatePassword() && validatePasswordSafety()){
					
				
				authenticationService.registerNewUser(emailAdress, password,
						firstName, lastName, company);
				logger.debug("Registrierung abgeschlossen.");
				//getView().closeDialog(getView().getRegDialog());
				return true;
				} else {
					return false;
				}
			

		} catch (UserAlreadyExistsException e) {
			getView().showErrorMessage(e.getMessage());
			logger.debug("Der Benutzer Existiert bereits.");
			return false;
		} 

	}

	/**
	 * Prueft ob alle Eingabefelder mit mindestens einem Zeichen befuellt wurden
	 * 
	 * @author Christian Scherer
	 * @return noNullPointer Ist false wenn NullPointer bestehen (leere Strings)
	 *         und true wenn alle Strings einen Wert enthalten
	 */
	public boolean validateNoNullPointer() {
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

	// TrivialPasswordException

	/**
	 * Prueft ob es sich um einen gültigen Vorname handelt. Gibt "true" fuer
	 * gültig und "false" fuer nicht gültig zurueck. Ein Vorname muss mit einem
	 * Großbuchstaben beginnen und darf maximal 20 Zeichen lang sein. Beginnt er nicht mit einem Großbuchstabe,
	 * wird der erste Buchstabe automatisch in einen Großbuchstabe konvertiert. Weiterhin
	 * ist ein Bindestrich "-" erlaubt. Bei keiner Uebereinstimmung wird zudem
	 * eine Fehlermeldung an die ViewImpl zur Ausgabe zurueckgegeben.
	 * 
	 * @author Marcel Rosenberger, Annika Weis, Marco Glaser
	 * @return Ob es sich um einen gültigen Vornamen handelt.
	 */
	protected boolean validateFirstName() {
		boolean validFirstName;

		// hier wird der Vorname überprüft
		if (Pattern.matches("^[A-ZÄÖÜ][a-zäöüA-ZÄÖÜ\\ \\-]{1,19}$", firstName)) {
			validFirstName = true;
			logger.debug("Vorname gültig.");
		} else if(Pattern.matches("^[a-zäöüA-ZÄÖÜ\\ \\-]{1,19}$", firstName)){
			validFirstName = true;
			char firstLetter = firstName.charAt(0);
			firstLetter = Character.toUpperCase(firstLetter);
			String upperCaseName = firstLetter + firstName.substring(1);
			this.firstName = upperCaseName;
		}
		else {
			validFirstName = false;
			getView().showErrorMessage(
					"Bitte geben Sie einen gültigen Vornamen ein.");
			logger.debug("Vorname ungültig.");
		}

		return validFirstName;
	}

	/**
	 * Prueft ob es sich um einen gültigen Nachnamen handelt. Gibt "true" fuer
	 * gültig und "false" fuer nicht gültig zurueck. Ein Nachname muss mit einem
	 * Großbuchstaben beginnen und darf maximal 20 Zeichen lang sein. Beginnt er nicht mit einem Großbuchstabe,
	 * wird der erste Buchstabe automatisch in einen Großbuchstabe konvertiert. Weiterhin
	 * ist ein Bindestrich "-" erlaubt. Bei keiner Uebereinstimmung wird zudem
	 * eine Fehlermeldung an die ViewImpl zur Ausgabe zurueckgegeben.
	 * 
	 * @author Marcel Rosenberger, Annika Weis, Marco Glaser
	 * @return Ob es sich um einen gültigen Nachnamen handelt.
	 */
	protected boolean validateLastName() {
		boolean validLastName;

		// hier wird der Nachname überprüft
		if (Pattern.matches("^[A-ZÄÖÜ][a-zäöüA-ZÄÖÜ\\ \\-]{1,19}$", lastName)) {
			validLastName = true;
			logger.debug("Nachname gültig.");
		} else if(Pattern.matches("^[a-zäöüA-ZÄÖÜ\\ \\-]{1,19}$", lastName)){
			validLastName = true;
			char firstLetter = lastName.charAt(0);
			firstLetter = Character.toUpperCase(firstLetter);
			String upperCaseName = firstLetter + lastName.substring(1);
			this.lastName = upperCaseName;
		}else {
			validLastName = false;
			getView().showErrorMessage(
					"Bitte geben Sie einen gültigen Nachnamen ein.");
			logger.debug("Nachname ungültig.");
		}

		return validLastName;
	}

	/**
	 * Prueft ob es sich um eine gültige Mailadresse handelt und gibt "true"
	 * fuer gültig und "false" fuer nicht gültig zurueck. Eine Mailadresse muss
	 * aus mindestens einem Zeichen vor dem '@', mindestens einem zwischen '@'
	 * und Domain, einem Punkt und einer Domain bestehen. Bei keiner
	 * Uebereinstimmung wird zudem eine Fehlermeldung an die ViewImpl zur
	 * Ausgabe zurueckgegeben.
	 * 
	 * @author Marcel Rosenberger, Annika Weis
	 * @return Ob es sich um eine gültige Mailadresse handelt.
	 */
	protected boolean validateMailAdress() {
		boolean validMailAdress;

		// hier wird die Mailadresse überprüft
		if (Pattern
				.matches(
						"^([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})$",
						emailAdress)) {
			validMailAdress = true;
			logger.debug("Mailadresse gültig.");
		} else {
			validMailAdress = false;
			getView().showErrorMessage(
					"Bitte geben Sie eine gültige Mailadresse ein.");
			logger.debug("Mailadresse ungültig.");
		}

		return validMailAdress;
	}

	/**
	 * Prueft ob das Passwort gleich der Passwortwiederholung ist und gibt
	 * "true" fuer uebereinstimmung und "false" fuer keine Uebereinstimmung
	 * zurueck. Bei keiner Uebereinstimmung wird zudem eine Fehlermeldung an die
	 * ViewImpl zur Ausgabe zurueckgegeben.
	 * 
	 * @author Christian Scherer, Marcel Rosenberger
	 * @return Ob die beiden Passwörter gleich sind oder nicht
	 */
	protected boolean validatePassword() {

		String password = getView().getPassword();
		String passwordRep = getView().getPasswordRep();
		boolean validPassword;

		if (password.equals(passwordRep)) {
			validPassword = true;
			logger.debug("Passwörter stimmen überein.");
		} else {
			validPassword = false;
			getView()
			.showErrorMessage(
					"Passwort und dessen Wiederholung stimmen nicht überein. Bitte überprüfen Sie Ihre Eingabe");
			logger.debug("Passwörter stimmen nicht überein.");
		}
		return validPassword;

	}

	/**
	 * Prueft ob das Passwort den Sicherheitsbestimmungen entspricht und gibt
	 * "true" fuer Entsprechnung und "false" fuer keine Entsprechung zurueck.
	 * Wie im Fachkonzept beschrieben muss ein Passwort aus 6-20 Zeichen,
	 * mindestens einer Zahl, Gruß- und Kleinbuchstaben sowie einem
	 * Sonderzeichen bestehen. Trifft das nicht zu, wird zudem eine
	 * Fehlermeldung an die ViewImpl zur Ausgabe zurueckgegeben.
	 * 
	 * @author Marcel Rosenberger, Annika Weis
	 * @return Ob die beiden Passwörter gleich sind oder nicht
	 */
	protected boolean validatePasswordSafety() {

		boolean safePassword;

		/**
		 * hier wird das Passwort überprüft
		 * - 6 bis 20 Zeichen
		 * - Groß- und Kleinbuchstaben entalten
		 * - Ziffer enthalten
		 * - Sonderzeichen enthalten
		 * Erlaubte Sonderzeichen:
		 * @ # $ % . , ; : ? ! ' | ° ^ § & _ - + / ( ) [ ] { } * "
		 */
		if (Pattern
				.matches(
						"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\.,;\\:\\?!'|°\\^§&\\_\\-\\+/\\(\\)\\[\\]{}\\*\"]).{6,20})",
						password)) {
			safePassword = true;
			logger.debug("Passwort genügt Sicherheitsbestimmungen.");
		} else {
			safePassword = false;
			getView()
			.showErrorMessage(
					"Das Passwort muss zwischen 6-20 Zeichen lang sein, sowie mindestens eine Zahl, Groß- und Kleinbuchstaben und ein Sonderzeichen enthalten.");
			logger.debug("Passwort genügt Sicherheitsbestimmungen nicht.");
		}

		return safePassword;

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
	//public void registerUserDialog() {
	//	getView().showRegisterUserDialog();
	//}

}

