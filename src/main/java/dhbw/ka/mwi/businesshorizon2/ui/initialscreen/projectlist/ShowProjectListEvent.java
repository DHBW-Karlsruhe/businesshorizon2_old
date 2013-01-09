package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Das Event, der zum Anzeigen der ProjectListView, das vom
 * InitialScreenPresenter gefeuert wird. Es beinhaltet das eingeloggte
 * User-Objekt.
 * 
 * @author Christian Scherer
 * 
 */
public class ShowProjectListEvent extends Event {

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
	public ShowProjectListEvent(User user) {
		this.user = user;
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
