package online_store.services.impl;


import online_store.entities.Purchase;
import online_store.services.PurchaseManagementService;

import java.util.ArrayList;
import java.util.List;

public class DefaultPurchaseManagementService implements PurchaseManagementService{
	private static final int DEFAULT_PURCHASE_CAPACITY = 10;

	private static DefaultPurchaseManagementService instance;

	private List<Purchase> purchases;
	private int lastIndexOrder;
	
	{
		purchases = new ArrayList<Purchase>(DEFAULT_PURCHASE_CAPACITY);
	}
	
	public static PurchaseManagementService getInstance() {
		if (instance == null) {
			instance = new DefaultPurchaseManagementService();
		}
		return instance;
	}

	@Override
	public void addPurchase(Purchase purchase) {
		if(purchase == null) {
			return;
		}
		if(lastIndexOrder>= purchases.size()) {
			((ArrayList<Purchase>)purchases).ensureCapacity(purchases.size()<<1);
		}
		purchases.add(lastIndexOrder++, purchase);
	}

	@Override
	public List<Purchase> getPurchasesByUserId(int userId) {
		int indexUserPurchases=0;
		for(Purchase o: purchases) {
			if(o != null && o.getCustomerId() == userId) {
				indexUserPurchases++;
			}
		}
		
		List<Purchase> purchasesUser = new ArrayList<Purchase>(indexUserPurchases);
		
		int i=0;
		for(Purchase o: purchases) {
			if(o!=null && o.getCustomerId()==userId) {
				purchasesUser.add(i++, o);
			}
		}
		return purchasesUser;
	}

	@Override
	public List<Purchase> getPurchases() {
		int notNull=0;
		for(Purchase o: purchases) {
			if(o!=null) {
				notNull++;
			}
		}
		int i=0;
		List<Purchase> purchasesNotNull = new ArrayList<Purchase>(notNull);
		for(Purchase o: purchases) {
			if(o!= null) {
				purchasesNotNull.add(i++, o);
			}
		}
		return purchasesNotNull;
	}
	
	void clearServiceState() {
		purchases = new ArrayList<Purchase>(DEFAULT_PURCHASE_CAPACITY);
		lastIndexOrder =0;
	}
}
