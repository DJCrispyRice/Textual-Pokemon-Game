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
		for (int j = 1 ; j <= 6; j++)
		{
			try 
			{
				this.setTeam((Pokemon) gd.allPkmn[ThreadLocalRandom.current().nextInt(1, 151 + 1)].clone());
			} 
			catch (CloneNotSupportedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			win.logTrace("Pokémon "+j+" is... "+this.getTeam().get(j-1).getName()+" !");
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
				while (rdatt == this.checkDisabledAttack())
					rdatt = r.nextInt(this.getCurrentPkmn().getAttacks().size());
			}
			return rdatt;
		}
		else
			return 0;
	}
}
