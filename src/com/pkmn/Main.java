/*
 * TO DO :
 * - Finish special attacks > OK
 * - Enable switching in battle > OK
 * - Better pokémon selection > OK
 * - Better code organisation > OK
 * - Add IA > OK
 * - Sound (cries, music) > OK
 * - Graphics (not sure if it suits this project yet)
 */

package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.channels.AlreadyBoundException;

public class Main implements ActionListener, KeyListener
{
	Window win;
	GameData gd;
	String choice;
	Battle b = new Battle(this.gd);
	public Main() throws InterruptedException, IOException
	{
		win = new Window();
		gd = new GameData(win);
		win.clear();
		win.jtf.addActionListener(this);
		win.jtf.addKeyListener(this);
		win.jtf.setFocusTraversalKeysEnabled(false);
		win.logTrace("Welcome in the Pokémon Textual Battle Game !\nPress Enter to start.");
	}
	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		@SuppressWarnings("unused")
		Main m = new Main();
	}
	
	//Autocompletion using TAB key
	public void keyPressed(KeyEvent event) 
	{
		choice = win.jtf.getText().toUpperCase();
		if (event.getKeyChar() == KeyEvent.VK_TAB && win.whatToChoose.equals("team") && !choice.equals("")) 
		{
			for (int i = 1; i < 152 ; i++)
			{
				if (this.gd.allPkmn[i].getName().startsWith(choice))
				{
					win.jtf.setText(this.gd.allPkmn[i].getName());
					break;
				}
			}
		}
	}

	//When Enter is pushed
	public void actionPerformed(ActionEvent arg0) 
	{
		choice = win.jtf.getText().toUpperCase();
		win.jtf.setText("");
		//Choosing the difficulty
		if (win.whatToChoose.equals("level"))
		{
			try
			{
				//Shows the "HELP" menu
				if (choice.equals("HELP"))
				{
					win.clear();
					win.logTrace("Easy difficulty means the IA will randomly choose its attack.");
					win.logTrace("Medium difficuly means the IA will work as \"good IA\" in original pokémon games, meaning it will choose by type.");
					win.logTrace("Hard difficulty means the IA will try to beat you using status moves, healing, pokémon's stats...");
				}
				if (choice.equals("EASY") || Integer.parseInt(choice) == 0)
				{
					b.p2.setLevel(0);
					win.logTrace("Difficulty set to EASY.");
				}
				else if (choice.equals("MEDIUM") || Integer.parseInt(choice) == 1)
				{
					b.p2.setLevel(1);
					win.logTrace("Difficulty set to MEDIUM.");
				}
				else if (choice.equals("HARD") || Integer.parseInt(choice) == 2)
				{
					b.p2.setLevel(2);
					win.logTrace("Difficulty set to HARD.");
				}
				else
					throw new NegativeArraySizeException();
				if (b.p2.getLevel() != 3)
				{
					win.logTrace("A new battle will begin ! Please choose your team.");
					win.logTrace("To do so, type the number of your Pokémon or its name and press Enter.\nYou can refer to the Pokédex if needed by typing \"POKEDEX\".");
					win.logTrace("You can auto-complete your input by pressing TAB after the beginning of a Pokémon's name.");
					win.whatToChoose = "team";
				}
				else
				{
					win.logTrace("Please choose your difficulty.");
					win.logTrace("0. Easy");
					win.logTrace("1. Medium");
					win.logTrace("2. Hard");
					win.logTrace("Type the number next to the difficulty you want (or the difficulty directly)");
					win.logTrace("For help, type HELP.");
				}
			}
			//If the user typed something wrongs, re-print the difficulty choosing screen
			catch (Exception eee)
			{
				win.logTrace("Please choose your difficulty.");
				win.logTrace("0. Easy");
				win.logTrace("1. Medium");
				win.logTrace("2. Hard");
				win.logTrace("Type the number next to the difficulty you want (or the difficulty directly)");
				win.logTrace("For help, type HELP.");
			}
		}
		//Part that is used to select your team
		else if (win.whatToChoose.equals("team"))
		{
			try
			{
				b.p1.chooseByNumber(choice, gd, win);
			}
			//Case the user didn't type a number
			catch (NumberFormatException nfe)
			{
				b.p1.chooseByName(choice, gd, win);
			}
			//If what the user type is not a pokémon (not a number, something higher than 151...)
			catch (NullPointerException e)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon !");
			} 
			catch (CloneNotSupportedException e) 
			{}
			if (b.p1.getTeam().size() == 6)
			{
				win.logTrace("There you go ! Your party is full.");
				win.music.stop();
				win.logTrace("Hold on, your opponant is choosing his team...");
				try 
				{
					Thread.sleep(500);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				//Randomly choosing 6 pokémons for the opponent.
				b.p2.createTeam(gd, win);
				//Now that the teams are created, the game switchs to "battle mode".
				win.whatToChoose = "attack";
				b.p1.currentPkmn = b.p1.getTeam().get(0);
				b.p2.currentPkmn = b.p2.getTeam().get(0);
				win.drawPlayerHp(b.getpPkmn(b.p1));
				win.drawIaHp(b.getpPkmn(b.p2));
				win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+ " !");
				win.drawPlayerSprite("src/res/sprites/back/"+b.getpPkmn(b.p1).getId()+".png");
				win.se = new Sound(this.getClass().getResource("/res/cries/"+b.getpPkmn(b.p1).getId()+".wav"));
				win.se.play();
				try 
				{
					Thread.sleep(500);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				win.logTrace("Your opponent sent "+b.getpPkmn(b.p2).getName()+ " !");
				win.drawIaSprite(b.getpPkmn(b.p2).getId());
				win.logTrace(b.getpPkmn(b.p1).showAttacks());
				win.music = new Sound(this.getClass().getResource("/res/audio/battle.wav"));
				win.music.playLoop();
			}
			else
				win.logTrace("You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose.");
		}
		//Selecting the attack
		else if (win.whatToChoose.equals("attack"))
		{
			//Try/catch to check if the number is a valid choice
			try
			{
				//Check if the user asks for a switch
				if (choice.equals("0") && b.getpPkmn(b.p1).getTwoturnstatus() == 0)
				{
					win.whatToChoose = "switch";
					b.p1.showTeam(win);
				}
				else
				{
					//If choice is empty, checks if we are in a twoturn or bind situation.
					if (choice.equals("") && b.getpPkmn(b.p1).getTwoturnstatus() == 0)
						throw new NullPointerException();
					//If not, checks the choice to see if it's valid
					else if (!choice.equals("") && Integer.parseInt(choice) > b.getpPkmn(b.p1).getAttacks().size())
						throw new NullPointerException();
					
					//Choosing the attack of the opponent
					int rdatt = b.p2.chooseAttack(b.p1);
					//To avoid a disabled attack being a choice
					
					if (!choice.equals("") && Integer.parseInt(choice) - 1 == b.getpPkmn(b.p1).checkDisabledAttack())
						throw new AlreadyBoundException();
					
					//Checking prioritary using speed and moves used
					b.checkPriority(b.p1, b.p2, rdatt, choice);
					
					//Sending strings instead of integers because of the twoturnstatus check in aTurn function.
					if (b.getpPkmn(b.p1).getPrio())
						b.aTurn(b.p1,b.p2,win,choice,Integer.toString(rdatt+1),gd);
					else
						b.aTurn(b.p2,b.p1,win,Integer.toString(rdatt+1),choice,gd);
					
					//Checks after attacks were used
					b.endOfTurn(win);
				}
			}
			catch (NumberFormatException e1)
			{
				win.logTrace("Woops ! That doesn't look like a valid attack !");
				choice = null;
			}
			catch (AlreadyBoundException e3)
			{
				win.logTrace("This move is disabled. Please choose another one.");
			}
		}
		else if (win.whatToChoose.equals("swap") || win.whatToChoose.equals("switch"))
		{
			try
			{
				//Throws a NPE if the number is not a valid choice
				if (Integer.parseInt(choice) > b.p1.getTeam().size())
					throw  new NullPointerException();
				else if (Integer.parseInt(choice) == 0)
				{
					if (b.getpPkmn(b.p1).getStatus() != 9)
					{
						win.logTrace(b.getpPkmn(b.p1).showAttacks());
						win.whatToChoose = "attack";
					}
					else
						throw  new NullPointerException();
				}
				else
				{
					if (b.p1.getCurrentPkmn().equals(b.p1.getTeam().get(Integer.parseInt(choice)-1)))
						throw new AlreadyBoundException();
					b.p1.setCurrentPkmn(b.p1.getTeam().get(Integer.parseInt(choice)-1));
					b.getpPkmn(b.p1).setCurrentStats(false);
					win.music.stop();
					win.se = new Sound(this.getClass().getResource("/res/cries/"+b.getpPkmn(b.p1).getId()+".wav"));
					win.se.play();
					try 
					{
						Thread.sleep(500);
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+" !");
					win.drawPlayerSprite("src/res/sprites/back/"+b.getpPkmn(b.p1).getId()+".png");
					win.drawPlayerHp(b.getpPkmn(b.p1));
					win.music.playLoop();
					//If it's a switch (requested swap), the enemy will attack like normal
					if (win.whatToChoose.equals("switch"))
					{
						if (b.getpPkmn(b.p2).getTwoturnstatus() != 0)
							win.logTrace(b.useAttack(gd.allAtks[b.getpPkmn(b.p2).getTwoturnstatus()],b.p2,b.p1,1));
						else
						{
							//Choosing the attack of the opponent
							int rdatt = b.p2.chooseAttack(b.p1);
							win.logTrace(b.useAttack(b.getpPkmn(b.p2).getAttacks().get(rdatt),b.p2,b.p1,1));
							//Checks if someone fainted
							b.p2.checkDead(win);
							b.p1.checkDead(win);
							if (b.checkSeed(b.p1,b.p2,win))
								b.p1.checkDead(win);
							if (b.checkSeed(b.p2,b.p1,win))
								b.p2.checkDead(win);
							b.reinitPrio();
						}
					}
					win.whatToChoose = "attack";
					choice = null;
					win.logTrace(b.getpPkmn(b.p1).showAttacks());
				}
			}
			catch (NullPointerException e10)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon in your team !");
				b.p1.showTeam(win);
			}
			catch (NumberFormatException e10)
			{
				win.logTrace("Woops ! Please type a number to choose your Pokémon !");
				b.p1.showTeam(win);
			}
			catch (AlreadyBoundException e12)
			{
				win.logTrace("Woops ! This Pokémon is already battling !");
				b.p1.showTeam(win);
			}
		}
		else if (win.whatToChoose.equals("continue"))
		{
			//Throws a NPE if the number is not a valid choice
			try
			{
				if (!choice.equals("1") && !choice.equals("2"))
					throw  new NullPointerException();
				if (choice.equals("1"))
				{
					win.logTrace("Okay, let's play again. Good luck !");
					win.music.stop();
					Thread.sleep(1000);
					win.clear();
					win.logTrace("Please choose your difficulty.");
					win.logTrace("0. Easy");
					win.logTrace("1. Medium");
					win.logTrace("2. Hard");
					win.logTrace("Type the number next to the difficulty you want (or the difficulty directly)");
					win.logTrace("For help, type HELP.");
					win.whatToChoose = "level";
					b = new Battle(gd);
					this.choice = null;
					win.music = new Sound(this.getClass().getResource("/res/audio/menu.wav"));
					win.music.playLoop();
				}
				else
				{
					win.logTrace("Okay then. See you later.");
					Thread.sleep(1000);
					System.exit(0);
				}
			}
			catch (Exception e54)
			{
				win.logTrace("Woops ! That choice is not valid.");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
