package online_store.services.impl;

import java.util.List;

import online_store.dao.PurchaseDao;
import online_store.dao.impl.MySqlJdbcPurchaseDao;
import online_store.dto.converter.PurchaseDtoToPurchaseConverter;
import online_store.entities.Purchase;
import online_store.services.PurchaseManagementService;

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
