package com.magicbaits.persistence.enteties;

public interface Address {
	int getId();
	User getUser();
	String getShippingCompany();
	String getFirstDirection();
	String getSecondDirection();
	String getCity();
	int getHouseNumber();
	int getPostalCode();
	String getExtraMessage();
	String getPhoneNumber();
	
	void setId(int id);
	void setUser(User user);
	void setShippingCompany(String shippingCompany);
	void setFirstDirection(String firstDirection);
	void setSecondDirection(String secondDirection);
	void setCity(String city);
	void setHouseNumber(int houseNumber);
	void setPostalCode(int postalCode);
	void setExtraMessage(String extraMessage);
	void setPhoneNumber(String phoneNumber);
}
