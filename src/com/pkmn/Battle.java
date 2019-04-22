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
			//If move's power is higher than 0, do damages. Then check status change
			if (this.getpattack(this.p1,iAtt).getPower()>0)
				atk0(this.p1, this.p2, iAtt, 1);
			int randomstat = ThreadLocalRandom.current().nextInt(0,100);
			if (randomstat >= 100 - this.getpattack(this.p1, iAtt).getAccu_status())
			{
				switch (this.getpattack(this.p1, iAtt).getStatus())
				{			
					//Can cause paralysis
					case 1 : 
						atk1(this.p2);
						break;
					//Attack boost for user
					case 9 :
						atk9(this.p1);
						break;
					//Def drop for opponent
					case 16:
						atk16(this.p2);
						break;
				}
			}
		}
		else
		{
			//If moves power is higher than 0, do damages. Then check status change
			if (this.getpattack(this.p2,iAtt).getPower()>0)
				atk0(this.p2, this.p1, iAtt, 0);
			int randomstat = ThreadLocalRandom.current().nextInt(0,100);
			if (randomstat >= 100 - this.getpattack(this.p2, iAtt).getAccu_status())
			{
				switch (this.getpattack(this.p2, iAtt).getStatus())
				{
					case 9 : 
						atk9(this.p2);
						break;
					case 16 :
						atk16(this.p1);
						break;
				}
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
	
	//To write how many hp left the pokémon has. i -> 0 = player, 1 = IA
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
		for (int i = 0;i<att.getType().getStrength().size();i++)
		{
			if (att.getType().getStrength().get(i).equals(pk.getType1()))
			{
				this.s = this.s + "\nIt's super effective ! ";
				return dmg*2;
			}
		}
		//Checking weakness then
		for (int i = 0;i<att.getType().getWeak().size();i++)
		{
			
			if (att.getType().getWeak().get(i).equals(pk.getType1()))
			{
				this.s = this.s + "\nIt's not very effective... ";
				return dmg/2;
			}
		}
		//Checking uselessness
		for (int i = 0;i<pk.getType1().getUseless().size();i++)
		{
			if (att.getType().equals(pk.getType1().getUseless().get(i)))
			{
				this.s = this.s+"\nIt did nothing at all ! ";
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
				this.s = s + "\nA critical hit !";
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
				this.s = s + "\nA critical hit ! ";
				return true;
			}
			else
				return false;
		}
	}
	
	//Mechanics for status 0 attack.
	//atk is attacker, def is defender
	private void atk0(Player atk, Player def, int iAtt, int i)
	{
		int damage = 0;
		int power;
		power = this.getpattack(atk,iAtt).getPower();
		//Verify if STAB
		if (this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType1()) || this.getpattack(atk,iAtt).getType().equals(atk.getCurrentPkmn().getType2()))
		{	
			power = (int) (power * 1.5);
		}
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
		damage = this.checkStrWeak(damage, this.getpattack(atk,iAtt), def.getCurrentPkmn());
		if (damage != 0)
			this.s = this.s + def.getCurrentPkmn().getName()+" lost "+Integer.toString(damage)+" HP.";
		def.getCurrentPkmn().setCurrentHp(def.getCurrentPkmn().getCurrentHp() - damage);
		this.checkHpLeft(def.getCurrentPkmn(),i);
	}
	
	private void atk1(Player def)
	{
		def.getCurrentPkmn().setStatus(1);
		this.s = this.s + "\n" + def.getCurrentPkmn().getName() + " is paralysed ! It may not be able to attack !";
		def.getCurrentPkmn().setCurrentSpd((int) (def.getCurrentPkmn().getBaseSpd()*0.25));
	}
	
	//Mechanics for atk9 which is single atk boost for the user
	private void atk9(Player atk)
	{
		System.out.println(atk.getCurrentPkmn().getStageAtk());
		if (atk.getCurrentPkmn().getStageAtk()>=6)
			this.s = this.s + atk.getCurrentPkmn().getName()+"'s attack won't go higher !";
		else
		{
			atk.getCurrentPkmn().setStageAtk(atk.getCurrentPkmn().getStageAtk() + 1);
			this.s = this.s + atk.getCurrentPkmn().getName()+"'s attack rose !";
			atk.getCurrentPkmn().setCurrentAtk(calculateStat(atk.getCurrentPkmn().getStageAtk(),atk.getCurrentPkmn().getBaseAtk()));
		}
	}
	
	//Mechanics for atk16 which is single def drop
	private void atk16(Player def)
	{
		if (def.getCurrentPkmn().getStageDef()==6)
			this.s = this.s + def.getCurrentPkmn().getName()+"'s defense won't go lower !";
		else
		{
			def.getCurrentPkmn().setStageDef(def.getCurrentPkmn().getStageDef() - 1);
			this.s = this.s + def.getCurrentPkmn().getName()+"'s defense fell !";
			def.getCurrentPkmn().setCurrentDef(calculateStat(def.getCurrentPkmn().getStageDef(),def.getCurrentPkmn().getBaseDef()));
		}
	}
	
	private int calculateStat(int stage,int stat)
	{
		switch (stage)
		{
			case 6 :
				return stat * 4;
			case 5 : 
				return (int) (stat * 3.5);
			case 4 : 
				return (int) (stat * 3);
			case 3 : 
				return (int) (stat * 2.5);
			case 2 : 
				return (int) (stat * 2);
			case 1 :
				return (int) (stat * 1.5);
			case 0 :
				return stat;
			case -1 :
				return (int) (stat * 0.66);
			case -2 :
				return (int) (stat * 0.5);
			case -3 :
				return (int) (stat * 0.4);
			case -4 :
				return (int) (stat * 0.33);
			case -5 :
				return (int) (stat * 0.28);
			case -6 :
				return (int) (stat * 0.25);
		}
		return stat;
	}
}
