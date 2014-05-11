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

package dhbw.ka.mwi.businesshorizon2.services.authentication;

import java.io.Serializable;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Dieses Interface stellt Methoden zur Authentifizierung einzelner Benutzer zur
 * Verfügung. Durch Bereitstellung des Interfaces ist es möglich die
 * implementierende(n) Klasse(n) auszutauschen und die Kommunikation zwischen
 * Applikation und Nutzerdatenbank anzupassen.
 * 
 * @author Florian Stier, Marcel Rosenberger
 * 
 */
public interface AuthenticationServiceInterface extends Serializable {

	/**
	 * Methode zum einloggen eines Benutzers. Implementierende Klassen müssen
	 * die Kommunikation mit der User Datenbank herstellen und die
	 * Authentifizierung durchführen.
	 * 
	 * @param emailAdress
	 *            E-Mail Adresse des Users zum Anmelden
	 * @param password
	 *            Passwort des Nutzers
	 * @return User Objekt mit Benutzerdaten
	 */

	public User doLogin(String emailAdress, String password) throws UserNotFoundException, WrongPasswordException;

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
	 * @param emailAdress
	 *            E-Mail Adresse des Users zum anmelden
	 * @param password
	 *            Passwort zum Anmeldenamen
	 * @param firstName
	 *            Vorname des Anwenders
	 * @param lastName
	 *            Nachname des Anwenders
	 * @throws UserAlreadyExistsException
	 * @throws InvalidMailAdressException 
	 * @throws LastnameTooLongException 
	 * @throws TrivialPasswordException 
	 * @throws InvalidFirstNameException 
	 */
	public void registerNewUser(String emailAdress, String password, String firstName, String lastName, String company)
			throws UserAlreadyExistsException;

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
