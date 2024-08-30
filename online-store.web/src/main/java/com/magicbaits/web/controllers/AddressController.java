package com.magicbaits.web.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.facades.AddressFacade;
import com.magicbaits.core.facades.impl.DefaultAddressFacade;
import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.User;

import jakarta.servlet.http.HttpSession;

@RestController
public class AddressController {

	private final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	private AddressFacade addressFacade;
	{
		addressFacade = DefaultAddressFacade.getInstance();
	}
	
	@GetMapping("/address/user")
	public Address getUserAddress(HttpSession session) {
		User user = (session != null) ? ((User) session.getAttribute(LOGGED_IN_USER_ATTR)) : null;
		Address address = (user!=null) ? addressFacade.getAddressByUserId(user.getId()) : null;
		System.out.println(address.toString());
		return address;
	}
	
	@PutMapping("/address")
	public String createOrPutAddress(HttpSession session,@RequestParam String address1, @RequestParam String address2, @RequestParam String city, @RequestParam String phone, @RequestParam String houseNumber, @RequestParam String postalCode) {
		User user = ((User) session.getAttribute(LOGGED_IN_USER_ATTR));
		String message = "Error en la modificacion de la direccion";
		
		Address address = addressFacade.getAddressByUserId(user.getId());
		address.setFirstDirection(address1);
		address.setSecondDirection(address2);
		address.setCity(city);
		address.setPhoneNumber(phone);
		address.setHouseNumber(Integer.parseInt(houseNumber));
		address.setPostalCode(Integer.parseInt(postalCode));
		
		if(addressFacade.updateAddress(address)) {
			message = "Direccion modificada con exito";
		}
		return message;
	}
}
