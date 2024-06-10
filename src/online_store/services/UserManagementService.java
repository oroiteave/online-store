package online_store.services;

import java.util.List;

import online_store.entities.User;

public interface UserManagementService {
	String registerUser(User user);
	
	List<User> getUsers();

	User getUserByEmail(String userEmail);
	
	void resetPasswordForUser(User user);

}
