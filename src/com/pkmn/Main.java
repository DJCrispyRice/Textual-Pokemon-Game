package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener
{
	Window win;
	GameData gd;
	String choice;
	Battle b = new Battle(this.gd, this.win);
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
		if (choice.equals("1") && win.whatToChoose.equals("start"))
		{
			win.jl.setText(win.jl.getText() + "<br>A new battle will begin ! Please choose your team.");
			win.jl.setText(win.jl.getText() + "<br>To do so, type the number of your Pokémon and Enter. You can refer to the Pokédex if needed.");
			win.whatToChoose = "team";
		}
		else if (win.whatToChoose.equals("team"))
		{
			try
			{
				//To avoid MissingNo being a choice
				if (choice.equals("0"))
					throw  new NullPointerException();
				b.p1.setTeam(gd.allPkmn[Integer.parseInt(choice)]);
				win.jl.setText(win.jl.getText() + "<br>You chose "+gd.allPkmn[Integer.parseInt(choice)].getName()+" !");
				//Checking if the party is full
				if (b.p1.getTeam().size() == 6)
				{
					win.jl.setText(win.jl.getText() + "<br>There you go ! Your party is full !");
					win.whatToChoose = "attack";
				}
				//... Otherwise showing how many pokémons are left to choose
				else
					win.jl.setText(win.jl.getText() + "<br>You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose !");
			}
			catch (Exception e)
			{
				win.jl.setText(win.jl.getText() + "<br>Woops ! That doesn't look like a valid Pokémon !");
			}
		}
	}
}
