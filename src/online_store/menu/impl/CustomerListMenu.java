package online_store.menu.impl;

import java.util.List;
import java.util.ResourceBundle;

import online_store.entities.User;
import online_store.menu.Menu;
import online_store.services.UserManagementService;
import online_store.services.impl.MySqlUserManagementService;

public class CustomerListMenu implements Menu{
	private ResourceBundle rb;
	private UserManagementService userManagementService;
	
	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		userManagementService = new MySqlUserManagementService();
	}
	
	@Override
	public void start() {
		printMenuHeader();
		printCustomers();
		new MainMenu().start();
	}

	private void printCustomers() {
		List<User> users = userManagementService.getUsers();
		for(User u: users) {
			System.out.println(u.toString());
		}
	}
	
	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("users"));
	}

}
