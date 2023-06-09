package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

import java.util.Scanner;
public class ChangePasswordMenu implements Menu{
	private ApplicationContext context;

	{
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		printMenuHeader();
		Scanner sc = new Scanner(System.in);
		String newPassword = sc.next();
		context.getLoggedInUser().setPassword(newPassword);
		System.out.println("Your password has been successfully changed");
		sc.close();
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println("*** CHANGE PASSWORD ***");
		System.out.println("enter a new password: ");		
	}
}
