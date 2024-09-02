package com.magicbaits.persistence.enteties;

import java.util.List;

public interface Purchase {

	int getCustomerId();
	Address getAddress();
	List<Product> getProducts();
	String getExtraMessage();
	String getShippingCompany();
	
	void setProducts(List<Product> products);
	void setCustomerId(int customerId);
	void setAddress(Address address);
	void setExtraMessage(String extraMessage);
	void setShippingCompany(String shippingCompany);
	
}
