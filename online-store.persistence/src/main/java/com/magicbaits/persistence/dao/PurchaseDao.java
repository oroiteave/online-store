package com.magicbaits.persistence.dao;

import java.util.List;

import com.magicbaits.persistence.dto.PurchaseDto;

public interface PurchaseDao {
	
	boolean savePurchase(PurchaseDto order);
	
	List<PurchaseDto> getPurchaces();
	
	List<PurchaseDto> getPurchasesByUserId(int id);
	
	boolean updatePurchase(PurchaseDto purchase);
	
	PurchaseDto getPurchaseById(int id);
	
	List<PurchaseDto> getPurchasePaginationLimit(int page, int paginationLimit);
	
	List<PurchaseDto> getPurchasePaginationLimitByUserId(int page, int paginationLimit, int userId);
	
	int getPurchaseCount();
	
	int getPurchaseCountByUserId(int id);
	
	boolean deletePurchaseById(int id);
}
