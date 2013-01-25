package dhbw.ka.mwi.businesshorizon2.services.proxies;

import dhbw.ka.mwi.businesshorizon2.models.User;

public class UserProxy {
	private User selectedUser;
	
	public UserProxy() {
		super();
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	
}
