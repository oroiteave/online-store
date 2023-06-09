package online_store.services;

import online_store.entities.Order;

public interface OrderManagementService {
	void addOrder(Order order);

	Order[] getOrdersByUserId(int userId);
	
	Order[] getOrders();

}
