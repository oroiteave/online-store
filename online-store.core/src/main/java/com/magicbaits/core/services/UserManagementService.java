package com.magicbaits.core.services;

import java.util.List;

import com.magicbaits.persistence.enteties.User;


public interface UserManagementService {
	String registerUser(User user);
	
	List<User> getUsers();

	User getUserByEmail(String userEmail);
	
	void resetPasswordForUser(User user);

}
