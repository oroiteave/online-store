package com.magicbaits.persistence.enteties;

public interface User {
	String getFirstName();
	String getLastName();
	String getPassword();
	String getEmail();
	int getId();
	double getMoney();
	String getCreditCard();
	String getRoleName();
	String getPartnerCode();
	User getReferrerUser();
	
	void setFirstName(String newFirstName);
	void setLastName(String newLastName);
	void setPassword(String newPassword);
	void setEmail(String newEmail);
	void setMoney(double newMoney);
	void setCreditCard(String newCreditCard);
	void setRoleName(String newRoleName);
	void setId(int newId);
	void setPartnerCode(String partnerCode);
	void setReferrerUser(User convertUserDtoToUser);
}
