package com.magicbaits.core.menu.impl;

import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.Main;
import com.magicbaits.core.menu.Menu;
import com.magicbaits.core.services.UserManagementService;
import com.magicbaits.core.services.impl.MySqlUserManagementService;
import com.magicbaits.persistence.enteties.User;

public class ResetPasswordMenu implements Menu{

	private ResourceBundle rb;
	private UserManagementService userManagementService;
	
	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		userManagementService = new MySqlUserManagementService();
	}
	
	@Override
	public void start() {
		while(true){
			printMenuHeader();
			Scanner sc = new Scanner(System.in); 
			
			String userInput = sc.next();
			if(userInput.equalsIgnoreCase(Main.EXIT_COMMAND)) {
				System.exit(0);
			}
			if(userInput.equalsIgnoreCase("menu")){
				break;
			}
			User user = userManagementService.getUserByEmail(userInput);
			if(user !=null) {
				Thread thread = new Thread(() -> userManagementService.resetPasswordForUser(user));
				thread.start();
				break;
			}else {
				System.out.println(rb.getString("user.with.the.email.not.exist"));
			}
		}
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("reset.password.header"));
		System.out.println(rb.getString("user.email.request"));
	}

}
