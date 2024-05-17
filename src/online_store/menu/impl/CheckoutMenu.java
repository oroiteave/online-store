package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.Order;
import online_store.entities.impl.DefaultOrder;
import online_store.menu.Menu;
import online_store.services.OrderManagementService;
import online_store.services.impl.DefaultOrderManagementService;

import java.util.ResourceBundle;
import java.util.Scanner;
public class CheckoutMenu implements Menu{
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
		printMenuHeader();
		
		while(true) {
			
			Scanner sc = new Scanner(System.in);
			String userInput = sc.next();
			
			if (!createOrder(userInput)) {
				continue;
			}
			//sc.close();
			context.getSessionCart().clear();
			break;
		}
		
		System.out.println(rb.getString("thanks"));
		context.getMainMenu().start();
	}
	
	public boolean createOrder(String creditCardNumber) {
		Order order = new DefaultOrder();
		if(!order.isCreditCardNumberValid(creditCardNumber)) {
			System.out.println(rb.getString("invalid.credit.card.error.msg"));
			return false;
		}
		order.setCreditCardNumber(creditCardNumber);
		order.setCustomerId(context.getLoggedInUser().getId());
		order.setProducts(context.getSessionCart().getProducts());
		orderManagementService.addOrder(order);
		return true;

	}
	
	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("checkout.header"));
		System.out.println(rb.getString("enter.your.credit.card"));
	}

}
