package com.magicbaits.core.facades;

import java.util.List;

import com.magicbaits.persistence.enteties.Purchase;

public interface PurchaseFacade {
	boolean addPurchase(Purchase purchase);
	
	List<Purchase> getPurchases();
	
	List<Purchase> getPurchaseByUserId(int id);
	
	boolean updatePurchase(Purchase purchase);
	
	Purchase getPurchaseById(int id);
}