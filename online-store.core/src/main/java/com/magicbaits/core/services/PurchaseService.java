package com.magicbaits.core.services;

import org.springframework.stereotype.Service;

import com.magicbaits.persistence.enteties.Address;

@Service
public interface PurchaseService {
	void purchaseProduct(int userId, int productId, Address address, String shippingCompany, String extraMessage);
}
