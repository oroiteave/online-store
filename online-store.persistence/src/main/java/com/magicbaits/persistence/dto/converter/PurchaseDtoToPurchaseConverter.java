package com.magicbaits.persistence.dto.converter;

import java.util.ArrayList;
import java.util.List;

import com.magicbaits.persistence.dto.PurchaseDto;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.impl.DefaultPurchase;

public class PurchaseDtoToPurchaseConverter {
	private ProductDtoToProductConverter productConverter;
	private UserDtoToUserConverter userConverter;
	private AddressDtoToAddressConverter addressConverter;
	
	{
		productConverter = new ProductDtoToProductConverter();
		userConverter = new UserDtoToUserConverter();
		addressConverter = new AddressDtoToAddressConverter();
	}
	
	public Purchase convertPurchaseDtoToPurchase(PurchaseDto purchaseDto) {
		Purchase purchase = new DefaultPurchase();
		purchase.setCustomerId(purchaseDto.getUserDto().getId());
		purchase.setProducts(productConverter.convertProductDtosToProducts(purchaseDto.getProductDtos()));
		purchase.setAddress(addressConverter.convertAddressDtoToAddress(purchaseDto.getAddressDto()));
		
		return purchase;
	}
	
	public PurchaseDto convertPurchaseToPurchaseDto(Purchase purchase) {
		PurchaseDto purchaseDto = new PurchaseDto();
		purchaseDto.setProductDtos(productConverter.convertProductsToProductDtos(purchase.getProducts()));
		purchaseDto.setUserDto(userConverter.convertUserIdToUserDtoWithOnlyId(purchase.getCustomerId()));
		purchaseDto.setAddressDto(addressConverter.convertAddressToAddressDto(purchase.getAddress()));
		
		return purchaseDto;
	}

	public List<Purchase> convertPurchaseDtosToPurchases(List<PurchaseDto> purchasesDtos) {
		List<Purchase> purchases = new ArrayList<>();
		if (purchasesDtos != null) {
			for (PurchaseDto purchaseDto : purchasesDtos) {
				purchases.add(convertPurchaseDtoToPurchase(purchaseDto));
			}
		}
		return purchases;
	}
}
