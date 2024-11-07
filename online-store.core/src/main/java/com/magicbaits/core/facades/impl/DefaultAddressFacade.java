package com.magicbaits.core.facades.impl;

import org.springframework.stereotype.Service;

import com.magicbaits.core.facades.AddressFacade;
import com.magicbaits.persistence.dao.AddressDao;
import com.magicbaits.persistence.dto.converter.AddressDtoToAddressConverter;
import com.magicbaits.persistence.enteties.Address;

@Service
public class DefaultAddressFacade implements AddressFacade{
	
	private final AddressDao addressDao;
	private final AddressDtoToAddressConverter addressConverter; 
	
	public DefaultAddressFacade(AddressDao addressDao, AddressDtoToAddressConverter addressConverter) {
		this.addressDao = addressDao;
		this.addressConverter = addressConverter;
	}

	@Override
	public int saveAddress(Address address) {
		return addressDao.saveAddress(addressConverter.convertAddressToAddressDto(address));
	}

	@Override
	public Address getAddresByPurchaseId(int purchaseId) {
		return addressConverter.convertAddressDtoToAddress(addressDao.getAddressByPurchaseId(purchaseId));
	}

	@Override
	public Address getAddressByUserId(int userId) {
		return addressConverter.convertAddressDtoToAddress(addressDao.getAddressByUserId(userId));
	}

	@Override
	public boolean userAddressExist(int userId) {
		return addressDao.existAddressByUserId(userId);
	}

	@Override
	public boolean updateAddress(Address address) {
		return addressDao.updateAddress(addressConverter.convertAddressToAddressDto(address));
	}
}