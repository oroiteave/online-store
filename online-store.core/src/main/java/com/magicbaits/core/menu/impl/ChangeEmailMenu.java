package com.magicbaits.core.menu.impl;

import java.util.ResourceBundle;
import java.util.Scanner;

import com.magicbaits.core.configs.ApplicationContext;
import com.magicbaits.core.menu.Menu;

public class ChangeEmailMenu implements Menu{
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
		String newEmail = sc.next();
		context.getLoggedInUser().setEmail(newEmail);
		System.out.println(rb.getString("email.successfully.change"));
		//sc.close();
		new MainMenu().start();
	}

	@Override
	public void printMenuHeader() {
		System.out.println(rb.getString("enter.new.email"));
		System.out.println(rb.getString("change.email.header"));
	}
}