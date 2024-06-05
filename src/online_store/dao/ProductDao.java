package online_store.dao;

import java.util.List;

import online_store.dto.ProductDto;

public interface ProductDao {
	
	List<ProductDto> getProducts();

	ProductDto getProductByProductId(int productId);
}
