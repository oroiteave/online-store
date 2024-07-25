package com.magicbaits.persistence.dao;

import java.util.List;

import com.magicbaits.persistence.dto.PurchaseDto;

public interface PurchaseDao {
	
	void savePurchase(PurchaseDto order);
	
	List<PurchaseDto> getPurchaces();
	
	List<PurchaseDto> getPurchasesByUserId(int id);
}
