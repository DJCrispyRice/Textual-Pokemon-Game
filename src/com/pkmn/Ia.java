package com.pkmn;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * This class will contain everything related to an IA player such as level, choosing attacks, etc.
 */

public class Ia extends Player
{
	int level = 0;
	public Ia() 
	{
		super("Opponent");
	}
	
	public void createTeam(GameData gd, Window win)
	{
		try
		{
			for (int j = 1 ; j <= 6; j++)
			{
				this.setTeam((Pokemon) gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)].clone());
				win.logTrace("Pokémon "+j+" is... "+this.getTeam().get(j-1).getName()+" !");
			}
		}
		catch (CloneNotSupportedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		win.logTrace("Your opponent chose his team, now let's the battle begin !");
	}
	
	public int chooseAttack()
	{
		if (level == 0)
		{
			int rdatt;
			if (this.getCurrentPkmn().getAttacks().size() == 1)
				rdatt = 0;
			else
			{
				Random r = new Random();
				rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
				while (rdatt == this.getCurrentPkmn().checkDisabledAttack())
					rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
			}
			return rdatt;
		}
		else
			return 0;
	}
	
	public String checkHpLeft()
	{
		String s = new String();
		//Checks if the pokemon is dead.
		if (this.getCurrentPkmn().getStatus() == 9)
		{
			s = s + "\n***********************\n"+this.getCurrentPkmn().getName()+" fainted !";
			this.getTeam().remove(this.getCurrentPkmn());
			s = s + "\nYour opponent has "+this.getTeam().size()+" pokémon left.";
		}
		else
		{
			if (this.getCurrentPkmn().getSub())
			{
				if (this.getCurrentPkmn().getHpSubstitute() <= 0)
				{
					this.getCurrentPkmn().setSub(false);
					s = s + "\n Enemy" +this.getCurrentPkmn().getName()+"'s substitute broke.";
				}
			}
			else
				s = s + "\nEnemy " + this.getCurrentPkmn().getName()+" has "+this.getCurrentPkmn().getCurrentHp()+"/"+this.getCurrentPkmn().getBaseHp()+" HP.";
		}
		return s;
	}
	
	public boolean checkDead(Window win)
	{
		if (this.getCurrentPkmn().getStatus()==9)
		{
			if (this.getTeam().size()>0)
			{
				if (this.getTeam().size()==1)
					this.setCurrentPkmn(this.getTeam().get(0));
				else
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
			return true;
		}
		else
			return false;
	}
}
