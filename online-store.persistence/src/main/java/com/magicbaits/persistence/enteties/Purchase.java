package com.magicbaits.persistence.enteties;

import java.util.List;

public interface Purchase {
	boolean isCreditCardNumberValid(String userInput);

	void setCreditCardNumber(String userInput);

	void setProducts(List<Product> products);

	void setCustomerId(int customerId);
	
	int getCustomerId();
	
	List<Product> getProducts();
	
}
