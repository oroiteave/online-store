package com.magicbaits.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addPurchase")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PurchaseFacade purchaseFacade;
	private ProductFacade productFacade;
	private AddressFacade addressFacade;
	
	{
		purchaseFacade = DefaultPurchaseFacade.getInstance();
		productFacade = DefaultProductFacade.getInstance();
		addressFacade = DefaultAddressFacade.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
			      + request.getServerPort() + request.getServletContext().getContextPath();
		
		User user = ((User) request.getSession().getAttribute("loggedInUser"));
		int userId = user.getId();
		
		String productId = request.getParameter("productId");
		Product product = productFacade.getProductById(Integer.parseInt(productId));
		List<Product> products = new ArrayList<>();
		products.add(product);
		
		Address address = new DefaultAddress();
		if(!addressFacade.userAddressExist(userId)) {
			address = createAddress(request, user);
		}else {
			address = addressFacade.getAddressByUserId(userId);
		}
		
		Purchase purchase = new DefaultPurchase();
		purchase.setCustomerId(userId);
		purchase.setProducts(products);
		purchase.setAddress(address);
		
		if(purchaseFacade.addPurchase(purchase)) {
			response.sendRedirect(baseUrl+"/transaction-approve.html");
		}else {
			response.sendRedirect(baseUrl+"/transaction-fail.html");
		}
	}
	
	private Address createAddress(HttpServletRequest request, User user) {
		Address address = new DefaultAddress();
		
		address.setUser(user);
		address.setShippingCompany(request.getParameter("flexRadioDefault"));
		
		if(!address.getShippingCompany().equals("localPickup")) {
			address.setFirstDirection(request.getParameter("address1"));
			address.setSecondDirection(request.getParameter("address2"));
			address.setCity(request.getParameter("city"));
			address.setHouseNumber(Integer.parseInt(request.getParameter("houseNumber")));
			address.setPostalCode(Integer.parseInt(request.getParameter("postalCode")));
			address.setPhoneNumber(request.getParameter("phone"));
			address.setExtraMessage(request.getParameter("extraMessage"));
		}
		
		address.setId(addressFacade.saveAddress(address));
		
		return address;
	}
}