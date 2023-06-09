package online_store.services.impl;


import online_store.entities.Order;
import online_store.services.OrderManagementService;

import java.util.Arrays;
public class DefaultOrderManagementService implements OrderManagementService{
	private static final int DEFAULT_ORDER_CAPACITY = 10;

	private static DefaultOrderManagementService instance;

	private Order[] orders;
	private int lastIndexOrder;
	
	{
		orders = new Order[DEFAULT_ORDER_CAPACITY];
	}
	
	public static OrderManagementService getInstance() {
		if (instance == null) {
			instance = new DefaultOrderManagementService();
		}
		return instance;
	}

	@Override
	public void addOrder(Order order) {
		if(order == null) {
			return;
		}
		if(lastIndexOrder>= orders.length) {
			orders = Arrays.copyOf(orders, orders.length<<1);
		}
		orders[lastIndexOrder++]= order;
	}

	@Override
	public Order[] getOrdersByUserId(int userId) {
		int indexUserOrders=0;
		for(Order o: orders) {
			if(o != null && o.getCustomerId() == userId) {
				indexUserOrders++;
			}
		}
		
		Order[] ordersUser = new Order[indexUserOrders];
		
		int i=0;
		for(Order o: orders) {
			if(o!=null && o.getCustomerId()==userId) {
				ordersUser[i++] = o;
			}
		}
		return ordersUser;
	}

	@Override
	public Order[] getOrders() {
		int notNull=0;
		for(Order o: orders) {
			if(o!=null) {
				notNull++;
			}
		}
		int i=0;
		Order[] ordersNotNull = new Order[notNull];
		for(Order o: orders) {
			if(o!= null) {
				ordersNotNull[i++] = o;
			}
		}
		return ordersNotNull;
	}
	
	void clearServiceState() {
		orders = new Order[DEFAULT_ORDER_CAPACITY];
		lastIndexOrder =0;
	}
}
