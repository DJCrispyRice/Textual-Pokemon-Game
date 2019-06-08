package com.pkmn;
/* 
 * This class contains the team of each player (two player per battle usually). The team contains at least one pokemon and max 6. 
 * The current pokemon is copied in the currentPkmn object. 
 */

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
			setTeam(gd.allPkmn[Integer.parseInt(choice)].clone());
			win.logTrace("You chose "+gd.allPkmn[Integer.parseInt(choice)].getName()+" !");
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
						setTeam(gd.allPkmn[i].clone());
						win.logTrace("You chose "+gd.allPkmn[i].getName()+" !");
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
	
	public int checkDisabledAttack()
	{
		for (int i = 0;i < this.getCurrentPkmn().getAttacks().size();i++)
		{
			if (!this.getCurrentPkmn().getAttacks().get(i).getEnabled())
				return i;
		}	
		return 5;
	}
	
	//To write how many hp left the pokémon has. Def is the player who is being checked.
	public String checkHpLeft()
	{
		String s = new String();
		//Checks if the pokemon is dead.
		if (this.getCurrentPkmn().getStatus() == 9)
		{
			s = s + "\n***********************\n"+this.getCurrentPkmn().getName()+" fainted !";
			if (this.getName().equals("Player"))
			{
				this.getTeam().remove(this.getCurrentPkmn());
				s = s + "\nYou have "+this.getTeam().size()+" pokémon left.";
			}
			else
			{
				this.getTeam().remove(this.getCurrentPkmn());
				s = s + "\nYour opponent has "+this.getTeam().size()+" pokémon left.";
			}
		}
		else
		{
			if (this.getCurrentPkmn().getSub())
			{
				if (this.getCurrentPkmn().getHpSubstitute() <= 0)
				{
					this.getCurrentPkmn().setSub(false);
					if (this.getName().equals("Opponent"))
						s = s + "\nEnemy ";
					else
						s = s + "\n";
					s = s + this.getCurrentPkmn().getName()+"'s substitute broke.";
				}
			}
			else
			{
				if (this.getName().equals("Opponent"))
					s = s + "\nEnemy ";
				else
					s = s + "\n";
				s = s + this.getCurrentPkmn().getName()+" has "+this.getCurrentPkmn().getCurrentHp()+"/"+this.getCurrentPkmn().getBaseHp()+" HP. ";
			}
		}
		return s;
	}
	
	public boolean checkDead(Window win)
	{
		if (this.getCurrentPkmn().getStatus()==9)
		{
			if (this.getName().equals("Opponent"))
			{				
				if (this.getTeam().size()>0)
				{
					if (this.getTeam().size()==1)
						this.setCurrentPkmn(this.getTeam().get(0));
					this.setCurrentPkmn(this.getTeam().get(ThreadLocalRandom.current().nextInt(0, this.getTeam().size() - 1)));
					this.getCurrentPkmn().setCurrentStats(false);
					win.logTrace("Your opponent sent "+this.getCurrentPkmn().getName()+" !");
					this.getCurrentPkmn().setCanAttack(false);
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
			}
			return true;
		}
		else
			return false;
	}
}
