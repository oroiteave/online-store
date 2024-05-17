package online_store.menu.impl;

import java.util.ResourceBundle;

import online_store.configs.ApplicationContext;
import online_store.entities.Order;
import online_store.menu.Menu;
import online_store.services.OrderManagementService;
import online_store.services.impl.DefaultOrderManagementService;

public class MyOrdersMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;
	private OrderManagementService orderManagementService;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
		orderManagementService = DefaultOrderManagementService.getInstance();
	}

	@Override
	public void start() {
		if(context.getLoggedInUser()==null) {
			System.out.println(rb.getString("orders.not.logged.error.msg"));
			new MainMenu().start();
			return;
		}
		
		Order[] loggedOrders = orderManagementService.getOrdersByUserId(context.getLoggedInUser().getId());
		if(loggedOrders == null || loggedOrders.length ==0) {
			System.out.println(rb.getString("no.orders.yet.error.msg"));
		}else {
			printMenuHeader();
			printOrders(loggedOrders);
		}
		
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("orders.header"));
		System.out.println();
	}
	private void printOrders(Order[] orders) {
		int i=1;
		for(Order o: orders) {
			System.out.println("------ "+rb.getString("order")  + i + " ------");
			System.out.println(o);
			i++;
		}
	}
}
