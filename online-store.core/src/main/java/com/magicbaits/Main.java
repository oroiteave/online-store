package com.magicbaits;

import java.util.Locale;

import com.magicbaits.core.menu.Menu;
import com.magicbaits.core.menu.impl.MainMenu;

public class Main {

	public static final String EXIT_COMMAND = "exit";

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en_US"));
		Menu mainMenu = new MainMenu();
		mainMenu.start();
	}
	
}