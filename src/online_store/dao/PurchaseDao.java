package online_store.dao;

import java.util.List;

import online_store.dto.PurchaseDto;

public interface PurchaseDao {
	
	void savePurchase(PurchaseDto order);
	
	List<PurchaseDto> getPurchaces();
	
	List<PurchaseDto> getPurchasesByUserId(int id);
}
