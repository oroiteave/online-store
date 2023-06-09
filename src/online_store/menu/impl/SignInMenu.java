package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.User;
import online_store.menu.Menu;
import online_store.services.UserManagementService;
import online_store.services.impl.DefaultUserManagementService;

import java.util.Scanner;
public class SignInMenu implements Menu{
	private ApplicationContext context;
	private UserManagementService userManagementService;

	{
		context = ApplicationContext.getInstance();
		userManagementService = DefaultUserManagementService.getInstance();
	}

	@Override
	public void start() {
		printMenuHeader();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter your email");
		String email = sc.next();
		System.out.println("Enter your password");
		String password = sc.next();
		
		User user = userManagementService.getUserByEmail(email);
		
		if(password!=null && password.equals(password)) {
			context.setLoggedInUser(user);
			System.out.println("Glad to see you back "+user.getFirstName() + user.getLastName());
		}else {
			System.out.println("Unfortunately, such login and password doesn’t exist");
		}
		sc.close();
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println("*** SIGN IN ***");
	}
}
