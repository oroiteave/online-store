package online_store.services;

import online_store.entities.Product;

public interface ProductManagementService {
	Product[] getProducts();

	Product getProductById(int productIdToAddToCart);

}
