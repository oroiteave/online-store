package online_store.entities.impl;


import online_store.entities.Purchase;
import online_store.entities.Product;
import java.util.List;

public class DefaultPurchase implements Purchase{
private static final int AMOUNT_OF_DIGITS_IN_CREDIT_CARD_NUMBER = 16;
	
	private String creditCardNumber;
	private List<Product> products;
	private int customerId;

	@Override
	public boolean isCreditCardNumberValid(String creditCardNumber) {
		return creditCardNumber.toCharArray().length == AMOUNT_OF_DIGITS_IN_CREDIT_CARD_NUMBER && 
				!creditCardNumber.contains(" ") && Long.parseLong(creditCardNumber) > 0;
	}

	@Override
	public void setCreditCardNumber(String creditCardNumber) {
		if(creditCardNumber == null) {
			return;
		}
		this.creditCardNumber = creditCardNumber;
	}
	
	@Override
	public void setProducts(List<Product> products) {
		if (products == null) {
			return;
		}
		this.products = products;
	}

	@Override
	public List<Product> getProducts() {
		return products;
	}
	
	@Override
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	@Override
	public int getCustomerId() {
		return this.customerId;
	}
	
	
	@Override
	public String toString() {
		return "Order: customer id - " + this.customerId + "\t" +
				"credit card number - " + this.creditCardNumber + "\t" + 
				"products - " + (this.products);
	}



}
