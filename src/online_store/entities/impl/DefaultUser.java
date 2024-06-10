package online_store.entities.impl;

import online_store.annotations.Validate;
import online_store.entities.User;

public class DefaultUser implements User{
	
	private static int userCount=0;
	
	private int id;
	
	@Validate(pattern = "[a-zA-Z]+")
	private String firstName;
	
	@Validate(pattern = "[a-zA-Z]+")
	private String lastName;
	
	private String password;
	
	@Validate(pattern = ".+@.+")
	private String email;
	
	private double money;
	
	private String creditCard;
	
	private String roleName;
	
	{
		id = ++userCount;
	}
	
	public DefaultUser() {
	}
	
	public DefaultUser(String firstName, String lastName, String password, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}
	
	public DefaultUser(String firstName, String lastName, String password, String email, String creditCard) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.setCreditCard(creditCard);
	}
	
	public DefaultUser(int id, String firstName, String lastName, String password, String email) {
		this.id = id;
		userCount--;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
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
	public String getCreditCard() {
		return creditCard;
	}

	@Override
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
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

}
