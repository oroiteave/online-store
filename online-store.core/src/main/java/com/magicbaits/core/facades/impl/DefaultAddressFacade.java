package com.magicbaits.core.facades.impl;

import com.magicbaits.core.facades.AddressFacade;
import com.magicbaits.persistence.dao.AddressDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcAddressDao;
import com.magicbaits.persistence.dto.converter.AddressDtoToAddressConverter;
import com.magicbaits.persistence.enteties.Address;

public class DefaultAddressFacade implements AddressFacade{
	
	private static DefaultAddressFacade instance;
	private AddressDao addressDao = new MySqlJdbcAddressDao();
	private AddressDtoToAddressConverter addressConverter = new AddressDtoToAddressConverter(); 

	public static synchronized DefaultAddressFacade getInstance() {
		if(instance==null) {
			instance = new DefaultAddressFacade();
		}
		return instance;
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
	public Address addressByUserId(int userId) {
		return addressConverter.convertAddressDtoToAddress(addressDao.getAddressByUserId(userId));
	}

	@Override
	public int userAddressExist(int userId) {
		return addressDao.totalAddressByUserId(userId);
	}

	@Override
	public boolean updateAddress(Address address) {
		return addressDao.updateAddress(addressConverter.convertAddressToAddressDto(address));
	}
}