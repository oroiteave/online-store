package online_store.menu.impl;

import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

public class SignOutMenu implements Menu{
private ApplicationContext context;
	
	{
		context = ApplicationContext.getInstance();
	}
	
	@Override
	public void start() {
		System.out.println("*** Sign Out ***");
		
		context.setLoggedInUser(null);	
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println("*** SIGN OUT ***");
		System.out.println("Bye");
		System.out.println();
	}
}
