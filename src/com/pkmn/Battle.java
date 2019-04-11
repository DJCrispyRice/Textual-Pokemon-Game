package com.pkmn;

/* 
 * This class will contain all the battle's logic such as attacking, calculating damages, accuracy, etc. All it will contain is two players 
 * since every thing else comes from it.
 */

public class Battle 
{
	Player p1;
	Player p2;
	GameData gd;
	
	public Battle(GameData gd)
	{
		this.p1 = new Player();
		this.p2 = new Player();
	}
	
	//Shows attacks that can be used by your pokémon
	public String showAttacks()
	{
		String s = new String();
		s = "<br>Please choose an attack.";
		for (int i=0;i<this.p1.getCurrentPkmn().getAttacks().size();i++)
		{
			s = s + "<br> "+Integer.toString(i+1)+". "+this.p1.currentPkmn.getAttacks().get(i).getName()+" - "+this.p1.currentPkmn.getAttacks().get(i).getDescription();
		}
		return s;
	}
	//i : i=0 -> player, i=1 -> opponent (IA). iAtt is the id of the attack (refers to ArrayList Attacks
	public String useAttack(int iAtt, int i)
	{
		String s = new String();
		if (i == 0)
		{
			s = "<br>"+this.p1.getCurrentPkmn().getName()+" used "+this.p1.getCurrentPkmn().getAttacks().get(i).getName()+".";
		}
		else
		{
			s = "<br>"+this.p2.getCurrentPkmn().getName()+" used "+this.p2.getCurrentPkmn().getAttacks().get(i).getName()+".";
		}
		return s;
	}
}
