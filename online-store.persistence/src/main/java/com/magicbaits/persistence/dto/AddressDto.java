package com.magicbaits.persistence.dto;

public class AddressDto {
	private int id;
	private UserDto user;
	private String firstDirection;
	private String secondDirection;
	private String city;
	private int houseNumber;
	private int postalCode;
	private String phoneNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getFirstDirection() {
		return firstDirection;
	}
	public void setFirstDirection(String firstDirection) {
		this.firstDirection = firstDirection;
	}
	public String getSecondDirection() {
		return secondDirection;
	}
	public void setSecondDirection(String secondDirection) {
		this.secondDirection = secondDirection;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
