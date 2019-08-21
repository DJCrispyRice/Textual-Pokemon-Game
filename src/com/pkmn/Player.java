package com.pkmn;
/* 
 * This class contains the team of each player (two player per battle usually). The team contains at least one pokemon and max 6. 
 * The current pokemon is copied in the currentPkmn object. 
 */

import java.util.ArrayList;

public class Player 
{
	String name;
	ArrayList <Pokemon> team;
	Pokemon currentPkmn;
	int wall = 0;
	int countWall = 0;
	boolean seeded = false;
	
	public Player(String name)
	{
		this.team = new ArrayList <Pokemon>();
		this.currentPkmn = new Pokemon();
		this.name = new String(name);
	}
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public ArrayList<Pokemon> getTeam() 
	{
		return team;
	}
	
	public boolean setTeam(Pokemon pkmn) 
	{
		if (team.size()<6)
		{
			this.team.add(pkmn);
			return true;
		}
		else
			return false;
	}
	
	public Pokemon getCurrentPkmn() 
	{
		return currentPkmn;
	}
	
	public void setCurrentPkmn(Pokemon currentPkmn) 
	{
		this.currentPkmn = currentPkmn;
	}
	
	public int getWall() 
	{
		return wall;
	}
	
	public void setWall(int wall) 
	{
		this.wall = wall;
	}
	
	public int getCountWall() 
	{
		return countWall;
	}
	
	public void setCountWall(int countWall) 
	{
		this.countWall = countWall;
		if (this.countWall == 0)
			setWall(0);
	}
	
	public boolean getSeeded() 
	{
		return seeded;
	}
	
	public void setSeeded(boolean seeded) 
	{
		this.seeded = seeded;
	}
	
	public void chooseByNumber(String choice,GameData gd, Window win) throws NumberFormatException, CloneNotSupportedException
	{
		if (choice.equals("0") || Integer.parseInt(choice) == 0)
			throw  new NullPointerException();
		else
		{
			win.music.stop();
			win.se = new Sound(this.getClass().getResource("/res/cries/"+gd.allPkmn[Integer.parseInt(choice)].getId()+".wav"));
			win.se.play();
			setTeam(gd.allPkmn[Integer.parseInt(choice)].clone());
			win.logTrace("You chose "+gd.allPkmn[Integer.parseInt(choice)].getName()+" !");
			try 
			{
				Thread.sleep(500);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			win.music.playLoop();
		}
	}
	
	public void chooseByName(String choice,GameData gd, Window win)
	{
		boolean ok = false;
		if (choice.equals("POKEDEX"))
			gd.showPokedex(win);
		else
		{
			for (int i = 1; i < 152; i++)
			{
				if (gd.allPkmn[i].getName().equals(choice))
				{
					try 
					{
						win.music.stop();
						win.se = new Sound(this.getClass().getResource("/res/cries/"+i+".wav"));
						win.se.play();
						setTeam(gd.allPkmn[i].clone());
						win.logTrace("You chose "+gd.allPkmn[i].getName()+" !");
						try 
						{
							Thread.sleep(500);
						} catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
						win.music.playLoop();
						ok = true;
						break;
					} 
					catch (CloneNotSupportedException e) 
					{}
				}
			}
			if (!ok)
				win.logTrace("Woops ! That doesn't look like a valid Pokémon !");
		}
	}
	
	public void showTeam(Window win)
	{
		win.logTrace("Please choose another Pokémon from your team.");
		if (win.whatToChoose.equals("switch"))
			win.logTrace("0 - Cancel");
		for (int i = 0; i < this.getTeam().size(); i++)
		{
			win.logTrace(Integer.toString(i+1)+" - " + this.getTeam().get(i).getName());
		}
		win.logTrace("*********************** ");
	}
	
	//To write how many hp left the pokémon has.
	public String checkHpLeft()
	{
		String s = new String();
		//Checks if the pokemon is dead.
		if (this.getCurrentPkmn().getStatus() == 9)
		{
			s = s + "\n***********************\n"+this.getCurrentPkmn().getName()+" fainted !";
			this.getTeam().remove(this.getCurrentPkmn());
			s = s + "\nYou have "+this.getTeam().size()+" pokémon left.";
		}
		else
		{
			if (this.getCurrentPkmn().getSub())
			{
				if (this.getCurrentPkmn().getHpSubstitute() <= 0)
				{
					this.getCurrentPkmn().setSub(false);
					s = s + "\n" + this.getCurrentPkmn().getName()+"'s substitute broke.";
				}
			}
			else
				s = s + "\n" + this.getCurrentPkmn().getName()+" has "+this.getCurrentPkmn().getCurrentHp()+"/"+this.getCurrentPkmn().getBaseHp()+" HP. ";
		}
		return s;
	}
	
	public boolean checkDead(Window win)
	{
		if (this.getCurrentPkmn().getStatus()==9)
		{
			win.music.stop();
			win.se = new Sound(this.getClass().getResource("/res/cries/"+this.getCurrentPkmn().getId()+".wav"));
			try 
			{
				Thread.sleep(500);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			win.se.play();
			win.music.playLoop();
			if (this.getTeam().size()>0)
			{
				win.logTrace("Please choose another Pokémon from your team.");
				for (int i = 0; i < this.getTeam().size(); i++)
				{
					win.logTrace(Integer.toString(i+1)+" - "+this.getTeam().get(i).getName());
				}
				win.logTrace("*********************** ");
				win.whatToChoose = "swap";
				this.getCurrentPkmn().setCanAttack(false);
			}
			else
			{
				win.logTrace("You lost the battle, all of your pokémons fainted. :-(");
				win.logTrace("Wanna play again ? 1 for YES, 2 for NO");
				win.whatToChoose = "continue";
			}
			return true;
		}
		else
			return false;
	}
}
