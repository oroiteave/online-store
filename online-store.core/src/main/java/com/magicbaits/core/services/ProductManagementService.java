package com.magicbaits.core.services;

import java.util.List;

import com.magicbaits.persistence.enteties.Product;


public interface ProductManagementService {
	List<Product> getProducts();

	Product getProductById(int productIdToAddToCart);

}
