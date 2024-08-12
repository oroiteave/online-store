package com.magicbaits.persistence.dto.converter;

import com.magicbaits.persistence.dto.AddressDto;
import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.impl.DefaultAddress;

public class AddressDtoToAddressConverter {
	private UserDtoToUserConverter userConverter;
	{
		userConverter = new UserDtoToUserConverter();
	}
	
	Address convertAddressDtoToAddress(AddressDto addressDto){
		if(addressDto == null) {
			return null;
		}
		
		Address address = new DefaultAddress();
		
		address.setId(addressDto.getId());
		address.setShippingCompany(addressDto.getShippingCompany());
		address.setHouseNumber(addressDto.getHouseNumber());
		address.setPostalCode(addressDto.getPostalCode());
		
		if(addressDto.getUser()!= null) {
			address.setUser(userConverter.convertUserDtoToUser(addressDto.getUser()));
		}
		if(addressDto.getCity()!=null) {
			address.setCity(addressDto.getCity());
		}
		if(addressDto.getExtraMessage()!=null) {
			address.setExtraMessage(addressDto.getExtraMessage());
		}
		if(addressDto.getFirstDirection()!=null) {
			address.setFirstDirection(addressDto.getFirstDirection());
		}
		if(addressDto.getSecondDirection()!=null) {
			address.setSecondDirection(addressDto.getSecondDirection());
		}
		if(addressDto.getPhoneNumber()!=null) {
			address.setPhoneNumber(addressDto.getPhoneNumber());
		}
		
		return address;
	}
	
	AddressDto convertAddressToAddressDto(Address address) {
		if(address==null) {
			return null;
		}
		
		AddressDto addressDto = new AddressDto();
		
		addressDto.setId(address.getId());
		addressDto.setShippingCompany(address.getShippingCompany());
		addressDto.setHouseNumber(address.getHouseNumber());
		addressDto.setPostalCode(address.getPostalCode());
		
		if(address.getUser()!= null) {
			addressDto.setUser(userConverter.convertUserToUserDto(address.getUser()));
		}
		if(address.getCity()!=null) {
			addressDto.setCity(address.getCity());
		}
		if(address.getExtraMessage()!=null) {
			addressDto.setExtraMessage(address.getExtraMessage());
		}
		if(address.getFirstDirection()!=null) {
			addressDto.setFirstDirection(address.getFirstDirection());
		}
		if(address.getSecondDirection()!=null) {
			addressDto.setSecondDirection(address.getSecondDirection());
		}
		if(address.getPhoneNumber()!=null) {
			addressDto.setPhoneNumber(address.getPhoneNumber());
		}
		
		return addressDto;
	}
}
