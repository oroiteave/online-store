package com.magicbaits.core.services.impl;

import java.util.List;

import com.magicbaits.core.mail.MailSender;
import com.magicbaits.core.mail.impl.DefaultMailSender;
import com.magicbaits.core.services.UserManagementService;
import com.magicbaits.persistence.dao.UserDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcUserDao;
import com.magicbaits.persistence.dto.converter.UserDtoToUserConverter;
import com.magicbaits.persistence.enteties.User;


public class MySqlUserManagementService implements UserManagementService{
	
	public static final String SUCCESSFULL_REGISTRATION_MESSAGE = "";
	private static final String REGISTRATION_ERROR_MESSAGE = "The email is already in use by other user.";
	
	private UserDao userDao;
	private UserDtoToUserConverter converter;
	private MailSender mailSender;
	
	{
		userDao = new MySqlJdbcUserDao();
		converter = new UserDtoToUserConverter();
		mailSender = DefaultMailSender.getInstance();
	}

	@Override
	public String registerUser(User user) {
		boolean isCreated = userDao.saveUser(converter.convertUserToUserDto(user));
		
		if(isCreated) {
			return SUCCESSFULL_REGISTRATION_MESSAGE;
		}
		return REGISTRATION_ERROR_MESSAGE;
	}

	@Override
	public List<User> getUsers() {
		return converter.convertUserDtosToUsers(userDao.getUsers());
	}

	@Override
	public User getUserByEmail(String userEmail) {
		return converter.convertUserDtoToUser(userDao.getUserByEmail(userEmail));
	}

	@Override
	public void resetPasswordForUser(User user) {
		mailSender.sendEmail(user.getEmail(), "Please, use this password to login: " + user.getPassword());
	}

}
