package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.User;
import online_store.menu.Menu;
import online_store.services.UserManagementService;
import online_store.services.impl.DefaultUserManagementService;

import java.util.ResourceBundle;
import java.util.Scanner;
public class SignInMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;
	private UserManagementService userManagementService;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
		userManagementService = DefaultUserManagementService.getInstance();
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
		
		if(user != null && user.getPassword().equals(password)) {
			context.setLoggedInUser(user);
			System.out.println(rb.getString("welcome.msg")+user.getFirstName() + user.getLastName());
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
