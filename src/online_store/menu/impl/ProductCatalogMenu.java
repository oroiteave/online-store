package online_store.menu.impl;


import online_store.configs.ApplicationContext;
import online_store.entities.Cart;
import online_store.entities.Product;
import online_store.menu.Menu;
import online_store.services.ProductManagementService;
import online_store.services.impl.DefaultProductManagementService;

import java.util.Scanner;
public class ProductCatalogMenu implements Menu{
	private static final String CHECKOUT_COMMAND = "checkout";
	private ApplicationContext context;
	private ProductManagementService productManagementService;

	{
		context = ApplicationContext.getInstance();
		productManagementService = DefaultProductManagementService.getInstance();
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
				System.out.println("You are not logged, sign in or create a new account");
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
					System.out.println("Your cart is empty. Please, add product to cart first and then proceed with checkout");
				}else {
					menuToNavigate = new CheckoutMenu();
					break;
				}
				
			}else {
				Product product = getProductToAdd(userInput);
				if(product == null) {
					System.out.println("Please, enter product ID if you want to add product to cart. Or enter ‘checkout’ "
							+ "if you want to proceed with checkout. Or enter ‘menu’ if you want to navigate back to the main menu.");
					continue;
				}
				addProductToCart(product);
			}
			
		}
		
		menuToNavigate.start();
	}

	
	public void printProducts() {
		Product[] products = productManagementService.getProducts();
		for(Product p: products) {
			System.out.println(p.toString());
		}
	}
	
	private String readUserInput() {
		System.out.print("Product ID to add to cart or enter 'checkout' to proceed with checkout: ");
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
		System.out.printf("Product %s has been added to your cart. "
				+ "If you want to add a new product - enter the product id. "
				+ "If you want to proceed with checkout - enter word "
				+ "'checkout' to console %n", product.getProductName());
	}
	
	@Override
	public void printMenuHeader() {
		System.out.println("*** PRODUCT CATALOG ***");
		System.out.println("Enter product id to add it to the cart or 'menu' if you want to navigate back to the main menu");	
		System.out.println();
	}
}
