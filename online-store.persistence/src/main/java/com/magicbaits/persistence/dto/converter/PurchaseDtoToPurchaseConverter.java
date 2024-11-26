package com.magicbaits.persistence.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.magicbaits.persistence.dto.PurchaseDto;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.impl.DefaultPurchase;

@Component
public class PurchaseDtoToPurchaseConverter {
	@Autowired
	private ProductDtoToProductConverter productConverter;
	
	@Autowired
	private UserDtoToUserConverter userConverter;
	
	@Autowired
	private AddressDtoToAddressConverter addressConverter;
	
	public Purchase convertPurchaseDtoToPurchase(PurchaseDto purchaseDto) {
		Purchase purchase = new DefaultPurchase();
		purchase.setId(purchaseDto.getId());
		purchase.setCustomerId(purchaseDto.getUserDto().getId());
		purchase.setProducts(productConverter.convertProductDtosToProducts(purchaseDto.getProductDtos()));
		purchase.setAddress(addressConverter.convertAddressDtoToAddress(purchaseDto.getAddressDto()));
		purchase.setShippingCompany(purchaseDto.getShippingCompany());
		purchase.setStatus(purchaseDto.getStatus());
		if(purchaseDto.getExtraMessage()!=null) {
			purchase.setExtraMessage(purchaseDto.getExtraMessage());
		}
		
		return purchase;
	}
	
	public PurchaseDto convertPurchaseToPurchaseDto(Purchase purchase) {
		PurchaseDto purchaseDto = new PurchaseDto();
		purchaseDto.setId(purchase.getId());
		purchaseDto.setProductDtos(productConverter.convertProductsToProductDtos(purchase.getProducts()));
		purchaseDto.setUserDto(userConverter.convertUserIdToUserDtoWithOnlyId(purchase.getCustomerId()));
		purchaseDto.setAddressDto(addressConverter.convertAddressToAddressDto(purchase.getAddress()));
		purchaseDto.setShippingCompany(purchase.getShippingCompany());
		purchaseDto.setStatus(purchase.getStatus());
		if(purchase.getExtraMessage()!=null) {
			purchaseDto.setExtraMessage(purchase.getExtraMessage());
		}
		
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
