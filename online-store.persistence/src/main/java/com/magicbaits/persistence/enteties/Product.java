package com.magicbaits.persistence.enteties;

public interface Product{
	int getId();
	String getProductName();
	String getCategoryName();
	double getPrice();
	String getImgName();
	String getDescription();
	
	void setImgName(String imgName);
	void setDescription(String description);
	void setId(int id);
	void setProductName(String newProductName);
	void setCategoryName(String newCategoryName);
	void setPrice(double price);
}
