package com.magicbaits.persistence.enteties.impl;


import java.util.ArrayList;
import java.util.List;

import com.magicbaits.persistence.enteties.Cart;
import com.magicbaits.persistence.enteties.Product;
public class DefaultCart implements Cart{
	private static final int DEFAULT_PRODUCTS_NUMBER = 10;
	
	private List<Product> products;
	private int lastIndexAdded;
	
	{
		products = new ArrayList<Product>(DEFAULT_PRODUCTS_NUMBER);
	}
	
	@Override
	public boolean isEmpty() {
		if(products == null || products.size()==0) {
			return true;
		}
		for(Product p: products) {
			if(p!=null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void addProduct(Product product) {
		if(product==null) {
			return;
		}
		if(lastIndexAdded>=products.size()) {
			((ArrayList<Product>) products).ensureCapacity(products.size()<<1);
		}
		products.add(lastIndexAdded++, product);
	}

	@Override
	public List<Product> getProducts() {
		int notNulls=0;
		for(Product p: products) {
			if(p!=null) {
				notNulls++;
			}
		}
		List<Product> productNotNull = new ArrayList<Product>(notNulls);
		int i =0;
		for(Product p: products) {
			if(p!=null) {
				productNotNull.add(i, products.get(i));
				i++;
			}
		}
		return productNotNull;
	}

	@Override
	public void clear() {
		products = new ArrayList<Product>(DEFAULT_PRODUCTS_NUMBER);
	}
}