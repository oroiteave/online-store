package online_store.menu.impl;

import online_store.configs.ApplicationContext;
import online_store.entities.Order;
import online_store.menu.Menu;
import online_store.services.OrderManagementService;
import online_store.services.impl.DefaultOrderManagementService;

public class MyOrdersMenu implements Menu{
	private ApplicationContext context;
	private OrderManagementService orderManagementService;

	{
		context = ApplicationContext.getInstance();
		orderManagementService = DefaultOrderManagementService.getInstance();
	}

	@Override
	public void start() {
		if(context.getLoggedInUser()==null) {
			System.out.println("Please, log in or create new account to see list of your orders");
			new MainMenu().start();
			return;
		}
		
		Order[] loggedOrders = orderManagementService.getOrdersByUserId(context.getLoggedInUser().getId());
		if(loggedOrders == null || loggedOrders.length ==0) {
			System.out.println("Unfortunately, you don’t have any orders yet. Navigate back to main menu to place a new order");
		}else {
			printMenuHeader();
			printOrders(loggedOrders);
		}
		
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println("*** MY ORDERS ***");
		System.out.println();
	}
	private void printOrders(Order[] orders) {
		int i=1;
		for(Order o: orders) {
			System.out.println("------ Order " + i + " ------");
			System.out.println(o);
			i++;
		}
	}
}
