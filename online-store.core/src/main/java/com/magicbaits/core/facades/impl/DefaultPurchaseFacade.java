package com.magicbaits.core.facades.impl;

import java.util.List;

import com.magicbaits.core.facades.PurchaseFacade;
import com.magicbaits.persistence.dao.PurchaseDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcPurchaseDao;
import com.magicbaits.persistence.dto.converter.PurchaseDtoToPurchaseConverter;
import com.magicbaits.persistence.enteties.Purchase;

public class DefaultPurchaseFacade implements PurchaseFacade{

	private static DefaultPurchaseFacade instance;
	private PurchaseDtoToPurchaseConverter converter = new PurchaseDtoToPurchaseConverter();
	private PurchaseDao purchaseDao = new MySqlJdbcPurchaseDao();
	public static synchronized DefaultPurchaseFacade getInstance() {
		if(instance ==null) {
			instance = new DefaultPurchaseFacade();
		}
		return instance;
	}
	
	@Override
	public boolean addPurchase(Purchase purchase) {
		return purchaseDao.savePurchase(converter.convertPurchaseToPurchaseDto(purchase));
	}

	@Override
	public List<Purchase> getPurchases() {
		return converter.convertPurchaseDtosToPurchases(purchaseDao.getPurchaces());
	}

	@Override
	public List<Purchase> getPurchaseByUserId(int id) {
		return converter.convertPurchaseDtosToPurchases(purchaseDao.getPurchasesByUserId(id));
	}
}