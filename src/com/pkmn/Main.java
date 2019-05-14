package com.pkmn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main implements ActionListener
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
		Thread.sleep(700);
		win.jtf.addActionListener(this);
		win.logTrace("Welcome in the Pokémon Textual Game !\nType 1 and Enter to start.");
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
			win.logTrace("A new battle will begin ! Please choose your team.");
			win.logTrace("To do so, type the number of your Pokémon and Enter. You can refer to the Pokédex if needed.");
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
					win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+ " !");
					win.logTrace("Your opponent sent "+b.getpPkmn(b.p2).getName()+ " !");
					win.logTrace(b.showAttacks());
				}
				//... Otherwise showing how many pokémons are left to choose
				else
					win.logTrace("You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose.");
			}
			//If what the user type is not a pokémon (not a number, something higher than 151...)
			catch (Exception e)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon !");
			}
		}
		//Selecting the attack
		else if (win.whatToChoose.equals("attack"))
		{
			//Try/catch to check if the number is a valid choice
			try
			{
				//If choice is empty, checks if we are in a twoturn situation.
				if (choice.equals(""))
				{
					if (b.getpPkmn(b.p1).getTwoturnstatus() != 0)
					{
						for (int i = 0 ; i < b.getpPkmn(b.p1).getAttacks().size() ; i++)
						{
							if (b.getpattack(b.p1,i).getId() == b.getpPkmn(b.p1).getTwoturnstatus())
							{
								choice = Integer.toString(i+1);
								break;
							}
						}
					}
					else
						throw new NullPointerException();
				}
				//If not, checks the choice to see if it's valid
				else if (Integer.parseInt(choice) > b.getpPkmn(b.p1).getAttacks().size())
					throw new NullPointerException();
				
				//Choosing the attack of the opponent
				Random r = new Random();
				int rdatt = r.nextInt(b.getpPkmn(b.p2).getAttacks().size());
				//Checking if a prioritary move has been used
				if (b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 51)
					b.getpPkmn(b.p1).setPrio(true);
				else if (b.getpattack(b.p2, rdatt).getStatus() == 51)
					b.getpPkmn(b.p2).setPrio(true);
				else if (b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 51 && b.getpattack(b.p2, rdatt).getStatus() == 51)
					if (b.getpPkmn(b.p1).getSpeed("current") >= b.getpPkmn(b.p2).getSpeed("current"))
						b.getpPkmn(b.p1).setPrio(true);
					else
						b.getpPkmn(b.p2).setPrio(true);
						
				/*
				* Speed checking to choose the first Pokémon that hits. If tie, the player moves first
				* Case : player faster
				*/
				if (b.getpPkmn(b.p1).getSpeed("current") >= b.getpPkmn(b.p2).getSpeed("current") || b.getpPkmn(b.p1).getPrio())
				{
					//Calls the useAttack function for index 0 which is the player
					win.logTrace(b.useAttack(Integer.parseInt(choice) - 1,b.p1,b.p2,0));
					//Checks if the opponent fainted
					b.checkDead(b.p2, win);
					//If the attack deals to user, checks if it's not dead
					if (b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 49 || b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 50)
						if (b.checkDead(b.p1, win))
							b.getpPkmn(b.p2).setCanAttack(false);
					if (b.getpPkmn(b.p2).getStatus() != 9)
					{
						//Checks if the pokémon didn't flinched between turns
						if (b.getpPkmn(b.p2).getCanAttack())
						{
							//If we are in a two turn situation, chooses the previous attack instead.
							if (b.getpPkmn(b.p2).getTwoturnstatus() != 0)
							{
								//Forces to choose the two turn attack stocked in the getTwoturnstatus variable.
								for (int i = 0 ; i < b.getpPkmn(b.p2).getAttacks().size() ; i++)
								{
									if (b.getpattack(b.p2,i).getId() == b.getpPkmn(b.p2).getTwoturnstatus())
									{
										rdatt = i;
										break;
									}
								}
							}
							else if (b.getpPkmn(b.p2).getAttacks().size() == 1)
								rdatt = 0;
							win.logTrace(b.useAttack(rdatt,b.p2,b.p1,1));
							//Checks if the player fainted
							b.checkDead(b.p1, win);
						}
						//... if it flinched, reset canAttack and shows attacks
						else
							b.getpPkmn(b.p2).setCanAttack(true);
					}
				}
				
				/*
				 * Case : opponent faster
				 */
				else
				{
					//If we are in a two turn situation, chooses the previous attack instead.
					if (b.getpPkmn(b.p2).getTwoturnstatus() != 0)
					{
						for (int i = 0 ; i < b.getpPkmn(b.p2).getAttacks().size() ; i++)
						{
							if (b.getpattack(b.p2,i).getId() == b.getpPkmn(b.p2).getTwoturnstatus())
							{
								rdatt = i;
								break;
							}
						}
					}
					else if (b.getpPkmn(b.p2).getAttacks().size() == 1)
						rdatt = 0;
					win.logTrace(b.useAttack(rdatt,b.p2,b.p1,0));
					//Checks if the player fainted
					b.checkDead(b.p1, win);
					//If the attack deals recoil to user, checks if it's not dead
					if (b.getpattack(b.p2, rdatt).getStatus() == 49 || b.getpattack(b.p2, rdatt).getStatus() == 50)
						b.checkDead(b.p2, win);
					if (b.getpPkmn(b.p2).getStatus() != 9)
					{
						if (b.getpPkmn(b.p1).getCanAttack())
						{
							win.logTrace(b.useAttack(Integer.parseInt(choice)-1,b.p1,b.p2,1));
							//Checks if the opponent fainted
							b.checkDead(b.p2, win);
							//If the attack deals to user,  checks if he dies from it
							if (b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 49 || b.getpattack(b.p1, Integer.parseInt(choice)-1).getStatus() == 50)
								b.checkDead(b.p1, win);
						}
						else
							b.getpPkmn(b.p1).setCanAttack(true);
					}
					
				}
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
				if (b.getpPkmn(b.p2).getStatus() != 9 && b.getpPkmn(b.p1).getStatus() != 9)
					win.logTrace(b.showAttacks());
				b.reinitPrio();
			}
			catch (Exception e1)
			{
				win.logTrace("Woops ! That doesn't look like a valid attack !");
				choice = null;
			}
		}
		else if (win.whatToChoose.equals("swap"))
		{
			try
			{
				//Throws a NPE if the number is not a valid choice
				if (Integer.parseInt(choice) > b.p1.getTeam().size() || Integer.parseInt(choice) == 0)
					throw  new NullPointerException();
				b.p1.setCurrentPkmn(b.p1.getTeam().get(Integer.parseInt(choice)-1));
				b.p1.setCurrentStats(true);
				win.logTrace("You sent "+b.getpPkmn(b.p1).getName()+" !");
				win.whatToChoose = "attack";
				choice = null;
				win.logTrace(b.showAttacks());
			}
			catch (Exception e10)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon in your team !");
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
					win.logTrace("Ok then. See you later.");
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
}
