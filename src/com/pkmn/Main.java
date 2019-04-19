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
		else if (win.whatToChoose.equals("team"))
		{
			try
			{
				//To avoid MissingNo being a choice
				if (choice.equals("0") || Integer.parseInt(choice) == 0)
					throw  new NullPointerException();
				b.p1.setTeam(gd.allPkmn[Integer.parseInt(choice)]);
				win.logTrace("You chose "+gd.allPkmn[Integer.parseInt(choice)].getName()+" !");
				//Checking if the party is full
				if (b.p1.getTeam().size() == 6)
				{
					win.logTrace("There you go ! Your party is full.");
					win.logTrace("Hold on, your opponant is choosing his team...");
					//Randomly choosing 6 pokémons for the opponent.
					for (int i = 1;i<=6;i++)
					{
						b.p2.setTeam(gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)]);
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
					win.logTrace("You have "+ Integer.toString(6 - b.p1.getTeam().size())+" Pokémon left to choose !");
			}
			//If what the user type is not a pokémon (not a number, something higher than 151...)
			catch (Exception e)
			{
				win.logTrace("Woops ! That doesn't look like a valid Pokémon !");
			}
		}
		else if (win.whatToChoose.equals("attack"))
		{
			//Try/catch to check if the number is a valid choice
			try
			{
				//Throws a NPE if the number is not a valid choice
				if (Integer.parseInt(choice) > b.getpPkmn(b.p1).getAttacks().size())
					throw  new NullPointerException();
				//Speed checking to choose the first Pokémon that hits. If tie, the player moves first
				
				//Case : player faster
				if (b.getpPkmn(b.p1).getCurrentSpd() >= b.getpPkmn(b.p2).getCurrentSpd())
				{
					//Calls the useAttack function for index 0 which is the player
					win.logTrace(b.useAttack(Integer.parseInt(choice)-1,0));
					//Checks if the opponent fainted
					if (b.p2.getCurrentPkmn().getStatus()==9)
					{
						//Randomly selects a new pokémon if the previous one fainted, if any available
						if (b.p2.getTeam().size()>0)
						{
							b.p2.setCurrentPkmn(b.p2.getTeam().get(ThreadLocalRandom.current().nextInt(0, b.p2.getTeam().size())));
							b.p2.setCurrentStats();
							win.logTrace("Your opponent sent "+b.getpPkmn(b.p2).getName()+" !");
							win.logTrace(b.showAttacks());
						}
						else
						{
							win.logTrace("You won ! :-D");
							win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
							win.whatToChoose = "continue";
						}
					}
					else
					{
						//The opponent randomly selects an attack and use it.
						win.logTrace(b.useAttack(ThreadLocalRandom.current().nextInt(0,b.getpPkmn(b.p2).getAttacks().size()),1));
						//Checks if the player fainted
						if (b.p1.getCurrentPkmn().getStatus()==9)
						{
							//Checking if the battle is over
							if (b.p1.getTeam().size()>0)
							{
								win.logTrace("Please choose another Pokémon from your team.");
								for (int i = 0; i < b.p1.getTeam().size(); i++)
								{
									win.logTrace(Integer.toString(i+1)+" - "+b.p1.getTeam().get(i).getName());
								}
								win.logTrace("*********************** ");
								win.whatToChoose = "swap";
							}
							else
							{
								win.logTrace("You lost the battle, all of your pokémons fainted. :-(");
								win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
								choice = null;
								win.whatToChoose = "continue";
							}
						}
						else
							win.logTrace(b.showAttacks());
					}
				}
				
				//Case : opponent faster
				else
				{
					//The opponent randomly selects an attack and use it (case he is faster than player)
					win.logTrace(b.useAttack(ThreadLocalRandom.current().nextInt(0,b.getpPkmn(b.p2).getAttacks().size()),1));
					//Checks if the player fainted
					if (b.p1.getCurrentPkmn().getStatus()==9)
					{
						//Checking if the battle is over
						if (b.p1.getTeam().size()>0)
						{
							win.logTrace("Please choose another Pokémon from your team.");
							for (int i = 0; i < b.p1.getTeam().size(); i++)
							{
								win.logTrace(Integer.toString(i+1)+" - "+b.p1.getTeam().get(i).getName());
							}
							win.logTrace("*********************** ");
							win.whatToChoose = "swap";
						}
						else
						{
							win.logTrace("You lost the battle, all of your pokémons fainted. :-(");
							win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
							choice = null;
							win.whatToChoose = "continue";
						}
					}
					else
					{
						win.logTrace(b.useAttack(Integer.parseInt(choice)-1,0));
						//Checks if the opponent fainted
						if (b.p2.getCurrentPkmn().getStatus()==9)
						{
							//Randomly selects a new pokémon if the previous one fainted, if any available
							if (b.p2.getTeam().size()>0)
							{
								b.p2.setCurrentPkmn(b.p2.getTeam().get(ThreadLocalRandom.current().nextInt(0, b.p2.getTeam().size())));
								b.p2.setCurrentStats();
								win.logTrace("Your opponent sent "+b.getpPkmn(b.p2).getName()+" !");
								win.logTrace(b.showAttacks());
							}
							else
							{
								win.logTrace("You won ! :-D");
								win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
								win.whatToChoose = "continue";
							}
						}
						else
							win.logTrace(b.showAttacks());
					}
				}
			}
			catch (NullPointerException e1)
			{
				win.logTrace("Woops ! That doesn't look like a valid attack !");
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
				b.p1.setCurrentStats();
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
