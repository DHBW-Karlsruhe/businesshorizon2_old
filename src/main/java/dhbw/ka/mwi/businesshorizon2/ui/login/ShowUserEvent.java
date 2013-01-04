package dhbw.ka.mwi.businesshorizon2.ui.login;

import com.mvplite.event.Event;

import dhbw.ka.mwi.businesshorizon2.models.User;

public class ShowUserEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public ShowUserEvent(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
}
