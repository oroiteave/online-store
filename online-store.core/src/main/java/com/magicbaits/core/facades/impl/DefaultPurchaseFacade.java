package com.magicbaits.core.facades.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.magicbaits.core.facades.PurchaseFacade;
import com.magicbaits.persistence.dao.PurchaseDao;
import com.magicbaits.persistence.dto.converter.PurchaseDtoToPurchaseConverter;
import com.magicbaits.persistence.enteties.Purchase;

@Service
public class DefaultPurchaseFacade implements PurchaseFacade{

	private final PurchaseDtoToPurchaseConverter converter;
	private final PurchaseDao purchaseDao;
	
	public DefaultPurchaseFacade(PurchaseDtoToPurchaseConverter converter, PurchaseDao purchaseDao) {
		this.converter = converter;
		this.purchaseDao = purchaseDao;
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

	@Override
	public boolean updatePurchase(Purchase purchase) {
		return purchaseDao.updatePurchase(converter.convertPurchaseToPurchaseDto(purchase));
	}

	@Override
	public Purchase getPurchaseById(int id) {
		return converter.convertPurchaseDtoToPurchase(purchaseDao.getPurchaseById(id));
	}

	@Override
	public List<Purchase> getPurchasesForPageWithLimit(int page, int paginationLimit) {
		return converter.convertPurchaseDtosToPurchases(purchaseDao.getPurchasePaginationLimit(page, paginationLimit));
	}

	@Override
	public int numberOfPagesForPurchases(int paginationLimit) {
		int totalPurchases = purchaseDao.getPurchaseCount();
		int pages = totalPurchases / paginationLimit;
		if((totalPurchases % paginationLimit) != 0) {
			pages++;
		}
		return pages;
	}

	@Override
	public List<Purchase> getPurchasesForPageWithLimitByUserId(int page, int paginationLimit, int userId) {
		return converter.convertPurchaseDtosToPurchases(purchaseDao.getPurchasePaginationLimitByUserId(page, paginationLimit,userId));
	}

	@Override
	public int numberOfPagesForPurchasesByUserId(int paginationLimit, int userId) {
		int totalPurchases = purchaseDao.getPurchaseCountByUserId(userId);
		int pages = totalPurchases / paginationLimit;
		if((totalPurchases % paginationLimit) != 0) {
			pages++;
		}
		return pages;
	}

	@Override
	public boolean deletePurchase(int id) {
		return purchaseDao.deletePurchaseById(id);
	}
}