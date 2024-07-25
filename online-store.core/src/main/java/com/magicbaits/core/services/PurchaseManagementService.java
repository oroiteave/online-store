package com.magicbaits.core.services;

import java.util.List;

import com.magicbaits.persistence.enteties.Purchase;


public interface PurchaseManagementService {
	void addPurchase(Purchase purchase);

	List<Purchase> getPurchasesByUserId(int userId);
	
	List<Purchase> getPurchases();

}
