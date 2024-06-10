package online_store.services.impl;

import java.util.List;

import online_store.dao.ProductDao;
import online_store.dao.impl.MySqlJdbcProductDao;
import online_store.dto.converter.ProductDtoToProductConverter;
import online_store.entities.Product;
import online_store.services.ProductManagementService;

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
