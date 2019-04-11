package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class Main implements ActionListener
{
	Window win;
	GameData gd;
	String choice;
	Battle b = new Battle(this.gd);
	public Main() throws InterruptedException
	{
		this.win = new Window();
		this.gd = new GameData(win);
		this.win.clear();
		Thread.sleep(700);
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
		//Checking the 1 to start the game
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
					win.jl.setText(win.jl.getText() + "<br>There you go ! Your party is full.");
					win.jl.setText(win.jl.getText() + "<br>Hold on, your opponant is choosing his team...");
					//Randomly choosing 6 pokémons for the opponent.
					for (int i = 1;i<=6;i++)
					{
						b.p2.setTeam(gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)]);
						win.jl.setText(win.jl.getText() + "<br>Pokémon "+i+" is... "+b.p2.getTeam().get(i-1).getName()+" !");
					}
					win.jl.setText(win.jl.getText() + "<br>Your opponent chose his team, now let's the battle begin !");
					win.whatToChoose = "attack";
					b.p1.currentPkmn = b.p1.getTeam().get(0);
					b.p2.currentPkmn = b.p2.getTeam().get(0);
					win.jl.setText(win.jl.getText() + "<br>You sent "+b.p1.currentPkmn.getName()+ " !");
					win.jl.setText(win.jl.getText() + "<br>Your opponent sent "+b.p2.currentPkmn.getName()+ " !");
					win.jl.setText(win.jl.getText() + b.showAttacks());
				}
				//... Otherwise showing how many pokémons are left to choose
				else
					win.jl.setText(win.jl.getText() + "<br>You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose !");
			}
			//If what the user type is not a pokémon (not a number, something higher than 151...)
			catch (Exception e)
			{
				win.jl.setText(win.jl.getText() + "<br>Woops ! That doesn't look like a valid Pokémon !");
			}
		}
		else if (win.whatToChoose.equals("attack"))
		{
			try
			{
				if (Integer.parseInt(choice) > b.p1.getCurrentPkmn().getAttacks().size())
					throw  new NullPointerException();
				if (b.p1.getCurrentPkmn().getCurrentSpd() >= b.p2.getCurrentPkmn().getCurrentSpd())
				{
					win.jl.setText(win.jl.getText() + b.useAttack(Integer.parseInt(choice)-1,0));
					win.jl.setText(win.jl.getText() + b.useAttack(ThreadLocalRandom.current().nextInt(0,b.p2.getCurrentPkmn().getAttacks().size()),1));
				}
				else
				{
					win.jl.setText(win.jl.getText() + b.useAttack(ThreadLocalRandom.current().nextInt(0,b.p2.getCurrentPkmn().getAttacks().size()),1));
					win.jl.setText(win.jl.getText() + b.useAttack(Integer.parseInt(choice)-1,0));
				}
			}
			catch (Exception e1)
			{
				win.jl.setText(win.jl.getText() + "<br>Woops ! That doesn't look like a valid attack !");
			}
			win.jl.setText(win.jl.getText() + b.showAttacks());
		}
	}
}
