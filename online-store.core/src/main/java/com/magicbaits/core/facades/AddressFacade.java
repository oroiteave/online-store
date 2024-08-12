package com.magicbaits.core.facades;

import com.magicbaits.persistence.enteties.Address;

public interface AddressFacade {
	void saveAddress(Address address);
	Address getAddresByPurchaseId(int purchaseId);
	Address addressByUserId(int userId);
	int userAddressExist(int userId);
	boolean updateAddress(Address address);
}
