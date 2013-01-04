package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectlist;

import org.springframework.beans.factory.annotation.Autowired;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

/**
 * Das Event, der zum Anzeigen der ProjectListView, vom MainPresenter beim
 * Anzeigen der Start-Ansicht abgesetzt wird.
 * 
 * @author Christian Scherer
 * 
 */
public class ShowProjectListEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private User user;
	
	public ShowProjectListEvent(User user) {
		this.user = user;
	}
	
	
	/**
	 * Es wird das User Objekt übergeben, das erfolgreich angemeldet wurde
	 * 
	 * @author Christian Scherer
	 * @return user-objekt 
	 */
	public User getUser(){
		return this.user;
		
	}
}
