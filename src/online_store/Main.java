package online_store;

import java.util.Locale;

import online_store.menu.Menu;
import online_store.menu.impl.MainMenu;

public class Main {

	public static final String EXIT_COMMAND = "exit";

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en_US"));
		Menu mainMenu = new MainMenu();
		mainMenu.start();
	}
	
}