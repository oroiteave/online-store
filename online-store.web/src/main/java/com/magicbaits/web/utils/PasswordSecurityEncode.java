package com.magicbaits.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordSecurityEncode {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	public String encoder(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
}
