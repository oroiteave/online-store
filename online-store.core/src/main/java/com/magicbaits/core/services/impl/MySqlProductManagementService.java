package com.magicbaits.core.services.impl;

import java.util.List;

import com.magicbaits.core.services.ProductManagementService;
import com.magicbaits.persistence.dao.ProductDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcProductDao;
import com.magicbaits.persistence.dto.converter.ProductDtoToProductConverter;
import com.magicbaits.persistence.enteties.Product;


public class MySqlProductManagementService implements ProductManagementService{

	private ProductDao productDao;
	private ProductDtoToProductConverter converter;
	
	{
		productDao = new MySqlJdbcProductDao();
		converter = new ProductDtoToProductConverter();
	}
	
	@Override
	public List<Product> getProducts() {
		return converter.convertProductDtosToProducts(productDao.getProducts());
	}

	@Override
	public Product getProductById(int productIdToAddToCart) {
		return converter.convertProductDtoToProduct(productDao.getProductByProductId(productIdToAddToCart));
	}
}
