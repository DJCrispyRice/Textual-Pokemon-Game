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
		s = "\nPlease choose an attack.";
		for (int i=0;i<this.p1.getCurrentPkmn().getAttacks().size();i++)
		{
			s = s + "\n "+Integer.toString(i+1)+". "+this.p1.currentPkmn.getAttacks().get(i).getName()+" - "+this.p1.currentPkmn.getAttacks().get(i).getDescription();
		}
		return s;
	}
	//i : i=0 -> player, i=1 -> opponent (IA). iAtt is the id of the attack (refers to ArrayList Attacks)
	public String useAttack(int iAtt, int i)
	{
		String s = new String();
		if (i == 0)
		{
			s = this.p1.getCurrentPkmn().getName()+" used "+this.p1.getCurrentPkmn().getAttacks().get(i).getName()+".";
			s = s + "\n"+this.doDamages(iAtt, 0);
		}
		else
		{
			s = this.p2.getCurrentPkmn().getName()+" used "+this.p2.getCurrentPkmn().getAttacks().get(i).getName()+".";
			s = s + "\n"+this.doDamages(iAtt, 1);
		}
		return s;
	}
	
	//i : i=0 -> player, i=1 -> opponent (IA). iAtt is the id of the attack (refers to ArrayList Attacks)
	private String doDamages(int iAtt, int i)
	{
		String s = new String();
		int damage = 0;
		if (i == 0)
		{
			//Checking the type of the attack to see what will happen next
			//0 = standard attack, do nothing more than damages
			if (this.p1.getCurrentPkmn().getAttacks().get(i).getStatus() == 0)
			{
				damage = (((2*25)/5)+2*this.p1.getCurrentPkmn().getAttacks().get(i).getPower());
				if (this.p1.getCurrentPkmn().getAttacks().get(i).getPhy())
					damage = (damage * (this.p1.getCurrentPkmn().getCurrentAtk()/this.p2.getCurrentPkmn().getCurrentDef()/50))+2;
				else
					damage = (damage * (this.p1.getCurrentPkmn().getCurrentSpe()/this.p2.getCurrentPkmn().getCurrentSpe()/50))+2;
				s = this.p2.getCurrentPkmn().getName()+" took "+Integer.toString(damage)+" HP.";
			}
		}
		else
		{
			if (this.p2.getCurrentPkmn().getAttacks().get(i).getStatus() == 0)
			{
				damage = (((2*25)/5)+2*this.p2.getCurrentPkmn().getAttacks().get(i).getPower());
				if (this.p2.getCurrentPkmn().getAttacks().get(i).getPhy())
					damage = (damage * (this.p2.getCurrentPkmn().getCurrentAtk()/this.p1.getCurrentPkmn().getCurrentDef()/50))+2;
				else
					damage = (damage * (this.p2.getCurrentPkmn().getCurrentSpe()/this.p1.getCurrentPkmn().getCurrentSpe()/50))+2;
				s = this.p1.getCurrentPkmn().getName()+" took "+Integer.toString(damage)+" HP.";
			}
		}
		return s;
	}
}
