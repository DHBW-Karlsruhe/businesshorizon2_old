package dhbw.ka.mwi.businesshorizon2.ui.initialscreen.projectcreation;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

public class ShowProjectCreationButtonsEvent extends Event {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public ShowProjectCreationButtonsEvent(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}

}
