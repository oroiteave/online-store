package online_store.entities.impl;

import online_store.entities.Product;

public class DefaultProduct implements Product{
	private int id;
	private String productName;
	private String categoryName;
	private double price;

	public DefaultProduct() {
	}
	public DefaultProduct(int id, String productName, String categoryName, double price) {
		this.id = id;
		this.productName = productName;
		this.categoryName = categoryName;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product id=" + id + ", product name=" + productName
				+ ", category name=" + categoryName + ", price=" + price;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getProductName() {
		return productName;
	}
	@Override
	public String getCategoryName() {
		return categoryName;
	}
	@Override
	public double getPrice() {
		return price;
	}
	@Override
	public void setPrice(double price) {
		this.price = price;
		
	}
	@Override
	public void setId(int id) {
		this.id = id;
		
	}
	@Override
	public void setProductName(String productName) {
		this.productName = productName;
		
	}
	@Override
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
		
	}
}
