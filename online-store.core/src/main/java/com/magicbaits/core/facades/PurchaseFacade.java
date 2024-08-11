package com.magicbaits.core.facades;

import java.util.List;

import com.magicbaits.persistence.enteties.Purchase;

public interface PurchaseFacade {
	void addPurchase(Purchase purchase);
	
	List<Purchase> getPurchases();
	
	List<Purchase> getPurchaseByUserId(int id);
}