package dhbw.ka.mwi.businesshorizon2.services.proxies;

import org.springframework.beans.factory.annotation.Autowired;

import dhbw.ka.mwi.businesshorizon2.models.User;

public class UserProxy {
	@Autowired
	private User selectedUser;
	public UserProxy() {
		super();
	}
	@Autowired
	public User getSelectedUser() {
		return selectedUser;
	}
	@Autowired
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	
}
