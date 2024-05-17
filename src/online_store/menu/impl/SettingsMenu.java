package online_store.menu.impl;


import online_store.Main;
import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

import java.util.ResourceBundle;
import java.util.Scanner;
public class SettingsMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		if(context.getLoggedInUser() == null) {
			System.out.println(rb.getString("settings.not.logged.in.error.msg"));
			new MainMenu().start();
			return;
		}
		while(true) {
			printMenuHeader();
			Scanner sc = new Scanner(System.in);
			String userInput = sc.next();
			if (userInput.equalsIgnoreCase(Main.EXIT_COMMAND)) {
				System.exit(0);
			}
			if(userInput.equalsIgnoreCase("menu")) {
				new MainMenu().start();
			}
			if(!userInput.equals("1") && !userInput.equals("2")) {
				System.out.println(rb.getString("setting.option.validate.msg"));
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
			//sc.close();
		}
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("settings.header"));
		System.out.println(rb.getString("settings.options"));
	}
}
