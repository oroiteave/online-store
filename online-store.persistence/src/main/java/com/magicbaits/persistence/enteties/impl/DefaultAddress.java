package com.magicbaits.persistence.enteties.impl;

import com.magicbaits.persistence.enteties.Address;
import com.magicbaits.persistence.enteties.User;

public class DefaultAddress implements Address{

	private int id;
	private User user;
	private String shippingCompany;
	private String firstDirection;
	private String secondDirection;
	private String city;
	private int houseNumber;
	private int postalCode;
	private String extraMessage;
	private String phoneNumber;
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public User getUser() {
		return user;
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String getShippingCompany() {
		return shippingCompany;
	}
	
	@Override
	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}
	
	@Override
	public String getFirstDirection() {
		return firstDirection;
	}
	
	@Override
	public void setFirstDirection(String firstDirection) {
		this.firstDirection = firstDirection;
	}
	
	@Override
	public String getSecondDirection() {
		return secondDirection;
	}
	
	@Override
	public void setSecondDirection(String secondDirection) {
		this.secondDirection = secondDirection;
	}
	
	@Override
	public String getCity() {
		return city;
	}
	
	@Override
	public void setCity(String city) {
		this.city = city;
	}
	
	@Override
	public int getHouseNumber() {
		return houseNumber;
	}
	
	@Override
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	@Override
	public int getPostalCode() {
		return postalCode;
	}
	
	@Override
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	
	@Override
	public String getExtraMessage() {
		return extraMessage;
	}
	
	@Override
	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}
	
	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
