package com.magicbaits.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.magicbaits.core.facades.AddressFacade;
import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.PurchaseFacade;
import com.magicbaits.core.facades.impl.DefaultAddressFacade;
import com.magicbaits.core.facades.impl.DefaultProductFacade;
import com.magicbaits.core.facades.impl.DefaultPurchaseFacade;
import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.persistence.enteties.impl.DefaultAddress;
import com.magicbaits.persistence.enteties.impl.DefaultPurchase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PurchaseController {

	private String LOGGED_IN_USER_ATTR = "loggedInUser";
	private PurchaseFacade purchaseFacade;
	private ProductFacade productFacade;
	private AddressFacade addressFacade;
	
	{
		purchaseFacade = DefaultPurchaseFacade.getInstance();
		productFacade = DefaultProductFacade.getInstance();
		addressFacade = DefaultAddressFacade.getInstance();
	}
	
	@PostMapping("/purchase")
	public String purchase(HttpSession session, @RequestParam String productId, @RequestParam String useSaveAddress, HttpServletRequest request) {
		User user = ((User) session.getAttribute(LOGGED_IN_USER_ATTR));
		int userId = user.getId();
		
		Product product = productFacade.getProductById(Integer.parseInt(productId));
		List<Product> products = new ArrayList<>();
		products.add(product);
		
		Address address = new DefaultAddress();
		if(useSaveAddress!=null && useSaveAddress.equals("true")) {
			address = addressFacade.getAddressByUserId(userId);
		}else {
			address = createAddress(request,user);
		}
		
		Purchase purchase = new DefaultPurchase();
		purchase.setCustomerId(userId);
		purchase.setProducts(products);
		purchase.setAddress(address);
		
		if(purchaseFacade.addPurchase(purchase)) {
			return "redirect:/transaction-approve.html";
		}else {
			return "redirect:/transaction-fail.html";
		}
	}
	
	private Address createAddress(HttpServletRequest request, User user) {
		Address address = new DefaultAddress();
		address.setShippingCompany(request.getParameter("flexRadioDefault"));
		
		if(!address.getShippingCompany().equals("localPickup")) {
			address.setUser(user);
			address.setFirstDirection(request.getParameter("address1"));
			address.setSecondDirection(request.getParameter("address2"));
			address.setCity(request.getParameter("city"));
			address.setHouseNumber(Integer.parseInt(request.getParameter("houseNumber")));
			address.setPostalCode(Integer.parseInt(request.getParameter("postalCode")));
			address.setPhoneNumber(request.getParameter("phone"));
			address.setExtraMessage(request.getParameter("extraMessage"));
		}
		
		if(addressFacade.userAddressExist(user.getId()) && !address.getShippingCompany().equals("localPickup")) {
			address.setId(addressFacade.getAddressByUserId(user.getId()).getId());
			addressFacade.updateAddress(address);
		}else {
			address.setId(addressFacade.saveAddress(address));
		}
		return address;
	}
	
}
