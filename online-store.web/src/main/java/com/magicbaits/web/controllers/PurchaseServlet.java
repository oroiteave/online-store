package com.magicbaits.web.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.PurchaseFacade;
import com.magicbaits.core.facades.impl.DefaultProductFacade;
import com.magicbaits.core.facades.impl.DefaultPurchaseFacade;
import com.magicbaits.persistence.enteties.Product;
import com.magicbaits.persistence.enteties.Purchase;
import com.magicbaits.persistence.enteties.User;
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
	
	{
		purchaseFacade = DefaultPurchaseFacade.getInstance();
		productFacade = DefaultProductFacade.getInstance();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
			      + request.getServerPort() + request.getServletContext().getContextPath();
		
		int userId = ((User) request.getSession().getAttribute("loggedInUser")).getId();
		
		String productId = request.getParameter("productId");
		Product product = productFacade.getProductById(Integer.parseInt(productId));
		List<Product> products = new ArrayList<>();
		products.add(product);
		
		Purchase purchase = new DefaultPurchase();
		purchase.setCustomerId(userId);
		purchase.setProducts(products);
		purchaseFacade.addPurchase(purchase);
		
		response.sendRedirect(baseUrl+"/transaction-approve.html");
	}
}