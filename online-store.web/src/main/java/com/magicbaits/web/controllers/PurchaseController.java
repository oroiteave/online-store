package com.magicbaits.web.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.exceptions.InsufficientStockException;
import com.magicbaits.core.facades.AddressFacade;
import com.magicbaits.core.facades.PurchaseFacade;
import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.facades.impl.DefaultAddressFacade;
import com.magicbaits.core.facades.impl.DefaultPurchaseFacade;
import com.magicbaits.core.facades.impl.DefaultUserFacade;
import com.magicbaits.core.services.PurchaseService;
import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.persistence.enteties.impl.DefaultAddress;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class PurchaseController {

	private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	@Autowired
	private PurchaseService purchaseService;
	
	private PurchaseFacade purchaseFacade;
	
	private AddressFacade addressFacade;
	
	private UserFacade userFacade;
	
	private static final int PAGINATION_LIMIT = 6;
	
	{
		purchaseFacade = DefaultPurchaseFacade.getInstance();
		addressFacade = DefaultAddressFacade.getInstance();
		userFacade = DefaultUserFacade.getInstance();
	}
	
	@GetMapping("/purchase")
	public List<Purchase> getPurchases(){
		return purchaseFacade.getPurchases();
	}
	
	@GetMapping("purchase/pages")
	public Map<String, Object> getPurchasesWithPagesLimit(@RequestParam String page) {
		Map<String, Object> response = new HashMap<>();
		response.put("purchases", purchaseFacade.getPurchasesForPageWithLimit(Integer.parseInt(page), PAGINATION_LIMIT));
		response.put("numberOfPages",purchaseFacade.numberOfPagesForPurchases(PAGINATION_LIMIT));
		response.put("userEmails", userFacade.getUserEmailsForPurchasesPageWithLimit(Integer.parseInt(page), PAGINATION_LIMIT));
		return response; 
	}
	@GetMapping("/purchase/user")
	public Map<String,Object> getPurchasesByUserWithPagesLimit(@RequestParam String page, HttpSession session){
		Map<String, Object> response = new HashMap<>();
		int userId = ((User)session.getAttribute(LOGGED_IN_USER_ATTR)).getId();
		response.put("purchases", purchaseFacade.getPurchasesForPageWithLimitByUserId(Integer.parseInt(page),PAGINATION_LIMIT,userId));
		response.put("numberOfPages", purchaseFacade.numberOfPagesForPurchasesByUserId(PAGINATION_LIMIT, userId));
		
		return response;
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
		try {
	        User user = (User) session.getAttribute(LOGGED_IN_USER_ATTR);
	        int userId = user.getId();

	        String shippingCompany = request.getParameter("flexRadioDefault");
	        String extraMessage = request.getParameter("extraMessage");

	        Address address = null;
	        if (!"localPickup".equals(shippingCompany)) {
	            if ("true".equals(useSaveAddress)) {
	                address = addressFacade.getAddressByUserId(userId);
	            } else {
	                address = createAddress(request, user);
	            }
	        }

	        purchaseService.purchaseProduct(userId, Integer.parseInt(productId), address, shippingCompany, extraMessage);

	        response.sendRedirect("/transaction-approve.html");
	    } catch (InsufficientStockException e) {
	        response.sendRedirect("/out-of-stock.html");
	    } catch (Exception e) {
	        e.printStackTrace();
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
	
	@DeleteMapping("/purchase")
	public String deletePurchase(@RequestParam String id) {
		String message = "Error al borrar la compra";
		boolean deleteStatus = purchaseFacade.deletePurchase(Integer.parseInt(id));
		
		if(deleteStatus) {
			message = "Compra eliminada";
		}
		
		return message;
	}
}
