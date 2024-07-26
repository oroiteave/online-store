package com.magicbaits.core.menu.impl;

import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.core.configs.ApplicationContext;
import com.magicbaits.core.menu.Menu;
import com.magicbaits.core.services.UserManagementService;
import com.magicbaits.core.services.impl.MySqlUserManagementService;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.persistence.enteties.impl.DefaultUser;

public class SignUpMenu implements Menu{
	private ResourceBundle rb;
	private UserManagementService userManagementService;
	private ApplicationContext context;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		userManagementService = new MySqlUserManagementService();
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		printMenuHeader();
		Scanner sc = new Scanner(System.in);
		
		System.out.println(rb.getString("enter.your.first.name"));
		String firstName = sc.next();
		System.out.println(rb.getString("enter.your.last.name"));
		String lastName = sc.next();
		System.out.println(rb.getString("enter.your.pass"));
		String password = sc.next();
		System.out.println(rb.getString("enter.your.email"));
		String email = sc.next();
		
		User user = new DefaultUser(firstName,lastName,password,email);
		
		String errorMessage = userManagementService.registerUser(user);
		if(errorMessage == null || errorMessage.isEmpty()) {
			context.setLoggedInUser(user);
			System.out.println(rb.getString("user.created.msg"));
		} else {
			System.out.println(errorMessage);
		}
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("sign.up.header"));
	}
}
