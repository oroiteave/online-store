package online_store.services;

import java.util.List;

import online_store.entities.Product;

public interface ProductManagementService {
	List<Product> getProducts();

	Product getProductById(int productIdToAddToCart);

}
