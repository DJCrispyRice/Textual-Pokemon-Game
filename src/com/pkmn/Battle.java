package com.pkmn;

import java.util.concurrent.ThreadLocalRandom;

/* 
 * This class will contain all the battle's logic such as attacking, calculating damages, accuracy, etc. All it will contain is two players 
 * since every thing else comes from it.
 */

public class Battle 
{
	Player p1;
	Player p2;
	String s;
	GameData gd;
	
	public Battle(GameData gd)
	{
		this.p1 = new Player();
		this.p2 = new Player();
	}
	
	//Shows attacks that can be used by your pokémon
	public String showAttacks()
	{
		this.s = new String();
		s = "***********************\nPlease choose an attack.";
		for (int i=0;i<this.getp1pkmn().getAttacks().size();i++)
		{
			s = s + "\n "+Integer.toString(i+1)+". "+this.getp1attack(i).getName()+" - "+this.getp1attack(i).getDescription();
		}
		s = s + "\n*********************** ";
		return s;
	}
	//i : i=0 -> player, i=1 -> opponent (IA). iAtt is the id of the attack (refers to ArrayList Attacks).
	//Return s which will be printed as a log trace.
	public String useAttack(int iAtt, int i)
	{
		this.s = new String();
		if (i == 0)
		{
			s = this.getp1pkmn().getName()+" used "+this.getp1attack(iAtt).getName()+".";
			if (checkHit(this.getp1attack(iAtt),this.getp1pkmn()))
				s = s + "\n"+this.doDamages(iAtt, 0);
			else
				s = s + "\n"+this.getp1pkmn().getName() + " missed !";
		}
		else
		{
			s = this.getp2pkmn().getName()+" used "+this.getp2attack(iAtt).getName()+".";
			if (checkHit(this.getp2attack(iAtt),this.getp2pkmn()))
				s = s + "\n"+this.doDamages(iAtt, 1);
			else
				s = s + "\n"+this.getp2pkmn().getName() + " missed !";
		}
		return s;
	}
	
	//i : i=0 -> player, i=1 -> opponent (IA). iAtt is the id of the attack (refers to ArrayList Attacks)
	//Will calculate what happens if the attack hits 
	//Return s which will be printed as a log trace.
	private String doDamages(int iAtt, int i)
	{
		this.s = new String();
		int damage = 0;
		int power;
		if (i == 0)
		{
			//Checking the status of the attack to see what will happen next
			//0 = standard attack, do nothing more than damages
			if (this.getp1attack(iAtt).getStatus() == 0)
			{
				power = this.getp1attack(iAtt).getPower();
				//Verify if STAB
				if (this.getp1attack(iAtt).getType().equals(this.getp1pkmn().getType1()) || this.getp1attack(iAtt).getType().equals(this.getp1pkmn().getType2()))
					power = (int) (power * 1.5);
				//Mathematical calculation for damages
				damage = ((2*20)/2 + 2)*power;
				if (this.getp1attack(iAtt).getPhy())
					damage = damage * (this.getp1pkmn().getCurrentAtk()/this.getp2pkmn().getCurrentDef());
				else
					damage = damage * (this.getp1pkmn().getCurrentSpe()/this.getp2pkmn().getCurrentSpe());
				damage = damage/50 + 2;
				//Checks strength/weakness
				damage = this.checkStrWeak(damage, this.getp1attack(iAtt),this.getp2pkmn());
				s = this.getp2pkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
				this.getp2pkmn().setCurrentHp(this.getp2pkmn().getCurrentHp() - damage);
				s = this.checkHpLeft(s,this.getp2pkmn(),1);
			}
		}
		else
		{
			if (this.getp2attack(iAtt).getStatus() == 0)
			{
				power = this.getp2attack(iAtt).getPower();
				//Verify if STAB
				if (this.getp2attack(iAtt).getType().equals(this.getp2pkmn().getType1()) || this.getp2attack(iAtt).getType().equals(this.getp2pkmn().getType2()))
					power = (int) (power * 1.5);
				//Mathematical calculation for damages
				damage = ((2*20)/2 + 2)*power;
				if (this.getp2attack(iAtt).getPhy())
					damage = damage * (this.getp2pkmn().getCurrentAtk()/this.getp1pkmn().getCurrentDef());
				else
					damage = damage * (this.getp2pkmn().getCurrentSpe()/this.getp1pkmn().getCurrentSpe());
				damage = damage/50 + 2;
				//Check strength/weakness
				damage = this.checkStrWeak(damage, this.getp2attack(iAtt),this.getp1pkmn());
				s = this.getp1pkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
				this.getp1pkmn().setCurrentHp(this.getp1pkmn().getCurrentHp() - damage);
				s = this.checkHpLeft(s,this.getp1pkmn(),0);
			}
		}
		return s;
	}
	
	public Pokemon getp1pkmn()
	{
		return this.p1.getCurrentPkmn();
	}
	
	public Pokemon getp2pkmn()
	{
		return this.p2.getCurrentPkmn();
	}
	
	public Attack getp1attack(int i)
	{
		return this.getp1pkmn().getAttacks().get(i);
	}
	
	public Attack getp2attack(int i)
	{
		return this.getp2pkmn().getAttacks().get(i);
	}
	
	//To write how many hp left the pokémon has
	public String checkHpLeft(String s, Pokemon pk, int i)
	{
		if (pk.getStatus() == 9)
		{
			s = s + "\n***********************\n"+pk.getName()+" fainted !";
			if (i == 0)
			{
				this.p1.getTeam().remove(pk);
				s = s + "\nYou have "+this.p1.getTeam().size()+" pokémons left.";
			}
			else
			{
				this.p2.getTeam().remove(pk);
				s = s + "\nYour opponent has "+this.p2.getTeam().size()+" pokémons left.";
			}
		}
		else
			s = s +"\n"+pk.getName()+" has "+pk.getCurrentHp()+" HP left.";
		return s;
	}
	
	private boolean checkHit(Attack att, Pokemon pk)
	{
		int accurate = 100;
		accurate = accurate - (100 - att.getAccuracy()) - (100 - pk.getCurrentAccu());
		if (accurate < 0)
			accurate = 0;
		int random = ThreadLocalRandom.current().nextInt(0,100);
		if (random > accurate)
			return false;
		else
			return true;
	}
	
	private int checkStrWeak(int dmg, Attack att, Pokemon pk)
	{
		//Checking Strength first
		for (int i = 0;i<pk.getType1().getStrength().size();i++)
		{
			if (att.getType().equals(pk.getType1().getStrength().get(i)))
			{
				s = "It's super effective !\n";
				return dmg*2;
			}
		}
		//Checking weakness then
		for (int i = 0;i<pk.getType1().getWeak().size();i++)
		{
			if (att.getType().equals(pk.getType1().getWeak().get(i)))
			{
				this.s = "It's not very effective...\n";
				return dmg/2;
			}
		}
		//Checking uselessness
		for (int i = 0;i<pk.getType1().getUseless().size();i++)
		{
			if (att.getType().equals(pk.getType1().getUseless().get(i)))
			{
				this.s = "It did nothing at all !\n";
				return 0;
			}
		}
		return dmg;
	}
}
