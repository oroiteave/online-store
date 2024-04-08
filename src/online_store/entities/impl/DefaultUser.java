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

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
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
	
	void clearState() {
		userCount=0;
	}
}
