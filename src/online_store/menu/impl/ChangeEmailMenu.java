package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

import java.util.Scanner;
public class ChangeEmailMenu implements Menu{
	private ApplicationContext context;

	{
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		printMenuHeader();
		Scanner sc = new Scanner(System.in);
		String newEmail = sc.next();
		context.getLoggedInUser().setEmail(newEmail);
		System.out.println("Your email has been successfully changed");
		//sc.close();
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println("Enter a nwe email: ");
		System.out.println("*** CHANGE EMAIL ***");
		
	}
}
