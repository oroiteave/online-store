package com.magicbaits.core.menu.impl;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.core.configs.ApplicationContext;
import com.magicbaits.core.menu.Menu;
import com.magicbaits.core.services.ProductManagementService;
import com.magicbaits.core.services.impl.MySqlProductManagementService;
import com.magicbaits.persistence.enteties.Cart;
import com.magicbaits.persistence.enteties.Product;
public class ProductCatalogMenu implements Menu{
	private ResourceBundle rb;
	private static final String CHECKOUT_COMMAND = "checkout";
	private ApplicationContext context;
	private ProductManagementService productManagementService;

	{
		productManagementService = new MySqlProductManagementService();
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		Menu menuToNavigate = null;
		while(true) {
			printMenuHeader();
			printProducts();
			String userInput = readUserInput();
			if(context.getLoggedInUser()==null) {
				menuToNavigate = new MainMenu();
				System.out.println();
				System.out.println(rb.getString("product.catalog.not.logged.in.warning.msg"));
				System.out.println();
				break;
			}
			if(userInput.equalsIgnoreCase(MainMenu.MENU_COMMAND)) {
				menuToNavigate = new MainMenu();
				break;
			}
			
			if(userInput.equalsIgnoreCase(CHECKOUT_COMMAND)) {
				Cart cart = context.getSessionCart();
				if(cart == null || cart.isEmpty()) {
					System.out.println(rb.getString("empty.cart.error.msg"));
				}else {
					menuToNavigate = new CheckoutMenu();
					break;
				}
				
			}else {
				Product product = getProductToAdd(userInput);
				if(product == null) {
					System.out.println(rb.getString("enter.product.id.or.checkout"));
					continue;
				}
				addProductToCart(product);
			}
			
		}
		
		menuToNavigate.start();
	}

	
	public void printProducts() {
		List<Product> products = productManagementService.getProducts();
		for(Product p: products) {
			System.out.println(p.toString());
		}
	}
	
	private String readUserInput() {
		System.out.print(rb.getString("enter.product.id"));
		Scanner sc = new Scanner(System.in);
		String userInput = sc.next();
		//sc.close();
		return userInput;
	}
	
	private Product getProductToAdd(String userInput) {
		int procductIdtoAdd = Integer.parseInt(userInput);
		Product product = productManagementService.getProductById(procductIdtoAdd);
		return product;
	}
	
	private void addProductToCart(Product product) {
		context.getSessionCart().addProduct(product);
		System.out.printf(rb.getString("product.has.been.added"), product.getProductName());
	}
	
	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("product.catalog.header"));
		System.out.println(rb.getString("catalog.welcome"));	
		System.out.println();
	}
}
