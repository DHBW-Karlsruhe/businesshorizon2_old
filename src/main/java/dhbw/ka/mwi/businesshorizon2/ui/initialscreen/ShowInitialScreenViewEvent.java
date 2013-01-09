package dhbw.ka.mwi.businesshorizon2.ui.initialscreen;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Der Event, der zum Anzeigen des InitalScreen-Fensters z.B. in der
 * BHApplication-Klasse abgesetzt wird.
 * 
 * @author Christian Scherer
 * 
 */

public class ShowInitialScreenViewEvent extends Event {

	private User user;

	public ShowInitialScreenViewEvent(User user) {
		this.user = user;
	}

	/**
	 * Es wird das User Objekt übergeben, das erfolgreich angemeldet wurde
	 * 
	 * @author Christian Scherer
	 * @return user-objekt
	 */
	public User getUser() {
		return this.user;

	}

	private static final long serialVersionUID = 1L;

}