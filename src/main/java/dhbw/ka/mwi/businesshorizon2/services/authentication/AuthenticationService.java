package dhbw.ka.mwi.businesshorizon2.services.authentication;

import org.apache.catalina.User;

public class AuthenticationService implements AuthenticationServiceInterface {

	private static AuthenticationService INSTANCE;

	/**
	 * Authentication Service der die Authentifizierung über eine einfache .dat
	 * Datei durchführt. Die Klasse ist als Singleton implementiert, damit sie
	 * nur einmal in der gesamten Applikation existiert.
	 */

	/**
	 * privater standard Konstruktor zur Realisierung des Singletons
	 */
	private AuthenticationService() {
		super();
	}

	/**
	 * Liefert die Singleton Instanz des Authentication Service zurück
	 * 
	 * @return AuthenticationService Instanz
	 */
	public static AuthenticationService getInstance() {
		if (INSTANCE != null) {
			return INSTANCE;
		} else {
			INSTANCE = new AuthenticationService();
		}
		return INSTANCE;

	}

	@Override
	public User doLogin(String username, String password) {
		return null;
	}

	@Override
	public void doLogout(User user) {

	}

	@Override
	public void registerNewUser(String username, String password,
			String firstName, String lastName) {

	}

	@Override
	public void deleteUser(User user) {

	}

}
