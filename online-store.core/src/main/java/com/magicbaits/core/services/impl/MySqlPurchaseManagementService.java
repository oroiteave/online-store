package com.magicbaits.core.services.impl;

import java.util.List;

import com.magicbaits.core.services.PurchaseManagementService;
import com.magicbaits.persistence.dao.PurchaseDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcPurchaseDao;
import com.magicbaits.persistence.dto.converter.PurchaseDtoToPurchaseConverter;
import com.magicbaits.persistence.enteties.Purchase;

public class MySqlPurchaseManagementService implements PurchaseManagementService{

	private PurchaseDao purchaseDao;
	private PurchaseDtoToPurchaseConverter converter;
	
	{
		purchaseDao = new MySqlJdbcPurchaseDao();
		converter = new PurchaseDtoToPurchaseConverter();
	}
	
	@Override
	public void addPurchase(Purchase purchase) {
		purchaseDao.savePurchase(converter.convertPurchaseToPurchaseDto(purchase));
	}

	@Override
	public List<Purchase> getPurchasesByUserId(int userId) {
		return converter.convertPurchaseDtosToPurchases(purchaseDao.getPurchasesByUserId(userId));
	}

	@Override
	public List<Purchase> getPurchases() {
		// TODO Auto-generated method stub
		return null;
	}

}
