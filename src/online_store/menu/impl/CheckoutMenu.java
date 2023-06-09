package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.Order;
import online_store.entities.impl.DefaultOrder;
import online_store.menu.Menu;
import online_store.services.OrderManagementService;
import online_store.services.impl.DefaultOrderManagementService;

import java.util.Scanner;
public class CheckoutMenu implements Menu{
	private ApplicationContext context;
	private OrderManagementService orderManagementService;
	
	{
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
			sc.close();
			context.getSessionCart().clear();
			break;
		}
		
		System.out.println("Thanks a lot for your purchase. Details about order delivery are sent to your email.");
		context.getMainMenu().start();
	}
	
	public boolean createOrder(String creditCardNumber) {
		Order order = new DefaultOrder();
		if(!order.isCreditCardNumberValid(creditCardNumber)) {
			System.out.println("You entered invalid credit card number. Valid credit card should contain 16 digits. Please, try one more time.");
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
		System.out.println("*** CHECKOUT ***");
		System.out.println("Enter your credit card number without spaces and press enter if you confirm purchase: ");
	}

}
