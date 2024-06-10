package online_store.menu.impl;

import java.util.List;
import java.util.ResourceBundle;

import online_store.configs.ApplicationContext;
import online_store.entities.Purchase;
import online_store.menu.Menu;
import online_store.services.PurchaseManagementService;
import online_store.services.impl.MySqlPurchaseManagementService;

public class MyPurchasesMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;
	private PurchaseManagementService purchaseManagementService;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
		purchaseManagementService = new MySqlPurchaseManagementService();
	}

	@Override
	public void start() {
		if(context.getLoggedInUser()==null) {
			System.out.println(rb.getString("orders.not.logged.error.msg"));
			new MainMenu().start();
			return;
		}
		
		List<Purchase> loggedProducts = purchaseManagementService.getPurchasesByUserId(context.getLoggedInUser().getId());
		if(loggedProducts == null || loggedProducts.size() ==0) {
			System.out.println(rb.getString("no.orders.yet.error.msg"));
		}else {
			printMenuHeader();
			printPurchases(loggedProducts);
		}
		
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("orders.header"));
		System.out.println();
	}
	private void printPurchases(List<Purchase> purchases) {
		int i=1;
		for(Purchase o: purchases) {
			System.out.println("------ "+rb.getString("order")  + i + " ------");
			System.out.println(o);
			i++;
		}
	}
}
