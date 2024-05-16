package online_store.menu.impl;

import java.util.ResourceBundle;

import online_store.configs.ApplicationContext;
import online_store.menu.Menu;

public class SignOutMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;
	
	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
	}
	
	@Override
	public void start() {
		printMenuHeader();
		
		context.setLoggedInUser(null);	
		context.getMainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("sign.out.header"));
		System.out.println(rb.getString("bye.msg"));
		System.out.println();
	}
}
