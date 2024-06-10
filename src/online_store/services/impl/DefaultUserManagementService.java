package online_store.services.impl;


import online_store.entities.User;
import online_store.services.UserManagementService;
import online_store.util.mail.MailSender;

import java.util.ArrayList;
import java.util.List;
	public class DefaultUserManagementService implements UserManagementService{
	private static final String NOT_UNIQUE_EMAIL_ERROR_MESSAGE = "This email is already used by another user. Please, use another email";
	private static final String EMPTY_EMAIL_ERROR_MESSAGE = "You have to input email to register. Please, try one more time";
	private static final String NO_ERROR_MESSAGE = "";
	
	private static final int DEFAULT_USERS_CAPACITY = 10;
	
	private static DefaultUserManagementService instance;
	
	private MailSender mailSender;
	private List<User> users;
	private int lastUserIndex;
	
	{
		users = new ArrayList<User>(DEFAULT_USERS_CAPACITY);
		mailSender = DefaultMailSender.getInstance();
	}

	public DefaultUserManagementService() {
	}
	
	@Override
	public String registerUser(User user) {
		if(user == null) {
			return NO_ERROR_MESSAGE;
		}
		String errorMessage = checkUniqueEmail(user.getEmail());
		if(errorMessage != null && !errorMessage.isEmpty()) {
			return errorMessage;
		}
		if (users.size() <= lastUserIndex) {
			users = new ArrayList<User>(users.size()<<1);
		}
		users.add(lastUserIndex++, user);
		return NO_ERROR_MESSAGE;
	}
	
	private String checkUniqueEmail(String email) {
		if(email == null || email.isEmpty()) {
			return EMPTY_EMAIL_ERROR_MESSAGE;
		}
		for(User u:users) {
			if(u != null && u.getEmail()!=null && u.getEmail().equalsIgnoreCase(email)) {
				return NOT_UNIQUE_EMAIL_ERROR_MESSAGE;
			}
		}
		return NO_ERROR_MESSAGE;
	}

	public static UserManagementService getInstance() {
		if (instance == null) {
			instance = new DefaultUserManagementService();
		}
		return instance;
	}
	
	public void resetPasswordForUser(User user) {
		System.out.println("Password changed succefully, please check your email");
		mailSender.sendEmail(user.getEmail(), user.getPassword());
	}

	
	@Override
	public List<User> getUsers() {
		int notNullUser = 0;
		for(User u:users) {
			if(u!=null) {
				notNullUser++;
			}
		}
		int i=0;
		List<User> notNullUsers = new ArrayList<User>(notNullUser);
		for(User u:users) {
			if(u!=null) {
				notNullUsers.add(i++, u);
			}
		}
		return notNullUsers;
	}

	@Override
	public User getUserByEmail(String userEmail) {
		for(User u: users) {
			if(u!=null && u.getEmail().equalsIgnoreCase(userEmail)) {
				return u;
			}
		}
		return null;
	}
	
	void clearServiceState() {
		lastUserIndex =0;
		users = new ArrayList<User>(DEFAULT_USERS_CAPACITY);
	}
}
