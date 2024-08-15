package com.magicbaits.persistence.enteties;

import java.util.List;

public interface Purchase {

	void setProducts(List<Product> products);

	void setCustomerId(int customerId);
	
	int getCustomerId();
	
	void setAddress(Address address);
	
	Address getAddress();
	
	List<Product> getProducts();
	
}
