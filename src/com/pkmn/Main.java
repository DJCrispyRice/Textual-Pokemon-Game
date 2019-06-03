/*
 * TO DO :
 * - Finish special attacks > OK
 * - Enable switching in battle > OK
 * - Better pokémon selection > OK
 * - Sound (cries, music)
 * - Graphics (not sure if it suits this project yet)
 */

package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.channels.AlreadyBoundException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main implements ActionListener, KeyListener
{
	Window win;
	GameData gd;
	String choice;
	Battle b = new Battle(this.gd);
	public Main() throws InterruptedException
	{
		win = new Window();
		gd = new GameData(win);
		win.clear();
		win.jtf.addActionListener(this);
		win.jtf.addKeyListener(this);
		win.jtf.setFocusTraversalKeysEnabled(false);
		win.logTrace("Welcome in the Pokémon Textual Battle Game !\nPress Enter to start.");
	}
	
	public static void main(String[] args) throws InterruptedException
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

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		choice = win.jtf.getText().toUpperCase();
		win.jtf.setText("");
		//Checking the 1 to start the game
		if (win.whatToChoose.equals("start"))
		{
			win.logTrace("A new battle will begin ! Please choose your team.");
			win.logTrace("To do so, type the number of your Pokémon or its name and press Enter.\nYou can refer to the Pokédex if needed by typing \"POKEDEX\".");
			win.whatToChoose = "team";
		}
		//Part that is used to select your team
		else if (win.whatToChoose.equals("team"))
		{
			try
			{
				//To avoid MissingNo being a choice
				if (choice.equals("0") || Integer.parseInt(choice) == 0)
					throw  new NullPointerException();
				else
				{
					b.p1.setTeam(gd.allPkmn[Integer.parseInt(choice)].clone());
					win.logTrace("You chose "+gd.allPkmn[Integer.parseInt(choice)].getName()+" !");
					//Checking if the party is full
					if (b.p1.getTeam().size() == 6)
					{
						win.logTrace("There you go ! Your party is full.");
						win.logTrace("Hold on, your opponant is choosing his team...");
						//Randomly choosing 6 pokémons for the opponent.
						for (int i = 1 ;i<=6;i++)
						{
							b.p2.setTeam((Pokemon) gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)].clone());
							win.logTrace("Pokémon "+i+" is... "+b.p2.getTeam().get(i-1).getName()+" !");
						}
						win.logTrace("Your opponent chose his team, now let's the battle begin !");
						win.whatToChoose = "attack";
						b.p1.currentPkmn = b.p1.getTeam().get(0);
						b.p2.currentPkmn = b.p2.getTeam().get(0);
						win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+ " !\n***********************");
						win.logTrace("Your opponent sent "+b.getpPkmn(b.p2).getName()+ " !");
						win.logTrace(b.showAttacks());
					}
					//... Otherwise showing how many pokémons are left to choose
					else
						win.logTrace("You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose.");
				}
			}
			//Case the user didn't type a number
			catch (NumberFormatException nfe)
			{
				boolean ok = false;
				if (choice.equals("POKEDEX"))
					gd.showPokedex(win);
				else
				{
					for (int i = 1; i < 152; i++)
					{
						if (this.gd.allPkmn[i].getName().equals(choice))
						{
							try 
							{
								b.p1.setTeam(gd.allPkmn[i].clone());
								win.logTrace("You chose "+gd.allPkmn[i].getName()+" !");
								ok = true;
								break;
							} 
							catch (CloneNotSupportedException e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					if (!ok)
						win.logTrace("Woops ! That doesn't look like a valid Pokémon !");
					else if (b.p1.getTeam().size() == 6)
					{
						win.logTrace("There you go ! Your party is full.");
						win.logTrace("Hold on, your opponant is choosing his team...");
						//b.p2.setTeam((Pokemon) gd.allPkmn[1].clone());
						//Randomly choosing 6 pokémons for the opponent.
						for (int j = 1 ; j <= 6; j++)
						{
							try 
							{
								b.p2.setTeam((Pokemon) gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)].clone());
							} catch (CloneNotSupportedException e) 
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							win.logTrace("Pokémon "+j+" is... "+b.p2.getTeam().get(j-1).getName()+" !");
						}
						win.logTrace("Your opponent chose his team, now let's the battle begin !");
						win.whatToChoose = "attack";
						b.p1.currentPkmn = b.p1.getTeam().get(0);
						b.p2.currentPkmn = b.p2.getTeam().get(0);
						win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+ " !\n***********************");
						win.logTrace("Your opponent sent "+b.getpPkmn(b.p2).getName()+ " !");
						win.logTrace(b.showAttacks());
					}
					//... Otherwise showing how many pokémons are left to choose
					else
						win.logTrace("You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose.");
				}
			}
			//If what the user type is not a pokémon (not a number, something higher than 151...)
			catch (NullPointerException e)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon !");
			} 
			catch (CloneNotSupportedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					win.logTrace("Please choose another Pokémon from your team.");
					win.logTrace("0  - Cancel");
					for (int i = 0; i < b.p1.getTeam().size(); i++)
					{
						win.logTrace(Integer.toString(i+1)+" - "+b.p1.getTeam().get(i).getName());
					}
					win.logTrace("*********************** ");
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
					
					int rdatt;
					if (b.getpPkmn(b.p2).getAttacks().size() == 1)
						rdatt = 0;
					else
					{
						Random r = new Random();
						rdatt = r.nextInt(b.getpPkmn(b.p2).getAttacks().size());
						while (rdatt == b.checkDisabledAttack(b.p2))
							rdatt = r.nextInt(b.getpPkmn(b.p2).getAttacks().size());
					}
					//To avoid a disabled attack being a choice
					
					if (!choice.equals("") && Integer.parseInt(choice) - 1 == b.checkDisabledAttack(b.p1))
						throw new AlreadyBoundException();
					
					//Checking prioritary using speed and moves used
					if (b.getpPkmn(b.p1).getSpeed("current") >= b.getpPkmn(b.p2).getSpeed("current"))
					{
						if (b.getpattack(b.p2, rdatt).getStatus() == 51)
							b.getpPkmn(b.p1).setPrio(false);
						else if (b.getpPkmn(b.p1).getTwoturnstatus() != 0)
							b.getpPkmn(b.p1).setPrio(true);
						else if (b.getpattack(b.p1, Integer.parseInt(choice)-1).getId() == 22)
							b.getpPkmn(b.p1).setPrio(false);
						else if (b.getpattack(b.p1, Integer.parseInt(choice)-1).getId() == 80)
							b.getpPkmn(b.p1).setPrio(false);
						else
							b.getpPkmn(b.p1).setPrio(true);
					}
					else
					{
						if (!choice.equals("") && b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 51)
							b.getpPkmn(b.p1).setPrio(true);
						else if (b.getpattack(b.p2, rdatt).getId() == 22)
							b.getpPkmn(b.p1).setPrio(true);
						else if (b.getpattack(b.p2, rdatt).getId() == 80)
							b.getpPkmn(b.p1).setPrio(false);
						else
							b.getpPkmn(b.p1).setPrio(false);
					}
					/*
					* Case : player faster
					*/
					if (b.getpPkmn(b.p1).getPrio())
					{
						//Calls the useAttack function for index 0 which is the player. Checks if there is a twoturnstatus going on
						if (b.getpPkmn(b.p1).getTwoturnstatus() != 0)
							win.logTrace(b.useAttack(gd.allAtks[b.getpPkmn(b.p1).getTwoturnstatus()],b.p1,b.p2,0));
						else
							win.logTrace(b.useAttack(b.getpPkmn(b.p1).getAttacks().get(Integer.parseInt(choice) - 1),b.p1,b.p2,0));
						//Checks if the opponent fainted
						b.checkDead(b.p2, win);
						//If the attack deals to user, checks if it's not dead
						if (b.checkDead(b.p1, win))
							b.getpPkmn(b.p2).setCanAttack(false);
						if (b.getpPkmn(b.p2).getStatus() != 9)
						{
							//Checks if the pokémon didn't flinched between turns
							if (b.getpPkmn(b.p2).getCanAttack())
							{
								if (b.getpPkmn(b.p1).getTwoturnstatus() != 0)
									win.logTrace(b.useAttack(gd.allAtks[b.getpPkmn(b.p2).getTwoturnstatus()],b.p2,b.p1,1));
								else
									win.logTrace(b.useAttack(b.getpPkmn(b.p2).getAttacks().get(rdatt),b.p2,b.p1,1));
								//Checks if the player fainted
								b.checkDead(b.p1, win);
								b.checkDead(b.p2, win);
							}
							//... if it flinched, reset canAttack
							else
								b.getpPkmn(b.p2).setCanAttack(true);
						}
					}
					
					/*
					 * Case : opponent faster
					 */
					else
					{
						if (b.getpPkmn(b.p2).getTwoturnstatus() != 0)
							win.logTrace(b.useAttack(gd.allAtks[b.getpPkmn(b.p2).getTwoturnstatus()],b.p2,b.p1,1));
						else
							win.logTrace(b.useAttack(b.getpPkmn(b.p2).getAttacks().get(rdatt),b.p2,b.p1,1));
						//Checks if the player fainted
						b.checkDead(b.p1, win);
						if (b.checkDead(b.p2, win))
							b.getpPkmn(b.p1).setCanAttack(false);
						if (b.getpPkmn(b.p1).getStatus() != 9)
						{
							if (b.getpPkmn(b.p1).getCanAttack())
							{
								//Calls the useAttack function for index 0 which is the player. Checks if there is a twoturnstatus going on
								if (b.getpPkmn(b.p1).getTwoturnstatus() != 0)
									win.logTrace(b.useAttack(gd.allAtks[b.getpPkmn(b.p1).getTwoturnstatus()],b.p1,b.p2,0));
								else
									win.logTrace(b.useAttack(b.getpPkmn(b.p1).getAttacks().get(Integer.parseInt(choice) - 1),b.p1,b.p2,0));
								//Checks if someone fainted
								b.checkDead(b.p2, win);
								b.checkDead(b.p1, win);
							}
							else
								b.getpPkmn(b.p1).setCanAttack(true);
						}
					}
					/*
					 * Checks after attacks were used
					*/
					if (b.p1.getCountWall() > 0)
					{
						b.p1.setCountWall(b.p1.getCountWall() - 1);
						if (b.p1.getCountWall() == 0)
							win.logTrace("The wall of protection vanished !");
					}
					if (b.p2.getCountWall() > 0)
					{
						b.p2.setCountWall(b.p1.getCountWall() - 1);
						if (b.p2.getCountWall() == 0)
							win.logTrace("The enemy's wall of protection vanished !");
					}
					//Trap checking
					
					if (b.checkSeed(b.p1,b.p2,win))
						b.checkDead(b.p1, win);
					if (b.checkSeed(b.p2,b.p1,win))
						b.checkDead(b.p2, win);
					
					//Shows attacks if both player and opponent are not dead
					if (b.getpPkmn(b.p2).getStatus() != 9 && b.getpPkmn(b.p1).getStatus() != 9)
						win.logTrace(b.showAttacks());
					b.reinitPrio();
					b.getpPkmn(b.p1).setLastattacksuffered(new Attack());
					b.getpPkmn(b.p2).setLastattacksuffered(new Attack());
				}
			}
			catch (NullPointerException e1)
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
						win.logTrace(b.showAttacks());
						win.whatToChoose = "attack";
					}
					else
						throw  new NullPointerException();
				}
				else
				{
					b.p1.setCurrentPkmn(b.p1.getTeam().get(Integer.parseInt(choice)-1));
					b.p1.setCurrentStats(false);
					win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+" !");
					//If it's a switch (requested swap), the enemy will attack like normal
					if (win.whatToChoose.equals("switch"))
					{
						if (b.getpPkmn(b.p2).getTwoturnstatus() != 0)
							win.logTrace(b.useAttack(gd.allAtks[b.getpPkmn(b.p2).getTwoturnstatus()],b.p2,b.p1,1));
						else
						{
							//Choosing the attack of the opponent
							int rdatt;
							if (b.getpPkmn(b.p2).getAttacks().size() == 1)
								rdatt = 0;
							else
							{
								Random r = new Random();
								rdatt = r.nextInt(b.getpPkmn(b.p2).getAttacks().size());
								while (rdatt == b.checkDisabledAttack(b.p2))
									rdatt = r.nextInt(b.getpPkmn(b.p2).getAttacks().size());
							}
							win.logTrace(b.useAttack(b.getpPkmn(b.p2).getAttacks().get(rdatt),b.p2,b.p1,1));
							//Checks if someone fainted
							b.checkDead(b.p2, win);
							b.checkDead(b.p1, win);
							if (b.checkSeed(b.p1,b.p2,win))
								b.checkDead(b.p1, win);
							if (b.checkSeed(b.p2,b.p1,win))
								b.checkDead(b.p2, win);
							b.reinitPrio();
						}
					}
					win.whatToChoose = "attack";
					choice = null;
					win.logTrace(b.showAttacks());
				}
			}
			catch (NullPointerException e10)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon in your team !");
			}
			catch (AlreadyBoundException e12)
			{
				win.logTrace("Woops ! This Pokémon is already battling !");
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
					Thread.sleep(1000);
					win.clear();
					win.logTrace("A new battle will begin ! Please choose your team.");
					win.logTrace("To do so, type the number of your Pokémon and Enter. You can refer to the Pokédex if needed.");
					win.whatToChoose = "team";
					b = new Battle(gd);
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
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
