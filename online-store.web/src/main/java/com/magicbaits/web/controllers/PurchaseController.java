package com.magicbaits.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
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
	
	@GetMapping("/purchase")
	public List<Purchase> getPurchases(){
		return purchaseFacade.getPurchases();
	}
	
	@PutMapping("/purchase")
	public String updateStatus(@RequestParam String purchaseId, @RequestParam String newStatus) {
		String message="error al cambiar el status";
		
		Purchase purchase = purchaseFacade.getPurchaseById(Integer.parseInt(purchaseId));
		purchase.setStatus(newStatus);
		
		if(purchaseFacade.updatePurchase(purchase)) {
			message = "cambio del status exitoso";
		}
		
		return message;
	}
	
	@PostMapping("/purchase")
	public void purchase(HttpSession session, @RequestParam String productId, @RequestParam String useSaveAddress, 
			HttpServletRequest request,HttpServletResponse response) throws IOException{
		User user = ((User) session.getAttribute(LOGGED_IN_USER_ATTR));
		int userId = user.getId();
		
		Product product = productFacade.getProductById(Integer.parseInt(productId));
		List<Product> products = new ArrayList<>();
		products.add(product);
		
		Purchase purchase = new DefaultPurchase();
		purchase.setCustomerId(userId);
		purchase.setProducts(products);
		purchase.setShippingCompany(request.getParameter("flexRadioDefault"));
		purchase.setExtraMessage(request.getParameter("extraMessage"));
		purchase.setStatus("CONFIRMADO");
		
		if(!purchase.getShippingCompany().equals("localPickup")) {
			Address address = new DefaultAddress();
			if(useSaveAddress!=null && useSaveAddress.equals("true")) {
				address = addressFacade.getAddressByUserId(userId);
			}else {
				address = createAddress(request,user);
			}
			purchase.setAddress(address);
		}
		
		if(purchaseFacade.addPurchase(purchase)) {
			response.sendRedirect("/transaction-approve.html");
		}else {
			response.sendRedirect("/transaction-fail.html");
		}
	}
	
	private Address createAddress(HttpServletRequest request, User user) {
		Address address = new DefaultAddress();
		
		address.setUser(user);
		address.setFirstDirection(request.getParameter("address1"));
		address.setSecondDirection(request.getParameter("address2"));
		address.setCity(request.getParameter("city"));
		address.setHouseNumber(Integer.parseInt(request.getParameter("houseNumber")));
		address.setPostalCode(Integer.parseInt(request.getParameter("postalCode")));
		address.setPhoneNumber(request.getParameter("phone"));
		
		if(addressFacade.userAddressExist(user.getId())) {
			address.setId(addressFacade.getAddressByUserId(user.getId()).getId());
			addressFacade.updateAddress(address);
		}else {
			address.setId(addressFacade.saveAddress(address));
		}
		return address;
	}
	
}
