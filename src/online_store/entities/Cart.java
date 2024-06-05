package online_store.entities;

import java.util.List;

public interface Cart {
	boolean isEmpty();

	void addProduct(Product productById);

	List<Product> getProducts();

	void clear();
}
