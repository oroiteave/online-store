package com.magicbaits.core.menu.impl;



import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.core.configs.ApplicationContext;
import com.magicbaits.core.menu.Menu;
public class ChangePasswordMenu implements Menu{
	private ResourceBundle rb;
	private ApplicationContext context;

	{
		rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);
		context = ApplicationContext.getInstance();
	}

	@Override
	public void start() {
		printMenuHeader();
		Scanner sc = new Scanner(System.in);
		String newPassword = sc.next();
		context.getLoggedInUser().setPassword(newPassword);
		System.out.println(rb.getString("password.successfully.changed.msg"));
		
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("change.password.header"));
		System.out.println(rb.getString("enter.new.password"));
	}
}
