package com.magicbaits.core.menu.impl;



import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.core.configs.ApplicationContext;
import com.magicbaits.core.menu.Menu;
import com.magicbaits.core.services.PurchaseManagementService;
import com.magicbaits.core.services.impl.MySqlPurchaseManagementService;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.impl.DefaultPurchase;

public class CheckoutMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;
	private PurchaseManagementService productManagementService;
	
	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
		productManagementService = new MySqlPurchaseManagementService();
	}
	
	@Override
	public void start() {
		printMenuHeader();
		
		while(true) {
			
			Scanner sc = new Scanner(System.in);
			String userInput = sc.next();
			
			if (!createPurchase(userInput)) {
				continue;
			}
			context.getSessionCart().clear();
			break;
		}
		
		System.out.println(rb.getString("thanks"));
		context.getMainMenu().start();
	}
	
	public boolean createPurchase(String creditCardNumber) {
		Purchase purchase = new DefaultPurchase();
		if(!purchase.isCreditCardNumberValid(creditCardNumber)) {
			System.out.println(rb.getString("invalid.credit.card.error.msg"));
			return false;
		}
		purchase.setCreditCardNumber(creditCardNumber);
		purchase.setCustomerId(context.getLoggedInUser().getId());
		purchase.setProducts(context.getSessionCart().getProducts());
		productManagementService.addPurchase(purchase);
		return true;

	}
	
	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("checkout.header"));
		System.out.println(rb.getString("enter.your.credit.card"));
	}

}
