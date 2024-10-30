package com.magicbaits.persistence.dao;

import java.util.List;

import com.magicbaits.persistence.dto.ProductDto;

public interface ProductDao {
	
	boolean saveProduct(ProductDto product);
	
	List<ProductDto> getProducts();

	ProductDto getProductByProductId(int productId);
	
	List<ProductDto> getProductsLikeName(String searchQuery);

	List<ProductDto> getProductsByCategoryId(int id);

	List<ProductDto> getProductsByCategoryIdPaginationLimit(int categoryId, int page, int paginationLimit);

	int getProductCountForCategory(int categoryId);

	int getProductCountForSearch(String searchQuery);

	List<ProductDto> getProductsLikeNameForPageWithLimit(String searchQuery, int page, int paginationLimit);
}
