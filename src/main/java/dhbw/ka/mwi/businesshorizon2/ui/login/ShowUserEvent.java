package dhbw.ka.mwi.businesshorizon2.ui.login;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Der Event, der zum Anzeigen des LogIn-Screen-Fensters in der
 * BHApplication-Klasse abgesetzt wird.
 * 
 * @author Christian Scherer
 * 
 */

public class ShowUserEvent extends Event {
	private static final long serialVersionUID = 1L;

	private User user;

	/**
	 * Der Konstrutkor empfängt ein User Objekt, welches er speichert um es
	 * später mittels der getter Methode weiterzugeben.
	 * 
	 * @author Christian Scherer
	 * @param user
	 *            Das Objekt des erfolgreich angemeldeten User
	 * 
	 */
	public ShowUserEvent() {
	}

	/**
	 * Der Konstrutkor empfängt ein User Objekt, welches er speichert um es
	 * später mittels der getter Methode weiterzugeben.
	 * 
	 * @author Christian Scherer
	 * @return Rückgabe des User Objekts
	 */
	public User getUser() {
		return this.user;
	}
}
