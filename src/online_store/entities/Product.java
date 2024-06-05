package online_store.entities;

import java.io.Serializable;

public interface Product extends Serializable{
	int getId();
	String getProductName();
	String getCategoryName();
	double getPrice();
	
	void setId(int id);
	void setProductName(String newProductName);
	void setCategoryName(String newCategoryName);
	void setPrice(double price);
}
