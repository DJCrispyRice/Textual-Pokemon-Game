package com.pkmn;

/*  
 * Main class of the project.
 */

public class Main 
{
	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException 
	{
		Window win = new Window();
		GameData gd = new GameData(win);
		Thread.sleep(2000);
		win.clear();
		Thread.sleep(500);
		win.setJl("<html>Welcome in the Pokémon Textual Game !<br>Type 1 and Enter to start.");
	}
}
