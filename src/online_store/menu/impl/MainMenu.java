package online_store.menu.impl;

import online_store.Main;
import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
public class MainMenu implements Menu{
	public static final String MENU_COMMAND = "menu";
	private ResourceBundle rb;
	private ApplicationContext context;
	
	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
	}
	
	@Override
	public void start() {
		if (context.getMainMenu() == null) {
			context.setMainMenu(this);
		}
		
		Menu menuToNavigate = null;
		mainLoop: while (true) {
			printMenuHeader();
			
			Scanner sc = new Scanner(System.in);

			System.out.print(rb.getString("user.input"));
			String userInput = sc.next();
			if (userInput.equalsIgnoreCase(Main.EXIT_COMMAND)) {
				System.exit(0);
			} else {
				int commandNumber = Integer.parseInt(userInput);
				switch (commandNumber) {
				
				case 1:
					menuToNavigate = new SignUpMenu();
					break mainLoop;
				case 2:
					if (context.getLoggedInUser() == null) {
						menuToNavigate = new SignInMenu();
					} else {
						menuToNavigate = new SignOutMenu();
					}
					break mainLoop;
				case 3:
					menuToNavigate = new ProductCatalogMenu();
					break mainLoop;
				case 4:
					menuToNavigate = new MyPurchasesMenu();
					break mainLoop;
				case 5:
					menuToNavigate = new SettingsMenu();
					break mainLoop;
				case 6:
					menuToNavigate = new CustomerListMenu();
					break mainLoop;
				case 7:
					menuToNavigate = new ChangeLenguageMenu();
					break mainLoop;
				case 8:
					menuToNavigate = new ResetPasswordMenu();
					break mainLoop;
				default:
					System.out.println(rb.getString("error.msg"));
					continue;
				}
			}
			//sc.close();
		}
		menuToNavigate.start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("main.menu.header"));
		if (context.getLoggedInUser() == null) {
			System.out.println(rb.getString("main.menu.for.logged.out.user"));
		} else {
			System.out.println(rb.getString("main.menu.for.logged.in.user"));
		}
	}
}