package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener
{
	Window win;
	GameData gd;
	String choice;
	public Main() throws InterruptedException
	{
		this.win = new Window();
		this.gd = new GameData(win);
		Thread.sleep(2000);
		this.win.clear();
		Thread.sleep(500);
		this.win.jtf.addActionListener(this);
		this.win.setJl("<html>Welcome in the Pokémon Textual Game !<br>Type 1 and Enter to start.");
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		@SuppressWarnings("unused")
		Main m = new Main();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		choice = win.jtf.getText();
		win.jtf.setText("");
		if (choice.equals("1"))
		{
			Battle b = new Battle(this.gd);
		}
	}
}
