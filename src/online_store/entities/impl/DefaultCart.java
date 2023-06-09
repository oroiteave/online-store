package online_store.entities.impl;


import online_store.entities.Cart;
import online_store.entities.Product;

import java.util.Arrays;
public class DefaultCart implements Cart{
	private static final int DEFAULT_PRODUCTS_NUMBER = 10;
	
	private Product[] products;
	private int lastIndexAdded;
	
	{
		products = new Product[DEFAULT_PRODUCTS_NUMBER];
	}
	
	@Override
	public boolean isEmpty() {
		if(products == null || products.length==0) {
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
		if(lastIndexAdded>=products.length) {
			products = Arrays.copyOf(products, products.length<<1);
		}
		products[lastIndexAdded++] = product;
	}

	@Override
	public Product[] getProducts() {
		int notNulls=0;
		for(Product p: products) {
			if(p!=null) {
				notNulls++;
			}
		}
		Product[] productNotNull = new Product[notNulls];
		int i =0;
		for(Product p: products) {
			if(p!=null) {
				productNotNull[i] = products[i];
				i++;
			}
		}
		return productNotNull;
	}

	@Override
	public void clear() {
		products = new Product[DEFAULT_PRODUCTS_NUMBER];
	}
}
