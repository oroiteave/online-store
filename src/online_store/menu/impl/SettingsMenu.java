package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

import java.util.Scanner;
public class SettingsMenu implements Menu{
	private static final String SETTINGS = "1. Change Password" + System.lineSeparator()
	+ "2. Change Email";

	private ApplicationContext context;

	{
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		if(context.getLoggedInUser() == null) {
			System.out.println("Please, log in or create new account to change your account settings");
			new MainMenu().start();
			return;
		}
		while(true) {
			printMenuHeader();
			Scanner sc = new Scanner(System.in);
			String userInput = sc.next();
			if(userInput.equalsIgnoreCase("menu")) {
				new MainMenu().start();
			}
			if(!userInput.equals("1") || !userInput.equals("2")) {
				System.out.println("Only 1, 2 is allowed. Try one more time");
				continue;
			}else {
				int userOption = Integer.parseInt(userInput);
				switch (userOption) {
				case 1:
					new ChangePasswordMenu().start();
					break;
				case 2:
					new ChangeEmailMenu().start();
					break;
				default:
					new MainMenu().start();
				}
			}
			sc.close();
		}
	}

	@Override
	public void printMenuHeader() {
		System.out.println("*** SETTINGS ***");
		System.out.println(SETTINGS);
	}
}
