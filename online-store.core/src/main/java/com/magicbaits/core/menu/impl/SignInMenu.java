package com.magicbaits.core.menu.impl;



import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.core.configs.ApplicationContext;
import com.magicbaits.core.menu.Menu;
import com.magicbaits.core.services.UserManagementService;
import com.magicbaits.core.services.impl.MySqlUserManagementService;
import com.magicbaits.persistence.enteties.User;
public class SignInMenu implements Menu{
	
	private ResourceBundle rb;
	private ApplicationContext context;
	private UserManagementService userManagementService;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
		userManagementService = new MySqlUserManagementService();
	}

	@Override
	public void start() {
		printMenuHeader();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println(rb.getString("enter.your.email"));
		String email = sc.next();
		System.out.println(rb.getString("enter.your.password"));
		String password = sc.next();
		
		User user = userManagementService.getUserByEmail(email);
		
		if(user != null && user.getPassword() != null && user.getPassword().equals(password)) {
			context.setLoggedInUser(user);
			System.out.println(rb.getString("welcome.msg")+user.getFirstName()+ " " + user.getLastName());
		}else {
			System.out.println(rb.getString("login.or.password.doest.exist.error.msg"));
		}
		//sc.close();
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("sign.in.header"));
	}
}
