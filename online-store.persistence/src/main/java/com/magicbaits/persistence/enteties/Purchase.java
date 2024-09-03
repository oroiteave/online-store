package com.magicbaits.persistence.enteties;

import java.util.List;

public interface Purchase {

	int getId();
	int getCustomerId();
	Address getAddress();
	List<Product> getProducts();
	String getExtraMessage();
	String getShippingCompany();
	String getStatus();
	
	void setId(int id);
	void setStatus(String status);
	void setProducts(List<Product> products);
	void setCustomerId(int customerId);
	void setAddress(Address address);
	void setExtraMessage(String extraMessage);
	void setShippingCompany(String shippingCompany);
	
}
