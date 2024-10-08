package com.magicbaits.core.facades;

import com.magicbaits.persistence.enteties.Address;

public interface AddressFacade {
	int saveAddress(Address address);
	Address getAddresByPurchaseId(int purchaseId);
	Address getAddressByUserId(int userId);
	boolean userAddressExist(int userId);
	boolean updateAddress(Address address);
}
