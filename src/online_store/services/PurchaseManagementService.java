package online_store.services;

import java.util.List;

import online_store.entities.Purchase;

public interface PurchaseManagementService {
	void addPurchase(Purchase purchase);

	List<Purchase> getPurchasesByUserId(int userId);
	
	List<Purchase> getPurchases();

}
