package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.User;
import online_store.entities.impl.DefaultUser;
import online_store.menu.Menu;
import online_store.services.UserManagementService;
import online_store.services.impl.DefaultUserManagementService;

import java.util.Scanner;
public class SignUpMenu implements Menu{
	private UserManagementService userManagementService;
	private ApplicationContext context;

	{
		userManagementService = DefaultUserManagementService.getInstance();
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		printMenuHeader();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter your first name");
		String firstName = sc.next();
		System.out.println("Enter your last name");
		String lastName = sc.next();
		System.out.println("Enter a new password");
		String password = sc.next();
		System.out.println("Enter a email");
		String email = sc.next();
		
		User user = new DefaultUser(firstName,lastName,password,email);
		
		String errorMessage = userManagementService.registerUser(user);
		if(errorMessage == null || errorMessage.isEmpty()) {
			context.setLoggedInUser(user);
			System.out.println("New user is created");
		} else {
			System.out.println(errorMessage);
		}
		sc.close();
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println("***** SIGN UP *****");
	}
}
