package com.magicbaits.persistence.enteties.impl;

import com.magicbaits.persistence.enteties.User;

public class DefaultUser implements User{
	
	private static int userCount=0;
	
	private String firstName;
	private String lastName;
	private String email;
	private int id;
	private String password;
	private double money;
	private String roleName;
	private String partnerCode;
	private User referrerUser;
	
	
	{
		id = ++userCount;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return "First Name: " + this.getFirstName() + "\t\t" +
				"Last Name: " + this.getLastName() + "\t\t" +
				"Email: " + this.getEmail();
	}

	@Override
	public void setPassword(String password) {
		if(password==null) {
			return;
		}
		this.password = password;
	}

	@Override
	public void setEmail(String newEmail) {
		if(newEmail == null) {
			return;
		}
		this.email = newEmail;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public double getMoney() {
		return money;
	}
	
	@Override
	public void setMoney(double newMoney) {
		this.money = newMoney;
	}
	
	@Override
	public String getRoleName() {
		return roleName;
	}

	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	void clearState() {
		userCount=0;
	}

	@Override
	public String getPartnerCode() {
		return this.partnerCode;
	}

	@Override
	public User getReferrerUser() {
		return this.referrerUser;
	}

	@Override
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
		
	}

	@Override
	public void setReferrerUser(User convertUserDtoToUser) {
		this.referrerUser = convertUserDtoToUser;
	}

}
