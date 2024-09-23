package com.magicbaits.web.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.facades.impl.DefaultUserFacade;
import com.magicbaits.persistence.enteties.User;

public class PasswordsEncoderScript {

	public static void main(String[] args){
		UserFacade userFacade = DefaultUserFacade.getInstance();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		for(int i = 29;i<46;i++) {
			User user = userFacade.getUserByid(i);
			System.out.println(user.getEmail());
			String password = user.getPassword();
			user.setPassword(passwordEncoder.encode(password));
			userFacade.updateUser(user);
		}
		
	}
}