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
	String s = new String();
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
		for (int i=0;i<this.getpPkmn(p1).getAttacks().size();i++)
		{
			s = s + "\n "+Integer.toString(i+1)+". "+this.getpattack(this.p1,i).getName()+" - "+this.getpattack(this.p1,i).getDescription();
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
			this.s = this.getpPkmn(p1).getName()+" used "+this.getpattack(this.p1,iAtt).getName()+". ";
			if (checkHit(this.getpattack(this.p1,iAtt),this.getpPkmn(this.p1)))
				this.s = this.doDamages(iAtt, 0);
			else
				this.s = this.s + "\n"+this.getpPkmn(this.p1).getName() + " missed !";
		}
		else
		{
			this.s = this.getpPkmn(p2).getName()+" used "+this.getpattack(this.p2,iAtt).getName()+". ";
			if (checkHit(this.getpattack(this.p2,iAtt),this.getpPkmn(this.p2)))
				this.s = this.doDamages(iAtt, 1);
			else
				this.s = this.s + "\n"+this.getpPkmn(this.p2).getName() + " missed !";
		}
		return s;
	}
	
	//i : i=0 -> player, i=1 -> opponent (IA). iAtt is the id of the attack (refers to ArrayList Attacks)
	//Will calculate what happens if the attack hits 
	//Return s which will be printed as a log trace.
	private String doDamages(int iAtt, int i)
	{
		if (i == 0)
		{
			//Checking the status of the attack to see what will happen next
			//0 = standard attack, do nothing more than damages
			if (this.getpattack(this.p1, iAtt).getStatus() == 0)
			{
				atk0(this.p1, this.p2, iAtt);
			}
		}
		else
		{
			if (this.getpattack(this.p2, iAtt).getStatus() == 0)
			{
				atk0(this.p2, this.p1, iAtt);
			}
		}
		return this.s;
	}
	
	public Pokemon getpPkmn(Player p)
	{
		return p.getCurrentPkmn();
	}
	
	public Attack getpattack(Player p, int i)
	{
		return p.getCurrentPkmn().getAttacks().get(i);
	}
	
	//To write how many hp left the pokémon has
	public void checkHpLeft(Pokemon pk, int i)
	{
		if (pk.getStatus() == 9)
		{
			this.s = this.s + "\n***********************\n"+pk.getName()+" fainted !";
			if (i == 0)
			{
				this.p1.getTeam().remove(pk);
				this.s = this.s + "\nYou have "+this.p1.getTeam().size()+" pokémons left.";
			}
			else
			{
				this.p2.getTeam().remove(pk);
				this.s = this.s + "\nYour opponent has "+this.p2.getTeam().size()+" pokémons left.";
			}
		}
		else
			this.s = this.s +"\n"+pk.getName()+" has "+pk.getCurrentHp()+"/"+pk.getBaseHp()+" HP.";
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
				this.s = "It's super effective !\n";
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
	
	private boolean checkCrit(Attack att, Pokemon pk)
	{
		//Check if the attack has high critical ratio
		if (att.getStatus() == 47)
		{
			int T = pk.getBaseSpd() * 4;
			int P = ThreadLocalRandom.current().nextInt(0,256);
			if (T > P)
			{
				this.s = s + "\n A critical hit !";
				return true;
			}
			else
				return false;
		}
		else
		{
			int T = pk.getBaseSpd() / 2;
			int P = ThreadLocalRandom.current().nextInt(0,256);
			if (T > P)
			{
				this.s = s + "\n A critical hit ! ";
				return true;
			}
			else
				return false;
		}
	}
	//Mechanics for status 0 attack.
	//atk is attacker, def is defender
	private void atk0(Player atk, Player def, int iAtt)
	{
		int damage = 0;
		int power;
		power = this.getpattack(atk,iAtt).getPower();
		//Verify if STAB
		if (this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType1()) || this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType2()))
			power = (int) (power * 1.5);
		//Mathematical calculation for damages
		damage = ((2*20)/2 + 2)*power;
		//Checking crit to see if we use base or current stats
		if (checkCrit(this.getpattack(atk,iAtt), atk.getCurrentPkmn()))
		{
			if (this.getpattack(atk,iAtt).getPhy())
				damage = damage * (atk.getCurrentPkmn().getBaseAtk()/def.getCurrentPkmn().getBaseDef());
			else
				damage = damage * (atk.getCurrentPkmn().getBaseSpe()/def.getCurrentPkmn().getBaseSpe());
			damage = damage/50 + 2;
			damage = (int) (damage * 1.5);
		}
		else
		{
			if (this.getpattack(atk,iAtt).getPhy())
				damage = damage * (atk.getCurrentPkmn().getCurrentAtk()/def.getCurrentPkmn().getCurrentDef());
			else
				damage = damage * (atk.getCurrentPkmn().getCurrentSpe()/def.getCurrentPkmn().getCurrentSpe());
			damage = damage/50 + 2;
		}
		//Checks strength/weakness
		damage = this.checkStrWeak(damage, this.getpattack(atk,iAtt),def.getCurrentPkmn());
		if (damage != 0)
			this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
		def.getCurrentPkmn().setCurrentHp(def.getCurrentPkmn().getCurrentHp() - damage);
		this.checkHpLeft(def.getCurrentPkmn(),1);
	}
}
