package online_store.services;

import online_store.entities.User;

public interface UserManagementService {
	String registerUser(User user);
	
	User[] getUsers();

	User getUserByEmail(String userEmail);

}
