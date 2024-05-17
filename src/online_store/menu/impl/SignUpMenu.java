package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.User;
import online_store.entities.impl.DefaultUser;
import online_store.menu.Menu;
import online_store.services.UserManagementService;
import online_store.services.impl.DefaultUserManagementService;

import java.util.ResourceBundle;
import java.util.Scanner;
public class SignUpMenu implements Menu{
	private ResourceBundle rb;
	private UserManagementService userManagementService;
	private ApplicationContext context;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		userManagementService = DefaultUserManagementService.getInstance();
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
		//sc.close();
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("sign.up.header"));
	}
}
