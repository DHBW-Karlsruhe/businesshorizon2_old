package dhbw.ka.mwi.businesshorizon2.services.authentication;

import java.io.Serializable;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Dieses Interface stellt Methoden zur Authentifizierung einzelner Benutzer zur
 * Verfügung. Durch Bereitstellung des Interfaces ist es möglich die
 * implementierende(n) Klasse(n) auszutauschen und die Kommunikation zwischen
 * Applikation und Nutzerdatenbank anzupassen.
 * 
 * @author Florian Stier
 * 
 */
public interface AuthenticationServiceInterface extends Serializable {

	/**
	 * Methode zum einloggen eines Benutzers. Implementierende Klassen müssen
	 * die Kommunikation mit der User Datenbank herstellen und die
	 * Authentifizierung durchführen.
	 * 
	 * @param username
	 *            Anmeldename eines Nutzers
	 * @param password
	 *            Passwort des Nutzers
	 * @return User Objekt mit Benutzerdaten
	 */

	public User doLogin(String username, String password) throws UserNotFoundException, WrongPasswordException;

	/**
	 * Methode zum Ausloggen eines Users.
	 * 
	 * @param user
	 *            User der ausgeloggt werden soll
	 */
	public void doLogout(User user) throws UserNotLoggedInException;

	/**
	 * Methode zum registrieren eines neuen Nutzers. Implementierende Klassen
	 * müssen eine Verbindung zur Datenbank herstellen und einen neuen User mit
	 * den übergebenen Parametern anlegen.
	 * 
	 * @param username
	 *            Name zur Anmeldung eines Users
	 * @param password
	 *            Passwort zum Anmeldenamen
	 * @param firstName
	 *            Vorname des Anwenders
	 * @param lastName
	 *            Nachname des Anwenders
	 */
	public void registerNewUser(String username, String password, String firstName, String lastName);

	/**
	 * Methode zum Löschen des als Parameter übergebenen Users.
	 * 
	 * @param user
	 *            der zu löschende User
	 */
	public void deleteUser(User user);

	/**
	 * Methode zum zurücksetzen/erhalten des Userpasswords. In der
	 * Implementierung könnte zum Beispiel eine Mail an den entsprechenden User
	 * geschickt werden.
	 * 
	 * @param Die
	 *            Mailadresse, für den das Passwort gelesen werden soll
	 */
	public void forgotPassword(String email);
}
