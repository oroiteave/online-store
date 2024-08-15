package com.magicbaits.persistence.dao;

import com.magicbaits.persistence.dto.AddressDto;

public interface AddressDao {
	int saveAddress(AddressDto address);
	AddressDto getAddressByPurchaseId(int purchaseId);
	AddressDto getAddressByUserId(int userId);
	boolean updateAddress(AddressDto address);
	int totalAddressByUserId(int userId);
}
