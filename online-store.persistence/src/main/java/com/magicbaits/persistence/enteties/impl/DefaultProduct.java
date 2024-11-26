package com.magicbaits.persistence.enteties.impl;

import com.magicbaits.persistence.enteties.Product;

public class DefaultProduct implements Product{
	private int id;
	private String productName;
	private String categoryName;
	private double price;
	private String imgName;
	private String description;
	private int stock;
	
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
	@Override
	public String getImgName() {
		return this.imgName;
	}
	@Override
	public String getDescription() {
		return this.description;
	}
	@Override
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int getStock() {
		return this.stock;
	}
	@Override
	public void setStock(int stock) {
		this.stock = stock;
	}
}
