package com.magicbaits.core.facades.impl;

import java.util.List;

import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.persistence.dao.ProductDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcProductDao;
import com.magicbaits.persistence.dto.converter.ProductDtoToProductConverter;
import com.magicbaits.persistence.enteties.Product;

public class DefaultProductFacade implements ProductFacade {
	ProductDao productDao = new MySqlJdbcProductDao();
	ProductDtoToProductConverter productConverter = new ProductDtoToProductConverter();
	private static DefaultProductFacade instance;
	
	public static synchronized DefaultProductFacade getInstance() {
		if (instance == null) {
			instance = new DefaultProductFacade();
		}
		return instance;
	}

	@Override
	public List<Product> getProductsLikeName(String searchQuery) {
		return productConverter.convertProductDtosToProducts(productDao.getProductsLikeName(searchQuery));
	}

	@Override
	public List<Product> getProductsByCategoryId(int id) {
		return productConverter.convertProductDtosToProducts(productDao.getProductsByCategoryId(id));
	}

	@Override
	public List<Product> getProductsByCategoryIdForPageWithLimit(int categoryId, int page, int paginationLimit) {
		return productConverter.convertProductDtosToProducts(productDao.getProductsByCategoryIdPaginationLimit(categoryId, page, paginationLimit));
	}

	@Override
	public int getNumberOfPagesForCategory(int categoryId, int paginationLimit) {
		int totalProductsInCategory = productDao.getProductCountForCategory(categoryId);
		int pages = totalProductsInCategory / paginationLimit;
		if ( (totalProductsInCategory % paginationLimit) != 0) {
			pages++;
		}
		return pages;
	}

	@Override
	public int getNumberOfPagesForSearch(String searchQuery, int paginationLimit) {
		int totalProductsForSearch = productDao.getProductCountForSearch(searchQuery);
		int pages = totalProductsForSearch / paginationLimit;
		if ( (totalProductsForSearch % paginationLimit) != 0) {
			pages++;
		}
		return pages;
	}

	@Override
	public List<Product> getProductsLikeNameForPageWithLimit(String searchQuery, int page,int paginationLimit) {
		return productConverter
				.convertProductDtosToProducts(productDao.getProductsLikeNameForPageWithLimit(searchQuery, page, paginationLimit));
	}

	@Override
	public Product getProductById(int productId) {
		return productConverter.convertProductDtoToProduct(productDao.getProductByProductId(productId));
	}

}
