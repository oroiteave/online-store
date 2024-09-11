package com.magicbaits.core.facades;

import java.util.List;

import com.magicbaits.persistence.enteties.Purchase;

public interface PurchaseFacade {
	boolean addPurchase(Purchase purchase);
	
	List<Purchase> getPurchases();
	
	List<Purchase> getPurchaseByUserId(int id);
	
	List<Purchase> getPurchasesForPageWithLimit(int page, int paginationLimit);
	
	List<Purchase> getPurchasesForPageWithLimitByUserId(int page, int paginationLimit, int userId);
	
	int numberOfPagesForPurchasesByUserId(int paginationLimit, int userId);
	
	int numberOfPagesForPurchases(int paginationLimit);
	
	boolean updatePurchase(Purchase purchase);
	
	Purchase getPurchaseById(int id);
}