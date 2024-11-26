package com.magicbaits.core.facades;

import java.util.List;

import com.magicbaits.persistence.enteties.Product;

public interface ProductFacade {
	
	boolean addProduct(Product product,int categoryId);
	
	boolean updateProduct(Product product);
	
	List<Product> getProductsLikeName(String searchQuery);

	List<Product> getProductsByCategoryId(int id);

	List<Product> getProductsByCategoryIdForPageWithLimit(int categoryId, int page, int paginationLimit);

	int getNumberOfPagesForCategory(int categoryId, int paginationLimit);

	int getNumberOfPagesForSearch(String searchQuery, int paginationLimit);
	
	int getNumberOfPagesForAllProducts(int paginationLimit);

	List<Product> getProductsLikeNameForPageWithLimit(String searchQuery, int page, int paginationLimit);

	Product getProductById(int parameter);
	
	List<Product> getProductsForPageWithLimit(int page, int paginationLimit);
	
	boolean deleteProduct(int productId);
	
	boolean decrementStock(int productId);
}
