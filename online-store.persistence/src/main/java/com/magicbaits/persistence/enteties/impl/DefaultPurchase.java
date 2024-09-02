package com.magicbaits.persistence.enteties.impl;


import java.util.List;

import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.Purchase;

public class DefaultPurchase implements Purchase{
	
	private List<Product> products;
	private int customerId;
	private Address address;
	private String shippingCompany;
	private String extraMessage;


	
	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getExtraMessage() {
		return extraMessage;
	}

	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
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
	public Address getAddress() {
		return address;
	}
	
	@Override
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Order: customer id - " + this.customerId + "\t" + 
				"products - " + (this.products);
	}



}
